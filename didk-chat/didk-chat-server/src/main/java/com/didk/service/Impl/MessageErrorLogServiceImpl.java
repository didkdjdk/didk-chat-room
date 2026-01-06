package com.didk.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didk.dao.MessageErrorLogDao;
import com.didk.entity.MessageErrorLogEntity;
import com.didk.service.MessageErrorLogService;
import org.springframework.stereotype.Service;

@Service
public class MessageErrorLogServiceImpl extends ServiceImpl<MessageErrorLogDao, MessageErrorLogEntity> implements MessageErrorLogService {
}