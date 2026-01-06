package com.didk.websocket.service;

import com.didk.websocket.model.response.WSBaseResp;
import jakarta.websocket.Session;

public interface WebSocketService {
    /**
     * 处理用户上线
     * (维护在线列表、广播上线通知等)
     */
    void connect(Session session, Long userIdId);

    /**
     * 处理用户下线
     * (移除连接、更新状态、计算时长等)
     */
    void disconnect(Session session, Long userId);

    void sendToUid(WSBaseResp<?> wsBaseResp, Long uid);

    /**
     * 处理聊天消息
     * (写入 Redis Stream、转发给接收人等)
     * @param rawMessage 原始 JSON 消息，Service 内部再解析成具体的 ChatMessageVo
     */
    void handleChatMessage(Session session, String rawMessage, Long userId);

    /**
     * 处理心跳
     * (回复 PONG)
     */
    void handleHeartbeat(Session session, Long userId);

}
