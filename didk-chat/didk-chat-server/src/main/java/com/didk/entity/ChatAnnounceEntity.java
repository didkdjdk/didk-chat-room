package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 公告表实体类
 */
@Data
@TableName("chat_announce")
public class ChatAnnounceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 群聊id
     */
    private Long roomId;

    /**
     * 发布公告用户id
     */
    private Long userId;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告图片地址（最多一张）
     */
    private String imageUrl;

    /**
     * 是否置顶（0否1是）
     */
    private Integer isPinned;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}