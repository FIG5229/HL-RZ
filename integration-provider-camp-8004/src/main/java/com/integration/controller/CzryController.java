package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.cnsugar.ai.face.FaceHelper;
import com.google.common.base.Joiner;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.config.Audience;
import com.integration.dao.RoleDomainDao;
import com.integration.entity.*;
import com.integration.generator.dao.IomCampLoginMapper;
import com.integration.generator.entity.IomCampCzry;
import com.integration.generator.entity.IomCampLoginExample;
import com.integration.service.*;
import com.integration.utils.*;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
/**
* @Package: com.integration.controller
* @ClassName: CzryController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 操作人管理及登录等
*/
@RestController
public class CzryController {

    @Autowired
    private CzryService czryService;

    @Autowired
    private CzryRoleService czryRoleService;

    @Autowired
    private Audience audience;

    @Autowired
    private IomCampLoginService loginService;

    @Autowired
    private FaceService faceService;

    @Autowired
    private OrgService orgService;

    @Resource
    private DomainService domainService;

    @Resource
    private IomCampLoginMapper iomCampLoginMapper;

    @Resource
    private RoleDomainDao roleDomainDao;


    private final static Logger logger = LoggerFactory
            .getLogger(CzryController.class);

    @RequestMapping(value = "/findtest1", method = RequestMethod.GET)
    public void test1() throws IOException{
        String img1 = "D:\\1\\12.png";
        String img2 = "D:\\1\\13.png";
        float a = FaceHelper.compare(new File(img1), new File(img2));
    }

    @RequestMapping(value = "/findtest2", method = RequestMethod.POST)
    public void test2(@RequestBody Map map) throws IOException{
    	boolean img1 = Base64ImageUtils.Base64ToImage(map.get("base").toString(),"D:\\1\\14.png");
    	float a = FaceHelper.compare(new File("D:\\1\\14.png"), new File("D:\\1\\12.png"));
    }

    @RequestMapping(value = "/faceId", method = RequestMethod.POST)
    public Map<String,Object> duibi(@RequestBody Map m) throws Exception{
    	List<Face> faceList = faceService.findFaceList();
    	String d = String.valueOf(System.currentTimeMillis());
    	//拍照生成的图片，用于与数据库图片做比对
    	Base64ImageUtils.Base64ToImage(m.get("base").toString().substring(22),ProjectPath.RootPath(d+".png"));
    	Map<String,Object> map = new HashMap<String,Object>();
        boolean flag = false;
        String msg = "";

        File img1 = new File(ProjectPath.RootPath(d+".png"));
    	for (int i = 0; i < faceList.size(); i++) {

    		List<String> imgList = new ArrayList<String>();
    		imgList.add(faceList.get(i).getImg_main().toString().substring(22));
    		imgList.add(faceList.get(i).getImg_up().toString().substring(22));
    		imgList.add(faceList.get(i).getImg_down().toString().substring(22));
    		imgList.add(faceList.get(i).getImg_left().toString().substring(22));
    		imgList.add(faceList.get(i).getImg_right().toString().substring(22));

    		for (int j = 0; j < imgList.size(); j++) {
        		//将数据库中存的图片编码转换成图片，用于比对
        		Base64ImageUtils.Base64ToImage(imgList.get(j).toString(),ProjectPath.RootPath(d+j+i+".png"));

        		File img2 = new File(ProjectPath.RootPath(d+j+i+".png"));
        		float aa = FaceHelper.compare(img1,img2);

    			if(FaceHelper.compare(img1,img2) > 0.8 ){

    				Czry czry = czryService.findCzryById(faceList.get(i).getCzry_id().toString());
    		        if (czry != null) {
    		            if (czry.getStatus() == 0) {
    		                msg = "人员状态已停用!";
    		                flag = false;
    		                map.put("userId", "");
    		                map.put("token", "");
    		                map.put("userName", "");
    		                map.put("czry_dldm", "");
    		            } else if (czry.getLock_bz() == 1) {
    		                msg = "当前帐号已锁定!";
    		                flag = false;
    		                map.put("userId", "");
    		                map.put("token", "");
    		                map.put("userName", "");
    		                map.put("czry_dldm", "");
    		            } else {
                            //获取当前登录人配置的所有角色
                            List<String> roleDmList = czryService.getRoleIdByCzryId(czry.getId());
                            //查询数据所用数据域
                            String dataDomain =null;
                            //登录人所在机构数据域
                            String orgDomain =null;
                            //获取默认数据域
                            Domain domain = domainService.findDomainByDomainCode("default");
                            //获取登录人所在机构配置的数据域
                            Domain domains = domainService.findDomainByDomainCode(czry.getOrg_id());
                            if (roleDmList!=null && roleDmList.size()>0){
                                //根据登录人配置角色获取所有配置数据域
                                List<String> dataDomainList = roleDomainDao.getRoleDomainByRoleDmList(roleDmList);
                                if (dataDomainList!=null && dataDomainList.size()>0){
                                    for (String str:dataDomainList) {
                                        dataDomain +=str;
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
                            czry.setDomainId(domainId);
                            czry.setDataDomain(dataDomain);
                            czry.setOrgDomain(orgDomain);
    		            	msg = "欢迎"+czry.getCzry_mc()+"登录系统!!";
    		                String jwtToken = JwtHelper.createJWT(Long.parseLong(czry.getId()),czry.getCzry_mc(),
    		                        czry.getCzry_dldm(), czry.getDept_id(),czry.getOrg_id(),czry.getDomainId(),audience.getClientId(),
    		                        audience.getName(), audience.getExpiresSecond() * 1000,
    		                        audience.getBase64Secret(),czry.getDataDomain(),czry.getOrgDomain());

    		                String ticket = JwtHelper.createJWT(Long.parseLong(czry.getId()),czry.getCzry_mc(),
    		                        "ticket", czry.getDept_id(),czry.getOrg_id(),czry.getDomainId(),audience.getClientId(),
    		                        audience.getName(), audience.getExpiresSecond() * 1000,
    		                        audience.getBase64Secret(),czry.getDataDomain(),czry.getOrgDomain());
                            String ticketKey = new StringBuilder().append("ticket").append(czry.getId()).
                                    append("-").append(ticket).toString();
    		                RedisUtils.set(ticketKey, ticket);
    		                map.put("ticket", ticket);

                            String tokenKey = new StringBuilder().append(audience.getPrefix()).append(czry.getId()).
                                    append("-").append(jwtToken).toString();
                            RedisUtils.set(tokenKey, jwtToken);
    		                flag = true;
    		                map.put("userId", String.valueOf(czry.getId()));
    		                map.put("token", jwtToken);
    		                map.put("userName", czry.getCzry_mc());
    		                map.put("czry_dldm", czry.getCzry_dldm());
    		                // 添加登录日志
    		                IomCampLoginInfo loginInfo = new IomCampLoginInfo();
    		                // id
    		                loginInfo.setId(SeqUtil.nextId() +"");
    		                // 用户id
    		                loginInfo.setUser_id(Long.parseLong(czry.getId()));
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
    		            }

    		        }
    		        //删除从库里生成的本地图片
    		        img2.delete();
    		        img1.delete();
    		        PageResult.setMessage(msg,true);
    		        return map;
    			}
    	        //删除从库里生成的本地图片
    	        img2.delete();
			}
		}
    	img1.delete();
    	PageResult.setMessage("人脸库没有匹配!!",true);
    	return map;

    }

    /**
     * 分页查询用户以及用户角色
     *
     * @param search
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/findCzryList", method = RequestMethod.GET)
    public PageResult findCzryList(@RequestParam(required = false) String id, @RequestParam(required = false) String is_dept, @RequestParam(required = true) String search, @RequestParam(required = true) String limit, @RequestParam(required = true) String offset,
                                   HttpServletRequest request) {
        String dept_id ="";
        String org_id ="";
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(is_dept) ) {

            if("0".equals(is_dept)){
                org_id = id;
            }else{
                dept_id = id;
            }
        }
        // 总数
        Integer totalRecord = 0;
        List<Czry> czrylist = new ArrayList<Czry>();
        //如果org_id是1，则是超级管理员则做处理
        if("1".equals(id)) {
        	org_id=null;
        }
        czrylist = czryService.findCzryList(org_id,dept_id,search,
        Integer.parseInt(limit), Integer.parseInt(offset));
        totalRecord = czryService.findCzryListCount(org_id,dept_id,search);
        return DataUtils.returnPr(totalRecord, czrylist);
    }

    /**
     * 根据id查询用户以及用户角色
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/findCzryById", method = RequestMethod.GET)
    public Map findCzryById(String id, HttpServletRequest request) {
        Czry czry = new Czry();
        List<CzryRole> crList = new ArrayList<CzryRole>();
        czry = czryService.findCzryById(id);
        if(czry != null){
            czry.setCzry_pass(null);
            if(StringUtils.isNotEmpty(czry.getDept_id())){
                Org org = orgService.findOrg(czry.getDept_id());
                if(org != null){
                    czry.setZzjg_mc(org.getName());
                }
            }
        }
        crList = czryRoleService.findCzryRoleByCzryId(id);
        Map map = new HashMap();
        map.put("czry", czry);
        map.put("crList", crList);
        return map;
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @param
     * @return
     */
    @RequestMapping(value = "/findCzryByIdFeign", method = RequestMethod.POST)
    public Czry findCzryByIdFeign(@RequestParam String id) {
        Czry czry = czryService.findCzryById(id);
        return czry;
    }

    private final static String  DEFAULT_PASS_WORD = "@123";

    /**
     * 添加用户同时添加角色权限
     *
     * @param czry
     * @return
     */
    @OptionLog(desc="添加用户同时添加角色权限", module="用户模块", writeParam=true, writeResult=true)
    @RequestMapping(value = "/addCzry", method = RequestMethod.POST)
    public Object addCzry(Czry czry, HttpServletRequest request,
                              String[] roleIds) {
    	String msg = czryService.checkCzry(czry);
    	if (StringUtils.isNotEmpty(msg)) {
    		PageResult.setMessage(msg,false);
    		return false;
		}

    	String cjrId = TokenUtils.getTokenUserId();
        /*根据部门ID获取机构ID*/
        czry.setOrg_id(orgService.findOrgByDept(czry.getDept_id()) );
        czry.setCzry_pass(MD5Utils.encryptPassword(czry.getCzry_dldm() +
                czry.getCzry_dldm() + DEFAULT_PASS_WORD, "UTF-8"));
        czry.setCjr_id(cjrId);
        czry.setCjsj(DateUtils.getDate());
        czry.setYxbz("1");
        czry.setId(SeqUtil.nextId().toString());
        czry.setStatus(1);
        czry.setLock_bz(0);
        //根据部门ID得到机构ID
        int result = czryService.addCzry(czry, roleIds, cjrId);
        PageResult.setMessage("添加成功!",true);
        return result;
    }



    /**
     * 删除用户同时删除角色
     *
     * @param id
     * @return
     */
    @OptionLog(desc="删除用户同时删除角色", module="用户模块", writeParam=true, writeResult=true)
    @RequestMapping(value = "delCzry", method = RequestMethod.POST)
    public Object delCzry(String id, HttpServletRequest request) {
         PageResult.setMessage("删除成功!",true);
         return czryService.deleteCzry(id);
    }

    /**
     * 更新用户同时更新用户角色
     *
     * @param czry
     * @return
     */
    @OptionLog(desc="更新用户同时更新用户角色", module="用户模块", writeParam=true, writeResult=true)
    @RequestMapping(value = "/updCzry", method = RequestMethod.POST)
    public Object updCzry(Czry czry, HttpServletRequest request,
                              @RequestParam String[] roleIds) {
    	String msg = czryService.checkCzry(czry);
    	if (StringUtils.isNotEmpty(msg)) {
    		return false;
		}
    	String cjrId = TokenUtils.getTokenUserId();
        czry.setXgr_id(cjrId);
        czry.setXgsj(DateUtils.getDate());
        /*用户调整部门时同步修改机构ID*/
        if(StringUtils.isNotEmpty(czry.getDept_id())){
            /*根据部门ID获取机构ID*/
            czry.setOrg_id(orgService.findOrgByDept(czry.getDept_id()) );
        }
            PageResult.setMessage("更新成功!",true);
            return czryService.updateCzry(czry, roleIds, cjrId);
    }

    /**
     * 重置密码
     *
     * @param czry
     * @return
     */
    @OptionLog(desc="重置密码", module="用户模块", writeParam=false, writeResult=false)
    @RequestMapping(value = "/resetPWDCzry", method = RequestMethod.POST)
    public Object resetPWDCzry(Czry czry, HttpServletRequest request) {
            String cjrId = TokenUtils.getTokenUserId();
            Czry c = czryService.findCzryById(czry.getId());
            if (c == null) {
                 //DataUtils.returnPr(false, "此用户不存在!操作失败!");
            	PageResult.setMessage("此用户不存在!操作失败!",false);
            	return 0;
            }
            czry.setXgr_id(cjrId);
            czry.setXgsj(DateUtils.getDate());
            czry.setCzry_pass(MD5Utils.encryptPassword(c.getCzry_dldm() +
                    c.getCzry_dldm() + DEFAULT_PASS_WORD, "UTF-8"));
            PageResult.setMessage("重置密码成功!",true);
            return czryService.resetPWDCzry(czry);
    }

    /**
     * 验证原始密码
     *
     * @param czry
     * @return
     */
    @RequestMapping(value = "/validateOldPWD", method = RequestMethod.POST)
    public Object validateOldPWD(Czry czry, String oldPWD,
                                     HttpServletRequest request) {
            if (czry == null || StringUtils.isEmpty(czry.getCzry_dldm())) {
            	PageResult.setMessage("登录代码为空，操作失败!",false);
                return false;
            }
            String oldMD5 = MD5Utils.encryptPassword(czry.getCzry_dldm()
                    + oldPWD, "UTF-8");

            Czry c = czryService.findCzryById(czry.getId());
            if (c != null) {
                if (c.getCzry_pass().equals(oldMD5)) {
                	PageResult.setMessage("原始密码输入正确!",true);
                    return true;
                } else {
                	PageResult.setMessage("原始密码输入错误!",false);
                    return false;
                }
            } else {
            	PageResult.setMessage("用户不存在!",false);
                return false;
            }
    }

    /**
     * 修改密码
     *
     * @param czry
     * @return
     */
    @OptionLog(desc="修改密码", module="用户模块", writeParam=false, writeResult=false)
    @RequestMapping(value = "/updatePWDCzry", method = RequestMethod.POST)
    public Object updatePWDCzry(Czry czry, String newPWD,
                                    HttpServletRequest request) {
            String cjrId = TokenUtils.getTokenUserId();
            czry.setId(cjrId);
            czry.setXgr_id(cjrId);
            czry.setXgsj(DateUtils.getDate());
            if (czry == null || StringUtils.isEmpty(czry.getCzry_dldm())) {
            	PageResult.setMessage("登录代码为空，操作失败!",false);
            	return false;
            }
            czry.setCzry_pass(MD5Utils.encryptPassword(czry.getCzry_dldm()
                    + newPWD, "UTF-8"));
            PageResult.setMessage("修改密码成功!",true);
            return czryService.resetPWDCzry(czry);
    }

    /**
     * 登出
     *
     * @param id 登录用户id
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public PageResult delRedis(String id) {
        //获取登录Id
        String logoutKey = new StringBuilder().append("logout-").append(id).
                append("-").append(TokenUtils.getToken()).toString();

        String loginId = (String) RedisUtils.get(logoutKey);
        //添加登出日志
        try {
            IomCampLoginInfo info = new IomCampLoginInfo();
            info.setLogout_time(DateUtils.getDate());
            info.setId(loginId);
            loginService.updateInfo(info);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            //清空登录用户id
            String tokenKey = new StringBuilder().append(audience.getPrefix()).append(id).
                    append("-").append(TokenUtils.getToken()).toString();
            if (RedisUtils.exists(tokenKey)) {
                RedisUtils.remove(tokenKey);
            }
            //清空登录日志ID
            if (RedisUtils.exists(logoutKey)) {
                RedisUtils.remove(logoutKey);
            }
        }


        return DataUtils.returnPr(true);
    }

    /**
     * 登录
     *
     * @param aesCode aes加密后信息
     * @return
     */
    @RequestMapping(value = "/loginByAesCode", method = RequestMethod.POST)
    public PageResult loginByAesCode(String aesCode){
        if(StringUtils.isEmpty(aesCode)){
            return DataUtils.returnPr(false, "参数错误！");
        }
        JSONObject o = JSONObject.parseObject(AESUtil.deCode(aesCode));
        String czrDldm = o.getString("czrDldm");
        String czryPass = o.getString("czryPass");
        PageResult pg = login(czrDldm, czryPass);
        pg.setReturnObject(AESUtil.enCode(JSONObject.toJSONString(pg.getReturnObject())));
        return pg;
    }


    /**
     * 登录
     *
     * @param czrDldm
     * @param czryPass
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public PageResult login(String czrDldm, String czryPass) {
        // 此账号是停用还是启用
        // 此账号是否锁定
        boolean flag = false;
        String msg = "";
        String a = MD5Utils.encryptPassword(czrDldm + czryPass, "UTF-8");
        Czry czry = czryService.login(czrDldm, a);
        Map<String, String> map = new HashMap<String, String>();
        if (czry != null) {
            if (czry.getStatus() == 0) {
                msg = "人员状态已停用!";
                flag = false;
                map.put("userId", "");
                map.put("token", "");
                map.put("userName", "");
                map.put("czry_dldm", "");
                map.put("dept_id","");
                map.put("org_id","");
            } else if (czry.getLock_bz() == 1) {
                msg = "当前帐号已锁定!";
                flag = false;
                map.put("userId", "");
                map.put("token", "");
                map.put("userName", "");
                map.put("czry_dldm", "");
                map.put("dept_id","");
                map.put("org_id","");
            } else {
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
                        audience.getBase64Secret(),czry.getDataDomain(),czry.getOrgDomain());

                String ticket = JwtHelper.createJWT(Long.valueOf(czry.getId()),czry.getCzry_mc(),
                        "ticket", czry.getDept_id(),czry.getOrg_id(),czry.getDomainId(),audience.getClientId(),
                        audience.getName(), audience.getExpiresSecond() * 1000,
                        audience.getBase64Secret(),czry.getDataDomain(),czry.getOrgDomain());
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
                flag = true;
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
            }
        } else {
            msg = "用户名或密码错误!";
            flag = false;
            map.put("userId", "");
            map.put("token", "");
            map.put("userName", "");
            map.put("czry_dldm", "");
        }
        return DataUtils.returnPr(flag, msg, map, 0);
    }

    /**
     * 查询日志
     * @param czryDldm 登录名
     * @param czryMc 用户名
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GetMapping("/findLog")
    public Object findLog(String czryDldm,String czryMc,String startTime, String endTime) {
        IomCampLoginExample iomCampLoginExample = new IomCampLoginExample();
        com.integration.generator.entity.IomCampLoginExample.Criteria criteria = iomCampLoginExample.createCriteria();
        if (StringUtils.isNotEmpty(czryDldm)) {
            criteria.andCzryDldmLike("%"+czryDldm+"%");
        }

        if (StringUtils.isNotEmpty(czryMc)) {
        	criteria.andCzryMcLike("%"+czryMc+"%");
		}

        if (StringUtils.isNotEmpty(startTime)) {
        	criteria.andLoginTimeGreaterThanOrEqualTo(DateUtils.getDate(startTime + " 00:00:00"));
		}

        if (StringUtils.isNotEmpty(endTime)) {
        	criteria.andLoginTimeLessThanOrEqualTo(DateUtils.getDate(endTime + " 23:59:59"));
		}
        //按登录时间降序排列
        iomCampLoginExample.setOrderByClause("login_time desc");
        MyPagUtile.startPage();
        return MyPagUtile.getPageResult(iomCampLoginMapper.selectByExample(iomCampLoginExample));
    }

    /**
     * 通过id串查询用户名
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/findCzryByIds", method = RequestMethod.POST)
    public List<IomCampCzry> findCzryByIds(String[] ids){
        return czryService.findCzryByIds(Arrays.asList(ids));
    }

    /**
     * 通过id串查询用户名 包含已删除用户
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/findCzryByIdsWithDelete", method = RequestMethod.POST)
    public List<IomCampCzry> findCzryByIdsWithDelete(String[] ids){
        return czryService.findCzryByIdsWithDelete(Arrays.asList(ids));
    }

    /**
     * 查询操作人员
     * @param nameOrId
     * @return
     */
    @RequestMapping(value = "/getCzrysAll", method = RequestMethod.POST)
    public List<Map<String, Object>> getCzrysAll(String nameOrId){
        return czryService.getCzrysAll(nameOrId);
    }

    /**
     * 根据操作人id，查询操作人
     * @param listStr
     * @return
     */
    @RequestMapping(value = "/getCzrysNameById", method = RequestMethod.POST)
    public List<Map<String, Object>> getCzrysNameById(String[] listStr){
        return czryService.getCzrysNameById(listStr);
    }

}
