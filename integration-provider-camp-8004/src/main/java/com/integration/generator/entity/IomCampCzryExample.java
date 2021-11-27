package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampCzryExample
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 用户
*/
public class IomCampCzryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCampCzryExample() {
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

        public Criteria andCzryDmIsNull() {
            addCriterion("czry_dm is null");
            return (Criteria) this;
        }

        public Criteria andCzryDmIsNotNull() {
            addCriterion("czry_dm is not null");
            return (Criteria) this;
        }

        public Criteria andCzryDmEqualTo(String value) {
            addCriterion("czry_dm =", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmNotEqualTo(String value) {
            addCriterion("czry_dm <>", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmGreaterThan(String value) {
            addCriterion("czry_dm >", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmGreaterThanOrEqualTo(String value) {
            addCriterion("czry_dm >=", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmLessThan(String value) {
            addCriterion("czry_dm <", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmLessThanOrEqualTo(String value) {
            addCriterion("czry_dm <=", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmLike(String value) {
            addCriterion("czry_dm like", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmNotLike(String value) {
            addCriterion("czry_dm not like", value, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmIn(List<String> values) {
            addCriterion("czry_dm in", values, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmNotIn(List<String> values) {
            addCriterion("czry_dm not in", values, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmBetween(String value1, String value2) {
            addCriterion("czry_dm between", value1, value2, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryDmNotBetween(String value1, String value2) {
            addCriterion("czry_dm not between", value1, value2, "czryDm");
            return (Criteria) this;
        }

        public Criteria andCzryMcIsNull() {
            addCriterion("czry_mc is null");
            return (Criteria) this;
        }

        public Criteria andCzryMcIsNotNull() {
            addCriterion("czry_mc is not null");
            return (Criteria) this;
        }

        public Criteria andCzryMcEqualTo(String value) {
            addCriterion("czry_mc =", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcNotEqualTo(String value) {
            addCriterion("czry_mc <>", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcGreaterThan(String value) {
            addCriterion("czry_mc >", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcGreaterThanOrEqualTo(String value) {
            addCriterion("czry_mc >=", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcLessThan(String value) {
            addCriterion("czry_mc <", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcLessThanOrEqualTo(String value) {
            addCriterion("czry_mc <=", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcLike(String value) {
            addCriterion("czry_mc like", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcNotLike(String value) {
            addCriterion("czry_mc not like", value, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcIn(List<String> values) {
            addCriterion("czry_mc in", values, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcNotIn(List<String> values) {
            addCriterion("czry_mc not in", values, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcBetween(String value1, String value2) {
            addCriterion("czry_mc between", value1, value2, "czryMc");
            return (Criteria) this;
        }

        public Criteria andCzryMcNotBetween(String value1, String value2) {
            addCriterion("czry_mc not between", value1, value2, "czryMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcIsNull() {
            addCriterion("zzjg_mc is null");
            return (Criteria) this;
        }

        public Criteria andZzjgMcIsNotNull() {
            addCriterion("zzjg_mc is not null");
            return (Criteria) this;
        }

        public Criteria andZzjgMcEqualTo(String value) {
            addCriterion("zzjg_mc =", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcNotEqualTo(String value) {
            addCriterion("zzjg_mc <>", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcGreaterThan(String value) {
            addCriterion("zzjg_mc >", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcGreaterThanOrEqualTo(String value) {
            addCriterion("zzjg_mc >=", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcLessThan(String value) {
            addCriterion("zzjg_mc <", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcLessThanOrEqualTo(String value) {
            addCriterion("zzjg_mc <=", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcLike(String value) {
            addCriterion("zzjg_mc like", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcNotLike(String value) {
            addCriterion("zzjg_mc not like", value, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcIn(List<String> values) {
            addCriterion("zzjg_mc in", values, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcNotIn(List<String> values) {
            addCriterion("zzjg_mc not in", values, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcBetween(String value1, String value2) {
            addCriterion("zzjg_mc between", value1, value2, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andZzjgMcNotBetween(String value1, String value2) {
            addCriterion("zzjg_mc not between", value1, value2, "zzjgMc");
            return (Criteria) this;
        }

        public Criteria andMobileNoIsNull() {
            addCriterion("mobile_no is null");
            return (Criteria) this;
        }

        public Criteria andMobileNoIsNotNull() {
            addCriterion("mobile_no is not null");
            return (Criteria) this;
        }

        public Criteria andMobileNoEqualTo(String value) {
            addCriterion("mobile_no =", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotEqualTo(String value) {
            addCriterion("mobile_no <>", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoGreaterThan(String value) {
            addCriterion("mobile_no >", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_no >=", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLessThan(String value) {
            addCriterion("mobile_no <", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLessThanOrEqualTo(String value) {
            addCriterion("mobile_no <=", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoLike(String value) {
            addCriterion("mobile_no like", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotLike(String value) {
            addCriterion("mobile_no not like", value, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoIn(List<String> values) {
            addCriterion("mobile_no in", values, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotIn(List<String> values) {
            addCriterion("mobile_no not in", values, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoBetween(String value1, String value2) {
            addCriterion("mobile_no between", value1, value2, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andMobileNoNotBetween(String value1, String value2) {
            addCriterion("mobile_no not between", value1, value2, "mobileNo");
            return (Criteria) this;
        }

        public Criteria andEmailAddresIsNull() {
            addCriterion("email_addres is null");
            return (Criteria) this;
        }

        public Criteria andEmailAddresIsNotNull() {
            addCriterion("email_addres is not null");
            return (Criteria) this;
        }

        public Criteria andEmailAddresEqualTo(String value) {
            addCriterion("email_addres =", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresNotEqualTo(String value) {
            addCriterion("email_addres <>", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresGreaterThan(String value) {
            addCriterion("email_addres >", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresGreaterThanOrEqualTo(String value) {
            addCriterion("email_addres >=", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresLessThan(String value) {
            addCriterion("email_addres <", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresLessThanOrEqualTo(String value) {
            addCriterion("email_addres <=", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresLike(String value) {
            addCriterion("email_addres like", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresNotLike(String value) {
            addCriterion("email_addres not like", value, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresIn(List<String> values) {
            addCriterion("email_addres in", values, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresNotIn(List<String> values) {
            addCriterion("email_addres not in", values, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresBetween(String value1, String value2) {
            addCriterion("email_addres between", value1, value2, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andEmailAddresNotBetween(String value1, String value2) {
            addCriterion("email_addres not between", value1, value2, "emailAddres");
            return (Criteria) this;
        }

        public Criteria andCzryDldmIsNull() {
            addCriterion("czry_dldm is null");
            return (Criteria) this;
        }

        public Criteria andCzryDldmIsNotNull() {
            addCriterion("czry_dldm is not null");
            return (Criteria) this;
        }

        public Criteria andCzryDldmEqualTo(String value) {
            addCriterion("czry_dldm =", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmNotEqualTo(String value) {
            addCriterion("czry_dldm <>", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmGreaterThan(String value) {
            addCriterion("czry_dldm >", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmGreaterThanOrEqualTo(String value) {
            addCriterion("czry_dldm >=", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmLessThan(String value) {
            addCriterion("czry_dldm <", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmLessThanOrEqualTo(String value) {
            addCriterion("czry_dldm <=", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmLike(String value) {
            addCriterion("czry_dldm like", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmNotLike(String value) {
            addCriterion("czry_dldm not like", value, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmIn(List<String> values) {
            addCriterion("czry_dldm in", values, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmNotIn(List<String> values) {
            addCriterion("czry_dldm not in", values, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmBetween(String value1, String value2) {
            addCriterion("czry_dldm between", value1, value2, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryDldmNotBetween(String value1, String value2) {
            addCriterion("czry_dldm not between", value1, value2, "czryDldm");
            return (Criteria) this;
        }

        public Criteria andCzryPassIsNull() {
            addCriterion("czry_pass is null");
            return (Criteria) this;
        }

        public Criteria andCzryPassIsNotNull() {
            addCriterion("czry_pass is not null");
            return (Criteria) this;
        }

        public Criteria andCzryPassEqualTo(String value) {
            addCriterion("czry_pass =", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassNotEqualTo(String value) {
            addCriterion("czry_pass <>", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassGreaterThan(String value) {
            addCriterion("czry_pass >", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassGreaterThanOrEqualTo(String value) {
            addCriterion("czry_pass >=", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassLessThan(String value) {
            addCriterion("czry_pass <", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassLessThanOrEqualTo(String value) {
            addCriterion("czry_pass <=", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassLike(String value) {
            addCriterion("czry_pass like", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassNotLike(String value) {
            addCriterion("czry_pass not like", value, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassIn(List<String> values) {
            addCriterion("czry_pass in", values, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassNotIn(List<String> values) {
            addCriterion("czry_pass not in", values, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassBetween(String value1, String value2) {
            addCriterion("czry_pass between", value1, value2, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryPassNotBetween(String value1, String value2) {
            addCriterion("czry_pass not between", value1, value2, "czryPass");
            return (Criteria) this;
        }

        public Criteria andCzryShortIsNull() {
            addCriterion("czry_short is null");
            return (Criteria) this;
        }

        public Criteria andCzryShortIsNotNull() {
            addCriterion("czry_short is not null");
            return (Criteria) this;
        }

        public Criteria andCzryShortEqualTo(String value) {
            addCriterion("czry_short =", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortNotEqualTo(String value) {
            addCriterion("czry_short <>", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortGreaterThan(String value) {
            addCriterion("czry_short >", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortGreaterThanOrEqualTo(String value) {
            addCriterion("czry_short >=", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortLessThan(String value) {
            addCriterion("czry_short <", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortLessThanOrEqualTo(String value) {
            addCriterion("czry_short <=", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortLike(String value) {
            addCriterion("czry_short like", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortNotLike(String value) {
            addCriterion("czry_short not like", value, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortIn(List<String> values) {
            addCriterion("czry_short in", values, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortNotIn(List<String> values) {
            addCriterion("czry_short not in", values, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortBetween(String value1, String value2) {
            addCriterion("czry_short between", value1, value2, "czryShort");
            return (Criteria) this;
        }

        public Criteria andCzryShortNotBetween(String value1, String value2) {
            addCriterion("czry_short not between", value1, value2, "czryShort");
            return (Criteria) this;
        }

        public Criteria andAllowPassIsNull() {
            addCriterion("allow_pass is null");
            return (Criteria) this;
        }

        public Criteria andAllowPassIsNotNull() {
            addCriterion("allow_pass is not null");
            return (Criteria) this;
        }

        public Criteria andAllowPassEqualTo(String value) {
            addCriterion("allow_pass =", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassNotEqualTo(String value) {
            addCriterion("allow_pass <>", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassGreaterThan(String value) {
            addCriterion("allow_pass >", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassGreaterThanOrEqualTo(String value) {
            addCriterion("allow_pass >=", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassLessThan(String value) {
            addCriterion("allow_pass <", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassLessThanOrEqualTo(String value) {
            addCriterion("allow_pass <=", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassLike(String value) {
            addCriterion("allow_pass like", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassNotLike(String value) {
            addCriterion("allow_pass not like", value, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassIn(List<String> values) {
            addCriterion("allow_pass in", values, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassNotIn(List<String> values) {
            addCriterion("allow_pass not in", values, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassBetween(String value1, String value2) {
            addCriterion("allow_pass between", value1, value2, "allowPass");
            return (Criteria) this;
        }

        public Criteria andAllowPassNotBetween(String value1, String value2) {
            addCriterion("allow_pass not between", value1, value2, "allowPass");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(String value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(String value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(String value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(String value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(String value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(String value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLike(String value) {
            addCriterion("last_login_time like", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotLike(String value) {
            addCriterion("last_login_time not like", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<String> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<String> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(String value1, String value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(String value1, String value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLoginBzIsNull() {
            addCriterion("login_bz is null");
            return (Criteria) this;
        }

        public Criteria andLoginBzIsNotNull() {
            addCriterion("login_bz is not null");
            return (Criteria) this;
        }

        public Criteria andLoginBzEqualTo(Integer value) {
            addCriterion("login_bz =", value, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzNotEqualTo(Integer value) {
            addCriterion("login_bz <>", value, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzGreaterThan(Integer value) {
            addCriterion("login_bz >", value, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_bz >=", value, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzLessThan(Integer value) {
            addCriterion("login_bz <", value, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzLessThanOrEqualTo(Integer value) {
            addCriterion("login_bz <=", value, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzIn(List<Integer> values) {
            addCriterion("login_bz in", values, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzNotIn(List<Integer> values) {
            addCriterion("login_bz not in", values, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzBetween(Integer value1, Integer value2) {
            addCriterion("login_bz between", value1, value2, "loginBz");
            return (Criteria) this;
        }

        public Criteria andLoginBzNotBetween(Integer value1, Integer value2) {
            addCriterion("login_bz not between", value1, value2, "loginBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzIsNull() {
            addCriterion("super_bz is null");
            return (Criteria) this;
        }

        public Criteria andSuperBzIsNotNull() {
            addCriterion("super_bz is not null");
            return (Criteria) this;
        }

        public Criteria andSuperBzEqualTo(Integer value) {
            addCriterion("super_bz =", value, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzNotEqualTo(Integer value) {
            addCriterion("super_bz <>", value, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzGreaterThan(Integer value) {
            addCriterion("super_bz >", value, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzGreaterThanOrEqualTo(Integer value) {
            addCriterion("super_bz >=", value, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzLessThan(Integer value) {
            addCriterion("super_bz <", value, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzLessThanOrEqualTo(Integer value) {
            addCriterion("super_bz <=", value, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzIn(List<Integer> values) {
            addCriterion("super_bz in", values, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzNotIn(List<Integer> values) {
            addCriterion("super_bz not in", values, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzBetween(Integer value1, Integer value2) {
            addCriterion("super_bz between", value1, value2, "superBz");
            return (Criteria) this;
        }

        public Criteria andSuperBzNotBetween(Integer value1, Integer value2) {
            addCriterion("super_bz not between", value1, value2, "superBz");
            return (Criteria) this;
        }

        public Criteria andLockBzIsNull() {
            addCriterion("lock_bz is null");
            return (Criteria) this;
        }

        public Criteria andLockBzIsNotNull() {
            addCriterion("lock_bz is not null");
            return (Criteria) this;
        }

        public Criteria andLockBzEqualTo(Integer value) {
            addCriterion("lock_bz =", value, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzNotEqualTo(Integer value) {
            addCriterion("lock_bz <>", value, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzGreaterThan(Integer value) {
            addCriterion("lock_bz >", value, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_bz >=", value, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzLessThan(Integer value) {
            addCriterion("lock_bz <", value, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzLessThanOrEqualTo(Integer value) {
            addCriterion("lock_bz <=", value, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzIn(List<Integer> values) {
            addCriterion("lock_bz in", values, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzNotIn(List<Integer> values) {
            addCriterion("lock_bz not in", values, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzBetween(Integer value1, Integer value2) {
            addCriterion("lock_bz between", value1, value2, "lockBz");
            return (Criteria) this;
        }

        public Criteria andLockBzNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_bz not between", value1, value2, "lockBz");
            return (Criteria) this;
        }

        public Criteria andPassDaysIsNull() {
            addCriterion("pass_days is null");
            return (Criteria) this;
        }

        public Criteria andPassDaysIsNotNull() {
            addCriterion("pass_days is not null");
            return (Criteria) this;
        }

        public Criteria andPassDaysEqualTo(Integer value) {
            addCriterion("pass_days =", value, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysNotEqualTo(Integer value) {
            addCriterion("pass_days <>", value, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysGreaterThan(Integer value) {
            addCriterion("pass_days >", value, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("pass_days >=", value, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysLessThan(Integer value) {
            addCriterion("pass_days <", value, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysLessThanOrEqualTo(Integer value) {
            addCriterion("pass_days <=", value, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysIn(List<Integer> values) {
            addCriterion("pass_days in", values, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysNotIn(List<Integer> values) {
            addCriterion("pass_days not in", values, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysBetween(Integer value1, Integer value2) {
            addCriterion("pass_days between", value1, value2, "passDays");
            return (Criteria) this;
        }

        public Criteria andPassDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("pass_days not between", value1, value2, "passDays");
            return (Criteria) this;
        }

        public Criteria andUpPassBzIsNull() {
            addCriterion("up_pass_bz is null");
            return (Criteria) this;
        }

        public Criteria andUpPassBzIsNotNull() {
            addCriterion("up_pass_bz is not null");
            return (Criteria) this;
        }

        public Criteria andUpPassBzEqualTo(Integer value) {
            addCriterion("up_pass_bz =", value, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzNotEqualTo(Integer value) {
            addCriterion("up_pass_bz <>", value, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzGreaterThan(Integer value) {
            addCriterion("up_pass_bz >", value, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzGreaterThanOrEqualTo(Integer value) {
            addCriterion("up_pass_bz >=", value, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzLessThan(Integer value) {
            addCriterion("up_pass_bz <", value, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzLessThanOrEqualTo(Integer value) {
            addCriterion("up_pass_bz <=", value, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzIn(List<Integer> values) {
            addCriterion("up_pass_bz in", values, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzNotIn(List<Integer> values) {
            addCriterion("up_pass_bz not in", values, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzBetween(Integer value1, Integer value2) {
            addCriterion("up_pass_bz between", value1, value2, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andUpPassBzNotBetween(Integer value1, Integer value2) {
            addCriterion("up_pass_bz not between", value1, value2, "upPassBz");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andCjsjEqualTo(String value) {
            addCriterion("cjsj =", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotEqualTo(String value) {
            addCriterion("cjsj <>", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThan(String value) {
            addCriterion("cjsj >", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjGreaterThanOrEqualTo(String value) {
            addCriterion("cjsj >=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThan(String value) {
            addCriterion("cjsj <", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLessThanOrEqualTo(String value) {
            addCriterion("cjsj <=", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjLike(String value) {
            addCriterion("cjsj like", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotLike(String value) {
            addCriterion("cjsj not like", value, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjIn(List<String> values) {
            addCriterion("cjsj in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotIn(List<String> values) {
            addCriterion("cjsj not in", values, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjBetween(String value1, String value2) {
            addCriterion("cjsj between", value1, value2, "cjsj");
            return (Criteria) this;
        }

        public Criteria andCjsjNotBetween(String value1, String value2) {
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

        public Criteria andXgsjEqualTo(String value) {
            addCriterion("xgsj =", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotEqualTo(String value) {
            addCriterion("xgsj <>", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThan(String value) {
            addCriterion("xgsj >", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThanOrEqualTo(String value) {
            addCriterion("xgsj >=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThan(String value) {
            addCriterion("xgsj <", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThanOrEqualTo(String value) {
            addCriterion("xgsj <=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLike(String value) {
            addCriterion("xgsj like", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotLike(String value) {
            addCriterion("xgsj not like", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjIn(List<String> values) {
            addCriterion("xgsj in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotIn(List<String> values) {
            addCriterion("xgsj not in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjBetween(String value1, String value2) {
            addCriterion("xgsj between", value1, value2, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotBetween(String value1, String value2) {
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

        public Criteria andDeptIdIsNull() {
            addCriterion("dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNotNull() {
            addCriterion("dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeptIdEqualTo(String value) {
            addCriterion("dept_id =", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotEqualTo(String value) {
            addCriterion("dept_id <>", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThan(String value) {
            addCriterion("dept_id >", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThanOrEqualTo(String value) {
            addCriterion("dept_id >=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThan(String value) {
            addCriterion("dept_id <", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThanOrEqualTo(String value) {
            addCriterion("dept_id <=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdIn(List<String> values) {
            addCriterion("dept_id in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotIn(List<String> values) {
            addCriterion("dept_id not in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdBetween(String value1, String value2) {
            addCriterion("dept_id between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotBetween(String value1, String value2) {
            addCriterion("dept_id not between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
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