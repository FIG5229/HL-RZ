package com.integration.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @ClassName MyDefaultHandshakeHandler
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/7/30 16:12
 * @Version 1.0
 **/
@Component
public class MyDefaultHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 设置认证通过的用户到当前会话中
        return (Principal) attributes.get("user");
    }
}
