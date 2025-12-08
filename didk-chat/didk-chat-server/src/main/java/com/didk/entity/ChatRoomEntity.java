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
     * 加群模式：0-无需审批（自由加入），1-需要审批（无需问题），2-需要回答问题并审批
     */
    private Integer joinMode;

    /**
     * 入群问题（当join_mode=2时有效）
     */
    private String joinQuestion;

}