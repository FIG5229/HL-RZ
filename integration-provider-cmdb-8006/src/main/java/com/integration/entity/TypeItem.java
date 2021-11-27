package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: TypeItem
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 类定义实体类
*/
public class TypeItem extends BaseEntity {

    /**
     * id
     */
    private String id;

    /**
     * 所属类别
     */
    private String ci_type_id;

    /**
     * 属性名
     */
    private String attr_name;

    /**
     * 标准名
     */
    private String attr_std_name;

    /**
     * 属性类型
     */
    private String attr_type;

    /**
     * 属性描述
     */
    private String attr_desc;

    /**
     * 映射字段
     */
    private String mp_ci_item;

    /**
     * 是否主键
     */
    private int is_major;

    /**
     * 是否必填
     */
    private int is_requ;

    /**
     * 缺省值
     */
    private String def_val;

    /**
     * 枚举值
     */
    private String enum_vals;

    /**
     * 拍序列
     */
    private int sort;

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
    private int yxbz;

    private String ciValue;

    /**
     * 是否关联字段
     */
    private int is_rela;

    private String regexp;

    /**
     * 是否标签（显示字段）
     */
    private int is_label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCi_type_id() {
        return ci_type_id;
    }

    public void setCi_type_id(String ci_type_id) {
        this.ci_type_id = ci_type_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public String getAttr_std_name() {
        return attr_std_name;
    }

    public void setAttr_std_name(String attr_std_name) {
        this.attr_std_name = attr_std_name;
    }

    public String getAttr_type() {
        return attr_type;
    }

    public void setAttr_type(String attr_type) {
        this.attr_type = attr_type;
    }

    public String getAttr_desc() {
        return attr_desc;
    }

    public void setAttr_desc(String attr_desc) {
        this.attr_desc = attr_desc;
    }

    public String getMp_ci_item() {
        return mp_ci_item;
    }

    public void setMp_ci_item(String mp_ci_item) {
        this.mp_ci_item = mp_ci_item;
    }

    public int getIs_major() {
        return is_major;
    }

    public void setIs_major(int is_major) {
        this.is_major = is_major;
    }

    public int getIs_requ() {
        return is_requ;
    }

    public void setIs_requ(int is_requ) {
        this.is_requ = is_requ;
    }

    public String getDef_val() {
        return def_val;
    }

    public void setDef_val(String def_val) {
        this.def_val = def_val;
    }

    public String getEnum_vals() {
        return enum_vals;
    }

    public void setEnum_vals(String enum_vals) {
        this.enum_vals = enum_vals;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public int getYxbz() {
        return yxbz;
    }

    public void setYxbz(int yxbz) {
        this.yxbz = yxbz;
    }

    public String getCiValue() {
        return ciValue;
    }

    public void setCiValue(String ciValue) {
        this.ciValue = ciValue;
    }

    public int getIs_rela() {
        return is_rela;
    }

    public void setIs_rela(int is_rela) {
        this.is_rela = is_rela;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public int getIs_label() {
        return is_label;
    }

    public void setIs_label(int is_label) {
        this.is_label = is_label;
    }
}
