package com.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.integration.entity.MenuRes;
/**
* @Package: com.integration.dao
* @ClassName: MenuResDao
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单资源实体类
*/
@Mapper
public interface MenuResDao {
	/**
	 * 根据菜单id查询菜单资源
	 * @param gncdDm
	 * @return
	 */
	List<MenuRes> selectMenuResListByDm(@Param("id")String gncdDm,@Param("search") String search);
	
	/**
	 * 删除菜单资源
	 * @param resDm
	 * @return
	 */
    int deleteMenuResByKey(@Param("id")Integer resDm);
    
    /**
     * 新增菜单资源
     * @param menuRes
     * @return
     */
    int insertMenuRes(MenuRes menuRes);
    
    /**
     * 根据id查询菜单id
     * @param resDm
     * @return
     */
    MenuRes selectMenuResByKey(@Param("id")Integer resDm);
    
    /**
     * 更新菜单
     * @param menuRes
     * @return
     */
    int  updateMenuRes(MenuRes menuRes);

}
