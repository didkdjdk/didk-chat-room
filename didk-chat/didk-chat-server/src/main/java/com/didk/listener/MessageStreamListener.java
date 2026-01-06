package com.didk.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.didk.config.RedisStreamConfig;
import com.didk.entity.ChatMessageEntity;
import com.didk.entity.ChatSessionEntity;
import com.didk.entity.ChatUserRoomEntity;
import com.didk.enums.MessageTypeEnum;
import com.didk.enums.ReceiverTypeEnum;
import com.didk.service.ChatMessageService;
import com.didk.service.ChatSessionService;
import com.didk.service.ChatUserRoomService;
import com.didk.websocket.adapter.WSAdapter;
import com.didk.websocket.model.response.ChatMessageResp;
import com.didk.websocket.model.response.WSBaseResp;
import com.didk.websocket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessage;
import org.springframework.data.redis.connection.stream.PendingMessages;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 消息日志消费者
 * 负责：Redis Stream -> MySQL (消息入库 + 会话更新) -> WebSocket Push
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    private final ChatMessageService chatMessageService;
    private final ChatSessionService chatSessionService;
    private final ChatUserRoomService chatUserRoomService;
    private final WebSocketService webSocketService;
    private final WSAdapter wsAdapter;
    private final TransactionTemplate transactionTemplate;
    private final StringRedisTemplate redisTemplate;

    private static final String DLQ_STREAM_KEY = "im:message:dlq";
    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public void onMessage(MapRecord<String, String, String> record) {
        Map<String, String> value = record.getValue();
        String tempId = value.get("tempId");

        try {
            // 1. 解析数据
            String serverMsgIdStr = value.get("server_msg_id");
            Long serverMsgId = serverMsgIdStr != null ? Long.valueOf(serverMsgIdStr) : null;

            Long sendId = Long.valueOf(value.get("fromUserId"));
            String content = value.get("content");
            int receiverType = Integer.parseInt(value.get("type"));
            Long targetId = Long.valueOf(value.get("targetId"));

            String msgTypeStr = value.get("messageType");
            int messageType = msgTypeStr != null ? Integer.parseInt(msgTypeStr) : MessageTypeEnum.TEXT.getCode();

            // 2. 幂等性检查
            if (serverMsgId != null) {
                ChatMessageEntity existed = chatMessageService.getOne(new LambdaQueryWrapper<ChatMessageEntity>()
                        .eq(ChatMessageEntity::getServerMsgId, serverMsgId)
                        .last("limit 1"));
                if (existed != null) {
                    log.warn("消息重复消费，执行幂等: tempId={}, id={}", tempId, existed.getId());
                    pushMessage(existed, tempId);
                    ackMessage(record);
                    return;
                }
            }

            log.info("Stream 收到消息: tempId={}", tempId);

            // 3. 数据库事务
            ChatMessageEntity savedMessage = transactionTemplate.execute(status -> {
                Date now = new Date();

                ChatMessageEntity message = new ChatMessageEntity();
                message.setSendId(sendId);
                message.setReceiverId(targetId);
                message.setReceiverType(receiverType);
                message.setContent(content);
                message.setMessageType(messageType);
                message.setServerMsgId(serverMsgId);
                message.setCreateDate(now);

                chatMessageService.save(message);

                updateSessions(message, sendId, targetId, receiverType, content, now);

                return message;
            });

            if (savedMessage == null) return;

            // 4. WebSocket 推送
            pushMessage(savedMessage, tempId);

            // 5. ACK
            ackMessage(record);

        } catch (Exception e) {
            log.error("消息消费异常: tempId={}", tempId, e);
            handleException(record);
        }
    }

    private void updateSessions(ChatMessageEntity message, Long sendId, Long targetId, int receiverType, String content, Date now) {
        String lastMsgContent = (message.getMessageType() == MessageTypeEnum.TEXT.getCode()) ? content : "[非文本消息]";

        if (receiverType == ReceiverTypeEnum.USER.getCode()) {
            updateSingleSession(sendId, targetId, receiverType, lastMsgContent, now, false);
            updateSingleSession(targetId, sendId, receiverType, lastMsgContent, now, true);
        } else if (receiverType == ReceiverTypeEnum.ROOM.getCode()) {
            List<ChatUserRoomEntity> members = chatUserRoomService.list(new LambdaQueryWrapper<ChatUserRoomEntity>()
                    .eq(ChatUserRoomEntity::getRoomId, targetId));
            for (ChatUserRoomEntity member : members) {
                boolean isSelf = member.getUserId().equals(sendId);
                updateSingleSession(member.getUserId(), targetId, receiverType, lastMsgContent, now, !isSelf);
            }
        }
    }

    private void updateSingleSession(Long userId, Long targetId, int type, String content, Date time, boolean increaseUnread) {
        ChatSessionEntity session = chatSessionService.getOne(new LambdaQueryWrapper<ChatSessionEntity>()
                .eq(ChatSessionEntity::getUserId, userId)
                .eq(ChatSessionEntity::getTargetId, targetId)
                .eq(ChatSessionEntity::getType, type));

        if (session == null) {
            session = new ChatSessionEntity();
            session.setUserId(userId);
            session.setTargetId(targetId);
            session.setType(type);
            session.setName("会话" + targetId);
            session.setUnreadCount(increaseUnread ? 1 : 0);
            session.setLastMsgContent(content);
            session.setLastMsgTime(time);
            session.setIsDelete(0);
            session.setIsPinned(0);
            chatSessionService.save(session);
        } else {
            LambdaUpdateWrapper<ChatSessionEntity> update = new LambdaUpdateWrapper<>();
            update.eq(ChatSessionEntity::getId, session.getId())
                    .set(ChatSessionEntity::getLastMsgContent, content)
                    .set(ChatSessionEntity::getLastMsgTime, time)
                    .set(ChatSessionEntity::getIsDelete, 0);
            if (increaseUnread) {
                update.setSql("unread_count = unread_count + 1");
            }
            chatSessionService.update(update);
        }
    }

    /**
     * 推送消息逻辑
     */
    private void pushMessage(ChatMessageEntity message, String tempId) {
        // 使用 Adapter 构建带 tempId 的响应对象
        WSBaseResp<ChatMessageResp> wsResp = wsAdapter.buildChatMessageResp(message, tempId);

        // 1. 发送给发送者 (为了回执，tempId 必须正确)
        webSocketService.sendToUid(wsResp, message.getSendId());

        // 2. 发送给其他接收人
        if (Objects.equals(message.getReceiverType(), ReceiverTypeEnum.USER.getCode())) {
            webSocketService.sendToUid(wsResp, message.getReceiverId());
        } else {
            List<ChatUserRoomEntity> members = chatUserRoomService.list(new LambdaQueryWrapper<ChatUserRoomEntity>()
                    .eq(ChatUserRoomEntity::getRoomId, message.getReceiverId()));

            for (ChatUserRoomEntity member : members) {
                if (!member.getUserId().equals(message.getSendId())) {
                    webSocketService.sendToUid(wsResp, member.getUserId());
                }
            }
        }
    }

    private void ackMessage(MapRecord<String, String, String> record) {
        redisTemplate.opsForStream().acknowledge(
                RedisStreamConfig.IM_STREAM_KEY,
                RedisStreamConfig.IM_GROUP,
                record.getId());
    }

    private void handleException(MapRecord<String, String, String> record) {
        try {
            PendingMessages pendingMessages = redisTemplate.opsForStream().pending(
                    RedisStreamConfig.IM_STREAM_KEY,
                    Consumer.from(RedisStreamConfig.IM_GROUP, RedisStreamConfig.IM_CONSUMER),
                    Range.just(record.getId().getValue()),
                    1L);

            if (pendingMessages.isEmpty()) return;

            PendingMessage pendingMessage = pendingMessages.get(0);
            if (pendingMessage.getTotalDeliveryCount() >= MAX_RETRY_COUNT) {
                log.warn("DLQ: id={}", record.getId());
                redisTemplate.opsForStream().add(DLQ_STREAM_KEY, record.getValue());
                ackMessage(record);
            }
        } catch (Exception ex) {
            log.error("DLQ Error", ex);
        }
    }
}