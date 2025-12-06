package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomDTO;
import com.didk.service.ChatRoomService;
import com.didk.vo.ChatRoomVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.didk.commons.tools.page.PageData;

import java.util.List;
import java.util.Map;

/**
 * 群聊控制层
 */
@RestController
@RequestMapping("room")
@Tag(name = "群聊管理")
@Transactional
public class ChatRoomController {

    @Resource
    private ChatRoomService chatRoomService;

    // 查询所有群聊
    @GetMapping("listAllRooms")
    @Operation(summary = "分页查询群聊")
    @Parameters({
        @Parameter(name = "page", description = "当前页码，从1开始", required = true),
        @Parameter(name = "limit", description = "每页显示记录数", required = true),
        @Parameter(name = "orderField", description = "排序字段"),
        @Parameter(name = "order", description = "排序方式，可选值(asc、desc)"),
        @Parameter(name = "roomName", description = "群聊名称"),
        @Parameter(name = "status", description = "状态（0正常1删除）")
    })
    public Result<PageData<ChatRoomVO>> list(@RequestParam Map<String, Object> params) {
        PageData<ChatRoomVO> page = chatRoomService.listAllRooms(params);
        return new Result<PageData<ChatRoomVO>>().ok(page);
    }

    // 根据群聊名称查询群聊
    @GetMapping("listByName")
    @Operation(summary = "根据群聊名称查询群聊")
    public Result<List<ChatRoomVO>> listByName(@RequestParam String roomName) {
        List<ChatRoomVO> data = chatRoomService.listByName(roomName);
        return new Result<List<ChatRoomVO>>().ok(data);
    }

    // 根据群主id查询群聊
    @GetMapping("listByOwnerId/{ownerId}")
    @Operation(summary = "根据群主ID查询群聊")
    public Result<List<ChatRoomVO>> listByOwnerId(@PathVariable Long ownerId) {
        List<ChatRoomVO> data = chatRoomService.listByOwnerId(ownerId);
        return new Result<List<ChatRoomVO>>().ok(data);
    }

    // 根据id查询群聊
    @GetMapping("{roomId}")
    @Operation(summary = "根据ID获取群聊信息")
    public Result<ChatRoomVO> get(@PathVariable("roomId") Long roomId) {
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

    // 批量解散群聊
    @DeleteMapping("deleteBatch")
    @Operation(summary = "批量解散群聊")
    public Result<?> deleteBatch(@RequestBody Long[] ids) {
        chatRoomService.deleteBatch(ids);
        return new Result<>();
    }
}