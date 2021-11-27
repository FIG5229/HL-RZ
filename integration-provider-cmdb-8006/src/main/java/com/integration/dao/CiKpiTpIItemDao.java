

package com.integration.dao;

import com.integration.entity.CiKpiTpIItemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author hbr
 * @date 2018-12-14 03:49:58
 * @version 1.0
 */
 @Mapper
 @Repository
 public interface CiKpiTpIItemDao {
 
 	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public List<CiKpiTpIItemInfo> getAllPage(HashMap<Object, Object> params);
	
	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	public int getAllCount(HashMap<Object, Object> params);
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	public List<CiKpiTpIItemInfo> getAllList(HashMap<Object, Object> params);
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	public CiKpiTpIItemInfo getInfo(HashMap<Object, Object> params);
	
		/**
	 * 修改单条记录
	 * @param info
	 */
	public int updateInfo(CiKpiTpIItemInfo info);
	
		/**
	 * 新增单条记录
	 * @param info
	 */
	public int insertInfo(CiKpiTpIItemInfo info);
	
	/**
	 * 删除单条记录
	 * @param tplItemId
	 */
	public int deleteInfo(String tplItemId);

	/**
	 * 模板id获取模板对象
	 * @param tplId
	 * @return
	 */
	public List<CiKpiTpIItemInfo> getTplById(String tplId);
 }