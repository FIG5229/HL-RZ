package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampDict
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 字典实体类
*/
public class IomCampDict {
    private String dictId;

    private String dictBm;

    private String dictName;

    private String sjId;

    private Integer gnfl;

    private Integer sort;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    private String coutent;

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId == null ? null : dictId.trim();
    }

    public String getDictBm() {
        return dictBm;
    }

    public void setDictBm(String dictBm) {
        this.dictBm = dictBm == null ? null : dictBm.trim();
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getSjId() {
        return sjId;
    }

    public void setSjId(String sjId) {
        this.sjId = sjId == null ? null : sjId.trim();
    }

    public Integer getGnfl() {
        return gnfl;
    }

    public void setGnfl(Integer gnfl) {
        this.gnfl = gnfl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getCoutent() {
        return coutent;
    }

    public void setCoutent(String coutent) {
        this.coutent = coutent == null ? null : coutent.trim();
    }
}