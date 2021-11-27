package com.integration.job;

import com.integration.fegin.EmvService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
* @Package: com.integration.job
* @ClassName: EventClearJob
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 定时清理定时任务
*/
@Component
public class EventClearJob implements Job {

    Logger log = LoggerFactory.getLogger(EventClearJob.class);

    @Autowired
    private EmvService emvService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("定时清理定时任务执行开始");
        emvService.clearTiming();
        log.debug("定时清理定时任务执行结束");
    }
}
