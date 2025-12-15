package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatUserBlockDTO;
import com.didk.entity.ChatUserBlockEntity;
import com.didk.vo.ChatUserBlockVO;

import com.didk.commons.tools.page.PageData;
import java.util.List;
import java.util.Map;

/**
 * 用户黑名单服务类
 */
public interface ChatUserBlockService extends IService<ChatUserBlockEntity> {

    /**
     * 查询我拉黑的用户列表
     */
    List<ChatUserBlockVO> listBlockedUsers(Map<String, Object> params);

    /**
     * 拉黑用户
     */
    Result<?> save(ChatUserBlockDTO dto);

    /**
     * 取消拉黑用户
     */
    Result<?> delete(Long targetId);
}