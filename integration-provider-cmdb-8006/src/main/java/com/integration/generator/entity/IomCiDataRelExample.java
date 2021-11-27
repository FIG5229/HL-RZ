package com.integration.generator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiDataRelExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiDataRelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IomCiDataRelExample() {
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

        public Criteria andRelIdIsNull() {
            addCriterion("REL_ID is null");
            return (Criteria) this;
        }

        public Criteria andRelIdIsNotNull() {
            addCriterion("REL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRelIdEqualTo(String value) {
            addCriterion("REL_ID =", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdNotEqualTo(String value) {
            addCriterion("REL_ID <>", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdGreaterThan(String value) {
            addCriterion("REL_ID >", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdGreaterThanOrEqualTo(String value) {
            addCriterion("REL_ID >=", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdLessThan(String value) {
            addCriterion("REL_ID <", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdLessThanOrEqualTo(String value) {
            addCriterion("REL_ID <=", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdLike(String value) {
            addCriterion("REL_ID like", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdNotLike(String value) {
            addCriterion("REL_ID not like", value, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdIn(List<String> values) {
            addCriterion("REL_ID in", values, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdNotIn(List<String> values) {
            addCriterion("REL_ID not in", values, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdBetween(String value1, String value2) {
            addCriterion("REL_ID between", value1, value2, "relId");
            return (Criteria) this;
        }

        public Criteria andRelIdNotBetween(String value1, String value2) {
            addCriterion("REL_ID not between", value1, value2, "relId");
            return (Criteria) this;
        }

        public Criteria andRelNameIsNull() {
            addCriterion("REL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRelNameIsNotNull() {
            addCriterion("REL_NAME is not null");
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

        public Criteria andRelNameEqualTo(String value) {
            addCriterion("REL_NAME =", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameNotEqualTo(String value) {
            addCriterion("REL_NAME <>", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameGreaterThan(String value) {
            addCriterion("REL_NAME >", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameGreaterThanOrEqualTo(String value) {
            addCriterion("REL_NAME >=", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameLessThan(String value) {
            addCriterion("REL_NAME <", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameLessThanOrEqualTo(String value) {
            addCriterion("REL_NAME <=", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameLike(String value) {
            addCriterion("REL_NAME like", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameNotLike(String value) {
            addCriterion("REL_NAME not like", value, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameIn(List<String> values) {
            addCriterion("REL_NAME in", values, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameNotIn(List<String> values) {
            addCriterion("REL_NAME not in", values, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameBetween(String value1, String value2) {
            addCriterion("REL_NAME between", value1, value2, "relName");
            return (Criteria) this;
        }

        public Criteria andRelNameNotBetween(String value1, String value2) {
            addCriterion("REL_NAME not between", value1, value2, "relName");
            return (Criteria) this;
        }

        public Criteria andRelDescIsNull() {
            addCriterion("REL_DESC is null");
            return (Criteria) this;
        }

        public Criteria andRelDescIsNotNull() {
            addCriterion("REL_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andRelDescEqualTo(String value) {
            addCriterion("REL_DESC =", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescNotEqualTo(String value) {
            addCriterion("REL_DESC <>", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescGreaterThan(String value) {
            addCriterion("REL_DESC >", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescGreaterThanOrEqualTo(String value) {
            addCriterion("REL_DESC >=", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescLessThan(String value) {
            addCriterion("REL_DESC <", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescLessThanOrEqualTo(String value) {
            addCriterion("REL_DESC <=", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescLike(String value) {
            addCriterion("REL_DESC like", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescNotLike(String value) {
            addCriterion("REL_DESC not like", value, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescIn(List<String> values) {
            addCriterion("REL_DESC in", values, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescNotIn(List<String> values) {
            addCriterion("REL_DESC not in", values, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescBetween(String value1, String value2) {
            addCriterion("REL_DESC between", value1, value2, "relDesc");
            return (Criteria) this;
        }

        public Criteria andRelDescNotBetween(String value1, String value2) {
            addCriterion("REL_DESC not between", value1, value2, "relDesc");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("SOURCE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("SOURCE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Integer value) {
            addCriterion("SOURCE_ID =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Integer value) {
            addCriterion("SOURCE_ID <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Integer value) {
            addCriterion("SOURCE_ID >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_ID >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Integer value) {
            addCriterion("SOURCE_ID <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("SOURCE_ID <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Integer> values) {
            addCriterion("SOURCE_ID in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Integer> values) {
            addCriterion("SOURCE_ID not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_ID between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SOURCE_ID not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdIsNull() {
            addCriterion("OWENR_ID is null");
            return (Criteria) this;
        }

        public Criteria andOwenrIdIsNotNull() {
            addCriterion("OWENR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOwenrIdEqualTo(Integer value) {
            addCriterion("OWENR_ID =", value, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdNotEqualTo(Integer value) {
            addCriterion("OWENR_ID <>", value, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdGreaterThan(Integer value) {
            addCriterion("OWENR_ID >", value, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("OWENR_ID >=", value, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdLessThan(Integer value) {
            addCriterion("OWENR_ID <", value, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdLessThanOrEqualTo(Integer value) {
            addCriterion("OWENR_ID <=", value, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdIn(List<Integer> values) {
            addCriterion("OWENR_ID in", values, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdNotIn(List<Integer> values) {
            addCriterion("OWENR_ID not in", values, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdBetween(Integer value1, Integer value2) {
            addCriterion("OWENR_ID between", value1, value2, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOwenrIdNotBetween(Integer value1, Integer value2) {
            addCriterion("OWENR_ID not between", value1, value2, "owenrId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(Integer value) {
            addCriterion("ORG_ID =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(Integer value) {
            addCriterion("ORG_ID <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(Integer value) {
            addCriterion("ORG_ID >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORG_ID >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(Integer value) {
            addCriterion("ORG_ID <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("ORG_ID <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<Integer> values) {
            addCriterion("ORG_ID in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<Integer> values) {
            addCriterion("ORG_ID not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("ORG_ID between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ORG_ID not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdIsNull() {
            addCriterion("SOURCE_CI_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdIsNotNull() {
            addCriterion("SOURCE_CI_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdEqualTo(String value) {
            addCriterion("SOURCE_CI_ID =", value, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdNotEqualTo(String value) {
            addCriterion("SOURCE_CI_ID <>", value, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdGreaterThan(String value) {
            addCriterion("SOURCE_CI_ID >", value, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_CI_ID >=", value, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdLessThan(String value) {
            addCriterion("SOURCE_CI_ID <", value, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_CI_ID <=", value, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdIn(List<String> values) {
            addCriterion("SOURCE_CI_ID in", values, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdNotIn(List<String> values) {
            addCriterion("SOURCE_CI_ID not in", values, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdBetween(String value1, String value2) {
            addCriterion("SOURCE_CI_ID between", value1, value2, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiIdNotBetween(String value1, String value2) {
            addCriterion("SOURCE_CI_ID not between", value1, value2, "sourceCiId");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmIsNull() {
            addCriterion("SOURCE_CI_BM is null");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmIsNotNull() {
            addCriterion("SOURCE_CI_BM is not null");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmEqualTo(String value) {
            addCriterion("SOURCE_CI_BM =", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmNotEqualTo(String value) {
            addCriterion("SOURCE_CI_BM <>", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmGreaterThan(String value) {
            addCriterion("SOURCE_CI_BM >", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_CI_BM >=", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmLessThan(String value) {
            addCriterion("SOURCE_CI_BM <", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_CI_BM <=", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmLike(String value) {
            addCriterion("SOURCE_CI_BM like", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmNotLike(String value) {
            addCriterion("SOURCE_CI_BM not like", value, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmIn(List<String> values) {
            addCriterion("SOURCE_CI_BM in", values, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmNotIn(List<String> values) {
            addCriterion("SOURCE_CI_BM not in", values, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmBetween(String value1, String value2) {
            addCriterion("SOURCE_CI_BM between", value1, value2, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceCiBmNotBetween(String value1, String value2) {
            addCriterion("SOURCE_CI_BM not between", value1, value2, "sourceCiBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmIsNull() {
            addCriterion("SOURCE_TYPE_BM is null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmIsNotNull() {
            addCriterion("SOURCE_TYPE_BM is not null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmEqualTo(String value) {
            addCriterion("SOURCE_TYPE_BM =", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmNotEqualTo(String value) {
            addCriterion("SOURCE_TYPE_BM <>", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmGreaterThan(String value) {
            addCriterion("SOURCE_TYPE_BM >", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_TYPE_BM >=", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmLessThan(String value) {
            addCriterion("SOURCE_TYPE_BM <", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_TYPE_BM <=", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmLike(String value) {
            addCriterion("SOURCE_TYPE_BM like", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmNotLike(String value) {
            addCriterion("SOURCE_TYPE_BM not like", value, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmIn(List<String> values) {
            addCriterion("SOURCE_TYPE_BM in", values, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmNotIn(List<String> values) {
            addCriterion("SOURCE_TYPE_BM not in", values, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmBetween(String value1, String value2) {
            addCriterion("SOURCE_TYPE_BM between", value1, value2, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeBmNotBetween(String value1, String value2) {
            addCriterion("SOURCE_TYPE_BM not between", value1, value2, "sourceTypeBm");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdIsNull() {
            addCriterion("SOURCE_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdIsNotNull() {
            addCriterion("SOURCE_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdEqualTo(String value) {
            addCriterion("SOURCE_TYPE_ID =", value, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdNotEqualTo(String value) {
            addCriterion("SOURCE_TYPE_ID <>", value, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdGreaterThan(String value) {
            addCriterion("SOURCE_TYPE_ID >", value, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_TYPE_ID >=", value, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdLessThan(String value) {
            addCriterion("SOURCE_TYPE_ID <", value, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_TYPE_ID <=", value, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdIn(List<String> values) {
            addCriterion("SOURCE_TYPE_ID in", values, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdNotIn(List<String> values) {
            addCriterion("SOURCE_TYPE_ID not in", values, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdBetween(String value1, String value2) {
            addCriterion("SOURCE_TYPE_ID between", value1, value2, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andSourceTypeIdNotBetween(String value1, String value2) {
            addCriterion("SOURCE_TYPE_ID not between", value1, value2, "sourceTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdIsNull() {
            addCriterion("TARGET_CI_ID is null");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdIsNotNull() {
            addCriterion("TARGET_CI_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdEqualTo(String value) {
            addCriterion("TARGET_CI_ID =", value, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdNotEqualTo(String value) {
            addCriterion("TARGET_CI_ID <>", value, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdGreaterThan(String value) {
            addCriterion("TARGET_CI_ID >", value, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdGreaterThanOrEqualTo(String value) {
            addCriterion("TARGET_CI_ID >=", value, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdLessThan(String value) {
            addCriterion("TARGET_CI_ID <", value, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdLessThanOrEqualTo(String value) {
            addCriterion("TARGET_CI_ID <=", value, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdIn(List<String> values) {
            addCriterion("TARGET_CI_ID in", values, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdNotIn(List<String> values) {
            addCriterion("TARGET_CI_ID not in", values, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdBetween(String value1, String value2) {
            addCriterion("TARGET_CI_ID between", value1, value2, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiIdNotBetween(String value1, String value2) {
            addCriterion("TARGET_CI_ID not between", value1, value2, "targetCiId");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmIsNull() {
            addCriterion("TARGET_CI_BM is null");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmIsNotNull() {
            addCriterion("TARGET_CI_BM is not null");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmEqualTo(String value) {
            addCriterion("TARGET_CI_BM =", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmNotEqualTo(String value) {
            addCriterion("TARGET_CI_BM <>", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmGreaterThan(String value) {
            addCriterion("TARGET_CI_BM >", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmGreaterThanOrEqualTo(String value) {
            addCriterion("TARGET_CI_BM >=", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmLessThan(String value) {
            addCriterion("TARGET_CI_BM <", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmLessThanOrEqualTo(String value) {
            addCriterion("TARGET_CI_BM <=", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmLike(String value) {
            addCriterion("TARGET_CI_BM like", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmNotLike(String value) {
            addCriterion("TARGET_CI_BM not like", value, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmIn(List<String> values) {
            addCriterion("TARGET_CI_BM in", values, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmNotIn(List<String> values) {
            addCriterion("TARGET_CI_BM not in", values, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmBetween(String value1, String value2) {
            addCriterion("TARGET_CI_BM between", value1, value2, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetCiBmNotBetween(String value1, String value2) {
            addCriterion("TARGET_CI_BM not between", value1, value2, "targetCiBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmIsNull() {
            addCriterion("TARGET_TYPE_BM is null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmIsNotNull() {
            addCriterion("TARGET_TYPE_BM is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmEqualTo(String value) {
            addCriterion("TARGET_TYPE_BM =", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmNotEqualTo(String value) {
            addCriterion("TARGET_TYPE_BM <>", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmGreaterThan(String value) {
            addCriterion("TARGET_TYPE_BM >", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmGreaterThanOrEqualTo(String value) {
            addCriterion("TARGET_TYPE_BM >=", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmLessThan(String value) {
            addCriterion("TARGET_TYPE_BM <", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmLessThanOrEqualTo(String value) {
            addCriterion("TARGET_TYPE_BM <=", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmLike(String value) {
            addCriterion("TARGET_TYPE_BM like", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmNotLike(String value) {
            addCriterion("TARGET_TYPE_BM not like", value, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmIn(List<String> values) {
            addCriterion("TARGET_TYPE_BM in", values, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmNotIn(List<String> values) {
            addCriterion("TARGET_TYPE_BM not in", values, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmBetween(String value1, String value2) {
            addCriterion("TARGET_TYPE_BM between", value1, value2, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBmNotBetween(String value1, String value2) {
            addCriterion("TARGET_TYPE_BM not between", value1, value2, "targetTypeBm");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdIsNull() {
            addCriterion("TARGET_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdIsNotNull() {
            addCriterion("TARGET_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdEqualTo(String value) {
            addCriterion("TARGET_TYPE_ID =", value, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdNotEqualTo(String value) {
            addCriterion("TARGET_TYPE_ID <>", value, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdGreaterThan(String value) {
            addCriterion("TARGET_TYPE_ID >", value, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("TARGET_TYPE_ID >=", value, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdLessThan(String value) {
            addCriterion("TARGET_TYPE_ID <", value, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdLessThanOrEqualTo(String value) {
            addCriterion("TARGET_TYPE_ID <=", value, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdIn(List<String> values) {
            addCriterion("TARGET_TYPE_ID in", values, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdNotIn(List<String> values) {
            addCriterion("TARGET_TYPE_ID not in", values, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdBetween(String value1, String value2) {
            addCriterion("TARGET_TYPE_ID between", value1, value2, "targetTypeId");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIdNotBetween(String value1, String value2) {
            addCriterion("TARGET_TYPE_ID not between", value1, value2, "targetTypeId");
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