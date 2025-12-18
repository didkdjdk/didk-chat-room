package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatRoomRequestEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 群聊申请DAO
 */
@Mapper
public interface ChatRoomRequestDao extends BaseDao<ChatRoomRequestEntity> {

    List<ChatRoomRequestEntity> selectByRoomId(Map<String, Object> params);

    List<ChatRoomRequestEntity> selectByUserId(Long userId);

    ChatRoomRequestEntity selectByRoomAndUser(Long roomId, Long userId);
}