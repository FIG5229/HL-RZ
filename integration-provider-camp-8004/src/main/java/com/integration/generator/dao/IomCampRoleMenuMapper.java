package com.integration.generator.dao;

import com.integration.generator.entity.IomCampRoleMenu;
import com.integration.generator.entity.IomCampRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampRoleMenuMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 角色菜单管理
*/
public interface IomCampRoleMenuMapper {
    long countByExample(IomCampRoleMenuExample example);

    int deleteByExample(IomCampRoleMenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCampRoleMenu record);

    int insertSelective(IomCampRoleMenu record);

    List<IomCampRoleMenu> selectByExample(IomCampRoleMenuExample example);

    IomCampRoleMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCampRoleMenu record, @Param("example") IomCampRoleMenuExample example);

    int updateByExample(@Param("record") IomCampRoleMenu record, @Param("example") IomCampRoleMenuExample example);

    int updateByPrimaryKeySelective(IomCampRoleMenu record);

    int updateByPrimaryKey(IomCampRoleMenu record);
}