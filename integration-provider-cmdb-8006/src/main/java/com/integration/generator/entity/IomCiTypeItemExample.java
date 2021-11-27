package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiTypeItemExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiTypeItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiTypeItemExample() {
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

        public Criteria andAttrNameIsNull() {
            addCriterion("ATTR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAttrNameIsNotNull() {
            addCriterion("ATTR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAttrNameEqualTo(String value) {
            addCriterion("ATTR_NAME =", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotEqualTo(String value) {
            addCriterion("ATTR_NAME <>", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameGreaterThan(String value) {
            addCriterion("ATTR_NAME >", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameGreaterThanOrEqualTo(String value) {
            addCriterion("ATTR_NAME >=", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameLessThan(String value) {
            addCriterion("ATTR_NAME <", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameLessThanOrEqualTo(String value) {
            addCriterion("ATTR_NAME <=", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameLike(String value) {
            addCriterion("ATTR_NAME like", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotLike(String value) {
            addCriterion("ATTR_NAME not like", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameIn(List<String> values) {
            addCriterion("ATTR_NAME in", values, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotIn(List<String> values) {
            addCriterion("ATTR_NAME not in", values, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameBetween(String value1, String value2) {
            addCriterion("ATTR_NAME between", value1, value2, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotBetween(String value1, String value2) {
            addCriterion("ATTR_NAME not between", value1, value2, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameIsNull() {
            addCriterion("ATTR_STD_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameIsNotNull() {
            addCriterion("ATTR_STD_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameEqualTo(String value) {
            addCriterion("ATTR_STD_NAME =", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameNotEqualTo(String value) {
            addCriterion("ATTR_STD_NAME <>", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameGreaterThan(String value) {
            addCriterion("ATTR_STD_NAME >", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameGreaterThanOrEqualTo(String value) {
            addCriterion("ATTR_STD_NAME >=", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameLessThan(String value) {
            addCriterion("ATTR_STD_NAME <", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameLessThanOrEqualTo(String value) {
            addCriterion("ATTR_STD_NAME <=", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameLike(String value) {
            addCriterion("ATTR_STD_NAME like", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameNotLike(String value) {
            addCriterion("ATTR_STD_NAME not like", value, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameIn(List<String> values) {
            addCriterion("ATTR_STD_NAME in", values, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameNotIn(List<String> values) {
            addCriterion("ATTR_STD_NAME not in", values, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameBetween(String value1, String value2) {
            addCriterion("ATTR_STD_NAME between", value1, value2, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrStdNameNotBetween(String value1, String value2) {
            addCriterion("ATTR_STD_NAME not between", value1, value2, "attrStdName");
            return (Criteria) this;
        }

        public Criteria andAttrTypeIsNull() {
            addCriterion("ATTR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAttrTypeIsNotNull() {
            addCriterion("ATTR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAttrTypeEqualTo(String value) {
            addCriterion("ATTR_TYPE =", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotEqualTo(String value) {
            addCriterion("ATTR_TYPE <>", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeGreaterThan(String value) {
            addCriterion("ATTR_TYPE >", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ATTR_TYPE >=", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeLessThan(String value) {
            addCriterion("ATTR_TYPE <", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeLessThanOrEqualTo(String value) {
            addCriterion("ATTR_TYPE <=", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeLike(String value) {
            addCriterion("ATTR_TYPE like", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotLike(String value) {
            addCriterion("ATTR_TYPE not like", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeIn(List<String> values) {
            addCriterion("ATTR_TYPE in", values, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotIn(List<String> values) {
            addCriterion("ATTR_TYPE not in", values, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeBetween(String value1, String value2) {
            addCriterion("ATTR_TYPE between", value1, value2, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotBetween(String value1, String value2) {
            addCriterion("ATTR_TYPE not between", value1, value2, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrDescIsNull() {
            addCriterion("ATTR_DESC is null");
            return (Criteria) this;
        }

        public Criteria andAttrDescIsNotNull() {
            addCriterion("ATTR_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andAttrDescEqualTo(String value) {
            addCriterion("ATTR_DESC =", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescNotEqualTo(String value) {
            addCriterion("ATTR_DESC <>", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescGreaterThan(String value) {
            addCriterion("ATTR_DESC >", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescGreaterThanOrEqualTo(String value) {
            addCriterion("ATTR_DESC >=", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescLessThan(String value) {
            addCriterion("ATTR_DESC <", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescLessThanOrEqualTo(String value) {
            addCriterion("ATTR_DESC <=", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescLike(String value) {
            addCriterion("ATTR_DESC like", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescNotLike(String value) {
            addCriterion("ATTR_DESC not like", value, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescIn(List<String> values) {
            addCriterion("ATTR_DESC in", values, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescNotIn(List<String> values) {
            addCriterion("ATTR_DESC not in", values, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescBetween(String value1, String value2) {
            addCriterion("ATTR_DESC between", value1, value2, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andAttrDescNotBetween(String value1, String value2) {
            addCriterion("ATTR_DESC not between", value1, value2, "attrDesc");
            return (Criteria) this;
        }

        public Criteria andMpCiItemIsNull() {
            addCriterion("MP_CI_ITEM is null");
            return (Criteria) this;
        }

        public Criteria andMpCiItemIsNotNull() {
            addCriterion("MP_CI_ITEM is not null");
            return (Criteria) this;
        }

        public Criteria andMpCiItemEqualTo(String value) {
            addCriterion("MP_CI_ITEM =", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemNotEqualTo(String value) {
            addCriterion("MP_CI_ITEM <>", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemGreaterThan(String value) {
            addCriterion("MP_CI_ITEM >", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemGreaterThanOrEqualTo(String value) {
            addCriterion("MP_CI_ITEM >=", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemLessThan(String value) {
            addCriterion("MP_CI_ITEM <", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemLessThanOrEqualTo(String value) {
            addCriterion("MP_CI_ITEM <=", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemLike(String value) {
            addCriterion("MP_CI_ITEM like", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemNotLike(String value) {
            addCriterion("MP_CI_ITEM not like", value, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemIn(List<String> values) {
            addCriterion("MP_CI_ITEM in", values, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemNotIn(List<String> values) {
            addCriterion("MP_CI_ITEM not in", values, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemBetween(String value1, String value2) {
            addCriterion("MP_CI_ITEM between", value1, value2, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andMpCiItemNotBetween(String value1, String value2) {
            addCriterion("MP_CI_ITEM not between", value1, value2, "mpCiItem");
            return (Criteria) this;
        }

        public Criteria andIsMajorIsNull() {
            addCriterion("IS_MAJOR is null");
            return (Criteria) this;
        }

        public Criteria andIsMajorIsNotNull() {
            addCriterion("IS_MAJOR is not null");
            return (Criteria) this;
        }

        public Criteria andIsMajorEqualTo(Integer value) {
            addCriterion("IS_MAJOR =", value, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorNotEqualTo(Integer value) {
            addCriterion("IS_MAJOR <>", value, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorGreaterThan(Integer value) {
            addCriterion("IS_MAJOR >", value, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_MAJOR >=", value, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorLessThan(Integer value) {
            addCriterion("IS_MAJOR <", value, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorLessThanOrEqualTo(Integer value) {
            addCriterion("IS_MAJOR <=", value, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorIn(List<Integer> values) {
            addCriterion("IS_MAJOR in", values, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorNotIn(List<Integer> values) {
            addCriterion("IS_MAJOR not in", values, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorBetween(Integer value1, Integer value2) {
            addCriterion("IS_MAJOR between", value1, value2, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsMajorNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_MAJOR not between", value1, value2, "isMajor");
            return (Criteria) this;
        }

        public Criteria andIsRequIsNull() {
            addCriterion("IS_REQU is null");
            return (Criteria) this;
        }

        public Criteria andIsRequIsNotNull() {
            addCriterion("IS_REQU is not null");
            return (Criteria) this;
        }

        public Criteria andIsRequEqualTo(Integer value) {
            addCriterion("IS_REQU =", value, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequNotEqualTo(Integer value) {
            addCriterion("IS_REQU <>", value, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequGreaterThan(Integer value) {
            addCriterion("IS_REQU >", value, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_REQU >=", value, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequLessThan(Integer value) {
            addCriterion("IS_REQU <", value, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequLessThanOrEqualTo(Integer value) {
            addCriterion("IS_REQU <=", value, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequIn(List<Integer> values) {
            addCriterion("IS_REQU in", values, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequNotIn(List<Integer> values) {
            addCriterion("IS_REQU not in", values, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequBetween(Integer value1, Integer value2) {
            addCriterion("IS_REQU between", value1, value2, "isRequ");
            return (Criteria) this;
        }

        public Criteria andIsRequNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_REQU not between", value1, value2, "isRequ");
            return (Criteria) this;
        }

        public Criteria andDefValIsNull() {
            addCriterion("DEF_VAL is null");
            return (Criteria) this;
        }

        public Criteria andDefValIsNotNull() {
            addCriterion("DEF_VAL is not null");
            return (Criteria) this;
        }

        public Criteria andDefValEqualTo(String value) {
            addCriterion("DEF_VAL =", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValNotEqualTo(String value) {
            addCriterion("DEF_VAL <>", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValGreaterThan(String value) {
            addCriterion("DEF_VAL >", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValGreaterThanOrEqualTo(String value) {
            addCriterion("DEF_VAL >=", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValLessThan(String value) {
            addCriterion("DEF_VAL <", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValLessThanOrEqualTo(String value) {
            addCriterion("DEF_VAL <=", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValLike(String value) {
            addCriterion("DEF_VAL like", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValNotLike(String value) {
            addCriterion("DEF_VAL not like", value, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValIn(List<String> values) {
            addCriterion("DEF_VAL in", values, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValNotIn(List<String> values) {
            addCriterion("DEF_VAL not in", values, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValBetween(String value1, String value2) {
            addCriterion("DEF_VAL between", value1, value2, "defVal");
            return (Criteria) this;
        }

        public Criteria andDefValNotBetween(String value1, String value2) {
            addCriterion("DEF_VAL not between", value1, value2, "defVal");
            return (Criteria) this;
        }

        public Criteria andEnumValsIsNull() {
            addCriterion("ENUM_VALS is null");
            return (Criteria) this;
        }

        public Criteria andEnumValsIsNotNull() {
            addCriterion("ENUM_VALS is not null");
            return (Criteria) this;
        }

        public Criteria andEnumValsEqualTo(String value) {
            addCriterion("ENUM_VALS =", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsNotEqualTo(String value) {
            addCriterion("ENUM_VALS <>", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsGreaterThan(String value) {
            addCriterion("ENUM_VALS >", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsGreaterThanOrEqualTo(String value) {
            addCriterion("ENUM_VALS >=", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsLessThan(String value) {
            addCriterion("ENUM_VALS <", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsLessThanOrEqualTo(String value) {
            addCriterion("ENUM_VALS <=", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsLike(String value) {
            addCriterion("ENUM_VALS like", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsNotLike(String value) {
            addCriterion("ENUM_VALS not like", value, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsIn(List<String> values) {
            addCriterion("ENUM_VALS in", values, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsNotIn(List<String> values) {
            addCriterion("ENUM_VALS not in", values, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsBetween(String value1, String value2) {
            addCriterion("ENUM_VALS between", value1, value2, "enumVals");
            return (Criteria) this;
        }

        public Criteria andEnumValsNotBetween(String value1, String value2) {
            addCriterion("ENUM_VALS not between", value1, value2, "enumVals");
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