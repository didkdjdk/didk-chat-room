package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 好友请求表实体类
 */
@Data
@TableName("chat_friend_request")
public class ChatFriendRequestEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
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
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 处理时间
     */
    private Date processDate;
}