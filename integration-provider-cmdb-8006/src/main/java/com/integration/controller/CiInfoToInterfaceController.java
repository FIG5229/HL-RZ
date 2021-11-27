package com.integration.controller;

import com.integration.dao.CiInfoToInterfaceDao;
import com.integration.service.CiInfoToInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiInfoToInterfaceController
 * @Author: ztl
 * @Date: 2020-09-11
 * @Version: 1.0
 * @description:为接口平台提供ci数据
 */
@RestController
@RequestMapping("ciInfoToInterface")
public class CiInfoToInterfaceController {

    @Autowired
    private CiInfoToInterfaceService ciInfoToInterfaceService;
    @Resource
    private CiInfoToInterfaceDao ciInfoToInterfaceDao;
    /**
     * 查询CI信息
     *
     * @return
     */
    @RequestMapping(value = "/findCiInfo",method = RequestMethod.POST)
    public List<Map<String,Object>> findCiInfo(){
        return ciInfoToInterfaceService.findCiInfo();
    }

    /**
     * 查询CI版本信息
     * @return
     */
    @RequestMapping(value = "/finCiInfoVersion",method = RequestMethod.POST)
    public String finCiInfoVersion(){
        return ciInfoToInterfaceDao.findCiVersion();
    }

}
