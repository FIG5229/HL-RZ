package com.integration.entity;

/**
* @Package: com.integration.entity
* @ClassName: UserMapping
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 用户实体类
*/
public class UserMapping {
    private String id;
    private String czry_id;
    private String mapping_dldm;
    private String mapping_password;
    private String mapping_url;
    private String subsystem;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCzry_id() {
        return czry_id;
    }

    public void setCzry_id(String czry_id) {
        this.czry_id = czry_id;
    }

    public String getMapping_dldm() {
        return mapping_dldm;
    }

    public void setMapping_dldm(String mapping_dldm) {
        this.mapping_dldm = mapping_dldm;
    }

    public String getMapping_password() {
        return mapping_password;
    }

    public void setMapping_password(String mapping_password) {
        this.mapping_password = mapping_password;
    }

    public String getMapping_url() {
        return mapping_url;
    }

    public void setMapping_url(String mapping_url) {
        this.mapping_url = mapping_url;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }
}
