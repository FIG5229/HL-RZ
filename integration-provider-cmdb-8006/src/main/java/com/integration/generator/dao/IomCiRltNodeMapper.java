package com.integration.generator.dao;

import com.integration.generator.entity.IomCiRltNode;
import com.integration.generator.entity.IomCiRltNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiRltNodeMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiRltNodeMapper {
    long countByExample(IomCiRltNodeExample example);

    int deleteByExample(IomCiRltNodeExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiRltNode record);

    int insertSelective(IomCiRltNode record);

    List<IomCiRltNode> selectByExample(IomCiRltNodeExample example);

    IomCiRltNode selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiRltNode record, @Param("example") IomCiRltNodeExample example);

    int updateByExample(@Param("record") IomCiRltNode record, @Param("example") IomCiRltNodeExample example);

    int updateByPrimaryKeySelective(IomCiRltNode record);

    int updateByPrimaryKey(IomCiRltNode record);
}