package com.integration.generator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltLineExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltLineExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public IomCiRltLineExample() {
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

    protected abstract static class GeneratedCriteria implements Serializable {
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

        public Criteria andStartTypeIdIsNull() {
            addCriterion("START_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdIsNotNull() {
            addCriterion("START_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdEqualTo(String value) {
            addCriterion("START_TYPE_ID =", value, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdNotEqualTo(String value) {
            addCriterion("START_TYPE_ID <>", value, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdGreaterThan(String value) {
            addCriterion("START_TYPE_ID >", value, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("START_TYPE_ID >=", value, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdLessThan(String value) {
            addCriterion("START_TYPE_ID <", value, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdLessThanOrEqualTo(String value) {
            addCriterion("START_TYPE_ID <=", value, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdIn(List<String> values) {
            addCriterion("START_TYPE_ID in", values, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdNotIn(List<String> values) {
            addCriterion("START_TYPE_ID not in", values, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdBetween(String value1, String value2) {
            addCriterion("START_TYPE_ID between", value1, value2, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andStartTypeIdNotBetween(String value1, String value2) {
            addCriterion("START_TYPE_ID not between", value1, value2, "startTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdIsNull() {
            addCriterion("END_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdIsNotNull() {
            addCriterion("END_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdEqualTo(String value) {
            addCriterion("END_TYPE_ID =", value, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdNotEqualTo(String value) {
            addCriterion("END_TYPE_ID <>", value, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdGreaterThan(String value) {
            addCriterion("END_TYPE_ID >", value, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("END_TYPE_ID >=", value, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdLessThan(String value) {
            addCriterion("END_TYPE_ID <", value, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdLessThanOrEqualTo(String value) {
            addCriterion("END_TYPE_ID <=", value, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdIn(List<String> values) {
            addCriterion("END_TYPE_ID in", values, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdNotIn(List<String> values) {
            addCriterion("END_TYPE_ID not in", values, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdBetween(String value1, String value2) {
            addCriterion("END_TYPE_ID between", value1, value2, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andEndTypeIdNotBetween(String value1, String value2) {
            addCriterion("END_TYPE_ID not between", value1, value2, "endTypeId");
            return (Criteria) this;
        }

        public Criteria andRltIdIsNull() {
            addCriterion("RLT_ID is null");
            return (Criteria) this;
        }

        public Criteria andRltIdIsNotNull() {
            addCriterion("RLT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRltIdEqualTo(String value) {
            addCriterion("RLT_ID =", value, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdNotEqualTo(String value) {
            addCriterion("RLT_ID <>", value, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdGreaterThan(String value) {
            addCriterion("RLT_ID >", value, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdGreaterThanOrEqualTo(String value) {
            addCriterion("RLT_ID >=", value, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdLessThan(String value) {
            addCriterion("RLT_ID <", value, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdLessThanOrEqualTo(String value) {
            addCriterion("RLT_ID <=", value, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdIn(List<String> values) {
            addCriterion("RLT_ID in", values, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdNotIn(List<String> values) {
            addCriterion("RLT_ID not in", values, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdBetween(String value1, String value2) {
            addCriterion("RLT_ID between", value1, value2, "rltId");
            return (Criteria) this;
        }

        public Criteria andRltIdNotBetween(String value1, String value2) {
            addCriterion("RLT_ID not between", value1, value2, "rltId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdIsNull() {
            addCriterion("START_NODE_ID is null");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdIsNotNull() {
            addCriterion("START_NODE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdEqualTo(String value) {
            addCriterion("START_NODE_ID =", value, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdNotEqualTo(String value) {
            addCriterion("START_NODE_ID <>", value, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdGreaterThan(String value) {
            addCriterion("START_NODE_ID >", value, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("START_NODE_ID >=", value, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdLessThan(String value) {
            addCriterion("START_NODE_ID <", value, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdLessThanOrEqualTo(String value) {
            addCriterion("START_NODE_ID <=", value, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdIn(List<String> values) {
            addCriterion("START_NODE_ID in", values, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdNotIn(List<String> values) {
            addCriterion("START_NODE_ID not in", values, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdBetween(String value1, String value2) {
            addCriterion("START_NODE_ID between", value1, value2, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andStartNodeIdNotBetween(String value1, String value2) {
            addCriterion("START_NODE_ID not between", value1, value2, "startNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdIsNull() {
            addCriterion("END_NODE_ID is null");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdIsNotNull() {
            addCriterion("END_NODE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdEqualTo(String value) {
            addCriterion("END_NODE_ID =", value, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdNotEqualTo(String value) {
            addCriterion("END_NODE_ID <>", value, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdGreaterThan(String value) {
            addCriterion("END_NODE_ID >", value, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("END_NODE_ID >=", value, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdLessThan(String value) {
            addCriterion("END_NODE_ID <", value, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdLessThanOrEqualTo(String value) {
            addCriterion("END_NODE_ID <=", value, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdIn(List<String> values) {
            addCriterion("END_NODE_ID in", values, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdNotIn(List<String> values) {
            addCriterion("END_NODE_ID not in", values, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdBetween(String value1, String value2) {
            addCriterion("END_NODE_ID between", value1, value2, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andEndNodeIdNotBetween(String value1, String value2) {
            addCriterion("END_NODE_ID not between", value1, value2, "endNodeId");
            return (Criteria) this;
        }

        public Criteria andLineTypeIsNull() {
            addCriterion("LINE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLineTypeIsNotNull() {
            addCriterion("LINE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLineTypeEqualTo(Integer value) {
            addCriterion("LINE_TYPE =", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotEqualTo(Integer value) {
            addCriterion("LINE_TYPE <>", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeGreaterThan(Integer value) {
            addCriterion("LINE_TYPE >", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_TYPE >=", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeLessThan(Integer value) {
            addCriterion("LINE_TYPE <", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_TYPE <=", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeIn(List<Integer> values) {
            addCriterion("LINE_TYPE in", values, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotIn(List<Integer> values) {
            addCriterion("LINE_TYPE not in", values, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeBetween(Integer value1, Integer value2) {
            addCriterion("LINE_TYPE between", value1, value2, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_TYPE not between", value1, value2, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineOpIsNull() {
            addCriterion("LINE_OP is null");
            return (Criteria) this;
        }

        public Criteria andLineOpIsNotNull() {
            addCriterion("LINE_OP is not null");
            return (Criteria) this;
        }

        public Criteria andLineOpEqualTo(String value) {
            addCriterion("LINE_OP =", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpNotEqualTo(String value) {
            addCriterion("LINE_OP <>", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpGreaterThan(String value) {
            addCriterion("LINE_OP >", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_OP >=", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpLessThan(String value) {
            addCriterion("LINE_OP <", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpLessThanOrEqualTo(String value) {
            addCriterion("LINE_OP <=", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpLike(String value) {
            addCriterion("LINE_OP like", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpNotLike(String value) {
            addCriterion("LINE_OP not like", value, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpIn(List<String> values) {
            addCriterion("LINE_OP in", values, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpNotIn(List<String> values) {
            addCriterion("LINE_OP not in", values, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpBetween(String value1, String value2) {
            addCriterion("LINE_OP between", value1, value2, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineOpNotBetween(String value1, String value2) {
            addCriterion("LINE_OP not between", value1, value2, "lineOp");
            return (Criteria) this;
        }

        public Criteria andLineValIsNull() {
            addCriterion("LINE_VAL is null");
            return (Criteria) this;
        }

        public Criteria andLineValIsNotNull() {
            addCriterion("LINE_VAL is not null");
            return (Criteria) this;
        }

        public Criteria andLineValEqualTo(String value) {
            addCriterion("LINE_VAL =", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValNotEqualTo(String value) {
            addCriterion("LINE_VAL <>", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValGreaterThan(String value) {
            addCriterion("LINE_VAL >", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_VAL >=", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValLessThan(String value) {
            addCriterion("LINE_VAL <", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValLessThanOrEqualTo(String value) {
            addCriterion("LINE_VAL <=", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValLike(String value) {
            addCriterion("LINE_VAL like", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValNotLike(String value) {
            addCriterion("LINE_VAL not like", value, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValIn(List<String> values) {
            addCriterion("LINE_VAL in", values, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValNotIn(List<String> values) {
            addCriterion("LINE_VAL not in", values, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValBetween(String value1, String value2) {
            addCriterion("LINE_VAL between", value1, value2, "lineVal");
            return (Criteria) this;
        }

        public Criteria andLineValNotBetween(String value1, String value2) {
            addCriterion("LINE_VAL not between", value1, value2, "lineVal");
            return (Criteria) this;
        }

        public Criteria andOpTypeIsNull() {
            addCriterion("OP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOpTypeIsNotNull() {
            addCriterion("OP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOpTypeEqualTo(Integer value) {
            addCriterion("OP_TYPE =", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotEqualTo(Integer value) {
            addCriterion("OP_TYPE <>", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeGreaterThan(Integer value) {
            addCriterion("OP_TYPE >", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("OP_TYPE >=", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeLessThan(Integer value) {
            addCriterion("OP_TYPE <", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeLessThanOrEqualTo(Integer value) {
            addCriterion("OP_TYPE <=", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeIn(List<Integer> values) {
            addCriterion("OP_TYPE in", values, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotIn(List<Integer> values) {
            addCriterion("OP_TYPE not in", values, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeBetween(Integer value1, Integer value2) {
            addCriterion("OP_TYPE between", value1, value2, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("OP_TYPE not between", value1, value2, "opType");
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

        public Criteria andDomainIdIn(List<Integer> values) {
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

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
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