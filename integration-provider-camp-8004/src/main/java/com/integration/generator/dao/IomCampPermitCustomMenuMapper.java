package com.integration.generator.dao;

import com.integration.entity.CustMenuEntity;
import com.integration.generator.entity.IomCampPermitCustomMenu;
import com.integration.generator.entity.IomCampPermitCustomMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampPermitCustomMenuMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 自定义菜单管理
*/
public interface IomCampPermitCustomMenuMapper {
    long countByExample(IomCampPermitCustomMenuExample example);

    int deleteByExample(IomCampPermitCustomMenuExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCampPermitCustomMenu record);

    int insertSelective(IomCampPermitCustomMenu record);

    List<IomCampPermitCustomMenu> selectByExample(IomCampPermitCustomMenuExample example);

    IomCampPermitCustomMenu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCampPermitCustomMenu record, @Param("example") IomCampPermitCustomMenuExample example);

    int updateByExample(@Param("record") IomCampPermitCustomMenu record, @Param("example") IomCampPermitCustomMenuExample example);

    int updateByPrimaryKeySelective(IomCampPermitCustomMenu record);

    int updateByPrimaryKey(IomCampPermitCustomMenu record);

    boolean deleteCustomMenu(@Param("menuId") String menuId,@Param("userId") String userId);

    List<IomCampPermitCustomMenu> selectByUserId(@Param("userId") String userId);

    Integer getMaxSortNum(@Param("cjrId")String userId,@Param("pMenuId") String pMenuId);

    boolean addCustMenu(@Param("custMenuEntity") CustMenuEntity custMenuEntity);

    boolean updateCustMenu(@Param("custMenuEntity") CustMenuEntity custMenuEntity);

    Integer deleteCustMenu(String id);
}