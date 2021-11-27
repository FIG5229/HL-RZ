package com.integration.service.impl;

import com.integration.dao.RelDao;
import com.integration.entity.Rel;
import com.integration.service.RelService;
import com.integration.utils.token.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-11-28 09:27:13
 *
 * @version 1.0
 */
@Transactional
@Service
public class RelServiceImpl implements RelService{

	@Resource
	private RelDao iRelDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
    @Override
	public List<Rel> getAllPage(HashMap<Object,Object> params){
		return iRelDao.getAllPage(params);
	}
	
	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	@Override 
	public int getAllCount(HashMap<Object,Object> params){
		return iRelDao.getAllCount(params);
	}
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	@Override 
	public List<Rel> getAllList(Map<String,String> params){
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		params.put("domainId", domainId);
		return iRelDao.getAllList(params);
	}
	/**
	 * 查询列表不模糊
	 * @param params
	 * @return
	 */
	@Override
	public List<Rel> getAllListNoLike(Map<String,String> params){
		return iRelDao.getAllListNoLike(params);
	}
	/**
	 * 查询单条
	 * @return
	 */
	@Override 
	public Rel getInfo(Map<String,String> map){
		return iRelDao.getInfo(map);
	}
	
	/**
	 * 修改单条记录
	 * @param info
	 */
	@Override 
	public int updateInfo(Rel info){
		return iRelDao.updateInfo(info);
	}
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Override 
	public int insertInfo(Rel info){
		 return iRelDao.insertInfo(info);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	@Override 
	public int deleteInfo(String id){
		return iRelDao.deleteInfo(id);
	}

	@Override
	public Rel findRelNameById(String id) {
		return iRelDao.findRelNameById(id);
	}

	@Override
	public List<Rel> findRelName(){
		return iRelDao.findRelName();
	}

	@Override
	public int importRelTobase(List<Rel> relList) {
		int a=0;
		if(relList != null){
			for (Rel rel : relList) {
				a=iRelDao.insertInfo(rel);
			}
		}
		return a;
	}

}