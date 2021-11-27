package com.integration.service.impl;

import com.integration.entity.PageResult;
import com.integration.service.JobService;
import com.integration.utils.DataUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * @ClassName JobServiceImpl
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/8/12 9:38
 * @Version 1.0
 **/
@Service
public class JobServiceImpl implements JobService {

    private static Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired(required = false)
    private Scheduler scheduler;

    static String jobNamePrefix = "job-";
    static String triggerNamePrefix = "trigger-";
    static String group = "group";


    @Override
    public PageResult add(String name) {


        Class jobClass = null;
        try {
            jobClass = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        name = name.substring(name.lastIndexOf(".")+1);

        if(select(name).isReturnBoolean()){
            delete(name);
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobNamePrefix+name, group)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerNamePrefix+name, group)
                .startNow()
                // 每天0点执行
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.warn("添加任务出现异常： ", e);
            return DataUtils.returnPr(false,"添加出现异常！");
        }

        return DataUtils.returnPr("添加成功！");
    }

    @Override
    public PageResult add(String name, String cron) {
        Class jobClass = null;
        try {
            jobClass = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        name = name.substring(name.lastIndexOf(".")+1);

        if(select(name).isReturnBoolean()){
            delete(name);
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobNamePrefix+name, group)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerNamePrefix+name, group)
                .startNow()
                // 每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.warn("添加任务出现异常： {}{}",name,cron, e);
            return DataUtils.returnPr(false,"添加出现异常！");
        }

        return DataUtils.returnPr("添加成功！");
    }

    @Override
    public PageResult addMulti(String name, String crons) {
        Class jobClass = null;
        try {
            jobClass = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        name = name.substring(name.lastIndexOf(".")+1);

        if(select(name).isReturnBoolean()){
            delete(name);
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobNamePrefix+name, group)
                .storeDurably()
                .build();
        final String triggerName = name;

        Set<Trigger> triggerSet = new HashSet<>();
        String [] jobCrons  = crons.split(",");
        for (int i = 0; i < jobCrons.length; i++) {
            triggerSet.add(TriggerBuilder.newTrigger()
                    .withIdentity(triggerNamePrefix+triggerName+i, group)
                    .startNow()
                    // 每天0点执行
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobCrons[i]))
                    .build());
        }

        try {
            scheduler.scheduleJob(jobDetail, triggerSet, true);
        } catch (SchedulerException e) {
            log.warn("添加任务出现异常： {}{}",name,crons, e);
            return DataUtils.returnPr(false,"添加出现异常！");
        }

        return DataUtils.returnPr("添加成功");
    }

    @Override
    public PageResult update(String name, String cron) {

        String jobName = triggerNamePrefix + name;
        TriggerKey triggerKey = new TriggerKey(jobName, group);
        CronTrigger cronTrigger = null;
        try {
            cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            log.warn("修改任务出现异常： ", e);
            DataUtils.returnPr(false);
        }
        String oldTime = "";
        if(cronTrigger != null){
            oldTime = cronTrigger.getCronExpression();
        }
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, group)
                    .withSchedule(cronScheduleBuilder).build();
            try {
                scheduler.rescheduleJob(triggerKey, trigger);
                return DataUtils.returnPr("");
            } catch (SchedulerException e) {
                log.warn("修改任务出现异常： ", e);
                return DataUtils.returnPr(false);
            }
        }

        return DataUtils.returnPr(false);
    }

    @Override
    public PageResult delete(String name) {
        try {
            String triggerName = triggerNamePrefix + name;
            String jobName = jobNamePrefix + name;
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, group);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, group));
            log.info("removeJob: {}", JobKey.jobKey(jobName));
        } catch (Exception e) {
            log.warn("删除任务出现异常： ", e);
            return DataUtils.returnPr(false);
        }
        return DataUtils.returnPr("");
    }

    @Override
    public PageResult state(String name) {
        try {
            String triggerName = triggerNamePrefix + name;
            String jobName = jobNamePrefix + name;
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, group);
            // 查看状态
            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            return DataUtils.returnPr(state);
        } catch (Exception e) {
            log.warn("删除任务出现异常： ", e);
            return DataUtils.returnPr(false);
        }
    }

    @Override
    public PageResult select(String name) {
        try {
            JobKey key = new JobKey(jobNamePrefix+name,group);
            JobDetail jobDetail = scheduler.getJobDetail(key);
            return DataUtils.returnPr(jobDetail);
        } catch (SchedulerException e) {
            log.warn("查询任务出现异常： ", e);
            return DataUtils.returnPr(false);
        }
    }

    @Override
    public PageResult pauseJob(String name) {

        JobKey key = new JobKey(jobNamePrefix+name,group);
        try {
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            log.warn("暂停任务出现异常： ", e);
            return DataUtils.returnPr(false);
        }
        return DataUtils.returnPr("");
    }

    @Override
    public PageResult resumeJob(String name) {
        JobKey key = new JobKey(jobNamePrefix+name,group);
        try {
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            log.warn("恢复任务出现异常： ", e);
            return DataUtils.returnPr(false);
        }
        return DataUtils.returnPr("");
    }
}
