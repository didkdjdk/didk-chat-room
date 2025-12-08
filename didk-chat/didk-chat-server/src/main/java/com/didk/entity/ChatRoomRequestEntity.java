package com.didk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 群聊申请表实体类
 */
@Data
@TableName("chat_room_request")
public class ChatRoomRequestEntity implements Serializable {
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
     * 申请用户id
     */
    private Long userId;

    /**
     * 状态：0待处理 1已同意 2已拒绝
     */
    private Integer status;

    /**
     * 申请理由/验证消息（用户填写）
     */
    private String applyReason;

    /**
     * 入群问题回答（用户填写，仅在群设置了问题时有效）
     */
    private String answerContent;

    /**
     * 拒绝理由（管理员填写，仅在拒绝时有效）
     */
    private String rejectReason;

    /**
     * 处理人id（管理员或群主）
     */
    private Long handlerId;

    /**
     * 申请时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 处理时间
     */
    private Date processDate;
}