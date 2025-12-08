package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomDTO;
import com.didk.entity.ChatRoomEntity;
import com.didk.vo.ChatRoomVO;

/**
 * 群聊服务类
 */
public interface ChatRoomService extends IService<ChatRoomEntity> {

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
     * 群聊成员数+1
     */
    void incrementMember(Long roomId);

    /**
     * 群聊成员数-1
     */
    void reduceMember(Long roomId);

    /**
     * 群聊总消息数量+1
     */
    void incrementSeq(Long roomId);

    /**
     * 解散群聊
     */
    void delete(Long id);

}