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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IomCampInterfaceMarketController
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/12/8 10:08
 * @Version 1.0
 **/
@RequestMapping("/interfaceMarket")
@RestController
public class IomCampInterfaceMarketController {

    private static Logger log = LoggerFactory.getLogger(IomCampInterfaceMarketController.class);

    @Autowired
    private OpenApiUtil openApiUtil;

    @Autowired
    private IomCampInterfaceMarketService interfaceMarketService;

    @Value("${iomplatform.interfaceMarket:}")
    private String interfaceMarket;

    @RequestMapping("/list")
    public PageResult list(IomCampInterfaceMarket iomCampInterfaceMarket){

        return interfaceMarketService.list(iomCampInterfaceMarket);

    }

    @RequestMapping("/info")
    public PageResult info(IomCampInterfaceMarket iomCampInterfaceMarket){

        return interfaceMarketService.info(iomCampInterfaceMarket.getId());

    }

    @RequestMapping("/save")
    public PageResult save(IomCampInterfaceMarket iomCampInterfaceMarket){

        return interfaceMarketService.save(iomCampInterfaceMarket);

    }

    @RequestMapping("/update")
    public PageResult update(IomCampInterfaceMarket iomCampInterfaceMarket){

        return interfaceMarketService.update(iomCampInterfaceMarket);

    }

    @RequestMapping("/updatePass")
    public PageResult updatePass(IomCampInterfaceMarket iomCampInterfaceMarket){

        IomCampInterfaceMarket update = new IomCampInterfaceMarket();
        update.setId(iomCampInterfaceMarket.getId());
        update.setPassword(iomCampInterfaceMarket.getPassword());


        return interfaceMarketService.updatePass(update);

    }

    @RequestMapping("/delete")
    public PageResult delete(IomCampInterfaceMarket iomCampInterfaceMarket){

        if(iomCampInterfaceMarket == null || iomCampInterfaceMarket.getId() == null){
            return DataUtils.returnPr(false,"参数错误");
        }

        return interfaceMarketService.deleteById(iomCampInterfaceMarket.getId());

    }

    @RequestMapping("/test")
    public PageResult test(OpenApiInterfaceMarket openApiInterfaceMarket){

        IomCampInterfaceMarket interfaceMarket = openApiInterfaceMarket.getInterfaceMarket();

        if(interfaceMarket == null ){
            return DataUtils.returnPr(false, "参数错误。");
        }
        if(interfaceMarket.getInterfaceType() == null || interfaceMarket.getInterfaceParamJson() == null){
            return DataUtils.returnPr(false, "参数错误。");
        }


        PageResult pg = openApiUtil.sendRequest(openApiInterfaceMarket);


        log.info("返回值：{}", JSONObject.toJSONString(pg));
        return pg;
    }

    @RequestMapping("/uri")
    public PageResult interfaceMarketUrl(){
        return DataUtils.returnPr(interfaceMarket);
    }

    public String getInterfaceMarket() {
        return interfaceMarket;
    }

    public void setInterfaceMarket(String interfaceMarket) {
        this.interfaceMarket = interfaceMarket;
    }
}
