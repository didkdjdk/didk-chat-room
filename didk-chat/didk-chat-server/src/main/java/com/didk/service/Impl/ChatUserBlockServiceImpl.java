package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatUserBlockDao;
import com.didk.dto.ChatUserBlockDTO;
import com.didk.entity.ChatUserBlockEntity;
import com.didk.service.ChatFriendService;
import com.didk.service.ChatUserBlockService;
import com.didk.vo.ChatUserBlockVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户黑名单实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatUserBlockServiceImpl extends ServiceImpl<ChatUserBlockDao, ChatUserBlockEntity> implements ChatUserBlockService {

    @Resource
    private ChatUserBlockDao chatUserBlockDao;
    @Resource
    private ChatFriendService chatFriendService;

    /**
     * 查询我拉黑的用户列表
     */
    @Override
    public List<ChatUserBlockVO> listBlockedUsers(Map<String, Object> params) {
        Long userId = SecurityUser.getUserId();
        params.put("userId", userId);
        return chatUserBlockDao.listBlockedUsers(params);
    }

    /**
     * 拉黑用户
     */
    @Override
    public Result<?> save(ChatUserBlockDTO dto) {
        Long userId = SecurityUser.getUserId();
        // 检查是否已经拉黑该用户
        ChatUserBlockEntity entity = this.getOne(new QueryWrapper<ChatUserBlockEntity>()
                .eq("user_id", userId)
                .eq("target_id", dto.getTargetId()));
        
        if (entity != null) {
            return new Result<>().error("您已经拉黑该用户");
        }

        //添加拉黑信息
        ChatUserBlockEntity blockEntity = new ChatUserBlockEntity();
        blockEntity.setUserId(userId);
        blockEntity.setTargetId(dto.getTargetId());
        chatUserBlockDao.insert(blockEntity);

        //删除好友关系
        chatFriendService.delete(dto.getTargetId());
        
        return new Result<>().ok(null);
    }

    /**
     * 取消拉黑用户
     */
    @Override
    public Result<?> delete(Long targetId) {
        Long userId = SecurityUser.getUserId();
        ChatUserBlockEntity entity = this.getOne(new QueryWrapper<ChatUserBlockEntity>()
                .eq("user_id", userId)
                .eq("target_id", targetId));
        
        if (entity == null) {
            return new Result<>().error("未找到拉黑记录");
        }
        
        chatUserBlockDao.deleteById(entity.getId());
        return new Result<>().ok(null);
    }
}