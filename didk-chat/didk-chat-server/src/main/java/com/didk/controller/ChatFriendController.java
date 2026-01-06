package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatFriendDTO;
import com.didk.service.ChatFriendService;
import com.didk.vo.ChatFriendVO;
import com.didk.vo.ChatUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 好友控制层
 */
@RestController
@RequestMapping("friend")
@Tag(name = "好友管理")
public class ChatFriendController {

    @Resource
    private ChatFriendService chatFriendService;

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