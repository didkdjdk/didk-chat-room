package com.didk.websocket.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.didk.config.RedisStreamConfig;
import com.didk.config.ThreadPoolConfig;
import com.didk.enums.WSReqTypeEnum;
import com.didk.websocket.model.request.ChatMessageReq;
import com.didk.websocket.model.response.WSBaseResp;
import com.didk.websocket.model.response.WSErrorResp;
import com.didk.websocket.model.response.WSMessageAck;
import com.didk.websocket.service.WebSocketService;
import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService{

    @Resource
    private StringRedisTemplate redisTemplate;
    @Resource(name = ThreadPoolConfig.WS_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // ================== 会话管理 (内存级) ==================

    // 1. 用户 ID -> Session 列表 (支持多端登录)
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Session>> ONLINE_UID_MAP = new ConcurrentHashMap<>();

    // 2. Session -> 用户 ID (用于快速查找归属者)
    private static final ConcurrentHashMap<Session, Long> ONLINE_WS_MAP = new ConcurrentHashMap<>();


    // ================== 业务方法实现 ==================

    @Override
    public void connect(Session session, Long userId) {
        //将 Session 存入 map
        ONLINE_UID_MAP.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(session);

        ONLINE_WS_MAP.put(session,userId);

        log.info("用户上线: {}, 当前在线人数: {}", userId, ONLINE_UID_MAP.size());
    }

    @Override
    public void disconnect(Session session, Long userId) {
        //从 map 中移除 Session
        CopyOnWriteArrayList<Session> sessions = ONLINE_UID_MAP.get(userId);
        if (sessions != null){
            sessions.remove(session);
            //如果用户的所有端都下线了，则直接将该用户从Map中删除
            if (sessions.isEmpty()){
                ONLINE_UID_MAP.remove(userId);
            }
        }

        ONLINE_WS_MAP.remove(session);

        log.info("用户下线: {}", userId);
    }

    /**
     * 给某个用户发送信息
     */
    @Override
    public void sendToUid(WSBaseResp<?> wsBaseResp, Long uid) {
        CopyOnWriteArrayList<Session> sessions = ONLINE_UID_MAP.get(uid);
        if (CollUtil.isEmpty(sessions)) {
            log.info("用户：{}不在线", uid);
            return;
        }
        // 推送给该用户的所有在线设备
        sessions.forEach(channel -> threadPoolTaskExecutor.execute(() -> sendMessage(channel, wsBaseResp)));
    }

    /**
     * 处理聊天消息
     * rawMessage: data字段
     */
    @Override
    public void handleChatMessage(Session session, String rawMessage, Long userId) {
        //解析 rawMessage 为的 ChatMessageReq
        ChatMessageReq chatReq = JSON.parseObject(rawMessage, ChatMessageReq.class);

        // 校验 tempId (前端生成的消息唯一标识，用于 ACK)
        String tempId = chatReq.getTempId();
        if (StrUtil.isBlank(tempId)) {
            return; // 缺少关键参数，直接丢弃
        }

        // 准备存入 Redis Stream 的消息体
        String serverMsgId = String.valueOf(IdWorker.getId()); // MyBatis-Plus 的雪花算法
        long serverTs = System.currentTimeMillis();

        // 构建 Redis Stream 消息体 (Map<String, String>)
        Map<String, String> streamMessage = new HashMap<>();
        streamMessage.put("serverMsgId", serverMsgId);
        streamMessage.put("tempId", tempId);
        streamMessage.put("fromUserId", String.valueOf(userId));
        streamMessage.put("targetId", String.valueOf(chatReq.getToUserId())); // 接收者ID 或 群ID
        streamMessage.put("content", chatReq.getContent());
        streamMessage.put("receiverType", String.valueOf(chatReq.getChatType()));
        // 传递消息内容类型 (默认文本)
        Integer msgType = chatReq.getMsgType();
        streamMessage.put("messageType", String.valueOf(msgType != null ? msgType : 1));
        streamMessage.put("createTime", String.valueOf(serverTs));

        try {
            // 5. 写入 Redis Stream (异步解耦)
            // 确保 RedisStreamConfig.IM_STREAM_KEY 常量已定义 (例如 "im:message:stream")
            RecordId recordId = redisTemplate.opsForStream().add(
                    RedisStreamConfig.IM_STREAM_KEY,
                    streamMessage
            );

            log.info("消息写入 Stream 成功, StreamId: {}, TempId: {}", recordId, tempId);

            // 6. 立即回复 ACK 给发送者
            // 告诉前端：“服务器已收到你的消息，暂定ID为 serverMsgId，请将消息状态改为‘发送成功’”
            WSMessageAck ackData = new WSMessageAck(tempId, serverMsgId, new Date(serverTs));
            sendAck(session, ackData);

        } catch (Exception e) {
            log.error("消息写入 Redis 失败", e);
            // 7. 发生异常，通知前端发送失败
            sendError(session, tempId, "服务器繁忙，发送失败");
        }
    }

    @Override
    public void handleHeartbeat(Session session, Long userId) {
        //直接回复一个 PONG 消息
        sendMessage(session, new WSBaseResp<>(WSReqTypeEnum.ACK.getType(), "{\"type\": \"PONG\"}"));
    }

    // ================== 私有辅助方法 ==================

    /**
     * 发送消息给指定 Session
     */
    private void sendMessage(Session session, WSBaseResp<?> response) {
        if (session != null && session.isOpen()) {
            try {
                String jsonStr = JSON.toJSONString(response);

                // 使用异步发送防止阻塞
                session.getBasicRemote().sendText(jsonStr);
            } catch (IOException e) {
                log.error("消息发送失败, userId: {}", ONLINE_WS_MAP.get(session), e);
            }
        }
    }

    /**
     * 发送 ACK 确认消息
     */
    private void sendAck(Session session, WSMessageAck ackData) {
        WSBaseResp<WSMessageAck> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSReqTypeEnum.ACK.getType());
        wsBaseResp.setData(ackData);

        sendMessage(session, wsBaseResp);
    }

    /**
     * 发送错误通知
     */
    private void sendError(Session session, String tempId, String msg) {
        // 1. 构建错误响应体
        WSErrorResp errorData = new WSErrorResp(tempId, msg);

        // 2. 封装 WS 协议包
        WSBaseResp<WSErrorResp> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSReqTypeEnum.ERROR.getType());
        wsBaseResp.setData(errorData);

        sendMessage(session, wsBaseResp);
    }


}
