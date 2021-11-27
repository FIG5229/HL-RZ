package com.integration.service.impl;

import com.integration.dao.RoleDao;
import com.integration.entity.Role;
import com.integration.entity.RoleMenu;
import com.integration.generator.dao.IomCampRoleMenuMapper;
import com.integration.generator.entity.IomCampRoleMenu;
import com.integration.service.RoleService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色service
 * 
 * @author dell
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleMapper;

	@Resource
	private IomCampRoleMenuMapper iomCampRoleMenuMapper;

	/**
	 * 添加角色和角色功能菜单权限
	 */
	@Override
	public Role addRole(Role role, String[] list) {
		role.setRole_dm(SeqUtil.nextId().toString());
		role.setCjsj(DateUtils.getDate());
		// 有效标识
		role.setYxbz("1");
		// 返回新添加的角色ID
		int add = roleMapper.addRole(role);
		// 如果添加角色成功,给此角色添加功能菜单
		if (add > 0) {
			if (null != list && list.length > 0) {
				for (int i = 0; i < list.length; i++) {
					RoleMenu m = new RoleMenu();
					m.setId(SeqUtil.nextId().toString());
					m.setRole_dm(role.getRole_dm());
					m.setGncd_dm(list[i]);
					m.setCjsj(DateUtils.getDate());
					m.setCjr_id(role.getCjr_id());
					roleMapper.addRoleMenu(m);
				}
			}
			return role;
		}
		return null;
	}

	/***
	 * 根据ID删除角色
	 */
	@Override
	public int deleteRole(Long id) {
		// 删除角色菜单关联表
		roleMapper.deleteRoleMenu(id);
		// 删除人员角色关联表
		roleMapper.deleteCzryRole(id);
		// 删除角色
		return roleMapper.deleteRole(id);
	}

	/**
	 * 根据角色名称查询角色列表
	 */
	@Override
	public List<Role> findRoleAll(String name) {
		return roleMapper.findRoleAll(name,"超级管理员");
	}

	/**
	 * 修改角色
	 */
	@Override
	public int updateRole(Role role) {
		role.setXgsj(DateUtils.getDate());
		return roleMapper.updateRole(role);
	}



	/**
	 * 修改角色的功能菜单
	 */
	@Override
	public boolean updateRoleMenu(Long roleId, String cjr_id, String[] list, String[] cdIdList) {
		// 先删除当前角色关联的功能菜单,然后重新添加
		roleMapper.deleteRoleMenu(roleId);
		if (null != list && list.length > 0) {
			for (int i = 0; i < list.length; i++) {
				IomCampRoleMenu iomCampRoleMenu = new IomCampRoleMenu();
				iomCampRoleMenu.setId(SeqUtil.nextId() + "");
				iomCampRoleMenu.setRoleDm(String.valueOf(roleId));
				iomCampRoleMenu.setGnflType(1);
				iomCampRoleMenu.setCjsj(new Date());
				iomCampRoleMenu.setCjrId(TokenUtils.getTokenUserId());
				iomCampRoleMenu.setGncdDm(list[i].toString());
				iomCampRoleMenuMapper.insert(iomCampRoleMenu);
			}
		}
		if (null != cdIdList && cdIdList.length > 0) {
			for (int i = 0; i < cdIdList.length; i++) {
				IomCampRoleMenu iomCampRoleMenu = new IomCampRoleMenu();
				iomCampRoleMenu.setId(SeqUtil.nextId() + "");
				iomCampRoleMenu.setRoleDm(String.valueOf(roleId));
				iomCampRoleMenu.setGnflType(2);
				iomCampRoleMenu.setGncdDm(cdIdList[i]);
				iomCampRoleMenu.setCjsj(new Date());
				iomCampRoleMenu.setCjrId(TokenUtils.getTokenUserId());
				iomCampRoleMenuMapper.insert(iomCampRoleMenu);
			}
		}
		return true;
	}

	/**
	 * 根据角色ID获取角色信息
	 */
	@Override
	public List<Map<String,Object>> findRoleById(Long id) {

		return roleMapper.findRoleById(id);
	}

	@Override
	public int getRoleNameBymc(String name) {
		return roleMapper.getRoleNameBymc(name);
	}

	@Override
	public Role getRoleById(String id) {
		return roleMapper.getRoleById(id);
	}

}
