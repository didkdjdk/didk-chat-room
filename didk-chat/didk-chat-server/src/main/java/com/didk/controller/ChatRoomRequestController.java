package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomRequestDTO;
import com.didk.service.ChatRoomRequestService;
import com.didk.vo.ChatRoomRequestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 群聊申请控制层
 */
@RestController
@RequestMapping("roomRequest")
@Tag(name = "群聊申请管理")
public class ChatRoomRequestController {

    @Resource
    private ChatRoomRequestService chatRoomRequestService;

    // 根据群聊id游标查询申请列表
    @GetMapping("listByRoomId")
    @Operation(summary = "根据群聊ID游标查询申请列表")
    @Parameters({
            @Parameter(name = "limit", description = "每页显示记录数(默认50)"),
            @Parameter(name = "roomId", description = "群聊ID"),
            @Parameter(name = "lastCreateDate", description = "最后一条请求的创建时间")
    })
    public Result<List<ChatRoomRequestVO>> listByRoomId(@RequestParam Map<String, Object> params) {
        List<ChatRoomRequestVO> data = chatRoomRequestService.listByRoomId(params);
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