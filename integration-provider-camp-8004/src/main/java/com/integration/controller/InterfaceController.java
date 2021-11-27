package com.integration.controller;

import com.integration.fegin.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: InterfaceController
 * @Author: ztl
 * @Date: 2021-01-11
 * @Version: 1.0
 * @description:接口平台接口状态
 */
@RestController
@RequestMapping("/interface")
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    /**
     * 获取接口平台接口状态
     *
     * @return
     */
    @RequestMapping(value = "/interfaceStateInfo",method = RequestMethod.GET)
    public List<Map> findInterfaceStateInfo(){
        return interfaceService.findInterfaceStateInfo(new HashMap());
    }
}
