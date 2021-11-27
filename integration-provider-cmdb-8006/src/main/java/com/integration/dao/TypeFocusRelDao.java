package com.integration.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
* @Package: com.integration.dao
* @ClassName: TypeFocusRelDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 影响关系管理
*/
@Mapper
@Repository
public interface TypeFocusRelDao {
	/**
	 * 根据大类Id查询影响关系(我影响谁)
	 * @return
	 */
	List<Map<String, Object>> queryInfluenceRelByTypeId(@Param("ciTypeId") String ciTypeId) ;
	
	/**
	 * 根据大类Id查询影响关系(我影响谁)
	 * @return
	 */
	List<Map<String, Object>> queryInfluenceRelByTypeIdOptimize(@Param("ciTypeId") String ciTypeId,@Param("domainId")String domainId) ;
	
	/**
	 * 根据大类Id查询根因关系(谁影响我)
	 * @return
	 */
	List<Map<String,Object>> queryRootCauseRelByTypeIdOptimize(@Param("ciTypeId") String ciTypeId,@Param("domainId")String domainId);
	
	List<Map<String,Object>> queryInfluenceRelByTypeIdMutualInfluence(@Param("ciTypeId") String ciTypeId);
	
	/**
	 * 根据大类Id查询影响关系(我影响谁,自己影响自己)
	 * @return
	 */
	List<Map<String,Object>> queryInfluenceRelByTypeIdInfluenceSelf(@Param("ciTypeId") String ciTypeId,@Param("domainId")String domainId);
}
