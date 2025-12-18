package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatAnnounceDao;
import com.didk.dto.ChatAnnounceDTO;
import com.didk.dto.ChatMessageDTO;
import com.didk.entity.ChatAnnounceEntity;
import com.didk.enums.MessageTypeEnum;
import com.didk.enums.ReceiverTypeEnum;
import com.didk.service.ChatAnnounceService;
import com.didk.service.ChatMessageService;
import com.didk.vo.ChatAnnounceListItemVO;
import com.didk.vo.ChatAnnounceVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公告实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatAnnounceServiceImpl extends ServiceImpl<ChatAnnounceDao, ChatAnnounceEntity> implements ChatAnnounceService {

    @Resource
    private ChatAnnounceDao announceDao;
    @Resource
    private ChatMessageService messageService;

    /**
     * 游标分页查询公告
     */
    @Override
    public List<ChatAnnounceListItemVO> listByRoomId(Map<String, Object> params) {
        params.putIfAbsent("limit", 50);
        return announceDao.selectByRoomId(params);
    }

    /**
     * 根据用户id游标查询发布的公告
     */
    @Override
    public List<ChatAnnounceListItemVO> listByUserId(Map<String, Object> params) {
        params.putIfAbsent("limit", 50);
        return announceDao.selectByUserIdAndRoomId(params);
    }

    /**
     * 根据id查询公告
     */
    @Override
    public ChatAnnounceVO get(Long id) {
        ChatAnnounceEntity announceEntity = announceDao.getById(id);
        return ConvertUtils.sourceToTarget(announceEntity, ChatAnnounceVO.class);
    }

    /**
     * 发布公告
     */
    @Override
    public Result<?> save(ChatAnnounceDTO dto) {
        Long userId = SecurityUser.getUserId();
        //添加公告
        ChatAnnounceEntity announceEntity = ConvertUtils.sourceToTarget(dto, ChatAnnounceEntity.class);
        announceEntity.setUserId(userId);
        announceDao.insert(announceEntity);

        //发送公告消息
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setSendId(userId);
        chatMessageDTO.setReceiverType(ReceiverTypeEnum.ROOM.getCode());
        chatMessageDTO.setReceiverId(announceEntity.getRoomId());
        chatMessageDTO.setMessageType(MessageTypeEnum.ANNOUNCEMENT.getCode());
        chatMessageDTO.setContent(announceEntity.getContent());
        chatMessageDTO.setAnnounceId(announceEntity.getId());
        messageService.save(chatMessageDTO);

        return new Result<>().ok(null);
    }

    /**
     * 修改公告
     */
    @Override
    public Result<?> update(ChatAnnounceDTO dto) {
        Long userId = SecurityUser.getUserId();
        //修改公告数据（需要刷新创建时间）
        ChatAnnounceEntity announceEntity = ConvertUtils.sourceToTarget(dto, ChatAnnounceEntity.class);
        announceEntity.setUserId(userId);
        announceEntity.setCreateDate(new Date());
        announceDao.updateById(announceEntity);

        //将之前的公告消息的公告id设置为null
        messageService.deleteAnnounceId(announceEntity.getId());

        //添加新的公告消息
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setSendId(userId);
        chatMessageDTO.setReceiverType(ReceiverTypeEnum.ROOM.getCode());
        chatMessageDTO.setReceiverId(announceEntity.getRoomId());
        chatMessageDTO.setMessageType(MessageTypeEnum.ANNOUNCEMENT.getCode());
        chatMessageDTO.setContent(announceEntity.getContent());
        chatMessageDTO.setAnnounceId(announceEntity.getId());
        messageService.save(chatMessageDTO);

        return new Result<>().ok(null);
    }

    /**
     * 删除公告
     */
    @Override
    public void delete(Long id) {
        announceDao.deleteById(id);
        //将之前的公告消息的公告id设置为null
        messageService.deleteAnnounceId(id);
    }

    /**
     * 批量删除公告
     */
    @Override
    public void deleteBatch(Long[] ids) {
        removeBatchByIds(Arrays.asList(ids));

        //将之前的公告消息的公告id设置为null
        messageService.deleteBatchAnnounceId(ids);

    }
}