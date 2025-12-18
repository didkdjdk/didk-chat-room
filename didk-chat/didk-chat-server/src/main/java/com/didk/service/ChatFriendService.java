package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatFriendDTO;
import com.didk.entity.ChatFriendEntity;
import com.didk.vo.ChatConversationListItemVO;
import com.didk.vo.ChatFriendVO;

import com.didk.commons.tools.page.PageData;
import com.didk.vo.ChatUserVO;

import java.util.List;
import java.util.Map;

/**
 * 好友服务类
 */
public interface ChatFriendService extends IService<ChatFriendEntity> {

    /**
     * 查询所有好友关系和群聊关系
     */
    PageData<ChatConversationListItemVO> listAllFriendsAndRooms(Map<String, Object> params);

    /**
     * 根据id查询好友关系
     */
    ChatFriendVO get(Long id);

    /**
     * 以当前用户查询另一个用户的信息
     */
    ChatUserVO getUserInfo(Long friendId);

    /**
     * 添加好友关系
     */
    Result<?> save(Long userId, Long friendId);

    /**
     * 修改好友关系（如修改备注等）
     */
    Result<?> update(ChatFriendDTO dto);

    /**
     * 删除好友关系
     */
    void delete(Long friendId);


}