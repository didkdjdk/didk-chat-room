package com.didk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFriendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 好友id
     */
    private Long friendId;

    /**
     * 好友名称
     */
    private String friendName;

    /**
     * 好友备注名
     */
    private String alias;

    /**
     * 是否置顶：0否 1是
     */
    private Integer isPinned;

    /**
     * 创建日期（正式成为好友时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}