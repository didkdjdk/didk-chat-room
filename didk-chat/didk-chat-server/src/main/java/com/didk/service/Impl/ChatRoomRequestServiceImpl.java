package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatRoomRequestDao;
import com.didk.dto.ChatRoomRequestDTO;
import com.didk.entity.ChatRoomRequestEntity;
import com.didk.enums.RoomRequestStatusEnum;
import com.didk.enums.UserRoomRoleEnum;
import com.didk.service.ChatRoomRequestService;
import com.didk.service.ChatUserRoomService;
import com.didk.vo.ChatRoomRequestVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 群聊申请实现类
 */
@Service
public class ChatRoomRequestServiceImpl extends ServiceImpl<ChatRoomRequestDao, ChatRoomRequestEntity> implements ChatRoomRequestService {

    @Resource
    private ChatRoomRequestDao roomRequestDao;
    
    @Resource
    private ChatUserRoomService userRoomService;

    /**
     * 根据群聊id查询申请列表
     */
    @Override
    public List<ChatRoomRequestVO> listByRoomId(Long roomId) {
        List<ChatRoomRequestEntity> requestEntities = roomRequestDao.selectByRoomId(roomId);
        return ConvertUtils.sourceToTarget(requestEntities, ChatRoomRequestVO.class);
    }

    /**
     * 根据用户id查询申请列表
     */
    @Override
    public List<ChatRoomRequestVO> listByUserId(Long userId) {
        List<ChatRoomRequestEntity> requestEntities = roomRequestDao.selectByUserId(userId);
        return ConvertUtils.sourceToTarget(requestEntities, ChatRoomRequestVO.class);
    }

    /**
     * 根据id查询申请
     */
    @Override
    public ChatRoomRequestVO get(Long id) {
        ChatRoomRequestEntity requestEntity = roomRequestDao.selectById(id);
        return ConvertUtils.sourceToTarget(requestEntity, ChatRoomRequestVO.class);
    }

    /**
     * 提交入群申请
     */
    @Override
    public Result<?> save(ChatRoomRequestDTO dto) {
        // 存储群聊申请数据
        ChatRoomRequestEntity requestEntity = ConvertUtils.sourceToTarget(dto, ChatRoomRequestEntity.class);
        requestEntity.setUserId(SecurityUser.getUserId());
        requestEntity.setStatus(RoomRequestStatusEnum.PENDING.getCode());
        roomRequestDao.insert(requestEntity);

        // 通过Websocket向前端发送消息 TODO

        return new Result<>().ok(null);
    }

    /**
     * 处理入群申请（同意或拒绝）
     */
    @Override
    public Result<?> processRequest(Long id, Integer status, String rejectReason) {
        // 更改申请数据的状态
        ChatRoomRequestEntity requestEntity = roomRequestDao.selectById(id);
        requestEntity.setId(id);
        requestEntity.setStatus(status);
        requestEntity.setRejectReason(rejectReason);
        requestEntity.setProcessDate(new Date());
        roomRequestDao.updateById(requestEntity);

        // 如果同意申请，则添加用户到群聊
        if (status == RoomRequestStatusEnum.APPROVED.getCode()) {
            Long userId = requestEntity.getUserId();
            Long roomId = requestEntity.getRoomId();
            userRoomService.save(userId, roomId, UserRoomRoleEnum.MEMBER.getCode());
        }

        // 通过Websocket向前端发送消息 TODO

        return new Result<>().ok(null);
    }

}