package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.google.common.base.Joiner;
import com.integration.aop.log.entity.IomCampActionApiInfo;
import com.integration.aop.log.service.IomCampActionApiService;
import com.integration.aop.log.service.UserApiService;
import com.integration.dao.*;
import com.integration.entity.*;
import com.integration.feign.CzryService;
import com.integration.feign.DictService;
import com.integration.feign.EmvEvCurrentService;
import com.integration.feign.RoleDataService;
import com.integration.generator.dao.IomCiInfoMapper;
import com.integration.generator.entity.IomCiInfo;
import com.integration.generator.entity.IomCiInfoExample;
import com.integration.service.*;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
* @Package: com.integration.service.impl
* @ClassName: TypeDataServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: ?????????
*/
@Transactional
@Service
public class TypeDataServiceImpl implements TypeDataService {
	@Autowired
	private TypeItemService typeItemService;
	@Autowired
	private TypeDataDao typeDataDao;

	@Autowired
	private TypeItemDao typeItemDao;

	@Autowired
	private InfoDao infoDao;

	@Resource
	private DataIndexDao dataIndexDao;

	@Resource
	private DataRelDao dataRelDao;

	@Autowired
	private TypeDao typeDao;

	@Autowired
	private TypeService typeServie;

	@Resource
	private IomCiInfoMapper iomCiInfoMapper;
	/**
	 * ????????????service
	 */
	@Autowired
	private EmvEvCurrentService emvEvCurrentService;
	
	@Autowired
	private TypeFocusRelDao typeFocusRelDao;
	

	@Resource(name = "transactionManager")
	private DataSourceTransactionManager transactionManager;
	
	@Autowired
	private CiTypeRelService ciTypeRelService;

	@Resource
	CiMgtLogImpl ciMgtLogImpl;

	@Resource
	private RoleDataService roleDataService;

	@Autowired
	private DictService dictService;

	@Autowired
	private CiAssociatedFieldConfService ciAssociatedFieldConfService;

	@Autowired
	private InfoService infoService;

	@Autowired
	private CiInfoToInterfaceService ciInfoToInterfaceService;

	@Autowired
	IomCampActionApiService actionApiService;

	@Autowired
	private CzryService czryService;

	public final ObjectMapper mapper = new ObjectMapper();

	/**
	 * ??????????????????ci???????????????id
	 */
	final static String CI_TYPE_ID = "ci_type_id";
	/**
	 * ??????????????????ci?????????ciCode
	 */
	final static String CI_CODE = "ciCode";
	/**
	 * ??????????????????ci???????????????id
	 */
	final static String DOMAIN_ID = "domainId";

	/**
	 * ??????????????????ci????????????????????????
	 */
	final static String DATA_FIELD_PREFIX = "DATA_";

	final static String SYSADMINID = "72904780934168577";

	static Logger log = LoggerFactory.getLogger(TypeDataServiceImpl.class);

	/**
	 * ????????????
	 */
	@Override
	public TypeData addData(TypeData typeData, String cjr) {
		//???????????????ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		typeData.setId(SeqUtil.nextId() +"");
		typeData.setDomain_id(domainId==null?"-1":domainId);
		// ??????DATA
		int result = typeDataDao.addData(typeData);
		if (result > 0) {
			// ????????????ID????????????????????????????????????
			List<TypeItem> list = typeItemDao.findItemByTid(typeData
					.getCi_type_id());
			// ????????????ID???????????????????????????
			String mp = typeItemDao.findPK(typeData.getCi_type_id());
			// ???????????????????????????SQL
			String sql = "SELECT " + mp
					+ " FROM IOMCI.IOM_CI_TYPE_DATA WHERE ID = "
					+ typeData.getId();
			// ??????????????????
			String pk = typeDataDao.findDataByMp(sql);
			// ??????INFO
			Info info = new Info();
			info.setId(typeData.getId());
			info.setCiCode(pk);
			info.setCi_type_id(typeData.getCi_type_id());
			info.setSource_id(1);
			info.setCjr_id(cjr);
			info.setCjsj(DateUtils.getDate());
			info.setYxbz("1");
			info.setDomain_id(domainId==null?"-1":domainId);
			/*???CI???????????????????????????????????????????????????*/
			Map attrsStrMap = JSONObject.parseObject(JSON.toJSONString(typeData), Map.class);
			attrsStrMap.remove("id");
			attrsStrMap.remove("ci_type_id");
			attrsStrMap.remove("domain_id");
			String attrsStr = JSON.toJSONString(attrsStrMap);
			for (TypeItem typeItem : list) {
				if ("DATA_1".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_1\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_2".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_2\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_3".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_3\"",typeItem.getAttr_name()+"\"");
				}else {
					attrsStr = attrsStr.replace(typeItem.getMp_ci_item().toLowerCase(),typeItem.getAttr_name());
				}
			}
			info.setAttrs_str(attrsStr);
			infoDao.addTypeInfo(info);
			
			//??????CI????????????redis??????
			clearInfoRedisCache(info.getId());

			String ciCode = typeData.getData_1();

			ciMgtLogImpl.addLogs(ciCode);
			return typeData;
		}
		return null;
	}

	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: ??????????????????
	 */
	public boolean addDatas(List<TypeData> typeData, String cjr,String typeId) {
		List<TypeItem> items = typeItemService.findItemByTid(typeId);
		//???????????????ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		if(typeData.size()==0){
			return true;
		}
		List<String> dataIds = new ArrayList<String>();
		for (int i = 0; i < typeData.size(); i++) {
			String nextId = SeqUtil.nextId() +"";
			typeData.get(i).setId(nextId);
			typeData.get(i).setDomain_id(domainId==null?"-1":domainId);
			dataIds.add(nextId);
		}
		//???????????????
		int num=1000;
		mapreduceTypeData(typeData, num, (list)->typeDataDao.importExcelData(list));
		// ????????????ID???????????????????????????
		String mp = typeItemDao.findPK(typeId).toLowerCase();

		//????????????jsonobeject?????????????????????
		List<String> mpList = typeData.stream().map(item->{
			return JSONObject.parseObject(JSONObject.toJSONString(item)).getString(mp);
		}).collect(Collectors.toList());

		List<Info> infoList = new ArrayList<Info>();

		for (int i = 0; i < dataIds.size(); i++) {
			// ??????INFO
			Info info = new Info();
			info.setId(dataIds.get(i));
			info.setCiCode(mpList.get(i));
			info.setCi_type_id(typeId);
			info.setSource_id(1);
			info.setCjr_id(cjr);
			info.setCjsj(DateUtils.getDate());
			info.setYxbz("1");
			info.setDomain_id(domainId==null?"-1":domainId);
			/*???CI???????????????????????????????????????????????????*/
			Map attrsStrMap = JSONObject.parseObject(JSON.toJSONString(typeData.get(i)), Map.class);
			attrsStrMap.remove("id");
			attrsStrMap.remove("ci_type_id");
			attrsStrMap.remove("domain_id");
			String attrsStr = JSON.toJSONString(attrsStrMap);
			for (TypeItem typeItem : items) {
				if ("DATA_1".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_1\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_2".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_2\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_3".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_3\"",typeItem.getAttr_name()+"\"");
				}else {
					attrsStr=attrsStr.replace(typeItem.getMp_ci_item().toLowerCase(),typeItem.getAttr_name());
				}
			}
			info.setAttrs_str(attrsStr);
			infoList.add(info);
		}

		mapreduceTypeData(infoList, num, (list)->infoDao.addTypeInfos(list));

		mpList.forEach(id->{
			RedisUtils.remove("ciInfo_"+id);
		});
		return true;

	}

	/**
	 * ????????????
	 */
	@Override
	public boolean deleteData(String dataId) {
		List<String> list = new ArrayList<String>();
		list.add(dataId);
		dataRelDao.updateRelByCi(list);
		boolean flag = typeDataDao.deleteData(dataId) > 0;
		ciMgtLogImpl.delLogs(dataId);

		clearInfoRedisCache(dataId);

		return flag;
	}

	/**
	 * ????????????
	 */
	@Override
	public boolean deleteDataByTid(String tid) {

		List<TypeData> dataList = typeDataDao.finDataByTypeId(tid);
		List<String> relList = new ArrayList<String>();
		for (int i = 0; i < dataList.size(); i++) {
			relList.add(dataList.get(i).getId());
			clearInfoRedisCache(dataList.get(i).getId());
		}
		dataRelDao.updateRelByCi(relList);
		List<String> tidList = new ArrayList<String>();
		tidList.add(tid);
		return typeDataDao.deleteDataByTid(tidList) > 0;
	}

	/**
	 * ????????????
	 */
	@Override
	public boolean updateData(TypeData typeData) throws Exception{
		//???????????????ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		//ci id
		String ciId = typeData.getId();
		typeData.setDomain_id(domainId==null?"-1":domainId);
		Map<String, String> oldCi = ciMgtLogImpl.getCiInfo(ciId);

		String ciCode ="";

		//??????iom_ci_type_data???CI??????
		int result = typeDataDao.updateData(typeData);
		//??????????????????????????????iom_ci_info???ci_bm???iom_ci_type_data_index?????????
		if (result > 0) {
			/*??????iom_ci_info???ci_bm*/
			//ci??????id
			String ciTypeId = typeData.getCi_type_id();
			List<TypeItem> items = typeItemService.findItemByTid(ciTypeId);
			//??????iom_ci_info???ci_bm
			String pky = typeItemDao.findPK(ciTypeId);
			String sql = "SELECT " + pky
					+ " FROM IOMCI.IOM_CI_TYPE_DATA WHERE ID="
					+ ciId;
			String pkValue = typeDataDao.findDataByMp(sql);
			ciCode = pkValue;
			Info info = new Info();
			info.setCiCode(pkValue);
			info.setId(ciId);
			info.setDomain_id(domainId==null?"-1":domainId);
			/*???CI???????????????????????????????????????????????????*/
			Map attrsStrMap = JSONObject.parseObject(JSON.toJSONString(typeData), Map.class);
			attrsStrMap.remove("id");
			attrsStrMap.remove("ci_type_id");
			attrsStrMap.remove("domain_id");
			String attrsStr = JSON.toJSONString(attrsStrMap);
			for (TypeItem typeItem : items) {
				if ("DATA_1".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_1\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_2".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_2\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_3".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_3\"",typeItem.getAttr_name()+"\"");
				}else {
					attrsStr = attrsStr.replace(typeItem.getMp_ci_item().toLowerCase(),typeItem.getAttr_name());
				}
			}
			info.setAttrs_str(attrsStr);
			infoDao.updateInfo(info);
			String key = "ciInfo_"+pkValue;
			RedisUtils.remove(key);
		}

		ciMgtLogImpl.editLogs(ciCode,oldCi);

		return result > 0;
	}

	/**
	 * ????????????????????????
	 *
	 * @param fieldName
	 * @param typeData
	 * @return
	 */
	private static Object getResult(Object fieldName, TypeData typeData) throws Exception{

			Class<?> aClass = typeData.getClass();
			Field declaredField = aClass.getDeclaredField(fieldName.toString());
			declaredField.setAccessible(true);
			PropertyDescriptor pd = new PropertyDescriptor(declaredField.getName(), aClass);
			Method readMethod = pd.getReadMethod();
			return readMethod.invoke(typeData);
	}

	/**
	 * ??????ID????????????
	 */
	@Override
	public TypeData findDataById(String did) {
		//??????????????????????????????????????????????????????????????????????????????????????????CI?????????????????? @author ztl  @date 2020-06-23
		return typeDataDao.findDataById(did,null);
	}

	/**
	 * ????????????ciId??????ci_type_id
	 */
	@Override
	public List<Map<String, Object>> findCiTypeIdByCiId(String ciIds,String ciCodes) {
		String[] ciIdsArray = ciCodes.split(",");
		List<String> list=Arrays.asList(ciIdsArray);
		return typeDataDao.findCiTypeIdByCiId(list);
	}

	/**
	 * excel????????????
	 */
	@Override
	public void exportExcelData(HSSFWorkbook workbook,
								XSSFWorkbook workbook2007, List<String> tid,
								HttpServletResponse response) {
		String fileName = "";
		//???????????????ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<Type> typeList = new ArrayList<Type>();
		if (tid == null) {
			tid = new ArrayList<String>();
		}

		if (tid.size() == 0) {
			List<Type> tList = typeDao.findTypeList(domainId);
			List<Map> roleDataList = roleDataService.findRoleHighDataList();
			List<String> typeIdList = new ArrayList<String>();
			for (Map map : roleDataList){
				boolean searchAuth = (boolean) map.get("searchAuth");
				if (!searchAuth){
					typeIdList.add(map.get("dataId").toString());
				}
			}
			for (int i = 0; i < tList.size(); i++) {
				if (!typeIdList.contains(tList.get(i))){
					tid.add(tList.get(i).getId());
				}
			}
		}
		for (int i = 0; i < tid.size(); i++) {
			Type type = typeDao.findTypeById(tid.get(i),domainId);
			if (type != null) {
				if ("".equals(fileName)){
					fileName = type.getCi_type_bm();
				}else{
					fileName = fileName +"&" + type.getCi_type_bm();
				}
				typeList.add(type);
			}
		}
		for (int i = 0; i < typeList.size(); i++) {
			// ?????????????????????.
			List<TypeItem> items = typeItemDao.findItemByTid(tid.get(i));
			// ??????????????????????????????
			if (items.size() > 0) {
				// ????????????ID????????????LIST
				List dataList = getData(typeList.get(i).getId());
				List<String> headersId = new ArrayList<String>();
				// ????????????LIST
				List<String> names = new ArrayList<String>();
				for (int j = 0; j < items.size(); j++) {
					names.add(items.get(j).getAttr_name());
					headersId.add(items.get(j).getMp_ci_item());
				}
				// ?????????LIST???????????? ??????excel?????????
				String[] strings = new String[names.size()];
				names.toArray(strings);
				ExcelUtilWrapper<Dir> util = new ExcelUtilWrapper<Dir>();
				util.MapExcel(workbook, fileName, typeList.get(i)
						.getCi_type_mc(), names, headersId, dataList, response);
			}
		}
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}

	/**
	 * sql??????
	 *
	 * @param tid
	 * @return
	 */
	public List getData(String tid) {
		// ????????????ID??????????????????
		List<TypeItem> list = typeItemDao.findItemByTid(tid);
		if (list.size() > 0) {
			// ????????????????????????
			List<String> arr = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				// ??????????????????????????????,?????????DATA_1,DATA_2???,??????????????????SQL
				arr.add(list.get(i).getMp_ci_item());
			}
			StringBuffer sb = new StringBuffer();
			if (list.size() > 0) {
				sb.append("SELECT ");
				for (int i = 0; i < arr.size(); i++) {
					sb.append(arr.get(i) + ",");
				}
				// ???????????????,???
				String sqlStr = sb.toString().substring(0, sb.length() - 1);
				StringBuffer buffer = new StringBuffer();
				buffer.append(sqlStr);
				buffer.append(" FROM IOMCI.IOM_CI_TYPE_DATA WHERE ID IN(select ID FROM IOMCI.IOM_CI_INFO WHERE CI_TYPE_ID = "
						+ tid + " AND YXBZ=1)"); // ??????SQL
				List data = typeItemDao.findDataMap(buffer.toString());
				return data;
			}
		}
		return null;
	}

	/**
	 * ??????excel??????
	 */
	@Override
	public void downloadTemplet(HSSFWorkbook workbook,
								XSSFWorkbook workbook2007, List<String> tid,
								HttpServletResponse response) {
		//???????????????ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<Type> typeList = new ArrayList<Type>();
		if (tid == null) {
			tid = new ArrayList<String>();
		}
		if ( tid.size() == 0) {
			List<Type> tList = typeDao.findTypeList(domainId);
			for (int i = 0; i < tList.size(); i++) {
				tid.add(tList.get(i).getId());
			}
		}
		for (int i = 0; i < tid.size(); i++) {
			Type type = typeDao.findTypeById(tid.get(i),domainId);
			if (type != null) {
				typeList.add(type);
			}
		}
		for (int i = 0; i < typeList.size(); i++) {
			// ?????????????????????.
			List<TypeItem> items = typeItemDao.findItemByTid(tid.get(i));
			// ??????????????????????????????
			if (items.size() > 0) {
				// ????????????ID????????????LIST
				// ????????????LIST
				List<String> names = new ArrayList<String>();
				for (int j = 0; j < items.size(); j++) {
					names.add(items.get(j).getAttr_name());
				}
				String[] strings = new String[names.size()];
				// ?????????LIST???????????? ??????excel?????????
				names.toArray(strings);
				// excel?????????
				String fileName = "template";
				ExcelUtilWrapper<Dir> util = new ExcelUtilWrapper<Dir>();
				util.exportExcel(workbook, workbook2007, fileName, typeList
								.get(i).getCi_type_mc(), strings, null, response,
						ExcelUtilWrapper.EXCEL_FILE_2003);
			}
		}
	}

	/**
	 * ??????????????????????????????
	 */
	@Override
	public boolean pkExists(String pkName, String ci_type_id) {
		// ????????????ID???????????????????????????
		String mp = typeItemDao.findPK(ci_type_id);
		String sql = "SELECT COUNT(" + mp
				+ ") FROM IOMCI.IOM_CI_TYPE_DATA WHERE CI_TYPE_ID = "
				+ ci_type_id +" AND " + mp + " = '" + pkName + "'";
		return typeDataDao.pkExists(sql) > 0;
	}

	/**
	 * excel????????????
	 */
	@Override
	public Map importExcelData(String ci_type_id,  List<TypeData> dataList , String cjr) {


		//???????????????ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tj", 0);
		map.put("xg", 0);
		map.put("cf", 0);
		map.put("dataBaseCf", 0);

		List<TypeItem> items = typeItemService.findItemByTid(ci_type_id);

		int size = dataList.size();
		// ????????????ID??????TYPEDATA????????????
		List<TypeData> list = typeDataDao.finDataByTypeId(ci_type_id);
		dataList.addAll(list);

		//??????excel??????????????????????????????
		List<TypeData> xtList = getDiffrent(dataList, list);
		//?????????????????????????????????????????????
		int outSize = size - xtList.size();
		map.put("cf",outSize);

		List<TypeData> idList = new ArrayList<TypeData>();

		for (int i = 0; i < list.size(); i++) {

			Iterator<TypeData> it = xtList.iterator();
			while(it.hasNext()){
				TypeData x = it.next();
				if(list.get(i).getData_1().equals(x.getData_1())){
					x.setDomain_id(domainId==null?"-1":domainId);
					x.setCi_type_id(ci_type_id);
			    	x.setId(list.get(i).getId());
					//?????????data1???excel???data1?????????????????????????????????????????????
			    	idList.add(x);
			        it.remove();
			    }
			}
		}

		log.info(String.format("??????ci???------------------------------"));

		if(idList.size()>0){
			//?????????????????????
			mapreduceTypeData(idList, 1000, tempList -> typeDataDao.updateDatas(tempList));
			String pky = typeItemDao.findPK(ci_type_id);
			List<Info> infoLists = new ArrayList<>();
			for (int i=0;i<idList.size();i++){

				String pkValue = JSONObject.parseObject(JSONObject.toJSONString(idList.get(i))).getString(pky.toLowerCase());
				Info info = new Info();
				info.setCiCode(pkValue);
				info.setId(idList.get(i).getId());
				info.setDomain_id(domainId==null?"-1":domainId);
				/*???CI???????????????????????????????????????????????????*/
				Map attrsStrMap = JSONObject.parseObject(JSON.toJSONString(idList.get(i)), Map.class);
				attrsStrMap.remove("id");
				attrsStrMap.remove("ci_type_id");
				attrsStrMap.remove("domain_id");
				String attrsStr = JSON.toJSONString(attrsStrMap);
				for (TypeItem typeItem : items) {
					if ("DATA_1".equals(typeItem.getMp_ci_item())){
						attrsStr=attrsStr.replace("data_1\"",typeItem.getAttr_name()+"\"");
					}else if("DATA_2".equals(typeItem.getMp_ci_item())){
						attrsStr=attrsStr.replace("data_2\"",typeItem.getAttr_name()+"\"");
					}else if("DATA_3".equals(typeItem.getMp_ci_item())){
						attrsStr=attrsStr.replace("data_3\"",typeItem.getAttr_name()+"\"");
					}else {
						attrsStr = attrsStr.replace(typeItem.getMp_ci_item().toLowerCase(),typeItem.getAttr_name());
					}
				}
				info.setAttrs_str(attrsStr);
				infoLists.add(info);

			}
			if (infoLists !=null && infoLists.size()>0){
				mapreduceTypeData(infoLists, 1000, (tempList)->infoDao.updateInfoList(tempList));
				infoLists.forEach(item->{
					String ciCode=item.getCiCode();
					String key = "ciInfo_"+ciCode;
					RedisUtils.remove(key);
				});
			}
		}

		map.put("xg", idList.size());

		if (xtList.size()>0) {
			List<String> bms = new ArrayList<String>();
			for (TypeData typeData : xtList) {
				typeData.setCi_type_id(ci_type_id);
				bms.add(typeData.getData_1());
			}
			IomCiInfoExample iomCiInfoExample = new IomCiInfoExample();
			com.integration.generator.entity.IomCiInfoExample.Criteria criteria = iomCiInfoExample.createCriteria();
			criteria.andCiCodeIn(bms);
			criteria.andYxbzEqualTo(1);
			List<IomCiInfo> iomCiInfos = iomCiInfoMapper.selectByExample(iomCiInfoExample);
			List<TypeData> adds = new ArrayList<TypeData>();
			if (iomCiInfos.size()>0) {
				map.put("dataBaseCf", iomCiInfos.size());
				for (TypeData typeData : xtList) {
					boolean flag = true;
					for (IomCiInfo iomCiInfo : iomCiInfos) {
						if (iomCiInfo.getCiCode().equals(typeData.getData_1())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						adds.add(typeData);
					}
				}
			}else {
				adds.addAll(xtList);
			}

			if (adds.size()>0) {
				addDatas(adds,cjr,ci_type_id);
			}
			map.put("tj", adds.size());
		}

		return map;

	}




	/**
	 * ?????????????????????LIST?????????
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	private List<TypeData> getDiffrent(List<TypeData> list1,
									   List<TypeData> list2) {
		List<TypeData> diff = new ArrayList<TypeData>();
		Map<TypeData, Integer> map = new HashMap<TypeData, Integer>(
				list1.size() + list2.size());
		List<TypeData> maxList = list1;
		List<TypeData> minList = list2;
		if (list2.size() > list1.size()) {
			maxList = list2;
			minList = list1;
		}
		for (TypeData string : maxList) {
			map.put(string, 1);
		}
		for (TypeData string : minList) {
			Integer count = map.get(string);
			if (count != null) {
				map.put(string, ++count);
				continue;
			}
			map.put(string, 1);
		}
		for (Map.Entry<TypeData, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				diff.add(entry.getKey());
			}
		}
		return diff;

	}

	public JavaType getCollectionType(Class<?> collectionClass,
									  Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

	/**
	 * ????????????????????????
	 *
	 * @param map ci????????????
	 * @return ??????????????????
	 */
	@Override
	public List<LinkedHashMap> findConfigInfo(Map map) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> mapList = dictService.findDiceBySj_id(257777);
		List<LinkedHashMap> configList = typeDataDao.findConfigInfoPage(map);
		//ciId??????
		List<String> ciIds = new ArrayList<String>();
		List<String> ciCodes = new ArrayList<String>();
		//???????????????????????????ciId????????????
		for (LinkedHashMap configHashMap : configList) {
			ciIds.add(String.valueOf(configHashMap.get("ci_id")));
			ciCodes.add(String.valueOf(configHashMap.get("ciCode")));
		}
		//??????id????????????????????????
		List<Map<String, Object>> alarmCountList = emvEvCurrentService.selectCountByCiIdList(ciIds.toArray(new String[ciIds.size()]),ciCodes.toArray(new String[ciCodes.size()]));
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		for (LinkedHashMap m : configList) {
			LinkedHashMap newMap = new LinkedHashMap();
			LinkedHashMap newMap2 = new LinkedHashMap();
			for (Object o : m.keySet()) {
				if (o.toString().indexOf("DATA") != -1) {
					if (m.get(o) !=null &&!m.get(o).toString().isEmpty()
							&& m.get(o).toString() != null) {
						newMap.put(o, m.get(o) + ",");
					}
				} else {
					newMap2.put(o, m.get(o));
					newMap2.put("alarmCount", 0);
					String ciCode = String.valueOf(m.get("ciCode"));
					//??????????????????list
					if (alarmCountList != null && alarmCountList.size() > 0) {
						for (Map<String, Object> alarmCountMap : alarmCountList) {
							//??????ciId??????????????????
							if (ciCode.equals(alarmCountMap.get("ciCode"))) {
								Object num = alarmCountMap.get("num");
								if (StringUtils.isNotEmpty(String.valueOf(num))) {
									newMap2.put("alarmCount", num);
								}

								for (Map<String, Object> s : mapList) {
									if (s.get("dict_bm").equals(alarmCountMap.get("eventLevel"))) {
										newMap2.put("alarmColor", s.get("coutent"));
									}
								}

							}
						}
					}

				}
			}
			newMap2.put("DataMap", newMap);
			newList.add(newMap2);
		}
		return newList;
	}
	
	@Override
	public List<Map<String, Object>> findConfigItemByCiCode(String ciName,String domainId) {
		List<Map<String, Object>> list=typeDataDao.findConfigItemByCiCode(ciName,domainId);
		return list;
	}

	/**
	 * CI?????????????????????????????????????????????
	 *
	 * @param map ci????????????
	 * @return
	 */
	@Override
	public List<LinkedHashMap> findConfigInfoHump(Map map){
		// TODO Auto-generated method stub
		List<LinkedHashMap> configList = typeDataDao.findHumpConfigInfoPage(map);
		//ciId??????
		List<String> ciIds = new ArrayList<String>();
		//ciId??????
		List<String> ciCodes = new ArrayList<String>();
		//???????????????????????????ciId????????????
		for (LinkedHashMap configHashMap : configList) {
			ciIds.add(String.valueOf(configHashMap.get("ciId")));
			ciCodes.add(String.valueOf(configHashMap.get("ciCode")));
		}
		//??????id????????????????????????
		List<Map<String, Object>> alarmCountList = emvEvCurrentService.selectCountByCiIdList(ciIds.toArray(new String[ciIds.size()]),ciCodes.toArray(new String[ciCodes.size()]));
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		for (LinkedHashMap m : configList) {
			LinkedHashMap newMap = new LinkedHashMap();
			LinkedHashMap newMap2 = new LinkedHashMap();
			for (Object o : m.keySet()) {
				if (o.toString().indexOf("DATA") != -1) {
					if (m.get(o) != null && !"".equals(m.get(o))) {
						newMap.put(o, m.get(o) + ",");
					}
				} else {
					newMap2.put(o, m.get(o));
					newMap2.put("alarmCount", 0);
					String ciCode = String.valueOf(m.get("ciBm"));
					//??????????????????list
					if (alarmCountList != null && alarmCountList.size() > 0) {
						for (Map<String, Object> alarmCountMap : alarmCountList) {
							//??????ciId??????????????????
							if (ciCode.equals(alarmCountMap.get("ciCode"))) {
								Object num = alarmCountMap.get("num");
								if (StringUtils.isNotEmpty(String.valueOf(num))) {
									newMap2.put("alarmCount", num);
								}

							}
						}
					}

				}
			}
			newMap2.put("DataMap", newMap);
			newList.add(newMap2);
		}
		return newList;
	}

	/**
	 * ????????????????????????
	 * @param map map
	 * @return ????????????
	 */
	@Override
	public int findConfigInfoCount(Map map) {
		return typeDataDao.findConfigInfoCount(map);
	}

	/**
	 * ??????CIid??????????????????ci??????
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<Map> findRelCITypeByCIId(String id) {
		// TODO Auto-generated method stub
		return typeDataDao.findRelCITypeByCIId(id);
	}

	/**
	 * ??????CIid,??????CI????????????????????????
	 *
	 * @param map
	 * @return
	 */
	@Override
	public List<Map> findRelCIDataByCIIdAndCIType(Map map) {
		// TODO Auto-generated method stub
		return typeDataDao.findRelCIDataByCIIdAndCIType(getSql(map));
	}

	/**
	 * ??????????????????SQL
	 *
	 * @param map
	 * @return
	 */
	public String getSql(Map map) {
		String tid = map.get("tid").toString();
		// ????????????ID??????????????????
		List<TypeItem> list = typeItemDao.findItemByTid(tid);
		if (list.size() > 0) {
			// ????????????????????????
			List<String> arr = new ArrayList<String>();
			// ??????????????????????????????,?????????DATA_1,DATA_2???,??????????????????SQL
			for (int i = 0; i < list.size(); i++) {
				arr.add(list.get(i).getMp_ci_item());
			}
			StringBuffer sb = new StringBuffer();
			if (list.size() > 0) {
				sb.append(" SELECT CONCAT(ID) as ID,CONCAT(CI_TYPE_ID) AS CI_TYPE_ID, ");
			} else {
				sb.append(" SELECT CONCAT(ID) as ID,CONCAT(CI_TYPE_ID) AS CI_TYPE_ID ");
			}
			for (int i = 0; i < arr.size(); i++) {
				sb.append(arr.get(i) + ",");
			}
			// ???????????????,???
			String sqlStr = sb.toString().substring(0, sb.length() - 1);
			StringBuffer buffer = new StringBuffer();
			buffer.append(sqlStr);
			buffer.append(" FROM IOMCI.IOM_CI_TYPE_DATA WHERE ID IN (select ID FROM IOMCI.IOM_CI_INFO WHERE YXBZ=1)");
			if (map.get("cid") != null) {
				buffer.append(" and id in (select TARGET_CI_ID from IOMCI.IOM_CI_DATA_REL where SOURCE_CI_ID= "
						+ map.get("cid") + ")");
			}
			if (map.get("tid") != null) {
				buffer.append(" and CI_TYPE_ID= " + map.get("tid"));
			}
			return buffer.toString();
		}
		return "";
	}

	/**
	 * ????????????CI??????
	 */
	@Override
	public boolean deleteDataByids(List<String> dataIds) {
		//??????redis-ciInfo??????
		List<Info> list=typeDataDao.findCiInfoById(dataIds);
		List<String> ciCodeList = new ArrayList<>();
		if(list!=null && list.size()>0) {
			ciCodeList = list.stream().map(Info::getCiCode).collect(Collectors.toList());
			for(Info info:list) {
				String ciCode=info.getCiCode();
				String key="ciInfo_"+ciCode;
				if(RedisUtils.exists(key)) {
					RedisUtils.remove(key);
	        	}	
			}
		}
		//??????????????????CI??????
		dataRelDao.updateRelByCi(dataIds);
		ciMgtLogImpl.delLogs(ciCodeList);
		boolean flag = typeDataDao.deleteDataByidTrue(dataIds)>0;
		typeDataDao.deleteDataByidstrue(dataIds);
		dataIndexDao.deleteDataIndexByciIds(dataIds);
		return flag;
	}
	/**
	 * ????????????ID??????????????????
	 * @param tid
	 * @return
	 */
	@Override
	public List<TypeData> finDataByTypeId(String tid) {
		return typeDataDao.finDataByTypeId(tid);
	}

	@Override
	public List<Map> findEmv() {

		List<Map> list = typeDataDao.findEmv();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("CJSJ", DateUtils.getDate());
		}
		return list;
	}

	@Override
	public List<Map> findPmv() {
		// TODO Auto-generated method stub
		return typeDataDao.findPmv();
	}

	@Override
	public String findDataByCiIDAndAttrId(String ciId, String attrId) {
		return typeDataDao.findDataByCiIDAndAttrId(ciId, attrId);
	}

	@Override
	public List<DataIndex> findDataByVal(List<Map> maps) {
		return typeDataDao.findDataByVal(maps);
	}

	@Resource
	private CiTypeRelDao ciTypeRelDao;

	@Override
	public Map<String, Object> getUpCiRels(String ciId,String ciCode) {
		Map<String, Object> map = getCiRels(ciId, 999, 0, null, null,ciCode);
		map.put("cis", getAlarmCount((List<Map<String, Object>>)map.get("cis")));
		return map;
	}

	@Override
	public Map<String, Object> getDownCiRels(String ciId,String ciCode) {
		Map<String, Object> map = getCiRels(ciId, 0, 999, null, null,ciCode);
		map.put("cis", getAlarmCount((List<Map<String, Object>>)map.get("cis")));
		return map;
	}
	
	@Override
	public Map<String, Object> queryDirectRelationByCiTypeId(String ciTypeId,String ciTypeMc,Integer upNum,Integer downNum) {
		//???????????????ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<Map<String, Object>> upSet=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> downSet=new ArrayList<Map<String,Object>>();
		Set<String> ciTypeIds=new HashSet<String>();
		//??????upNum???
		if (upNum!=null&&upNum>0) {
			Map<String, Object> upMap = getCiTypeSingleRels(ciTypeId, upNum, "up");
			List<Map<String, Object>> list=(List<Map<String, Object>>) upMap.get("rels");
			upSet=filterListBySourceIdAndTargetId(list);
		}		
		//??????downNum???
	    if (downNum!=null&&downNum>0) {
	    	Map<String, Object> downMap = getCiTypeSingleRels(ciTypeId, downNum, "down");
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) downMap.get("rels");
	    	downSet=filterListBySourceIdAndTargetId(list);
	    }
	    //????????????????????????
	    List<String> listciTypeIds=new ArrayList<String>();
	    listciTypeIds.add(ciTypeId);
	    Map<String, Object> conditionMap=new HashMap<String, Object>();
		String [] idsArray = listciTypeIds.toArray(new String[listciTypeIds.size()]);
		conditionMap.put("sourceTypeIdList", idsArray);
		conditionMap.put("targetTypeIdList", idsArray);	
		List<Map<String, Object>> selfList=typeDataDao.queryCiTypeRelBySourceIdAndTargetId(conditionMap);
        //??????????????????
		for(Map<String, Object> map:selfList) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String relId=(String) map.get("relId");
			String targetTypeId=(String) map.get("targetTypeId");
			map.put("IUAGASJBKNKJS", sourceTypeId+relId+targetTypeId);
		}
		for(Map<String, Object> map:upSet) {
		    String sourceTypeId=(String) map.get("sourceTypeId");
		    ciTypeIds.add(sourceTypeId);
	    }
		for(Map<String, Object> map:downSet) {
		    String targetTypeId=(String) map.get("targetTypeId");
		    ciTypeIds.add(targetTypeId);
	    }	
		for(Map<String, Object> map:selfList) {
			String sourceTypeId=(String) map.get("sourceTypeId");
		    String targetTypeId=(String) map.get("targetTypeId");
		    ciTypeIds.add(sourceTypeId);
		    ciTypeIds.add(targetTypeId);
	    }
		List<String> ciTypeIdsList = new ArrayList<String>(ciTypeIds);
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
		List<TypeInfoList> listData =new ArrayList<TypeInfoList>();
		if(arr!=null && arr.length>0) {
			listData = typeDao.getCiTypeAllListByCiTypeId(itemMap);
		}	
		List<Map<String, Object>> listCiNum=typeDao.getCiInfoNumByCiTypeId(itemMap);
		List<Map<String, Object>> listHump=new ArrayList<Map<String,Object>>();
		for(TypeInfoList typeInfoList:listData) {
			try {
				Map<String, Object> map=bean2map(typeInfoList);
				Map<String, Object> mapHump=formatHumpName(map);
				listHump.add(mapHump);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Map<String, Object> typeInfoMap : listHump) {
			String id = (String) typeInfoMap.get("id");
			for (Map<String, Object> map1 : listCiNum) {
				String ciTypeIdStr = (String) map1.get("ciTypeId");
				Long ciNum = (Long) map1.get("ciNum");
				if (id.equals(ciTypeIdStr)) {
					typeInfoMap.put("num", ciNum.intValue());
				}
			}
		}
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:upSet){
			list.add(map);
		}
		for(Map<String, Object> map:downSet){
			list.add(map);
		}
		for(Map<String, Object> map:selfList) {
			list.add(map);
	    }
		list=filterListBySourceIdAndTargetId(list);
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("ciTypeRltLines", list);
		//????????????
	    if(ciTypeMc!=null && !"".equals(ciTypeMc)) {
	    	List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
	    	for (Map<String, Object> typeInfoMap : listHump) {
	    		String ciTypeBm=(String) typeInfoMap.get("ciTypeMc");
	    		boolean flag=ciTypeBm.contains(ciTypeMc);
	    		if(flag) {
	    			returnList.add(typeInfoMap);
	    		}
	    	}
	    	returnMap.put("ciType", returnList);
	    }else {
	    	returnMap.put("ciType", listHump);
	    }	
	    return returnMap;
	}
    
	@Override
	public List<Map<String,Object>> queryRootCauseAnalysisByCiCodeOptimize(String ciTypeId, String ciCode, List<String> ciTypeIdList, Map<String,Set<String>> ciCodeMap, List<Map<String,Object>> returnRelList, Set<String> historyTypeId, String layer,String domainId){
		//????????????????????????CI??????????????????
		List<String> ciTypeIdsList=new ArrayList<String>();
		//??????????????????ciCodeMap??????ciCode?????????????????????ciCode??????????????????
		if(ciTypeIdList!=null && ciTypeIdList.size()>0) {
			//????????????ciCode???
			for(String strTypeId:ciTypeIdList) {
				boolean flag=ciCodeMap.containsKey(strTypeId);
				if(flag) {
					Set<String> set=ciCodeMap.get(strTypeId);
					if(set!=null && set.size()>0) {
						ciTypeIdsList.add(strTypeId);
					}
				}
			}
			//???????????????ciCode???
			for(String strTypeId:ciTypeIdList) {
				boolean flag=ciCodeMap.containsKey(strTypeId);
				if(flag) {
					Set<String> set=ciCodeMap.get(strTypeId);
					if(set==null || set.size()==0) {
						ciTypeIdsList.add(strTypeId);
					}
				}
			}
		}
		//????????????????????????ciCode????????????
		Map<String,Set<String>> ciCodesMap=new HashMap<String,Set<String>>();
		Iterator<Entry<String, Set<String>>> entries = ciCodeMap.entrySet().iterator();
		while(entries.hasNext()){
			 Entry<String, Set<String>> entry = entries.next();
			 String key = entry.getKey();
			 Set<String> value = entry.getValue();
			 ciCodesMap.put(key, value);
		}
		if(ciTypeIdsList!=null && ciTypeIdsList.size()>0) {
			Set<String> ciTypeIdSet=new HashSet<String>();
			for(String ciTypeIdStr:ciTypeIdsList) {
				//????????????Id??????????????????
				List<Map<String, Object>> relFocusList=typeFocusRelDao.queryRootCauseRelByTypeIdOptimize(ciTypeIdStr,domainId);
				//??????????????????????????????ID
				if(relFocusList!=null && relFocusList.size()>0) {
					for(Map<String, Object> map:relFocusList) {
					    String sourceTypeId=(String) map.get("sourceTypeId");
					    String targetTypeId=(String) map.get("targetTypeId");
						if(!ciTypeIdsList.contains(sourceTypeId)) {
							if(!historyTypeId.contains(sourceTypeId)) {
								ciTypeIdSet.add(sourceTypeId);
							}
						}
						if(!ciTypeIdsList.contains(targetTypeId)) {
							if(!historyTypeId.contains(targetTypeId)) {
								ciTypeIdSet.add(targetTypeId);
							}
						}
					}
				}
				//????????????Id??????????????????(??????????????????)
				List<Map<String, Object>> relFocusSelfList=typeFocusRelDao.queryInfluenceRelByTypeIdInfluenceSelf(ciTypeIdStr,domainId);
				//??????????????????
				List<Map<String, Object>> relFocusDataList=new ArrayList<Map<String, Object>>();
				relFocusDataList.addAll(relFocusList);
				relFocusDataList.addAll(relFocusSelfList);
				//???????????????????????????????????????
				if(relFocusDataList!=null && relFocusDataList.size()>0) {
					for(Map<String, Object> map:relFocusDataList) {
						String sourceTypeId=(String) map.get("sourceTypeId");
						String targetTypeId=(String) map.get("targetTypeId");
						String rltId=(String) map.get("rltId");
						List<String> rltIds=Arrays.asList(rltId.split(","));
						Set<String> ciCodes=(Set<String>) ciCodesMap.get(ciTypeIdStr);
						List<Map<String,Object>> dataList=null;
						if(sourceTypeId.equals(ciTypeIdStr) && !sourceTypeId.equals(targetTypeId)) {
							String [] relArray = rltIds.toArray(new String[rltIds.size()]);
							String [] ciCodeArray = ciCodes.toArray(new String[ciCodes.size()]);
							Map<String,Object> itemMap=new HashMap<String,Object>();
							itemMap.put("sourceTypeId", sourceTypeId);
							itemMap.put("targetTypeId", targetTypeId);
							itemMap.put("sourceCiCodeList", ciCodeArray);
							itemMap.put("targetCiCodeList", "");
							itemMap.put("relIdList", relArray);
							itemMap.put("domainId", domainId);
							if(ciCodeArray==null || ciCodeArray.length==0) {
								dataList=new ArrayList<Map<String,Object>>();
							}else {
								dataList=ciTypeRelDao.getImpactAnalysisRelDatasOptimize(itemMap);
							}
							Set<String> ciCodesSet=new HashSet<String>();
							//????????????ciCode
							for(Map<String,Object> mapData:dataList) {
								String ciBm=(String) mapData.get("targetCiBm");
								ciCodesSet.add(ciBm);
							}
							Set<String> ciCodeData=ciCodesMap.get(targetTypeId);
							if(ciCodeData==null) {
								ciCodeData=new HashSet<String>();
							}
							ciCodeData.addAll(ciCodesSet);
							ciCodesMap.put(targetTypeId, ciCodeData);
						}else if(targetTypeId.equals(ciTypeIdStr) && !sourceTypeId.equals(targetTypeId)) {
							String [] relArray = rltIds.toArray(new String[rltIds.size()]);
							String [] ciCodeArray = ciCodes.toArray(new String[ciCodes.size()]);
							Map<String,Object> itemMap=new HashMap<String,Object>();
							itemMap.put("sourceTypeId", sourceTypeId);
							itemMap.put("targetTypeId", targetTypeId);
							itemMap.put("sourceCiCodeList", "");
							itemMap.put("targetCiCodeList", ciCodeArray);
							itemMap.put("relIdList", relArray);
							itemMap.put("domainId", domainId);
							if(ciCodeArray==null || ciCodeArray.length==0) {
								dataList=new ArrayList<Map<String,Object>>();
							}else {
								dataList=ciTypeRelDao.getImpactAnalysisRelDatasOptimize(itemMap);
							}
							
							Set<String> ciCodesSet=new HashSet<String>();
							//????????????ciCode
							for(Map<String,Object> mapData:dataList) {
								String ciBm=(String) mapData.get("sourceCiBm");
								ciCodesSet.add(ciBm);
							}
							Set<String> ciCodeData=ciCodesMap.get(sourceTypeId);
							if(ciCodeData==null) {
								ciCodeData=new HashSet<String>();
							}
							ciCodeData.addAll(ciCodesSet);
							ciCodesMap.put(sourceTypeId, ciCodeData);
						}else if(sourceTypeId.equals(targetTypeId)) {
							String [] relArray = rltIds.toArray(new String[rltIds.size()]);
							String [] ciCodeArray = ciCodes.toArray(new String[ciCodes.size()]);
							Map<String,Object> itemMap=new HashMap<String,Object>();
							itemMap.put("sourceTypeId", sourceTypeId);
							itemMap.put("targetTypeId", targetTypeId);
							itemMap.put("sourceCiCodeList", ciCodeArray);
							itemMap.put("targetCiCodeList", ciCodeArray);
							itemMap.put("relIdList", relArray);
							itemMap.put("domainId", domainId);
							if(ciCodeArray==null || ciCodeArray.length==0) {
								dataList=new ArrayList<Map<String,Object>>();
							}else {
								dataList=ciTypeRelDao.getImpactAnalysisRelDatasSelf(itemMap);
							}
						}
						returnRelList.addAll(dataList);
					}
				}
			}
			//???????????????????????????
			ciTypeIdList.clear();
			//???????????????????????????
			List<String> nextCiTypeIdList=new ArrayList<String>(ciTypeIdSet);
			//????????????????????????
			for(String ciTypeIdStr:ciTypeIdsList) {
				historyTypeId.add(ciTypeIdStr);
			}
			if(layer==null || "".equals(layer)) {
				queryRootCauseAnalysisByCiCodeOptimize(ciTypeId,ciCode,nextCiTypeIdList,ciCodesMap,returnRelList,historyTypeId,"",domainId);
			}		
		}
		return returnRelList;
	}
	
	
	@Override
	public List<Map<String, Object>> queryImpactAnalysisByCiCodeOptimize(String ciTypeId,String ciCode,List<String> ciTypeIdList,Map<String,Set<String>> ciCodeMap,List<Map<String,Object>> returnRelList,Set<String> historyTypeId,String domainId) {
		//????????????????????????CI??????????????????
		List<String> ciTypeIdsList=new ArrayList<String>();
		//??????????????????ciCodeMap??????ciCode?????????????????????ciCode??????????????????
		if(ciTypeIdList!=null && ciTypeIdList.size()>0) {
			//????????????ciCode???
			for(String strTypeId:ciTypeIdList) {
				boolean flag=ciCodeMap.containsKey(strTypeId);
				if(flag) {
					Set<String> set=ciCodeMap.get(strTypeId);
					if(set!=null && set.size()>0) {
						ciTypeIdsList.add(strTypeId);
					}
				}
			}
			//???????????????ciCode???
			for(String strTypeId:ciTypeIdList) {
				boolean flag=ciCodeMap.containsKey(strTypeId);
				if(flag) {
					Set<String> set=ciCodeMap.get(strTypeId);
					if(set==null || set.size()==0) {
						ciTypeIdsList.add(strTypeId);
					}
				}
			}
		}
		//????????????????????????ciCode????????????
		Map<String,Set<String>> ciCodesMap=new HashMap<String,Set<String>>();
		Iterator<Entry<String, Set<String>>> entries = ciCodeMap.entrySet().iterator();
		while(entries.hasNext()){
		    Entry<String, Set<String>> entry = entries.next();
		    String key = entry.getKey();
		    Set<String> value = entry.getValue();
		    ciCodesMap.put(key, value);
		}
		if(ciTypeIdsList!=null && ciTypeIdsList.size()>0) {
			Set<String> ciTypeIdSet=new HashSet<String>();
			for(String ciTypeIdStr:ciTypeIdsList) {
				//????????????Id??????????????????
				List<Map<String, Object>> relFocusList=typeFocusRelDao.queryInfluenceRelByTypeIdOptimize(ciTypeIdStr,domainId);
				//??????????????????????????????ID
				if(relFocusList!=null && relFocusList.size()>0) {
					for(Map<String, Object> map:relFocusList) {
						String typeId=(String) map.get("typeId");
						if(!ciTypeIdsList.contains(typeId)) {
							if(!historyTypeId.contains(typeId)) {
								ciTypeIdSet.add(typeId);
							}
						}
					}
				}
				//????????????Id??????????????????(??????????????????)
				List<Map<String, Object>> relFocusSelfList=typeFocusRelDao.queryInfluenceRelByTypeIdInfluenceSelf(ciTypeIdStr,domainId);
				//??????????????????
				List<Map<String, Object>> relFocusDataList=new ArrayList<Map<String, Object>>();
				relFocusDataList.addAll(relFocusList);
				relFocusDataList.addAll(relFocusSelfList);
				//???????????????????????????????????????
				if(relFocusDataList!=null && relFocusDataList.size()>0) {
					for(Map<String, Object> map:relFocusDataList) {
						String sourceTypeId=(String) map.get("sourceTypeId");
						String targetTypeId=(String) map.get("targetTypeId");
						String rltId=(String) map.get("rltId");
						List<String> rltIds=Arrays.asList(rltId.split(","));
						Set<String> ciCodes=(Set<String>) ciCodesMap.get(ciTypeIdStr);
						List<Map<String,Object>> dataList=null;
						if(sourceTypeId.equals(ciTypeIdStr) && !sourceTypeId.equals(targetTypeId)) {
							String [] relArray = rltIds.toArray(new String[rltIds.size()]);
							String [] ciCodeArray = ciCodes.toArray(new String[ciCodes.size()]);
							Map<String,Object> itemMap=new HashMap<String,Object>();
							itemMap.put("sourceTypeId", sourceTypeId);
							itemMap.put("targetTypeId", targetTypeId);
							itemMap.put("sourceCiCodeList", ciCodeArray);
							itemMap.put("targetCiCodeList", "");
							itemMap.put("relIdList", relArray);
							itemMap.put("domainId", domainId);
							if(ciCodeArray==null || ciCodeArray.length==0) {
								dataList=new ArrayList<Map<String,Object>>();
							}else {
								dataList=ciTypeRelDao.getImpactAnalysisRelDatasOptimize(itemMap);
							}
							Set<String> ciCodesSet=new HashSet<String>();
							//????????????ciCode
							for(Map<String,Object> mapData:dataList) {
								String ciBm=(String) mapData.get("targetCiBm");
								ciCodesSet.add(ciBm);
							}
							Set<String> ciCodeData=ciCodesMap.get(targetTypeId);
							if(ciCodeData==null) {
								ciCodeData=new HashSet<String>();
							}
							ciCodeData.addAll(ciCodesSet);
							ciCodesMap.put(targetTypeId, ciCodeData);
						}else if(targetTypeId.equals(ciTypeIdStr) && !sourceTypeId.equals(targetTypeId)) {
							String [] relArray = rltIds.toArray(new String[rltIds.size()]);
							String [] ciCodeArray = ciCodes.toArray(new String[ciCodes.size()]);
							Map<String,Object> itemMap=new HashMap<String,Object>();
							itemMap.put("sourceTypeId", sourceTypeId);
							itemMap.put("targetTypeId", targetTypeId);
							itemMap.put("sourceCiCodeList", "");
							itemMap.put("targetCiCodeList", ciCodeArray);
							itemMap.put("relIdList", relArray);
							itemMap.put("domainId", domainId);
							if(ciCodeArray==null || ciCodeArray.length==0) {
								dataList=new ArrayList<Map<String,Object>>();
							}else {
								dataList=ciTypeRelDao.getImpactAnalysisRelDatasOptimize(itemMap);
							}
							
							Set<String> ciCodesSet=new HashSet<String>();
							//????????????ciCode
							for(Map<String,Object> mapData:dataList) {
								String ciBm=(String) mapData.get("sourceCiBm");
								ciCodesSet.add(ciBm);
							}
							Set<String> ciCodeData=ciCodesMap.get(sourceTypeId);
							if(ciCodeData==null) {
								ciCodeData=new HashSet<String>();
							}
							ciCodeData.addAll(ciCodesSet);
							ciCodesMap.put(sourceTypeId, ciCodeData);
						}else if(sourceTypeId.equals(targetTypeId)) {
							String [] relArray = rltIds.toArray(new String[rltIds.size()]);
							String [] ciCodeArray = ciCodes.toArray(new String[ciCodes.size()]);
							Map<String,Object> itemMap=new HashMap<String,Object>();
							itemMap.put("sourceTypeId", sourceTypeId);
							itemMap.put("targetTypeId", targetTypeId);
							itemMap.put("sourceCiCodeList", ciCodeArray);
							itemMap.put("targetCiCodeList", ciCodeArray);
							itemMap.put("relIdList", relArray);
							itemMap.put("domainId", domainId);
							if(ciCodeArray==null || ciCodeArray.length==0) {
								dataList=new ArrayList<Map<String,Object>>();
							}else {
								dataList=ciTypeRelDao.getImpactAnalysisRelDatasSelf(itemMap);
							}
						}
						returnRelList.addAll(dataList);
					}					
				}
			}
			//???????????????????????????
			ciTypeIdList.clear();
			//???????????????????????????
			List<String> nextCiTypeIdList=new ArrayList<String>(ciTypeIdSet);
			//????????????????????????
			for(String ciTypeIdStr:ciTypeIdsList) {
				historyTypeId.add(ciTypeIdStr);
			}
			queryImpactAnalysisByCiCodeOptimize(ciTypeId,ciCode,nextCiTypeIdList,ciCodesMap,returnRelList,historyTypeId,domainId);
		}
		return returnRelList;
	}

	@Override
	public Map<String, Object> queryImpactAnalysisByCiId(String ciId,String ciTypeId,Integer upNum,Integer downNum,String ciCode) {
		List<Map<String, Object>> upSet=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> downSet=new ArrayList<Map<String,Object>>();
		Set<String> ciTypeIds=new HashSet<String>();
		//??????upNum???
		if (upNum!=null&&upNum>0) {
			//????????????ID??????????????????????????????????????????????????????????????????
			Map<String, Object> upMap = getCiTypeSingleRels(ciTypeId, upNum, "up");
			List<Map<String, Object>> list=(List<Map<String, Object>>) upMap.get("rels");
			for(Map<String, Object> map:list) {
				String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				ciTypeIds.add(sourceTypeId);
				ciTypeIds.add(targetTypeId);
			}
			//??????????????????
			upSet=filterListBySourceIdAndTargetId(list);
			//??????????????????????????????????????????????????????---start
			List<Map<String, Object>> upSelfList=new ArrayList<Map<String,Object>>();
			for(Map<String, Object> map:upSet) {
				String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				if(!sourceTypeId.equals(ciTypeId)) {
					if(!sourceTypeId.equals(targetTypeId)) {
						upSelfList.add(map);
					}					
				}
			}
			upSet=upSelfList;
			//??????????????????????????????????????????????????????---end
			
		}
		
		
		//??????downNum???
	    if (downNum!=null&&downNum>0) {
	    	//????????????ID??????????????????????????????????????????????????????????????????
	    	Map<String, Object> downMap = getCiTypeSingleRels(ciTypeId, downNum, "down");
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) downMap.get("rels");
	    	for(Map<String, Object> map:list) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				ciTypeIds.add(sourceTypeId);
				ciTypeIds.add(targetTypeId);
			}
	    	//??????????????????
	    	downSet=filterListBySourceIdAndTargetId(list);
	    	//??????????????????????????????????????????????????????---start
	    	List<Map<String, Object>> downSelfList=new ArrayList<Map<String,Object>>();
			for(Map<String, Object> map:downSet) {
				String targetTypeId=(String) map.get("targetTypeId");
				String sourceTypeId=(String) map.get("sourceTypeId");
				if(!targetTypeId.equals(ciTypeId)) {
					if(!targetTypeId.equals(sourceTypeId)) {
						downSelfList.add(map);
					}					
				}
			}
			downSet=downSelfList;
			//??????????????????????????????????????????????????????---end
	    }
	    
	    //??????????????????????????????--start
	    List<Map<String, Object>> selfList=new ArrayList<Map<String,Object>>();
	    for(String typeId:ciTypeIds) {
	    	//???????????????????????????????????????
	    	List<Map<String, Object>> list=ciTypeRelService.getCiDataRelBySelf(typeId);
	    	list=filterListBySourceIdAndTargetIdAndRelId(list);
	    	for(Map<String, Object> map:list) {
	    		selfList.add(map);
	    	}
	    }

	  //?????????????????????????????????????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> list4=getDownDataByImpact(downSet,ciTypeId,ciId,ciCode);
	    for(Map<String, Object> map:list4) {
	    	boolean flag=(boolean) map.get("isErgodic");
	    	if(flag) {
	    		continue;
	    	}else {
	    		list4=getDownDataByImpact(list4,ciTypeId,ciId,ciCode);
	    	}
	    }
	    downSet=list4;
	  //?????????????????????????????????????????????????????????????????????????????????????????????---end
	    for(Map<String, Object> map:downSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    	if(relDataList!=null && relDataList.size()>0) {
	    		List<Map<String, Object>> ownList=ciTypeRelService.getCiTypeRelByTypeId(sourceTypeId);
	    		for(Map<String, Object> map1:ownList) {
	    			String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		String targetTypeIdStr=(String) map1.get("targetTypeId");
		    		String relIdStr=(String) map1.get("relId");
		    		boolean checked=(boolean) map1.get("checked");
		    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
		    			if(ciTypeId.equals(sourceTypeIdStr)) {
		    				if(!checked) {
		    					List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			    				for(Map<String, Object> map2:relDataList) {
			    					String sourceCiId=(String) map2.get("sourceCiId");
			    					String sourceCiBm=(String) map2.get("sourceCiBm");
			    					if(ciCode.equals(sourceCiBm)) {
			    						list.add(map2);
			    					}
			    				}
			    				relDataList=list;
			    				map.put("relData", relDataList);
		    				}
		    			}else {
		    				if(!checked) {
		    					List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		    					Set<String> targetCiIds=new HashSet<String>();
		    					Set<String> targetCiBms=new HashSet<String>();
		    					for(Map<String, Object> map2:downSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(targetTypeId2.equals(sourceTypeId)) {
		    				    		for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("targetCiId");
		    				    			String targetCiBm=(String) map4.get("targetCiBm");
		    				    			targetCiBms.add(targetCiBm);
		    				    		}
		    				    	}
		    					}
		    					for(String ciCodeStr:targetCiBms) {
		    						for(Map<String, Object> map2:relDataList) {
				    					String sourceCiId=(String) map2.get("sourceCiId");
				    					String sourceCiBm=(String) map2.get("sourceCiBm");
				    					if(ciCodeStr.equals(sourceCiBm)) {
				    						list1.add(map2);
				    					}
				    				}
		    					}
		    					relDataList=list1;
		    					map.put("relData", relDataList);
		    				}
		    			}
		    		}
	    		}
	    		
	    	}	    	
	    }
	    //?????????????????????????????????????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> list3=getUpDataByImpact(upSet,ciTypeId,ciId,ciCode);
	    for(Map<String, Object> map:list3) {
	    	boolean flag=(boolean) map.get("isErgodic");
	    	if(flag) {
	    		continue;
	    	}else {
	    		list3=getUpDataByImpact(list3,ciTypeId,ciId,ciCode);
	    	}
	    }
	    upSet=list3;
	  //?????????????????????????????????????????????????????????????????????????????????????????????---end
	    for(Map<String, Object> map:upSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    	if(relDataList!=null && relDataList.size()>0) {
	    		List<Map<String, Object>> ownList=ciTypeRelService.getCiTypeRelByTypeId(targetTypeId);
	    		for(Map<String, Object> map1:ownList) {
	    			String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		String targetTypeIdStr=(String) map1.get("targetTypeId");
		    		String relIdStr=(String) map1.get("relId");
		    		boolean checked=(boolean) map1.get("checked");
		    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
		    			if(ciTypeId.equals(targetTypeIdStr)) {
		    				if(!checked) {
		    					List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			    				for(Map<String, Object> map2:relDataList) {
			    					String targetCiId=(String) map2.get("targetCiId");
			    					String targetCiBm=(String) map2.get("targetCiBm");
			    					if(ciCode.equals(targetCiBm)) {
			    						list.add(map2);
			    					}
			    				}
			    				relDataList=list;
			    				map.put("relData", relDataList);
		    				}
		    			}else {
		    				if(!checked) {
		    					List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		    					Set<String> sourceCiIds=new HashSet<String>();
		    					Set<String> sourceCiBms=new HashSet<String>();
		    					for(Map<String, Object> map2:upSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(sourceTypeId2.equals(targetTypeId)) {
		    				    		for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("sourceCiId");
		    				    			String sourceCiBm=(String) map4.get("sourceCiBm");
		    				    			sourceCiBms.add(sourceCiBm);
		    				    		}
		    				    	}
		    					}
		    					for(String ciCodeStr:sourceCiBms) {
		    						for(Map<String, Object> map2:relDataList) {
				    					String targetCiId=(String) map2.get("targetCiId");
				    					String targetCiBm=(String) map2.get("targetCiBm");
				    					if(ciCodeStr.equals(targetCiBm)) {
				    						list1.add(map2);
				    					}
				    				}
		    					}
		    					relDataList=list1;
		    					map.put("relData", relDataList);
		    				}
		    			}
		    		}
	    		}
	    	}
	    }
	    //???????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> downList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:downSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	if(ciTypeId.equals(sourceTypeId)) {
	    		downList.add(map);
	    	}
	    }
	    Integer downNumber=0;
	    for(Map<String, Object> map:downList) {
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	if(list==null || list.size()==0) {
	    		downNumber=downNumber+1;
	    	}
	    }	    
	    //???????????????????????????????????????????????????????????????---end	    
	    //???????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> upList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:upSet) {
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	if(ciTypeId.equals(targetTypeId)) {
	    		upList.add(map);
	    	}
	    }	    
        Integer upNumber=0;
	    for(Map<String, Object> map:upList) {
		    List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
		    String sourceTypeId=(String) map.get("sourceTypeId");
		    if(list==null || list.size()==0) {
		    	upNumber=upNumber+1;
		    }
		}
	    //???????????????????????????????????????????????????????????????---end
	    
	    //???????????????????????????CI????????????---start
	    if(upSet==null || upSet.size()==0) {
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    		//?????????????????????????????????????????????????????????????????????????????????????????????
	    		Set<String> typeIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			if(!ciTypeId.equals(sourceTypeId)) {
	    				typeIds.add(sourceTypeId);
	    			}	    			
	    		}
	    		//??????CI???????????????????????????????????????????????????CIID	    		
	    		for(String typeIdStr:typeIds) {
	    			Set<String> setCiIds=new HashSet<String>();
	    			Set<String> setCiCodes=new HashSet<String>();
	    			Set<String> setRelIds=new HashSet<String>();
	    			for(Map<String,Object> map: downSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCiCodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCiCodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			//?????????????????????????????????????????????????????????????????????
	    			for(Map<String, Object> map:selfList) {
		    			String sourceTypeId=(String) map.get("sourceTypeId");
		    			String relId=(String) map.get("relId");
		    			//???????????????????????????????????????????????????
		    			if(typeIdStr.equals(sourceTypeId)) {
		    				//?????????????????????????????????????????????????????????
		    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
		    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
		    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
		    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
		    	    			String rltId=(String) mapFocus.get("rltId");
		    	    			setRelIds.add(rltId);
		    	    		}	    	    		
		    			}
		    		}
	    			if(setRelIds!=null && setRelIds.size()>0 && setCiIds!=null && setCiIds.size()>0) {
	    				String ciIdsStr = String.join(",", setCiIds);
	    				String ciCodesStr = String.join(",", setCiCodes);
		    			String relIdsStr = String.join(",", setRelIds);
		    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciIdsStr,relIdsStr,typeIdStr,999,ciCodesStr);
		    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
		    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
		    			if(listData!=null && listData.size()>0) {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", listData);	
		    	    			}
		    	    		}
		    			}else {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
		    	    			}
		    	    		}
		    			}
		    		}else {
		    			for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(typeIdStr.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
		    		}
	    		}
	    		
	    	}
	    	//??????????????????????????????CI????????????---end
	    }
	    if(downSet==null || downSet.size()==0) {
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    		//?????????????????????????????????????????????????????????????????????????????????????????????
	    		Set<String> typeIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			if(!ciTypeId.equals(sourceTypeId)) {
	    				typeIds.add(sourceTypeId);
	    			}	    			
	    		}
	    		//??????CI???????????????????????????????????????????????????CIID	    		
	    		for(String typeIdStr:typeIds) {
	    			Set<String> setCiIds=new HashSet<String>();
	    			Set<String> setCiCodes=new HashSet<String>();
	    			Set<String> setRelIds=new HashSet<String>();
	    			for(Map<String,Object> map: upSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCiCodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCiCodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			//?????????????????????????????????????????????????????????????????????
	    			for(Map<String, Object> map:selfList) {
		    			String sourceTypeId=(String) map.get("sourceTypeId");
		    			String relId=(String) map.get("relId");
		    			//???????????????????????????????????????????????????
		    			if(typeIdStr.equals(sourceTypeId)) {
		    				//?????????????????????????????????????????????????????????
		    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
		    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
		    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
		    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
		    	    			String rltId=(String) mapFocus.get("rltId");
		    	    			setRelIds.add(rltId);
		    	    		}	    	    		
		    			}
		    		}
	    			if(setRelIds!=null && setRelIds.size()>0 && setCiCodes!=null && setCiCodes.size()>0) {
	    				String ciIdsStr = String.join(",", setCiIds);
	    				String ciCodesStr = String.join(",", setCiCodes);
		    			String relIdsStr = String.join(",", setRelIds);
		    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciIdsStr,relIdsStr,typeIdStr,999,ciCodesStr);
		    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
		    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
		    			if(listData!=null && listData.size()>0) {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", listData);	
		    	    			}
		    	    		}
		    			}else {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
		    	    			}
		    	    		}
		    			}
		    		}else {
		    			for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(typeIdStr.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
		    		}
	    		}
	    		
	    	}
	    	//??????????????????????????????CI????????????---end
	    }
	    if(upSet.size()>0 && downSet.size()>0) {
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    		//?????????????????????????????????????????????????????????????????????????????????????????????
	    		Set<String> typeIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			if(!ciTypeId.equals(sourceTypeId)) {
	    				typeIds.add(sourceTypeId);
	    			}	    			
	    		}
	    		//??????CI???????????????????????????????????????????????????CIID	    		
	    		for(String typeIdStr:typeIds) {
	    			Set<String> setCiIds=new HashSet<String>();
	    			Set<String> setCiCodes=new HashSet<String>();
	    			Set<String> setRelIds=new HashSet<String>();
	    			for(Map<String,Object> map: upSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCiCodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCiCodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			for(Map<String,Object> map: downSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCiCodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCiCodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			//?????????????????????????????????????????????????????????????????????
	    			for(Map<String, Object> map:selfList) {
		    			String sourceTypeId=(String) map.get("sourceTypeId");
		    			String relId=(String) map.get("relId");
		    			//???????????????????????????????????????????????????
		    			if(typeIdStr.equals(sourceTypeId)) {
		    				//?????????????????????????????????????????????????????????
		    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
		    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
		    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
		    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
		    	    			String rltId=(String) mapFocus.get("rltId");
		    	    			setRelIds.add(rltId);
		    	    		}	    	    		
		    			}
		    		}
	    			if(setRelIds!=null && setRelIds.size()>0 && setCiCodes!=null && setCiCodes.size()>0) {
	    				String ciIdsStr = String.join(",", setCiIds);
	    				String ciCodesStr = String.join(",", setCiCodes);
		    			String relIdsStr = String.join(",", setRelIds);
		    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciIdsStr,relIdsStr,typeIdStr,999,ciCodesStr);
		    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
		    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
		    			if(listData!=null && listData.size()>0) {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", listData);	
		    	    			}
		    	    		}
		    			}else {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
		    	    			}
		    	    		}
		    			}
		    		}else {
		    			for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(typeIdStr.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
		    		}
	    		}
	    	}
	    	//??????????????????????????????CI????????????---end
	    }
	    if(upSet.size()==0 && downSet.size()==0) {
	    	//??????upSet???downSet????????????????????????????????????????????????
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    	}
	    }
	  //?????????????????????????????????????????????
	    List<Map<String, Object>> selfNodes=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:selfList){
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    	Set<String> setCiIds=new HashSet<String>();
	    	Set<String> setCiCodes=new HashSet<String>();
	    	String sourceTypeName=(String) map.get("sourceTypeName");
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
	    	Map<String, Object> map3=new HashMap<String, Object>();
	    	if(list!=null && list.size()>0) {
	    		for(Map<String, Object> map1:list) {
	    			String sourceCiId=(String) map1.get("sourceCiId");
	    			String sourceCiBm=(String) map1.get("sourceCiBm");
	    			String targetCiId=(String) map1.get("targetCiId");
	    			String targetCiBm=(String) map1.get("targetCiBm");
	    			setCiIds.add(sourceCiId);
	    			setCiIds.add(targetCiId);
	    			setCiCodes.add(sourceCiBm);
	    			setCiCodes.add(targetCiBm);
	    		}
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		map2.put("ciClassId", sourceTypeId);
	    		map2.put("ciCodes", setCiCodes);
	    		map2.put("ciCount", setCiCodes.size());
	    		map2.put("className", sourceTypeName);
	    		dataList.add(map2);
	    		dataList.add(map2);	    		
	    		map3.put("nodes", dataList);
	    		selfNodes.add(map3);
	    	}	    	
	    }
	    //???????????????????????????CI????????????---end
	    
	    List<Map<String, Object>> allNodesList=new ArrayList<Map<String,Object>>();
	    //??????????????????????????????---start
	    if(upSet==null || upSet.size()==0) {
	    	List<String> ciTypeIdsList=new ArrayList<String>();
	    	ciTypeIdsList.add(ciTypeId);
	    	List<Map<String, Object>> list=queryAllNodesDown(ciTypeIdsList,downSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());	    	
	    	for(Map<String, Object> map:list) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		//??????????????????????????????????????????
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    		
	    	}
	    	
	    	//???????????????????????????????????????---start
	    	List<Map<String, Object>> selfNodesRen=new ArrayList<Map<String, Object>>();
	    	for(Map<String, Object> map:selfList) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		List<Map<String, Object>> relDatasSelf=(List<Map<String, Object>>) map.get("relData");
	    		for(Map<String, Object> map1:downSet) {
		    		String targetTypeId=(String) map1.get("targetTypeId");
		    		if(sourceTypeId.equals(targetTypeId)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String sourceCiId=(String) map4.get("sourceCiId");
			    				String sourceCiBm=(String) map4.get("sourceCiBm");
			    				boolean flag=ciCodes.contains(sourceCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list1=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list1!=null && list1.size()>0) {
		    						Map<String,Object> map6=list1.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    			}
		    			
		    		}
		    	}
	    	}	
	    	selfNodes=selfNodesRen;
	    	//???????????????????????????????????????---end
	    }	    
	    if(downSet==null || downSet.size()==0) {
	    	List<String> ciTypeIdsList=new ArrayList<String>();
	    	ciTypeIdsList.add(ciTypeId);
	    	List<Map<String, Object>> list=queryAllNodesUp(ciTypeIdsList,upSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());
	    	for(Map<String, Object> map:list) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    	}
	    	
	    	
	    	//???????????????????????????????????????---start
	    	List<Map<String, Object>> selfNodesRen=new ArrayList<Map<String, Object>>();
	    	for(Map<String, Object> map:selfList) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		List<Map<String, Object>> relDatasSelf=(List<Map<String, Object>>) map.get("relData");
	    		for(Map<String, Object> map1:upSet) {
		    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		if(sourceTypeId.equals(sourceTypeIdStr)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String targetCiId=(String) map4.get("targetCiId");
			    				String targetCiBm=(String) map4.get("targetCiBm");
			    				boolean flag=ciCodes.contains(targetCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list1=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list1!=null && list1.size()>0) {
		    						Map<String,Object> map6=list1.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    			}
		    			
		    		}
		    	}
	    	}	
	    	selfNodes=selfNodesRen;
	    	//???????????????????????????????????????---end
	    }
	    if(upSet.size()>0 && downSet.size()>0) {
	    	List<String> ciTypeIdsList=new ArrayList<String>();
	    	ciTypeIdsList.add(ciTypeId);
	    	List<Map<String, Object>> listUp=queryAllNodesUp(ciTypeIdsList,upSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());
	    	for(Map<String, Object> map:listUp) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    	}	
	    	List<Map<String, Object>> listDown=queryAllNodesDown(ciTypeIdsList,downSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());
	    	for(Map<String, Object> map:listDown) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    	}
	    	
	    	//???????????????????????????????????????---start
	    	List<Map<String, Object>> selfNodesRen=new ArrayList<Map<String, Object>>();
	    	for(Map<String, Object> map:selfList) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		List<Map<String, Object>> relDatasSelf=(List<Map<String, Object>>) map.get("relData");
	    		for(Map<String, Object> map1:downSet) {
		    		String targetTypeId=(String) map1.get("targetTypeId");
		    		if(sourceTypeId.equals(targetTypeId)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String sourceCiId=(String) map4.get("sourceCiId");
			    				String sourceCiBm=(String) map4.get("sourceCiBm");
			    				boolean flag=ciCodes.contains(sourceCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());		    				
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list!=null && list.size()>0) {
		    						Map<String,Object> map6=list.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    				
		    			}
		    			
		    		}
		    	}
	    		for(Map<String, Object> map1:upSet) {
		    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		if(sourceTypeId.equals(sourceTypeIdStr)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String targetCiId=(String) map4.get("targetCiId");
			    				String targetCiBm=(String) map4.get("targetCiBm");
			    				boolean flag=ciCodes.contains(targetCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list!=null && list.size()>0) {
		    						Map<String,Object> map6=list.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    			}
		    			
		    		}
		    	}
	    	}
	    	selfNodes=selfNodesRen;
	    	//???????????????????????????????????????---end
	    }	    
	    //??????????????????????????????---end
	    
	    //?????????????????????ciIds?????????????????????ci?????????
	    Set<String> allCiIdsSet=new HashSet<String>();
	    Set<String> allCiCodesSet=new HashSet<String>();
	    //??????????????????????????????ciTypeId?????????????????????
	    Set<String> allCiTypeIdsSet=new HashSet<String>();
	    //????????????ci????????????---start
	    List<Map<String, Object>> allCiReList=new ArrayList<Map<String,Object>>();
	    Set<Map<String, Object>> setRelInfo=new HashSet<Map<String,Object>>(); 
	    if(upSet!=null && upSet.size()>0) {
	    	for(Map<String, Object> map:upSet) {
	    		List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		String targetTypeId=(String) map.get("targetTypeId");
	    		allCiTypeIdsSet.add(sourceTypeId);
	    		allCiTypeIdsSet.add(targetTypeId);
	    		String relId=(String) map.get("relId");
	    		String relName=(String) map.get("relName");
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);    		
	    		for(Map<String, Object> map1:list) {
	    			String sourceCiId=(String) map1.get("sourceCiId");
	    			String sourceCiBm=(String) map1.get("sourceCiBm");
	    			String targetCiId=(String) map1.get("targetCiId");
	    			String targetCiBm=(String) map1.get("targetCiBm");
	    			allCiIdsSet.add(sourceCiId);
	    			allCiIdsSet.add(targetCiId);
	    			allCiCodesSet.add(sourceCiBm);
	    			allCiCodesSet.add(targetCiBm);
	    			allCiReList.add(map1);
	    		}
	    	}
	    }
	    if(downSet!=null && downSet.size()>0) {
	    	for(Map<String, Object> map:downSet) {
	    		List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		String targetTypeId=(String) map.get("targetTypeId");
	    		allCiTypeIdsSet.add(sourceTypeId);
	    		allCiTypeIdsSet.add(targetTypeId);
	    		String relId=(String) map.get("relId");
	    		String relName=(String) map.get("relName");
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);	    		
	    		for(Map<String, Object> map1:list) {
	    			String sourceCiId=(String) map1.get("sourceCiId");
	    			String sourceCiBm=(String) map1.get("sourceCiBm");
	    			String targetCiId=(String) map1.get("targetCiId");
	    			String targetCiBm=(String) map1.get("targetCiBm");
	    			allCiIdsSet.add(sourceCiId);
	    			allCiIdsSet.add(targetCiId);
	    			allCiCodesSet.add(sourceCiBm);
	    			allCiCodesSet.add(targetCiBm);
	    			allCiReList.add(map1);
	    		}
	    	}
	    }
	    //?????????????????????????????????????????????
	    for(Map<String, Object> map:selfList){
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    	for(Map<String, Object> map1:list) {
	    		allCiReList.add(map1);
	    		String sourceCiId=(String) map1.get("sourceCiId");
	    		String sourceCiBm=(String) map1.get("sourceCiBm");
	    		String targetCiId=(String) map1.get("targetCiId");
	    		String targetCiBm=(String) map1.get("targetCiBm");
	    		allCiIdsSet.add(sourceCiId);
	    		allCiIdsSet.add(targetCiId);
	    		allCiCodesSet.add(sourceCiBm);
	    		allCiCodesSet.add(targetCiBm);
	    		String sourceTypeId=(String) map1.get("sourceTypeId");
	    		String targetTypeId=(String) map1.get("targetTypeId");
	    		allCiTypeIdsSet.add(sourceTypeId);
	    		allCiTypeIdsSet.add(targetTypeId);
	    		String relId=(String) map1.get("relId");
	    		String relName=(String) map1.get("relName");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);    	
	    	}
	    }
	    //????????????ci????????????---end    
	    List<String> list = new ArrayList<String>(allCiIdsSet);
	    List<String> listCiCode = new ArrayList<String>(allCiCodesSet);
	    List<Map<String, Object>> listData=new ArrayList<Map<String,Object>>();
	    if(listCiCode!=null && listCiCode.size()>0) {
	    	listData=typeDao.findClassInfoAndCiInfoByCiIds(listCiCode);
	    }
	    //????????????????????????????????????????????????---start
	    List<String> allCiTypeIdsList = new ArrayList<String>(allCiTypeIdsSet);
	    List<Map<String, Object>> allClassInfoList=new ArrayList<Map<String,Object>>();
	    if(allCiTypeIdsList!=null && allCiTypeIdsList.size()>0) {
	    	allClassInfoList=typeDao.findClassInfoByCiTypeIds(allCiTypeIdsList);
	    }	    
	    List<Map<String, Object>> classInfoAndRelInfoList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:allClassInfoList) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    for(Map<String, Object> map:setRelInfo) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    //????????????????????????????????????????????????---end
	    //???????????????????????????????????????---start
	    Set<String> setTypeIds=new HashSet<String>();
	    for(Map<String, Object> map:allCiReList) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	setTypeIds.add(sourceTypeId);
	    	setTypeIds.add(targetTypeId);
	    }
	    List<Map<String, Object>> selfNodesNew=new ArrayList<Map<String, Object>>();
	    for(Map<String, Object> map5:selfNodes) {
			List<Map<String, Object>> list5=(List<Map<String, Object>>) map5.get("nodes");
			if(list!=null && list.size()>0) {
				Map<String,Object> map6=list5.get(0);
				String ciClassId=(String) map6.get("ciClassId");
				Integer ciCount6=(Integer) map6.get("ciCount");
				boolean flag=setTypeIds.contains(ciClassId);
				if(flag) {
					if(selfNodesNew!=null && selfNodesNew.size()>0) {
						for(Map<String, Object> map:selfNodesNew) {
							List<Map<String, Object>> list6=(List<Map<String, Object>>) map5.get("nodes");
							if(list!=null && list.size()>0) {
								Map<String,Object> map7=list5.get(0);
								String ciClassId7=(String) map7.get("ciClassId");
								Integer ciCount7=(Integer) map7.get("ciCount");
								if(!ciClassId7.equals(ciClassId) && !ciCount6.equals(ciCount7)) {
									selfNodesNew.add(map5);
								}
							}
						}
					}else {
						selfNodesNew.add(map5);
					}					
				}
			}
		}
	    for(Map<String, Object> map:selfNodesNew){
	    	allNodesList.add(map);
	    }
	    //???????????????????????????????????????---end
	    Map<String, Object> returnMap=new HashMap<String, Object>();
	    returnMap.put("friendPaths", allNodesList);
	    returnMap.put("ciRltLines", allCiReList);
	    returnMap.put("ciNodes", listData);
	    if(listData!=null && listData.size()>0) {
	    	for(Map<String, Object> map:listData) {
		    	String ciIdStr=(String) map.get("ciId");
		    	String ciBmStr=(String) map.get("ciBm");
		    	if(ciCode.equals(ciBmStr)) {
		    		returnMap.put("startCi", map);
		    	}
		    }
	    }else {
	    	returnMap.put("startCi", new HashedMap());
	    }
	    returnMap.put("ciClassInfos", classInfoAndRelInfoList);
		return returnMap;
	}
	
	public List<Map<String, Object>> queryAllNodesDown(List<String> ciTypeIdsList,List<Map<String, Object>> downSet,List<Map<String, Object>> dataList,String typeId,Map<String, Object> itemMap,List<Map<String, Object>> returnList){
		for(String ciTypeId:ciTypeIdsList) {
    		List<Map<String, Object>> list=queryDownClassInfo(ciTypeId,downSet,dataList);
    		if(list==null || list.size()==0) {
    			itemMap.put("nodes", dataList);
    			returnList.add(itemMap);
    			break;
    		}
    		for(Map<String, Object> map:list) {
    			String sourceTypeId=(String) map.get("sourceTypeId");
		    	String sourceTypeBm=(String) map.get("sourceTypeBm");
		    	String targetTypeId=(String) map.get("targetTypeId");
		    	String targetTypeBm=(String) map.get("targetTypeBm");
		    	List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map.get("relData");
		    	if(relDatas!=null && relDatas.size()>0) {
		    		Set<String> sourceCis=new HashSet<String>();
		    		Set<String> sourceCiCodes=new HashSet<String>();
			    	Set<String> targetCis=new HashSet<String>();
			    	Set<String> targetCiCodes=new HashSet<String>();
			    	for(Map<String, Object> map1:relDatas) {
			    		String sourceCiId=(String) map1.get("sourceCiId");
			    		String sourceCiBm=(String) map1.get("sourceCiBm");
			    		String targetCiId=(String) map1.get("targetCiId");
			    		String targetCiBm=(String) map1.get("targetCiBm");
			    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
			    		String targetTypeIdStr=(String) map1.get("targetTypeId");
			    		if(sourceTypeId.equals(sourceTypeIdStr)) {
			    			sourceCiCodes.add(sourceCiBm);
			    		}
			    		if(targetTypeId.equals(targetTypeIdStr)) {
			    			targetCiCodes.add(targetCiBm);
			    		}
			    	}
			    	Map<String, Object> sourceMap=new HashMap<String, Object>();
			    	sourceMap.put("className", sourceTypeBm);
			    	sourceMap.put("ciClassId", sourceTypeId);
			    	sourceMap.put("ciCodes", sourceCiCodes);
			    	sourceMap.put("ciCount", sourceCiCodes.size());
			    	Map<String, Object> targetMap=new HashMap<String, Object>();
			    	targetMap.put("className", targetTypeBm);
			    	targetMap.put("ciClassId", targetTypeId);
			    	targetMap.put("ciCodes", targetCiCodes);
			    	targetMap.put("ciCount", targetCiCodes.size());
			    	if(ciTypeId.equals(typeId)) {
			    		dataList.add(sourceMap);
			    		dataList.add(targetMap);		    			    	
			    	}else {
			    		dataList.add(targetMap);
			    	}
			    	List<String> ciTypeIds=new ArrayList<String>();
			    	ciTypeIds.add(targetTypeId);
			    	queryAllNodesDown(ciTypeIds,downSet,dataList,typeId,itemMap,returnList);
			        dataList=new ArrayList<Map<String,Object>>();
			        itemMap=new HashMap<String, Object>();
		    	}
    		}
    	}
    	return returnList;
	}
    public List<Map<String, Object>> queryAllNodesUp(List<String> ciTypeIdsList,List<Map<String, Object>> upSet,List<Map<String, Object>> dataList,String typeId,Map<String, Object> itemMap,List<Map<String, Object>> returnList){
    	for(String ciTypeId:ciTypeIdsList) {
    		List<Map<String, Object>> list=queryUpClassInfo(ciTypeId,upSet);
    		if(list==null || list.size()==0) {
    			itemMap.put("nodes", dataList);
    			returnList.add(itemMap);
    			break;
    		}
    		for(Map<String, Object> map:list) {
    			String sourceTypeId=(String) map.get("sourceTypeId");
		    	String sourceTypeBm=(String) map.get("sourceTypeBm");
		    	String targetTypeId=(String) map.get("targetTypeId");
		    	String targetTypeBm=(String) map.get("targetTypeBm");
		    	List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map.get("relData");
		    	if(relDatas!=null && relDatas.size()>0) {
		    		Set<String> sourceCis=new HashSet<String>();
		    		Set<String> sourceCiCodes=new HashSet<String>();
			    	Set<String> targetCis=new HashSet<String>();
			    	Set<String> targetCiCodes=new HashSet<String>();
			    	for(Map<String, Object> map1:relDatas) {
			    		String sourceCiId=(String) map1.get("sourceCiId");
			    		String sourceCiBm=(String) map1.get("sourceCiBm");
			    		String targetCiId=(String) map1.get("targetCiId");
			    		String targetCiBm=(String) map1.get("targetCiBm");
			    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
			    		String targetTypeIdStr=(String) map1.get("targetTypeId");
			    		if(sourceTypeId.equals(sourceTypeIdStr)) {
			    			sourceCiCodes.add(sourceCiBm);
			    		}
			    		if(targetTypeId.equals(targetTypeIdStr)) {
			    			targetCiCodes.add(targetCiBm);
			    		}
			    	}
			    	Map<String, Object> sourceMap=new HashMap<String, Object>();
			    	sourceMap.put("className", sourceTypeBm);
			    	sourceMap.put("ciClassId", sourceTypeId);
			    	sourceMap.put("ciCodes", sourceCiCodes);
			    	sourceMap.put("ciCount", sourceCiCodes.size());
			    	Map<String, Object> targetMap=new HashMap<String, Object>();
			    	targetMap.put("className", targetTypeBm);
			    	targetMap.put("ciClassId", targetTypeId);
			    	targetMap.put("ciCodes", targetCiCodes);
			    	targetMap.put("ciCount", targetCiCodes.size());
			    	if(ciTypeId.equals(typeId)) {
			    		dataList.add(targetMap);
			    		dataList.add(sourceMap);		    			    	
			    	}else {
			    		dataList.add(sourceMap);
			    	}
			    	List<String> ciTypeIds=new ArrayList<String>();
			    	ciTypeIds.add(sourceTypeId);
			    	queryAllNodesUp(ciTypeIds,upSet,dataList,typeId,itemMap,returnList);
			        dataList=new ArrayList<Map<String,Object>>();
			        itemMap=new HashMap<String, Object>();
		    	}
    		}
    	}
    	return returnList;
	}
	public List<Map<String, Object>> queryDownClassInfo(String id,List<Map<String, Object>> list,List<Map<String, Object>> dataList){
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String targetTypeId=(String) map.get("targetTypeId");
			List<Map<String, Object>> listData=(List<Map<String, Object>>) map.get("relData");
			if(sourceTypeId.equals(id)) {
				if(listData!=null && listData.size()>0) {
					//?????????????????????????????????????????????---2020-06-23
					if(!sourceTypeId.equals(targetTypeId)) {
						if(dataList!=null && dataList.size()>0) {
							boolean flag1=true;
							boolean flag2=true;
							for(Map<String, Object> mapData:dataList) {
								String ciClassId=(String) mapData.get("ciClassId");
								if(sourceTypeId.equals(ciClassId)) {
									flag1=false;
								}
							}
							for(Map<String, Object> mapData:dataList) {
								String ciClassId=(String) mapData.get("ciClassId");
								if(targetTypeId.equals(ciClassId)) {
									flag2=false;
								}
							}
							if(!flag1 && !flag2) {
								continue;
							}else {
								returnList.add(map);
							}
						}else {
							returnList.add(map);
						}
						
					}
					
				}
				
			}			
		}
		return returnList;
	}
	
	public List<Map<String, Object>> queryUpCiInfoToCiDataRel(String id,List<Map<String, Object>> list){
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			String sourceCiId=(String) map.get("sourceCiId");
			String targetCiId=(String) map.get("targetCiId");
			if(targetCiId.equals(id)) {
				//?????????????????????????????????????????????---2020-07-15
				if(!sourceCiId.equals(targetCiId)) {
					returnList.add(map);
				}			
			}			
		}
		return returnList;
	}
	public List<Map<String, Object>> queryUpClassInfo(String id,List<Map<String, Object>> list){
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String targetTypeId=(String) map.get("targetTypeId");
			List<Map<String, Object>> listData=(List<Map<String, Object>>) map.get("relData");
			if(targetTypeId.equals(id)) {
				if(listData!=null && listData.size()>0) {
					//?????????????????????????????????????????????---2020-06-23
					if(!sourceTypeId.equals(targetTypeId)) {
						returnList.add(map);
					}
				}
				
			}			
		}
		return returnList;
	}
	public  List<Map<String, Object>> getUpDataByImpact(List<Map<String, Object>> upSet,String ciTypeId,String ciId,String ciCode){
		for(Map<String, Object> map:upSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relList=ciTypeRelService.getCiTypeRelByTypeId(sourceTypeId);
	    	List<Map<String, Object>> relDataList=new ArrayList<Map<String,Object>>();
	    	for(Map<String, Object> map1:relList) {
	    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
	    		String targetTypeIdStr=(String) map1.get("targetTypeId");
	    		String relIdStr=(String) map1.get("relId");
	    		boolean checked=(boolean) map1.get("checked");
	    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
	    			if(checked) {
	    				if(ciTypeId.equals(targetTypeId)) {
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					if(ciCode!=null && !"".equals(ciCode)) {
    						    itemMap.put("ciCodeList", ciCode.split(","));
    					    }else {
    						    itemMap.put("ciCodeList", "");
    					    }
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
	    					relDataList=ciTypeRelService.getCiDataRelByTargetIdAndSourceIdAndCiIds(itemMap);
	    				}else {
	    					Set<String> sourceCiIds=new HashSet<String>();
	    					Set<String> sourceCiBms=new HashSet<String>();
	    					for(Map<String, Object> map2:upSet) {
	    						String sourceTypeId2=(String) map2.get("sourceTypeId");
	    				    	String targetTypeId2=(String) map2.get("targetTypeId");
	    				    	String relId2=(String) map2.get("relId");
	    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
	    				    	if(sourceTypeId2.equals(targetTypeId)) {
	    				    		if(list!=null && list.size()>0) {
	    				    			for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("sourceCiId");
		    				    			String sourceCiBm=(String) map4.get("sourceCiBm");
		    				    			sourceCiBms.add(sourceCiBm);
		    				    		}
	    				    		}else if(list==null){
	    				    			map.put("isErgodic", false);
	    				    		}
	    				    		
	    				    	}
	    					}
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					String[] ssourceCiBmsArray = sourceCiBms.toArray(new String[sourceCiBms.size()]);
	    					if(ssourceCiBmsArray.length>0) {
	    						if(sourceCiBms!=null && sourceCiBms.size()>0) {
		    						itemMap.put("ciCodeList", ssourceCiBmsArray);
		    					}else {
		    						itemMap.put("ciCodeList", "");
		    					}
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
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelByTargetIdAndSourceIdAndCiIds(itemMap);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}		    					
	    					}
	    					//??????up???????????????????????????????????????sourceTypeId??????????????????---start
	    					if(sourceCiBms==null || sourceCiBms.size()==0) {
	    						for(Map<String, Object> map2:upSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> listRel=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(sourceTypeId2.equals(sourceTypeId)) {
		    				    		if(listRel!=null && listRel.size()>0) {
		    				    			for(Map<String, Object> map4:listRel) {
			    				    			String id=(String) map4.get("sourceCiId");
			    				    			String sourceCiBm=(String) map4.get("sourceCiBm");
			    				    			sourceCiBms.add(sourceCiBm);
			    				    		}
		    				    		}
		    				    		
		    				    	}
		    					}
	    					}
	    					Map<String, Object> itemMap1 = new HashMap<String, Object>();
	    					String[] sourceCiBmsArray1 = sourceCiBms.toArray(new String[sourceCiBms.size()]);
	    					if(sourceCiBmsArray1.length>0) {
	    						if(sourceCiBms!=null && sourceCiBms.size()>0) {
	    							itemMap1.put("ciCodeList", sourceCiBmsArray1);
		    					}else {
		    						itemMap1.put("ciCodeList", "");
		    					}
		    					if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
		    						itemMap1.put("sourceTypeId", sourceTypeId);
		    					}else {
		    						itemMap1.put("sourceTypeId", "");
		    					}
		    					if(targetTypeId!=null && !"".equals(targetTypeId)) {
		    						itemMap1.put("targetTypeId", targetTypeId);
		    					}else {
		    						itemMap1.put("targetTypeId", "");
		    					}
		    					if(relId!=null && !"".equals(relId)) {
		    						itemMap1.put("relId", relId);
		    					}else {
		    						itemMap1.put("relId", "");
		    					}
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelByTargetIdAndSourceIdAndCiIdsUp(itemMap1);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}		    					
	    					}
	    					//??????up???????????????????????????????????????sourceTypeId??????????????????---end
	    				}
	    				boolean flag=map.containsKey("isErgodic");
	    				if(!flag) {
	    					map.put("isErgodic", true);
	    				}	    				
	    				map.put("relData", relDataList);
	    			}else {
	    				map.put("isErgodic", true);
	    				map.put("relData", relDataList);
	    				break;
	    			}
	    		}
	    	}
	    }
		return upSet;
	}
	
	public List<Map<String, Object>> getUpDataByRoot(List<Map<String, Object>> upSet,String ciTypeId,String ciId,String ciCode){
		for(Map<String, Object> map:upSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relList=ciTypeRelService.getCiTypeRelByTypeId(targetTypeId);
	    	List<Map<String, Object>> relDataList=new ArrayList<Map<String,Object>>();
	    	for(Map<String, Object> map1:relList) {
	    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
	    		String targetTypeIdStr=(String) map1.get("targetTypeId");
	    		String relIdStr=(String) map1.get("relId");
	    		boolean checked=(boolean) map1.get("checked");
	    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
	    			if(checked) {
	    				if(ciTypeId.equals(targetTypeId)) {
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					if(ciCode!=null && !"".equals(ciCode)) {
	    						itemMap.put("ciCodeList", ciCode.split(","));
	    					}else {
	    						itemMap.put("ciCodeList", "");
	    					}
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
	    					relDataList=ciTypeRelService.getCiDataRelByTargetIdAndSourceIdAndCiIds(itemMap);
	    				}else {
	    					Set<String> sourceCiIds=new HashSet<String>();
	    					Set<String> sourceCiCodes=new HashSet<String>();
	    					for(Map<String, Object> map2:upSet) {
	    						String sourceTypeId2=(String) map2.get("sourceTypeId");
	    				    	String targetTypeId2=(String) map2.get("targetTypeId");
	    				    	String relId2=(String) map2.get("relId");
	    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
	    				    	if(sourceTypeId2.equals(targetTypeId)) {
	    				    		if(list!=null && list.size()>0) {
	    				    			for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("sourceCiId");
		    				    			String sourceCiBm=(String) map4.get("sourceCiBm");
		    				    			sourceCiCodes.add(sourceCiBm);
		    				    		}
	    				    		}else if(list==null){
	    				    			map.put("isErgodic", false);
	    				    		}
	    				    		
	    				    	}
	    					}
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					String[] sourceCiCodesArray = sourceCiCodes.toArray(new String[sourceCiCodes.size()]);
	    					if(sourceCiCodesArray.length>0) {
	    						if(sourceCiCodes!=null && sourceCiCodes.size()>0) {
		    						itemMap.put("ciCodeList", sourceCiCodesArray);
		    					}else {
		    						itemMap.put("ciCodeList", "");
		    					}
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
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelByTargetIdAndSourceIdAndCiIds(itemMap);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}
	    					}
	    					//??????up???????????????????????????????????????sourceTypeId??????????????????---start
	    					if(sourceCiCodes==null || sourceCiCodes.size()==0) {
	    						for(Map<String, Object> map2:upSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> listRel=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(sourceTypeId2.equals(sourceTypeId)) {
		    				    		if(listRel!=null && listRel.size()>0) {
		    				    			for(Map<String, Object> map4:listRel) {
			    				    			String id=(String) map4.get("sourceCiId");
			    				    			String sourceCiBm=(String) map4.get("sourceCiBm");
			    				    			sourceCiCodes.add(sourceCiBm);
			    				    		}
		    				    		}
		    				    		
		    				    	}
		    					}
	    					}
	    					Map<String, Object> itemMap1 = new HashMap<String, Object>();
	    					String[] sourceCiCodesArray1 = sourceCiCodes.toArray(new String[sourceCiCodes.size()]);
	    					if(sourceCiCodesArray1.length>0) {
	    						if(sourceCiCodes!=null && sourceCiCodes.size()>0) {
	    							itemMap1.put("ciCodeList", sourceCiCodesArray1);
		    					}else {
		    						itemMap1.put("ciCodeList", "");
		    					}
		    					if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
		    						itemMap1.put("sourceTypeId", sourceTypeId);
		    					}else {
		    						itemMap1.put("sourceTypeId", "");
		    					}
		    					if(targetTypeId!=null && !"".equals(targetTypeId)) {
		    						itemMap1.put("targetTypeId", targetTypeId);
		    					}else {
		    						itemMap1.put("targetTypeId", "");
		    					}
		    					if(relId!=null && !"".equals(relId)) {
		    						itemMap1.put("relId", relId);
		    					}else {
		    						itemMap1.put("relId", "");
		    					}
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelByTargetIdAndSourceIdAndCiIdsUp(itemMap1);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}		    					
	    					}
	    					//??????up???????????????????????????????????????sourceTypeId??????????????????---end
	    				}
	    				boolean flag=map.containsKey("isErgodic");
	    				if(!flag) {
	    					map.put("isErgodic", true);
	    				}	  
	    				map.put("relData", relDataList);
	    			}else {
	    				map.put("isErgodic", true);
	    				map.put("relData", relDataList);
	    				break;
	    			}
	    		}
	    	}
	    }
		return upSet;
	}
	
	public  List<Map<String, Object>> getDownDataByImpact(List<Map<String, Object>> downSet,String ciTypeId,String ciId,String ciCode){
		for(Map<String, Object> map:downSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relList=ciTypeRelService.getCiTypeRelByTypeId(targetTypeId);
	    	List<Map<String, Object>> relDataList=new ArrayList<Map<String,Object>>();
	    	for(Map<String, Object> map1:relList) {
	    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
	    		String targetTypeIdStr=(String) map1.get("targetTypeId");
	    		String relIdStr=(String) map1.get("relId");
	    		boolean checked=(boolean) map1.get("checked");
	    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
	    			if(checked) {
	    				if(ciTypeId.equals(sourceTypeId)) {
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					if(ciCode!=null && !"".equals(ciCode)) {
	    						itemMap.put("ciCodeList", ciCode.split(","));
	    					}else {
	    						itemMap.put("ciCodeList", "");
	    					}
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
	    					relDataList=ciTypeRelService.getCiDataRelBySourceIdAndTargetIdAndCiIds(itemMap);
	    				}else {
	    					Set<String> targetCiIds=new HashSet<String>();
	    					Set<String> targetCiBms=new HashSet<String>();
	    					for(Map<String, Object> map2:downSet) {
	    						String sourceTypeId2=(String) map2.get("sourceTypeId");
	    				    	String targetTypeId2=(String) map2.get("targetTypeId");
	    				    	String relId2=(String) map2.get("relId");
	    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
	    				    	if(targetTypeId2.equals(sourceTypeId)) {
	    				    		if(list!=null && list.size()>0) {
	    				    			for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("targetCiId");
		    				    			String targetCiBm=(String) map4.get("targetCiBm");
		    				    			targetCiBms.add(targetCiBm);
		    				    		}
	    				    		}else if(list==null){
	    				    			map.put("isErgodic", false);
	    				    		}
	    				    		
	    				    	}
	    					}
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					String[] targetCiBmsArray = targetCiBms.toArray(new String[targetCiBms.size()]);
	    					if(targetCiBmsArray.length>0) {
	    						if(targetCiBms!=null && targetCiBms.size()>0) {
		    						itemMap.put("ciCodeList", targetCiBmsArray);
		    					}else {
		    						itemMap.put("ciCodeList", "");
		    					}
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
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelBySourceIdAndTargetIdAndCiIds(itemMap);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}
	    					}
	    					//??????dowm???????????????????????????????????? ?????????????????????targetTypeId---start
	    					if(targetCiBms==null || targetCiBms.size()==0) {
	    						for(Map<String, Object> map2:downSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> listRel=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(targetTypeId2.equals(targetTypeId)) {
		    				    		if(listRel!=null && listRel.size()>0) {
		    				    			for(Map<String, Object> map4:listRel) {
			    				    			String id=(String) map4.get("targetCiId");
			    				    			String targetCiBm=(String) map4.get("targetCiBm");
			    				    			targetCiBms.add(targetCiBm);
			    				    		}
		    				    		}
		    				    		
		    				    	}
		    					}
	    					}
	    					Map<String, Object> itemMap1 = new HashMap<String, Object>();
	    					String[] targetCiBmsArray1 = targetCiBms.toArray(new String[targetCiBms.size()]);
	    					if(targetCiBmsArray1.length>0) {
	    						if(targetCiBms!=null && targetCiBms.size()>0) {
	    							itemMap1.put("ciCodeList", targetCiBmsArray1);
		    					}else {
		    						itemMap1.put("ciCodeList", "");
		    					}
		    					if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
		    						itemMap1.put("sourceTypeId", sourceTypeId);
		    					}else {
		    						itemMap1.put("sourceTypeId", "");
		    					}
		    					if(targetTypeId!=null && !"".equals(targetTypeId)) {
		    						itemMap1.put("targetTypeId", targetTypeId);
		    					}else {
		    						itemMap1.put("targetTypeId", "");
		    					}
		    					if(relId!=null && !"".equals(relId)) {
		    						itemMap1.put("relId", relId);
		    					}else {
		    						itemMap1.put("relId", "");
		    					}
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelBySourceIdAndTargetIdAndCiIdsDown(itemMap1);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}		    					
	    					}
	    					//??????dowm???????????????????????????????????? ?????????????????????targetTypeId---end
	    					
	    				}
	    				boolean flag=map.containsKey("isErgodic");
	    				if(!flag) {
	    					map.put("isErgodic", true);
	    				}	   
	    				map.put("relData", relDataList);
	    			}else {
	    				map.put("isErgodic", true);
	    				map.put("relData", relDataList);
	    				break;
	    			}
	    		}
	    	}
	    }
		return downSet;
	}
	public List<Map<String, Object>> getDownDataByRoot(List<Map<String, Object>> downSet,String ciTypeId,String ciId,String ciCode){
		for(Map<String, Object> map:downSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relList=ciTypeRelService.getCiTypeRelByTypeId(sourceTypeId);
	    	List<Map<String, Object>> relDataList=new ArrayList<Map<String,Object>>();
	    	for(Map<String, Object> map1:relList) {
	    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
	    		String targetTypeIdStr=(String) map1.get("targetTypeId");
	    		String relIdStr=(String) map1.get("relId");
	    		boolean checked=(boolean) map1.get("checked");
	    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
	    			if(checked) {
	    				if(ciTypeId.equals(sourceTypeId)) {
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					if(ciCode!=null && !"".equals(ciCode)) {
    						    itemMap.put("ciCodeList", ciCode.split(","));
    					    }else {
    						    itemMap.put("ciCodeList", "");
    					    }
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
	    					relDataList=ciTypeRelService.getCiDataRelBySourceIdAndTargetIdAndCiIds(itemMap);
	    				}else {
	    					Set<String> targetCiCodes=new HashSet<String>();
	    					for(Map<String, Object> map2:downSet) {
	    				    	String targetTypeId2=(String) map2.get("targetTypeId");
	    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
	    				    	if(targetTypeId2.equals(sourceTypeId)) {
	    				    		if(list!=null && list.size()>0) {
	    				    			for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("targetCiId");
		    				    			String targetCiBm=(String) map4.get("targetCiBm");
		    				    			targetCiCodes.add(targetCiBm);
		    				    		}
	    				    		}else if(list==null){
	    				    			map.put("isErgodic", false);
	    				    		}
	    				    		
	    				    	}
	    					}
	    					Map<String, Object> itemMap = new HashMap<String, Object>();
	    					String[] targetCiCodesArray = targetCiCodes.toArray(new String[targetCiCodes.size()]);
	    					if(targetCiCodesArray.length>0) {
	    						if(targetCiCodes!=null && targetCiCodes.size()>0) {
		    						itemMap.put("ciCodeList", targetCiCodesArray);
		    					}else {
		    						itemMap.put("ciCodeList", "");
		    					}
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
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelBySourceIdAndTargetIdAndCiIds(itemMap);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}
	    					}
	    					
	    					//??????dowm???????????????????????????????????? ?????????????????????targetTypeId---start
	    					if(targetCiCodes==null || targetCiCodes.size()==0) {
	    						for(Map<String, Object> map2:downSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> listRel=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(targetTypeId2.equals(targetTypeId)) {
		    				    		if(listRel!=null && listRel.size()>0) {
		    				    			for(Map<String, Object> map4:listRel) {
			    				    			String id=(String) map4.get("targetCiId");
			    				    			String targetCiBm=(String) map4.get("targetCiBm");
			    				    			targetCiCodes.add(targetCiBm);
			    				    		}
		    				    		}
		    				    		
		    				    	}
		    					}
	    					}
	    					Map<String, Object> itemMap1 = new HashMap<String, Object>();
	    					String[] targetCiCodesArray1 = targetCiCodes.toArray(new String[targetCiCodes.size()]);
	    					if(targetCiCodesArray1.length>0) {
	    						if(targetCiCodes!=null && targetCiCodes.size()>0) {
	    							itemMap1.put("ciCodeList", targetCiCodesArray1);
		    					}else {
		    						itemMap1.put("ciCodeList", "");
		    					}
		    					if(sourceTypeId!=null && !"".equals(sourceTypeId)) {
		    						itemMap1.put("sourceTypeId", sourceTypeId);
		    					}else {
		    						itemMap1.put("sourceTypeId", "");
		    					}
		    					if(targetTypeId!=null && !"".equals(targetTypeId)) {
		    						itemMap1.put("targetTypeId", targetTypeId);
		    					}else {
		    						itemMap1.put("targetTypeId", "");
		    					}
		    					if(relId!=null && !"".equals(relId)) {
		    						itemMap1.put("relId", relId);
		    					}else {
		    						itemMap1.put("relId", "");
		    					}
		    					List<Map<String, Object>> list=ciTypeRelService.getCiDataRelBySourceIdAndTargetIdAndCiIdsDown(itemMap1);
		    					for(Map<String, Object> map2:list) {
		    						relDataList.add(map2);
		    					}		    					
	    					}
	    					//??????dowm???????????????????????????????????? ?????????????????????targetTypeId---end
	    				}
	    				boolean flag=map.containsKey("isErgodic");
	    				if(!flag) {
	    					map.put("isErgodic", true);
	    				}	  
	    				map.put("relData", relDataList);
	    			}else {
	    				map.put("isErgodic", true);
	    				map.put("relData", relDataList);
	    				break;
	    			}
	    		}
	    	}
	    }
		return downSet;
	}
	@Override
	public Map<String, Object> queryPathByStartCiIdAndEndCiId(String startCiId,String endCiId,String typeId,String startCiCode,String endCiCode) {
		Integer downNum=999;
		List<String> startCiCodesList=Arrays.asList(startCiCode.split(","));
		List<String> endCiCodesList=Arrays.asList(endCiCode.split(","));
		List<String> typeIdsList=Arrays.asList(typeId.split(","));
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(String sCiCode:startCiCodesList) {
			for(String eCiCode:endCiCodesList) {
				//??????downNum???
				Map<String, Object> downMap = getCiSingleRelsByStartCiIdAndEndCiId("", "", downNum,sCiCode,eCiCode);
				List<Map<String, Object>> list=(List<Map<String, Object>>) downMap.get("rels");
				list=duplicateRemoval(list);
				list=filterData(sCiCode,eCiCode,list);
				List<Map<String, Object>> monolayerData=new ArrayList<Map<String,Object>>();
				for(Map<String, Object> map:list) {
					boolean flag=map.containsKey("check");
					if(flag) {
						boolean bool=(boolean) map.get("check");
						if(bool) {
							monolayerData.add(map);
						}
					}
				}
				//??????????????????---start
				List<String> ids=new ArrayList<String>();
				Set<String> ciTypeIdsSet=new HashSet<String>();
				for(int i=0;i<monolayerData.size();i++) {
					List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		    		if(i==0) {
		    			List<String> idList=new ArrayList<String>();
		    			idList.add(sCiCode);
		    			list1=queryDownClInfo(idList,monolayerData);
		    		}else {
		    			if(ids!=null && ids.size()>0) {
		    				list1=queryDownClInfo(ids,monolayerData);
		    			}	    			
		    		}
		    		ids.clear();
		    		if(list1!=null && list1.size()>0) {
		    			for(Map<String, Object> map:list1) {
		    				String targetCiBm=(String) map.get("targetCiBm");
		    				ids.add(targetCiBm);
		    				String targetTypeId=(String) map.get("targetTypeId");
		    				String sourceTypeId=(String) map.get("sourceTypeId");
		    				if(endCiCode.equals(targetCiBm)) {
		    					ciTypeIdsSet.add(sourceTypeId);
		    				}else {
		    					ciTypeIdsSet.add(targetTypeId);
		    				}
		    			}
		    		}
				}			
				if(typeId!=null && !"".equals(typeId)) {
					boolean flag=false;
					for(String str:typeIdsList) {
						boolean dge=ciTypeIdsSet.contains(str);
						if(dge) {
							flag=dge;
						}
					}
					if(flag) {
						for(Map<String, Object> map:list) {
							boolean dge=map.containsKey("check");
							if(dge) {
								boolean bool=(boolean) map.get("check");
								if(bool) {
									returnList.add(map);
								}
							}
						}
					}
				}else {
					for(Map<String, Object> map:monolayerData) {
						returnList.add(map);
					}
				}
				//??????????????????---end				
			}
		}
	    Set<String> allCiIdsSet=new HashSet<String>();
		if(returnList!=null && returnList.size()>0) {
			for(Map<String, Object> map:returnList){
				String sourceCiBm=(String) map.get("sourceCiBm");
				String targetCiBm=(String) map.get("targetCiBm");
				allCiIdsSet.add(sourceCiBm);
				allCiIdsSet.add(targetCiBm);
			}
		}
		List<String> list = new ArrayList<String>(allCiIdsSet);
		List<Map<String, Object>> listData=new ArrayList<Map<String,Object>>();
	    if(list!=null && list.size()>0) {
	    	listData=typeDao.findClassInfoAndCiInfoByCiIds(list);
	    }
		//?????????????????????ciIds?????????????????????ci?????????--end
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("ciRltLines", returnList);
		returnMap.put("ciNodes", listData);
		return returnMap;
	}
	
	public Map<String,Object> getBranchAll(String endId,List<String> endIds,List<Map<String, Object>> ciDataRelList,Map<String,Object> returnMap,String num,List<Map<String,Object>> returnList,Integer temp){
		for(String ciId:endIds) {
			List<Map<String, Object>> list=queryUpCiInfoToCiDataRel(ciId,ciDataRelList);
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
    			String sourceCiId=(String) map.get("sourceCiId");
    			List<String> sourceCiIds1=new ArrayList<String>();
    			sourceCiIds1.add(sourceCiId);
    			getBranchAll(endId,sourceCiIds1,ciDataRelList,returnMap,num,returnList,temp);
    		}else if(list!=null && list.size()>0){   			
    			String targetCiId=(String) list.get(0).get("targetCiId");
    			List<Map<String, Object>> list1=queryDownCiInfoToCiDataRel(targetCiId,ciDataRelList,new ArrayList<Map<String,Object>>());
    			if(endId.equals(targetCiId)) {
    				list1=new ArrayList<Map<String,Object>>();
    			}
    			for(Map<String, Object> map:list) {
    				//?????????targetCiId????????????????????????targetCiId??????endId,?????????????????????????????????returnList??????---start
    				Integer numInt=0;
    				for(Map<String, Object> map1:list) {
    					String targetCiIdStr=(String) map1.get("targetCiId");
    					if(endId.equals(targetCiIdStr)) {
    						numInt+=1;
    					}
    				}
    				if(numInt==list.size()) {
    					Integer size=returnList.size();
    					for(int i=0;i<size;i++) {
    						returnList.remove(i);
    					}
    				}
    				//?????????targetCiId????????????????????????targetCiId??????endId,?????????????????????????????????returnList??????---end
    				String sourceCiId=(String) map.get("sourceCiId");
    				List<String> sourceCiIds=new ArrayList<String>();
    				sourceCiIds.add(sourceCiId);
        			returnList.add(map);      			
        			getBranchAll(endId,sourceCiIds,ciDataRelList,returnMap,num,returnList,list1.size());
    			}
    		}
		}
		return returnMap;
	}
	
	public List<Map<String, Object>> queryDownCiInfoToCiDataRel(String id,List<Map<String, Object>> list,List<Map<String, Object>> returnList){
		for(Map<String, Object> map:list) {
			String sourceCiId=(String) map.get("sourceCiId");
			String targetCiId=(String) map.get("targetCiId");
			if(sourceCiId.equals(id)) {
				//?????????????????????????????????????????????---2020-07-15
				if(!sourceCiId.equals(targetCiId)) {
					returnList.add(map);
					queryDownCiInfoToCiDataRel(targetCiId,list,returnList);
				}			
			}			
		}
		return returnList;
	}
	
	public List<Map<String, Object>> queryDownClInfo(List<String> id,List<Map<String, Object>> list){
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			String sourceCiBm=(String) map.get("sourceCiBm");
			for(String strId:id) {
				if(sourceCiBm.equals(strId)) {
					returnList.add(map);
				}	
			}
					
		}
		return returnList;
	}
	
	public List<Map<String, Object>> filterData(String sCiCode,String eCiCode,List<Map<String, Object>> downList) {
		List<String> eCiCodeStrs=new ArrayList<String>();
		//String eCiIdStr="";
		Integer num=0;
		for(Map<String, Object> map:downList) {
			String sourceCiBm=(String) map.get("sourceCiBm");
			String targetCiBm=(String) map.get("targetCiBm");
			if(eCiCode.equals(targetCiBm)) {
				map.put("check", true);
				eCiCodeStrs.add(sourceCiBm);
			}
		}
		for(Map<String, Object> map:downList) {
			boolean flag=map.containsKey("check");
			if(!flag) {
				num+=1;
			}
		}
		if(num==downList.size()) {
			return downList;
		}
		if(eCiCodeStrs!=null && eCiCodeStrs.size()>0) {
			for(String eCiCodeStr:eCiCodeStrs) {
				filterData(sCiCode,eCiCodeStr,downList);
			}
		}
		return downList;
	}
	
	private static List<Map<String, Object>> duplicateRemovalData(List<Map<String, Object>> list) {
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
	
	private static List<Map<String, Object>> duplicateRemoval(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("sourceCiBm").equals(list.get(j).get("sourceCiBm")) && map1.get("targetCiBm").equals(list.get(j).get("targetCiBm")) && map1.get("relId").equals(list.get(j).get("relId"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }

	public Map<String, Object> getCiSingleRelsByStartCiIdAndEndCiId(String startCiId,String endCiId,Integer downNum,String startCiCode,String endCiCode) {
		//??????downNum???
		CiRelUtil downRelUtil = new CiRelUtil("sourceCiBm","targetCiBm",downNum) {
			@Override
			public Object getDate(List<String> ciCodes) {
				List<Map<String, Object>> returnList;
				Map<String, Object> itemMap=new HashMap<String, Object>();
				String [] ciCodesArray = ciCodes.toArray(new String[ciCodes.size()]);
				List<String> list=new ArrayList<String>();
				for(int i = 0; i < ciCodesArray.length; i++){
					String ciCodeStr=ciCodesArray[i];
					list=Arrays.asList(ciCodeStr.split(","));
				}
				itemMap.put("sourceCiBmList", ciCodesArray);
			    returnList=typeDataDao.getCiDataRelDownBySourceIdAndTargetId(itemMap);
		        return returnList;
			}

			@Override
			public Object getDateByImpactAnalysisAndRootCauseAnalysis(List<String> ids, String relIds, String ciTypeId,
					List<String> ciCodes) {
				return null;
			}

			
		};
		return downRelUtil.getData(startCiCode,endCiCode);
	}
	
	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: ?????????????????????????????????????????????????????????CI??????????????????
	 */
	public Map<String, Object> getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(String ciIds,String relIds,String ciTypeId,Integer downNum,String ciCode) {
		//??????downNum???
		CiRelUtil downRelUtil = new CiRelUtil("sourceCiBm","targetCiBm",downNum) {
			@Override
			public Object getDateByImpactAnalysisAndRootCauseAnalysis(List<String> ids, String relIds, String ciTypeId, List<String> ciCodes) {
				List<Map<String, Object>> returnList;
				List<String> relIdsList = Arrays.asList(relIds.split(","));
				List<String> ciTypeIdsList = Arrays.asList(ciTypeId.split(","));
				Map<String, Object> itemMap=new HashMap<String, Object>();
				String [] ciCodesArray = ciCodes.toArray(new String[ciCodes.size()]);
				String [] relIdsArray = relIdsList.toArray(new String[relIdsList.size()]);
				String [] ciTypeIdsArray = ciTypeIdsList.toArray(new String[ciTypeIdsList.size()]);
				itemMap.put("relList", relIdsArray);
				itemMap.put("sourceTypeIdList", ciTypeIdsArray);
				itemMap.put("targetTypeIdList", ciTypeIdsArray);
				List<String> list=new ArrayList<String>();
				for(int i = 0; i < ciCodesArray.length; i++){
					String ciCodeStr=ciCodesArray[i];
					list=Arrays.asList(ciCodeStr.split(","));
				}
				String [] listArray = list.toArray(new String[list.size()]);
				itemMap.put("sourceCiBmList", listArray);
				itemMap.put("targetCiBmList", listArray);
				returnList=typeDataDao.getCiDataRelByImpactAnalysisAndRootCauseAnalysisSource(itemMap);
		        return returnList;
			}

			@Override
			public Object getDate(List<String> ids) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		return downRelUtil.getDataByImpactAnalysisAndRootCauseAnalysis(ciIds,relIds,ciTypeId,ciCode);
	}
	
	@Override
	public Map<String, Object> queryRootCauseAnalysisByCiId(String ciId,String ciTypeId,Integer upNum,Integer downNum,String ciCode) {
		List<Map<String, Object>> upSet=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> downSet=new ArrayList<Map<String,Object>>();
		Set<String> ciTypeIds=new HashSet<String>();
		//??????upNum???
		if (upNum!=null&&upNum>0) {
			Map<String, Object> upMap = getCiTypeSingleRels(ciTypeId, upNum, "up");
			List<Map<String, Object>> list=(List<Map<String, Object>>) upMap.get("rels");
			for(Map<String, Object> map:list) {
				String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				ciTypeIds.add(sourceTypeId);
				ciTypeIds.add(targetTypeId);
			}
			upSet=filterListBySourceIdAndTargetId(list);
			//??????????????????????????????????????????????????????---start
			List<Map<String, Object>> upSelfList=new ArrayList<Map<String,Object>>();
			for(Map<String, Object> map:upSet) {
				String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				if(sourceTypeId.equals(ciTypeId)) {
					continue;
				}else {
					upSelfList.add(map);
				}
			}
			upSet=upSelfList;
			//??????????????????????????????????????????????????????---end
		}
		
		//??????downNum???
	    if (downNum!=null&&downNum>0) {
	    	Map<String, Object> downMap = getCiTypeSingleRels(ciTypeId, downNum, "down");
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) downMap.get("rels");
	    	for(Map<String, Object> map:list) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				ciTypeIds.add(sourceTypeId);
				ciTypeIds.add(targetTypeId);
			}
	    	downSet=filterListBySourceIdAndTargetId(list);
	    	//??????????????????????????????????????????????????????---start
	    	List<Map<String, Object>> downSelfList=new ArrayList<Map<String,Object>>();
			for(Map<String, Object> map:downSet) {
				String sourceTypeId=(String) map.get("sourceTypeId");
				String targetTypeId=(String) map.get("targetTypeId");
				if(targetTypeId.equals(ciTypeId)) {
					continue;
				}else {
					downSelfList.add(map);
				}
			}
			downSet=downSelfList;
			//??????????????????????????????????????????????????????---end
	    }
	    
	  //??????????????????????????????--start
	    List<Map<String, Object>> selfList=new ArrayList<Map<String,Object>>();
	    for(String typeId:ciTypeIds) {
	    	List<Map<String, Object>> list=ciTypeRelService.getCiDataRelBySelf(typeId);
	    	for(Map<String, Object> map:list) {
	    		selfList.add(map);
	    	}
	    }
	  //?????????????????????????????????????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> list4=getDownDataByRoot(downSet,ciTypeId,ciId,ciCode);
	    for(Map<String, Object> map:list4) {
	    	boolean flag=(boolean) map.get("isErgodic");
	    	if(flag) {
	    		continue;
	    	}else {
	    		list4=getDownDataByRoot(list4,ciTypeId,ciId,ciCode);
	    	}
	    }
	    downSet=list4;
	  //?????????????????????????????????????????????????????????????????????????????????????????????---end
	    for(Map<String, Object> map:downSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    	if(relDataList!=null && relDataList.size()>0) {
	    		List<Map<String, Object>> ownList=ciTypeRelService.getCiTypeRelByTypeId(targetTypeId);
	    		for(Map<String, Object> map1:ownList) {
	    			String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		String targetTypeIdStr=(String) map1.get("targetTypeId");
		    		String relIdStr=(String) map1.get("relId");
		    		boolean checked=(boolean) map1.get("checked");
		    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
		    			if(ciTypeId.equals(sourceTypeIdStr)) {
		    				if(!checked) {
		    					List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			    				for(Map<String, Object> map2:relDataList) {
			    					String sourceCiId=(String) map2.get("sourceCiId");
			    					String sourceCiBm=(String) map2.get("sourceCiBm");
			    					if(ciCode.equals(sourceCiBm)) {
			    						list.add(map2);
			    					}
			    				}
			    				relDataList=list;
			    				map.put("relData", relDataList);
		    				}
		    			}else {
		    				if(!checked) {
		    					List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		    					Set<String> targetCiIds=new HashSet<String>();
		    					Set<String> targetCiCodes=new HashSet<String>();
		    					for(Map<String, Object> map2:downSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(targetTypeId2.equals(sourceTypeId)) {
		    				    		for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("targetCiId");
		    				    			String targetCiBm=(String) map4.get("targetCiBm");
		    				    			targetCiCodes.add(targetCiBm);
		    				    		}
		    				    	}
		    					}
		    					for(String ciCodeStr:targetCiCodes) {
		    						for(Map<String, Object> map2:relDataList) {
				    					String sourceCiId=(String) map2.get("sourceCiId");
				    					String sourceCiBm=(String) map2.get("sourceCiBm");
				    					if(ciCodeStr.equals(sourceCiBm)) {
				    						list1.add(map2);
				    					}
				    				}
		    					}
		    					relDataList=list1;
		    					map.put("relData", relDataList);
		    				}
		    			}
		    		}
	    		}
	    		
	    	}	    	
	    }
	    
	  //?????????????????????????????????????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> list3=getUpDataByRoot(upSet,ciTypeId,ciId,ciCode);
	    for(Map<String, Object> map:list3) {
	    	boolean flag=(boolean) map.get("isErgodic");
	    	if(flag) {
	    		continue;
	    	}else {
	    		list3=getUpDataByRoot(list3,ciTypeId,ciId,ciCode);
	    	}
	    }
	    upSet=list3;
	  //?????????????????????????????????????????????????????????????????????????????????????????????---end
	    
	    for(Map<String, Object> map:upSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	String relId=(String) map.get("relId");
	    	List<Map<String, Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    	if(relDataList!=null && relDataList.size()>0) {
	    		List<Map<String, Object>> ownList=ciTypeRelService.getCiTypeRelByTypeId(sourceTypeId);
	    		for(Map<String, Object> map1:ownList) {
	    			String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		String targetTypeIdStr=(String) map1.get("targetTypeId");
		    		String relIdStr=(String) map1.get("relId");
		    		boolean checked=(boolean) map1.get("checked");
		    		if(sourceTypeId.equals(sourceTypeIdStr) && targetTypeId.equals(targetTypeIdStr) && relId.equals(relIdStr)) {
		    			if(ciTypeId.equals(targetTypeIdStr)) {
		    				if(!checked) {
		    					List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			    				for(Map<String, Object> map2:relDataList) {
			    					String targetCiId=(String) map2.get("targetCiId");
			    					String targetCiBm=(String) map2.get("targetCiBm");
			    					if(ciCode.equals(targetCiBm)) {
			    						list.add(map2);
			    					}
			    				}
			    				relDataList=list;
			    				map.put("relData", relDataList);
		    				}
		    			}else {
		    				if(!checked) {
		    					List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		    					Set<String> sourceCiIds=new HashSet<String>();
		    					Set<String> sourceCiCodes=new HashSet<String>();
		    					for(Map<String, Object> map2:upSet) {
		    						String sourceTypeId2=(String) map2.get("sourceTypeId");
		    				    	String targetTypeId2=(String) map2.get("targetTypeId");
		    				    	String relId2=(String) map2.get("relId");
		    				    	List<Map<String, Object>> list=(List<Map<String, Object>>) map2.get("relData");
		    				    	if(sourceTypeId2.equals(targetTypeId)) {
		    				    		for(Map<String, Object> map4:list) {
		    				    			String id=(String) map4.get("sourceCiId");
		    				    			String sourceCiBm=(String) map4.get("sourceCiBm");
		    				    			sourceCiCodes.add(sourceCiBm);
		    				    		}
		    				    	}
		    					}
		    					for(String ciCodeStr:sourceCiCodes) {
		    						for(Map<String, Object> map2:relDataList) {
				    					String targetCiId=(String) map2.get("targetCiId");
				    					String targetCiBm=(String) map2.get("targetCiBm");
				    					if(ciCodeStr.equals(targetCiBm)) {
				    						list1.add(map2);
				    					}
				    				}
		    					}
		    					relDataList=list1;
		    					map.put("relData", relDataList);
		    				}
		    			}
		    		}
	    		}
	    	}
	    }
	  //???????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> downList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:downSet) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	if(ciTypeId.equals(sourceTypeId)) {
	    		downList.add(map);
	    	}
	    }
	    Integer downNumber=0;
	    for(Map<String, Object> map:downList) {
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	if(list==null || list.size()==0) {
	    		downNumber=downNumber+1;
	    	}
	    }
	  //???????????????????????????????????????????????????????????????---end
	    
	  //???????????????????????????????????????????????????????????????---start
	    List<Map<String, Object>> upList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:upSet) {
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	if(ciTypeId.equals(targetTypeId)) {
	    		upList.add(map);
	    	}
	    }
	    Integer upNumber=0;
	    for(Map<String, Object> map:upList) {
		    List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
		    String sourceTypeId=(String) map.get("sourceTypeId");
		    if(list==null || list.size()==0) {
		    	upNumber=upNumber+1;
		    }
		}
        
	  //???????????????????????????CI????????????---start
	    if(upSet==null || upSet.size()==0) {
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    		//?????????????????????????????????????????????????????????????????????????????????????????????
	    		Set<String> typeIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			if(!ciTypeId.equals(sourceTypeId)) {
	    				typeIds.add(sourceTypeId);
	    			}	    			
	    		}
	    		//??????CI???????????????????????????????????????????????????CIID	    		
	    		for(String typeIdStr:typeIds) {
	    			Set<String> setCiIds=new HashSet<String>();
	    			Set<String> setCiCodes=new HashSet<String>();
	    			Set<String> setRelIds=new HashSet<String>();
	    			for(Map<String,Object> map: downSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCiCodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCiCodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			//?????????????????????????????????????????????????????????????????????
	    			for(Map<String, Object> map:selfList) {
		    			String sourceTypeId=(String) map.get("sourceTypeId");
		    			String relId=(String) map.get("relId");
		    			//???????????????????????????????????????????????????
		    			if(typeIdStr.equals(sourceTypeId)) {
		    				//?????????????????????????????????????????????????????????
		    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
		    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
		    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
		    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
		    	    			String rltId=(String) mapFocus.get("rltId");
		    	    			setRelIds.add(rltId);
		    	    		}	    	    		
		    			}
		    		}
	    			if(setRelIds!=null && setRelIds.size()>0 && setCiCodes!=null && setCiCodes.size()>0) {
	    				String ciIdsStr = String.join(",", setCiIds);
	    				String ciCodesStr = String.join(",", setCiCodes);
		    			String relIdsStr = String.join(",", setRelIds);
		    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciIdsStr,relIdsStr,typeIdStr,999,ciCodesStr);
		    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
		    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
		    			if(listData!=null && listData.size()>0) {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", listData);	
		    	    			}
		    	    		}
		    			}else {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
		    	    			}
		    	    		}
		    			}
		    		}else {
		    			for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(typeIdStr.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
		    		}
	    		}
	    		
	    	}
	    	//??????????????????????????????CI????????????---end
	    }
	    if(downSet==null || downSet.size()==0) {
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    		//?????????????????????????????????????????????????????????????????????????????????????????????
	    		Set<String> typeIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			if(!ciTypeId.equals(sourceTypeId)) {
	    				typeIds.add(sourceTypeId);
	    			}	    			
	    		}
	    		//??????CI???????????????????????????????????????????????????CIID	    		
	    		for(String typeIdStr:typeIds) {
	    			Set<String> setCiIds=new HashSet<String>();
	    			Set<String> setCiCodes=new HashSet<String>();
	    			Set<String> setRelIds=new HashSet<String>();
	    			for(Map<String,Object> map: upSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCiCodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCiCodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			//?????????????????????????????????????????????????????????????????????
	    			for(Map<String, Object> map:selfList) {
		    			String sourceTypeId=(String) map.get("sourceTypeId");
		    			String relId=(String) map.get("relId");
		    			//???????????????????????????????????????????????????
		    			if(typeIdStr.equals(sourceTypeId)) {
		    				//?????????????????????????????????????????????????????????
		    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
		    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
		    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
		    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
		    	    			String rltId=(String) mapFocus.get("rltId");
		    	    			setRelIds.add(rltId);
		    	    		}	    	    		
		    			}
		    		}
	    			if(setRelIds!=null && setRelIds.size()>0 && setCiCodes!=null && setCiCodes.size()>0) {
	    				String ciIdsStr = String.join(",", setCiIds);
	    				String ciCodesStr = String.join(",", setCiCodes);
		    			String relIdsStr = String.join(",", setRelIds);
		    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciIdsStr,relIdsStr,typeIdStr,999,ciCodesStr);
		    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
		    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
		    			if(listData!=null && listData.size()>0) {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", listData);	
		    	    			}
		    	    		}
		    			}else {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
		    	    			}
		    	    		}
		    			}
		    		}else {
		    			for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(typeIdStr.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
		    		}
	    		}
	    		
	    	}
	    	//??????????????????????????????CI????????????---end
	    }
	    if(upSet.size()>0 && downSet.size()>0) {
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    		//?????????????????????????????????????????????????????????????????????????????????????????????
	    		Set<String> typeIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			if(!ciTypeId.equals(sourceTypeId)) {
	    				typeIds.add(sourceTypeId);
	    			}	    			
	    		}
	    		//??????CI???????????????????????????????????????????????????CIID	    		
	    		for(String typeIdStr:typeIds) {
	    			Set<String> setCiIds=new HashSet<String>();
	    			Set<String> setCicodes=new HashSet<String>();
	    			Set<String> setRelIds=new HashSet<String>();
	    			for(Map<String,Object> map: upSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCicodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCicodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			for(Map<String,Object> map: downSet) {
	    				String sourceTypeId=(String) map.get("sourceTypeId");
	    				String targetTypeId=(String) map.get("targetTypeId");
	    				if(typeIdStr.equals(sourceTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String sourceCiId=(String) map1.get("sourceCiId");
	    						String sourceCiBm=(String) map1.get("sourceCiBm");
	    						setCicodes.add(sourceCiBm);
	    					}
	    				}else if(typeIdStr.equals(targetTypeId)) {
	    					List<Map<String,Object>> relDataList=(List<Map<String, Object>>) map.get("relData");
	    					for(Map<String,Object> map1:relDataList) {
	    						String targetCiId=(String) map1.get("targetCiId");
	    						String targetCiBm=(String) map1.get("targetCiBm");
	    						setCicodes.add(targetCiBm);
	    					}
	    				}
	    			}
	    			//?????????????????????????????????????????????????????????????????????
	    			for(Map<String, Object> map:selfList) {
		    			String sourceTypeId=(String) map.get("sourceTypeId");
		    			String relId=(String) map.get("relId");
		    			//???????????????????????????????????????????????????
		    			if(typeIdStr.equals(sourceTypeId)) {
		    				//?????????????????????????????????????????????????????????
		    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
		    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
		    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
		    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
		    	    			String rltId=(String) mapFocus.get("rltId");
		    	    			setRelIds.add(rltId);
		    	    		}	    	    		
		    			}
		    		}
	    			if(setRelIds!=null && setRelIds.size()>0 && setCicodes!=null && setCicodes.size()>0) {
	    				String ciIdsStr = String.join(",", setCiIds);
	    				String ciCodesStr = String.join(",", setCicodes);
		    			String relIdsStr = String.join(",", setRelIds);
		    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciIdsStr,relIdsStr,typeIdStr,999,ciCodesStr);
		    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
		    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
		    			if(listData!=null && listData.size()>0) {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", listData);	
		    	    			}
		    	    		}
		    			}else {
		    				for(Map<String, Object> map1:selfList) {
		    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
		    	    			//???CI????????????
		    	    			if(typeIdStr.equals(sourceTypeId)) {
		    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
		    	    			}
		    	    		}
		    			}
		    		}else {
		    			for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(typeIdStr.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
		    		}
	    		}
	    	}
	    	//??????????????????????????????CI????????????---end
	    }
	    if(upSet.size()==0 && downSet.size()==0) {
	    	//??????upSet???downSet????????????????????????????????????????????????
	    	//??????????????????????????????CI????????????---start
	    	if(selfList!=null && selfList.size()>0) {
	    		Set<String> relIds=new HashSet<String>();
	    		for(Map<String, Object> map:selfList) {
	    			String sourceTypeId=(String) map.get("sourceTypeId");
	    			String relId=(String) map.get("relId");
	    			//???????????????????????????????????????????????????
	    			if(ciTypeId.equals(sourceTypeId)) {
	    				//?????????????????????????????????????????????????????????
	    	    		List<Map<String, Object>> mapFocusList=ciTypeRelService.getCiTypeFocusRelBySelf(sourceTypeId,relId);
	    	    		if(mapFocusList!=null && mapFocusList.size()>0) {
	    	    			mapFocusList=filterListBySourceIdAndTargetIdAndRltId(mapFocusList);
	    	    			Map<String,Object> mapFocus=mapFocusList.get(0);
	    	    			String rltId=(String) mapFocus.get("rltId");
	    	    			relIds.add(rltId);
	    	    		}	    	    		
	    			}
	    		}
	    		if(relIds!=null && relIds.size()>0) {
	    			String relIdsStr = String.join(",", relIds);
	    			Map<String, Object> map=getCiSingleRelsByImpactAnalysisAndRootCauseAnalysis(ciId,relIdsStr,ciTypeId,999,ciCode);
	    			List<Map<String,Object>> listData=(List<Map<String, Object>>) map.get("rels");
	    			listData=filterListBySourceCiBmAndTargetCiBmAndRelName(listData);
	    			if(listData!=null && listData.size()>0) {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", listData);	
	    	    			}
	    	    		}
	    			}else {
	    				for(Map<String, Object> map1:selfList) {
	    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
	    	    			//???CI????????????
	    	    			if(ciTypeId.equals(sourceTypeId)) {
	    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
	    	    			}
	    	    		}
	    			}
	    		}else {
	    			for(Map<String, Object> map1:selfList) {
    	    			String sourceTypeId=(String) map1.get("sourceTypeId");
    	    			//???CI????????????
    	    			if(ciTypeId.equals(sourceTypeId)) {
    	    				map1.put("relData", new ArrayList<Map<String, Object>>());	
    	    			}
    	    		}
	    		}
	    	}
	    }
	  //?????????????????????????????????????????????
	    List<Map<String, Object>> selfNodes=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:selfList){
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    	Set<String> setCiIds=new HashSet<String>();
	    	Set<String> setCiCodes=new HashSet<String>();
	    	String sourceTypeName=(String) map.get("sourceTypeName");
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
	    	Map<String, Object> map3=new HashMap<String, Object>();
	    	if(list!=null && list.size()>0) {
	    		for(Map<String, Object> map1:list) {
	    			String sourceCiId=(String) map1.get("sourceCiId");
	    			String sourceCiBm=(String) map1.get("sourceCiBm");
	    			String targetCiId=(String) map1.get("targetCiId");
	    			String targetCiBm=(String) map1.get("targetCiBm");
	    			setCiIds.add(sourceCiId);
	    			setCiIds.add(targetCiId);
	    			setCiCodes.add(sourceCiBm);
	    			setCiCodes.add(targetCiBm);
	    		}
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		map2.put("ciClassId", sourceTypeId);
	    		map2.put("ciCodes", setCiCodes);
	    		map2.put("ciCount", setCiCodes.size());
	    		map2.put("className", sourceTypeName);
	    		dataList.add(map2);
	    		dataList.add(map2);	    		
	    		map3.put("nodes", dataList);
	    		selfNodes.add(map3);
	    	}	    	
	    }
	    //???????????????????????????CI????????????---end
	    
	    List<Map<String, Object>> allNodesList=new ArrayList<Map<String,Object>>();    
	  //??????????????????????????????---start
	    if(upSet==null || upSet.size()==0) {
	    	List<String> ciTypeIdsList=new ArrayList<String>();
	    	ciTypeIdsList.add(ciTypeId);
	    	List<Map<String, Object>> list=queryAllNodesDown(ciTypeIdsList,downSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());	    	
	    	for(Map<String, Object> map:list) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		//??????????????????????????????????????????
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}	    		
	    	}
	    	//???????????????????????????????????????---start
	    	List<Map<String, Object>> selfNodesRen=new ArrayList<Map<String, Object>>();
	    	for(Map<String, Object> map:selfList) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		List<Map<String, Object>> relDatasSelf=(List<Map<String, Object>>) map.get("relData");
	    		for(Map<String, Object> map1:downSet) {
		    		String targetTypeId=(String) map1.get("targetTypeId");
		    		if(sourceTypeId.equals(targetTypeId)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String sourceCiId=(String) map4.get("sourceCiId");
			    				String sourceCiBm=(String) map4.get("sourceCiBm");
			    				boolean flag=ciCodes.contains(sourceCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list1=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list1!=null && list1.size()>0) {
		    						Map<String,Object> map6=list1.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    			}
		    			
		    		}
		    	}
	    	}	
	    	selfNodes=selfNodesRen;
	    	//???????????????????????????????????????---end
	    }	    
	    if(downSet==null || downSet.size()==0) {
	    	List<String> ciTypeIdsList=new ArrayList<String>();
	    	ciTypeIdsList.add(ciTypeId);
	    	List<Map<String, Object>> list=queryAllNodesUp(ciTypeIdsList,upSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());
	    	for(Map<String, Object> map:list) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    	}
	    	//???????????????????????????????????????---start
	    	List<Map<String, Object>> selfNodesRen=new ArrayList<Map<String, Object>>();
	    	for(Map<String, Object> map:selfList) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		List<Map<String, Object>> relDatasSelf=(List<Map<String, Object>>) map.get("relData");
	    		for(Map<String, Object> map1:upSet) {
		    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		if(sourceTypeId.equals(sourceTypeIdStr)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String targetCiId=(String) map4.get("targetCiId");
			    				String targetCiBm=(String) map4.get("targetCiBm");
			    				boolean flag=ciCodes.contains(targetCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list1=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list1!=null && list1.size()>0) {
		    						Map<String,Object> map6=list1.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    			}
		    			
		    		}
		    	}
	    	}	
	    	selfNodes=selfNodesRen;
	    	//???????????????????????????????????????---end
	    }
	    if(upSet.size()>0 && downSet.size()>0) {
	    	List<String> ciTypeIdsList=new ArrayList<String>();
	    	ciTypeIdsList.add(ciTypeId);
	    	List<Map<String, Object>> listUp=queryAllNodesUp(ciTypeIdsList,upSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());
	    	for(Map<String, Object> map:listUp) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    	}	
	    	List<Map<String, Object>> listDown=queryAllNodesDown(ciTypeIdsList,downSet,new ArrayList<Map<String,Object>>(),ciTypeId,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>());
	    	for(Map<String, Object> map:listDown) {
	    		List<Map<String, Object>> nodesList=(List<Map<String, Object>>) map.get("nodes");
	    		if(nodesList.size()>1) {
	    			allNodesList.add(map);
	    		}
	    	}
	    	//???????????????????????????????????????---start
	    	List<Map<String, Object>> selfNodesRen=new ArrayList<Map<String, Object>>();
	    	for(Map<String, Object> map:selfList) {
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		List<Map<String, Object>> relDatasSelf=(List<Map<String, Object>>) map.get("relData");
	    		for(Map<String, Object> map1:downSet) {
		    		String targetTypeId=(String) map1.get("targetTypeId");
		    		if(sourceTypeId.equals(targetTypeId)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String sourceCiId=(String) map4.get("sourceCiId");
			    				String sourceCiBm=(String) map4.get("sourceCiBm");
			    				boolean flag=ciCodes.contains(sourceCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());		    				
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list!=null && list.size()>0) {
		    						Map<String,Object> map6=list.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    				
		    			}
		    			
		    		}
		    	}
	    		for(Map<String, Object> map1:upSet) {
		    		String sourceTypeIdStr=(String) map1.get("sourceTypeId");
		    		if(sourceTypeId.equals(sourceTypeIdStr)) {
		    			List<Map<String, Object>> relDatas=(List<Map<String, Object>>) map1.get("relData");
		    			Set<String> ciIds=new HashSet<String>();
		    			Set<String> ciCodes=new HashSet<String>();
		    			for(Map<String, Object> map2:relDatas) {
		    				String targetCiId=(String) map2.get("targetCiId");
		    				String targetCiBm=(String) map2.get("targetCiBm");
		    				ciCodes.add(targetCiBm);
		    			}
		    			if(ciCodes!=null && ciCodes.size()>0) {
		    				List<Map<String, Object>> returnList=new ArrayList<Map<String, Object>>();
			    			for(Map<String, Object> map4:relDatasSelf) {
			    				String targetCiId=(String) map4.get("targetCiId");
			    				String targetCiBm=(String) map4.get("targetCiBm");
			    				boolean flag=ciCodes.contains(targetCiBm);
			    				if(flag) {
			    					returnList.add(map4);
			    				}
			    			}
			    			map.put("relData", returnList);
		    			}else {
		    				map.put("relData", new ArrayList<Map<String, Object>>());
		    				for(Map<String, Object> map5:selfNodes) {
		    					List<Map<String, Object>> list=(List<Map<String, Object>>) map5.get("nodes");
		    					if(list!=null && list.size()>0) {
		    						Map<String,Object> map6=list.get(0);
		    						String ciClassId=(String) map6.get("ciClassId");
		    						if(!sourceTypeId.equals(ciClassId)) {
		    							selfNodesRen.add(map5);
		    						}
		    					}
		    				}
		    			}
		    			
		    		}
		    	}
	    	}
	    	selfNodes=selfNodesRen;
	    	//???????????????????????????????????????---end
	    }
	    //??????????????????????????????---end
	    
	    //?????????????????????ciIds?????????????????????ci?????????
	    Set<String> allCiIdsSet=new HashSet<String>();
	    Set<String> allCiCodesSet=new HashSet<String>();
	  //??????????????????????????????ciTypeId?????????????????????
	    Set<String> allCiTypeIdsSet=new HashSet<String>();
	    //????????????ci????????????---start
	    List<Map<String, Object>> allCiReList=new ArrayList<Map<String,Object>>();
	    Set<Map<String, Object>> setRelInfo=new HashSet<Map<String,Object>>(); 
	    if(upSet!=null && upSet.size()>0) {
	    	for(Map<String, Object> map:upSet) {
	    		List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		String targetTypeId=(String) map.get("targetTypeId");
	    		allCiTypeIdsSet.add(sourceTypeId);
	    		allCiTypeIdsSet.add(targetTypeId);
	    		String relId=(String) map.get("relId");
	    		String relName=(String) map.get("relName");
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);    		
	    		for(Map<String, Object> map1:list) {
	    			String sourceCiId=(String) map1.get("sourceCiId");
	    			String sourceCiBm=(String) map1.get("sourceCiBm");
	    			String targetCiId=(String) map1.get("targetCiId");
	    			String targetCiBm=(String) map1.get("targetCiBm");
	    			allCiIdsSet.add(sourceCiId);
	    			allCiIdsSet.add(targetCiId);
	    			allCiCodesSet.add(sourceCiBm);
	    			allCiCodesSet.add(targetCiBm);
	    			allCiReList.add(map1);
	    		}
	    	}
	    }
	    if(downSet!=null && downSet.size()>0) {
	    	for(Map<String, Object> map:downSet) {
	    		List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		String sourceTypeId=(String) map.get("sourceTypeId");
	    		String targetTypeId=(String) map.get("targetTypeId");
	    		allCiTypeIdsSet.add(sourceTypeId);
	    		allCiTypeIdsSet.add(targetTypeId);
	    		String relId=(String) map.get("relId");
	    		String relName=(String) map.get("relName");
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);	    		
	    		for(Map<String, Object> map1:list) {
	    			String sourceCiId=(String) map1.get("sourceCiId");
	    			String sourceCiBm=(String) map1.get("sourceCiBm");
	    			String targetCiId=(String) map1.get("targetCiId");
	    			String targetCiBm=(String) map1.get("targetCiBm");
	    			allCiIdsSet.add(sourceCiId);
	    			allCiIdsSet.add(targetCiId);
	    			allCiCodesSet.add(sourceCiBm);
	    			allCiCodesSet.add(targetCiBm);
	    			allCiReList.add(map1);
	    		}
	    	}
	    }
	    //?????????????????????????????????????????????
	    for(Map<String, Object> map:selfList){
	    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("relData");
	    	for(Map<String, Object> map1:list) {
	    		allCiReList.add(map1);
	    		String sourceCiId=(String) map1.get("sourceCiId");
	    		String sourceCiBm=(String) map1.get("sourceCiBm");
	    		String targetCiId=(String) map1.get("targetCiId");
	    		String targetCiBm=(String) map1.get("targetCiBm");
	    		allCiIdsSet.add(sourceCiId);
	    		allCiIdsSet.add(targetCiId);
	    		allCiCodesSet.add(sourceCiBm);
	    		allCiCodesSet.add(targetCiBm);
	    		String sourceTypeId=(String) map1.get("sourceTypeId");
	    		String targetTypeId=(String) map1.get("targetTypeId");
	    		allCiTypeIdsSet.add(sourceTypeId);
	    		allCiTypeIdsSet.add(targetTypeId);
	    		String relId=(String) map1.get("relId");
	    		String relName=(String) map1.get("relName");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);
	    	}
	    }
	    //????????????ci????????????---end
	    List<String> list = new ArrayList<String>(allCiIdsSet);
	    List<String> listCiCode = new ArrayList<String>(allCiCodesSet);
	    List<Map<String, Object>> listData=new ArrayList<Map<String,Object>>();
	    if(list!=null && list.size()>0) {
	    	listData=typeDao.findClassInfoAndCiInfoByCiIds(listCiCode);
	    }
	  //????????????????????????????????????????????????---start
	    List<String> allCiTypeIdsList = new ArrayList<String>(allCiTypeIdsSet);
	    List<Map<String, Object>> allClassInfoList=new ArrayList<Map<String,Object>>();
	    if(allCiTypeIdsList!=null && allCiTypeIdsList.size()>0){
	    	allClassInfoList=typeDao.findClassInfoByCiTypeIds(allCiTypeIdsList);
	    }
	    List<Map<String, Object>> classInfoAndRelInfoList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:allClassInfoList) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    for(Map<String, Object> map:setRelInfo) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    //????????????????????????????????????????????????---end
	  //???????????????????????????????????????---start
	    Set<String> setTypeIds=new HashSet<String>();
	    for(Map<String, Object> map:allCiReList) {
	    	String sourceTypeId=(String) map.get("sourceTypeId");
	    	String targetTypeId=(String) map.get("targetTypeId");
	    	setTypeIds.add(sourceTypeId);
	    	setTypeIds.add(targetTypeId);
	    }
	    List<Map<String, Object>> selfNodesNew=new ArrayList<Map<String, Object>>();
	    for(Map<String, Object> map5:selfNodes) {
			List<Map<String, Object>> list5=(List<Map<String, Object>>) map5.get("nodes");
			if(list!=null && list.size()>0) {
				Map<String,Object> map6=list5.get(0);
				String ciClassId=(String) map6.get("ciClassId");
				Integer ciCount6=(Integer) map6.get("ciCount");
				boolean flag=setTypeIds.contains(ciClassId);
				if(flag) {
					if(selfNodesNew!=null && selfNodesNew.size()>0) {
						for(Map<String, Object> map:selfNodesNew) {
							List<Map<String, Object>> list6=(List<Map<String, Object>>) map5.get("nodes");
							if(list!=null && list.size()>0) {
								Map<String,Object> map7=list5.get(0);
								String ciClassId7=(String) map7.get("ciClassId");
								Integer ciCount7=(Integer) map7.get("ciCount");
								if(!ciClassId7.equals(ciClassId) && !ciCount6.equals(ciCount7)) {
									selfNodesNew.add(map5);
								}
							}
						}
					}else {
						selfNodesNew.add(map5);
					}					
				}
			}
		}
	    for(Map<String, Object> map:selfNodesNew){
	    	allNodesList.add(map);
	    }
	    //???????????????????????????????????????---end
	    Map<String, Object> returnMap=new HashMap<String, Object>();
	    returnMap.put("friendPaths", allNodesList);
	    returnMap.put("ciRltLines", allCiReList);
	    returnMap.put("ciNodes", listData);
	    if(listData!=null && listData.size()>0) {
	    	for(Map<String, Object> map:listData) {
		    	String ciIdStr=(String) map.get("ciId");
		    	String ciBmStr=(String) map.get("ciBm");
		    	if(ciCode.equals(ciBmStr)) {
		    		returnMap.put("startCi", map);
		    	}
		    }
	    }else {
	    	returnMap.put("startCi", new HashedMap());
	    }
	    returnMap.put("ciClassInfos", classInfoAndRelInfoList);
		return returnMap;
	}
	
	private static List<Map<String, Object>> filterListByCiClassId(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("ciClassId").equals(list.get(j).get("ciClassId"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
	private static List<Map<String, Object>> filterListBySourceIdAndTargetId(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("IUAGASJBKNKJS").equals(list.get(j).get("IUAGASJBKNKJS"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
	private static List<Map<String, Object>> filterListBySourceIdAndTargetIdAndRelId(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("sourceTypeId").equals(list.get(j).get("sourceTypeId")) && map1.get("targetTypeId").equals(list.get(j).get("targetTypeId")) && map1.get("relId").equals(list.get(j).get("relId"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
	private static List<Map<String, Object>> filterListBySourceIdAndTargetIdAndRltId(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("sourceTypeId").equals(list.get(j).get("sourceTypeId")) && map1.get("targetTypeId").equals(list.get(j).get("targetTypeId")) && map1.get("rltId").equals(list.get(j).get("rltId"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
	
	private static List<Map<String, Object>> filterListBySourceCiBmAndTargetCiBmAndRelName(List<Map<String, Object>> list) {
        if(null==list || list.size()<=0){
            return list;
        }else{
            for(int i=0; i<list.size(); i++){
                Map<String, Object> map1 = list.get(i);
                for(int j=i+1; j<list.size(); j++){
                    if(map1.get("sourceCiBm").equals(list.get(j).get("sourceCiBm")) && map1.get("targetCiBm").equals(list.get(j).get("targetCiBm")) && map1.get("relName").equals(list.get(j).get("relName"))){
                        list.remove(j);
                        j--;
                    }
                }
            }
            return list;
        }

    }
	public List<Map<String, Object>> allDataDown(String typeId,List<Map<String, Object>> downSet) {
		for(Map<String, Object> map:downSet) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String targetTypeId=(String) map.get("targetTypeId");
			if(typeId.equals(sourceTypeId)) {
				map.put("check", true);
				allDataDown(targetTypeId,downSet);
			}
		}
		return downSet;
	}
	public List<Map<String, Object>> allDataUp(String typeId,List<Map<String, Object>> upSet) {
		for(Map<String, Object> map:upSet) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String targetTypeId=(String) map.get("targetTypeId");
			if(typeId.equals(targetTypeId)) {
				map.put("check", true);
				allDataUp(sourceTypeId,upSet);
			}
		}
		return upSet;
	}
	public List<Map<String, Object>> excludeDataDown(String typeId,List<Map<String, Object>> downSet) {
		for(Map<String, Object> map:downSet) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String targetTypeId=(String) map.get("targetTypeId");
			if(typeId.equals(sourceTypeId)) {
				map.put("relData", new ArrayList<Map<String,Object>>());
				excludeDataDown(targetTypeId,downSet);
			}
		}
		return downSet;
	}
	public List<Map<String, Object>> excludeDataUp(String typeId,List<Map<String, Object>> upSet) {
		for(Map<String, Object> map:upSet) {
			String sourceTypeId=(String) map.get("sourceTypeId");
			String targetTypeId=(String) map.get("targetTypeId");
			if(typeId.equals(targetTypeId)) {
				map.put("relData", new ArrayList<Map<String,Object>>());
				excludeDataUp(sourceTypeId,upSet);
			}
		}
		return upSet;
	}

	@Override
	public int getBMByCiTypeId(String bm, String id) {
		return typeDataDao.getBMByCiTypeId(bm, id);
	}

	public List<Map<String, Object>> getAlarmCount(List<Map<String, Object>> ciList) {
		List<String> ciIds = new ArrayList<String>();
		List<String> ciCodes = new ArrayList<String>();
		for (Map<String, Object> map : ciList) {
			map.put("num", 0);
			Object id = map.get("id");
			Object ciCode = map.get("ciBm");
			if (id!=null&&ciIds!=null) {
				ciIds.add(id.toString());
			}
			if (ciCode!=null&&ciCodes!=null) {
				ciCodes.add(ciCode.toString());
			}

		}
		if (ciCodes!=null && ciCodes.size()>0) {
			List<Map<String, Object>> alarmCountList = emvEvCurrentService.selectCountByCiIdList(ciIds.toArray(new String[ciIds.size()]),ciCodes.toArray(new String[ciCodes.size()]));
			for (Map<String, Object> ci : ciList) {
				String ciId = String.valueOf(ci.get("id"));
				String ciCode = String.valueOf(ci.get("ciBm"));
				for (Map<String, Object> alarmCount : alarmCountList) {
					if (ciCode.equals(alarmCount.get("ciCode").toString())) {
						ci.put("num", alarmCount.get("num"));
						break;
					}
				}

			}
		}
		return ciList;
	}
    
	@Override
	public Map<String, Object> getRelIdAndCiTypeIdByHierarchy(String ciId,Integer upNum,Integer downNum,String ciCode) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		
		List<String> upIds = new ArrayList<String>();
		JSONArray upRels = new JSONArray();

		List<String> downIds = new ArrayList<String>();
		JSONArray downRels = new JSONArray();

		//??????upNum???
		if (upNum!=null&&upNum>0) {
			Map<String, Object> upMap = getCiSingleRels(ciId, upNum, new ArrayList<String>(), new ArrayList<String>(), "up",ciCode);
			upIds = (List<String>) upMap.get("ids");
			upRels = (JSONArray) upMap.get("rels");
		}

		//??????downNum???
		if (downNum!=null&&downNum>0) {
			Map<String, Object> downMap = getCiSingleRels(ciId, downNum, new ArrayList<String>(), new ArrayList<String>(), "down",ciCode);
			downIds = (List<String>) downMap.get("ids");
			downRels = (JSONArray) downMap.get("rels");
		}
		//????????????????????????????????????????????????
		if(upNum==0 && downNum==0) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ciTypeData", new ArrayList<Map<String, Object>>());
			map.put("relData", new ArrayList<Map<String, Object>>());
			return map;
		}
		List<String> ids = new ArrayList<String>();
		ids.addAll(upIds);
		ids.addAll(downIds);
		ids = ids.stream().distinct().collect(Collectors.toList());
		List<Map<String, Object>> list=ciTypeRelDao.selectCiAndType(ids);
		Set<Map<String, Object>> setData=new HashSet<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			Map<String, Object> map1=new HashMap<String, Object>();
			String ciTypeId=(String) map.get("ciTypeId");
			String ciTypeBm=(String) map.get("ciTypeBm");
			map1.put("ciTypeId", ciTypeId);
			map1.put("ciTypeBm", ciTypeBm);
			setData.add(map1);
		}
		returnMap.put("ciTypeData", setData);
		
		JSONArray rels = new JSONArray();
		rels.addAll(upRels);
		rels.addAll(downRels);
		rels = BaseRelUtil.distinctJSONArray(rels);
		List<Map<String, String>> list2=new ArrayList<Map<String,String>>();
		for (Object object : rels) {
			Map<String, String> map1=new HashMap<String, String>();
			JSONObject one = (JSONObject) object;
			String relId = (String) one.get("relId");
			String relName = (String) one.get("relName");
			map1.put("relId", relId);
			map1.put("relName", relName);
			list2.add(map1);
		}
		ArrayList<Map<String, String>> data = list2.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(m -> m.get("relId")))), ArrayList::new));

		returnMap.put("relData", data);
        return returnMap;
	}

	@Override
	public List<Map<String,String>> findCiIdByMap(Map<String, Object> cMap) {
		return typeDataDao.findCiIdByMap(cMap);
	}

	@Override
	public Map<String, Object> getCiRels(String ciId,Integer upNum,Integer downNum,String relIds,String typeIds,String ciCode) {
		List<String> relIdList = getList(relIds);
		List<String> typeIdList = getList(typeIds);

		List<String> upIds = new ArrayList<String>();
		JSONArray upRels = new JSONArray();

		List<String> downIds = new ArrayList<String>();
		JSONArray downRels = new JSONArray();
        if((relIds==null || "".equals(relIds)) || (typeIds==null || "".equals(typeIds))) {
        	Map<String, Object> map=getRelIdAndCiTypeIdByHierarchy(ciId,upNum,downNum,ciCode);
        	//??????ID
        	Set<Map<String, Object>> set=(Set<Map<String, Object>>) map.get("ciTypeData");
        	//??????ID
        	ArrayList<Map<String, String>> list=(ArrayList<Map<String, String>>) map.get("relData");
        	List<String> relIdsList=new ArrayList<String>();
        	List<String> ciTypeIdsList=new ArrayList<String>();
        	if(relIds==null || "".equals(relIds)) {
        		for(Map<String, String> map1:list) {
        			String relId=map1.get("relId");
        			relIdsList.add(relId);
        		}
        		relIdList=relIdsList;
        	}
            if(typeIds==null || "".equals(typeIds)) {
        		for(Map<String, Object> map2:set) {
        			String ciTypeId=(String) map2.get("ciTypeId");
        			ciTypeIdsList.add(ciTypeId);
        		}
        		typeIdList=ciTypeIdsList;
        	}
        	
        }
		//??????upNum???
		if (upNum!=null&&upNum>0) {
			Map<String, Object> upMap = getCiSingleRels(ciId, upNum, relIdList, typeIdList, "up",ciCode);
			upIds = (List<String>) upMap.get("ids");
			upRels = (JSONArray) upMap.get("rels");
			JSONArray returnUpRels = new JSONArray();
			//?????????ci?????????????????????
			if(upRels!=null && upRels.size()>0) {
				for (Object object : upRels) {
					JSONObject one = (JSONObject) object;
					Object sourceObj = one.get("sourceCiBm");
					Object targetObj = one.get("targetCiBm");
					if (!ciCode.equals(sourceObj)) {
						returnUpRels.add(one);
					}
				}
			}
			upRels=returnUpRels;
		}
		//??????downNum???
		if (downNum!=null&&downNum>0) {
			Map<String, Object> downMap = getCiSingleRels(ciId, downNum, relIdList, typeIdList, "down",ciCode);
			downIds = (List<String>) downMap.get("ids");
			downRels = (JSONArray) downMap.get("rels");
			JSONArray returnDownRels = new JSONArray();
			//?????????ci?????????????????????
			if(downRels!=null && downRels.size()>0) {
				for (Object object : downRels) {
					JSONObject one = (JSONObject) object;
					Object sourceObj = one.get("sourceCiBm");
					Object targetObj = one.get("targetCiBm");
					if (!ciCode.equals(targetObj)) {
						returnDownRels.add(one);
					}
				}
			}
			downRels=returnDownRels;
		}

		List<String> ids = new ArrayList<String>();
		ids.addAll(upIds);
		ids.addAll(downIds);
		ids = ids.stream().distinct().collect(Collectors.toList());

		JSONArray rels = new JSONArray();
		rels.addAll(upRels);
		rels.addAll(downRels);
		rels = BaseRelUtil.distinctJSONArray(rels);

		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("ids", ids);
		resMap.put("cis", ciTypeRelDao.selectCiAndType(ids));
		resMap.put("rels", rels);
		return resMap;
	}
	
	@Override
	public List<Map<String, Object>> getImageFullNameByCiCodes(List<String> ids){
		return ciTypeRelDao.selectCiAndType(ids);
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-03
	 * @description: ????????????????????????
	 */
	@Override
	public void exportExcelDataDeploy(HSSFWorkbook workbook, XSSFWorkbook workbook2007, List<String> ciPropertyList, List<String> tid, HttpServletResponse response) {
		String fileName = "";
		//???????????????ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<Type> typeList = new ArrayList<Type>();
		if (tid == null) {
			tid = new ArrayList<String>();
		}
		List<Map> roleDataList = roleDataService.findRoleHighDataList();
		if(tid.size()==0 && ciPropertyList!=null && ciPropertyList.size()>0){
			List<String> typeIdList = typeDao.getTypeIdListCondition(ciPropertyList,domainId);
			List<String> typeIdLists = new ArrayList<String>();
			for (Map map : roleDataList){
				boolean searchAuth = (boolean) map.get("searchAuth");
				if (!searchAuth){
					typeIdLists.add(map.get("dataId").toString());
				}
			}
			for (int i = 0; i < typeIdList.size(); i++) {
				if (!typeIdLists.contains(typeIdList.get(i))){
					tid.add(typeIdList.get(i));
				}
			}
		}
		if (tid.size() == 0) {
			List<Type> tList = typeDao.findTypeList(domainId);
			List<String> typeIdList = new ArrayList<String>();
			for (Map map : roleDataList){
				boolean searchAuth = (boolean) map.get("searchAuth");
				if (!searchAuth){
					typeIdList.add(map.get("dataId").toString());
				}
			}
			for (int i = 0; i < tList.size(); i++) {
				if (!typeIdList.contains(tList.get(i))){
					tid.add(tList.get(i).getId());
				}
			}
		}
		for (int i = 0; i < tid.size(); i++) {
			Type type = typeDao.findTypeById(tid.get(i),domainId);
			if (type != null) {
				if ("".equals(fileName)){
					fileName = type.getCi_type_bm();
				}else{
					fileName = fileName +"&" + type.getCi_type_bm();
				}
				typeList.add(type);
			}
		}
		for (int i = 0; i < typeList.size(); i++) {
			// ?????????????????????.
			List<TypeItem> items = typeItemDao.findItemByTid(tid.get(i));
			// ??????????????????????????????
			if (items.size() > 0) {
				// ????????????ID????????????LIST
				List dataList = getDataDeploy(typeList.get(i).getId(),ciPropertyList);
				List<String> headersId = new ArrayList<String>();
				// ????????????LIST
				List<String> names = new ArrayList<String>();
				for (int j = 0; j < items.size(); j++) {
					names.add(items.get(j).getAttr_name());
					headersId.add(items.get(j).getMp_ci_item());
				}
				// ?????????LIST???????????? ??????excel?????????
				String[] strings = new String[names.size()];
				names.toArray(strings);
				ExcelUtilWrapper<Dir> util = new ExcelUtilWrapper<Dir>();
				util.MapExcel(workbook, fileName, typeList.get(i)
						.getCi_type_mc(), names, headersId, dataList, response);
			}
		}
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-03
	 * @description: ????????????ID????????????(????????????)
	 */
	private List getDataDeploy(String tid,List<String> ciPropertyList) {
		// ????????????ID??????????????????
		List<TypeItem> list = typeItemDao.findItemByTid(tid);
		if (list.size() > 0) {
			// ????????????????????????
			List<String> arr = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				// ??????????????????????????????,?????????DATA_1,DATA_2???,??????????????????SQL
				arr.add(list.get(i).getMp_ci_item());
			}
			if (list.size() > 0) {
				List data = typeItemDao.findDataMapDeploy(tid,ciPropertyList, Joiner.on(",").join(arr));
				return data;
			}
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> getWorldMapCiSingleRels(List<String> relIdList, List<String> ciCodeList, String ciTypeName){
		String [] relArray = relIdList.toArray(new String[relIdList.size()]);
		String [] ciCodeArray = ciCodeList.toArray(new String[ciCodeList.size()]);
		Map<String,Object> itemMap=new HashMap<String,Object>();
		itemMap.put("sourceCiCodeList", ciCodeArray);
		itemMap.put("relIdList", relArray);
		itemMap.put("targetTypeName", ciTypeName);
		List<Map<String,Object>> listData=ciTypeRelDao.getWorldMapCiSingleRels(itemMap);
		return listData;
	}
	
	@Override
	public List<Map<String,Object>> getWorldMapCiSingleRelsUp(List<String> relIdList, List<String> ciCodeList, String ciTypeName){
		String [] relArray = relIdList.toArray(new String[relIdList.size()]);
		String [] ciCodeArray = ciCodeList.toArray(new String[ciCodeList.size()]);
		Map<String,Object> itemMap=new HashMap<String,Object>();
		itemMap.put("targetCiCodeList", ciCodeArray);
		itemMap.put("relIdList", relArray);
		itemMap.put("sourceTypeName", ciTypeName);
		List<Map<String,Object>> listData=ciTypeRelDao.getWorldMapCiSingleRelsUp(itemMap);
		return listData;
	}
	public Map<String, Object> getCiSingleRels(String ciId,Integer num,List<String> relIdList,List<String> typeIdList,String direction,String ciCode) {
		//??????downNum???
		BaseRelUtil downRelUtil = new BaseRelUtil("sourceCiBm","targetCiBm",num) {
			@Override
			public Object getDate(List<String> ids, List<String> ciCodes) {
				List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
				if("up".equals(direction)) {
					String [] ciCodesArray = ciCodes.toArray(new String[ciCodes.size()]);
					List<String> list=new ArrayList<String>();
					for(int i = 0; i < ciCodesArray.length; i++){
						String ciCodeStr=ciCodesArray[i];
						List<String> listCiCode=Arrays.asList(ciCodeStr.split(","));
						list.addAll(listCiCode);
					}
					String [] listArray = list.toArray(new String[list.size()]);
					Map<String, Object> itemMap=new HashMap<String, Object>();
					String [] relIdArray = relIdList.toArray(new String[relIdList.size()]);
					String [] typeIdArray = typeIdList.toArray(new String[typeIdList.size()]);
					String [] idsArray = ids.toArray(new String[ids.size()]);
					if(relIdArray.length>0) {
					  itemMap.put("relList", relIdArray);
					}else {
					  itemMap.put("relList", "");
					}
					if(typeIdArray.length>0) {
						itemMap.put("sourceTypeIdList", typeIdArray);
					}else {
						itemMap.put("sourceTypeIdList", "");
					}					
					itemMap.put("targetCiBmList", listArray);
					returnList=typeDataDao.getCiDataRelUpBySourceIdAndTargetId(itemMap);

				}else if ("down".equals(direction)) {
					String [] ciCodesArray = ciCodes.toArray(new String[ciCodes.size()]);
					List<String> list=new ArrayList<String>();
					for(int i = 0; i < ciCodesArray.length; i++){
						String ciCodeStr=ciCodesArray[i];
						//list=Arrays.asList(ciCodeStr.split(","));
						List<String> listCiCode=Arrays.asList(ciCodeStr.split(","));
						list.addAll(listCiCode);
					}
					String [] listArray = list.toArray(new String[list.size()]);
					Map<String, Object> itemMap=new HashMap<String, Object>();
					String [] relIdArray = relIdList.toArray(new String[relIdList.size()]);
					String [] typeIdArray = typeIdList.toArray(new String[typeIdList.size()]);
					String [] idsArray = ids.toArray(new String[ids.size()]);
					if(relIdArray.length>0) {
					  itemMap.put("relList", relIdArray);	
					}else {
						itemMap.put("relList", "");
					}
					if(typeIdArray.length>0) {
					  itemMap.put("targetTypeIdList", typeIdArray);	
					}else {
					  itemMap.put("targetTypeIdList", "");
					}
					
					itemMap.put("sourceCiBmList", ciCodesArray);
					returnList=typeDataDao.getCiDataRelDownBySourceIdAndTargetId(itemMap);
				}
				

		        return returnList;
			}
		};
		return downRelUtil.getData(ciId,ciCode);
	}


	public Map<String, Object> getCiTypeSingleRels(String ciTypeId,Integer num,String direction) {

		BaseTypeRelUtil downTypeRelUtil = new BaseTypeRelUtil("sourceTypeId","targetTypeId",num) {
			@Override
			public Object getDate(List<String> ids) {
				List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
				if("up".equals(direction)) {
					Map<String, Object> itemMap=new HashMap<String, Object>();
					String [] idsArray = ids.toArray(new String[ids.size()]);
					itemMap.put("targetTypeIdList", idsArray);
					itemMap.put("sourceTypeIdList", "");
					returnList=typeDataDao.queryCiTypeRelBySourceIdAndTargetIdUp(itemMap);
				}else if("down".equals(direction)) {
					Map<String, Object> itemMap=new HashMap<String, Object>();
					String [] idsArray = ids.toArray(new String[ids.size()]);
					itemMap.put("sourceTypeIdList", idsArray);
					itemMap.put("targetTypeIdList", "");
					returnList=typeDataDao.queryCiTypeRelBySourceIdAndTargetIdDown(itemMap);
				}
				return returnList;
			}

		};
		return downTypeRelUtil.getData(ciTypeId);
	}


	private List<String> getList(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			return Arrays.asList(ids.split(","));
		}
		return null;
	}

	@Override
	public List<Map> findDataInfo(String jsonStr) {
		Map map = JSONObject.parseObject(jsonStr,Map.class);
		String typeId = map.get("typeId").toString();
		List list = (List) map.get("query");
		String sql = "";
		String head = "select ci_id from iomci.iom_ci_type_data_index where type_id = "+typeId;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map<String,String> queryMap  = JSONObject.parseObject(JSON.toJSONString(list.get(i)),Map.class);
			if (queryMap!=null) {
				String itemId = queryMap.get("itemId");
				String operator = queryMap.get("operator");
				String value = queryMap.get("value");
				if (itemId!=null&&operator!=null&&value!=null&&sb!=null) {
					sb.append("(attr_id = '"+itemId+"' and idx "+operator+" '"+value+"') ||");
				}
			}
		}

		if(null!=sb){
			head += " and (";
			String sbSql = sb.substring(0, sb.length()-2);
			sbSql += ")";
			sql = head+sbSql;
		}

		List<String> indexList = typeDataDao.findDataInfo(sql);
		List<Map> typeList = new ArrayList<Map>();
		if(indexList.size()>0) {
			typeList = typeDataDao.findCiTypeInfo(indexList);
		}

		List<Map> resultList = new ArrayList<Map>();
		for (int i = 0; i < typeList.size(); i++) {
			Map m = typeList.get(i);
			Map dataMap = typeDataDao.findCiDataInfo(m.get("ci_id").toString());
			m.put("DataMap", dataMap);
			resultList.add(m);
		}

		return resultList;
	}
	
	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: ???JavaBean?????????map
	 */
	public static Map<String,Object> bean2map(Object bean) throws Exception{
		Map<String,Object> map = new HashMap<>();
		//??????JavaBean????????????
		BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
		//?????????????????????
		PropertyDescriptor[] pds = b.getPropertyDescriptors();
		//???????????????
		for (PropertyDescriptor pd : pds) {
			//????????????
			String propertyName = pd.getName();
			//?????????,???getter????????????
			Method m = pd.getReadMethod();
			//???????????????getter?????????????????????
			Object properValue = m.invoke(bean);

			//????????????-????????? ??????Map???
			map.put(propertyName, properValue);
		}
		return map;
	}
			

	/**
	   * ???Map??????key???????????????????????????
	   *
	   * @param map
	   * @return
	   */
	  public Map<String, Object> formatHumpName(Map<String, Object> map) {
	    Map<String, Object> newMap = new HashMap<String, Object>();
	    Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	      Map.Entry<String, Object> entry = it.next();
	      String key = entry.getKey();
	      String newKey = toFormatCol(key);
	      newMap.put(newKey, entry.getValue());
	    }
	    return newMap;
	  }
 
	  public String toFormatCol(String colName) {
	    StringBuilder sb = new StringBuilder();
	    String[] str = colName.toLowerCase().split("_");
	    int i = 0;
	    for (String s : str) {
	      if (s.length() == 1) {
	        s = s.toUpperCase();
	      }
	      i++;
	      if (i == 1) {
	        sb.append(s);
	        continue;
	      }
	      if (s.length() > 0) {
	        sb.append(s.substring(0, 1).toUpperCase());
	        sb.append(s.substring(1));
	      }
	    }
	    return sb.toString();

	  }
	/**
	 * CI?????????????????????????????????????????????
	 * ???????????????
	 * @param map ci????????????
	 * @return
	 */
	@Override
	public List<LinkedHashMap> findConfigInfoNoPageHump(Map map) {
		List<LinkedHashMap> configList = typeDataDao.findConfigInfoNoPageHump(map);
		//ciId??????
		List<String> ciIds = new ArrayList<String>();
		List<String> ciCodes = new ArrayList<String>();
		//???????????????????????????ciId????????????
		for (LinkedHashMap configHashMap : configList) {
			ciIds.add(String.valueOf(configHashMap.get("ciId")));
			ciCodes.add(String.valueOf(configHashMap.get("ciBm")));
		}
		//??????id????????????????????????
		List<Map<String, Object>> alarmCountList = emvEvCurrentService.selectCountByCiIdList(ciIds.toArray(new String[ciIds.size()]),ciCodes.toArray(new String[ciCodes.size()]));
		List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
		for (LinkedHashMap m : configList) {
			LinkedHashMap newMap = new LinkedHashMap();
			LinkedHashMap newMap2 = new LinkedHashMap();
			for (Object o : m.keySet()) {
				if (o.toString().indexOf("DATA") != -1) {
					if (m.get(o) != null) {
						newMap.put(o, m.get(o) + ",");
					}
				} else {
					newMap2.put(o, m.get(o));
					newMap2.put("alarmCount", 0);
					String ciId = String.valueOf(m.get("ciId"));
					String ciCode = String.valueOf(m.get("ciBm"));
					//??????????????????list
					if (alarmCountList != null && alarmCountList.size() > 0) {
						for (Map<String, Object> alarmCountMap : alarmCountList) {
							//??????ciId??????????????????
							if (ciCode.equals(alarmCountMap.get("ciCode"))) {
								Object num = alarmCountMap.get("num");
								if (StringUtils.isNotEmpty(String.valueOf(num))) {
									newMap2.put("alarmCount", num);
								}

							}
						}
					}

				}
			}
			newMap2.put("DataMap", newMap);
			newList.add(newMap2);
		}
		return newList;
	}

	@Override
	public List<String> getAttrValuesList(String ciTypeId, String mpCiItem, String searchCondition, String domainId) {
		return typeDataDao.getAttrValuesList(ciTypeId,mpCiItem, searchCondition, domainId);
	}

	@Override
	public PageResult findCiByCiCode(Map map) {
		PageResult pageResult = new PageResult();
		List<Map<String, Object>> listResult = new ArrayList<>();
		List<Map<String, Object>> list = typeDataDao.findCiByCiCode(map);
		for (Map<String, Object> maps: list) {
			Map<String, Object> result = new HashMap<>();
			result.put("typeId",maps.get("typeId"));
			result.put("ciCode",maps.get("ciCode"));
			result.put("ciName",maps.get("ciName"));
			result.put("ciId",maps.get("ciId"));
			result.put("ciTypeMc",maps.get("ciTypeMc"));
			List<TypeItem> itemList = typeItemDao.selItemByTypeId(maps.get("typeId").toString());
			for (TypeItem item:itemList) {
				result.put(item.getAttr_name(),maps.get(item.getMp_ci_item()));
			}
			listResult.add(result);

		}
		pageResult.setReturnObject(listResult);
		pageResult.setReturnBoolean(true);
		if (list!=null && list.size()>0){
			pageResult.setTotalPage(((Page)list).getPages());
			pageResult.setTotalResult((int)((Page)list).getTotal());
		}else {
			pageResult.setTotalPage(0);
			pageResult.setTotalResult(0);
		}
		return pageResult;
	}

	@Override
	public List<LinkedHashMap> getCiTypeInfo(Map map) {
		MyPagUtile.startPage();
		List<LinkedHashMap> configList = typeDataDao.getCiTypeInfo(map);
		return configList;
	}

	/**
	 * ??????CIID??????CI?????????????????????????????????
	 * @param
	 * @return
	 */
	@Override
	public Map<String, Object> findCiByCiId(Map map) {
		Map<String, Object> listResult = new HashMap<>();
		Map<String, Object> list = typeDataDao.findCiByCiId(map);
		if (list!=null && list.size()>0){
			Map<String, Object> result = new HashMap<>();
			result.put("typeId",list.get("typeId"));
			result.put("ciBm",list.get("ciBm"));
			result.put("ciId",list.get("ciId"));
			result.put("ciTypeMc",list.get("ciTypeMc"));
			List<TypeItem> itemList = typeItemDao.selItemByTypeId(list.get("typeId").toString());
			for (TypeItem item:itemList) {
				result.put(item.getAttr_name(),list.get(item.getMp_ci_item()));
			}
			listResult=result;
		}
		return listResult;
	}

	/**
	 * ??????CICODE?????????ID?????????
	 *
	 * @param ciCode
	 * @param attrId
	 * @return
	 */
	@Override
	public String findDataByCiCodeAndAttrId(String ciCode, String attrId,String ciType) {
		return typeDataDao.findDataByCiCodeAndAttrId(ciCode, attrId,ciType);
	}


	public static void main(String[] args) {
		String [] arr = new String [10];
		Arrays.fill(arr,"11");

		List<String> typeData = Arrays.asList(arr);
		mapreduce(typeData, 3, (list)->{
			System.out.println(list);
		});
	}


	static void mapreduce(List<String> typeData, int num , Consumer<List<String>> action){

		int index = typeData.size() / num ;
		if(typeData.size() % num != 0){
			index ++;
		}
		Stream.iterate(0, i -> i + 1).limit(index).parallel().forEach(i -> {
			//????????????????????????
			List<String> perList = typeData.parallelStream().skip(i * num)
					.limit(num)
					.collect(Collectors.toList());
			//????????????
			//System.out.println(perList);
			action.accept(perList);
		});
	}

	/**
	 * ????????????????????????
	 * @param typeData
	 * @param num
	 * @param action
	 * @param <T>
	 */
	static <T> void  mapreduceTypeData(List<T> typeData, int num , Consumer<List<T>> action){

		int index = typeData.size() / num ;
		if(typeData.size() % num != 0){
			index ++;
		}
		log.info(String.format("??????????????????%s???????????????%s-------------------------4-----",typeData.size(),index));
		Stream.iterate(0, i -> i + 1).limit(index).parallel().forEach(i -> {
			//????????????????????????
			List<T> perList = typeData.stream().skip(i * num)
					.limit(num)
					.collect(Collectors.toList());
			log.info(String.format("?????????%s-------------------------4-----",i));
			action.accept(perList);
		});
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @param ciCode
	 * @return
	 */
	@Override
	public List<Map> getCommandByCiCode(String ciCode) {
		//??????
		List<Map> result = new ArrayList<>();
		//?????????????????????????????????????????????
		List<Map> commandClassList = typeDataDao.getCommandClassList();
		//??????????????????????????????????????????????????????
		for (Map map:commandClassList) {
			Map newMap = new HashMap();
			//???????????????????????????
			List<Map> classList = typeDataDao.getClassList(map.get("CI_TYPE_ID").toString());
			//???????????????????????????CI???????????????????????????CI??????
			List<Map> ciList = typeDataDao.getCommandCiList(map,classList,ciCode);
			if (ciList!=null && ciList.size()>0){
				newMap.put("classList",classList);
				newMap.put("ciList", ciList);
				result.add(newMap);
			}
		}
		return result;
	}

	/**
	 *
	 * ???????????????????????????????????????CI????????????
	 *
	 * @param sourceCiId
	 * @param ciTypeId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findFieldConfCiData(String sourceCiId, String ciTypeId) {

		List<Map<String, Object>> resultList = new ArrayList<>();
		//????????????????????????
		List<CiAssociatedFieldConf> confList = ciAssociatedFieldConfService.findConfList(sourceCiId,ciTypeId);
		if (confList!=null && confList.size()>0){
			Map<String,List<CiAssociatedFieldConf>> groupList =  confList.stream().collect(Collectors.groupingBy(e->e.getSourceCiId()));
			for (String key : groupList.keySet()) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("sourceCiId", key);
				List<CiAssociatedFieldConf> confLists = groupList.get(key);
				for (CiAssociatedFieldConf conf:confLists) {
					List<String> itemList =  typeItemDao.findItemValByIdList(Arrays.asList(conf.getCiItemId().split(",")));
					if (itemList!=null && itemList.size()>0){
						List<Map<String, Object>> ciList = new ArrayList<>();
						List<Map<String,Object>> ciDataList = typeDataDao.getCiDataList(conf.getCiTypeId(),itemList);
						for (Map<String, Object> map:ciDataList) {
							Map<String, Object> ciMap = new HashMap<>();
							List<String> sourceCi = new ArrayList<>();
							ciMap.put("ciCode",map.get("ciCode"));
							for (String ciKey:map.keySet()){
								if (!"ciCode".equals(ciKey)){
									sourceCi.add(String.valueOf(map.get(ciKey)));
								}
							}
							ciMap.put("sourceCiCode",sourceCi);
							ciList.add(ciMap);
						}
						resultMap.put("ciInfo",ciList);

					}else{
						continue;
					}

				}
				resultList.add(resultMap);
			}
		}

		return resultList;
	}


	@Override
	public PageResult updateCI(List<Map<String, Object>> data) {
		//???????????????????????????
		//??????
		PageResult pg = validateData(data);

		if(!pg.isReturnBoolean()){
			saveLog(JSONObject.toJSONString(data), JSONObject.toJSONString(pg));
			return pg;
		}
		Optional<Map<String, Object>> dataMap = data.stream().filter(map->map.get(CI_TYPE_ID) != null).findFirst();
		String ciTypeId = String.valueOf(dataMap.get().get(CI_TYPE_ID));
		//????????????
		Type type = typeServie.findTypeById(ciTypeId);
		List<TypeItem> items = typeItemService.findItemByTid(ciTypeId);


		//????????????????????????typedata??????
		List<TypeData> dataList = buildTypeData(data);

		dataList = dataList.parallelStream().distinct().collect(Collectors.toList());


		//?????????????????????
		List<Map<String, Object>> failedList = new ArrayList<>();


		//?????????????????????
		List<TypeData> successList = new ArrayList<>();

		for(int i = 0; i < dataList.size(); i ++){
			//??????cicode???????????????
			Set<String> ciCodeSet = new HashSet<>();
			if(!ciCodeSet.add(dataList.get(i).getCiCode())){
				failedList.add(data.get(i));
				continue;
			}
			for (int k=0;k<items.size();k++){
				TypeItem typeItem = items.get(k);
				JSONObject typeDataJo = JSONObject.parseObject(JSONObject.toJSONString(dataList.get(i)));
				String key = typeItem.getMp_ci_item();

				Object fieldValue = typeDataJo.get(key.toLowerCase());
				if(fieldValue != null && StringUtils.isNotEmpty(fieldValue.toString()) &&
						StringUtils.isNotEmpty(typeItem.getRegexp())){
					if(!RegexpUtil.checkValidity(fieldValue.toString(), typeItem.getRegexp())){
						//????????????
						failedList.add(data.get(i));
						continue;
					}

				}

			}
			successList.add(dataList.get(i));
		}

		//????????????????????????
		successList.forEach(item->{
			item.setCi_type_id(ciTypeId);
			item.setDomain_id(type.getDomain_id());
		});



		Map m = syncData(ciTypeId, successList, type.getDomain_id());
		//?????????????????????CI????????????
		ciAssociatedFieldConfService.sendCiChangeMsg(ciTypeId);
		//???????????????CINAME
		infoService.updateCiInfoName(ciTypeId);
		//??????CI??????
		ciInfoToInterfaceService.updateCiVesion();
		String result = "????????????!??????" + m.get("tj") + "???,??????" + m.get("xg") + "???,????????????????????????" + m.get("cf") + "???,??????????????????"
				+ m.get("dataBaseCf") + "???!";
		if(failedList.size() > 0){
			result += "????????????????????????" + JSONObject.toJSONString(failedList);
		}

		saveLog(JSONObject.toJSONString(data), result);

		return DataUtils.returnPr(true);

	}

	private void saveLog(String param, String result){
		Object resObject = czryService.findCzryByIdFeign(SYSADMINID);
		JSONObject czry = new JSONObject();
		if (resObject!=null) {
			czry = JSONObject.parseObject(JSON.toJSONString(resObject));
		}

		String userId = czry.getOrDefault("id","").toString();
		String userDlzh = czry.getOrDefault("czry_dldm","").toString();
		String userName = czry.getOrDefault("czry_mc","").toString();
		String module = "";

		IomCampActionApiInfo campAction = new IomCampActionApiInfo();
		campAction.setId(SeqUtil.getId());
		campAction.setLog_time(DateUtils.getNowStr());
		campAction.setUser_id(userId);
		campAction.setCzry_dldm(userDlzh);
		campAction.setCzry_mc(userName);
		campAction.setOp_name(module);
		campAction.setOp_desc("????????????");
		campAction.setIs_status(1);
		campAction.setOp_param(param);
		campAction.setOp_result(result);
		campAction.setCjsj(DateUtils.getNowStr());


		actionApiService.insertInfo(campAction);
	}

	/**
	 * excel????????????
	 */
	private Map syncData(String ci_type_id,  List<TypeData> dataList, String domainId) {


		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tj", 0);
		map.put("xg", 0);
		map.put("cf", 0);
		map.put("dataBaseCf", 0);

		List<TypeItem> items = typeItemService.findItemByTid(ci_type_id);

		int size = dataList.size();
		// ????????????ID??????TYPEDATA????????????
		List<TypeData> list = typeDataDao.finDataByTypeId(ci_type_id);
		List<Info> infoList = infoDao.findDataIdByTid(ci_type_id, domainId);
		Map<String, String> idCodeMap = new HashMap<>();
		infoList.stream().forEach(item->{
			idCodeMap.put(item.getId(), item.getCiCode());
		});
		dataList.addAll(list);

		//??????excel??????????????????????????????
		List<TypeData> xtList = getDiffrent(dataList, list);
		//?????????????????????????????????????????????
		int outSize = size - xtList.size();
		map.put("cf",outSize);

		List<TypeData> idList = new ArrayList<TypeData>();

		for (int i = 0; i < list.size(); i++) {

			Iterator<TypeData> it = xtList.iterator();
			while(it.hasNext()){
				TypeData x = it.next();
				String id = list.get(i).getId();
				if(idCodeMap.containsKey(id) && idCodeMap.get(id).equals(x.getCiCode())){
					x.setDomain_id(domainId==null?"-1":domainId);
					x.setCi_type_id(ci_type_id);
					x.setId(list.get(i).getId());
					//?????????data1???excel???data1?????????????????????????????????????????????
					idList.add(x);
					it.remove();
				}
			}
		}


		if(idList.size()>0){
			//?????????????????????
			mapreduceTypeData(idList, 1000, tempList -> typeDataDao.updateDatas(tempList));
			String pky = typeItemDao.findPK(ci_type_id);
			List<Info> infoLists = new ArrayList<>();
			for (int i=0;i<idList.size();i++){

				String pkValue = JSONObject.parseObject(JSONObject.toJSONString(idList.get(i))).getString(pky.toLowerCase());
				Info info = new Info();
				info.setCiCode(pkValue);
				info.setId(idList.get(i).getId());
				info.setDomain_id(domainId==null?"-1":domainId);
				/*???CI???????????????????????????????????????????????????*/
				Map attrsStrMap = JSONObject.parseObject(JSON.toJSONString(idList.get(i)), Map.class);
				attrsStrMap.remove("id");
				attrsStrMap.remove("ci_type_id");
				attrsStrMap.remove("domain_id");
				String attrsStr = JSON.toJSONString(attrsStrMap);
				for (TypeItem typeItem : items) {
					if ("DATA_1".equals(typeItem.getMp_ci_item())){
						attrsStr=attrsStr.replace("data_1\"",typeItem.getAttr_name()+"\"");
					}else if("DATA_2".equals(typeItem.getMp_ci_item())){
						attrsStr=attrsStr.replace("data_2\"",typeItem.getAttr_name()+"\"");
					}else if("DATA_3".equals(typeItem.getMp_ci_item())){
						attrsStr=attrsStr.replace("data_3\"",typeItem.getAttr_name()+"\"");
					}else {
						attrsStr = attrsStr.replace(typeItem.getMp_ci_item().toLowerCase(),typeItem.getAttr_name());
					}
				}
				info.setAttrs_str(attrsStr);
				infoLists.add(info);

			}
			if (infoLists !=null && infoLists.size()>0){
				mapreduceTypeData(infoLists, 1000, (tempList)->infoDao.updateInfoList(tempList));
				infoLists.forEach(item->{
					String ciCode=item.getCiCode();
					String key = "ciInfo_"+ciCode;
					RedisUtils.remove(key);
				});
			}
		}

		map.put("xg", idList.size());

		if (xtList.size()>0) {
			List<String> bms = new ArrayList<String>();
			for (TypeData typeData : xtList) {
				typeData.setCi_type_id(ci_type_id);
				bms.add(typeData.getData_1());
			}
			IomCiInfoExample iomCiInfoExample = new IomCiInfoExample();
			com.integration.generator.entity.IomCiInfoExample.Criteria criteria = iomCiInfoExample.createCriteria();
			criteria.andCiCodeIn(bms);
			criteria.andYxbzEqualTo(1);
			List<IomCiInfo> iomCiInfos = iomCiInfoMapper.selectByExample(iomCiInfoExample);
			List<TypeData> adds = new ArrayList<TypeData>();
			if (iomCiInfos.size()>0) {
				map.put("dataBaseCf", iomCiInfos.size());
				for (TypeData typeData : xtList) {
					boolean flag = true;
					for (IomCiInfo iomCiInfo : iomCiInfos) {
						if (iomCiInfo.getCiCode().equals(typeData.getData_1())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						adds.add(typeData);
					}
				}
			}else {
				adds.addAll(xtList);
			}

			if (adds.size()>0) {
				addSyncDatas(adds,SYSADMINID,ci_type_id, domainId);
			}
			map.put("tj", adds.size());
		}

		return map;

	}


	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description: ??????????????????
	 */
	public boolean addSyncDatas(List<TypeData> typeData, String cjr,String typeId, String domainId) {
		List<TypeItem> items = typeItemService.findItemByTid(typeId);
		if(typeData.size()==0){
			return true;
		}
		List<String> dataIds = new ArrayList<String>();
		for (int i = 0; i < typeData.size(); i++) {
			String nextId = SeqUtil.nextId() +"";
			typeData.get(i).setId(nextId);
			typeData.get(i).setDomain_id(domainId==null?"-1":domainId);
			dataIds.add(nextId);
		}
		//???????????????
		int num=1000;
		mapreduceTypeData(typeData, num, (list)->typeDataDao.importExcelData(list));
		// ????????????ID???????????????????????????
		String mp = typeItemDao.findPK(typeId).toLowerCase();

		//????????????jsonobeject?????????????????????
		List<String> mpList = typeData.stream().map(item->{
			return JSONObject.parseObject(JSONObject.toJSONString(item)).getString(mp);
		}).collect(Collectors.toList());

		List<Info> infoList = new ArrayList<Info>();

		for (int i = 0; i < dataIds.size(); i++) {
			// ??????INFO
			Info info = new Info();
			info.setId(dataIds.get(i));
			info.setCiCode(mpList.get(i));
			info.setCi_type_id(typeId);
			info.setSource_id(1);
			info.setCjr_id(cjr);
			info.setCjsj(DateUtils.getDate());
			info.setYxbz("1");
			info.setDomain_id(domainId==null?"-1":domainId);
			/*???CI???????????????????????????????????????????????????*/
			Map attrsStrMap = JSONObject.parseObject(JSON.toJSONString(typeData.get(i)), Map.class);
			attrsStrMap.remove("id");
			attrsStrMap.remove("ci_type_id");
			attrsStrMap.remove("domain_id");
			String attrsStr = JSON.toJSONString(attrsStrMap);
			for (TypeItem typeItem : items) {
				if ("DATA_1".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_1\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_2".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_2\"",typeItem.getAttr_name()+"\"");
				}else if("DATA_3".equals(typeItem.getMp_ci_item())){
					attrsStr=attrsStr.replace("data_3\"",typeItem.getAttr_name()+"\"");
				}else {
					attrsStr=attrsStr.replace(typeItem.getMp_ci_item().toLowerCase(),typeItem.getAttr_name());
				}
			}
			info.setAttrs_str(attrsStr);
			infoList.add(info);
		}

		mapreduceTypeData(infoList, num, (list)->infoDao.addTypeInfos(list));

		mpList.forEach(id->{
			RedisUtils.remove("ciInfo_"+id);
		});
		return true;

	}

	private void clearInfoRedisCache(String id){
		Map<String,Object> map=infoDao.queryCiInfoByCiId(id);
		String ciCode=(String) map.get("ciCode");
		String key = "ciInfo_"+ciCode;
		RedisUtils.remove(key);
	}

	/**
	 * ????????????
	 * @param data
	 * @return
	 */
	private PageResult validateData(List<Map<String, Object>> data){
		if(data == null || data.size() == 0){
			return DataUtils.returnPr(false, "?????????????????????");
		}



		//???????????????ci_type_id

		Optional<Map<String, Object>> dataMap = data.stream().filter(map->map.get(CI_TYPE_ID) != null).findFirst();
		if(!dataMap.isPresent()){
			return DataUtils.returnPr(false, "??????id???????????????");
		}


		long dataCount = data.stream().filter(map->map.get(CI_CODE) != null).count();
		if(dataCount == 0){
			return DataUtils.returnPr(false, "?????????????????????????????????ciCode???");
		}


		return DataUtils.returnPr(true);
	}


	/**
	 * ??????ci??????
	 * @param data
	 * @return
	 */
	private List<TypeData> buildTypeData(List<Map<String, Object>> data){
		if(data == null || data.size() == 0){
			return new ArrayList<>();
		}


		Map<String, Object> temp = new HashMap<>();
		return data.stream().map(map->{
			map.entrySet().forEach(entry->{
				if(entry.getKey().startsWith(DATA_FIELD_PREFIX)){
					temp.put(entry.getKey().toLowerCase(), entry.getValue());
				}
			});
			TypeData typeData = JSONObject.parseObject(JSONObject.toJSONString(temp), TypeData.class);

			temp.clear();

			typeData.setCi_type_id(map.getOrDefault(CI_TYPE_ID,"").toString());
			typeData.setDomain_id(map.getOrDefault(DOMAIN_ID,"-1").toString());
			typeData.setCiCode(map.get(CI_CODE).toString());


			return typeData;
		}).collect(Collectors.toList());

	}
}

