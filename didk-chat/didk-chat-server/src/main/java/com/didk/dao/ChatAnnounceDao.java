package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatAnnounceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 公告DAO
 */
@Mapper
public interface ChatAnnounceDao extends BaseDao<ChatAnnounceEntity> {

    List<ChatAnnounceEntity> selectByRoomId(Map<String, Object> params);

    List<ChatAnnounceEntity> selectByUserIdAndRoomId(Map<String, Object> params);

    List<ChatAnnounceEntity> selectPinnedByRoomId(Long roomId);

    ChatAnnounceEntity getById(Long id);
}