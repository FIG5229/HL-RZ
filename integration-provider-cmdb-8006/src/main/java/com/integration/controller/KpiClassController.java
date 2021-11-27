package com.integration.controller;

import com.integration.entity.Type;
import com.integration.entity.TypeItem;
import com.integration.service.CiKpiService;
import com.integration.service.TypeDataService;
import com.integration.service.TypeItemService;
import com.integration.service.TypeService;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
* @Package: com.integration.controller
* @ClassName: KpiClassController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: KPI大类Controller
*/
@RestController
@RequestMapping("kpiClass")
public class KpiClassController {

    @Autowired
    private CiKpiService ciKpiService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeItemService typeItemService;
    @Autowired
    private TypeDataService typeDataService;

    /**
     * @Method selAllKpiClass
     * @Description 获取大类列表
     * @Param []
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/5/14 10:18
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("selAllKpiClass")
    public List<Map<String, Object>> selAllKpiClass() {
        return ciKpiService.selAllKpiClass();
    }

    /**
     * @Method ciIdBySource
     * @Description 根据来源查询ci集合，根据属性分组
     * @Param [map]
     * @Param Source 来源
     * @Param ciType CI大类字段
     * @Param ciClassName 大类名称
     * @Param groupName 分组字段
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/5/18 10:09
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("ciIdBySource")
    public List<Map<String, String>> ciIdBySource(@RequestBody Map<String, Object> map) {
        String orgDomainId = TokenUtils.getTokenOrgDomainId();
        //校验
        if (map == null) {
            return null;
        }
        //最终返回值
        List<Map<String, String>> mapList = new ArrayList<>();
        //根据ci大类名称，获取ci大类属性值
        if (map.get("ciClassName") == null) {
            //ci大类名称不存在
            return null;
        }
        Type type = typeService.findByMc(map.get("ciClassName").toString(),orgDomainId);
        if (type == null) {
            //未查询到大类信息
            return null;
        }
        //获取CI大类ID
        String ciClassId = type.getId();
        //获取大类属性中对应（根据ci大类ID和属性名称)
        List<TypeItem> typeItems = typeItemService.selItemByTypeId(ciClassId);
        if (typeItems == null || typeItems.size() == 0) {
            //不存在的大类属性
            return null;
        }
        //获取属性key
        if (map.get("ciType") == null) {
            //ci大类属性不存在
            return null;
        }
       /* if (map.get("groupName") == null) {
            return null;
        }*/
        //ci大类属性对应字段名称
        String ciClassName = null;
        String groupName = null;
        for (TypeItem t : typeItems) {
            if (t.getAttr_name().equals(map.get("ciType"))) {
                ciClassName = t.getMp_ci_item();
                continue;
            }
            if (t.getAttr_name().equals(map.get("groupName"))) {
                groupName = t.getMp_ci_item();
            }
        }
        //封装参数
        Map<String, Object> cMap = new HashMap<>();
        if (map.get("source") == null) {
            //不存在来源
            return null;
        }
        cMap.put("ciClassName", ciClassName);
        cMap.put("source", map.get("source"));
        cMap.put("groupName", groupName);
        cMap.put("ciClassId", ciClassId);
        //查询ciId
        mapList = typeDataService.findCiIdByMap(cMap);
        return mapList;
    }

    /**
     * @Method findKpiIdsBySource
     * @Description 根据KPI大类ID查询kpiId集合返回id按逗号隔开
     * @Param [source]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/5/27 10:49
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findKpiIdsBySource")
    public List<Map<String, Object>> findKpiIdsBySource(String source) {
        //最终返回值
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        //查询kpi集合
        mapList = ciKpiService.findKpiIdsBySource(source);
        return mapList;
    }

    /**
     * @Method findKpiIdBySource
     * @Description 根据来源id查询kpiId集合返回id按逗号隔开
     * @Param [source]
     * @Return java.util.List<java.lang.String>
     * @Author sgh
     * @Date 2020/5/27 16:05
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findKpiIdBySource")
    public String findKpiIdBySource(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        return ciKpiService.findKpiIdBySource(source);
    }

    /**
     * @Method findKpiClassByClassId
     * @Description 根据大类名称查询KPI大类的信息(只对内使用 ）
     * @Param [id]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/6/4 14:46
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findKpiClassByClassName")
    public List<Map<String, Object>> findKpiClassByClassName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        List<String> kpiClass = Arrays.asList(name.split(","));
        return ciKpiService.findKpiClassByClassId(kpiClass);
    }

    /**
     * @Method findKpiIdsByClassName
     * @Description 根据kpi大类的名称查询kpi
     * @Param [name]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/4 15:01
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findKpiIdsByClassName")
    public List<Map<String, Object>> findKpiIdsByClassName(String name) {
        //跟据大类名称查询KPI大类的信息
        List<Map<String, Object>> kpiClassByClassName = findKpiClassByClassName(name);
        if (kpiClassByClassName == null || kpiClassByClassName.size() == 0) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (Map<String, Object> m : kpiClassByClassName) {
            ids.add(m.get("ID").toString());
        }
        return findKpiIdsBySource(ids.stream().collect(Collectors.joining(",")));
    }

    /**
     * @Method findKpiByKpiName
     * @Description 根据kpi名称查询kpi信息(只对内使用 ）
     * @Param [name]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/4 15:11
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findKpiByKpiName")
    public List<Map<String, Object>> findKpiByKpiName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return ciKpiService.findKpiByKpiName(Arrays.asList(name.split(",")));
    }


    /**
     * @Method findKpiByKpiName
     * @Description 根据kpi名称查询kpiId信息(只对内使用 ）
     * @Param [name]
     * @Return java.lang.String
     * @Author sgh
     * @Date 2020/6/8 15:11
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findKpiIdByKpiName")
    public String findKpiIdByKpiName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        List<Map<String, Object>> kpiByKpiName = ciKpiService.findKpiByKpiName(Arrays.asList(name.split(",")));
        if (kpiByKpiName == null || kpiByKpiName.size() == 0) {
            return null;
        }
        Set<String> stringSet = new HashSet<>();
        //封装ID集合
        for (Map<String, Object> m : kpiByKpiName) {
            stringSet.add(m.get("ID").toString());
        }
        return stringSet.stream().collect(Collectors.joining(","));
    }
}
