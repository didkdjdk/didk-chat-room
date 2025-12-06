package com.didk.websocket;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.didk.component.GetHttpSessionConfig;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{userName}", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    //注：websock是多对象的，无法直接导入bean，需要自行获取
//    private SlChatService chatService = SpringUtil.getBean(SlChatService.class);
//    private SlChatDao chatDao = SpringUtil.getBean(SlChatDao.class);

    // 保存在线的用户，key为用户名，value为 Session 对象
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 建立websocket连接后，被调用
     *
     * @param session Session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config,@PathParam("userName") String userName) {

    }

    /**
     * 浏览器发送消息到服务端时该方法会被调用，也就是私聊
     * @param message String
     */
    @OnMessage
    public void onMessage(String message) {

    }

    /**
     * 断开 websocket 连接时被调用
     *
     * @param session Session
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        onlineUsers.values().remove(session);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("WebSocket 连接出现错误：" + t.getMessage());
    }


}