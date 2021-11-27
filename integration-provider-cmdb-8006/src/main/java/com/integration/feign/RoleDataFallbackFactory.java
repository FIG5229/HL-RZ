package com.integration.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.feign
 * @ClassName: RoleDataFallbackFactory
 * @Author: ztl
 * @Date: 2020-06-14
 * @Version: 1.0
 * @description:获取用户最高数据权限
 */
@Component
public class RoleDataFallbackFactory implements FallbackFactory<RoleDataService> {
    private final static Logger logger = LoggerFactory.getLogger(RoleDataFallbackFactory.class);
    @Override
    public RoleDataService create(Throwable throwable) {
        logger.error("获取用户最高数据权限异常：",throwable);
        return new RoleDataService(){
            @Override
            public List<Map> findRoleHighDataList() {
                return new ArrayList<>();
            }
        };
    }
}
