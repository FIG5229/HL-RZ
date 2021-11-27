package com.integration.controller;

import com.integration.entity.IomCiKpiClass;
import com.integration.service.IomCiKpiClassService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 指标大类表(IomCiKpiClass)表控制层
 *
 * @author makejava
 * @since 2019-11-01 11:37:02
 */
@RestController
@RequestMapping("iomCiKpiClas")
public class IomCiKpiClassController {
    /**
     * 服务对象
     */
    @Resource
    private IomCiKpiClassService iomCiKpiClasService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public IomCiKpiClass selectOne(String id) {
        return this.iomCiKpiClasService.queryById(id);
    }

    /**
     * 查询所有的大类
     *
     * @return
     */
    @RequestMapping("selectAll")
    public List<Map> selectAll() {
        return iomCiKpiClasService.selectAll();
    }

    /**
     * 查询名称是否存在
     *
     * @param name
     * @return
     */
    @RequestMapping("booById")
    public boolean booById(@RequestParam("name") String name) {
        if (iomCiKpiClasService.booById(name) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加单条数据
     *
     * @param iomCiKpiClas
     * @return
     */
    @RequestMapping("save")
    public boolean save(IomCiKpiClass iomCiKpiClas) {
        int num = iomCiKpiClasService.insert(iomCiKpiClas);
        return num > 0;
    }

    @RequestMapping(value = "/getClassAndKpiByPmv", method = RequestMethod.GET)
    public Object getClassAndKpiByPmv() {
        List<IomCiKpiClass> listClas = iomCiKpiClasService.getAllIomCiKpiClas();
        List<Map<String, Object>> listKpi = iomCiKpiClasService.getCiKpiAllByPmv();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (listClas != null && listClas.size() > 0) {
            for (IomCiKpiClass iomPmvKpiClas : listClas) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", iomPmvKpiClas.getId());
                map.put("kpiName", iomPmvKpiClas.getName());
                List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
                if (listKpi != null && listKpi.size() > 0) {
                    for (Map<String, Object> map2 : listKpi) {
                        String kpi_class_id = (String) map2.get("kpiClassId");
                        if (iomPmvKpiClas.getId().equals(kpi_class_id)) {
                            list1.add(map2);
                        }


                    }
                    map.put("children", list1);
                }
                list.add(map);
            }
        }

        return list;
    }

    /**
     * @Method findByName
     * @Author sgh
     * @Version 1.0
     * @Description [name]
     * @Return boolean
     * @Exception 根据大类名称查找是否存在
     * @Date 2019/11/6
     * @Param [name]
     */
    @RequestMapping("findByName")
    public boolean findByName(String name) {
        IomCiKpiClass iomCiKpiClass = iomCiKpiClasService.findByName(name);
        if (iomCiKpiClass != null && StringUtils.isNotEmpty(iomCiKpiClass.getId())) {
            return true;
        }
        return false;
    }
}