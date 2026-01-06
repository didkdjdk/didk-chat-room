package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 会话传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatSessionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "归属用户ID（列表的所有者）")
    private Long userId;

    @Schema(description = "目标ID（对方用户ID 或 群聊ID）")
    private Long targetId;

    @Schema(description = "会话类型：0-私聊 1-群聊")
    private Integer type;

    @Schema(description = "会话名称（缓存的对方昵称/群名）")
    private String name;

    @Schema(description = "会话头像（缓存的对方头像/群头像）")
    private String headImage;

    @Schema(description = "最后一条消息简述")
    private String lastMsgContent;

    @Schema(description = "最后一条消息时间（用于排序）")
    private String lastMsgTime;

    @Schema(description = "未读消息数")
    private Integer unreadCount;

    @Schema(description = "是否删除（隐藏）：0否 1是")
    private Integer isDelete;

    @Schema(description = "是否置顶：0否 1是")
    private Integer isPinned;

}