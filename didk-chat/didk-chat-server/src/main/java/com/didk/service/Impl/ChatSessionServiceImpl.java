package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.dao.ChatSessionDao;
import com.didk.entity.ChatSessionEntity;
import com.didk.service.ChatSessionService;
import com.didk.vo.ChatConversationListItemVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会话服务实现类
 */
@Service
public class ChatSessionServiceImpl extends ServiceImpl<ChatSessionDao, ChatSessionEntity> implements ChatSessionService {

    @Resource
    private ChatSessionDao chatSessionDao;

    /**
     * 查询会话列表(游标分页)
     */
    @Override
    public List<ChatConversationListItemVO> listConversations(Map<String, Object> params) {
        params.putIfAbsent("limit", 50);
        Long userId = SecurityUser.getUserId();
        params.put("userId",userId);
        return chatSessionDao.selectConversationsByCursor(params);
    }
}