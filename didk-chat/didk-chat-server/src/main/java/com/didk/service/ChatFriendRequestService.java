package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatFriendRequestDTO;
import com.didk.entity.ChatFriendRequestEntity;
import com.didk.vo.ChatFriendRequestVO;

import java.util.List;

/**
 * 好友请求服务类
 */
public interface ChatFriendRequestService extends IService<ChatFriendRequestEntity> {

    /**
     * 根据用户id查询好友请求列表
     */
    List<ChatFriendRequestVO> listByUserId(Long toUserId);

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