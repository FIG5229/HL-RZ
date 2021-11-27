package com.integration.service;

import java.util.HashMap;
import java.util.List;
import com.integration.entity.CiKpiTpIItemInfo;

/**
 * @author hbr
 * @date 2018-12-14 03:49:58
 * @version 1.0
 */
 
 public interface CiKpiTpIItemService {
 	
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
 }