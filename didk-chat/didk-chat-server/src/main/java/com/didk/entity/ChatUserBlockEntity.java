package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户黑名单表实体类
 */
@Data
@TableName("chat_user_block")
public class ChatUserBlockEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.NONE)
    private Long id;

    /**
     * 用户id（主动拉黑方）
     */
    private Long userId;

    /**
     * 被拉黑用户id
     */
    private Long targetId;

    /**
     * 拉黑时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}