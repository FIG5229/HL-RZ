package com.integration.generator.dao;

import com.integration.generator.entity.IomCiRel;
import com.integration.generator.entity.IomCiRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiRelMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 台账关系管理
*/
public interface IomCiRelMapper {
    long countByExample(IomCiRelExample example);

    int deleteByExample(IomCiRelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiRel record);

    int insertSelective(IomCiRel record);

    List<IomCiRel> selectByExample(IomCiRelExample example);

    IomCiRel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiRel record, @Param("example") IomCiRelExample example);

    int updateByExample(@Param("record") IomCiRel record, @Param("example") IomCiRelExample example);

    int updateByPrimaryKeySelective(IomCiRel record);

    int updateByPrimaryKey(IomCiRel record);
}