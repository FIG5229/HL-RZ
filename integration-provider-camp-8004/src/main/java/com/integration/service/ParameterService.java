package com.integration.service;

import com.integration.entity.Parameter;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service
* @ClassName: ParameterService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 系统参数
*/
public interface ParameterService {
	
	/**
	 * 添加参数
	 * @param parameter
	 * @return
	 */
	public Parameter addParameter(Parameter parameter);
	
	
	/**
	 * 删除参数
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
	
	
	/**
	 * 分页查询参数列表
	 * @param search
	 * @param pageCount
	 * @param pageIndex
	 * @return
	 */
	public List<Parameter> findParameter(String search,Integer pageCount,Integer pageIndex);
	
	
	/**
	 * 查询参数总数
	 * @return
	 */
	public int findParameterCount(String search);
	
	
	/**
	 * 根据ID查询参数信息
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
	Map<String, Object> findParByCode(String code);
}
