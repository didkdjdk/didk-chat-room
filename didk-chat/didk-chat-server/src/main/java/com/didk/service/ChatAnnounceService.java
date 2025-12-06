package com.didk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didk.commons.tools.utils.Result;
import com.didk.dto.ChatAnnounceDTO;
import com.didk.entity.ChatAnnounceEntity;
import com.didk.vo.ChatAnnounceVO;

import com.didk.commons.tools.page.PageData;
import java.util.List;
import java.util.Map;

/**
 * 公告服务类
 */
public interface ChatAnnounceService extends IService<ChatAnnounceEntity> {

    /**
     * 查询所有公告
     */
    PageData<ChatAnnounceVO> listAllAnnounces(Map<String, Object> params);

    /**
     * 根据群聊id查询公告
     */
    List<ChatAnnounceVO> listByRoomId(Long roomId);

    /**
     * 根据用户id查询发布的公告
     */
    List<ChatAnnounceVO> listByUserId(Long userId);

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