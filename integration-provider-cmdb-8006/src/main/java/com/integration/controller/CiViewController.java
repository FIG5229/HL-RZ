package com.integration.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.integration.entity.vo.CiView;
import com.integration.service.impl.CiViewServiceImpl;
import com.integration.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
/**
* @Package: com.integration.controller
* @ClassName: CiViewController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: ci视图查询
*/
@RestController
@RequestMapping("ciView")
public class CiViewController {

    @Autowired
    private CiViewServiceImpl ciViewServiceImpl;

    /**
     * 列表
     *
     * @param param
     * @return
     */
    @RequestMapping("list")
    public List<CiView> list(@RequestParam Map<String, Object> param) {
        return ciViewServiceImpl.list(param);
    }

    /**
     * 分页
     *
     * @param param
     * @return
     */
    @RequestMapping("page")
    public PageInfo<CiView> page(@RequestParam Map<String, Object> param) {
        return ciViewServiceImpl.page(param);
    }

    /**
     * 格式转换列表
     *
     * @param param
     * @return
     */
    @RequestMapping("traList")
    public List<Map<String, Object>> traList(@RequestParam Map<String, Object> param) {
        return ciViewServiceImpl.traList(param);
    }
    
    /**
     * 格式转换列表(济宁项目)
     *
     * @param param
     * @return
     */
    @RequestMapping("traListByJniom")
    public List<Map<String, Object>> traListByJniom(@RequestParam Map<String, Object> param) {
        String key = "traList_" + JSON.toJSONString(param);
        List<Map<String, Object>> res = (List<Map<String, Object>>) RedisUtils.get(key);
        if (res == null || res.size() == 0) {
            res = ciViewServiceImpl.traListByJniom(param);
            RedisUtils.set(key, res);
        }
        return res;
    }

    /**
     * 格式转化分页
     *
     * @param param
     * @return
     */
    @RequestMapping("traPage")
    public PageInfo<Map<String, Object>> traPage(@RequestParam Map<String, Object> param) {
        return ciViewServiceImpl.traPage(param);
    }

    /**
     * @Method findCiByMap
     * @Description 根据属性和值查询ci信息(遵守约定, 传入参数中的key真是在数据库中存在)
     * @Param [mapList]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/6/4 9:30
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findCiByMap")
    public List<Map<String, Object>> findCiByMap(@RequestBody Map<String, Object> map) {
       return findCi(map);
    }

    /**
     * @Method findCi
     * @Description 根据属性和值查询ci信息(遵守约定, 传入参数中的key真是在数据库中存在)
     * @Param [mapList]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/6/4 9:30
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findCi")
    public List<Map<String, Object>> findCi(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        //非json字段
        List<Map<String, Object>> mapCList = new ArrayList<>();
        //json字段
        List<Map<String, Object>> mapJList = new ArrayList<>();
        //循环使用MAP
        for (String key : map.keySet()) {
            Map<String, Object> m = new HashMap<>();
            m.put("key", key);
            m.put("val", map.get(key));
            if ("ciId".equals(key) || "ciTypeId".equals(key) || "ciCode".equals(key) || "sourceId".equals(key) || "major".equals(key)) {
                mapCList.add(m);
            } else {
                mapJList.add(m);
            }
        }
        return ciViewServiceImpl.findCi(mapCList, mapJList);
    }

    /**
     * @Method findCiIds
     * @Description 根据属性和值查询ciId集合(遵守约定, 传入参数中的key真是在数据库中存在)
     * @Param [mapList]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/6/4 11:30
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findCiIds")
    public Set<String> findCiIds(Map<String, Object> map) {
        List<Map<String, Object>> mapList = findCi(map);
        if (mapList == null || mapList.size() == 0) {
            return null;
        }
        Set<String> stringSet = new HashSet<>();
        for (Map<String, Object> m : mapList) {
            if (m.get("ciId") != null) {
                stringSet.add(m.get("ciId").toString());
            }
        }
        return stringSet;
    }



}
