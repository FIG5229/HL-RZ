package com.integration.fegin;

import feign.hystrix.FallbackFactory;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
/**
* @Package: com.integration.fegin
* @ClassName: MenuSmvServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 场景可视化feign调用
*/
@Component
public class MenuSmvServiceFallbackFactory implements FallbackFactory<MenuSmvService> {
    @Override
    public MenuSmvService create(Throwable arg0) {
        return new MenuSmvService() {

            @Override
            public List<Map> findMenuAll() {
                return null;
            }

            @Override
            public List<Map> getCustMenuListMap() {
                return null;
            }

            @Override
            public List<Map> getCustMenuUrlByIds(String[] ids) {
                return null;
            }
        };
    }


}
