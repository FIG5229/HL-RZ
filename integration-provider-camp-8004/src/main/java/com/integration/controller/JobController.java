package com.integration.controller;

import com.integration.entity.IomCampJob;
import com.integration.entity.PageResult;
import com.integration.service.IomCampJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName JobController
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/8/12 10:16
 * @Version 1.0
 **/
@RequestMapping("/job")
@RestController
public class JobController {

    @Autowired
    private IomCampJobService jobService;


    @RequestMapping("/add")
    public PageResult add(IomCampJob job){

        return jobService.save(job);
    }



    @RequestMapping("/update")
    public PageResult update(IomCampJob job){
        return jobService.update(job);
    }


    @RequestMapping("/delete")
    public PageResult delete(Integer id){
        return jobService.deleteById(id);
    }

    @RequestMapping("/info")
    public PageResult info(Integer id){
        return jobService.info(id);
    }

    @RequestMapping("/list")
    public PageResult list(IomCampJob job, Integer pageSize, Integer pageNum){
        return jobService.list(job, pageSize, pageNum);
    }

    @RequestMapping("/pauseJob")
    public PageResult pauseJob(Integer id){
        return jobService.pauseJob(id);
    }

    @RequestMapping("/resumeJob")
    public PageResult resumeJob(Integer id){
        return jobService.resumeJob(id);
    }



}
