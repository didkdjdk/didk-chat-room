package com.didk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}