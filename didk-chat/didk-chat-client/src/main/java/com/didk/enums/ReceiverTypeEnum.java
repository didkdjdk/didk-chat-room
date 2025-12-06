package com.didk.enums;

import lombok.Getter;

/**
 * 接收者类型枚举
 */
@Getter
public enum ReceiverTypeEnum {
    /**
     * 用户
     */
    USER(0, "用户"),
    /**
     * 群聊
     */
    ROOM(1, "群聊");

    private final int code;
    private final String desc;

    ReceiverTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static ReceiverTypeEnum getByCode(int code) {
        for (ReceiverTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}