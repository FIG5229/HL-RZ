package com.integration.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.fegin
 * @ClassName: InterfaceService
 * @Author: ztl
 * @Date: 2021-01-11
 * @Version: 1.0
 * @description:接口平台相关接口
 */
@FeignClient(value ="springcloud-interface-8013", fallbackFactory = InterfaceServiceFallbackFactory.class)
public interface InterfaceService {

    @RequestMapping(value = "interfaceInfo/list",method = RequestMethod.GET)
    List<Map> findInterfaceStateInfo(@RequestParam Map map);

}
