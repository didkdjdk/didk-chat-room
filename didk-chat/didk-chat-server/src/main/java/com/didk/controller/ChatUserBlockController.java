package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatUserBlockDTO;
import com.didk.service.ChatUserBlockService;
import com.didk.vo.ChatUserBlockVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户黑名单控制层
 */
@RestController
@RequestMapping("block")
@Tag(name = "用户黑名单管理")
public class ChatUserBlockController {

    @Resource
    private ChatUserBlockService chatUserBlockService;

    // 查询我拉黑的用户列表
    @GetMapping("blocked-users")
    @Operation(summary = "查询我拉黑的用户列表")
    @Parameters({
            @Parameter(name = "limit", description = "每页显示记录数")
    })
    public Result<List<ChatUserBlockVO>> listBlockedUsers(@RequestParam Map<String, Object> params) {
        List<ChatUserBlockVO> list = chatUserBlockService.listBlockedUsers(params);
        return new Result<List<ChatUserBlockVO>>().ok(list);
    }

    // 拉黑用户
    @PostMapping
    @Operation(summary = "拉黑用户")
    public Result<?> save(@RequestBody ChatUserBlockDTO dto) {
        return chatUserBlockService.save(dto);
    }

    // 取消拉黑用户
    @DeleteMapping("{targetId}")
    @Operation(summary = "取消拉黑用户")
    public Result<?> delete(@PathVariable("targetId") Long targetId) {
        return chatUserBlockService.delete(targetId);
    }
}