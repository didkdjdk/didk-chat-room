package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.ChatUserBlockEntity;
import com.didk.vo.ChatUserBlockVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户黑名单DAO
 */
@Mapper
public interface ChatUserBlockDao extends BaseDao<ChatUserBlockEntity> {

    List<ChatUserBlockVO> listBlockedUsers(Map<String, Object> params);

}