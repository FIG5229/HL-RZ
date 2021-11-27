package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.integration.entity.PageResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 性能管理 pmv-8005
 * @author zhuxd
 * @date 2019-09-20 15:00
 */
@FeignClient(value = "index",fallbackFactory = PmvServiceFallbackFactory.class)
 public interface PmvService {
    /**
     * 根据ciId获取性能数据信息
     * @param params HashMap
     * @return PageResult
     */
    @RequestMapping(value = "/bigScreen/findPerformanceInfo", method = RequestMethod.GET)
    public List findPerformanceList(@RequestParam  HashMap<String, Object> params);
    
    @RequestMapping(value = "/bigScreen/findPerformanceChart", method = RequestMethod.POST)
    public PageResult findPerformanceInfo(@RequestParam("selectMethod")String selectMethod, @RequestParam("interval")Long interval, @RequestParam("startTime")String startTime, @RequestParam("endTime")String endTime,
    		@RequestParam("ciTypeId")String ciTypeId, @RequestParam("ciId")String ciId, @RequestParam("attrs")String attrs,@RequestParam("pageNum")String pageNum,@RequestParam("pageSize")String pageSize,@RequestParam("ciCode")String ciCode);
}
