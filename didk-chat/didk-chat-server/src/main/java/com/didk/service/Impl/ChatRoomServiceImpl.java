package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.PageUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatRoomDao;
import com.didk.dto.ChatRoomDTO;
import com.didk.entity.ChatRoomEntity;
import com.didk.service.ChatRoomService;
import com.didk.vo.ChatRoomVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 群聊实现类
 */
@Service
public class ChatRoomServiceImpl extends ServiceImpl<ChatRoomDao, ChatRoomEntity> implements ChatRoomService {

    @Resource
    private ChatRoomDao roomDao;

    /**
     * 查询所有群聊
     */
    @Override
    public PageData<ChatRoomVO> listAllRooms(Map<String, Object> params) {
        PageUtils.paramsToLike(params, "roomName");
        IPage<ChatRoomEntity> page = PageUtils.getPage(params, null, false);
        List<ChatRoomEntity> list = roomDao.selectAll(params);
        return PageUtils.getPageData(list, page.getTotal(), ChatRoomVO.class);
    }

    /**
     * 根据群聊名称查询群聊
     */
    @Override
    public List<ChatRoomVO> listByName(String roomName) {
        List<ChatRoomEntity> roomEntities = roomDao.selectByName(roomName);
        return ConvertUtils.sourceToTarget(roomEntities, ChatRoomVO.class);
    }

    /**
     * 根据群主id查询群聊
     */
    @Override
    public List<ChatRoomVO> listByOwnerId(Long ownerId) {
        List<ChatRoomEntity> roomEntities = roomDao.selectByOwnerId(ownerId);
        return ConvertUtils.sourceToTarget(roomEntities, ChatRoomVO.class);
    }

    /**
     * 根据id查询群聊
     */
    @Override
    public ChatRoomVO get(Long id) {
        ChatRoomEntity roomEntity = roomDao.selectById(id);
        return ConvertUtils.sourceToTarget(roomEntity, ChatRoomVO.class);
    }

    /**
     * 创建群聊
     */
    @Override
    public Result<?> save(ChatRoomDTO dto) {
        ChatRoomEntity roomEntity = new ChatRoomEntity();
        roomEntity.setRoomName(dto.getRoomName());
        roomEntity.setCurrentMembers(dto.getCurrentMembers());
        roomEntity.setDescription(dto.getDescription());
        roomEntity.setOwnerId(dto.getOwnerId());
        roomEntity.setStatus(dto.getStatus());

        roomDao.insert(roomEntity);
        return new Result<>().ok(null);
    }

    /**
     * 修改群聊信息
     */
    @Override
    public Result<?> update(ChatRoomDTO dto) {
        ChatRoomEntity roomEntity = new ChatRoomEntity();
        roomEntity.setId(dto.getId());
        roomEntity.setRoomName(dto.getRoomName());
        roomEntity.setDescription(dto.getDescription());
        roomEntity.setStatus(dto.getStatus());

        roomDao.updateById(roomEntity);
        return new Result<>().ok(null);
    }

    /**
     * 解散群聊
     */
    @Override
    public void delete(Long id) {
        roomDao.deleteById(id);
    }

    /**
     * 批量解散群聊
     */
    @Override
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            roomDao.deleteById(id);
        }
    }
}