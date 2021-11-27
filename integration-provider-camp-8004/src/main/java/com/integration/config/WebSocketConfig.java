package com.integration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.integration.interceptor.WsInterceptor;

/**
 * WebSocket配置类
 * @author dell
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WsHandler(), "/myHandler/{ID}")
				.setAllowedOrigins("*")
				.addInterceptors(new WsInterceptor());

	}
}
