package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiIconExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiIconExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiIconExample() {
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

        public Criteria andIconNameIsNull() {
            addCriterion("ICON_NAME is null");
            return (Criteria) this;
        }

        public Criteria andIconNameIsNotNull() {
            addCriterion("ICON_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andIconNameEqualTo(String value) {
            addCriterion("ICON_NAME =", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotEqualTo(String value) {
            addCriterion("ICON_NAME <>", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameGreaterThan(String value) {
            addCriterion("ICON_NAME >", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_NAME >=", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameLessThan(String value) {
            addCriterion("ICON_NAME <", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameLessThanOrEqualTo(String value) {
            addCriterion("ICON_NAME <=", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameLike(String value) {
            addCriterion("ICON_NAME like", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotLike(String value) {
            addCriterion("ICON_NAME not like", value, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameIn(List<String> values) {
            addCriterion("ICON_NAME in", values, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotIn(List<String> values) {
            addCriterion("ICON_NAME not in", values, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameBetween(String value1, String value2) {
            addCriterion("ICON_NAME between", value1, value2, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconNameNotBetween(String value1, String value2) {
            addCriterion("ICON_NAME not between", value1, value2, "iconName");
            return (Criteria) this;
        }

        public Criteria andIconDirIsNull() {
            addCriterion("ICON_DIR is null");
            return (Criteria) this;
        }

        public Criteria andIconDirIsNotNull() {
            addCriterion("ICON_DIR is not null");
            return (Criteria) this;
        }

        public Criteria andIconDirEqualTo(String value) {
            addCriterion("ICON_DIR =", value, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirNotEqualTo(String value) {
            addCriterion("ICON_DIR <>", value, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirGreaterThan(String value) {
            addCriterion("ICON_DIR >", value, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_DIR >=", value, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirLessThan(String value) {
            addCriterion("ICON_DIR <", value, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirLessThanOrEqualTo(String value) {
            addCriterion("ICON_DIR <=", value, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirIn(List<String> values) {
            addCriterion("ICON_DIR in", values, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirNotIn(List<String> values) {
            addCriterion("ICON_DIR not in", values, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirBetween(String value1, String value2) {
            addCriterion("ICON_DIR between", value1, value2, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconDirNotBetween(String value1, String value2) {
            addCriterion("ICON_DIR not between", value1, value2, "iconDir");
            return (Criteria) this;
        }

        public Criteria andIconTypeIsNull() {
            addCriterion("ICON_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andIconTypeIsNotNull() {
            addCriterion("ICON_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andIconTypeEqualTo(Integer value) {
            addCriterion("ICON_TYPE =", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotEqualTo(Integer value) {
            addCriterion("ICON_TYPE <>", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeGreaterThan(Integer value) {
            addCriterion("ICON_TYPE >", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ICON_TYPE >=", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeLessThan(Integer value) {
            addCriterion("ICON_TYPE <", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ICON_TYPE <=", value, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeIn(List<Integer> values) {
            addCriterion("ICON_TYPE in", values, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotIn(List<Integer> values) {
            addCriterion("ICON_TYPE not in", values, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeBetween(Integer value1, Integer value2) {
            addCriterion("ICON_TYPE between", value1, value2, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ICON_TYPE not between", value1, value2, "iconType");
            return (Criteria) this;
        }

        public Criteria andIconDescIsNull() {
            addCriterion("ICON_DESC is null");
            return (Criteria) this;
        }

        public Criteria andIconDescIsNotNull() {
            addCriterion("ICON_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andIconDescEqualTo(String value) {
            addCriterion("ICON_DESC =", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescNotEqualTo(String value) {
            addCriterion("ICON_DESC <>", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescGreaterThan(String value) {
            addCriterion("ICON_DESC >", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_DESC >=", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescLessThan(String value) {
            addCriterion("ICON_DESC <", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescLessThanOrEqualTo(String value) {
            addCriterion("ICON_DESC <=", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescLike(String value) {
            addCriterion("ICON_DESC like", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescNotLike(String value) {
            addCriterion("ICON_DESC not like", value, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescIn(List<String> values) {
            addCriterion("ICON_DESC in", values, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescNotIn(List<String> values) {
            addCriterion("ICON_DESC not in", values, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescBetween(String value1, String value2) {
            addCriterion("ICON_DESC between", value1, value2, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconDescNotBetween(String value1, String value2) {
            addCriterion("ICON_DESC not between", value1, value2, "iconDesc");
            return (Criteria) this;
        }

        public Criteria andIconFormIsNull() {
            addCriterion("ICON_FORM is null");
            return (Criteria) this;
        }

        public Criteria andIconFormIsNotNull() {
            addCriterion("ICON_FORM is not null");
            return (Criteria) this;
        }

        public Criteria andIconFormEqualTo(String value) {
            addCriterion("ICON_FORM =", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormNotEqualTo(String value) {
            addCriterion("ICON_FORM <>", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormGreaterThan(String value) {
            addCriterion("ICON_FORM >", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_FORM >=", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormLessThan(String value) {
            addCriterion("ICON_FORM <", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormLessThanOrEqualTo(String value) {
            addCriterion("ICON_FORM <=", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormLike(String value) {
            addCriterion("ICON_FORM like", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormNotLike(String value) {
            addCriterion("ICON_FORM not like", value, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormIn(List<String> values) {
            addCriterion("ICON_FORM in", values, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormNotIn(List<String> values) {
            addCriterion("ICON_FORM not in", values, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormBetween(String value1, String value2) {
            addCriterion("ICON_FORM between", value1, value2, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconFormNotBetween(String value1, String value2) {
            addCriterion("ICON_FORM not between", value1, value2, "iconForm");
            return (Criteria) this;
        }

        public Criteria andIconColorIsNull() {
            addCriterion("ICON_COLOR is null");
            return (Criteria) this;
        }

        public Criteria andIconColorIsNotNull() {
            addCriterion("ICON_COLOR is not null");
            return (Criteria) this;
        }

        public Criteria andIconColorEqualTo(String value) {
            addCriterion("ICON_COLOR =", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorNotEqualTo(String value) {
            addCriterion("ICON_COLOR <>", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorGreaterThan(String value) {
            addCriterion("ICON_COLOR >", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_COLOR >=", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorLessThan(String value) {
            addCriterion("ICON_COLOR <", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorLessThanOrEqualTo(String value) {
            addCriterion("ICON_COLOR <=", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorLike(String value) {
            addCriterion("ICON_COLOR like", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorNotLike(String value) {
            addCriterion("ICON_COLOR not like", value, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorIn(List<String> values) {
            addCriterion("ICON_COLOR in", values, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorNotIn(List<String> values) {
            addCriterion("ICON_COLOR not in", values, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorBetween(String value1, String value2) {
            addCriterion("ICON_COLOR between", value1, value2, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconColorNotBetween(String value1, String value2) {
            addCriterion("ICON_COLOR not between", value1, value2, "iconColor");
            return (Criteria) this;
        }

        public Criteria andIconPathIsNull() {
            addCriterion("ICON_PATH is null");
            return (Criteria) this;
        }

        public Criteria andIconPathIsNotNull() {
            addCriterion("ICON_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andIconPathEqualTo(String value) {
            addCriterion("ICON_PATH =", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathNotEqualTo(String value) {
            addCriterion("ICON_PATH <>", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathGreaterThan(String value) {
            addCriterion("ICON_PATH >", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathGreaterThanOrEqualTo(String value) {
            addCriterion("ICON_PATH >=", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathLessThan(String value) {
            addCriterion("ICON_PATH <", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathLessThanOrEqualTo(String value) {
            addCriterion("ICON_PATH <=", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathLike(String value) {
            addCriterion("ICON_PATH like", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathNotLike(String value) {
            addCriterion("ICON_PATH not like", value, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathIn(List<String> values) {
            addCriterion("ICON_PATH in", values, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathNotIn(List<String> values) {
            addCriterion("ICON_PATH not in", values, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathBetween(String value1, String value2) {
            addCriterion("ICON_PATH between", value1, value2, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconPathNotBetween(String value1, String value2) {
            addCriterion("ICON_PATH not between", value1, value2, "iconPath");
            return (Criteria) this;
        }

        public Criteria andIconSizeIsNull() {
            addCriterion("ICON_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andIconSizeIsNotNull() {
            addCriterion("ICON_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andIconSizeEqualTo(Integer value) {
            addCriterion("ICON_SIZE =", value, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeNotEqualTo(Integer value) {
            addCriterion("ICON_SIZE <>", value, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeGreaterThan(Integer value) {
            addCriterion("ICON_SIZE >", value, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ICON_SIZE >=", value, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeLessThan(Integer value) {
            addCriterion("ICON_SIZE <", value, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeLessThanOrEqualTo(Integer value) {
            addCriterion("ICON_SIZE <=", value, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeIn(List<Integer> values) {
            addCriterion("ICON_SIZE in", values, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeNotIn(List<Integer> values) {
            addCriterion("ICON_SIZE not in", values, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeBetween(Integer value1, Integer value2) {
            addCriterion("ICON_SIZE between", value1, value2, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("ICON_SIZE not between", value1, value2, "iconSize");
            return (Criteria) this;
        }

        public Criteria andIconRangeIsNull() {
            addCriterion("ICON_RANGE is null");
            return (Criteria) this;
        }

        public Criteria andIconRangeIsNotNull() {
            addCriterion("ICON_RANGE is not null");
            return (Criteria) this;
        }

        public Criteria andIconRangeEqualTo(Integer value) {
            addCriterion("ICON_RANGE =", value, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeNotEqualTo(Integer value) {
            addCriterion("ICON_RANGE <>", value, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeGreaterThan(Integer value) {
            addCriterion("ICON_RANGE >", value, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ICON_RANGE >=", value, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeLessThan(Integer value) {
            addCriterion("ICON_RANGE <", value, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeLessThanOrEqualTo(Integer value) {
            addCriterion("ICON_RANGE <=", value, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeIn(List<Integer> values) {
            addCriterion("ICON_RANGE in", values, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeNotIn(List<Integer> values) {
            addCriterion("ICON_RANGE not in", values, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeBetween(Integer value1, Integer value2) {
            addCriterion("ICON_RANGE between", value1, value2, "iconRange");
            return (Criteria) this;
        }

        public Criteria andIconRangeNotBetween(Integer value1, Integer value2) {
            addCriterion("ICON_RANGE not between", value1, value2, "iconRange");
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

        public Criteria andScrIdIsNull() {
            addCriterion("SCR_ID is null");
            return (Criteria) this;
        }

        public Criteria andScrIdIsNotNull() {
            addCriterion("SCR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andScrIdEqualTo(Long value) {
            addCriterion("SCR_ID =", value, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdNotEqualTo(Long value) {
            addCriterion("SCR_ID <>", value, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdGreaterThan(Long value) {
            addCriterion("SCR_ID >", value, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SCR_ID >=", value, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdLessThan(Long value) {
            addCriterion("SCR_ID <", value, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdLessThanOrEqualTo(Long value) {
            addCriterion("SCR_ID <=", value, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdIn(List<Long> values) {
            addCriterion("SCR_ID in", values, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdNotIn(List<Long> values) {
            addCriterion("SCR_ID not in", values, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdBetween(Long value1, Long value2) {
            addCriterion("SCR_ID between", value1, value2, "scrId");
            return (Criteria) this;
        }

        public Criteria andScrIdNotBetween(Long value1, Long value2) {
            addCriterion("SCR_ID not between", value1, value2, "scrId");
            return (Criteria) this;
        }

        public Criteria andScsjIsNull() {
            addCriterion("SCSJ is null");
            return (Criteria) this;
        }

        public Criteria andScsjIsNotNull() {
            addCriterion("SCSJ is not null");
            return (Criteria) this;
        }

        public Criteria andScsjEqualTo(Date value) {
            addCriterion("SCSJ =", value, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjNotEqualTo(Date value) {
            addCriterion("SCSJ <>", value, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjGreaterThan(Date value) {
            addCriterion("SCSJ >", value, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjGreaterThanOrEqualTo(Date value) {
            addCriterion("SCSJ >=", value, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjLessThan(Date value) {
            addCriterion("SCSJ <", value, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjLessThanOrEqualTo(Date value) {
            addCriterion("SCSJ <=", value, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjIn(List<Date> values) {
            addCriterion("SCSJ in", values, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjNotIn(List<Date> values) {
            addCriterion("SCSJ not in", values, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjBetween(Date value1, Date value2) {
            addCriterion("SCSJ between", value1, value2, "scsj");
            return (Criteria) this;
        }

        public Criteria andScsjNotBetween(Date value1, Date value2) {
            addCriterion("SCSJ not between", value1, value2, "scsj");
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