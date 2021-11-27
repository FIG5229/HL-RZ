package com.integration.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.fegin
* @ClassName: WebsocketService
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: websocket feign调用
*/
@FeignClient(value = "websocket",fallbackFactory = WebsocketServiceFallbackFactory.class)
public interface WebsocketService {
	@RequestMapping(value = "/websocketSend/sendAppointUser", method = RequestMethod.POST)
	List<Map> sendAppointUser(@RequestParam("content") String content);

	@RequestMapping(value = "/websocketSend/sendAllUser", method = RequestMethod.POST)
	void sendAllUser(@RequestParam("content") String content);
}
