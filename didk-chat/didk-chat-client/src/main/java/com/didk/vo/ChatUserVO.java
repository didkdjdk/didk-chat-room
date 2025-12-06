package com.didk.vo;

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

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String headImage;

    /**
     * 性别
     */
    private int gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 好友备注名
     */
    private String alias;

}