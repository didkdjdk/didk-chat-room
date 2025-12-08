package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomRequestDTO;
import com.didk.service.ChatRoomRequestService;
import com.didk.vo.ChatRoomRequestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 群聊申请控制层
 */
@RestController
@RequestMapping("roomRequest")
@Tag(name = "群聊申请管理")
@Transactional
public class ChatRoomRequestController {

    @Resource
    private ChatRoomRequestService chatRoomRequestService;

    // 根据群聊id查询申请列表
    @GetMapping("listByRoomId/{roomId}")
    @Operation(summary = "根据群聊ID查询申请列表")
    public Result<List<ChatRoomRequestVO>> listByRoomId(@PathVariable Long roomId) {
        List<ChatRoomRequestVO> data = chatRoomRequestService.listByRoomId(roomId);
        return new Result<List<ChatRoomRequestVO>>().ok(data);
    }

    // 根据用户id查询申请列表
    @GetMapping("listByUserId/{userId}")
    @Operation(summary = "根据用户ID查询申请列表")
    public Result<List<ChatRoomRequestVO>> listByUserId(@PathVariable Long userId) {
        List<ChatRoomRequestVO> data = chatRoomRequestService.listByUserId(userId);
        return new Result<List<ChatRoomRequestVO>>().ok(data);
    }

    // 根据id查询申请
    @GetMapping("{requestId}")
    @Operation(summary = "根据ID获取申请信息")
    public Result<ChatRoomRequestVO> get(@PathVariable("requestId") Long requestId) {
        ChatRoomRequestVO data = chatRoomRequestService.get(requestId);
        return new Result<ChatRoomRequestVO>().ok(data);
    }

    // 提交入群申请
    @PostMapping
    @Operation(summary = "提交入群申请")
    public Result<?> save(@RequestBody ChatRoomRequestDTO dto) {
        return chatRoomRequestService.save(dto);
    }

    // 处理入群申请（同意或拒绝）
    @PutMapping("process/{requestId}")
    @Operation(summary = "处理入群申请")
    public Result<?> processRequest(@PathVariable Long requestId, 
                                   @RequestParam Integer status, 
                                   @RequestParam(required = false) String rejectReason) {
        return chatRoomRequestService.processRequest(requestId, status, rejectReason);
    }

}