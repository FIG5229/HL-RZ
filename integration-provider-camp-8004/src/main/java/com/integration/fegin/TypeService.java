package com.integration.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.fegin
 * @ClassName: TypeService
 * @Author: ztl
 * @Date: 2020-06-11
 * @Version: 1.0
 * @description:获取CI大类相关信息
 */
@FeignClient(value = "cmdb", fallbackFactory = TypeServiceFallbackFactory.class)
public interface TypeService {

    /**
     *获取所有大类（驼峰 有数据域权限）
     * @return
     */
    @RequestMapping(value = "/type/findCiTypeListHumps", method = RequestMethod.GET)
    public List<Map> findTypeListHump();
}
