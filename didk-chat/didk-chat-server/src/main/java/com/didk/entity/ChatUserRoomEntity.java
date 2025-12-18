package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户-群聊表实体类
 */
@Data
@TableName("chat_user_room")
public class ChatUserRoomEntity implements Serializable {
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
     * 群聊id
     */
    private Long roomId;

    /**
     * 角色（0群主1管理员2成员）
     */
    private Integer role;

    /**
     * 群昵称
     */
    private String alias;

    /**
     * 是否退出（踢出）群聊0否1是
     */
    private Integer isExit;

    /**
     * 创建日期（入群时间）
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}