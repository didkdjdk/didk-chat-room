package com.didk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.didk.commons.mybatis.service.impl.BaseServiceImpl;
import com.didk.commons.tools.constant.Constant;
import com.didk.commons.tools.page.PageData;
import com.didk.dao.OssDao;
import com.didk.entity.OssEntity;
import com.didk.service.OssService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class OssServiceImpl extends BaseServiceImpl<OssDao, OssEntity> implements OssService {

	@Override
	public PageData<OssEntity> page(Map<String, Object> params) {
		IPage<OssEntity> page = baseDao.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			new QueryWrapper<>()
		);
		return getPageData(page, OssEntity.class);
	}
}
