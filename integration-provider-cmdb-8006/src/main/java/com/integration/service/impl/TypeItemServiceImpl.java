package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.integration.dao.*;
import com.integration.entity.*;
import com.integration.feign.DfileService;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeExample;
import com.integration.rabbit.Sender;
import com.integration.service.TypeItemService;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/**
* @Package: com.integration.service.impl
* @ClassName: TypeItemServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 类定义
*/
@Transactional
@Service
public class TypeItemServiceImpl implements TypeItemService {
	private static final Logger logger = LoggerFactory.getLogger(TypeItemServiceImpl.class);
    @Autowired
    private TypeItemDao typeItemDao;

    @Autowired
    private InfoDao infoDao;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TypeDataDao typeDataDao;

    @Resource
    private DataIndexDao dataIndexDao;

    @Autowired
    private DfileService dfileService;

    @Resource
    private IomCiTypeMapper iomCiTypeMapper;

    @Autowired
    private InfoServiceImpl infoService;

    @Autowired
    private Sender sender;

    public final ObjectMapper mapper = new ObjectMapper();

    /**
     * 根据大类ID获取字段列表
     */
    @Override
    public List<TypeItem> findItemByTid(String tid) {
        return typeItemDao.findItemByTid(tid);
    }
    
    /**
     * 根据大类ID获取字段列表(驼峰)
     */
    @Override
    public List<Map<String,Object>> findItemByTidToHump(String tid) {
    	List<Map<String,Object>> list=typeItemDao.findItemByTidToHump(tid);
        return list;
    }
    
    /**
     * 根据多个大类ID获取字段列表(驼峰)
     */
    @Override
    public List<Map<String,Object>> findItemByTidsToHump(String tid) {
    	if(tid!=null && !"".equals(tid)) {
    		String[] tids = tid.split(",");
    		Map<String,Object> itemMap=new HashMap<String,Object>();
    		itemMap.put("ciTypeIdList", tids);
    		List<Map<String,Object>> list=typeItemDao.findItemByTidsToHump(itemMap);
    		return list;
    	}
        return null;
    }

    @Override
	public Integer deleteItemByAllByTid(List<TypeItem> list) {
		return typeItemDao.deleteItemByAllByTidTrue(list);
	}

    /**
     * 添加字段
     */
    @Override
    public TypeItem addItem(TypeItem typeItem) {
        typeItem.setId(SeqUtil.nextId() + "");
        // 创建时间
        typeItem.setCjsj(DateUtils.getDate());
        // 有效标志
        typeItem.setYxbz(1);
        // 获取当前数据列集合,找到当前最大的数据列
        List<TypeItem> dataNum = typeItemDao.dataNum(typeItem.getCi_type_id());
        String mp = "";
        // 获取新添加的字段映射的数据列
        if (dataNum.size() > 0) {
            List<Integer> num = new ArrayList<Integer>();
            for (int i = 0; i < dataNum.size(); i++) {
                num.add(Integer.parseInt(dataNum.get(i).getMp_ci_item()
                        .substring(5)));
            }
            mp = "DATA_" + (Collections.max(num) + 1);
        } else {
            mp = "DATA_1";
        }
        typeItem.setIs_rela(0);
        if (typeItem.getIs_label()<=0){
            typeItem.setIs_label(0);
        }

        typeItem.setMp_ci_item(mp);
        int result = typeItemDao.addItem(typeItem);

        if (result > 0) {
            try{
                //向队列推送大类及类定义数据
                sender.sendCiTypeAndItemData();
            }catch (Exception e){
                logger.error("向队列推送大类及类定义数据异常",e);
            }
            return typeItem;
        }
        return null;
    }

    /**
     * 修改字段
     */
    @Override
    public boolean updateItem(TypeItem typeItem) {

        boolean result = typeItemDao.updateItem(typeItem) > 0;
        try{
            //向队列推送大类及类定义数据
            sender.sendCiTypeAndItemData();
        }catch (Exception e){
            logger.error("向队列推送大类及类定义数据异常",e);
        }
        return result;
    }

    /**
     * 根据字段ID删除字段
     */
    @Override
    public boolean deleteItem(String id) {
    	//根据id typeid获取item的data?
    	TypeItem typeItem=typeItemDao.findTypeItem(id);
    	String dataNo=typeItem.getMp_ci_item();
    	String typeId=typeItem.getCi_type_id();
    	//去index根据 itemid typeid获取删除字段的所有ciid;
    	List<DataIndex> IndexList=dataIndexDao.findInfoByItemAndType(id,typeId);

    	for (DataIndex dataIndex : IndexList) {
    		//根据typeid，ciId删除data表data?数据
    		typeDataDao.deleteDataByTypeAndCI(dataIndex.getCi_id(),typeId,dataNo);
		}
    	//根据itemid typeid删除index表数据
    	dataIndexDao.deleteDataIndexByTypeIdAndCI(id,typeId);
    	boolean result = typeItemDao.deleteItemTrue(id) > 0;
        try{
            //向队列推送大类及类定义数据
            sender.sendCiTypeAndItemData();
        }catch (Exception e){
            logger.error("向队列推送大类及类定义数据异常",e);
        }
        //return typeItemDao.deleteItem(id) > 0; 将逻辑删除字段改为真实删除  @author ztl @date 2020-06-28
        return result;
    }

    /**
     * 根据ID获取字段信息
     */
    @Override
    public TypeItem findTypeItem(String id) {
        return typeItemDao.findTypeItem(id);
    }

    /**
     * 根据大类ID获取数据信息
     */
    @Override
    public Map<String,Object> findData(Map<String, String> map) {
    	Object pageIndex = map.get("pageIndex");
    	Object pageCount = map.get("pageCount");
    	String sql = getSqlNoPage(map);
    	if(StringUtils.isNotEmpty(sql)){
            MyPagUtile.startPage(pageIndex,pageCount);
            List<Map> data = typeItemDao.findDataMap(sql);
            PageResult  pageResult= MyPagUtile.getPageResult(data);
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("total", pageResult.getTotalResult());
            dataMap.put("rows", pageResult.getReturnObject());
            return dataMap;
        }
    	return null;
    }
    /**
     * 根据大类ID获取数据信息(不分页)
     */
    @Override
    public Map<String,Object> findDataNoPage(Map<String, String> map) {
        String sql = getSqlNoPage(map);
        List<Map> data = typeItemDao.findDataMap(sql);
        PageResult  pageResult= MyPagUtile.getPageResult(data);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("total", pageResult.getTotalResult());
        dataMap.put("rows", pageResult.getReturnObject());
        return dataMap;
    }
    
    /**
     * 根据大类ID获取数据信息(不分页+驼峰)
     */
    @Override
    public Map<String,Object> findDataNoPageToHump(Map<String, String> map,List<Map<String,Object>> dataVal) {
        String sql = getSqlNoPageToHump(map);
        if(sql!=null) {
        	StringBuffer buffer = new StringBuffer(sql);
            for(Map<String,Object> mapVal:dataVal) {
            	String data=(String) mapVal.get("data");
            	String cdtOp=(String) mapVal.get("cdtOp");
            	String cdtVal=(String) mapVal.get("cdtVal");
            	if("like".equals(cdtOp) || "not like".equals(cdtOp)) {
            		buffer.append(" AND "+data+" "+cdtOp+"'%"+cdtVal+ "%' ");
            	}else {
            		buffer.append(" AND "+data+""+cdtOp+"'"+cdtVal+"' ");	
            	}
            	
            }
            List<Map<String,Object>> data = typeItemDao.findDataToHump(buffer.toString());
            List<Map<String,Object>> dataHump=new ArrayList<Map<String,Object>>();
            for(Map<String,Object> mapData:data) {
            	Map<String, Object> mapDataHump=formatHumpName(mapData);
            	dataHump.add(mapDataHump);
            }
            PageResult  pageResult= MyPagUtile.getPageResult(dataHump);
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("total", pageResult.getTotalResult());
            dataMap.put("rows", pageResult.getReturnObject());
            return dataMap;
        }else {
        	Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("total", 0);
            dataMap.put("rows", new ArrayList<Map<String,Object>>());
            return dataMap;
        }
        
    }

    /**
               * 根据大类ID全量删除
     */
    @Override
    public Integer deleteDataAll(Map<String, String> map) {
    	List<String> ids=new ArrayList<String>();
    	Map map1=findDataNoPage(map);
    	List list=(List) map1.get("rows");
    	if(list!=null && list.size()>0) {
    		for(int i=0;i<list.size();i++) {
        		Map map2=(Map) list.get(i);
        		String id1=(String) map2.get("ID");
        		ids.add(id1);

        	}
    	}
        dataIndexDao.deleteDataIndexByTypeId(map.get("tid"));
        //return infoDao.deleteInfoByids(ids); 将逻辑删除改为真实删除  @author ztl @date 2020-06-28
        typeDataDao.deleteDataByidstrue(ids);
        int result = typeDataDao.deleteDataByidTrue(ids);
        try{
            //向队列推送大类及类定义数据
            sender.sendCiTypeAndItemData();
        }catch (Exception e){
            logger.error("向队列推送大类及类定义数据异常",e);
        }
        return result;
    }

    /**
     * 拼接SQL
     */
    @Override
    public String getSql(Map<String, String> map) {
        // Map dataMap = new HashMap();
        // 根据大类ID获取字段列表
        List<TypeItem> list = findItemByTid(map.get("tid"));
        //如果大类下有字段
        if (list != null && list.size() > 0) {

            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                // 获取映射数据列的集合,返回为DATA_1,DATA_2等,用于拼接查询SQL
                arr.add(list.get(i).getMp_ci_item());
            }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) {         	
            	sb.append("SELECT I.CI_CODE as ciCode,IFNULL(I.CI_NAME,I.CI_CODE) as ciName,CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            } else {           	
            	sb.append("SELECT I.CI_CODE as ciCode,IFNULL(I.CI_NAME,I.CI_CODE) as ciName,CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            }
            for (int i = 0; i < arr.size(); i++) {
                sb.append(arr.get(i) + ",");
            }
            // 去掉多余的,号
            String sqlStr = sb.toString().substring(0, sb.length() - 1);

            StringBuffer buffer = new StringBuffer();
            buffer.append(sqlStr);

            if (map.get("ciCodes") != null && "".equals(map.get("ciCodes"))) {
                if(map.get("domainId") != null){
                    buffer.append(" ,C.CI_TYPE_MC,DATA_1,C.CI_CODE  FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                            + map.get("tid")+") AS C ON C.ID = D.ID  WHERE I.CI_CODE='"
                            + map.get("ciCodes")
                            +"' and D.domain_id in("+map.get("domainId")+")");
                }else{
                    buffer.append(" ,C.CI_TYPE_MC,DATA_1,C.CI_CODE FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                            + map.get("tid")+") AS C ON C.ID = D.ID  WHERE I.CI_CODE='"
                            + map.get("ciCodes")+"'");
                }
            } else {
                if(map.get("domainId") != null){
                    // 组合SQL
                    buffer.append(" FROM IOMCI.IOM_CI_TYPE_DATA AS D,IOMCI.IOM_CI_INFO AS I WHERE I.ID=D.ID AND I.CI_TYPE_ID = "
                            + map.get("tid") + " AND I.YXBZ=1 AND I.DOMAIN_ID in("
                            + map.get("domainId")+") AND D.DOMAIN_ID in("
                            + map.get("domainId")+")"
                            );
                }else{
                    // 组合SQL
                    buffer.append(" FROM IOMCI.IOM_CI_TYPE_DATA AS D,IOMCI.IOM_CI_INFO AS I WHERE I.ID=D.ID AND I.CI_TYPE_ID = "
                            + map.get("tid") + " AND I.YXBZ=1 ");
                }
                if (!"".equals(map.get("search")) && null != map.get("search")) {
                        buffer.append(" AND I.ATTRS_STR LIKE '%"
                                + map.get("search") + "%'");
                }
                if (!"".equals(map.get("pageIndex"))
                        && null != map.get("pageIndex")
                        && !"".equals(map.get("pageCount"))
                        && null != map.get("pageCount")) {
                    buffer.append(" limit " + (Integer.parseInt(map.get("pageIndex")) - 1) * Integer.parseInt(map.get("pageCount"))
                            + ", " + map.get("pageCount"));
                }

            }

            return buffer.toString();
        } else {
            try {
                throw new Exception("大类下无字段!不能查询数据!");
            } catch (Exception e) {
                return null;
            }
        }

        // return "";
    }

    @Override
    public String getSqlNoPage(Map<String, String> map) {
    	if (map!=null) {
            //数据域ID,区分不同省公司用户查询的数据
            String domainId = TokenUtils.getTokenDataDomainId();
    		map.put("pageIndex", null);
    		map.put("pageCount", null);
    		map.put("domainId",domainId);
    		return getSql(map);
		}
    	return null;
    }
    
    @Override
    public String getSqlNoPageToHump(Map<String, String> map) {
    	if (map!=null) {
            //数据域ID,区分不同省公司用户查询的数据
            String domainId = TokenUtils.getTokenDataDomainId();
    		map.put("pageIndex", null);
    		map.put("pageCount", null);
    		map.put("domainId",domainId);
    		return getSql(map);
		}
    	return null;
    }

    @Override
    public TypeData findDataByTid(Map<String, Object> map) {
        return typeDataDao.findDataByTid(map);
    }

    /**
     * 根据TYPE_id 和CI_id 获取所需数据信息
     *
     * @param map
     * @return
     */
    @Override
    public Map getCiInfo(Map<String, String> map) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        Map dataMap = new LinkedHashMap();
        // 根据大类ID获取字段列表
        List<TypeItem> list = findItemByTid(map.get("tid"));
        Map<String, String> nameMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            nameMap.put(list.get(i).getMp_ci_item(), list.get(i).getAttr_name());
        }

        if (list.size() > 0) {
            // 如果大类下有字段
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                // 获取映射数据列的集合,返回为DATA_1,DATA_2等,用于拼接查询SQL
                arr.add(list.get(i).getMp_ci_item());
            }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            } else {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID ");
            }
            for (int i = 0; i < arr.size(); i++) {
                sb.append(arr.get(i) + ",");
            }
            // 去掉多余的,号
            String sqlStr = sb.toString().substring(0, sb.length() - 1);

            StringBuffer buffer = new StringBuffer();
            buffer.append(sqlStr);
            if (domainId !=null){
                buffer.append(" ,C.CI_TYPE_MC,C.CI_CODE ciCode,IFNULL(C.CI_NAME,C.CI_CODE) ciName FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                        + map.get("tid")
                        + " AND I.DOMAIN_ID in("+domainId+") AND T.DOMAIN_ID in("+domainId+")) AS C ON C.ID = D.ID  WHERE C.CI_CODE ='"
                        + map.get("ciCode")+"' AND D.DOMAIN_ID in("+domainId+")");
            }else{
                buffer.append(" ,C.CI_TYPE_MC,C.CI_CODE ciCode,IFNULL(C.CI_NAME,C.CI_CODE) FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                        + map.get("tid")
                        + ") AS C ON C.ID = D.ID  WHERE C.CI_CODE ='"
                        + map.get("ciCode")+"'");
            }

            List data = typeItemDao.findDataMap(buffer.toString());

            for (int i = 0; i < data.size(); i++) {
                Map keyMap = (Map) data.get(i);
                for (Object k : keyMap.keySet()) {
                    if (nameMap.get(k) != null) {
                        dataMap.put(nameMap.get(k), keyMap.get(k));
                    }

                }

            }
            return dataMap;
        }

        return null;
    }

    /**
     * 根据TYPE_id 和CI_id 获取所需数据信息(无数据域)
     *
     * @param map
     * @return
     */
    @Override
    public Map getCiInfoNoDomain(Map<String, String> map) {

        Map dataMap = new LinkedHashMap();
        // 根据大类ID获取字段列表
        List<TypeItem> list = findItemByTid(map.get("tid"));
        Map<String, String> nameMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            nameMap.put(list.get(i).getMp_ci_item(), list.get(i).getAttr_name());
        }
        nameMap.put("CI_TYPE_MC", "大类名称");
        nameMap.put("ciCode", "CI编码");
        // 如果大类下有字段
        if (list.size() > 0) {
            List<String> arr = new ArrayList<String>();
            // 获取映射数据列的集合,返回为DATA_1,DATA_2等,用于拼接查询SQL
            for (int i = 0; i < list.size(); i++) {
                arr.add(list.get(i).getMp_ci_item());
            }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            } else {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID ");
            }
            for (int i = 0; i < arr.size(); i++) {
                sb.append(arr.get(i) + ",");
            }
            // 去掉多余的,号
            String sqlStr = sb.toString().substring(0, sb.length() - 1);

            StringBuffer buffer = new StringBuffer();
            buffer.append(sqlStr);
            buffer.append(" ,C.CI_TYPE_MC,C.CI_CODE as ciCode,IFNULL(C.CI_NAME,C.CI_CODE) as ciName FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                    + map.get("tid")
                    + ") AS C ON C.ID = D.ID  WHERE C.CI_CODE='"
                    + map.get("ciCode")+"'");

            List data = typeItemDao.findDataMap(buffer.toString());

            for (int i = 0; i < data.size(); i++) {
                Map keyMap = (Map) data.get(i);
                for (Object k : keyMap.keySet()) {
                    if (nameMap.get(k) != null) {
                        dataMap.put(nameMap.get(k), keyMap.get(k));
                    }
                }
            }
            return dataMap;
        }

        return null;
    }
    
    /**
     * 根据TYPE_id 和CI_id 获取所需数据信息(无数据域)
     *
     * @param map
     * @return
     */
    @Override
    public Map getCiInfoNoDomainByDimension(Map<String, String> map) {

        Map dataMap = new LinkedHashMap();
        // 根据大类ID获取字段列表
        List<TypeItem> list = findItemByTid(map.get("tid"));
        Map<String, String> nameMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            nameMap.put(list.get(i).getMp_ci_item(), list.get(i).getAttr_name());
        }
        // 如果大类下有字段
        if (list.size() > 0) {
            List<String> arr = new ArrayList<String>();
            // 获取映射数据列的集合,返回为DATA_1,DATA_2等,用于拼接查询SQL
            for (int i = 0; i < list.size(); i++) {
                arr.add(list.get(i).getMp_ci_item());
            }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            } else {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID ");
            }
            for (int i = 0; i < arr.size(); i++) {
                sb.append(arr.get(i) + ",");
            }
            // 去掉多余的,号
            String sqlStr = sb.toString().substring(0, sb.length() - 1);

            StringBuffer buffer = new StringBuffer();
            buffer.append(sqlStr);
            buffer.append(" ,C.CI_TYPE_MC,C.CI_CODE ciCode,IFNNULL(C.CI_NAME,C.CI_CODE) ciName FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                    + map.get("tid")
                    + ") AS C ON C.ID = D.ID  WHERE C.CI_CODE='"
                    + map.get("ciCode")+"'");

            List data = typeItemDao.findDataMap(buffer.toString());

            for (int i = 0; i < data.size(); i++) {
                Map keyMap = (Map) data.get(i);
                for (Object k : keyMap.keySet()) {
                    if (nameMap.get(k) != null) {
                        dataMap.put(nameMap.get(k), keyMap.get(k));
                    }

                }

            }
            return dataMap;
        }

        return null;
    }
    /**
     * 判断属性名称是否存在
     *
     * @param attr_name
     * @param ci_type_id
     * @return
     */
    @Override
    public boolean itemNameExists(String attr_name, String ci_type_id) {
        return typeItemDao.itemNameExists(attr_name, ci_type_id) > 0;
    }

    /**
     * 字段导入
     */
    @Override
    public boolean importExcelItem(String jsonStr, String ci_type_id, HttpServletRequest request) {
        List<TypeItem> itemList;
        JavaType javaType = getCollectionType(ArrayList.class, TypeItem.class);
        try {
            itemList = mapper.readValue(jsonStr, javaType);
            for (int i = 0; i < itemList.size(); i++) {
                itemList.get(i).setIs_major(0);
                itemList.get(i).setIs_requ(0);
                itemList.get(i).setCi_type_id(ci_type_id);
                itemList.get(i).setAttr_type("字符串");
                itemList.get(i).setCjr_id(TokenUtils.getTokenUserId());
                addItem(itemList.get(i));
            }
            return true;
        } catch (JsonParseException e) {
            return false;
        } catch (JsonMappingException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public JavaType getCollectionType(Class<?> collectionClass,
                                      Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass,
                elementClasses);
    }

    /**
     * 导出字段信息
     */
    @Override
    public void exportExcelItem(HSSFWorkbook workbook,
                                XSSFWorkbook workbook2007, List<String> tid,
                                HttpServletResponse response) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        List<Type> typeList = new ArrayList<Type>();
        if (tid == null) {
			tid = new ArrayList<String>();
		}
        if (tid.size() == 0) {
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
            // 获取大类的字段信息
            List<TypeItem> items = typeItemDao.findItemByTid(tid.get(i));
            List<String> nameList = new ArrayList<String>();
            for (int j = 0; j < items.size(); j++) {
                nameList.add(items.get(j).getAttr_name());
            }
            String[] strings = new String[nameList.size()];
            // 将字段LIST转为数组 作为excel的表头
            nameList.toArray(strings);
            // excel文件名
            String fileName = "data";
            ExcelUtilWrapper<Dir> util = new ExcelUtilWrapper<Dir>();

            util.exportExcel(workbook, workbook2007, fileName, typeList.get(i)
                            .getCi_type_mc(), strings, null, response,
                    ExcelUtilWrapper.EXCEL_FILE_2003);

        }

    }

    /**
     * 根据CI_ID集合，获取所有CI所需的信息
     */
    @Override
    public List findCiList(List<String> ciList) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        List list = new ArrayList();
        for (int i = 0; i < ciList.size(); i++) {
            TypeData typeData = typeDataDao.findDataById(ciList.get(i),domainId);
            Info info = infoDao.findInfoById(ciList.get(i),domainId);

            if(info!=null){
                if ("1".equals(info.getYxbz()) && typeData!=null) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("ciCode", ciList.get(i));
                    map.put("tid", typeData.getCi_type_id());
                    Map dataMap = getCiInfo(map);
                    list.add(dataMap);
                }else{
                	list.add(new HashMap());
                }
            }else{
            	list.add(new HashMap());
            }


        }
        return list;
    }

    /**
     * 根据标签名获取大类字段,导入数据库
     *
     * @param
     * @return
     */
    @Override
    public void findDfileList(HttpServletRequest request) {
        List<String> list = new ArrayList<String>();
        // 厂站
        list.add("Substation");
        // 开关
        list.add("Breaker");
        // 刀闸
        list.add("Disconnector");
        // 接地刀闸
        list.add("GroundDisconnector");
        // 变压器绕组
        list.add("TransformerWinding");
        // 母线
        list.add("BusbarSection");
        // 交流线段端点
        list.add("ACLineSegment");
        // 发电机
        list.add("SynchronousMachine");
        // 电容电抗
        list.add("Compensator_P");

        List<String> idList = new ArrayList<String>();
        idList.add("1000000000000000404");
        idList.add("1000000000000000405");
        idList.add("1000000000000000406");
        idList.add("1000000000000000409");
        idList.add("1000000000000000410");
        idList.add("1000000000000000411");
        idList.add("1000000000000000412");
        idList.add("1000000000000000413");
        idList.add("1000000000000000414");
        for (int i = 0; i < list.size(); i++) {
            // 根据标签名返回此标签所有数据LIST
            List data = dfileService.getDfile(list.get(i));

            String item = data.get(0).toString();
            StringBuffer sb = new StringBuffer("[");
            List<String> itemList = Arrays.asList(item.split("        "));
            List arrList = new ArrayList(itemList);
            arrList.remove(0);
            for (int k = 0; k < arrList.size(); k++) {
                sb.append("{\"attr_name\":\"" + arrList.get(k) + "\"},");
            }
            String json = sb.toString()
                    .substring(0, sb.toString().length() - 1) + "]";
            // 导入字段
            importExcelItem(json, idList.get(i), request);

        }

    }

    /**
     * 根据标签名获取大类数据,导入数据库
     *
     * @param cjr
     */
    @Override
    public void findDfileData(String cjr) {
        List<String> list = new ArrayList<String>();
        // 厂站
        list.add("Substation");
        // 开关
        list.add("Breaker");
        // 刀闸
        list.add("Disconnector");
        // 接地刀闸
        list.add("GroundDisconnector");
        // 变压器绕组
        list.add("TransformerWinding");
        // 母线
        list.add("BusbarSection");
        // 交流线段端点
        list.add("ACLineSegment");
        // 发电机
        list.add("SynchronousMachine");
        // 电容电抗
        list.add("Compensator_P");

        List<String> idList = new ArrayList<String>();
        idList.add("1000000000000000404");
        idList.add("1000000000000000405");
        idList.add("1000000000000000406");
        idList.add("1000000000000000409");
        idList.add("1000000000000000410");
        idList.add("1000000000000000411");
        idList.add("1000000000000000412");
        idList.add("1000000000000000413");
        idList.add("1000000000000000414");

        for (int i = 0; i < list.size(); i++) {
            // 根据标签名返回此标签所有数据LIST
            List data = dfileService.getDfile(list.get(i));
            StringBuffer dataBuffer = new StringBuffer("[");
            String dataJson = "";

            for (int j = 1; j < data.size(); j++) {
                List<String> dataList = Arrays.asList(data.get(j).toString()
                        .split("        "));
                List arrDataList = new ArrayList(dataList);
                arrDataList.remove(0);
                StringBuffer buffer = new StringBuffer("{");
                for (int k = 0; k < arrDataList.size(); k++) {
                    buffer.append("\"data_" + (k + 1) + "\":\""
                            + arrDataList.get(k) + "\",");
                }
                String dataJsonBuffer = buffer
                        .substring(0, buffer.length() - 1);
                dataBuffer.append(dataJsonBuffer + "},");
            }
            dataJson = dataBuffer.substring(0, dataBuffer.length() - 1) + "]";
            try {
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage());
            }

        }
    }

    /**
     * 根据TYPE_id 和CI_id 获取所需数据信息
     *
     * @param map
     * @return
     */
    @Override
    public List<Map> getAlarmCiInfo(Map<String, String> map) {
        List<Map> dataList = new ArrayList<Map>();
        // 根据大类ID获取字段列表
        List<TypeItem> list = findItemByTid(map.get("tid"));
        Map<String, String> nameMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            nameMap.put(list.get(i).getMp_ci_item(), list.get(i).getAttr_name());
        }
        nameMap.put("CI_TYPE_MC", "大类名称");
        nameMap.put("CI_CODE", "CI编码");
        nameMap.put("CI_NAME", "CI名称");

        if (list.size() > 0) {
            // 如果大类下有字段
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                // 获取映射数据列的集合,返回为DATA_1,DATA_2等,用于拼接查询SQL
                arr.add(list.get(i).getMp_ci_item());
            }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            } else {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID ");
            }
            for (int i = 0; i < arr.size(); i++) {
                sb.append(arr.get(i) + ",");
            }
            // 去掉多余的,号
            String sqlStr = sb.toString().substring(0, sb.length() - 1);

            StringBuffer buffer = new StringBuffer();
            buffer.append(sqlStr);
            buffer.append(" ,C.CI_TYPE_MC,C.CI_CODE,IFNULL(C.CI_NAME,C.CI_CODE) CI_NAME FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                    + map.get("tid")
                    + ") AS C ON C.ID = D.ID");

            List data = typeItemDao.findDataMap(buffer.toString());

            for (int i = 0; i < data.size(); i++) {
                Map keyMap = (Map) data.get(i);
                Map dataMap = new LinkedHashMap();
                for (Object k : keyMap.keySet()) {
                    if (nameMap.get(k) != null) {
                        dataMap.put(nameMap.get(k), keyMap.get(k));

                    }

                }
                dataList.add(dataMap);

            }
            return dataList;
        }

        return null;
    }

    /**
     * 根据TYPE_id 和CI_id 获取所需数据信息
     *
     * @param map
     * @return
     */
    @Override
    public List<Map> getCiInfoByConfigId(Map<String, String> map) {
        List<Map> dataList = new ArrayList<Map>();
        String dataCode = typeItemDao.findDataCodebyTid(map.get("tid"));
        // 根据大类ID获取字段列表
        List<TypeItem> list = findItemByTid(map.get("tid"));
        Map<String, String> nameMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            nameMap.put(list.get(i).getMp_ci_item(), list.get(i).getAttr_name());
        }
        nameMap.put("CI_TYPE_MC", "CI_TYPE_MC");
        nameMap.put("CI_CODE", "CI_CODE");
        nameMap.put("CI_NAME", "CI_NAME");

        if (list.size() > 0) {
            // 如果大类下有字段
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                // 获取映射数据列的集合,返回为DATA_1,DATA_2等,用于拼接查询SQL
                arr.add(list.get(i).getMp_ci_item());
            }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID, ");
            } else {
                sb.append(" SELECT CONCAT(D.ID) as ID,CONCAT(D.CI_TYPE_ID) AS CI_TYPE_ID ");
            }
            for (int i = 0; i < arr.size(); i++) {
                sb.append(arr.get(i) + ",");
            }
            // 去掉多余的,号
            String sqlStr = sb.toString().substring(0, sb.length() - 1);

            StringBuffer buffer = new StringBuffer();
            buffer.append(sqlStr);
            buffer.append(" ,C.CI_TYPE_MC,C.CI_CODE,IFNULL(C.CI_NAME,C.CI_CODE)CI_NAME FROM IOMCI.IOM_CI_TYPE_DATA AS D INNER JOIN (SELECT I.*,T.CI_TYPE_MC FROM IOMCI.IOM_CI_INFO AS I,IOMCI.IOM_CI_TYPE AS T WHERE T.ID = I.CI_TYPE_ID AND T.ID="
                    + map.get("tid")
                    + ") AS C ON C.ID = D.ID where " + dataCode + "=" + map.get("id"));

            List data = typeItemDao.findDataMap(buffer.toString());

            for (int i = 0; i < data.size(); i++) {
                Map keyMap = (Map) data.get(i);
                Map dataMap = new LinkedHashMap();
                for (Object k : keyMap.keySet()) {
                    if (nameMap.get(k) != null) {
                        dataMap.put(nameMap.get(k), keyMap.get(k));

                    }

                }
                dataList.add(dataMap);

            }
            return dataList;
        }

        return null;
    }

	@Override
	public String findPK(String tid) {
		return typeItemDao.findPK(tid);
	}

	@Override
	public List<String> findItemVal(String typeId, String itemId, String value) {
		return typeItemDao.findItemVal(typeId, itemId, value);
	}

	@Override
	public List<IomCiKpi> findKpi() {

		return typeItemDao.findKpi();
	}

    @Override
    public Map<String, Object> getMappingCiData(String[] list){
        return typeItemDao.getMappingCiData(list);
    }

    @Override
    public List<Map<String, Object>> selectCiAttrs(String ciTypeId) {
        List<String> typeItemId = typeItemDao.selItemListByTypeIdAndCiBm(ciTypeId,null);
        List<TypeItem> typeItemIds = typeItemDao.selItemByTypeIdAndCiBm(ciTypeId,null);
        List<Map<String, Object>> mapList = typeItemDao.selectJiFangLists(ciTypeId,null,typeItemId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map map: mapList) {
            Map<String,Object> newMap =  new HashMap<>();
            newMap.put("ciId", map.get("ciId"));
            map.remove("ciId");
            String str = JsonUtils.mapToString(map);
            for (TypeItem typeItem: typeItemIds ) {
                if (typeItem.getIs_major()==1){
                    newMap.put("major", typeItem.getAttr_name());
                }
                if ("DATA_1".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_1\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_2".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_2\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_3".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_3\"",typeItem.getAttr_name()+"\"");
                }else {
                    str=str.replace(typeItem.getMp_ci_item(),typeItem.getAttr_name());
                }
            }
            newMap.put("jsonAttr", str);
            list.add(newMap);
        }
        List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
        if (list!= null && list.size()>0) {
            for (Map<String, Object> map : list) {
                if (map.get("ciId")!=null&&map.get("jsonAttr")!=null&&map.get("major")!=null) {
                    String ciId = map.get("ciId").toString();
                    String jsonAttr = map.get("jsonAttr").toString();
                    String major = map.get("major").toString();
                    if (StringUtils.isNotEmpty(ciId)&& StringUtils.isNotEmpty(jsonAttr)&& StringUtils.isNotEmpty(major)) {
                        JSONObject jsonObject = JSON.parseObject(jsonAttr);
                        //ciId,ciCode有可能会被类定义给占用了，建议给类定义加入保留字ciId,ciCode，目前不考虑这个
                        jsonObject.put("ciId", ciId);
                        jsonObject.put("ciCode", jsonObject.get(major));
                        resList.add(jsonObject);
                    }
                }
            }
        }
        return resList;
    }


    @Override
    public List<Map<String, Object>> findComputerRoomInfo(String computerRoom,String computerRoomArea,List<String> listRole,String computerRoomBuilding) {

    	//根据ciTypeBm查询出对应的ciTypeId---start
    	List<String> list=new ArrayList<String>();
    	list.add(computerRoom);
    	list.add(computerRoomArea);
    	IomCiTypeExample example=new IomCiTypeExample();
    	example.createCriteria().andCiTypeBmIn(list).andYxbzEqualTo(1);
    	List<IomCiType> list2=iomCiTypeMapper.selectByExample(example);
    	for(IomCiType iomCiType:list2) {
    		String ciTypeBm=iomCiType.getCiTypeBm();
    		String ciTypeId=iomCiType.getId();
    		if(computerRoom.equals(ciTypeBm)) {
    			computerRoom=ciTypeId;
    		}
    		if(computerRoomArea.equals(ciTypeBm)) {
    			computerRoomArea=ciTypeId;
    		}
    	}
    	//根据ciTypeBm查询出对应的ciTypeId---end

    	//根据大类机房地区的ID查询jsonAttr:{"主键":"德州调控机房（主）","父级":"德州市","权限角色":"德州管理员","排序":"5"}---start
        List<String> typeItemId = typeItemDao.selItemListByTypeIdAndCiBm(computerRoomArea,null);
        List<TypeItem> typeItemIds = typeItemDao.selItemByTypeIdAndCiBm(computerRoomArea,null);
        List<Map<String, Object>> mapList = typeItemDao.selectJiFangLists(computerRoomArea,null,typeItemId);
        List<Map<String, Object>> list5 = new ArrayList<>();
        for (Map map: mapList) {
            Map<String,Object> newMap =  new HashMap<>();
            newMap.put("ciId", map.get("ciId"));
            map.remove("ciId");
            String str = JsonUtils.mapToString(map);
            for (TypeItem typeItem: typeItemIds ) {
                if ("DATA_1".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_1\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_2".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_2\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_3".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_3\"",typeItem.getAttr_name()+"\"");
                }else {
                    str=str.replace(typeItem.getMp_ci_item(),typeItem.getAttr_name());
                }
            }
            newMap.put("jsonAttr", str);
            list5.add(newMap);
        }
    	List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
    	 if (list!= null && list.size()>0) {
             for (Map<String, Object> map : list5) {
            	 if (map.get("ciId")!=null&&map.get("jsonAttr")!=null) {
            		 String ciId = map.get("ciId").toString();
                     String jsonAttr = map.get("jsonAttr").toString();
                     if (StringUtils.isNotEmpty(ciId)&& StringUtils.isNotEmpty(jsonAttr)) {
                    	 resList.add(JSON.parseObject(jsonAttr));
                     }
                 }
             }
         }
    	//根据大类机房地区的ID查询jsonAttr:{"主键":"德州调控机房（主）","父级":"德州市","权限角色":"德州管理员","排序":"5"}---end

    	 List<Map<String, Object>> list3=selectTreeListAll(resList);
    	 list3=findRoleTreeList(list3,listRole);
    	 list3=findComputerRoomBuilding(list3,computerRoomBuilding);
    	 List<Map<String, Object>> list6=selectCiAttrs(computerRoom);
    	 sortList(list6, "sort");
    	 addChindData(list3,list6);
         return list3;
    }

    @Override
    public List<String> findDataIdByTidList(String ids) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        List<String> stringList = typeItemDao.findDataIdByTidList(ids,null);
        return stringList;
    }

    /**
     * 根据CI_id集合，获取所有所需CI_信息(无数据权限)
     * @param ciList
     * @return
     */
    @Override
    public List findCiListNoDomain(List<String> ciList) {
        List list = new ArrayList();
        for (int i = 0; i < ciList.size(); i++) {
            TypeData typeData = typeDataDao.findDataById(ciList.get(i),null);
            Info info = infoDao.findInfoById(ciList.get(i),null);

            if(info!=null){
                if ("1".equals(info.getYxbz()) && typeData!=null) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("ci_id", info.getId());
                    map.put("ciCode", ciList.get(i));
                    map.put("tid", typeData.getCi_type_id());
                    Map dataMap = getCiInfoNoDomain(map);
                    list.add(dataMap);
                }else{
                    list.add(new HashMap());
                }
            }else{
                list.add(new HashMap());
            }


        }
        return list;
    }
    
    /**
     * 根据CI_id集合，获取所有所需CI_信息(无数据权限，8009查询维度使用)
     * @param ciList
     * @return
     */
    @Override
    public List findCiListByDimension(List<String> ciList) {
        List list = new ArrayList();
        for (int i = 0; i < ciList.size(); i++) {
            TypeData typeData = typeDataDao.findDataById(ciList.get(i),null);
            Info info = infoDao.findInfoById(ciList.get(i),null);

            if(info!=null){
                if ("1".equals(info.getYxbz()) && typeData!=null) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("ci_id", ciList.get(i));
                    map.put("ciCode", ciList.get(i));
                    map.put("tid", typeData.getCi_type_id());
                    Map dataMap = getCiInfoNoDomainByDimension(map);
                    list.add(dataMap);
                }else{
                    list.add(new HashMap());
                }
            }else{
                list.add(new HashMap());
            }


        }
        return list;
    }

    @Override
    public List<TypeItem> selItemByTypeId(String ciClassId) {
        return typeItemDao.selItemByTypeId(ciClassId);
    }

    public List<Map<String, Object>> findComputerRoomBuilding(List<Map<String, Object>> list3,String computerRoomBuilding) {
    	List<Map<String, Object>> retrunList=new ArrayList<Map<String,Object>>();
    	for(Map<String, Object> map1:list3) {
    		String roleName=(String) map1.get("类型");
    		boolean flag=true;

    			if(roleName.equals(computerRoomBuilding)) {
    				retrunList.add(map1);
    				flag=false;
    			}


    		if(flag) {
    			List<Map<String, Object>> list2=(List<Map<String, Object>>) map1.get("child");
    			retrunList.addAll(findComputerRoomBuilding(list2,computerRoomBuilding));
    		}

    	}

    	return retrunList;
	}

    @Override
    public JSONObject getCiItemByBm(String ciBm) {
        List<String> typeItemId = typeItemDao.selItemListByTypeIdAndCiBm(null,ciBm);
        List<TypeItem> typeItemIds = typeItemDao.selItemByTypeIdAndCiBm(null,ciBm);
        List<Map<String, Object>> mapList = typeItemDao.selectJiFangLists(null,ciBm,typeItemId);
        List<Map<String, Object>> list5 = new ArrayList<>();
        for (Map map: mapList) {
            Map<String,Object> newMap =  new HashMap<>();
            newMap.put("ciId", map.get("ciId"));
            map.remove("ciId");
            String str = JsonUtils.mapToString(map);
            for (TypeItem typeItem: typeItemIds ) {
                if ("DATA_1".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_1\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_2".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_2\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_3".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_3\"",typeItem.getAttr_name()+"\"");
                }else {
                    str=str.replace(typeItem.getMp_ci_item(),typeItem.getAttr_name());
                }
            }
            newMap.put("jsonAttr", str);
            list5.add(newMap);
        }
    	if (list5!=null && list5.size()>0) {
    		return JSON.parseObject(list5.get(0).get("jsonAttr").toString());
		}
    	return null;
	}


    @Override
    public List<Map<String, Object>> selectJiFangList(String computerRoom,String computerRoomArea,List<String> listRole) {
    	//根据ciTypeBm查询出对应的ciTypeId---start
    	List<String> list=new ArrayList<String>();
    	list.add(computerRoom);
    	list.add(computerRoomArea);
    	IomCiTypeExample example=new IomCiTypeExample();
    	example.createCriteria().andCiTypeBmIn(list).andYxbzEqualTo(1);
    	List<IomCiType> list2=iomCiTypeMapper.selectByExample(example);
    	for(IomCiType iomCiType:list2) {
    		String ciTypeBm=iomCiType.getCiTypeBm();
    		String ciTypeId=iomCiType.getId();
    		if(computerRoom.equals(ciTypeBm)) {
    			computerRoom=ciTypeId;
    		}
    		if(computerRoomArea.equals(ciTypeBm)) {
    			computerRoomArea=ciTypeId;
    		}
    	}
    	//根据ciTypeBm查询出对应的ciTypeId---end

    	//根据大类机房地区的ID查询jsonAttr:{"主键":"德州调控机房（主）","父级":"德州市","权限角色":"德州管理员","排序":"5"}---start
        List<String> typeItemId = typeItemDao.selItemListByTypeIdAndCiBm(computerRoomArea,null);
        List<TypeItem> typeItemIds = typeItemDao.selItemByTypeIdAndCiBm(computerRoomArea,null);
        List<Map<String, Object>> mapList = typeItemDao.selectJiFangLists(computerRoomArea,null,typeItemId);
        List<Map<String, Object>> list5 = new ArrayList<>();
        for (Map map: mapList) {
            Map<String,Object> newMap =  new HashMap<>();
            newMap.put("ciId", map.get("ciId"));
            map.remove("ciId");
            String str = JsonUtils.mapToString(map);
            for (TypeItem typeItem: typeItemIds ) {
                if ("DATA_1".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_1\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_2".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_2\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_3".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_3\"",typeItem.getAttr_name()+"\"");
                }else {
                    str=str.replace(typeItem.getMp_ci_item(),typeItem.getAttr_name());
                }
            }
            newMap.put("jsonAttr", str);
            list5.add(newMap);
        }
    	List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
    	 if (list!= null && list.size()>0) {
             for (Map<String, Object> map : list5) {
            	 if (map.get("ciId")!=null&&map.get("jsonAttr")!=null) {
            		 String ciId = map.get("ciId").toString();
                     String jsonAttr = map.get("jsonAttr").toString();
                     if (StringUtils.isNotEmpty(ciId)&& StringUtils.isNotEmpty(jsonAttr)) {
                    	 JSONObject jsonObject = JSON.parseObject(jsonAttr);
                    	 resList.add(jsonObject);
                     }
                 }
             }
         }

    	 sortList(resList, "排序");

    	//根据大类机房地区的ID查询jsonAttr:{"主键":"德州调控机房（主）","父级":"德州市","权限角色":"德州管理员","排序":"5"}---end

    	 List<Map<String, Object>> list3=selectTreeListAll(resList);
    	 if (listRole!=null && listRole.size()>0){
             list3=findRoleTreeList(list3,listRole);
         }
    	 List<Map<String, Object>> list6=selectCiAttrs(computerRoom);
         //给list6(data数据)按照sort排序整理数据start-->在进入递归遍历之前把数据排列好
    	 List<Map<String, Object>> list7=new ArrayList<Map<String,Object>>();
    	 Set<String> set=new HashSet<String>();
    	 if(list6!=null && list6.size()>0) {
    		 for(Map<String, Object> map:list6) {
        		 String suoshuString=(String) map.get("所属");
        		 set.add(suoshuString);
        	 }
    	 }

    	 for(String str:set) {
    		 List<Map<String, Object>> list8=new ArrayList<Map<String,Object>>();
    		 for(Map<String, Object> map:list6) {
    			 String suoshuString=(String) map.get("所属");
    			 if(str.equals(suoshuString)) {
    				 list8.add(map);
    			 }
    		 }
    		 if(list8!=null && list8.size()>0) {
    			 if(list8.size()>1) {
    				 sortList(list8, "sort");
    			 }
				 for(Map<String, Object> map:list8) {
					 list7.add(map);
				 }
			 }
    	 }
    	//给list6(data数据)按照sort排序整理数据end
    	 addChindData(list3,list7);
        if (list3 == null || list3.size() == 0) {
            return null;
        }
    	 Map<String, Object> map=list3.get(0);
    	 String zhuJian=(String) map.get("主键");
    	 if("山东省".equals(zhuJian)) {
    		 list3=(List<Map<String, Object>>) map.get("child");
    	 }
         return list3;
    }

    /**
     * list按照key值排序
     * @param list
     * @param key
     */
	private void sortList(List<Map<String, Object>> list, final String key) {
		if (StringUtils.isNotEmpty(key)) {
			if (list != null && list.size() > 0) {
				Collections.sort(list, new Comparator<Map<String, Object>>() {
					@Override
					public int compare(Map<String, Object> o1, Map<String, Object> o2) {
						String num1 = (String) o1.get(key);
						String num2 = (String) o2.get(key);
						if (StringUtils.isNotEmpty(num1)&&StringUtils.isNotEmpty(num2)) {
							if (Double.valueOf(num1) > Double.valueOf(num2)) {
								return 1;
							}
							if (Double.valueOf(num1).equals(Double.valueOf(num2))) {
								return 0;
							}
						}
						return -1;
					}
				});

				for (Map<String, Object> map : list) {
					map.remove(key);
				}
			}
		}
	}

    public void addChindData(List<Map<String, Object>> treeList,List<Map<String, Object>> dataList) {
	   	 for(Map<String, Object> map:treeList) {
			 String zhuJian=(String) map.get("主键");
			 List<Map<String, Object>> list7=(List<Map<String, Object>>) map.get("child");
			 if (list7==null) {
				 list7=new ArrayList<Map<String,Object>>();
			 }
			 if (list7.size()>0) {
				 addChindData(list7,dataList);
			 }
			 for(Map<String, Object> map1:dataList) {
				 String suoShu=(String) map1.get("所属");
				 if(zhuJian.equals(suoShu)) {
					 list7.add(map1);
				 }
			 }
			 String fuji = (String) map.get("父级");
			 for (Map<String, Object> map2 : list7) {
				 map2.put("祖所属", fuji);
			}
			 map.put("child", list7);
		 }
	}

    public List<Map<String, Object>> findRoleTreeList(List<Map<String, Object>> list3,List<String> list) {
    	List<Map<String, Object>> retrunList=new ArrayList<Map<String,Object>>();
    	for(Map<String, Object> map1:list3) {
    		String roleName=(String) map1.get("权限角色");
    		boolean flag=true;
    		for(String str:list) {
    			if(roleName.equals(str)) {
    				retrunList.add(map1);
    				flag=false;
    				break;
    			}

    		}
    		if(flag) {
    			List<Map<String, Object>> list2=(List<Map<String, Object>>) map1.get("child");
    			retrunList.addAll(findRoleTreeList(list2,list));
    		}

    	}

    	return retrunList;
	}

    public Map<String, Object> name(Map<String, Object> map,List<String> list) {
    	String roleNameString=(String) map.get("权限角色");
    	List<Map<String, Object>> list1=(List<Map<String, Object>>) map.get("child");
    	for(String str:list) {
    		if(str.equals(roleNameString)) {
    			return map;
    		}
    	}
		if(list1.size()>0) {
			for(Map<String, Object> map2:list1) {
				name(map2,list);
			}
		}else {
			return map;
		}
		return map;
	}

    @Override
    public List<Map<String, Object>> selectCiAttrsById(String ciTypeId) {
        List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
        StringBuffer sb = new StringBuffer();
        String[] temp = ciTypeId.split(",");
        for (int i = 0; i < temp.length; i++) {
            if (!"".equals(temp[i]) && temp[i] != null){
                sb.append("'" + temp[i] + "',");
            }
        }
        String result = sb.toString();
        String tp = result.substring(result.length() - 1, result.length());
        if (",".equals(tp)){
            ciTypeId = result.substring(0, result.length() - 1);
        }else{
            ciTypeId = result;
        }
        List<String> ciTypeList =typeItemDao.findCiTypeListByCiIds(ciTypeId);
        for (String str:ciTypeList) {
            List<Map<String, Object>> res = selectCiAttrsByIds(str,ciTypeId);
            resList.addAll(res);

        }
        return resList;
    }
    public List<Map<String, Object>> selectCiAttrsByIds(String ciTypeId,String ciIds){
        List<String> typeItemId = typeItemDao.selItemListByTypeIdAndCiBm(ciTypeId,null);
        List<TypeItem> typeItemIds = typeItemDao.selItemByTypeIdAndCiBm(ciTypeId,null);
        List<Map<String, Object>> mapList = typeItemDao.selectCiAttrsByIds(ciTypeId,null,typeItemId,ciIds);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map map: mapList) {
            Map<String,Object> newMap =  new HashMap<>();
            newMap.put("ciId", map.get("ciId"));
            map.remove("ciId");
            String str = JsonUtils.mapToString(map);
            for (TypeItem typeItem: typeItemIds ) {
                if (typeItem.getIs_major()==1){
                    newMap.put("major", typeItem.getAttr_name());
                }
                if ("DATA_1".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_1\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_2".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_2\"",typeItem.getAttr_name()+"\"");
                }else if("DATA_3".equals(typeItem.getMp_ci_item())){
                    str=str.replace("DATA_3\"",typeItem.getAttr_name()+"\"");
                }else {
                    str=str.replace(typeItem.getMp_ci_item(),typeItem.getAttr_name());
                }
            }
            newMap.put("jsonAttr", str);
            list.add(newMap);
        }
        List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
        if (list!= null && list.size()>0) {
            for (Map<String, Object> map : list) {
                if (map.get("ciId")!=null&&map.get("jsonAttr")!=null&&map.get("major")!=null) {
                    String ciId = map.get("ciId").toString();
                    String jsonAttr = map.get("jsonAttr").toString();
                    String major = map.get("major").toString();
                    if (StringUtils.isNotEmpty(ciId)&& StringUtils.isNotEmpty(jsonAttr)&& StringUtils.isNotEmpty(major)) {
                        JSONObject jsonObject = JSON.parseObject(jsonAttr);
                        //ciId,ciCode有可能会被类定义给占用了，建议给类定义加入保留字ciId,ciCode，目前不考虑这个
                        jsonObject.put("ciId", ciId);
                        jsonObject.put("ciCode", jsonObject.get(major));
                        resList.add(jsonObject);
                    }
                }
            }
        }
        return resList;
    }



    public List<Map<String, Object>> selectTreeListAll(List<Map<String, Object>> treeList) {
		// 根节点
		List<Map<String, Object>> root = new ArrayList<Map<String, Object>>();
		// 获取父节点
		for (Map<String, Object> nav : treeList) {
			if (ConstantUtil.JIFANG_PARENT.equals(nav.get("父级"))) {
			    // 父节点是0的，为根节点。
				root.add(nav);
			}
		}

		// 为根菜单设置子菜单，getClild是递归调用的
		for (Map<String, Object> nav : root) {
			/* 获取根节点下的所有子节点 使用getChild方法 */
			List<Map<String, Object>> childList = getChild((String) nav.get("主键"), treeList);
			// nav.setChildren(childList);// 给根节点设置子节点
			nav.put("child", childList);
		}

		return root;
	}

	public List<Map<String, Object>> getChild(String id, List<Map<String, Object>> allTree) {
		// 子菜单
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> nav : allTree) {
			// 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
			// 相等说明：为该根节点的子节点。
			if (id.equals(nav.get("父级"))) {
				childList.add(nav);
			}
		}
		// 递归
		for (Map<String, Object> nav : childList) {
			List<Map<String, Object>> list = getChild((String) nav.get("主键"), allTree);
			nav.put("child", list);
		}
		// 如果节点下没有子节点，返回一个空List（递归退出）
		if (childList.size() == 0) {
			return new ArrayList<Map<String, Object>>();
		}
		return childList;
	}

	public List<Map<String, Object>> getChildRecursion(List<Map<String, Object>> allTree,Map<String, Object> map) {
		String suoShu=(String) map.get("所属");
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map1:allTree) {
			String zhuJianString=(String) map1.get("主键");
			if(suoShu.equals(zhuJianString)) {
				childList.add(map);
				map1.put("child", childList);
			}else {
				List<Map<String, Object>> list=(List<Map<String, Object>>) map1.get("child");
				if(list==null || list.size()==0) {
					continue;
				}else {
					getChildRecursion(list,map);
				}
			}
		}
		return allTree;
	}

    /**
     * 所属机柜为空校验
     * @return
     */
    @Override
    public List<Map<String, Object>> jhIsnull(String ciTypeId, String sbmc, String checkColumn) {
        return typeItemDao.jhIsnull(ciTypeId, sbmc, checkColumn);
    }

    /**
     * 所属机柜是否存在校验
     * @return
     */
    @Override
    public List<Map<String, Object>> jhIsexis(String ciTypeId, String sbbh, String sbmc, String checkColumn) {
        return typeItemDao.jhIsexis(ciTypeId, sbbh, sbmc, checkColumn);
    }

    /**
     * U位数据为空校验
     * @return
     */
    @Override
    public List<Map<String, Object>> uDataIsnull(String ciTypeId, String sbmc, String checkColumn) {
        return typeItemDao.uDataIsnull(ciTypeId, sbmc, checkColumn);
    }

    /**
     * U位数据格式校验
     * @return
     */
    @Override
    public List<Map<String, Object>> uDatasFormat(String ciTypeId, String sbbh, String sbmc, String checkColumn) {
        return typeItemDao.uDatasFormat(ciTypeId, sbbh, sbmc, checkColumn);
    }

    /**
     * U位数据重复校验
     * @return
     */
    @Override
    public List<Map<String, Object>> getRepeatDataId(String ciTypeId, String sbbh, String sbmc, String ss, String checkColumn){
        return typeItemDao.getRepeatDataId(ciTypeId, sbbh, sbmc, ss, checkColumn);
    }

    /**
     * 根据大类ID获取字段列表及对应的字段数据
     * @param tid 大类ID
     * @param ciId CIID
     * @return
     */
    @Override
    public List<TypeItem> findItemAndDataByTid(String tid, String ciId,String ciCode) {

        List<TypeItem> typeItemList = typeItemDao.findItemByTid(tid);
        ciId = typeDataDao.findCiIdByCiCode(tid,ciCode);
        for (TypeItem typeItem : typeItemList){
            String data=typeDataDao.finDataByidAndItem(ciId,typeItem.getMp_ci_item());
            typeItem.setCiValue(data);
        }
        return  typeItemList;
    }
    
    /**
     * 将Map中的key由下划线转换为驼峰
     *
     * @param map
     * @return
     */
    public static Map<String, Object> formatHumpName(Map<String, Object> map) {
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

    public static String toFormatCol(String colName) {
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

    private List<String> removeDuplicationList(List<String> list, List<String> newList) {
        //去除子集后的集合
        List<String> resList = new ArrayList<String>();
        if (list !=null && newList!=null && newList.size()>0 && list.size()>0){
            for (String bean : list) {
                boolean flag = true;
                for (String newBean : newList) {
                    if (newBean.equals(bean)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    resList.add(bean);
                }
            }
            return resList;
        }else {
            return list;
        }
    }

    /**
     *
     * @param tid
     * @return
     */
    @Override
    public List<Map<String,String>> getSecurityCiInfo(String tid) {
        List<Map<String,Object>> itemList = typeItemDao.findItemByTidToHump(tid);
        String sql = "ID";
        for (Map<String,Object> map:itemList) {
            if ("厂站名称".equals(map.get("attrName").toString())){
                sql=sql+","+map.get("mpCiItem").toString()+" name";
            }else if ("经度".equals(map.get("attrName").toString())){
                sql=sql+","+map.get("mpCiItem").toString()+" longitude";
            }else if ("纬度".equals(map.get("attrName").toString())){
                sql=sql+","+map.get("mpCiItem").toString()+" latitude";
            }
            sql=sql+",DATA_3 region";
        }
        List<Map<String,String>> list = typeDataDao.getSecurityCiInfo(tid,sql);
        return list;
    }

    /**
     *
     * @param tid
     * @param vague
     * @return
     */
    @Override
    public Map<String, String> getSecurityCiInfoByTidAndVague(String tid, String vague) {
        List<Map<String,Object>> itemList = typeItemDao.findItemByTidToHump(tid);
        String sql = "a.ID";
        for (Map<String,Object> map:itemList) {
            if ("厂站名称".equals(map.get("attrName").toString())){
                sql=sql+","+map.get("mpCiItem").toString()+" name";
            }else if ("经度".equals(map.get("attrName").toString())){
                sql=sql+","+map.get("mpCiItem").toString()+" longitude";
            }else if ("纬度".equals(map.get("attrName").toString())){
                sql=sql+","+map.get("mpCiItem").toString()+" latitude";
            }
        }
        Map<String, String> stringMap = typeDataDao.getSecurityCiInfoByTidAndVague(tid,vague,sql);
        return stringMap;
    }

    @Override
    public int findItemSortByTid(String ci_type_id) {
        return typeDataDao.findItemSortByTid(ci_type_id);
    }

    @Override
    public boolean updateTypeItem(String typeItemStr) {
        List<TypeItem> typeItemList = JSONObject.parseArray(typeItemStr,TypeItem.class);
        for (TypeItem typeItem: typeItemList) {
            if(StringUtils.isNotBlank(typeItem.getRegexp())){
                try {
                    Pattern.compile(typeItem.getRegexp());
                    if(StringUtils.isNotBlank(typeItem.getDef_val())){
                        boolean def_val_valid = RegexpUtil.checkValidity(typeItem.getDef_val(), typeItem.getRegexp());
                        if(!def_val_valid){
                            PageResult.fail("添加失败,默认值有误！");
                            return false;
                        }
                    }
                }catch (PatternSyntaxException e){
                    PageResult.fail("添加失败,正则表达式有误！");
                    return false;
                }
            }
        }
        boolean result = typeItemDao.updateItemList(typeItemList);
        if (result){
            //增加修改CI数据方法
            infoService.updateCiInfoName(typeItemList.get(0).getCi_type_id());
            //向队列推送大类及类定义数据
            sender.sendCiTypeAndItemData();
        }

        return result;
    }

    @Override
    public String findMpCiItemByIdTypeId(String cIType, String cIName) {
        return typeItemDao.findMpCiItemByIdTypeId(cIType,cIName);
    }

    @Override
    public String getCiCodeList(String ciIdList) {

        List<String> list = Arrays.asList(ciIdList.split(","));
        List<String> ciCodeList = typeItemDao.getCiCodeList(list);
        return Joiner.on(",").join(ciCodeList);
    }

    @Override
    public List<String> findItemNameByItemId(List<String> asList) {
        return typeItemDao.findItemNameByItemId(asList);
    }
    /**
     * @Author: ztl
     * date: 2021-08-12
     * @description: 获取所有大类和类定义字段（为接口平台同步数据提供）
     */
    @Override
    public Map<String, Object> getAllCiTypeAndItem() {
        Map<String, Object> result= new HashMap<>();
        //获取所有大类信息
        List<Map<String, Object>> typeList = typeDao.getCiTypeList(null);
        //获取大类下所有字段
        List<Map<String,Object>> itemList = typeItemDao.findAllItemHump();
        result.put("typeData", typeList);
        result.put("typeItemData", itemList);
        return result;
    }

}
