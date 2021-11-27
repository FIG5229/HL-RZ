package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiInfo
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 台账信息实体类
*/
public class IomCiInfo {
    private String id;

    private String ciBm;

    private String ciName;

    private String ciCode;

    private String ciDesc;

    private String ciTypeId;

    private Integer sourceId;

    private Integer ownerId;

    private Integer orgId;

    private String ciVersion;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCiBm() {
        return ciBm;
    }

    public void setCiBm(String ciBm) {
        this.ciBm = ciBm == null ? null : ciBm.trim();
    }

    public String getCiDesc() {
        return ciDesc;
    }

    public void setCiDesc(String ciDesc) {
        this.ciDesc = ciDesc == null ? null : ciDesc.trim();
    }

    public String getCiTypeId() {
        return ciTypeId;
    }

    public void setCiTypeId(String ciTypeId) {
        this.ciTypeId = ciTypeId == null ? null : ciTypeId.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getCiVersion() {
        return ciVersion;
    }

    public void setCiVersion(String ciVersion) {
        this.ciVersion = ciVersion == null ? null : ciVersion.trim();
    }

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId == null ? null : cjrId.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId == null ? null : xgrId.trim();
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

    public String getCiCode() {
        return ciCode;
    }

    public void setCiCode(String ciCode) {
        this.ciCode = ciCode;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }
}