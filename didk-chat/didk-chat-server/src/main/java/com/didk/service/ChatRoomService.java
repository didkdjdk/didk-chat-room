package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomDTO;
import com.didk.entity.ChatRoomEntity;
import com.didk.vo.ChatRoomVO;

import com.didk.commons.tools.page.PageData;
import java.util.List;
import java.util.Map;

/**
 * 群聊服务类
 */
public interface ChatRoomService extends IService<ChatRoomEntity> {

    /**
     * 查询所有群聊
     */
    PageData<ChatRoomVO> listAllRooms(Map<String, Object> params);

    /**
     * 根据群聊名称查询群聊
     */
    List<ChatRoomVO> listByName(String roomName);

    /**
     * 根据群主id查询群聊
     */
    List<ChatRoomVO> listByOwnerId(Long ownerId);

    /**
     * 根据id查询群聊
     */
    ChatRoomVO get(Long id);

    /**
     * 创建群聊
     */
    Result<?> save(ChatRoomDTO dto);

    /**
     * 修改群聊信息
     */
    Result<?> update(ChatRoomDTO dto);

    /**
     * 解散群聊
     */
    void delete(Long id);

    /**
     * 批量解散群聊
     */
    void deleteBatch(Long[] ids);
}