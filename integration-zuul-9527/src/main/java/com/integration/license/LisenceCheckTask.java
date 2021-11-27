package com.integration.license;

import com.integration.config.IomPlatformParam;
import com.integration.service.LisenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@ConditionalOnProperty(name = "product.license")
@Component
public class LisenceCheckTask implements Runnable{

    static Logger logger = LoggerFactory.getLogger(LisenceCheckTask.class);

    @Autowired
    private LisenceService lisenceService;



    @Override
    public void run() {
        logger.debug("授权检测");
        if(!IomPlatformParam.LICENSES_ENABLE){
            return ;
        }
        Thread.currentThread().setName("license-check");

        lisenceService.authCodeCheck();
    }
}
