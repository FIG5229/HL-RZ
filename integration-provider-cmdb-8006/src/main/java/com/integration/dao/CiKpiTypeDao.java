

package com.integration.dao;

import com.integration.entity.CiKpiThres;
import com.integration.entity.CiKpiTypeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author hbr
 * @date 2018-12-11 05:47:33
 * @version 1.0
 */
 @Mapper
 @Repository
 public interface CiKpiTypeDao {
 
 	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	public List<CiKpiTypeInfo> getAllPage(HashMap<Object, Object> params);
	
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
	public List<CiKpiTypeInfo> getAllList(HashMap<Object, Object> params);
	
	/**
	 * 查询单条
	 * @param params
	 * @return
	 */
	public CiKpiTypeInfo getInfo(HashMap<Object, Object> params);
	
		/**
	 * 修改单条记录
	 * @param info
	 */
	public int updateInfo(CiKpiTypeInfo info);
	
		/**
	 * 新增单条记录
	 * @param info
	 */
	public int insertInfo(CiKpiTypeInfo info);

	/**
	 * 新增多条记录
	 * @param list
	 */
	public int insertInfoList(List<CiKpiTypeInfo> list);
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	public int deleteInfo(String id);

	/**
	 * 获取所有模型对象
	 * @return
	 */
	public List<CiKpiTypeInfo> getAllInfo();

	/**
	 * 通过kpiid查询对象
	 */
	public List<CiKpiTypeInfo> getObjByKpiId(String kpiId);

	/**
	 * 通过kpiId删除对象组
	 * @param kpiId
	 * @return
	 */
	int deleteByKpiId(String kpiId);

	/**
	 * 根据Id获取对象id列表值
	 *
	 * @param id
	 * @return
	 */
	List<String> findById(@Param("id") String id);

	/**
	 * 根据id获取Thres列表
	 *
	 * @param id
	 * @return
	 */
	List<CiKpiThres> findByThres(@Param("id") String id);
	
	/**
	 * 根据大类ID查询是否有关联指标
	 *
	 * @return
	 */
	public int getHangKpiByCiId(@Param("ciTypeId") String ciTypeId);

	List<CiKpiTypeInfo> getAllCikpiTypeInfo();
}