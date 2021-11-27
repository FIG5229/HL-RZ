package com.integration.controller;

import com.integration.entity.Organiza;
import com.integration.entity.PageResult;
import com.integration.service.OrganizaService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
* @Package: com.integration.controller
* @ClassName: OrganizaController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 初版组织架构
*/
@RestController
public class OrganizaController {

    @Autowired
    OrganizaService organizaService;

    /**
     * 增加机构
     *
     * @param organiza
     * @return
     */
    @RequestMapping(value = "/addOrgan", method = RequestMethod.POST)
    public boolean addOrgan(Organiza organiza, String sjId, HttpServletRequest request) {
        //id
        organiza.setId(SeqUtil.nextId() + "");
        //创建人id
        String cjrId = TokenUtils.getTokenUserId();
        organiza.setCjr_id(cjrId);
        //创建时间
        organiza.setCjsj(DateUtils.getDate());
        //上级id
        if (sjId.isEmpty()) {
            organiza.setSj_id(null);
        } else {
            organiza.setSj_id(sjId);
        }
        if (organiza.getXgr_id().isEmpty()) {
            organiza.setXgr_id(null);
        }
        if (organiza.getXgsj().isEmpty()) {
            organiza.setXgsj(null);
        }
        int result = organizaService.addOrgan(organiza);
        PageResult.success("添加成功");
        return result > 0;
    }

    /**
     * 删除机构
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteOrgan", method = RequestMethod.POST)
    public boolean deleteOrgan(String id) {
        int result = organizaService.deleteOrgan(id);
        PageResult.success("删除成功");
        return result > 0;
    }

    /**
     * 修改机构
     *
     * @param organiza
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateOrgan", method = RequestMethod.POST)
    public boolean updateOrgan(Organiza organiza, HttpServletRequest request) {
        //修改人id
        String xgrId = TokenUtils.getTokenUserId();
        organiza.setXgr_id(xgrId);
        //修改时间
        organiza.setXgsj(DateUtils.getDate());
        int result = organizaService.updateOrgan(organiza);
        PageResult.success("修改成功");
        return result > 0;
    }

    /**
     * 查询机构
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findOrgan", method = RequestMethod.GET)
    public List<Organiza> findOrgan(String id) {
        List<Organiza> list = new ArrayList<Organiza>();
        if (id.isEmpty()) {
            list = organizaService.findAllMax();
        } else {
            list = organizaService.findNex(id);
        }
        return list;
    }
}
