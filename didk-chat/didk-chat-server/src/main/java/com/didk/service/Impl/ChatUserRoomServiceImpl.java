package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.PageUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatUserRoomDao;
import com.didk.dto.ChatUserRoomDTO;
import com.didk.entity.ChatUserRoomEntity;
import com.didk.service.ChatUserRoomService;
import com.didk.vo.ChatUserRoomVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户-群聊实现类
 */
@Service
public class ChatUserRoomServiceImpl extends ServiceImpl<ChatUserRoomDao, ChatUserRoomEntity> implements ChatUserRoomService {

    @Resource
    private ChatUserRoomDao userRoomDao;

    /**
     * 查询所有用户-群聊关系
     */
    @Override
    public PageData<ChatUserRoomVO> listAllUserRooms(Map<String, Object> params) {
        IPage<ChatUserRoomEntity> page = PageUtils.getPage(params, null, false);
        List<ChatUserRoomEntity> list = userRoomDao.selectAll(params);
        return PageUtils.getPageData(list, page.getTotal(), ChatUserRoomVO.class);
    }

    /**
     * 根据用户id查询加入的群聊
     */
    @Override
    public List<ChatUserRoomVO> listByUserId(Long userId) {
        List<ChatUserRoomEntity> userRoomEntities = userRoomDao.selectByUserId(userId);
        return ConvertUtils.sourceToTarget(userRoomEntities, ChatUserRoomVO.class);
    }

    /**
     * 根据群聊id查询群成员
     */
    @Override
    public List<ChatUserRoomVO> listByRoomId(Long roomId) {
        List<ChatUserRoomEntity> userRoomEntities = userRoomDao.selectByRoomId(roomId);
        return ConvertUtils.sourceToTarget(userRoomEntities, ChatUserRoomVO.class);
    }

    /**
     * 根据id查询用户-群聊关系
     */
    @Override
    public ChatUserRoomVO get(Long id) {
        ChatUserRoomEntity userRoomEntity = userRoomDao.selectById(id);
        return ConvertUtils.sourceToTarget(userRoomEntity, ChatUserRoomVO.class);
    }

    /**
     * 用户加入群聊
     */
    @Override
    public Result<?> save(ChatUserRoomDTO dto) {
        ChatUserRoomEntity userRoomEntity = new ChatUserRoomEntity();
        userRoomEntity.setUserId(dto.getUserId());
        userRoomEntity.setRoomId(dto.getRoomId());
        userRoomEntity.setRole(dto.getRole());
        userRoomEntity.setIsPinned(dto.getIsPinned());
        userRoomEntity.setAlias(dto.getAlias());
        userRoomEntity.setIsExit(dto.getIsExit());

        userRoomDao.insert(userRoomEntity);
        return new Result<>().ok(null);
    }

    /**
     * 修改用户在群聊中的信息（如群昵称、置顶等）
     */
    @Override
    public Result<?> update(ChatUserRoomDTO dto) {
        ChatUserRoomEntity userRoomEntity = new ChatUserRoomEntity();
        userRoomEntity.setId(dto.getId());
        userRoomEntity.setIsPinned(dto.getIsPinned());
        userRoomEntity.setAlias(dto.getAlias());

        userRoomDao.updateById(userRoomEntity);
        return new Result<>().ok(null);
    }

    /**
     * 用户退出群聊（或被踢出）
     */
    @Override
    public void delete(Long id) {
        userRoomDao.deleteById(id);
    }

    /**
     * 批量删除用户-群聊关系
     */
    @Override
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            userRoomDao.deleteById(id);
        }
    }
}