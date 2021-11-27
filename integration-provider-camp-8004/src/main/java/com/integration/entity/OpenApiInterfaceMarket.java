package com.integration.entity;

/**
 * @ClassName OpenApiInterfaceMarket
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/12/8 11:50
 * @Version 1.0
 **/
public class OpenApiInterfaceMarket {

    private IomCampInterfaceMarket interfaceMarket;

    private Integer pageNum;

    private Integer pageSize;

    private String username;

    private String password;

    public IomCampInterfaceMarket getInterfaceMarket() {
        return interfaceMarket;
    }

    public void setInterfaceMarket(IomCampInterfaceMarket interfaceMarket) {
        this.interfaceMarket = interfaceMarket;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
