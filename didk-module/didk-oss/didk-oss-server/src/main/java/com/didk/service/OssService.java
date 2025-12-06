package com.didk.service;

import com.didk.commons.mybatis.service.BaseService;
import com.didk.commons.tools.page.PageData;
import com.didk.entity.OssEntity;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author jiujingz@126.com
 */
public interface OssService extends BaseService<OssEntity> {

	PageData<OssEntity> page(Map<String, Object> params);
}
