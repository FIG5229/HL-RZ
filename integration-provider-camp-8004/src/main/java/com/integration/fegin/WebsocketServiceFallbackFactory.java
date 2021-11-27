package com.integration.fegin;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.fegin
* @ClassName: WebsocketServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: websocket feign调用
*/
@Component
public class WebsocketServiceFallbackFactory implements FallbackFactory<WebsocketService> {

	@Override
	public WebsocketService create(Throwable cause) {
		return new WebsocketService(){

			@Override
			public List<Map> sendAppointUser(String content) {
				return null;
			}

			@Override
			public void sendAllUser(String content) {
			}
		};
	}

}
