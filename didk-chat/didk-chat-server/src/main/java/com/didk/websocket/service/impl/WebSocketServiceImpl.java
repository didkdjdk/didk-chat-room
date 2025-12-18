package com.didk.websocket.service.impl;

import com.alibaba.fastjson.JSON;
import com.didk.websocket.model.ChatMessageReq;
import com.didk.websocket.service.WebSocketService;
import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService{

    @Resource
    private StringRedisTemplate redisTemplate;

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
     * 处理聊天消息
     * rawMessage: data字段
     */
    @Override
    public void handleChatMessage(Session session, String rawMessage, Long userId) {
        //1. 解析 rawMessage 为的 ChatMessageReq
        ChatMessageReq chatReq = JSON.parseObject(rawMessage, ChatMessageReq.class);

        //2. 业务校验 (是否被禁言、是否是好友)


        //3. 写入 Redis Stream (异步解耦)



        //4. 回复前端 ACK
    }

    @Override
    public void handleHeartbeat(Session session, Long userId) {
        //直接回复一个 PONG 消息
        sendMessage(session, "{\"type\": \"PONG\"}");
    }

    // ================== 私有辅助方法 ==================

    /**
     * 发送消息给指定 Session
     */
    private void sendMessage(Session session, String message) {
        if (session != null && session.isOpen()) {
            try {
                // 使用异步发送防止阻塞
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
