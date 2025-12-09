package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatAnnounceDTO;
import com.didk.entity.ChatAnnounceEntity;
import com.didk.vo.ChatAnnounceListItemVO;
import com.didk.vo.ChatAnnounceVO;

import com.didk.commons.tools.page.PageData;

import java.util.Map;

/**
 * 公告服务类
 */
public interface ChatAnnounceService extends IService<ChatAnnounceEntity> {

    /**
     * 根据群聊ID查询公告
     */
    PageData<ChatAnnounceListItemVO> listByRoomId(Map<String, Object> params);

    /**
     * 根据用户id查询发布的公告
     */
    PageData<ChatAnnounceListItemVO> listByUserId(Map<String, Object> params);

    /**
     * 根据id查询公告
     */
    ChatAnnounceVO get(Long id);

    /**
     * 发布公告
     */
    Result<?> save(ChatAnnounceDTO dto);

    /**
     * 修改公告
     */
    Result<?> update(ChatAnnounceDTO dto);

    /**
     * 删除公告
     */
    void delete(Long id);

    /**
     * 批量删除公告
     */
    void deleteBatch(Long[] ids);
}