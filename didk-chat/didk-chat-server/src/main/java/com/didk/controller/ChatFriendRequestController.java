package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatFriendRequestDTO;
import com.didk.service.ChatFriendRequestService;
import com.didk.vo.ChatFriendRequestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 好友请求控制层
 */
@RestController
@RequestMapping("friendRequest")
@Tag(name = "好友请求管理")
@Transactional
public class ChatFriendRequestController {

    @Resource
    private ChatFriendRequestService chatFriendRequestService;

    // 根据用户id查询好友请求列表(查询发送方或接收方是自己的好友请求)
    @GetMapping("listByUserId/{userId}")
    @Operation(summary = "根据用户ID查询好友请求列表")
    public Result<List<ChatFriendRequestVO>> listByUserId(@PathVariable Long userId) {
        List<ChatFriendRequestVO> data = chatFriendRequestService.listByUserId(userId);
        return new Result<List<ChatFriendRequestVO>>().ok(data);
    }

    // 根据id查询好友请求
    @GetMapping("{requestId}")
    @Operation(summary = "根据ID获取好友请求信息")
    public Result<ChatFriendRequestVO> get(@PathVariable("requestId") Long requestId) {
        ChatFriendRequestVO data = chatFriendRequestService.get(requestId);
        return new Result<ChatFriendRequestVO>().ok(data);
    }

    // 发送好友请求
    @PostMapping
    @Operation(summary = "发送好友请求")
    public Result<?> save(@RequestBody ChatFriendRequestDTO dto) {
        return chatFriendRequestService.save(dto);
    }

    // 处理好友请求（同意或拒绝）
    @PutMapping("process/{requestId}")
    @Operation(summary = "处理好友请求")
    public Result<?> processRequest(@PathVariable Long requestId, @RequestParam Integer status) {
        return chatFriendRequestService.processRequest(requestId, status);
    }

}