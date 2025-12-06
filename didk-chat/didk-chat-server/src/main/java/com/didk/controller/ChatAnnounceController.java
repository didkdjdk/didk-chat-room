package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatAnnounceDTO;
import com.didk.service.ChatAnnounceService;
import com.didk.vo.ChatAnnounceVO;
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
 * 公告控制层
 */
@RestController
@RequestMapping("announce")
@Tag(name = "公告管理")
@Transactional
public class ChatAnnounceController {

    @Resource
    private ChatAnnounceService chatAnnounceService;

    // 分页查询公告
    @GetMapping("listAllAnnounces")
    @Operation(summary = "分页查询公告")
    @Parameters({
        @Parameter(name = "page", description = "当前页码，从1开始", required = true),
        @Parameter(name = "limit", description = "每页显示记录数", required = true),
        @Parameter(name = "orderField", description = "排序字段"),
        @Parameter(name = "order", description = "排序方式，可选值(asc、desc)"),
        @Parameter(name = "roomId", description = "群聊id"),
    })
    public Result<PageData<ChatAnnounceVO>> list(@RequestParam Map<String, Object> params) {
        PageData<ChatAnnounceVO> page = chatAnnounceService.listAllAnnounces(params);
        return new Result<PageData<ChatAnnounceVO>>().ok(page);
    }

    // 根据群聊id查询公告
    @GetMapping("listByRoomId/{roomId}")
    @Operation(summary = "根据群聊ID查询公告")
    public Result<List<ChatAnnounceVO>> listByRoomId(@PathVariable Long roomId) {
        List<ChatAnnounceVO> data = chatAnnounceService.listByRoomId(roomId);
        return new Result<List<ChatAnnounceVO>>().ok(data);
    }

    // 根据用户id查询发布的公告
    @GetMapping("listByUserId/{userId}")
    @Operation(summary = "根据用户ID查询发布的公告")
    public Result<List<ChatAnnounceVO>> listByUserId(@PathVariable Long userId) {
        List<ChatAnnounceVO> data = chatAnnounceService.listByUserId(userId);
        return new Result<List<ChatAnnounceVO>>().ok(data);
    }

    // 根据id查询公告
    @GetMapping("{announceId}")
    @Operation(summary = "根据ID获取公告信息")
    public Result<ChatAnnounceVO> get(@PathVariable("announceId") Long announceId) {
        ChatAnnounceVO data = chatAnnounceService.get(announceId);
        return new Result<ChatAnnounceVO>().ok(data);
    }

    // 发布公告
    @PostMapping
    @Operation(summary = "发布公告")
    public Result<?> save(@RequestBody ChatAnnounceDTO dto) {
        return chatAnnounceService.save(dto);
    }

    // 修改公告
    @PutMapping
    @Operation(summary = "修改公告")
    public Result<?> update(@RequestBody ChatAnnounceDTO dto) {
        return chatAnnounceService.update(dto);
    }

    // 删除公告
    @DeleteMapping("{announceId}")
    @Operation(summary = "删除公告")
    public Result<?> delete(@PathVariable Long announceId) {
        chatAnnounceService.delete(announceId);
        return new Result<>();
    }

    // 批量删除公告
    @DeleteMapping("deleteBatch")
    @Operation(summary = "批量删除公告")
    public Result<?> deleteBatch(@RequestBody Long[] ids) {
        chatAnnounceService.deleteBatch(ids);
        return new Result<>();
    }
}