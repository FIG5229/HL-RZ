package com.integration.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.integration.dao.IomCampJobMapper;
import com.integration.entity.IomCampJob;
import com.integration.entity.PageResult;
import com.integration.service.IomCampJobService;
import com.integration.service.JobService;
import com.integration.utils.DataUtils;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName IomCampJobServiceImpl
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/3/23 15:14
 * @Version 1.0
 **/
@Service
public class IomCampJobServiceImpl implements IomCampJobService {

    @Resource
    private IomCampJobMapper jobMapper;

    @Autowired
    private JobService jobService;


    static final List<Map<String, String>> JOB_LIST;

    static final List<Map<String, String>> NEW_ADD_JOB_LIST;

    static {
        JOB_LIST = new ArrayList<>();
        Map<String, String> sceneSendMessageJob = new HashMap<>();
        sceneSendMessageJob.put("name", "SceneSendMessageJob");
        sceneSendMessageJob.put("description", "场景可视化工作台日程提醒定时任务");
        sceneSendMessageJob.put("className", "com.integration.job.SceneSendMessageJob");
        sceneSendMessageJob.put("cron", "0/5 * * * * ?");
        JOB_LIST.add(sceneSendMessageJob);

        Map<String, String> eventClearJob = new HashMap<>();
        eventClearJob.put("name", "EventClearJob");
        eventClearJob.put("description", "告警定时清理规则定时任务");
        eventClearJob.put("className", "com.integration.job.EventClearJob");
        eventClearJob.put("cron", "0/59 * * * * ?");
        JOB_LIST.add(eventClearJob);

        Map<String, String> performanceClearJob = new HashMap<>();
        performanceClearJob.put("name", "PerformanceClearJob");
        performanceClearJob.put("description", "定时清理性能历史数据");
        performanceClearJob.put("className", "com.integration.job.PerformanceClearJob");
        performanceClearJob.put("cron", "0 0 23 * * ?");
        JOB_LIST.add(performanceClearJob);

        Map<String, String> eventUpgradeJob = new HashMap<>();
        eventUpgradeJob.put("name", "EventUpgradeJob");
        eventUpgradeJob.put("description", "告警超时升级定时任务");
        eventUpgradeJob.put("className", "com.integration.job.EventUpgradeJob");
        eventUpgradeJob.put("cron", "0/59 * * * * ?");
        JOB_LIST.add(eventUpgradeJob);

        Map<String, String> eventClearEventJob = new HashMap<>();
        eventClearEventJob.put("name", "EventClearEventJob");
        eventClearEventJob.put("description", "清理告警历史数据定时任务");
        eventClearEventJob.put("className", "com.integration.job.EventClearEventJob");
        eventClearEventJob.put("cron", "0 0 23 * * ?");
        JOB_LIST.add(eventClearEventJob);

        Map<String, String> eventIncidentJob = new HashMap<>();
        eventIncidentJob.put("name", "EventIncidentJob");
        eventIncidentJob.put("description", "故障定时刷新定时任务");
        eventIncidentJob.put("className", "com.integration.job.EventIncidentJob");
        eventIncidentJob.put("cron", "0 */1 * * * ?");
        JOB_LIST.add(eventIncidentJob);

        Map<String, String> shiftRecordJob = new HashMap<>();
        shiftRecordJob.put("name", "ShiftRecordJob");
        shiftRecordJob.put("description", "未发送交班记录定时任务");
        shiftRecordJob.put("className", "com.integration.job.ShiftRecordJob");
        shiftRecordJob.put("cron", "0/5 * * * * ?");
        JOB_LIST.add(shiftRecordJob);
    }

    static {
        NEW_ADD_JOB_LIST = new ArrayList<>();
        Map<String, String> inspectionWorkOrderJob = new HashMap<>();
        inspectionWorkOrderJob.put("name", "InspectionWorkOrderJob");
        inspectionWorkOrderJob.put("description", "定期巡检定时任务");
        inspectionWorkOrderJob.put("className", "com.integration.job.InspectionWorkOrderJob");
        inspectionWorkOrderJob.put("cron", "0 */1 * * * ?");
        NEW_ADD_JOB_LIST.add(inspectionWorkOrderJob);
    }

    @Override
    public PageResult save(IomCampJob job) {
        PageResult pg = validateSave(job);
        if(!pg.isReturnBoolean()){
            return pg;
        }

        String cjrId = TokenUtils.getTokenUserId();
        job.setCjrId(cjrId);
        job.setCjsj(new Date());
        job.setYxbz(1);
        job.setJobState(IomCampJob.jobstate.running.name());

        jobMapper.insert(job);
        jobService.add(job.getClassName(), job.getCron());
        return DataUtils.returnPr(true, "保存成功");
    }

    @Override
    public PageResult update(IomCampJob job) {
        if(job == null || job.getId() == null || job.getId() < 0){
            return DataUtils.returnPr(false,"参数错误");
        }

        PageResult pg = validateUpdate(job);
        if(!pg.isReturnBoolean()){
            return pg;
        }

        IomCampJob jobOld = jobMapper.selectByPrimaryKey(job.getId());
        if(jobOld == null || jobOld.getId() == null || jobOld.getId() < 0){
            return DataUtils.returnPr(false,"参数错误");
        }
        String cjrId = TokenUtils.getTokenUserId();
        jobOld.setXgrId(cjrId);
        jobOld.setXgsj(new Date());
        jobOld.setName(job.getName());
        jobOld.setCron(job.getCron());
        jobOld.setDescription(job.getDescription());

        jobMapper.updateByPrimaryKey(jobOld);
        String name = jobOld.getClassName();
        name = name.substring(name.lastIndexOf(".")+1);
        jobService.update(name, job.getCron());

        return DataUtils.returnPr(true, "修改成功");
    }

    @Override
    public PageResult deleteById(Integer id) {
        if(id == null ||id < 0){
            return DataUtils.returnPr(false,"参数错误");
        }
        IomCampJob job = jobMapper.selectByPrimaryKey(id);
        if(job != null){
            jobMapper.deleteByPrimaryKey(id);
            String name = job.getClassName();
            name = name.substring(name.lastIndexOf(".")+1);
            jobService.delete(name);
        }
        return DataUtils.returnPr(true,"删除成功");
    }

    @Override
    public PageResult list(IomCampJob job, Integer pageSize, Integer pageNum) {
        if(pageSize == null || pageSize < 1 || pageSize > 100){
            pageSize = 10;
        }
        if(pageNum == null || pageNum < 1 ){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<IomCampJob> list = jobMapper.selectList(job);
        PageInfo pageInfo = new PageInfo(list);
        PageResult pg = DataUtils.returnPr(pageInfo);
        pg.setTotalResult(Long.valueOf(pageInfo.getTotal()).intValue());
        return pg;
    }

    @Override
    public PageResult list(IomCampJob job) {
        List<IomCampJob> list = jobMapper.selectList(job);
        return DataUtils.returnPr(list);
    }

    @Override
    public PageResult info(Integer id) {
        if(id == null ||id < 0){
            return DataUtils.returnPr(false,"参数错误");
        }
        IomCampJob job = jobMapper.selectByPrimaryKey(id);
        return DataUtils.returnPr(job);
    }

    @Override
    public PageResult pauseJob(Integer id) {
        if(id == null ||id < 0){
            return DataUtils.returnPr(false,"参数错误");
        }
        IomCampJob job = jobMapper.selectByPrimaryKey(id);
        if(job != null){
            String name = job.getClassName();
            name = name.substring(name.lastIndexOf(".")+1);
            jobService.pauseJob(name);
            String cjrId = TokenUtils.getTokenUserId();
            job.setXgrId(cjrId);
            job.setXgsj(new Date());
            job.setJobState(IomCampJob.jobstate.stopped.name());
            jobMapper.updateByPrimaryKey(job);
            return DataUtils.returnPr(true,"操作成功");
        }
        return DataUtils.returnPr(false,"没有该记录");
    }

    @Override
    public PageResult resumeJob(Integer id) {
        if(id == null ||id < 0){
            return DataUtils.returnPr(false,"参数错误");
        }
        IomCampJob job = jobMapper.selectByPrimaryKey(id);
        if(job != null){
            String name = job.getClassName();
            name = name.substring(name.lastIndexOf(".")+1);
            jobService.resumeJob(name);
            String cjrId = TokenUtils.getTokenUserId();
            job.setXgrId(cjrId);
            job.setXgsj(new Date());
            job.setJobState(IomCampJob.jobstate.running.name());
            jobMapper.updateByPrimaryKey(job);
            return DataUtils.returnPr(true,"操作成功");
        }
        return DataUtils.returnPr(false,"没有该记录");
    }

    private PageResult validateSave(IomCampJob record){
        if(record == null){
            return DataUtils.returnPr(false, "参数错误！");
        }


        if(StringUtils.isEmpty(record.getName()) || record.getName().length() > 20){
            return DataUtils.returnPr(false, "参数错误，名称不合法！");
        }

        if(StringUtils.isEmpty(record.getCron()) || record.getCron().length() > 50 || !CronExpression.isValidExpression(record.getCron())){
            return DataUtils.returnPr(false, "参数错误，cron表达式不合法！");
        }


        if(StringUtils.isEmpty(record.getClassName()) || record.getClassName().length() > 100){
            return DataUtils.returnPr(false, "参数错误，job类名不合法！");
        }
        try {
            Class.forName(record.getClassName());
        }catch (Exception e){
            return DataUtils.returnPr(false, "参数错误，job类名不存在！");
        }



        return DataUtils.returnPr(true);
    }


    private PageResult validateUpdate(IomCampJob record){
        if(record == null){
            return DataUtils.returnPr(false, "参数错误！");
        }


        if(StringUtils.isEmpty(record.getName()) || record.getName().length() > 20){
            return DataUtils.returnPr(false, "参数错误，名称不合法！");
        }

        if(StringUtils.isEmpty(record.getCron()) || record.getCron().length() > 50 || !CronExpression.isValidExpression(record.getCron())){
            return DataUtils.returnPr(false, "参数错误，cron表达式不合法！");
        }





        return DataUtils.returnPr(true);
    }

    @Override
    public PageResult init() {
        /**
         * 说明：当第一次初始化时，将newAddJobList初始化为空列表，初始化的队列数据放在
         * jobList队列中。
         * 当需要新增定时任务时，将需要新增的队列增加到newAddJobList队列中，若队列中
         * 已有数据，则需要将已有数据移至jobList队列中，否则会导致定时任务重复增加。
         *
         */
        try {
            List exists = jobMapper.selectList(null);
            if(exists != null && exists.size() > 0 && NEW_ADD_JOB_LIST.size()<=0){
                return DataUtils.returnPr(true,"定时任务已经初始化过且无新增");
            }else if(NEW_ADD_JOB_LIST!=null && NEW_ADD_JOB_LIST.size()>0){
                NEW_ADD_JOB_LIST.forEach(item->{
                    IomCampJob job = JSONObject.parseObject(JSONObject.toJSONString(item), IomCampJob.class);
                    job.setJobState(IomCampJob.jobstate.running.name());
                    job.setCjrId("72904780934168577");
                    job.setCjsj(new Date());
                    job.setYxbz(1);
                    List exist = jobMapper.selectList(job);
                    if (exist==null || exist.size()<=0){
                        jobMapper.insert(job);
                        jobService.add(job.getClassName(), job.getCron());
                    }
                });
                if(exists == null || exists.size() <= 0){
                    JOB_LIST.forEach(item->{
                        IomCampJob job = JSONObject.parseObject(JSONObject.toJSONString(item), IomCampJob.class);
                        job.setJobState(IomCampJob.jobstate.running.name());
                        job.setCjrId("72904780934168577");
                        job.setCjsj(new Date());
                        job.setYxbz(1);
                        jobMapper.insert(job);
                        jobService.add(job.getClassName(), job.getCron());

                    });
                }
                return DataUtils.returnPr(true,"定时任务已经完成新增");
            }
            JOB_LIST.forEach(item->{
                IomCampJob job = JSONObject.parseObject(JSONObject.toJSONString(item), IomCampJob.class);
                job.setJobState(IomCampJob.jobstate.running.name());
                job.setCjrId("72904780934168577");
                job.setCjsj(new Date());
                job.setYxbz(1);
                jobMapper.insert(job);
                jobService.add(job.getClassName(), job.getCron());

            });
        }catch (Exception e){
            e.printStackTrace();
            return DataUtils.returnPr(false,"定时任务初始化失败");
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(CronExpression.isValidExpression("0/10 * * * * ?"));
    }
}
