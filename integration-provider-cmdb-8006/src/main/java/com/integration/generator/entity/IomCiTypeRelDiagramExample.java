package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiTypeRelDiagramExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 视图
*/
public class IomCiTypeRelDiagramExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiTypeRelDiagramExample() {
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

        public Criteria andDomainId(String value) {
            addCriterion("domain_id =", value, "domainId");
            return (Criteria) this;
        }
        public Criteria andDomainIdIn(List<String> value) {
            addCriterion("domain_id in", value, "domainId");
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

        public Criteria andDiagNameIsNull() {
            addCriterion("DIAG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDiagNameIsNotNull() {
            addCriterion("DIAG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDiagNameEqualTo(String value) {
            addCriterion("DIAG_NAME =", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameNotEqualTo(String value) {
            addCriterion("DIAG_NAME <>", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameGreaterThan(String value) {
            addCriterion("DIAG_NAME >", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameGreaterThanOrEqualTo(String value) {
            addCriterion("DIAG_NAME >=", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameLessThan(String value) {
            addCriterion("DIAG_NAME <", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameLessThanOrEqualTo(String value) {
            addCriterion("DIAG_NAME <=", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameLike(String value) {
            addCriterion("DIAG_NAME like", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameNotLike(String value) {
            addCriterion("DIAG_NAME not like", value, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameIn(List<String> values) {
            addCriterion("DIAG_NAME in", values, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameNotIn(List<String> values) {
            addCriterion("DIAG_NAME not in", values, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameBetween(String value1, String value2) {
            addCriterion("DIAG_NAME between", value1, value2, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagNameNotBetween(String value1, String value2) {
            addCriterion("DIAG_NAME not between", value1, value2, "diagName");
            return (Criteria) this;
        }

        public Criteria andDiagXmlIsNull() {
            addCriterion("DIAG_XML is null");
            return (Criteria) this;
        }

        public Criteria andDiagXmlIsNotNull() {
            addCriterion("DIAG_XML is not null");
            return (Criteria) this;
        }

        public Criteria andDiagXmlEqualTo(String value) {
            addCriterion("DIAG_XML =", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotEqualTo(String value) {
            addCriterion("DIAG_XML <>", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlGreaterThan(String value) {
            addCriterion("DIAG_XML >", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlGreaterThanOrEqualTo(String value) {
            addCriterion("DIAG_XML >=", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlLessThan(String value) {
            addCriterion("DIAG_XML <", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlLessThanOrEqualTo(String value) {
            addCriterion("DIAG_XML <=", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlLike(String value) {
            addCriterion("DIAG_XML like", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotLike(String value) {
            addCriterion("DIAG_XML not like", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlIn(List<String> values) {
            addCriterion("DIAG_XML in", values, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotIn(List<String> values) {
            addCriterion("DIAG_XML not in", values, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlBetween(String value1, String value2) {
            addCriterion("DIAG_XML between", value1, value2, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotBetween(String value1, String value2) {
            addCriterion("DIAG_XML not between", value1, value2, "diagXml");
            return (Criteria) this;
        }

        public Criteria andIconoUrlIsNull() {
            addCriterion("ICONO_URL is null");
            return (Criteria) this;
        }

        public Criteria andIconoUrlIsNotNull() {
            addCriterion("ICONO_URL is not null");
            return (Criteria) this;
        }

        public Criteria andIconoUrlEqualTo(String value) {
            addCriterion("ICONO_URL =", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlNotEqualTo(String value) {
            addCriterion("ICONO_URL <>", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlGreaterThan(String value) {
            addCriterion("ICONO_URL >", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("ICONO_URL >=", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlLessThan(String value) {
            addCriterion("ICONO_URL <", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlLessThanOrEqualTo(String value) {
            addCriterion("ICONO_URL <=", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlLike(String value) {
            addCriterion("ICONO_URL like", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlNotLike(String value) {
            addCriterion("ICONO_URL not like", value, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlIn(List<String> values) {
            addCriterion("ICONO_URL in", values, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlNotIn(List<String> values) {
            addCriterion("ICONO_URL not in", values, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlBetween(String value1, String value2) {
            addCriterion("ICONO_URL between", value1, value2, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andIconoUrlNotBetween(String value1, String value2) {
            addCriterion("ICONO_URL not between", value1, value2, "iconoUrl");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andDiagTypeIsNull() {
            addCriterion("DIAG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDiagTypeIsNotNull() {
            addCriterion("DIAG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDiagTypeEqualTo(Integer value) {
            addCriterion("DIAG_TYPE =", value, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeNotEqualTo(Integer value) {
            addCriterion("DIAG_TYPE <>", value, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeGreaterThan(Integer value) {
            addCriterion("DIAG_TYPE >", value, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("DIAG_TYPE >=", value, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeLessThan(Integer value) {
            addCriterion("DIAG_TYPE <", value, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeLessThanOrEqualTo(Integer value) {
            addCriterion("DIAG_TYPE <=", value, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeIn(List<Integer> values) {
            addCriterion("DIAG_TYPE in", values, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeNotIn(List<Integer> values) {
            addCriterion("DIAG_TYPE not in", values, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeBetween(Integer value1, Integer value2) {
            addCriterion("DIAG_TYPE between", value1, value2, "diagType");
            return (Criteria) this;
        }

        public Criteria andDiagTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("DIAG_TYPE not between", value1, value2, "diagType");
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