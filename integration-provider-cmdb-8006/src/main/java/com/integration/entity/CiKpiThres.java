package com.integration.entity;

/**
* @Package: com.integration.entity
* @ClassName: CiKpiThres
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: CI指标关系
*/
public class CiKpiThres {

    private String id;
    private String kpi_id;
    private String kpi_thres;
    private String kpi_icon;
    private String kpi_color;
    private String cjsj;
    private String xgsj;
    private String yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKpi_id() {
        return kpi_id;
    }

    public void setKpi_id(String kpi_id) {
        this.kpi_id = kpi_id;
    }

    public String getKpi_thres() {
        return kpi_thres;
    }

    public void setKpi_thres(String kpi_thres) {
        this.kpi_thres = kpi_thres;
    }

    public String getKpi_icon() {
        return kpi_icon;
    }

    public void setKpi_icon(String kpi_icon) {
        this.kpi_icon = kpi_icon;
    }

    public String getKpi_color() {
        return kpi_color;
    }

    public void setKpi_color(String kpi_color) {
        this.kpi_color = kpi_color;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getXgsj() {
        return xgsj;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }
}
