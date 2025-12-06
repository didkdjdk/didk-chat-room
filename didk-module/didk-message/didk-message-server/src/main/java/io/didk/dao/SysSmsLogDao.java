package io.didk.dao;
import com.didk.commons.mybatis.dao.BaseDao;
import io.didk.entity.SysSmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信日志
 *
 * @author jiujingz@126.com
 */
@Mapper
public interface SysSmsLogDao extends BaseDao<SysSmsLogEntity> {
	
}