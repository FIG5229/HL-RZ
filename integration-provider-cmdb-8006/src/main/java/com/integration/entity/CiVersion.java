package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: CiVersion
 * @Author: ztl
 * @Date: 2020-10-13
 * @Version: 1.0
 * @description:CI版本信息
 */
public class CiVersion {
    /**
     * id
     */
    private String id;
    /**
     * 版本号
     */
    private String version;
    /**
     * 创建人
     */
    private String cjr_id;
    /**
     * 创建时间
     */

    private String cjsj;
    /**
     * 有效标志
     */
    private int yxbz;
    /**
     * 数据域ID
     */
    private String domain_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public int getYxbz() {
        return yxbz;
    }

    public void setYxbz(int yxbz) {
        this.yxbz = yxbz;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }
}
