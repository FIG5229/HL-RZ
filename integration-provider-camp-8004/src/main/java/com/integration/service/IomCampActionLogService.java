package com.integration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integration.entity.IomCampActionInfo;
import com.integration.entity.PageResult;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2019-01-17 05:39:39
 * @version 1.0
 */
 
 public interface IomCampActionLogService {
 	
 	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public List<IomCampActionInfo> getAllPage(Map<String, Object> params);

	/**
	 * 分页查询新
	 * @param params
	 * @return
	 */
	public PageResult getAllPageNew(Map<String, Object> params);
	
	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	public int getAllCount(Map<String, Object> params);
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	public List<IomCampActionInfo> getAllList(HashMap<Object,Object> params);
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	public IomCampActionInfo getInfo(HashMap<Object,Object> params);
	
		/**
	 * 修改单条记录
	 * @param info
	 */
	public void updateInfo(IomCampActionInfo info);
	
		/**
	 * 新增单条记录
	 * @param info
	 */
	public void insertInfo(IomCampActionInfo info);
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	public void deleteInfo(int id);
 }