package com.didk.websocket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResp {
    // === 消息基本信息 ===
    private Long id;            // 消息ID
    private String content;     // 内容
    private Integer msgType;    // 类型 (文本/图片)
    private Date createTime;    // 时间

    // === 发送者信息 (接收者渲染UI必须要有这些) ===
    private UserInfo fromUser;

    @Data
    public static class UserInfo {
        private Long uid;
        private String username;
        private String avatar;
    }
}