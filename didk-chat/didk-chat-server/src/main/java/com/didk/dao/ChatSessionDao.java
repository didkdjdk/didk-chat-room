package com.didk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didk.entity.ChatSessionEntity;
import com.didk.vo.ChatConversationListItemVO;

import java.util.List;
import java.util.Map;

/**
 * 会话DAO接口
 */
public interface ChatSessionDao extends BaseMapper<ChatSessionEntity> {

    List<ChatConversationListItemVO> selectConversationsByCursor(Map<String, Object> params);

    void hideById(Long id);
}