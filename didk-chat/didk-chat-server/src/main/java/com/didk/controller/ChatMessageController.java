package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatMessageDTO;
import com.didk.service.ChatMessageService;
import com.didk.vo.ChatMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 消息控制层
 */
@RestController
@RequestMapping("message")
@Tag(name = "消息管理")
public class ChatMessageController {

    @Resource
    private ChatMessageService chatMessageService;

    // 游标分页查询私聊消息
    @GetMapping("listFriendMessages")
    @Operation(summary = "游标分页查询私聊消息")
    @Parameters({
            @Parameter(name = "friendId", description = "好友id", required = true),
            @Parameter(name = "limit", description = "每页显示记录数(默认50)"),
            @Parameter(name = "receiverType", description = "消息类型：0-文本 1-图片 2-文件 3-系统消息 4-公告"),
            @Parameter(name = "lastCreateDate", description = "最后一条消息的创建时间")
    })
    public Result<List<ChatMessageVO>> listFriendMessages(@RequestParam Map<String, Object> params) {
        List<ChatMessageVO> page = chatMessageService.listFriendMessages(params);
        return new Result<List<ChatMessageVO>>().ok(page);
    }

    // 游标分页查询群聊消息
    @GetMapping("listRoomMessages")
    @Operation(summary = "游标分页查询群聊消息")
    @Parameters({
            @Parameter(name = "roomId", description = "群聊id", required = true),
            @Parameter(name = "sendId", description = "发送者id"),
            @Parameter(name = "limit", description = "每页显示记录数(默认50)"),
            @Parameter(name = "receiverType", description = "消息类型：0-文本 1-图片 2-文件 3-系统消息 4-公告"),
            @Parameter(name = "lastCreateDate", description = "最后一条消息的创建时间")
    })
    public Result<List<ChatMessageVO>> listRoomMessages(@RequestParam Map<String, Object> params) {
        List<ChatMessageVO> page = chatMessageService.listRoomMessages(params);
        return new Result<List<ChatMessageVO>>().ok(page);
    }

    // 发送消息
    @PostMapping
    @Operation(summary = "发送消息")
    public Result<?> save(@RequestBody ChatMessageDTO dto) {
        return chatMessageService.save(dto);
    }

}