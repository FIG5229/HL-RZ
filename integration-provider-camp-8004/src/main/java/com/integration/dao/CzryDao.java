package com.integration.dao;

import java.util.List;
import java.util.Map;

import com.integration.entity.Org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.integration.entity.Czry;

/**
* @Package: com.integration.dao
* @ClassName: CzryDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 操作人员
*/
@Mapper
public interface CzryDao {

	/**
	 * 添加用户
	 * @param czry
	 * @return
	 */
	public int addCzry(Czry czry);
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	public int deleteCzry(@Param("id")Integer id);

	/**
	 * 更新用户
	 * @param czry
	 * @return
	 */
	public int updateCzry(Czry czry);

	/**
	 * 分页查询用户
	 */
	public List<Czry> findCzryListPage(@Param("org_id")String org_id,@Param("dept_id")String dept_id,@Param("search") String search, @Param("pageIndex") Integer pageIndex, @Param("pageCount") Integer pageCount);

	/**
	 * 查询用户总数
	 */
	public int findCzryListCount(@Param("org_id")String org_id,@Param("dept_id")String dept_id,@Param("search") String search);

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	public Czry findCzryById(@Param("id")String id);

	/**
	 * 登录
	 * @param czrDldm
	 * @param czryPass
	 * @return
	 */
	public Czry login(@Param("czryDldm")String czrDldm,@Param("czryPass")String czryPass);

	/**
	 * 修改状态
	 * @param id
	 * @param statu
	 * @return
	 */
	public int updateStatus(@Param("id")Integer id,@Param("statu")Integer statu);

	/**
	 * 停用用户
	 * @param id
	 * @return
	 */
	public int blockUpCzry(@Param("id")String id);

	/**
	 * 查询所有用户
	 * @param nameOrId
	 * @return
	 */
	public List<Map<String, Object>> getCzrysAll(@Param("nameOrId")String nameOrId);

	/**
	 * 根据操作人id，查询操作人
	 * @param listStr
	 * @return
	 */
	public List<Map<String, Object>> getCzrysNameById(@Param("listStr")String[] listStr);

	List<Org> getCzryListByDeptId(@Param("id") String id);
}
