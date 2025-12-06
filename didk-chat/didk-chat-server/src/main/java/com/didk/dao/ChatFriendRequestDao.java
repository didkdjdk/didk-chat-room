package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatFriendRequestEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 好友请求DAO
 */
@Mapper
public interface ChatFriendRequestDao extends BaseDao<ChatFriendRequestEntity> {

    List<ChatFriendRequestEntity> selectByUserId(Long userId);

    ChatFriendRequestEntity selectByFromAndToUser(Long fromUserId, Long toUserId);
}