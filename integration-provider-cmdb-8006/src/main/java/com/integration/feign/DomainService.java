package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ProjectName: integration
 * @Package: com.integration.feign
 * @ClassName: DomainService
 * @Author: ztl
 * @Description: 获取数据域信息
 * @Date: 2020/5/20 14:51
 * @Version: 1.0
 * @Modified By:
 */
@FeignClient(value = "camp", fallbackFactory = DomainServiceFallbackFactory.class)
public interface DomainService {

    /**
     * 根据机构代码获取数据域对象
     * @param domainCode 机构代码
     * @return 数据域对象
     */
    @RequestMapping("/domain/findDomain")
    Object findDomain(@RequestParam("domainCode")String domainCode);

}
