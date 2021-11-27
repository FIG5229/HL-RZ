package com.integration.service;

import com.integration.generator.entity.IomCiRltLine;
import com.integration.generator.entity.IomCiRltNode;
import com.integration.generator.entity.IomCiRltNodeCdt;
import com.integration.generator.entity.IomCiRltRule;

import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.service
* @ClassName: RltRuleService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 关系管理
*/
public interface RltRuleService {
	/**
	 * 保存关系遍历
	 * 
	 * @param iomCiRltRule
	 * @param iomCiRltNodes
	 * @param iomCiRltLines
	 * @param iomCiRltNodeCdts
	 * @return
	 */
	public String save(IomCiRltRule iomCiRltRule, List<IomCiRltNode> iomCiRltNodes, List<IomCiRltLine> iomCiRltLines,
			List<IomCiRltNodeCdt> iomCiRltNodeCdts);

	
    
	public void iomCiRltNodesInsertBatch(List<IomCiRltNode> iomCiRltNodes);

	

	public void iomCiRltNodeCdtsInsertBatch(List<IomCiRltNodeCdt> iomCiRltNodeCdts);
	
	

	public void iomCiRltLineInsertBatch(List<IomCiRltLine> iomCiRltLines);

	

	public Integer clearData(String id);

	

	/**
	 * 删除关系遍历
	 * 
	 * @param id
	 * @return
	 */
	public Integer delete(String id);
	
		
	

	/**
	 * 获取关系遍历详情
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> get(String id);

	

	/**
	 * 查询关系便利列表
	 * 
	 * @param rltName
	 * @return
	 */
	public List<Map<String,Object>> getList(String rltName);
		
	
	
	public Object saveOrUpdateDataExtractionRules(Map<String, Object> map);



	List<Map<String, Object>> getDataExtractionRulesByruleId(String ruleId);



	List<Map<String, Object>> getItemDataAllByruleId(String ruleId);
	
	public List<String> getCiRelByLineBm(String lineBm);

}
