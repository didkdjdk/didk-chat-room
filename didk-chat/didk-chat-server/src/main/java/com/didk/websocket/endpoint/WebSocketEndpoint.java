package com.didk.websocket.endpoint;

import com.didk.commons.security.user.UserDetail;
import com.didk.component.GetHttpSessionConfig;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.alibaba.fastjson.JSON;
import com.didk.commons.security.user.UserDetail;
import com.didk.websocket.config.WebSocketAuthConfig; // 假设你创建了配置类
import com.didk.websocket.model.WSBaseReq; // 假设这是你的基础请求对象
import com.didk.websocket.service.WebSocketService;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@ServerEndpoint(value = "/chat/{userName}", configurator = GetHttpSessionConfig.class)
@Component
public class WebSocketEndpoint {

    // 1. 定义静态的 Service，用于处理业务
    private static WebSocketService webSocketService;

    // 2. 通过 Setter 方法注入 (Spring 启动时会调用)
    @Autowired
    public void setWebSocketService(WebSocketService webSocketService) {
        WebSocketEndpoint.webSocketService = webSocketService;
    }

    /**
     * 建立websocket连接后，被调用
     *
     * @param session Session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("userName") String userName) {
        // 1. 获取鉴权信息 (从 Configurator 拦截器中传递过来的)
        Long userId = (Long)config.getUserProperties().get("currentUser");

        // 2. 最后的安全防线
        if (userId == null) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "未鉴权"));
            } catch (IOException e) {
                log.error("关闭未鉴权连接异常", e);
            }
            return;
        }

        // 3. 将用户信息绑定到 Session，方便后续方法取用
        session.getUserProperties().put("currentUser", userId);

        // 4. 交给业务层处理 "上线" 逻辑
        webSocketService.connect(session, userId);
    }

    /**
     * 浏览器发送消息到服务端时该方法会被调用，也就是私聊
     * @param message String
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        try {
            // 1. 获取当前用户
            Long userId = (Long) session.getUserProperties().get("currentUser");

            // 2. 解析消息类型 (反序列化为基础对象，只看 type 字段)
            // 假设 WSBaseReq 有一个 Integer type 或 String type 字段
            WSBaseReq baseReq = JSON.parseObject(message, WSBaseReq.class);

            if (baseReq == null || baseReq.getType() == null) {
                return;
            }

            // 3. 路由分发 (根据不同的 type 调用 Service 不同的方法)
            switch (baseReq.getType()) {
                case "CHAT": // 聊天消息
                    webSocketService.handleChatMessage(session, baseReq.getData(), userId);
                    break;
                case "HEARTBEAT": // 心跳检测
                    webSocketService.handleHeartbeat(session, userId);
                    break;
                default:
                    log.warn("未知的消息类型: {}", baseReq.getType());
            }

        } catch (Exception e) {
            log.error("消息处理异常", e);
            // 可以选择给前端回一个错误包
            // webSocketService.sendError(session, "消息格式错误");
        }
    }

    /**
     * 断开 websocket 连接时被调用
     *
     * @param session Session
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        Long userId = (Long) session.getUserProperties().get("currentUser");
        // 交给业务层处理 "下线" 逻辑
        if (userId != null) {
            webSocketService.disconnect(session, userId);
        }
    }

    @OnError
    public void onError(Session session,Throwable t) {
        //业务OnError后会自动触发onClose
        log.error("WebSocket 连接错误: sessionId={}", session.getId(), t);
    }


}