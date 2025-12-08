package com.didk.enums;

import lombok.Getter;

/**
 * 用户群聊退出状态枚举
 */
@Getter
public enum UserRoomExitStatusEnum {
    /**
     * 未退出
     */
    NOT_EXITED(0, "未退出"),
    /**
     * 已退出
     */
    EXITED(1, "已退出");

    private final int code;
    private final String desc;

    UserRoomExitStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static UserRoomExitStatusEnum getByCode(int code) {
        for (UserRoomExitStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}