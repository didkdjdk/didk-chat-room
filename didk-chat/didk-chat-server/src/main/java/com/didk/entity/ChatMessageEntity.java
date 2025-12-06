package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息表实体类
 */
@Data
@TableName("chat_message")
public class ChatMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 发送者id
     */
    private Long sendId;

    /**
     * 接收者类型（0用户 1群聊）
     */
    private Integer receiverType;

    /**
     * 接收者id
     */
    private Long receiverId;

    /**
     * 消息类型：0-文本 1-图片 2-文件 3-系统消息 4-公告
     */
    private Integer messageType;

    /**
     * 内容（文本或url地址）
     */
    private String content;

    /**
     * 公告id（如果是公告信息，为null代表对应公告已修改或删除）
     */
    private Long announceId;

    /**
     * 创建时间（发送时间）
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}