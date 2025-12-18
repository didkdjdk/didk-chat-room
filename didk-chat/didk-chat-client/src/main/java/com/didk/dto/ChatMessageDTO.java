package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 消息传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    //服务端消息id
    private Long serverMsgId;

    @Schema(description = "发送者id")
    private Long sendId;

    @Schema(description = "接收者类型（0用户 1群聊）")
    private Integer receiverType;

    @Schema(description = "接收者id")
    private Long receiverId;

    @Schema(description = "消息类型：0-文本 1-图片 2-文件 3-公告")
    private Integer messageType;

    @Schema(description = "内容（文本或url地址）")
    private String content;

    @Schema(description = "公告id（如果是公告信息，为null代表对应公告已修改或删除）")
    private Long announceId;

    @Schema(description = "消息附件（图片或文件）")
    private List<String> attachments;
}