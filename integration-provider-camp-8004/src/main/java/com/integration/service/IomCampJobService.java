package com.integration.service;

import com.integration.entity.IomCampJob;
import com.integration.entity.PageResult;

/**
 * @Description //定时任务
 * @Author zhangfeng
 * @Version 1.0
 **/
public interface IomCampJobService {

    /**
     * 新增记录
     * @return
     */
    PageResult save(IomCampJob job);


    /**
     * 修改记录
     * @return
     */
    PageResult update(IomCampJob job);



    /**
     * 删除记录
     * @return
     */
    PageResult deleteById(Integer id);


    /**
     * 查询记录列表
     * @return
     */
    PageResult list(IomCampJob job, Integer pageSize, Integer pageNum);

    /**
     * 查询记录列表
     * @return
     */
    PageResult list(IomCampJob job);


    /**
     * 查询记录
     * @return
     */
    PageResult info(Integer id);

    /**
     * 查询记录
     * @return
     */
    PageResult pauseJob(Integer id);

    /**
     * 查询记录
     * @return
     */
    PageResult resumeJob(Integer id);

    /**
     * 初始化定时任务
     * @return
     */
    PageResult init();
}
