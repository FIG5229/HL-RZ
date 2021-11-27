package com.integration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integration.entity.ConfMapping;
import com.integration.entity.ConfOutMapping;
import com.integration.entity.Configure;
import com.integration.entity.PageResult;
import com.integration.entity.Subsystem;
import com.integration.service.ConfigureService;
import com.integration.service.SubsystemService;
import com.integration.utils.DataUtils;
/**
* @Package: com.integration.controller
* @ClassName: ConfigureController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 配置
*/
@RestController
public class ConfigureController {

    @Autowired
    private ConfigureService configureService;
    @Autowired
    private SubsystemService systemService;


    @GetMapping(value = "findAllCFegin")
    public List<Configure> findAllC() {
        List<Configure> confList = configureService.findAllC();
        return confList;
    }

    @GetMapping(value = "findAllCMFegin")
    public List<Map> findAllCM() {
        List<Map> cmList = configureService.findAllCM();
        return cmList;
    }

    @GetMapping(value = "findAllSFegin")
    public List<Subsystem> findAllS() {
        List<Subsystem> cmList = systemService.findAllS();
        return cmList;
    }

    @GetMapping(value = "findAllCOMFegin")
    public List<Map> findAllCOM() {
        List<Map> comList = configureService.findAllCOM();
        return comList;
    }

    /**
     * 根据子系统id查询配置
     *
     * @param systemId
     * @return
     */
    @GetMapping(value = "/url/getListById")
    public PageResult findBySid(String systemId) {
        List<Configure> list = new ArrayList<>();
        try {
            list = configureService.findBySid(systemId);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        return DataUtils.returnPr(true, "查询成功", list);
    }

    /**
     * 添加配置
     *
     * @param configure
     * @return
     */
    @PostMapping(value = "/url")
    public PageResult addConfigure(Configure configure) {
        int i = 0;
        try {
            i = configureService.addConfigure(configure);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "添加异常");
        }
        if (i > 0) {
            return DataUtils.returnPr(true, "添加成功");
        }
        return DataUtils.returnPr(false, "添加失败");
    }

    /**
     * 根据id删除配置
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/url/deleteById")
    public PageResult deleteConfById(String id) {
        int i = 0;
        try {
            i = configureService.deleteConfById(id);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "删除异常");
        }
        if (i > 0) {
            return DataUtils.returnPr(true, "删除成功");
        }
        return DataUtils.returnPr(false, "删除失败");
    }

    /**
     * 根据id修改
     *
     * @param configure
     * @return
     */
    @PutMapping(value = "/url")
    public PageResult updateConfById(Configure configure) {
        int i = 0;
        try {
            i = configureService.updateConfById(configure);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "修改异常");
        }
        if (i > 0) {
            return DataUtils.returnPr(true, "修改成功");
        }
        return DataUtils.returnPr(false, "修改失败");
    }

    /**
     * 根据配置id查询入参和出参
     * @param urlId
     * @return
     */
    @GetMapping(value = "/url/getParamsById")
    public PageResult findMapping(String urlId) {
        List<ConfMapping> mappingList = new ArrayList<>();
        List<ConfOutMapping> outMappingList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            mappingList = configureService.findMappingByConfId(urlId);
            outMappingList = configureService.findOutMappingByConfId(urlId);
        } catch (Exception e) {
            return DataUtils.returnPr(false, "查询异常");
        }
        map.put("requestParams", mappingList);
        map.put("responseParams", outMappingList);
        return DataUtils.returnPr(true, "查询成功", map);
    }

    /**
     * 添加入参
     * @param confMapping
     * @return
     */
    @PostMapping(value = "/url/mapping")
    public PageResult addMapping(ConfMapping confMapping){
        int i = 0;
        try {
            i = configureService.addMapping(confMapping);
        } catch (Exception e) {
            return DataUtils.returnPr(false,"添加异常");
        }
        if (i > 0){
            return DataUtils.returnPr(true,"添加成功");
        }
        return DataUtils.returnPr(false,"添加失败");
    }

    /**
     * 根据id删除入参
     * @param id
     * @return
     */
    @DeleteMapping(value = "/url/deleteMappingById")
    public PageResult deleteMappingById(String id){
        int i = 0;
        try {
            i = configureService.deleteMappingById(id);
        } catch (Exception e) {
            return DataUtils.returnPr(false,"删除异常");
        }
        if (i > 0){
            return DataUtils.returnPr(true,"删除成功");
        }
        return DataUtils.returnPr(false,"删除失败");
    }

    /**
     * 修改入参
     * @param confMapping
     * @return
     */
    @PutMapping(value = "/url/mapping")
    public PageResult updateMapping(ConfMapping confMapping){
        int i = 0;
        try {
            i = configureService.updateMapping(confMapping);
        } catch (Exception e) {
            return DataUtils.returnPr(false,"修改异常");
        }
        if (i > 0){
            return DataUtils.returnPr(true,"修改成功");
        }
        return DataUtils.returnPr(false,"修改失败");
    }

    /**
     * 添加出参
     * @param confOutMapping
     * @return
     */
    @PostMapping(value = "/url/outMapping")
    public PageResult addOutMapping(ConfOutMapping confOutMapping){
        int i = 0;
        try {
            i = configureService.addOutMapping(confOutMapping);
        } catch (Exception e) {
            return DataUtils.returnPr(false,"添加异常");
        }
        if (i > 0){
            return DataUtils.returnPr(true,"添加成功");
        }
        return DataUtils.returnPr(false,"添加失败");
    }

    /**
     * 根据id删除出参
     * @param id
     * @return
     */
    @DeleteMapping(value = "/url/deleteOutMappingById")
    public PageResult deleteOutMapping(String id){
        int i = 0;
        try {
            i = configureService.deleteOutMappingById(id);
        } catch (Exception e) {
            return DataUtils.returnPr(false,"删除异常");
        }
        if (i > 0){
            return DataUtils.returnPr(true,"删除成功");
        }
        return DataUtils.returnPr(false,"删除失败");
    }

    /**
     * 修改出参
     * @param confOutMapping
     * @return
     */
    @PutMapping(value = "/url/outMapping")
    public PageResult updateOutMapping(ConfOutMapping confOutMapping){
        int i = 0;
        try {
            i = configureService.updateOutMapping(confOutMapping);
        } catch (Exception e) {
            return DataUtils.returnPr(false,"修改异常");
        }
        if (i > 0){
            return DataUtils.returnPr(true,"修改成功");
        }
        return DataUtils.returnPr(false,"修改失败");
    }

}
