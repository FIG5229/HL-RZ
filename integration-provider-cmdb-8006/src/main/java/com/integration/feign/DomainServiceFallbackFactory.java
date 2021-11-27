package com.integration.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: integration
 * @Package: com.integration.feign
 * @ClassName: DomainServiceFallbackFactory
 * @Author: ztl
 * @Description:获取数据域信息
 * @Date: 2020/5/20 15:04
 * @Version: 1.0
 * @Modified By:
 */
@Component
public class DomainServiceFallbackFactory implements FallbackFactory<DomainService> {
    @Override
    public DomainService create(Throwable throwable) {
        return new DomainService() {
            @Override
            public Object findDomain(String domainCode) {
                return null;
            }
        };
    }
}
