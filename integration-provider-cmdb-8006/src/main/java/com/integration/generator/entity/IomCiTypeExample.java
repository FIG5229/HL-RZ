package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiTypeExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiTypeExample() {
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

        public Criteria andCiTypeBmIsNull() {
            addCriterion("CI_TYPE_BM is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmIsNotNull() {
            addCriterion("CI_TYPE_BM is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmEqualTo(String value) {
            addCriterion("CI_TYPE_BM =", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmNotEqualTo(String value) {
            addCriterion("CI_TYPE_BM <>", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmGreaterThan(String value) {
            addCriterion("CI_TYPE_BM >", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_BM >=", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmLessThan(String value) {
            addCriterion("CI_TYPE_BM <", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_BM <=", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmLike(String value) {
            addCriterion("CI_TYPE_BM like", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmNotLike(String value) {
            addCriterion("CI_TYPE_BM not like", value, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmIn(List<String> values) {
            addCriterion("CI_TYPE_BM in", values, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmNotIn(List<String> values) {
            addCriterion("CI_TYPE_BM not in", values, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmBetween(String value1, String value2) {
            addCriterion("CI_TYPE_BM between", value1, value2, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeBmNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_BM not between", value1, value2, "ciTypeBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcIsNull() {
            addCriterion("CI_TYPE_MC is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcIsNotNull() {
            addCriterion("CI_TYPE_MC is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcEqualTo(String value) {
            addCriterion("CI_TYPE_MC =", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcNotEqualTo(String value) {
            addCriterion("CI_TYPE_MC <>", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcGreaterThan(String value) {
            addCriterion("CI_TYPE_MC >", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_MC >=", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcLessThan(String value) {
            addCriterion("CI_TYPE_MC <", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_MC <=", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcLike(String value) {
            addCriterion("CI_TYPE_MC like", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcNotLike(String value) {
            addCriterion("CI_TYPE_MC not like", value, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcIn(List<String> values) {
            addCriterion("CI_TYPE_MC in", values, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcNotIn(List<String> values) {
            addCriterion("CI_TYPE_MC not in", values, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcBetween(String value1, String value2) {
            addCriterion("CI_TYPE_MC between", value1, value2, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeMcNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_MC not between", value1, value2, "ciTypeMc");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmIsNull() {
            addCriterion("CI_TYPE_STD_BM is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmIsNotNull() {
            addCriterion("CI_TYPE_STD_BM is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmEqualTo(String value) {
            addCriterion("CI_TYPE_STD_BM =", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmNotEqualTo(String value) {
            addCriterion("CI_TYPE_STD_BM <>", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmGreaterThan(String value) {
            addCriterion("CI_TYPE_STD_BM >", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_STD_BM >=", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmLessThan(String value) {
            addCriterion("CI_TYPE_STD_BM <", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_STD_BM <=", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmLike(String value) {
            addCriterion("CI_TYPE_STD_BM like", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmNotLike(String value) {
            addCriterion("CI_TYPE_STD_BM not like", value, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmIn(List<String> values) {
            addCriterion("CI_TYPE_STD_BM in", values, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmNotIn(List<String> values) {
            addCriterion("CI_TYPE_STD_BM not in", values, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmBetween(String value1, String value2) {
            addCriterion("CI_TYPE_STD_BM between", value1, value2, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeStdBmNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_STD_BM not between", value1, value2, "ciTypeStdBm");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirIsNull() {
            addCriterion("CI_TYPE_DIR is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirIsNotNull() {
            addCriterion("CI_TYPE_DIR is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirEqualTo(String value) {
            addCriterion("CI_TYPE_DIR =", value, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirNotEqualTo(String value) {
            addCriterion("CI_TYPE_DIR <>", value, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirGreaterThan(String value) {
            addCriterion("CI_TYPE_DIR >", value, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_DIR >=", value, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirLessThan(String value) {
            addCriterion("CI_TYPE_DIR <", value, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_DIR <=", value, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirIn(List<String> values) {
            addCriterion("CI_TYPE_DIR in", values, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirNotIn(List<String> values) {
            addCriterion("CI_TYPE_DIR not in", values, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirBetween(String value1, String value2) {
            addCriterion("CI_TYPE_DIR between", value1, value2, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andCiTypeDirNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_DIR not between", value1, value2, "ciTypeDir");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdIsNull() {
            addCriterion("PARENT_CI_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdIsNotNull() {
            addCriterion("PARENT_CI_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdEqualTo(String value) {
            addCriterion("PARENT_CI_TYPE_ID =", value, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdNotEqualTo(String value) {
            addCriterion("PARENT_CI_TYPE_ID <>", value, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdGreaterThan(String value) {
            addCriterion("PARENT_CI_TYPE_ID >", value, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_CI_TYPE_ID >=", value, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdLessThan(String value) {
            addCriterion("PARENT_CI_TYPE_ID <", value, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_CI_TYPE_ID <=", value, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdIn(List<String> values) {
            addCriterion("PARENT_CI_TYPE_ID in", values, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdNotIn(List<String> values) {
            addCriterion("PARENT_CI_TYPE_ID not in", values, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdBetween(String value1, String value2) {
            addCriterion("PARENT_CI_TYPE_ID between", value1, value2, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andParentCiTypeIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_CI_TYPE_ID not between", value1, value2, "parentCiTypeId");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvIsNull() {
            addCriterion("CI_TYPE_LV is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvIsNotNull() {
            addCriterion("CI_TYPE_LV is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvEqualTo(Integer value) {
            addCriterion("CI_TYPE_LV =", value, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvNotEqualTo(Integer value) {
            addCriterion("CI_TYPE_LV <>", value, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvGreaterThan(Integer value) {
            addCriterion("CI_TYPE_LV >", value, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvGreaterThanOrEqualTo(Integer value) {
            addCriterion("CI_TYPE_LV >=", value, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvLessThan(Integer value) {
            addCriterion("CI_TYPE_LV <", value, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvLessThanOrEqualTo(Integer value) {
            addCriterion("CI_TYPE_LV <=", value, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvIn(List<Integer> values) {
            addCriterion("CI_TYPE_LV in", values, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvNotIn(List<Integer> values) {
            addCriterion("CI_TYPE_LV not in", values, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvBetween(Integer value1, Integer value2) {
            addCriterion("CI_TYPE_LV between", value1, value2, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypeLvNotBetween(Integer value1, Integer value2) {
            addCriterion("CI_TYPE_LV not between", value1, value2, "ciTypeLv");
            return (Criteria) this;
        }

        public Criteria andCiTypePathIsNull() {
            addCriterion("CI_TYPE_PATH is null");
            return (Criteria) this;
        }

        public Criteria andCiTypePathIsNotNull() {
            addCriterion("CI_TYPE_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypePathEqualTo(String value) {
            addCriterion("CI_TYPE_PATH =", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathNotEqualTo(String value) {
            addCriterion("CI_TYPE_PATH <>", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathGreaterThan(String value) {
            addCriterion("CI_TYPE_PATH >", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_PATH >=", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathLessThan(String value) {
            addCriterion("CI_TYPE_PATH <", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_PATH <=", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathLike(String value) {
            addCriterion("CI_TYPE_PATH like", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathNotLike(String value) {
            addCriterion("CI_TYPE_PATH not like", value, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathIn(List<String> values) {
            addCriterion("CI_TYPE_PATH in", values, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathNotIn(List<String> values) {
            addCriterion("CI_TYPE_PATH not in", values, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathBetween(String value1, String value2) {
            addCriterion("CI_TYPE_PATH between", value1, value2, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andCiTypePathNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_PATH not between", value1, value2, "ciTypePath");
            return (Criteria) this;
        }

        public Criteria andLeafIsNull() {
            addCriterion("LEAF is null");
            return (Criteria) this;
        }

        public Criteria andLeafIsNotNull() {
            addCriterion("LEAF is not null");
            return (Criteria) this;
        }

        public Criteria andLeafEqualTo(String value) {
            addCriterion("LEAF =", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafNotEqualTo(String value) {
            addCriterion("LEAF <>", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafGreaterThan(String value) {
            addCriterion("LEAF >", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafGreaterThanOrEqualTo(String value) {
            addCriterion("LEAF >=", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafLessThan(String value) {
            addCriterion("LEAF <", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafLessThanOrEqualTo(String value) {
            addCriterion("LEAF <=", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafLike(String value) {
            addCriterion("LEAF like", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafNotLike(String value) {
            addCriterion("LEAF not like", value, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafIn(List<String> values) {
            addCriterion("LEAF in", values, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafNotIn(List<String> values) {
            addCriterion("LEAF not in", values, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafBetween(String value1, String value2) {
            addCriterion("LEAF between", value1, value2, "leaf");
            return (Criteria) this;
        }

        public Criteria andLeafNotBetween(String value1, String value2) {
            addCriterion("LEAF not between", value1, value2, "leaf");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconIsNull() {
            addCriterion("CI_TYPE_ICON is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconIsNotNull() {
            addCriterion("CI_TYPE_ICON is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconEqualTo(String value) {
            addCriterion("CI_TYPE_ICON =", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconNotEqualTo(String value) {
            addCriterion("CI_TYPE_ICON <>", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconGreaterThan(String value) {
            addCriterion("CI_TYPE_ICON >", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_ICON >=", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconLessThan(String value) {
            addCriterion("CI_TYPE_ICON <", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_ICON <=", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconLike(String value) {
            addCriterion("CI_TYPE_ICON like", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconNotLike(String value) {
            addCriterion("CI_TYPE_ICON not like", value, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconIn(List<String> values) {
            addCriterion("CI_TYPE_ICON in", values, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconNotIn(List<String> values) {
            addCriterion("CI_TYPE_ICON not in", values, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconBetween(String value1, String value2) {
            addCriterion("CI_TYPE_ICON between", value1, value2, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeIconNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_ICON not between", value1, value2, "ciTypeIcon");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescIsNull() {
            addCriterion("CI_TYPE_DESC is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescIsNotNull() {
            addCriterion("CI_TYPE_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescEqualTo(String value) {
            addCriterion("CI_TYPE_DESC =", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescNotEqualTo(String value) {
            addCriterion("CI_TYPE_DESC <>", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescGreaterThan(String value) {
            addCriterion("CI_TYPE_DESC >", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_DESC >=", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescLessThan(String value) {
            addCriterion("CI_TYPE_DESC <", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_DESC <=", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescLike(String value) {
            addCriterion("CI_TYPE_DESC like", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescNotLike(String value) {
            addCriterion("CI_TYPE_DESC not like", value, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescIn(List<String> values) {
            addCriterion("CI_TYPE_DESC in", values, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescNotIn(List<String> values) {
            addCriterion("CI_TYPE_DESC not in", values, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescBetween(String value1, String value2) {
            addCriterion("CI_TYPE_DESC between", value1, value2, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeDescNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_DESC not between", value1, value2, "ciTypeDesc");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorIsNull() {
            addCriterion("CI_TYPE_COLOR is null");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorIsNotNull() {
            addCriterion("CI_TYPE_COLOR is not null");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorEqualTo(String value) {
            addCriterion("CI_TYPE_COLOR =", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorNotEqualTo(String value) {
            addCriterion("CI_TYPE_COLOR <>", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorGreaterThan(String value) {
            addCriterion("CI_TYPE_COLOR >", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorGreaterThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_COLOR >=", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorLessThan(String value) {
            addCriterion("CI_TYPE_COLOR <", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorLessThanOrEqualTo(String value) {
            addCriterion("CI_TYPE_COLOR <=", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorLike(String value) {
            addCriterion("CI_TYPE_COLOR like", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorNotLike(String value) {
            addCriterion("CI_TYPE_COLOR not like", value, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorIn(List<String> values) {
            addCriterion("CI_TYPE_COLOR in", values, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorNotIn(List<String> values) {
            addCriterion("CI_TYPE_COLOR not in", values, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorBetween(String value1, String value2) {
            addCriterion("CI_TYPE_COLOR between", value1, value2, "ciTypeColor");
            return (Criteria) this;
        }

        public Criteria andCiTypeColorNotBetween(String value1, String value2) {
            addCriterion("CI_TYPE_COLOR not between", value1, value2, "ciTypeColor");
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
        public Criteria andDomainId(String value){
            addCriterion("DOMAIN_ID=",value,"domainId");
            return (Criteria) this;
        }
        public Criteria andDomainIdIn(List<String> value){
            addCriterion("DOMAIN_ID in",value,"domainId");
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