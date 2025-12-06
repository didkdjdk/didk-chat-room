package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id")
    private Long id;

    @Schema(description = "群聊id")
    private Long roomId;

    @Schema(description = "发布公告用户id")
    private Long userId;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "是否置顶（0否1是）")
    private Integer isPinned;

    @Schema(description = "公告图片文件")
    private MultipartFile imageFile;
}