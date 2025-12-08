package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatRoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 群聊DAO
 */
@Mapper
public interface ChatRoomDao extends BaseDao<ChatRoomEntity> {

    List<ChatRoomEntity> selectByName(String roomName);

    List<ChatRoomEntity> selectByOwnerId(Long ownerId);

    void incrementMember(Long roomId);

    void reduceMember(Long roomId);

    void incrementSeq(Long roomId);
}