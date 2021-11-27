package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampRoleMenuExample
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 角色菜单
*/
public class IomCampRoleMenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCampRoleMenuExample() {
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRoleDmIsNull() {
            addCriterion("role_dm is null");
            return (Criteria) this;
        }

        public Criteria andRoleDmIsNotNull() {
            addCriterion("role_dm is not null");
            return (Criteria) this;
        }

        public Criteria andRoleDmEqualTo(String value) {
            addCriterion("role_dm =", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmNotEqualTo(String value) {
            addCriterion("role_dm <>", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmGreaterThan(String value) {
            addCriterion("role_dm >", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmGreaterThanOrEqualTo(String value) {
            addCriterion("role_dm >=", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmLessThan(String value) {
            addCriterion("role_dm <", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmLessThanOrEqualTo(String value) {
            addCriterion("role_dm <=", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmLike(String value) {
            addCriterion("role_dm like", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmNotLike(String value) {
            addCriterion("role_dm not like", value, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmIn(List<String> values) {
            addCriterion("role_dm in", values, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmNotIn(List<String> values) {
            addCriterion("role_dm not in", values, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmBetween(String value1, String value2) {
            addCriterion("role_dm between", value1, value2, "roleDm");
            return (Criteria) this;
        }

        public Criteria andRoleDmNotBetween(String value1, String value2) {
            addCriterion("role_dm not between", value1, value2, "roleDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmIsNull() {
            addCriterion("gncd_dm is null");
            return (Criteria) this;
        }

        public Criteria andGncdDmIsNotNull() {
            addCriterion("gncd_dm is not null");
            return (Criteria) this;
        }

        public Criteria andGncdDmEqualTo(String value) {
            addCriterion("gncd_dm =", value, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmNotEqualTo(String value) {
            addCriterion("gncd_dm <>", value, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmGreaterThan(String value) {
            addCriterion("gncd_dm >", value, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmGreaterThanOrEqualTo(String value) {
            addCriterion("gncd_dm >=", value, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmLessThan(String value) {
            addCriterion("gncd_dm <", value, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmLessThanOrEqualTo(String value) {
            addCriterion("gncd_dm <=", value, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmIn(List<String> values) {
            addCriterion("gncd_dm in", values, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmNotIn(List<String> values) {
            addCriterion("gncd_dm not in", values, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmBetween(String value1, String value2) {
            addCriterion("gncd_dm between", value1, value2, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGncdDmNotBetween(String value1, String value2) {
            addCriterion("gncd_dm not between", value1, value2, "gncdDm");
            return (Criteria) this;
        }

        public Criteria andGnflTypeIsNull() {
            addCriterion("gnfl_type is null");
            return (Criteria) this;
        }

        public Criteria andGnflTypeIsNotNull() {
            addCriterion("gnfl_type is not null");
            return (Criteria) this;
        }

        public Criteria andGnflTypeEqualTo(Integer value) {
            addCriterion("gnfl_type =", value, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeNotEqualTo(Integer value) {
            addCriterion("gnfl_type <>", value, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeGreaterThan(Integer value) {
            addCriterion("gnfl_type >", value, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("gnfl_type >=", value, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeLessThan(Integer value) {
            addCriterion("gnfl_type <", value, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeLessThanOrEqualTo(Integer value) {
            addCriterion("gnfl_type <=", value, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeIn(List<Integer> values) {
            addCriterion("gnfl_type in", values, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeNotIn(List<Integer> values) {
            addCriterion("gnfl_type not in", values, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeBetween(Integer value1, Integer value2) {
            addCriterion("gnfl_type between", value1, value2, "gnflType");
            return (Criteria) this;
        }

        public Criteria andGnflTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("gnfl_type not between", value1, value2, "gnflType");
            return (Criteria) this;
        }

        public Criteria andCjrIdIsNull() {
            addCriterion("cjr_id is null");
            return (Criteria) this;
        }

        public Criteria andCjrIdIsNotNull() {
            addCriterion("cjr_id is not null");
            return (Criteria) this;
        }

        public Criteria andCjrIdEqualTo(String value) {
            addCriterion("cjr_id =", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotEqualTo(String value) {
            addCriterion("cjr_id <>", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdGreaterThan(String value) {
            addCriterion("cjr_id >", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdGreaterThanOrEqualTo(String value) {
            addCriterion("cjr_id >=", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdLessThan(String value) {
            addCriterion("cjr_id <", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdLessThanOrEqualTo(String value) {
            addCriterion("cjr_id <=", value, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdIn(List<String> values) {
            addCriterion("cjr_id in", values, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotIn(List<String> values) {
            addCriterion("cjr_id not in", values, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdBetween(String value1, String value2) {
            addCriterion("cjr_id between", value1, value2, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjrIdNotBetween(String value1, String value2) {
            addCriterion("cjr_id not between", value1, value2, "cjrId");
            return (Criteria) this;
        }

        public Criteria andCjsjIsNull() {
            addCriterion("cjsj is null");
            return (Criteria) this;
        }

        public Criteria andCjsjIsNotNull() {
            addCriterion("cjsj is not null");
            return (Criteria) this;
        }

        public Criteria andCjsjEqualTo(Date value) {
            addCriterion("cjsj =", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotEqualTo(Date value) {
            addCriterion("cjsj <>", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThan(Date value) {
            addCriterion("cjsj >", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThanOrEqualTo(Date value) {
            addCriterion("cjsj >=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThan(Date value) {
            addCriterion("cjsj <", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThanOrEqualTo(Date value) {
            addCriterion("cjsj <=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjIn(List<Date> values) {
            addCriterion("cjsj in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotIn(List<Date> values) {
            addCriterion("cjsj not in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjBetween(Date value1, Date value2) {
            addCriterion("cjsj between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotBetween(Date value1, Date value2) {
            addCriterion("cjsj not between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andXgrIdIsNull() {
            addCriterion("xgr_id is null");
            return (Criteria) this;
        }

        public Criteria andXgrIdIsNotNull() {
            addCriterion("xgr_id is not null");
            return (Criteria) this;
        }

        public Criteria andXgrIdEqualTo(String value) {
            addCriterion("xgr_id =", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotEqualTo(String value) {
            addCriterion("xgr_id <>", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdGreaterThan(String value) {
            addCriterion("xgr_id >", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdGreaterThanOrEqualTo(String value) {
            addCriterion("xgr_id >=", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdLessThan(String value) {
            addCriterion("xgr_id <", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdLessThanOrEqualTo(String value) {
            addCriterion("xgr_id <=", value, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdIn(List<String> values) {
            addCriterion("xgr_id in", values, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotIn(List<String> values) {
            addCriterion("xgr_id not in", values, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdBetween(String value1, String value2) {
            addCriterion("xgr_id between", value1, value2, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgrIdNotBetween(String value1, String value2) {
            addCriterion("xgr_id not between", value1, value2, "xgrId");
            return (Criteria) this;
        }

        public Criteria andXgsjIsNull() {
            addCriterion("xgsj is null");
            return (Criteria) this;
        }

        public Criteria andXgsjIsNotNull() {
            addCriterion("xgsj is not null");
            return (Criteria) this;
        }

        public Criteria andXgsjEqualTo(Date value) {
            addCriterion("xgsj =", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotEqualTo(Date value) {
            addCriterion("xgsj <>", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThan(Date value) {
            addCriterion("xgsj >", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThanOrEqualTo(Date value) {
            addCriterion("xgsj >=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThan(Date value) {
            addCriterion("xgsj <", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThanOrEqualTo(Date value) {
            addCriterion("xgsj <=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjIn(List<Date> values) {
            addCriterion("xgsj in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotIn(List<Date> values) {
            addCriterion("xgsj not in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjBetween(Date value1, Date value2) {
            addCriterion("xgsj between", value1, value2, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotBetween(Date value1, Date value2) {
            addCriterion("xgsj not between", value1, value2, "xgsj");
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