package com.integration.controller;

import com.integration.entity.Org;
import com.integration.entity.PageResult;
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
 * @ClassName: PublicController
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:公共接口
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private DictController dictController;

    @Autowired
    private RoleDomainService roleDomainService;
    /**
     * 获取数据来源列表
     *
     * @param sjId
     * @return
     */
    @RequestMapping(value = "/findSourceList", method = RequestMethod.GET)
    public List<Map<String, Object>> findSourceList(String sjId) {

        return (List<Map<String, Object>>) dictController.findDiceBySjIdHump(sjId==null?"258888":sjId);
    }

    /**
     * 查询数据权限对应机构列表
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/findDomainOrgList", method = RequestMethod.POST)
    public Map<String, Object> findRoleDomainOrgList(String id, HttpServletRequest request) {

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
