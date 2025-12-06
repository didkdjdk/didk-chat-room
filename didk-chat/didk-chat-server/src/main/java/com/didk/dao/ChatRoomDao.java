package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatRoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 群聊DAO
 */
@Mapper
public interface ChatRoomDao extends BaseDao<ChatRoomEntity> {

    List<ChatRoomEntity> selectAll(Map<String, Object> params);

    List<ChatRoomEntity> selectByName(String roomName);

    List<ChatRoomEntity> selectByOwnerId(Long ownerId);
}