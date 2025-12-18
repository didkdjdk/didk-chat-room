package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatUserRoomEntity;
import com.didk.vo.ChatRoomListItemVO;
import com.didk.vo.ChatRoomMemberVO;
import com.didk.vo.UserGroupDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户-群聊DAO
 */
@Mapper
public interface ChatUserRoomDao extends BaseDao<ChatUserRoomEntity> {

    List<ChatRoomListItemVO> selectByUserId(Map<String, Object> params);

    UserGroupDetailVO getGroupChatDetail(Long userId, Long roomId);

    List<ChatRoomMemberVO> selectByRoomId(Long roomId);

    ChatUserRoomEntity selectByUserAndRoom(Long userId, Long roomId);

    void updateExitStatusBatch(Long roomId,List<Long> userIds);

    void deleteByUserIdAndRoomId(Long userId, Long roomId);
}