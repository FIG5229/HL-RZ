package com.integration.service.impl;

import com.integration.dao.IomCampLoginDao;
import com.integration.entity.Group;
import com.integration.entity.IomCampLoginInfo;
import com.integration.mybatis.entity.Page;
import com.integration.service.IomCampLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hbr
 * @date 2019-01-08 10:34:05
 *
 * @version 1.0
 */
@Transactional
@Service
public class IomCampLoginServiceImpl implements IomCampLoginService {

	@Autowired
	private IomCampLoginDao iomCampLoginDao;
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
    @Override
	public List<IomCampLoginInfo> getAllPage(HashMap<Object,Object> params){
		
		return iomCampLoginDao.getAllPage(params);
	}
	
	/**
	 * 查询总数
	 * @param params
	 * @return
	 */
	@Override 
	public int getAllCount(HashMap<Object,Object> params){
		
		return iomCampLoginDao.getAllCount(params);
	}
	
	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	@Override 
	public List<IomCampLoginInfo> getAllList(HashMap<Object,Object> params){
		
		return iomCampLoginDao.getAllList(params);
	}
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	@Override 
	public IomCampLoginInfo getInfo(HashMap<Object,Object> params){
	
		return iomCampLoginDao.getInfo(params);
	}
	
	/**
	 * 修改单条记录
	 * @param info
	 */
	@Override 
	public void updateInfo(IomCampLoginInfo info){

		iomCampLoginDao.updateInfo(info);
	}
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Override 
	public void insertInfo(IomCampLoginInfo info){

		iomCampLoginDao.insertInfo(info);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	@Override 
	public void deleteInfo(int id){

		iomCampLoginDao.deleteInfo(id);
	}

	/**
	 * 通过用户id查询
	 * @param userId
	 * @return
	 */
	@Override
	public List<IomCampLoginInfo> findByUserId(String userId) {
		return iomCampLoginDao.findByUserId(userId);
	}

	/**
	 * 查询
	 * @param pageNum
	 * @param pageSize
	 * @param czry_mc
	 * @return
	 */
	@Override
	public List<IomCampLoginInfo> findLog(int pageNum, int pageSize,String czry_mc, String startTime, String endTime) {
		
		int pageIndex = pageNum;
		int pageCount = pageSize;
		Page page = new Page(pageIndex,pageCount);

		//分页查询
		Group group = new Group();
		group.setCzry_mc(czry_mc);
		group.setStartTime(startTime);
		group.setEndTime(endTime);
		group.setPage(page);

		List<IomCampLoginInfo> logList = iomCampLoginDao.findLogPage(group);
		return logList;
	}

	/**
	 * 查询所有分页
	 * @return
	 */
	@Override
	public List<IomCampLoginInfo> findAllPage(Map<Object, Object> params) {
		return iomCampLoginDao.findAllPage(params);
	}

	/**
	 * 条件查询总数
	 * @return
	 */
	@Override
	public int findLogNum(Group group) {
		return iomCampLoginDao.findLogNum(group);
	}

	/**
	 * 查询名称
	 * @param group
	 * @return
	 */
	@Override
	public List<IomCampLoginInfo> findMcPage(Group group) {
		return iomCampLoginDao.findMcPage(group);
	}

	/**
	 * 查询名称总数
	 * @return
	 */
	@Override
	public int findMcNum(String czry_mc) {
		return iomCampLoginDao.findMcNum(czry_mc);
	}

}