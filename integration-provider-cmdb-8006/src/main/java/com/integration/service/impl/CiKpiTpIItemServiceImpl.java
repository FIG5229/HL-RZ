package com.integration.service.impl;

import com.integration.dao.CiKpiTpIItemDao;
import com.integration.entity.CiKpiTpIItemInfo;
import com.integration.service.CiKpiTpIItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author hbr
 * @date 2018-12-14 03:49:58
 *
 * @version 1.0
 */
@Transactional
@Service
public class CiKpiTpIItemServiceImpl implements CiKpiTpIItemService {

	@Autowired
	private CiKpiTpIItemDao iCiKpiTpIItemDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
    @Override
	public List<CiKpiTpIItemInfo> getAllPage(HashMap<Object,Object> params){
		
		return iCiKpiTpIItemDao.getAllPage(params);
	}
	
	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	@Override 
	public int getAllCount(HashMap<Object,Object> params){
		
		return iCiKpiTpIItemDao.getAllCount(params);
	}
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	@Override 
	public List<CiKpiTpIItemInfo> getAllList(HashMap<Object,Object> params){
		
		return iCiKpiTpIItemDao.getAllList(params);
	}
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	@Override 
	public CiKpiTpIItemInfo getInfo(HashMap<Object,Object> params){
	
		return iCiKpiTpIItemDao.getInfo(params);
	}
	
	/**
	 * 修改单条记录
	 * @param info
	 */
	@Override
	public int updateInfo(CiKpiTpIItemInfo info){

		return iCiKpiTpIItemDao.updateInfo(info);
	}
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Override 
	public int insertInfo(CiKpiTpIItemInfo info){
	
		return iCiKpiTpIItemDao.insertInfo(info);
	}
	
	/**
	 * 删除单条记录
	 * @param tplItemId
	 */
	@Override 
	public int deleteInfo(String tplItemId){
	
		return iCiKpiTpIItemDao.deleteInfo(tplItemId);
	}
	
}