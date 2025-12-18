package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatFriendRequestDTO;
import com.didk.entity.ChatFriendRequestEntity;
import com.didk.vo.ChatFriendRequestVO;

import java.util.List;
import java.util.Map;

/**
 * 好友请求服务类
 */
public interface ChatFriendRequestService extends IService<ChatFriendRequestEntity> {

    /**
     * 游标查询当前用户的好友请求列表(查询发送方或接收方是自己的好友请求)
     */
    List<ChatFriendRequestVO> listCurrentUser(Map<String, Object> params);

    /**
     * 根据id查询好友请求
     */
    ChatFriendRequestVO get(Long id);

    /**
     * 发送好友请求
     */
    Result<?> save(ChatFriendRequestDTO dto);

    /**
     * 处理好友请求（同意或拒绝）
     */
    Result<?> processRequest(Long id, Integer status);

}