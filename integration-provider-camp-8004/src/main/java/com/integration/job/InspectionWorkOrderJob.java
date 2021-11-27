package com.integration.job;

import com.integration.fegin.OcpService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: integration
 * @Package: com.integration.job
 * @ClassName: InspectionWorkOrderJob
 * @Author: ztl
 * @Date: 2021-06-23
 * @Version: 1.0
 * @description:定期巡检定时任务
 */
public class InspectionWorkOrderJob implements Job {
    Logger log = LoggerFactory.getLogger(InspectionWorkOrderJob.class);

    @Autowired
    private OcpService ocpService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("定期巡检定时任务执行开始");
        ocpService.jobTiming();
        log.debug("定期巡检定时任务执行结束");
    }
}
