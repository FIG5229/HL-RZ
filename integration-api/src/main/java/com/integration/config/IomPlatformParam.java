package com.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IomPlatformParam {

    //授权服务器ip
    public static String authServerIp;

    //版本
    public static String version;

    //授权系统url
    public static String authSystemUrl;


    public static boolean LICENSES_ENABLE = true;

    @Value("${iomplatform.authServer:}")
    public  void setAuthServerIp(String authServerIp) {
        IomPlatformParam.authServerIp = authServerIp;
    }

    //@Value("${iomplatform.version:1.4}")
    @Value("2.0")
    public  void setVersion(String version) {
        IomPlatformParam.version = version;
    }

    @Value("${iomplatform.auth.system.url:}")
    public  void setAuthSystemUrl(String authSystemUrl) {
        IomPlatformParam.authSystemUrl = authSystemUrl;
    }

    //@Value("${product.license:false}")
    public void setLicensesEnable(boolean licensesEnable) {
        LICENSES_ENABLE = licensesEnable;
    }
}
