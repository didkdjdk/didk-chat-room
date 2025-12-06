package com.didk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 用户-群聊传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserRoomDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
     * 是否置顶（0否1是）
     */
    private Integer isPinned;

    /**
     * 群昵称
     */
    private String alias;

    /**
     * 是否退出（踢出）群聊0否1是
     */
    private Integer isExit;

    /**
     * 查看的最后一条消息的序号
     */
    private Integer readSeq;

    /**
     * 最后一条消息的内容（文本或url地址）
     */
    private String lastMsgContent;

    /**
     * 最后一条消息的创建时间（发送时间）
     */
    private String lastMsgTime;

    /**
     * 最后一条消息的类型(0文本 1图片 2文件 3系统消息 4公告)
     */
    private Integer lastMsgType;
}