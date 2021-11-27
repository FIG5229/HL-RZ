package com.integration.entity;

import java.util.Date;
/**
* @Package: com.integration.entity
* @ClassName: IomCampInterfaceMarket
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description:
*/
public class IomCampInterfaceMarket {

    private String id;

    private String clientName;

    private String clientDesc;

    private InterfaceType interfaceType;



    private String interfaceUri;

    private String interfaceMethod;

    private String username;

    private String password;

    private Integer yxbz;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private String interfaceParamJson;


    private String interfaceUriLike;

    private String interfaceUrlPrefix;

    public enum InterfaceType{
        /* ci分类 */
        CITYPE ,
        /* 告警 */
        ALERT;

        private String prefix;
        private String innerPrefix;

        InterfaceType(){
            switch (this.name()){
                case "CITYPE":{
                    this.prefix = "/ciType";
                    this.innerPrefix = "http://cmdb/getCiTypeInfo";
                    break;
                }
                default:{
                    this.prefix = "/alert";
                    this.innerPrefix = "http://alarm/currentinfo/getCurrentEventInfo";
                }
            }
        }

        public String getPrefix() {
            return prefix;
        }

        public String getInnerPrefix() {
            return innerPrefix;
        }
    }



    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc == null ? null : clientDesc.trim();
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getInterfaceUri() {
        return interfaceUri;
    }

    public void setInterfaceUri(String interfaceUri) {
        this.interfaceUri = interfaceUri == null ? null : interfaceUri.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }


    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }


    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getInterfaceParamJson() {
        return interfaceParamJson;
    }

    public void setInterfaceParamJson(String interfaceParamJson) {
        this.interfaceParamJson = interfaceParamJson == null ? null : interfaceParamJson.trim();
    }

    public String getInterfaceUriLike() {
        return interfaceUriLike;
    }

    public void setInterfaceUriLike(String interfaceUriLike) {
        this.interfaceUriLike = interfaceUriLike;
    }

    public String getInterfaceUrlPrefix() {
        return interfaceUrlPrefix;
    }

    public void setInterfaceUrlPrefix(String interfaceUrlPrefix) {
        this.interfaceUrlPrefix = interfaceUrlPrefix;
    }

    public String getInterfaceMethod() {
        return interfaceMethod;
    }

    public void setInterfaceMethod(String interfaceMethod) {
        this.interfaceMethod = interfaceMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId;
    }
}