package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 好友请求传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFriendRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "发送请求用户id")
    private Long fromUserId;

    @Schema(description = "接收请求用户id")
    private Long toUserId;

    @Schema(description = "状态：0待处理 1已同意 2已拒绝")
    private Integer status;

    @Schema(description = "验证消息")
    private String verifyMessage;
}