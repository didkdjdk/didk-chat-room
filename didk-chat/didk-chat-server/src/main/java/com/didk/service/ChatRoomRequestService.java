package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatRoomRequestDTO;
import com.didk.entity.ChatRoomRequestEntity;
import com.didk.vo.ChatRoomRequestVO;

import java.util.List;
import java.util.Map;

/**
 * 群聊申请服务类
 */
public interface ChatRoomRequestService extends IService<ChatRoomRequestEntity> {

    /**
     * 根据群聊id游标查询申请列表
     */
    List<ChatRoomRequestVO> listByRoomId(Map<String, Object> params);

    /**
     * 根据用户id查询申请列表
     */
    List<ChatRoomRequestVO> listByUserId(Long userId);

    /**
     * 根据id查询申请
     */
    ChatRoomRequestVO get(Long id);

    /**
     * 提交入群申请
     */
    Result<?> save(ChatRoomRequestDTO dto);

    /**
     * 处理入群申请（同意或拒绝）
     */
    Result<?> processRequest(Long id, Integer status, String rejectReason);

}