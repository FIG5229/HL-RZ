package com.integration.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.integration.dao.InfoDao;
import com.integration.entity.EmvRequestMessage;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.Info;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.dao.IomCiTypeRelMapper;
import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeExample;
import com.integration.generator.entity.IomCiTypeRel;
import com.integration.generator.entity.IomCiTypeRelExample;
import com.integration.service.InfoService;
import com.integration.utils.RedisUtils;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 作者 :$Author$
 * @version Revision 创建时间:2018年12月7日 下午3:26:24 id:Id
 */

@Transactional
@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	public InfoDao infoDao;

	@Resource
	IomCiTypeRelMapper iomCiTypeRelMapper;
	
	@Resource
	IomCiTypeMapper iomCiTypeMapper;


	/**
	 * 查询CI信息列表包括大类编码
	 */
	@Override
	public List<LinkedHashMap> findCIInfoList(Map map) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		map.put("domainId",domainId);
		//CI信息列表
		List<LinkedHashMap> infoLsit =  infoDao.findCIInfoListPage(map);
        //CI信息列表(数据格式转换)
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		for (LinkedHashMap m : infoLsit) {
			LinkedHashMap newDataMap = new LinkedHashMap();
			LinkedHashMap newMap = new LinkedHashMap();
			for (Object o : m.keySet()) {
				if (o!=null) {
					String key = o.toString();
					//字段名以DATA开头
					if (key.indexOf("DATA") != -1 && m.get(key)!=null) {
						String val = m.get(key).toString();
						if (StringUtils.isNotEmpty(val)) {
							newDataMap.put(o, m.get(o) + ",");
						}
					} else {
						newMap.put(key, m.get(key));
					}
				}
			}
			newMap.put("DataMap", newDataMap);
			newList.add(newMap);
		}
		return newList;
	}

	/**
	 * 查询CI信息列表包括大类编码
	 * 维护期设置使用
	 */
	@Override
	public List<Info> findCIInfoListMaintain(String searchName,String domainId) {
		// TODO Auto-generated method stub
		return infoDao.findCIInfoListPageMaintain(searchName,domainId);
	}

	/**
	 * 根据大类ID查询ci信息和某一个属性的值
	 * @param emvRequestMessage
	 * @return
	 */
	@Override
	public List<EmvReturnCIMessage> findIndexByTypeId(EmvRequestMessage emvRequestMessage) {
		return infoDao.findIndexByTypeId(emvRequestMessage);
	}

	/**
	 * 查询CI信息总数
	 *
	 * @return
	 */
	@Override
	public int findCIInfoListCount(Map map) {
		// TODO Auto-generated method stub
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		map.put("domainId",domainId);
		return infoDao.findCIInfoListCount(map);
	}

	@Override
	public List<Info> findDataIdByTid(String tid) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		return infoDao.findDataIdByTid(tid,domainId);
	}

	@Override
	public List<Map> findCiInfoByBM(String ciBm) {
		return infoDao.findCiInfoByBM(ciBm);
	}

	@Override
	public String[] getCiIdsByCiTypeRel(String relId,String sourceTypeId,String targetTypeId,String[] tids,String relType) {
		//return tids;
		//默认-1，没有一个允许
		String[] res = new String[1];
		res[0] = "-1";
		//只有有关系id和源或者目标id之一同时存在时，才有校验的必要
		if (StringUtils.isNotEmpty(relId)&&StringUtils.isNotEmpty(relType)) {
			IomCiTypeRelExample iomCiTypeRelExample = new IomCiTypeRelExample();
			com.integration.generator.entity.IomCiTypeRelExample.Criteria criteria = iomCiTypeRelExample.createCriteria();

			criteria.andRelIdEqualTo(relId);
			List<String> tidList = new ArrayList<String>();
			if (tids!= null && tids.length>0) {
				tidList = Arrays.asList(tids);
			}
			if ("target".equals(relType)) {
				if (StringUtils.isNotEmpty(sourceTypeId)) {
					criteria.andSourceTypeIdEqualTo(sourceTypeId);
				}
				if ( tidList!=null && tidList.size()>0) {
					criteria.andTargetTypeIdIn(tidList);
				}
				List<IomCiTypeRel>  iomCiTypeRels = iomCiTypeRelMapper.selectByExample(iomCiTypeRelExample);
				if (iomCiTypeRels.size()>0) {
					res = new String[iomCiTypeRels.size()];
					for (int i = 0; i < res.length; i++) {
						res[i] = iomCiTypeRels.get(i).getTargetTypeId();
					}
				}
			}else if ("source".equals(relType)) {
				if (StringUtils.isNotEmpty(targetTypeId)) {
					criteria.andTargetTypeIdEqualTo(targetTypeId);
				}
				if ( tidList!=null && tidList.size()>0) {
					criteria.andSourceTypeIdIn(tidList);
				}
				List<IomCiTypeRel>  iomCiTypeRels = iomCiTypeRelMapper.selectByExample(iomCiTypeRelExample);
				if (iomCiTypeRels.size()>0) {
					res = new String[iomCiTypeRels.size()];
					for (int i = 0; i < res.length; i++) {
						res[i] = iomCiTypeRels.get(i).getSourceTypeId();
					}
				}
			}
		}else {
			res = tids;
		}
		return res;
	}
	
	@Override
	public List<IomCiType> selectIomCiType(String relId,String sourceTypeId,String targetTypeId,String[] tids,String relType) {

		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		IomCiTypeExample example = new IomCiTypeExample();
		com.integration.generator.entity.IomCiTypeExample.Criteria criteria = example.createCriteria();
		if (domainId !=null){
			criteria.andYxbzEqualTo(1).andDomainIdIn(Arrays.asList(domainId.split(",")));
		}else{
			criteria.andYxbzEqualTo(1);
		}
		String[] ids = getCiIdsByCiTypeRel(relId, sourceTypeId, targetTypeId, tids, relType);
		if (ids!=null&&ids.length>0) {
			criteria.andIdIn(Arrays.asList(ids));
		}
		return iomCiTypeMapper.selectByExample(example);
	}

	@Override
	public int findByIdNum(String ciName) {
		return infoDao.findByIdNum(ciName);
	}
	
	@Override
	public List<String> getCiIdsByCiBm(String ciBm){
		return infoDao.getCiIdsByCiBm(ciBm);
	}
	
	@Override
	public Info findCiInfoById(String id){
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		return infoDao.findInfoById(id,domainId);
	}

	@Override
	public PageInfo findDataIdByTids(String tids, Integer pageSize, Integer pageNum) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		PageHelper.startPage(pageNum, pageSize);
		List<Info> infos = infoDao.findDataIdByTids(tids,domainId);
		PageInfo pageInfo = new PageInfo(infos);
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> findCisBySource(String source) {
		return infoDao.findCisBySource(source);
	}
	
	@Override
    public void initializeCiInfoToRedis() {
		String keyPre = new StringBuilder().append("ciInfo").append("-").append("*").toString();
		int redisNum=RedisUtils.size(keyPre);
		List<Map<String, Object>> listData=infoDao.queryCiInfoAll();
		if(redisNum!=listData.size()) {
			if(listData!=null && listData.size()>0) {
				for(Map<String,Object> map:listData) {
					String ciCode=(String) map.get("ciCode");
					String key="ciInfo_"+ciCode;
					if (!RedisUtils.exists(key)) {
						RedisUtils.set(key,map,20*60*1000L);
					}					
				}
			}
		}
	}


	@Override
	public List<Map<String, String>> getCiList(List<String> dataIds,String domainId) {
		return infoDao.getCiList(dataIds,domainId);
	}

	/**
	 *
	 * 更新CI信息表中CI_NAME字段用于显示
	 *
	 * @param ciTypeId
	 */
	@Override
	public void updateCiInfoName(String ciTypeId) {

		//查询大类类定义中IS_LABEL为1的字段
		List<String> itemField = infoDao.findItemField(ciTypeId);
		if (itemField!=null && itemField.size()>0){

			String reField = "'、',";
			for (int i=0;i<itemField.size();i++){
				reField= reField + "CASE WHEN LENGTH(TRIM("+itemField.get(i)+"))= 0 THEN NULL ELSE "+itemField.get(i)+" END ";
				if (i<itemField.size()-1){
					reField = reField +",";
				}
			}
			infoDao.updateInfoName(ciTypeId,reField);
		}else{
			//将CINAME置空
			infoDao.updateInfoNames(ciTypeId);
		}
		List<Info> infoList = infoDao.findDataIdByTid(ciTypeId,null);
		if (infoList!=null && infoList.size()>0){
			for (Info info:infoList) {
				RedisUtils.remove("ciInfo_"+info.getCiCode());
			}
		}

	}
}
