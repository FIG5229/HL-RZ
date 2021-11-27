package com.integration.license;

import com.integration.utils.RSAUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

//@ConditionalOnProperty(name = "product.license")
@Configuration
@Import(RSAUtil.class)
public class LicenseConfig {

    public static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);



}
