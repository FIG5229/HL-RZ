package com.integration.fegin;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.fegin
 * @ClassName: InterfaceServiceFallbackFactory
 * @Author: ztl
 * @Date: 2021-01-11
 * @Version: 1.0
 * @description:
 */
@Component
public class InterfaceServiceFallbackFactory implements FallbackFactory<InterfaceService>{

    @Override
    public InterfaceService create(Throwable throwable) {
        throwable.printStackTrace();
        return new InterfaceService() {
            @Override
            public List<Map> findInterfaceStateInfo(Map map) {
                return null;
            }
        };
    }
}
