package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.feign
* @ClassName: EmvEvCurrentService
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 事件可视化feign调用
*/
@FeignClient(value = "alarm", fallbackFactory = EmvEvCurrentServiceFallbackFactory.class)
public interface EmvEvCurrentService {

	@RequestMapping(value = "/selectCountByCiIdList", method = RequestMethod.POST)
	public List<Map<String, Object>> selectCountByCiIdList(@RequestParam("ciIdList") String[] ciIdList,@RequestParam("ciCodeList") String[] ciCodeList);

	@RequestMapping(value = "/currentinfo/findEventByCiIDs_zhen", method = RequestMethod.POST)
	Map<String, Object> findEventByCiIDs(@RequestParam("ciId")String ciId);
}
