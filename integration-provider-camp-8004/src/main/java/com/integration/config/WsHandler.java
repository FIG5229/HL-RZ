package com.integration.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
/**
* @Package: com.integration.config
* @ClassName: WsHandler
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description:
*/
public class WsHandler implements WebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(WsHandler.class);
	private static final Map<String, WebSocketSession> USERS;

	static {
		USERS = new HashMap<>();
	}

	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: 连接关闭
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {


	}

	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: 新增socket
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		String id = session.getUri().toString().split("ID=")[1];
		if (id != null) {
			USERS.put(id, session);
			session.sendMessage(new TextMessage("成功建立socket连接"));

		}

	}

	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: 接收socket信息
	 */
	@Override
	public void handleMessage(WebSocketSession webSocketSession,
			WebSocketMessage<?> webSocketMessage) throws Exception {
	}
	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: 连接出错
	 */
	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1)
			throws Exception {

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 获取用户标识
	 *
	 * @param session
	 * @return
	 */
	private Integer getClientId(WebSocketSession session) {
		try {
			Integer clientId = (Integer) session.getAttributes().get(
					"WEBSOCKET_USERID");
			return clientId;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 广播信息
	 *
	 * @param message
	 * @return
	 */
	public boolean sendMessageToAllUsers(TextMessage message) {
		boolean allSendSuccess = true;
		Set<String> clientIds = USERS.keySet();
		WebSocketSession session = null;
		for (String clientId : clientIds) {
			try {
				session = USERS.get(clientId);
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				allSendSuccess = false;
			}
		}

		return allSendSuccess;
	}

	/**
	 * 发送信息给指定用户
	 *
	 * @param clientId
	 * @param message
	 * @return
	 */
	public boolean sendMessageToUser(String clientId, TextMessage message) {
		if (USERS.get(clientId) == null){
			return false;
		}
		WebSocketSession session = USERS.get(clientId);
		if (!session.isOpen()){
			return false;
		}
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

}
