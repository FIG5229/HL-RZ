package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.integration.dao.CiSourceRelDao;
import com.integration.entity.CiSourceRel;
import com.integration.entity.PageResult;
import com.integration.feign.CzryService;
import com.integration.feign.DictService;
import com.integration.service.CiSourceRelService;
import com.integration.utils.ConvertUtils;
import com.integration.utils.MyPagUtile;
import com.integration.utils.RedisUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: CiSourceRelServiceImpl
 * @Author: ztl
 * @Date: 2021-08-16
 * @Version: 1.0
 * @description:数据源和对象关联关系
 */
@Service
public class CiSourceRelServiceImpl implements CiSourceRelService {

    @Resource
    private CiSourceRelDao ciSourceRelDao;
    @Autowired
    private CzryService czryService;
    @Autowired
    private DictService dictService;
    @Autowired
	private RedisUtils redisUtil;
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 新增数据源和对象关联关系
     */
    @Override
    public boolean addCiSourceRel(CiSourceRel ciSourceRel) {

        List<String> ciCodeList = Arrays.asList(ciSourceRel.getCiCodes().split(","));
        List<String> ciTypeIdList = Arrays.asList(ciSourceRel.getCiTypeId().split(","));
        List<CiSourceRel> ciSourceRelList = new ArrayList<>();
        String cjrId = TokenUtils.getTokenUserId();
        String domainId = TokenUtils.getTokenOrgDomainId();
        int c=0;
        for (String str: ciCodeList) {
            CiSourceRel sourceRel = new CiSourceRel();
            sourceRel.setId(SeqUtil.getId());
            sourceRel.setCiCode(str);
            sourceRel.setCiTypeId(ciTypeIdList.get(c));
            sourceRel.setSourceId(ciSourceRel.getSourceId());
            sourceRel.setCjrId(cjrId);
            sourceRel.setXgrId(cjrId);
            sourceRel.setDomainId(domainId==null?"-1":domainId);
            ciSourceRelList.add(sourceRel);
            //判断redis是否存在，若不存在则添加
            String kpiKey = "ciSource_"+ciSourceRel.getSourceId()+"_"+ciTypeIdList.get(c)+"_"+str;
            if (!redisUtil.exists(kpiKey)) {          	
				try {
					Map<String, Object> map = ConvertUtils.bean2map(sourceRel);
					RedisUtils.set(kpiKey,map,20*60*1000L);
				} catch (Exception e) {
					e.printStackTrace();
				}            	
            }
            c++;
        }
        //先删除符合条件的关系
        ciSourceRelDao.deleteCiSourceRelList(ciSourceRelList);
        //返回
        boolean result = ciSourceRelDao.addCiSourceRelList(ciSourceRelList);
        return result;
    }
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 获取数据源和对象关联关系列表
     */
    @Override
    public PageResult getCiSourceRelList(String sourceId, String ciTypeId, String ciName) {
        String domainId = TokenUtils.getTokenOrgDomainId();
        PageResult pageResult = new PageResult();
        MyPagUtile.startPage();
        List<CiSourceRel> ciSourceRelList = ciSourceRelDao.getCiSourceRelList(sourceId,ciTypeId,ciName,domainId);
        pageResult.setReturnBoolean(true);
        if (ciSourceRelList!=null){
            //来源列表
            List<Map<String, Object>> sourceList = dictService.findDiceBySjIdHump("258888");
            pageResult.setTotalResult((int) ((Page)ciSourceRelList).getTotal());
            pageResult.setTotalPage(((Page)ciSourceRelList).getPages());
            for (CiSourceRel ciSourceRel:ciSourceRelList) {
                String cjrId=ciSourceRel.getCjrId();
                Object resObject = czryService.findCzryByIdFeign(cjrId);
                if (resObject!=null) {
                    JSONObject czry = JSONObject.parseObject(JSON.toJSONString(resObject));
                    ciSourceRel.setCjrName(String.valueOf(czry.get("czry_mc")));
                    ciSourceRel.setXgrName(String.valueOf(czry.get("czry_mc")));
                }
                for (Map map:sourceList) {
                    if (map.get("dictBm").equals(ciSourceRel.getSourceId())){
                        ciSourceRel.setSourceName(String.valueOf(map.get("dictName")));
                    }
                }
            }
            pageResult.setReturnObject(ciSourceRelList);
        }else{
            pageResult.setTotalResult(0);
            pageResult.setTotalPage(0);
        }
        return pageResult;
    }
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 根据ID删除数据源和对象关联关系
     */
    @Override
    public boolean deleteCiSourceRel(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        //根据ID查询出关系数据，判断redis是否存在，若有则删除
        Map<String,Object> itemMap=new HashMap<>();
        String [] idsArray = idList.toArray(new String[idList.size()]);
        if(idsArray.length>0) {
        	itemMap.put("idsList", idsArray);
        }else {
        	itemMap.put("idsList", "");
        }
        List<Map<String,Object>> list=ciSourceRelDao.getCiSourceRelInfoByIds(itemMap);
        if(list!=null && list.size()>0) {
        	for(Map<String,Object> map:list) {
        		String sourceId=(String) map.get("sourceId");
        		String ciTypeId=(String) map.get("ciTypeId");
        		String ciCode=(String) map.get("ciCode");
        		String kpiKey = "ciSource_"+sourceId+"_"+ciTypeId+"_"+ciCode;
        		if (redisUtil.exists(kpiKey)) {
        			RedisUtils.remove(kpiKey);
        		}
        	}
        }
        return ciSourceRelDao.deleteCiSourceRel(idList);
    }
    /**
     * @Author: tzq
     * date: 2021-08-18
     * @description: 获取数据源和对象关联关系数据
     */
    @Override
    public Map<String,Object> getCiSourceRelInfo(String dataSource,String ciTypeId,String ciCode){
    	return ciSourceRelDao.getCiSourceRelInfo(dataSource, ciTypeId, ciCode);
    }
    
    /**
     * @Author: tzq
     * date: 2021-08-18
     * @description: 新增数据源和对象关联关系并返回
     */
    @Override
    public Map<String,Object> addCiSourceRelByPerPush(String dataSource,String ciTypeId,String ciCode,String domainId) {
         String cjrId = TokenUtils.getTokenUserId();
         CiSourceRel sourceRel = new CiSourceRel();
         sourceRel.setId(SeqUtil.getId());
         sourceRel.setCiCode(ciCode);
         sourceRel.setCiTypeId(ciTypeId);
         sourceRel.setSourceId(dataSource);
         sourceRel.setCjrId(cjrId);
         sourceRel.setXgrId(cjrId);
         if(domainId==null || "".equals(domainId)) {
        	 sourceRel.setDomainId("-1");
         }else {
        	 sourceRel.setDomainId(domainId); 
         }
         
         List<CiSourceRel> ciSourceRelList=new ArrayList<>();
         ciSourceRelList.add(sourceRel);
         boolean result = ciSourceRelDao.addCiSourceRelList(ciSourceRelList);
         if(result) {
            try {
        	   Map<String,Object> map=ConvertUtils.convertBean(sourceRel);
			   return map;
			} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			   e.printStackTrace();
			}
         }
         return null;
    }
}
