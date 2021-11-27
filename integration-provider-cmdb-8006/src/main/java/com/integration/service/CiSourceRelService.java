package com.integration.service;

import com.integration.entity.CiSourceRel;
import com.integration.entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: CiSourceRelService
 * @Author: ztl
 * @Date: 2021-08-16
 * @Version: 1.0
 * @description:数据源和对象关联关系
 */
public interface CiSourceRelService {
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 新增数据源和对象关联关系
     */
    boolean addCiSourceRel(CiSourceRel ciSourceRel);
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 获取数据源和对象关联关系列表
     */
    PageResult getCiSourceRelList(String sourceId, String ciTypeId, String ciName);
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 根据ID删除数据源和对象关联关系
     */
    boolean deleteCiSourceRel(String ids);
    /**
     * @Author: tzq
     * date: 2021-08-18
     * @description: 获取数据源和对象关联关系数据
     */
    Map<String,Object> getCiSourceRelInfo(String dataSource,String ciTypeId,String ciCode);
	Map<String, Object> addCiSourceRelByPerPush(String dataSource, String ciTypeId, String ciCode,String domainId);
}
