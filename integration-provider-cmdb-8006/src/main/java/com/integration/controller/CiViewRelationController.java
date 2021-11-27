package com.integration.controller;

import com.integration.entity.DataRel;
import com.integration.entity.EmvRequestMessage;
import com.integration.service.CiViewRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiViewRelationController
 * @Author: sgh
 * @Description: CI视图关系Controller
 * @Date: 2019/10/17 15:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("viewRelation")
public class CiViewRelationController {

    @Autowired
    private CiViewRelationService ciViewRelationService;

    /**
     * @Method findCIView
     * @Author sgh
     * @Version 1.0
     * @Description 通过Ci和部分属性获取视图关系
     * @Return com.integration.entity.PageResult
     * @Exception
     * @Date 2019/10/17
     * @Param [emvRequestMessage]
     */
    @RequestMapping("findCIView")
    public Map<String, Object>  findCIView(DataRel dataRel, EmvRequestMessage emvRequestMessage) {
        Map<String, Object> map = ciViewRelationService.findRelByAll(emvRequestMessage, dataRel);
        return map;
    }

    /**
     * @Method findCIViewRCA
     * @Author sgh
     * @Version 1.0
     * @Description 通过ci获取视图关系告警
     * @Return com.integration.entity.PageResult
     * @Exception
     * @Date 2019/10/21
     * @Param [emvRequestMessage]
     */
    @RequestMapping("findCIViewRCA")
    public Map<String, Object> findCIViewRCA(EmvRequestMessage emvRequestMessage) {
        Map<String, Object> map = ciViewRelationService.findCIViewRCA(emvRequestMessage);
        return map;
    }
}
