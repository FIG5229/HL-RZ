package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Configure
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 配置实体类
*/
public class Configure {
    private String id;
    private String type;
    private String url;
    private String serverName;
    private String dtFormat;
    private String btnName;
    private String isShow;
    private String requestModel;
    private String sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDtFormat() {
        return dtFormat;
    }

    public void setDtFormat(String dtFormat) {
        this.dtFormat = dtFormat;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(String requestModel) {
        this.requestModel = requestModel;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
