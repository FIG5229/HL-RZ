package com.integration.fegin;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
public class SceneServiceFallbackFactory implements FallbackFactory<SceneService>{
    private static final Logger logger = LoggerFactory.getLogger(SceneServiceFallbackFactory.class);
    @Override
    public SceneService create(Throwable throwable) {
        logger.error("调用场景可视化工作台日程提醒定时任务执行方法异常：",throwable);

        return new SceneService() {

            @Override
            public void sendMessage() {

            }
        };
    }
}
