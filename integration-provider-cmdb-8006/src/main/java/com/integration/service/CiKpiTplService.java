package com.integration.service;

import java.util.HashMap;
import java.util.List;
import com.integration.entity.CiKpiTplInfo;
import com.integration.entity.Tpl_Item;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hbr
 * @date 2018-12-14 03:49:01
 * @version 1.0
 */
 public interface CiKpiTplService {
 	
 	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public List<CiKpiTplInfo> getAllPage(HashMap<Object, Object> params);
	
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
	public List<CiKpiTplInfo> getAllList(HashMap<Object, Object> params);
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	public CiKpiTplInfo getInfo(HashMap<Object, Object> params);
	
		/**
	 * 修改单条记录
	 * @param info
	 */
	public int updateInfo(CiKpiTplInfo info, String[] tplIdArr, String[] objIdArr, HttpServletRequest request);
	
		/**
	 * 新增单条记录
	 * @param info
	 */
	public int insertInfo(CiKpiTplInfo info, String[] kpiIdArr, String[] objIdArr, HttpServletRequest request);
	
	/**
	 * 删除单条记录
	 * @param tplId
	 */
	public int deleteInfo(String tplId);

	/**
	 * 获取模板和对象的组合体
	 * @return
	 */
	public List<Tpl_Item> getTpl_Item();
 }