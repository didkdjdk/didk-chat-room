package com.didk.service;

import com.didk.model.entity.SysLanguageEntity;
import com.didk.commons.mybatis.service.BaseService;

/**
 * 国际化
 */
public interface SysLanguageService extends BaseService<SysLanguageEntity> {

    /**
     * 保存或更新
     * @param tableName   表名
     * @param tableId     表主键
     * @param fieldName   字段名
     * @param fieldValue  字段值
     * @param language    语言
     */
    void saveOrUpdate(String tableName, Long tableId, String fieldName, String fieldValue, String language);
}

