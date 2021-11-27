package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiInfoExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCiBmIsNull() {
            addCriterion("CI_BM is null");
            return (Criteria) this;
        }

        public Criteria andCiBmIsNotNull() {
            addCriterion("CI_BM is not null");
            return (Criteria) this;
        }

        public Criteria andCiBmEqualTo(String value) {
            addCriterion("CI_BM =", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmNotEqualTo(String value) {
            addCriterion("CI_BM <>", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmGreaterThan(String value) {
            addCriterion("CI_BM >", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmGreaterThanOrEqualTo(String value) {
            addCriterion("CI_BM >=", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmLessThan(String value) {
            addCriterion("CI_BM <", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmLessThanOrEqualTo(String value) {
            addCriterion("CI_BM <=", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmLike(String value) {
            addCriterion("CI_BM like", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmNotLike(String value) {
            addCriterion("CI_BM not like", value, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmIn(List<String> values) {
            addCriterion("CI_BM in", values, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiCodeIn(List<String> values) {
            addCriterion("CI_CODE in", values, "ciCode");
            return (Criteria) this;
        }

        public Criteria andCiBmNotIn(List<String> values) {
            addCriterion("CI_BM not in", values, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmBetween(String value1, String value2) {
            addCriterion("CI_BM between", value1, value2, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiBmNotBetween(String value1, String value2) {
            addCriterion("CI_BM not between", value1, value2, "ciBm");
            return (Criteria) this;
        }

        public Criteria andCiDescIsNull() {
            addCriterion("CI_DESC is null");
            return (Criteria) this;
        }

        public Criteria andCiDescIsNotNull() {
            addCriterion("CI_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andCiDescEqualTo(String value) {
            addCriterion("CI_DESC =", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescNotEqualTo(String value) {
            addCriterion("CI_DESC <>", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescGreaterThan(String value) {
            addCriterion("CI_DESC >", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescGreaterThanOrEqualTo(String value) {
            addCriterion("CI_DESC >=", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescLessThan(String value) {
            addCriterion("CI_DESC <", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescLessThanOrEqualTo(String value) {
            addCriterion("CI_DESC <=", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescLike(String value) {
            addCriterion("CI_DESC like", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescNotLike(String value) {
            addCriterion("CI_DESC not like", value, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescIn(List<String> values) {
            addCriterion("CI_DESC in", values, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescNotIn(List<String> values) {
            addCriterion("CI_DESC not in", values, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescBetween(String value1, String value2) {
            addCriterion("CI_DESC between", value1, value2, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiDescNotBetween(String value1, String value2) {
            addCriterion("CI_DESC not between", value1, value2, "ciDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdIsNull() {
            addCriterion("CI_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdIsNotNull() {
            addCriterion("CI_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdEqualTo(String value) {
            addCriterion("CI_TYPE_ID =", value, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdNotEqualTo(String value) {
            addCriterion("CI_TYPE_ID <>", value, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdGreaterThan(String value) {
            addCriterion("CI_TYPE_ID >", value, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_ID >=", value, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdLessThan(String value) {
            addCriterion("CI_TYPE_ID <", value, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_ID <=", value, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdIn(List<String> values) {
            addCriterion("CI_TYPE_ID in", values, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdNotIn(List<String> values) {
            addCriterion("CI_TYPE_ID not in", values, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdBetween(String value1, String value2) {
            addCriterion("CI_TYPE_ID between", value1, value2, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeIdNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_ID not between", value1, value2, "ciTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("SOURCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("SOURCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Integer value) {
            addCriterion("SOURCE_ID =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Integer value) {
            addCriterion("SOURCE_ID <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Integer value) {
            addCriterion("SOURCE_ID >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_ID >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Integer value) {
            addCriterion("SOURCE_ID <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_ID <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Integer> values) {
            addCriterion("SOURCE_ID in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Integer> values) {
            addCriterion("SOURCE_ID not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_ID between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_ID not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNull() {
            addCriterion("OWNER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNotNull() {
            addCriterion("OWNER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdEqualTo(Integer value) {
            addCriterion("OWNER_ID =", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotEqualTo(Integer value) {
            addCriterion("OWNER_ID <>", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThan(Integer value) {
            addCriterion("OWNER_ID >", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("OWNER_ID >=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThan(Integer value) {
            addCriterion("OWNER_ID <", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThanOrEqualTo(Integer value) {
            addCriterion("OWNER_ID <=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIn(List<Integer> values) {
            addCriterion("OWNER_ID in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotIn(List<Integer> values) {
            addCriterion("OWNER_ID not in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdBetween(Integer value1, Integer value2) {
            addCriterion("OWNER_ID between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("OWNER_ID not between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(Integer value) {
            addCriterion("ORG_ID =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(Integer value) {
            addCriterion("ORG_ID <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(Integer value) {
            addCriterion("ORG_ID >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORG_ID >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(Integer value) {
            addCriterion("ORG_ID <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("ORG_ID <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<Integer> values) {
            addCriterion("ORG_ID in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<Integer> values) {
            addCriterion("ORG_ID not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("ORG_ID between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ORG_ID not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andCiVersionIsNull() {
            addCriterion("CI_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andCiVersionIsNotNull() {
            addCriterion("CI_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andCiVersionEqualTo(String value) {
            addCriterion("CI_VERSION =", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionNotEqualTo(String value) {
            addCriterion("CI_VERSION <>", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionGreaterThan(String value) {
            addCriterion("CI_VERSION >", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionGreaterThanOrEqualTo(String value) {
            addCriterion("CI_VERSION >=", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionLessThan(String value) {
            addCriterion("CI_VERSION <", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionLessThanOrEqualTo(String value) {
            addCriterion("CI_VERSION <=", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionLike(String value) {
            addCriterion("CI_VERSION like", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionNotLike(String value) {
            addCriterion("CI_VERSION not like", value, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionIn(List<String> values) {
            addCriterion("CI_VERSION in", values, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionNotIn(List<String> values) {
            addCriterion("CI_VERSION not in", values, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionBetween(String value1, String value2) {
            addCriterion("CI_VERSION between", value1, value2, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCiVersionNotBetween(String value1, String value2) {
            addCriterion("CI_VERSION not between", value1, value2, "ciVersion");
            return (Criteria) this;
        }

        public Criteria andCjrIdIsNull() {
            addCriterion("CJR_ID is null");
            return (Criteria) this;
        }

        public Criteria andCjrIdIsNotNull() {
            addCriterion("CJR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCjrIdEqualTo(String value) {
            addCriterion("CJR_ID =", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotEqualTo(String value) {
            addCriterion("CJR_ID <>", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdGreaterThan(String value) {
            addCriterion("CJR_ID >", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdGreaterThanOrEqualTo(String value) {
            addCriterion("CJR_ID >=", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdLessThan(String value) {
            addCriterion("CJR_ID <", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdLessThanOrEqualTo(String value) {
            addCriterion("CJR_ID <=", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdIn(List<String> values) {
            addCriterion("CJR_ID in", values, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotIn(List<String> values) {
            addCriterion("CJR_ID not in", values, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdBetween(String value1, String value2) {
            addCriterion("CJR_ID between", value1, value2, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotBetween(String value1, String value2) {
            addCriterion("CJR_ID not between", value1, value2, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjsjIsNull() {
            addCriterion("CJSJ is null");
            return (Criteria) this;
        }

        public Criteria andCjsjIsNotNull() {
            addCriterion("CJSJ is not null");
            return (Criteria) this;
        }

        public Criteria andCjsjEqualTo(Date value) {
            addCriterion("CJSJ =", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotEqualTo(Date value) {
            addCriterion("CJSJ <>", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThan(Date value) {
            addCriterion("CJSJ >", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThanOrEqualTo(Date value) {
            addCriterion("CJSJ >=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThan(Date value) {
            addCriterion("CJSJ <", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThanOrEqualTo(Date value) {
            addCriterion("CJSJ <=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjIn(List<Date> values) {
            addCriterion("CJSJ in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotIn(List<Date> values) {
            addCriterion("CJSJ not in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjBetween(Date value1, Date value2) {
            addCriterion("CJSJ between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotBetween(Date value1, Date value2) {
            addCriterion("CJSJ not between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andXgrIdIsNull() {
            addCriterion("XGR_ID is null");
            return (Criteria) this;
        }

        public Criteria andXgrIdIsNotNull() {
            addCriterion("XGR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andXgrIdEqualTo(String value) {
            addCriterion("XGR_ID =", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotEqualTo(String value) {
            addCriterion("XGR_ID <>", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdGreaterThan(String value) {
            addCriterion("XGR_ID >", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdGreaterThanOrEqualTo(String value) {
            addCriterion("XGR_ID >=", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdLessThan(String value) {
            addCriterion("XGR_ID <", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdLessThanOrEqualTo(String value) {
            addCriterion("XGR_ID <=", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdIn(List<String> values) {
            addCriterion("XGR_ID in", values, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotIn(List<String> values) {
            addCriterion("XGR_ID not in", values, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdBetween(String value1, String value2) {
            addCriterion("XGR_ID between", value1, value2, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotBetween(String value1, String value2) {
            addCriterion("XGR_ID not between", value1, value2, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgsjIsNull() {
            addCriterion("XGSJ is null");
            return (Criteria) this;
        }

        public Criteria andXgsjIsNotNull() {
            addCriterion("XGSJ is not null");
            return (Criteria) this;
        }

        public Criteria andXgsjEqualTo(Date value) {
            addCriterion("XGSJ =", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotEqualTo(Date value) {
            addCriterion("XGSJ <>", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThan(Date value) {
            addCriterion("XGSJ >", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThanOrEqualTo(Date value) {
            addCriterion("XGSJ >=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThan(Date value) {
            addCriterion("XGSJ <", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThanOrEqualTo(Date value) {
            addCriterion("XGSJ <=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjIn(List<Date> values) {
            addCriterion("XGSJ in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotIn(List<Date> values) {
            addCriterion("XGSJ not in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjBetween(Date value1, Date value2) {
            addCriterion("XGSJ between", value1, value2, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotBetween(Date value1, Date value2) {
            addCriterion("XGSJ not between", value1, value2, "xgsj");
            return (Criteria) this;
        }

        public Criteria andYxbzIsNull() {
            addCriterion("YXBZ is null");
            return (Criteria) this;
        }

        public Criteria andYxbzIsNotNull() {
            addCriterion("YXBZ is not null");
            return (Criteria) this;
        }

        public Criteria andYxbzEqualTo(Integer value) {
            addCriterion("YXBZ =", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzNotEqualTo(Integer value) {
            addCriterion("YXBZ <>", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzGreaterThan(Integer value) {
            addCriterion("YXBZ >", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzGreaterThanOrEqualTo(Integer value) {
            addCriterion("YXBZ >=", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzLessThan(Integer value) {
            addCriterion("YXBZ <", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzLessThanOrEqualTo(Integer value) {
            addCriterion("YXBZ <=", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzIn(List<Integer> values) {
            addCriterion("YXBZ in", values, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzNotIn(List<Integer> values) {
            addCriterion("YXBZ not in", values, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzBetween(Integer value1, Integer value2) {
            addCriterion("YXBZ between", value1, value2, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzNotBetween(Integer value1, Integer value2) {
            addCriterion("YXBZ not between", value1, value2, "yxbz");
            return (Criteria) this;
        }
        public Criteria andDomainIdEqualTo(String value) {
            addCriterion("DOMAIN_ID =", value, "domainId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}