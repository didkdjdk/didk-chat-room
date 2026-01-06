package com.didk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didk.entity.MessageErrorLogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageErrorLogDao extends BaseMapper<MessageErrorLogEntity> {
}