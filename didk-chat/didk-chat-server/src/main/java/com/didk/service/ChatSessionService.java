package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatSessionDTO;
import com.didk.entity.ChatSessionEntity;
import com.didk.vo.ChatConversationListItemVO;

import java.util.List;
import java.util.Map;

/**
 * 会话服务接口
 */
public interface ChatSessionService extends IService<ChatSessionEntity> {

    //查询会话列表(游标分页)
    List<ChatConversationListItemVO> listConversations(Map<String, Object> params);

    /**
     * 创建会话列表
     */
    Result<?> save(ChatSessionDTO dto);

    /**
     * 修改会话信息
     */
    Result<?> update(ChatSessionDTO dto);

    /**
     * 隐藏会话
     */
    void hide(Long id);

    /**
     * 删除会话
     */
    void delete(Long id);
}