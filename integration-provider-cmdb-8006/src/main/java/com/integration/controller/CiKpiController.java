package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.dao.CiKpiDao;
import com.integration.dao.CiKpiTypeDao;
import com.integration.dao.IomCiKpiClassDao;
import com.integration.entity.*;
import com.integration.feign.AlarmService;
import com.integration.feign.PmvService;
import com.integration.generator.dao.IomCiKpiMapper;
import com.integration.generator.dao.IomCiKpiTypeMapper;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.entity.IomCiKpi;
import com.integration.generator.entity.*;
import com.integration.service.CiKpiService;
import com.integration.service.CiKpiTypeService;
import com.integration.service.TypeService;
import com.integration.utils.*;

import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-11 05:46:51
 */

@RestController
public class CiKpiController {
    private static final Logger logger = LoggerFactory.getLogger(CiKpiController.class);

    @Autowired
    private CiKpiService ciKpiService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private CiKpiTypeService ciKpiTypeService;
    @Autowired
    private AlarmService alarmService;
    @Resource
    private IomCiKpiTypeMapper iomCiKpiTypeMapper;
    @Resource
    private IomCiTypeMapper iomCiTypeMapper;
    @Autowired
    private IomCiKpiClassDao iomCiKpiClassDao;
    @Autowired
    private PmvService pmvService;

    /**
     * 分页查询所有
     *
     * @param beginNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getAllPage", method = RequestMethod.GET)
    public PageResult getAllPage(String beginNum, String pageSize) {
        HashMap<String, String> map = new HashMap<>();
        map.put(beginNum, pageSize);
        List<CiKpiInfo> allPageList = ciKpiService.getAllPage(map);
        return DataUtils.returnPr(allPageList);
    }

    /**
     * 获取所有对象组
     *
     * @return
     */
    @RequestMapping(value = "/getObject", method = RequestMethod.GET)
    public List<Type> getObject() {
        List<Type> typeList = typeService.findTypeList();
        return typeList;
    }

    /**
     * 新增模型
     *
     * @param info
     * @param ciKpiThres
     * @param objectName
     * @param request
     * @return
     */
    @OptionLog(desc = "新增模块", module = "对象模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addKpi", method = RequestMethod.POST)
    public boolean addCiKpi(CiKpiInfo info, String[] ciKpiThres, String[] objectName,
                            HttpServletRequest request) {
        int i = 0;
        boolean flag = false;
        info.setIs_match(1);
        i = ciKpiService.insertInfo(info, ciKpiThres, objectName, request);
        if (i > 0) {
            flag = true;
            PageResult.setMessage("添加成功", true);
        } else {
            flag = false;
            PageResult.setMessage("添加失败", false);

        }
        return flag;

    }

    /**
     * 新增模型（对外服务）
     *
     * @param info
     * @param ciKpiThres
     * @param objectName
     * @param request
     * @return
     */
    @RequestMapping(value = "/addKpiCast", method = RequestMethod.POST)
    public boolean addCiKpiCast(@RequestBody CiKpiInfo info, @RequestParam("ciKpiThres") String[] ciKpiThres, @RequestParam("objectName") String[] objectName,
                                HttpServletRequest request) {
        //成功失败标志
        boolean flag = false;
        //校验objectName是否存在值
        if (objectName == null || objectName.length == 0) {
            PageResult.fail("添加失败,缺少关键参数");
            return false;
        }
        int i = 0;
        i = ciKpiService.insertInfo(info, ciKpiThres, objectName, request);
        if (i > 0) {
            flag = true;
            PageResult.success("添加成功");
        } else {
            flag = false;
            PageResult.fail("添加失败");
        }
        return flag;

    }

    /**
     * 获取模型和对象的组合实体
     * @param search 查询条件
     * @return
     */
    @RequestMapping(value = "/getKpi", method = RequestMethod.GET)
    public PageResult getKpiType(String search) {
        List<Kpi_Type> list = new ArrayList<>();
        try {
            list = ciKpiService.getKpi_Type(search);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        return DataUtils.returnPr(true, "查询成功", JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
    }

    /**
     * 获取模型和对象的组合实体(分页)
     * @param search 查询条件
     * @return
     */
    @RequestMapping(value = "/getKpiFY", method = RequestMethod.POST)
    public PageResult getKpiTypeFY(String search, String isMatch) {
        PageResult pageResult = new PageResult();
        MyPagUtile.startPage();
        List<Kpi_Type> list = ciKpiService.getKpi_Type(search, isMatch);
        pageResult.setReturnBoolean(true);
        pageResult.setReturnObject(JSON.parseArray(JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect), Kpi_Type.class));
        pageResult.setTotalPage(ciKpiService.getKpi_TypeCount(search, isMatch));
        return pageResult;
    }

    /**
     * 导出 模型和对象的组合实体
     * @param search 查询条件
     * @return
     */
    @RequestMapping(value = "/exportKpiFY", method = RequestMethod.GET)
    public void exportKpiFY(String search, String isMatch, HttpServletResponse response) {
        List<Kpi_Type> list = ciKpiService.getKpi_Type(search, isMatch);
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String date = df.format(new Date());
            String fileName = "指标模型数据-"+date+ ".xls";
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

            cell = row.createCell(0);
            cell.setCellValue("指标名");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("别名");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("描述");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("单位");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("指标大类");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("对象组");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("是否匹配字段");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("最大值");
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue("最小值");
            cell.setCellStyle(style);

            //填充内容
            for (int i = 0; i < list.size(); i++) {
                List<Type> typeList = list.get(i).getObj_name();
                String objName = "";
                if (typeList!=null && typeList.size()>0){
                    for (Type type:typeList) {
                        objName += type.getCi_type_bm()+",";
                    }
                    objName = objName.substring(0,objName.lastIndexOf(","));
                }
                row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(list.get(i).getKpi_name());
                row.createCell(1).setCellValue(list.get(i).getKpi_alias());
                row.createCell(2).setCellValue(list.get(i).getKpi_desc());
                row.createCell(3).setCellValue(list.get(i).getVal_unit());
                row.createCell(4).setCellValue(list.get(i).getKpi_class_name());
                row.createCell(5).setCellValue(objName);
                row.createCell(6).setCellValue(list.get(i).getIs_match()==1?"是":"否");
                row.createCell(7).setCellValue(list.get(i).getMaximum()==null?null:list.get(i).getMaximum().toString());
                row.createCell(8).setCellValue(list.get(i).getMinimum()==null?null:list.get(i).getMinimum().toString());
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
     * 导入 模型和对象的组合实体
     * @return
     */
    @RequestMapping(value = "/importKpiFY", method = RequestMethod.POST)
    public PageResult importKpiFY(MultipartFile file, HttpServletResponse response) {
        PageResult pageResult = new PageResult();
        String result = null;
        try{
            result = ImportExeclUtil.checkData(file);
            if (result!=null){
                pageResult.setReturnBoolean(false);
                pageResult.setReturnMessage(result);
                return pageResult;
            }
            List list = ImportExeclUtil.getDataList(file);
            pageResult = ciKpiService.importKpiFY(list);
            ciKpiService.clearCiKpiInfoRedisCache();
            return pageResult;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("导入指标异常：",e);
            pageResult.setReturnBoolean(false);
            pageResult.setReturnMessage(e.getMessage());
            return pageResult;
        }
    }

    /**
     * 根据ciTypeId获取指标getKpiFY
     *
     * @param ciTypeId
     * @return
     */
    @RequestMapping(value = "/selectKpiByCiTypeId", method = RequestMethod.GET)
    public Object selectKpiByCiTypeId(String ciTypeId) {
        return ciKpiService.selectKpiByCiTypeId(ciTypeId);
    }
    
    /**
     * 根据ciTypeId获取指标getKpiFY(增加排序)
     *
     * @param ciTypeId
     * @return
     */
    @RequestMapping(value = "/selectKpiByCiTypeIdSort", method = RequestMethod.GET)
    public Object selectKpiByCiTypeIdSort(String ciTypeId) {
        return ciKpiService.selectKpiByCiTypeIdSort(ciTypeId);
    }
    
    @RequestMapping(value = "/selectKpiByCiTypeIdByPerformanceChart", method = RequestMethod.GET)
    public Object selectKpiByCiTypeIdByPerformanceChart(String ciTypeId) {
//    	List<Map<String,Object>> list=ciKpiService.selectKpiByCiTypeIdByPerformanceChart(ciTypeId);
//    	for(int i=0;i<list.size();i++) {    		
//    		Map<String,Object> map=list.get(i);
//    		System.out.println(i+"------"+map);
//    	}
    	String domainId=TokenUtils.getTokenOrgDomainId();
        return ciKpiService.selectKpiByCiTypeIdByPerformanceChart(ciTypeId,domainId);
    }
    
    /**
     * 根据ciTypeId获取指标getKpiFY(分页)
     *
     * @param ciTypeId
     * @return
     */
    @RequestMapping(value = "/selectKpiByCiTypeIdByPaging", method = RequestMethod.GET)
    public Object selectKpiByCiTypeIdByPaging(String ciTypeId,String pageNum,String pageSize) {
        return ciKpiService.selectKpiByCiTypeIdByPaging(ciTypeId,pageNum,pageSize);
    }
    
    /**
     * 根据ciTypeId获取指标getKpiFY(分页)---维秘查询指标性能曲线
     *
     * @param ciTypeId
     * @return
     */
    @RequestMapping(value = "/queryPerformanceCurveByCiId", method = RequestMethod.GET)
    public Object queryPerformanceCurveByCiId(String ciId,String ciTypeId,String pageNum,String pageSize,String userId,String domainId,String kpiName,String ciCode) {
    	List<Map<String,Object>> ciKpiinfoList=ciKpiService.queryPerformanceCurveByCiId(ciTypeId, pageNum, pageSize,domainId,kpiName);
    	if(ciKpiinfoList!=null && ciKpiinfoList.size()>0) {
    		List<String> kpiClassIdList=new ArrayList<String>();
    		for(Map<String,Object> map:ciKpiinfoList) {
    			String kpiClassId=(String) map.get("kpiClassId");
    			if(kpiClassId==null || "".equals(kpiClassId)) {
    				continue;
    			}
    			kpiClassIdList.add(kpiClassId);
    		}
    		Map<String,Object> itemMap=new HashMap<String,Object>();
    		if(kpiClassIdList!=null && kpiClassIdList.size()>0) {
    			String [] idsArray = kpiClassIdList.toArray(new String[kpiClassIdList.size()]);
    			itemMap.put("kpiClassIdList", idsArray);
    		}else {
    			itemMap.put("kpiClassIdList", "");	
    		}
    		if(domainId!=null && !"".equals(domainId)) {
    			itemMap.put("domainId", domainId);
    		}else {
    			itemMap.put("domainId", null);
    		}
    		List<Map<String,Object>> list=iomCiKpiClassDao.getCiKpiClassInfoByKpiClassIds(itemMap);
    		if(list!=null && list.size()>0) {
    			for(Map<String,Object> map:list) {
    				String id=(String) map.get("id");
    				String name=(String) map.get("name");
    				for(Map<String,Object> mapKpi:ciKpiinfoList) {
    					String kpiClassId=(String) mapKpi.get("kpiClassId");
    					if(id.equals(kpiClassId)) {
    						mapKpi.put("kpiClassName", name);
    					}
    				}
    			}
    		}
    	}
    	int count=ciKpiService.selectKpiByCiTypeIdByPagingCount(ciTypeId,domainId,kpiName);
    	Map<String,Object> returnMap=new HashMap<String,Object>();
    	returnMap.put("total", count);
    	returnMap.put("data", ciKpiinfoList);
        return returnMap;
    }
    


    /**
     * 删除模型
     *
     * @param kpiId
     * @return
     */
    @OptionLog(desc = "删除模型", module = "对象模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteKpi", method = RequestMethod.POST)
    public boolean deleteKpiType(String kpiId) {
        //成功失败标志
        boolean flag;
        int i = 0;
        i = ciKpiService.deleteInfo(kpiId);

        if (i > 0) {
            flag = true;
            PageResult.success("删除成功");
        } else {
            flag = false;
            PageResult.fail("删除失败");
        }
        return flag;
    }

    /**
     * 修改模型
     *
     * @param info
     * @param ciKpiThres
     * @param objectName
     * @param request
     * @return
     */
    @OptionLog(desc = "修改模型对象", module = "对象模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateKpi", method = RequestMethod.POST)
    public boolean updateKpiType(CiKpiInfo info, String[] ciKpiThres, String[] objectName,
                                 HttpServletRequest request) {
        //成功失败标志
        boolean flag;
        int i = 0;
        i = ciKpiService.updateInfo(info, ciKpiThres, objectName, request);

        if (i > 0) {
            flag = true;
            PageResult.success("修改成功");
            //若选择的是否匹配为：匹配（1），将未丰富表中的数据KPIId一样的修改YXBZ
            if (null!=info.getIs_match() && info.getIs_match() == 1) {
                alarmService.updUnMatchByKpiId(info.getId());
            }
        } else {
            flag = false;
            PageResult.fail("修改失败");
        }
        return flag;
    }

    /**
     * 修改模型(对外服务)
     *
     * @param info
     * @param ciKpiThres
     * @param objectName
     * @param request
     * @return
     */
    @OptionLog(desc = "修改模型对象", module = "对象模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateKpiTypeCast", method = RequestMethod.POST)
    public boolean updateKpiTypeCast(@RequestBody CiKpiInfo info, @RequestParam("ciKpiThres") String[] ciKpiThres, @RequestParam("objectName") String[] objectName,
                                     HttpServletRequest request) {
        //成功失败标志
        boolean flag;
        int i = 0;
        i = ciKpiService.updateInfo(info, ciKpiThres, objectName, request);

        if (i > 0) {
            flag = true;
            PageResult.success("修改成功");
        } else {
            flag = false;
            PageResult.fail("修改失败");
        }
        return flag;
    }

    /**
     * @Method findById
     * @Author sgh
     * @Version 1.0
     * @Description 根据id获取KPI名称信息
     * @Return com.integration.entity.CiKpiInfo
     * @Exception
     * @Date 2019/11/1
     * @Param [id]
     */
    @RequestMapping("/kpi/findById")
    public String findById(@RequestParam("id") String id) {
        return ciKpiService.getInfo(id).getKpi_name();
    }

    /**
     * @Method findByNameKPI
     * @Author sgh
     * @Version 1.0
     * @Description 根据name获取KPIID
     * @Return java.lang.String
     * @Exception
     * @Date 2019/11/1
     * @Param [name]
     */
    @RequestMapping("/kpi/findByNameKPI")
    public String findByNameKPI(@RequestParam("name") String name) {
        CiKpiInfo ciKpiInfo = ciKpiService.findByNameKPI(name);
        if (ciKpiInfo == null) {
            return null;
        }
        String json = JSON.toJSONString(ciKpiInfo);
        return json;
    }

    /**
     * @Method findByNameKPI
     * @Author sgh
     * @Version 1.0
     * @Description 根据name获取KPIID返回Boolean
     * @Return java.lang.Boolean
     * @Exception
     * @Date 2019/11/1
     * @Param [name]
     */
    @RequestMapping("/kpi/findByNameKPIB")
    public Boolean findByNameKPIB(@RequestParam("name") String name) {
        CiKpiInfo ciKpiInfo = ciKpiService.findByNameKPI(name);
        if (ciKpiInfo == null) {
            return false;
        }
        return true;
    }

    /**
     * @Method findByNameKPI
     * @Author sgh
     * @Version 1.0
     * @Description 根据name获取KPI信息
     * @Return java.lang.String
     * @Exception
     * @Date 2019/11/1
     * @Param [name]
     */
    @RequestMapping("/kpi/findByNameKPIClass")
    public Map findByNameKPIClass(@RequestParam("name") String name) {
    	String key = "kpi_"+name;
    	if (RedisUtils.exists(key)) {
			return (Map) RedisUtils.get(key);
		}
    	Map map = ciKpiService.findByNameKPIClass(name);
    	if (map!=null && map.size()>0) {
            RedisUtils.set(key,map,20*60*1000L);
		}
        return map;
    }
    
    /**
     * @Method findKpiAndSave
     * @Author tzq
     * @Version 1.0
     * @Description 根据name获取KPI信息
     * @Return java.lang.String
     * @Exception
     * @Date 2021/07/08
     * @Param [name]
     */
    @RequestMapping(value = "/kpi/findKpiAndSave", method = RequestMethod.POST)
    public Map findKpiAndSave(String kpiName,String domainId,String valUnit,String ciTypeId) {
    	String key = "kpi_"+kpiName;
    	Map map = ciKpiService.findByNameKPIClass(kpiName);
    	if (map!=null && map.size()>0) {
            RedisUtils.set(key,map,20*60*1000L);
    		return map;
		}else {
			IomCiKpi iomCiKpi=new IomCiKpi();
			iomCiKpi.setValUnit(valUnit);
			iomCiKpi.setDomainId(Integer.valueOf(domainId));
			iomCiKpi.setKpiName(kpiName);
			iomCiKpi.setYxbz(1);
			iomCiKpi.setCjrId("4");
			iomCiKpi.setTypeId(ciTypeId);
			String id = SeqUtil.nextId().toString();
	        iomCiKpi.setIsMatch(0);
	        iomCiKpi.setId(id);
	        iomCiKpi.setCjsj(new Date());
	        iomCiKpi.setCjrId("4");
	        int i=iomCiKpiMapper.insert(iomCiKpi);
	        if(i>0) {
	        	try {
					map=convertBean(iomCiKpi);
					return map;
				} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
					e.printStackTrace();
				}
	        	
	        }
		}
        return map;
    }
    
    
    /**
     * @Method findByNameKPI
     * @Author sgh
     * @Version 1.0
     * @Description 根据name获取KPI信息
     * @Return java.lang.String
     * @Exception
     * @Date 2020/12/14
     * @Param [name]
     */
    @RequestMapping("/kpi/findByKpiNameKPIClass")
    public Map findByKpiNameKPIClass(@RequestParam("name") String name) {
    	String key = "kpi_"+name;   	
    	Map map = ciKpiService.findByNameKPIClass(name);
    	if (map!=null && map.size()>0) {
            RedisUtils.set(key,map,20*60*1000L);
		}
        return map;
    }

    /**
     * @Method findByNameKPI
     * @Author sgh
     * @Version 1.0
     * @Description 根据name获取KPI信息
     * @Return java.lang.String
     * @Exception
     * @Date 2019/11/1
     * @Param [name]
     */
    @RequestMapping("/kpi/findByNameKPIClassByZ")
    public Map findByNameKPIClassByZ(@RequestParam("name") String name, String s) {
        Map<String, Object> map = new HashMap<>();
        CiKpiInfo ciKpiInfo = ciKpiService.findByNameKPI(name);
        if (ciKpiInfo == null) {
            return null;
        }
        List<String> strings = ciKpiTypeService.findById(ciKpiInfo.getId());
        List<CiKpiThres> ciKpiThres = ciKpiTypeService.findByThres(ciKpiInfo.getId());
        map.put("ciKpiInfo", ciKpiInfo);
        map.put("obdId", strings);
        map.put("ciKpiThres", ciKpiThres);
        return map;
    }

    /**
     * @Method findByListClassId
     * @Author sgh
     * @Version 1.0
     * @Description 根据多个大类id获取KPI集合
     * @Return java.util.List<com.integration.entity.CiKpiInfo>
     * @Exception
     * @Date 2019/11/1
     * @Param [ids]
     */
    @RequestMapping("/kpi/findByListClassId")
    public List<Map> findByListClassId(@RequestParam("ids") String ids) {
        if (ids != null && !"".equals(ids)) {
            return ciKpiService.findByListClassId(ids);
        } else {
            return null;
        }
    }

    /**
     * @Method saveKpi
     * @Author sgh
     * @Version 1.0
     * @Description 添加
     * @Return String
     * @Exception
     * @Date 2019/11/1
     * @Param [ciKpiInfo]
     */
    @RequestMapping("/kpi/saveKpi")
    public String saveKpi(@RequestBody CiKpiInfo ciKpiInfo) {
        return ciKpiService.saveKpi(ciKpiInfo);
    }

    /**
     * @Method saveKpiByPmvSavePerformance(8009pmv使用)
     * @Author agh
     * @Version 1.0
     * @Description 添加
     * @Return String
     * @Exception
     * @Date 2019/11/4
     * @Param [ciKpiInfo]
     */
    @RequestMapping("/kpi/saveKpiByPmvSavePerformance")
    public Map<String, Object> saveKpiByPmvSavePerformance(@RequestBody IomCiKpi iomCiKpi) {
        return ciKpiService.saveKpiByPmvSavePerformance(iomCiKpi);
    }
    
    /**
     * @Method getIomCiKpiTypeByKpiIdAndCiTypeId(8009pmv使用)
     * @Author 
     * @Version 
     * @Description 
     * @Return 
     * @Exception
     * @Date 
     * @Param 
     */
    @RequestMapping("/kpi/getIomCiKpiTypeByKpiIdAndCiTypeId")
    public String getIomCiKpiTypeByKpiIdAndCiTypeId(String kpiId,String ciTypeId) {
        return ciKpiService.getIomCiKpiTypeByKpiIdAndCiTypeId(kpiId, ciTypeId);
    }
    
    /**
     * @Method getIomCiKpiTypeByKpiIdAndCiTypeId(8009pmv使用，改成先去redis去查指标与CI大类的关系)
     * @Author 
     * @Version 
     * @Description 
     * @Return 
     * @Exception
     * @Date 
     * @Param 
     */
    @RequestMapping("/kpi/insertIomCiKpiTypeByKpiIdAndCiTypeId")
    public String insertIomCiKpiTypeByKpiIdAndCiTypeId(@RequestBody Map<String, String> map) {
        return ciKpiService.insertIomCiKpiTypeByKpiIdAndCiTypeId(map);
    }

    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 获取指标对应的所有CI信息
     */
    @RequestMapping(value = "/getCiKpiAll", method = RequestMethod.GET)
    public PageResult getCiKpiAll(String kpiName, String pageNum, String pageSize,String kpiClassId) {
        MyPagUtile.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = ciKpiService.getCiKpiAll(kpiName,kpiClassId);
        PageResult pageResult=MyPagUtile.getPageResult(list);
        return pageResult;
    }

    @Resource
    private IomCiKpiMapper iomCiKpiMapper;

    /**
     * 根据ids获取指标
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/getCiKpiByIds", method = RequestMethod.POST)
    public List<IomCiKpi> getCiKpiByIds(@RequestBody String ids) {
        List<IomCiKpi> res = new ArrayList<IomCiKpi>();
        if (StringUtils.isNotEmpty(ids)) {
            IomCiKpiExample iomCiKpiExample = new IomCiKpiExample();
            com.integration.generator.entity.IomCiKpiExample.Criteria criteria = iomCiKpiExample.createCriteria();
            criteria.andIdIn(Arrays.asList(ids.split(",")));
            criteria.andYxbzEqualTo(1);
            res = iomCiKpiMapper.selectByExample(iomCiKpiExample);
        }
        return res;
    }
    
    /**
     * 根据ids获取指标
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/getCiKpiByIdsToJniom", method = RequestMethod.GET)
    public List<IomCiKpi> getCiKpiByIdsToJniom(String ids) {
        List<IomCiKpi> res = new ArrayList<IomCiKpi>();
        if (StringUtils.isNotEmpty(ids)) {
            IomCiKpiExample iomCiKpiExample = new IomCiKpiExample();
            com.integration.generator.entity.IomCiKpiExample.Criteria criteria = iomCiKpiExample.createCriteria();
            criteria.andIdIn(Arrays.asList(ids.split(",")));
            criteria.andYxbzEqualTo(1);
            res = iomCiKpiMapper.selectByExample(iomCiKpiExample);
        }
        return res;
    }
    
    /**
     * 根据ids获取指标
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/getCiKpiInfosByIds", method = RequestMethod.GET)
    public List<Map<String,Object>> getCiKpiInfosByIds(String ids,String kpiNames){
    	List<Map<String,Object>> res=new ArrayList<Map<String,Object>>();
    	if(kpiNames!=null && !"".equals(kpiNames)) {
    		Map<String,Object> itemMap=new HashMap<String,Object>();
        	List<String> kpiNameList=Arrays.asList(kpiNames.split(","));
        	String [] kpiNamesArray = kpiNameList.toArray(new String[kpiNameList.size()]);
        	itemMap.put("kpiNameList", kpiNamesArray);
        	res=ciKpiService.getCiKpiInfosByIds(itemMap);
    	}
    	return res;
    }
    

    /**
     * @Method findByKpiProperty
     * @Author sgh
     * @Version 1.0
     * @Description 根据KPiID查询指标某个属性值
     * @Return java.lang.Object
     * @Exception
     * @Date 2019/12/3
     * @Param [propertyName, kpiId]
     */
    @GetMapping("findByKpiProperty")
    public Object findByKpiProperty(String propertyName, String kpiId) {
        return ciKpiService.findByKpiProperty(propertyName, kpiId);
    }


    /**
     * @Method findCIidList
     * @Author sgh
     * @Version 1.0
     * @Description 根据CICODE和CI大类ID获取CIID列表
     * @Return java.util.List<java.lang.String>
     * @Exception
     * @Date 2019/12/3
     * @Param [ciCode, ciTypeId]
     */
    @GetMapping("findCIidList")
    public List<String> findCIidList(String ciCode,String type,String valName,String typeValName) {
        List<String> stringList = new ArrayList<>();
        if (ciCode == null) {
            return null;
        }
        stringList = ciKpiService.findCIidList(ciCode, type, valName, typeValName);
        return stringList;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 根据指标ID获取指标信息
     */
    @RequestMapping(value = "/getKpiInfoByKpiIds", method = RequestMethod.GET)
    public List<Map<String, Object>> getKpiInfoByKpiIds(String kpiIds) {
    	List<String> list= Arrays.asList(kpiIds.split(","));
    	List<Map<String, Object>> list2=ciKpiService.getKpiInfoByKpiIds(list);
    	return list2;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 根据指标名称获取指标信息
     */
    @RequestMapping(value = "/getKpiByKpiIds", method = RequestMethod.POST)
    public List<Map<String, Object>> getKpiByKpiIds(String kpiIds,String kpiNames) {
    	List<String> list= Arrays.asList(kpiNames.split(","));
    	Map<String, Object> itemMap=new HashMap<String, Object>();
    	String [] kpiNamesArray = list.toArray(new String[list.size()]);
		itemMap.put("kpiNameList", kpiNamesArray);  	
    	List<Map<String, Object>> list2=ciKpiService.getKpiByKpiIds(itemMap);
    	return list2;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 根据指标名称获取指标信息（SMV）
     */
    @RequestMapping(value = "/getKpiByKpiIdsBySmv", method = RequestMethod.POST)
    public List<Map<String, Object>> getKpiByKpiIdsBySmv(String kpiIds,String kpiNames) {
    	List<String> list= Arrays.asList(kpiNames.split(","));
    	Map<String, Object> itemMap=new HashMap<String, Object>();
		String [] kpiNamesArray = list.toArray(new String[list.size()]);
		itemMap.put("kpiNameList", kpiNamesArray);
    	List<Map<String, Object>> list2=ciKpiService.getKpiByKpiIds(itemMap);
    	return list2;
    }

    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 根据指标名称获取指标数据
     */
    @RequestMapping(value = "/getKpiByKpiNames", method = RequestMethod.POST)
    public List<Map<String, Object>> getKpiByKpiNames(String kpiNames) {
    	List<String> list= Arrays.asList(kpiNames.split(","));
    	Map<String, Object> itemMap=new HashMap<String, Object>();
		String [] namesArray = list.toArray(new String[list.size()]);
		itemMap.put("kpiNameList", namesArray);
    	List<Map<String, Object>> list2=ciKpiService.getKpiByKpiNames(itemMap);
    	return list2;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 根据指标ID获取大类信息
     */
    @RequestMapping(value = "/getCiTypeInfoByKpiIds", method = RequestMethod.POST)
    public List<Map<String, Object>> getCiTypeInfoByKpiIds(@RequestBody String kpiIds) {
    	List<String> kpiIdsList= Arrays.asList(kpiIds.split(","));
    	Map<String, Object> itemMap=new HashMap<String, Object>();
		String [] idsArray = kpiIdsList.toArray(new String[kpiIdsList.size()]);
		itemMap.put("kpiIdList", idsArray);
		List<String> ciTypeIdsList=new ArrayList<String>();
		if(kpiIds!=null && !"".equals(kpiIds)) {
			ciTypeIdsList=ciKpiService.getCiTypeInfoByKpiIds(itemMap);
			if(ciTypeIdsList!=null && ciTypeIdsList.size()>0) {
				//去重
				ciTypeIdsList=ciTypeIdsList.stream()
		                 .distinct()
		                 .collect(Collectors.toList());
				String ciTypeIds=String.join(",", ciTypeIdsList);
				List<Map<String,Object>> returnList=typeService.findCiTypeListHumpByIds(ciTypeIds);
				return returnList;
			}
		}else {
			return null;
		}   	
    	return null;
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description:根据指标ID获取指标对应单位
     */
    @RequestMapping(value = "/getKpiUnitByKpiIds", method = RequestMethod.POST)
    public List<Map<String, Object>> getKpiUnitByKpiIds(@RequestBody Map<String, Object> map) {
        ArrayList jsonArray=(ArrayList) map.get("kpiIds");
        String[] arr = new String[jsonArray.size()];
        for(int i=0;i<jsonArray.size();i++) {
        	String numString=(String) jsonArray.get(i);
        	arr[i]=numString;
        }
   	  if (arr != null && arr.length > 0) {
   		List<Map<String, Object>> list2=ciKpiService.getKpiUnitByKpiIds(Arrays.asList(arr));
  		return list2;
       }
    	
    	return new ArrayList<Map<String, Object>>();
    }
    

    @RequestMapping(value = "/getCiCode",method = RequestMethod.POST)
    public List<String> getCiCode(@RequestParam("ciValue") String ciValue){
        String[] sz = ciValue.split(",");
        List<String> stringList1 = Arrays.asList(sz);
        List<String> stringList = ciKpiService.getCiCode(stringList1);
        return stringList;
    }

    @RequestMapping(value = "/insertCikpiTypeByPmv",method = RequestMethod.POST)
    public void insertCikpiTypeByPmv(@RequestBody Map<String, Object> map){
    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("data");
    		for(Map<String, Object> map2:list) {
    			String ciTypeId=(String) map2.get("ciTypeId");
        		String kpiId=(String) map2.get("kpiId");
        		String key = kpiId+"_"+ciTypeId;
        		if (!RedisUtils.exists(key)) {
        			try {
                        RedisUtils.set(key,map2);
        				IomCiKpiType iomCiKpiType=new IomCiKpiType();
                		iomCiKpiType.setCjsj(new Date());
                		iomCiKpiType.setKpiId(kpiId);
                		iomCiKpiType.setObjId(ciTypeId);
                		iomCiKpiType.setId(SeqUtil.nextId() + "");
                		iomCiKpiType.setYxbz(1);
                		iomCiKpiType.setObjType(2);
                		iomCiKpiTypeMapper.insertSelective(iomCiKpiType);
					} catch (Exception e) {
						e.printStackTrace();
					}
        			    			     		
        		}
            		
        		
    		}
    	
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 获取大类数据（驼峰）
     */
	@RequestMapping(value = "/findCiTypeListHump",method = RequestMethod.POST)
    public List<Map<String, Object>> findCiTypeListHump(String kpiId,String kpiName){
		List<String> kpiIdList=new ArrayList<String>();
		if(kpiName!=null && !"".equals(kpiName)) {
			List<String> kpiNameList=Arrays.asList(kpiName.split(","));
			List<Map<String, Object>> list=ciKpiService.getKpiInfoByKpiIds(kpiNameList);
			if(list!=null && list.size()>0) {
				for(Map<String, Object> map:list) {
					String kpiIdStr=(String) map.get("id");
					kpiIdList.add(kpiIdStr);
				}
			}
		}	
		if(kpiId!=null && !"".equals(kpiId)) {
			kpiIdList=Arrays.asList(kpiId.split(","));
		}
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
    	List<Map<String, Object>> returnList=new ArrayList<Map<String,Object>>();
    	IomCiKpiTypeExample example=new IomCiKpiTypeExample();
    	example.createCriteria().andYxbzEqualTo(1).andKpiIdIn(kpiIdList);
    	List<IomCiKpiType> list=iomCiKpiTypeMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return null;
        }
    	List<String> ciTypeIdsList=new ArrayList<String>();
    	if(list!=null && list.size()>0) {
    		for(IomCiKpiType iomCiKpiType :list) {
    			String ciTypeId=iomCiKpiType.getObjId();
    			ciTypeIdsList.add(ciTypeId);
        	}
    	}

    	IomCiTypeExample exampleCiType=new IomCiTypeExample();
    	if (domainId!=null){
            exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList).andDomainIdIn(Arrays.asList(domainId.split(",")));
        }else{
            exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList);
        }
    	List<IomCiType> list2=iomCiTypeMapper.selectByExample(exampleCiType);
    	if(list2!=null && list2.size()>0) {
    		for(IomCiType iomCiType:list2){
    			try {
					Map<String, Object> map=bean2map(iomCiType);
					returnList.add(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	}
    	return returnList;

    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 把JavaBean转化为map
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
     * @Method findByListSource
     * @Author sgh
     * @Version 1.0
     * @Description 根据来源id获取KPI集合
     * @Return java.util.List<com.integration.entity.CiKpiInfo>
     * @Exception
     * @Date 2020/05/22
     * @Param [ids]
     */
    @RequestMapping("/kpi/findByListSource")
    public List<Map<String, Object>> findByListSource(@RequestParam("ids") String ids) {
        if (ids != null && !"".equals(ids)) {
            return ciKpiService.findByListSource(ids);
        } else {
            return null;
        }
    }

    /**
     * @Method findCisByClassName
     * @Description 根据CI大类名称查询相关的ci信息
     * @Param [className]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/2 15:33
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("ci/findCisByClassName")
    public List<Map<String, Object>> findCisByClassName(@RequestParam("className") String className) {
        return ciKpiService.findCisByClassName(className);
    }

    /**
     * @Method findCisByType
     * @Description 通过某个属性值，获取ci信息
     * @Param [type, typeVal]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/2 16:35
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("ci/findCIsByType")
    public List<Map<String, Object>> findCisByType(String type, String typeVal) {
        return ciKpiService.findCisByType(type, typeVal);
    }


    /**
     * @Method findCisByCiTypeId
     * @Description 根据ci大类获取ciId集合
     * @Param [ciTypeId]
     * @Return java.lang.String
     * @Author sgh
     * @Date 2020/6/11 9:52
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("ci/findCisByCiTypeId")
    public String findCisByCiTypeId(String ciTypeId) {
        return ciKpiService.findCisByCiTypeId(ciTypeId);
    }
    
    /**
     * @Method 
     * @Description 根据ciId查询是否关联指标 
     * @Param 
     * @Return 
     * @Author 
     * @Date 
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("ci/getHangKpiByCiId")
    public Integer getHangKpiByCiId(String ciId,String ciCode) {
    	//获取域ID
    	String domainId = TokenUtils.getTokenDataDomainId();
        if (ciCode != null && !"".equals(ciCode)) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            if(ciCode!=null && !"".equals(ciCode)) {
  			    itemMap.put("ciCodeList", ciCode.split(","));
  		    }else {
  			    itemMap.put("ciCodeList", "");
  		    }
            
            if(domainId!=null && !"".equals(domainId)) {
    			itemMap.put("domainId", domainId);
    		}else {
    			itemMap.put("domainId", "");
    		}
            List<Map<String, Object>> listMap = typeService.findCiInfoByCiIds(itemMap);
            if(listMap!=null && listMap.size()>0) {
            	Map<String,Object> map=listMap.get(0);
            	String ciTypeId=(String) map.get("ciTypeId");
            	int count=ciKpiTypeService.getHangKpiByCiId(ciTypeId);
            	return count;
            }
        }
        return null;
    }

    /**
     * @Method findCisByMap
     * @Description 根据条件查询ciId（逗号隔开的字符串）
     * @Param [m]
     * @Return java.lang.String
     * @Author sgh
     * @Date 2020/6/12 11:31
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "ci/findCisByMap")
    String findCisByMap(@RequestBody Map<String, Object> m){
        if (m == null) {
            return null;
        }
        return ciKpiService.findCisByMap(m);
    }
    
    @RequestMapping(value = "/findPerformanceChartData", method = RequestMethod.GET)
    public PageResult findPerformanceChartData(String selectMethod, Long interval, String startTime, String endTime,
                                                         String ciTypeId, String ciId, String attrs,String pageNum,String pageSize,String ciCode) {
    	PageResult pageResult=pmvService.findPerformanceInfo(selectMethod, interval, startTime, endTime, ciTypeId, ciId, attrs, pageNum, pageSize, ciCode);
    	return pageResult;
    }
    
    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map<String,Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}