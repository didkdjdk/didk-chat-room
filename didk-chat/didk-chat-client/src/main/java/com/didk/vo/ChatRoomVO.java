package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 群聊展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "群聊名称")
    private String roomName;

    @Schema(description = "头像地址")
    private String headUrl;

    @Schema(description = "当前成员数（最多500人）")
    private Integer currentMembers;

    @Schema(description = "总消息数量")
    private Integer totalSeq;

    @Schema(description = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @Schema(description = "群描述")
    private String description;

    @Schema(description = "群主id")
    private Long ownerId;

    @Schema(description = "加群模式：0-无需审批（自由加入），1-需要审批（无需问题），2-需要回答问题并审批")
    private Integer joinMode;

    @Schema(description = "入群问题（当join_mode=2时有效）")
    private String joinQuestion;

}