package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.DataRel;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.PageResult;
import com.integration.entity.Rel;
import com.integration.generator.entity.IomCiType;
import com.integration.service.CiTypeRelService;
import com.integration.service.DataRelService;
import com.integration.service.InfoService;
import com.integration.service.RelService;
import com.integration.utils.DataUtils;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Package: com.integration.controller
* @ClassName: DataRelController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 关系管理
*/
@RestController
public class DataRelController {

    @Autowired
    private DataRelService drs;
    @Autowired
    private RelService rs;

    @Autowired
    private InfoService infoService;

    @Autowired
	private CiTypeRelService ciTypeRelService;

    public final ObjectMapper mapper = new ObjectMapper();

    private final static Logger logger = LoggerFactory
            .getLogger(DataRelController.class);

    /**
     * 跟据关系id查询关系数据并分页
     *
     * @return
     */
    @RequestMapping(value = "/findDataRelListByRelId", method = RequestMethod.GET)
    public PageResult findDataRelListByRelId(@RequestParam Map map) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        //总数
        Integer totalRecord = 0;
        List<DataRel> drList = new ArrayList<DataRel>();
        try {
            map.put("domainId", domainId);
            totalRecord = drs.getAllCount(map);
            drList = drs.getAllPage(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            return DataUtils.returnPr(null, "查询异常!");
        }

        return DataUtils.returnPr(totalRecord, drList);

    }

    /**
     * 查询CI信息列表包括大类编码
     * @param pageCount 页面记录数
     * @param pageIndex 页码
     * @param tidList CI大类Id数组
     * @param ciPropertyList
     * @param relId	关系id
     * @param sourceTypeId	源类id
     * @param targetTypeId	目标类id
     * @param relType	查询方式（source,target）
     * @return
     */
    @RequestMapping(value = "/findCIInfoList", method = RequestMethod.GET)
    public PageResult findCIInfoList(String pageCount, String pageIndex,String[] tidList,String [] ciPropertyList
    		,String relId,String sourceTypeId,String targetTypeId,String relType) {
        Map map = new HashMap();
        map.put("pageCount", pageCount);
        map.put("pageIndex", pageIndex);
        map.put("tidList", infoService.getCiIdsByCiTypeRel(relId, sourceTypeId, targetTypeId, tidList,relType));
        map.put("ciPropertyList", ciPropertyList);
        List<LinkedHashMap> infoLsit = new ArrayList<LinkedHashMap>();
        Map datamap = new HashMap();
        infoLsit = infoService.findCIInfoList(map);
        int count = infoService.findCIInfoListCount(map);
        datamap.put("count", count);
        datamap.put("infoLsit", infoLsit);

        return DataUtils.returnPr(datamap);

    }
    
    /**
     * 根据可视化建模查询大类详情
     * @param relId
     * @param sourceTypeId
     * @param targetTypeId
     * @param relType
     * @param tidList
     * @return
     */
    @GetMapping("/selectIomCiType")
    public List<IomCiType> selectIomCiType(String relId,String sourceTypeId,String targetTypeId,String relType,String[] tidList) {
    	return infoService.selectIomCiType(relId, sourceTypeId, targetTypeId, tidList, relType);
	}

    /**
     * 增加数据关系
     *
     * @param
     * @return
     */
    @OptionLog(desc = "增加关系数据", module = "CMV-关系模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addDataRel", method = RequestMethod.POST)
    public boolean addDataRel(DataRel dr, HttpServletRequest request) {

        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        if (!ciTypeRelService.checkCiRel(dr.getSource_ci_code(), dr.getRel_id(), dr.getTarget_ci_code())) {
            PageResult.fail("不符合可视化建模规范!");
            return false;
        }

        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */
        dr.setId(SeqUtil.nextId() + "");
        dr.setCjr_id(TokenUtils.getTokenUserId());
        dr.setCjsj(DateUtils.getDate());
        dr.setYxbz("1");
        dr.setDomain_id(domainId==null?"-1":domainId);
        String massage = "";
        Map map = new HashMap();
        map.put("source_ci_code", dr.getSource_ci_code());
        map.put("target_ci_code", dr.getTarget_ci_code());
        map.put("yxbz", 1);
        map.put("rel_id",dr.getRel_id());
        DataRel dRel = drs.getInfo(map);
        if (dRel != null) {
            PageResult.fail("源对象与目标对象关系已存在!");
            return false;
        }
        //成功失败标志
        boolean flag;
        int result = drs.insertInfo(dr);
        if (result > 0) {
            flag = true;
            PageResult.success("添加成功!");
        } else {
            flag = false;
            PageResult.success("添加失败!");
        }
        return flag;

    }

    /**
     * 增加数据关系feign调用
     *
     * @param json
     * @param request
     * @return
     */
    @RequestMapping(value = "/addFeignDataRel", method = RequestMethod.POST)
    public Object addFeignDataRel(String json, HttpServletRequest request) {
        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */

        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        ObjectMapper mapper = new ObjectMapper();
        DataRel dr = null;
        try {
            dr = mapper.readValue(json, DataRel.class);
        } catch (JsonParseException e1) {
            // TODO Auto-generated catch block
            logger.error(e1.getMessage());
        } catch (JsonMappingException e1) {
            // TODO Auto-generated catch block
            logger.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            logger.error(e1.getMessage());
        }
        dr.setId(SeqUtil.nextId() + "");
        dr.setCjr_id(TokenUtils.getTokenUserId());
        dr.setCjsj(DateUtils.getDate());
        dr.setYxbz("1");
        dr.setDomain_id(domainId==null?"-1":domainId);
        String massage = "";
        boolean flag = false;

        Map map = new HashMap();
        map.put("source_ci_id", dr.getSource_ci_id());
        map.put("target_ci_id", dr.getTarget_ci_id());
        map.put("yxbz", 1);
        DataRel dRel = drs.getInfo(map);
        if (dRel != null) {
            PageResult.fail("源对象与目标对象关系已存在!");
            return null;

        }
        int result = drs.insertInfo(dr);
        if (result > 0) {
            PageResult.setMessage(true, "添加成功!");
            return  dRel;
        } else {
            PageResult.setMessage(false, "添加失败!");
            return null;
        }
    }

    /**
     * 删除数据关系
     *
     * @param dataRelId
     * @return
     */
    @OptionLog(desc = "删除数据关系", module = "关系模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteDataRelById", method = RequestMethod.POST)
    public boolean deleteRel(String dataRelId) {
        DataRel dr = new DataRel();
        dr.setId(dataRelId);
        dr.setYxbz("0");
        //改为逻辑删除
        int result = drs.updateInfo(dr);
        //成功失败标志
        boolean flag;
        if (result > 0) {
            flag = true;
            PageResult.success("删除成功!");
        } else {
            flag = false;
            PageResult.success("删除失败!");
        }
        return flag;

    }

    /**
     * 根据CIID获取上级或者下级关系
     *
     * @param cId
     * @param supLayer
     * @param subLayer
     * @return
     */
    @RequestMapping(value = "/getRelSupAndSubByCI", method = RequestMethod.GET)
    public Map getRelSupAndSubByCI(String cId, String supLayer, String subLayer) {
        Map map = new HashMap();
        map = drs.getRelSupAndSubByCI(cId, supLayer, subLayer);
        return map;
    }

    /**
     * 修改数据关系
     *
     * @param
     * @return
     */
    @OptionLog(desc = "修改数据关系", module = "关系模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateDataRelById", method = RequestMethod.POST)
    public boolean updateDataRelById(DataRel dr, HttpServletRequest request) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        if (!ciTypeRelService.checkCiRel(dr.getSource_ci_code(), dr.getRel_id(), dr.getTarget_ci_code())) {
            PageResult.setMessage(false, "不符合可视化建模规范!");
            return false;
        }
        dr.setXgr_id(TokenUtils.getTokenUserId());
        dr.setXgsj(DateUtils.getDate());
        dr.setDomain_id(domainId==null?"-1":domainId);
        Map map = new HashMap();
        map.put("source_ci_code", dr.getSource_ci_code());
        map.put("target_ci_code", dr.getTarget_ci_code());
        map.put("rel_id", dr.getRel_id());
        map.put("yxbz", 1);
        DataRel dRel = drs.getInfo(map);
        if (dRel != null && !dRel.getId().equals(dr.getId())) {
            PageResult.fail("源对象与目标对象关系已存在!");
            return false;
        }
        //成功失败标志
        boolean flag;
        int result = drs.updateInfo(dr);
        if (result > 0) {
            flag = true;
            PageResult.success("修改成功!");
        } else {
            flag = false;
            PageResult.success("修改失败!");
        }
        return flag;
    }

    /**
     * 根据id获取数据关系
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/findDataRelById", method = RequestMethod.GET)
    public PageResult findDataRelById(@RequestParam Map map) {
        DataRel dr = new DataRel();
        dr = drs.getInfo(map);
        return DataUtils.returnPr(dr);
    }

    /**
     * 清除全部
     *
     * @param relId
     * @return
     */
    @OptionLog(desc = "清除全部", module = "关系模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/clearAll", method = RequestMethod.POST)
    public boolean clearAll(String relId) {
    	//改为逻辑删除
        int result = drs.updateYXBZ(relId);
        //int result = drs.deleteByrelId(relId);
        //成功失败标志
        boolean flag;
        if (result > 0) {
            flag = true;
            PageResult.success("清除全部成功!");
        } else {
            flag = false;
            PageResult.success("清除全部失败!");
        }
        return flag;

    }

    /**
     * 关系数据导出(全部)
     *
     * @param response
     * @throws IOException
     */
    @OptionLog(desc = "关系数据导出", module = "关系模块", writeParam = false, writeResult = true)
    @RequestMapping(value = "exportDataRelToExcelAll", method = RequestMethod.GET)
    public boolean exportDataRelToExcelAll(HttpServletResponse response){
        HSSFWorkbook workbook = new HSSFWorkbook();
        Map params = new HashMap();
        List<Rel> relList = rs.getAllList(params);
        List<DataRel> dataRelList = drs.exportDataRelToExcel(null);

        Date date = new Date();
        String sj = DateUtils.parseDate(date, "yyyyMMdd-HHmmss");
        // 设置要导出的文件的名字
        String fileName = "CIRelation-" + sj + ".xls";
        // 循环关系表生成sheet页名
        for (Rel rel : relList) {
            // 新增数据行，并且设置单元格数据
            int rowNum = 1;
            String[] headers = {"源对象", "源分类", "目标对象", "目标分类"};
            // headers表示excel表中第一行的表头
            // 加表头
            HSSFSheet sheet = workbook.createSheet(rel.getLine_name());
            HSSFRow row = sheet.createRow(0);
            // 设行高
            row.setHeightInPoints(20);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            row.setRowStyle(cellStyle);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 加数据
            for (DataRel dataRel : dataRelList) {
                if (rel.getLine_bm().equals(dataRel.getRel_name())) {
                    HSSFRow row1 = sheet.createRow(rowNum);
                    row1.createCell(0).setCellValue(dataRel.getSource_ci_bm());
                    row1.createCell(1)
                            .setCellValue(dataRel.getSource_type_bm());
                    row1.createCell(2).setCellValue(dataRel.getTarget_ci_bm());
                    row1.createCell(3)
                            .setCellValue(dataRel.getTarget_type_bm());
                    rowNum++;
                }
            }
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename="
                + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        
    }


    /**
     * 关系数据导出(单个)
     *
     * @param response
     * @throws IOException
     */
    @OptionLog(desc = "关系数据导出", module = "关系模块", writeParam = false, writeResult = true)
    @RequestMapping(value = "exportDataRelToExcel", method = RequestMethod.GET)
    public boolean exportDataRelToExcel(HttpServletResponse response, String relId){
        HSSFWorkbook workbook = new HSSFWorkbook();
        Date date = new Date();
        String sj = DateUtils.parseDate(date, "yyyyMMdd-HHmmss");
        // 设置要导出的文件的名字
        String fileName = "CIRelation-" + sj + ".xls";
        Rel rel = rs.findRelNameById(relId);
        if (StringUtils.isNotEmpty(relId)) {
            Map params = new HashMap();
            List<Rel> relList = rs.getAllList(params);
            List<DataRel> dataRelList = drs.exportDataRelToExcel(relId);
            // 创建一个sheet页
            HSSFSheet sheet = workbook.createSheet(rel.getLine_bm());
            HSSFRow row = sheet.createRow(0);
            String[] headers = {"源对象", "源分类", "目标对象", "目标分类"};
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 新增数据行，并且设置单元格数据
            int rowNum = 1;
            // 创建第一行
            // 设行高
            row.setHeightInPoints(20);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            row.setRowStyle(cellStyle);
            // 加数据
            for (DataRel dataRel : dataRelList) {
                if (rel.getId().equals(relId)) {
                    HSSFRow row1 = sheet.createRow(rowNum);
                    row1.createCell(0).setCellValue(dataRel.getSource_ci_bm());
                    row1.createCell(1)
                            .setCellValue(dataRel.getSource_type_bm());
                    row1.createCell(2).setCellValue(dataRel.getTarget_ci_bm());
                    row1.createCell(3)
                            .setCellValue(dataRel.getTarget_type_bm());
                    rowNum++;
                }
            }
        } else {//导出模板
            // 创建一个sheet页
            HSSFSheet sheet = workbook.createSheet("Sheet1");
            HSSFRow row = sheet.createRow(0);
            String[] headers = {"源对象", "源分类", "目标对象", "目标分类"};
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 新增数据行，并且设置单元格数据
            // 创建第一行
            // 设行高
            row.setHeightInPoints(20);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            row.setRowStyle(cellStyle);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename="
                + fileName);
        try {
			response.flushBuffer();
			workbook.write(response.getOutputStream());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
       
    }

    /**
     * 关系数据导入(单个)
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/importDataRelToBaseOne", method = RequestMethod.POST)
    public boolean importDataRelToBaseOne(HttpServletRequest request,
                                          @RequestParam Map<String, String> map,String rel_id) throws IOException {
        //根据relId获取relname
        Rel rel = rs.findRelNameById(rel_id);
        if(rel != null){
            Map<String, List<DataRel>> tmpMap = new HashMap<String, List<DataRel>>();
            String jsonStr = map.get("map");
            tmpMap = mapper.readValue(jsonStr, Map.class);

            for (String key : tmpMap.keySet()) {
                if (rel.getLine_bm().equals(key)) {
                    List<DataRel> dataRels = tmpMap.get(rel.getLine_bm());
                    if (dataRels != null && dataRels.size() > 0) {
                        Map<String, List<DataRel>> tmpMap2 = new HashMap<String, List<DataRel>>();
                        tmpMap2.put(rel.getLine_bm(), dataRels);
                        return importData(tmpMap2);
                    }
                }
            }
        }
        PageResult.fail(" 导入失败!");
        return false;
    }

    /**
     * 关系数据导入(全部)
     *
     * @param map
     * @return
     */
    @OptionLog(desc = "关系数据导入", module = "关系模块", writeParam = false, writeResult = true)
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/importDataRelToBase", method = RequestMethod.POST)
    public boolean importDataRelToBase(HttpServletRequest request,
                                          @RequestParam Map<String, String> map) throws IOException {
        Map<String, List<DataRel>> tmpMap = new HashMap<String, List<DataRel>>();
        String jsonStr = map.get("map");
        tmpMap = mapper.readValue(jsonStr, Map.class);
        return importData(tmpMap);
    }

    /**
     * 导入关系数据
     * @param tmpMap
     * @return
     * @throws IOException
     */
    public boolean importData(Map<String, List<DataRel>> tmpMap) throws IOException {

        try {
            //获取数据域ID
            String domainId = TokenUtils.getTokenOrgDomainId();
            String cjid = TokenUtils.getTokenUserId();
            //所有合法的源IP、目标IP
            List<Map<String, Object>> souTar = drs.findSouTar();
            //所有关系名
            List<DataRel> relList = drs.findRelName();
            List<Rel> relName = rs.findRelName();
            String cjsj = DateUtils.getDate();
            String ls = "";
            List<DataRel> llList = new ArrayList<DataRel>();
            //1,获得rel
            List<Rel> rels = new ArrayList<Rel>();
            for (String key : tmpMap.keySet()) {
                Rel rel = null;
                for (Rel rela : relName) {
                    if (key.equals(rela.getLine_bm())) {
                        rel = new Rel();
                        rel.setId(rela.getId());
                        rel.setLine_bm(rela.getLine_bm());
                        rel.setLine_name(rela.getLine_bm());
                        rel.setDomain_id(domainId==null?"-1":domainId);
                        break;
                    }
                }
                if (rel == null) {
                    rel = new Rel();
                    rel.setId(SeqUtil.nextId().toString());
                    rel.setLine_bm(key);
                    rel.setLine_name(key);
                    rel.setLine_style(1);
                    rel.setLine_width(1);
                    rel.setLine_color("#000");
                    rel.setSort(1000000);
                    rel.setCjr_id(cjid);
                    rel.setCjsj(cjsj);
                    rel.setLine_arror(1);
                    rel.setYxbz(1);
                    rel.setDomain_id(domainId==null?"-1":domainId);
                    rels.add(rel);
                }
                ls = mapper.writeValueAsString(tmpMap.get(key));
                JavaType javaType = getCollectionType(ArrayList.class, DataRel.class);
                List<DataRel> llList2 = mapper.readValue(ls, javaType);
                for (DataRel dataRel : llList2) {
                    dataRel.setRel_id(rel.getId());
                    dataRel.setRel_name(rel.getLine_bm());
                    dataRel.setDomain_id(domainId==null?"-1":domainId);
                }
                llList.addAll(llList2);
            }

            //去除列表里重复数据
            List<Map> mapList= JSONObject.parseArray(JSON.toJSONString(llList),Map.class);
            List<Map> listTemp = mapList.stream().distinct().collect(Collectors.toList());
            llList.clear();
            llList=JSONObject.parseArray(JSON.toJSONString(listTemp),DataRel.class);


            //2，获得ciId和去掉重复的
            //二者合法数据
            List<DataRel> list = new ArrayList<DataRel>();
            for (DataRel newDataRel : llList) {
                boolean flag = true;
                for (DataRel oldDataRel : relList) {
                    if (newDataRel.getSource_ci_bm().equals(oldDataRel.getSource_ci_bm()) &&
                            newDataRel.getTarget_ci_bm().equals(oldDataRel.getTarget_ci_bm()) &&
                            newDataRel.getRel_id().equals(oldDataRel.getRel_id())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    list.add(newDataRel);
                }
            }
            //二者合法数据
            List<DataRel> list2 = new ArrayList<DataRel>();
            for (int j = 0; j < list.size(); j++) {
                DataRel dataRel = list.get(j);
                for (int i = 0; i < souTar.size(); i++) {
                    Map<String, Object> st = souTar.get(i);
                    //源IP、目标IP都合法
                    if (dataRel.getSource_ci_bm().equals(st.get("CI_CODE")) && dataRel.getSource_type_bm().equals(st.get("CI_TYPE_bm"))) {
                        dataRel.setSource_ci_id(st.get("id").toString());
                        dataRel.setSource_type_id(st.get("CI_TYPE_ID").toString());
                        dataRel.setDomain_id(domainId==null?"-1":domainId);
                    }
                    //源IP、目标IP都合法
                    if (dataRel.getTarget_ci_bm().equals(st.get("CI_CODE")) && dataRel.getTarget_type_bm().equals(st.get("CI_TYPE_bm"))) {
                        dataRel.setTarget_ci_id(st.get("id").toString());
                        dataRel.setTarget_type_id(st.get("CI_TYPE_ID").toString());
                        dataRel.setDomain_id(domainId==null?"-1":domainId);
                    }
                    if (StringUtils.isNotEmpty(dataRel.getSource_ci_id()) && StringUtils.isNotEmpty(dataRel.getTarget_ci_id())) {
                        dataRel.setId(SeqUtil.nextId() + "");
                        dataRel.setCjr_id(cjid);
                        dataRel.setCjsj(cjsj);
                        dataRel.setXgsj(cjsj);
                        dataRel.setYxbz("1");
                        dataRel.setDomain_id(domainId==null?"-1":domainId);

                        list2.add(dataRel);
                        break;
                    }
                }
            }

            //不需要验证可视化建模
            //批量插入rels
            rs.importRelTobase(rels);

            //成功数据条数
            int result = drs.importDataRelTobase(list2);
            //重复数据条数
            int repeatCount = llList.size() - list.size();
            //不合法数据条数
            int illegalCount = list.size() - list2.size();
            String msgString = "";
            if (result > 0) {
                msgString += "成功:" + result + "条;";
            }

            if (repeatCount > 0) {
                msgString += "重复数据:" + repeatCount + "条;";
            }

            if (illegalCount > 0) {
                msgString += "不合法数据:" + illegalCount + "条;";
            }
            //成功失败标志
            boolean flag;
            if (result > 0) {
                PageResult.success("导入成功!" + msgString);
                flag = true;
            } else {
                PageResult.fail("导入失败!" + msgString);
                flag = false;

            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            PageResult.fail("导入失败!请检查导入数据是否正确！" );
            return false;
        }
    }


    public JavaType getCollectionType(Class<?> collectionClass,
                                      Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass,
                elementClasses);
    }

    /**
     * 根据CIList获取每个CIID和与它有关系的关系集合
     *
     * @return
     */
    @RequestMapping(value = "/getDataRelByCiList", method = RequestMethod.POST)
    public Map<String, List<DataRel>> getDataRelByCiList(@RequestParam("ciIdList") String ciIdList,@RequestParam("ciCodeList") String ciCodeList) {
        List<String> list = Arrays.asList(ciCodeList.split(","));
        return drs.getDataRelByCiList(list);
    }
    
    /**
     * 根据CIList获取每个CIID和与它有关系的关系集合(8007配置数据)
     *
     * @return
     */
    @RequestMapping(value = "/getDataRelByCiIdsList", method = RequestMethod.POST)
    public Map<String, List<Map<String,Object>>> getDataRelByCiIdsList(String ciIdList,String ciCodeList) {
        List<String> list = Arrays.asList(ciCodeList.split(","));
        return drs.getDataRelByCiIdsList(list);
    }

    /**
	 * 根据对象id获取所有关联的对象及其未关闭告警数量
	 * @param ciId
	 * @return
	 */
    @GetMapping("dataRel/selectAllEventCountByCiId")
	public Map<String, Object> selectAllEventCountByCiId(String ciId,String ciCode) {
		return drs.selectAllEventCountByCiId(ciId,ciCode);
	}

	/**
	 * 根据对象id获取一级关联的对象及其未关闭告警数量
	 *
	 * @param ciId
	 * @return
	 */
    @GetMapping("dataRel/selectOneEventCountByCiId")
	public Map<String, Object> selectOneEventCountByCiId(String ciId) {
		return drs.selectOneEventCountByCiId(ciId);
	}

    /**
     * @Method findAll
     * @Author sgh
     * @Version  1.0
     * @Description 根据ci查询关系
     * @Return java.util.List<com.integration.entity.DataRel>
     * @Exception
     * @Date 2019/10/10
     * @Param []
     */
    @RequestMapping("dataRel/findAllByCiID")
    public List<DataRel> findAllByCiID(@RequestBody Map map) {
        List<EmvReturnCIMessage> emvReturnCIMessages = JSONArray.parseArray(map.get("returnCIMessageList").toString(), EmvReturnCIMessage.class);
        String cItype = null;
        if (map.get("ciType") != null && !"".equals(map.get("ciType"))) {
            cItype = map.get("cIType").toString();
        }
        return drs.findAllByCiID(emvReturnCIMessages,cItype);
    }

    /**
     * @Method findAll
     * @Author sgh
     * @Version  1.0
     * @Description 根据ci查询关系(过滤)
     * @Return java.util.List<com.integration.entity.DataRel>
     * @Exception
     * @Date 2019/10/10
     * @Param []
     */
    @RequestMapping("dataRel/findAllByCiIDFl")
    public List<DataRel> findAllByCiIDFl(@RequestBody Map map) {
        List<EmvReturnCIMessage> emvReturnCIMessages = JSONArray.parseArray(map.get("returnCIMessageList").toString(), EmvReturnCIMessage.class);
        String startDate = null;
        String endDate = null;
        String cIType = map.get("cIType").toString();
        if (map.get("startDate") != null) {
            startDate = map.get("startDate").toString();
        }
        if (map.get("endDate") != null) {
            endDate = map.get("endDate").toString();
        }
        return drs.findAllByCiIDFl(emvReturnCIMessages,startDate,endDate,cIType);
    }

    @GetMapping("dataRel/getPath")
    public Object getPath(String startIds,String endIds, String typeIds,String startCiCodes,String endCiCodes) {
    	List<String> startIdList = Arrays.asList(startIds.split(","));
    	List<String> startCiCodeList = Arrays.asList(startCiCodes.split(","));
    	List<String> endIdList = Arrays.asList(endIds.split(","));
    	List<String> endCiCodeList = Arrays.asList(endCiCodes.split(","));
    	List<String> typeIdList = null;
    	if (StringUtils.isNotEmpty(typeIds)) {
    		typeIdList = Arrays.asList(typeIds.split(","));
		}
        return drs.getPath(startIdList, endIdList, typeIdList,startCiCodeList,endCiCodeList);
    }

    /**
	 * 根据多个ID查询关系表信息
	 *
	 * @param ids
	 * @return
	 */

    @RequestMapping(value = "/getCiDataRelByIds", method = RequestMethod.POST)
    public List<Map<String, Object>> getCiDataRelByIds(String ids,String relationId) {
    	List<Map<String, Object>> listData=new ArrayList<Map<String,Object>>();

        if (ids != null && !"".equals(ids)) {
            List<String> list = Arrays.asList(ids.split(","));
            if(relationId!=null && !"".equals(relationId)) {
                List<String> listRel=Arrays.asList(relationId.split(","));
                listData=drs.getCiDataRelByIds(list,listRel);
                return listData;
             }else {
                List<String> listRel=new ArrayList<String>();
                listData=drs.getCiDataRelByIds(list,listRel);
                 return listData;
             }
        }
        return null;
    }
    
    /**
	 * 判断CI关系数据是否存在
	 *
	 * @param ids
	 * @return
	 */

    @RequestMapping(value = "/queryCiDataRelIsExist", method = RequestMethod.POST)
    public JSONArray queryCiDataRelIsExist(String relData) {
    	JSONArray jsonArray=JSON.parseArray(relData);
    	JSONArray returnJsonArray=drs.queryCiDataRelIsExist(jsonArray);
        return returnJsonArray;
    }
}
