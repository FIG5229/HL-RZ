package com.integration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.integration.config.Audience;
import com.integration.entity.Domain;
import com.integration.mybatis.utils.dbInit.DbInitialization;
import com.integration.service.DomainService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.integration.entity.Org;
import com.integration.entity.PageResult;
import com.integration.generator.dao.IomCampOrgMapper;
import com.integration.generator.entity.IomCampOrgExample;
import com.integration.service.OrgService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;

/**
 * 组织机构Controller
 *
 * @author zhuxd
 * @date 2019-07-31
 */
@RestController
public class OrgController {
    @Autowired
    OrgService orgService;
    @Autowired
    Audience audience;
    @Autowired
    private DomainService domainService;
    @Resource
    DbInitialization dbInit;
    private final static Logger logger = LoggerFactory
            .getLogger(MenuConntroller.class);

    /**
     * 增加组织机构
     *
     * @param org 组织机构
     * @return PageResult
     */
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public boolean addOrg(Org org, HttpServletRequest request) {
        if (org.getDataDomain()!=null && !"".equals(org.getDataDomain())){
            int dataDomainNum = orgService.checkDataDomain(org.getDataDomain(),org.getId());
            if (dataDomainNum>0){
                PageResult.fail("存在相同数据域，请修改数据域后再进行保存!");
                return false;
            }
        }
        //设置根节点的上级id为0
        if (StringUtils.isEmpty(org.getPid())) {
            org.setPid("0");
        }
        //id
        org.setId(SeqUtil.nextId() + "");
        //根据上级节点验证是否存在同名的节点
        int num = orgService.isHasSameName(org);
        if (num > 0) {
            PageResult.fail("同一父节点下机构或部门名称不允许重复，请更正!");
            return false;
        }

        //创建人id
        String cjrId = TokenUtils.getTokenUserId();
        org.setCjr_id(cjrId);
        //创建时间
        org.setCjsj(DateUtils.getDate());
        //有效标志
        org.setYxbz("1");

        /*获取序号*/
        String sort = orgService.getSort(org.getPid());
        org.setSort(sort);

        /*设置菜单路径 path+id，根节点默认为自己*/
        String path = path = org.getPath() + "/" + org.getId();
        org.setPath(path);

        /*部门标志为 0 时代表机构，机构类别后台自动生成*/
        if ("0".equals(org.getIs_dept())) {
            /*根节点*/
            if ("0".equals(org.getPid())) {
                org.setService_type("01");

                /*非根节点*/
            } else {
                //父机构的机构类型
                String parentServiceType = org.getService_type();
                //子机构的机构类别 = 父机构的机构ID+1
                if (!parentServiceType.isEmpty()) {
                    int ServiceType = Integer.parseInt(parentServiceType) + 1;
                    org.setService_type("0" + ServiceType);
                }

            }

        }
        PageResult.success("添加成功！");
        int result = orgService.addOrg(org);
        /**
         * 如果存在数据域则进行数据域表数据存储
         */
        if (org.getDataDomain()!=null && !"".equals(org.getDataDomain())){
            //存在数据域则进行数据域的存储
            Domain domain = new Domain();
            domain.setId(SeqUtil.getId());
            domain.setYxbz("1");
            domain.setDomain_code(org.getId());
            domain.setDomain_name(org.getName());
            domain.setDomain_id(org.getDataDomain());
            domainService.insertDomain(domain);
            try{
                dbInit.initDomain(org.getDataDomain());
            }catch (Exception e){
                logger.error("初始化数据域失败",e);
            }

        }
        return result > 0;
    }

    /**
     * 删除组织机构
     *
     * @param id 机构ID
     * @return PageResult
     */
    @RequestMapping(value = "/deleteOrg", method = RequestMethod.POST)
    public boolean deleteOrg(String id) {
        //校验是否存在下级单位
        boolean isHasChildOrg = orgService.isHasChildOrg(id);
        //校验是否存在部门
        boolean isHasDept = orgService.isHasDept(id);
        //校验部门下是否存在用户
        boolean isHasSysUser = orgService.isHasSysUser(id);
        if (isHasChildOrg || isHasDept) {
            PageResult.fail("存在下级组织机构，不允许删除!");
            return false;
        }
        if (isHasSysUser) {
            PageResult.fail("部门下存在操作人员，不允许删除!");
            return false;
        }
        int result = orgService.blockUpOrg(id);
        domainService.deleteByDomainCode(id);
        PageResult.success("删除成功");
        return result > 0;
    }

    /**
     * 修改组织机构
     *
     * @param org     组织机构
     * @param request
     * @return PageResult
     */
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public boolean updateOrg(Org org, HttpServletRequest request) {
        if (org.getDataDomain()!=null && !"".equals(org.getDataDomain())){
            int dataDomainNum = orgService.checkDataDomain(org.getDataDomain(),org.getId());
            if (dataDomainNum>0){
                PageResult.fail("存在相同数据域，请修改数据域后再进行修改!");
                return false;
            }
        }
        //根据上级节点验证是否存在同名的节点
        int num = orgService.isHasSameName(org);
        if (num > 0) {
            PageResult.fail("同一父节点下机构或部门名称不允许重复，请更正!");
            return false;
        }
        /*修改人id*/
        String xgrId = TokenUtils.getTokenUserId();
        org.setXgr_id(xgrId);
        /*修改时间*/
        org.setXgsj(DateUtils.getDate());
        int result = orgService.updateOrg(org);
        if (org.getId()!=null && "sysadmin".equals(TokenUtils.getTokenDldm())){
            domainService.updateDomainByOrg(org);
            PageResult.success("修改成功!");
        }
        PageResult.fail("修改机构对应数据域需联系【超级管理员】!");
        return result > 0;
    }

    /**
     * 查询组织机构列表
     * 组织机构管理功能
     *
     * @param id 机构ID
     * @return PageResult
     */
    @RequestMapping(value = "/findOrgList", method = RequestMethod.POST)
    public Map<String, Object> findOrgList(String id, HttpServletRequest request) {
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
            List<Org> list = orgService.findOrgList(id);
            /*输出构建好的组织机构树*/
            orgMap.put("success", "true");
            orgMap.put("list", list);
        } catch (Exception e) {
            logger.error("查询组织机构异常(/findOrgList):" + e.getMessage());
            PageResult.fail("查询异常");
            return null;

        }
        return orgMap;
    }
    /**
     * 查询组织机构列表
     * 组织机构管理功能
     *
     * @param id 机构ID
     * @return PageResult
     */
    @RequestMapping(value = "/findOrgAndUserList", method = RequestMethod.POST)
    public Map<String, Object> findOrgAndUserList(String id, HttpServletRequest request) {
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
            List<Org> list = orgService.findOrgAndUserList(id);
            /*输出构建好的组织机构树*/
            orgMap.put("success", "true");
            orgMap.put("list", list);
        } catch (Exception e) {
            logger.error("查询组织机构异常(/findOrgList):" + e.getMessage());
            PageResult.fail("查询异常");
            return null;

        }
        return orgMap;
    }


    /**
     * 根据登录人员所在机构获取组织机构列表(不包含部门)
     * 业务功能使用，根据登录人员单位编号获取组织机构列表
     *
     * @param id 机构ID
     * @return PageResult
     */
    @RequestMapping(value = "/findOrgListBusi", method = RequestMethod.POST)
    public Map<String, Object> findOrgListForBusi(String id, HttpServletRequest request) {
        if (StringUtils.isEmpty(id)) {
            /**
             * 获取登录人员机构ID
             */
            id = TokenUtils.getTokenOrgId();
        }

        Map<String, Object> orgMap = new HashMap<String, Object>();
        try {
            List<Org> list = orgService.findOrgListForBusi(id);
            /*输出构建好的组织机构树*/
            orgMap.put("success", "true");
            orgMap.put("list", list);
        } catch (Exception e) {
            logger.error("业务功能查询组织机构异常（findOrgListBusi）：" + e.getMessage());
            PageResult.fail("查询异常");
            return null;

        }
        return orgMap;

    }

    /**
     * 查询下级组织机构列表
     *
     * @param id 机构ID
     * @return PageResult
     */
    @RequestMapping(value = "/findNextList", method = RequestMethod.POST)
    public List<Org> findNextList(String id) {
        List<Org> list = null;
        try {
            list = orgService.findNext(id);

        } catch (Exception e) {
            logger.error("查询下级组织机构异常：(findNextList)" + e.getMessage());
            PageResult.fail("查询下级异常!");
            return null;
        }
        return list;
    }

    /**
     * 查询单个组织机构信息
     *
     * @param id 机构ID
     * @return Org
     */
    @RequestMapping(value = "/findOrg", method = RequestMethod.POST)
    public Org findOrg(String id) {
        Org org = orgService.findOrg(id);
        return org;
    }

    /**
     * 根据组织机构ID查询部门标志
     *
     * @param id 机构ID
     * @return PageResult
     */
    @RequestMapping(value = "/findIsDept", method = RequestMethod.POST)
    public boolean findIsDept(String id) {
        int is_dept = orgService.findIsDept(id);
        if (is_dept == 1) {
            //部门
            PageResult.success("1");
            return true;
        } else {
            //0代表机构
            PageResult.success("0");
            return true;
        }
    }
    
    @Resource
    IomCampOrgMapper iomCampOrgMapper;
    
    @GetMapping("/org/selectByType")
    public Object selectByType(String service_type) {
    	IomCampOrgExample iomCampOrgExample = new IomCampOrgExample();
    	com.integration.generator.entity.IomCampOrgExample.Criteria criteria = iomCampOrgExample.createCriteria();
    	criteria.andYxbzEqualTo(1);
    	criteria.andServiceTypeEqualTo(service_type);
    	return iomCampOrgMapper.selectByExample(iomCampOrgExample);
	}
    
    @GetMapping("/org/selectById")
    public Object selectById(String id) {
    	return iomCampOrgMapper.selectByPrimaryKey(id);
	}
    
}
