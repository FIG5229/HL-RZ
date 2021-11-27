package com.integration.controller;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.*;
import com.integration.service.CiKpiService;
import com.integration.service.CiKpiTplService;
import com.integration.service.TypeService;
import com.integration.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author hbr
 * @date 2018-12-17 09:49:01
 *
 * @version 1.0
 */
@RestController
public class CiKpiTplController {

    @Autowired
    CiKpiService ciKpiService;

    @Autowired
    TypeService typeService;

    @Autowired
    CiKpiTplService ciKpiTplService;
    /**
     * 获取所有模型
     * @return
     */
    @RequestMapping(value = "/getAllKpi", method = RequestMethod.GET)
    public List<Kpi_Type> getAllKpi(String search){
        List<Kpi_Type> kpiType = ciKpiService.getKpi_Type(search);
        return kpiType;
    }

    /**
     * 获取所有对象组
     * @return
     */
    @RequestMapping(value = "/getObjects", method = RequestMethod.GET)
    public List<Type> getObjects(){
        List<Type> typeList = typeService.findTypeList();
        return typeList;
    }

    /**
     * 新增模板
     * @param info
     * @param kpiIdArr
     * @param objIdArr
     * @return
     */
    @RequestMapping(value = "/addKpiTpl", method = RequestMethod.POST)
    public boolean addKpiTpl(CiKpiTplInfo info, String[] kpiIdArr, String[] objIdArr, HttpServletRequest request) {
        boolean flag;
        int i = 0;
        i = ciKpiTplService.insertInfo(info, kpiIdArr, objIdArr, request);
        if (i > 0) {
            flag = true;
            PageResult.success("添加成功");
        } else {
            flag = false;
            PageResult.fail("添加失败");
        }
        return flag;
    }

    /**
     * 获取所有模板
     * @return
     */
    @RequestMapping(value = "/getAllTpl", method = RequestMethod.GET)
    public List<Tpl_Item> getTplItem() {
        List<Tpl_Item> list = ciKpiTplService.getTpl_Item();
        return list;
    }

    /**
     * 删除模板
     * @return
     */
    @OptionLog(desc="删除模块", module="模型模块" , writeParam=true, writeResult=true)
    @RequestMapping(value = "/deleteTpl", method = RequestMethod.POST)
    public boolean deleteTpl(String tplId) {
        //成功失败标志
        boolean flag = false;
        int i = ciKpiTplService.deleteInfo(tplId);
        if (i > 0) {
            flag = true;
        }
        if (flag == true) {
            PageResult.success("删除成功");
        } else {
            PageResult.fail("删除失败");

        }
        return flag;
    }

    /**
     * 修改模板
     * @param info
     * @param kpiIdArr
     * @param objIdArr
     * @param request
     * @return
     */
    @OptionLog(desc = "修改模板", module = "模型模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateTpl", method = RequestMethod.POST)
    public boolean updateTpl(CiKpiTplInfo info, String[] kpiIdArr, String[] objIdArr, HttpServletRequest request) {
        //成功失败标志
        boolean flag = false;
        int i = 0;
        i = ciKpiTplService.updateInfo(info, kpiIdArr, objIdArr, request);
        if (i > 0) {
            flag = true;
            PageResult.success("修改成功");
        } else {
            flag = false;
            PageResult.success("修改失败");
        }
        return flag;
    }

}