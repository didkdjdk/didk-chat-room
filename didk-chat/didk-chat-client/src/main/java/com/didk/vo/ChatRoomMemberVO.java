package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomMemberVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像")
    private String headImage;

    @Schema(description = "角色（0群主、1管理员、2成员）")
    private int role;

}