package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}