package com.didk.enums;

import lombok.Getter;

/**
 * 群聊申请状态枚举
 */
@Getter
public enum RoomRequestStatusEnum {
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

    RoomRequestStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static RoomRequestStatusEnum getByCode(int code) {
        for (RoomRequestStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}