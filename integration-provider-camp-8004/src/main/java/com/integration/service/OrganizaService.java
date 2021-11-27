package com.integration.service;

import com.integration.entity.Organiza;

import java.util.List;

/**
* @Package: com.integration.service
* @ClassName: OrganizaService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 初版组织机构管理
*/
public interface OrganizaService {
	/**
	 * 添加机构
	 * @param organiza
	 */
	public int addOrgan(Organiza organiza);

	/**
	 * 根据id删除机构
	 * @param id
	 * @return
	 */
	public int deleteOrgan(String id);


	/**
	 * 修改机构
	 * @param organiza
	 * @return
	 */
	public int updateOrgan(Organiza organiza);

	/**
	 * 查询最大级
	 * @return
	 */
	public List<Organiza> findAllMax();

	/**
	 * 查询下一级
	 * @return
	 */
	public List<Organiza> findNex(String sj_id);
}
