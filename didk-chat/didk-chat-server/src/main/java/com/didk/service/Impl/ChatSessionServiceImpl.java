package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatSessionDao;
import com.didk.dto.ChatSessionDTO;
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

    //创建会话列表
    @Override
    public Result<?> save(ChatSessionDTO dto) {
        ChatSessionEntity chatSessionEntity = ConvertUtils.sourceToTarget(dto, ChatSessionEntity.class);
        chatSessionDao.insert(chatSessionEntity);
        return new Result<>().ok(null);
    }

    /**
     * 修改会话信息
     */
    @Override
    public Result<?> update(ChatSessionDTO dto) {
        ChatSessionEntity chatSessionEntity = ConvertUtils.sourceToTarget(dto, ChatSessionEntity.class);
        chatSessionDao.updateById(chatSessionEntity);
        return new Result<>().ok(null);
    }

    /**
     * 隐藏会话
     */
    @Override
    public void hide(Long id) {
        chatSessionDao.hideById(id);
    }

    /**
     * 删除会话
     */
    @Override
    public void delete(Long id) {
        chatSessionDao.deleteById(id);
    }
}