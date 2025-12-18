package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatMessageDTO;
import com.didk.entity.ChatMessageEntity;
import com.didk.vo.ChatMessageVO;

import java.util.List;
import java.util.Map;

/**
 * 消息服务类
 */
public interface ChatMessageService extends IService<ChatMessageEntity> {

    /**
     * 游标分页查询私聊消息
     */
    List<ChatMessageVO> listFriendMessages(Map<String, Object> params);

    /**
     * 游标分页查询群聊消息
     */
    List<ChatMessageVO> listRoomMessages(Map<String, Object> params);

    /**
     * 发送消息
     */
    Result<?> save(ChatMessageDTO dto);

    /**
     * 根据公告id删除公告消息中的公告id
     */
    void deleteAnnounceId(Long id);

    /**
     * 根据公告id批量删除公告消息中的公告id
     */
    void deleteBatchAnnounceId(Long[] ids);
}