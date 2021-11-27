package com.integration.entity;

import java.util.Date;

public class SgccUserCzry {
    private String id;

    private String sgccUserId;

    private String czryId;

    private String cjrId;

    private Date cjsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSgccUserId() {
        return sgccUserId;
    }

    public void setSgccUserId(String sgccUserId) {
        this.sgccUserId = sgccUserId == null ? null : sgccUserId.trim();
    }

    public String getCzryId() {
        return czryId;
    }

    public void setCzryId(String czryId) {
        this.czryId = czryId;
    }

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
}