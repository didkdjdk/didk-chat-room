package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 群聊表实体类
 */
@Data
@TableName("chat_room")
public class ChatRoomEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 群聊名称
     */
    private String roomName;

    /**
     * 头像地址
     */
    private String headUrl;

    /**
     * 当前成员数（最多500人）
     */
    private Integer currentMembers;

    /**
     * 总消息数量
     */
    private Integer totalSeq;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 群描述
     */
    private String description;

    /**
     * 群主id
     */
    private Long ownerId;

    /**
     * 状态（0正常1删除）
     */
    private Integer status;
}