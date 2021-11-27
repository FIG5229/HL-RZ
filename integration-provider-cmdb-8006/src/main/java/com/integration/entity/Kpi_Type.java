package com.integration.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hbr
 * @date 2018-12-14 10:47:33
 * @version 1.0
 **/
public class Kpi_Type {

    /**
     * id
     */
    private String id;

    /**
     * KPI名称
     */
    private String kpi_name;

    /**
     * KPI别名
     */
    private String kpi_alias;

    /**
     * KPI描述
     */
    private String kpi_desc;

    /**
     * 数值单位
     */
    private String val_unit;

    /**
     * 对象组
     */
    private List<Type> obj_name;

    /**
     * 指标值
     */
    private List<CiKpiThres> thres;
    /**
     * 指标分类
     */
    private String kpi_class_id;
    /**
     * 指标分类名称
     */
    private String kpi_class_name;
    private String type_id;
    private String val_type;
    private String val_exp;
    private String dw_id;
    private String dw_name;
    private String source_id;
    private String op_id;
    private String idx_field;
    private String cjr_id;
    private String cjsj;
    private String xgsj;
    private String xgr_id;
    private String yxbz;
    /**
     * 最大值
     */
    private BigDecimal maximum;

    /**
     * 最小值
     */
    private BigDecimal minimum;

    /**
     * 最大值
     */
    private String maximums;

    /**
     * 最小值
     */
    private String minimums;


    /**
     * 是否匹配 0未匹配 1匹配
     */
    private Integer is_match;

    /**
     * 数据域ID
     */
    private String domain_id;

    public String getKpi_class_id() {
        return kpi_class_id;
    }

    public void setKpi_class_id(String kpi_class_id) {
        this.kpi_class_id = kpi_class_id;
    }

    public Integer getIs_match() {
        return is_match;
    }

    public void setIs_match(Integer is_match) {
        this.is_match = is_match;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKpi_desc() {
        return kpi_desc;
    }

    public void setKpi_desc(String kpi_desc) {
        this.kpi_desc = kpi_desc;
    }

    public String getVal_unit() {
        return val_unit;
    }

    public void setVal_unit(String val_unit) {
        this.val_unit = val_unit;
    }

    public List<CiKpiThres> getThres() {
        return thres;
    }

    public void setThres(List<CiKpiThres> thres) {
        this.thres = thres;
    }

    public String getKpi_name() {
        return kpi_name;
    }

    public void setKpi_name(String kpi_name) {
        this.kpi_name = kpi_name;
    }

    public String getKpi_alias() {
        return kpi_alias;
    }

    public void setKpi_alias(String kpi_alias) {
        this.kpi_alias = kpi_alias;
    }

    public List<Type> getObj_name() {
        return obj_name;
    }

    public void setObj_name(List<Type> obj_name) {
        this.obj_name = obj_name;
    }

    public String getKpi_class_name() {
        return kpi_class_name;
    }

    public void setKpi_class_name(String kpi_class_name) {
        this.kpi_class_name = kpi_class_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getVal_type() {
        return val_type;
    }

    public void setVal_type(String val_type) {
        this.val_type = val_type;
    }

    public String getVal_exp() {
        return val_exp;
    }

    public void setVal_exp(String val_exp) {
        this.val_exp = val_exp;
    }

    public String getDw_id() {
        return dw_id;
    }

    public void setDw_id(String dw_id) {
        this.dw_id = dw_id;
    }

    public String getDw_name() {
        return dw_name;
    }

    public void setDw_name(String dw_name) {
        this.dw_name = dw_name;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getOp_id() {
        return op_id;
    }

    public void setOp_id(String op_id) {
        this.op_id = op_id;
    }

    public String getIdx_field() {
        return idx_field;
    }

    public void setIdx_field(String idx_field) {
        this.idx_field = idx_field;
    }

    public String getCjr_id() {
        return cjr_id;
    }

    public void setCjr_id(String cjr_id) {
        this.cjr_id = cjr_id;
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

    public String getXgr_id() {
        return xgr_id;
    }

    public void setXgr_id(String xgr_id) {
        this.xgr_id = xgr_id;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    public BigDecimal getMaximum() {
        return maximum;
    }

    public void setMaximum(BigDecimal maximum) {
        this.maximum = maximum;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public String getMaximums() {
        return maximums;
    }

    public void setMaximums(String maximums) {
        this.maximums = maximums;
    }

    public String getMinimums() {
        return minimums;
    }

    public void setMinimums(String minimums) {
        this.minimums = minimums;
    }
}