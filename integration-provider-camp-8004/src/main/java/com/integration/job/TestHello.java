package com.integration.job;

import com.integration.aop.log.service.UserApiService;
import com.integration.utils.RedisUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestHello
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/8/12 11:34
 * @Version 1.0
 **/
@Component
public class TestHello implements Job {

    Logger log = LoggerFactory.getLogger(TestHello.class);

    @Autowired
    private UserApiService userApiService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(userApiService.getUserNameById("72904780934168577"));
        log.info("test hello run",userApiService.getUserNameById("2222"));
    }
}
