package com.integration.config.init;

import com.integration.entity.IomCampJob;
import com.integration.mybatis.utils.dbInit.DbInitialization;
import com.integration.service.IomCampJobService;
import com.integration.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JobInit
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/4/20 11:13
 * @Version 1.0
 **/
@Component
public class JobInit implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Autowired
    private IomCampJobService jobService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        try {
            LOGGER.debug("定时任务初始化开始");
            jobService.init();
            LOGGER.debug("定时任务初始化结束");
        } catch (Exception e) {
            LOGGER.error("======================定时任务初始化出错======================");
            e.printStackTrace();
        }

    }
}
