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
public class ChatUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像")
    private String headImage;

    @Schema(description = "性别")
    private int gender;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "个性签名")
    private String signature;

    @Schema(description = "好友备注名")
    private String alias;

}