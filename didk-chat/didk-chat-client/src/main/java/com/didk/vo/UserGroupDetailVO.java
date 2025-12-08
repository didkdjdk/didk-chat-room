package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户群聊详情返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "群聊id")
    private Long id;

    @Schema(description = "群聊名称")
    private String roomName;

    @Schema(description = "群聊头像")
    private String headUrl;

    @Schema(description = "当前成员数")
    private Integer currentMembers;

    @Schema(description = "是否置顶：0否 1是")
    private Integer isPinned;

    @Schema(description = "群描述")
    private String description;

    @Schema(description = "群主id")
    private Long ownerId;

    @Schema(description = "用户对群聊的昵称")
    private String alias;

    @Schema(description = "用户角色（0群主1管理员2成员）")
    private Integer userRole;

    @Schema(description = "最新一条公告的文本内容")
    private String latestAnnounceContent;
}