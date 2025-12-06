package com.didk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didk.model.entity.SysLanguageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国际化
 * 
 * @author jiujingz@126.com
 */
@Mapper
public interface SysLanguageDao extends BaseMapper<SysLanguageEntity> {

    SysLanguageEntity getLanguage(SysLanguageEntity entity);

    void updateLanguage(SysLanguageEntity entity);

}