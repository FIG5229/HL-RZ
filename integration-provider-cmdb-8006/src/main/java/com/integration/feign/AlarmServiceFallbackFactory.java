package com.integration.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.feign
* @ClassName: AlarmServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 事件可视化feign调用
*/
@Component
public class AlarmServiceFallbackFactory implements FallbackFactory<AlarmService> {
    @Override
    public AlarmService create(Throwable cause) {
        return new AlarmService() {
            @Override
            public List<Map<String, Object>> getEmvEvCurrentInfoByciid(Map map) {
                return null;
            }

            @Override
            public void updUnMatchByKpiId(String kpiId) {

            }

        };
    }
}
