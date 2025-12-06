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
     * 状态：1正常 2已删除
     */
    private Integer status;

    /**
     * 好友备注名
     */
    private String alias;

    /**
     * 是否置顶：0否 1是
     */
    private Integer isPinned;

    /**
     * 未读消息数量
     */
    private Integer unreadCount;

    /**
     * 最后一条消息的内容（文本或url地址）
     */
    private String lastMsgContent;

    /**
     * 最后一条消息的创建时间（发送时间）
     */
    private Date lastMsgTime;

    /**
     * 最后一条消息的类型(0文本 1图片 2文件 3系统消息 4公告)
     */
    private Integer lastMsgType;

    /**
     * 创建日期（正式成为好友时间）
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}