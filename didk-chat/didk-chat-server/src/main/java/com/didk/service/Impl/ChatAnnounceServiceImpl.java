package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.PageUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatAnnounceDao;
import com.didk.dto.ChatAnnounceDTO;
import com.didk.entity.ChatAnnounceEntity;
import com.didk.service.ChatAnnounceService;
import com.didk.vo.ChatAnnounceVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 公告实现类
 */
@Service
public class ChatAnnounceServiceImpl extends ServiceImpl<ChatAnnounceDao, ChatAnnounceEntity> implements ChatAnnounceService {

    @Resource
    private ChatAnnounceDao announceDao;

    /**
     * 分页查询公告
     */
    @Override
    public PageData<ChatAnnounceVO> listAllAnnounces(Map<String, Object> params) {
        IPage<ChatAnnounceEntity> page = PageUtils.getPage(params, null, false);
        List<ChatAnnounceEntity> list = announceDao.selectAll(params);
        return PageUtils.getPageData(list, page.getTotal(), ChatAnnounceVO.class);
    }

    /**
     * 根据群聊id查询公告
     */
    @Override
    public List<ChatAnnounceVO> listByRoomId(Long roomId) {
        List<ChatAnnounceEntity> announceEntities = announceDao.selectByRoomId(roomId);
        return ConvertUtils.sourceToTarget(announceEntities, ChatAnnounceVO.class);
    }

    /**
     * 根据用户id查询发布的公告
     */
    @Override
    public List<ChatAnnounceVO> listByUserId(Long userId) {
        List<ChatAnnounceEntity> announceEntities = announceDao.selectByUserId(userId);
        return ConvertUtils.sourceToTarget(announceEntities, ChatAnnounceVO.class);
    }

    /**
     * 根据id查询公告
     */
    @Override
    public ChatAnnounceVO get(Long id) {
        ChatAnnounceEntity announceEntity = announceDao.selectById(id);
        return ConvertUtils.sourceToTarget(announceEntity, ChatAnnounceVO.class);
    }

    /**
     * 发布公告
     */
    @Override
    public Result<?> save(ChatAnnounceDTO dto) {
        ChatAnnounceEntity announceEntity = new ChatAnnounceEntity();
        announceEntity.setRoomId(dto.getRoomId());
        announceEntity.setUserId(dto.getUserId());
        announceEntity.setTitle(dto.getTitle());
        announceEntity.setContent(dto.getContent());
        announceEntity.setIsPinned(dto.getIsPinned());

        announceDao.insert(announceEntity);
        return new Result<>().ok(null);
    }

    /**
     * 修改公告
     */
    @Override
    public Result<?> update(ChatAnnounceDTO dto) {
        ChatAnnounceEntity announceEntity = new ChatAnnounceEntity();
        announceEntity.setId(dto.getId());
        announceEntity.setTitle(dto.getTitle());
        announceEntity.setContent(dto.getContent());
        announceEntity.setIsPinned(dto.getIsPinned());

        announceDao.updateById(announceEntity);
        return new Result<>().ok(null);
    }

    /**
     * 删除公告
     */
    @Override
    public void delete(Long id) {
        announceDao.deleteById(id);
    }

    /**
     * 批量删除公告
     */
    @Override
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            announceDao.deleteById(id);
        }
    }
}