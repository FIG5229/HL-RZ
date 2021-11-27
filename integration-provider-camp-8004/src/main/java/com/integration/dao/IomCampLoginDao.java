

package com.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.integration.entity.Group;
import com.integration.entity.IomCampLoginInfo;

/**
 * @author hbr
 * @date 2019-01-08 10:34:05
 * @version 1.0
 */
 @Mapper
 @Repository
 public interface IomCampLoginDao {
 
 	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public List<IomCampLoginInfo> getAllPage(HashMap<Object, Object> params);
	
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
	public List<IomCampLoginInfo> getAllList(HashMap<Object, Object> params);
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	public IomCampLoginInfo getInfo(HashMap<Object, Object> params);
	
	/**
	 * 修改单条记录
	 * @param info
	 */
	public void updateInfo(IomCampLoginInfo info);
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	public void insertInfo(IomCampLoginInfo info);
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	public void deleteInfo(int id);

	/**
	 * 通过用户id查询
	 * @param userId
	 * @return
	 */
	public List<IomCampLoginInfo> findByUserId(String userId);

	/**
	 * 查询日志
	 * @return
	 */
	public List<IomCampLoginInfo> findLogPage(Group group);

	/**
	 * 查询总数
	 */
	public List<IomCampLoginInfo> findAll();

    /**
     * 分页查询所有
     */
    public List<IomCampLoginInfo> findAllPage(Map<Object, Object> params);

    /**
     * 条件查询总数
     */
    public int findLogNum(Group group);

	/**
	 * 查询时间所有
	 */
	public List<IomCampLoginInfo> findTimeAllPage(Group group);

	/**
	 * 查询时间总数
	 */
	public int findTimeNum(Group group);
	/**
	 * 查询名称
	 */
	public List<IomCampLoginInfo> findMcPage(Group group);

	/**
	 * 查询名称总数
	 */
	public int findMcNum(String czry_mc);
}