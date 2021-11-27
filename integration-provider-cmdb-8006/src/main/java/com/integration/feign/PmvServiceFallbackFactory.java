package com.integration.feign;

import com.integration.entity.PageResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.feign
* @ClassName: PmvServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 性能模块feign调用
*/
@Component
public class PmvServiceFallbackFactory implements FallbackFactory<PmvService> {
    @Override
    public PmvService create(Throwable cause) {
        return new PmvService() {
            @Override
            public List findPerformanceList(HashMap<String, Object> params) {
                return null;
            }

			@Override
			public PageResult findPerformanceInfo(String selectMethod, Long interval, String startTime, String endTime,
					String ciTypeId, String ciId, String attrs, String pageNum, String pageSize, String ciCode) {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }
}
