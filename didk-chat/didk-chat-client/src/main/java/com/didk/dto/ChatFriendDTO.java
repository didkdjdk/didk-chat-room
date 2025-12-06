package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 好友传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFriendDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "好友id")
    private Long friendId;

    @Schema(description = "好友名称")
    private String friendName;

    @Schema(description = "状态：1正常 2已删除")
    private Integer status;

    @Schema(description = "好友备注名")
    private String alias;

    @Schema(description = "是否置顶：0否 1是")
    private Integer isPinned;

    @Schema(description = "未读消息数量")
    private Integer unreadCount;

    @Schema(description = "最后一条消息的内容（文本或url地址）")
    private String lastMsgContent;

    @Schema(description = "最后一条消息的创建时间（发送时间）")
    private String lastMsgTime;

    @Schema(description = "最后一条消息的类型(0文本 1图片 2文件 3系统消息 4公告)")
    private Integer lastMsgType;
}