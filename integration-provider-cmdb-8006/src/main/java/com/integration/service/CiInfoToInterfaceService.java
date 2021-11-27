package com.integration.service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: CiInfoToInterfaceService
 * @Author: ztl
 * @Date: 2020-09-11
 * @Version: 1.0
 * @description:为接口平台提供CI数据
 */
public interface CiInfoToInterfaceService {
    /**
     * 查询CI信息
     * @return
     */
    public List<Map<String,Object>> findCiInfo();

    /**
     * 修改CI版本
     * @return
     */
    boolean updateCiVesion();

}
