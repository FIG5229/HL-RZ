package com.integration.dao;

import com.integration.generator.entity.IomCiTypeRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: CiTypeRelDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 对象关系
*/
@Mapper
@Repository
public interface CiTypeRelDao {

	/**
	 * 批量插入
	 *
	 * @return
	 */
	public Integer insertByBatch(List list);

	/**
	 * 获取失效的影响id
	 * @return
	 */
	public List<Long> selectInvalidFocus();
	
	/**
	 * 全部置为失效
	 * @return
	 */
	public Integer updateYXBZ();
	
	/**
	 * 根据source_type_id 和 target_type_id查询
	 * @return
	 */
	public IomCiTypeRel getSourceTypeIdAndTargetTypeId(@Param("source_type_id")String source_type_id, @Param("target_type_id")String target_type_id);
	
	
	public List<Map<String,Object>> getCiTypeRelByTypeId(@Param("typeId")String typeId,@Param("domainId") String domainId);
	
	/**
	 * 校验对象关系是否正确
	 * @return
	 */
	public Long checkCiType(@Param("sourceBm")String sourceBm,@Param("targetBm")String targetBm,@Param("relId")String relId);
	
	/**
	 * c
	 * @return
	 */
	public List<Map<String, Object>> selectRelTypeIds(@Param("ciId")String ciId,@Param("typeId")String typeId,@Param("ciCode") String ciCode);
	
	public List<String> selectCiIds(@Param("ciId")String ciId,@Param("typeId1")String typeId1,@Param("typeId2")String typeId2,@Param("relId")String relId,@Param("ciCode")String ciCode);
	
	/**
	 * 查询ci及其大类
	 * @param ids
	 * @return
	 */
	public List<Map<String, Object>> selectCiAndType(@Param("ids")List<String> ids);
	
	public List<Map<String, Object>> getCiDataRelByCiIdAndTypeId(Map<String, Object> itemMap);
	
	public List<Map<String,Object>> getWorldMapCiSingleRels(Map<String, Object> itemMap);
	
	public List<Map<String,Object>> getImpactAnalysisRelDatasOptimize(Map<String, Object> itemMap);
	
	public List<Map<String,Object>> getImpactAnalysisRelDatasSelf(Map<String, Object> itemMap);
	
	public List<Map<String,Object>> getWorldMapCiSingleRelsUp(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> getCiDataRelByCiId(@Param("ciId")String ciId,@Param("targetTypeId")String targetTypeId,@Param("relId")String relId);
	
	public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndCiIds(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndCiIdsDown(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndCiIds(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndCiIdsUp(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> getCiDataRelBySelf(@Param("typeId")String typeId);
	
	public List<Map<String, Object>> getCiTypeFocusRelBySelf(@Param("typeId")String typeId,@Param("relId")String relId);
	
	public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetId(@Param("sourceTypeId")String sourceTypeId,@Param("targetTypeId")String targetTypeId,@Param("relId")String relId);
}
