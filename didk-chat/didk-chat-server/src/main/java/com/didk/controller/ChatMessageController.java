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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.didk.commons.tools.page.PageData;

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

    // 查询所有消息
    @GetMapping("listAllMessages")
    @Operation(summary = "分页查询消息")
    @Parameters({
        @Parameter(name = "page", description = "当前页码，从1开始", required = true),
        @Parameter(name = "limit", description = "每页显示记录数", required = true),
        @Parameter(name = "orderField", description = "排序字段"),
        @Parameter(name = "order", description = "排序方式，可选值(asc、desc)"),
        @Parameter(name = "sendId", description = "发送方id"),
        @Parameter(name = "receiverId", description = "接收方id"),
        @Parameter(name = "receiverType", description = "消息类型：0-文本 1-图片 2-文件 3-系统消息 4-公告")
    })
    public Result<PageData<ChatMessageVO>> list(@RequestParam Map<String, Object> params) {
        PageData<ChatMessageVO> page = chatMessageService.listAllMessages(params);
        return new Result<PageData<ChatMessageVO>>().ok(page);
    }

    // 根据发送者id查询消息
    @GetMapping("listBySendId/{sendId}")
    @Operation(summary = "根据发送者ID查询消息")
    public Result<List<ChatMessageVO>> listBySendId(@PathVariable Long sendId) {
        List<ChatMessageVO> data = chatMessageService.listBySendId(sendId);
        return new Result<List<ChatMessageVO>>().ok(data);
    }

    // 根据接收者id和类型查询消息
    @GetMapping("listByReceiver")
    @Operation(summary = "根据接收者ID和类型查询消息")
    public Result<List<ChatMessageVO>> listByReceiver(@RequestParam Long receiverId, @RequestParam Integer receiverType) {
        List<ChatMessageVO> data = chatMessageService.listByReceiver(receiverId, receiverType);
        return new Result<List<ChatMessageVO>>().ok(data);
    }

    // 根据群聊id查询消息
    @GetMapping("listByRoomId/{roomId}")
    @Operation(summary = "根据群聊ID查询消息")
    public Result<List<ChatMessageVO>> listByRoomId(@PathVariable Long roomId) {
        List<ChatMessageVO> data = chatMessageService.listByRoomId(roomId);
        return new Result<List<ChatMessageVO>>().ok(data);
    }

    // 根据id查询消息
    @GetMapping("{messageId}")
    @Operation(summary = "根据ID获取消息信息")
    public Result<ChatMessageVO> get(@PathVariable("messageId") Long messageId) {
        ChatMessageVO data = chatMessageService.get(messageId);
        return new Result<ChatMessageVO>().ok(data);
    }

    // 发送消息
    @PostMapping
    @Operation(summary = "发送消息")
    public Result<?> save(@RequestBody ChatMessageDTO dto) {
        return chatMessageService.save(dto);
    }

    // 删除消息
    @DeleteMapping("{messageId}")
    @Operation(summary = "删除消息")
    public Result<?> delete(@PathVariable Long messageId) {
        chatMessageService.delete(messageId);
        return new Result<>();
    }

    // 批量删除消息
    @DeleteMapping("deleteBatch")
    @Operation(summary = "批量删除消息")
    public Result<?> deleteBatch(@RequestBody Long[] ids) {
        chatMessageService.deleteBatch(ids);
        return new Result<>();
    }
}