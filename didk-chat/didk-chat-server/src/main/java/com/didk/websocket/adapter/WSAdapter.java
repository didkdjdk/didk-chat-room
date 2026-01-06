package com.didk.websocket.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.didk.entity.ChatMessageEntity;
import com.didk.websocket.model.response.ChatMessageResp;
import com.didk.websocket.model.response.WSBaseResp;
import com.didk.enums.WSReqTypeEnum;
import org.springframework.stereotype.Component;

/**
 * WebSocket 消息适配器
 * 负责构建发送给前端的响应对象
 */
@Component
public class WSAdapter {

    /**
     * 构建聊天消息响应
     * @param message 数据库实体
     * @param tempId 前端临时ID
     */
    public WSBaseResp<ChatMessageResp> buildChatMessageResp(ChatMessageEntity message, String tempId) {
        // 1. 转换数据
        ChatMessageResp resp = new ChatMessageResp();
        // 自动属性拷贝 (要求字段名一致)
        BeanUtil.copyProperties(message, resp);
        // 手动设置 tempId
        resp.setTempId(tempId);

        // 2. 包装成统一响应格式
        WSBaseResp<ChatMessageResp> wsResp = new WSBaseResp<>();
        // 确保你的 WSReqTypeEnum 中有 CHAT 枚举 (如果没有请添加: CHAT(3, "聊天消息"))
        wsResp.setType(WSReqTypeEnum.CHAT.getType());
        wsResp.setData(resp);

        return wsResp;
    }
}