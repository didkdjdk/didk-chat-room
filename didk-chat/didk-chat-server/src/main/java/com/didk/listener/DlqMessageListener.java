package com.didk.listener;

import cn.hutool.json.JSONUtil;
import com.didk.entity.MessageErrorLogEntity;
import com.didk.service.MessageErrorLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 死信队列消费者
 * 将无法处理的消息持久化到 MySQL 的错误日志表
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DlqMessageListener implements StreamListener<String, MapRecord<String, String, String>> {

    private final MessageErrorLogService messageErrorLogService;

    @Override
    public void onMessage(MapRecord<String, String, String> record) {
        Map<String, String> value = record.getValue();
        String tempId = value.get("tempId");
        log.error("DLQ 收到死信消息，准备落库告警: tempId={}, payload={}", tempId, value);

        try {
            // 1. 构建错误日志实体
            MessageErrorLogEntity errorLog = new MessageErrorLogEntity();
            errorLog.setTempId(tempId);

            // 安全转换数据类型 (Redis Stream 中都是 String)
            if (value.get("fromUserId") != null) {
                errorLog.setSendId(Long.valueOf(value.get("fromUserId")));
            }
            if (value.get("targetId") != null) {
                errorLog.setReceiverId(Long.valueOf(value.get("targetId")));
            }
            if (value.get("type") != null) {
                errorLog.setReceiverType(Integer.parseInt(value.get("type")));
            }
            if (value.get("messageType") != null) {
                errorLog.setMessageType(Integer.parseInt(value.get("messageType")));
            }

            errorLog.setContent(value.get("content"));
            errorLog.setOriginalPayload(JSONUtil.toJsonStr(value)); // 保存原始 JSON 以便排查
            errorLog.setErrorMessage("Retry count exceeded limit (Max 3 retries)"); // 记录原因
            errorLog.setStatus(0); // 0-待处理
            errorLog.setCreateDate(new Date());

            // 2. 保存到 MySQL
            messageErrorLogService.save(errorLog);

            log.info("死信消息已归档: id={}", errorLog.getId());

        } catch (Exception e) {
            // 死信处理都报错了，只能打本地日志作为最后一道防线
            log.error("DLQ 严重错误：死信落库失败！！！数据可能丢失。Raw: {}", value, e);
        }
    }
}