package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: ConfOutMapping
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description:
*/
public class ConfOutMapping {
    private String id;
    private String configId;
    private String mappingCode;
    private String mappingName;

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

    public String getMappingCode() {
        return mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }
}
