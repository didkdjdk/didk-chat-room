package com.didk.websocket.model;

import lombok.Data;

@Data
public class WSBaseReq {
    // 对应 WSReqTypeEnum 或简单的 String 常量
    // 例如: "CHAT", "HEARTBEAT", "ACK"
    private String type;

    // 具体的业务数据 JSON 字符串，或者你可以定义为泛型 T
    // 简单起见，这里可以是 String data 或者 Object data
    private String data;
}