package com.didk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 群聊列表项
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "群聊id")
    private Long id;

    @Schema(description = "群聊名称（备注名称）")
    private String name;

    @Schema(description = "群聊头像头像")
    private String image;

}