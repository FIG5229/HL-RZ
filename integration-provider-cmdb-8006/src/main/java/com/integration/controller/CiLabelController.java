package com.integration.controller;

import com.github.pagehelper.Page;
import com.integration.entity.CiLabel;
import com.integration.entity.PageResult;
import com.integration.service.CiLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiLabelController
 * @Author: ztl
 * @Date: 2020-12-03
 * @Version: 1.0
 * @description:CI标签
 */
@RestController
@RequestMapping("/label")
public class CiLabelController {

    @Autowired
    private CiLabelService ciLabelService;
    /**
     * 获取CI大类及大类下所有字段
     *
     * @return
     */
    @RequestMapping(value = "/getCitypeAndItemList", method = RequestMethod.POST)
    public List<Map<String,Object>> getCitypeAndItemList(){
        return ciLabelService.getCitypeAndItemList();
    }

    /**
     * 根据大类ID、映射字段、查询条件获取字段列表
     *
     * @param ciTypeId
     * @param mpCiItem
     * @param searchCondition
     * @return
     */
    @RequestMapping(value = "/getAttrValuesList", method = RequestMethod.POST)
    public List<String> getAttrValuesList(String ciTypeId,String mpCiItem,String searchCondition){
        return ciLabelService.getAttrValuesList(ciTypeId,mpCiItem,searchCondition);
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @RequestMapping(value = "/getLabelTree", method = RequestMethod.POST)
    public List<Map<String,Object>> getLabelTree(String labelName){
        return ciLabelService.getLabelTree(labelName);
    }

    /**
     * 根据标签ID获取标签详情
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/getLabelById", method = RequestMethod.POST)
    public Map<String,Object> getLabelById(String labelId){
        return ciLabelService.getLabelById(labelId);
    }

    /**
     * 新增或者修改标签
     *
     * @param ciLabel
     * @return
     */
    @RequestMapping(value = "/addOrUpdateLabel", method = RequestMethod.POST)
    public boolean addOrUpdateLabel(CiLabel ciLabel) {
        boolean flag = false;
        flag = ciLabelService.addOrUpdateLabel(ciLabel);
        return flag;
    }
    /**
     * 根据ID删除标签
     *
     * @param id 主键
     * @return
     */
    @RequestMapping(value = "/deleteLabelById", method = RequestMethod.POST)
    public boolean deleteLabelById(String id) {
        return ciLabelService.deleteLabelById(id);
    }

    /**
     * 获取预览数据
     *
     * @return
     */
    @RequestMapping(value = "/getLabelCiInfo", method = RequestMethod.POST)
    public PageResult getLabelCiInfo(String labelId){
       return ciLabelService.getLabelCiInfo(labelId);
    }

}
