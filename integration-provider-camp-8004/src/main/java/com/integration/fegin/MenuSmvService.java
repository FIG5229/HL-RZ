package com.integration.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.fegin
* @ClassName: MenuSmvService
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 场景可视化feign调用
*/
@FeignClient(value ="scene", fallbackFactory = MenuSmvServiceFallbackFactory.class)
public interface MenuSmvService {

    /**
     * 查询全部
     * @return
     */
    @RequestMapping(value = "customMenu/findMenuAll",method = RequestMethod.GET)
    List<Map> findMenuAll();

    @RequestMapping(value = "custmenu/getCustMenuListMap",method = RequestMethod.POST)
    List<Map> getCustMenuListMap();


    @RequestMapping(value = "custmenu/getCustMenuUrlByIds",method = RequestMethod.POST)
    List<Map> getCustMenuUrlByIds(@RequestParam("ids") String[] ids);
}
