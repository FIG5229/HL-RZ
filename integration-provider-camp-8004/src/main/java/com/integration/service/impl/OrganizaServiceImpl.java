package com.integration.service.impl;


import com.integration.dao.OrganizaDao;
import com.integration.entity.Organiza;
import com.integration.service.OrganizaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @Package: com.integration.service.impl
* @ClassName: OrganizaServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 初版组织架构
*/
@Transactional
@Service
public class OrganizaServiceImpl implements OrganizaService {

	@Resource
	private OrganizaDao organizaDao;

	/**
	 * 添加机构
	 * @param organiza
	 */
	@Override
	public int addOrgan(Organiza organiza) {
		return organizaDao.addOrgan(organiza);
	}

	/**
	 * 根据id删除机构
	 * @param id
	 * @return
	 */
	@Override
	public int deleteOrgan(String id) {
		return organizaDao.deleteOrgan(id);
	}

	/**
	 * 修改机构
	 * @param organiza
	 * @return
	 */
	@Override
	public int updateOrgan(Organiza organiza) {
		return organizaDao.updateOrgan(organiza);
	}

	/**
	 * 查询最大级
	 * @return
	 */
	@Override
	public List<Organiza> findAllMax() {
		return organizaDao.findAllMax();
	}

	/**
	 * 查询下一级
	 * @return
	 */
	@Override
	public List<Organiza> findNex(String sj_id) {
		return organizaDao.findNex(sj_id);
	}
}
