package com.integration.generator.dao;

import com.integration.generator.entity.IomCiTypeRelDiagram;
import com.integration.generator.entity.IomCiTypeRelDiagramExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiTypeRelDiagramMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 视图
*/
public interface IomCiTypeRelDiagramMapper {
    long countByExample(IomCiTypeRelDiagramExample example);

    int deleteByExample(IomCiTypeRelDiagramExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiTypeRelDiagram record);

    int insertSelective(IomCiTypeRelDiagram record);

    List<IomCiTypeRelDiagram> selectByExample(IomCiTypeRelDiagramExample example);

    IomCiTypeRelDiagram selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiTypeRelDiagram record, @Param("example") IomCiTypeRelDiagramExample example);

    int updateByExample(@Param("record") IomCiTypeRelDiagram record, @Param("example") IomCiTypeRelDiagramExample example);

    int updateByPrimaryKeySelective(IomCiTypeRelDiagram record);

    int updateByPrimaryKey(IomCiTypeRelDiagram record);
}