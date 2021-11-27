package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiMgtLogExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiMgtLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiMgtLogExample() {
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

        public Criteria andCiIdIsNull() {
            addCriterion("CI_ID is null");
            return (Criteria) this;
        }

        public Criteria andCiIdIsNotNull() {
            addCriterion("CI_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCiIdEqualTo(String value) {
            addCriterion("CI_ID =", value, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdNotEqualTo(String value) {
            addCriterion("CI_ID <>", value, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdGreaterThan(String value) {
            addCriterion("CI_ID >", value, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdGreaterThanOrEqualTo(String value) {
            addCriterion("CI_ID >=", value, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdLessThan(String value) {
            addCriterion("CI_ID <", value, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdLessThanOrEqualTo(String value) {
            addCriterion("CI_ID <=", value, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdIn(List<String> values) {
            addCriterion("CI_ID in", values, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdNotIn(List<String> values) {
            addCriterion("CI_ID not in", values, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdBetween(String value1, String value2) {
            addCriterion("CI_ID between", value1, value2, "ciId");
            return (Criteria) this;
        }

        public Criteria andCiIdNotBetween(String value1, String value2) {
            addCriterion("CI_ID not between", value1, value2, "ciId");
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

        public Criteria andBgrIdIsNull() {
            addCriterion("BGR_ID is null");
            return (Criteria) this;
        }

        public Criteria andBgrIdIsNotNull() {
            addCriterion("BGR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBgrIdEqualTo(String value) {
            addCriterion("BGR_ID =", value, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdNotEqualTo(String value) {
            addCriterion("BGR_ID <>", value, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdGreaterThan(String value) {
            addCriterion("BGR_ID >", value, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdGreaterThanOrEqualTo(String value) {
            addCriterion("BGR_ID >=", value, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdLessThan(String value) {
            addCriterion("BGR_ID <", value, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdLessThanOrEqualTo(String value) {
            addCriterion("BGR_ID <=", value, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdIn(List<String> values) {
            addCriterion("BGR_ID in", values, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdNotIn(List<String> values) {
            addCriterion("BGR_ID not in", values, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdBetween(String value1, String value2) {
            addCriterion("BGR_ID between", value1, value2, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgrIdNotBetween(String value1, String value2) {
            addCriterion("BGR_ID not between", value1, value2, "bgrId");
            return (Criteria) this;
        }

        public Criteria andBgsjIsNull() {
            addCriterion("BGSJ is null");
            return (Criteria) this;
        }

        public Criteria andBgsjIsNotNull() {
            addCriterion("BGSJ is not null");
            return (Criteria) this;
        }

        public Criteria andBgsjEqualTo(Date value) {
            addCriterion("BGSJ =", value, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjNotEqualTo(Date value) {
            addCriterion("BGSJ <>", value, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjGreaterThan(Date value) {
            addCriterion("BGSJ >", value, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjGreaterThanOrEqualTo(Date value) {
            addCriterion("BGSJ >=", value, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjLessThan(Date value) {
            addCriterion("BGSJ <", value, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjLessThanOrEqualTo(Date value) {
            addCriterion("BGSJ <=", value, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjIn(List<Date> values) {
            addCriterion("BGSJ in", values, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjNotIn(List<Date> values) {
            addCriterion("BGSJ not in", values, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjBetween(Date value1, Date value2) {
            addCriterion("BGSJ between", value1, value2, "bgsj");
            return (Criteria) this;
        }

        public Criteria andBgsjNotBetween(Date value1, Date value2) {
            addCriterion("BGSJ not between", value1, value2, "bgsj");
            return (Criteria) this;
        }

        public Criteria andMgtTypeIsNull() {
            addCriterion("MGT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andMgtTypeIsNotNull() {
            addCriterion("MGT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andMgtTypeEqualTo(Integer value) {
            addCriterion("MGT_TYPE =", value, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeNotEqualTo(Integer value) {
            addCriterion("MGT_TYPE <>", value, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeGreaterThan(Integer value) {
            addCriterion("MGT_TYPE >", value, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("MGT_TYPE >=", value, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeLessThan(Integer value) {
            addCriterion("MGT_TYPE <", value, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeLessThanOrEqualTo(Integer value) {
            addCriterion("MGT_TYPE <=", value, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeIn(List<Integer> values) {
            addCriterion("MGT_TYPE in", values, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeNotIn(List<Integer> values) {
            addCriterion("MGT_TYPE not in", values, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeBetween(Integer value1, Integer value2) {
            addCriterion("MGT_TYPE between", value1, value2, "mgtType");
            return (Criteria) this;
        }

        public Criteria andMgtTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("MGT_TYPE not between", value1, value2, "mgtType");
            return (Criteria) this;
        }

        public Criteria andUpItemIsNull() {
            addCriterion("UP_ITEM is null");
            return (Criteria) this;
        }

        public Criteria andUpItemIsNotNull() {
            addCriterion("UP_ITEM is not null");
            return (Criteria) this;
        }

        public Criteria andUpItemEqualTo(String value) {
            addCriterion("UP_ITEM =", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemNotEqualTo(String value) {
            addCriterion("UP_ITEM <>", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemGreaterThan(String value) {
            addCriterion("UP_ITEM >", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemGreaterThanOrEqualTo(String value) {
            addCriterion("UP_ITEM >=", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemLessThan(String value) {
            addCriterion("UP_ITEM <", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemLessThanOrEqualTo(String value) {
            addCriterion("UP_ITEM <=", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemLike(String value) {
            addCriterion("UP_ITEM like", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemNotLike(String value) {
            addCriterion("UP_ITEM not like", value, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemIn(List<String> values) {
            addCriterion("UP_ITEM in", values, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemNotIn(List<String> values) {
            addCriterion("UP_ITEM not in", values, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemBetween(String value1, String value2) {
            addCriterion("UP_ITEM between", value1, value2, "upItem");
            return (Criteria) this;
        }

        public Criteria andUpItemNotBetween(String value1, String value2) {
            addCriterion("UP_ITEM not between", value1, value2, "upItem");
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