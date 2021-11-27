

package com.integration.entity;


import java.math.BigDecimal;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-11 05:46:51
 */

public class CiKpiInfo {


    /**
     * 主键
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
     * 所属分类
     */
    private String type_id;

    /**
     * 指标分类
     */
    private String kpi_class_id;

    /**
     * 指标分类名称
     */
    private String kpi_class_name;

    /**
     * 是否匹配 0未匹配 1匹配
     */
    private Integer is_match;
    /**
     * 数值单位
     */
    private String val_unit;


    /**
     * 数值类型1:数值;2:字符
     */
    private Integer val_type;


    /**
     * 数据阀值
     */
    private String val_exp;


    /**
     * 单位ID
     */
    private Integer dw_id;


    /**
     * 单位名称
     */
    private String dw_name;


    /**
     * 来源
     */
    private String source_id;


    /**
     * 所有者
     */
    private Integer op_id;


    /**
     * 搜索字段
     */
    private String idx_field;


    /**
     * 创建人
     */
    private String cjr_id;


    /**
     * 创建时间
     */
    private String cjsj;


    /**
     * 修改人
     */
    private String xgr_id;


    /**
     * 修改时间
     */
    private String xgsj;


    /**
     * 有效标志
     */
    private Integer yxbz;

    /**
     * 数据域ID
     */
    private String domain_id;

    /**
     * 最大值
     */
    private BigDecimal maximum;

    /**
     * 最小值
     */
    private BigDecimal minimum;

    public String getVal_unit() {
        return val_unit;
    }

    public void setVal_unit(String val_unit) {
        this.val_unit = val_unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getKpi_desc() {
        return kpi_desc;
    }

    public void setKpi_desc(String kpi_desc) {
        this.kpi_desc = kpi_desc;
    }


    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }


    public Integer getVal_type() {
        return val_type;
    }

    public void setVal_type(Integer val_type) {
        this.val_type = val_type;
    }


    public String getVal_exp() {
        return val_exp;
    }

    public void setVal_exp(String val_exp) {
        this.val_exp = val_exp;
    }


    public Integer getDw_id() {
        return dw_id;
    }

    public void setDw_id(Integer dw_id) {
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


    public Integer getOp_id() {
        return op_id;
    }

    public void setOp_id(Integer op_id) {
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


    public String getXgr_id() {
        return xgr_id;
    }

    public void setXgr_id(String xgr_id) {
        this.xgr_id = xgr_id;
    }


    public String getXgsj() {
        return xgsj;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }


    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

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

    public String getKpi_class_name() {
        return kpi_class_name;
    }

    public void setKpi_class_name(String kpi_class_name) {
        this.kpi_class_name = kpi_class_name;
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

    @Override
    public String toString() {
        return "CiKpiInfo{" +
                "id='" + id + '\'' +
                ", kpi_name='" + kpi_name + '\'' +
                ", kpi_alias='" + kpi_alias + '\'' +
                ", kpi_desc='" + kpi_desc + '\'' +
                ", type_id='" + type_id + '\'' +
                ", kpi_class_id='" + kpi_class_id + '\'' +
                ", kpi_class_name='" + kpi_class_name + '\'' +
                ", is_match=" + is_match +
                ", val_unit='" + val_unit + '\'' +
                ", val_type=" + val_type +
                ", val_exp='" + val_exp + '\'' +
                ", dw_id=" + dw_id +
                ", dw_name='" + dw_name + '\'' +
                ", source_id='" + source_id + '\'' +
                ", op_id=" + op_id +
                ", idx_field='" + idx_field + '\'' +
                ", cjr_id='" + cjr_id + '\'' +
                ", cjsj='" + cjsj + '\'' +
                ", xgr_id='" + xgr_id + '\'' +
                ", xgsj='" + xgsj + '\'' +
                ", yxbz=" + yxbz +
                ", domain_id='" + domain_id + '\'' +
                ", maximum=" + maximum +
                ", minimum=" + minimum +
                '}';
    }
}

