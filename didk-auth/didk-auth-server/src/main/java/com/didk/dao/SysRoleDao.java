package com.didk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didk.model.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
}
