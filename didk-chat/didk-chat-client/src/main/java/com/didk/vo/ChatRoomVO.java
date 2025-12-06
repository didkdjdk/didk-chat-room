package com.didk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 群聊展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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