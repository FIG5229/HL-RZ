package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IomCiRltNodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiRltNodeExample() {
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

        public Criteria andRuleIdIsNull() {
            addCriterion("RULE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNotNull() {
            addCriterion("RULE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIdEqualTo(String value) {
            addCriterion("RULE_ID =", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotEqualTo(String value) {
            addCriterion("RULE_ID <>", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThan(String value) {
            addCriterion("RULE_ID >", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThanOrEqualTo(String value) {
            addCriterion("RULE_ID >=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThan(String value) {
            addCriterion("RULE_ID <", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThanOrEqualTo(String value) {
            addCriterion("RULE_ID <=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIn(List<String> values) {
            addCriterion("RULE_ID in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotIn(List<String> values) {
            addCriterion("RULE_ID not in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdBetween(String value1, String value2) {
            addCriterion("RULE_ID between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotBetween(String value1, String value2) {
            addCriterion("RULE_ID not between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRltTypeIsNull() {
            addCriterion("RLT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRltTypeIsNotNull() {
            addCriterion("RLT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRltTypeEqualTo(Integer value) {
            addCriterion("RLT_TYPE =", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeNotEqualTo(Integer value) {
            addCriterion("RLT_TYPE <>", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeGreaterThan(Integer value) {
            addCriterion("RLT_TYPE >", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("RLT_TYPE >=", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeLessThan(Integer value) {
            addCriterion("RLT_TYPE <", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeLessThanOrEqualTo(Integer value) {
            addCriterion("RLT_TYPE <=", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeIn(List<Integer> values) {
            addCriterion("RLT_TYPE in", values, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeNotIn(List<Integer> values) {
            addCriterion("RLT_TYPE not in", values, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeBetween(Integer value1, Integer value2) {
            addCriterion("RLT_TYPE between", value1, value2, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("RLT_TYPE not between", value1, value2, "rltType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIsNull() {
            addCriterion("NODE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIsNotNull() {
            addCriterion("NODE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andNodeTypeEqualTo(Integer value) {
            addCriterion("NODE_TYPE =", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotEqualTo(Integer value) {
            addCriterion("NODE_TYPE <>", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeGreaterThan(Integer value) {
            addCriterion("NODE_TYPE >", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("NODE_TYPE >=", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeLessThan(Integer value) {
            addCriterion("NODE_TYPE <", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("NODE_TYPE <=", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIn(List<Integer> values) {
            addCriterion("NODE_TYPE in", values, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotIn(List<Integer> values) {
            addCriterion("NODE_TYPE not in", values, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeBetween(Integer value1, Integer value2) {
            addCriterion("NODE_TYPE between", value1, value2, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("NODE_TYPE not between", value1, value2, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeLogicIsNull() {
            addCriterion("NODE_LOGIC is null");
            return (Criteria) this;
        }

        public Criteria andNodeLogicIsNotNull() {
            addCriterion("NODE_LOGIC is not null");
            return (Criteria) this;
        }

        public Criteria andNodeLogicEqualTo(Integer value) {
            addCriterion("NODE_LOGIC =", value, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicNotEqualTo(Integer value) {
            addCriterion("NODE_LOGIC <>", value, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicGreaterThan(Integer value) {
            addCriterion("NODE_LOGIC >", value, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicGreaterThanOrEqualTo(Integer value) {
            addCriterion("NODE_LOGIC >=", value, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicLessThan(Integer value) {
            addCriterion("NODE_LOGIC <", value, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicLessThanOrEqualTo(Integer value) {
            addCriterion("NODE_LOGIC <=", value, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicIn(List<Integer> values) {
            addCriterion("NODE_LOGIC in", values, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicNotIn(List<Integer> values) {
            addCriterion("NODE_LOGIC not in", values, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicBetween(Integer value1, Integer value2) {
            addCriterion("NODE_LOGIC between", value1, value2, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andNodeLogicNotBetween(Integer value1, Integer value2) {
            addCriterion("NODE_LOGIC not between", value1, value2, "nodeLogic");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(String value) {
            addCriterion("TYPE_ID =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(String value) {
            addCriterion("TYPE_ID <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(String value) {
            addCriterion("TYPE_ID >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE_ID >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(String value) {
            addCriterion("TYPE_ID <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(String value) {
            addCriterion("TYPE_ID <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<String> values) {
            addCriterion("TYPE_ID in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<String> values) {
            addCriterion("TYPE_ID not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(String value1, String value2) {
            addCriterion("TYPE_ID between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(String value1, String value2) {
            addCriterion("TYPE_ID not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdIsNull() {
            addCriterion("NODE_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdIsNotNull() {
            addCriterion("NODE_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdEqualTo(String value) {
            addCriterion("NODE_TYPE_ID =", value, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdNotEqualTo(String value) {
            addCriterion("NODE_TYPE_ID <>", value, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdGreaterThan(String value) {
            addCriterion("NODE_TYPE_ID >", value, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("NODE_TYPE_ID >=", value, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdLessThan(String value) {
            addCriterion("NODE_TYPE_ID <", value, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdLessThanOrEqualTo(String value) {
            addCriterion("NODE_TYPE_ID <=", value, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdIn(List<String> values) {
            addCriterion("NODE_TYPE_ID in", values, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdNotIn(List<String> values) {
            addCriterion("NODE_TYPE_ID not in", values, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdBetween(String value1, String value2) {
            addCriterion("NODE_TYPE_ID between", value1, value2, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIdNotBetween(String value1, String value2) {
            addCriterion("NODE_TYPE_ID not between", value1, value2, "nodeTypeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdIsNull() {
            addCriterion("PAGE_NODE_ID is null");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdIsNotNull() {
            addCriterion("PAGE_NODE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdEqualTo(String value) {
            addCriterion("PAGE_NODE_ID =", value, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdNotEqualTo(String value) {
            addCriterion("PAGE_NODE_ID <>", value, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdGreaterThan(String value) {
            addCriterion("PAGE_NODE_ID >", value, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("PAGE_NODE_ID >=", value, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdLessThan(String value) {
            addCriterion("PAGE_NODE_ID <", value, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdLessThanOrEqualTo(String value) {
            addCriterion("PAGE_NODE_ID <=", value, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdIn(List<String> values) {
            addCriterion("PAGE_NODE_ID in", values, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdNotIn(List<String> values) {
            addCriterion("PAGE_NODE_ID not in", values, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdBetween(String value1, String value2) {
            addCriterion("PAGE_NODE_ID between", value1, value2, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andPageNodeIdNotBetween(String value1, String value2) {
            addCriterion("PAGE_NODE_ID not between", value1, value2, "pageNodeId");
            return (Criteria) this;
        }

        public Criteria andXIsNull() {
            addCriterion("X is null");
            return (Criteria) this;
        }

        public Criteria andXIsNotNull() {
            addCriterion("X is not null");
            return (Criteria) this;
        }

        public Criteria andXEqualTo(Integer value) {
            addCriterion("X =", value, "x");
            return (Criteria) this;
        }

        public Criteria andXNotEqualTo(Integer value) {
            addCriterion("X <>", value, "x");
            return (Criteria) this;
        }

        public Criteria andXGreaterThan(Integer value) {
            addCriterion("X >", value, "x");
            return (Criteria) this;
        }

        public Criteria andXGreaterThanOrEqualTo(Integer value) {
            addCriterion("X >=", value, "x");
            return (Criteria) this;
        }

        public Criteria andXLessThan(Integer value) {
            addCriterion("X <", value, "x");
            return (Criteria) this;
        }

        public Criteria andXLessThanOrEqualTo(Integer value) {
            addCriterion("X <=", value, "x");
            return (Criteria) this;
        }

        public Criteria andXIn(List<Integer> values) {
            addCriterion("X in", values, "x");
            return (Criteria) this;
        }

        public Criteria andXNotIn(List<Integer> values) {
            addCriterion("X not in", values, "x");
            return (Criteria) this;
        }

        public Criteria andXBetween(Integer value1, Integer value2) {
            addCriterion("X between", value1, value2, "x");
            return (Criteria) this;
        }

        public Criteria andXNotBetween(Integer value1, Integer value2) {
            addCriterion("X not between", value1, value2, "x");
            return (Criteria) this;
        }

        public Criteria andYIsNull() {
            addCriterion("Y is null");
            return (Criteria) this;
        }

        public Criteria andYIsNotNull() {
            addCriterion("Y is not null");
            return (Criteria) this;
        }

        public Criteria andYEqualTo(Integer value) {
            addCriterion("Y =", value, "y");
            return (Criteria) this;
        }

        public Criteria andYNotEqualTo(Integer value) {
            addCriterion("Y <>", value, "y");
            return (Criteria) this;
        }

        public Criteria andYGreaterThan(Integer value) {
            addCriterion("Y >", value, "y");
            return (Criteria) this;
        }

        public Criteria andYGreaterThanOrEqualTo(Integer value) {
            addCriterion("Y >=", value, "y");
            return (Criteria) this;
        }

        public Criteria andYLessThan(Integer value) {
            addCriterion("Y <", value, "y");
            return (Criteria) this;
        }

        public Criteria andYLessThanOrEqualTo(Integer value) {
            addCriterion("Y <=", value, "y");
            return (Criteria) this;
        }

        public Criteria andYIn(List<Integer> values) {
            addCriterion("Y in", values, "y");
            return (Criteria) this;
        }

        public Criteria andYNotIn(List<Integer> values) {
            addCriterion("Y not in", values, "y");
            return (Criteria) this;
        }

        public Criteria andYBetween(Integer value1, Integer value2) {
            addCriterion("Y between", value1, value2, "y");
            return (Criteria) this;
        }

        public Criteria andYNotBetween(Integer value1, Integer value2) {
            addCriterion("Y not between", value1, value2, "y");
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