package com.didk.enums;

import lombok.Getter;

/**
 * 用户群聊角色枚举
 */
@Getter
public enum UserRoomRoleEnum {
    /**
     * 群主
     */
    OWNER(0, "群主"),
    /**
     * 管理员
     */
    ADMIN(1, "管理员"),
    /**
     * 成员
     */
    MEMBER(2, "成员");

    private final int code;
    private final String desc;

    UserRoomRoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static UserRoomRoleEnum getByCode(int code) {
        for (UserRoomRoleEnum role : values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        return null;
    }
}