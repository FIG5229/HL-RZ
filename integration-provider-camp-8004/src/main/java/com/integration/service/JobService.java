package com.integration.service;


import com.integration.entity.PageResult;

/**
 * @ClassName JobService
 * @Description 定时任务服务
 * @Author zhangfeng
 * @Date 2020/8/12 9:25
 * @Version 1.0
 **/
public interface JobService {


    PageResult add(String name);

    PageResult add(String name, String cron);


    PageResult addMulti(String name, String crons);


    PageResult update(String name, String cron);


    PageResult delete(String name);

    PageResult state(String name);

    PageResult select(String name);

    PageResult pauseJob(String name);

    PageResult resumeJob(String name);

}
