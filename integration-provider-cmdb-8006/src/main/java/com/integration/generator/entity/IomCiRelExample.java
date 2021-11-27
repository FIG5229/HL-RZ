package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRelExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiRelExample() {
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

        public Criteria andLineBmIsNull() {
            addCriterion("LINE_BM is null");
            return (Criteria) this;
        }

        public Criteria andLineBmIsNotNull() {
            addCriterion("LINE_BM is not null");
            return (Criteria) this;
        }

        public Criteria andLineBmEqualTo(String value) {
            addCriterion("LINE_BM =", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmNotEqualTo(String value) {
            addCriterion("LINE_BM <>", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmGreaterThan(String value) {
            addCriterion("LINE_BM >", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_BM >=", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmLessThan(String value) {
            addCriterion("LINE_BM <", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmLessThanOrEqualTo(String value) {
            addCriterion("LINE_BM <=", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmLike(String value) {
            addCriterion("LINE_BM like", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmNotLike(String value) {
            addCriterion("LINE_BM not like", value, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmIn(List<String> values) {
            addCriterion("LINE_BM in", values, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmNotIn(List<String> values) {
            addCriterion("LINE_BM not in", values, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmBetween(String value1, String value2) {
            addCriterion("LINE_BM between", value1, value2, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineBmNotBetween(String value1, String value2) {
            addCriterion("LINE_BM not between", value1, value2, "lineBm");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNull() {
            addCriterion("LINE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNotNull() {
            addCriterion("LINE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLineNameEqualTo(String value) {
            addCriterion("LINE_NAME =", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andDomainIdEqualTo(String value) {
            addCriterion("DOMAIN_ID =", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdIn(List<String> value) {
            addCriterion("DOMAIN_ID in", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andLineNameNotEqualTo(String value) {
            addCriterion("LINE_NAME <>", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThan(String value) {
            addCriterion("LINE_NAME >", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_NAME >=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThan(String value) {
            addCriterion("LINE_NAME <", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThanOrEqualTo(String value) {
            addCriterion("LINE_NAME <=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLike(String value) {
            addCriterion("LINE_NAME like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotLike(String value) {
            addCriterion("LINE_NAME not like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameIn(List<String> values) {
            addCriterion("LINE_NAME in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotIn(List<String> values) {
            addCriterion("LINE_NAME not in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameBetween(String value1, String value2) {
            addCriterion("LINE_NAME between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotBetween(String value1, String value2) {
            addCriterion("LINE_NAME not between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineStdBmIsNull() {
            addCriterion("LINE_STD_BM is null");
            return (Criteria) this;
        }

        public Criteria andLineStdBmIsNotNull() {
            addCriterion("LINE_STD_BM is not null");
            return (Criteria) this;
        }

        public Criteria andLineStdBmEqualTo(String value) {
            addCriterion("LINE_STD_BM =", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmNotEqualTo(String value) {
            addCriterion("LINE_STD_BM <>", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmGreaterThan(String value) {
            addCriterion("LINE_STD_BM >", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_STD_BM >=", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmLessThan(String value) {
            addCriterion("LINE_STD_BM <", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmLessThanOrEqualTo(String value) {
            addCriterion("LINE_STD_BM <=", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmLike(String value) {
            addCriterion("LINE_STD_BM like", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmNotLike(String value) {
            addCriterion("LINE_STD_BM not like", value, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmIn(List<String> values) {
            addCriterion("LINE_STD_BM in", values, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmNotIn(List<String> values) {
            addCriterion("LINE_STD_BM not in", values, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmBetween(String value1, String value2) {
            addCriterion("LINE_STD_BM between", value1, value2, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineStdBmNotBetween(String value1, String value2) {
            addCriterion("LINE_STD_BM not between", value1, value2, "lineStdBm");
            return (Criteria) this;
        }

        public Criteria andLineDescIsNull() {
            addCriterion("LINE_DESC is null");
            return (Criteria) this;
        }

        public Criteria andLineDescIsNotNull() {
            addCriterion("LINE_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andLineDescEqualTo(String value) {
            addCriterion("LINE_DESC =", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescNotEqualTo(String value) {
            addCriterion("LINE_DESC <>", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescGreaterThan(String value) {
            addCriterion("LINE_DESC >", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_DESC >=", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescLessThan(String value) {
            addCriterion("LINE_DESC <", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescLessThanOrEqualTo(String value) {
            addCriterion("LINE_DESC <=", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescLike(String value) {
            addCriterion("LINE_DESC like", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescNotLike(String value) {
            addCriterion("LINE_DESC not like", value, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescIn(List<String> values) {
            addCriterion("LINE_DESC in", values, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescNotIn(List<String> values) {
            addCriterion("LINE_DESC not in", values, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescBetween(String value1, String value2) {
            addCriterion("LINE_DESC between", value1, value2, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andLineDescNotBetween(String value1, String value2) {
            addCriterion("LINE_DESC not between", value1, value2, "lineDesc");
            return (Criteria) this;
        }

        public Criteria andParentLineIdIsNull() {
            addCriterion("PARENT_LINE_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentLineIdIsNotNull() {
            addCriterion("PARENT_LINE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentLineIdEqualTo(String value) {
            addCriterion("PARENT_LINE_ID =", value, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdNotEqualTo(String value) {
            addCriterion("PARENT_LINE_ID <>", value, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdGreaterThan(String value) {
            addCriterion("PARENT_LINE_ID >", value, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_LINE_ID >=", value, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdLessThan(String value) {
            addCriterion("PARENT_LINE_ID <", value, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_LINE_ID <=", value, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdIn(List<String> values) {
            addCriterion("PARENT_LINE_ID in", values, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdNotIn(List<String> values) {
            addCriterion("PARENT_LINE_ID not in", values, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdBetween(String value1, String value2) {
            addCriterion("PARENT_LINE_ID between", value1, value2, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andParentLineIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_LINE_ID not between", value1, value2, "parentLineId");
            return (Criteria) this;
        }

        public Criteria andLineLvlIsNull() {
            addCriterion("LINE_LVL is null");
            return (Criteria) this;
        }

        public Criteria andLineLvlIsNotNull() {
            addCriterion("LINE_LVL is not null");
            return (Criteria) this;
        }

        public Criteria andLineLvlEqualTo(Integer value) {
            addCriterion("LINE_LVL =", value, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlNotEqualTo(Integer value) {
            addCriterion("LINE_LVL <>", value, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlGreaterThan(Integer value) {
            addCriterion("LINE_LVL >", value, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_LVL >=", value, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlLessThan(Integer value) {
            addCriterion("LINE_LVL <", value, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_LVL <=", value, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlIn(List<Integer> values) {
            addCriterion("LINE_LVL in", values, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlNotIn(List<Integer> values) {
            addCriterion("LINE_LVL not in", values, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlBetween(Integer value1, Integer value2) {
            addCriterion("LINE_LVL between", value1, value2, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLineLvlNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_LVL not between", value1, value2, "lineLvl");
            return (Criteria) this;
        }

        public Criteria andLinePathIsNull() {
            addCriterion("LINE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andLinePathIsNotNull() {
            addCriterion("LINE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andLinePathEqualTo(String value) {
            addCriterion("LINE_PATH =", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathNotEqualTo(String value) {
            addCriterion("LINE_PATH <>", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathGreaterThan(String value) {
            addCriterion("LINE_PATH >", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_PATH >=", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathLessThan(String value) {
            addCriterion("LINE_PATH <", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathLessThanOrEqualTo(String value) {
            addCriterion("LINE_PATH <=", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathLike(String value) {
            addCriterion("LINE_PATH like", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathNotLike(String value) {
            addCriterion("LINE_PATH not like", value, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathIn(List<String> values) {
            addCriterion("LINE_PATH in", values, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathNotIn(List<String> values) {
            addCriterion("LINE_PATH not in", values, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathBetween(String value1, String value2) {
            addCriterion("LINE_PATH between", value1, value2, "linePath");
            return (Criteria) this;
        }

        public Criteria andLinePathNotBetween(String value1, String value2) {
            addCriterion("LINE_PATH not between", value1, value2, "linePath");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("SORT not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andLineCostIsNull() {
            addCriterion("LINE_COST is null");
            return (Criteria) this;
        }

        public Criteria andLineCostIsNotNull() {
            addCriterion("LINE_COST is not null");
            return (Criteria) this;
        }

        public Criteria andLineCostEqualTo(Integer value) {
            addCriterion("LINE_COST =", value, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostNotEqualTo(Integer value) {
            addCriterion("LINE_COST <>", value, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostGreaterThan(Integer value) {
            addCriterion("LINE_COST >", value, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_COST >=", value, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostLessThan(Integer value) {
            addCriterion("LINE_COST <", value, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_COST <=", value, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostIn(List<Integer> values) {
            addCriterion("LINE_COST in", values, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostNotIn(List<Integer> values) {
            addCriterion("LINE_COST not in", values, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostBetween(Integer value1, Integer value2) {
            addCriterion("LINE_COST between", value1, value2, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineCostNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_COST not between", value1, value2, "lineCost");
            return (Criteria) this;
        }

        public Criteria andLineStyleIsNull() {
            addCriterion("LINE_STYLE is null");
            return (Criteria) this;
        }

        public Criteria andLineStyleIsNotNull() {
            addCriterion("LINE_STYLE is not null");
            return (Criteria) this;
        }

        public Criteria andLineStyleEqualTo(Integer value) {
            addCriterion("LINE_STYLE =", value, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleNotEqualTo(Integer value) {
            addCriterion("LINE_STYLE <>", value, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleGreaterThan(Integer value) {
            addCriterion("LINE_STYLE >", value, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_STYLE >=", value, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleLessThan(Integer value) {
            addCriterion("LINE_STYLE <", value, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_STYLE <=", value, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleIn(List<Integer> values) {
            addCriterion("LINE_STYLE in", values, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleNotIn(List<Integer> values) {
            addCriterion("LINE_STYLE not in", values, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleBetween(Integer value1, Integer value2) {
            addCriterion("LINE_STYLE between", value1, value2, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_STYLE not between", value1, value2, "lineStyle");
            return (Criteria) this;
        }

        public Criteria andLineWidthIsNull() {
            addCriterion("LINE_WIDTH is null");
            return (Criteria) this;
        }

        public Criteria andLineWidthIsNotNull() {
            addCriterion("LINE_WIDTH is not null");
            return (Criteria) this;
        }

        public Criteria andLineWidthEqualTo(Integer value) {
            addCriterion("LINE_WIDTH =", value, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthNotEqualTo(Integer value) {
            addCriterion("LINE_WIDTH <>", value, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthGreaterThan(Integer value) {
            addCriterion("LINE_WIDTH >", value, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_WIDTH >=", value, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthLessThan(Integer value) {
            addCriterion("LINE_WIDTH <", value, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_WIDTH <=", value, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthIn(List<Integer> values) {
            addCriterion("LINE_WIDTH in", values, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthNotIn(List<Integer> values) {
            addCriterion("LINE_WIDTH not in", values, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthBetween(Integer value1, Integer value2) {
            addCriterion("LINE_WIDTH between", value1, value2, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_WIDTH not between", value1, value2, "lineWidth");
            return (Criteria) this;
        }

        public Criteria andLineColorIsNull() {
            addCriterion("LINE_COLOR is null");
            return (Criteria) this;
        }

        public Criteria andLineColorIsNotNull() {
            addCriterion("LINE_COLOR is not null");
            return (Criteria) this;
        }

        public Criteria andLineColorEqualTo(String value) {
            addCriterion("LINE_COLOR =", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorNotEqualTo(String value) {
            addCriterion("LINE_COLOR <>", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorGreaterThan(String value) {
            addCriterion("LINE_COLOR >", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorGreaterThanOrEqualTo(String value) {
            addCriterion("LINE_COLOR >=", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorLessThan(String value) {
            addCriterion("LINE_COLOR <", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorLessThanOrEqualTo(String value) {
            addCriterion("LINE_COLOR <=", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorLike(String value) {
            addCriterion("LINE_COLOR like", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorNotLike(String value) {
            addCriterion("LINE_COLOR not like", value, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorIn(List<String> values) {
            addCriterion("LINE_COLOR in", values, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorNotIn(List<String> values) {
            addCriterion("LINE_COLOR not in", values, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorBetween(String value1, String value2) {
            addCriterion("LINE_COLOR between", value1, value2, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineColorNotBetween(String value1, String value2) {
            addCriterion("LINE_COLOR not between", value1, value2, "lineColor");
            return (Criteria) this;
        }

        public Criteria andLineArrorIsNull() {
            addCriterion("LINE_ARROR is null");
            return (Criteria) this;
        }

        public Criteria andLineArrorIsNotNull() {
            addCriterion("LINE_ARROR is not null");
            return (Criteria) this;
        }

        public Criteria andLineArrorEqualTo(Integer value) {
            addCriterion("LINE_ARROR =", value, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorNotEqualTo(Integer value) {
            addCriterion("LINE_ARROR <>", value, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorGreaterThan(Integer value) {
            addCriterion("LINE_ARROR >", value, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_ARROR >=", value, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorLessThan(Integer value) {
            addCriterion("LINE_ARROR <", value, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_ARROR <=", value, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorIn(List<Integer> values) {
            addCriterion("LINE_ARROR in", values, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorNotIn(List<Integer> values) {
            addCriterion("LINE_ARROR not in", values, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorBetween(Integer value1, Integer value2) {
            addCriterion("LINE_ARROR between", value1, value2, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineArrorNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_ARROR not between", value1, value2, "lineArror");
            return (Criteria) this;
        }

        public Criteria andLineAnimeIsNull() {
            addCriterion("LINE_ANIME is null");
            return (Criteria) this;
        }

        public Criteria andLineAnimeIsNotNull() {
            addCriterion("LINE_ANIME is not null");
            return (Criteria) this;
        }

        public Criteria andLineAnimeEqualTo(Integer value) {
            addCriterion("LINE_ANIME =", value, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeNotEqualTo(Integer value) {
            addCriterion("LINE_ANIME <>", value, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeGreaterThan(Integer value) {
            addCriterion("LINE_ANIME >", value, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINE_ANIME >=", value, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeLessThan(Integer value) {
            addCriterion("LINE_ANIME <", value, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeLessThanOrEqualTo(Integer value) {
            addCriterion("LINE_ANIME <=", value, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeIn(List<Integer> values) {
            addCriterion("LINE_ANIME in", values, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeNotIn(List<Integer> values) {
            addCriterion("LINE_ANIME not in", values, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeBetween(Integer value1, Integer value2) {
            addCriterion("LINE_ANIME between", value1, value2, "lineAnime");
            return (Criteria) this;
        }

        public Criteria andLineAnimeNotBetween(Integer value1, Integer value2) {
            addCriterion("LINE_ANIME not between", value1, value2, "lineAnime");
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

        public Criteria andCjrIdEqualTo(Long value) {
            addCriterion("CJR_ID =", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotEqualTo(Long value) {
            addCriterion("CJR_ID <>", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdGreaterThan(Long value) {
            addCriterion("CJR_ID >", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CJR_ID >=", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdLessThan(Long value) {
            addCriterion("CJR_ID <", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdLessThanOrEqualTo(Long value) {
            addCriterion("CJR_ID <=", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdIn(List<Long> values) {
            addCriterion("CJR_ID in", values, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotIn(List<Long> values) {
            addCriterion("CJR_ID not in", values, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdBetween(Long value1, Long value2) {
            addCriterion("CJR_ID between", value1, value2, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotBetween(Long value1, Long value2) {
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

        public Criteria andXgrIdEqualTo(Long value) {
            addCriterion("XGR_ID =", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotEqualTo(Long value) {
            addCriterion("XGR_ID <>", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdGreaterThan(Long value) {
            addCriterion("XGR_ID >", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("XGR_ID >=", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdLessThan(Long value) {
            addCriterion("XGR_ID <", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdLessThanOrEqualTo(Long value) {
            addCriterion("XGR_ID <=", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdIn(List<Long> values) {
            addCriterion("XGR_ID in", values, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotIn(List<Long> values) {
            addCriterion("XGR_ID not in", values, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdBetween(Long value1, Long value2) {
            addCriterion("XGR_ID between", value1, value2, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotBetween(Long value1, Long value2) {
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