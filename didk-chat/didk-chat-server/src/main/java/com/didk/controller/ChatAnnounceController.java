package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatAnnounceDTO;
import com.didk.service.ChatAnnounceService;
import com.didk.vo.ChatAnnounceListItemVO;
import com.didk.vo.ChatAnnounceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 公告控制层
 */
@RestController
@RequestMapping("announce")
@Tag(name = "公告管理")
public class ChatAnnounceController {

    @Resource
    private ChatAnnounceService chatAnnounceService;

    // 游标分页查询公告
    @GetMapping("listAllAnnounces")
    @Operation(summary = "根据群聊ID游标查询公告")
    @Parameters({
            @Parameter(name = "limit", description = "每页显示记录数(默认50)"),
            @Parameter(name = "roomId", description = "群聊id"),
            @Parameter(name = "lastCreateDate", description = "最后一条公告的创建时间")
    })
    public Result<List<ChatAnnounceListItemVO>> list(@RequestParam Map<String, Object> params) {
        List<ChatAnnounceListItemVO> page = chatAnnounceService.listByRoomId(params);
        return new Result<List<ChatAnnounceListItemVO>>().ok(page);
    }

    // 根据用户id和群聊id游标查询发布的公告
    @GetMapping("listByUserIdAndRoomId")
    @Operation(summary = "根据用户id和群聊id游标查询发布的公告")
    @Parameters({
            @Parameter(name = "limit", description = "每页显示记录数(默认50)"),
            @Parameter(name = "userId", description = "用户id"),
            @Parameter(name = "roomId", description = "群聊id"),
            @Parameter(name = "lastCreateDate", description = "最后一条公告的创建时间")
    })
    public Result<List<ChatAnnounceListItemVO>> listByUserId(@RequestParam Map<String, Object> params) {
        List<ChatAnnounceListItemVO> data = chatAnnounceService.listByUserId(params);
        return new Result<List<ChatAnnounceListItemVO>>().ok(data);
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