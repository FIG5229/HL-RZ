package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.integration.dao.CiViewDao;
import com.integration.entity.vo.CiView;
import com.integration.utils.MyPagUtile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ci属性查询
 *
 * @author yxc
 */
@Transactional
@Service
public class CiViewServiceImpl {

    @Resource
    private CiViewDao ciViewDao;

    /**
     * 查询
     *
     * @return
     */
    public List<CiView> list(Map<String, Object> param) {
        if (param != null) {
            String ciId = getString(param, "ciId");
            String ciIds = getString(param, "ciIds");
            String ciTypeId = getString(param, "ciTypeId");
            String ciCode = getString(param, "ciCode");
            String sourceId = getString(param, "sourceId");
            String major = getString(param, "major");

            param.remove("ciIds");
            param.remove("ciTypeId");
            param.remove("ciCode");
            param.remove("sourceId");
            param.remove("major");

            return ciViewDao.selectList(ciId, ciIds, ciTypeId, ciCode, sourceId, major, param);
        }
        return null;
    }
    
    /**
     * 查询
     *
     * @return
     */
    public List<CiView> listByJniom(Map<String, Object> param) {
        if (param != null) {
            String ciId = getString(param, "ciId");
            String ciIds = getString(param, "ciIds");
            String ciTypeId = getString(param, "ciTypeId");
            String ciTypeName = getString(param, "ciTypeName");
            String ciCode = getString(param, "ciCode");
            String sourceId = getString(param, "sourceId");
            String major = getString(param, "major");
            param.remove("ciId");
            param.remove("ciIds");
            param.remove("ciTypeId");
            param.remove("ciTypeName");
            param.remove("ciCode");
            param.remove("sourceId");
            param.remove("major");

            return ciViewDao.selectListByJniom(ciId, ciIds, ciTypeId, ciTypeName, ciCode, sourceId, major, param);
        }
        return null;
    }


    /**
     * 分页查询
     *
     * @return
     */
    public PageInfo<CiView> page(Map<String, Object> param) {
        MyPagUtile.startPage();
        List<CiView> ciViews = list(param);
        return new PageInfo<CiView>(ciViews);
    }

    /**
     * json数据解析查询
     *
     * @return
     */
    public List<Map<String, Object>> traList(Map<String, Object> param) {
        List<CiView> ciViews = list(param);
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        for (CiView ciView : ciViews) {
            change(ciView);
            Map<String, Object> map = change(ciView);
            if (map != null) {
                resList.add(map);
            }
        }
        return resList;
    }
    
    /**
     * json数据解析查询
     *
     * @return
     */
    public List<Map<String, Object>> traListByJniom(Map<String, Object> param) {
        List<CiView> ciViews = listByJniom(param);
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        for (CiView ciView : ciViews) {
            change(ciView);
            Map<String, Object> map = change(ciView);
            if (map != null) {
                resList.add(map);
            }
        }
        return resList;
    }

    /**
     * json数据解析分页
     *
     * @return
     */
    public PageInfo<Map<String, Object>> traPage(Map<String, Object> param) {
        PageInfo<CiView> pages = page(param);
        PageInfo<Map<String, Object>> resPages = new PageInfo<Map<String, Object>>();
        resPages.setList(changeList(pages.getList()));
        resPages.setPages(pages.getPages());
        resPages.setTotal(pages.getTotal());
        resPages.setPageNum(pages.getPageNum());
        return resPages;
    }

    /**
     * json解析列表
     *
     * @param ciViews
     * @return
     */
    List<Map<String, Object>> changeList(List<CiView> ciViews) {
        if (ciViews == null) {
            return null;
        }
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        for (CiView ciView : ciViews) {
            change(ciView);
            Map<String, Object> map = change(ciView);
            if (map != null) {
                resList.add(map);
            }
        }
        return resList;
    }

    /**
     * json解析
     *
     * @param ciView
     * @return
     */
    Map<String, Object> change(CiView ciView) {
        if (ciView == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ciId", ciView.getCiId());
        map.put("ciTypeId", ciView.getCiTypeId());
        map.put("ciCode", ciView.getCiCode());
        map.put("sourceId", ciView.getSourceId());
        map.put("major", ciView.getMajor());
        String jsondataStr = ciView.getJsondata();
        if (StringUtils.isNotEmpty(jsondataStr)) {
            JSONObject jsondataObj = JSON.parseObject(jsondataStr);
            for (String key : jsondataObj.keySet()) {
                map.put(key, jsondataObj.get(key));
            }
        }
        return map;
    }

    /**
     * map取值
     *
     * @param param
     * @param key
     * @return
     */
    String getString(Map<String, Object> param, String key) {
        if (param == null) {
            return null;
        }
        Object obj = param.get(key);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * @Method findCi
     * @Description 根据属性和值查询ci信息
     * @Param [mapCList, mapJList]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/4 9:56
     * @Version 1.0
     * @Exception
     **/
    public List<Map<String, Object>> findCi(List<Map<String, Object>> mapCList, List<Map<String, Object>> mapJList) {
        //获取集合中数量，不存在值时赋予NULL
        if (mapCList.size() == 0) {
            mapCList = null;
        }
        if (mapJList.size() == 0) {
            mapJList = null;
        }
        //在数据库中查询
        return changeList(ciViewDao.findCi(mapCList, mapJList));
    }
}
