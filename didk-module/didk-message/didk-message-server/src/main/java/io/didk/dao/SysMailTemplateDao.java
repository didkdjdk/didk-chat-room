package io.didk.dao;

import com.didk.commons.mybatis.dao.BaseDao;
import io.didk.entity.SysMailTemplateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件模板
 * 
 * @author jiujingz@126.com
 */
@Mapper
public interface SysMailTemplateDao extends BaseDao<SysMailTemplateEntity> {
	
}
