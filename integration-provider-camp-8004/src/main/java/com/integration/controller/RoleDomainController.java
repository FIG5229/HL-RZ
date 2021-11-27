package com.integration.controller;

import com.integration.entity.Org;
import com.integration.entity.PageResult;
import com.integration.entity.RoleDomain;
import com.integration.service.RoleDomainService;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: RoleDomainController
 * @Author: ztl
 * @Date: 2021-07-20
 * @Version: 1.0
 * @description:角色对应数据域关系
 */
@RequestMapping("/roleDomain")
@RestController
public class RoleDomainController {

    @Autowired
    private RoleDomainService roleDomainService;

    /**
     * 根据角色代码获取角色对应的数据域
     *
     * @param roleDm
     *
     * @return
     */
    @RequestMapping(value = "/getRoleDomainByRoleDm", method = RequestMethod.POST)
    public RoleDomain getRoleDomainByRoleDm(String roleDm){

        return roleDomainService.getRoleDomainByRoleDm(roleDm);

    }

    /**
     * 保存角色对应的数据域
     *
     * @param roleDomain
     * @return
     */
    @RequestMapping(value = "/saveRoleDomain", method = RequestMethod.POST)
    public boolean saveRoleDomain(RoleDomain roleDomain){

        return roleDomainService.saveRoleDomain(roleDomain);

    }

    /**
     * 查询数据权限对应机构列表
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/findRoleDomainOrgList", method = RequestMethod.POST)
    public Map<String, Object> findRoleDomainOrgList(String id, HttpServletRequest request) {
        //暂时没有前端传递机构ID参数场景
        //登录人员代码
        String userCode = TokenUtils.getTokenUserName();
        //超级管理员展示所有组织机构列表，其余根据登录用户的机构id进行查询
        if ("sysadmin".equalsIgnoreCase(userCode)) {
            id = "";
        } else {
            //获取登录人员机构ID
            id = TokenUtils.getTokenOrgId();
        }
        Map<String, Object> orgMap = new HashMap<String, Object>();
        try {
            List<Org> list = roleDomainService.findRoleDomainOrgList(id);
            /*输出构建好的组织机构树*/
            orgMap.put("success", "true");
            orgMap.put("list", list);
        } catch (Exception e) {
            PageResult.fail("查询异常");
            return null;

        }
        return orgMap;
    }


}
