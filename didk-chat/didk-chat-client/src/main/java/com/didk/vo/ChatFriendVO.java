package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id")
    private Long id;

    @Schema(description = "好友id")
    private Long friendId;

    @Schema(description = "好友名称")
    private String friendName;

    @Schema(description = "好友备注名")
    private String alias;

    @Schema(description = "是否置顶：0否 1是")
    private Integer isPinned;

    @Schema(description = "创建日期（正式成为好友时间）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}