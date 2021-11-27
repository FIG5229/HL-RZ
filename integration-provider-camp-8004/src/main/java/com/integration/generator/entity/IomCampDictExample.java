package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IomCampDictExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCampDictExample() {
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

        public Criteria andDictIdIsNull() {
            addCriterion("DICT_ID is null");
            return (Criteria) this;
        }

        public Criteria andDictIdIsNotNull() {
            addCriterion("DICT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDictIdEqualTo(String value) {
            addCriterion("DICT_ID =", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotEqualTo(String value) {
            addCriterion("DICT_ID <>", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdGreaterThan(String value) {
            addCriterion("DICT_ID >", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_ID >=", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLessThan(String value) {
            addCriterion("DICT_ID <", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdLessThanOrEqualTo(String value) {
            addCriterion("DICT_ID <=", value, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdIn(List<String> values) {
            addCriterion("DICT_ID in", values, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotIn(List<String> values) {
            addCriterion("DICT_ID not in", values, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdBetween(String value1, String value2) {
            addCriterion("DICT_ID between", value1, value2, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictIdNotBetween(String value1, String value2) {
            addCriterion("DICT_ID not between", value1, value2, "dictId");
            return (Criteria) this;
        }

        public Criteria andDictBmIsNull() {
            addCriterion("DICT_BM is null");
            return (Criteria) this;
        }

        public Criteria andDictBmIsNotNull() {
            addCriterion("DICT_BM is not null");
            return (Criteria) this;
        }

        public Criteria andDictBmEqualTo(String value) {
            addCriterion("DICT_BM =", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmNotEqualTo(String value) {
            addCriterion("DICT_BM <>", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmGreaterThan(String value) {
            addCriterion("DICT_BM >", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_BM >=", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmLessThan(String value) {
            addCriterion("DICT_BM <", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmLessThanOrEqualTo(String value) {
            addCriterion("DICT_BM <=", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmLike(String value) {
            addCriterion("DICT_BM like", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmNotLike(String value) {
            addCriterion("DICT_BM not like", value, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmIn(List<String> values) {
            addCriterion("DICT_BM in", values, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmNotIn(List<String> values) {
            addCriterion("DICT_BM not in", values, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmBetween(String value1, String value2) {
            addCriterion("DICT_BM between", value1, value2, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictBmNotBetween(String value1, String value2) {
            addCriterion("DICT_BM not between", value1, value2, "dictBm");
            return (Criteria) this;
        }

        public Criteria andDictNameIsNull() {
            addCriterion("DICT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andDictNameIsNotNull() {
            addCriterion("DICT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andDictNameEqualTo(String value) {
            addCriterion("DICT_NAME =", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotEqualTo(String value) {
            addCriterion("DICT_NAME <>", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameGreaterThan(String value) {
            addCriterion("DICT_NAME >", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameGreaterThanOrEqualTo(String value) {
            addCriterion("DICT_NAME >=", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameLessThan(String value) {
            addCriterion("DICT_NAME <", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameLessThanOrEqualTo(String value) {
            addCriterion("DICT_NAME <=", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameLike(String value) {
            addCriterion("DICT_NAME like", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotLike(String value) {
            addCriterion("DICT_NAME not like", value, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameIn(List<String> values) {
            addCriterion("DICT_NAME in", values, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotIn(List<String> values) {
            addCriterion("DICT_NAME not in", values, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameBetween(String value1, String value2) {
            addCriterion("DICT_NAME between", value1, value2, "dictName");
            return (Criteria) this;
        }

        public Criteria andDictNameNotBetween(String value1, String value2) {
            addCriterion("DICT_NAME not between", value1, value2, "dictName");
            return (Criteria) this;
        }

        public Criteria andSjIdIsNull() {
            addCriterion("SJ_ID is null");
            return (Criteria) this;
        }

        public Criteria andSjIdIsNotNull() {
            addCriterion("SJ_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSjIdEqualTo(String value) {
            addCriterion("SJ_ID =", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdNotEqualTo(String value) {
            addCriterion("SJ_ID <>", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdGreaterThan(String value) {
            addCriterion("SJ_ID >", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdGreaterThanOrEqualTo(String value) {
            addCriterion("SJ_ID >=", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdLessThan(String value) {
            addCriterion("SJ_ID <", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdLessThanOrEqualTo(String value) {
            addCriterion("SJ_ID <=", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdLike(String value) {
            addCriterion("SJ_ID like", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdNotLike(String value) {
            addCriterion("SJ_ID not like", value, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdIn(List<String> values) {
            addCriterion("SJ_ID in", values, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdNotIn(List<String> values) {
            addCriterion("SJ_ID not in", values, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdBetween(String value1, String value2) {
            addCriterion("SJ_ID between", value1, value2, "sjId");
            return (Criteria) this;
        }

        public Criteria andSjIdNotBetween(String value1, String value2) {
            addCriterion("SJ_ID not between", value1, value2, "sjId");
            return (Criteria) this;
        }

        public Criteria andGnflIsNull() {
            addCriterion("GNFL is null");
            return (Criteria) this;
        }

        public Criteria andGnflIsNotNull() {
            addCriterion("GNFL is not null");
            return (Criteria) this;
        }

        public Criteria andGnflEqualTo(Integer value) {
            addCriterion("GNFL =", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflNotEqualTo(Integer value) {
            addCriterion("GNFL <>", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflGreaterThan(Integer value) {
            addCriterion("GNFL >", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflGreaterThanOrEqualTo(Integer value) {
            addCriterion("GNFL >=", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflLessThan(Integer value) {
            addCriterion("GNFL <", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflLessThanOrEqualTo(Integer value) {
            addCriterion("GNFL <=", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflIn(List<Integer> values) {
            addCriterion("GNFL in", values, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflNotIn(List<Integer> values) {
            addCriterion("GNFL not in", values, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflBetween(Integer value1, Integer value2) {
            addCriterion("GNFL between", value1, value2, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflNotBetween(Integer value1, Integer value2) {
            addCriterion("GNFL not between", value1, value2, "gnfl");
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

        public Criteria andCoutentIsNull() {
            addCriterion("COUTENT is null");
            return (Criteria) this;
        }

        public Criteria andCoutentIsNotNull() {
            addCriterion("COUTENT is not null");
            return (Criteria) this;
        }

        public Criteria andCoutentEqualTo(String value) {
            addCriterion("COUTENT =", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentNotEqualTo(String value) {
            addCriterion("COUTENT <>", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentGreaterThan(String value) {
            addCriterion("COUTENT >", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentGreaterThanOrEqualTo(String value) {
            addCriterion("COUTENT >=", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentLessThan(String value) {
            addCriterion("COUTENT <", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentLessThanOrEqualTo(String value) {
            addCriterion("COUTENT <=", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentLike(String value) {
            addCriterion("COUTENT like", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentNotLike(String value) {
            addCriterion("COUTENT not like", value, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentIn(List<String> values) {
            addCriterion("COUTENT in", values, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentNotIn(List<String> values) {
            addCriterion("COUTENT not in", values, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentBetween(String value1, String value2) {
            addCriterion("COUTENT between", value1, value2, "coutent");
            return (Criteria) this;
        }

        public Criteria andCoutentNotBetween(String value1, String value2) {
            addCriterion("COUTENT not between", value1, value2, "coutent");
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