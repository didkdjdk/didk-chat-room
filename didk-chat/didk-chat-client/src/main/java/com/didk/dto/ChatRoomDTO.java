package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

/**
 * 群聊传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "群聊名称")
    private String roomName;

    @Schema(description = "当前成员数（最多500人）")
    private Integer currentMembers;

    @Schema(description = "总消息数量")
    private Integer totalSeq;

    @Schema(description = "群描述")
    private String description;

    @Schema(description = "群主id")
    private Long ownerId;

    @Schema(description = "加群模式：0-无需审批（自由加入），1-需要审批（无需问题），2-需要回答问题并审批")
    private Integer joinMode;

    @Schema(description = "入群问题（当join_mode=2时有效）")
    private String joinQuestion;

    @Schema(description = "群头像文件")
    private MultipartFile headImageFile;
}