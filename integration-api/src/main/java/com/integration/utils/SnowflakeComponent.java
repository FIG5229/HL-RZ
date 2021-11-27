package com.integration.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName SnowflakeComponent
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/1/28 14:33
 * @Version 1.0
 **/
@Component
public class SnowflakeComponent {
    @Value("${iomplatform.datacenterId:31}")
    private long datacenterId;

    @Value("${iomplatform.workId:31}")
    private long workId;


    private static volatile IdWorker instance;

    public IdWorker getInstance() {
        if (instance == null) {
            synchronized (IdWorker.class) {
                if (instance == null) {
                    instance = new IdWorker(workId, datacenterId);
                }
            }
        }
        return instance;
    }
}
