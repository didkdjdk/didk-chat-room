package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatMessageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 消息DAO
 */
@Mapper
public interface ChatMessageDao extends BaseDao<ChatMessageEntity> {

    List<ChatMessageEntity> selectAll(Map<String, Object> params);

    List<ChatMessageEntity> selectBySendId(Long sendId);

    List<ChatMessageEntity> selectByReceiverIdAndType(Long receiverId, Integer receiverType);

    List<ChatMessageEntity> selectByRoomId(Long roomId);

    List<ChatMessageEntity> selectHistoryMessages(Long sendId, Long receiverId, Integer receiverType, Integer limit);
}