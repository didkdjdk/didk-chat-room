package com.didk.enums;

import lombok.Getter;

/**
 * 消息类型枚举
 */
@Getter
public enum MessageTypeEnum {
    /**
     * 文本消息
     */
    TEXT(0, "文本"),
    /**
     * 图片消息
     */
    IMAGE(1, "图片"),
    /**
     * 文件消息
     */
    FILE(2, "文件"),
    /**
     * 系统消息
     */
    SYSTEM(3, "系统消息"),
    /**
     * 公告消息
     */
    ANNOUNCEMENT(4, "公告");

    private final int code;
    private final String desc;

    MessageTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static MessageTypeEnum getByCode(int code) {
        for (MessageTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}