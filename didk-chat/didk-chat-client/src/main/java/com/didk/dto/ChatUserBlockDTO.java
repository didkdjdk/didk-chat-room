package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户黑名单表传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserBlockDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id（主动拉黑方）")
    private Long userId;

    @Schema(description = "被拉黑用户id")
    private Long targetId;

}