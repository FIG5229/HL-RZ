package com.integration.license;

import com.integration.config.IomPlatformParam;
import com.integration.entity.PageResult;
import com.integration.service.LisenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 启动 检查授权
 */
//@ConditionalOnProperty(name = "product.license")
@Component
public class LisenceRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(LisenceRunner.class);


    @Autowired
    private LisenceCheckTask lisenceCheckTask;

//    @Value("${iomplatform.scheduled.license.cron:18000}")
    private Long delay = 60*60*3L;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LisenceService lisenceService;

    //8004状态 true可用 false不可用
    static boolean ENABLE8004 = false;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        if(!IomPlatformParam.LICENSES_ENABLE){
            return ;
        }
        try {
            while (!ENABLE8004){
                try {
                    ResponseEntity<PageResult> resultResponseEntity = restTemplate.getForEntity("http://camp/appStatus", PageResult.class);
                    PageResult pg = resultResponseEntity.getBody();
                    if(pg != null && pg.isReturnBoolean()){
                        ENABLE8004 = true;
                    }else {
                        ENABLE8004 = false;
                        logger.error("8004未启动");
                        Thread.sleep(30000);
                    }
                }catch (Exception e){
                    ENABLE8004 = false;
                    e.printStackTrace();
                    logger.error("8004未启动");
                    Thread.sleep(30000);
                }
            }

            lisenceService.authCodeCheck();

            LicenseConfig.executor.scheduleWithFixedDelay(lisenceCheckTask,1,delay, TimeUnit.SECONDS);
        }catch (Exception e){

            logger.error("启动自检异常", e);

        }

    }
}
