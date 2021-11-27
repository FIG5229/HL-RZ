package com.integration.config.init;

import com.integration.config.IomPlatformParam;
import com.integration.entity.IomCampLicenseAuth;
import com.integration.service.IomCampLicenseAuthService;
import com.integration.utils.LicenseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
/**
* @Package: com.integration.config.init
* @ClassName: LisenceRunner
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 启动 检查授权
*/
@Component
public class LisenceRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(LisenceRunner.class);

    @Autowired
    private IomCampLicenseAuthService iomCampLicenseAuthService;

    private Long delay = 60*60*3L;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
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
            LicenseUtil.check(authCode);

        }catch (Exception e){

            logger.error("启动自检异常", e);

        }

    }
}
