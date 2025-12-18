package com.didk.websocket.config;

import cn.hutool.extra.spring.SpringUtil;
import com.didk.commons.security.cache.TokenStoreCache;
import com.didk.commons.security.user.UserDetail;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * WebSocket 握手鉴权配置
 * 作用等同于 HTTP 的 AuthenticationTokenFilter
 */
@Component
public class WebSocketAuthConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 1. 获取 Token
        // WebSocket 通常无法自定义 Header，所以 Token 一般放在 URL 参数中: ws://xxx/chat?token=xyz
        String token = getTokenFromQuery(request.getRequestURI().getQuery());

        if (StringUtils.isBlank(token)) {
            // 可以在这里记录日志，但抛出异常可能导致连接直接断开且无明确报错，通常直接放行在 Endpoint 里处理，或者直接拒绝
            System.out.println("WebSocket 握手失败: Token 为空");
            return;
        }

        // 2. 复用你的 TokenStoreCache
        // 注意：Configurator 是由 Tomcat/Jetty 实例化的，不是 Spring Bean，所以不能 @Autowired
        // 必须使用 SpringUtil 手动获取 Bean
        TokenStoreCache tokenStoreCache = SpringUtil.getBean(TokenStoreCache.class);

        // 3. 校验并获取用户
        UserDetail user = tokenStoreCache.getUser(token);

        if (user != null) {
            // 4. 【关键】将用户信息存入 WebSocket 的配置属性中，传递给 Endpoint
            sec.getUserProperties().put("currentUser", user.getId());
        } else {
            System.out.println("WebSocket 握手失败: Token 无效");
        }
    }

    /**
     * 简单的解析 QueryString 获取 token 参数
     */
    private String getTokenFromQuery(String query) {
        if (StringUtils.isBlank(query)) return null;
        String[] params = query.split("&");
        for (String param : params) {
            String[] pair = param.split("=");
            if (pair.length == 2 && ("token".equals(pair[0]) || "accessToken".equals(pair[0]))) {
                return pair[1];
            }
        }
        return null;
    }
}