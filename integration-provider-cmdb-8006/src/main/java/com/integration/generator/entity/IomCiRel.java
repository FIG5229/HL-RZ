package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRel
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 台账关系实体类
*/
public class IomCiRel {
    private String id;

    private String lineBm;

    private String lineName;

    private String lineStdBm;

    private String lineDesc;

    private String parentLineId;

    private Integer lineLvl;

    private String linePath;

    private Integer sort;

    private Integer lineCost;

    private Integer lineStyle;

    private Integer lineWidth;

    private String lineColor;

    private Integer lineArror;

    private Integer lineAnime;

    private Long cjrId;

    private Date cjsj;

    private Long xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLineBm() {
        return lineBm;
    }

    public void setLineBm(String lineBm) {
        this.lineBm = lineBm == null ? null : lineBm.trim();
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getLineStdBm() {
        return lineStdBm;
    }

    public void setLineStdBm(String lineStdBm) {
        this.lineStdBm = lineStdBm == null ? null : lineStdBm.trim();
    }

    public String getLineDesc() {
        return lineDesc;
    }

    public void setLineDesc(String lineDesc) {
        this.lineDesc = lineDesc == null ? null : lineDesc.trim();
    }

    public String getParentLineId() {
        return parentLineId;
    }

    public void setParentLineId(String parentLineId) {
        this.parentLineId = parentLineId == null ? null : parentLineId.trim();
    }

    public Integer getLineLvl() {
        return lineLvl;
    }

    public void setLineLvl(Integer lineLvl) {
        this.lineLvl = lineLvl;
    }

    public String getLinePath() {
        return linePath;
    }

    public void setLinePath(String linePath) {
        this.linePath = linePath == null ? null : linePath.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLineCost() {
        return lineCost;
    }

    public void setLineCost(Integer lineCost) {
        this.lineCost = lineCost;
    }

    public Integer getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(Integer lineStyle) {
        this.lineStyle = lineStyle;
    }

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor == null ? null : lineColor.trim();
    }

    public Integer getLineArror() {
        return lineArror;
    }

    public void setLineArror(Integer lineArror) {
        this.lineArror = lineArror;
    }

    public Integer getLineAnime() {
        return lineAnime;
    }

    public void setLineAnime(Integer lineAnime) {
        this.lineAnime = lineAnime;
    }

    public Long getCjrId() {
        return cjrId;
    }

    public void setCjrId(Long cjrId) {
        this.cjrId = cjrId;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Long getXgrId() {
        return xgrId;
    }

    public void setXgrId(Long xgrId) {
        this.xgrId = xgrId;
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