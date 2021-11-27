package com.integration.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* @Package: com.integration.fegin
* @ClassName: OcpService
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 智能运维feign调用
*/
@FeignClient(value = "ocp", fallbackFactory = OcpServiceFallbackFactory.class)
public interface OcpService {
    /**
     * 自动巡检定时任务
     */
    @RequestMapping(value = "/inspectWorkOrder/jobTiming", method = RequestMethod.POST)
    public void jobTiming();

}
