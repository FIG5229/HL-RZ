package com.integration.generator.dao;

import com.integration.generator.entity.IomCampCzry;
import com.integration.generator.entity.IomCampCzryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampCzryMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 用户管理
*/
public interface IomCampCzryMapper {
    long countByExample(IomCampCzryExample example);

    int deleteByExample(IomCampCzryExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCampCzry record);

    int insertSelective(IomCampCzry record);

    List<IomCampCzry> selectByExample(IomCampCzryExample example);

    IomCampCzry selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCampCzry record, @Param("example") IomCampCzryExample example);

    int updateByExample(@Param("record") IomCampCzry record, @Param("example") IomCampCzryExample example);

    int updateByPrimaryKeySelective(IomCampCzry record);

    int updateByPrimaryKey(IomCampCzry record);
}