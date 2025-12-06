package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatUserRoomDTO;
import com.didk.entity.ChatUserRoomEntity;
import com.didk.vo.ChatUserRoomVO;

import com.didk.commons.tools.page.PageData;
import java.util.List;
import java.util.Map;

/**
 * 用户-群聊服务类
 */
public interface ChatUserRoomService extends IService<ChatUserRoomEntity> {

    /**
     * 查询所有用户-群聊关系
     */
    PageData<ChatUserRoomVO> listAllUserRooms(Map<String, Object> params);

    /**
     * 根据用户id查询加入的群聊
     */
    List<ChatUserRoomVO> listByUserId(Long userId);

    /**
     * 根据群聊id查询群成员
     */
    List<ChatUserRoomVO> listByRoomId(Long roomId);

    /**
     * 根据id查询用户-群聊关系
     */
    ChatUserRoomVO get(Long id);

    /**
     * 用户加入群聊
     */
    Result<?> save(ChatUserRoomDTO dto);

    /**
     * 修改用户在群聊中的信息（如群昵称、置顶等）
     */
    Result<?> update(ChatUserRoomDTO dto);

    /**
     * 用户退出群聊（或被踢出）
     */
    void delete(Long id);

    /**
     * 批量删除用户-群聊关系
     */
    void deleteBatch(Long[] ids);
}