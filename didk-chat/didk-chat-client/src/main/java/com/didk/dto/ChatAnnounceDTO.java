package com.didk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

/**
 * 公告传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatAnnounceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
     * 是否置顶（0否1是）
     */
    private Integer isPinned;

    /**
     * 公告图片文件
     */
    private MultipartFile imageFile;
}