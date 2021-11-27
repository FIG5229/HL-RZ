package com.integration.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
* @Package: com.integration.dao
* @ClassName: RltRuleDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Mapper
@Repository
public interface RltRuleDao {
     
	public List<Map<String,Object>> getIomCiRltLineByRuleId(@Param("ruleId")String ruleId,@Param("domainId")String domainId);
	
	public List<Map<String,Object>> getIomCiRltLineByRuleIdToStartTypeId(Map<String,Object> itemMap);
	
	public List<Map<String,Object>> getIomCiRltNodeCdtByNodeId(@Param("ruleId")String ruleId,@Param("domainId")String domainId);
	
	public List<Map<String,Object>> getIomCiRltRuleByRltName(Map<String,Object> itemMap);
	
	public List<String> getCiRelByLineBm(Map<String,Object> itemMap);
	
}
