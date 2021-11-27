

package com.integration.entity;

/**
 * @author hbr
 * @version 1.0
 * @date 2019-01-08 10:34:05
 */

public class IomCampLoginInfo {


    /**
     * 主键
     */
    private String id;


    /**
     * 用户ID
     */
    private Long user_id;


    /**
     * 用户登录名
     */
    private String czry_dldm;


    /**
     * 用户名
     */
    private String czry_mc;


    /**
     * sessionID
     */
    private String session_id;


    /**
     * 登录时间
     */
    private String login_time;
    /**
     * 部门ID
     */
    private String dept_id;
    /**
     * 机构ID
     */
    private String org_id;
    /**
     * 退出时间
     */
    private String logout_time;


    /**
     * 创建时间
     */
    private String cjsj;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }


    public String getCzry_dldm() {
        return czry_dldm;
    }

    public void setCzry_dldm(String czry_dldm) {
        this.czry_dldm = czry_dldm;
    }


    public String getCzry_mc() {
        return czry_mc;
    }

    public void setCzry_mc(String czry_mc) {
        this.czry_mc = czry_mc;
    }


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }


    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }


    public String getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.logout_time = logout_time;
    }


    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }
}