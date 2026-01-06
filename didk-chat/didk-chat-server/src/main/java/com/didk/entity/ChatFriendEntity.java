package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 好友表实体类
 */
@Data
@TableName("chat_friend")
public class ChatFriendEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 好友id
     */
    private Long friendId;

    /**
     * 好友名称
     */
    private String friendName;

    /**
     * 好友头像
     */
    private String friendImage;

    /**
     * 好友备注名
     */
    private String alias;

    /**
     * 创建日期（正式成为好友时间）
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}