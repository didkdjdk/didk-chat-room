package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatUserRoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户-群聊DAO
 */
@Mapper
public interface ChatUserRoomDao extends BaseDao<ChatUserRoomEntity> {

    List<ChatUserRoomEntity> selectAll(Map<String, Object> params);

    List<ChatUserRoomEntity> selectByUserId(Long userId);

    List<ChatUserRoomEntity> selectByRoomId(Long roomId);

    ChatUserRoomEntity selectByUserAndRoom(Long userId, Long roomId);

    List<ChatUserRoomEntity> selectAdminsByRoomId(Long roomId);

    void updateExitStatus(Long userId, Long roomId, Integer isExit);
}