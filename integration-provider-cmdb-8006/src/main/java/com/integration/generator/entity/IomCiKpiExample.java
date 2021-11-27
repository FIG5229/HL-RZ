package com.integration.generator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiKpiExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiKpiExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public IomCiKpiExample() {
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

        public Criteria andKpiNameIsNull() {
            addCriterion("KPI_NAME is null");
            return (Criteria) this;
        }

        public Criteria andKpiNameIsNotNull() {
            addCriterion("KPI_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andKpiNameEqualTo(String value) {
            addCriterion("KPI_NAME =", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotEqualTo(String value) {
            addCriterion("KPI_NAME <>", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameGreaterThan(String value) {
            addCriterion("KPI_NAME >", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameGreaterThanOrEqualTo(String value) {
            addCriterion("KPI_NAME >=", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameLessThan(String value) {
            addCriterion("KPI_NAME <", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameLessThanOrEqualTo(String value) {
            addCriterion("KPI_NAME <=", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameLike(String value) {
            addCriterion("KPI_NAME like", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotLike(String value) {
            addCriterion("KPI_NAME not like", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameIn(List<String> values) {
            addCriterion("KPI_NAME in", values, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotIn(List<String> values) {
            addCriterion("KPI_NAME not in", values, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameBetween(String value1, String value2) {
            addCriterion("KPI_NAME between", value1, value2, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotBetween(String value1, String value2) {
            addCriterion("KPI_NAME not between", value1, value2, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiAliasIsNull() {
            addCriterion("KPI_ALIAS is null");
            return (Criteria) this;
        }

        public Criteria andKpiAliasIsNotNull() {
            addCriterion("KPI_ALIAS is not null");
            return (Criteria) this;
        }

        public Criteria andKpiAliasEqualTo(String value) {
            addCriterion("KPI_ALIAS =", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasNotEqualTo(String value) {
            addCriterion("KPI_ALIAS <>", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasGreaterThan(String value) {
            addCriterion("KPI_ALIAS >", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasGreaterThanOrEqualTo(String value) {
            addCriterion("KPI_ALIAS >=", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasLessThan(String value) {
            addCriterion("KPI_ALIAS <", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasLessThanOrEqualTo(String value) {
            addCriterion("KPI_ALIAS <=", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasLike(String value) {
            addCriterion("KPI_ALIAS like", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasNotLike(String value) {
            addCriterion("KPI_ALIAS not like", value, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasIn(List<String> values) {
            addCriterion("KPI_ALIAS in", values, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasNotIn(List<String> values) {
            addCriterion("KPI_ALIAS not in", values, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasBetween(String value1, String value2) {
            addCriterion("KPI_ALIAS between", value1, value2, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiAliasNotBetween(String value1, String value2) {
            addCriterion("KPI_ALIAS not between", value1, value2, "kpiAlias");
            return (Criteria) this;
        }

        public Criteria andKpiDescIsNull() {
            addCriterion("KPI_DESC is null");
            return (Criteria) this;
        }

        public Criteria andKpiDescIsNotNull() {
            addCriterion("KPI_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andKpiDescEqualTo(String value) {
            addCriterion("KPI_DESC =", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescNotEqualTo(String value) {
            addCriterion("KPI_DESC <>", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescGreaterThan(String value) {
            addCriterion("KPI_DESC >", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescGreaterThanOrEqualTo(String value) {
            addCriterion("KPI_DESC >=", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescLessThan(String value) {
            addCriterion("KPI_DESC <", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescLessThanOrEqualTo(String value) {
            addCriterion("KPI_DESC <=", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescLike(String value) {
            addCriterion("KPI_DESC like", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescNotLike(String value) {
            addCriterion("KPI_DESC not like", value, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescIn(List<String> values) {
            addCriterion("KPI_DESC in", values, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescNotIn(List<String> values) {
            addCriterion("KPI_DESC not in", values, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescBetween(String value1, String value2) {
            addCriterion("KPI_DESC between", value1, value2, "kpiDesc");
            return (Criteria) this;
        }

        public Criteria andKpiDescNotBetween(String value1, String value2) {
            addCriterion("KPI_DESC not between", value1, value2, "kpiDesc");
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

        public Criteria andKpiClassIdIsNull() {
            addCriterion("KPI_CLASS_ID is null");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdIsNotNull() {
            addCriterion("KPI_CLASS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdEqualTo(String value) {
            addCriterion("KPI_CLASS_ID =", value, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdNotEqualTo(String value) {
            addCriterion("KPI_CLASS_ID <>", value, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdGreaterThan(String value) {
            addCriterion("KPI_CLASS_ID >", value, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdGreaterThanOrEqualTo(String value) {
            addCriterion("KPI_CLASS_ID >=", value, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdLessThan(String value) {
            addCriterion("KPI_CLASS_ID <", value, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdLessThanOrEqualTo(String value) {
            addCriterion("KPI_CLASS_ID <=", value, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdIn(List<String> values) {
            addCriterion("KPI_CLASS_ID in", values, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdNotIn(List<String> values) {
            addCriterion("KPI_CLASS_ID not in", values, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdBetween(String value1, String value2) {
            addCriterion("KPI_CLASS_ID between", value1, value2, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andKpiClassIdNotBetween(String value1, String value2) {
            addCriterion("KPI_CLASS_ID not between", value1, value2, "kpiClassId");
            return (Criteria) this;
        }

        public Criteria andIsMatchIsNull() {
            addCriterion("IS_MATCH is null");
            return (Criteria) this;
        }

        public Criteria andIsMatchIsNotNull() {
            addCriterion("IS_MATCH is not null");
            return (Criteria) this;
        }

        public Criteria andIsMatchEqualTo(Integer value) {
            addCriterion("IS_MATCH =", value, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchNotEqualTo(Integer value) {
            addCriterion("IS_MATCH <>", value, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchGreaterThan(Integer value) {
            addCriterion("IS_MATCH >", value, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_MATCH >=", value, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchLessThan(Integer value) {
            addCriterion("IS_MATCH <", value, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchLessThanOrEqualTo(Integer value) {
            addCriterion("IS_MATCH <=", value, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchIn(List<Integer> values) {
            addCriterion("IS_MATCH in", values, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchNotIn(List<Integer> values) {
            addCriterion("IS_MATCH not in", values, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchBetween(Integer value1, Integer value2) {
            addCriterion("IS_MATCH between", value1, value2, "isMatch");
            return (Criteria) this;
        }

        public Criteria andIsMatchNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_MATCH not between", value1, value2, "isMatch");
            return (Criteria) this;
        }

        public Criteria andValUnitIsNull() {
            addCriterion("VAL_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andValUnitIsNotNull() {
            addCriterion("VAL_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andValUnitEqualTo(String value) {
            addCriterion("VAL_UNIT =", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitNotEqualTo(String value) {
            addCriterion("VAL_UNIT <>", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitGreaterThan(String value) {
            addCriterion("VAL_UNIT >", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitGreaterThanOrEqualTo(String value) {
            addCriterion("VAL_UNIT >=", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitLessThan(String value) {
            addCriterion("VAL_UNIT <", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitLessThanOrEqualTo(String value) {
            addCriterion("VAL_UNIT <=", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitLike(String value) {
            addCriterion("VAL_UNIT like", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitNotLike(String value) {
            addCriterion("VAL_UNIT not like", value, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitIn(List<String> values) {
            addCriterion("VAL_UNIT in", values, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitNotIn(List<String> values) {
            addCriterion("VAL_UNIT not in", values, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitBetween(String value1, String value2) {
            addCriterion("VAL_UNIT between", value1, value2, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValUnitNotBetween(String value1, String value2) {
            addCriterion("VAL_UNIT not between", value1, value2, "valUnit");
            return (Criteria) this;
        }

        public Criteria andValTypeIsNull() {
            addCriterion("VAL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andValTypeIsNotNull() {
            addCriterion("VAL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andValTypeEqualTo(Integer value) {
            addCriterion("VAL_TYPE =", value, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeNotEqualTo(Integer value) {
            addCriterion("VAL_TYPE <>", value, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeGreaterThan(Integer value) {
            addCriterion("VAL_TYPE >", value, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("VAL_TYPE >=", value, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeLessThan(Integer value) {
            addCriterion("VAL_TYPE <", value, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeLessThanOrEqualTo(Integer value) {
            addCriterion("VAL_TYPE <=", value, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeIn(List<Integer> values) {
            addCriterion("VAL_TYPE in", values, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeNotIn(List<Integer> values) {
            addCriterion("VAL_TYPE not in", values, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeBetween(Integer value1, Integer value2) {
            addCriterion("VAL_TYPE between", value1, value2, "valType");
            return (Criteria) this;
        }

        public Criteria andValTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("VAL_TYPE not between", value1, value2, "valType");
            return (Criteria) this;
        }

        public Criteria andValExpIsNull() {
            addCriterion("VAL_EXP is null");
            return (Criteria) this;
        }

        public Criteria andValExpIsNotNull() {
            addCriterion("VAL_EXP is not null");
            return (Criteria) this;
        }

        public Criteria andValExpEqualTo(String value) {
            addCriterion("VAL_EXP =", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpNotEqualTo(String value) {
            addCriterion("VAL_EXP <>", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpGreaterThan(String value) {
            addCriterion("VAL_EXP >", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpGreaterThanOrEqualTo(String value) {
            addCriterion("VAL_EXP >=", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpLessThan(String value) {
            addCriterion("VAL_EXP <", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpLessThanOrEqualTo(String value) {
            addCriterion("VAL_EXP <=", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpLike(String value) {
            addCriterion("VAL_EXP like", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpNotLike(String value) {
            addCriterion("VAL_EXP not like", value, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpIn(List<String> values) {
            addCriterion("VAL_EXP in", values, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpNotIn(List<String> values) {
            addCriterion("VAL_EXP not in", values, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpBetween(String value1, String value2) {
            addCriterion("VAL_EXP between", value1, value2, "valExp");
            return (Criteria) this;
        }

        public Criteria andValExpNotBetween(String value1, String value2) {
            addCriterion("VAL_EXP not between", value1, value2, "valExp");
            return (Criteria) this;
        }

        public Criteria andDwIdIsNull() {
            addCriterion("DW_ID is null");
            return (Criteria) this;
        }

        public Criteria andDwIdIsNotNull() {
            addCriterion("DW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDwIdEqualTo(Integer value) {
            addCriterion("DW_ID =", value, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdNotEqualTo(Integer value) {
            addCriterion("DW_ID <>", value, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdGreaterThan(Integer value) {
            addCriterion("DW_ID >", value, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("DW_ID >=", value, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdLessThan(Integer value) {
            addCriterion("DW_ID <", value, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdLessThanOrEqualTo(Integer value) {
            addCriterion("DW_ID <=", value, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdIn(List<Integer> values) {
            addCriterion("DW_ID in", values, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdNotIn(List<Integer> values) {
            addCriterion("DW_ID not in", values, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdBetween(Integer value1, Integer value2) {
            addCriterion("DW_ID between", value1, value2, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwIdNotBetween(Integer value1, Integer value2) {
            addCriterion("DW_ID not between", value1, value2, "dwId");
            return (Criteria) this;
        }

        public Criteria andDwNameIsNull() {
            addCriterion("DW_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDwNameIsNotNull() {
            addCriterion("DW_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDwNameEqualTo(String value) {
            addCriterion("DW_NAME =", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameNotEqualTo(String value) {
            addCriterion("DW_NAME <>", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameGreaterThan(String value) {
            addCriterion("DW_NAME >", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameGreaterThanOrEqualTo(String value) {
            addCriterion("DW_NAME >=", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameLessThan(String value) {
            addCriterion("DW_NAME <", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameLessThanOrEqualTo(String value) {
            addCriterion("DW_NAME <=", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameLike(String value) {
            addCriterion("DW_NAME like", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameNotLike(String value) {
            addCriterion("DW_NAME not like", value, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameIn(List<String> values) {
            addCriterion("DW_NAME in", values, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameNotIn(List<String> values) {
            addCriterion("DW_NAME not in", values, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameBetween(String value1, String value2) {
            addCriterion("DW_NAME between", value1, value2, "dwName");
            return (Criteria) this;
        }

        public Criteria andDwNameNotBetween(String value1, String value2) {
            addCriterion("DW_NAME not between", value1, value2, "dwName");
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

        public Criteria andSourceIdEqualTo(String value) {
            addCriterion("SOURCE_ID =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(String value) {
            addCriterion("SOURCE_ID <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(String value) {
            addCriterion("SOURCE_ID >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_ID >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(String value) {
            addCriterion("SOURCE_ID <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_ID <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<String> values) {
            addCriterion("SOURCE_ID in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<String> values) {
            addCriterion("SOURCE_ID not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(String value1, String value2) {
            addCriterion("SOURCE_ID between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(String value1, String value2) {
            addCriterion("SOURCE_ID not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andOpIdIsNull() {
            addCriterion("OP_ID is null");
            return (Criteria) this;
        }

        public Criteria andOpIdIsNotNull() {
            addCriterion("OP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOpIdEqualTo(Integer value) {
            addCriterion("OP_ID =", value, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdNotEqualTo(Integer value) {
            addCriterion("OP_ID <>", value, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdGreaterThan(Integer value) {
            addCriterion("OP_ID >", value, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("OP_ID >=", value, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdLessThan(Integer value) {
            addCriterion("OP_ID <", value, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdLessThanOrEqualTo(Integer value) {
            addCriterion("OP_ID <=", value, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdIn(List<Integer> values) {
            addCriterion("OP_ID in", values, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdNotIn(List<Integer> values) {
            addCriterion("OP_ID not in", values, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdBetween(Integer value1, Integer value2) {
            addCriterion("OP_ID between", value1, value2, "opId");
            return (Criteria) this;
        }

        public Criteria andOpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("OP_ID not between", value1, value2, "opId");
            return (Criteria) this;
        }

        public Criteria andIdxFieldIsNull() {
            addCriterion("IDX_FIELD is null");
            return (Criteria) this;
        }

        public Criteria andIdxFieldIsNotNull() {
            addCriterion("IDX_FIELD is not null");
            return (Criteria) this;
        }

        public Criteria andIdxFieldEqualTo(String value) {
            addCriterion("IDX_FIELD =", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldNotEqualTo(String value) {
            addCriterion("IDX_FIELD <>", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldGreaterThan(String value) {
            addCriterion("IDX_FIELD >", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldGreaterThanOrEqualTo(String value) {
            addCriterion("IDX_FIELD >=", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldLessThan(String value) {
            addCriterion("IDX_FIELD <", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldLessThanOrEqualTo(String value) {
            addCriterion("IDX_FIELD <=", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldLike(String value) {
            addCriterion("IDX_FIELD like", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldNotLike(String value) {
            addCriterion("IDX_FIELD not like", value, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldIn(List<String> values) {
            addCriterion("IDX_FIELD in", values, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldNotIn(List<String> values) {
            addCriterion("IDX_FIELD not in", values, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldBetween(String value1, String value2) {
            addCriterion("IDX_FIELD between", value1, value2, "idxField");
            return (Criteria) this;
        }

        public Criteria andIdxFieldNotBetween(String value1, String value2) {
            addCriterion("IDX_FIELD not between", value1, value2, "idxField");
            return (Criteria) this;
        }

        public Criteria andMinimumIsNull() {
            addCriterion("MINIMUM is null");
            return (Criteria) this;
        }

        public Criteria andMinimumIsNotNull() {
            addCriterion("MINIMUM is not null");
            return (Criteria) this;
        }

        public Criteria andMinimumEqualTo(String value) {
            addCriterion("MINIMUM =", value, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumNotEqualTo(String value) {
            addCriterion("MINIMUM <>", value, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumGreaterThan(String value) {
            addCriterion("MINIMUM >", value, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumGreaterThanOrEqualTo(String value) {
            addCriterion("MINIMUM >=", value, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumLessThan(String value) {
            addCriterion("MINIMUM <", value, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumLessThanOrEqualTo(String value) {
            addCriterion("MINIMUM <=", value, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumIn(List<String> values) {
            addCriterion("MINIMUM in", values, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumNotIn(List<String> values) {
            addCriterion("MINIMUM not in", values, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumBetween(String value1, String value2) {
            addCriterion("MINIMUM between", value1, value2, "minimum");
            return (Criteria) this;
        }

        public Criteria andMinimumNotBetween(String value1, String value2) {
            addCriterion("MINIMUM not between", value1, value2, "minimum");
            return (Criteria) this;
        }

        public Criteria andMaximumIsNull() {
            addCriterion("MAXIMUM is null");
            return (Criteria) this;
        }

        public Criteria andMaximumIsNotNull() {
            addCriterion("MAXIMUM is not null");
            return (Criteria) this;
        }

        public Criteria andMaximumEqualTo(String value) {
            addCriterion("MAXIMUM =", value, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumNotEqualTo(String value) {
            addCriterion("MAXIMUM <>", value, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumGreaterThan(String value) {
            addCriterion("MAXIMUM >", value, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumGreaterThanOrEqualTo(String value) {
            addCriterion("MAXIMUM >=", value, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumLessThan(String value) {
            addCriterion("MAXIMUM <", value, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumLessThanOrEqualTo(String value) {
            addCriterion("MAXIMUM <=", value, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumIn(List<String> values) {
            addCriterion("MAXIMUM in", values, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumNotIn(List<String> values) {
            addCriterion("MAXIMUM not in", values, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumBetween(String value1, String value2) {
            addCriterion("MAXIMUM between", value1, value2, "maximum");
            return (Criteria) this;
        }

        public Criteria andMaximumNotBetween(String value1, String value2) {
            addCriterion("MAXIMUM not between", value1, value2, "maximum");
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