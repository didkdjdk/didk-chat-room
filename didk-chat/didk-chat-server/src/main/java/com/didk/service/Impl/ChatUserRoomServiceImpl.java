package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.PageUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatUserRoomDao;
import com.didk.dto.ChatUserRoomDTO;
import com.didk.entity.ChatUserRoomEntity;
import com.didk.enums.PinnedStatusEnum;
import com.didk.enums.UserRoomExitStatusEnum;
import com.didk.enums.UserRoomRoleEnum;
import com.didk.service.ChatRoomService;
import com.didk.service.ChatUserRoomService;
import com.didk.vo.ChatConversationListItemVO;
import com.didk.vo.ChatRoomMemberVO;
import com.didk.vo.UserGroupDetailVO;
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
    @Resource
    private ChatRoomService roomService;

    /**
     * 根据群主id查询群聊
     */
    @Override
    public PageData<ChatConversationListItemVO> listByUserId(Map<String, Object> params) {
        PageUtils.paramsToLike(params,"roomName");
        IPage<ChatConversationListItemVO> page = PageUtils.getPage(params, null, false);
        List<ChatConversationListItemVO> list = userRoomDao.selectByUserId(params);
        return PageUtils.getPageData(list, page.getTotal(), ChatConversationListItemVO.class);
    }

    /**
     * 用户查看自己的某个的群聊的信息
     */
    @Override
    public UserGroupDetailVO userSelectRoom(Long roomId) {
        Long userId = SecurityUser.getUserId();
        return userRoomDao.getGroupChatDetail(userId,roomId);
    }

    /**
     * 根据群聊id查询群成员
     */
    @Override
    public List<ChatRoomMemberVO> listByRoomId(Long roomId) {
        return userRoomDao.selectByRoomId(roomId);
    }

    /**
     * 用户加入群聊
     */
    public Result<?> save(Long userId, Long roomId, Integer role) {
        ChatUserRoomEntity chatUserRoomEntity = userRoomDao.selectByUserAndRoom(userId, roomId);
        if (chatUserRoomEntity == null) {
            ChatUserRoomEntity userRoomEntity = new ChatUserRoomEntity();
            userRoomEntity.setUserId(userId);
            userRoomEntity.setRoomId(roomId);
            userRoomEntity.setRole(role);
            userRoomEntity.setIsPinned(PinnedStatusEnum.NOT_PINNED.getCode());
            userRoomEntity.setIsExit(UserRoomExitStatusEnum.NOT_EXITED.getCode());

            userRoomDao.insert(userRoomEntity);
        }else {
            if (chatUserRoomEntity.getIsExit() == UserRoomExitStatusEnum.NOT_EXITED.getCode()){
                return new Result<>().error("您已加入该群聊");
            }else {
                //用户曾经被踢出群聊且没有删除群聊
                chatUserRoomEntity.setIsExit(UserRoomExitStatusEnum.NOT_EXITED.getCode());
                chatUserRoomEntity.setRole(UserRoomRoleEnum.MEMBER.getCode());
                userRoomDao.updateById(chatUserRoomEntity);
            }
        }
        //群聊成员数+1
        roomService.incrementMember(roomId);

        //通过Websocket给前端用户发送信息：加入群聊提醒TODO


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
     * 用户退出群聊
     */
    @Override
    public void delete(Long roomId) {
        Long userId = SecurityUser.getUserId();
        userRoomDao.deleteByUserIdAndRoomId(userId,roomId);
        roomService.reduceMember(roomId);
    }

    /**
     * 从群聊中批量踢出一批用户
     */
    @Override
    public void updateExitStatusBatch(Long roomId, List<Long> userIds) {
        userRoomDao.updateExitStatusBatch(roomId,userIds);
    }

}