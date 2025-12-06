package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatMessageDTO;
import com.didk.entity.ChatMessageEntity;
import com.didk.vo.ChatMessageVO;

import com.didk.commons.tools.page.PageData;
import java.util.List;
import java.util.Map;

/**
 * 消息服务类
 */
public interface ChatMessageService extends IService<ChatMessageEntity> {

    /**
     * 查询所有消息
     */
    PageData<ChatMessageVO> listAllMessages(Map<String, Object> params);

    /**
     * 根据发送者id查询消息
     */
    List<ChatMessageVO> listBySendId(Long sendId);

    /**
     * 根据接收者id和类型查询消息
     */
    List<ChatMessageVO> listByReceiver(Long receiverId, Integer receiverType);

    /**
     * 根据群聊id查询消息
     */
    List<ChatMessageVO> listByRoomId(Long roomId);

    /**
     * 根据id查询消息
     */
    ChatMessageVO get(Long id);

    /**
     * 发送消息
     */
    Result<?> save(ChatMessageDTO dto);

    /**
     * 删除消息
     */
    void delete(Long id);

    /**
     * 批量删除消息
     */
    void deleteBatch(Long[] ids);
}