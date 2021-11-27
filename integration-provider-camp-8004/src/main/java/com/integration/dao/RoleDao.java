package com.integration.dao;

import com.integration.entity.Role;
import com.integration.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: RoleDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 角色管理
*/
@Mapper
public interface RoleDao {
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public int addRole(Role role);
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	public int deleteRole(@Param("id")Long id);
	
	/**
	 * 根据角色名称查询角色列表
	 * @param role_mc
	 * @return
	 */
	public List<Role> findRoleAll(@Param("role_mc")String role_mc,@Param("name")String name);
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);
	
	/**
	 * 删除角色菜单
	 * @param roleId
	 * @return
	 */
	public int deleteRoleMenu(@Param("roleId")Long roleId);
	
	/**
	 * 删除人员角色
	 * @param roleId
	 * @return
	 */
	public int deleteCzryRole(@Param("roleId")Long roleId);
	
	
	/**
	 * 根据角色ID查询角色的菜单列表
	 * @param roleId
	 * @return
	 */
	public List<RoleMenu> findRoleMenuList(@Param("roleId")Long roleId);
	
	/**
	 * 添加角色菜单
	 * @param roleMenu
	 * @return
	 */
	public int addRoleMenu(RoleMenu roleMenu);
	
	/**
	 * 根据角色ID获取角色信息
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> findRoleById(Long id);

	/**
	 * 根据名称查询是否有数据
	 * @param name
	 * @return
	 */
	int getRoleNameBymc(@Param("name") String name);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Role getRoleById(@Param("id") String id);
	
}
