package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatFriendDTO;
import com.didk.service.ChatFriendService;
import com.didk.vo.ChatConversationListItemVO;
import com.didk.vo.ChatFriendVO;
import com.didk.vo.ChatUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import com.didk.commons.tools.page.PageData;

import java.util.Map;

/**
 * 好友控制层
 */
@RestController
@RequestMapping("friend")
@Tag(name = "好友管理")
public class ChatFriendController {

    @Resource
    private ChatFriendService chatFriendService;

    // 根据用户ID查询好友、群聊列表
    @GetMapping("listAllFriendsAndRooms")
    @Operation(summary = "根据用户ID查询好友、群聊列表")
    @Parameters({
        @Parameter(name = "page", description = "当前页码，从1开始", required = true),
        @Parameter(name = "limit", description = "每页显示记录数", required = true),
        @Parameter(name = "orderField", description = "排序字段"),
        @Parameter(name = "order", description = "排序方式，可选值(asc、desc)"),
        @Parameter(name = "userId", description = "用户ID"),
        @Parameter(name = "friendName", description = "好友、群聊名称模糊查询"),
    })
    public Result<PageData<ChatConversationListItemVO>> listAllFriendsAndRooms(@RequestParam Map<String, Object> params) {
        PageData<ChatConversationListItemVO> page = chatFriendService.listAllFriendsAndRooms(params);
        return new Result<PageData<ChatConversationListItemVO>>().ok(page);
    }

    // 根据id查询好友关系的相关信息
    @GetMapping("{id}")
    @Operation(summary = "根据ID获取好友关系信息")
    public Result<ChatFriendVO> get(@PathVariable("id") Long id) {
        ChatFriendVO data = chatFriendService.get(id);
        return new Result<ChatFriendVO>().ok(data);
    }

    //以当前用户查询另一个用户的信息
    @GetMapping("user/{friendId}")
    @Operation(summary = "以当前用户查询另一个用户的信息（展示备注）")
    public Result<ChatUserVO> getUserInfo(@PathVariable("friendId") Long friendId) {
        ChatUserVO data = chatFriendService.getUserInfo(friendId);
        return new Result<ChatUserVO>().ok(data);
    }

    // 修改好友关系（如修改备注等）
    @PutMapping
    @Operation(summary = "修改好友关系（如修改备注）")
    public Result<?> update(@RequestBody ChatFriendDTO dto) {
        return chatFriendService.update(dto);
    }

    // 删除好友关系
    @DeleteMapping("{friendId}")
    @Operation(summary = "删除好友关系")
    public Result<?> delete(@PathVariable Long friendId) {
        chatFriendService.delete(friendId);
        return new Result<>();
    }

}