package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 群聊申请传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "群聊id")
    private Long roomId;

    @Schema(description = "申请用户id")
    private Long userId;

    @Schema(description = "状态：0待处理 1已同意 2已拒绝")
    private Integer status;

    @Schema(description = "申请理由/验证消息（用户填写）")
    private String applyReason;

    @Schema(description = "入群问题回答（用户填写，仅在群设置了问题时有效）")
    private String answerContent;

    @Schema(description = "拒绝理由（管理员填写，仅在拒绝时有效）")
    private String rejectReason;

    @Schema(description = "处理人id（管理员或群主）")
    private Long handlerId;
}