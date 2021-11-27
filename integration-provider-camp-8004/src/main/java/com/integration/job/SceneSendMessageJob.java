package com.integration.job;

import com.integration.fegin.EmvService;
import com.integration.fegin.SceneService;
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
 * @ClassName: SceneSendMessageJob
 * @Author: ztl
 * @Date: 2021-04-19
 * @Version: 1.0
 * @description:场景可视化工作台日程提醒定时任务
 */
@Component
public class SceneSendMessageJob implements Job {
    Logger log = LoggerFactory.getLogger(EventUpgradeJob.class);

    @Autowired
    private SceneService sceneService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("场景可视化工作台日程提醒定时任务执行开始");
        sceneService.sendMessage();
        log.debug("场景可视化工作台日程提醒定时任务执行结束");
    }
}
