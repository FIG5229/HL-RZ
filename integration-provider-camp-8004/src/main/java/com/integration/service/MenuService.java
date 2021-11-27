package com.integration.service;

import java.util.List;
import java.util.Map;

import com.integration.entity.Menu;
import com.integration.entity.MenuRes;
/**
* @Package: com.integration.service
* @ClassName: MenuService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 菜单管理
*/
public interface MenuService {
	//层级菜单部分
	/**
	 * 查询层级菜单
	 * @return
	 */
    List<Menu> selectMenuList();

    /**
     * 查询层级菜单
     * @return
     */
    List<Map> selectMenuListMap();

    /**
     * 查询所有菜单集合
     */
    List<Map> selectMenuEndLevleListMap();

    /**
     * 根据用户角色查询菜单
     * @param CzryId
     * @return
     */
    List<Menu> selectMenuListByCzryRole(String CzryId,String type);
    
    /**
     * 查询菜单集合
     * @param menuList
     * @return
     */
    List<Menu> selectMenuListAll(List<Menu> menuList);

    /**
     * 查询自定义菜单
     * @return
     */
    List<Map> getCustMenuListMap();
    
    /**
     * 删除菜单
     * @param gncdDm
     * @return
     */
    int deleteMenuByKey(String gncdDm);
    
    /**
     * 新增菜单
     * @param menu
     * @return
     */
    int insertMenu(Menu menu);
    
    /**
     * 根据id查询菜单
     * @param gncdDm
     * @return
     */
    Menu selectMenuByKey(Integer gncdDm);
      
    /**
     * 更新菜单
     * @param menu
     * @return
     */
    int  updateMenu(Menu menu);
    
    //资源部分
    /**
     * 根据菜单选项查询菜单资源集合
     * @param gncdDm
     * @return
     */
    List<MenuRes> selectMenuResListByDm(String gncdDm,String search);
	
    /**
     * 删除菜单资源
     * @param resDm
     * @return
     */
    int deleteMenuResByKey(Integer resDm);
    
    /**
     * 新增菜单
     * @param menuRes
     * @return
     */
    int insertMenuRes(MenuRes menuRes);
    
    /**
     * 查询菜单资源
     * @param resDm
     * @return
     */
    MenuRes selectMenuResByKey(Integer resDm);
    
    /**
     * 更新菜单资源
     * @param menuRes
     * @return
     */
    int  updateMenuRes(MenuRes menuRes);
    
    /**
     * 根据id查询菜单资源
     * @param roleId
     * @return
     */
    Map MenuRoleAll(String roleId);

	List<Menu> selectMenuListByCzryRoleToSuperAdmin(String type);


    /**
     * 根据用户角色查询菜单，不区分菜单类型（业务菜单，基础管理菜单）
     * @param czryId
     * @return
     */
    List<Map> getMenuListByCzryId(String czryId);

    /**
     * 根据用户角色查询菜单，不区分菜单类型（业务菜单，基础管理菜单）
     * @param czryId
     * @return
     */
    List<Map> getMenuListByCzryId(String czryId, String menuName);

    /**
     * 查询菜单集合
     * @param mapList
     * @return
     */
    List<Map> selectMenuListAllHump(List<Map> mapList);

    /**
     * 根据id查询菜单
     * 返回list<Map>
     * @param menuIds
     * @return
     */
    List<Map> getMenuListByIds(String[] menuIds);

    String createMenuStr();
}
