package com.integration.service.impl;

import com.google.common.base.Joiner;
import com.integration.dao.CiTypeRelDao;
import com.integration.entity.PageResult;
import com.integration.generator.dao.*;
import com.integration.generator.entity.*;
import com.integration.generator.entity.IomCiTypeFocusRelExample.Criteria;
import com.integration.service.CiTypeRelService;
import com.integration.service.TypeItemService;
import com.integration.utils.DataUtils;
import com.integration.utils.MyPagUtile;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-11 05:46:51
 */
@Transactional
@Service
public class CiTypeRelServiceImpl implements CiTypeRelService {

    @Autowired
    private CiTypeRelDao ciTypeRelDao;

    @Resource
    private IomCiTypeRelMapper iomCiTypeRelMapper;
    
    @Resource
    private IomCiTypeMapper iomCiTypeMapper;
    
    @Resource
    private IomCiRelMapper iomCiRelMapper;
    
    @Resource
    private IomCiTypeFocusRelMapper iomCiTypeFocusRelMapper;
    
    @Resource
    private IomCiTypeRelDiagramMapper iomCiTypeRelDiagramMapper;
    
    @Autowired
    private TypeItemService typeItemService;
    
    @Override
    public Integer save(List list){
		String domainId = TokenUtils.getTokenOrgDomainId();
    	//删除老数据
		IomCiTypeRelExample iomCiTypeRelExample = new IomCiTypeRelExample();
		com.integration.generator.entity.IomCiTypeRelExample.Criteria criteria = iomCiTypeRelExample.createCriteria();
		if (domainId!=null){
			criteria.andDomainIdEqualTo(domainId);
		}
    	iomCiTypeRelMapper.deleteByExample(iomCiTypeRelExample);
    	//存入新的数据
    	if(list!=null && list.size()>0) {
    		ciTypeRelDao.insertByBatch(list);
    	}
    	//清理无用的关系
		List<Long> idLongs = ciTypeRelDao.selectInvalidFocus();
		if (idLongs!=null&&idLongs.size()>0) {
			List<String> idsList = new ArrayList<String>();
			for (Long long1 : idLongs) {
				idsList.add(long1.toString());
			}
			IomCiTypeFocusRelExample iomCiTypeFocusRelExample = new IomCiTypeFocusRelExample();
			iomCiTypeFocusRelExample.createCriteria().andIdIn(idsList);
			iomCiTypeFocusRelMapper.deleteByExample(iomCiTypeFocusRelExample);
		}
    	return 0;
    }
	@Override
    public List<IomCiTypeRel> selectCiTypeRels(){
    	String domainId = TokenUtils.getTokenOrgDomainId();
    	IomCiTypeRelExample iomCiTypeRelExample = new IomCiTypeRelExample();
    	com.integration.generator.entity.IomCiTypeRelExample.Criteria criteria = iomCiTypeRelExample.createCriteria();
    	criteria.andYxbzEqualTo(1);
    	if (domainId!=null){
			criteria.andDomainIdEqualTo(domainId);
		}else{
    		return null;
		}
    	return iomCiTypeRelMapper.selectByExample(iomCiTypeRelExample);
    }
	@Override
    public List<IomCiType> selectCiTypes(String ciTypeMc){
    	IomCiTypeExample iomCiTypeExample = new IomCiTypeExample();
    	com.integration.generator.entity.IomCiTypeExample.Criteria criteria = iomCiTypeExample.createCriteria();
    	if (StringUtils.isNotEmpty(ciTypeMc)) {
    		criteria.andCiTypeMcLike("%"+ciTypeMc+"%");
		}
    	criteria.andYxbzEqualTo(1);
    	return iomCiTypeMapper.selectByExample(iomCiTypeExample);
    }
	@Override
    public List<IomCiRel> selectCiRels(String lineName){
    	//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
    	IomCiRelExample iomCiRelExample = new IomCiRelExample();
    	com.integration.generator.entity.IomCiRelExample.Criteria criteria = iomCiRelExample.createCriteria();
    	if (StringUtils.isNotEmpty(lineName)) {
    		criteria.andLineNameLike("%"+lineName+"%");
    	}
    	if (domainId !=null){
    		criteria.andDomainIdIn(Arrays.asList(domainId.split(",")));
		}
    	criteria.andYxbzEqualTo(1);
    	return iomCiRelMapper.selectByExample(iomCiRelExample);
    }

	@Override
	public IomCiTypeRel getIomCiTypeRel(String source_type_id, String target_type_id) {
			
		return ciTypeRelDao.getSourceTypeIdAndTargetTypeId(source_type_id, target_type_id);
	}

	@Override
	public Integer getIomCiTypeRelBySourceIdAndTargetId(IomCiTypeFocusRel iomCiTypeFocusRel) {
		IomCiTypeFocusRelExample example=new IomCiTypeFocusRelExample();
		Criteria criteria=example.createCriteria();
		criteria.andSourceTypeIdEqualTo(iomCiTypeFocusRel.getSourceTypeId());
		criteria.andTargetTypeIdEqualTo(iomCiTypeFocusRel.getTargetTypeId());
		criteria.andRltIdEqualTo(iomCiTypeFocusRel.getRltId());
		criteria.andYxbzEqualTo(1);
		criteria.andTypeIdEqualTo(iomCiTypeFocusRel.getTypeId());
		IomCiTypeFocusRel iomCiTypeFocusRel1 = new IomCiTypeFocusRel();
		iomCiTypeFocusRel1.setYxbz(0);
		iomCiTypeFocusRel1.setDomainId(iomCiTypeFocusRel.getDomainId());
		return iomCiTypeFocusRelMapper.updateByExampleSelective(iomCiTypeFocusRel1, example);
	}

	@Override
	public Integer insert(IomCiTypeFocusRel iomCiTypeFocusRel) {
		
		IomCiTypeFocusRelExample example=new IomCiTypeFocusRelExample();
		Criteria criteria=example.createCriteria();
		criteria.andTypeIdEqualTo(iomCiTypeFocusRel.getTypeId());
		criteria.andSourceTypeIdEqualTo(iomCiTypeFocusRel.getSourceTypeId());
		criteria.andTargetTypeIdEqualTo(iomCiTypeFocusRel.getTargetTypeId());
		criteria.andRltIdEqualTo(iomCiTypeFocusRel.getRltId());
		criteria.andYxbzEqualTo(1);
		if (iomCiTypeFocusRel.getDomainId()!=null){
			criteria.andDomainIdEqualTo(iomCiTypeFocusRel.getDomainId());
		}
		List<IomCiTypeFocusRel> list=iomCiTypeFocusRelMapper.selectByExample(example);
		if(list==null || list.size()==0) {
			return iomCiTypeFocusRelMapper.insertSelective(iomCiTypeFocusRel);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getCiTypeRelByTypeId(String typeId) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<Map<String,Object>> ciTypeRels=ciTypeRelDao.getCiTypeRelByTypeId(typeId,domainId);
		if (ciTypeRels!=null && ciTypeRels.size()>0) {
			IomCiTypeFocusRelExample example=new IomCiTypeFocusRelExample();
			Criteria criteria=example.createCriteria();
			criteria.andTypeIdEqualTo(typeId);
			
			criteria.andYxbzEqualTo(1);
			if (domainId != null){
				criteria.andDomainIdIn(Arrays.asList(domainId.split(",")));
			}
			List<IomCiTypeFocusRel> iomCiTypeFocusRels=iomCiTypeFocusRelMapper.selectByExample(example);
			
			for (Map<String,Object> iomCiTypeRel : ciTypeRels) {
				String name = null;
				String sourceTypeId=iomCiTypeRel.get("sourceTypeId")==null?"":iomCiTypeRel.get("sourceTypeId").toString();
				String targetTypeId =iomCiTypeRel.get("targetTypeId")==null?"":iomCiTypeRel.get("targetTypeId").toString();
				String sourceTypeName=iomCiTypeRel.get("sourceTypeName")==null?"":iomCiTypeRel.get("sourceTypeName").toString();
				String targetTypeName=iomCiTypeRel.get("targetTypeName")==null?"":iomCiTypeRel.get("targetTypeName").toString();
				String relId = iomCiTypeRel.get("relId").toString();
				if(typeId.equals(sourceTypeId)) {
					name = targetTypeName;
				}else {
					name = sourceTypeName;
				}
				iomCiTypeRel.put("name", name);
				iomCiTypeRel.put("checked", false);
				for (IomCiTypeFocusRel iomCiTypeFocusRel : iomCiTypeFocusRels) {
					if(sourceTypeId.equals(iomCiTypeFocusRel.getSourceTypeId())
							&&targetTypeId.equals(iomCiTypeFocusRel.getTargetTypeId())
							&&relId.equals(iomCiTypeFocusRel.getRltId())) {
						iomCiTypeRel.put("checked", true);
						break;
					}
					
				}
			}
		}
		return ciTypeRels;
	}
	
	@Override
	public Integer saveIomCiTypeRelDiagram(IomCiTypeRelDiagram iomCiTypeRelDiagram) {
		String userId = TokenUtils.getTokenUserId();
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		String orgDomainId = TokenUtils.getTokenOrgDomainId();
		IomCiTypeRelDiagramExample example=new IomCiTypeRelDiagramExample();
		com.integration.generator.entity.IomCiTypeRelDiagramExample.Criteria criteria=example.createCriteria();		
		criteria.andDiagTypeEqualTo(1);
		criteria.andYxbzEqualTo(1);
		if (domainId !=null){
			criteria.andDomainIdIn(Arrays.asList(domainId.split(",")));
		}
		List<IomCiTypeRelDiagram> list=iomCiTypeRelDiagramMapper.selectByExample(example);
		iomCiTypeRelDiagram.setYxbz(1);
		if(list==null || list.size()==0) {
			iomCiTypeRelDiagram.setId(SeqUtil.nextId() + "");
			iomCiTypeRelDiagram.setUserId(userId);
			iomCiTypeRelDiagram.setCjrId(userId);
			iomCiTypeRelDiagram.setCjsj(new Date());
			iomCiTypeRelDiagram.setDiagType(1);
			iomCiTypeRelDiagram.setDomain_id(orgDomainId==null?"-1":orgDomainId);
			Integer i=iomCiTypeRelDiagramMapper.insertSelective(iomCiTypeRelDiagram);
			return i;
		}else {
			iomCiTypeRelDiagram.setXgrId(userId);
			iomCiTypeRelDiagram.setXgsj(new Date());
			iomCiTypeRelDiagram.setDomain_id(orgDomainId==null?"-1":orgDomainId);
			Integer i=iomCiTypeRelDiagramMapper.updateByExampleSelective(iomCiTypeRelDiagram, example);
			return i;
		}	
	}
	
	@Override
	public IomCiTypeRelDiagram getIomCiTypeRelDiagram() {
    	//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		IomCiTypeRelDiagramExample example=new IomCiTypeRelDiagramExample();
		com.integration.generator.entity.IomCiTypeRelDiagramExample.Criteria criteria=example.createCriteria();		
		criteria.andDiagTypeEqualTo(1);
		criteria.andYxbzEqualTo(1);
		if (domainId != null){
			criteria.andDomainIdIn(Arrays.asList(domainId.split(",")));
		}
		List<IomCiTypeRelDiagram> list=iomCiTypeRelDiagramMapper.selectByExample(example);
		if(list!=null && list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean checkCiRel(String sourceBm, String relId, String targetBm) {
		//return true;
		if (StringUtils.isNotEmpty(sourceBm)&&StringUtils.isNotEmpty(relId)&&StringUtils.isNotEmpty(targetBm)) {
			return ciTypeRelDao.checkCiType(sourceBm, targetBm, relId)>0;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> selectRelTypeIds(String ciId,String typeId,String ciCode) {
		List<Map<String, Object>> list = ciTypeRelDao.selectRelTypeIds(ciId,typeId,ciCode);
		return list;
	}
	
	@Override
	public PageResult selectRelCi(String ciId,String typeId1,String typeId2,String relId,String ciCode) {
		MyPagUtile.startPage();
		List<String> ciCodes = ciTypeRelDao.selectCiIds(ciId,typeId1,typeId2,relId,ciCode);
		PageResult pageResult  = MyPagUtile.getPageResult(ciCodes);
		List<String> ciCodeList = (List<String>) pageResult.getReturnObject();
		if (ciCodeList.size()>0) {
			pageResult.setReturnObject(typeItemService.findCiList(ciCodeList));
			return pageResult;
		}
		return DataUtils.returnPr(true,"成功",new ArrayList<>());
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelByCiIdAndTypeId(String ciIds,String relId,String typeId,String identifier) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		if("down".equals(identifier)) {
			if(ciIds!=null && !"".equals(ciIds)) {
				itemMap.put("sourceCiIdList", ciIds.split(","));
			}else {
				itemMap.put("sourceCiIdList", "");
			}
			
			if(relId!=null && !"".equals(relId)) {
				itemMap.put("relId", relId);
			}else {
				itemMap.put("relId", "");
			}
			
			if(typeId!=null && !"".equals(typeId)) {
				itemMap.put("targetTypeId", typeId);
			}else {
				itemMap.put("targetTypeId", "");
			}
			itemMap.put("targetCiIdList", "");
			itemMap.put("sourceTypeId", "");
			returnList=ciTypeRelDao.getCiDataRelByCiIdAndTypeId(itemMap);
			
		}else {
			if(ciIds!=null && !"".equals(ciIds)) {
				itemMap.put("targetCiIdList", ciIds.split(","));
			}else {
				itemMap.put("targetCiIdList", "");
			}
			
			if(relId!=null && !"".equals(relId)) {
				itemMap.put("relId", relId);
			}else {
				itemMap.put("relId", "");
			}
			
			if(typeId!=null && !"".equals(typeId)) {
				itemMap.put("sourceTypeId", typeId);
			}else {
				itemMap.put("sourceTypeId", "");
			}
			itemMap.put("sourceCiIdList", "");
			itemMap.put("targetTypeId", "");
			returnList=ciTypeRelDao.getCiDataRelByCiIdAndTypeId(itemMap);
		}
		return returnList;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelBySourceCiIdAndTargetCiId(String sourceCiIds,String targetCiIds,String relId,String typeId,String identifier) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		return returnList;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelByCiId(String ciId,String targetTypeId,String relId) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelByCiId(ciId, targetTypeId,relId);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetId(String sourceTypeId,String targetTypeId,String relId) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelBySourceIdAndTargetId(sourceTypeId,targetTypeId,relId);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndCiIds(Map<String, Object> itemMap) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelBySourceIdAndTargetIdAndCiIds(itemMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndCiIdsDown(Map<String, Object> itemMap) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelBySourceIdAndTargetIdAndCiIdsDown(itemMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndCiIds(Map<String, Object> itemMap) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelByTargetIdAndSourceIdAndCiIds(itemMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndCiIdsUp(Map<String, Object> itemMap) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelByTargetIdAndSourceIdAndCiIdsUp(itemMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiDataRelBySelf(String typeId) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiDataRelBySelf(typeId);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getCiTypeFocusRelBySelf(String typeId,String relId) {
		List<Map<String, Object>> list=ciTypeRelDao.getCiTypeFocusRelBySelf(typeId,relId);
		return list;
	}

}