package com.integration.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/** 
* @author 作者 :$Author$
* @version Revision
* 创建时间:2019年3月24日 下午8:31:47 
* id:Id
*/
@Mapper
public interface CzryConfigDao {
	
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public List<Map> findCzryConfigList(String userId);
	
	/**
	 * 新增
	 * @param map
	 */
	public int addCzryConfig(Map map);
	
	/**
	 * 修改
	 * @param map
	 * @return 
	 */
	public int updateCzryConfig(Map map);

}
