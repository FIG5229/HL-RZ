package com.integration.feign;

import com.integration.entity.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value="camp",fallbackFactory = CzryServiceFallbackFactory.class)
public interface CzryService {


	@RequestMapping(value = "/authCodeCheck")
	PageResult authCodeCheck();

}
