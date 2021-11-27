package com.integration.service.impl;

import com.integration.dao.InfoDao;
import com.integration.dao.TypeDao;
import com.integration.dao.TypeItemDao;
import com.integration.entity.*;
import com.integration.feign.RoleDataService;
import com.integration.rabbit.Sender;
import com.integration.service.CiIconService;
import com.integration.service.DirService;
import com.integration.service.TypeService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
* @Package: com.integration.service.impl
* @ClassName: TypeServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 大类管理
*/
@Transactional
@Service
public class TypeServiceImpl implements TypeService {
	private static final Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);
	@Autowired
	private TypeDao typeDao;

	@Autowired
	private TypeItemDao typeItemDao;

	@Autowired
	private InfoDao infoDao;

	@Autowired
	private CiIconService ciIconService;

	@Resource
	private RoleDataService roleDataService;

	@Resource
	private DirService dirService;

	@Autowired
	private Sender sender;

	/**
	 * 获取所有大类
	 *
	 * @return
	 */
	@Override
	public List<Type> findTypeList() {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		return typeDao.findTypeList(domainId);
	}

	/**
	 * 获取所有大类（驼峰）
	 *
	 * @return
	 */
	@Override
	public List<Map> findTypeListHump() {
		//获取数据域ID
		String domainId=TokenUtils.getTokenOrgDomainId();
		return typeDao.findTypeListHump(domainId);
	}
	
	/**
	 * 根据多个大类ID获取大类信息（驼峰）
	 *
	 * @return
	 */
	@Override
	public List<Map<String,Object>> findCiTypeListHumpByIds(String tids) {
		//获取数据域ID
		String domainId=TokenUtils.getTokenOrgDomainId();
		Map<String,Object> itemMap= new HashMap<String,Object>();
		if(domainId!=null && !"".equals(domainId)) {
			itemMap.put("domainId", domainId);
		}else {
			itemMap.put("domainId", "");
		}
		if (tids != null && !"".equals(tids)) {
			List<String> ciTypeIds= Arrays.asList(tids.split(","));
			String [] ciTypeIdsArray = ciTypeIds.toArray(new String[ciTypeIds.size()]);
			if(ciTypeIds!=null && ciTypeIds.size()>0) {
				itemMap.put("ciTypeIdList", ciTypeIdsArray);
			}else {
				itemMap.put("ciTypeIdList", "");
			}
		}else {
			itemMap.put("ciTypeIdList", "");
		}
		return typeDao.findCiTypeListHumpByIds(itemMap);
	}
	

	/**
	 * 根据多个大类ID获取大类信息（驼峰）
	 *
	 * @return
	 */
	@Override
	public List<Map<String,Object>> findCiTypeInfoListHumpByIds(String tids) {
		Map<String,Object> itemMap= new HashMap<String,Object>();
		itemMap.put("domainId", "");		
		if (tids != null && !"".equals(tids)) {
			List<String> ciTypeIds= Arrays.asList(tids.split(","));
			String [] ciTypeIdsArray = ciTypeIds.toArray(new String[ciTypeIds.size()]);
			if(ciTypeIds!=null && ciTypeIds.size()>0) {
				itemMap.put("ciTypeIdList", ciTypeIdsArray);
			}else {
				itemMap.put("ciTypeIdList", "");
			}
		}else {
			itemMap.put("ciTypeIdList", "");
		}
		return typeDao.findCiTypeListHumpByIds(itemMap);
	}

	/**
	 * 获取所有大类（驼峰）并可以根据ciTypeBm模糊查询
	 *
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findCiTypeListHumpByCiTypeBm(String ciTypeMc) {
		//获取数据域ID
		String domainId=TokenUtils.getTokenOrgDomainId();
		List<Map<String, Object>> list=typeDao.findCiTypeListHumpByCiTypeBm(domainId,ciTypeMc);
		List<String> ciTypeIdsList=new ArrayList<String>();
		for(Map<String, Object> map:list) {
			String id=(String) map.get("id");
			ciTypeIdsList.add(id);
		}
		Map<String, Object> itemMap = new HashMap<String, Object>();
		String[] arr = ciTypeIdsList.toArray(new String[ciTypeIdsList.size()]);
		if(ciTypeIdsList!=null && ciTypeIdsList.size()>0) {
			itemMap.put("typeIdList", arr);
		}else {
			itemMap.put("typeIdList", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			itemMap.put("domainId", domainId);
		}else {
			itemMap.put("domainId", "");
		}
		List<Map<String, Object>> listCiNum=typeDao.getCiInfoNumByCiTypeId(itemMap);
		for (Map<String, Object> typeInfoMap : list) {
			String id = (String) typeInfoMap.get("id");
			for (Map<String, Object> map1 : listCiNum) {
				String ciTypeIdStr = (String) map1.get("ciTypeId");
				Long ciNum = (Long) map1.get("ciNum");
				if (id.equals(ciTypeIdStr)) {
					typeInfoMap.put("num", ciNum.intValue());
				}
			}
			boolean flag=typeInfoMap.containsKey("num");
			if(!flag) {
				typeInfoMap.put("num", 0);
			}
		}
		return list;
	}

	/**
	 * 获取所有大类（驼峰）并可以根据ciTypeBm模糊查询
	 *
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findCiTypeListHumpByCiTypeBms(String ciTypeMc) {
		//获取数据域ID
		String domainId=null;
		List<Map<String, Object>> list=typeDao.findCiTypeListHumpByCiTypeBm(domainId,ciTypeMc);
		List<String> ciTypeIdsList=new ArrayList<String>();
		for(Map<String, Object> map:list) {
			String id=(String) map.get("id");
			ciTypeIdsList.add(id);
		}
		Map<String, Object> itemMap = new HashMap<String, Object>();
		String[] arr = ciTypeIdsList.toArray(new String[ciTypeIdsList.size()]);
		if(ciTypeIdsList!=null && ciTypeIdsList.size()>0) {
			itemMap.put("typeIdList", arr);
		}else {
			itemMap.put("typeIdList", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			itemMap.put("domainId", domainId);
		}else {
			itemMap.put("domainId", "");
		}
		List<Map<String, Object>> listCiNum=typeDao.getCiInfoNumByCiTypeId(itemMap);
		for (Map<String, Object> typeInfoMap : list) {
			String id = (String) typeInfoMap.get("id");
			for (Map<String, Object> map1 : listCiNum) {
				String ciTypeIdStr = (String) map1.get("ciTypeId");
				Long ciNum = (Long) map1.get("ciNum");
				if (id.equals(ciTypeIdStr)) {
					typeInfoMap.put("num", ciNum.intValue());
				}
			}
			boolean flag=typeInfoMap.containsKey("num");
			if(!flag) {
				typeInfoMap.put("num", 0);
			}
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> findCiInfoByBaseMapId(String ciTypeName,String ciCodes) {
		//获取数据域ID
		String domainId=TokenUtils.getTokenOrgDomainId();
		List<String> ciCodesList=Arrays.asList(ciCodes.split(","));
		Map<String, Object> itemMap = new HashMap<String, Object>();
		String[] arr = ciCodesList.toArray(new String[ciCodesList.size()]);
		if(ciCodesList!=null && ciCodesList.size()>0) {
			itemMap.put("ciCodeList", arr);
		}else {
			itemMap.put("ciCodeList", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			itemMap.put("domainId", domainId);
		}else {
			itemMap.put("domainId", "");
		}
		List<Map<String, Object>> list=typeDao.findCiInfoByBaseMapId(itemMap);
		List<String> diagramCiCode=new ArrayList<String>();
		if(list!=null && list.size()>0) {
			for(Map<String, Object> map:list) {
				String ciTypeNameStr=(String) map.get("ciTypeName");
				String ciCode=(String) map.get("ciCode");
				if(ciTypeName.equals(ciTypeNameStr)) {
					diagramCiCode.add(ciCode);
				}
			}
			if(diagramCiCode!=null && diagramCiCode.size()>0) {
				Map<String, Object> itemMap1 = new HashMap<String, Object>();
				String[] arr1 = diagramCiCode.toArray(new String[diagramCiCode.size()]);
				if(diagramCiCode!=null && diagramCiCode.size()>0) {
					itemMap1.put("ciCodeList", arr1);
				}else {
					itemMap1.put("ciCodeList", "");
				}
				if(domainId!=null && !"".equals(domainId)) {
					itemMap1.put("domainId", domainId);
				}else {
					itemMap1.put("domainId", "");
				}
				List<Map<String, Object>> returnList=typeDao.findCiInfoByCiIds(itemMap1);
				return returnList;
			}
			
		}
		return new ArrayList<Map<String, Object>>();
	}


	/**
	 * 根据查询条件获取大类列表
	 *
	 * @param map
	 * @return
	 */
	@Override
	public List<Type> findTypeListByCond(Map<String, String> map) {
		return typeDao.findTypeListByCond(map);
	}

	/**
	 * 根据多个ciTypeIds获取大类信息
	 *
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findClassInfoByCiTypeIds(List<String> ids) {

		return typeDao.findClassInfoByCiTypeIds(ids);
	}

	/**
	 * 添加大类
	 */
	@Override
	public Type addType(Type type) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		Dir dir = dirService.getDirByDirName("默认图标");
		String iconDirId = "73181313049444352";
		if (dir != null){
			iconDirId = dir.getId();
		}
		List<CiIconInfo> ciList=ciIconService.searchByDirId(iconDirId);
		CiIconInfo c=new CiIconInfo();
		if (ciList!=null &&ciList.size()>0){
			for (CiIconInfo ciIconInfo:ciList) {
				if ("DefaultIcon".equals(ciIconInfo.getIcon_name())){
					c=ciIconInfo;
					break;
				}
			}
		}
		if (type.getCi_type_icon()!=null && !"".equals(type.getCi_type_icon())){

		}else{
			type.setCi_type_icon(c.getIcon_path());
		}
		type.setId(SeqUtil.nextId() + "");
		// 时间
		type.setCjsj(DateUtils.getDate());
		// 有效标志
		type.setYxbz(1);
		type.setDomain_id(domainId==null?"-1":domainId);
		int result = typeDao.addType(type);
		if (result > 0) {
			try{
				//向队列推送大类及类定义数据
				sender.sendCiTypeAndItemData();
			}catch (Exception e){
				logger.error("向队列推送大类及类定义数据异常",e);
			}
			return type;
		}
		return null;
	}

	/**
	 * 修改大类
	 */
	@Override
	public boolean updateType(Type type) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		type.setXgsj(DateUtils.getDate());
		type.setDomain_id(domainId==null?"-1":domainId);

		boolean result = typeDao.updateType(type) > 0;
		try{
			//向队列推送大类及类定义数据
			sender.sendCiTypeAndItemData();
		}catch (Exception e){
			logger.error("向队列推送大类及类定义数据异常",e);
		}
		return result;
	}

	/**
	 * 删除大类
	 */
	@Override
	public boolean deleteType(String typeId) {
		List<String> tidList = new ArrayList<String>();
		tidList.add(typeId);
		// 根据TypeId集合删除字段
		typeItemDao.deleteItemByTid(tidList);
		// 根据typeId集合删除info
		infoDao.deleteInfoByTid(tidList);

		boolean result = typeDao.deleteType(typeId) > 0;
		try{
			//向队列推送大类及类定义数据
			sender.sendCiTypeAndItemData();
		}catch (Exception e){
			logger.error("向队列推送大类及类定义数据异常",e);
		}
		return result;
	}

	/**
	 * 根据ID获取大类
	 */
	@Override
	public Type findTypeById(String typeId)
	{
		//获取数据域ID
		//String domainId = TokenUtils.getTokenOrgDomainId();
		return typeDao.findTypeById(typeId,null);
	}

	/**
	 * 判断大类名字是否存在
	 *
	 * @return
	 */
	@Override
	public boolean typeNameExists(String ci_type_bm, String ci_type_dir,String domainId) {
		int num = typeDao.typeNameExists(ci_type_bm,domainId);
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取所有大类及大类下的所有CI信息
	 *
	 * @return
	 */
	@Override
	public List<TypeInfoList> findTypeInfo(String ci_type_id, String search) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		if (search == null || "".equals(search)) {
			List<TypeInfoList> list = typeDao.findTypeAllList(search,domainId);
			for (int i = 0; i < list.size(); i++) {
				int num = typeDao.ciCountByTid(list.get(i).getId(),domainId);
				list.get(i).setNum(num);
			}

			if (!"".equals(ci_type_id) || null != ci_type_id) {

				List<Info> infoList = typeDao.findDataIdByTid(ci_type_id, search);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId().equals(ci_type_id)) {
						for (int j = 0; j < infoList.size(); j++) {
							infoList.get(j).setCi_type_icon(list.get(i).getCi_type_icon());
						}
						list.get(i).setInfoList(infoList);

					}
				}
			}
			return list;
		} else {
			List<Map<String, Object>> list = typeDao.findCiInfoNumByGroupId(search,domainId);
			List<String> list2 = new ArrayList<String>();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					String ciTypeId = (String) map.get("CI_TYPE_ID");
					list2.add(ciTypeId);
				}
			}
			if (list2 != null && list2.size() > 0) {
				Map<String, Object> itemMap = new HashMap<String, Object>();
				String[] arr = list2.toArray(new String[list2.size()]);
				if(list2!=null && list2.size()>0) {
					itemMap.put("typeIdList", arr);
				}else {
					itemMap.put("typeIdList", "");
				}
				if(domainId!=null && !"".equals(domainId)) {
					itemMap.put("domainId", domainId);
				}else {
					itemMap.put("domainId", "");
				}
				List<TypeInfoList> list3 = typeDao.getCiTypeAllListByCiTypeId(itemMap);
				for (TypeInfoList typeInfo : list3) {
					String id = (String) typeInfo.getId();
					for (Map<String, Object> map1 : list) {
						String ciTypeId = (String) map1.get("CI_TYPE_ID");
						Long num = (Long) map1.get("num");
						if (id.equals(ciTypeId)) {
							typeInfo.setNum(num.intValue());
						}
					}
				}
				if (!"".equals(ci_type_id) && null != ci_type_id) {
					List<Info> list4 = typeDao.getCiInfoByLikeCiBmAndCiTypeId(search, ci_type_id,domainId);
					for (TypeInfoList typeInfo : list3) {
						// CI大类ID
						String id = (String) typeInfo.getId();
						if (id.equals(ci_type_id)) {
							for (Info info : list4) {
								// 更新CI的图标为对应大类的图标
								info.setCi_type_icon(typeInfo.getCi_type_icon());
							}
							typeInfo.setInfoList(list4);
						}

					}
				}
				return list3;
			} else {
				List<TypeInfoList> list3 = new ArrayList<TypeInfoList>();
				return list3;
			}

		}

	}

	@Override
	public Type findByMc(String name,String domainId) {
		return typeDao.findByMc(name,domainId);
	}

	@Override
	public List<Map<String, Object>> findCiTypeIdAndMcByCiId(List<String> list) {
		return typeDao.findCiTypeIdAndMcByCiId(list);
	}

	@Override
	public List<Map<String, Object>> findClassAndCiInfoByCiIds(List<String> list) {
		return typeDao.findClassAndCiInfoByCiIds(list);
	}

	@Override
	public List<Map<String, Object>> findClassInfoAndCiInfoByCiIds(List<String> list) {
		return typeDao.findClassInfoAndCiInfoByCiIds(list);
	}

	@Override
	public List<Map<String, Object>> findCiInfoByCiIds(Map<String, Object> itemMap) {
		return typeDao.findCiInfoByCiIds(itemMap);
	}
	
	@Override
	public List<String> findCiInfoByCiCodes(Map<String, Object> itemMap) {
		return typeDao.findCiInfoByCiCodes(itemMap);
	}

	@Override
	public List<Map<String, Object>> findTypeByMap(Map<String, Object> map) {
		return typeDao.findTypeByMap(map);
	}
	
	@Override
	public List<Map<String, Object>> queryCiInfoByCiTypeName(String ciTypeName,String domainId) {
		return typeDao.queryCiInfoByCiTypeName(ciTypeName,domainId);
	}
	
	@Override
	public List<Map<String, Object>> queryCiInfoByCiTypeNameWorldMap(String ciTypeName) {
		return typeDao.queryCiInfoByCiTypeNameWorldMap(ciTypeName);
	}
	
	@Override
	public List<Map<String, Object>> getCiTypeInfoByCiTypeName(String domainId,String ciTypeName) {
		return typeDao.getCiTypeInfoByCiTypeName(domainId,ciTypeName);
	}

	@Override
	public Integer findTypeSort(String pId) {
		return typeDao.findTypeSort(pId);
	}

	/**
	 * 获取所有大类搜索用（驼峰 私有）
	 *
	 * 有数据权限限制
	 * @param type 0:查询，1：添加
	 * @return
	 */
	@Override
	public List<Map> findCiTypeListHumpSearch(int type) {
		//获取数据域ID
		String domainId=TokenUtils.getTokenDataDomainId();
		List<String> typeIdList = new ArrayList<String>();
		if (!"sysadmin".equals(TokenUtils.getTokenUserName())){
			//获取当前用户最高数据权限列表
			List<Map> roleDataList = roleDataService.findRoleHighDataList();
			for (Map map : roleDataList){
				if (type == 0){
					boolean searchAuth = (boolean) map.get("searchAuth");
					if (!searchAuth){
						typeIdList.add(map.get("dataId").toString());
						;				}
				}else{
					boolean addAuth = (boolean) map.get("addAuth");
					if (!addAuth){
						typeIdList.add(map.get("dataId").toString());
					}
				}

			}
		}
		return typeDao.findCiTypeListHumpSearch(domainId,typeIdList);

	}

	/**
	 *
	 * @param typeId
	 * @return
	 */
	@Override
	public Type findTypeByIdPublic(String typeId) {
		return typeDao.findTypeById(typeId,null);
	}

	/**
	 * 获取所有大类及大类下的所有CI信息（驼峰）
	 * @param ciTypeId
	 * @param search
	 * @return
	 */
	@Override
	public List<TypeInfoListHump> findTypeInfoHump(String ciTypeId, String search) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		if (search == null || "".equals(search)) {
			List<TypeInfoListHump> list = typeDao.findTypeAllListHump(search,domainId);
			for (int i = 0; i < list.size(); i++) {
				int num = typeDao.ciCountByTid(list.get(i).getId(),domainId);
				list.get(i).setNum(num);
			}

			if (!"".equals(ciTypeId) || null != ciTypeId) {

				List<InfoHump> infoList = typeDao.findDataIdByTidHump(ciTypeId, search);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId().equals(ciTypeId)) {
						for (int j = 0; j < infoList.size(); j++) {
							infoList.get(j).setCiTypeIcon(list.get(i).getCiTypeIcon());
						}
						list.get(i).setInfoList(infoList);

					}
				}
			}
			return list;
		} else {
			List<Map<String, Object>> list = typeDao.findCiInfoNumByGroupId(search,domainId);
			List<String> list2 = new ArrayList<String>();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					String ciTypeIds = (String) map.get("CI_TYPE_ID");
					list2.add(ciTypeIds);
				}
			}
			if (list2 != null && list2.size() > 0) {
				Map<String, Object> itemMap = new HashMap<String, Object>();
				String[] arr = list2.toArray(new String[list2.size()]);
				if(list2!=null && list2.size()>0) {
					itemMap.put("typeIdList", arr);
				}else {
					itemMap.put("typeIdList", "");
				}
				if(domainId!=null && !"".equals(domainId)) {
					itemMap.put("domainId", domainId);
				}else {
					itemMap.put("domainId", "");
				}
				List<TypeInfoListHump> list3 = typeDao.getCiTypeAllListByCiTypeIdHump(itemMap);
				for (TypeInfoListHump typeInfo : list3) {
					String id = typeInfo.getId();
					for (Map<String, Object> map1 : list) {
						String ciTypeIds = (String) map1.get("CI_TYPE_ID");
						Long num = (Long) map1.get("num");
						if (id.equals(ciTypeIds)) {
							typeInfo.setNum(num.intValue());
						}
					}
				}
				if (!"".equals(ciTypeId) && null != ciTypeId) {
					List<InfoHump> list4 = typeDao.getCiInfoByLikeCiBmAndCiTypeIdHump(search, ciTypeId,domainId);
					for (TypeInfoListHump typeInfo : list3) {
						// CI大类ID
						String id = (String) typeInfo.getId();
						if (id.equals(ciTypeId)) {
							for (InfoHump info : list4) {
								// 更新CI的图标为对应大类的图标
								info.setCiTypeIcon(typeInfo.getCiTypeIcon());
							}
							typeInfo.setInfoList(list4);
						}

					}
				}
				return list3;
			} else {
				List<TypeInfoListHump> list3 = new ArrayList<TypeInfoListHump>();
				return list3;
			}

		}

	}


	@Override
	public List<Map<String, Object>> getCiTypeList() {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		return typeDao.getCiTypeList(domainId);
	}

	@Override
	public List<Info> getCiInfoList(Map<String, Object> map) {
		return typeDao.getCiInfoList(map);
	}
}
