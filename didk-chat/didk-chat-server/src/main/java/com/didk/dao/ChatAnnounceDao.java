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

    List<ChatAnnounceEntity> selectAll(Map<String, Object> params);

    List<ChatAnnounceEntity> selectByRoomId(Long roomId);

    List<ChatAnnounceEntity> selectByUserId(Long userId);

    List<ChatAnnounceEntity> selectPinnedByRoomId(Long roomId);
}