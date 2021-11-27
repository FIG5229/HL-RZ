package com.integration.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;


/**
 * WebSocket测试类
 * @author dell
 *
 */
@Component
public class WebSocketTest {
	 /**
	  * 每5秒向前台推送数据
	  */
	@Scheduled(cron = "0/5 * * * * *")
	public void sendMessage(){

	}

}
