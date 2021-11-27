package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: ConfMapping
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 配置实体类
*/
public class ConfMapping {
    private String id;
    private String configId;
    private String mappingField;
    private String selfField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getMappingField() {
        return mappingField;
    }

    public void setMappingField(String mappingField) {
        this.mappingField = mappingField;
    }

    public String getSelfField() {
        return selfField;
    }

    public void setSelfField(String selfField) {
        this.selfField = selfField;
    }
}
