package com.integration.generator.dao;

import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiTypeMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 大类管理
*/
public interface IomCiTypeMapper {
    long countByExample(IomCiTypeExample example);

    int deleteByExample(IomCiTypeExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiType record);

    int insertSelective(IomCiType record);

    List<IomCiType> selectByExample(IomCiTypeExample example);

    IomCiType selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiType record, @Param("example") IomCiTypeExample example);

    int updateByExample(@Param("record") IomCiType record, @Param("example") IomCiTypeExample example);

    int updateByPrimaryKeySelective(IomCiType record);

    int updateByPrimaryKey(IomCiType record);
}