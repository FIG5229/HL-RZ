package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.dao.RltRuleDao;
import com.integration.entity.PageResult;
import com.integration.entity.Type;
import com.integration.feign.CzryService;
import com.integration.service.TypeService;
import com.integration.service.impl.RltRuleServiceImpl;
import com.integration.utils.DataUtils;
import com.integration.utils.MyPagUtile;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关系遍历
 * @author Dell
 *
 */
@RestController
@RequestMapping("rltRule")
public class RltRuleController {

	@Resource
	private RltRuleServiceImpl rltRuleService;

	@Resource
	private CzryService czryService;
	
	@Resource
	private TypeService typeService;
	
	@Resource
	private RltRuleDao rltRuleDao;

	/**
	 * 获取全局唯一id
	 * @return
	 */
	@GetMapping("/id")
	public Object getId() {
		return DataUtils.returnPr(SeqUtil.getId());
	}

	private <T> List<T> getJSONList(JSONObject jsonObject,String key, Class<T> clazz) {
		if (jsonObject!=null) {
			Object listObj = jsonObject.get(key);
			if (listObj!=null && !"".equals(listObj.toString())) {
				return JSON.parseArray(JSON.toJSONString(listObj), clazz);
			}
		}
		return null;
	}

	/**
	 * 获取关系遍历详情
	 * @param id
	 * @return
	 */
	@GetMapping
	public Map<String, Object> get(String id) {
		return rltRuleService.get(id);
	}

	/**
	 * 保存规则数据---配置报表
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveOrUpdateDataExtractionRules", method = RequestMethod.POST)
	public Object saveOrUpdateDataExtractionRules(String json) {
		String userId=TokenUtils.getTokenUserId();
		String domainId = TokenUtils.getTokenDataDomainId();
		if (StringUtils.isNotEmpty(json)) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>) JSON.parse(json);
			//判断节点只有一个时，则返回提示前端不允许有一个节点
			List<Map<String, Object>> iomCiRltNodeList = (List<Map<String, Object>>) map.get("nodes");
			if(iomCiRltNodeList.size()<=1) {
				PageResult.fail("CI大类不能唯一");
        		return false;
			}
			Map<String, Object> mapData=(Map<String, Object>) map.get("def");
			String rltName=(String) mapData.get("rltName");
			String idStr=(String) mapData.get("id");			
			if(idStr!=null && !"".equals(idStr)) {
				Map<String,Object> itemMap=new HashMap<String,Object>();							   				   	
			   	itemMap.put("rltName", "");
			   	if(userId!=null && !"".equals(userId)) {
			   		itemMap.put("userId", userId);
			   	}else {
			   		itemMap.put("userId", "");
			   	}				
				if(domainId!=null && !"".equals(domainId)) {
			   	    itemMap.put("domainId", domainId);
			   	}else {
			   	    itemMap.put("domainId", "");
			   	}
				List<Map<String,Object>> list=rltRuleDao.getIomCiRltRuleByRltName(itemMap);
				for(Map<String, Object> map1:list) {
					String idString=(String) map1.get("id");
					if(!idStr.equals(idString)) {
						String diagNameString=(String) map1.get("rltName");
						if(diagNameString.equals(rltName)) {
							PageResult.fail("规则名称["+rltName+"]"+"已存在");
		            		return false;
						}
					}
				}
			}
			if(idStr==null || "".equals(idStr)) {
				Map<String,Object> itemMap=new HashMap<String,Object>();
				if(rltName!=null && !"".equals(rltName)) {
					itemMap.put("rltName", rltName);
				}else {
					itemMap.put("rltName", "");
				}			   	
			   	if(userId!=null && !"".equals(userId)) {
			   		itemMap.put("userId", userId);
			   	}else {
			   		itemMap.put("userId", "");
			   	}				
				if(domainId!=null && !"".equals(domainId)) {
			   	    itemMap.put("domainId", domainId);
			   	}else {
			   	    itemMap.put("domainId", "");
			   	}
				List<Map<String,Object>> list=rltRuleDao.getIomCiRltRuleByRltName(itemMap);
				if(list!=null && list.size()>0) {
					PageResult.fail("规则名称["+rltName+"]"+"已存在");
            		return false;
				}
			}
			return rltRuleService.saveOrUpdateDataExtractionRules(map);
		}
		PageResult.fail("保存失败");
		return null;
	}
	
	/**
	 * 删除规则数据---配置报表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteDataExtractionRules", method = RequestMethod.DELETE)
	public Object deleteDataExtractionRules(String id) {
		Integer i=rltRuleService.delete(id);
		if(i>0) {
			PageResult.setMessage(true,"删除成功");
			return true;
		}
		return false;
	}
	
	/**
	 * 查询规则数据列表---配置报表
	 * @param rltName
	 * @return
	 */
	@RequestMapping(value = "/getDataExtractionRulesList", method = RequestMethod.GET)
	public PageResult getDataExtractionRulesList(String rltName) {
		MyPagUtile.startPage();
		List<Map<String,Object>> iomCiRltRules = rltRuleService.getList(rltName);
		for (Map<String,Object> map : iomCiRltRules) {
			String cjrId=(String) map.get("cjrId");
			Object resObject = czryService.findCzryByIdFeign(cjrId);
			if (resObject!=null) {
				JSONObject czry = JSONObject.parseObject(JSON.toJSONString(resObject));
				map.put("cjrName", czry.get("czry_mc"));

			}
			String ciTypeId=(String) map.get("typeId");
			Type type=typeService.findTypeById(ciTypeId);
			map.put("ciTypeBm", type.getCi_type_bm());
		}
		PageResult pageResult=MyPagUtile.getPageResult(iomCiRltRules);
		return pageResult;
	}
	
	/**
	 * 获取规则详情数据---配置报表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getDataExtractionRulesById", method = RequestMethod.GET)
	public Map<String, Object> getDataExtractionRulesById(String id) {
		return rltRuleService.get(id);
	}
	
	/**
	 * 获取配置报表数据---配置报表
	 * @param ruleId
	 * @return
	 */
	@RequestMapping(value = "/getDataExtractionRulesByruleId", method = RequestMethod.GET)
	public List<Map<String, Object>> getDataExtractionRulesByruleId(String ruleId) {
		return rltRuleService.getDataExtractionRulesByruleId(ruleId);
	}
	
	/**
	 * 下载配置报表
	 * @return
	 */
	@OptionLog(desc = "配置报表导出", module = "配置报表模块", writeParam = false, writeResult = true)
	@RequestMapping(value = "/exportConfigureReports", method = RequestMethod.POST)
	public void exportConfigureReports(@RequestBody String json,HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		String ruleId=(String) jsonObject.get("ruleId");
		String jsonArrayStr=(String) jsonObject.get("json");
		JSONArray jsonArray=JSONArray.parseArray(jsonArrayStr);
		JSONArray jSONArray=new JSONArray();
		List<Map<String, Object>> list=rltRuleService.getDataExtractionRulesByruleId(ruleId);
		if(jsonArray!=null && jsonArray.size()>0) {
			for(int i=0;i<jsonArray.size();i++){
				JSONObject object2= (JSONObject) jsonArray.get(i);
				String ciTypeId=(String) object2.get("ciTypeId");
				List<String> list2=(List<String>) object2.get("attr");
				for(String dataStr:list2) {
					JSONObject jSONObject=new JSONObject();
					jSONObject.put("ciTypeId", ciTypeId);
					jSONObject.put("attr", dataStr);
					jSONArray.add(jSONObject);
				}
			}
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String date = df.format(new Date());
            String fileName = "配置报表数据-"+date+ ".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(fileName);
            
            //设置表头
            CellRangeAddress region1 = new CellRangeAddress(0, 1, 0, 0);
            HSSFRow row = sheet.createRow(0);
            //设置列宽度
            sheet.setColumnWidth(0,30*180);
            sheet.setColumnWidth(1,30*100);
            sheet.setColumnWidth(2,30*100);
            sheet.setColumnWidth(3,30*100);
            sheet.setColumnWidth(4,30*150);
            sheet.setColumnWidth(5,30*300);
            sheet.setColumnWidth(6,30*150);
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            style.setFont(font);
            HSSFCell cell;
            Integer cellNum=0;
            //循环增加表头
            for(Map<String, Object> map:list) {
            	List<Map<String,Object>> attrList=(List<Map<String, Object>>) map.get("attr");           	
            	if(attrList!=null && attrList.size()>0) {
            		Map<String,Object> mapAttr=attrList.get(0);
            		String ciTypeId=(String) mapAttr.get("ciTypeId");
            		if(jSONArray!=null && jSONArray.size()>0) {
            			for(int i=0;i<jSONArray.size();i++){
           		            JSONObject object= (JSONObject) jSONArray.get(i);
           		            String ciTypeIdStr=(String) object.get("ciTypeId");
           		            String attrStr=(String) object.get("attr");
           		            if(ciTypeId.equals(ciTypeIdStr)) {
           		            	for(Map<String,Object> map3:attrList) {
           		            		String mpCiItem=(String) map3.get("mpCiItem");
           		            		String attrName=(String) map3.get("attrName");
           		            		if(mpCiItem.equals(attrStr)) {
           		            			cell = row.createCell(cellNum);
           		                        cell.setCellValue(attrName);
           		                        cell.setCellStyle(style);
           		                        cellNum+=1;
           		            		}
           		            	}
           		            }
           		        }
            		}
            		
            	}           	
            }
            //循环添加数据
            Integer num=0;
            if(list!=null && list.size()>0) {
            	Map<String,Object> map=list.get(0);
            	if(map!=null && map.size()>0) {
            		List<Map<String,Object>> rowsList=(List<Map<String, Object>>) map.get("rows");
            		if(rowsList!=null && rowsList.size()>0) {
            			num=rowsList.size();
            		}
            	}
            }
            
            for(int i=0;i<num;i++) {
            	row = sheet.createRow(i+1);
            	Integer attrNum=0;
            	for(int j=0;j<list.size();j++) {
                	Map<String,Object> map=list.get(j);                	
                	if(map!=null && map.size()>0) {
                		List<Map<String,Object>> rowsList=(List<Map<String, Object>>) map.get("rows");
                		Map<String,Object> map1=rowsList.get(i);
                		String ciTypeId=(String) map1.get("ciTypeId");
                		for(int k=0;k<jSONArray.size();k++){
                			JSONObject object= (JSONObject) jSONArray.get(k);
           		            String ciTypeIdStr=(String) object.get("ciTypeId");
           		            String attrStr=(String) object.get("attr");
           		            if(ciTypeId.equals(ciTypeIdStr)) {
           		            	String attrVal=(String) map1.get(attrStr);
           		            	row.createCell(attrNum).setCellValue(attrVal);
           		            	attrNum+=1;
           		            }
                		}
                	}              	
                }
            }
            //浏览器下载excel
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName, "UTF-8"));
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 根据规则ID查询大类属性以及类数据
	 * @return
	 */
	@RequestMapping(value = "/getItemDataAllByruleId", method = RequestMethod.GET)
	public List<Map<String, Object>> getItemDataAllByruleId(String ruleId) {
		return rltRuleService.getItemDataAllByruleId(ruleId);
	}
	
	@RequestMapping(value = "/getCiRelByLineBm", method = RequestMethod.GET)
	public List<Map<String, Object>> getCiRelByLineBm(String lineBm) {
		return null;
	}
	
}
