package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 会话列表项
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatConversationListItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "会话id（好友id或群聊id）")
    private Long id;

    @Schema(description = "会话名称")
    private String name;

    @Schema(description = "会话头像")
    private String image;

    @Schema(description = "私聊、群聊区分（FRIEND和ROOM）")
    private String chatType;

    @Schema(description = "是否置顶：0否 1是")
    private Integer isPinned;

    @Schema(description = "未读消息数量")
    private Integer unreadCount;

    @Schema(description = "最后一条消息内容")
    private String lastMsgContent;

    @Schema(description = "最后一条消息时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastMsgTime;

    @Schema(description = "最后一条消息类型(0-文本 1-图片 2-文件 3-系统消息 4-公告)")
    private Integer lastMsgType;

    @Schema(description = "创建日期（正式成为好友时间）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}