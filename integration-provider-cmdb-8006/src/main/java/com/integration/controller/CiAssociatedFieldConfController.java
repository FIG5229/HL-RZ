package com.integration.controller;

import com.integration.entity.CiAssociatedFieldConf;
import com.integration.entity.PageResult;
import com.integration.feign.DictService;
import com.integration.service.CiAssociatedFieldConfService;
import com.integration.service.TypeItemService;
import com.integration.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiAssociatedFieldConfController
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:关联字段配置
 */
@RestController
@RequestMapping("/fieldConf")
public class CiAssociatedFieldConfController {
    @Autowired
    private DictService dictService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeItemService typeItemService;
    @Autowired
    private CiAssociatedFieldConfService ciAssociatedFieldConfService;

    /**
     * 获取数据源列表
     *
     * @param sjId
     * @return
     */
    @RequestMapping(value = "/findSourceList", method = RequestMethod.GET)
    public List<Map<String, Object>> findSourceList(String sjId) {

        return dictService.findDiceBySjIdHump(sjId==null?"258888":sjId);
    }

    /**
     * 获取所有CI大类
     *
     * @return
     */
    @RequestMapping(value = "/findCiTypeList", method = RequestMethod.GET)
    public List<Map> findCiTypeList() {
        return typeService.findTypeListHump();
    }

    /**
     * 根据CI大类ID获取CI属性列表
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/findCiTypeItemList", method = RequestMethod.GET)
    public List<Map<String, Object>> findCiTypeItemList(String typeId) {
        return typeItemService.findItemByTidToHump(typeId);
    }

    /**
     * 保存修改关联字段配置
     *
     * @param ciAssociatedFieldConf
     * @return
     */
    @RequestMapping(value = "/saveFieldConf", method = RequestMethod.POST)
    public boolean saveFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf) {

        return ciAssociatedFieldConfService.saveFieldConf(ciAssociatedFieldConf);
    }

    /**
     * 确定保存修改关联字段配置
     *
     * 增加给接口平台推送MQ消息
     *
     * @param ciAssociatedFieldConf
     * @return
     */
    @RequestMapping(value = "/confirmFieldConf", method = RequestMethod.POST)
    public boolean confirmFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf) {

        return ciAssociatedFieldConfService.confirmFieldConf(ciAssociatedFieldConf);
    }

    /**
     * 删除关联字段配置信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteFieldConf", method = RequestMethod.POST)
    public boolean deleteFieldConf(String id) {

        return ciAssociatedFieldConfService.deleteFieldConf(id);
    }

    /**
     * 获取关联字段配置列表
     *
     * @param sourceCiId 来源ID
     * @param ciTypeId 大类ID
     * @return
     */
    @RequestMapping(value = "/findFieldConfList", method = RequestMethod.POST)
    public PageResult findFieldConfList(String sourceCiId, String ciTypeId) {
        return ciAssociatedFieldConfService.findFieldConfList(sourceCiId,ciTypeId);
    }

    /**
     *
     * 获取关联字段配置详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findFieldConf", method = RequestMethod.POST)
    public CiAssociatedFieldConf findFieldConf(String id) {
        return ciAssociatedFieldConfService.findFieldConf(id);
    }

    /**
     * 触发关联字段配置
     *
     * @param id
     */
    @RequestMapping(value = "/triggerFieldConf", method = RequestMethod.POST)
    public void triggerFieldConf(String id) {
        ciAssociatedFieldConfService.triggerFieldConf(id);
    }

}
