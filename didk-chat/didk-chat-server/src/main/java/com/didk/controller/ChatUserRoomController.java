package com.didk.controller;

import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatUserRoomDTO;
import com.didk.service.ChatUserRoomService;
import com.didk.vo.ChatRoomListItemVO;
import com.didk.vo.ChatRoomMemberVO;
import com.didk.vo.UserGroupDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户-群聊控制层
 */
@RestController
@RequestMapping("userRoom")
@Tag(name = "用户群聊管理")
public class ChatUserRoomController {

    @Resource
    private ChatUserRoomService chatUserRoomService;

    // 查询当前用户加入的群聊
    @GetMapping("listByUserId")
    @Operation(summary = "查询当前用户加入的群聊")
    @Parameters({
            @Parameter(name = "limit", description = "每页显示记录数(默认50)"),
            @Parameter(name = "userId", description = "用户ID"),
            @Parameter(name = "roomName", description = "群聊名称模糊查询"),
            @Parameter(name = "lastCreateDate", description = "最后一个群聊的创建时间")
    })
    public Result<List<ChatRoomListItemVO>> listByUserId(@RequestParam Map<String, Object> params) {
        List<ChatRoomListItemVO> data = chatUserRoomService.listCurrentRoom(params);
        return new Result<List<ChatRoomListItemVO>>().ok(data);
    }

    // 用户查看自己的某个的群聊的信息
    @GetMapping("user/{roomId}")
    @Operation(summary = "用户查看自己的某个的群聊的信息")
    public Result<UserGroupDetailVO> userSelectRoom(@PathVariable("roomId") Long roomId) {
        UserGroupDetailVO data = chatUserRoomService.userSelectRoom(roomId);
        return new Result<UserGroupDetailVO>().ok(data);
    }

    // 根据群聊id查询群成员
    @GetMapping("listByRoomId/{roomId}")
    @Operation(summary = "根据群聊ID查询群成员")
    public Result<List<ChatRoomMemberVO>> listByRoomId(@PathVariable Long roomId) {
        List<ChatRoomMemberVO> data = chatUserRoomService.listByRoomId(roomId);
        return new Result<List<ChatRoomMemberVO>>().ok(data);
    }

    // 用户加入群聊
    @PostMapping
    @Operation(summary = "用户加入群聊")
    public Result<?> save(Long userId, Long roomId) {
        return chatUserRoomService.save(userId,roomId,0);
    }

    // 修改用户在群聊中的信息（如群昵称等）
    @PutMapping
    @Operation(summary = "修改用户在群聊中的信息（如群昵称）")
    public Result<?> update(@RequestBody ChatUserRoomDTO dto) {
        return chatUserRoomService.update(dto);
    }

    // 用户退出群聊
    @DeleteMapping("{roomId}")
    @Operation(summary = "用户退出群聊")
    public Result<?> delete(@PathVariable Long roomId) {
        chatUserRoomService.delete(roomId);
        return new Result<>();
    }

    // 将一批用户踢出群聊
    @DeleteMapping("kickOut")
    @Operation(summary = "将一批用户踢出群聊")
    public Result<?> kickOut(Long roomId,List<Long> userIds) {
        chatUserRoomService.updateExitStatusBatch(roomId,userIds);
        return new Result<>();
    }

}