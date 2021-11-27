package com.integration.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


/**
 * WebSocket拦截器类
 * @author dell
 *
 */
public class WsInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1,
			WebSocketHandler arg2, Exception arg3) {


	}
	
	/**
	 * 通过拦截器获取前台传入的用户ID
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Map<String, Object> map)
			throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			String id = request.getURI().toString().split("ID=")[1];
			ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
			HttpSession session = serverHttpRequest.getServletRequest()
					.getSession();
			map.put("WEBSOCKET_USERID", id);
		}
		return true;
	}
}
