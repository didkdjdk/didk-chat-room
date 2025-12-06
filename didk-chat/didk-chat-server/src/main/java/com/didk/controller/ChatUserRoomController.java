package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatUserRoomDTO;
import com.didk.service.ChatUserRoomService;
import com.didk.vo.ChatUserRoomVO;
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
 * 用户-群聊控制层
 */
@RestController
@RequestMapping("userRoom")
@Tag(name = "用户群聊管理")
@Transactional
public class ChatUserRoomController {

    @Resource
    private ChatUserRoomService chatUserRoomService;

    // 查询所有用户-群聊关系
    @GetMapping("listAllUserRooms")
    @Operation(summary = "分页查询用户群聊关系")
    @Parameters({
        @Parameter(name = "page", description = "当前页码，从1开始", required = true),
        @Parameter(name = "limit", description = "每页显示记录数", required = true),
        @Parameter(name = "orderField", description = "排序字段"),
        @Parameter(name = "order", description = "排序方式，可选值(asc、desc)"),
        @Parameter(name = "userId", description = "用户ID"),
        @Parameter(name = "roomId", description = "群聊ID"),
        @Parameter(name = "role", description = "角色（0群主1管理员2成员）"),
        @Parameter(name = "isExit", description = "是否退出（踢出）群聊0否1是")
    })
    public Result<PageData<ChatUserRoomVO>> list(@RequestParam Map<String, Object> params) {
        PageData<ChatUserRoomVO> page = chatUserRoomService.listAllUserRooms(params);
        return new Result<PageData<ChatUserRoomVO>>().ok(page);
    }

    // 根据用户id查询加入的群聊
    @GetMapping("listByUserId/{userId}")
    @Operation(summary = "根据用户ID查询加入的群聊")
    public Result<List<ChatUserRoomVO>> listByUserId(@PathVariable Long userId) {
        List<ChatUserRoomVO> data = chatUserRoomService.listByUserId(userId);
        return new Result<List<ChatUserRoomVO>>().ok(data);
    }

    // 根据群聊id查询群成员
    @GetMapping("listByRoomId/{roomId}")
    @Operation(summary = "根据群聊ID查询群成员")
    public Result<List<ChatUserRoomVO>> listByRoomId(@PathVariable Long roomId) {
        List<ChatUserRoomVO> data = chatUserRoomService.listByRoomId(roomId);
        return new Result<List<ChatUserRoomVO>>().ok(data);
    }

    // 根据id查询用户-群聊关系
    @GetMapping("{userRoomId}")
    @Operation(summary = "根据ID获取用户群聊关系信息")
    public Result<ChatUserRoomVO> get(@PathVariable("userRoomId") Long userRoomId) {
        ChatUserRoomVO data = chatUserRoomService.get(userRoomId);
        return new Result<ChatUserRoomVO>().ok(data);
    }

    // 用户加入群聊
    @PostMapping
    @Operation(summary = "用户加入群聊")
    public Result<?> save(@RequestBody ChatUserRoomDTO dto) {
        return chatUserRoomService.save(dto);
    }

    // 修改用户在群聊中的信息（如群昵称、置顶等）
    @PutMapping
    @Operation(summary = "修改用户在群聊中的信息")
    public Result<?> update(@RequestBody ChatUserRoomDTO dto) {
        return chatUserRoomService.update(dto);
    }

    // 用户退出群聊（或被踢出）
    @DeleteMapping("{userRoomId}")
    @Operation(summary = "用户退出群聊")
    public Result<?> delete(@PathVariable Long userRoomId) {
        chatUserRoomService.delete(userRoomId);
        return new Result<>();
    }

    // 批量删除用户-群聊关系
    @DeleteMapping("deleteBatch")
    @Operation(summary = "批量删除用户群聊关系")
    public Result<?> deleteBatch(@RequestBody Long[] ids) {
        chatUserRoomService.deleteBatch(ids);
        return new Result<>();
    }
}