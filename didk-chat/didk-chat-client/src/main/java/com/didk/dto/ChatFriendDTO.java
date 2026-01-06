package com.didk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 好友传输类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFriendDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "好友id")
    private Long friendId;

    @Schema(description = "好友名称")
    private String friendName;

    @Schema(description = "好友头像")
    private String friendImage;

    @Schema(description = "好友备注名")
    private String alias;
}