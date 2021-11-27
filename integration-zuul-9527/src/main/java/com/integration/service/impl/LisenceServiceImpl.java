package com.integration.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.integration.config.IomPlatformParam;
import com.integration.entity.PageResult;
import com.integration.feign.CzryService;
import com.integration.service.LisenceService;
import com.integration.utils.AllowFlag;
import com.integration.utils.DataUtils;
import com.integration.utils.SerialNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LisenceServiceImpl
 * @Description 授权服务
 * @Author zhangfeng
 * @Date 2021/8/9 15:45
 * @Version 1.0
 **/
@Service
public class LisenceServiceImpl implements LisenceService {

    Logger log = LoggerFactory.getLogger(LisenceServiceImpl.class);

    @Autowired
    private CzryService czryService;

    @Override
    public PageResult authCodeCheck() {
        if(!IomPlatformParam.LICENSES_ENABLE){
            return DataUtils.returnPr(true, "授权未开");
        }
        PageResult checkPg = czryService.authCodeCheck();
        log.info("鉴权检测" + JSONObject.toJSONString(checkPg));
        if(checkPg.isReturnBoolean()){
            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(checkPg.getReturnObject()));
            String allowModule = jo.getString(SerialNumberUtil.ALLOWMODULE);
            System.setProperty(SerialNumberUtil.ALLOWMODULE, allowModule);
            Integer allowFlag = jo.getInteger(SerialNumberUtil.ALLOWFLAG);
            //授权检测通过设置环境变量
            System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(allowFlag));
        }
        return DataUtils.returnPr(false);
    }
}
