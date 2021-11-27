package com.integration.controller;

import com.integration.entity.CiSourceRel;
import com.integration.entity.PageResult;
import com.integration.generator.entity.IomCiKpi;
import com.integration.service.CiSourceRelService;
import com.integration.utils.RedisUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiSourceRelController
 * @Author: ztl
 * @Date: 2021-08-16
 * @Version: 1.0
 * @description:数据源和对象关联关系
 */
@RestController
@RequestMapping("/ciSourceRel")
public class CiSourceRelController {

    @Autowired
    private CiSourceRelService ciSourceRelService;
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 新增数据源和对象关联关系
     */
    @RequestMapping(value = "addCiSourceRel",method = RequestMethod.POST)
    public boolean addCiSourceRel(CiSourceRel ciSourceRel){
        return ciSourceRelService.addCiSourceRel(ciSourceRel);
    }
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 获取数据源和对象关联关系列表
     */
    @RequestMapping(value = "getCiSourceRelList",method = RequestMethod.POST)
    public PageResult getCiSourceRelList(String sourceId, String ciTypeId, String ciName){
        return ciSourceRelService.getCiSourceRelList(sourceId,ciTypeId,ciName);
    }
    /**
     * @Author: ztl
     * date: 2021-08-16
     * @description: 根据ID删除数据源和对象关联关系
     */
    @RequestMapping(value = "deleteCiSourceRel",method = RequestMethod.POST)
    public boolean deleteCiSourceRel(String ids){
        return ciSourceRelService.deleteCiSourceRel(ids);
    }
    
    /**
     * @Author: tzq
     * date: 2021-08-18
     * @description: 查询数据源和对象关联关系数据并返回
     */
    @RequestMapping(value = "/findCiSourceRel", method = RequestMethod.POST)
    public Map<String,Object> findCiSourceRel(String dataSource,String ciTypeId,String ciCode,String domainId) {
    	String key = "ciSource_"+dataSource+"_"+ciTypeId+"_"+ciCode;
    	Map<String,Object> map=ciSourceRelService.getCiSourceRelInfo(dataSource, ciTypeId, ciCode);  	
    	if (map!=null && map.size()>0) {
            RedisUtils.set(key,map,20*60*1000L);
            return map;
		}else {
			Map<String,Object> mapData=ciSourceRelService.addCiSourceRelByPerPush(dataSource, ciTypeId, ciCode,domainId);
			return mapData;
		}
    }


}
