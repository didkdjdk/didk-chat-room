package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatFriendRequestDao;
import com.didk.dto.ChatFriendDTO;
import com.didk.dto.ChatFriendRequestDTO;
import com.didk.entity.ChatFriendRequestEntity;
import com.didk.enums.FriendRequestStatusEnum;
import com.didk.service.ChatFriendRequestService;
import com.didk.service.ChatFriendService;
import com.didk.vo.ChatFriendRequestVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 好友请求实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatFriendRequestServiceImpl extends ServiceImpl<ChatFriendRequestDao, ChatFriendRequestEntity> implements ChatFriendRequestService {

    @Resource
    private ChatFriendRequestDao friendRequestDao;
    @Resource
    private ChatFriendService friendService;

    /**
     * 根据接收用户id查询好友请求列表
     */
    @Override
    public List<ChatFriendRequestVO> listByUserId(Long userId) {
        List<ChatFriendRequestEntity> requestEntities = friendRequestDao.selectByUserId(userId);
        return ConvertUtils.sourceToTarget(requestEntities, ChatFriendRequestVO.class);
    }

    /**
     * 根据id查询好友请求
     */
    @Override
    public ChatFriendRequestVO get(Long id) {
        ChatFriendRequestEntity requestEntity = friendRequestDao.selectById(id);
        return ConvertUtils.sourceToTarget(requestEntity, ChatFriendRequestVO.class);
    }

    /**
     * 发送好友请求
     */
    @Override
    public Result<?> save(ChatFriendRequestDTO dto) {
        //存储好友请求数据
        ChatFriendRequestEntity requestEntity = ConvertUtils.sourceToTarget(dto, ChatFriendRequestEntity.class);
        requestEntity.setStatus(0);
        friendRequestDao.insert(requestEntity);

        //通过Websocket向前端发送消息TODO

        return new Result<>().ok(null);
    }

    /**
     * 处理好友请求（同意或拒绝）
     */
    @Override
    public Result<?> processRequest(Long id, Integer status) {
        //更改好友请求数据的状态
        ChatFriendRequestEntity requestEntity = friendRequestDao.selectById(id);
        requestEntity.setId(id);
        requestEntity.setStatus(status);
        requestEntity.setProcessDate(new Date());
        friendRequestDao.updateById(requestEntity);

        //添加好友表数据
        if (status == FriendRequestStatusEnum.APPROVED.getCode()){
            Long fromUserId = requestEntity.getFromUserId();
            Long toUserId = requestEntity.getToUserId();
            friendService.save(fromUserId,toUserId);
            friendService.save(toUserId,fromUserId);
        }

        //通过Websocket向前端发送消息TODO

        return new Result<>().ok(null);
    }

}