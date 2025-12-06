package com.didk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

/**
 * 群聊传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO implements Serializable {
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
     * 当前成员数（最多500人）
     */
    private Integer currentMembers;

    /**
     * 总消息数量
     */
    private Integer totalSeq;

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

    /**
     * 群头像文件
     */
    private MultipartFile headImageFile;
}