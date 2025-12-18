package com.didk.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.commons.security.user.SecurityUser;
import com.didk.commons.security.user.UserDetail;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.ConvertUtils;
import com.didk.commons.tools.utils.PageUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.dao.ChatFriendDao;
import com.didk.dto.ChatFriendDTO;
import com.didk.entity.ChatFriendEntity;
import com.didk.enums.FriendStatusEnum;
import com.didk.feign.UserFeignClient;
import com.didk.service.ChatFriendService;
import com.didk.vo.ChatConversationListItemVO;
import com.didk.vo.ChatFriendVO;
import com.didk.vo.ChatUserVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 好友实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatFriendServiceImpl extends ServiceImpl<ChatFriendDao, ChatFriendEntity> implements ChatFriendService {

    @Resource
    private ChatFriendDao friendDao;
    @Resource
    private UserFeignClient userFeignClient;

    /**
     * 查询所有好友关系和群聊关系
     */
    @Override
    public PageData<ChatConversationListItemVO> listAllFriendsAndRooms(Map<String, Object> params) {
        PageUtils.paramsToLike(params,"friendName");
        IPage<ChatConversationListItemVO> page = PageUtils.getPage(params, null, false);
        List<ChatConversationListItemVO> list = friendDao.selectAllFriendsAndRoomsByUserId(params);
        return PageUtils.getPageData(list, page.getTotal(), ChatConversationListItemVO.class);
    }

    /**
     * 根据id查询好友关系
     */
    @Override
    public ChatFriendVO get(Long id) {
        return friendDao.getById(id);
    }

    //以当前用户查询另一个用户的信息
    @Override
    public ChatUserVO getUserInfo(Long friendId) {
        Long userId = SecurityUser.getUserId();
        return friendDao.getUserInfo(userId,friendId);
    }

    /**
     * 添加好友关系
     */
    @Override
    public Result<?> save(Long userId, Long friendId) {
        ChatFriendEntity friendEntity = new ChatFriendEntity();
        friendEntity.setUserId(userId);
        friendEntity.setFriendId(friendId);
        friendEntity.setStatus(FriendStatusEnum.NORMAL.getCode());
        UserDetail friendInfo = userFeignClient.getById(friendId).getData();
        friendEntity.setFriendName(friendInfo.getUsername());

        friendDao.insert(friendEntity);
        return new Result<>().ok(null);
    }

    /**
     * 修改好友关系（如修改备注等）
     */
    @Override
    public Result<?> update(ChatFriendDTO dto) {
        ChatFriendEntity friendEntity = ConvertUtils.sourceToTarget(dto, ChatFriendEntity.class);
        friendDao.updateById(friendEntity);
        return new Result<>().ok(null);
    }

    /**
     * 删除好友关系
     */
    @Override
    public void delete(Long friendId) {
        //从好友表中删除当前用户对好友的好友关系
        Long userId = SecurityUser.getUserId();
        friendDao.deleteByUserIdAndFriendId(userId,friendId);

        //将好友表中好友对当前用户的好友关系的状态改为已删除
        ChatFriendVO friendVO = friendDao.getById(friendId);
        if (friendVO != null) {
            ChatFriendDTO dto = new ChatFriendDTO();
            dto.setId(friendVO.getId());
            dto.setStatus(FriendStatusEnum.DELETED.getCode());
            update(dto);
        }
    }

}