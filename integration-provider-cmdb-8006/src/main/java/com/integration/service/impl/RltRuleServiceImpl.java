package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.integration.dao.CiTypeRelDao;
import com.integration.dao.DataRelDao;
import com.integration.dao.RltRuleDao;
import com.integration.entity.Type;
import com.integration.entity.TypeItem;
import com.integration.feign.CzryService;
import com.integration.feign.DictService;
import com.integration.generator.dao.IomCiRltLineMapper;
import com.integration.generator.dao.IomCiRltNodeCdtMapper;
import com.integration.generator.dao.IomCiRltNodeMapper;
import com.integration.generator.dao.IomCiRltRuleMapper;
import com.integration.generator.entity.*;
import com.integration.service.RltRuleService;
import com.integration.service.TypeItemService;
import com.integration.service.TypeService;
import com.integration.utils.SeqUtil;
import com.integration.utils.StringUtil;
import com.integration.utils.BaseTypeRelRuleUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Package: com.integration.service.impl
* @ClassName: RltRuleServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 关系管理
*/
@Service
@Transactional
public class RltRuleServiceImpl implements RltRuleService{

	@Resource
	private IomCiRltLineMapper iomCiRltLineMapper;

	@Resource
	private IomCiRltNodeCdtMapper iomCiRltNodeCdtMapper;

	@Resource
	private IomCiRltNodeMapper iomCiRltNodeMapper;

	@Resource
	private IomCiRltRuleMapper iomCiRltRuleMapper;
	
	@Resource
	private TypeService typeService;
	
	@Resource
	private CzryService czryService;
	
	@Resource
	private RltRuleDao rltRuleDao;
	
	@Resource
	private DataRelDao dataRelDao;
	
	@Resource
	private DictService dictService;
	
	@Resource
	private CiTypeRelDao ciTypeRelDao;
	
	@Autowired
    private TypeItemService typeItemService;
	

	/**
	 * 保存关系遍历
	 * 
	 * @param iomCiRltRule
	 * @param iomCiRltNodes
	 * @param iomCiRltLines
	 * @param iomCiRltNodeCdts
	 * @return
	 */
	@Override
	public String save(IomCiRltRule iomCiRltRule, List<IomCiRltNode> iomCiRltNodes, List<IomCiRltLine> iomCiRltLines,
			List<IomCiRltNodeCdt> iomCiRltNodeCdts) {
		if (iomCiRltRule != null) {
			String userId = TokenUtils.getTokenUserId();
			Date now = new Date();

			// 规则
			iomCiRltRule.setXgrId(userId);
			iomCiRltRule.setXgsj(now);
			if (StringUtils.isEmpty(iomCiRltRule.getId())) {
				iomCiRltRule.setId(SeqUtil.getId());
				iomCiRltRule.setCjrId(userId);
				iomCiRltRule.setCjsj(now);
				iomCiRltRule.setYxbz(1);
				iomCiRltRuleMapper.insertSelective(iomCiRltRule);
			} else {
				iomCiRltRuleMapper.updateByPrimaryKeySelective(iomCiRltRule);
			}

			String ruleId = iomCiRltRule.getId();

			//清理老数据，由于加入了事务，所以需要从这个方法里从新拿一个方法出来
			clearData(ruleId);
			
			// 节点
			if (iomCiRltNodes != null && iomCiRltNodes.size() > 0) {
				for (IomCiRltNode iomCiRltNode : iomCiRltNodes) {
					if (StringUtils.isEmpty(iomCiRltNode.getId())) {
						iomCiRltNode.setId(SeqUtil.getId());
					}
					iomCiRltNode.setRuleId(ruleId);
					iomCiRltNode.setCjsj(now);
					iomCiRltNode.setXgsj(now);
					iomCiRltNode.setYxbz(1);
				}
				iomCiRltNodesInsertBatch(iomCiRltNodes);
			}

			if (iomCiRltNodeCdts != null && iomCiRltNodeCdts.size() > 0) {
				for (IomCiRltNodeCdt iomCiRltNodeCdt : iomCiRltNodeCdts) {
					if (StringUtils.isEmpty(iomCiRltNodeCdt.getId())) {
						iomCiRltNodeCdt.setId(SeqUtil.getId());
					}
					iomCiRltNodeCdt.setCjsj(now);
					iomCiRltNodeCdt.setXgsj(now);
					iomCiRltNodeCdt.setYxbz(1);
				}
				iomCiRltNodeCdtsInsertBatch(iomCiRltNodeCdts);
			}

			// 线
			if (iomCiRltLines != null && iomCiRltLines.size() > 0) {
				for (IomCiRltLine iomCiRltLine : iomCiRltLines) {
					if (StringUtils.isEmpty(iomCiRltLine.getId())) {
						iomCiRltLine.setId(SeqUtil.getId());
					}
					iomCiRltLine.setRuleId(ruleId);
					iomCiRltLine.setCjsj(now);
					iomCiRltLine.setXgsj(now);
					iomCiRltLine.setYxbz(1);
				}
				iomCiRltLineInsertBatch(iomCiRltLines);
			}

			return ruleId;
		}
		return null;
	}
	@Override
	public void iomCiRltNodesInsertBatch(List<IomCiRltNode> iomCiRltNodes) {
		if (iomCiRltNodes != null && iomCiRltNodes.size() > 0) {
			for (IomCiRltNode iomCiRltNode : iomCiRltNodes) {
				iomCiRltNodeMapper.insertSelective(iomCiRltNode);
			}
		}
	}
	@Override
	public void iomCiRltNodeCdtsInsertBatch(List<IomCiRltNodeCdt> iomCiRltNodeCdts) {
		if (iomCiRltNodeCdts != null && iomCiRltNodeCdts.size() > 0) {
			for (IomCiRltNodeCdt iomCiRltNodeCdt : iomCiRltNodeCdts) {
				iomCiRltNodeCdtMapper.insertSelective(iomCiRltNodeCdt);
			}
		}
	}
	@Override
	public void iomCiRltLineInsertBatch(List<IomCiRltLine> iomCiRltLines) {
		if (iomCiRltLines != null && iomCiRltLines.size() > 0) {
			for (IomCiRltLine iomCiRltLine : iomCiRltLines) {
				iomCiRltLineMapper.insertSelective(iomCiRltLine);
			}
		}
	}
	@Override
	public Integer clearData(String id) {
		String ruleId = id;
		if (StringUtils.isNotEmpty(ruleId)) {
			// 节点
			IomCiRltNodeExample iomCiRltNodeExample = new IomCiRltNodeExample();
			iomCiRltNodeExample.createCriteria().andRuleIdEqualTo(ruleId);
			List<IomCiRltNode> oldNodes = iomCiRltNodeMapper.selectByExample(iomCiRltNodeExample);
			iomCiRltNodeMapper.deleteByExample(iomCiRltNodeExample);

			// 节点约束
			if (oldNodes != null && oldNodes.size() > 0) {
				List<String> nodeId = new ArrayList<String>();
				for (IomCiRltNode oldNode : oldNodes) {
					nodeId.add(oldNode.getId());
				}
				IomCiRltNodeCdtExample iomCiRltNodeCdtExample = new IomCiRltNodeCdtExample();
				iomCiRltNodeCdtExample.createCriteria().andNodeIdIn(nodeId);
				iomCiRltNodeCdtMapper.deleteByExample(iomCiRltNodeCdtExample);
			}

			// 线
			IomCiRltLineExample iomCiRltLineExample = new IomCiRltLineExample();
			iomCiRltLineExample.createCriteria().andRuleIdEqualTo(ruleId);
			iomCiRltLineMapper.deleteByExample(iomCiRltLineExample);
		}
		return 1;
	}

	/**
	 * 删除关系遍历
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Integer delete(String id) {
		String ruleId = id;
		if (StringUtils.isNotEmpty(ruleId)) {
			String userId = TokenUtils.getTokenUserId();
			Date now = new Date();
			// 规则
			IomCiRltRule iomCiRltRule = new IomCiRltRule();
			iomCiRltRule.setId(ruleId);
			iomCiRltRule.setXgrId(userId);
			iomCiRltRule.setXgsj(now);
			iomCiRltRule.setYxbz(0);
			iomCiRltRuleMapper.updateByPrimaryKeySelective(iomCiRltRule);

			// 节点
			IomCiRltNode iomCiRltNode = new IomCiRltNode();
			iomCiRltNode.setXgsj(now);
			iomCiRltNode.setYxbz(0);
			IomCiRltNodeExample iomCiRltNodeExample = new IomCiRltNodeExample();
			iomCiRltNodeExample.createCriteria().andRuleIdEqualTo(ruleId);
			List<IomCiRltNode> oldNodes = iomCiRltNodeMapper.selectByExample(iomCiRltNodeExample);
			iomCiRltNodeMapper.updateByExampleSelective(iomCiRltNode, iomCiRltNodeExample);

			// 节点约束
			if (oldNodes != null && oldNodes.size() > 0) {
				IomCiRltNodeCdt iomCiRltNodeCdt = new IomCiRltNodeCdt();
				iomCiRltNodeCdt.setXgsj(now);
				iomCiRltNodeCdt.setYxbz(0);
				List<String> nodeId = new ArrayList<String>();
				for (IomCiRltNode oldNode : oldNodes) {
					nodeId.add(oldNode.getId());
				}
				IomCiRltNodeCdtExample iomCiRltNodeCdtExample = new IomCiRltNodeCdtExample();
				iomCiRltNodeCdtExample.createCriteria().andNodeIdIn(nodeId);
				iomCiRltNodeCdtMapper.updateByExampleSelective(iomCiRltNodeCdt, iomCiRltNodeCdtExample);
			}

			// 线
			IomCiRltLine iomCiRltLine = new IomCiRltLine();
			iomCiRltLine.setXgsj(now);
			iomCiRltLine.setYxbz(0);
			IomCiRltLineExample iomCiRltLineExample = new IomCiRltLineExample();
			iomCiRltLineExample.createCriteria().andRuleIdEqualTo(ruleId);
			iomCiRltLineMapper.updateByExampleSelective(iomCiRltLine, iomCiRltLineExample);
		}
		return 1;
	}

	/**
	 * 获取规则详情数据---配置报表
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> get(String id) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		if (StringUtils.isNotEmpty(id)) {
			Map<String, Object> res = new HashMap<String, Object>();
			// 规则
			IomCiRltRule iomCiRltRule = iomCiRltRuleMapper.selectByPrimaryKey(id);
			Map<String, Object> iomCiRltRuleMap=new HashMap<String, Object>();
			try {
				iomCiRltRuleMap=bean2map(iomCiRltRule);
				Object resObject = czryService.findCzryByIdFeign(iomCiRltRule.getCjrId());
				if (resObject!=null) {
					JSONObject czry = JSONObject.parseObject(JSON.toJSONString(resObject));
					iomCiRltRuleMap.put("cjrName", czry.get("czry_mc"));

				}
				res.put("def", iomCiRltRuleMap);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 节点
			IomCiRltNodeExample iomCiRltNodeExample = new IomCiRltNodeExample();
			if(domainId==null) {
				iomCiRltNodeExample.createCriteria().andRuleIdEqualTo(id);
			}else {
				iomCiRltNodeExample.createCriteria().andRuleIdEqualTo(id).andDomainIdIn(Arrays.asList(domainId.split(",")));
			}
			
			List<IomCiRltNode> iomCiRltNodes = iomCiRltNodeMapper.selectByExample(iomCiRltNodeExample);
			List<Map<String, Object>> iomCiRltNodesList=new ArrayList<Map<String,Object>>();
			for(IomCiRltNode iomCiRltNode:iomCiRltNodes){
				try {
					String idStr=iomCiRltNode.getId();
					String nodeTypeId=iomCiRltNode.getNodeTypeId();
					Map<String, Object> map=bean2map(iomCiRltNode);
					Type type=typeService.findTypeById(nodeTypeId);
					map.put("ciTypeIcon", type.getCi_type_icon());
					map.put("ciTypeBm", type.getCi_type_bm());
					iomCiRltNodesList.add(map);
					IomCiRltNodeCdtExample iomCiRltNodeCdtExample = new IomCiRltNodeCdtExample();
					iomCiRltNodeCdtExample.createCriteria().andNodeIdEqualTo(idStr).andYxbzEqualTo(1).andDomainIdIn(Arrays.asList(domainId.split(",")));
					List<IomCiRltNodeCdt> iomCiRltNodeCdts = iomCiRltNodeCdtMapper.selectByExample(iomCiRltNodeCdtExample);
					map.put("nodeCdts", iomCiRltNodeCdts);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			res.put("nodes", iomCiRltNodesList);
			
			// 线
			List<Map<String, Object>> iomCiRltLineList=rltRuleDao.getIomCiRltLineByRuleId(id,domainId);
			for(Map<String, Object> map:iomCiRltLineList) {
				String rltId=(String) map.get("rltId");
				map.put("relId", rltId);
			}
			res.put("lines", iomCiRltLineList);
			return res;
		}
		return null;
	}

	/**
	 * 查询关系便利列表
	 * 
	 * @param rltName
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getList(String rltName) {
		String domainId = TokenUtils.getTokenDataDomainId();
		Map<String,Object> itemMap=new HashMap<String,Object>();
		if(rltName!=null && !"".equals(rltName)) {
	   	    itemMap.put("rltName", rltName);
	   	}else {
	   	    itemMap.put("rltName", "");
	   	}
		itemMap.put("userId", "");
		if(domainId!=null && !"".equals(domainId)) {
	   	    itemMap.put("domainId", domainId);
	   	}else {
	   	    itemMap.put("domainId", "");
	   	}
		return rltRuleDao.getIomCiRltRuleByRltName(itemMap);
	}
	@Override
	public Object saveOrUpdateDataExtractionRules(Map<String, Object> map) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		IomCiRltRule iomCiRltRule = new IomCiRltRule();
		Map<String, Object> mapData=(Map<String, Object>) map.get("def");
		List<Map<String, Object>> iomCiRltNodeList = (List<Map<String, Object>>) map.get("nodes");
		List<Map<String, Object>> iomCiRltLineList = (List<Map<String, Object>>) map.get("lines");
		List<IomCiRltNode> listIomCiRltNode=new ArrayList<IomCiRltNode>();
		List<IomCiRltLine> listIomCiRltLine=new ArrayList<IomCiRltLine>();
		try {
			if (mapData != null && mapData.size()>0) {
				String idStr=(String) mapData.get("id");
				if (idStr!=null && !"".equals(idStr)) {
					String cjrName=(String) mapData.get("cjrName");
					String validDesc=(String) mapData.get("validDesc");
					String cjrId=(String) mapData.get("cjrId");
					Integer rltType=(Integer) mapData.get("rltType");
					Integer domainIdInt=(Integer) mapData.get("domainId");
					String diagXml=(String) mapData.get("diagXml");
					Integer yxbz=(Integer) mapData.get("yxbz");
					String cjsj=(String) mapData.get("cjsj");
					String xgrId=(String) mapData.get("xgrId");
					String typeId=(String) mapData.get("typeId");
					String id=(String) mapData.get("id");
					String rltName=(String) mapData.get("rltName");
					String xgsj=(String) mapData.get("xgsj");
					String rltDesc=(String) mapData.get("rltDesc");
					Integer status=(Integer) mapData.get("status");
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date cjsjDate=sdf.parse(cjsj);
					Date xgsjDate=sdf.parse(xgsj);
					if(validDesc==null) {
						iomCiRltRule.setValidDesc("");
					}else {
						iomCiRltRule.setValidDesc(validDesc);
					}					
					iomCiRltRule.setCjrId(cjrId);
					iomCiRltRule.setRltType(rltType);
					iomCiRltRule.setDomainId(domainIdInt);
					if(diagXml==null) {
						iomCiRltRule.setDiagXml("");
					}else {
						iomCiRltRule.setDiagXml(diagXml);
					}					
					iomCiRltRule.setYxbz(yxbz);
					iomCiRltRule.setCjsj(cjsjDate);
					iomCiRltRule.setXgrId(xgrId);
					iomCiRltRule.setTypeId(typeId);
					iomCiRltRule.setId(id);
					iomCiRltRule.setRltName(rltName);
					iomCiRltRule.setXgsj(xgsjDate);
					if(rltDesc==null) {
						iomCiRltRule.setRltDesc("");
					}else {
						iomCiRltRule.setRltDesc(rltDesc);
					}
					
					iomCiRltRule.setStatus(status);
				}else {
					iomCiRltRule = convertMap2Bean(mapData,IomCiRltRule.class);
				}
				
			}
			listIomCiRltNode=convertListMap2ListBean(iomCiRltNodeList,IomCiRltNode.class);
			listIomCiRltLine=convertListMap2ListBean(iomCiRltLineList,IomCiRltLine.class);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		if (iomCiRltRule != null) {
			String userId = TokenUtils.getTokenUserId();
			Date now = new Date();

			// 规则
			iomCiRltRule.setXgrId(userId);
			iomCiRltRule.setXgsj(now);
			if (StringUtils.isEmpty(iomCiRltRule.getId())) {
				iomCiRltRule.setId(SeqUtil.getId());
				iomCiRltRule.setCjrId(userId);
				iomCiRltRule.setCjsj(now);
				iomCiRltRule.setYxbz(1);
				iomCiRltRule.setDomainId(Integer.valueOf(domainId==null?"-1":domainId));
				iomCiRltRule.setStatus(1);
				iomCiRltRuleMapper.insertSelective(iomCiRltRule);
			} else {
				iomCiRltRuleMapper.updateByPrimaryKeySelective(iomCiRltRule);
			}
            //规则ID
			String ruleId = iomCiRltRule.getId();
			//触发CI大类ID
			String typeId=iomCiRltRule.getTypeId();
			//清理老数据，由于加入了事务，所以需要从这个方法里从新拿一个方法出来
			clearData(ruleId);
			
			// 节点
			if (listIomCiRltNode != null && listIomCiRltNode.size() > 0) {
				for (IomCiRltNode iomCiRltNode : listIomCiRltNode) {
					if (StringUtils.isEmpty(iomCiRltNode.getId())) {
						iomCiRltNode.setId(SeqUtil.getId());
					}
					iomCiRltNode.setRuleId(ruleId);
					iomCiRltNode.setCjsj(now);
					iomCiRltNode.setXgsj(now);
					iomCiRltNode.setYxbz(1);
					iomCiRltNode.setRltType(1);
					iomCiRltNode.setTypeId(typeId);
					iomCiRltNode.setDomainId(Integer.valueOf(domainId==null?"-1":domainId));
					List iomCiRltNodeCdts =iomCiRltNode.getNodeCdts();
					if(iomCiRltNodeCdts!=null && iomCiRltNodeCdts.size()>0) {
						for(int i=0;i<iomCiRltNodeCdts.size();i++) {
							Map<String, Object> mapJson=(Map<String, Object>) iomCiRltNodeCdts.get(i);
							try {
								IomCiRltNodeCdt iomCiRltNodeCdt=new IomCiRltNodeCdt();
								String cjsj=(String) mapJson.get("cjsj");
								Integer stor=(Integer) mapJson.get("stor");
								String attrId=(String) mapJson.get("attrId");
								String cdtOp=(String) mapJson.get("cdtOp");
								String cdtVal=(String) mapJson.get("cdtVal");
								String id=(String) mapJson.get("id");
								String nodeId=(String) mapJson.get("nodeId");
								Integer domainId1=(Integer) mapJson.get("domainId");
								String xgsj=(String) mapJson.get("xgsj");
								Integer yxbz=(Integer) mapJson.get("yxbz");								
								if (StringUtils.isEmpty(id)) {
									iomCiRltNodeCdt.setId(SeqUtil.getId());
								}else {
									iomCiRltNodeCdt.setId(id);
								}
								iomCiRltNodeCdt.setCjsj(now);
								iomCiRltNodeCdt.setXgsj(now);
								iomCiRltNodeCdt.setYxbz(1);
								iomCiRltNodeCdt.setDomainId(Integer.valueOf(domainId==null?"-1":domainId));
								iomCiRltNodeCdt.setNodeId(iomCiRltNode.getId());
								iomCiRltNodeCdt.setStor(i+1);
								iomCiRltNodeCdt.setCdtOp(cdtOp);
								iomCiRltNodeCdt.setCdtVal(cdtVal);
								iomCiRltNodeCdt.setAttrId(attrId);
								iomCiRltNodeCdtMapper.insertSelective(iomCiRltNodeCdt);
							} catch (Exception e) {
								e.printStackTrace();
							}
				        }
					}
				}
				iomCiRltNodesInsertBatch(listIomCiRltNode);
			}
			
			// 线
			if (listIomCiRltLine != null && listIomCiRltLine.size() > 0) {
				for (IomCiRltLine iomCiRltLine : listIomCiRltLine) {
					if (StringUtils.isEmpty(iomCiRltLine.getId())) {
						iomCiRltLine.setId(SeqUtil.getId());
					}
					iomCiRltLine.setRuleId(ruleId);
					iomCiRltLine.setCjsj(now);
					iomCiRltLine.setXgsj(now);
					iomCiRltLine.setYxbz(1);
					iomCiRltLine.setDomainId(Integer.valueOf(domainId==null?"-1":domainId));
					iomCiRltLine.setRltType(1);
					iomCiRltLine.setTypeId(typeId);
				}
				iomCiRltLineInsertBatch(listIomCiRltLine);
			}
			return ruleId;
		}
		
		return null;
	}
	
	public Map<String, Object> getCiTypeSingleRels(String ruleId,String ciTypeId,Integer num,String direction,String domainId) {

		BaseTypeRelRuleUtil downTypeRelUtil = new BaseTypeRelRuleUtil("startTypeId","endTypeId",num) {
			@Override
			public Object getDate(List<String> ids) {
				List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
				if("up".equals(direction)) {
					Map<String, Object> itemMap=new HashMap<String, Object>();
					String [] idsArray = ids.toArray(new String[ids.size()]);
					itemMap.put("targetTypeIdList", idsArray);
					itemMap.put("sourceTypeIdList", "");
				}else if("down".equals(direction)) {
					Map<String, Object> itemMap=new HashMap<String, Object>();
					String [] idsArray = ids.toArray(new String[ids.size()]);
					itemMap.put("ruleId", ruleId);
					itemMap.put("startTypeIdList", idsArray);
					itemMap.put("endTypeIdList", "");
					if(domainId!=null && !"".equals(domainId)) {
						itemMap.put("domainId", domainId);
					}else {
						itemMap.put("domainId", null);
					}
					returnList=rltRuleDao.getIomCiRltLineByRuleIdToStartTypeId(itemMap);
				}
				return returnList;
			}

		};
		return downTypeRelUtil.getData(ciTypeId);
	}
	/**
	 * 获取配置报表数据---配置报表
	 * 
	 * @param ruleId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getDataExtractionRulesByruleId(String ruleId) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		List<Map<String,Object>> ciRltLineDataList=rltRuleDao.getIomCiRltLineByRuleId(ruleId,domainId);
		String typeIdStr="";
		if(ciRltLineDataList!=null && ciRltLineDataList.size()>0) {
			Map<String,Object> map=ciRltLineDataList.get(0);
			typeIdStr=(String) map.get("typeId");
		}
		Map<String,Object> mapRule=getCiTypeSingleRels(ruleId,typeIdStr,999,"down",domainId);
		List<Map<String,Object>> ciRltLineList=(List<Map<String, Object>>) mapRule.get("rels");
		//触发类别ID
		String typeId=null;
		//查询连线条件符号
		List<Map<String, Object>> dataDict=dictService.findDiceBySjIdHump("202006221639");
		//查询出每一个节点的限制条件
		List<Map<String,Object>> cdtList=rltRuleDao.getIomCiRltNodeCdtByNodeId(ruleId, domainId);
		//查询出节点限制条件符号
		List<Map<String, Object>> dataDictNode=dictService.findDiceBySjIdHump("202006221010");
		List<Map<String,Object>> ciRltLineListUp=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> ciRltLineListDown=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> ciRltLineListSelf=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:ciRltLineList) {
			ciRltLineListUp.add(map);
			ciRltLineListDown.add(JSON.parseObject(JSON.toJSONString(map)));
			ciRltLineListSelf.add(JSON.parseObject(JSON.toJSONString(map)));
		}
		if(ciRltLineList!=null && ciRltLineList.size()>0) {
			Map<String,Object> map=ciRltLineList.get(0);
			//触发ID
			typeId=(String) map.get("typeId");
			List<String> typeIds=new ArrayList<String>();
			typeIds.add(typeId);
			//筛选出向下数据
			Map<String,Object> returnMap=getDownDataList(typeId,typeIds,ciRltLineListDown,dataDict,new ArrayList<String>(),cdtList,dataDictNode,new ArrayList<Map<String,Object>>(),new HashMap<String,Object>(),domainId);
			ciRltLineListDown=(List<Map<String, Object>>) returnMap.get("data");
			ciRltLineListDown=filterListBySourceIdAndTargetId(ciRltLineListDown);
			
			//筛选出自己连自己数据
			ciRltLineListSelf=getSelfDataList(ciRltLineListSelf,new ArrayList<Map<String,Object>>(),dataDict);
            for(Map<String,Object> mapLine:ciRltLineList) {
            	String id=(String) mapLine.get("id");
            	List<Map<String,Object>> relDatas=(List<Map<String, Object>>) mapLine.get("relData");
            	if(relDatas==null || relDatas.size()==0) {
            		mapLine.put("relData", new ArrayList<Map<String,Object>>());
            		relDatas=(List<Map<String, Object>>) mapLine.get("relData");
            	}
            	if(ciRltLineListDown!=null && ciRltLineListDown.size()>0) {
            		for(Map<String,Object> mapDown:ciRltLineListDown) {
                		String idStr=(String) mapDown.get("id");
                		List<Map<String,Object>> relDataDown=(List<Map<String, Object>>) mapDown.get("relData");
                		if(id.equals(idStr)) {
                			if(relDataDown!=null && relDataDown.size()>0) {
                				for(Map<String,Object> relDataDownMap:relDataDown) {
                					relDatas.add(relDataDownMap);
                    			}
                			}
                			
                		}
                	}
            	}
            	if(ciRltLineListSelf!=null && ciRltLineListSelf.size()>0) {
            		for(Map<String,Object> mapSelf:ciRltLineListSelf) {
                		String idStr=(String) mapSelf.get("id");
                		List<Map<String,Object>> relDataSelf=(List<Map<String, Object>>) mapSelf.get("relData");
                		if(id.equals(idStr)) {
                			if(relDataSelf!=null && relDataSelf.size()>0) {
                				for(Map<String,Object> relDataSelfMap:relDataSelf) {
                					relDatas.add(relDataSelfMap);
                    			}
                			}
                			
                		}
                	}
            	}
            	
            }
            for(Map<String,Object> mapLine:ciRltLineList) {
            	List<Map<String,Object>> relDataList=(List<Map<String, Object>>) mapLine.get("relData");
            	List<Map<String,Object>> relDataListFilter=filterListBySourceCiIdAndTargetCiId(relDataList);
            	mapLine.put("relData", relDataListFilter);
            }
            //2020-11-25增加A-->B , B-->A把B-->A去掉
            for(Map<String,Object> mapLine:ciRltLineList) {
            	
            }
			//筛选出有几条路线
            List<Map<String, Object>> listUp=queryUpdClassInfo(typeId,ciRltLineList,new ArrayList<Map<String,Object>>());
            List<Map<String, Object>> ciRltLineListNew=new ArrayList<Map<String, Object>>();
            Set<String> ids=new HashSet<String>();
            for(Map<String, Object> map1:listUp) {
            	String id=(String) map1.get("id");
            	ids.add(id);
            }
            for(Map<String,Object> map2:ciRltLineList) {
            	String id=(String) map2.get("id");
            	boolean flag=ids.contains(id);
            	if(!flag) {
            		ciRltLineListNew.add(map2);
            	}
            }
            //去重
            ciRltLineListNew=filterListBySourceIdAndTargetId(ciRltLineListNew);
            Map<String,Object> mapBranch= getBranchAll(typeId,typeIds,ciRltLineListNew,new HashMap<String,Object>(),"1",new ArrayList<Map<String,Object>>(),0);
            System.out.println(mapBranch);
           List<Map<String,Object>> dataStructure=new ArrayList<Map<String,Object>>();
            for (String key : mapBranch.keySet()) {
            	 List<Map<String,Object>> list=(List<Map<String, Object>>) mapBranch.get(key);
            	 Map<String,Object> mapData=new LinkedHashMap<String,Object>();
            	 boolean flag=true;
            	 for(int i = list.size()-1; i>=0; i --) {
            		 List<Map<String,Object>> relList=(List<Map<String, Object>>)list.get(i).get("relData");
            		 List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
            		 if(i==list.size()-1 && relList.size()==0) {
            			 flag=false;
            		 }
            		 if(flag) {
            			 if(relList!=null && relList.size()>0) {
                			 String sourceTypeId="";
                			 String targetTypeId="";
                			 List<String> source=new ArrayList<String>();
                			 List<String> target=new ArrayList<String>();
                			 if(i==list.size()-1) {
                				 for(Map<String,Object> mapRel:relList) {
                    				 sourceTypeId=(String) mapRel.get("sourceTypeId");
                    				 targetTypeId=(String) mapRel.get("targetTypeId");
                    				 String sourceCiId=(String) mapRel.get("sourceCiId");
                    				 String targetCiId=(String) mapRel.get("targetCiId");
                    				 source.add(sourceCiId);
                    				 target.add(targetCiId);
                    			 }
                    			 mapData.put(targetTypeId, target);
                    			 mapData.put(sourceTypeId, source);
                			 }else {
                				 String endTypeIdStr=(String) list.get(i).get("endTypeId");
                				 List<String> listCiIds=(List<String>) mapData.get(endTypeIdStr);
                				 for(String ciId:listCiIds) {
                					 for(Map<String,Object> mapRel:relList) {
                						 sourceTypeId=(String) mapRel.get("sourceTypeId");
                        				 targetTypeId=(String) mapRel.get("targetTypeId");
                        				 String sourceCiId=(String) mapRel.get("sourceCiId");
                        				 String targetCiId=(String) mapRel.get("targetCiId");
                        				 if(targetCiId.equals(ciId)) {
                        					 source.add(sourceCiId);
                        					 continue;
                        				 }
                					 }
                				 }
                				 mapData.put(sourceTypeId, source);
                			 }
                			 
                		 }
            		 }
            	
            	 }
            	 Map<String,Object> mapDataNew=new LinkedHashMap<String,Object>();
            	// 使用的是迭代器 ListIterator
                 ListIterator<Map.Entry> i=
                         new ArrayList<Map.Entry>(
                        		 mapData.entrySet()).listIterator(mapData.size());
                 while(i.hasPrevious()) {
                     Map.Entry entry=i.previous();
                     String ciTypeId=(String) entry.getKey();
                     List<String> ciIdsList=(List<String>) entry.getValue();
                     mapDataNew.put(ciTypeId, ciIdsList);
                 }
                 //mapDataNew是将原来的倒着排顺序，改成正序列，就是改成由触发CI向下级查找，这里判断触发CI是否有数据，有则添加到List,因为最终是由每一条线路的目的端作为限制数据
                 if(mapDataNew!=null && mapDataNew.size()>0) {
                	 boolean flag1=mapDataNew.containsKey(typeId);
                	 if(flag1) {
                		 List<String> list1=(List<String>) mapDataNew.get(typeId); 
                		 if(list1.size()>0) {
                			 dataStructure.add(mapDataNew);
                		 }
                	 }
                	 
                 }
            }
            if(mapBranch.size()>0) {
            	if(dataStructure!=null && dataStructure.size()>0) {
                	Map<String,Object> mapData=dataStructure.get(0);
                    List<String> keys=new ArrayList<String>();
                    String key1="";
                    for (String key : mapData.keySet()) {
                    	key1=key;
                    	keys.add(key);
                    }
                    if(dataStructure!=null && dataStructure.size()>0) {
                    	if(dataStructure.size()==1) {
                    		Integer numOne=0;
                     	 	for(int i=0;i<keys.size();i++) {
                     	 		if(i==0) {
                     	 			String strKey=keys.get(i);
                         	 		List<String> list=(List<String>) mapData.get(strKey);
                         	 		numOne=list.size();
                     	 		}else {
                     	 			String strKey=keys.get(i);
                         	 		List<String> list=(List<String>) mapData.get(strKey);
                         	 		if(list.size()<numOne) {
                         	 			int num=numOne-list.size();
                         	 			for(int j=0;j<num;j++) {
                         	 				list.add(null);
                         	 			}
                         	 			mapData.put(strKey, list);
                         	 		}
                     	 		}                 	 		
                     	 	}
                    	}else {
                    	 	for(int i=0;i<dataStructure.size()-1;i++) {
                            	Map<String,Object> mapData1=dataStructure.get(i+1);
                            	String keyV="";
                            	for (String key2 : mapData1.keySet()) {
                            		boolean flag=keys.contains(key2);
                            		if(!flag) {
                            			List<String> list=(List<String>) mapData1.get(key2);
                            			List<String> list1=(List<String>) mapData.get(keyV);
                            			Integer numInteger=list1.size()-list.size();
                            			for(int j=0;j<numInteger;j++) {
                            				list.add(j, null);
                            			}
                            			mapData.put(key2, list);
                            			keys.add(key2);
                            		}
                            		if(flag) {
                            			List<String> list=(List<String>) mapData1.get(key2);
                            			List<String> list1=(List<String>) mapData.get(key2);
                            			for(String ciId:list) {
                            				list1.add(ciId);
                            			}
                            			keyV=key2;
                            			mapData.put(key2, list1);
                            		}
                            		
                                }
                            	List<String> keysMapData1=new ArrayList<String>(); 
                            	for (String key : mapData1.keySet()) {
                            		keysMapData1.add(key);
                                }
                            	String keyTypeId="";
                            	for(String key:keys) {
                            		boolean flag=keysMapData1.contains(key);
                            		if(flag) {
                            			keyTypeId=key;
                            		}
                            	}
                            	for(String key:keys) {
                            		boolean flag=keysMapData1.contains(key);
                            		if(!flag) {
                            			List<String> list=(List<String>) mapData.get(key);
                            			List<String> list1=(List<String>) mapData.get(keyTypeId);
                            			Integer numInteger=list1.size()-list.size();
                            			for(int k=0;k<numInteger;k++) {
                            				list.add(null);
                            			}
                            		}
                            	}
                            	
                            }
                    	}
                   
                    }
                    List<Map<String,Object>> itemList=queryOrganizeData(mapData);
                    //改驼峰
                    for(Map<String,Object> map1:itemList) {
                    	List<Map<String,Object>> list=(List<Map<String, Object>>) map1.get("attr");
                    	for(Map<String,Object> map2:list) {
                    		String mpCiItem=(String) map2.get("mpCiItem");
                    		String mpCiItemHump=StringUtil.underlineToCamel(mpCiItem);
                    		map2.put("mpCiItem", mpCiItemHump);
                    	}
                    }
        			return itemList;
            	}
        
            }          
		}
		
		return null;
	}
	
	public List<Map<String,Object>> queryOrganizeData(Map<String,Object> map){
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		for (String key : map.keySet()) {
			List<String> list=(List<String>) map.get(key);
			List<Map<String,Object>> itemList=typeItemService.findItemByTidToHump(key);
			Map itemMap=new HashMap<>();
			itemMap.put("search", "");
			itemMap.put("tid", key);
			Map dataMap=typeItemService.findDataNoPageToHump(itemMap,new ArrayList<Map<String,Object>>());
			List<Map<String,Object>> startDatas=(List<Map<String, Object>>) dataMap.get("rows");
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			for(String ciId:list) {
				for(Map<String,Object> map1:startDatas) {
					String ciIdStr=(String) map1.get("id");
					if(ciId==null) {
						Map<String,Object> map2=new HashMap<String,Object>();
						for (String key1 : map1.keySet()) {
							if("ciTypeId".equals(key1)) {
								String ciTypeId=(String) map1.get(key1);
								map2.put(key1, ciTypeId);
							}else {
								map2.put(key1, "");
							}						
		                }
						dataList.add(map2);
						break;
					}else {
						if(ciId.equals(ciIdStr)) {
							dataList.add(map1);
						}
					}
				}					
			}
			Map<String,Object> returnMap=new HashMap<String,Object>();
			returnMap.put("rows", dataList);
			returnMap.put("attr", itemList);
			returnList.add(returnMap);
        }
		return returnList;
	}
	
	public Map<String,Object> getBranchAllNew(String triggerId,List<String> typeIds,List<Map<String, Object>> ciRltLineList,String num,List<Map<String,Object>> returnList,String nodeId){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		for(String ciTypeId:typeIds) {
			List<Map<String, Object>> list=queryDownClassInfo(ciTypeId,ciRltLineList);
    		if(list==null || list.size()==0) {
    			Date date=new Date();
    			long longTime=date.getTime();  			
    			returnMap.put(String.valueOf(longTime), returnList);
    			List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
    			List<Map<String, Object>> list1=queryUpdClassInfo(nodeId,ciRltLineList,new ArrayList<Map<String,Object>>());
    			for(Map<String, Object> map:list1) {
    				String startTypeId=(String) map.get("startTypeId");
    				String endTypeId=(String) map.get("endTypeId");
    				for(Map<String,Object> map1:returnList) {
    					String startTypeId1=(String) map1.get("startTypeId");
        				if(startTypeId.equals(startTypeId1) && endTypeId.equals(endTypeId)) {
        					list2.add(map1);
        				}
    				}
    			}
    			returnList.addAll(list2);
    			return returnMap;
    		}
    		if(list!=null && list.size()==1) {
    			Map<String,Object> map=list.get(0);
    			returnList.add(map);
    			String endTypeId=(String) map.get("endTypeId");
    			List<String> endTypeIds1=new ArrayList<String>();
    			endTypeIds1.add(endTypeId);
    			List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
    			list2=returnList;
    			getBranchAllNew(triggerId,endTypeIds1,ciRltLineList,num,list2,nodeId);
    		}else if(list!=null && list.size()>0){
    			for(Map<String, Object> map:list) {
    				String startTypeId1=(String) map.get("startTypeId");
    				String endTypeId=(String) map.get("endTypeId");
    				List<String> endTypeIds=new ArrayList<String>();
        			endTypeIds.add(endTypeId);
        			returnList.add(map);
        			List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
        			list2=returnList;
        			getBranchAllNew(triggerId,endTypeIds,ciRltLineList,num,list2,startTypeId1);
    			}
    		}
		}
		return returnMap;
	}
	
	public Map<String,Object> getBranchAll(String triggerId,List<String> typeIds,List<Map<String, Object>> ciRltLineList,Map<String,Object> returnMap,String num,List<Map<String,Object>> returnList,Integer temp){
		for(String ciTypeId:typeIds) {
			List<Map<String, Object>> list=queryDownClassInfo(ciTypeId,ciRltLineList);
    		if(list==null || list.size()==0) {
    			List<Map<String, Object>> itemList=new ArrayList<Map<String, Object>>();
    			for(Map<String, Object> map:returnList) {
    				itemList.add(map);
    			}
    			String key="";
    			if(itemList!=null && itemList.size()>0) {
    				int numInt=itemList.size()-1;
    				Map<String,Object> map=itemList.get(numInt);
    				key=(String) map.get("endTypeId");
    			}
    			if(!"".equals(key)) {
                    UUID uuid = UUID.randomUUID();
    				returnMap.put(uuid.toString(), itemList);
    			}			  			
    			int size = returnList.size();
    			if(returnList!=null && returnList.size()>0) {
    				for(int index = size -1; index >= temp; index -- ) {
        				returnList.remove(index);
        			}
    			}   			
    			break;
    		}
    		if(list!=null && list.size()==1) {
    			Map<String,Object> map=list.get(0);
    			returnList.add(map);
    			String endTypeId=(String) map.get("endTypeId");
    			List<String> endTypeIds1=new ArrayList<String>();
    			endTypeIds1.add(endTypeId);
    			getBranchAll(triggerId,endTypeIds1,ciRltLineList,returnMap,num,returnList,temp);
    		}else if(list!=null && list.size()>0){

    			String startTypeId=(String) list.get(0).get("startTypeId");
    			List<Map<String, Object>> list1=queryUpdClassInfo(startTypeId,ciRltLineList,new ArrayList<Map<String,Object>>());
    			if(triggerId.equals(startTypeId)) {
    				list1=new ArrayList<Map<String,Object>>();
    			}
    			for(Map<String, Object> map:list) {
    				List<Map<String,Object>> list11=(List<Map<String, Object>>) map.get("relData");
    				if(list11==null || list11.size()==0) {
    					continue;
    				}
    				String endTypeId=(String) map.get("endTypeId");
    				List<String> endTypeIds=new ArrayList<String>();
        			endTypeIds.add(endTypeId);
        			returnList.add(map);      			
        			getBranchAll(triggerId,endTypeIds,ciRltLineList,returnMap,num,returnList,list1.size());
    			}
    		}
		}
		return returnMap;
	}
	public List<Map<String, Object>> queryDownClassInfo(String id,List<Map<String, Object>> list){
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			String startTypeId=(String) map.get("startTypeId");
			String endTypeId=(String) map.get("endTypeId");
			if(startTypeId.equals(id)) {
				//先暂时过滤掉自己指向自己的数据---2020-07-15
				if(!startTypeId.equals(endTypeId)) {
					returnList.add(map);
				}			
			}			
		}
		return returnList;
	}
	public List<Map<String, Object>> queryUpdClassInfo(String id,List<Map<String, Object>> list,List<Map<String, Object>> returnList){
		for(Map<String, Object> map:list) {
			String startTypeId=(String) map.get("startTypeId");
			String endTypeId=(String) map.get("endTypeId");
			if(endTypeId.equals(id)) {
				//先暂时过滤掉自己指向自己的数据---2020-07-15
				if(!startTypeId.equals(endTypeId)) {
					returnList.add(map);
					queryUpdClassInfo(startTypeId,list,returnList);
				}			
			}			
		}
		return returnList;
	}
	public List<Map<String, Object>> getCiTypeItemByCiRltLine(String triggerId,List<String> typeIds,List<Map<String, Object>> ciRltLineList,List<Map<String, Object>> returnItemList,List<Map<String,Object>> cdtList,List<Map<String,Object>> dataDictNode){
		List<String> targetTypeIds=new ArrayList<String>();
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		for(String typeId:typeIds) {
			for(Map<String, Object> map:ciRltLineList) {
				String sourceTypeId=(String) map.get("startTypeId");
				String targetTypeId=(String) map.get("endTypeId");
				if(typeId.equals(sourceTypeId) && !typeId.equals(targetTypeId)) {
					returnList.add(map);
					targetTypeIds.add(targetTypeId);
				}
			}
		}
		if(returnList!=null && returnList.size()>0) {
			if(returnList.size()==1) {
				Map<String, Object> map=returnList.get(0);
				boolean flage=map.containsKey("isFlag");
				if(flage) {
					boolean flag=(boolean) map.get("isFlag");
					if(flag) {
						return ciRltLineList;
					}					
				}
				String sourceTypeId=(String) map.get("startTypeId");
				String endTypeId=(String) map.get("endTypeId");
				List<Map<String,Object>> relData=(List<Map<String, Object>>) map.get("relData");				
				if(triggerId.equals(sourceTypeId)) {
					List<Map<String,Object>> returnListStart=new ArrayList<Map<String,Object>>();
					List<Map<String,Object>> returnListEnd=new ArrayList<Map<String,Object>>();
					Map<String,Object> mapStartData=new HashMap<String,Object>();
					Map<String,Object> mapEndData=new HashMap<String,Object>();
					List<Map<String,Object>> itemListStart=typeItemService.findItemByTidToHump(sourceTypeId);
					Map itemMapStart=new HashMap<>();
					itemMapStart.put("search", "");
					itemMapStart.put("tid", sourceTypeId);
					Map dataMapStart=typeItemService.findDataNoPageToHump(itemMapStart,new ArrayList<Map<String,Object>>());
					List<Map<String,Object>> itemListEnd=typeItemService.findItemByTidToHump(endTypeId);
					Map itemMapEnd=new HashMap<>();
					itemMapEnd.put("search", "");
					itemMapEnd.put("tid", endTypeId);
					Map dataMapEnd=typeItemService.findDataNoPageToHump(itemMapEnd,new ArrayList<Map<String,Object>>());
					List<Map<String,Object>> startDatas=(List<Map<String, Object>>) dataMapStart.get("rows");
					List<Map<String,Object>> endDatas=(List<Map<String, Object>>) dataMapEnd.get("rows");
					for(Map<String,Object> mapRel:relData) {
						String sourceCiId=(String) mapRel.get("sourceCiId");
						String targetCiId=(String) mapRel.get("targetCiId");
						for(Map<String,Object> mapDataStart:startDatas) {
							String id=(String) mapDataStart.get("id");
							if(sourceCiId.equals(id)) {
								returnListStart.add(mapDataStart);
							}
						}
						for(Map<String,Object> mapDataEnd:endDatas) {
							String id=(String) mapDataEnd.get("id");
							if(targetCiId.equals(id)) {
								returnListEnd.add(mapDataEnd);
							}
						}
					}
					if(returnListStart==null || returnListStart.size()==0) {
						mapStartData.put("rows", returnListStart);
						mapStartData.put("attr", new ArrayList<TypeItem>());
					}else {
						mapStartData.put("rows", returnListStart);
						mapStartData.put("attr", itemListStart);
					}
					if(returnListEnd==null || returnListEnd.size()==0) {
						mapEndData.put("rows", returnListEnd);
						mapEndData.put("attr", new ArrayList<TypeItem>());
					}else {
						mapEndData.put("rows", returnListEnd);
						mapEndData.put("attr", itemListEnd);
					}
					
					returnItemList.add(mapStartData);
					returnItemList.add(mapEndData);
					for(Map<String, Object> mapRlt:ciRltLineList) {
						String id=(String) mapRlt.get("id");
						for(Map<String, Object> mapReturn:returnList){
							String idStr=(String) mapReturn.get("id");
							if(id.equals(idStr)) {
								mapRlt.put("isFlag", true);
							}
						}
					}
					if(targetTypeIds!=null && targetTypeIds.size()>0) {
						getCiTypeItemByCiRltLine(triggerId,targetTypeIds,ciRltLineList,returnItemList,cdtList,dataDictNode);									
					}	
				}else {
					List<Map<String,Object>> returnListEnd=new ArrayList<Map<String,Object>>();
					Map<String,Object> mapEndData=new HashMap<String,Object>();
					List<Map<String,Object>> itemListEnd=typeItemService.findItemByTidToHump(endTypeId);
					Map itemMapEnd=new HashMap<>();
					itemMapEnd.put("search", "");
					itemMapEnd.put("tid", endTypeId);
					Map dataMapEnd=typeItemService.findDataNoPageToHump(itemMapEnd,new ArrayList<Map<String,Object>>());
					List<Map<String,Object>> endDatas=(List<Map<String, Object>>) dataMapEnd.get("rows");
					for(Map<String,Object> mapRel:relData) {
						String targetCiId=(String) mapRel.get("targetCiId");
						for(Map<String,Object> mapDataEnd:endDatas) {
							String id=(String) mapDataEnd.get("id");
							if(targetCiId.equals(id)) {
								returnListEnd.add(mapDataEnd);
							}
						}
					}
					if(returnListEnd==null || returnListEnd.size()==0) {
						mapEndData.put("rows", returnListEnd);
						mapEndData.put("attr", new ArrayList<TypeItem>());
					}else {
						mapEndData.put("rows", returnListEnd);
						mapEndData.put("attr", itemListEnd);
					}					
					returnItemList.add(mapEndData);
					for(Map<String, Object> mapRlt:ciRltLineList) {
						String id=(String) mapRlt.get("id");
						for(Map<String, Object> mapReturn:returnList){
							String idStr=(String) mapReturn.get("id");
							if(id.equals(idStr)) {
								mapRlt.put("isFlag", true);
							}
						}
					}
				}
				if(targetTypeIds!=null && targetTypeIds.size()>0) {
					getCiTypeItemByCiRltLine(triggerId,targetTypeIds,ciRltLineList,returnItemList,cdtList,dataDictNode);									
				}		
			}else if(returnList.size()>1){
				Map<String,Object> map=returnList.get(0);
				String sourceTypeId=(String) map.get("startTypeId");				
				if(triggerId.equals(sourceTypeId)) {
					List<Map<String,Object>> returnListStart=new ArrayList<Map<String,Object>>();
					Map<String,Object> mapStartData=new HashMap<String,Object>();
					List<String> setCiIds=new ArrayList<String>();
					int num=0;
					for(Map<String,Object> mapLine:returnList) {
						boolean flag=mapLine.containsKey("isFlag");
						if(flag) {
							boolean flage=(boolean) mapLine.get("isFlag");
							if(flage) {
								num+=1;
								continue;
							}					
						}
						List<Map<String,Object>> relDatas=(List<Map<String, Object>>) mapLine.get("relData");
						for(Map<String,Object> mapData:relDatas) {
							String sourceCiId=(String) mapData.get("sourceCiId");
							setCiIds.add(sourceCiId);
						}
					}
					if(num!=returnList.size()) {
						List<Map<String,Object>> itemListStart=typeItemService.findItemByTidToHump(sourceTypeId);
						Map itemMapStart=new HashMap<>();
						itemMapStart.put("search", "");
						itemMapStart.put("tid", sourceTypeId);
						Map dataMapStart=typeItemService.findDataNoPageToHump(itemMapStart,new ArrayList<Map<String,Object>>());
						List<Map<String,Object>> startDatas=(List<Map<String, Object>>) dataMapStart.get("rows");
						for(String ciId:setCiIds) {
							for(Map<String,Object> mapDataStart:startDatas) {
								String id=(String) mapDataStart.get("id");
								if(ciId.equals(id)) {
									returnListStart.add(mapDataStart);
								}
							}
							
						}
						mapStartData.put("rows", returnListStart);
						mapStartData.put("attr", itemListStart);
						returnItemList.add(mapStartData);
						for(int i=0;i<returnList.size();i++) {
							Map<String,Object> mapReturn=returnList.get(i);
							boolean flag=mapReturn.containsKey("isFlag");
							if(flag) {
								boolean flage=(boolean) map.get("isFlag");
								if(flage) {
									continue;
								}					
							}
							List<Map<String,Object>> returnListEnd=new ArrayList<Map<String,Object>>();
							Map<String,Object> mapEndData=new HashMap<String,Object>();
							String targetTypeId=(String) mapReturn.get("endTypeId");
							List<Map<String,Object>> relDatas=(List<Map<String, Object>>) mapReturn.get("relData");
							List<Map<String,Object>> itemListEnd=typeItemService.findItemByTidToHump(targetTypeId);
							Map itemMapEnd=new HashMap<>();
							itemMapEnd.put("search", "");
							itemMapEnd.put("tid", targetTypeId);
							Map dataMapEnd=typeItemService.findDataNoPageToHump(itemMapEnd,new ArrayList<Map<String,Object>>());
							List<Map<String,Object>> endDatas=(List<Map<String, Object>>) dataMapEnd.get("rows");
							for(Map<String,Object> mapRel:relDatas) {
								String targetCiId=(String) mapRel.get("targetCiId");
								for(Map<String,Object> mapData:endDatas) {
									String id=(String) mapData.get("id");
									if(targetCiId.equals(id)) {
										returnListEnd.add(mapData);
									}
								}
							}
							if(returnListEnd==null || returnListEnd.size()==0) {
								mapEndData.put("rows", returnListEnd);
								mapEndData.put("attr", new ArrayList<TypeItem>());
							}else {
								mapEndData.put("rows", returnListEnd);
								mapEndData.put("attr", itemListEnd);
							}
							
							returnItemList.add(mapEndData);
						}
						for(Map<String, Object> mapRlt:ciRltLineList) {
							String id=(String) mapRlt.get("id");
							for(Map<String, Object> mapReturn:returnList){
								String idStr=(String) mapReturn.get("id");
								if(id.equals(idStr)) {
									mapRlt.put("isFlag", true);
								}
							}
						}
						for(String targetTypeIdStr:targetTypeIds) {
							List<String> ciTypeIds=new ArrayList<String>();
							ciTypeIds.add(targetTypeIdStr);
		                    if(ciTypeIds!=null && ciTypeIds.size()>0) {
		                    	getCiTypeItemByCiRltLine(triggerId,ciTypeIds,ciRltLineList,returnItemList,cdtList,dataDictNode);
		                    }
							
						}
					}
					
				}else {
					for(Map<String,Object> mapReturn:returnList) {
						boolean flag=mapReturn.containsKey("isFlag");
						if(flag) {
							boolean flage=(boolean) map.get("isFlag");
							if(flage) {
								continue;
							}					
						}
						List<Map<String,Object>> returnListEnd=new ArrayList<Map<String,Object>>();
						Map<String,Object> mapEndData=new HashMap<String,Object>();
						String targetTypeId=(String) mapReturn.get("endTypeId");
						List<Map<String,Object>> relDatas=(List<Map<String, Object>>) mapReturn.get("relData");
						List<Map<String,Object>> itemListEnd=typeItemService.findItemByTidToHump(targetTypeId);
						Map itemMapEnd=new HashMap<>();
						itemMapEnd.put("search", "");
						itemMapEnd.put("tid", targetTypeId);
						Map dataMapEnd=typeItemService.findDataNoPageToHump(itemMapEnd,new ArrayList<Map<String,Object>>());
						List<Map<String,Object>> endDatas=(List<Map<String, Object>>) dataMapEnd.get("rows");
						for(Map<String,Object> mapRel:relDatas) {
							String targetCiId=(String) mapRel.get("targetCiId");
							for(Map<String,Object> mapData:endDatas) {
								String id=(String) mapData.get("id");
								if(targetCiId.equals(id)) {
									returnListEnd.add(mapData);
								}
							}
						}
						if(returnListEnd==null || returnListEnd.size()==0) {
							mapEndData.put("rows", returnListEnd);
							mapEndData.put("attr", new ArrayList<TypeItem>());
						}else {
							mapEndData.put("rows", returnListEnd);
							mapEndData.put("attr", itemListEnd);
						}
						
						returnItemList.add(mapEndData);
					}
					for(Map<String, Object> mapRlt:ciRltLineList) {
						String id=(String) mapRlt.get("id");
						for(Map<String, Object> mapReturn:returnList){
							String idStr=(String) mapReturn.get("id");
							if(id.equals(idStr)) {
								mapRlt.put("isFlag", true);
							}
						}
					}
					for(String targetTypeIdStr:targetTypeIds) {
						List<String> ciTypeIds=new ArrayList<String>();
						ciTypeIds.add(targetTypeIdStr);
	                    if(ciTypeIds!=null && ciTypeIds.size()>0) {
	                    	getCiTypeItemByCiRltLine(triggerId,ciTypeIds,ciRltLineList,returnItemList,cdtList,dataDictNode);
	                    }
						
					}
				}
			}
		}
		return returnItemList;
	}
	
	public List<Map<String, Object>> getItemScreenCriteria(String typeId,List<Map<String,Object>> cdtList,List<Map<String,Object>> itemList,List<Map<String,Object>> dataDict){
		//过滤节点的条件--start
		List<Map<String,Object>> cdtListNew=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> dataVal=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> mapCdt:cdtList) {
			String nodeTypeId=(String) mapCdt.get("nodeTypeId");
			if(typeId.equals(nodeTypeId)) {
				cdtListNew.add(mapCdt);
			}
		}
		for(Map<String,Object> mapCdt:cdtListNew) {
			String attrId=(String) mapCdt.get("attrId");
			String cdtOp=(String) mapCdt.get("cdtOp");
			String cdtVal=(String) mapCdt.get("cdtVal");
			Map<String,Object> returnMap=new HashMap<String,Object>();
			for(Map<String,Object> mapItem:itemList) {
				String id=(String) mapItem.get("id");
				String mpCiItem=(String) mapItem.get("mpCiItem");
				if(attrId.equals(id)) {
					returnMap.put("data", mpCiItem);
				}
			}
			for(Map<String,Object> mapDict:dataDict) {
				String dictId=(String) mapDict.get("dictId");
				String dictBm=(String) mapDict.get("dictBm");
				if(cdtOp.equals(String.valueOf(dictId))) {
					returnMap.put("cdtOp", dictBm);
				}
			}
			returnMap.put("cdtVal", cdtVal);
			dataVal.add(returnMap);
		}
		//过滤节点的条件--end
		return dataVal;
	}
	public List<Map<String, Object>> getUpDataList(String triggerId,List<String> typeIds,List<Map<String, Object>> ciRltLineListUp,List<Map<String, Object>> dataDict,List<String> sourceCiIds){
		List<String> sourceTypeIds=new ArrayList<String>();
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		for(String typeId:typeIds) {
			for(Map<String, Object> map:ciRltLineListUp) {
				String sourceTypeId=(String) map.get("startTypeId");
				String targetTypeId=(String) map.get("endTypeId");
				if(typeId.equals(targetTypeId) && !typeId.equals(sourceTypeId)) {
					returnList.add(map);
					sourceTypeIds.add(sourceTypeId);
				}
			}
		}
		if(returnList!=null && returnList.size()>0) {
			if(returnList.size()==1) {
				Map<String, Object> map=returnList.get(0);
				boolean flage=map.containsKey("isJudge");
				if(flage) {
					boolean judge=(boolean) map.get("isJudge");
					if(judge) {
						return ciRltLineListUp;
					}					
				}
				String sourceTypeId=(String) map.get("startTypeId");
				String targetTypeId=(String) map.get("endTypeId");
				String relId=(String) map.get("rltId");
				String [] sourceCiIdsArray = sourceCiIds.toArray(new String[sourceCiIds.size()]);
				Map<String, Object> itemMap = new HashMap<String, Object>();				
				if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
					itemMap.put("sourceTypeId", sourceTypeId);
				}else {
					itemMap.put("sourceTypeId", "");
				}
				if(targetTypeId!=null && !"".equals(targetTypeId)) {
					itemMap.put("targetTypeId", targetTypeId);
				}else {
					itemMap.put("targetTypeId", "");
				}
				if(relId!=null && !"".equals(relId)) {
					itemMap.put("relId", relId);
				}else {
					itemMap.put("relId", "");
				}
				if(sourceCiIds!=null && sourceCiIds.size()>0) {
					itemMap.put("ciIdList", sourceCiIdsArray);
				}else {
					itemMap.put("ciIdList", "");
				}
				List<Map<String, Object>> relDataList=dataRelDao.getCiDataRelByTargetIdAndSourceIdAndRelId(itemMap);
				String lineOp=(String) map.get("lineOp");
				String lineVal=(String) map.get("lineVal");
				boolean flag=lineVal.contains(",");
				String min="";
				String max="";
				if(flag) {
					String[] split = lineVal.split(",");
					for (int i = 0; i < split.length; i++) {
						if(i==0) {
							min=split[i];
						}else {
							max=split[i];
						}
						
					}
				}
				String lineOpName="";
				for(Map<String, Object> mapDataDict:dataDict) {
					Long dictId=(Long) mapDataDict.get("dictId");
					String dictBm=(String) mapDataDict.get("dictBm");
					if(lineOp.equals(String.valueOf(dictId))) {
						lineOpName=dictBm;
					}
				}
				if("大于等于".equals(lineOpName)) {
					if(Integer.parseInt(lineVal)!=0 && relDataList.size()<Integer.parseInt(lineVal)) {
						map.put("relData", new ArrayList<Map<String,Object>>());
						map.put("isJudge", true);
						return ciRltLineListUp;
					}else {
						sourceCiIds=new ArrayList<String>();
						map.put("relData", relDataList);
						map.put("isJudge", true);
					}
				}else if("小于等于".equals(lineOpName)) {
					if(Integer.parseInt(lineVal)!=0 && relDataList.size()>Integer.parseInt(lineVal)) {
						map.put("relData", new ArrayList<Map<String,Object>>());
						map.put("isJudge", true);
						return ciRltLineListUp;
					}else {
						sourceCiIds=new ArrayList<String>();
						map.put("relData", relDataList);
						map.put("isJudge", true);
					}
				}else if("区间".equals(lineOpName)) {
					if(relDataList.size()<Integer.valueOf(min) || relDataList.size()>Integer.valueOf(max)) {
						map.put("relData", new ArrayList<Map<String,Object>>());
						map.put("isJudge", true);
						return ciRltLineListUp;
					}else {
						sourceCiIds=new ArrayList<String>();
						map.put("relData", relDataList);
						map.put("isJudge", true);
					}
				}
				for(Map<String, Object> mapRel:relDataList){
					String sourceCiId=(String) mapRel.get("sourceCiId");
					sourceCiIds.add(sourceCiId);
				}
				for(Map<String, Object> mapRlt:ciRltLineListUp) {
					String id=(String) mapRlt.get("id");
					for(Map<String, Object> mapReturn:returnList){
						String idStr=(String) mapReturn.get("id");
						List<Map<String, Object>> list=(List<Map<String, Object>>) mapReturn.get("relData");
						if(id.equals(idStr)) {
							mapRlt.put("relData", list);
						}
					}
				}
				if(sourceTypeIds!=null && sourceTypeIds.size()>0) {
					if(sourceTypeIds!=null && sourceTypeIds.size()>0) {
						getUpDataList(triggerId,sourceTypeIds,ciRltLineListUp,dataDict,sourceCiIds);
					}
					
				}		
			}else if(returnList.size()>1) {
				for(int i=0;i<returnList.size();i++) {
					if(i==returnList.size()-1) {
						break;
					}else {
						Map<String, Object> startMap=returnList.get(i);
						Map<String, Object> endMap=returnList.get(i+1);
						boolean startFlage=startMap.containsKey("isJudge");
						boolean endFlage=endMap.containsKey("isJudge");
						if(startFlage && endFlage) {
							boolean judgeStart=(boolean) startMap.get("isJudge");
							boolean judgeEnd=(boolean) endMap.get("isJudge");
							if(judgeStart && judgeEnd) {
								return ciRltLineListUp;
							}
						}
						String startLineOp=(String) startMap.get("lineOp");
						String startLineVal=(String) startMap.get("lineVal");
						String endLineOp=(String) endMap.get("lineOp");
						String endLineVal=(String) endMap.get("lineVal");
						String startRelId=(String) startMap.get("rltId");
						String endRelId=(String) endMap.get("rltId");
						String startSourceTypeId=(String) startMap.get("startTypeId");
						String startTargetTypeId=(String) startMap.get("endTypeId");
						String endSourceTypeId=(String) endMap.get("startTypeId");
						String endTargetTypeId=(String) endMap.get("endTypeId");
						String startLineOpName="";
						String endLineOpName="";
						String startMin="";
						String startMax="";
						String endMin="";
						String endMax="";
						for(Map<String, Object> mapDataDict:dataDict) {
							Long dictId=(Long) mapDataDict.get("dictId");
							String dictBm=(String) mapDataDict.get("dictBm");
							if(startLineOp.equals(String.valueOf(dictId))) {
								startLineOpName=dictBm;
							}
							if(endLineOp.equals(String.valueOf(dictId))) {
								endLineOpName=dictBm;
							}
						}
						boolean startFlag=startLineVal.contains(",");						
						if(startFlag) {
							String[] split = startLineVal.split(",");
							for (int j = 0; j < split.length; j++) {
								if(i==0) {
									startMin=split[j];
								}else {
									startMax=split[j];
								}
								
							}
						}
						boolean endFlag=endLineVal.contains(",");						
						if(endFlag) {
							String[] split = endLineVal.split(",");
							for (int j = 0; j < split.length; j++) {
								if(i==0) {
									endMin=split[j];
								}else {
									endMax=split[j];
								}
								
							}
						}
						if(!"0".equals(startLineVal) && !"0".equals(endLineVal)){
							String type="AND";
						    Map<String, Object> returnMap=getCiDataRelListByIdsUp(startLineOpName,startLineVal,startMin,startMax,startRelId,startSourceTypeId,startTargetTypeId,endLineOpName,endLineVal,endMin,endMax,endRelId,endSourceTypeId,endTargetTypeId,sourceCiIds,type);
						    if(returnMap!=null && returnMap.size()>0) {
						    	List<Map<String,Object>> startList=(List<Map<String, Object>>) returnMap.get("start");
						    	List<Map<String,Object>> endList=(List<Map<String, Object>>) returnMap.get("end");
						    	startMap.put("relData", startList);
						    	endMap.put("relData", endList);
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    }else {
						    	startMap.put("relData", new ArrayList<Map<String,Object>>());
						    	endMap.put("relData", new ArrayList<Map<String,Object>>());
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    }
						}else {
							String type="OR";
							Map<String, Object> returnMap=getCiDataRelListByIdsUp(startLineOpName,startLineVal,startMin,startMax,startRelId,startSourceTypeId,startTargetTypeId,endLineOpName,endLineVal,endMin,endMax,endRelId,endSourceTypeId,endTargetTypeId,sourceCiIds,type);
							if(returnMap!=null && returnMap.size()>0) {
						    	List<Map<String,Object>> startList=(List<Map<String, Object>>) returnMap.get("start");
						    	List<Map<String,Object>> endList=(List<Map<String, Object>>) returnMap.get("end");
						    	startMap.put("relData", startList);
						    	endMap.put("relData", endList);
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    }else {
						    	startMap.put("relData", new ArrayList<Map<String,Object>>());
						    	endMap.put("relData", new ArrayList<Map<String,Object>>());
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    }
						}
					}				
				}
				for(Map<String, Object> mapRlt:ciRltLineListUp) {
					String id=(String) mapRlt.get("id");
					for(Map<String, Object> mapReturn:returnList){
						String idStr=(String) mapReturn.get("id");
						List<Map<String, Object>> list=(List<Map<String, Object>>) mapReturn.get("relData");
						if(id.equals(idStr)) {
							mapRlt.put("relData", list);
						}
					}
				}
				for(String sourceTypeId:sourceTypeIds) {
					List<String> ciTypeIds=new ArrayList<String>();
					ciTypeIds.add(sourceTypeId);
					List<String> sourceCiIdsList=new ArrayList<String>();
                    for(Map<String,Object> map:returnList) {
                    	String sourceTypeIdStr=(String) map.get("startTypeId");
                    	List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
                    	if(sourceTypeId.equals(sourceTypeIdStr)) {
                    		for(Map<String,Object> relMap:relDataList) {
                    			String sourceCiId=(String) relMap.get("sourceCiId");
                    			sourceCiIdsList.add(sourceCiId);
                    		}
                    	}
                    }
                    if(sourceCiIdsList!=null && sourceCiIdsList.size()>0) {
                    	getUpDataList(triggerId,ciTypeIds,ciRltLineListUp,dataDict,sourceCiIdsList);
                    }
					
				}
			}
		}
		return ciRltLineListUp;
	}
	public List<Map<String,Object>> getRelDatas(Map<String, Object> map,List<String> targetCiIds,List<Map<String,Object>> cdtList,List<Map<String,Object>> dataDictNode,String domainId){
		String sourceTypeId=(String) map.get("startTypeId");
		String targetTypeId=(String) map.get("endTypeId");
		String relId=(String) map.get("rltId");
		String [] targetCiIdsArray = targetCiIds.toArray(new String[targetCiIds.size()]);
		Map<String, Object> itemMap = new HashMap<String, Object>();				
		if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
			itemMap.put("sourceTypeId", sourceTypeId);
		}else {
			itemMap.put("sourceTypeId", "");
		}
		if(targetTypeId!=null && !"".equals(targetTypeId)) {
			itemMap.put("targetTypeId", targetTypeId);
		}else {
			itemMap.put("targetTypeId", "");
		}
		if(relId!=null && !"".equals(relId)) {
			itemMap.put("relId", relId);
		}else {
			itemMap.put("relId", "");
		}
		if(targetCiIds!=null && targetCiIds.size()>0) {
			itemMap.put("ciIdList", targetCiIdsArray);
		}else {
			itemMap.put("ciIdList", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			itemMap.put("domainId", domainId);
		}else {
			itemMap.put("domainId", "");
		}
		List<Map<String, Object>> relDataList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(itemMap);
		//过滤节点条件数据
		relDataList=getOrgDataBySourceTypeIdAndTargetTypeId(sourceTypeId,targetTypeId,cdtList,relDataList,dataDictNode);							
		return relDataList;
	}
	public Map<String, Object> getDownDataList(String triggerId,List<String> typeIds,List<Map<String, Object>> ciRltLineListDown,List<Map<String, Object>> dataDict,List<String> targetCiIds,List<Map<String,Object>> cdtList,List<Map<String,Object>> dataDictNode,List<Map<String,Object>> listTable,Map<String,Object> returnMap,String domainId){
		List<String> targetTypeIds=new ArrayList<String>();
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		for(String typeId:typeIds) {
			for(Map<String, Object> map:ciRltLineListDown) {
				String sourceTypeId=(String) map.get("startTypeId");
				String targetTypeId=(String) map.get("endTypeId");
				if(typeId.equals(sourceTypeId) && !typeId.equals(targetTypeId)) {
					returnList.add(map);
					targetTypeIds.add(targetTypeId);
				}
			}
		}
		if(returnList!=null && returnList.size()>0) {
			//去除重复的数据---start
			returnList=filterListBySourceIdAndTargetId(returnList);
			//去除重复的数据---end
			if(returnList.size()==1) {
				if(targetCiIds==null || targetCiIds.size()==0) {
					Map<String, Object> map=returnList.get(0);
					boolean flage=map.containsKey("isJudge");
					if(flage) {
						boolean judge=(boolean) map.get("isJudge");
						if(judge) {
							returnMap.put("data", ciRltLineListDown);
							returnMap.put("table", listTable);
							return returnMap;
						}					
					}
					List<Map<String, Object>> relDataList=getRelDatas(map,targetCiIds,cdtList,dataDictNode,domainId);
					String lineOp=(String) map.get("lineOp");
					String lineVal=(String) map.get("lineVal");
					boolean flag=lineVal.contains(",");
					String min="";
					String max="";
					if(flag) {
						String[] split = lineVal.split(",");
						for (int i = 0; i < split.length; i++) {
							if(i==0) {
								min=split[i];
							}else {
								max=split[i];
							}
							
						}
					}
					String lineOpName="";
					for(Map<String, Object> mapDataDict:dataDict) {
						String dictId=(String) mapDataDict.get("dictId");
						String dictBm=(String) mapDataDict.get("dictBm");
						if(lineOp.equals(dictId)) {
							lineOpName=dictBm;
						}
					}
					if("大于等于".equals(lineOpName)) {
						if(Integer.parseInt(lineVal)!=0 && relDataList.size()<Integer.parseInt(lineVal)) {
							map.put("relData", new ArrayList<Map<String,Object>>());
							map.put("isJudge", true);
							returnMap.put("data", ciRltLineListDown);
							returnMap.put("table", listTable);
							return returnMap;
						}else {
							targetCiIds=new ArrayList<String>();
							map.put("relData", relDataList);
							map.put("isJudge", true);
						}
					}else if("小于等于".equals(lineOpName)) {
						if(Integer.parseInt(lineVal)!=0 && relDataList.size()>Integer.parseInt(lineVal)) {
							map.put("relData", new ArrayList<Map<String,Object>>());
							map.put("isJudge", true);
							returnMap.put("data", ciRltLineListDown);
							returnMap.put("table", listTable);
							return returnMap;
						}else {
							targetCiIds=new ArrayList<String>();
							map.put("relData", relDataList);
							map.put("isJudge", true);
						}
					}else if("区间".equals(lineOpName)) {
						if(relDataList.size()<Integer.valueOf(min) || relDataList.size()>Integer.valueOf(max)) {
							map.put("relData", new ArrayList<Map<String,Object>>());
							map.put("isJudge", true);
							returnMap.put("data", ciRltLineListDown);
							returnMap.put("table", listTable);
							return returnMap;
						}else {
							targetCiIds=new ArrayList<String>();
							map.put("relData", relDataList);
							map.put("isJudge", true);
						}
					}
					for(Map<String, Object> mapRel:relDataList){
						String targetCiId=(String) mapRel.get("targetCiId");
						targetCiIds.add(targetCiId);
					}
					for(Map<String, Object> mapRlt:ciRltLineListDown) {
						String id=(String) mapRlt.get("id");
						for(Map<String, Object> mapReturn:returnList){
							String idStr=(String) mapReturn.get("id");
							List<Map<String, Object>> list=(List<Map<String, Object>>) mapReturn.get("relData");
							if(id.equals(idStr)) {
								mapRlt.put("relData", list);
							}
						}
					}
					if(targetTypeIds!=null && targetTypeIds.size()>0) {
						if(targetCiIds!=null && targetCiIds.size()>0) {
							getDownDataList(triggerId,targetTypeIds,ciRltLineListDown,dataDict,targetCiIds,cdtList,dataDictNode,listTable,returnMap,domainId);
						}
						
					}
				}else {
					for(int k=0;k<targetCiIds.size();k++) {
						String ciId=targetCiIds.get(k);
                        List<String> targetCiIdsNew=new ArrayList<String>();
                        targetCiIdsNew.add(ciId);
                        Map<String, Object> map=returnList.get(0);
    					boolean flage=map.containsKey("isJudge");
    					if(flage) {
    						boolean judge=(boolean) map.get("isJudge");
    						if(judge) {
    							returnMap.put("data", ciRltLineListDown);
    							returnMap.put("table", listTable);
    							return returnMap;
    						}					
    					}
    					List<Map<String, Object>> relDataList=getRelDatas(map,targetCiIdsNew,cdtList,dataDictNode,domainId);
    					String lineOp=(String) map.get("lineOp");
    					String lineVal=(String) map.get("lineVal");
    					boolean flag=lineVal.contains(",");
    					String min="";
    					String max="";
    					if(flag) {
    						String[] split = lineVal.split(",");
    						for (int i = 0; i < split.length; i++) {
    							if(i==0) {
    								min=split[i];
    							}else {
    								max=split[i];
    							}
    							
    						}
    					}
    					String lineOpName="";
    					for(Map<String, Object> mapDataDict:dataDict) {
    						String dictId=(String) mapDataDict.get("dictId");
    						String dictBm=(String) mapDataDict.get("dictBm");
    						if(lineOp.equals(dictId)) {
    							lineOpName=dictBm;
    						}
    					}
    					if("大于等于".equals(lineOpName)) {
    						if(Integer.valueOf(lineVal)!=0 && relDataList.size()<Integer.valueOf(lineVal)) {
    							if(targetCiIds.size()==1) {
    								map.put("relData", new ArrayList<Map<String,Object>>());
    								map.put("isJudge", true);
        							returnMap.put("data", ciRltLineListDown);
        							returnMap.put("table", listTable);
        							return returnMap;
    							}else {
    								map.put("relData", new ArrayList<Map<String,Object>>());
    							}
    							
    						}else {
    							if(k==0) {
    								//targetCiIds=new ArrayList<String>();
    								map.put("relData", relDataList);
    							}else {
    								List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("relData");
    								for(Map<String,Object> map1:relDataList) {
    									list.add(map1);
    								}
    								map.put("relData", list);
    							}
    							
    						}
    					}else if("小于等于".equals(lineOpName)) {
    						if(Integer.valueOf(lineVal)!=0 && relDataList.size()>Integer.valueOf(lineVal)) {
    							if(targetCiIds.size()==1) {
    								map.put("relData", new ArrayList<Map<String,Object>>());
    								map.put("isJudge", true);
        							returnMap.put("data", ciRltLineListDown);
        							returnMap.put("table", listTable);
        							return returnMap;
    							}else {
    								map.put("relData", new ArrayList<Map<String,Object>>());
    							}
    						}else {
    							if(k==0) {
    								//targetCiIds=new ArrayList<String>();
    								map.put("relData", relDataList);
    							}else {
    								List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("relData");
    								for(Map<String,Object> map1:relDataList) {
    									list.add(map1);
    								}
    								map.put("relData", list);
    							}
    						}
    					}else if("区间".equals(lineOpName)) {
    						if(relDataList.size()<Integer.valueOf(min) || relDataList.size()>Integer.valueOf(max)) {
    							if(targetCiIds.size()==1) {
    								map.put("relData", new ArrayList<Map<String,Object>>());
    								map.put("isJudge", true);
        							returnMap.put("data", ciRltLineListDown);
        							returnMap.put("table", listTable);
        							return returnMap;
    							}else {
    								map.put("relData", new ArrayList<Map<String,Object>>());
    							}
    						}else {
    							if(k==0) {
    								map.put("relData", relDataList);
    							}else {
    								List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("relData");
    								for(Map<String,Object> map1:relDataList) {
    									list.add(map1);
    								}
    								map.put("relData", list);
    							}
    						}
    					}
    					
					}
					Map<String, Object> map=returnList.get(0);
					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
					if(relDataList!=null&&relDataList.size()>0){
						for(Map<String, Object> mapRel:relDataList){
							String targetCiId=(String) mapRel.get("targetCiId");
							targetCiIds.add(targetCiId);
						}
					}
					for(Map<String, Object> mapRlt:ciRltLineListDown) {
						String id=(String) mapRlt.get("id");
						for(Map<String, Object> mapReturn:returnList){
							String idStr=(String) mapReturn.get("id");
							List<Map<String, Object>> list=(List<Map<String, Object>>) mapReturn.get("relData");
							if(id.equals(idStr)) {
								mapRlt.put("relData", list);
							}
						}
					}
					if(targetTypeIds!=null && targetTypeIds.size()>0) {
						if(targetCiIds!=null && targetCiIds.size()>0) {
							getDownDataList(triggerId,targetTypeIds,ciRltLineListDown,dataDict,targetCiIds,cdtList,dataDictNode,listTable,returnMap,domainId);
						}
						
					}
					
				}
				
				
			}else if(returnList.size()>1) {
				if(targetCiIds==null || targetCiIds.size()==0) {
					for(int i=0;i<returnList.size();i++) {
						if(i==returnList.size()-1) {
							Map<String, Object> startMap=returnList.get(i);
							Map<String, Object> endMap=returnList.get(0);
							String startSourceTypeId=(String) startMap.get("startTypeId");
							String startTargetTypeId=(String) startMap.get("endTypeId");
							String endSourceTypeId=(String) endMap.get("startTypeId");
							String endTargetTypeId=(String) endMap.get("endTypeId");
							Map<String,Object> returnDataMap=getStartAndEnDatas(startMap,endMap,dataDict,targetCiIds,domainId);
							//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---start
							Integer conditionNum=0;
							for(int j=0;j<returnList.size();j++) {
								Map<String, Object> map=returnList.get(j);
								String lineVal=(String) map.get("lineVal");
								if(!"0".equals(lineVal)) {
									conditionNum+=1;
								}
							}
							if(conditionNum==returnList.size()) {
								if(returnDataMap==null || returnDataMap.size()==0) {
									for(Map<String,Object> map:ciRltLineListDown) {
										map.put("relData", new ArrayList<Map<String,Object>>());
									}
									return returnMap;
								}
							}
							//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---end
							if(returnDataMap!=null && returnDataMap.size()>0) {
						    	List<Map<String,Object>> startList=(List<Map<String, Object>>) returnDataMap.get("start");
						    	//过滤节点条件数据
						    	startList=getOrgDataBySourceTypeIdAndTargetTypeId(startSourceTypeId,startTargetTypeId,cdtList,startList,dataDictNode);
						    	List<Map<String,Object>> endList=(List<Map<String, Object>>) returnDataMap.get("end");
						    	//过滤节点条件数据
						    	endList=getOrgDataBySourceTypeIdAndTargetTypeId(endSourceTypeId,endTargetTypeId,cdtList,endList,dataDictNode);					    	
						    	//筛选出符合共同条件的数据
						    	Set<String> ciIdsSet=new HashSet<String>();
						    	List<Map<String,Object>> startListFilter=new ArrayList<Map<String,Object>>(); 
						    	String lineValStart=(String) startMap.get("lineVal");
						    	String lineValEnd=(String) endMap.get("lineVal");
						    	//AND关系
						    	boolean flag=lineValStart.contains(",");
						    	if(flag) {
						    		String[] split = lineValStart.split(",");
						    		lineValStart=split[split.length-1];
						    	}
						    	boolean flag1=lineValEnd.contains(",");
						    	if(flag1) {
						    		String[] split = lineValEnd.split(",");
						    		lineValEnd=split[split.length-1];
						    	}
						    	if(Integer.valueOf(lineValStart)>0 && Integer.valueOf(lineValEnd)>0) {
						    		if(endList!=null && endList.size()>0) {
							    		for(Map<String,Object> map:endList) {
							    			String sourceCiId=(String) map.get("sourceCiId");
							    			ciIdsSet.add(sourceCiId);
							    		}
							    	}
							    	for(String ciId:ciIdsSet) {
							    		for(Map<String,Object> map:startList) {
							    			String sourceCiId=(String) map.get("sourceCiId");
							    			if(ciId.equals(sourceCiId)) {
							    				startListFilter.add(map);
							    			}
							    		}
							    	}
							    	startList=startListFilter;
							    	if(targetCiIds==null || targetCiIds.size()==0) {
							    		for(Map<String,Object> map:startList) {
							    			String sourceCiId=(String) map.get("sourceCiId");
							    			targetCiIds.add(sourceCiId);
							    		}
							    	}
							    	//OR关系
						    	}else if(Integer.valueOf(lineValStart)==0 && Integer.valueOf(lineValEnd)==0) {
						    		if(targetCiIds==null || targetCiIds.size()==0) {
							    		for(Map<String,Object> map:startList) {
							    			String sourceCiId=(String) map.get("sourceCiId");
							    			targetCiIds.add(sourceCiId);
							    		}
							    		for(Map<String,Object> map:endList) {
							    			String sourceCiId=(String) map.get("sourceCiId");
							    			targetCiIds.add(sourceCiId);
							    		}
							    	}
						    	}					    	
						    	startMap.put("relData", startList);
						    	endMap.put("relData", endList);
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    	
						    }else {
						    	startMap.put("relData", new ArrayList<Map<String,Object>>());
						    	endMap.put("relData", new ArrayList<Map<String,Object>>());
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    }
						}else {
							Map<String, Object> startMap=returnList.get(i);
							Map<String, Object> endMap=returnList.get(i+1);
							String startSourceTypeId=(String) startMap.get("startTypeId");
							String startTargetTypeId=(String) startMap.get("endTypeId");
							String endSourceTypeId=(String) endMap.get("startTypeId");
							String endTargetTypeId=(String) endMap.get("endTypeId");
							Map<String,Object> returnDataMap=getStartAndEnDatas(startMap,endMap,dataDict,targetCiIds,domainId);
							//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---start
							Integer conditionNum=0;
							for(int j=0;j<returnList.size();j++) {
								Map<String, Object> map=returnList.get(i);
								String lineVal=(String) map.get("lineVal");
								if(!"0".equals(lineVal)) {
									conditionNum+=1;
								}
							}
							if(conditionNum==returnList.size()) {
								if(returnDataMap==null || returnDataMap.size()==0) {
									for(Map<String,Object> map:ciRltLineListDown) {
										map.put("relData", new ArrayList<Map<String,Object>>());
									}
									return returnMap;
								}
							}
							//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---end
							if(returnDataMap!=null && returnDataMap.size()>0) {
						    	List<Map<String,Object>> startList=(List<Map<String, Object>>) returnDataMap.get("start");
						    	//过滤节点条件数据
						    	startList=getOrgDataBySourceTypeIdAndTargetTypeId(startSourceTypeId,startTargetTypeId,cdtList,startList,dataDictNode);
						    	List<Map<String,Object>> endList=(List<Map<String, Object>>) returnDataMap.get("end");
						    	//过滤节点条件数据
						    	endList=getOrgDataBySourceTypeIdAndTargetTypeId(endSourceTypeId,endTargetTypeId,cdtList,endList,dataDictNode);					    	
						    	//筛选出符合共同条件的数据
						    	Set<String> ciIdsSet=new HashSet<String>();
						    	List<Map<String,Object>> startListFilter=new ArrayList<Map<String,Object>>(); 
						    	String lineValStart=(String) startMap.get("lineVal");
						    	String lineValEnd=(String) endMap.get("lineVal");
						    	//AND关系
						    	boolean flag=lineValStart.contains(",");
						    	if(flag) {
						    		String[] split = lineValStart.split(",");
						    		lineValStart=split[split.length-1];
						    	}
						    	boolean flag1=lineValEnd.contains(",");
						    	if(flag1) {
						    		String[] split = lineValEnd.split(",");
						    		lineValEnd=split[split.length-1];
						    	}
						    	if(lineValStart!=null && !"".equals(lineValStart) && lineValEnd!=null && !"".equals(lineValEnd)) {
						    		if(Integer.valueOf(lineValStart)>0 && Integer.valueOf(lineValEnd)>0) {
							    		if(endList!=null && endList.size()>0) {
								    		for(Map<String,Object> map:endList) {
								    			String sourceCiId=(String) map.get("sourceCiId");
								    			ciIdsSet.add(sourceCiId);
								    		}
								    	}
								    	for(String ciId:ciIdsSet) {
								    		for(Map<String,Object> map:startList) {
								    			String sourceCiId=(String) map.get("sourceCiId");
								    			if(ciId.equals(sourceCiId)) {
								    				startListFilter.add(map);
								    			}
								    		}
								    	}
								    	startList=startListFilter;
								    	if(targetCiIds==null || targetCiIds.size()==0) {
								    		for(Map<String,Object> map:startList) {
								    			String sourceCiId=(String) map.get("sourceCiId");
								    			targetCiIds.add(sourceCiId);
								    		}
								    	}
								    	//OR关系
							    	}else if(Integer.valueOf(lineValStart)==0 && Integer.valueOf(lineValEnd)==0) {
							    		if(targetCiIds==null || targetCiIds.size()==0) {
								    		for(Map<String,Object> map:startList) {
								    			String sourceCiId=(String) map.get("sourceCiId");
								    			targetCiIds.add(sourceCiId);
								    		}
								    		for(Map<String,Object> map:endList) {
								    			String sourceCiId=(String) map.get("sourceCiId");
								    			targetCiIds.add(sourceCiId);
								    		}
								    	}
							    	}
						    	}						    						    	
						    	startMap.put("relData", startList);
						    	endMap.put("relData", endList);
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    	
						    }else {
						    	startMap.put("relData", new ArrayList<Map<String,Object>>());
						    	endMap.put("relData", new ArrayList<Map<String,Object>>());
						    	startMap.put("isJudge", true);
						    	endMap.put("isJudge", true);
						    }
						}				
					}
					//依据最后一个分支筛选数据
					Map<String,Object> map1=returnList.get(returnList.size()-1);
					String lineVal=(String) map1.get("lineVal");
					if(Integer.valueOf(lineVal)>=1) {
						Set<String> ciIds=new HashSet<String>();
						List<Map<String,Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
						for(Map<String,Object> mapRel:relDatas) {
							String sourceCiId=(String) mapRel.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						for(String ciId:ciIds) {
							for(int i=0;i<returnList.size();i++) {
								Map<String,Object> mapData=returnList.get(i);
								List<Map<String,Object>> relDatasList=(List<Map<String, Object>>) mapData.get("relData");
								List<Map<String,Object>> relDatasListNew=new ArrayList<Map<String,Object>>();
								for(Map<String,Object> mapRel:relDatasList) {
									String sourceCiId=(String) mapRel.get("sourceCiId");
									if(ciId.equals(sourceCiId)) {
										relDatasListNew.add(mapRel);
									}
								}
								mapData.put("relData", relDatasListNew);
							}
						}
						
					}
				}else {
					for(int h=0;h<targetCiIds.size();h++) {
						String ciId=targetCiIds.get(h);
						for(int i=0;i<returnList.size();i++) {
							if(i==returnList.size()-1) {
								Map<String, Object> startMap=returnList.get(i);
								Map<String, Object> endMap=returnList.get(0);
								String startSourceTypeId=(String) startMap.get("startTypeId");
								String startTargetTypeId=(String) startMap.get("endTypeId");
								String endSourceTypeId=(String) endMap.get("startTypeId");
								String endTargetTypeId=(String) endMap.get("endTypeId");
								List<String> targetCiIdsNew=new ArrayList<String>();
								targetCiIdsNew.add(ciId);
								Map<String,Object> returnDataMap=getStartAndEnDatas(startMap,endMap,dataDict,targetCiIdsNew,domainId);
								//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---start
								Integer conditionNum=0;
								for(int j=0;j<returnList.size();j++) {
									Map<String, Object> map=returnList.get(j);
									String lineVal=(String) map.get("lineVal");
									if(!"0".equals(lineVal)) {
										conditionNum+=1;
									}
								}
								if(conditionNum==returnList.size()) {
									if(returnDataMap==null || returnDataMap.size()==0) {									
										break;
									}
								}
								//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---end
								if(returnDataMap!=null && returnDataMap.size()>0) {
							    	List<Map<String,Object>> startList=(List<Map<String, Object>>) returnDataMap.get("start");
							    	//过滤节点条件数据
							    	startList=getOrgDataBySourceTypeIdAndTargetTypeId(startSourceTypeId,startTargetTypeId,cdtList,startList,dataDictNode);
							    	List<Map<String,Object>> endList=(List<Map<String, Object>>) returnDataMap.get("end");
							    	//过滤节点条件数据
							    	endList=getOrgDataBySourceTypeIdAndTargetTypeId(endSourceTypeId,endTargetTypeId,cdtList,endList,dataDictNode);					    	
							    	//筛选出符合共同条件的数据
							    	Set<String> ciIdsSet=new HashSet<String>();
							    	List<Map<String,Object>> startListFilter=new ArrayList<Map<String,Object>>(); 
							    	String lineValStart=(String) startMap.get("lineVal");
							    	String lineValEnd=(String) endMap.get("lineVal");				    	
							    	if(targetCiIds.size()>1 && h==targetCiIds.size()-1) {
							    		List<Map<String,Object>> listStart=(List<Map<String, Object>>) startMap.get("relData");
							    		List<Map<String,Object>> listEnd=(List<Map<String, Object>>) endMap.get("relData");
							    		List<Map<String,Object>> listStartNew=new ArrayList<Map<String,Object>>();
							    		List<Map<String,Object>> listEndNew=new ArrayList<Map<String,Object>>();
							    		for(Map<String,Object> map:startList) {
							    			String id=(String) map.get("id");
							    			if(listStart!=null && listStart.size()>0) {
							    				for(Map<String,Object> map1:listStart) {
								    				String idStr=(String) map1.get("id");
								    				if(!id.equals(idStr)) {
								    					listStartNew.add(map);
								    				}
								    			}
							    			}else {
							    				listStart.add(map);
							    			}	
							    		}
							    		//去重
							    		listStartNew=filterListById(listStartNew);
							    		for(Map<String,Object> map:listStartNew) {
							    			listStart.add(map);
							    		}
							    		for(Map<String,Object> map:endList) {
							    			String id=(String) map.get("id");
							    			if(listEnd!=null && listEnd.size()>0) {
							    				for(Map<String,Object> map1:listEnd) {
								    				String idStr=(String) map1.get("id");
								    				if(!id.equals(idStr)) {
								    					listEndNew.add(map);
								    				}
								    			}
							    			}else {
							    				listEnd.add(map);
							    			}
							    			
							    		}
							    		//去重
							    		listEndNew=filterListById(listEndNew);
							    		for(Map<String,Object> map:listEndNew) {
							    			listEnd.add(map);
							    		}
							    		startMap.put("relData", listStart);
							    		endMap.put("relData", listEnd);
							    	}else {
							    		startMap.put("relData", startList);
								    	endMap.put("relData", endList);
								    	startMap.put("isJudge", true);
								    	endMap.put("isJudge", true);
							    	}							
							    	
							    }else {
							    	if(h==0) {
							    		startMap.put("relData", new ArrayList<Map<String,Object>>());
								    	endMap.put("relData", new ArrayList<Map<String,Object>>());
								    	startMap.put("isJudge", true);
								    	endMap.put("isJudge", true);
							    	}
							    	
							    }
							}else {
								Map<String, Object> startMap=returnList.get(i);
								Map<String, Object> endMap=returnList.get(i+1);
								String startSourceTypeId=(String) startMap.get("startTypeId");
								String startTargetTypeId=(String) startMap.get("endTypeId");
								String endSourceTypeId=(String) endMap.get("startTypeId");
								String endTargetTypeId=(String) endMap.get("endTypeId");
								List<String> targetCiIdsNew=new ArrayList<String>();
								targetCiIdsNew.add(ciId);
								Map<String,Object> returnDataMap=getStartAndEnDatas(startMap,endMap,dataDict,targetCiIdsNew,domainId);
								//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---start
								Integer conditionNum=0;
								for(int j=0;j<returnList.size();j++) {
									Map<String, Object> map=returnList.get(j);
									String lineVal=(String) map.get("lineVal");
									if(!"0".equals(lineVal)) {
										conditionNum+=1;
									}
								}
								if(conditionNum==returnList.size()) {
									if(returnDataMap==null || returnDataMap.size()==0) {
										for(Map<String, Object> map:returnList) {
											map.put("relData", new ArrayList<Map<String,Object>>());
										}
										break;
									}
								}
								//如果分支都是AND关系，且有一对组合不满足条件则把所有关系连线的relData置为空---end
								if(returnDataMap!=null && returnDataMap.size()>0) {
							    	List<Map<String,Object>> startList=(List<Map<String, Object>>) returnDataMap.get("start");
							    	//过滤节点条件数据
							    	startList=getOrgDataBySourceTypeIdAndTargetTypeId(startSourceTypeId,startTargetTypeId,cdtList,startList,dataDictNode);
							    	List<Map<String,Object>> endList=(List<Map<String, Object>>) returnDataMap.get("end");
							    	//过滤节点条件数据
							    	endList=getOrgDataBySourceTypeIdAndTargetTypeId(endSourceTypeId,endTargetTypeId,cdtList,endList,dataDictNode);					    	
							    	//筛选出符合共同条件的数据
							    	Set<String> ciIdsSet=new HashSet<String>();
							    	List<Map<String,Object>> startListFilter=new ArrayList<Map<String,Object>>(); 
							    	String lineValStart=(String) startMap.get("lineVal");
							    	String lineValEnd=(String) endMap.get("lineVal");
							    	if(targetCiIds.size()>1 && h==targetCiIds.size()-1) {
							    		List<Map<String,Object>> listStart=(List<Map<String, Object>>) startMap.get("relData");
							    		List<Map<String,Object>> listEnd=(List<Map<String, Object>>) endMap.get("relData");
							    		List<Map<String,Object>> listStartNew=new ArrayList<Map<String,Object>>();
							    		List<Map<String,Object>> listEndNew=new ArrayList<Map<String,Object>>();
							    		for(Map<String,Object> map:startList) {
							    			String id=(String) map.get("id");
							    			if(listStart!=null && listStart.size()>0) {
							    				for(Map<String,Object> map1:listStart) {
								    				String idStr=(String) map1.get("id");
								    				if(!id.equals(idStr)) {
								    					listStartNew.add(map);
								    				}
								    			}
							    			}else {
							    				listStart.add(map);
							    			}		
							    		}
							    		//去重
							    		listStartNew=filterListById(listStartNew);
							    		for(Map<String,Object> map:listStartNew) {
							    			listStart.add(map);
							    		}
							    		for(Map<String,Object> map:endList) {
							    			String id=(String) map.get("id");
							    			if(listEnd!=null && listEnd.size()>0) {
							    				for(Map<String,Object> map1:listEnd) {
								    				String idStr=(String) map1.get("id");
								    				if(!id.equals(idStr)) {
								    					listEndNew.add(map);
								    				}
								    			}
							    			}else {
							    				listEnd.add(map);
							    			}
							    			
							    		}
							    		//去重
							    		listEndNew=filterListById(listEndNew);
							    		for(Map<String,Object> map:listEndNew) {
							    			listEnd.add(map);
							    		}
							    		startMap.put("relData", listStart);
							    		endMap.put("relData", listEnd);
							    	}else {
							    		startMap.put("relData", startList);
								    	endMap.put("relData", endList);
								    	startMap.put("isJudge", true);
								    	endMap.put("isJudge", true);
							    	}							    	
							    	
							    }else {
							    	if(h==0) {
							    		startMap.put("relData", new ArrayList<Map<String,Object>>());
								    	endMap.put("relData", new ArrayList<Map<String,Object>>());
								    	startMap.put("isJudge", true);
								    	endMap.put("isJudge", true);
							    	}
							    	
							    }
							}
						}
					}
				}
				
				for(Map<String, Object> mapRlt:ciRltLineListDown) {
					String id=(String) mapRlt.get("id");
					for(Map<String, Object> mapReturn:returnList){
						String idStr=(String) mapReturn.get("id");
						List<Map<String, Object>> list=(List<Map<String, Object>>) mapReturn.get("relData");
						if(id.equals(idStr)) {
							mapRlt.put("relData", list);
						}
					}
				}
				for(String targetTypeId:targetTypeIds) {
					List<String> ciTypeIds=new ArrayList<String>();
					ciTypeIds.add(targetTypeId);
					List<String> targetCiIdsList=new ArrayList<String>();
                    for(Map<String,Object> map:returnList) {
                    	String targetTypeIdStr=(String) map.get("endTypeId");
                    	List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
                    	if(targetTypeId.equals(targetTypeIdStr)) {
                    		for(Map<String,Object> relMap:relDataList) {
                    			String targetCiId=(String) relMap.get("targetCiId");
                    			targetCiIdsList.add(targetCiId);
                    		}
                    	}
                    }
                    if(targetCiIdsList!=null && targetCiIdsList.size()>0) {
                    	getDownDataList(triggerId,ciTypeIds,ciRltLineListDown,dataDict,targetCiIdsList,cdtList,dataDictNode,listTable,returnMap,domainId);
                    }
					
				}
			}
		}
		
		returnMap.put("data", ciRltLineListDown);
		returnMap.put("table", listTable);
		return returnMap;
	}
	
	public Map<String,Object> getStartAndEnDatas(Map<String,Object> startMap,Map<String,Object> endMap,List<Map<String,Object>> dataDict,List<String> targetCiIds,String domainId){
		String startLineOp=(String) startMap.get("lineOp");
		String startLineVal=(String) startMap.get("lineVal");
		String endLineOp=(String) endMap.get("lineOp");
		String endLineVal=(String) endMap.get("lineVal");
		String startRelId=(String) startMap.get("rltId");
		String endRelId=(String) endMap.get("rltId");
		String startSourceTypeId=(String) startMap.get("startTypeId");
		String startTargetTypeId=(String) startMap.get("endTypeId");
		String endSourceTypeId=(String) endMap.get("startTypeId");
		String endTargetTypeId=(String) endMap.get("endTypeId");
		String startLineOpName="";
		String endLineOpName="";
		String startMin="";
		String startMax="";
		String endMin="";
		String endMax="";
		for(Map<String, Object> mapDataDict:dataDict) {
			String dictId=(String) mapDataDict.get("dictId");
			String dictBm=(String) mapDataDict.get("dictBm");
			if(startLineOp.equals(String.valueOf(dictId))) {
				startLineOpName=dictBm;
			}
			if(endLineOp.equals(String.valueOf(dictId))) {
				endLineOpName=dictBm;
			}
		}
		boolean startFlag=startLineVal.contains(",");						
		if(startFlag) {
			String[] split = startLineVal.split(",");
			for (int j = 0; j < split.length; j++) {
				if(j==0) {
					startMin=split[j];
				}else {
					startMax=split[j];
				}
				
			}
		}
		boolean endFlag=endLineVal.contains(",");						
		if(endFlag) {
			String[] split = endLineVal.split(",");
			for (int j = 0; j < split.length; j++) {
				if(j==0) {
					endMin=split[j];
				}else {
					endMax=split[j];
				}
				
			}
		}
		
		Map<String, Object> returnDataMap=getCiDataRelListByIdsDown(startLineOpName,startLineVal,startMin,startMax,startRelId,startSourceTypeId,startTargetTypeId,endLineOpName,endLineVal,endMin,endMax,endRelId,endSourceTypeId,endTargetTypeId,targetCiIds,"",domainId);
		return returnDataMap;
	}
	public Map<String, Object> getCiDataRelListByIdsDown(String startLineOpName,String startLineVal,String startMin,String startMax,String startRelId,String startSourceTypeId,String startTargetTypeId,String endLineOpName
			,String endLineVal,String endMin,String endMax,String endRelId,String endSourceTypeId,String endTargetTypeId,List<String> targetCiIds,String type,String domainId){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		Map<String, Object> itemMap = new HashMap<String, Object>();
		if(startMin!=null && !"".equals(startMin)) {
			itemMap.put("startMin", startMin);
		}else {
			itemMap.put("startMin", "");
		}
		if(startMax!=null && !"".equals(startMax)) {
			itemMap.put("startMax", startMax);
		}else {
			itemMap.put("startMax", "");
		}
		if(targetCiIds!=null && targetCiIds.size()>0) {
			itemMap.put("ciIdList", targetCiIds.toArray(new String[targetCiIds.size()]));
		}else {
			itemMap.put("ciIdList", "");
		}
		if(startSourceTypeId!=null && !"".equals(startSourceTypeId)) {
			itemMap.put("startSourceTypeId", startSourceTypeId);
		}else {
			itemMap.put("startSourceTypeId", "");
		}
		if(startTargetTypeId!=null && !"".equals(startTargetTypeId)) {
			itemMap.put("startTargetTypeId", startTargetTypeId);
		}else {
			itemMap.put("startTargetTypeId", "");
		}
		if(startRelId!=null && !"".equals(startRelId)) {
			itemMap.put("startRelId", startRelId);
		}else {
			itemMap.put("startRelId", "");
		}
		if(startLineOpName!=null && !"".equals(startLineOpName)) {
			itemMap.put("startSymbol", startLineOpName);
		}else {
			itemMap.put("startSymbol", "");
		}
		if(startLineVal!=null && !"".equals(startLineVal)) {
			itemMap.put("startLineVal", startLineVal);
		}else {
			itemMap.put("startLineVal", "");
		}
		if(type!=null && !"".equals(type)) {
			itemMap.put("ao", type);
		}else {
			itemMap.put("ao", "");
		}
		if(endMin!=null && !"".equals(endMin)) {
			itemMap.put("endMin", endMin);
		}else {
			itemMap.put("endMin", "");
		}
		if(endMax!=null && !"".equals(endMax)) {
			itemMap.put("endMax", endMax);
		}else {
			itemMap.put("endMax", "");
		}
		if(endSourceTypeId!=null && !"".equals(endSourceTypeId)) {
			itemMap.put("endSourceTypeId", endSourceTypeId);
		}else {
			itemMap.put("endSourceTypeId", "");
		}
		if(endTargetTypeId!=null && !"".equals(endTargetTypeId)) {
			itemMap.put("endTargetTypeId", endTargetTypeId);
		}else {
			itemMap.put("endTargetTypeId", "");
		}
		if(endRelId!=null && !"".equals(endRelId)) {
			itemMap.put("endRelId", endRelId);
		}else {
			itemMap.put("endRelId", "");
		}
		if(endLineOpName!=null && !"".equals(endLineOpName)) {
			itemMap.put("endSymbol", endLineOpName);
		}else {
			itemMap.put("endSymbol", "");
		}
		if(endLineVal!=null && !"".equals(endLineVal)) {
			itemMap.put("endLineVal", endLineVal);
		}else {
			itemMap.put("endLineVal", "");
		}
		Map<String, Object> startMap = new HashMap<String, Object>();
		if(targetCiIds!=null && targetCiIds.size()>0) {
			startMap.put("ciIdList", targetCiIds.toArray(new String[targetCiIds.size()]));
		}else {
			startMap.put("ciIdList", "");
		}
		if(startSourceTypeId!=null && !"".equals(startSourceTypeId)) {
			startMap.put("sourceTypeId", startSourceTypeId);
		}else {
			startMap.put("sourceTypeId", "");
		}
		if(startTargetTypeId!=null && !"".equals(startTargetTypeId)) {
			startMap.put("targetTypeId", startTargetTypeId);
		}else {
			startMap.put("targetTypeId", "");
		}
		if(startRelId!=null && !"".equals(startRelId)) {
			startMap.put("relId", startRelId);
		}else {
			startMap.put("relId", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			startMap.put("domainId", domainId);
		}else {
			startMap.put("domainId", "");
		}
		Map<String, Object> endMap = new HashMap<String, Object>();
		if(targetCiIds!=null && targetCiIds.size()>0) {
			endMap.put("ciIdList", targetCiIds.toArray(new String[targetCiIds.size()]));
		}else {
			endMap.put("ciIdList", "");
		}
		if(endSourceTypeId!=null && !"".equals(endSourceTypeId)) {
			endMap.put("sourceTypeId", endSourceTypeId);
		}else {
			endMap.put("sourceTypeId", "");
		}
		if(endTargetTypeId!=null && !"".equals(endTargetTypeId)) {
			endMap.put("targetTypeId", endTargetTypeId);
		}else {
			endMap.put("targetTypeId", "");
		}
		if(endRelId!=null && !"".equals(endRelId)) {
			endMap.put("relId", endRelId);
		}else {
			endMap.put("relId", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			endMap.put("domainId", domainId);
		}else {
			endMap.put("domainId", "");
		}
		//第一个关系和第二个关系都是大于等于或者小于等于
		if("".equals(startMin) && "".equals(startMax) && "".equals(endMin) && "".equals(endMax)) {
			if(!"0".equals(startLineVal) && !"0".equals(endLineVal)) {
				type="AND";
			}else {
				type="OR";
			}
			returnMap=getDataByStartSymbolAndEndSymbol(type,startLineOpName,startLineVal,endLineOpName,endLineVal,startMap,endMap,targetCiIds);
			return returnMap;
		}else {
			if(startMin!=null && !"".equals(startMax) && endMin!=null && !"".equals(endMax)) {
				if("0".equals(startMax) || "0".equals(endMax)) {
					type="OR";
				}else {
					type="AND";
				}
				returnMap=getDataBySectionAll(type,startMin,startMax,endMin,endMax,startMap,endMap,targetCiIds);
				return returnMap;
			}else if((endMin==null || "".equals(endMin)) && (endMax==null || "".equals(endMax))){
				if("0".equals(endLineVal) || "0".equals(startMax)) {
					type="OR";
				}else {
					type="AND";
				}
				returnMap=getDataByStartSectionAndEndSymbol(type,endLineOpName,endLineVal,startMin,startMax,startMap,endMap,targetCiIds);
				return returnMap;
			}else if((startMin==null || "".equals(startMin)) && (startMax==null || "".equals(startMax))) {
				if("0".equals(startLineVal) || "0".equals(endMax)) {
					type="OR";
				}else {
					type="AND";
				}
				returnMap=getDataByStartSymbolAndEndSection(type,startLineOpName,startLineVal,endMin,endMax,startMap,endMap,targetCiIds);
				return returnMap;
			}
			
			return returnMap;
		}
	}
	
	public Map<String, Object> getDataByStartSymbolAndEndSymbol(String type,String startLineOpName,String startLineVal,String endLineOpName,String endLineVal,Map<String,Object> startMap,Map<String,Object> endMap,List<String> targetCiIds){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		List<Map<String,Object>> startList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> endList=new ArrayList<Map<String,Object>>();
		if("AND".equals(type)) {
			startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
			if("大于等于".equals(startLineOpName)) {
				if(startList.size()>=Integer.parseInt(startLineVal)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						List<String> ciIds=new ArrayList<String>();
						for(Map<String,Object> map:startList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							endMap.put("ciIdList", "");
						}						
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}else {
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}
					if("大于等于".equals(endLineOpName)) {
						if(endList.size()>=Integer.parseInt(endLineVal)) {
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							return returnMap;
						}
					}else if("小于等于".equals(endLineOpName)){
						if(endList.size()<=Integer.parseInt(endLineVal) && endList.size()>=0) {
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							return returnMap;
						}
					}
					
					
				}else {
					return returnMap;
				}					
			}else if("小于等于".equals(startLineOpName)) {
				if(startList.size()<=Integer.valueOf(startLineVal) && startList.size()>=0) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:startList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							endMap.put("ciIdList", "");
						}						
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}else {
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}
					if("小于等于".equals(endLineOpName)) {
						if(endList.size()<=Integer.valueOf(endLineVal) && endList.size()>=0) {
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							return returnMap;
						}
					}else if("大于等于".equals(endLineOpName)){
						if(endList.size()>=Integer.valueOf(endLineVal)) {
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							return returnMap;
						}
					}
					
				}else {
					return returnMap;
				}
			}
		}else if("OR".equals(type)) {
			    if("0".equals(startLineVal) && "0".equals(endLineVal)) {
			    	startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
			    	endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
			    	returnMap.put("start", startList);
					returnMap.put("end", endList);
					return returnMap;
			    }else if(!"0".equals(startLineVal)) {
					startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					if("大于等于".equals(startLineOpName)) {
						if(startList.size()>=Integer.valueOf(startLineVal)) {
							if(targetCiIds==null || targetCiIds.size()==0) {
								Set<String> ciIds=new HashSet<String>();
								for(Map<String,Object> map:startList) {
									String sourceCiId=(String) map.get("sourceCiId");
									ciIds.add(sourceCiId);
								}
								if(ciIds!=null && ciIds.size()>0) {
									endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
								}else {
									endMap.put("ciIdList", "");
								}
								endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
							}else {
								endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
							}
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
							returnMap.put("start", new ArrayList<Map<String,Object>>());
							returnMap.put("end", endList);
							return returnMap;
						}
					}else if("小于等于".equals(startLineOpName)){
						if(startList.size()<=Integer.valueOf(startLineVal) && startList.size()>=0) {
							if(targetCiIds==null || targetCiIds.size()==0) {
								Set<String> ciIds=new HashSet<String>();
								for(Map<String,Object> map:startList) {
									String sourceCiId=(String) map.get("sourceCiId");
									ciIds.add(sourceCiId);
								}
								if(ciIds!=null && ciIds.size()>0) {
									endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
								}else {
									endMap.put("ciIdList", "");
								}
								endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
							}else {
								endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
							}
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
							returnMap.put("start", new ArrayList<Map<String,Object>>());
							returnMap.put("end", endList);
							return returnMap;
						}
					}						
				}else {
					endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					if("大于等于".equals(endLineOpName)) {
						if(endList.size()>=Integer.valueOf(endLineVal)) {
							if(targetCiIds==null || targetCiIds.size()==0) {
								Set<String> ciIds=new HashSet<String>();
								for(Map<String,Object> map:endList) {
									String sourceCiId=(String) map.get("sourceCiId");
									ciIds.add(sourceCiId);
								}
								if(ciIds!=null && ciIds.size()>0) {
									startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
								}else {
									startMap.put("ciIdList", "");
								}
								startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
							}else {
								startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
							}
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
							returnMap.put("start", startList);
							returnMap.put("end", new ArrayList<Map<String,Object>>());
							return returnMap;
						}
					}else if("小于等于".equals(endLineOpName)) {
						if(endList.size()<=Integer.valueOf(endLineVal) && endList.size()>=0) {
							if(targetCiIds==null || targetCiIds.size()==0) {
								Set<String> ciIds=new HashSet<String>();
								for(Map<String,Object> map:endList) {
									String sourceCiId=(String) map.get("sourceCiId");
									ciIds.add(sourceCiId);
								}
								if(ciIds!=null && ciIds.size()>0) {
									startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
								}else {
									startMap.put("ciIdList", "");
								}								
								startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
							}else {
								startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
							}
							returnMap.put("start", startList);
							returnMap.put("end", endList);
							return returnMap;
						}else {
							startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
							returnMap.put("start", startList);
							returnMap.put("end", new ArrayList<Map<String,Object>>());
							return returnMap;
						}
					}
					
				}			
		}
		return returnMap;
	}
	
	public Map<String, Object> getDataBySectionAll(String type,String startMin,String startMax,String endMin,String endMax,Map<String,Object> startMap,Map<String,Object> endMap,List<String> targetCiIds){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		List<Map<String,Object>> startList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> endList=new ArrayList<Map<String,Object>>();
		if("AND".equals(type)) {
			startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
			if(Integer.parseInt(startMin)<=startList.size() && startList.size()<=Integer.parseInt(startMax)) {
				if(targetCiIds==null || targetCiIds.size()==0) {
					Set<String> ciIds=new HashSet<String>();
					for(Map<String,Object> map:startList) {
						String sourceCiId=(String) map.get("sourceCiId");
						ciIds.add(sourceCiId);
					}
					if(ciIds!=null && ciIds.size()>0) {
						endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
					}else {
						endMap.put("ciIdList", "");
					}					
					endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
				}else {
					endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
				}
				if(Integer.parseInt(endMin)<=endList.size() && endList.size()<=Integer.parseInt(endMax)) {
					returnMap.put("start", startList);
					returnMap.put("end", endList);
					return returnMap;
				}else {
					return returnMap;
				}						
			}else {
				return returnMap;
			}
		}else if("OR".equals(type)) {
			if(!"0".equals(startMax)) {
				startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
				if(Integer.valueOf(startMin)<=startList.size() && startList.size()<=Integer.valueOf(startMax)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:startList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							endMap.put("ciIdList", "");
						}						
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}else {
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}
					returnMap.put("start", startList);
					returnMap.put("end", endList);
					return returnMap;
				}else {
					return returnMap;
				}
			}else {
				endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
				if(Integer.valueOf(endMin)<=endList.size() && endList.size()<=Integer.valueOf(endMax)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:endList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							startMap.put("ciIdList", "");
						}						
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}else {
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}
					returnMap.put("start", startList);
					returnMap.put("end", endList);
					return returnMap;
				}else {
					return returnMap;
				}
			}
		}
		return null;
	}
	public Map<String,Object> getDataByStartSymbolAndEndSection(String type,String startLineOpName,String startLineVal,String endMin,String endMax,Map<String,Object> startMap,Map<String,Object> endMap,List<String> targetCiIds){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		List<Map<String,Object>> startList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> endList=new ArrayList<Map<String,Object>>();
		if("AND".equals(type)) {
			startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
			if("大于等于".equals(startLineOpName)) {
				if(startList.size()>=Integer.parseInt(startLineVal)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:startList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							endMap.put("ciIdList", "");
						}
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}else {
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}
					if(Integer.parseInt(endMin)<=endList.size() && endList.size()<=Integer.parseInt(endMax)) {
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}else {
					return returnMap;
				}
			}else if("小于等于".equals(startLineOpName)) {
				if(startList.size()<=Integer.valueOf(startLineVal) && startList.size()>0) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:startList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							endMap.put("ciIdList", "");
						}
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}else {
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}
					if(Integer.valueOf(endMin)<=endList.size() && endList.size()<=Integer.valueOf(endMax)) {
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}else {
					return returnMap;
				}
			}
		}else if("OR".equals(type)) {
			if(!"0".equals(startLineVal)) {
				startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
				if("大于等于".equals(startLineOpName)) {
					if(startList.size()>=Integer.valueOf(startLineVal)) {
						if(targetCiIds==null || targetCiIds.size()==0) {
							Set<String> ciIds=new HashSet<String>();
							for(Map<String,Object> map:startList) {
								String sourceCiId=(String) map.get("sourceCiId");
								ciIds.add(sourceCiId);
							}
							if(ciIds!=null && ciIds.size()>0) {
								endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
							}else {
								endMap.put("ciIdList", "");
							}
							endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
						}else {
							endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
						}
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}else if("小于等于".equals(startLineOpName)){
					if(startList.size()<=Integer.valueOf(startLineVal) && startList.size()>0) {
						if(targetCiIds==null || targetCiIds.size()==0) {
							Set<String> ciIds=new HashSet<String>();
							for(Map<String,Object> map:startList) {
								String sourceCiId=(String) map.get("sourceCiId");
								ciIds.add(sourceCiId);
							}
							if(ciIds!=null && ciIds.size()>0) {
								endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
							}else {
								endMap.put("ciIdList", "");
							}
							endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
						}else {
							endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
						}
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}
			}else {
				endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
				if(Integer.valueOf(endMin)<=endList.size() && endList.size()<=Integer.valueOf(endMax)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:endList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							startMap.put("ciIdList", "");
						}
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}else {
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}
					returnMap.put("start", startList);
					returnMap.put("end", endList);
					return returnMap;
				}else {
					return returnMap;
				}
			}
		}
		return null;
	}
	
	public Map<String,Object> getDataByStartSectionAndEndSymbol(String type,String endLineOpName,String endLineVal,String startMin,String startMax,Map<String,Object> startMap,Map<String,Object> endMap,List<String> targetCiIds){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		List<Map<String,Object>> startList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> endList=new ArrayList<Map<String,Object>>();
		if("AND".equals(type)) {
			endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
			if("大于等于".equals(endLineOpName)) {
				if(endList.size()>=Integer.parseInt(endLineVal)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:endList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							startMap.put("ciIdList", "");
						}						
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}else {
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}
					if(Integer.parseInt(startMin)<=startList.size() && startList.size()<=Integer.parseInt(startMax)) {
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}else {
					return returnMap;
				}
			}else if("小于等于".equals(endLineOpName)) {
				if(endList.size()<=Integer.valueOf(endLineVal) && endList.size()>0) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:endList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							startMap.put("ciIdList", "");
						}
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}else {
						startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
					}
					if(Integer.valueOf(startMin)<=startList.size() && startList.size()<=Integer.valueOf(startMax)) {
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}else {
					return returnMap;
				}
			}
		}else if("OR".equals(type)) {
			if(!"0".equals(endLineVal)) {
				endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
				if("大于等于".equals(endLineOpName)) {
					if(endList.size()>=Integer.valueOf(endLineVal)) {
						if(targetCiIds==null || targetCiIds.size()==0) {
							Set<String> ciIds=new HashSet<String>();
							for(Map<String,Object> map:endList) {
								String sourceCiId=(String) map.get("sourceCiId");
								ciIds.add(sourceCiId);
							}
							if(ciIds!=null && ciIds.size()>0) {
								startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
							}else {
								startMap.put("ciIdList", "");
							}
							startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
						}else {
							startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
						}
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}else if("小于等于".equals(endLineOpName)){
					if(endList.size()<=Integer.valueOf(endLineVal) && endList.size()>0) {
						if(targetCiIds==null || targetCiIds.size()==0) {
							Set<String> ciIds=new HashSet<String>();
							for(Map<String,Object> map:endList) {
								String sourceCiId=(String) map.get("sourceCiId");
								ciIds.add(sourceCiId);
							}
							if(ciIds!=null && ciIds.size()>0) {
								startMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
							}else {
								startMap.put("ciIdList", "");
							}
							startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
						}else {
							startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
						}
						returnMap.put("start", startList);
						returnMap.put("end", endList);
						return returnMap;
					}else {
						return returnMap;
					}
				}
			}else {
				startList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(startMap);
				if(Integer.valueOf(startMin)<=startList.size() && startList.size()<=Integer.valueOf(startMax)) {
					if(targetCiIds==null || targetCiIds.size()==0) {
						Set<String> ciIds=new HashSet<String>();
						for(Map<String,Object> map:startList) {
							String sourceCiId=(String) map.get("sourceCiId");
							ciIds.add(sourceCiId);
						}
						if(ciIds!=null && ciIds.size()>0) {
							endMap.put("ciIdList", ciIds.toArray(new String[ciIds.size()]));
						}else {
							endMap.put("ciIdList", "");
						}						
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}else {
						endList=dataRelDao.getCiDataRelBySourceIdAndTargetIdAndRelId(endMap);
					}
					returnMap.put("start", startList);
					returnMap.put("end", endList);
					return returnMap;
				}else {
					return returnMap;
				}
			}
		}
		return null;
	}
	
	public Map<String, Object> getCiDataRelListByIdsUp(String startLineOpName,String startLineVal,String startMin,String startMax,String startRelId,String startSourceTypeId,String startTargetTypeId,String endLineOpName
			,String endLineVal,String endMin,String endMax,String endRelId,String endSourceTypeId,String endTargetTypeId,List<String> sourceCiIds,String type){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		if("大于等于".equals(startLineOpName)) {
			startLineOpName=">=";
		}else if("小于等于".equals(startLineOpName)) {
			startLineOpName="<=";
		}
		if("大于等于".equals(endLineOpName)) {
			endLineOpName=">=";
		}else if("小于等于".equals(endLineOpName)) {
			endLineOpName="<=";
		}
		Map<String, Object> itemMap = new HashMap<String, Object>();
		if(startMin!=null && !"".equals(startMin)) {
			itemMap.put("startMin", startMin);
		}else {
			itemMap.put("startMin", "");
		}
		if(startMax!=null && !"".equals(startMax)) {
			itemMap.put("startMax", startMax);
		}else {
			itemMap.put("startMax", "");
		}
		if(sourceCiIds!=null && sourceCiIds.size()>0) {
			itemMap.put("ciIdList", sourceCiIds.toArray(new String[sourceCiIds.size()]));
		}else {
			itemMap.put("ciIdList", "");
		}
		if(startSourceTypeId!=null && !"".equals(startSourceTypeId)) {
			itemMap.put("startSourceTypeId", startSourceTypeId);
		}else {
			itemMap.put("startSourceTypeId", "");
		}
		if(startTargetTypeId!=null && !"".equals(startTargetTypeId)) {
			itemMap.put("startTargetTypeId", startTargetTypeId);
		}else {
			itemMap.put("startTargetTypeId", "");
		}
		if(startRelId!=null && !"".equals(startRelId)) {
			itemMap.put("startRelId", startRelId);
		}else {
			itemMap.put("startRelId", "");
		}
		if(startLineOpName!=null && !"".equals(startLineOpName)) {
			itemMap.put("startSymbol", startLineOpName);
		}else {
			itemMap.put("startSymbol", "");
		}
		if(startLineVal!=null && !"".equals(startLineVal)) {
			itemMap.put("startLineVal", startLineVal);
		}else {
			itemMap.put("startLineVal", "");
		}
		if(type!=null && !"".equals(type)) {
			itemMap.put("ao", type);
		}else {
			itemMap.put("ao", "");
		}
		if(endMin!=null && !"".equals(endMin)) {
			itemMap.put("endMin", endMin);
		}else {
			itemMap.put("endMin", "");
		}
		if(endMax!=null && !"".equals(endMax)) {
			itemMap.put("endMax", endMax);
		}else {
			itemMap.put("endMax", "");
		}
		if(endSourceTypeId!=null && !"".equals(endSourceTypeId)) {
			itemMap.put("endSourceTypeId", endSourceTypeId);
		}else {
			itemMap.put("endSourceTypeId", "");
		}
		if(endTargetTypeId!=null && !"".equals(endTargetTypeId)) {
			itemMap.put("endTargetTypeId", endTargetTypeId);
		}else {
			itemMap.put("endTargetTypeId", "");
		}
		if(endRelId!=null && !"".equals(endRelId)) {
			itemMap.put("endRelId", endRelId);
		}else {
			itemMap.put("endRelId", "");
		}
		if(endLineOpName!=null && !"".equals(endLineOpName)) {
			itemMap.put("endSymbol", endLineOpName);
		}else {
			itemMap.put("endSymbol", "");
		}
		if(endLineVal!=null && !"".equals(endLineVal)) {
			itemMap.put("endLineVal", endLineVal);
		}else {
			itemMap.put("endLineVal", "");
		}
		Map<String, Object> startMap = new HashMap<String, Object>();
		if(sourceCiIds!=null && sourceCiIds.size()>0) {
			startMap.put("ciIdList", sourceCiIds.toArray(new String[sourceCiIds.size()]));
		}else {
			startMap.put("ciIdList", "");
		}
		if(startSourceTypeId!=null && !"".equals(startSourceTypeId)) {
			startMap.put("sourceTypeId", startSourceTypeId);
		}else {
			startMap.put("sourceTypeId", "");
		}
		if(startTargetTypeId!=null && !"".equals(startTargetTypeId)) {
			startMap.put("targetTypeId", startTargetTypeId);
		}else {
			startMap.put("targetTypeId", "");
		}
		if(startRelId!=null && !"".equals(startRelId)) {
			startMap.put("relId", startRelId);
		}else {
			startMap.put("relId", "");
		}
		Map<String, Object> endMap = new HashMap<String, Object>();
		if(sourceCiIds!=null && sourceCiIds.size()>0) {
			endMap.put("ciIdList", sourceCiIds.toArray(new String[sourceCiIds.size()]));
		}else {
			endMap.put("ciIdList", "");
		}
		if(endSourceTypeId!=null && !"".equals(endSourceTypeId)) {
			endMap.put("sourceTypeId", endSourceTypeId);
		}else {
			endMap.put("sourceTypeId", "");
		}
		if(endTargetTypeId!=null && !"".equals(endTargetTypeId)) {
			endMap.put("targetTypeId", endTargetTypeId);
		}else {
			endMap.put("targetTypeId", "");
		}
		if(endRelId!=null && !"".equals(endRelId)) {
			endMap.put("relId", endRelId);
		}else {
			endMap.put("relId", "");
		}
		if("".equals(startMin) && "".equals(startMax) && "".equals(endMin) && "".equals(endMax)) {			
			int returnVal=dataRelDao.getCiDataRelCountByRuleUp(itemMap);			
			if(returnVal>0) {				
				List<Map<String,Object>> startList=dataRelDao.getCiDataRelByTargetIdAndSourceIdAndRelId(startMap);				
				List<Map<String,Object>> endList=dataRelDao.getCiDataRelByTargetIdAndSourceIdAndRelId(endMap);				
				returnMap.put("start", startList);
				returnMap.put("end", endList);
				return returnMap;
			}
			return returnMap;
		}else {
			int returnVal=0;
			if(startMin!=null && !"".equals(startMax) && endMin!=null && !"".equals(endMax)) {				
				returnVal=dataRelDao.getCiDataRelCountByMinToMaxAllUp(itemMap);
			}else if(endMin==null || "".equals(endMin) || endMax==null || "".equals(endMax)){
				returnVal=dataRelDao.getCiDataRelCountByMinToMaxStartUp(itemMap);
			}else if(startMin==null || "".equals(startMin) || startMax==null || "".equals(startMax)) {
				returnVal=dataRelDao.getCiDataRelCountByMinToMaxEndUp(itemMap);
			}
			if(returnVal>0) {
				List<Map<String,Object>> startList=dataRelDao.getCiDataRelByTargetIdAndSourceIdAndRelId(startMap);				
				List<Map<String,Object>> endList=dataRelDao.getCiDataRelByTargetIdAndSourceIdAndRelId(endMap);				
				returnMap.put("start", startList);
				returnMap.put("end", endList);
				return returnMap;
			}
			return returnMap;
		}
	}
	public List<Map<String, Object>> getSelfDataList(List<Map<String, Object>> ciRltLineListSelf,List<Map<String, Object>> returnList,List<Map<String,Object>> dataDict){
			for(Map<String, Object> map:ciRltLineListSelf) {
				String sourceTypeId=(String) map.get("startTypeId");
				String targetTypeId=(String) map.get("endTypeId");
				if(sourceTypeId.equals(targetTypeId)) {
					returnList.add(map);
				}
			}
			if(returnList!=null && returnList.size()>0) {
				for(Map<String, Object> map:returnList) {
					String sourceTypeId=(String) map.get("startTypeId");
					String relId=(String) map.get("rltId");
					Map<String, Object> itemMap = new HashMap<String, Object>();
	    			List<Map<String, Object>> relDataList=new ArrayList<Map<String,Object>>();	    			
	    			itemMap.put("sourceCiIdList", "");
	    			itemMap.put("targetCiIdList", "");
	    			if(relId!=null && !"".equals(relId)) {
	    				itemMap.put("relId", relId);
	    			}else {
	    				itemMap.put("relId", "");
	    			}    				
	    			if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
	    				itemMap.put("sourceTypeId", sourceTypeId);
	    			}else {
	    				itemMap.put("sourceTypeId", "");
	    			}
	    			if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
	    				itemMap.put("targetTypeId", sourceTypeId);
	    			}else {
	    				itemMap.put("targetTypeId", "");
	    			}
	    			relDataList=ciTypeRelDao.getCiDataRelByCiIdAndTypeId(itemMap);
	    			String lineOp=(String) map.get("lineOp");
					String lineVal=(String) map.get("lineVal");
					boolean flag=lineVal.contains(",");
					String min="";
					String max="";
					if(flag) {
						String[] split = lineVal.split(",");
						for (int i = 0; i < split.length; i++) {
							if(i==0) {
								min=split[i];
							}else {
								max=split[i];
							}
							
						}
					}
					String lineOpName="";
					for(Map<String, Object> mapDataDict:dataDict) {
						Long dictId=(Long) mapDataDict.get("dictId");
						String dictBm=(String) mapDataDict.get("dictBm");
						if(lineOp.equals(String.valueOf(dictId))) {
							lineOpName=dictBm;
						}
					}
					if("大于等于".equals(lineOpName)) {
						if(Integer.parseInt(lineVal)!=0 && relDataList.size()<Integer.parseInt(lineVal)) {
							map.put("relData", new ArrayList<Map<String,Object>>());
						}else {
							map.put("relData", relDataList);
						}
					}else if("小于等于".equals(lineOpName)) {
						if(Integer.parseInt(lineVal)!=0 && relDataList.size()>Integer.parseInt(lineVal)) {
							map.put("relData", new ArrayList<Map<String,Object>>());
						}else {
							map.put("relData", relDataList);
						}
					}else if("区间".equals(lineOpName)) {
						if(relDataList.size()<Integer.parseInt(min) || relDataList.size()>Integer.parseInt(max)) {
							map.put("relData", new ArrayList<Map<String,Object>>());
						}else {
							map.put("relData", relDataList);
						}
					}				
				}
				for(Map<String, Object> mapRlt:ciRltLineListSelf) {
					String id=(String) mapRlt.get("id");
					for(Map<String, Object> mapReturn:returnList){
						String idStr=(String) mapReturn.get("id");
						List<Map<String, Object>> list=(List<Map<String, Object>>) mapReturn.get("relData");
						if(id.equals(idStr)) {
							mapRlt.put("relData", list);
						}
					}
				}
			}
		    return ciRltLineListSelf;
	}
	
	public List<Map<String, Object>> getOrgDataBySourceTypeIdAndTargetTypeId(String sourceTypeId,String targetTypeId,List<Map<String,Object>> cdtList,List<Map<String,Object>> relDataList,List<Map<String,Object>> dataDictNode){
		//查询起点大类属性
		List<Map<String,Object>> itemListStart=typeItemService.findItemByTidToHump(sourceTypeId);
		Map itemMapStart=new HashMap<>();
		itemMapStart.put("search", "");
		itemMapStart.put("tid", sourceTypeId);
		//过滤节点的条件
		List<Map<String,Object>> itemScreenCriteriaStart=getItemScreenCriteria(sourceTypeId,cdtList,itemListStart,dataDictNode);
		//查询起点类数据
		Map dataMapStart=typeItemService.findDataNoPageToHump(itemMapStart,itemScreenCriteriaStart);
		//查询终点大类属性
		List<Map<String,Object>> itemListEnd=typeItemService.findItemByTidToHump(targetTypeId);
		Map itemMapEnd=new HashMap<>();
		itemMapEnd.put("search", "");
		itemMapEnd.put("tid", targetTypeId);
		//过滤节点的条件
		List<Map<String,Object>> itemScreenCriteriaEnd=getItemScreenCriteria(targetTypeId,cdtList,itemListEnd,dataDictNode);
		//查询终点类数据
		Map dataMapEnd=typeItemService.findDataNoPageToHump(itemMapEnd,itemScreenCriteriaEnd);
		List<Map<String,Object>> startDatas=(List<Map<String, Object>>) dataMapStart.get("rows");
		List<Map<String,Object>> endDatas=(List<Map<String, Object>>) dataMapEnd.get("rows");
		List<Map<String,Object>> organizeDatas=new ArrayList<Map<String,Object>>();
		if(startDatas!=null && startDatas.size()>0 && endDatas!=null && endDatas.size()>0) {
			for(Map<String,Object> mapRel:relDataList) {
				String sourceCiId=(String) mapRel.get("sourceCiId");
				String targetCiId=(String) mapRel.get("targetCiId");
				for(Map<String,Object> mapDataStart:startDatas) {
					String idStart=(String) mapDataStart.get("id");
					if(sourceCiId.equals(idStart)) {
						for(Map<String,Object> mapDataEnd:endDatas) {
							String idEnd=(String) mapDataEnd.get("id");
							if(targetCiId.equals(idEnd)) {
									organizeDatas.add(mapRel);
							}
						}
					}
				}								
			}

		}
		else {
			return 	organizeDatas;
		}
		return organizeDatas;
	}
	
	@Override
	public List<Map<String, Object>> getItemDataAllByruleId(String ruleId) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		// 节点
		IomCiRltNodeExample iomCiRltNodeExample = new IomCiRltNodeExample();
		if(domainId==null) {
			iomCiRltNodeExample.createCriteria().andRuleIdEqualTo(ruleId);
		}else {
			iomCiRltNodeExample.createCriteria().andRuleIdEqualTo(ruleId).andDomainIdIn(Arrays.asList(domainId.split(",")));
		}		
		List<IomCiRltNode> iomCiRltNodes = iomCiRltNodeMapper.selectByExample(iomCiRltNodeExample);
		Set<String> typeIds=new HashSet<String>();
		if(iomCiRltNodes!=null && iomCiRltNodes.size()>0) {
			for(IomCiRltNode iomCiRltNode:iomCiRltNodes) {
				String typeId=iomCiRltNode.getNodeTypeId();
				typeIds.add(typeId);
			}
		}
		Object[] array = typeIds.toArray();
	    String splitTids = StringUtils.join(array, ",");
		List<Map<String,Object>> typeDatas=typeService.findCiTypeListHumpByIds(splitTids);
		if(typeDatas!=null && typeDatas.size()>0) {
			for(Map<String,Object> map:typeDatas) {
				String tid=(String) map.get("id");
				List<TypeItem> list=typeItemService.findItemByTid(tid);
				List<Map<String,Object>> listHump=new ArrayList<Map<String,Object>>();
				for(TypeItem typeItem:list) {
					try {
						Map<String,Object> mapHump=bean2map(typeItem);
						String mp_ci_item=(String) mapHump.get("mp_ci_item");
						String hump=StringUtil.underlineToCamel(mp_ci_item);
						mapHump.put("mp_ci_item", hump);
						listHump.add(mapHump);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				map.put("items", listHump);
			}
		}
		return typeDatas;
	}
	public List<Map<String, Object>> getDataList(List<String> typeIds,List<Map<String,Object>> ciRltLineList,List<Map<String, Object>> dataDict){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(String typeId:typeIds){
			for(Map<String,Object> map:ciRltLineList){
				String startTypeId=(String) map.get("startTypeId");
				if(typeId.equals(startTypeId)) {
					list.add(map);
				}
			}
		}	
		if(list!=null && list.size()>0) {
			if(list.size()==1) {
				Map<String,Object> map=list.get(0);
				List<Map<String, Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
				String lineOp=(String) map.get("lineOp");
				String lineVal=(String) map.get("lineVal");
				boolean flag=lineVal.contains(",");
				String min="";
				String max="";
				if(flag) {
					String[] split = lineVal.split(",");
					for (int i = 0; i < split.length; i++) {
						if(i==0) {
							min=split[i];
						}else {
							max=split[i];
						}
						
					}
				}
				String lineOpName="";
				for(Map<String, Object> mapDataDict:dataDict) {
					String dictId=(String) mapDataDict.get("dictId");
					String dictBm=(String) mapDataDict.get("dictBm");
					if(lineOp.equals(dictId)) {
						lineOpName=dictBm;
					}
				}
				if("大于等于".equals(lineOpName)) {
					if(Integer.parseInt(lineVal)!=0 && relDataList.size()<Integer.parseInt(lineVal)) {
						map.put("relData", new ArrayList<Map<String,Object>>());
					}
				}else if("小于等于".equals(lineOpName)) {
					if(Integer.parseInt(lineVal)!=0 && relDataList.size()>Integer.parseInt(lineVal)) {
						map.put("relData", new ArrayList<Map<String,Object>>());
					}
				}else if("区间".equals(lineOpName)) {
					if(relDataList.size()<Integer.parseInt(min) || relDataList.size()>Integer.parseInt(max)) {
						map.put("relData", new ArrayList<Map<String,Object>>());
					}
				}
				
			}else if(list.size()>1) {
				
			}
		}
		return null;
	}
	/**
     * 将 List<Map>对象转化为List<JavaBean>
     * @author loulan

     * @return Object对象
     */
    public static <T> List<T> convertListMap2ListBean(List<Map<String,Object>> listMap, Class T) throws Exception {
        List<T> beanList = new ArrayList<T>();
        for(int i=0, n=listMap.size(); i<n; i++){
            Map<String,Object> map = listMap.get(i);
            T bean = convertMap2Bean(map,T);
            beanList.add(bean);
        }
        return beanList;
    }
	/**
     * 将 Map对象转化为JavaBean
     * @author loulan
     * @param map
     * @return Object对象
     */
    public static <T> T convertMap2Bean(Map map, Class T) throws Exception {
        if(map==null || map.size()==0){
            return null;
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        T bean = (T)T.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            //将字母大写，这里先注掉，因为本例子直接读取名字匹配即可。
            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
               //用这个方法对时间等类型会报参数类型不匹配错误，也需要我们手动判断类型进行转换，比较麻烦。
               BeanUtils.setProperty(bean, propertyName, value);
            }
        }
        return bean;
    }
    
	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: 把JavaBean转化为map
	 */
    public static Map<String, Object> bean2map(Object bean) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取JavaBean的描述器
        BeanInfo b = Introspector.getBeanInfo(bean.getClass(), Object.class);
        //获取属性描述器
        PropertyDescriptor[] pds = b.getPropertyDescriptors();
        //对属性迭代
        for (PropertyDescriptor pd : pds) {
            //属性名称
            String propertyName = pd.getName();
            //属性值,用getter方法获取
            Method m = pd.getReadMethod();
			//用对象执行getter方法获得属性值
            Object properValue = m.invoke(bean);

            //把属性名-属性值 存到Map中
            map.put(propertyName, properValue);
        }
        return map;
    }
    
    private static List<Map<String, Object>> filterListBySourceIdAndTargetId(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("startTypeId").equals(list.get(j).get("startTypeId")) && map1.get("endTypeId").equals(list.get(j).get("endTypeId")) && map1.get("rltId").equals(list.get(j).get("rltId"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
    private static List<Map<String, Object>> filterListBySourceCiIdAndTargetCiId(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("sourceCiId").equals(list.get(j).get("sourceCiId")) && map1.get("targetCiId").equals(list.get(j).get("targetCiId")) && map1.get("relId").equals(list.get(j).get("relId"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
    
    @Override
    public List<String> getCiRelByLineBm(String lineBm){
    	List<String> listString = Arrays.asList(lineBm.split(","));
    	String [] relArray = listString.toArray(new String[listString.size()]);
    	Map<String,Object> itemMap=new HashMap<String,Object>();
    	itemMap.put("relNameList", relArray);
    	List<String> list=rltRuleDao.getCiRelByLineBm(itemMap);
    	return list;
    }
    
    private static List<Map<String, Object>> filterListById(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("id").equals(list.get(j).get("id"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }

}
