package com.integration.common;

import com.integration.service.TypeDataService;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.common
 * @ClassName: PubTypeDataController
 * @Author: ztl
 * @Date: 2021-01-04
 * @Version: 1.0
 * @description:公共CI信息查询接口
 */
@RestController
@RequestMapping("/public/typeDate")
public class PubTypeDataController {
    @Autowired
    private TypeDataService typeDataService;

    /**
     * 根据CIID查询CI信息，字段转换为属性名
     * @param ciId
     * @return
     */
    @RequestMapping(value = "/findCiByCiId", method = RequestMethod.GET)
    public Map<String, Object> findCiByCiId(String ciId,String ciBm) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDomainId();
        Map map = new HashMap();
        map.put("domainId",domainId);
        map.put("ciId",ciId);
        map.put("ciBm",ciBm);
        Map<String, Object> result = typeDataService.findCiByCiId(map);
        return result;
    }

    /**
     *
     * 给接口平台提供关联字段相关CI信息查询
     *
     * @param sourceCiId
     * @param ciTypeId
     * @return
     */
    @RequestMapping(value = "/findFieldConfCiData", method = RequestMethod.GET)
    public List<Map<String, Object>> findFieldConfCiData(String sourceCiId, String ciTypeId) {

        return typeDataService.findFieldConfCiData(sourceCiId,ciTypeId);
    }
}
