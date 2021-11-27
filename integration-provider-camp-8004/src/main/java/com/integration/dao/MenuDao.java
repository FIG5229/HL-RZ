package com.integration.dao;

import com.integration.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: MenuDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 菜单管理
*/
@Mapper
public interface MenuDao {

	/**
	 * 查询菜单集合
	 * @return
	 */
	List<Menu> selectMenuList();

	/**
	 * 查询菜单集合
	 * @return
	 */
	List<Map> selectMenuListMap();


	/**
	 * 查询菜单集合,返回叶子节点
	 * @return
	 */
	List<Map> selectMenuEndLevleListMap();

	/**
	 * 根据id删除菜单
	 * @param gncdDm
	 * @return
	 */
    int deleteMenuByKey(@Param("id")String gncdDm);
    
    /**
     * 新增菜单
     * @param record
     * @return
     */
    int insertMenu(Menu record);
    
    /**
     * 根据id查询菜单
     * @param gncdDm
     * @return
     */
    Menu selectMenuByKey(@Param("id")Integer gncdDm);
    
    
    /**
     * 根据roleid查询菜单
     * @return
     */
    List<Menu> selectMenuByRoleid(String roleid);
    
    /**
     * 更新菜单
     * @param record
     * @return
     */
    int  updateMenu(Menu record);

	void updateCustomerMenuName(@Param("menu") Menu menu);
	
	void updateCampPermitCustomMenuName(@Param("menuName")String menuName,@Param("menuId")String menuId);
    
    /**
     * 根据用户角色查询菜单集合
     * @param czryId
     * @return
     */
	List<Menu> selectMenuListByCzryRole(@Param("id")String czryId,@Param("type")String type);
	
	  /**
     * 根据用户角色查询菜单集合(如果是超级管理员)
     * @return
     */
	List<Menu> selectMenuListByCzryRoleToSuperAdmin(@Param("type")String type);

	/**
	 * 查询自定义菜单
	 * @return
	 * @param token
	 */
	List<Map> getCustMenuListMap(@Param("token") String token);

	/**
	 * 删除菜单角色关联表的数据
	 */
	void deleteRoleMenuByid(@Param("gncd_dm")String gncd_dm);
	
	/**
	 * 查询是否有子菜单
	 */
	List<Map<String,Object>> getGncdBySjgncd (@Param("gncd_dm")String gncd_dm);


	/**
	 * 根据用户角色查询菜单集合，不区分菜单类型（业务菜单，基础管理菜单）
	 * @param czryId
	 * @return
	 */
	List<Map> getMenuListByCzryId(@Param("id")String czryId);

	/**
	 * 根据用户角色查询菜单集合，不区分菜单类型（业务菜单，基础管理菜单）
	 * @param czryId
	 * @return
	 */
	List<Map> getMenuListByCzryId(@Param("id")String czryId, @Param("menuName")String menuName);

	/**
	 * 根据id查询菜单
	 * 返回list<Map>
	 * @param menuIds
	 * @return
	 */
	List<Map> getMenuListByIds(@Param("menuIds") String[] menuIds);


	/**
	 * 获取所有可用菜单
	 * @return
	 */
	List<String> getDisplayDmList();

}