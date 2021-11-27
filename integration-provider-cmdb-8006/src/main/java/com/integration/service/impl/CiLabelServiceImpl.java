package com.integration.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.integration.dao.CiLabelDao;
import com.integration.dao.TypeDataDao;
import com.integration.entity.CiLabel;
import com.integration.entity.Dir;
import com.integration.entity.PageResult;
import com.integration.service.*;
import com.integration.utils.DateUtils;
import com.integration.utils.MapUtils;
import com.integration.utils.MyPagUtile;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import io.netty.util.Recycler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: CiLabelServiceImpl
 * @Author: ztl
 * @Date: 2020-12-03
 * @Version: 1.0
 * @description:CI标签
 */
@Transactional
@Service
public class CiLabelServiceImpl implements CiLabelService {

    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeItemService typeItemService;
    @Autowired
    private TypeDataService typeDataService;
    @Autowired
    private DirService dirService;
    @Autowired
    private CiLabelDao ciLabelDao;
    @Autowired
    private TypeDataDao typeDataDao;
    @Override
    public List<Map<String, Object>> getCitypeAndItemList() {

        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> ciTypeList = typeService.getCiTypeList();
        for (Map map:ciTypeList) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("ciType",map);
            resultMap.put("ciTypeItem",typeItemService.findItemByTidToHump(map.get("id").toString()));
            result.add(resultMap);
        }
        return result;
    }

    @Override
    public List<String> getAttrValuesList(String ciTypeId, String mpCiItem, String searchCondition) {
        String domainId = TokenUtils.getTokenDataDomainId();
        return typeDataService.getAttrValuesList(ciTypeId,mpCiItem,searchCondition,domainId);
    }

    @Override
    public List<Map<String, Object>> getLabelTree(String labelName) {
        String domainId = TokenUtils.getTokenDataDomainId();
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Dir> dirList = dirService.findDirList(5);
        for (Dir dir: dirList) {
            Map dirMap = new HashMap<>();
            dirMap = JSONObject.parseObject(JSONObject.toJSONString(dir), Map.class);
            Map<String, Object> map = new HashMap<>();
            map.put("labelDir",MapUtils.formatHumpName(dirMap));
            List<Map<String, String>> labelList = ciLabelDao.getLabelListByDirId(dir.getId(),domainId,labelName);
            map.put("labelNum",labelList.size());
            map.put("labelList",labelList);
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getLabelById(String labelId) {
        String domainId = TokenUtils.getTokenDataDomainId();
        return ciLabelDao.getLabelById(labelId,domainId);
    }

    @Override
    public boolean addOrUpdateLabel(CiLabel ciLabel) {
        boolean result = labelNameExists(ciLabel.getLabelName(),ciLabel.getId(),TokenUtils.getTokenDataDomainId());
        if (result){
            PageResult.setMessage(false, "标签名称["+ciLabel.getLabelName()+"]已存在!");
            return false;
        }else {
            if (ciLabel.getId() != null && !"".equals(ciLabel.getId())){
                ciLabel.setXgrId(TokenUtils.getTokenUserId());
                ciLabel.setXgsj(DateUtils.getDate());
                return ciLabelDao.updateLabel(ciLabel);
            }else{
                ciLabel.setId(SeqUtil.nextId()+"");
                ciLabel.setCjrId(TokenUtils.getTokenUserId());
                ciLabel.setCjsj(DateUtils.getDate());
                ciLabel.setDomainId(TokenUtils.getTokenOrgDomainId()==null?"-1":TokenUtils.getTokenOrgDomainId());
                ciLabel.setYxbz("1");
                return ciLabelDao.addLabel(ciLabel);
            }
        }
    }

    public boolean labelNameExists(String labelName,String id,String domainId){


        return ciLabelDao.labelNameExists(labelName,id,domainId)>0;
    }

    @Override
    public boolean deleteLabelById(String id) {
        return ciLabelDao.deleteLabelById(id);
    }

    @Override
    public PageResult getLabelCiInfo(String labelId) {
        PageResult pageResult = new PageResult();
        pageResult.setReturnBoolean(true);
        String domainId = TokenUtils.getTokenDataDomainId();
        Map<String, Object> labelInfo = ciLabelDao.getLabelById(labelId,domainId);
        if (labelInfo!=null && labelInfo.get("labelRuleInfo")!=null){
            String labelRuleInfo = labelInfo.get("labelRuleInfo").toString();
            String sqlStr = queryCriteria(labelRuleInfo);
            if (sqlStr!=null){
                MyPagUtile.startPage();
                List<Map<String,Object>> resultMap = typeDataDao.getLabelCiInfo(sqlStr,domainId);
                if (resultMap !=null && resultMap.size()>0){
                    pageResult.setTotalPage(((Page)resultMap).getPages());
                    pageResult.setTotalResult((int)((Page)resultMap).getTotal());
                }else {
                    pageResult.setTotalPage(0);
                    pageResult.setTotalResult(0);
                }
                List<Map<String,Object>> resultMaps = new ArrayList<>();
                for (Map map:resultMap) {
                    Map<String,Object> maps = new HashMap<>();
                    maps.putAll(map);
                    for (Object key:map.keySet()) {
                        if ("id".equals(key)||map.get(key)==null||"".equals(map.get(key))){
                            maps.remove(key);
                        }
                    }
                    resultMaps.add(maps);
                }
                pageResult.setReturnObject(resultMaps);

                return pageResult;
            }else {
                pageResult.setTotalPage(0);
                pageResult.setTotalResult(0);
                return pageResult;
            }
        }else {
            pageResult.setTotalPage(0);
            pageResult.setTotalResult(0);
            return pageResult;
        }
    }

    public String queryCriteria(String labelRuleInfo) {
        if(labelRuleInfo!=null && !"".equals(labelRuleInfo)) {
            StringBuffer sql=new StringBuffer();
            JSONObject returnJsonObject = JSONObject.parseObject(labelRuleInfo);
            JSONArray array = returnJsonObject.getJSONArray("condition");
            if(array!=null && array.size()>0) {
                for (int j = 0; j < array.size(); j++) {
                    String andList = null;
                    JSONObject ext = (JSONObject) array.get(j);
                    List<Map<String, Object>> list=(List<Map<String, Object>>) ext.get("list");
                    if(list!=null && list.size()!=0) {
                        andList = getAndList(list);
                        if(andList!=null ) {
                            sql.append(" ( " +andList + " ) ");
                            sql.append(" OR ");
                        }
                    }
                }
            }

            if (sql.length()>0) {
                String string = sql.toString();
                string = string.substring(0, string.lastIndexOf(" OR "));
                return " ( " +string+ " ) ";
            }

        }
        return null;
    }
    public String getAndList(List<Map<String, Object>> list) {
        StringBuffer sql = new StringBuffer();
        String ciTypeId = "";
        for (Map<String, Object> map : list) {
            ciTypeId = (String) map.get("ciTypeId");
            String name=(String) map.get("name");
            String operator=(String) map.get("operator");
            String content=(String) map.get("content");
            if("*".equals(content)) {
                sql.append(" AND 1=1");
                continue;
            }
            if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(operator) && StringUtils.isNotEmpty(content)) {
                sql.append(" AND ");
                if("contains".equals(operator)) {
                    //修改包含此条件的逻辑，将in改为like @author ztl @date 2020-06-16
                    sql.append(name);
                    if(content.contains(",")) {
                        String[] arr = content.split(",");
                        for(int i=0;i<arr.length;i++) {
                            String val=arr[i];
                            if (i==0){
                                sql.append(" like '%"+val+"%'");
                            }else {
                                sql.append(" or ");
                                sql.append(name);
                                sql.append(" like '%"+val+"%'");
                            }
                        }
                    }else {
                        sql.append(" like '%"+content);
                        sql.append("%'");
                    }

                }else if("not contains".equals(operator)) {
                    //修改不包含此条件的逻辑，将not in 改为 not like @author ztl @date 2020-06-16
                    sql.append(name);
                    if(content.contains(",")) {
                        String[] arr = content.split(",");
                        for(int i=0;i<arr.length;i++) {
                            String val=arr[i];
                            if (i==0){
                                sql.append(" not like '%"+val+"%'");
                            }else {
                                sql.append(" or ");
                                sql.append(name);
                                sql.append(" not like '%"+val+"%'");
                            }
                        }
                    }else {
                        sql.append(" not like '%"+content+"%'");
                    }
                }else if("like".equals(operator)) {
                    operator=" like ";
                    sql.append(name);
                    sql.append(operator);
                    sql.append("'"+"%"+content+"%"+"'");
                }else {
                    sql.append(name);
                    sql.append(operator);
                    sql.append("'"+content);
                    sql.append("'");
                }
            }
        }

        if (sql.length()>0) {
            return " CI_TYPE_ID= '"+ciTypeId +"'" + sql.toString();
        }

        return null;
    }
}
