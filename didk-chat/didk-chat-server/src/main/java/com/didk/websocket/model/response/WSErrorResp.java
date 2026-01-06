package com.didk.websocket.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket 错误响应实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSErrorResp {
    /**
     * 前端生成的临时ID
     */
    private String tempId;

    /**
     * 错误原因 (例如：服务器繁忙、Redis连接失败等)
     */
    private String reason;
}