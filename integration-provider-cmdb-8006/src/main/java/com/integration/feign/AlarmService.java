package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 告警管理 alarm-8005
 * @author zhuxd
 * @date 2019-09-20 15:00
 */
@FeignClient(value = "alarm", fallbackFactory = AlarmServiceFallbackFactory.class)
public interface AlarmService {
    /**
     * 获取告警信息
     * @param map ciId
     * @return
     */
    @RequestMapping(value = "/currentinfo/getEmvEvCurrentInfoByciid", method = RequestMethod.POST)
    public List<Map<String, Object>> getEmvEvCurrentInfoByciid(@RequestParam Map<String,Object> map);

    /**
     * 根据KPI的
     * @param kpiId
     */
    @RequestMapping(value = "/unmatch/updUnMatchByKpiId")
    public void updUnMatchByKpiId(@RequestParam("kpiId") String kpiId);


}
