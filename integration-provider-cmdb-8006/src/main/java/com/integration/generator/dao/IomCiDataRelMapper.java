package com.integration.generator.dao;

import com.integration.generator.entity.IomCiDataRel;
import com.integration.generator.entity.IomCiDataRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiDataRelMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 关系管理
*/
public interface IomCiDataRelMapper {
    long countByExample(IomCiDataRelExample example);

    int deleteByExample(IomCiDataRelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiDataRel record);

    int insertSelective(IomCiDataRel record);

    List<IomCiDataRel> selectByExample(IomCiDataRelExample example);

    IomCiDataRel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiDataRel record, @Param("example") IomCiDataRelExample example);

    int updateByExample(@Param("record") IomCiDataRel record, @Param("example") IomCiDataRelExample example);

    int updateByPrimaryKeySelective(IomCiDataRel record);

    int updateByPrimaryKey(IomCiDataRel record);
}