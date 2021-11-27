package com.integration.service.impl;

import com.integration.dao.CiKpiTypeDao;
import com.integration.entity.CiKpiThres;
import com.integration.entity.CiKpiTypeInfo;
import com.integration.service.CiKpiTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-12-11 05:47:33
 *
 * @version 1.0
 */
@Transactional
@Service
public class CiKpiTypeServiceImpl implements CiKpiTypeService {

	@Autowired
	private CiKpiTypeDao iCiKpiTypeDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
    @Override
	public List<CiKpiTypeInfo> getAllPage(HashMap<Object,Object> params){
		
		return iCiKpiTypeDao.getAllPage(params);
	}
	
	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	@Override 
	public int getAllCount(HashMap<Object,Object> params){
		
		return iCiKpiTypeDao.getAllCount(params);
	}
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	@Override 
	public List<CiKpiTypeInfo> getAllList(HashMap<Object,Object> params){
		
		return iCiKpiTypeDao.getAllList(params);
	}
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	@Override 
	public CiKpiTypeInfo getInfo(HashMap<Object,Object> params){
	
		return iCiKpiTypeDao.getInfo(params);
	}
	
	/**
	 * 修改单条记录
	 * @param info
	 */
	@Override 
	public int updateInfo(CiKpiTypeInfo info){
	
		return iCiKpiTypeDao.updateInfo(info);
	}
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Override 
	public void insertInfo(CiKpiTypeInfo info){
	
		iCiKpiTypeDao.insertInfo(info);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	@Override 
	public void deleteInfo(String id){
	
		iCiKpiTypeDao.deleteInfo(id);
	}

	@Override
	public List<String> findById(String id) {
		return iCiKpiTypeDao.findById(id);
	}

	@Override
	public List<CiKpiThres> findByThres(String id) {
		return iCiKpiTypeDao.findByThres(id);
	}
	
	@Override
	public int getHangKpiByCiId(String ciTypeId) {
		return iCiKpiTypeDao.getHangKpiByCiId(ciTypeId);
	}

}