package com.integration.dao;

import com.integration.entity.CiSourceRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: CiSourceRelDao
 * @Author: ztl
 * @Date: 2021-08-16
 * @Version: 1.0
 * @description:数据源和对象关联关系
 */
@Mapper
@Repository
public interface CiSourceRelDao {
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 批量删除
     */
    boolean deleteCiSourceRelList(@Param("list") List<CiSourceRel> ciSourceRelList);
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 批量增加
     */
    boolean addCiSourceRelList(@Param("list") List<CiSourceRel> ciSourceRelList);
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 获取数据源和对象关联关系列表
     */
    List<CiSourceRel> getCiSourceRelList(@Param("sourceId") String sourceId,@Param("ciTypeId") String ciTypeId,@Param("ciName") String ciName,@Param("domainId") String domainId);
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 根据ID删除数据源和对象关联关系
     */
    boolean deleteCiSourceRel(@Param("list") List<String> idList);
    /**
     * @Author: tzq
     * date: 2021-08-18
     * @description: 获取数据源和对象关联关系数据
     */
    Map<String,Object> getCiSourceRelInfo(@Param("dataSource") String dataSource,@Param("ciTypeId") String ciTypeId,@Param("ciCode") String ciCode);
    /**
     * @Author: tzq
     * date: 2021-08-27
     * @description: 根据ID查询数据源关系数据
     */
    List<Map<String,Object>> getCiSourceRelInfoByIds(Map<String,Object> itemMap);
}
