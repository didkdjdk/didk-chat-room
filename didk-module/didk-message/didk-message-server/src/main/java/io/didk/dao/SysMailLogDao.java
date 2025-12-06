package io.didk.dao;
import com.didk.commons.mybatis.dao.BaseDao;
import io.didk.entity.SysMailLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送记录
 * 
 * @author jiujingz@126.com
 */
@Mapper
public interface SysMailLogDao extends BaseDao<SysMailLogEntity> {
	
}
