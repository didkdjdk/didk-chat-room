package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatFriendEntity;
import com.didk.vo.ChatFriendVO;
import com.didk.vo.ChatUserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 好友DAO
 */
@Mapper
public interface ChatFriendDao extends BaseDao<ChatFriendEntity> {

    ChatFriendVO getById(Long id);

    ChatUserVO getUserInfo(Long userId, Long friendId);

    void deleteByUserIdAndFriendId(Long userId, Long friendId);
}