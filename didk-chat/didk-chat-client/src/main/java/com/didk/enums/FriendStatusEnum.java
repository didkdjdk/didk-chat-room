package com.didk.enums;

import lombok.Getter;

/**
 * 好友状态枚举
 */
@Getter
public enum FriendStatusEnum {
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 已删除
     */
    DELETED(2, "已删除");

    private final int code;
    private final String desc;

    FriendStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static FriendStatusEnum getByCode(int code) {
        for (FriendStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}