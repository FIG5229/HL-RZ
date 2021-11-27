package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiDirExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiDirExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiDirExample() {
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

        public Criteria andDomainIdEqualTo(String value) {
            addCriterion("DOMAIN_ID =", value, "domainId");
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

        public Criteria andDirNameIsNull() {
            addCriterion("DIR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDirNameIsNotNull() {
            addCriterion("DIR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDirNameEqualTo(String value) {
            addCriterion("DIR_NAME =", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotEqualTo(String value) {
            addCriterion("DIR_NAME <>", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameGreaterThan(String value) {
            addCriterion("DIR_NAME >", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameGreaterThanOrEqualTo(String value) {
            addCriterion("DIR_NAME >=", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameLessThan(String value) {
            addCriterion("DIR_NAME <", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameLessThanOrEqualTo(String value) {
            addCriterion("DIR_NAME <=", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameLike(String value) {
            addCriterion("DIR_NAME like", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotLike(String value) {
            addCriterion("DIR_NAME not like", value, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameIn(List<String> values) {
            addCriterion("DIR_NAME in", values, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotIn(List<String> values) {
            addCriterion("DIR_NAME not in", values, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameBetween(String value1, String value2) {
            addCriterion("DIR_NAME between", value1, value2, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirNameNotBetween(String value1, String value2) {
            addCriterion("DIR_NAME not between", value1, value2, "dirName");
            return (Criteria) this;
        }

        public Criteria andDirTypeIsNull() {
            addCriterion("DIR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andDirTypeIsNotNull() {
            addCriterion("DIR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDirTypeEqualTo(Integer value) {
            addCriterion("DIR_TYPE =", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotEqualTo(Integer value) {
            addCriterion("DIR_TYPE <>", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeGreaterThan(Integer value) {
            addCriterion("DIR_TYPE >", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("DIR_TYPE >=", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeLessThan(Integer value) {
            addCriterion("DIR_TYPE <", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeLessThanOrEqualTo(Integer value) {
            addCriterion("DIR_TYPE <=", value, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeIn(List<Integer> values) {
            addCriterion("DIR_TYPE in", values, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotIn(List<Integer> values) {
            addCriterion("DIR_TYPE not in", values, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeBetween(Integer value1, Integer value2) {
            addCriterion("DIR_TYPE between", value1, value2, "dirType");
            return (Criteria) this;
        }

        public Criteria andDirTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("DIR_TYPE not between", value1, value2, "dirType");
            return (Criteria) this;
        }

        public Criteria andParentDirIdIsNull() {
            addCriterion("PARENT_DIR_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentDirIdIsNotNull() {
            addCriterion("PARENT_DIR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentDirIdEqualTo(String value) {
            addCriterion("PARENT_DIR_ID =", value, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdNotEqualTo(String value) {
            addCriterion("PARENT_DIR_ID <>", value, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdGreaterThan(String value) {
            addCriterion("PARENT_DIR_ID >", value, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_DIR_ID >=", value, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdLessThan(String value) {
            addCriterion("PARENT_DIR_ID <", value, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_DIR_ID <=", value, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdIn(List<String> values) {
            addCriterion("PARENT_DIR_ID in", values, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdNotIn(List<String> values) {
            addCriterion("PARENT_DIR_ID not in", values, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdBetween(String value1, String value2) {
            addCriterion("PARENT_DIR_ID between", value1, value2, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andParentDirIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_DIR_ID not between", value1, value2, "parentDirId");
            return (Criteria) this;
        }

        public Criteria andDirLvlIsNull() {
            addCriterion("DIR_LVL is null");
            return (Criteria) this;
        }

        public Criteria andDirLvlIsNotNull() {
            addCriterion("DIR_LVL is not null");
            return (Criteria) this;
        }

        public Criteria andDirLvlEqualTo(Integer value) {
            addCriterion("DIR_LVL =", value, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlNotEqualTo(Integer value) {
            addCriterion("DIR_LVL <>", value, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlGreaterThan(Integer value) {
            addCriterion("DIR_LVL >", value, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlGreaterThanOrEqualTo(Integer value) {
            addCriterion("DIR_LVL >=", value, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlLessThan(Integer value) {
            addCriterion("DIR_LVL <", value, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlLessThanOrEqualTo(Integer value) {
            addCriterion("DIR_LVL <=", value, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlIn(List<Integer> values) {
            addCriterion("DIR_LVL in", values, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlNotIn(List<Integer> values) {
            addCriterion("DIR_LVL not in", values, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlBetween(Integer value1, Integer value2) {
            addCriterion("DIR_LVL between", value1, value2, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirLvlNotBetween(Integer value1, Integer value2) {
            addCriterion("DIR_LVL not between", value1, value2, "dirLvl");
            return (Criteria) this;
        }

        public Criteria andDirPathIsNull() {
            addCriterion("DIR_PATH is null");
            return (Criteria) this;
        }

        public Criteria andDirPathIsNotNull() {
            addCriterion("DIR_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andDirPathEqualTo(String value) {
            addCriterion("DIR_PATH =", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathNotEqualTo(String value) {
            addCriterion("DIR_PATH <>", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathGreaterThan(String value) {
            addCriterion("DIR_PATH >", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathGreaterThanOrEqualTo(String value) {
            addCriterion("DIR_PATH >=", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathLessThan(String value) {
            addCriterion("DIR_PATH <", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathLessThanOrEqualTo(String value) {
            addCriterion("DIR_PATH <=", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathLike(String value) {
            addCriterion("DIR_PATH like", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathNotLike(String value) {
            addCriterion("DIR_PATH not like", value, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathIn(List<String> values) {
            addCriterion("DIR_PATH in", values, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathNotIn(List<String> values) {
            addCriterion("DIR_PATH not in", values, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathBetween(String value1, String value2) {
            addCriterion("DIR_PATH between", value1, value2, "dirPath");
            return (Criteria) this;
        }

        public Criteria andDirPathNotBetween(String value1, String value2) {
            addCriterion("DIR_PATH not between", value1, value2, "dirPath");
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

        public Criteria andIsLeafIsNull() {
            addCriterion("IS_LEAF is null");
            return (Criteria) this;
        }

        public Criteria andIsLeafIsNotNull() {
            addCriterion("IS_LEAF is not null");
            return (Criteria) this;
        }

        public Criteria andIsLeafEqualTo(Integer value) {
            addCriterion("IS_LEAF =", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotEqualTo(Integer value) {
            addCriterion("IS_LEAF <>", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThan(Integer value) {
            addCriterion("IS_LEAF >", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_LEAF >=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThan(Integer value) {
            addCriterion("IS_LEAF <", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafLessThanOrEqualTo(Integer value) {
            addCriterion("IS_LEAF <=", value, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafIn(List<Integer> values) {
            addCriterion("IS_LEAF in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotIn(List<Integer> values) {
            addCriterion("IS_LEAF not in", values, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafBetween(Integer value1, Integer value2) {
            addCriterion("IS_LEAF between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andIsLeafNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_LEAF not between", value1, value2, "isLeaf");
            return (Criteria) this;
        }

        public Criteria andDirIconIsNull() {
            addCriterion("DIR_ICON is null");
            return (Criteria) this;
        }

        public Criteria andDirIconIsNotNull() {
            addCriterion("DIR_ICON is not null");
            return (Criteria) this;
        }

        public Criteria andDirIconEqualTo(String value) {
            addCriterion("DIR_ICON =", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconNotEqualTo(String value) {
            addCriterion("DIR_ICON <>", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconGreaterThan(String value) {
            addCriterion("DIR_ICON >", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconGreaterThanOrEqualTo(String value) {
            addCriterion("DIR_ICON >=", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconLessThan(String value) {
            addCriterion("DIR_ICON <", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconLessThanOrEqualTo(String value) {
            addCriterion("DIR_ICON <=", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconLike(String value) {
            addCriterion("DIR_ICON like", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconNotLike(String value) {
            addCriterion("DIR_ICON not like", value, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconIn(List<String> values) {
            addCriterion("DIR_ICON in", values, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconNotIn(List<String> values) {
            addCriterion("DIR_ICON not in", values, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconBetween(String value1, String value2) {
            addCriterion("DIR_ICON between", value1, value2, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirIconNotBetween(String value1, String value2) {
            addCriterion("DIR_ICON not between", value1, value2, "dirIcon");
            return (Criteria) this;
        }

        public Criteria andDirColorIsNull() {
            addCriterion("DIR_COLOR is null");
            return (Criteria) this;
        }

        public Criteria andDirColorIsNotNull() {
            addCriterion("DIR_COLOR is not null");
            return (Criteria) this;
        }

        public Criteria andDirColorEqualTo(String value) {
            addCriterion("DIR_COLOR =", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorNotEqualTo(String value) {
            addCriterion("DIR_COLOR <>", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorGreaterThan(String value) {
            addCriterion("DIR_COLOR >", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorGreaterThanOrEqualTo(String value) {
            addCriterion("DIR_COLOR >=", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorLessThan(String value) {
            addCriterion("DIR_COLOR <", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorLessThanOrEqualTo(String value) {
            addCriterion("DIR_COLOR <=", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorLike(String value) {
            addCriterion("DIR_COLOR like", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorNotLike(String value) {
            addCriterion("DIR_COLOR not like", value, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorIn(List<String> values) {
            addCriterion("DIR_COLOR in", values, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorNotIn(List<String> values) {
            addCriterion("DIR_COLOR not in", values, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorBetween(String value1, String value2) {
            addCriterion("DIR_COLOR between", value1, value2, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirColorNotBetween(String value1, String value2) {
            addCriterion("DIR_COLOR not between", value1, value2, "dirColor");
            return (Criteria) this;
        }

        public Criteria andDirDescIsNull() {
            addCriterion("DIR_DESC is null");
            return (Criteria) this;
        }

        public Criteria andDirDescIsNotNull() {
            addCriterion("DIR_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andDirDescEqualTo(String value) {
            addCriterion("DIR_DESC =", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescNotEqualTo(String value) {
            addCriterion("DIR_DESC <>", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescGreaterThan(String value) {
            addCriterion("DIR_DESC >", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescGreaterThanOrEqualTo(String value) {
            addCriterion("DIR_DESC >=", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescLessThan(String value) {
            addCriterion("DIR_DESC <", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescLessThanOrEqualTo(String value) {
            addCriterion("DIR_DESC <=", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescLike(String value) {
            addCriterion("DIR_DESC like", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescNotLike(String value) {
            addCriterion("DIR_DESC not like", value, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescIn(List<String> values) {
            addCriterion("DIR_DESC in", values, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescNotIn(List<String> values) {
            addCriterion("DIR_DESC not in", values, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescBetween(String value1, String value2) {
            addCriterion("DIR_DESC between", value1, value2, "dirDesc");
            return (Criteria) this;
        }

        public Criteria andDirDescNotBetween(String value1, String value2) {
            addCriterion("DIR_DESC not between", value1, value2, "dirDesc");
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