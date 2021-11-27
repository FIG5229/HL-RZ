package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.feign
 * @ClassName: RoleDataService
 * @Author: ztl
 * @Date: 2020-06-14
 * @Version: 1.0
 * @description:获取用户最高数据权限
 */
@FeignClient(value="camp",fallbackFactory = RoleDataFallbackFactory.class)
@Component
public interface RoleDataService {
    /**
     * 获取当前登录用户最高数据权限列表
     * @return
     */
    @RequestMapping(value = "/roleData/findRoleHighDataList", method = RequestMethod.GET)
    List<Map> findRoleHighDataList();
}
