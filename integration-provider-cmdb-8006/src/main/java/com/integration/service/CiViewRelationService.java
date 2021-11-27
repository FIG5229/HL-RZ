package com.integration.service;

import com.integration.entity.DataRel;
import com.integration.entity.EmvRequestMessage;

import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: CiViewRelationService
 * @Author: sgh
 * @Description: CI视图关系Service
 * @Date: 2019/10/17 15:52
 * @Version: 1.0
 */
public interface CiViewRelationService {

    /**
     * 根据多个属性值查询列表
     *
     * @param emvRequestMessage
     * @return
     */
    Map<String,Object> findRelByAll(EmvRequestMessage emvRequestMessage, DataRel dataRel);

    /**
     * 通过ci获取视图关系
     *
     * @param emvRequestMessage
     * @return
     */
    Map<String,Object> findCIViewRCA(EmvRequestMessage emvRequestMessage);

}
