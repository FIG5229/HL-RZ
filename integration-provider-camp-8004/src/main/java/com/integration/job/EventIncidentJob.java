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
 * @ProjectName: integration
 * @Package: com.integration.job
 * @ClassName: EventIncidentJob
 * @Author: ztl
 * @Date: 2021-04-27
 * @Version: 1.0
 * @description:告警故障定时刷新
 */
@Component
public class EventIncidentJob implements Job {
    Logger log = LoggerFactory.getLogger(EventIncidentJob.class);

    @Autowired
    private EmvService emvService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("故障定时刷新定时任务执行开始");
        emvService.incidentJob();
        log.debug("故障定时刷新定时任务执行结束");
    }
}
