package com.integration.generator.dao;

import com.integration.generator.entity.IomCiKpi;
import com.integration.generator.entity.IomCiKpiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiKpiMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标管理
*/
public interface IomCiKpiMapper {
    long countByExample(IomCiKpiExample example);

    int deleteByExample(IomCiKpiExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiKpi record);

    int insertSelective(IomCiKpi record);

    List<IomCiKpi> selectByExample(IomCiKpiExample example);

    IomCiKpi selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiKpi record, @Param("example") IomCiKpiExample example);

    int updateByExample(@Param("record") IomCiKpi record, @Param("example") IomCiKpiExample example);

    int updateByPrimaryKeySelective(IomCiKpi record);

    int updateByPrimaryKey(IomCiKpi record);
}