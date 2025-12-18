package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomDTO;
import com.didk.service.ChatRoomService;
import com.didk.vo.ChatRoomVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 群聊控制层
 */
@RestController
@RequestMapping("room")
@Tag(name = "群聊管理")
public class ChatRoomController {

    @Resource
    private ChatRoomService chatRoomService;

    // 根据id查询群聊
    @GetMapping("{roomId}")
    @Operation(summary = "根据ID获取群聊信息")
    public Result<ChatRoomVO> getById(@PathVariable("roomId") Long roomId) {
        ChatRoomVO data = chatRoomService.get(roomId);
        return new Result<ChatRoomVO>().ok(data);
    }

    // 创建群聊
    @PostMapping
    @Operation(summary = "创建群聊")
    public Result<?> save(@RequestBody ChatRoomDTO dto) {
        return chatRoomService.save(dto);
    }

    // 修改群聊信息
    @PutMapping
    @Operation(summary = "修改群聊信息")
    public Result<?> update(@RequestBody ChatRoomDTO dto) {
        return chatRoomService.update(dto);
    }

    // 解散群聊
    @DeleteMapping("{roomId}")
    @Operation(summary = "解散群聊")
    public Result<?> delete(@PathVariable Long roomId) {
        chatRoomService.delete(roomId);
        return new Result<>();
    }

}