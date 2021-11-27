
package com.integration.dao;

import com.integration.entity.Rel;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-11-28 09:27:13
 * @version 1.0
 */
@Mapper
public interface RelDao {

	/**
	 * 分页查询
	 * 
	 * @param params
	 * @return
	 */
	public List<Rel> getAllPage(HashMap<Object, Object> params);

	/**
	 * 查询总数
	 * 
	 * @param params
	 * @return
	 */
	public int getAllCount(HashMap<Object, Object> params);

	/**
	 * 查询列表
	 * 
	 * @param params
	 * @return
	 */
	public List<Rel> getAllList(Map<String, String> params);
	/**
	 * 查询列表
	 *
	 * @param params
	 * @return
	 */
	public List<Rel> getAllListNoLike(Map<String, String> params);
	/**
	 * 查询单条
	 * 
	 * @return
	 */
	public Rel getInfo(Map<String, String> map);

	/**
	 * 修改单条记录
	 * 
	 * @param info
	 */
	public int updateInfo(Rel info);

	/**
	 * 新增单条记录
	 * 
	 * @param info
	 */
	public int insertInfo(Rel info);

	/**
	 * 删除单条记录
	 * 
	 * @param id
	 */
	public int deleteInfo(String id);

	/**
	 * 导出添加sheet名
	 * @param id
	 * @return
	 */
	public Rel findRelNameById(String id);

	public List<Rel> findRelName();

}