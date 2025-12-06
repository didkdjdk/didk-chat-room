package com.didk.enums;

import lombok.Getter;

/**
 * 是否置顶枚举
 */
@Getter
public enum PinnedStatusEnum {
    /**
     * 未置顶
     */
    NOT_PINNED(0, "未置顶"),
    /**
     * 已置顶
     */
    PINNED(1, "已置顶");

    private final int code;
    private final String desc;

    PinnedStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static PinnedStatusEnum getByCode(int code) {
        for (PinnedStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}