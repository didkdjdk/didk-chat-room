package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatMessageDao;
import com.didk.dto.ChatMessageDTO;
import com.didk.entity.ChatMessageEntity;
import com.didk.enums.ReceiverTypeEnum;
import com.didk.service.ChatMessageService;
import com.didk.service.ChatRoomService;
import com.didk.vo.ChatMessageVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 消息实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageDao, ChatMessageEntity> implements ChatMessageService {

    @Resource
    private ChatMessageDao messageDao;
    @Resource
    private ChatRoomService chatRoomService;

    /**
     * 游标分页查询私聊消息
     */
    @Override
    public List<ChatMessageVO> listFriendMessages(Map<String, Object> params) {
        params.putIfAbsent("limit", 50);
        Long userId = SecurityUser.getUserId();
        params.put("userId",userId);
        return messageDao.selectFriendMessages(params);
    }

    /**
     * 游标分页查询私聊消息
     */
    @Override
    public List<ChatMessageVO> listRoomMessages(Map<String, Object> params) {
        params.putIfAbsent("limit", 50);
        Long userId = SecurityUser.getUserId();
        params.put("userId",userId);
        return messageDao.selectRoomMessages(params);
    }

    /**
     * 发送消息
     */
    @Override
    public Result<?> save(ChatMessageDTO dto) {
        Long userId = SecurityUser.getUserId();
        ChatMessageEntity messageEntity = ConvertUtils.sourceToTarget(dto, ChatMessageEntity.class);
        messageEntity.setSendId(userId);

        messageDao.insert(messageEntity);

        if (dto.getReceiverId() == ReceiverTypeEnum.ROOM.getCode()){
            //向群聊中发送信息，需要增加这个群聊的消息数量
            //统计群聊的总信息数并不需要很强的实时性，可以发送MQ异步处理TODO
            chatRoomService.incrementSeq(dto.getReceiverId());
        }

        //更新发送方会话窗口的数据（添加或修改）

        //更新接收方会话窗口的数据


        //websocket发送信息TODO

        return new Result<>().ok(null);
    }

    /**
     * 根据公告id删除公告消息中的公告id
     */
    @Override
    public void deleteAnnounceId(Long id) {
        messageDao.setAnnounceIdNull(id);
    }

    /**
     * 根据公告id批量删除公告消息中的公告id
     */
    @Override
    public void deleteBatchAnnounceId(Long[] ids) {
        messageDao.setAnnounceIdNullBatch(ids);
    }

}