package com.integration.generator.dao;

import com.integration.generator.entity.IomCampMenuRes;
import com.integration.generator.entity.IomCampMenuResExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampMenuResMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单管理
*/
public interface IomCampMenuResMapper {
    long countByExample(IomCampMenuResExample example);

    int deleteByExample(IomCampMenuResExample example);

    int deleteByPrimaryKey(String resDm);

    int insert(IomCampMenuRes record);

    int insertSelective(IomCampMenuRes record);

    List<IomCampMenuRes> selectByExample(IomCampMenuResExample example);

    IomCampMenuRes selectByPrimaryKey(String resDm);

    int updateByExampleSelective(@Param("record") IomCampMenuRes record, @Param("example") IomCampMenuResExample example);

    int updateByExample(@Param("record") IomCampMenuRes record, @Param("example") IomCampMenuResExample example);

    int updateByPrimaryKeySelective(IomCampMenuRes record);

    int updateByPrimaryKey(IomCampMenuRes record);
}