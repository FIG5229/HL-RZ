package com.integration.controller;

import com.integration.entity.Domain;
import com.integration.service.DomainService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: DomainController
 * @Author: ztl
 * @Description:1.数据域表用户控制数据权限。2. 可按组织机构（单位）配置数据域，在业务表中存放数据域字段
 * @Date: 2020/5/20 14:37
 * @Version: 1.0
 * @Modified By:
 */
@RestController
public class DomainController {

    @Resource
    private DomainService domainService;
    /**
     *
     * @param domainCode 机构代码
     * @return 数据域对象
     */
    @RequestMapping("/domain/findDomain")
    public Domain findDomain(@RequestParam("domainCode") String domainCode){
        return domainService.findDomainByDomainCode(domainCode);
    }
    /**
     * @Author: ztl
     * date: 2021-08-25
     * @description: 获取所有数据域
     */
    @RequestMapping("/domain/findAllDomainId")
    public List<String> findAllDomainId(){
        return domainService.findAllDomainId();
    }
}
