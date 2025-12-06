package com.didk.dto;

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

    /**
     * id
     */
    private Long id;

    /**
     * 发送请求用户id
     */
    private Long fromUserId;

    /**
     * 接收请求用户id
     */
    private Long toUserId;

    /**
     * 状态：0待处理 1已同意 2已拒绝
     */
    private Integer status;

    /**
     * 验证消息
     */
    private String verifyMessage;
}