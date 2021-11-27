package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IomCampMenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCampMenuExample() {
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

        public Criteria andGncdMcIsNull() {
            addCriterion("gncd_mc is null");
            return (Criteria) this;
        }

        public Criteria andGncdMcIsNotNull() {
            addCriterion("gncd_mc is not null");
            return (Criteria) this;
        }

        public Criteria andGncdMcEqualTo(String value) {
            addCriterion("gncd_mc =", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcNotEqualTo(String value) {
            addCriterion("gncd_mc <>", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcGreaterThan(String value) {
            addCriterion("gncd_mc >", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcGreaterThanOrEqualTo(String value) {
            addCriterion("gncd_mc >=", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcLessThan(String value) {
            addCriterion("gncd_mc <", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcLessThanOrEqualTo(String value) {
            addCriterion("gncd_mc <=", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcLike(String value) {
            addCriterion("gncd_mc like", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcNotLike(String value) {
            addCriterion("gncd_mc not like", value, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcIn(List<String> values) {
            addCriterion("gncd_mc in", values, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcNotIn(List<String> values) {
            addCriterion("gncd_mc not in", values, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcBetween(String value1, String value2) {
            addCriterion("gncd_mc between", value1, value2, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andGncdMcNotBetween(String value1, String value2) {
            addCriterion("gncd_mc not between", value1, value2, "gncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmIsNull() {
            addCriterion("sj_gncd_dm is null");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmIsNotNull() {
            addCriterion("sj_gncd_dm is not null");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmEqualTo(String value) {
            addCriterion("sj_gncd_dm =", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmNotEqualTo(String value) {
            addCriterion("sj_gncd_dm <>", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmGreaterThan(String value) {
            addCriterion("sj_gncd_dm >", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmGreaterThanOrEqualTo(String value) {
            addCriterion("sj_gncd_dm >=", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmLessThan(String value) {
            addCriterion("sj_gncd_dm <", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmLessThanOrEqualTo(String value) {
            addCriterion("sj_gncd_dm <=", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmLike(String value) {
            addCriterion("sj_gncd_dm like", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmNotLike(String value) {
            addCriterion("sj_gncd_dm not like", value, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmIn(List<String> values) {
            addCriterion("sj_gncd_dm in", values, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmNotIn(List<String> values) {
            addCriterion("sj_gncd_dm not in", values, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmBetween(String value1, String value2) {
            addCriterion("sj_gncd_dm between", value1, value2, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdDmNotBetween(String value1, String value2) {
            addCriterion("sj_gncd_dm not between", value1, value2, "sjGncdDm");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcIsNull() {
            addCriterion("sj_gncd_mc is null");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcIsNotNull() {
            addCriterion("sj_gncd_mc is not null");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcEqualTo(String value) {
            addCriterion("sj_gncd_mc =", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcNotEqualTo(String value) {
            addCriterion("sj_gncd_mc <>", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcGreaterThan(String value) {
            addCriterion("sj_gncd_mc >", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcGreaterThanOrEqualTo(String value) {
            addCriterion("sj_gncd_mc >=", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcLessThan(String value) {
            addCriterion("sj_gncd_mc <", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcLessThanOrEqualTo(String value) {
            addCriterion("sj_gncd_mc <=", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcLike(String value) {
            addCriterion("sj_gncd_mc like", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcNotLike(String value) {
            addCriterion("sj_gncd_mc not like", value, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcIn(List<String> values) {
            addCriterion("sj_gncd_mc in", values, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcNotIn(List<String> values) {
            addCriterion("sj_gncd_mc not in", values, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcBetween(String value1, String value2) {
            addCriterion("sj_gncd_mc between", value1, value2, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andSjGncdMcNotBetween(String value1, String value2) {
            addCriterion("sj_gncd_mc not between", value1, value2, "sjGncdMc");
            return (Criteria) this;
        }

        public Criteria andGnflIsNull() {
            addCriterion("gnfl is null");
            return (Criteria) this;
        }

        public Criteria andGnflIsNotNull() {
            addCriterion("gnfl is not null");
            return (Criteria) this;
        }

        public Criteria andGnflEqualTo(Integer value) {
            addCriterion("gnfl =", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflNotEqualTo(Integer value) {
            addCriterion("gnfl <>", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflGreaterThan(Integer value) {
            addCriterion("gnfl >", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflGreaterThanOrEqualTo(Integer value) {
            addCriterion("gnfl >=", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflLessThan(Integer value) {
            addCriterion("gnfl <", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflLessThanOrEqualTo(Integer value) {
            addCriterion("gnfl <=", value, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflIn(List<Integer> values) {
            addCriterion("gnfl in", values, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflNotIn(List<Integer> values) {
            addCriterion("gnfl not in", values, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflBetween(Integer value1, Integer value2) {
            addCriterion("gnfl between", value1, value2, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGnflNotBetween(Integer value1, Integer value2) {
            addCriterion("gnfl not between", value1, value2, "gnfl");
            return (Criteria) this;
        }

        public Criteria andGncdLevelIsNull() {
            addCriterion("gncd_level is null");
            return (Criteria) this;
        }

        public Criteria andGncdLevelIsNotNull() {
            addCriterion("gncd_level is not null");
            return (Criteria) this;
        }

        public Criteria andGncdLevelEqualTo(Integer value) {
            addCriterion("gncd_level =", value, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelNotEqualTo(Integer value) {
            addCriterion("gncd_level <>", value, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelGreaterThan(Integer value) {
            addCriterion("gncd_level >", value, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("gncd_level >=", value, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelLessThan(Integer value) {
            addCriterion("gncd_level <", value, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelLessThanOrEqualTo(Integer value) {
            addCriterion("gncd_level <=", value, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelIn(List<Integer> values) {
            addCriterion("gncd_level in", values, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelNotIn(List<Integer> values) {
            addCriterion("gncd_level not in", values, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelBetween(Integer value1, Integer value2) {
            addCriterion("gncd_level between", value1, value2, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("gncd_level not between", value1, value2, "gncdLevel");
            return (Criteria) this;
        }

        public Criteria andGncdTypeIsNull() {
            addCriterion("gncd_type is null");
            return (Criteria) this;
        }

        public Criteria andGncdTypeIsNotNull() {
            addCriterion("gncd_type is not null");
            return (Criteria) this;
        }

        public Criteria andGncdTypeEqualTo(Integer value) {
            addCriterion("gncd_type =", value, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeNotEqualTo(Integer value) {
            addCriterion("gncd_type <>", value, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeGreaterThan(Integer value) {
            addCriterion("gncd_type >", value, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("gncd_type >=", value, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeLessThan(Integer value) {
            addCriterion("gncd_type <", value, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeLessThanOrEqualTo(Integer value) {
            addCriterion("gncd_type <=", value, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeIn(List<Integer> values) {
            addCriterion("gncd_type in", values, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeNotIn(List<Integer> values) {
            addCriterion("gncd_type not in", values, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeBetween(Integer value1, Integer value2) {
            addCriterion("gncd_type between", value1, value2, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("gncd_type not between", value1, value2, "gncdType");
            return (Criteria) this;
        }

        public Criteria andGncdImgIsNull() {
            addCriterion("gncd_img is null");
            return (Criteria) this;
        }

        public Criteria andGncdImgIsNotNull() {
            addCriterion("gncd_img is not null");
            return (Criteria) this;
        }

        public Criteria andGncdImgEqualTo(String value) {
            addCriterion("gncd_img =", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgNotEqualTo(String value) {
            addCriterion("gncd_img <>", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgGreaterThan(String value) {
            addCriterion("gncd_img >", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgGreaterThanOrEqualTo(String value) {
            addCriterion("gncd_img >=", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgLessThan(String value) {
            addCriterion("gncd_img <", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgLessThanOrEqualTo(String value) {
            addCriterion("gncd_img <=", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgLike(String value) {
            addCriterion("gncd_img like", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgNotLike(String value) {
            addCriterion("gncd_img not like", value, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgIn(List<String> values) {
            addCriterion("gncd_img in", values, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgNotIn(List<String> values) {
            addCriterion("gncd_img not in", values, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgBetween(String value1, String value2) {
            addCriterion("gncd_img between", value1, value2, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdImgNotBetween(String value1, String value2) {
            addCriterion("gncd_img not between", value1, value2, "gncdImg");
            return (Criteria) this;
        }

        public Criteria andGncdUrlIsNull() {
            addCriterion("gncd_url is null");
            return (Criteria) this;
        }

        public Criteria andGncdUrlIsNotNull() {
            addCriterion("gncd_url is not null");
            return (Criteria) this;
        }

        public Criteria andGncdUrlEqualTo(String value) {
            addCriterion("gncd_url =", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlNotEqualTo(String value) {
            addCriterion("gncd_url <>", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlGreaterThan(String value) {
            addCriterion("gncd_url >", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlGreaterThanOrEqualTo(String value) {
            addCriterion("gncd_url >=", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlLessThan(String value) {
            addCriterion("gncd_url <", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlLessThanOrEqualTo(String value) {
            addCriterion("gncd_url <=", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlLike(String value) {
            addCriterion("gncd_url like", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlNotLike(String value) {
            addCriterion("gncd_url not like", value, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlIn(List<String> values) {
            addCriterion("gncd_url in", values, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlNotIn(List<String> values) {
            addCriterion("gncd_url not in", values, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlBetween(String value1, String value2) {
            addCriterion("gncd_url between", value1, value2, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andGncdUrlNotBetween(String value1, String value2) {
            addCriterion("gncd_url not between", value1, value2, "gncdUrl");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
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

        public Criteria andYxbzIsNull() {
            addCriterion("yxbz is null");
            return (Criteria) this;
        }

        public Criteria andYxbzIsNotNull() {
            addCriterion("yxbz is not null");
            return (Criteria) this;
        }

        public Criteria andYxbzEqualTo(Integer value) {
            addCriterion("yxbz =", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzNotEqualTo(Integer value) {
            addCriterion("yxbz <>", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzGreaterThan(Integer value) {
            addCriterion("yxbz >", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzGreaterThanOrEqualTo(Integer value) {
            addCriterion("yxbz >=", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzLessThan(Integer value) {
            addCriterion("yxbz <", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzLessThanOrEqualTo(Integer value) {
            addCriterion("yxbz <=", value, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzIn(List<Integer> values) {
            addCriterion("yxbz in", values, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzNotIn(List<Integer> values) {
            addCriterion("yxbz not in", values, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzBetween(Integer value1, Integer value2) {
            addCriterion("yxbz between", value1, value2, "yxbz");
            return (Criteria) this;
        }

        public Criteria andYxbzNotBetween(Integer value1, Integer value2) {
            addCriterion("yxbz not between", value1, value2, "yxbz");
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