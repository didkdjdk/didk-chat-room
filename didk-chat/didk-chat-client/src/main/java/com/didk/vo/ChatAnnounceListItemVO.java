package com.didk.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告列表展示类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatAnnounceListItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "群聊id")
    private Long roomId;

    @Schema(description = "发布公告用户用户名")
    private String username;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "公告图片地址（最多一张）")
    private String imageUrl;

    @Schema(description = "是否置顶（0否1是）")
    private Integer isPinned;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}