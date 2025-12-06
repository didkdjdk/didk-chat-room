package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户-群聊展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserRoomVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "群聊id")
    private Long roomId;

    @Schema(description = "角色（0群主1管理员2成员）")
    private Integer role;

    @Schema(description = "是否置顶（0否1是）")
    private Integer isPinned;

    @Schema(description = "群昵称")
    private String alias;

    @Schema(description = "是否退出（踢出）群聊0否1是")
    private Integer isExit;

    @Schema(description = "查看的最后一条消息的序号")
    private Integer readSeq;

    @Schema(description = "最后一条消息的内容（文本或url地址）")
    private String lastMsgContent;

    @Schema(description = "最后一条消息的创建时间（发送时间）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastMsgTime;

    @Schema(description = "最后一条消息的类型(0文本 1图片 2文件 3系统消息 4公告)")
    private Integer lastMsgType;

    @Schema(description = "创建日期（入群时间）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @Schema(description = "用户最后阅读群聊时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lookTime;
}