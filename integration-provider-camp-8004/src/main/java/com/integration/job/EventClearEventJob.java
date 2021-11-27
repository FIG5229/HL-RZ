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
 * 定时清理三个月告警数据定时任务
 * @author dell
 */
@Component
public class EventClearEventJob implements Job {

    Logger log = LoggerFactory.getLogger(EventClearEventJob.class);

    @Autowired
    private EmvService emvService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("定时清理三个月告警数据定时任务执行开始");
        emvService.clearEvent();
        log.debug("定时清理三个月告警数据定时任务执行结束");
    }
}
