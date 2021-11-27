package com.integration.generator.dao;

import com.integration.generator.entity.IomCiRltRule;
import com.integration.generator.entity.IomCiRltRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiRltRuleMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiRltRuleMapper {
    long countByExample(IomCiRltRuleExample example);

    int deleteByExample(IomCiRltRuleExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiRltRule record);

    int insertSelective(IomCiRltRule record);

    List<IomCiRltRule> selectByExample(IomCiRltRuleExample example);

    IomCiRltRule selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiRltRule record, @Param("example") IomCiRltRuleExample example);

    int updateByExample(@Param("record") IomCiRltRule record, @Param("example") IomCiRltRuleExample example);

    int updateByPrimaryKeySelective(IomCiRltRule record);

    int updateByPrimaryKey(IomCiRltRule record);
}