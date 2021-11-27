package com.integration.generator.dao;

import com.integration.generator.entity.IomCiKpiType;
import com.integration.generator.entity.IomCiKpiTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiKpiTypeMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标大类管理
*/
public interface IomCiKpiTypeMapper {
    long countByExample(IomCiKpiTypeExample example);

    int deleteByExample(IomCiKpiTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiKpiType record);

    int insertSelective(IomCiKpiType record);

    List<IomCiKpiType> selectByExample(IomCiKpiTypeExample example);

    IomCiKpiType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiKpiType record, @Param("example") IomCiKpiTypeExample example);

    int updateByExample(@Param("record") IomCiKpiType record, @Param("example") IomCiKpiTypeExample example);

    int updateByPrimaryKeySelective(IomCiKpiType record);

    int updateByPrimaryKey(IomCiKpiType record);
}