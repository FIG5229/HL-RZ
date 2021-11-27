package com.integration.entity;
/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: Domain
 * @Author: 1.数据域表用户控制数据权限。 2. 可按组织机构（单位）配置数据域，在业务表中存放数据域字段
 * @Description:
 * @Date: 2020/5/15 8:38
 * @Version: 1.0
 * @Modified By:
 */

public class Domain {

     /**
      * 主键
      */
    private String id;
     /**
      * 机构代码
      */
    private String domain_code;
     /**
      * 机构名称
      */
    private String domain_name;
     /**
      * 描述
      */
    private String domain_desc;
     /**
      * 数据域ID
      */
    private String domain_id;
     /**
      * 创建时间
      */
    private String cjsj;
     /**
      * 修改时间
      */
    private String xgsj;
     /**
      * 有效标志 0：无效 1：有效
      */
    private String yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain_code() {
        return domain_code;
    }

    public void setDomain_code(String domain_code) {
        this.domain_code = domain_code;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public String getDomain_desc() {
        return domain_desc;
    }

    public void setDomain_desc(String domain_desc) {
        this.domain_desc = domain_desc;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
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
