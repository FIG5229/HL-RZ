package com.integration.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.integration.entity.Param;
/**
* @Package: com.integration.feign
* @ClassName: DfileService
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@FeignClient(value = "springcloud-ws-8003", fallbackFactory = DfileServiceFallbackFactory.class)
public interface DfileService {

	@RequestMapping(value = "/common/findDfile", method = RequestMethod.GET)
	public List getDfile(@RequestParam("lable") String lable);

	@RequestMapping(value = "/common/findResult", method = RequestMethod.POST)
	public List getResult(@RequestBody Param parameter);

}
