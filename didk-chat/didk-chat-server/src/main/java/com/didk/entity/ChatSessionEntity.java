package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 统一会话表实体类
 */
@Data
@TableName("chat_session")
public class ChatSessionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 归属用户ID（列表的所有者）
     */
    private Long userId;

    /**
     * 目标ID（对方用户ID 或 群聊ID）
     */
    private Long targetId;

    /**
     * 会话类型：0-私聊 1-群聊
     */
    private Integer type;

    /**
     * 会话名称（缓存的对方昵称/群名）
     */
    private String name;

    /**
     * 会话头像（缓存的对方头像/群头像）
     */
    private String headImage;

    /**
     * 最后一条消息简述
     */
    private String lastMsgContent;

    /**
     * 最后一条消息时间（用于排序）
     */
    private Date lastMsgTime;

    /**
     * 未读消息数
     */
    private Integer unreadCount;

    /**
     * 是否置顶：0否 1是
     */
    private Integer isTop;

    /**
     * 状态：0正常 1隐藏/删除
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;
}