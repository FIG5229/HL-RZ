package com.integration.generator.dao;

import com.integration.generator.entity.IomCiDir;
import com.integration.generator.entity.IomCiDirExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiDirMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 目录管理
*/
public interface IomCiDirMapper {
    long countByExample(IomCiDirExample example);

    int deleteByExample(IomCiDirExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiDir record);

    int insertSelective(IomCiDir record);

    List<IomCiDir> selectByExample(IomCiDirExample example);

    IomCiDir selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiDir record, @Param("example") IomCiDirExample example);

    int updateByExample(@Param("record") IomCiDir record, @Param("example") IomCiDirExample example);

    int updateByPrimaryKeySelective(IomCiDir record);

    int updateByPrimaryKey(IomCiDir record);
}