package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.IomCampInterfaceMarket;
import com.integration.entity.OpenApiInterfaceMarket;
import com.integration.entity.PageResult;
import com.integration.service.IomCampInterfaceMarketService;
import com.integration.utils.DataUtils;
import com.integration.utils.OpenApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OpenApiController
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/12/8 10:45
 * @Version 1.0
 **/
@RequestMapping("/openApi")
@RestController
public class OpenApiController {

    private static Logger log = LoggerFactory.getLogger(OpenApiController.class);

    @Autowired
    private OpenApiUtil openApiUtil;
    @Autowired
    private IomCampInterfaceMarketService interfaceMarketService;


    @RequestMapping("/ciType/{path}")
    public PageResult ciType(@PathVariable("path") String path, OpenApiInterfaceMarket interfaceMarket){

        log.info("参数：path:{},{}",path,JSONObject.toJSONString(interfaceMarket));
        //根据path，类型查询对应接口记录
        IomCampInterfaceMarket market = new IomCampInterfaceMarket();
        market.setUsername(interfaceMarket.getUsername());
        market.setPassword(interfaceMarket.getPassword());
        market.setInterfaceType(IomCampInterfaceMarket.InterfaceType.CITYPE);
        market.setInterfaceUri(path);

        PageResult pg = interfaceMarketService.selectOpenApi(market);
        if(!pg.isReturnBoolean()){
            return pg;
        }
        IomCampInterfaceMarket iomCampInterfaceMarket = null;
        if(pg.getReturnObject() != null){
            iomCampInterfaceMarket =(IomCampInterfaceMarket)  pg.getReturnObject();
        }
        if(iomCampInterfaceMarket != null){
            //证明允许访问
            interfaceMarket.setInterfaceMarket(iomCampInterfaceMarket);
            return openApiUtil.sendRequest(interfaceMarket);

        }

        return DataUtils.returnPr(false,"未知错误！");
    }

    @RequestMapping("/alert/{path}")
    public PageResult alert(@PathVariable("path") String path, OpenApiInterfaceMarket interfaceMarket){
        log.info("参数：path:{},{}",path,JSONObject.toJSONString(interfaceMarket));
        //根据path，类型查询对应接口记录
        IomCampInterfaceMarket market = new IomCampInterfaceMarket();
        market.setUsername(interfaceMarket.getUsername());
        market.setPassword(interfaceMarket.getPassword());
        market.setInterfaceType(IomCampInterfaceMarket.InterfaceType.ALERT);
        market.setInterfaceUri(path);

        PageResult pg = interfaceMarketService.selectOpenApi(market);
        if(!pg.isReturnBoolean()){
            return pg;
        }
        IomCampInterfaceMarket iomCampInterfaceMarket = null;
        if(pg.getReturnObject() != null){
            iomCampInterfaceMarket =(IomCampInterfaceMarket)  pg.getReturnObject();
        }
        if(iomCampInterfaceMarket != null){
            //证明允许访问
            interfaceMarket.setInterfaceMarket(iomCampInterfaceMarket);
            return openApiUtil.sendRequest(interfaceMarket);

        }

        return DataUtils.returnPr(false,"未知错误！");
    }


}
