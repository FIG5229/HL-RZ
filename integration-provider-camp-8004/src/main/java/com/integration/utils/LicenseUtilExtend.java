package com.integration.utils;

import com.integration.config.IomPlatformParam;
import com.integration.config.init.LisenceRunner;
import com.integration.entity.IomCampLicenseAuth;
import com.integration.service.IomCampLicenseAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName LicenseUtil
 * @Description 集成api中鉴权工具类，新增无参授权方法
 * @Author zhangfeng
 * @Date 2021/8/9 14:19
 * @Version 1.0
 **/
@Component
public class LicenseUtilExtend extends LicenseUtil{

    Logger logger = LoggerFactory.getLogger(LisenceRunner.class);

    @Autowired
    private IomCampLicenseAuthService iomCampLicenseAuthService;

    /**
     * 检测系统是否授权
     */
    public void check(){
        if(!IomPlatformParam.LICENSES_ENABLE){
            return ;
        }
        try {
            IomCampLicenseAuth auth = iomCampLicenseAuthService.get(null);

            String authCode = null;
            if(auth != null){
                authCode = auth.getAuthCode();
            }
            logger.debug(authCode);
            check(authCode);

        }catch (Exception e){

            logger.error("启动自检异常", e);

        }
    }

}
