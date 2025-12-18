package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatUserRoomDTO;
import com.didk.entity.ChatUserRoomEntity;
import com.didk.vo.ChatRoomListItemVO;
import com.didk.vo.ChatRoomMemberVO;

import com.didk.vo.UserGroupDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 用户-群聊服务类
 */
public interface ChatUserRoomService extends IService<ChatUserRoomEntity> {

    /**
     * 查询当前用户加入的群聊
     */
    List<ChatRoomListItemVO> listCurrentRoom(Map<String, Object> params);

    /**
     * 用户查看自己的某个的群聊的信息
     */
    UserGroupDetailVO userSelectRoom(Long roomId);

    /**
     * 根据群聊id查询群成员
     */
    List<ChatRoomMemberVO> listByRoomId(Long roomId);

    /**
     * 用户加入群聊
     */
    Result<?> save(Long userId, Long roomId, Integer role);

    /**
     * 修改用户在群聊中的信息（如群昵称等）
     */
    Result<?> update(ChatUserRoomDTO dto);

    /**
     * 用户退出群聊
     */
    void delete(Long roomId);

    /**
     * 从群聊中批量踢出一批用户
     */
    void updateExitStatusBatch(Long roomId, List<Long> userIds);

}