package com.integration.controller;

import com.integration.entity.EmvRequestMessage;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.service.InfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiInfoIndexController
 * @Author: sgh
 * @Description: CI信息和属性值
 * @Date: 2019/10/15 17:33
 * @Version: 1.0
 */
@RestController
@RequestMapping("ciInfoIndex")
public class CiInfoIndexController {
    @Autowired
    private InfoService infoService;

    /**
     * @Method findByTypeId
     * @Author sgh
     * @Version 1.0
     * @Description 根据大类ID查询ci信息和某一个属性的值
     * @Return java.util.List<com.integration.entity.CiInfoIndex>
     * @Exception
     * @Date 2019/10/15
     * @Param []
     */
    @RequestMapping("findByTypeId")
    public List<EmvReturnCIMessage> findByTypeId(@RequestBody EmvRequestMessage emvRequestMessage) {
        List<EmvReturnCIMessage> ciInfoIndexList = infoService.findIndexByTypeId(emvRequestMessage);
        return ciInfoIndexList;
    }

    /**
     * @Method findCisBySource
     * @Description 根据来源查询CI
     * @Param [source] 来源Id
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/5/25 10:46
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findCisBySource")
    public List<Map<String, Object>> findCisBySource(String source) {
        if (StringUtils.isEmpty(source)) {
            //不存在来源ID
            return null;
        }
        return infoService.findCisBySource(source);
    }
}
