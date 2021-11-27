package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.PageResult;
import com.integration.entity.SgccUser;
import com.integration.service.SgccUserService;
import com.integration.sgcc.service.IscQueryInfo;
import com.integration.utils.DataUtils;
import com.integration.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName SgccController
 * @Description 国网用户
 * @Author zhangfeng
 * @Date 2021/9/6 17:06
 * @Version 1.0
 **/
@RequestMapping("sgcc")
@RestController
public class SgccController {

    @Autowired
    private SgccUserService sgccUserService;

    @Autowired
    private IscQueryInfo iscQueryInfo;

    /**
     * 分页列表，更换关联用户时使用
     * @param record
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping("listPage")
    public PageResult listPage(SgccUser record, Integer pageSize, Integer pageNum){
        return sgccUserService.listPage(record, pageSize, pageNum);
    }

    /**
     * 登录，
     * @return
     */
    @RequestMapping("loginByTicket")
    public PageResult login(String ticket){
        String result=null;
        try{
            result=iscQueryInfo.getUserInfoByTicket(ticket);
        }catch (Exception e){
            return DataUtils.returnPr(false, "获取爱国网用户信息失败");
        }
        JSONObject jo = JSONObject.parseObject(result);
        String code = jo.getString("code");
        if(StringUtils.equals("100001", code)){
            jo.put("loginTime",DateUtils.parseDate(new Date(jo.getIntValue("loginTime"))));
            SgccUser sgccUser = JSONObject.parseObject(jo.get("data").toString(), SgccUser.class);
            PageResult pg = login(sgccUser);
            if(pg.isReturnBoolean()){
                Map<String, String> map = (Map<String, String>) pg.getReturnObject();
                map.put("sgccUser", result);
            }
            return pg;
        }
        return DataUtils.returnPr(false, "获取爱国网用户信息失败！");
    }

    /**
     * 根据国网用户信息登录
     * @return
     */
    @RequestMapping("loginByUserInfo")
    public PageResult login(SgccUser sgccUser){

        return sgccUserService.loginByUserInfo(sgccUser);
    }

}
