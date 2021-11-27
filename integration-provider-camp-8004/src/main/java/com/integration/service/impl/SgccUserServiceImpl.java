package com.integration.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.integration.config.Audience;
import com.integration.dao.OrgDao;
import com.integration.dao.RoleDomainDao;
import com.integration.dao.SgccUserCzryMapper;
import com.integration.dao.SgccUserMapper;
import com.integration.entity.*;
import com.integration.service.*;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName SgccUserServiceImpl
 * @Description 国网操作服务
 * @Author zhangfeng
 * @Date 2021/9/6 16:53
 * @Version 1.0
 **/
@Service
public class SgccUserServiceImpl implements SgccUserService {

    @Resource
    private SgccUserMapper sgccUserMapper;

    @Resource
    private SgccUserCzryMapper sgccUserCzryMapper;

    @Autowired
    private CzryService czryService;

    @Resource
    private DomainService domainService;
    @Resource
    private RoleDomainDao roleDomainDao;

    @Autowired
    private Audience audience;

    @Autowired
    private IomCampLoginService loginService;

    @Autowired
    private OrgService orgService;
    @Autowired
    private OrgDao orgDao;

    private final static String  DEFAULT_PASS_WORD = "@123";


    @Override
    public PageResult deleteByPrimaryKey(String userId) {
        if(StringUtils.isEmpty(userId)){
            return DataUtils.returnPr(false, "删除用户失败，参数错误！");
        }
        int num = sgccUserMapper.deleteByPrimaryKey(userId);
        if(num > 0){
            return DataUtils.returnPr(true, "删除用户成功！");
        }
        return DataUtils.returnPr(false, "删除用户失败！");
    }

    @Override
    public PageResult insert(SgccUser record) {

        record.setCjsj(new Date());
        record.setYxbz(1);

        int num = sgccUserMapper.insert(record);
        if(num > 0){
            return DataUtils.returnPr(true, "新增用户成功！");
        }
        return DataUtils.returnPr(false, "新增用户失败！");
    }

    @Override
    public PageResult selectByPrimaryKey(String userId) {
        if(StringUtils.isEmpty(userId)){
            return DataUtils.returnPr(false, "查询用户信息失败，参数错误！");
        }
        SgccUser sgccUser = sgccUserMapper.selectByPrimaryKey(userId);

        return DataUtils.returnPr(sgccUser);
    }

    @Override
    public PageResult updateByPrimaryKey(SgccUser record) {

        int num = sgccUserMapper.updateByPrimaryKey(record);
        if(num > 0){
            DataUtils.returnPr(true, "修改成功！");
        }
        return DataUtils.returnPr(false, "修改失败！");
    }

    @Override
    public PageResult listPage(SgccUser record, Integer pageSize, Integer pageNum) {

        if(pageSize == null || pageSize < 1 || pageSize > 100){
            pageSize = 10;
        }
        if(pageNum == null || pageNum < 1 ){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SgccUser> list = sgccUserMapper.selectList(record);
        PageInfo pageInfo = new PageInfo(list);
        PageResult pg = DataUtils.returnPr(list);
        pg.setTotalResult(Long.valueOf(pageInfo.getTotal()).intValue());
        pg.setTotalPage(pageInfo.getPages());
        return pg;
    }

    @Override
    public PageResult loginByUserInfo(SgccUser record) {
        PageResult pg = validate4Add(record);
        if(!pg.isReturnBoolean()){
            return pg;
        }
        //查询判断是否已经添加用户
        SgccUser sgccUser = sgccUserMapper.selectByPrimaryKey(record.getUserId());
        if(sgccUser != null && StringUtils.isNotEmpty(sgccUser.getUserId())){
            //存在
            SgccUserCzry sgccUserCzry = new SgccUserCzry();
            sgccUserCzry.setSgccUserId(record.getUserId());
            List<SgccUserCzry> sgccUserCzryList = sgccUserCzryMapper.selectList(sgccUserCzry);
            if(sgccUserCzryList != null && sgccUserCzryList.size() > 0){
                sgccUserCzry = sgccUserCzryList.get(0);
                Czry czry = czryService.findCzryById(sgccUserCzry.getCzryId());
                if (czry.getStatus() == 0) {
                    return DataUtils.returnPr(false, "一体化帐号已停用!");
                } else if (czry.getLock_bz() == 1) {
                    return DataUtils.returnPr(false, "一体化帐号已锁定!");
                }
                //创建token
                return buildTokenResult(czry);
            }else{
                return DataUtils.returnPr(false, "已有同步用户，但是没有关联一体化账号！");
            }
        }else{
            //不存在

            //插入一体化用户
            Czry czry = new Czry();
            czry.setCzry_dldm(record.getAccount());
            czry.setCzry_mc(record.getName());
            pg = addIomUser(czry);
            if(!pg.isReturnBoolean()){
                return pg;
            }

            //插入国网用户

            insert(record);

            SgccUserCzry sgccUserCzry = new SgccUserCzry();
            sgccUserCzry.setId(SeqUtil.getId());
            sgccUserCzry.setSgccUserId(record.getUserId());
            sgccUserCzry.setCzryId(czry.getId());
            sgccUserCzry.setCjsj(new Date());

            //插入关系
            sgccUserCzryMapper.insert(sgccUserCzry);

            return buildTokenResult(czry);
        }

    }

    private PageResult addIomUser(Czry czry){
        /*根据部门ID获取机构ID*/
        List<Org> list = orgDao.findOrgList("");
        if(list == null || list.size() == 0){
            return DataUtils.returnPr(false, "一体化没有部门！");
        }
        Optional<Org> orgOptional = list.stream().filter(org-> StringUtils.endsWithIgnoreCase("1", org.getIs_dept())).findFirst();
        if(!orgOptional.isPresent()){
            return DataUtils.returnPr(false, "一体化没有部门！");
        }

        checkAndSetUserName(czry);

        czry.setDept_id(orgOptional.get().getId());
        czry.setOrg_id(orgService.findOrgByDept(orgOptional.get().getId()));
        czry.setCzry_pass(MD5Utils.encryptPassword(czry.getCzry_dldm() +
                czry.getCzry_dldm() + DEFAULT_PASS_WORD, "UTF-8"));
        czry.setCjsj(DateUtils.getDate());
        czry.setYxbz("1");
        czry.setStatus(1);
        czry.setLock_bz(0);
        //根据部门ID得到机构ID
        //手机端角色
        String [] roleIds = {"1435063944535207936"};
        czryService.addCzry(czry, roleIds, "72904780934168577");
        return DataUtils.returnPr(true, "添加成功！");
    }


    private void checkAndSetUserName(Czry czry){
        String result = czryService.checkCzry(czry);
        while (StringUtils.isNotEmpty(result)){
            czry.setCzry_dldm(czry.getCzry_dldm()+"1");
            result = czryService.checkCzry(czry);
        }
    }


    private PageResult validate4Add(SgccUser record){

        if(record == null){
            return DataUtils.returnPr(false, "参数错误！");
        }
        if(StringUtils.isEmpty(record.getUserId())){
            return DataUtils.returnPr(false, "参数错误,用户id不能为空！");
        }
        return DataUtils.returnPr(true);
    }

    private PageResult buildTokenResult(Czry czry){
        //获取当前登录人配置的所有角色
        List<String> roleDmList = czryService.getRoleIdByCzryId(czry.getId());
        //查询数据所用数据域
        String dataDomain ="";
        //登录人所在机构数据域
        String orgDomain =null;
        //获取默认数据域
        Domain domain = domainService.findDomainByDomainCode("default");
        //获取登录人所在机构配置的数据域
        Domain domains = domainService.findDomainByDomainCode(czry.getOrg_id());

        Map<String, String> map = new HashMap<String, String>();

        if (roleDmList!=null && roleDmList.size()>0){
            //根据登录人配置角色获取所有配置数据域(获取)
            List<String> dataDomainLists = roleDomainDao.getRoleDomainByRoleDmList(roleDmList);
            if (dataDomainLists!=null && dataDomainLists.size()>0){
                List<String> list = new ArrayList<>();
                for (String str:dataDomainLists) {
                    list.addAll(Arrays.asList(str.split(",")));
                }
                List<String> dataDomainList = domainService.getRoleDomainList(list);
                if (dataDomainList!=null && dataDomainList.size()>0){
                    dataDomain = Joiner.on(",").join(dataDomainList);
                }else{
                    if (domains != null){
                        dataDomain = domains.getDomain_id();
                    }else {
                        if (domain != null){
                            dataDomain = domain.getDomain_id();
                        }
                    }
                }
            }else{
                if (domains != null){
                    dataDomain = domains.getDomain_id();
                }else {
                    if (domain != null){
                        dataDomain = domain.getDomain_id();
                    }
                }
            }
        }else{
            if (domains != null){
                dataDomain = domains.getDomain_id();
            }else {
                if (domain != null){
                    dataDomain = domain.getDomain_id();
                }
            }
        }
        if (domains != null){
            orgDomain = domains.getDomain_id();
        }else {
            if (domain != null){
                orgDomain = domain.getDomain_id();
            }
        }
        //默认数据域
        String domainId = null;
        if (domain != null){
            domainId = domain.getDomain_id();
        }
        if ("".equals(dataDomain)){
            dataDomain = null;
        }
        czry.setDomainId(domainId);
        czry.setDataDomain(dataDomain);
        czry.setOrgDomain(orgDomain);
        String jwtToken = JwtHelper.createJWT(Long.valueOf(czry.getId()),czry.getCzry_mc(),
                czry.getCzry_dldm(), czry.getDept_id(),czry.getOrg_id(),czry.getDomainId(), audience.getClientId(),
                audience.getName(), audience.getExpiresSecond() * 1000,
                audience.getBase64Secret(),czry.getDataDomain(),czry.getOrgDomain(), "1");

        String ticket = JwtHelper.createJWT(Long.valueOf(czry.getId()),czry.getCzry_mc(),
                "ticket", czry.getDept_id(),czry.getOrg_id(),czry.getDomainId(),audience.getClientId(),
                audience.getName(), audience.getExpiresSecond() * 1000,
                audience.getBase64Secret(),czry.getDataDomain(),czry.getOrgDomain(),"1");
        //增加账号登录端数限制
        if(TokenUtils.isFull(czry.getId())){
            //随机剔除一个
            TokenUtils.removeToken(czry.getId());
        }
        String ticketKey = new StringBuilder().append("ticket").append(czry.getId()).
                append("-").append(ticket).toString();
        RedisUtils.set(ticketKey, "");
        map.put("ticket", ticket);

        String tokenKey = new StringBuilder().append(audience.getPrefix()).append(czry.getId()).
                append("-").append(jwtToken).toString();

        //主要是要key
        RedisUtils.set(tokenKey, "");
        map.put("userId", String.valueOf(czry.getId()));
        map.put("token", jwtToken);
        map.put("userName", czry.getCzry_mc());
        map.put("czry_dldm", czry.getCzry_dldm());
        map.put("dept_id",czry.getDept_id());
        map.put("org_id",czry.getOrg_id());
        // 添加登录日志
        IomCampLoginInfo loginInfo = new IomCampLoginInfo();
        //日志id
        String loginId = SeqUtil.nextId()+"";
        // 设置日志id
        loginInfo.setId(loginId);
        // 用户id
        loginInfo.setUser_id(Long.valueOf(czry.getId()));
        // 用户登录名
        loginInfo.setCzry_dldm(czry.getCzry_dldm());
        // 用户名
        loginInfo.setCzry_mc(czry.getCzry_mc());
        loginInfo.setSession_id("1");
        // 登录时间
        String time = DateUtils.getDate();
        loginInfo.setLogin_time(time);
        // 创建时间
        loginInfo.setCjsj(time);
        loginService.insertInfo(loginInfo);
        //将LoginId保存到redis中，退出时根据LoginId更新退出时间
        String logoutKey = new StringBuilder().append("logout-").append(czry.getId()).
                append("-").append(jwtToken).toString();
        RedisUtils.set(logoutKey, loginId);

        return DataUtils.returnPr(map);
    }
}
