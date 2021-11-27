package com.integration.service;

import com.integration.entity.Role;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service
* @ClassName: RoleService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 角色管理
*/
public interface RoleService {
	/**
	 * 新增角色
	 * @param role	
	 * @return
	 */
	public Role addRole(Role role,String[] list);
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	public int deleteRole(Long id);
	
	
	/**
	 * 根据名称模糊查询角色列表
	 * @param name
	 * @return
	 */
	public List<Role> findRoleAll(String name);
	
	
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public int updateRole(Role role);


	/**
	 * 修改角色菜单功能
	 * @param roleId
	 * @param cjr_id
	 * @param list
	 * @return
	 */
	public boolean updateRoleMenu(Long roleId,String cjr_id,String[] list,String[] cdIdList);


	/**
	 * 根据角色ID获取角色信息
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> findRoleById(Long id);

	/**
	 * 判断名称是否存在，存在返回1
	 * @param name
	 * @return
	 */
	int getRoleNameBymc(String name);

	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	Role getRoleById(String id);
}
