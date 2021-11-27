package com.integration.utils;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.IomCampInterfaceMarket;
import com.integration.entity.OpenApiInterfaceMarket;
import com.integration.entity.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName OpenApiUtil
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/12/8 14:46
 * @Version 1.0
 **/
@Component
public class OpenApiUtil {

    private static Logger log = LoggerFactory.getLogger(OpenApiUtil.class);

    @Autowired
    private RestTemplate restTemplate;


    public PageResult sendRequest(OpenApiInterfaceMarket openApiInterfaceMarket){
        Integer pageNum = openApiInterfaceMarket.getPageNum();
        Integer pageSize = openApiInterfaceMarket.getPageSize();
        IomCampInterfaceMarket interfaceMarket = openApiInterfaceMarket.getInterfaceMarket();

        if(pageNum == null ){
            pageNum = 1;
        }

        if(pageSize == null ){
            pageSize = 10;
        }

        String url = interfaceMarket.getInterfaceType().getInnerPrefix();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("pageNum", pageNum);
        param.add("pageSize", pageSize);
        param.add("jsonStr", interfaceMarket.getInterfaceParamJson());

        ResponseEntity<PageResult> responseEntity = restTemplate.postForEntity(url, param, PageResult.class);


        log.info("返回值：{}", JSONObject.toJSONString(responseEntity.getBody()));

        return responseEntity.getBody();
    }

}
