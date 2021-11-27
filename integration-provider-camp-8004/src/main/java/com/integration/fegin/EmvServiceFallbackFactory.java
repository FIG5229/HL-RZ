package com.integration.fegin;

import com.integration.entity.EmvEvCurrentInfo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EmvServiceFallbackFactory implements FallbackFactory<EmvService>{
    private static final Logger logger = LoggerFactory.getLogger(EmvServiceFallbackFactory.class);
    @Override
    public EmvService create(Throwable throwable) {
        logger.error("调用告警定时任务执行方法异常：",throwable);
        throwable.printStackTrace();
        return new EmvService() {

            @Override
            public void clearTiming() {

            }

            @Override
            public void upgradeTiming() {

            }

            @Override
            public void clearEvent() {

            }

            @Override
            public void incidentJob() {

            }

            @Override
            public boolean saveImitateEvcCurrentInfo(EmvEvCurrentInfo emvEvCurrentInfo) {
                return false;
            }
        };
    }
}
