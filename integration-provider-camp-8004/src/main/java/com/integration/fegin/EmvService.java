package com.integration.fegin;

import com.integration.entity.EmvEvCurrentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ProjectName: integration
 * @Package: com.integration.fegin
 * @ClassName: EmvService
 * @Author: ztl
 * @Date: 2021-04-19
 * @Version: 1.0
 * @description:告警模块Feign调用
 */
@FeignClient(value ="alarm", fallbackFactory = EmvServiceFallbackFactory.class)
public interface EmvService {
    /**
     * 定时清理定时任务
     */
    @RequestMapping(value = "/emvJob/clearTiming", method = RequestMethod.POST)
    public void clearTiming();

    /**
     * 定时超时升级规则
     */
    @RequestMapping(value = "/emvJob/upgradeTiming", method = RequestMethod.POST)
    public void upgradeTiming();

    /**
     * 定时清理三个月告警数据
     */
    @RequestMapping(value = "/emvJob/clearEvent", method = RequestMethod.POST)
    public void clearEvent();

    /**
     * 告警故障定时刷新
     */
    @RequestMapping(value = "/emvJob/incidentJob", method = RequestMethod.POST)
    public void incidentJob();

    /**
     * 模拟告警
     *
     * @param emvEvCurrentInfo
     * @return
     */
    @RequestMapping(value = "/eventCurrent/saveImitateEvcCurrentInfo", method = RequestMethod.POST)
    public boolean saveImitateEvcCurrentInfo(@RequestBody EmvEvCurrentInfo emvEvCurrentInfo);

}
