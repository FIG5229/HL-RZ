package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.integration.config.Advice.MsgException;
import com.integration.entity.vo.CiMgtLogItemVO;
import com.integration.generator.dao.IomCiInfoMapper;
import com.integration.generator.dao.IomCiMgtLogMapper;
import com.integration.generator.entity.IomCiInfo;
import com.integration.generator.entity.IomCiMgtLog;
import com.integration.generator.entity.IomCiMgtLogExample;
import com.integration.generator.entity.IomCiMgtLogExample.Criteria;
import com.integration.service.TypeItemService;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 
 * @author yangXiChuan ci操作日志
 */
@Transactional
@Service
public class CiMgtLogImpl {

	@Resource
	IomCiMgtLogMapper iomCiMgtLogMapper;
	
	@Resource
	IomCiInfoMapper iomCiInfoMapper;
	
	@Autowired
	private TypeItemService typeItemService;
	
	public void addLogs(String ciId) {
		if (StringUtils.isNotEmpty(ciId)) {
			try {
				logs(ciId, CiMgtLogItemVO.ADD, getCiMgtLogItemVOs(getCiInfo(ciId), null));
			} catch (Exception e) {
				throw new MsgException("新增CI时，日志错误");
			}
		}
	}
	
	public void editLogs(String ciId,Map<String, String> oldMap) {
		if (StringUtils.isNotEmpty(ciId)) {
			try {
				logs(ciId, CiMgtLogItemVO.EDIT, getCiMgtLogItemVOs(getCiInfo(ciId), oldMap));
			} catch (Exception e) {
				throw new MsgException("修改CI时，日志错误");
			}
		}
	}
	
	public void delLogs(List<String> ciIds) {
		if (ciIds!=null&&ciIds.size()>0) {
			for (String ciId : ciIds) {
				delLogs(ciId);
			}
		}
	}
	
	public void delLogs(String ciId) {
		if (StringUtils.isNotEmpty(ciId)) {
			try {
				logs(ciId, CiMgtLogItemVO.DEL, null);
			} catch (Exception e) {
				throw new MsgException("删除CI时，日志错误");
			}
		}
	}
	
	private List<CiMgtLogItemVO> getCiMgtLogItemVOs(Map<String, String> newMap,Map<String, String> oldMap) {
		List<CiMgtLogItemVO> ciMgtLogItemVOs = new ArrayList<CiMgtLogItemVO>();
		if (newMap==null) {
			newMap = new HashMap<String, String>();
		}
		
		if (oldMap==null) {
			oldMap = new HashMap<String, String>();
		}
		
		Set<String> keys =  new HashSet<String>();
		keys.addAll(newMap.keySet());
		keys.addAll(oldMap.keySet());
		
		for (String key : keys) {
			String newVal = newMap.get(key)==null?"":newMap.get(key);
			String oldVal = oldMap.get(key)==null?"":oldMap.get(key);
			//新旧值存在差异
			if (!newVal.equals(oldVal)) {
				CiMgtLogItemVO ciMgtLogItemVO =new CiMgtLogItemVO();
				//id现在先不拿
				ciMgtLogItemVO.setName(key);
				ciMgtLogItemVO.setBefor(newVal);
				ciMgtLogItemVO.setAfter(oldVal);
				ciMgtLogItemVOs.add(ciMgtLogItemVO);
			}
		}
		
		return ciMgtLogItemVOs;
	}
	
	public Map<String,String> getCiInfo(String ciId) {
		if (StringUtils.isNotEmpty(ciId)) {
			try {
				List<String> ciList = new ArrayList<String>();
				ciList.add(ciId);
				List list = typeItemService.findCiList(ciList);
				if (list!=null && list.size()>0) {
					return (Map<String,String>)list.get(0);
				}
			} catch (Exception e) {
				throw new MsgException("查询CI详情时，错误");
			}
		}
		return null;
	}
	
	private void logs(String ciId, String mgtType) {
		logs(ciId, mgtType, null);
	}
	
	private void logs(String ciId, String mgtType,Map<String, String> newMap,Map<String, String> oldMap) {
		logs(ciId, mgtType, getCiMgtLogItemVOs(newMap, oldMap));
	}
	
	private void logs(String ciId, String mgtType, List<CiMgtLogItemVO> ciMgtLogItemVOs) {
		IomCiInfo ciInfo = iomCiInfoMapper.selectByPrimaryKey(ciId);
		if (ciInfo!=null) {
			String upItem = null;
			if (ciMgtLogItemVOs!=null&&ciMgtLogItemVOs.size()>0) {
				upItem = JSON.toJSONString(ciMgtLogItemVOs);
			}
			if (StringUtils.isEmpty(mgtType)) {
				mgtType = "-1";
			}
			logs(ciId, ciInfo.getCiTypeId(), Integer.parseInt(mgtType), upItem);
		}
	}
	
	/**
	 * 新增日志
	 * @author yangXiChuan
	 * @data 2019年10月29日 下午4:01:08
	 * @param ciId
	 * @param typeId
	 * @param mgtType
	 * @param ciMgtLogItemVOs
	 */
	private void logs(String ciId, String typeId, Integer mgtType, List<CiMgtLogItemVO> ciMgtLogItemVOs) {
		String upItem = null;
		if (ciMgtLogItemVOs!=null&&ciMgtLogItemVOs.size()>0) {
			upItem = JSON.toJSONString(ciMgtLogItemVOs);
		}
		logs(ciId, typeId, mgtType, upItem);
	}

	/**
	 * 新增日志
	 * 
	 * @author yangXiChuan
	 * @data 2019年10月29日 下午3:42:58
	 * @param ciId
	 * @param typeId
	 * @param mgtType
	 * @param upItem
	 */
	private void logs(String ciId, String typeId, Integer mgtType, String upItem) {
		IomCiMgtLog iomCiMgtLog = new IomCiMgtLog();
		iomCiMgtLog.setCiId(ciId);
		iomCiMgtLog.setTypeId(typeId);
		iomCiMgtLog.setMgtType(mgtType);
		iomCiMgtLog.setUpItem(upItem);
		iomCiMgtLog.setId(SeqUtil.getId());
		iomCiMgtLog.setBgrId(TokenUtils.getTokenUserId());
		iomCiMgtLog.setBgsj(new Date());
		iomCiMgtLog.setYxbz(1);
		iomCiMgtLogMapper.insertSelective(iomCiMgtLog);
	}

	/**
	 * 查询ci的变更日志
	 * @author yangXiChuan
	 * @data 2019年10月29日 下午3:49:07
	 * @param ciId
	 * @return
	 */
	private List<IomCiMgtLog> selectList(String ciId) {
		IomCiMgtLogExample iomCiMgtLogExample = new IomCiMgtLogExample();
		iomCiMgtLogExample.setOrderByClause(" BGSJ DESC ");
		Criteria criteria = iomCiMgtLogExample.createCriteria();
		criteria.andYxbzEqualTo(1);
		criteria.andCiIdEqualTo(ciId);
		return iomCiMgtLogMapper.selectByExample(iomCiMgtLogExample);
	}

}
