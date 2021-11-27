package com.integration.generator.dao;

import com.integration.generator.entity.IomCampMenu;
import com.integration.generator.entity.IomCampMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampMenuMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单管理
*/
public interface IomCampMenuMapper {
    long countByExample(IomCampMenuExample example);

    int deleteByExample(IomCampMenuExample example);

    int deleteByPrimaryKey(String gncdDm);

    int insert(IomCampMenu record);

    int insertSelective(IomCampMenu record);

    List<IomCampMenu> selectByExample(IomCampMenuExample example);

    IomCampMenu selectByPrimaryKey(String gncdDm);

    int updateByExampleSelective(@Param("record") IomCampMenu record, @Param("example") IomCampMenuExample example);

    int updateByExample(@Param("record") IomCampMenu record, @Param("example") IomCampMenuExample example);

    int updateByPrimaryKeySelective(IomCampMenu record);

    int updateByPrimaryKey(IomCampMenu record);
}