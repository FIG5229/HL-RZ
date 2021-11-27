package com.integration.generator.entity;

import java.io.Serializable;
import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiKpi
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标实体类
*/
public class IomCiKpi implements Serializable {
    private String id;

    private String kpiName;

    private String kpiAlias;

    private String kpiDesc;

    private String typeId;

    private String kpiClassId;

    private Integer isMatch;

    private String valUnit;

    private Integer valType;

    private String valExp;

    private Integer dwId;

    private String dwName;

    private String sourceId;

    private Integer opId;

    private String idxField;

    private String minimum;

    private String maximum;

    private Integer domainId;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName == null ? null : kpiName.trim();
    }

    public String getKpiAlias() {
        return kpiAlias;
    }

    public void setKpiAlias(String kpiAlias) {
        this.kpiAlias = kpiAlias == null ? null : kpiAlias.trim();
    }

    public String getKpiDesc() {
        return kpiDesc;
    }

    public void setKpiDesc(String kpiDesc) {
        this.kpiDesc = kpiDesc == null ? null : kpiDesc.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getKpiClassId() {
        return kpiClassId;
    }

    public void setKpiClassId(String kpiClassId) {
        this.kpiClassId = kpiClassId == null ? null : kpiClassId.trim();
    }

    public Integer getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(Integer isMatch) {
        this.isMatch = isMatch;
    }

    public String getValUnit() {
        return valUnit;
    }

    public void setValUnit(String valUnit) {
        this.valUnit = valUnit == null ? null : valUnit.trim();
    }

    public Integer getValType() {
        return valType;
    }

    public void setValType(Integer valType) {
        this.valType = valType;
    }

    public String getValExp() {
        return valExp;
    }

    public void setValExp(String valExp) {
        this.valExp = valExp == null ? null : valExp.trim();
    }

    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    public String getDwName() {
        return dwName;
    }

    public void setDwName(String dwName) {
        this.dwName = dwName == null ? null : dwName.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getIdxField() {
        return idxField;
    }

    public void setIdxField(String idxField) {
        this.idxField = idxField == null ? null : idxField.trim();
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum == null ? null : minimum.trim();
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum == null ? null : maximum.trim();
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
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
}