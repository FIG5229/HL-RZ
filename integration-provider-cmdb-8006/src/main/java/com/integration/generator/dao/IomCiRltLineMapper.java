package com.integration.generator.dao;

import com.integration.generator.entity.IomCiRltLine;
import com.integration.generator.entity.IomCiRltLineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiRltLineMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiRltLineMapper {
    long countByExample(IomCiRltLineExample example);

    int deleteByExample(IomCiRltLineExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiRltLine record);

    int insertSelective(IomCiRltLine record);

    List<IomCiRltLine> selectByExample(IomCiRltLineExample example);

    IomCiRltLine selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiRltLine record, @Param("example") IomCiRltLineExample example);

    int updateByExample(@Param("record") IomCiRltLine record, @Param("example") IomCiRltLineExample example);

    int updateByPrimaryKeySelective(IomCiRltLine record);

    int updateByPrimaryKey(IomCiRltLine record);
}