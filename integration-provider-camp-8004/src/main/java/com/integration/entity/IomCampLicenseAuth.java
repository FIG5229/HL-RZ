package com.integration.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: IomCampLicenseAuth
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 模块授权
*/
public class IomCampLicenseAuth {
    private BigDecimal id;

    private String authCode;

    private String authVersion;

    private String version;

    private String clientCode;

    private String registerMan;

    private Date registerTime;

    private Date validDate;

    private Integer authUserNum;

    private Integer authCabinetNum;

    private Integer authDoorNum;

    private Integer authSceneNum;

    private Integer authViewNum;

    private Integer dayCount;

    private String isBlocked;

    private BigDecimal cjrId;

    private Date cjsj;

    private BigDecimal xgrId;

    private Date xgsj;

    private String authImg;

    private String cpuId;

    private String ip;

    private String mac;

    private String mainboard;

    private List<String> allowModule;

    private String allowModuleName;

    private String menuStr;

    public String getMenuStr() {
        return menuStr;
    }

    public void setMenuStr(String menuStr) {
        this.menuStr = menuStr;
    }
    /**
     * 模块
     */
    public enum module{
        /**
         * 基础管理
         */
        camp("基础管理", "camp", "/camp"),
        /**
         * 配置管理
         */
        cmdb("配置管理", "cmdb", "/cmdb"),
        /**
         * 事件可视化
         */
        alarm("事件可视化", "alarm", "/event"),
        /**
         * 性能可视化
         */
        pmv("性能可视化", "pmv", "/perf"),
        /**
         * 架构可视化
         */
        dmv("架构可视化", "dmv", "/topology"),
        /**
         * 场景配置化
         */
        smv("场景配置化", "smv", "/scene"),
        /**
         * 运维机器人
         */
        robot("运维机器人", "robot", "/robot"),
        /**
         * 3D可视化
         */
        dcv("3D可视化", "dcv", "/virtual"),
        /**
         * 智能运维
         */
        ocp("智能运维", "ocp", "/intel")
        /*,
        interf("数据采集监控", "interface", "interface")*/;

        private String name;
        private String url;
        private String preUrl;

        module(String name, String url, String preUrl) {
            this.name = name;
            this.url = url;
            this.preUrl = preUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPreUrl() {
            return preUrl;
        }

        public void setPreUrl(String preUrl) {
            this.preUrl = preUrl;
        }
    }


    /**
     * 冻结数据
     */
    public enum BLOCKED{
        /**
         * 正常
         */
        Y("正常"),
        /**
         * 冻结
         */
        N("冻结");

        private String label;
        private BLOCKED(String label){
            this.label = label;
        }
    }


    private List<String> serverIPList;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode == null ? null : clientCode.trim();
    }

    public String getRegisterMan() {
        return registerMan;
    }

    public void setRegisterMan(String registerMan) {
        this.registerMan = registerMan == null ? null : registerMan.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getAuthUserNum() {
        return authUserNum;
    }

    public void setAuthUserNum(Integer authUserNum) {
        this.authUserNum = authUserNum;
    }

    public Integer getAuthCabinetNum() {
        return authCabinetNum;
    }

    public void setAuthCabinetNum(Integer authCabinetNum) {
        this.authCabinetNum = authCabinetNum;
    }

    public Integer getAuthDoorNum() {
        return authDoorNum;
    }

    public void setAuthDoorNum(Integer authDoorNum) {
        this.authDoorNum = authDoorNum;
    }

    public Integer getAuthSceneNum() {
        return authSceneNum;
    }

    public void setAuthSceneNum(Integer authSceneNum) {
        this.authSceneNum = authSceneNum;
    }

    public Integer getAuthViewNum() {
        return authViewNum;
    }

    public void setAuthViewNum(Integer authViewNum) {
        this.authViewNum = authViewNum;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }


    public BigDecimal getCjrId() {
        return cjrId;
    }

    public void setCjrId(BigDecimal cjrId) {
        this.cjrId = cjrId;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public BigDecimal getXgrId() {
        return xgrId;
    }

    public void setXgrId(BigDecimal xgrId) {
        this.xgrId = xgrId;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getAuthImg() {
        return authImg;
    }

    public void setAuthImg(String authImg) {
        this.authImg = authImg == null ? null : authImg.trim();
    }

    public List<String> getServerIPList() {
        return serverIPList;
    }

    public void setServerIPList(List<String> serverIPList) {
        this.serverIPList = serverIPList;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public static void main(String[] args) {
        System.out.println(IomCampLicenseAuth.BLOCKED.Y.name());
    }

    public String getAuthVersion() {
        return authVersion;
    }

    public void setAuthVersion(String authVersion) {
        this.authVersion = authVersion;
    }

    public String getCpuId() {
        return cpuId;
    }

    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMainboard() {
        return mainboard;
    }

    public void setMainboard(String mainboard) {
        this.mainboard = mainboard;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getAllowModule() {
        return allowModule;
    }

    public void setAllowModule(List<String> allowModule) {
        this.allowModule = allowModule;
    }

    public String getAllowModuleName() {
        return allowModuleName;
    }

    public void setAllowModuleName(String allowModuleName) {
        this.allowModuleName = allowModuleName;
    }
}