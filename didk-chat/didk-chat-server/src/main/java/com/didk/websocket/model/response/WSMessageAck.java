package com.didk.websocket.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSMessageAck {
    /**
     * 前端生成的临时ID (前端靠这个知道是哪条消息发送成功了)
     */
    private String tempId;

    /**
     * 服务端生成的正式消息ID
     */
    private String serverMsgId;

    /**
     * 服务端接收时间戳
     */
    private Date createTime;
}