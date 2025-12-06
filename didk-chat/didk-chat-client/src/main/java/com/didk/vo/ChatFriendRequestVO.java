package com.didk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友请求展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFriendRequestVO implements Serializable {
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

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date processDate;
}