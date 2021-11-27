package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.integration.entity.PageResult;
import com.integration.entity.Type;
import com.integration.entity.TypeData;
import com.integration.entity.TypeItem;
import com.integration.generator.dao.IomCiInfoMapper;
import com.integration.generator.entity.IomCiInfo;
import com.integration.generator.entity.IomCiInfoExample;
import com.integration.service.CiInitializationService;
import com.integration.service.TypeDataService;
import com.integration.service.TypeItemService;
import com.integration.service.TypeService;
import com.integration.utils.ImportExeclUtil;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: CiInitializationServiceImpl
 * @Author: ztl
 * @Date: 2020-10-15
 * @Version: 1.0
 * @description:CI数据初始化
 */
@Service
public class CiInitializationServiceImpl implements CiInitializationService {
    private final static Logger logger = LoggerFactory.getLogger(CiInitializationServiceImpl.class);
    @Autowired
    private TypeService typeServie;
    @Autowired
    private TypeItemService typeItemService;
    @Resource
    private IomCiInfoMapper iomCiInfoMapper;
    @Autowired
    private TypeDataService typeDataService;
    /**
     * 初始化导入分类
     *
     * @param multipartFiles
     * @return
     */
    @Override
    public List<Map<String, Object>> importExcel(MultipartFile[] multipartFiles) {
        try{
            if (multipartFiles.length>0){
                List<Map<String, Object>> result = new ArrayList<>();
                for (MultipartFile multipartFile: multipartFiles) {
                    Map<String, Object> resultMap = new HashMap<>();
                    String fileName = multipartFile.getOriginalFilename();
                    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                    boolean isExcel2003 = "xls".equals(suffix);
                    InputStream is = multipartFile.getInputStream();
                    String newFileName = SeqUtil.getId();
                    String path = System.getProperty("user.dir")+"/data/temp/";
                    File uploadFile = new File(path+newFileName+"."+suffix);
                    try {
                        if (!uploadFile.getParentFile().exists()) {
                            uploadFile.getParentFile().mkdirs();
                        }
                        multipartFile.transferTo(uploadFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /** 根据版本选择创建Workbook的方式 */
                    Workbook wb = null;
                    if (isExcel2003) {
                        wb = new HSSFWorkbook(is);
                    } else {
                        wb = new XSSFWorkbook(is);
                    }
                    List<String> sheetNames = new ArrayList<>();
                    List<Map<String, Object>> sheetTitles = new ArrayList<>();
                    for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                        Map<String, Object> sheetTitle = new HashMap<>();
                        String name = wb.getSheetAt(i).getSheetName();
                        List<String> headerList= ImportExeclUtil.readExcellHeader(wb, name);
                        sheetNames.add(name);
                        sheetTitle.put("sheetName", name);
                        sheetTitle.put("titles",headerList);
                        sheetTitles.add(sheetTitle);
                    }
                    resultMap.put("originalFileName",fileName);
                    resultMap.put("sheetNames", sheetNames);
                    resultMap.put("sheetTitles", sheetTitles);
                    resultMap.put("fileName", newFileName+"."+suffix);
                    result.add(resultMap);
                }
                return result;
            }else {
                return new ArrayList<>();
            }
        }catch (Exception e){
            logger.error("初始化类数据上传文件异常",e);
            return new ArrayList<>();
        }
    }
    /**
     * 读取Excel的指定工作表
     * @param fileName 文件名
     * @param pageNum 页码
     * @param pageSize 条数
     * @param sheetName 工作表名称
     * @return
     */
    @Override
    public PageResult getSheetDataBySheetName(String fileName, int pageNum, int pageSize,String  sheetName) {
        PageResult page = new PageResult();
        int totalRows = 0;
        try {
            Map<String, Object> resultMap = new HashMap<>();
            String path = System.getProperty("user.dir")+"/data/temp/"+fileName;
            InputStream is = new FileInputStream(path);
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            boolean isExcel2003 = "xls".equals(suffix);
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                String name = wb.getSheetAt(i).getSheetName();
                if (sheetName.equals(name)){
                    List<String> headerList= ImportExeclUtil.readExcellHeader(wb, name);
                    List<Map<Object,Object>> datas = ImportExeclUtil.readExcelDate(wb,name,pageNum,pageSize);
                    totalRows = ImportExeclUtil.readExcelRows(wb, name);
                    resultMap.put("fieldNames",headerList);
                    resultMap.put("fileName", name);
                    resultMap.put("datas", datas);
                }
            }
            page.setTotalResult(totalRows-1);
            page.setReturnObject(resultMap);
            page.setReturnBoolean(true);
            page.setCurrentPage(pageNum);
            return page;

        }catch (Exception e){
            logger.error("获取sheet数据时异常",e);
            page.setReturnBoolean(false);
            page.setReturnObject(new HashMap<>());
            page.setReturnMessage("获取sheet数据时异常");
            return page;
        }
    }

    @Override
    public Map<String,Object> importCiBySheetName(String fileName, String sheetName) {
        Map<String,Object> resultMap = new HashMap<>();
        String domainId = TokenUtils.getTokenOrgDomainId();
        //根据sheet名获取大类
        try{
            Type type = typeServie.findByMc(sheetName,domainId);
            String ciTypeId = type.getId();
            String path = System.getProperty("user.dir")+"/data/temp/"+fileName;
            InputStream is = new FileInputStream(path);
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            boolean isExcel2003 = "xls".equals(suffix);
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            // 获取sheet页与大类名称相同的数据
            List<List<String>> listList = ImportExeclUtil.readDate(wb, type);
            List<TypeItem> items = typeItemService.findItemByTid(ciTypeId);
            int sortNum = typeItemService.findItemSortByTid(ciTypeId);
            List<TypeData> dataList = new ArrayList<TypeData>();
            // 获取excel字段，用于和类定义做匹配
            List<String> head = listList.get(0);

            List<String> titles = new ArrayList<>();
            titles.add("");
            titles.addAll(head);
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
                        typeItem.setCi_type_id(ciTypeId);
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
                items = typeItemService.findItemByTid(ciTypeId);
            }
            IomCiInfoExample iomCiInfoExample = new IomCiInfoExample();
            com.integration.generator.entity.IomCiInfoExample.Criteria criteria = iomCiInfoExample.createCriteria();
            criteria.andYxbzEqualTo(1);
            criteria.andCiTypeIdNotEqualTo(ciTypeId);
            List<IomCiInfo> iomCiInfos = iomCiInfoMapper.selectByExample(iomCiInfoExample);
            // 移除表头
            listList.remove(0);
            for (int i = 0; i < listList.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                for (int j = 0; j < listList.get(i).size(); j++) {
                    for (int k=0;k<items.size();k++){
                        if (head.get(j).equals(items.get(k).getAttr_name())){
                            map.put(items.get(k).getMp_ci_item(), listList.get(i).get(j));

                        }
                    }
                }
                TypeData typeData = JSON.parseObject(JSON.toJSONString(map), TypeData.class);
                dataList.add(typeData);
            }
            int c=0;
            List<TypeData> newDataList = new ArrayList<TypeData>();
            newDataList.addAll(dataList);
            List<List<String>> igroneDatas = new ArrayList<>();
            for (TypeData typeDate: dataList) {
                Map<String,String> map = JSONObject.parseObject(JSONObject.toJSONString(typeDate),Map.class);
                boolean flag = false;
                for (TypeItem item:items) {
                    if (item.getIs_requ()==1 && !flag){
                        if ("".equals(map.get(item.getMp_ci_item().toLowerCase()))||map.get(item.getMp_ci_item().toLowerCase())==null){
                            List<String> igroneData = new ArrayList<>();
                            for (String str: titles) {
                                for (TypeItem item1: items) {
                                    if("".equals(str)){
                                        igroneData.add("第"+(c+1)+"行：属性["+item.getAttr_name()+"]：不可为空");
                                    }
                                    if (str.equals(item1.getAttr_name())){
                                        igroneData.add(map.get(item1.getMp_ci_item().toLowerCase()));
                                    }
                                }
                            }
                            igroneDatas.add(igroneData);
                            flag=true;
                        }
                    }
                    if(item.getIs_major()==1){
                        if ("".equals(map.get(item.getMp_ci_item().toLowerCase()))||map.get(item.getMp_ci_item().toLowerCase())==null){
                            List<String> igroneData = new ArrayList<>();
                            for (String str: titles) {
                                for (TypeItem item1: items) {
                                    if("".equals(str)){
                                        igroneData.add("第"+(c+1)+"行：主键["+item.getAttr_name()+"]：不可为空");
                                    }
                                    if (str.equals(item1.getAttr_name())){
                                        igroneData.add(map.get(item1.getMp_ci_item().toLowerCase()));
                                    }
                                }
                            }
                            igroneDatas.add(igroneData);
                            flag=true;
                        }else {
                            int j =0;
                            for (TypeData typeDates: dataList ) {
                                Map<String,String> maps = JSONObject.parseObject(JSONObject.toJSONString(typeDates),Map.class);
                                if (c!=j && !flag){
                                    if (map.get(item.getMp_ci_item().toLowerCase()).equals(maps.get(item.getMp_ci_item().toLowerCase()))){
                                        List<String> igroneData = new ArrayList<>();
                                        for (String str: titles) {
                                            if("".equals(str)){
                                                igroneData.add("第"+(c+1)+"行：属性["+item.getAttr_name()+"]：该批次中存在相同的CI！");
                                            }
                                            for (TypeItem item1: items) {
                                                if (str.equals(item1.getAttr_name())){
                                                    igroneData.add(map.get(item1.getMp_ci_item().toLowerCase()));
                                                }
                                            }
                                        }
                                        igroneDatas.add(igroneData);
                                        flag=true;
                                        j++;
                                        continue;
                                    }else{
                                        j++;
                                        continue;
                                    }
                                }
                            }
                            long count=iomCiInfos.stream().filter(s->s.getCiCode().equals(map.get(item.getMp_ci_item().toLowerCase()))).count();
                            if (count>0 && !flag){
                                List<String> igroneData = new ArrayList<>();
                                for (String str: titles) {
                                    if("".equals(str)){
                                        igroneData.add("第"+(c+1)+"行：业务主键属性["+item.getAttr_name()+"]： CI已在其它分类中存在！");
                                    }
                                    for (TypeItem item1: items) {
                                        if (str.equals(item1.getAttr_name())){
                                            igroneData.add(map.get(item1.getMp_ci_item().toLowerCase()));
                                        }
                                    }
                                }
                                igroneDatas.add(igroneData);
                                flag=true;
                            }
                        }
                    }
                }
                if (flag){
                    newDataList.remove(typeDate);
                }
                c++;
            }
            igroneDatas=igroneDatas.parallelStream().distinct().collect(Collectors.toList());
            int updateNum =newDataList.size();
            Map m = typeDataService.importExcelData(ciTypeId, newDataList, TokenUtils.getTokenUserId());
            resultMap.put("sheetName", sheetName);
            resultMap.put("titles", titles);
            resultMap.put("igroneDatas",igroneDatas);
            resultMap.put("ignoreNum",igroneDatas.size());
            resultMap.put("updateNum",updateNum);
            resultMap.put("igroneType", igroneDatas.size()>100?"2":"1");
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
