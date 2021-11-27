package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.dao.TypeDao;
import com.integration.dao.TypeItemDao;
import com.integration.entity.*;
import com.integration.feign.DfileService;
import com.integration.feign.RoleDataService;
import com.integration.feign.ThemeFieldService;
import com.integration.generator.dao.IomCiKpiTypeMapper;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.entity.IomCiKpiType;
import com.integration.generator.entity.IomCiKpiTypeExample;
import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeExample;
import com.integration.service.*;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
/**
* @Package: com.integration.controller
* @ClassName: TypeDataController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 类数据管理
*/
@SuppressWarnings("ALL")
@RestController
public class TypeDataController {

	private static int num = 0;

	private static int pmvnum = 0;

	@Autowired
	private TypeDataService typeDataService;

	@Autowired
	private TypeItemService typeItemService;

	@Autowired
	private DfileService dfileService;

	@Autowired
	private InfoService infoService;

	@Autowired
	private TypeService typeServie;

	@Autowired
	private IomCiKpiTypeMapper iomCiKpiTypeMapper;

	@Autowired
	private IomCiTypeMapper iomCiTypeMapper;
	@Resource
	private RoleDataService roleDataService;

	@Autowired
	private CiInfoToInterfaceService ciInfoToInterfaceService;

	@Autowired
	private TypeItemDao typeItemDao;
	
	@Autowired
	private CiKpiService ciKpiService;
	
	@Autowired
	private RltRuleService rltRuleService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private ThemeFieldService themeFieldService;
	
	@Autowired
	private TypeDao typeDao;

	@Autowired
	private CiAssociatedFieldConfService ciAssociatedFieldConfService;
	/**
	 * 添加数据
	 * @return
	 */
	@OptionLog(desc = "添加数据", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/addTypeData", method = RequestMethod.POST)
	public Object addTypeData(TypeData typeData, HttpServletRequest request) {
		String cjr = TokenUtils.getTokenUserId();
		int sf = typeDataService.getBMByCiTypeId(typeData.getData_1(), typeData.getCi_type_id());
		if (sf>0) {
			PageResult.fail("主键已存在!");
			return false;
		}
		TypeData result = typeDataService.addData(typeData, cjr);
		if (result != null) {
			//给接口平台推送CI变化状态
			ciAssociatedFieldConfService.sendCiChangeMsg(typeData.getCi_type_id());
			//修改台账中CINAME
			infoService.updateCiInfoName(typeData.getCi_type_id());
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			PageResult.success("添加成功!");
		} else {
			PageResult.fail("添加失败!");
		}
		return result;
	}
	
	@RequestMapping(value = "/findVerificationCiCode", method = RequestMethod.POST)
	public Object findVerificationCiCode(@RequestParam Map map) {
		String ciCode=(String) map.get("data_1");
		String ciTypeId=(String) map.get("ci_type_id");
		Integer sf = typeDataService.getBMByCiTypeId(ciCode, ciTypeId);
		return sf;	
	}

	/**
	 * 添加数据（对外服务）
	 *
	 * @return
	 */
	@RequestMapping(value = "/addTypeDataCast", method = RequestMethod.POST)
	public boolean addTypeDataCast(@RequestBody TypeData typeData, HttpServletRequest request) {
		String cjr = TokenUtils.getTokenUserId();
		int sf = typeDataService.getBMByCiTypeId(typeData.getData_1(), typeData.getCi_type_id());
		if (sf>0) {
			PageResult.fail("主键已存在!");
			return false;
		}
		TypeData result = typeDataService.addData(typeData, cjr);
		if (result == null) {
			return false;
		}
		//修改CI版本
		ciInfoToInterfaceService.updateCiVesion();
		//给接口平台推送CI变化状态
		ciAssociatedFieldConfService.sendCiChangeMsg(typeData.getCi_type_id());
		return true;

	}

	/**
	 * 添加数据DCV
	 *
	 * @return
	 */
	@OptionLog(desc = "添加数据", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/addTypeDataDcv", method = RequestMethod.POST)
	public Object addTypeDataDcv(@RequestBody Map<String, String> map, HttpServletRequest request) {
		TypeData typeData = JSON.parseObject(JSON.toJSONString(map), TypeData.class);
		String cjr = TokenUtils.getTokenUserId();
		TypeData result = typeDataService.addData(typeData, cjr);
		if (result != null) {
			//给接口平台推送CI变化状态
			ciAssociatedFieldConfService.sendCiChangeMsg(typeData.getCi_type_id());
			//修改台账中CINAME
			infoService.updateCiInfoName(typeData.getCi_type_id());
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			String data = result.getData_4();
			result.setData_4(FastDfsUtils.getStorageip() + data);
			PageResult.success("添加成功!");
		} else {
			PageResult.fail("添加失败!");
		}
		return result;
	}

	/**
	 * 修改数据
	 *
	 * @return
	 */
	@OptionLog(desc = "修改数据", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/updateTypeData", method = RequestMethod.POST)
	public boolean updateTypeData(TypeData typeData) throws Exception {
		boolean result = typeDataService.updateData(typeData);
		if (result) {
			//给接口平台推送CI变化状态
			ciAssociatedFieldConfService.sendCiChangeMsg(typeData.getCi_type_id());
			//修改台账中CINAME
			infoService.updateCiInfoName(typeData.getCi_type_id());
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			PageResult.success("修改成功!");
		} else {
			PageResult.fail("修改失败!");
		}
		return result;
	}

	/**
	 * 修改数据DCV
	 *
	 * @return
	 */
	@OptionLog(desc = "修改数据", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/updateTypeDataDcv", method = RequestMethod.POST)
	public boolean updateTypeDataDcv(@RequestBody Map<String, String> map) throws Exception {
		TypeData typeData = JSON.parseObject(JSON.toJSONString(map), TypeData.class);
		boolean result = typeDataService.updateData(typeData);
		if (result) {
			//给接口平台推送CI变化状态
			ciAssociatedFieldConfService.sendCiChangeMsg(typeData.getCi_type_id());
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			PageResult.success("修改成功!");
		} else {
			PageResult.fail("修改失败!");
		}
		return result;
	}

	/**
	 * 删除单条数据
	 *
	 * @return
	 */
	@OptionLog(desc = "删除单条数据", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/deleteTypeData", method = RequestMethod.POST)
	public boolean deleteTypeData(String id) {
		boolean result = typeDataService.deleteData(id);
		if (result) {
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			PageResult.success("删除成功!");
		} else {
			PageResult.fail("删除失败!");
		}
		return result;
	}

	/**
	 * 删除数据支持单条删除和多条删除
	 *
	 * @return
	 */
	@OptionLog(desc = "删除单条数据", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/deleteTypeDatas", method = RequestMethod.POST)
	public boolean deleteTypeDataByids(String id) {
		List<String> list = Arrays.asList(id.split(","));
		boolean result = typeDataService.deleteDataByids(list);
		if (result) {
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			PageResult.success("删除成功!");
		} else {
			PageResult.fail("删除失败!");
		}
		return result;
	}

	/**
	 * 清空所有
	 *
	 * @return
	 */
	@OptionLog(desc = "清空所有", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/deleteDataByTid", method = RequestMethod.POST)
	public boolean deleteDataByTid(String tid) {
		boolean result = typeDataService.deleteDataByTid(tid);
		if (result) {
			//修改CI版本
			ciInfoToInterfaceService.updateCiVesion();
			PageResult.success("清空成功!");
		} else {
			PageResult.fail("清空失败!");
		}
		return result;
	}

	/**
	 * 根据ID返回数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/findDataById", method = RequestMethod.GET)
	public TypeData findDataById(@RequestParam("did") String did,@RequestParam("ciCode") String ciCode) {
		return typeDataService.findDataById(ciCode);

	}

	/**
	 * 根据多个ciId返回ci_type_id
	 *
	 * @return
	 * 将ciId修改为ciCode
	 * @Author ztl
	 */
	@RequestMapping(value = "/findCiTypeIdByCiId", method = RequestMethod.GET)
	public List<Map<String, Object>> findCiTypeIdByCiId(String ciIds,String ciCodes) {
		List<Map<String, Object>> list = typeDataService.findCiTypeIdByCiId(ciIds,ciCodes);
		return list;

	}

	/**
	 * 导出数据
	 */
	@OptionLog(desc = "导出数据", module = "数据模块", writeParam = false, writeResult = false)
	@RequestMapping(value = "/exportExcelData", method = RequestMethod.GET)
	public void exportExcelData(@RequestParam List<String> tid, HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		XSSFWorkbook workbook2007 = new XSSFWorkbook();
		typeDataService.exportExcelData(workbook, workbook2007, tid, response);
		workbook.write(response.getOutputStream());
	}


	/**
	 * 导出数据（配置查询）
	 */
	@OptionLog(desc = "导出数据", module = "数据模块", writeParam = false, writeResult = false)
	@RequestMapping(value = "/exportExcelDataDeploy", method = RequestMethod.GET)
	public void exportExcelDataDeploy(@RequestParam("tid") List<String> tid,@RequestParam("ciPropertyList") List<String> ciPropertyList,HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		XSSFWorkbook workbook2007 = new XSSFWorkbook();
		typeDataService.exportExcelDataDeploy(workbook, workbook2007,ciPropertyList, tid, response);
		workbook.write(response.getOutputStream());
	}

	@RequestMapping(value = "/exportExcelData1", method = RequestMethod.GET)
	public void exportExcelData1(String tid, String tid1, String tid2, HttpServletResponse response)
			throws IOException {
		List<String> list = new ArrayList<String>();
		list.add(tid);
		list.add(tid1);
		list.add(tid2);
		HSSFWorkbook workbook = new HSSFWorkbook();
		XSSFWorkbook workbook2007 = new XSSFWorkbook();
		typeDataService.exportExcelData(workbook, workbook2007, list, response);
		workbook.write(response.getOutputStream());
	}

	/**
	 * 下载模板
	 *
	 * @param tid
	 * @param response
	 */
	@RequestMapping(value = "/downloadTemplet", method = RequestMethod.GET)
	public void downloadTemplet(@RequestParam List<String> tid, HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		XSSFWorkbook workbook2007 = new XSSFWorkbook();
		typeDataService.downloadTemplet(workbook, workbook2007, tid, response);
		workbook.write(response.getOutputStream());
	}

	/**
	 * excel数据导入
	 *
	 * @param url
	 * @param isExcel2003 是否为2003版
	 * @param ci_type_id  大类id
	 * @param request
	 * @return
	 */
	@OptionLog(desc = "excel数据导入", module = "数据模块", writeParam = false, writeResult = false)
	@RequestMapping(value = "/importExcelData", method = RequestMethod.POST)
	public boolean importExcelData(MultipartFile file, String ci_type_id, HttpServletRequest request)
			throws IOException {
		String cjr = TokenUtils.getTokenUserId();

		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isExcel2003 = "xls".equals(suffix);
		InputStream is = file.getInputStream();
		Type type = typeServie.findTypeById(ci_type_id);

		// 获取sheet页与大类名称相同的数据
		List<List<String>> listList = ImportExeclUtil.readType(is, isExcel2003, type);
		if (listList == null) {
			PageResult.fail("无匹配Sheet页!");
			return false;
		}

		List<TypeData> dataList = new ArrayList<TypeData>();
		List<TypeItem> items = typeItemService.findItemByTid(ci_type_id);
		int sortNum = typeItemService.findItemSortByTid(ci_type_id);
		// 获取excel字段，用于和类定义做匹配
		List<String> head = listList.get(0);
		if(items.size()<head.size()) {
			for (int j=0;j<head.size();j++){
				boolean hasHead = true;
				for (TypeItem s : items) {
					if (s.getAttr_name().equals(head.get(j))) {
						hasHead=true;
						break;
					}else{
						hasHead = false;
					}
				}
				if (!hasHead){
					TypeItem typeItem = new TypeItem();
					typeItem.setCi_type_id(ci_type_id);
					typeItem.setAttr_name(head.get(j));
					typeItem.setAttr_std_name(head.get(j));
					typeItem.setAttr_type("字符串");
					typeItem.setIs_major(0);
					typeItem.setIs_requ(0);
					typeItem.setSort(sortNum+j);
					typeItem.setYxbz(1);
					typeItem.setCjr_id(TokenUtils.getTokenUserId());
					TypeItem result = typeItemService.addItem(typeItem);
				}
			}
			items = typeItemService.findItemByTid(ci_type_id);
		}

		// 移除表头
		listList.remove(0);

		for (int i = 0; i < listList.size(); i++) {
			Map<String, String> map = new HashMap<>();
			for (int j = 0; j < listList.get(i).size(); j++) {
				for (int k=0;k<items.size();k++){
					if (head.get(j).equals(items.get(k).getAttr_name())){
						//字段属性是否符合正则
						if(StringUtils.isNotEmpty(listList.get(i).get(j)) && StringUtils.isNotEmpty(items.get(k).getRegexp()) &&
								!RegexpUtil.checkValidity(listList.get(i).get(j), items.get(k).getRegexp())){
							String attr = items.get(k).getAttr_name();
							PageResult.fail("第" + (i + 2) + "行" + "第" + (j + 1) + "列数据,属性" + items.get(k).getAttr_name() + "不符合约束 正则格式，请修正！");
							return false;
						}
						map.put(items.get(k).getMp_ci_item(), listList.get(i).get(j));
					}
				}
			}
			TypeData typeData = JSON.parseObject(JSON.toJSONString(map), TypeData.class);
			dataList.add(typeData);
		}
		// 去excel重复数据
		dataList = dataList.parallelStream().distinct().collect(Collectors.toList());

		Iterator<TypeData> it = dataList.iterator();
		while (it.hasNext()) {
			TypeData x = it.next();
			if (StringUtils.isEmpty(x.getData_1())) {
				it.remove();
			}
		}

		Map<String, Integer> dataMap = new HashMap<>(dataList.size());
		TypeData typeData = null;
		for (int index = 0;index < dataList.size(); index++) {
			typeData = dataList.get(index);
			if(dataMap.containsKey(typeData.getData_1())){
				PageResult.fail("第" + (dataMap.get(typeData.getData_1()) + 2) + "行数据与第" + (index + 2) + "行数据主键重复!");
				return false;
			}
			dataMap.put(typeData.getData_1(), index);
		}
		Map m = typeDataService.importExcelData(ci_type_id, dataList, cjr);
		//给接口平台推送CI变化状态
		ciAssociatedFieldConfService.sendCiChangeMsg(ci_type_id);
		//修改台账中CINAME
		infoService.updateCiInfoName(ci_type_id);
		//修改CI版本
		ciInfoToInterfaceService.updateCiVesion();
		PageResult.success("导入成功!新增" + m.get("tj") + "条,修改" + m.get("xg") + "条,与数据库重复数据" + m.get("cf") + "条,过滤重复编码"
				+ m.get("dataBaseCf") + "条!");
		return true;

	}

	/**
	 * 初始化大类与数据
	 */
	@RequestMapping(value = "/initTypedata", method = RequestMethod.POST)
	public boolean initTypedata(MultipartFile file, String dirId, HttpServletRequest request) throws IOException {

		//获取域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		Map messageMap = new HashMap();
		String cjr = TokenUtils.getTokenUserId();
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isExcel2003 = "xls".equals(suffix);
		InputStream is = file.getInputStream();

		/** 根据版本选择创建Workbook的方式 */
		Workbook wb = null;

		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		List<String> existName =new ArrayList<>();
		int dataBaseCf =0;
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			String name = wb.getSheetAt(i).getSheetName();
			Type type = new Type();

			// 判断当前用户所在机构大类是否已经存在
			boolean sf = typeServie.typeNameExists(name, "0",domainId);
			//判断大类是否在全局存在
			boolean exist = typeServie.typeNameExists(name, "0",null);

			if (!sf&&!exist) {
				type.setCi_type_bm(name);
				type.setCi_type_mc(name);
				type.setCi_type_dir(dirId);
				type.setCjr_id(cjr);
				type = typeServie.addType(type);
			} else if (sf){
				type = typeServie.findByMc(name,domainId);
			}else if (exist){
				existName.add(name);
				continue;
			}

			// 开始导入大类的字段 ===========
			String ci_type_id = type.getId();
			// 获取sheet页与大类名称相同的数据
			List<List<String>> listList = ImportExeclUtil.readDate(wb, type);
			List<TypeItem> list = new ArrayList();

			if (listList.size()>0) {
				for (int j = 0; j < listList.get(0).size(); j++) {
					TypeItem typeItem = new TypeItem();
					if (j == 0 && typeItemService.findPK(ci_type_id) == null) {
						typeItem.setIs_requ(1);
						typeItem.setIs_major(1);
					}
					typeItem.setAttr_name(listList.get(0).get(j));
					typeItem.setCi_type_id(ci_type_id);
					typeItem.setCjr_id(cjr);
					typeItem.setAttr_type("字符串");
					typeItem.setSort(j+1);
					// 如果字段不重复
					if (!typeItemService.itemNameExists(typeItem.getAttr_name(), typeItem.getCi_type_id())) {
						list.add(typeItem);
					}

				}
				List<TypeItem> oldItemList = typeItemService.findItemByTid(ci_type_id);
				int allColumn = list.size();
				if(oldItemList != null){
					allColumn += oldItemList.size();
				}
				if(allColumn >199){
					PageResult.fail("导入失败，类定义总数不能多于199!");
					return false;
				}

				for (TypeItem typeItem : list) {
					typeItemService.addItem(typeItem);
				}
				// 字段导入结束，开始导入数据=============

				List<TypeData> dataList = new ArrayList<TypeData>();
				List<TypeItem> items = typeItemService.findItemByTid(ci_type_id);
				// 获取excel字段，用于和类定义做匹配
				List<String> head = listList.get(0);

				if(head.size() < items.size()){
					PageResult.fail("大类" + name + "类定义与excel字段不匹配!");
					return false;
				}

				for (int j = 0; j < head.size(); j++) {

					if (!head.get(j).equals(items.get(j).getAttr_name())) {
						PageResult.fail("大类" + name + "类定义与excel字段不匹配!");
						return false;
					}
				}

				// 移除表头
				listList.remove(0);

				for (int k = 0; k < listList.size(); k++) {
					for (int j=0;j<items.size();j++){
						if (head.get(j).equals(items.get(j).getAttr_name())){
							//字段属性是否符合正则
							if(StringUtils.isNotEmpty(listList.get(k).get(j)) && StringUtils.isNotEmpty(items.get(j).getRegexp()) &&
									!RegexpUtil.checkValidity(listList.get(k).get(j), items.get(j).getRegexp())){
								String attrName = items.get(j).getAttr_name();
								PageResult.fail("第" + (k + 2) + "行" + "第" + (j + 1) + "列数据,属性" + attrName + "不符合约束正则格式，请修正！");
								return false;
							}
						}
					}
					Map<String, String> map = new HashMap<>();
					for (int j = 0; j < listList.get(k).size(); j++) {
						map.put("DATA_" + (j + 1), listList.get(k).get(j));
					}
					TypeData typeData = JSON.parseObject(JSON.toJSONString(map), TypeData.class);
					dataList.add(typeData);
				}
				// 去excel重复数据
				dataList = dataList.parallelStream().distinct().collect(Collectors.toList());

				Iterator<TypeData> it = dataList.iterator();
				while (it.hasNext()) {
					TypeData x = it.next();
					if (StringUtils.isEmpty(x.getData_1())) {
						it.remove();
					}
				}

				Map<String, Integer> dataMap = new HashMap<>(dataList.size());
				TypeData typeData = null;
				for (int index = 0;index < dataList.size(); index++) {
					typeData = dataList.get(index);
					if(dataMap.containsKey(typeData.getData_1())){
						PageResult.fail("大类" + name + "第" + (dataMap.get(typeData.getData_1()) + 2) + "行数据与第" + (index + 2) + "行数据主键重复!");
						return false;
					}
					dataMap.put(typeData.getData_1(), index);
				}

				//根据ci编码找到记录
				List<Map<String, String>> ciList = null;
				if(dataMap.size() > 0){
					ciList = infoService.getCiList(new ArrayList<>(dataMap.keySet()),domainId);
				}
				if(ciList != null && ciList.size() > 0){
					List<String> repeatCiBMList = ciList.stream().filter(item->!StringUtils.equals(ci_type_id,item.get("ciTypeId").toString())).map(item->item.get("ciCode")).collect(Collectors.toList());
					if(repeatCiBMList != null && repeatCiBMList.size() > 0){
						PageResult.fail(String.format("大类【%s】主键【%s】重复!",name,String.join("、", repeatCiBMList)));
						return false;
					}
				}
				Map m = typeDataService.importExcelData(ci_type_id, dataList, cjr);
				dataBaseCf+=Integer.parseInt(String.valueOf(m.get("dataBaseCf")));
				messageMap.put(name, m);
			}

		}
		//修改CI版本
		ciInfoToInterfaceService.updateCiVesion();
		if (existName!=null && existName.size()>0){
			if(dataBaseCf>0){
				PageResult.fail("其他机构中存在相同大类："+ Joiner.on(",").join(existName)+";其中"+ dataBaseCf+"条数据已导入过或在其他机构中已存在！");
			}else{
				PageResult.fail("其他机构中存在相同大类："+ Joiner.on(",").join(existName));
			}
		}else{
			if(dataBaseCf>0){
				PageResult.fail("初始化大类成功 !其中"+ dataBaseCf+"条数据已导入过或在其他机构中已存在！");
			}
			PageResult.success("初始化大类成功 !");
		}
		return true;
	}

	/**
	 * 判断主键数据是否存在
	 */
	@OptionLog(desc = "判断主键数据是否存在", module = "数据模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/pkExists", method = RequestMethod.GET)
	public boolean pkExists(String pkName, String ci_type_id) {

		boolean result = typeDataService.pkExists(pkName, ci_type_id);
		if (result) {
			PageResult.success();
		} else {
			PageResult.fail();
		}
		return result;
	}

	/**
	 * 模糊查询配置信息
	 *
	 * @param pageCount 页面记录数
	 * @param pageIndex 页码
	 * @param tidList   CI分类数组
	 * @return
	 */
	@RequestMapping(value = "/findConfigInfo", method = RequestMethod.GET)
	public PageResult findConfigInfo(String pageCount, String pageIndex, String[] tidList, String[] ciPropertyList) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		Map map = new HashMap();
		map.put("pageCount", pageCount);
		map.put("pageIndex", pageIndex);
		map.put("tidList", tidList);
		map.put("ciPropertyList", ciPropertyList);
		map.put("domainId",domainId);
		List<LinkedHashMap> configList  = typeDataService.findConfigInfo(map);
		return DataUtils.returnPr(typeDataService.findConfigInfoCount(map), configList);
	}
	/**
	 * CI模糊查询、配置信息展示(私有)
	 *
	 * @param pageCount 页面记录数
	 * @param pageIndex 页码
	 * @param tidList   CI分类数组
	 * @return
	 */
	@RequestMapping(value = "/findConfigItem", method = RequestMethod.GET)
	public PageResult findConfigItem(String pageCount, String pageIndex, String[] tidList, String[] ciPropertyList) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<String> typeIdList = new ArrayList<String>();
		if (!"sysadmin".equals(TokenUtils.getTokenUserName())){
			List<Map> roleDataList = roleDataService.findRoleHighDataList();
			for (Map map : roleDataList){
				boolean searchAuth = (boolean) map.get("searchAuth");
				if (!searchAuth){
					typeIdList.add(map.get("dataId").toString());
				}
			}
		}
		Map map = new HashMap();
		map.put("pageCount", pageCount);
		map.put("pageIndex", pageIndex);
		map.put("tidList", tidList);
		map.put("ciPropertyList", ciPropertyList);
		map.put("domainId",domainId);
		map.put("typeIdList", typeIdList);
		List<LinkedHashMap> configList  = typeDataService.findConfigInfo(map);
		return DataUtils.returnPr(typeDataService.findConfigInfoCount(map), configList);
	}
	/**
	 * 根据ciBm模糊查询ciInfo
	 *
	 * @param pageNum 当前页
	 * @param pageSize 每页显示条数
	 * @param ciBm   ci编码
	 * @return
	 */
	@RequestMapping(value = "/findConfigItemByCiCode", method = RequestMethod.GET)
	public PageResult findConfigItemByCiCode(String ciName) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		MyPagUtile.startPage();
		List<Map<String, Object>> list=typeDataService.findConfigItemByCiCode(ciName,domainId);
		PageResult pageResult=MyPagUtile.getPageResult(list);
		return pageResult;
	}

	/**
	 * CI模糊查询、配置信息展示（驼峰）
	 *
	 * @param pageCount 页面记录数
	 * @param pageIndex 页码
	 * @param tidList   CI分类数组
	 * @return
	 */
	@RequestMapping(value = "/findConfigInfoHump", method = RequestMethod.GET)
	public PageResult findConfigInfoHump(String pageCount, String pageIndex, String[] tidList, String[] ciPropertyListCI,String kpiId,String kpiName) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenOrgDomainId();
		List<String> kpiIdsList=new ArrayList<String>();
		if(kpiName!=null && !"".equals(kpiName)) {
			List<String> kpiNameList=Arrays.asList(kpiName.split(","));
			List<Map<String, Object>> listData=ciKpiService.getKpiInfoByKpiIds(kpiNameList);
			if(listData!=null && listData.size()>0) {
				for(Map<String, Object> map:listData) {
					String kpiIdStr=(String) map.get("id");
					kpiIdsList.add(kpiIdStr);
				}
			}
			if (tidList == null || tidList.length == 0) {
				List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
				IomCiKpiTypeExample example=new IomCiKpiTypeExample();
				example.createCriteria().andYxbzEqualTo(1).andKpiIdIn(kpiIdsList);
				List<IomCiKpiType> list=iomCiKpiTypeMapper.selectByExample(example);
				List<String> ciTypeIdsList=new ArrayList<String>();
				if(list!=null && list.size()>0) {
					for(IomCiKpiType iomCiKpiType :list) {
						String ciTypeId=iomCiKpiType.getObjId();
						ciTypeIdsList.add(ciTypeId);
					}
				}

				IomCiTypeExample exampleCiType=new IomCiTypeExample();
				if (ciTypeIdsList == null ||ciTypeIdsList.size() == 0) {
					return null;
				}
				exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList);
				List<IomCiType> list2=iomCiTypeMapper.selectByExample(exampleCiType);
				if (list2 == null || list2.size() == 0) {
					return null;
				}
				String[] ss = new String[list2.size()];
				for (int i = 0; i < list2.size(); i++) {
					ss[i] = list2.get(i).getId();
				}
				tidList = ss;
			}
			Map map1 = new HashMap();
			map1.put("pageCount", pageCount);
			map1.put("pageIndex", pageIndex);
			map1.put("tidList", tidList);
			map1.put("ciPropertyList", ciPropertyListCI);
			map1.put("domainId", domainId);
			List<LinkedHashMap> configList = new ArrayList<LinkedHashMap>();
			configList = typeDataService.findConfigInfoHump(map1);
			return DataUtils.returnPr(typeDataService.findConfigInfoCount(map1), configList);
		}else {
			Map map = new HashMap();
			map.put("pageCount", pageCount);
			map.put("pageIndex", pageIndex);
			map.put("tidList", tidList);
			map.put("ciPropertyList", ciPropertyListCI);
			map.put("domainId",domainId);
			List<LinkedHashMap> configList  = typeDataService.findConfigInfoHump(map);
			return DataUtils.returnPr(typeDataService.findConfigInfoCount(map), configList);
		}	
	}
	/**
	 * CI模糊查询、配置信息展示（驼峰）
	 * 注：无分页
	 * @param tidList   CI分类数组
	 * @return
	 */
	@RequestMapping(value = "/findConfigInfoNoPageHump", method = RequestMethod.GET)
	public PageResult findConfigInfoNoPageHump(String[] tidList, String[] ciPropertyListCI) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		Map map = new HashMap();
		map.put("tidList", tidList);
		map.put("ciPropertyList", ciPropertyListCI);
		map.put("domainId",domainId);
		List<LinkedHashMap> configList = typeDataService.findConfigInfoNoPageHump(map);
		return DataUtils.returnPr(typeDataService.findConfigInfoCount(map), configList);
	}
	/**
	 * CI模糊查询、配置信息展示（驼峰）
	 *
	 * @param pageCount 页面记录数
	 * @param pageIndex 页码
	 * @param tidList   CI分类数组
	 * @return
	 */
	@RequestMapping(value = "/findConfigInfoHumpKpi", method = RequestMethod.GET)
	public PageResult findConfigInfoHumpKpi(String pageSize, String pageNum, String[] tidList, String[] ciPropertyListCI,String kpiId,String kpiName) {
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		List<String> kpiIdsList=new ArrayList<String>();
		if(kpiName!=null && !"".equals(kpiName)) {
			List<String> kpiNameList=Arrays.asList(kpiName.split(","));
			List<Map<String, Object>> list=ciKpiService.getKpiInfoByKpiIds(kpiNameList);
			if(list!=null && list.size()>0) {
				for(Map<String, Object> map:list) {
					String kpiIdStr=(String) map.get("id");
					kpiIdsList.add(kpiIdStr);
				}
			}
		}	
		if (tidList == null || tidList.length == 0) {
			List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
			IomCiKpiTypeExample example=new IomCiKpiTypeExample();
			example.createCriteria().andYxbzEqualTo(1).andKpiIdIn(kpiIdsList);
			List<IomCiKpiType> list=iomCiKpiTypeMapper.selectByExample(example);
			List<String> ciTypeIdsList=new ArrayList<String>();
			if(list!=null && list.size()>0) {
				for(IomCiKpiType iomCiKpiType :list) {
					String ciTypeId=iomCiKpiType.getObjId();
					ciTypeIdsList.add(ciTypeId);
				}
			}

			IomCiTypeExample exampleCiType=new IomCiTypeExample();
			if (ciTypeIdsList == null ||ciTypeIdsList.size() == 0) {
				return null;
			}
			exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList);
			List<IomCiType> list2=iomCiTypeMapper.selectByExample(exampleCiType);
			if (list2 == null || list2.size() == 0) {
				return null;
			}
			String[] ss = new String[list2.size()];
			for (int i = 0; i < list2.size(); i++) {
				ss[i] = list2.get(i).getId();
			}
			tidList = ss;
		}
		Map map1 = new HashMap();
		map1.put("pageCount", pageSize);
		map1.put("pageIndex", pageNum);
		map1.put("tidList", tidList);
		map1.put("ciPropertyList", ciPropertyListCI);
		map1.put("domainId", domainId);
		List<LinkedHashMap> configList = new ArrayList<LinkedHashMap>();
		configList = typeDataService.findConfigInfoHump(map1);
		return DataUtils.returnPr(typeDataService.findConfigInfoCount(map1), configList);
	}
	/**
	 * 根据CIid,关系CI大类查询配置信息
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/findConfigInfoByCIIdAndRelCIType", method = RequestMethod.GET)
	public Map findConfigInfoByCIIdAndRelCIType(@RequestParam Map map) {
		Map dataMap  = typeItemService.getCiInfo(map);
		return dataMap;
	}

	/**
	 * 根据CIid查询所有关系ci大类
	 *
	 * @param cid
	 * @return
	 */
	@RequestMapping(value = "/findRelCITypeByCIId", method = RequestMethod.GET)
	public List<Map> findRelCITypeByCIId(String cid) {
		List<Map> list =  typeDataService.findRelCITypeByCIId(cid);
		return list;
	}

	/**
	 * 根据CIid,关系CI大类查询关系数据列表
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/findRelCIDataByCIIdAndRelCIType", method = RequestMethod.GET)
	public List<Map> findRel(@RequestParam Map map) {
		List<Map> list = typeDataService.findRelCIDataByCIIdAndCIType(map);
		return list;
	}

	@RequestMapping(value = "/findResult", method = RequestMethod.POST)
	public List getResult() {
		Param parameter = new Param();
		parameter.setSql("select top 20 * from tbl_trap_data_critical");
		parameter.setDbtype("sqlserver");
		return dfileService.getResult(parameter);
	}

	/**
	 * 分页查询ci列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/findCiListPage", method = RequestMethod.POST)
	public PageResult findCiList(@RequestParam Map map) {
		int count = infoService.findCIInfoListCount(null);
		List list = infoService.findCIInfoList(map);
		List resultList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map m = JSON.parseObject(JSON.toJSONString(list.get(i)), Map.class);
			List l = new ArrayList();
			l.add(m.get("ciCode"));
			m.put("data", typeItemService.findCiList(l).get(0));
			resultList.add(m);
		}
		return DataUtils.returnPr(count, resultList);
	}

	/**
	 * 根据CIid,关系CI大类查询配置信息 分页 维护期设置使用
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/findCiListMaintain", method = RequestMethod.POST)
	public PageResult findCiListMaintain(String pageNum, String pageSize, String searchName) {
		int count = 0;
		String domainId = TokenUtils.getTokenOrgDomainId();
		MyPagUtile.startPage(pageNum, pageSize);
		List<Info> infos = infoService.findCIInfoListMaintain(searchName,domainId);
		PageInfo<Info> pageInfo = new PageInfo(infos);
		count = Integer.parseInt(pageInfo.getTotal() + "");
		List resultList = new ArrayList();
		for (int i = 0; i < pageInfo.getList().size(); i++) {
			Map m = JSON.parseObject(JSON.toJSONString(pageInfo.getList().get(i)), Map.class);
			List l = new ArrayList();
			l.add(m.get("ci_code"));
			m.put("data", typeItemService.findCiList(l).get(0));
			resultList.add(m);
		}

		return DataUtils.returnPr(count, resultList);

	}

	@RequestMapping(value = "/findckck", method = RequestMethod.GET)
	public void aaa() {
		List list = new ArrayList();

		for (int i = 0; i < 5; i++) {
			list.add(SeqUtil.nextId() + "");
		}

	}


	
	@RequestMapping(value = "/findEmv", method = RequestMethod.GET)
	 public Map findEmv() {

	  if (num >= 99) {
	   num = 0;
	  } else {
	   num += 1;
	  }
	  Map map = typeDataService.findEmv().get(num);
	  return map;
	 }

	 @RequestMapping(value = "/findPmv", method = RequestMethod.GET)
	 public Map findPmv() {
	  if (pmvnum >= 99) {
	   pmvnum = 0;
	  } else {
	   pmvnum += 1;
	  }
	  
	  Map map = typeDataService.findPmv().get(pmvnum);
	  List<IomCiKpi> kpiName = typeItemService.findKpi();
	  return map;
	 }

	@RequestMapping(value = "findDataByCiIDAndAttrId")
	public String findDataByCiIDAndAttrId(String ciId, String attrId) {
		if (ciId != null && attrId != null) {
			TypeItem attr = typeItemDao.findTypeItem(attrId);
			return typeDataService.findDataByCiIDAndAttrId(ciId, attr.getMp_ci_item());
		}
		return null;
	}

	/**
	 * 根据CICODE和属性ID获取值
	 *
	 * @param ciCode
	 * @param attrId
	 * @return
	 */
	@RequestMapping(value = "findDataByCiCodeAndAttrId")
	public String findDataByCiCodeAndAttrId(String ciCode, String attrId,String ciType) {
		if (ciCode != null && attrId != null) {
			TypeItem attr = typeItemDao.findTypeItem(attrId);
			return typeDataService.findDataByCiCodeAndAttrId(ciCode, attr.getMp_ci_item(),ciType);
		}
		return null;
	}

	/**
	 * 通过值获取ci对象和大类
	 *
	 * @param maps
	 * @return
	 */
	@RequestMapping("findDataByVal")
	public PageResult findDataByVal(List<Map> maps) {
		return DataUtils.returnPr(typeDataService.findDataByVal(maps));
	}

	/**
	 * 查询ci上下几层的关联ci
	 *
	 * @param ciId
	 * @param upNum
	 * @param downNum
	 * @param relIds
	 * @param typeIds
	 * @return
	 */
	@GetMapping("/ciDataRel/getCiRels")
	public Object getCiRels(String ciId, Integer upNum, Integer downNum, String relIds, String typeIds,String ciCode) {
		if (upNum == null) {
			upNum = 5;
		}

		if (downNum == null) {
			downNum = 5;
		}

		return typeDataService.getCiRels(ciId, upNum, downNum, relIds, typeIds,ciCode);
	}
	
	/**
	 * 根据多个ciCode查询imageFullName
	 *
	 * @param ciCodes
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/getImageFullNameByCiCodes", method = RequestMethod.POST)
	public List<Map<String,Object>> getImageFullNameByCiCodes(String ciCodes) {
		List<String> ciCodeList=Arrays.asList(ciCodes.split(","));
		return typeDataService.getImageFullNameByCiCodes(ciCodeList);
	}
	
	/**
	 * 根据层级查询大类Id和关系Id
	 *
	 * @param ciId
	 * @param upNum
	 * @param downNum
	 * @param 
	 * @param 
	 * @return
	 */
	@GetMapping("/ciDataRel/getRelIdAndCiTypeIdByHierarchy")
	public Object getRelIdAndCiTypeIdByHierarchy(String ciId, Integer upNum, Integer downNum,String ciCode) {
		if (upNum == null) {
			upNum = 5;
		}

		if (downNum == null) {
			downNum = 5;
		}

		return typeDataService.getRelIdAndCiTypeIdByHierarchy(ciId, upNum, downNum,ciCode);
	}

	/**
	 * 获取向上的999层关系
	 * 
	 * @param ciId
	 * @return
	 */
	@GetMapping("/ciDataRel/getUpCiRels")
	public Map<String, Object> getUpCiRels(String ciId,String ciCode) {
		return typeDataService.getUpCiRels(ciId,ciCode);
	}

	/**
	 * 获取向下的999层关系
	 * 
	 * @param ciId
	 * @return
	 */
	@GetMapping("/ciDataRel/getDownCiRels")
	public Map<String, Object> getDownCiRels(String ciId,String ciCode) {
		return typeDataService.getDownCiRels(ciId,ciCode);
	}

	/**
	 * 查询根因分析(优化)
	 * 
	 * @param ciId
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/queryRootCauseAnalysisByCiCode", method = RequestMethod.GET)
	public Map<String, Object> queryRootCauseAnalysisByCiCodeOptimize(String ciId,@RequestParam String ciCode) {
		String domainId=TokenUtils.getTokenOrgDomainId();
		//根据ciCode获取大类信息
		TypeData typeData=typeDataService.findDataById(ciCode);
		//提取大类ID
		String ciTypeId=typeData.getCi_type_id();
		List<String> ciTypeIdList=new ArrayList<String>();
		ciTypeIdList.add(ciTypeId);
		Map<String,Set<String>> ciCodeMap=new HashMap<String,Set<String>>();
		Set<String> ciCodeSet=new HashSet<String>();
		ciCodeSet.add(ciCode);
		ciCodeMap.put(ciTypeId, ciCodeSet);
		//最后一个参数这里传空字符串是因为RCA那里传1，RCA那里只循环一层
		List<Map<String,Object>> list=typeDataService.queryRootCauseAnalysisByCiCodeOptimize(ciTypeId,ciCode,ciTypeIdList,ciCodeMap,new ArrayList<Map<String,Object>>(),new HashSet<String>(),"",domainId);
		//去重CI关系数据
		List<Map<String, Object>> relList=filterListByCiDataRelId(list);
		//存储sourceType对应targetType
		Map<String,String> mapData=new HashMap<String,String>();
		List<Map<String,Object>> listRel=new ArrayList<Map<String,Object>>();
		Set<String> ciTypeBms=new HashSet<String>();
        if(relList!=null && relList.size()>0) {
        	for(Map<String, Object> map:relList) {
        		String sourceTypeBm=(String) map.get("sourceTypeBm");
        		String targetTypeBm=(String) map.get("targetTypeBm");
        		ciTypeBms.add(sourceTypeBm);
        		ciTypeBms.add(targetTypeBm);
        		boolean flag=mapData.containsKey(sourceTypeBm+"-"+targetTypeBm);
        		if(!flag) {
        			mapData.put(sourceTypeBm+"-"+targetTypeBm, sourceTypeBm+"-"+targetTypeBm);
        			listRel.add(map);
        		}
        	}
        }
        Set<String> ciTypeNameSet=new HashSet<String>();
    	//找出起始大类
        if(listRel!=null && listRel.size()>0) {
        	for(Map<String,Object> map:listRel) {
        		String sourceTypeBm=(String) map.get("sourceTypeBm");
        		boolean flag=true;
        		for(Map<String,Object> map1:listRel) {
        			String targetTypeBm=(String) map1.get("targetTypeBm");
        			if(sourceTypeBm.equals(targetTypeBm)) {
        				flag=false;
        				break;
        			}
        		}
        		if(flag) {
        			ciTypeNameSet.add(sourceTypeBm);
        		}
        		
        	}
        }
        //途径大类节点
        Map<String,Object> dataMap=new HashMap<String,Object>();
        Iterator it = ciTypeNameSet.iterator();
        while (it.hasNext()) {
             String strCiTypeBm = (String) it.next();
             Set<String> ciTypeNameSet1=new HashSet<String>();
             ciTypeNameSet1.add(strCiTypeBm);
             Map<String,Object> map=getQueue(strCiTypeBm,ciTypeNameSet1,listRel,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>(),"1",0);
             Iterator<Map.Entry<String, Object>> entries  = map.entrySet().iterator();
             while(entries.hasNext()){
                 Map.Entry<String, Object> entry = entries.next();
                 String key = entry.getKey();
                 Object value = entry.getValue();
                 List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
                 if (value instanceof ArrayList<?>) {
                     for (Object o : (List<?>) value) {
                    	 listData.add(Map.class.cast(o));
                     }
                 }
                 listData=queryDownClassInfoCombination(strCiTypeBm,listData,new ArrayList<Map<String,Object>>());
                 dataMap.put(key, listData);
             }
        }
        //途径大类验证与关系数据相匹配，排除那些没有匹配关系数据的路径
        Map<String,Object> dataMapRe=new HashMap<String,Object>();
        Iterator<Map.Entry<String, Object>> entries1  = dataMap.entrySet().iterator();
        while(entries1.hasNext()){
            Map.Entry<String, Object> entry = entries1.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
            if (value instanceof ArrayList<?>) {
                for (Object o : (List<?>) value) {
               	 listData.add(Map.class.cast(o));
                }
            }
            boolean flag=true;
            Set<String> setCi=new HashSet<>();
            for(int i=listData.size()-1;i>0;i--) {
            	Map<String,Object> map=listData.get(i);
            	String sourceTypeBm=(String) map.get("sourceTypeBm");
            	String targetTypeBm=(String) map.get("targetTypeBm");
            	if(i==listData.size()-1) {
            		for(Map<String, Object> mapRel:relList) {
                		String sourceTypeBm1=(String) mapRel.get("sourceTypeBm");
                    	String targetTypeBm1=(String) mapRel.get("targetTypeBm");
                    	String sourceCiCode=(String) mapRel.get("sourceCiCode");
                    	if(sourceTypeBm.equals(sourceTypeBm1) && targetTypeBm.equals(targetTypeBm1)) {
                    		setCi.add(sourceCiCode);
                    	}
                	}
            	}else {
            		if(setCi==null || setCi.size()==0) {
            			flag=false;
            			break;
            		}
            		Set<String> setCiCode=new HashSet<>();
            		for(Map<String, Object> mapRel:relList) {
                		String sourceTypeBm1=(String) mapRel.get("sourceTypeBm");
                    	String targetTypeBm1=(String) mapRel.get("targetTypeBm");
                    	String sourceCiCode=(String) mapRel.get("sourceCiCode");
                    	String targetCiCode=(String) mapRel.get("targetCiCode");
                    	boolean fl=setCi.contains(targetCiCode);
                    	if(sourceTypeBm.equals(sourceTypeBm1) && targetTypeBm.equals(targetTypeBm1) && fl) {
                    		setCiCode.add(sourceCiCode);
                    	}
                	}
            		setCi.clear();
            		setCi.addAll(setCiCode);
            	}
            	
            }
            if(flag) {
            	dataMapRe.put(key, listData);
            }         
        }
        dataMap=dataMapRe;       
        //汇总每个大类CI数量
        Map<String,Object> ciCodeNumMap=new HashMap<String,Object>();
        Set<String> allCiCodesSet=new HashSet<String>();
        Set<String> allCiTypeIdsSet=new HashSet<String>();
        Set<Map<String, Object>> setRelInfo=new HashSet<Map<String,Object>>(); 
        for(String strCiType:ciTypeBms) {
        	Set<String> ciCodes=new HashSet<String>();
        	Set<String> ciNames=new HashSet<String>();
        	for(Map<String, Object> map:relList) {
        		String sourceTypeBm=(String) map.get("sourceTypeBm");
        		String sourceTypeId=(String) map.get("sourceTypeId");
        		String targetTypeBm=(String) map.get("targetTypeBm");
        		String targetTypeId=(String) map.get("targetTypeId");
        		String sourceCiBm=(String) map.get("sourceCiBm");
        		String sourceCiName=(String) map.get("sourceCiName");
        		String targetCiBm=(String) map.get("targetCiBm");
        		String targetCiName=(String) map.get("targetCiName");
        		String relId=(String) map.get("relId");
	    		String relName=(String) map.get("relName");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);    	
        		allCiCodesSet.add(sourceCiBm.trim());
        		allCiCodesSet.add(targetCiBm.trim());
        		allCiTypeIdsSet.add(sourceTypeId);
        		allCiTypeIdsSet.add(targetTypeId);
        		if(strCiType.equals(sourceTypeBm)) {
        			ciCodes.add(sourceCiBm.trim());
        			ciNames.add(sourceCiName);
        		}
        		if(strCiType.equals(targetTypeBm)) {
        			ciCodes.add(targetCiBm.trim());
        			ciNames.add(targetCiName);
        		}
            }
        	Map<String,Object> mapCiCodeCiName=new HashMap<String,Object>();
        	mapCiCodeCiName.put("ciCode", ciCodes);
        	mapCiCodeCiName.put("ciName", ciNames);
        	ciCodeNumMap.put(strCiType, mapCiCodeCiName);
        }
        
        //查询出所有CI信息
        List<String> listCiCode = new ArrayList<String>(allCiCodesSet);
	    List<Map<String, Object>> listDatas=new ArrayList<Map<String,Object>>();
	    if(listCiCode!=null && listCiCode.size()>0) {
	    	listDatas=typeDao.findClassInfoAndCiInfoByCiIds(listCiCode);
	    }
	    
	    //查询大类信息并且汇总大类关系信息---start
	    List<String> allCiTypeIdsList = new ArrayList<String>(allCiTypeIdsSet);
	    List<Map<String, Object>> allClassInfoList=new ArrayList<Map<String,Object>>();
	    if(allCiTypeIdsList!=null && allCiTypeIdsList.size()>0) {
	    	allClassInfoList=typeDao.findClassInfoByCiTypeIds(allCiTypeIdsList);
	    }	    
        
        //汇总节点数据
        Map<String,Object> returnMap=new HashMap<String,Object>();
        List<Map<String,Object>> friendPathsList=new ArrayList<Map<String,Object>>();
        Iterator<Map.Entry<String, Object>> entries  = dataMap.entrySet().iterator();
        while(entries.hasNext()){
        	Map<String,Object> mapDataNode=new HashMap<String,Object>();
            Map.Entry<String, Object> entry = entries.next();
            List<Map<String,Object>> value = (List<Map<String, Object>>) entry.getValue();
            if(value!=null && value.size()>0) {
            	List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
            	for(int i=0;i<value.size();i++) {
            		
            		Map<String,Object> map=value.get(i);
              		String sourceTypeBm=(String) map.get("sourceTypeBm");
            		String sourceTypeId=(String) map.get("sourceTypeId");
            		String targetTypeBm=(String) map.get("targetTypeBm");
            		String targetTypeId=(String) map.get("targetTypeId");
            		if(i==0) {
            			Map<String,Object> mapItem1=new HashMap<String,Object>();
            			Map<String,Object> mapItem2=new HashMap<String,Object>();
            			boolean flag1=ciCodeNumMap.containsKey(sourceTypeBm);
                		if(flag1) {
                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(sourceTypeBm);
                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
                			mapItem1.put("className", sourceTypeBm);
                			mapItem1.put("ciClassId", sourceTypeId);
                			mapItem1.put("ciCodes", ciCodes);
                			mapItem1.put("ciNames", ciNames);
                			mapItem1.put("ciCount", ciCodes.size());
                		}
                		boolean flag2=ciCodeNumMap.containsKey(targetTypeBm);
                		if(flag2) {
                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(targetTypeBm);
                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
                			mapItem2.put("className", targetTypeBm);
                			mapItem2.put("ciClassId", targetTypeId);
                			mapItem2.put("ciCodes", ciCodes);
                			mapItem2.put("ciNames", ciNames);
                			mapItem2.put("ciCount", ciCodes.size());
                		}
                		listData.add(mapItem1);
                		listData.add(mapItem2);
            		}else {
            			Map<String,Object> mapItem1=new HashMap<String,Object>();
            			boolean flag2=ciCodeNumMap.containsKey(targetTypeBm);
                		if(flag2) {
                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(targetTypeBm);
                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
                			mapItem1.put("className", targetTypeBm);
                			mapItem1.put("ciClassId", targetTypeId);
                			mapItem1.put("ciCodes", ciCodes);
                			mapItem1.put("ciNames", ciNames);
                			mapItem1.put("ciCount", ciCodes.size());
                		}
                		listData.add(mapItem1);
            		}
            		
            	}
            	mapDataNode.put("nodes", listData);
            }
            friendPathsList.add(mapDataNode);
        }
        List<Map<String, Object>> classInfoAndRelInfoList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:allClassInfoList) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    for(Map<String, Object> map:setRelInfo) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    returnMap.put("ciClassInfos", classInfoAndRelInfoList);
        returnMap.put("friendPaths", friendPathsList);
        returnMap.put("ciRltLines", relList);
	    if(listDatas!=null && listDatas.size()>0) {
	    	for(Map<String, Object> map:listDatas) {
		    	String ciIdStr=(String) map.get("ciId");
		    	String ciBmStr=(String) map.get("ciCode");
		    	if(ciCode.equals(ciBmStr)) {
		    		returnMap.put("startCi", map);
		    	}
		    }
	    }else {
	    	returnMap.put("startCi", new HashedMap());
	    }
	    for(Map<String, Object> map:listDatas) {
	    	String ciCodeStr=(String) map.get("ciCode");
	    	map.put("ciCode", ciCodeStr.trim());
	    }
	    returnMap.put("ciNodes", listDatas);
		return returnMap;
	}
	
	/**
	 * 查询影响分析(优化)
	 * 
	 * @param ciId
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/queryImpactAnalysisByCiCode", method = RequestMethod.GET)
	public Object queryImpactAnalysisByCiCodeOptimize(String ciId,@RequestParam String ciCode) {
		String domainId=TokenUtils.getTokenOrgDomainId();
		//根据ciCode获取大类信息
		TypeData typeData=typeDataService.findDataById(ciCode);
		//提取大类ID
		String ciTypeId=typeData.getCi_type_id();
		List<String> ciTypeIdList=new ArrayList<String>();
		ciTypeIdList.add(ciTypeId);
		Map<String,Set<String>> ciCodeMap=new HashMap<String,Set<String>>();
		Set<String> ciCodeSet=new HashSet<String>();
		ciCodeSet.add(ciCode);
		ciCodeMap.put(ciTypeId, ciCodeSet);
		List<Map<String,Object>> list=typeDataService.queryImpactAnalysisByCiCodeOptimize(ciTypeId,ciCode,ciTypeIdList,ciCodeMap,new ArrayList<Map<String,Object>>(),new HashSet<String>(),domainId);
		//去重CI关系数据
		List<Map<String, Object>> relList=filterListByCiDataRelId(list);
		//存储sourceType对应targetType
		Map<String,String> mapData=new HashMap<String,String>();
		List<Map<String,Object>> listRel=new ArrayList<Map<String,Object>>();
		Set<String> ciTypeBms=new HashSet<String>();
        if(relList!=null && relList.size()>0) {
        	for(Map<String, Object> map:relList) {
        		String sourceTypeBm=(String) map.get("sourceTypeBm");
        		String targetTypeBm=(String) map.get("targetTypeBm");
        		ciTypeBms.add(sourceTypeBm);
        		ciTypeBms.add(targetTypeBm);
        		boolean flag=mapData.containsKey(sourceTypeBm+"-"+targetTypeBm);
        		if(!flag) {
        			mapData.put(sourceTypeBm+"-"+targetTypeBm, sourceTypeBm+"-"+targetTypeBm);
        			listRel.add(map);
        		}
        	}
        }
        Set<String> ciTypeNameSet=new HashSet<String>();
    	//找出起始大类
        if(listRel!=null && listRel.size()>0) {
        	for(Map<String,Object> map:listRel) {
        		String sourceTypeBm=(String) map.get("sourceTypeBm");
        		boolean flag=true;
        		for(Map<String,Object> map1:listRel) {
        			String targetTypeBm=(String) map1.get("targetTypeBm");
        			if(sourceTypeBm.equals(targetTypeBm)) {
        				flag=false;
        				break;
        			}
        		}
        		if(flag) {
        			ciTypeNameSet.add(sourceTypeBm);
        		}
        		
        	}
        }
        //途径大类节点
        Map<String,Object> dataMap=new HashMap<String,Object>();
        Iterator it = ciTypeNameSet.iterator();
        while (it.hasNext()) {
             String strCiTypeBm = (String) it.next();
             Set<String> ciTypeNameSet1=new HashSet<String>();
             ciTypeNameSet1.add(strCiTypeBm);
             Map<String,Object> map=getQueue(strCiTypeBm,ciTypeNameSet1,listRel,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>(),"1",0);
             Iterator<Map.Entry<String, Object>> entries  = map.entrySet().iterator();
             while(entries.hasNext()){
                 Map.Entry<String, Object> entry = entries.next();
                 String key = entry.getKey();
                 Object value = entry.getValue();
                 List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
                 if (value instanceof ArrayList<?>) {
                     for (Object o : (List<?>) value) {
                    	 listData.add(Map.class.cast(o));
                     }
                 }
                 listData=queryDownClassInfoCombination(strCiTypeBm,listData,new ArrayList<Map<String,Object>>());
                 dataMap.put(key, listData);
             }
        }
        //途径大类验证与关系数据相匹配，排除那些没有匹配关系数据的路径
        Map<String,Object> dataMapRe=new HashMap<String,Object>();
        Iterator<Map.Entry<String, Object>> entries1  = dataMap.entrySet().iterator();
        while(entries1.hasNext()){
            Map.Entry<String, Object> entry = entries1.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
            if (value instanceof ArrayList<?>) {
                for (Object o : (List<?>) value) {
               	 listData.add(Map.class.cast(o));
                }
            }
            boolean flag=true;
            Set<String> setCi=new HashSet<>();
            for(int i=listData.size()-1;i>0;i--) {
            	Map<String,Object> map=listData.get(i);
            	String sourceTypeBm=(String) map.get("sourceTypeBm");
            	String targetTypeBm=(String) map.get("targetTypeBm");
            	if(i==listData.size()-1) {
            		for(Map<String, Object> mapRel:relList) {
                		String sourceTypeBm1=(String) mapRel.get("sourceTypeBm");
                    	String targetTypeBm1=(String) mapRel.get("targetTypeBm");
                    	String sourceCiCode=(String) mapRel.get("sourceCiCode");
                    	if(sourceTypeBm.equals(sourceTypeBm1) && targetTypeBm.equals(targetTypeBm1)) {
                    		setCi.add(sourceCiCode);
                    	}
                	}
            	}else {
            		if(setCi==null || setCi.size()==0) {
            			flag=false;
            			break;
            		}
            		Set<String> setCiCode=new HashSet<>();
            		for(Map<String, Object> mapRel:relList) {
                		String sourceTypeBm1=(String) mapRel.get("sourceTypeBm");
                    	String targetTypeBm1=(String) mapRel.get("targetTypeBm");
                    	String sourceCiCode=(String) mapRel.get("sourceCiCode");
                    	String targetCiCode=(String) mapRel.get("targetCiCode");
                    	boolean fl=setCi.contains(targetCiCode);
                    	if(sourceTypeBm.equals(sourceTypeBm1) && targetTypeBm.equals(targetTypeBm1) && fl) {
                    		setCiCode.add(sourceCiCode);
                    	}
                	}
            		setCi.clear();
            		setCi.addAll(setCiCode);
            	}
            	
            }
            if(flag) {
            	dataMapRe.put(key, listData);
            }         
        }
        dataMap=dataMapRe; 
        //汇总每个大类CI数量
        Map<String,Object> ciCodeNumMap=new HashMap<String,Object>();
        Set<String> allCiCodesSet=new HashSet<String>();
        Set<String> allCiTypeIdsSet=new HashSet<String>();
        Set<Map<String, Object>> setRelInfo=new HashSet<Map<String,Object>>(); 
        for(String strCiType:ciTypeBms) {
        	Set<String> ciCodes=new HashSet<String>();
        	Set<String> ciNames=new HashSet<String>();
        	for(Map<String, Object> map:relList) {
        		String sourceTypeBm=(String) map.get("sourceTypeBm");
        		String sourceTypeId=(String) map.get("sourceTypeId");
        		String targetTypeBm=(String) map.get("targetTypeBm");
        		String targetTypeId=(String) map.get("targetTypeId");
        		String sourceCiBm=(String) map.get("sourceCiBm");
        		String sourceCiName=(String) map.get("sourceCiName");
        		String targetCiBm=(String) map.get("targetCiBm");
        		String targetCiName=(String) map.get("targetCiName");
        		String relId=(String) map.get("relId");
	    		String relName=(String) map.get("relName");
	    		Map<String, Object> map2=new HashMap<String, Object>();
	    		map2.put("ciTypeMc", relName);
	    		map2.put("ciTypeIcon", "");
	    		map2.put("id", relId);
	    		setRelInfo.add(map2);    	
        		allCiCodesSet.add(sourceCiBm.trim());
        		allCiCodesSet.add(targetCiBm.trim());
        		allCiTypeIdsSet.add(sourceTypeId);
        		allCiTypeIdsSet.add(targetTypeId);
        		if(strCiType.equals(sourceTypeBm)) {
        			ciCodes.add(sourceCiBm.trim());
        			ciNames.add(sourceCiName);
        		}
        		if(strCiType.equals(targetTypeBm)) {
        			ciCodes.add(targetCiBm.trim());
        			ciNames.add(targetCiName);
        		}
            }
        	Map<String,Object> mapCiCodeCiName=new HashMap<String,Object>();
        	mapCiCodeCiName.put("ciCode", ciCodes);
        	mapCiCodeCiName.put("ciName", ciNames);
        	ciCodeNumMap.put(strCiType, mapCiCodeCiName);
        }
        
        //查询出所有CI信息
        List<String> listCiCode = new ArrayList<String>(allCiCodesSet);
	    List<Map<String, Object>> listDatas=new ArrayList<Map<String,Object>>();
	    if(listCiCode!=null && listCiCode.size()>0) {
	    	listDatas=typeDao.findClassInfoAndCiInfoByCiIds(listCiCode);
	    }
	    
	    //查询大类信息并且汇总大类关系信息---start
	    List<String> allCiTypeIdsList = new ArrayList<String>(allCiTypeIdsSet);
	    List<Map<String, Object>> allClassInfoList=new ArrayList<Map<String,Object>>();
	    if(allCiTypeIdsList!=null && allCiTypeIdsList.size()>0) {
	    	allClassInfoList=typeDao.findClassInfoByCiTypeIds(allCiTypeIdsList);
	    }	    
        
        //汇总节点数据
        Map<String,Object> returnMap=new HashMap<String,Object>();
        List<Map<String,Object>> friendPathsList=new ArrayList<Map<String,Object>>();
        Iterator<Map.Entry<String, Object>> entries  = dataMap.entrySet().iterator();
        while(entries.hasNext()){
        	Map<String,Object> mapDataNode=new HashMap<String,Object>();
            Map.Entry<String, Object> entry = entries.next();
            List<Map<String,Object>> value = (List<Map<String, Object>>) entry.getValue();
            if(value!=null && value.size()>0) {
            	List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
            	for(int i=0;i<value.size();i++) {
            		
            		Map<String,Object> map=value.get(i);
              		String sourceTypeBm=(String) map.get("sourceTypeBm");
            		String sourceTypeId=(String) map.get("sourceTypeId");
            		String targetTypeBm=(String) map.get("targetTypeBm");
            		String targetTypeId=(String) map.get("targetTypeId");
            		if(i==0) {
            			Map<String,Object> mapItem1=new HashMap<String,Object>();
            			Map<String,Object> mapItem2=new HashMap<String,Object>();
            			boolean flag1=ciCodeNumMap.containsKey(sourceTypeBm);
                		if(flag1) {
                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(sourceTypeBm);
                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
                			mapItem1.put("className", sourceTypeBm);
                			mapItem1.put("ciClassId", sourceTypeId);
                			mapItem1.put("ciCodes", ciCodes);
                			mapItem1.put("ciNames", ciNames);
                			mapItem1.put("ciCount", ciCodes.size());
                		}
                		boolean flag2=ciCodeNumMap.containsKey(targetTypeBm);
                		if(flag2) {
                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(targetTypeBm);
                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
                			mapItem2.put("className", targetTypeBm);
                			mapItem2.put("ciClassId", targetTypeId);
                			mapItem2.put("ciCodes", ciCodes);
                			mapItem2.put("ciNames", ciNames);
                			mapItem2.put("ciCount", ciCodes.size());
                		}
                		listData.add(mapItem1);
                		listData.add(mapItem2);
            		}else {
            			Map<String,Object> mapItem1=new HashMap<String,Object>();
            			boolean flag2=ciCodeNumMap.containsKey(targetTypeBm);
                		if(flag2) {
                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(targetTypeBm);
                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
                			mapItem1.put("className", targetTypeBm);
                			mapItem1.put("ciClassId", targetTypeId);
                			mapItem1.put("ciCodes", ciCodes);
                			mapItem1.put("ciNames", ciNames);
                			mapItem1.put("ciCount", ciCodes.size());
                		}
                		listData.add(mapItem1);
            		}
            		
            	}
            	mapDataNode.put("nodes", listData);
            }
            friendPathsList.add(mapDataNode);
        }
        List<Map<String, Object>> classInfoAndRelInfoList=new ArrayList<Map<String,Object>>();
	    for(Map<String, Object> map:allClassInfoList) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    for(Map<String, Object> map:setRelInfo) {
	    	classInfoAndRelInfoList.add(map);
	    }
	    returnMap.put("ciClassInfos", classInfoAndRelInfoList);
        returnMap.put("friendPaths", friendPathsList);
        returnMap.put("ciRltLines", relList);
	    if(listDatas!=null && listDatas.size()>0) {
	    	for(Map<String, Object> map:listDatas) {
		    	String ciIdStr=(String) map.get("ciId");
		    	String ciBmStr=(String) map.get("ciBm");
		    	if(ciCode.equals(ciBmStr)) {
		    		returnMap.put("startCi", map);
		    	}
		    }
	    }else {
	    	returnMap.put("startCi", new HashedMap());
	    }
	    for(Map<String, Object> map:listDatas) {
	    	String ciCodeStr=(String) map.get("ciCode");
	    	map.put("ciCode", ciCodeStr.trim());
	    }
	    returnMap.put("ciNodes", listDatas);
		return returnMap;
	}
	
	public Map<String,Object> getQueue(String triggerBm,Set<String> typeNames,List<Map<String, Object>> ciRltLineList,Map<String,Object> returnMap,List<Map<String,Object>> returnList,String num,Integer temp){		 
		 for(String typeName:typeNames) {
			 List<Map<String, Object>> list=queryDownClassInfo(typeName,ciRltLineList,returnList);
			 if(list==null || list.size()==0) {
				 List<Map<String,Object>> listData=new ArrayList<>();
				 listData.addAll(returnList);
				 UUID uuid = UUID.randomUUID();
				 returnMap.put(uuid.toString(), listData);
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
				 String targetTypeBm=(String) map.get("targetTypeBm");
				 Set<String> targetSet=new HashSet<String>();
				 targetSet.add(targetTypeBm);
				 getQueue(triggerBm,targetSet,ciRltLineList,returnMap,returnList,num,temp);
			 }else if(list!=null && list.size()>0){
				 String sourceTypeBm=(String) list.get(0).get("sourceTypeBm");
	    		 List<Map<String, Object>> list1=queryUpdClassInfo(sourceTypeBm,ciRltLineList,new ArrayList<Map<String,Object>>(),returnList);
	    		 if(triggerBm.equals(sourceTypeBm)) {
	    			list1=new ArrayList<Map<String,Object>>();
	    		 }
				 for(Map<String, Object> map:list) {
					 String sourceTypeBmStr=(String) map.get("sourceTypeBm");
					 String targetTypeBm=(String) map.get("targetTypeBm");	
					 List<Map<String, Object>> list2=queryUpdClassInfo1(sourceTypeBmStr,returnList,new ArrayList<Map<String,Object>>());
					 returnList=list2;	 
					 Set<String> set=new HashSet<String>();
		        	 set.add(targetTypeBm);
		        	 returnList.add(map);
		        	 getQueue(triggerBm,set,ciRltLineList,returnMap,returnList,num,list1.size());
				 }
				 
			 }
			 
		 }

         return returnMap;
	}
	
	public List<Map<String, Object>> queryDownClassInfoCombination(String typeName,List<Map<String, Object>> list,List<Map<String, Object>> returnList){
		for(Map<String, Object> map:list) {
			String sourceTypeBm=(String) map.get("sourceTypeBm");
			String targetTypeBm=(String) map.get("targetTypeBm");
			if(typeName.equals(sourceTypeBm)) {
				returnList.add(map);
				queryDownClassInfoCombination(targetTypeBm,list,returnList);
			}
		}
		return returnList;
	}
	
	public List<Map<String, Object>> queryDownClassInfo(String typeName,List<Map<String, Object>> list,List<Map<String, Object>> list1){
		List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map:list) {
			String sourceTypeBm=(String) map.get("sourceTypeBm");
			String targetTypeBm=(String) map.get("targetTypeBm");
			if(sourceTypeBm.equals(typeName)) {
				//先暂时过滤掉自己指向自己的数据---2021-05-10
				if(!sourceTypeBm.equals(targetTypeBm)) {
					returnList.add(map);
				}			
			}			
		}
		List<Map<String, Object>> returnList1=new ArrayList<Map<String,Object>>();
		if(returnList!=null && returnList.size()>0) {
			if(list1!=null && list1.size()>0) {
				for(Map<String,Object> map1:returnList) {
					String sourceTypeBm1=(String) map1.get("sourceTypeBm");
					String targetTypeBm1=(String) map1.get("targetTypeBm");
					boolean flag=true;
					for(Map<String,Object> map:list1) {
						String sourceTypeBm=(String) map.get("sourceTypeBm");
						String targetTypeBm=(String) map.get("targetTypeBm");
						if(sourceTypeBm.equals(targetTypeBm1) && targetTypeBm.equals(sourceTypeBm1)) {
							flag=false;
						}						
					}
					if(flag) {
						returnList1.add(map1);
					}
				}
				return returnList1;
			}else {
				return returnList;
			}
			
		}
		return returnList1;
	}
	
	public List<Map<String, Object>> queryUpdClassInfo(String id,List<Map<String, Object>> list,List<Map<String, Object>> returnList,List<Map<String, Object>> returnList1){
		for(Map<String, Object> map:list) {
			String sourceTypeBm=(String) map.get("sourceTypeBm");
			String targetTypeBm=(String) map.get("targetTypeBm");
			if(targetTypeBm.equals(id)) {
				//先暂时过滤掉自己指向自己的数据---2021-05-10
				if(!sourceTypeBm.equals(targetTypeBm)) {
					boolean flag=false;
					for(Map<String, Object> map1:returnList1) {
						String sourceTypeBm1=(String) map1.get("sourceTypeBm");
						String targetTypeBm1=(String) map1.get("targetTypeBm");
						if(sourceTypeBm.equals(sourceTypeBm1) && targetTypeBm.equals(targetTypeBm1)) {
							flag=true;
						}
					}
					if(flag) {
						returnList.add(map);
						queryUpdClassInfo(sourceTypeBm,list,returnList,returnList1);
					}
					
				}			
			}			
		}
		return returnList;
	}
	
	public List<Map<String, Object>> queryUpdClassInfo1(String id,List<Map<String, Object>> list,List<Map<String, Object>> returnList){
		for(Map<String, Object> map:list) {
			String sourceTypeBm=(String) map.get("sourceTypeBm");
			String targetTypeBm=(String) map.get("targetTypeBm");
			if(targetTypeBm.equals(id)) {
				//先暂时过滤掉自己指向自己的数据---2021-05-10
				if(!sourceTypeBm.equals(targetTypeBm)) {				
					returnList.add(map);
					queryUpdClassInfo1(sourceTypeBm,list,returnList);				
				}			
			}			
		}
		return returnList;
	}


	/**
	 * 查询路径分析
	 * 
	 * @param ciId
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/queryPathByStartCiCodeAndEndCiCode", method = RequestMethod.GET)
	public Map<String, Object> queryPathByStartCiIdAndEndCiId(String startCiId,String endCiId,@RequestParam String typeId,String startCiCodes,String endCiCodes) {
		
		return typeDataService.queryPathByStartCiIdAndEndCiId(startCiId,endCiId,typeId,startCiCodes,endCiCodes);
	}
	
	/**
	 * 根据大类ID查询有直接关系的大类---配置报表
	 * 
	 * @param ciId
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/queryDirectRelationByCiTypeId", method = RequestMethod.GET)
	public Map<String, Object> queryDirectRelationByCiTypeId(@RequestParam String ciTypeId,@RequestParam String ciTypeMc) {
		
		return typeDataService.queryDirectRelationByCiTypeId(ciTypeId,ciTypeMc,1, 1);
	}
	
	/**
	 * 根据大类ID和字段等值条件获取CI信息
	 * 
	 * @param sql
	 * @return
	 */
	@GetMapping("findDataInfo")
	public List<Map> findDataInfo(String jsonStr) {
		return typeDataService.findDataInfo(jsonStr);
	}

	/**
	 * 根据大类ID,字段ID和字段值获取可能的字段值
	 * 
	 * @param sql
	 * @return
	 */
	@GetMapping("findItemInfo")
	public List<String> findItemInfo(String typeId, String itemId, String value) {
		List<String> dataList=typeItemService.findItemVal(typeId, itemId, value);
		return dataList;
	}

	/**
	 * 根据映射关系查询ci数据
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/getMappingCiData", method = RequestMethod.GET)
	public Map<String, Object> getMappingCiData(String[] list){
		return typeItemService.getMappingCiData(list);
	}

	/**
	 * 所属机柜为空校验
	 * @return
	 */
	@RequestMapping(value = "/jhIsnull", method = RequestMethod.POST)
	public List<Map<String, Object>> jhIsnull(String ciTypeId, String sbmc, String checkColumn) {
		return typeItemService.jhIsnull(ciTypeId, sbmc, checkColumn);
	}

	/**
	 * 所属机柜是否存在校验
	 * @return
	 */
	@RequestMapping(value = "/jhIsexis", method = RequestMethod.POST)
	public List<Map<String, Object>> jhIsexis(String ciTypeId, String sbbh, String sbmc, String checkColumn) {
		return typeItemService.jhIsexis(ciTypeId, sbbh, sbmc, checkColumn);
	}

	/**
	 * U位数据为空校验
	 * @return
	 */
	@RequestMapping(value = "/uDataIsnull", method = RequestMethod.POST)
	public List<Map<String, Object>> uDataIsnull(String ciTypeId, String sbmc, String checkColumn) {
		return typeItemService.uDataIsnull(ciTypeId, sbmc, checkColumn);
	}

	/**
	 * U位数据格式校验
	 * @return
	 */
	@RequestMapping(value = "/uDatasFormat", method = RequestMethod.POST)
	public List<Map<String, Object>> uDatasFormat(String ciTypeId, String sbbh, String sbmc, String checkColumn) {
		return typeItemService.uDatasFormat(ciTypeId, sbbh, sbmc, checkColumn);
	}

	/**
	 * U位数据重复校验
	 * @return
	 */
	@RequestMapping(value = "/getRepeatDataId", method = RequestMethod.POST)
	public List<Map<String, Object>> getRepeatDataId(String ciTypeId, String sbbh, String sbmc, String ss, String checkColumn){
		return typeItemService.getRepeatDataId(ciTypeId, sbbh, sbmc, ss, checkColumn);
	}
	
	/**
	 * 根据ciId查询信息(dcv验证使用)
	 * @return
	 */
	@RequestMapping(value = "/findCiInfoById", method = RequestMethod.POST)
	public Map<String, Object> findCiInfoById(String ciId){
		Info info=infoService.findCiInfoById(ciId);
		try {
			if(info!=null) {
				Map<String,Object> map=bean2map(info);
				return map;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Author: ztl
	 * date: 2021-08-09
	 * @description:把JavaBean转化为map
	 */
	public static Map<String,Object> bean2map(Object bean) throws Exception{
		Map<String,Object> map = new HashMap<>();
		//获取JavaBean的描述器
		BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
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
	/**
	 * 配置查询中查询根因分析
	 *
	 * @param ciId
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/rootCauseAnalysisByCiCode", method = RequestMethod.GET)
	public Map<String, Object> rootCauseAnalysisByCiId(String ciId,Integer upNum,Integer downNum,String ciCode) {
        String domainId=TokenUtils.getTokenOrgDomainId();
		//根据ciCode获取大类信息
		TypeData typeData=typeDataService.findDataById(ciCode);
		//提取大类ID
		String ciTypeId=typeData.getCi_type_id();
		List<String> ciTypeIdList=new ArrayList<String>();
		ciTypeIdList.add(ciTypeId);
		Map<String,Set<String>> ciCodeMap=new HashMap<String,Set<String>>();
		Set<String> ciCodeSet=new HashSet<String>();
		ciCodeSet.add(ciCode);
		ciCodeMap.put(ciTypeId, ciCodeSet);
		//这里最后一个参数传1，只循环一层
		List<Map<String,Object>> list=typeDataService.queryRootCauseAnalysisByCiCodeOptimize(ciTypeId,ciCode,ciTypeIdList,ciCodeMap,new ArrayList<Map<String,Object>>(),new HashSet<String>(),"1",domainId);
		//去重CI关系数据
				List<Map<String, Object>> relList=filterListByCiDataRelId(list);
				//存储sourceType对应targetType
				Map<String,String> mapData=new HashMap<String,String>();
				List<Map<String,Object>> listRel=new ArrayList<Map<String,Object>>();
				Set<String> ciTypeBms=new HashSet<String>();
		        if(relList!=null && relList.size()>0) {
		        	for(Map<String, Object> map:relList) {
		        		String sourceTypeBm=(String) map.get("sourceTypeBm");
		        		String targetTypeBm=(String) map.get("targetTypeBm");
		        		ciTypeBms.add(sourceTypeBm);
		        		ciTypeBms.add(targetTypeBm);
		        		boolean flag=mapData.containsKey(sourceTypeBm+"-"+targetTypeBm);
		        		if(!flag) {
		        			mapData.put(sourceTypeBm+"-"+targetTypeBm, sourceTypeBm+"-"+targetTypeBm);
		        			listRel.add(map);
		        		}
		        	}
		        }
		        Set<String> ciTypeNameSet=new HashSet<String>();
		    	//找出起始大类
		        if(listRel!=null && listRel.size()>0) {
		        	for(Map<String,Object> map:listRel) {
		        		String sourceTypeBm=(String) map.get("sourceTypeBm");
		        		boolean flag=true;
		        		for(Map<String,Object> map1:listRel) {
		        			String targetTypeBm=(String) map1.get("targetTypeBm");
		        			if(sourceTypeBm.equals(targetTypeBm)) {
		        				flag=false;
		        				break;
		        			}
		        		}
		        		if(flag) {
		        			ciTypeNameSet.add(sourceTypeBm);
		        		}
		        		
		        	}
		        }
		        //途径大类节点
		        Map<String,Object> dataMap=new HashMap<String,Object>();
		        Iterator it = ciTypeNameSet.iterator();
		        while (it.hasNext()) {
		             String strCiTypeBm = (String) it.next();
		             Set<String> ciTypeNameSet1=new HashSet<String>();
		             ciTypeNameSet1.add(strCiTypeBm);
		             Map<String,Object> map=getQueue(strCiTypeBm,ciTypeNameSet1,listRel,new HashMap<String,Object>(),new ArrayList<Map<String,Object>>(),"1",0);
		             Iterator<Map.Entry<String, Object>> entries  = map.entrySet().iterator();
		             while(entries.hasNext()){
		                 Map.Entry<String, Object> entry = entries.next();
		                 String key = entry.getKey();
		                 Object value = entry.getValue();
		                 List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
		                 if (value instanceof ArrayList<?>) {
		                     for (Object o : (List<?>) value) {
		                    	 listData.add(Map.class.cast(o));
		                     }
		                 }
		                 listData=queryDownClassInfoCombination(strCiTypeBm,listData,new ArrayList<Map<String,Object>>());
		                 dataMap.put(key, listData);
		             }
		        }
		        //汇总每个大类CI数量
		        Map<String,Object> ciCodeNumMap=new HashMap<String,Object>();
		        Set<String> allCiCodesSet=new HashSet<String>();
		        Set<String> allCiTypeIdsSet=new HashSet<String>();
		        Set<Map<String, Object>> setRelInfo=new HashSet<Map<String,Object>>(); 
		        for(String strCiType:ciTypeBms) {
		        	Set<String> ciCodes=new HashSet<String>();
		        	Set<String> ciNames=new HashSet<String>();
		        	for(Map<String, Object> map:relList) {
		        		String sourceTypeBm=(String) map.get("sourceTypeBm");
		        		String sourceTypeId=(String) map.get("sourceTypeId");
		        		String targetTypeBm=(String) map.get("targetTypeBm");
		        		String targetTypeId=(String) map.get("targetTypeId");
		        		String sourceCiBm=(String) map.get("sourceCiBm");
		        		String sourceCiName=(String) map.get("sourceCiName");
		        		String targetCiBm=(String) map.get("targetCiBm");
		        		String targetCiName=(String) map.get("targetCiName");
		        		String relId=(String) map.get("relId");
			    		String relName=(String) map.get("relName");
			    		Map<String, Object> map2=new HashMap<String, Object>();
			    		map2.put("ciTypeMc", relName);
			    		map2.put("ciTypeIcon", "");
			    		map2.put("id", relId);
			    		setRelInfo.add(map2);    	
		        		allCiCodesSet.add(sourceCiBm);
		        		allCiCodesSet.add(targetCiBm);
		        		allCiTypeIdsSet.add(sourceTypeId);
		        		allCiTypeIdsSet.add(targetTypeId);
		        		if(strCiType.equals(sourceTypeBm)) {
		        			ciCodes.add(sourceCiBm);
		        			ciNames.add(sourceCiName);
		        		}
		        		if(strCiType.equals(targetTypeBm)) {
		        			ciCodes.add(targetCiBm);
		        			ciNames.add(targetCiName);
		        		}
		            }
		        	Map<String,Object> mapCiCodeCiName=new HashMap<String,Object>();
		        	mapCiCodeCiName.put("ciCode", ciCodes);
		        	mapCiCodeCiName.put("ciName", ciNames);
		        	ciCodeNumMap.put(strCiType, mapCiCodeCiName);
		        }
		        
		        //查询出所有CI信息
		        List<String> listCiCode = new ArrayList<String>(allCiCodesSet);
			    List<Map<String, Object>> listDatas=new ArrayList<Map<String,Object>>();
			    if(listCiCode!=null && listCiCode.size()>0) {
			    	listDatas=typeDao.findClassInfoAndCiInfoByCiIds(listCiCode);
			    }
			    
			    //查询大类信息并且汇总大类关系信息---start
			    List<String> allCiTypeIdsList = new ArrayList<String>(allCiTypeIdsSet);
			    List<Map<String, Object>> allClassInfoList=new ArrayList<Map<String,Object>>();
			    if(allCiTypeIdsList!=null && allCiTypeIdsList.size()>0) {
			    	allClassInfoList=typeDao.findClassInfoByCiTypeIds(allCiTypeIdsList);
			    }	    
		        
		        //汇总节点数据
		        Map<String,Object> returnMap=new HashMap<String,Object>();
		        List<Map<String,Object>> friendPathsList=new ArrayList<Map<String,Object>>();
		        Iterator<Map.Entry<String, Object>> entries  = dataMap.entrySet().iterator();
		        while(entries.hasNext()){
		        	Map<String,Object> mapDataNode=new HashMap<String,Object>();
		            Map.Entry<String, Object> entry = entries.next();
		            List<Map<String,Object>> value = (List<Map<String, Object>>) entry.getValue();
		            if(value!=null && value.size()>0) {
		            	List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
		            	for(int i=0;i<value.size();i++) {
		            		
		            		Map<String,Object> map=value.get(i);
		              		String sourceTypeBm=(String) map.get("sourceTypeBm");
		            		String sourceTypeId=(String) map.get("sourceTypeId");
		            		String targetTypeBm=(String) map.get("targetTypeBm");
		            		String targetTypeId=(String) map.get("targetTypeId");
		            		if(i==0) {
		            			Map<String,Object> mapItem1=new HashMap<String,Object>();
		            			Map<String,Object> mapItem2=new HashMap<String,Object>();
		            			boolean flag1=ciCodeNumMap.containsKey(sourceTypeBm);
		                		if(flag1) {
		                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(sourceTypeBm);
		                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
		                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
		                			mapItem1.put("className", sourceTypeBm);
		                			mapItem1.put("ciClassId", sourceTypeId);
		                			mapItem1.put("ciCodes", ciCodes);
		                			mapItem1.put("ciNames", ciNames);
		                			mapItem1.put("ciCount", ciCodes.size());
		                		}
		                		boolean flag2=ciCodeNumMap.containsKey(targetTypeBm);
		                		if(flag2) {
		                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(targetTypeBm);
		                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
		                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
		                			mapItem2.put("className", targetTypeBm);
		                			mapItem2.put("ciClassId", targetTypeId);
		                			mapItem2.put("ciCodes", ciCodes);
		                			mapItem2.put("ciNames", ciNames);
		                			mapItem2.put("ciCount", ciCodes.size());
		                		}
		                		listData.add(mapItem1);
		                		listData.add(mapItem2);
		            		}else {
		            			Map<String,Object> mapItem1=new HashMap<String,Object>();
		            			boolean flag2=ciCodeNumMap.containsKey(targetTypeBm);
		                		if(flag2) {
		                			Map<String,Object> mapCiCodeCiName=(Map<String, Object>) ciCodeNumMap.get(targetTypeBm);
		                			Set<String> ciCodes=(Set<String>) mapCiCodeCiName.get("ciCode");
		                			Set<String> ciNames=(Set<String>) mapCiCodeCiName.get("ciName");
		                			mapItem1.put("className", targetTypeBm);
		                			mapItem1.put("ciClassId", targetTypeId);
		                			mapItem1.put("ciCodes", ciCodes);
		                			mapItem1.put("ciNames", ciNames);
		                			mapItem1.put("ciCount", ciCodes.size());
		                		}
		                		listData.add(mapItem1);
		            		}
		            		
		            	}
		            	mapDataNode.put("nodes", listData);
		            }
		            friendPathsList.add(mapDataNode);
		        }
		        List<Map<String, Object>> classInfoAndRelInfoList=new ArrayList<Map<String,Object>>();
			    for(Map<String, Object> map:allClassInfoList) {
			    	classInfoAndRelInfoList.add(map);
			    }
			    for(Map<String, Object> map:setRelInfo) {
			    	classInfoAndRelInfoList.add(map);
			    }
			    returnMap.put("ciClassInfos", classInfoAndRelInfoList);
		        returnMap.put("friendPaths", friendPathsList);
		        returnMap.put("ciRltLines", relList);
		        returnMap.put("ciNodes", listDatas);
			    if(listDatas!=null && listDatas.size()>0) {
			    	for(Map<String, Object> map:listDatas) {
				    	String ciIdStr=(String) map.get("ciId");
				    	String ciBmStr=(String) map.get("ciBm");
				    	if(ciCode.equals(ciBmStr)) {
				    		returnMap.put("startCi", map);
				    	}
				    }
			    }else {
			    	returnMap.put("startCi", new HashedMap());
			    }
				return returnMap;
		
	}
	/**
	 * 值班模式查询影响分析
	 *
	 * @param ciId
	 * @return
	 */
	@RequestMapping(value = "/ciDataRel/impactAnalysisByCiId", method = RequestMethod.GET)
	public Map<String, Object> impactAnalysisByCiId(String ciId,String ciCode,Integer upNum,Integer downNum) {
		if (upNum == null) {
			upNum = 1;
		}

		if (downNum == null) {
			downNum = 1;
		}
		//根据ciId获取大类信息
		TypeData typeData=typeDataService.findDataById(ciCode);
		//提取大类ID
		String ciTypeId=typeData.getCi_type_id();
		return typeDataService.queryImpactAnalysisByCiId(ciId,ciTypeId,upNum,downNum,ciCode);
	}

	/**
	 * 根据ciBm模糊查询CI信息
	 *
	 * @param ciBm
	 * @return
	 */
	@RequestMapping(value = "/findCiByCiCode", method = RequestMethod.GET)
	public PageResult findCiByCiBm(String ciCode) {
		PageResult pageResult = new PageResult();
		//获取数据域ID
		String domainId = TokenUtils.getTokenDataDomainId();
		Map map = new HashMap();
		map.put("domainId",domainId);
		map.put("ciCode",ciCode);
		MyPagUtile.startPage();
		pageResult = typeDataService.findCiByCiCode(map);
		return pageResult;
	}

	/**
	 * 为接口超时提供CI信息查询接口
	 *
	 * @param pageSize
	 * @param pageNum
	 * @param ciTypeId
	 * @return
	 */
	@RequestMapping(value = "/getCiTypeInfo", method = RequestMethod.POST)
	public PageResult getCiTypeInfo(String pageSize, String pageNum, String jsonStr) {
		PageResult pageResult = new PageResult();
		Map jsonMap = JSON.parseObject(jsonStr, Map.class);
		String[] tidList = ((JSONArray) jsonMap.get("ciTypeId")).toArray(new String[]{});
		Map map = new HashMap();
		map.put("tidList", tidList);
		List<LinkedHashMap> configList = new ArrayList<LinkedHashMap>();
		configList = typeDataService.getCiTypeInfo(map);
		pageResult.setReturnObject(configList);
		pageResult.setReturnBoolean(true);
		if (configList !=null && configList.size()>0){
			pageResult.setTotalPage(((Page)configList).getPages());
			pageResult.setTotalResult((int)((Page)configList).getTotal());
		}else{
			pageResult.setTotalPage(0);
			pageResult.setTotalResult(0);
		}
		return pageResult;
	}
	
	/**
	 * 世界地图查询层级关系
	 *
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getWorldMapCiSingleRels", method = RequestMethod.POST)
	public List<Map<String,Object>> getWorldMapCiSingleRels(@RequestParam Map<String,Object> itemMap){
		String ciCodes=(String) itemMap.get("ciCodes");
		String relNames=(String) itemMap.get("relIds");
		//查询relId
		List<String> relIdsList=rltRuleService.getCiRelByLineBm(relNames);
		String relIds = String.join(",", relIdsList);
		String ciTypeName=(String) itemMap.get("ciTypeName");
		List<String> relIdList = Arrays.asList(relIds.split(","));
		List<String> ciCodeList = Arrays.asList(ciCodes.split(","));
		List<Map<String,Object>> list=typeDataService.getWorldMapCiSingleRels(relIdList,ciCodeList,ciTypeName);
		return list;
	}
	
	@RequestMapping(value = "/getWorldMapCiSingleRelsUp", method = RequestMethod.POST)
	public List<Map<String,Object>> getWorldMapCiSingleRelsUp(@RequestParam Map<String,Object> itemMap){
		String ciCodes=(String) itemMap.get("ciCodes");
		String relNames=(String) itemMap.get("relIds");
		//查询relId
		List<String> relIdsList=rltRuleService.getCiRelByLineBm(relNames);
		String relIds = String.join(",", relIdsList);
		String ciTypeName=(String) itemMap.get("ciTypeName");
		List<String> relIdList = Arrays.asList(relIds.split(","));
		List<String> ciCodeList = Arrays.asList(ciCodes.split(","));
		List<Map<String,Object>> list=typeDataService.getWorldMapCiSingleRelsUp(relIdList,ciCodeList,ciTypeName);
		return list;
	}
	
	/**
	 * 获取世界地图所有层级关系数据
	 *
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getWorldMapCiSingleRelsAll", method = RequestMethod.POST)
	public List<Map<String,Object>> getWorldMapCiSingleRelsAll(String levelData,String firstFloorCiCode){
		List<String> firstFloorCiCodeList=Arrays.asList(firstFloorCiCode.split(","));
		JSONArray jsonArray=JSONArray.parseArray(levelData);
		//获取第一层大类所有CI数据
		String levelOneClassName="";
		String levelNextClassName="";
		String levelNext="";
		String highLevel="";
		Set<String> rltList=new HashSet<String>();
		List<String> ciBmList=new ArrayList<String>();
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject one =(JSONObject) jsonArray.get(i);
			Object levelObj = one.get("level");
			Object rltNameObj = one.get("rltName");
			String rltName=rltNameObj.toString();
			String levelStr=levelObj.toString();
			rltList.add(rltName);
			if(i+1==jsonArray.size()) {
				highLevel=levelStr;
			}
			if("1".equals(levelStr)) {
				Object classNameobj = one.get("className");
				levelOneClassName=classNameobj.toString();
			}
			if("2".equals(levelStr)) {
				Object classNameobj = one.get("className");
				levelNextClassName=classNameobj.toString();
			}
			
		}	
		List<Map<String, Object>> ciInfos=typeService.queryCiInfoByCiTypeName(levelOneClassName,"");
		if(ciInfos!=null && ciInfos.size()>0) {
			for(String strCiCode:firstFloorCiCodeList) {
				for(Map<String, Object> map:ciInfos) {
					String ciBm=(String) map.get("ciCode");
					if(strCiCode.equals(ciBm)) {
						ciBmList.add(ciBm);
					}
				}
			}
			
			
		}
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		//开始遍历查询
		List<String> ciBmNextList=null;
		for (int i=0;i<jsonArray.size();i++) {
			JSONObject one =(JSONObject) jsonArray.get(i);
			Object levelObj = one.get("level");
			String levelStr=levelObj.toString();
			if(levelStr.equals(highLevel)) {
				return returnList;
			}
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			if("1".equals(levelStr)) {
				String ciCodes=String.join(",", ciBmList);
				String rltNames=String.join(",", rltList);
				Map<String,Object> itemMap=new HashMap<String,Object>();
				itemMap.put("ciCodes", ciCodes);
				itemMap.put("relIds", rltNames);
				itemMap.put("ciTypeName", levelNextClassName);
				list=getWorldMapCiSingleRels(itemMap);
				if(list!=null && list.size()>0) {
					returnList.addAll(list);
					ciBmNextList=new ArrayList<String>();
					for(Map<String,Object> map:list) {
						String targetCiBm=(String) map.get("targetCiBm");
						ciBmNextList.add(targetCiBm);
					}
					for (Object object1 : jsonArray) {
						JSONObject one1 = (JSONObject) object1;
						Object levelObj1 = one1.get("level");
						String levelStr1=levelObj1.toString();
						if("3".equals(levelStr1)) {
							Object classNameobj = one1.get("className");
							levelNextClassName=classNameobj.toString();
							levelNext="3";
						}
					}		
				}
			}else {
				Map<String,Object> itemMap=new HashMap<String,Object>();
				itemMap.put("ciCodes", String.join(",", ciBmNextList));
				itemMap.put("relIds", String.join(",", rltList));
				itemMap.put("ciTypeName", levelNextClassName);
				list=getWorldMapCiSingleRels(itemMap);
				if(list!=null && list.size()>0) {
					returnList.addAll(list);
					ciBmNextList=new ArrayList<String>();
					for(Map<String,Object> map:list) {
						String targetCiBm=(String) map.get("targetCiBm");
						ciBmNextList.add(targetCiBm);
					}
					for (Object object1 : jsonArray) {
						JSONObject one1 = (JSONObject) object1;
						Object levelObj1 = one1.get("level");
						String levelStr1=levelObj1.toString();
						Integer levelNext1=Integer.valueOf(levelNext);
						Integer levelNext2=levelNext1+1;
						String levelNext3=String .valueOf(levelNext2);
						if(levelNext3.equals(levelStr1)) {
							Object classNameobj = one1.get("className");
							levelNextClassName=classNameobj.toString();
							levelNext=levelStr1;
							break;
						}
					}
					
				}
			}
		}
		return returnList;
	}
	
	@RequestMapping(value = "/getWorldMapCiSingleRelsDownAndUpLevel", method = RequestMethod.POST)
	public List<Map<String,Object>> getWorldMapCiSingleRelsDownAndLevel(String levels,String relConf){
		JSONArray data = JSON.parseArray(relConf);
		if (data != null && data.size() > 0) {
			String down="";
			String up="";
			String level="";
			List<String> ciCodeList=new ArrayList<String>();
			Set<String> rltClassList=new HashSet<String>();
			String nextClass="";
			for (Object object : data) {
				JSONObject one = (JSONObject) object;
				Object downObj = one.get("down");
				Object rltClassIdsObj = one.get("rltClassIds");
				Object upObj = one.get("up");
				Object sCiCodeObj = one.get("sCiCode");
				Object levelObj = one.get("level");
				if(downObj!=null) {
					down=downObj.toString();
				}
				if(upObj!=null) {
					up=upObj.toString();
				}
				if(rltClassIdsObj!=null) {
					String rltClassStr=rltClassIdsObj.toString();
					JSONArray rltJsonArray = JSON.parseArray(rltClassStr);
					if (rltJsonArray != null && rltJsonArray.size() > 0) {
						for (Object obj : rltJsonArray) {
							rltClassList.add(obj.toString());							
						}
					}
				}
				if(sCiCodeObj!=null) {
					ciCodeList.add(sCiCodeObj.toString());				
				}
				if(levelObj!=null) {
					level=levelObj.toString();
				}
			}
			List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
			if(!"0".equals(down)) {
				Integer downInt=Integer.valueOf(down);
				if(downInt==1) {
					Integer valI=Integer.valueOf(level);
					Integer val=valI+1;
					String valStr=String.valueOf(val);
					JSONArray dataArray =JSONArray.parseArray(levels);
					for (Object object : dataArray) {
						JSONObject one = (JSONObject) object;
						Object levelObj = one.get("level");
						String levelStr=levelObj.toString();
						if(valStr.equals(levelStr)) {
							Object classNameobj = one.get("className");
							nextClass=classNameobj.toString();
						}
					}
					String ciCodes=String.join(",", ciCodeList);
					String rltNames=String.join(",", rltClassList);
					Map<String,Object> itemMap=new HashMap<String,Object>();
					itemMap.put("ciCodes", ciCodes);
					itemMap.put("relIds", rltNames);
					itemMap.put("ciTypeName", nextClass);
					List<Map<String,Object>> list=getWorldMapCiSingleRels(itemMap);
					for(Map<String,Object> map:list) {
						map.put("targetLevel", nextClass);
					}
					return list;
				}
				for(int i=0;i<downInt;i++) {
						Integer valI=Integer.valueOf(level);
						Integer val=valI+1;
						String valStr=String.valueOf(val);
						JSONArray dataArray =JSONArray.parseArray(levels);
						for (Object object : dataArray) {
							JSONObject one = (JSONObject) object;
							Object levelObj = one.get("level");
							String levelStr=levelObj.toString();
							if(valStr.equals(levelStr)) {
								Object classNameobj = one.get("className");
								nextClass=classNameobj.toString();
								level=levelStr;
							}
						}
						String ciCodes=String.join(",", ciCodeList);
						String rltNames=String.join(",", rltClassList);
						Map<String,Object> itemMap=new HashMap<String,Object>();
						itemMap.put("ciCodes", ciCodes);
						itemMap.put("relIds", rltNames);
						itemMap.put("ciTypeName", nextClass);
						List<Map<String,Object>> list=getWorldMapCiSingleRels(itemMap);
						returnList.addAll(list);
						//循环取出targetCiBm
						if(list!=null && list.size()>0) {
							ciCodeList=new ArrayList<String>();
							for(Map<String,Object> map:list) {
								map.put("targetLevel", nextClass);
								String targetCiBm=(String) map.get("targetCiBm");
								ciCodeList.add(targetCiBm);
							}
						}
						
					
				}
			}else if(!"0".equals(up)) {
				Integer upInt=Integer.valueOf(up);
				if(upInt==1) {
					Integer valI=Integer.valueOf(level);
					Integer val=valI-1;
					String valStr=String.valueOf(val);
					JSONArray dataArray =JSONArray.parseArray(levels);
					for (Object object : dataArray) {
						JSONObject one = (JSONObject) object;
						Object levelObj = one.get("level");
						String levelStr=levelObj.toString();
						if(valStr.equals(levelStr)) {
							Object classNameobj = one.get("className");
							nextClass=classNameobj.toString();
						}
					}
					String ciCodes=String.join(",", ciCodeList);
					String rltNames=String.join(",", rltClassList);
					Map<String,Object> itemMap=new HashMap<String,Object>();
					itemMap.put("ciCodes", ciCodes);
					itemMap.put("relIds", rltNames);
					itemMap.put("ciTypeName", nextClass);
					List<Map<String,Object>> list=getWorldMapCiSingleRels(itemMap);
					return list;
				}
				for(int i=upInt;i>0;i--) {
					Integer valI=Integer.valueOf(level);
					Integer val=valI-1;
					String valStr=String.valueOf(val);
					JSONArray dataArray =JSONArray.parseArray(levels);
					for (Object object : dataArray) {
						JSONObject one = (JSONObject) object;
						Object levelObj = one.get("level");
						String levelStr=levelObj.toString();
						if(valStr.equals(levelStr)) {
							Object classNameobj = one.get("className");
							nextClass=classNameobj.toString();
							level=levelStr;
						}
					}
					String ciCodes=String.join(",", ciCodeList);
					String rltNames=String.join(",", rltClassList);
					Map<String,Object> itemMap=new HashMap<String,Object>();
					itemMap.put("ciCodes", ciCodes);
					itemMap.put("relIds", rltNames);
					itemMap.put("ciTypeName", nextClass);
					List<Map<String,Object>> list=getWorldMapCiSingleRelsUp(itemMap);
					returnList.addAll(list);
					//循环取出sourceCiBm
					if(list!=null && list.size()>0) {
						ciCodeList=new ArrayList<String>();
						for(Map<String,Object> map:list) {
							String sourceCiBm=(String) map.get("sourceCiBm");
							ciCodeList.add(sourceCiBm);
						}
					}
				
			}
			}
			return returnList;
		}
		return null;
	}
	
	@RequestMapping(value = "/getConfigurationInfoByCiCode", method = RequestMethod.GET)
	public Object getConfigurationInfoByCiCode(String ciCode){
		
		Map<String, Object> returnMap=new HashMap<String, Object>();
		TypeData typeData = typeDataService.findDataById(ciCode);
		if(typeData!=null) {
			String ciTypeId=typeData.getCi_type_id();
			//根据ciTypeId查询ciTypeIcon---start
			Type mapCiTypeData=typeService.findTypeById(ciTypeId);
        	//根据ciTypeId查询ciTypeIcon---end
			
			//根据ciId查询配置信息
        	Map<String, Object> mapHead = new HashMap<String, Object>();
    		Map<String, Object> map2 = new HashMap<String, Object>();
    		List<String> list = Arrays.asList(ciCode.split(","));
    		List<Map<String, Object>> ciNamesList=typeServie.findClassAndCiInfoByCiIds(list);
    		List<Map<String, Object>> listData=typeItemService.findCiListNoDomain(list);
    		if (listData!=null && listData.size() > 0) {
    			Map<String, Object> mapCiName=ciNamesList.get(0);
    			String ciCodeStr="";
    			String ciNameStr="";
    			if(mapCiName!=null && mapCiName.size()>0) {
    				ciCodeStr=(String) mapCiName.get("ciCode");
    				ciNameStr=(String) mapCiName.get("ciName");
    			}
    			map2 = listData.get(0);
    			// 把大类名称和CI编码单独放到一个map里面
    			String daleiName = (String) map2.get("大类名称");
    			String ciBianMa = (String) map2.get("CI编码");
    			mapHead.put("ciTypeName", daleiName);
    			mapHead.put("ciCode", ciCodeStr);
    			mapHead.put("ciName", ciCodeStr);
    			mapHead.put("ciTypeIcon", mapCiTypeData.getCi_type_icon());
    			// 利用迭代器把"大类名称"和"CI编码"在map中删除
    			Iterator<String> iter = map2.keySet().iterator();
    			while (iter.hasNext()) {
    				String key = iter.next();
    				if ("大类名称".equals(key)) {
    					iter.remove();
    				}
    				if ("CI编码".equals(key)) {
    					iter.remove();
    				}
    			}
    		}
    		returnMap.put("head", mapHead);
    		returnMap.put("attr", map2);
        	return returnMap;
		}	
		return returnMap;
	}
	
	@RequestMapping(value = "/getDiagramInfoByCiCode", method = RequestMethod.GET)
	public Object getDiagramInfoByCiCode(String ciId,String ciCode){
		return themeFieldService.getDiagramInfoByCiId(ciId, ciCode);
	}
	
	private static List<Map<String, Object>> filterListByCiDataRelId(List<Map<String, Object>> list) {
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

	/**
	 * 查询交换机等所属对应数据
	 *
	 * @param ciCode
	 * @return
	 */
	@RequestMapping(value = "/getCommandByCiCode", method = RequestMethod.GET)
	public List<Map> getCommandByCiCode(String ciCode){
		return typeDataService.getCommandByCiCode(ciCode);
	}
}
