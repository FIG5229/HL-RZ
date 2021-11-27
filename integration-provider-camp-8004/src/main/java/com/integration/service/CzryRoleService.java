package com.integration.service;

import java.util.List;

import com.integration.entity.CzryRole;
/**
* @Package: com.integration.service
* @ClassName: CzryRoleService
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 用户角色关系管理
*/
public interface CzryRoleService {
	/**
	 * 添加人员角色关系
	 * @param cr
	 * @return
	 */
	public int addCzryRole(CzryRole cr);
	
	/**
	 * 删除人员角色关系
	 * @param id
	 * @return
	 */
	public int deleteCzryRole(int id);
	
	/**
	 * 查询人员角色关系
	 * @return
	 */
	public List<CzryRole> findCzryRoleAll();
	
	/**
	 * 根据用户id查询角色关系
	 * @param czryId
	 * @return
	 */
	public List<CzryRole> findCzryRoleByCzryId(String czryId);
	
	/**
	 * 根据id查询角色关系
	 * @param id
	 * @return
	 */
	public CzryRole findCzryRoleById(Integer id);
	
	/**
	 * 修改用户角色关系
	 * @param cr
	 * @return
	 */
	public int updateCzryRole(CzryRole cr);

}
