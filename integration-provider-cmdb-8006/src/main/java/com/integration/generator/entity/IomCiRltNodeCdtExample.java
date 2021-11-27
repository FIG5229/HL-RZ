package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltNodeCdtExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltNodeCdtExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiRltNodeCdtExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andNodeIdIsNull() {
            addCriterion("NODE_ID is null");
            return (Criteria) this;
        }

        public Criteria andNodeIdIsNotNull() {
            addCriterion("NODE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNodeIdEqualTo(String value) {
            addCriterion("NODE_ID =", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotEqualTo(String value) {
            addCriterion("NODE_ID <>", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThan(String value) {
            addCriterion("NODE_ID >", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("NODE_ID >=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThan(String value) {
            addCriterion("NODE_ID <", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThanOrEqualTo(String value) {
            addCriterion("NODE_ID <=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdIn(List<String> values) {
            addCriterion("NODE_ID in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotIn(List<String> values) {
            addCriterion("NODE_ID not in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdBetween(String value1, String value2) {
            addCriterion("NODE_ID between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotBetween(String value1, String value2) {
            addCriterion("NODE_ID not between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andAttrIdIsNull() {
            addCriterion("ATTR_ID is null");
            return (Criteria) this;
        }

        public Criteria andAttrIdIsNotNull() {
            addCriterion("ATTR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAttrIdEqualTo(String value) {
            addCriterion("ATTR_ID =", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdNotEqualTo(String value) {
            addCriterion("ATTR_ID <>", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdGreaterThan(String value) {
            addCriterion("ATTR_ID >", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdGreaterThanOrEqualTo(String value) {
            addCriterion("ATTR_ID >=", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdLessThan(String value) {
            addCriterion("ATTR_ID <", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdLessThanOrEqualTo(String value) {
            addCriterion("ATTR_ID <=", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdIn(List<String> values) {
            addCriterion("ATTR_ID in", values, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdNotIn(List<String> values) {
            addCriterion("ATTR_ID not in", values, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdBetween(String value1, String value2) {
            addCriterion("ATTR_ID between", value1, value2, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdNotBetween(String value1, String value2) {
            addCriterion("ATTR_ID not between", value1, value2, "attrId");
            return (Criteria) this;
        }

        public Criteria andCdtOpIsNull() {
            addCriterion("CDT_OP is null");
            return (Criteria) this;
        }

        public Criteria andCdtOpIsNotNull() {
            addCriterion("CDT_OP is not null");
            return (Criteria) this;
        }

        public Criteria andCdtOpEqualTo(String value) {
            addCriterion("CDT_OP =", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpNotEqualTo(String value) {
            addCriterion("CDT_OP <>", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpGreaterThan(String value) {
            addCriterion("CDT_OP >", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpGreaterThanOrEqualTo(String value) {
            addCriterion("CDT_OP >=", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpLessThan(String value) {
            addCriterion("CDT_OP <", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpLessThanOrEqualTo(String value) {
            addCriterion("CDT_OP <=", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpLike(String value) {
            addCriterion("CDT_OP like", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpNotLike(String value) {
            addCriterion("CDT_OP not like", value, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpIn(List<String> values) {
            addCriterion("CDT_OP in", values, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpNotIn(List<String> values) {
            addCriterion("CDT_OP not in", values, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpBetween(String value1, String value2) {
            addCriterion("CDT_OP between", value1, value2, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtOpNotBetween(String value1, String value2) {
            addCriterion("CDT_OP not between", value1, value2, "cdtOp");
            return (Criteria) this;
        }

        public Criteria andCdtValIsNull() {
            addCriterion("CDT_VAL is null");
            return (Criteria) this;
        }

        public Criteria andCdtValIsNotNull() {
            addCriterion("CDT_VAL is not null");
            return (Criteria) this;
        }

        public Criteria andCdtValEqualTo(String value) {
            addCriterion("CDT_VAL =", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValNotEqualTo(String value) {
            addCriterion("CDT_VAL <>", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValGreaterThan(String value) {
            addCriterion("CDT_VAL >", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValGreaterThanOrEqualTo(String value) {
            addCriterion("CDT_VAL >=", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValLessThan(String value) {
            addCriterion("CDT_VAL <", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValLessThanOrEqualTo(String value) {
            addCriterion("CDT_VAL <=", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValLike(String value) {
            addCriterion("CDT_VAL like", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValNotLike(String value) {
            addCriterion("CDT_VAL not like", value, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValIn(List<String> values) {
            addCriterion("CDT_VAL in", values, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValNotIn(List<String> values) {
            addCriterion("CDT_VAL not in", values, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValBetween(String value1, String value2) {
            addCriterion("CDT_VAL between", value1, value2, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andCdtValNotBetween(String value1, String value2) {
            addCriterion("CDT_VAL not between", value1, value2, "cdtVal");
            return (Criteria) this;
        }

        public Criteria andIsNotIsNull() {
            addCriterion("IS_NOT is null");
            return (Criteria) this;
        }

        public Criteria andIsNotIsNotNull() {
            addCriterion("IS_NOT is not null");
            return (Criteria) this;
        }

        public Criteria andIsNotEqualTo(Integer value) {
            addCriterion("IS_NOT =", value, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotNotEqualTo(Integer value) {
            addCriterion("IS_NOT <>", value, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotGreaterThan(Integer value) {
            addCriterion("IS_NOT >", value, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_NOT >=", value, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotLessThan(Integer value) {
            addCriterion("IS_NOT <", value, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotLessThanOrEqualTo(Integer value) {
            addCriterion("IS_NOT <=", value, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotIn(List<Integer> values) {
            addCriterion("IS_NOT in", values, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotNotIn(List<Integer> values) {
            addCriterion("IS_NOT not in", values, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_NOT between", value1, value2, "isNot");
            return (Criteria) this;
        }

        public Criteria andIsNotNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_NOT not between", value1, value2, "isNot");
            return (Criteria) this;
        }

        public Criteria andStorIsNull() {
            addCriterion("STOR is null");
            return (Criteria) this;
        }

        public Criteria andStorIsNotNull() {
            addCriterion("STOR is not null");
            return (Criteria) this;
        }

        public Criteria andStorEqualTo(Integer value) {
            addCriterion("STOR =", value, "stor");
            return (Criteria) this;
        }

        public Criteria andStorNotEqualTo(Integer value) {
            addCriterion("STOR <>", value, "stor");
            return (Criteria) this;
        }

        public Criteria andStorGreaterThan(Integer value) {
            addCriterion("STOR >", value, "stor");
            return (Criteria) this;
        }

        public Criteria andStorGreaterThanOrEqualTo(Integer value) {
            addCriterion("STOR >=", value, "stor");
            return (Criteria) this;
        }

        public Criteria andStorLessThan(Integer value) {
            addCriterion("STOR <", value, "stor");
            return (Criteria) this;
        }

        public Criteria andStorLessThanOrEqualTo(Integer value) {
            addCriterion("STOR <=", value, "stor");
            return (Criteria) this;
        }

        public Criteria andStorIn(List<Integer> values) {
            addCriterion("STOR in", values, "stor");
            return (Criteria) this;
        }

        public Criteria andStorNotIn(List<Integer> values) {
            addCriterion("STOR not in", values, "stor");
            return (Criteria) this;
        }

        public Criteria andStorBetween(Integer value1, Integer value2) {
            addCriterion("STOR between", value1, value2, "stor");
            return (Criteria) this;
        }

        public Criteria andStorNotBetween(Integer value1, Integer value2) {
            addCriterion("STOR not between", value1, value2, "stor");
            return (Criteria) this;
        }

        public Criteria andDomainIdIsNull() {
            addCriterion("DOMAIN_ID is null");
            return (Criteria) this;
        }

        public Criteria andDomainIdIsNotNull() {
            addCriterion("DOMAIN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDomainIdEqualTo(Integer value) {
            addCriterion("DOMAIN_ID =", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotEqualTo(Integer value) {
            addCriterion("DOMAIN_ID <>", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdGreaterThan(Integer value) {
            addCriterion("DOMAIN_ID >", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("DOMAIN_ID >=", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdLessThan(Integer value) {
            addCriterion("DOMAIN_ID <", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdLessThanOrEqualTo(Integer value) {
            addCriterion("DOMAIN_ID <=", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdIn(List<String> values) {
            addCriterion("DOMAIN_ID in", values, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotIn(List<Integer> values) {
            addCriterion("DOMAIN_ID not in", values, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdBetween(Integer value1, Integer value2) {
            addCriterion("DOMAIN_ID between", value1, value2, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("DOMAIN_ID not between", value1, value2, "domainId");
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
            addCriterionForJDBCDate("CJSJ =", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ <>", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThan(Date value) {
            addCriterionForJDBCDate("CJSJ >", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ >=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThan(Date value) {
            addCriterionForJDBCDate("CJSJ <", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CJSJ <=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjIn(List<Date> values) {
            addCriterionForJDBCDate("CJSJ in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("CJSJ not in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CJSJ between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CJSJ not between", value1, value2, "cjsj");
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
            addCriterionForJDBCDate("XGSJ =", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("XGSJ <>", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThan(Date value) {
            addCriterionForJDBCDate("XGSJ >", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XGSJ >=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThan(Date value) {
            addCriterionForJDBCDate("XGSJ <", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XGSJ <=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjIn(List<Date> values) {
            addCriterionForJDBCDate("XGSJ in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("XGSJ not in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XGSJ between", value1, value2, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XGSJ not between", value1, value2, "xgsj");
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