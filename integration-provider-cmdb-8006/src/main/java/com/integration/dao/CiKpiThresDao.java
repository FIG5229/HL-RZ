package com.integration.dao;

import com.integration.entity.CiKpiThres;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @Package: com.integration.dao
* @ClassName: CiKpiThresDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标值
*/
@Mapper
public interface CiKpiThresDao {

    /**
     * 添加指标值
     *
     * @param ciKpiThres
     * @return
     */
    int insertInfo(CiKpiThres ciKpiThres);

    /**
     * 批量添加指标值
     *
     * @param list
     * @return
     */
    int insertInfoList(List<CiKpiThres> list);

    /**
     * 通过kpiid查询指标值
     * @param kpiId
     * @return
     */
    List<CiKpiThres> findByKpiId(String kpiId);

    /**
     * 通过kpiId删除
     * @param kpiId
     * @return
     */
    int deleteByKpiId(String kpiId);

    List<CiKpiThres> getAllCiKpiThres();
}
