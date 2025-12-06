package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.PageUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatMessageDao;
import com.didk.dto.ChatMessageDTO;
import com.didk.entity.ChatMessageEntity;
import com.didk.service.ChatMessageService;
import com.didk.vo.ChatMessageVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消息实现类
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageDao, ChatMessageEntity> implements ChatMessageService {

    @Resource
    private ChatMessageDao messageDao;

    /**
     * 查询所有消息
     */
    @Override
    public PageData<ChatMessageVO> listAllMessages(Map<String, Object> params) {
        IPage<ChatMessageEntity> page = PageUtils.getPage(params, null, false);
        List<ChatMessageEntity> list = messageDao.selectAll(params);
        return PageUtils.getPageData(list, page.getTotal(), ChatMessageVO.class);
    }

    /**
     * 根据发送者id查询消息
     */
    @Override
    public List<ChatMessageVO> listBySendId(Long sendId) {
        List<ChatMessageEntity> messageEntities = messageDao.selectBySendId(sendId);
        return ConvertUtils.sourceToTarget(messageEntities, ChatMessageVO.class);
    }

    /**
     * 根据接收者id和类型查询消息
     */
    @Override
    public List<ChatMessageVO> listByReceiver(Long receiverId, Integer receiverType) {
        List<ChatMessageEntity> messageEntities = messageDao.selectByReceiverIdAndType(receiverId, receiverType);
        return ConvertUtils.sourceToTarget(messageEntities, ChatMessageVO.class);
    }

    /**
     * 根据群聊id查询消息
     */
    @Override
    public List<ChatMessageVO> listByRoomId(Long roomId) {
        List<ChatMessageEntity> messageEntities = messageDao.selectByRoomId(roomId);
        return ConvertUtils.sourceToTarget(messageEntities, ChatMessageVO.class);
    }

    /**
     * 根据id查询消息
     */
    @Override
    public ChatMessageVO get(Long id) {
        ChatMessageEntity messageEntity = messageDao.selectById(id);
        return ConvertUtils.sourceToTarget(messageEntity, ChatMessageVO.class);
    }

    /**
     * 发送消息
     */
    @Override
    public Result<?> save(ChatMessageDTO dto) {
        ChatMessageEntity messageEntity = new ChatMessageEntity();
        messageEntity.setSendId(dto.getSendId());
        messageEntity.setReceiverType(dto.getReceiverType());
        messageEntity.setReceiverId(dto.getReceiverId());
        messageEntity.setMessageType(dto.getMessageType());
        messageEntity.setContent(dto.getContent());
        messageEntity.setAnnounceId(dto.getAnnounceId());

        messageDao.insert(messageEntity);
        return new Result<>().ok(null);
    }

    /**
     * 删除消息
     */
    @Override
    public void delete(Long id) {
        messageDao.deleteById(id);
    }

    /**
     * 批量删除消息
     */
    @Override
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            messageDao.deleteById(id);
        }
    }
}