package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatRoomDao;
import com.didk.dto.ChatRoomDTO;
import com.didk.entity.ChatRoomEntity;
import com.didk.enums.UserRoomRoleEnum;
import com.didk.service.ChatRoomService;
import com.didk.service.ChatUserRoomService;
import com.didk.vo.ChatRoomMemberVO;
import com.didk.vo.ChatRoomVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 群聊实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomDao, ChatRoomEntity> implements ChatRoomService {

    @Resource
    private ChatRoomDao roomDao;
    @Resource
    private ChatUserRoomService chatUserRoomService;

    /**
     * 根据id查询群聊
     */
    @Override
    public ChatRoomVO get(Long id) {
        ChatRoomEntity roomEntity = roomDao.selectById(id);
        return ConvertUtils.sourceToTarget(roomEntity, ChatRoomVO.class);
    }

    /**
     * 创建群聊
     */
    @Override
    public Result<?> save(ChatRoomDTO dto) {
        //添加群聊
        ChatRoomEntity roomEntity = ConvertUtils.sourceToTarget(dto, ChatRoomEntity.class);
        Long userId = SecurityUser.getUserId();
        roomEntity.setOwnerId(userId);
        roomDao.insert(roomEntity);

        //将当前用户添加到群聊中
        chatUserRoomService.save(userId,roomEntity.getId(), UserRoomRoleEnum.OWNER.getCode());

        return new Result<>().ok(null);
    }

    /**
     * 修改群聊信息
     */
    @Override
    public Result<?> update(ChatRoomDTO dto) {
        ChatRoomEntity roomEntity = ConvertUtils.sourceToTarget(dto, ChatRoomEntity.class);
        roomDao.updateById(roomEntity);
        return new Result<>().ok(null);
    }

    /**
     * 群聊成员数+1
     */
    @Override
    public void incrementMember(Long roomId) {
        roomDao.incrementMember(roomId);
        new Result<>().ok(null);
    }

    /**
     * 群聊成员数-1
     */
    @Override
    public void reduceMember(Long roomId) {
        roomDao.reduceMember(roomId);
        new Result<>().ok(null);
    }

    /**
     * 群聊总消息数量+1
     */
    @Override
    public void incrementSeq(Long roomId) {
        roomDao.incrementSeq(roomId);
    }

    /**
     * 解散群聊
     */
    @Override
    public void delete(Long id) {
        //删除群聊
        roomDao.deleteById(id);

        //删除群主和群聊之间的关系(当前用户退出群聊)
        chatUserRoomService.delete(id);

        //将这个群聊和其用户之间的关联改为已删除
        //查询群聊的所有用户
        List<ChatRoomMemberVO> userRoomVOS = chatUserRoomService.listByRoomId(id);
        List<Long> userIds = userRoomVOS.stream().map(ChatRoomMemberVO::getUserId).toList();
        chatUserRoomService.updateExitStatusBatch(id, userIds);
    }

}