package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatMessageEntity;
import com.didk.vo.ChatMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 消息DAO
 */
@Mapper
public interface ChatMessageDao extends BaseDao<ChatMessageEntity> {

    List<ChatMessageVO> selectFriendMessages(Map<String, Object> params);

    List<ChatMessageVO> selectRoomMessages(Map<String, Object> params);
}