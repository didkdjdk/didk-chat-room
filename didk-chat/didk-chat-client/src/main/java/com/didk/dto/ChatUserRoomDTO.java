package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 用户-群聊传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserRoomDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "群聊id")
    private Long roomId;

    @Schema(description = "角色（0群主1管理员2成员）")
    private Integer role;

    @Schema(description = "群昵称")
    private String alias;

    @Schema(description = "是否退出（踢出）群聊0否1是")
    private Integer isExit;
}