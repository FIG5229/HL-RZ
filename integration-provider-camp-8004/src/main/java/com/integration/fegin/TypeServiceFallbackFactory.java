package com.integration.fegin;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.fegin
 * @ClassName: TypeServiceFallbackFactory
 * @Author: ztl
 * @Date: 2020-06-11
 * @Version: 1.0
 * @description:获取CI大类相关信息
 */
@Component
public class TypeServiceFallbackFactory implements FallbackFactory<TypeService> {
    @Override
    public TypeService create(Throwable throwable) {
        return new TypeService() {
            @Override
            public List<Map> findTypeListHump() {
                return null;
            }
        };
    }
}
