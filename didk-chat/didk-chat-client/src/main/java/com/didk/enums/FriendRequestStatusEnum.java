package com.didk.enums;

import lombok.Getter;

/**
 * 好友请求状态枚举
 */
@Getter
public enum FriendRequestStatusEnum {
    /**
     * 待处理
     */
    PENDING(0, "待处理"),
    /**
     * 已同意
     */
    APPROVED(1, "已同意"),
    /**
     * 已拒绝
     */
    REJECTED(2, "已拒绝");

    private final int code;
    private final String desc;

    FriendRequestStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static FriendRequestStatusEnum getByCode(int code) {
        for (FriendRequestStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}