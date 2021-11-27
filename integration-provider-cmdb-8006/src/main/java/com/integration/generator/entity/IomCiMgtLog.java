package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiMgtLog
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiMgtLog {
    private String id;

    private String ciId;

    private String typeId;

    private String bgrId;

    private Date bgsj;

    private Integer mgtType;

    private String upItem;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCiId() {
        return ciId;
    }

    public void setCiId(String ciId) {
        this.ciId = ciId == null ? null : ciId.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getBgrId() {
        return bgrId;
    }

    public void setBgrId(String bgrId) {
        this.bgrId = bgrId == null ? null : bgrId.trim();
    }

    public Date getBgsj() {
        return bgsj;
    }

    public void setBgsj(Date bgsj) {
        this.bgsj = bgsj;
    }

    public Integer getMgtType() {
        return mgtType;
    }

    public void setMgtType(Integer mgtType) {
        this.mgtType = mgtType;
    }

    public String getUpItem() {
        return upItem;
    }

    public void setUpItem(String upItem) {
        this.upItem = upItem == null ? null : upItem.trim();
    }

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }
}