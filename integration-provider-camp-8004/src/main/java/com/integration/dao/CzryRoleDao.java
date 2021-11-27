package com.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.integration.entity.CzryRole;
/**
* @Package: com.integration.dao
* @ClassName: CzryRoleDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 操作人员角色管理
*/
@Mapper
public interface CzryRoleDao {

	/**
	 * 添加用户角色关系
	 * @param cr
	 * @return
	 */
	public int addCzryRole(CzryRole cr);

	/**
	 * 删除用户角色关系
	 * @param id
	 * @return
	 */
	public int deleteCzryRole(@Param("id")int id);

	/**
	 * 查询所有用户角色
	 * @return
	 */
	public List<CzryRole> findCzryRoleAll();

	/**
	 * 根据用户ID查询所有用户角色
	 * @param czryId
	 * @return
	 */
	public List<CzryRole> findCzryRoleByCzryId(@Param("czryId")String czryId);

	/**
	 * 根据id查询用户角色
	 * @param id
	 * @return
	 */
	public CzryRole findCzryRoleById(@Param("id")Integer id);

	/**
	 * 更新用户角色
	 * @param cr
	 * @return
	 */
	public int updateCzryRole(CzryRole cr);

	/**
	 * 删除用户角色
	 */
	public int deleteCzryRoleByCzryId(@Param("id")String  CzryId);
	/**
	 * @Author: ztl
	 * date: 2021-08-17
	 * @description: 根据登录人ID获取所有配置角色
	 */
    List<String> getRoleIdByCzryId(@Param("id") String id);
}
