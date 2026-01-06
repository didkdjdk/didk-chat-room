package com.didk.websocket.model.request;

import lombok.Data;

@Data
public class ChatMessageReq {
    // 接收者 ID (私聊是用户ID，群聊是房间ID)
    private Long toUserId;

    // 消息内容
    private String content;

    // 消息类型 (0:文本, 1:图片, 2:文件,3:公告)
    private Integer msgType;

    // 聊天类型 (1:私聊, 2:群聊)
    private Integer chatType;

    // 前端生成的临时ID (用于 ACK 确认)
    private String tempId;
}
