package io.didk.dao;
import com.didk.commons.mybatis.dao.BaseDao;
import io.didk.entity.SysSmsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信
 * 
 * @author jiujingz@126.com
 */
@Mapper
public interface SysSmsDao extends BaseDao<SysSmsEntity> {
	
}
