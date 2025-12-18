package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatAnnounceEntity;
import com.didk.vo.ChatAnnounceListItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 公告DAO
 */
@Mapper
public interface ChatAnnounceDao extends BaseDao<ChatAnnounceEntity> {

    List<ChatAnnounceListItemVO> selectByRoomId(Map<String, Object> params);

    List<ChatAnnounceListItemVO> selectByUserIdAndRoomId(Map<String, Object> params);

    ChatAnnounceEntity getById(Long id);
}