package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatFriendEntity;
import com.didk.vo.ChatFriendVO;
import com.didk.vo.ChatUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 好友DAO
 */
@Mapper
public interface ChatFriendDao extends BaseDao<ChatFriendEntity> {

    List<ChatFriendEntity> selectAll(Map<String, Object> params);

    ChatFriendVO getById(Long id);

    ChatFriendEntity selectByUserAndFriend(Long userId, Long friendId);

    void updateStatus(Long userId, Long friendId, Integer status);

    ChatUserVO getUserInfo(Long userId, Long friendId);

    void deleteByUserIdAndFriendId(Long userId, Long friendId);
}