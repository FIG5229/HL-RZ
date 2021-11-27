package com.integration.generator.dao;

import com.integration.generator.entity.IomCiKpiClas;
import com.integration.generator.entity.IomCiKpiClasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiKpiClasMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标大类管理
*/
public interface IomCiKpiClasMapper {
    long countByExample(IomCiKpiClasExample example);

    int deleteByExample(IomCiKpiClasExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiKpiClas record);

    int insertSelective(IomCiKpiClas record);

    List<IomCiKpiClas> selectByExample(IomCiKpiClasExample example);

    IomCiKpiClas selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiKpiClas record, @Param("example") IomCiKpiClasExample example);

    int updateByExample(@Param("record") IomCiKpiClas record, @Param("example") IomCiKpiClasExample example);

    int updateByPrimaryKeySelective(IomCiKpiClas record);

    int updateByPrimaryKey(IomCiKpiClas record);
}