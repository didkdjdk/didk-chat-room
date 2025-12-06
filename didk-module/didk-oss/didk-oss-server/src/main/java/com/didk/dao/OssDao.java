package com.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import com.didk.entity.OssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 */
@Mapper
public interface OssDao extends BaseDao<OssEntity> {
	
}
