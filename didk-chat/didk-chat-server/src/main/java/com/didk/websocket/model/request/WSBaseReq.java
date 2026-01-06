package com.didk.websocket.model.request;

import lombok.Data;

@Data
public class WSBaseReq {
    private Integer type;

    private String data;
}