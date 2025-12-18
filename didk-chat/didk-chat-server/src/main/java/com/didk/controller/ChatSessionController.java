package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.service.ChatSessionService;
import com.didk.vo.ChatConversationListItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 会话管理控制器
 */
@RestController
@RequestMapping("/session")
@Tag(name = "会话管理")
public class ChatSessionController {

    @Resource
    private ChatSessionService chatSessionService;

    @GetMapping("listConversations")
    @Operation(summary = "查询会话列表(游标分页)")
    @Parameters({
            @Parameter(name = "limit", description = "每页记录数(默认50)"),
            @Parameter(name = "cursorTime", description = "游标：上一页最后一条的时间(毫秒)，第一页传null"),
            @Parameter(name = "cursorPinned", description = "游标：上一页最后一条的置顶状态(0/1)，第一页传null"),
            @Parameter(name = "sessionName", description = "搜索关键词(可选)"),
    })
    public Result<List<ChatConversationListItemVO>> listConversations(@RequestParam Map<String,Object> params) {
        List<ChatConversationListItemVO> list = chatSessionService.listConversations(params);
        return new Result<List<ChatConversationListItemVO>>().ok(list);
    }

}