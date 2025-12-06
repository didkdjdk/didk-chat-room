package com.didk.enums;

import lombok.Getter;

/**
 * 群聊状态枚举
 */
@Getter
public enum RoomStatusEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 删除
     */
    DELETED(1, "删除");

    private final int code;
    private final String desc;

    RoomStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static RoomStatusEnum getByCode(int code) {
        for (RoomStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}