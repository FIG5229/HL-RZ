package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.integration.config.IomPlatformParam;
import com.integration.entity.IomCampLicenseAuth;
import com.integration.entity.PageResult;
import com.integration.service.IomCampLicenseAuthServerService;
import com.integration.service.IomCampLicenseAuthService;
import com.integration.utils.DataUtils;
import com.integration.utils.LicenseUtil;
import com.integration.utils.LicenseUtilExtend;
import com.integration.utils.SerialNumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
/**
* @Package: com.integration.controller
* @ClassName: LicenseController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 授权
*/
@RestController
public class LicenseController {

    public static final Logger log = LoggerFactory.getLogger(LicenseController.class);

    @Autowired
    IomCampLicenseAuthServerService licenseAuthServerService;

    @Autowired
    IomCampLicenseAuthService licenseAuthService;

    @Autowired
    private LicenseUtilExtend licenseUtilExtend;

    private boolean license = IomPlatformParam.LICENSES_ENABLE;
    @RequestMapping("/delAuthServer")
    public void deleteByServerIp(String ip){
        boolean b = licenseAuthServerService.deleteByServerIp(ip);
        String message = b ? "删除成功！" : "删除失败！";
        PageResult.setMessage(message, b);

    }


    @RequestMapping("/authClientCode")
    public String authClientCode(){
        String clientCode = licenseAuthService.getClientCode();
        PageResult.setMessage("识别码获取成功！",true);
        return clientCode;
    }

    /**
     * @Author zhangfeng
     * @Description //此方法供前端做授权判断用
     * @Date 16:21 2020/7/17
     * @Param []
     * @return java.lang.String
     */
    @RequestMapping("/licenseStatus")
    public PageResult licenseStatus(){
        return DataUtils.returnPr(true);
    }


    @RequestMapping("/authinfo")
    public String authinfo(){
        IomCampLicenseAuth auth = licenseAuthService.get(null);
        PageResult.setMessage("获取授权信息成功！",true);
        return JSONObject.toJSONString(auth);
    }


    /**
     * 检测系统是否授权
     * @return
     */
    @RequestMapping("/authCodeCheck")
    public PageResult authCodeCheck(){
        licenseUtilExtend.check();
        JSONObject jo = new JSONObject();
        jo.put(SerialNumberUtil.ALLOWFLAG, System.getProperty(SerialNumberUtil.ALLOWFLAG));
        jo.put(SerialNumberUtil.ALLOWMODULE, System.getProperty(SerialNumberUtil.ALLOWMODULE));
        return DataUtils.returnPr(jo);
    }


    @RequestMapping("/registerAuth")
    public String registerAuth(IomCampLicenseAuth auth){
        PageResult pageresult = validate(auth);
        log.info(String.format("授权码验证结束：%s", JSONObject.toJSONString(pageresult)));
        if(!pageresult.isReturnBoolean()){
            PageResult.setMessage("授权注册失败！",false);
            return null;
        }
        licenseAuthService.register(auth);
        IomCampLicenseAuth result = licenseAuthService.get(null);
        PageResult.setMessage("授权注册成功！",true);
        return JSONObject.toJSONString(result);
    }

    private PageResult validate(IomCampLicenseAuth authLog){
        if(StringUtils.isEmpty(authLog.getAuthCode())){
            return DataUtils.returnPr(false,"授权码不能为空");
        }
        return LicenseUtil.checkLicense(authLog.getAuthCode());

    }

    @RequestMapping("/authModule")
    public PageResult authModule(){
        List<String> urlList = new ArrayList<>();
        SerialNumberUtil.allowModule().stream().forEach(item->{
            IomCampLicenseAuth.module module = IomCampLicenseAuth.module.valueOf(item);
            urlList.add(module.getPreUrl());
        });
        return DataUtils.returnPr(urlList);
    }

    @RequestMapping("/functionDisplay")
    public Map<String, Object> functionDisplay(){
        Map<String,Object> authMap = new HashMap<>();
        boolean robotIsEnable = false;
        boolean alarmIsEnable = false;
       if (license){
           for (String item:SerialNumberUtil.allowModule()) {
               if ("robot".equals(item)){
                   robotIsEnable =true;
               }
               if ("alarm".equals(item)){
                   alarmIsEnable =true;
               }
           }
       }else{
           robotIsEnable = true;
           alarmIsEnable = true;
       }
        authMap.put("robotAlarmIsEnable", alarmIsEnable);
        authMap.put("robotIsEnable", robotIsEnable);
        return authMap;
    }
}
