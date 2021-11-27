package com.integration.service;

import com.integration.entity.PageResult;
import com.integration.generator.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-12-11 05:47:33
 * @version 1.0
 */

public interface CiTypeRelService {

	/**
	 * 保存类别之间的关系
	 * 
	 * @return
	 */
	public Integer save(List list);

	/**
	 * 查询大类关系
	 * 
	 * @return
	 */
	public List<IomCiTypeRel> selectCiTypeRels();

	/**
	 * 查询大类
	 * 
	 * @return
	 */
	public List<IomCiType> selectCiTypes(String ciTypeMc);

	/**
	 * 查询关系
	 * 
	 * @return
	 */
	public List<IomCiRel> selectCiRels(String lineName);

	/**
	 * 查询关系类别
	 * 
	 * @return
	 */
	public IomCiTypeRel getIomCiTypeRel(String source_type_id, String target_type_id);
	
	public Integer getIomCiTypeRelBySourceIdAndTargetId(IomCiTypeFocusRel iomCiTypeFocusRel);

	/**
	 * 保存影响
	 * 
	 * @return
	 */
	public Integer insert(IomCiTypeFocusRel iomCiTypeFocusRel);
	
	
	public List<Map<String,Object>> getCiTypeRelByTypeId(String typeId);
	
	public Integer saveIomCiTypeRelDiagram(IomCiTypeRelDiagram IomCiTypeRelDiagram);

	IomCiTypeRelDiagram getIomCiTypeRelDiagram();
	
	/**
	 * 校验两个对象的编码是否符合可视化建模的规范
	 * @param sourceBm
	 * @param relId
	 * @param targetBm
	 * @return
	 */
	public boolean checkCiRel(String sourceBm,String relId,String targetBm);
	

	List<Map<String, Object>> selectRelTypeIds(String ciId, String typeId,String ciCode);

	PageResult selectRelCi(String ciId, String typeId1, String typeId2, String relId,String ciCode);

	List<Map<String, Object>> getCiDataRelByCiIdAndTypeId(String ciIds,String relId,String typeId,String identifier);

	List<Map<String, Object>> getCiDataRelBySourceCiIdAndTargetCiId(String sourceCiIds, String targetCiIds,
			String relId, String typeId, String identifier);

	List<Map<String, Object>> getCiDataRelByCiId(String ciId,String targetTypeId,String relId);

	List<Map<String, Object>> getCiDataRelBySourceIdAndTargetId(String sourceTypeId,String targetTypeId,String relId);

	List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndCiIds(Map<String, Object> itemMap);

	List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndCiIds(Map<String, Object> itemMap);

	List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndCiIdsUp(Map<String, Object> itemMap);

	List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndCiIdsDown(Map<String, Object> itemMap);

	List<Map<String, Object>> getCiDataRelBySelf(String typeId);

	List<Map<String, Object>> getCiTypeFocusRelBySelf(String typeId, String relId);

}