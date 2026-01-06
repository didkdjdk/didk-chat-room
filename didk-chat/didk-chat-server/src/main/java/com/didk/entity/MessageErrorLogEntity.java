package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息投递错误日志实体
 */
@Data
@TableName("message_error_log")
public class MessageErrorLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id (使用雪花算法或 MP 默认生成)
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 前端临时ID
     */
    private String tempId;

    /**
     * 发送者ID
     */
    private Long sendId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 接收类型 (0私聊 1群聊)
     */
    private Integer receiverType;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 原始消息体 (JSON)
     */
    private String originalPayload;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 状态：0-待处理 1-已修复
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}