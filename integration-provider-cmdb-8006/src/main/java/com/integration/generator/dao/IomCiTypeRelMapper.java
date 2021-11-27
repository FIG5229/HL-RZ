package com.integration.generator.dao;

import com.integration.generator.entity.IomCiTypeRel;
import com.integration.generator.entity.IomCiTypeRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiTypeRelMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiTypeRelMapper {
    long countByExample(IomCiTypeRelExample example);

    int deleteByExample(IomCiTypeRelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiTypeRel record);

    int insertSelective(IomCiTypeRel record);

    List<IomCiTypeRel> selectByExample(IomCiTypeRelExample example);

    IomCiTypeRel selectByPrimaryKey(@Param("id") String id,@Param("domainId") String domainId);

    int updateByExampleSelective(@Param("record") IomCiTypeRel record, @Param("example") IomCiTypeRelExample example);

    int updateByExample(@Param("record") IomCiTypeRel record, @Param("example") IomCiTypeRelExample example);

    int updateByPrimaryKeySelective(IomCiTypeRel record);

    int updateByPrimaryKey(IomCiTypeRel record);
}