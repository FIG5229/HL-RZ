package com.integration.service.impl;

import com.integration.dao.CzryRoleDao;
import com.integration.entity.CzryRole;
import com.integration.service.CzryRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
* @Package: com.integration.service.impl
* @ClassName: CzryRoleServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 人员角色关系
*/
@Transactional
@Service
public class CzryRoleServiceImpl implements CzryRoleService {
	
	@Resource
	private CzryRoleDao czryRoleMapper;
	
	/**
	 * 添加人员角色关系
	 * @param cr
	 * @return
	 */
	@Override
	public int addCzryRole(CzryRole cr) {
		return czryRoleMapper.addCzryRole(cr);
	}
	
	/**
	 * 删除人员角色关系
	 * @param id
	 * @return
	 */
	@Override
	public int deleteCzryRole(int id) {
		// TODO Auto-generated method stub

		return czryRoleMapper.deleteCzryRole(id);
	}
	
	/**
	 * 查询人员角色关系
	 * @return
	 */
	@Override
	public List<CzryRole> findCzryRoleAll() {
		// TODO Auto-generated method stub
		return czryRoleMapper.findCzryRoleAll();
	}
	
	/**
	 * 根据用户id查询角色关系
	 * @param czryId
	 * @return
	 */
	@Override
	public List<CzryRole> findCzryRoleByCzryId(String czryId) {
		return czryRoleMapper.findCzryRoleByCzryId(czryId);
	}
	
	/**
	 * 根据id查询角色关系
	 * @param id
	 * @return
	 */
	@Override
	public CzryRole findCzryRoleById(Integer id) {
		// TODO Auto-generated method stub
		return czryRoleMapper.findCzryRoleById(id);
	}
	
	/**
	 * 修改用户角色关系
	 * @param cr
	 * @return
	 */
	@Override
	public int updateCzryRole(CzryRole cr) {
		// TODO Auto-generated method stub
		return czryRoleMapper.updateCzryRole(cr);
	}

}
