package com.integration.config;

import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

/**
 * @ClassName MyHandshakeInterceptor
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/7/30 16:08
 * @Version 1.0
 **/
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest req = (ServletServerHttpRequest) serverHttpRequest;

        // 根据token认证用户，不通过返回拒绝握手
        String token = req.getServletRequest().getParameter("token");
        //验证链接 TODO 鉴权
        if(StringUtils.isEmpty(token)){
            return false;
        }

        Principal user = new User(TokenUtils.getTokenUserId(token));

        // 保存会话信息
        map.put("user", user);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
