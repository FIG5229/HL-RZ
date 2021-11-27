package com.integration.dao;

import com.integration.entity.Parameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: ParameterDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 参数管理
*/
@Mapper
public interface ParameterDao {
	
	/**
	 * 添加参数
	 * @param parameter
	 * @return
	 */
	public int addParameter(Parameter parameter);
	
	
	/**
	 * 根据ID删除参数
	 * @param para_id
	 * @return
	 */
	public int deleteParameter(String para_id);
	
	
	/**
	 * 修改参数
	 * @param parameter
	 * @return
	 */
	public int updateParameter(Parameter parameter);
	
	
	/***
	 * 根据查询条件分页查询参数列表
	 * @param search
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 */
	public List<Parameter> findParameterPage(@Param("search")String search,@Param("pageIndex")Integer pageIndex, @Param("pageCount")Integer pageCount);
	
	
	/**
	 * 查询参数总数
	 * @return
	 */
	public int findParameterCount(@Param("search") String search);
	
	
	/**
	 * 根据参数ID查询参数信息
	 * @param para_id
	 * @return
	 */
	public Parameter findParameterById(int para_id);

	List<Parameter> findAllParam();

	/**
	 * 根据code获取参数信息
	 * @param code
	 * @return
	 */
	Map<String, Object> findParByCode(@Param("code") String code);
}
