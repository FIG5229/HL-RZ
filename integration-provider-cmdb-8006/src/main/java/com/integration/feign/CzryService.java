package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
/**
* @Package: com.integration.feign
* @ClassName: CzryService
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 基础管理feign调用
*/
@FeignClient(value="camp",fallbackFactory = CzryServiceFallbackFactory.class)
public interface CzryService {

	@RequestMapping(value = "/findCzryByIdFeign", method = RequestMethod.POST)
	public Object findCzryByIdFeign(@RequestParam("id") String id);

	@RequestMapping(value = "/findCzryById", method = RequestMethod.GET)
    public Map findCzryById(@RequestParam("id")String id);

}
