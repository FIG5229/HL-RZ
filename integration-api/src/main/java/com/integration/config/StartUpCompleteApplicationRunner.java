package com.integration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ProjectName: integration
 * @Package: com.integration.config
 * @ClassName: ttartUpCompleteApplicationRunner
 * @Author: ztl
 * @Date: 2020-09-27
 * @Version: 1.0
 * @description:项目启动完成打印
 */
@Component
@Order(5)
public class StartUpCompleteApplicationRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartUpCompleteApplicationRunner.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        try {
            LOGGER.info("=============================服务启动完成===========================");
        } catch (Exception e) {
            LOGGER.error("======================服务启动失败======================");
            e.printStackTrace();
        }
    }
}
