package com.integration.dao;

import com.integration.entity.Czry;
import com.integration.entity.Organiza;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Package: com.integration.dao
* @ClassName: OrganizaDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 初版组织机构
*/
@Mapper
public interface OrganizaDao {

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
