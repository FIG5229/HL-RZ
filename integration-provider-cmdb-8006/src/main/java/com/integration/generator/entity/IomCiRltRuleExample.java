package com.integration.generator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltRuleExample
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltRuleExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public IomCiRltRuleExample() {
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

    protected abstract static class GeneratedCriteria implements Serializable {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andRltTypeIsNull() {
            addCriterion("RLT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRltTypeIsNotNull() {
            addCriterion("RLT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRltTypeEqualTo(Integer value) {
            addCriterion("RLT_TYPE =", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeNotEqualTo(Integer value) {
            addCriterion("RLT_TYPE <>", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeGreaterThan(Integer value) {
            addCriterion("RLT_TYPE >", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("RLT_TYPE >=", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeLessThan(Integer value) {
            addCriterion("RLT_TYPE <", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeLessThanOrEqualTo(Integer value) {
            addCriterion("RLT_TYPE <=", value, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeIn(List<Integer> values) {
            addCriterion("RLT_TYPE in", values, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeNotIn(List<Integer> values) {
            addCriterion("RLT_TYPE not in", values, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeBetween(Integer value1, Integer value2) {
            addCriterion("RLT_TYPE between", value1, value2, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("RLT_TYPE not between", value1, value2, "rltType");
            return (Criteria) this;
        }

        public Criteria andRltNameIsNull() {
            addCriterion("RLT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andRltNameIsNotNull() {
            addCriterion("RLT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andRltNameEqualTo(String value) {
            addCriterion("RLT_NAME =", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameNotEqualTo(String value) {
            addCriterion("RLT_NAME <>", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameGreaterThan(String value) {
            addCriterion("RLT_NAME >", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameGreaterThanOrEqualTo(String value) {
            addCriterion("RLT_NAME >=", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameLessThan(String value) {
            addCriterion("RLT_NAME <", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameLessThanOrEqualTo(String value) {
            addCriterion("RLT_NAME <=", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameLike(String value) {
            addCriterion("RLT_NAME like", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameNotLike(String value) {
            addCriterion("RLT_NAME not like", value, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameIn(List<String> values) {
            addCriterion("RLT_NAME in", values, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameNotIn(List<String> values) {
            addCriterion("RLT_NAME not in", values, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameBetween(String value1, String value2) {
            addCriterion("RLT_NAME between", value1, value2, "rltName");
            return (Criteria) this;
        }

        public Criteria andRltNameNotBetween(String value1, String value2) {
            addCriterion("RLT_NAME not between", value1, value2, "rltName");
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

        public Criteria andRltDescIsNull() {
            addCriterion("RLT_DESC is null");
            return (Criteria) this;
        }

        public Criteria andRltDescIsNotNull() {
            addCriterion("RLT_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andRltDescEqualTo(String value) {
            addCriterion("RLT_DESC =", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescNotEqualTo(String value) {
            addCriterion("RLT_DESC <>", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescGreaterThan(String value) {
            addCriterion("RLT_DESC >", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescGreaterThanOrEqualTo(String value) {
            addCriterion("RLT_DESC >=", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescLessThan(String value) {
            addCriterion("RLT_DESC <", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescLessThanOrEqualTo(String value) {
            addCriterion("RLT_DESC <=", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescLike(String value) {
            addCriterion("RLT_DESC like", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescNotLike(String value) {
            addCriterion("RLT_DESC not like", value, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescIn(List<String> values) {
            addCriterion("RLT_DESC in", values, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescNotIn(List<String> values) {
            addCriterion("RLT_DESC not in", values, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescBetween(String value1, String value2) {
            addCriterion("RLT_DESC between", value1, value2, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andRltDescNotBetween(String value1, String value2) {
            addCriterion("RLT_DESC not between", value1, value2, "rltDesc");
            return (Criteria) this;
        }

        public Criteria andDiagXmlIsNull() {
            addCriterion("DIAG_XML is null");
            return (Criteria) this;
        }

        public Criteria andDiagXmlIsNotNull() {
            addCriterion("DIAG_XML is not null");
            return (Criteria) this;
        }

        public Criteria andDiagXmlEqualTo(String value) {
            addCriterion("DIAG_XML =", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotEqualTo(String value) {
            addCriterion("DIAG_XML <>", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlGreaterThan(String value) {
            addCriterion("DIAG_XML >", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlGreaterThanOrEqualTo(String value) {
            addCriterion("DIAG_XML >=", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlLessThan(String value) {
            addCriterion("DIAG_XML <", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlLessThanOrEqualTo(String value) {
            addCriterion("DIAG_XML <=", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlLike(String value) {
            addCriterion("DIAG_XML like", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotLike(String value) {
            addCriterion("DIAG_XML not like", value, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlIn(List<String> values) {
            addCriterion("DIAG_XML in", values, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotIn(List<String> values) {
            addCriterion("DIAG_XML not in", values, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlBetween(String value1, String value2) {
            addCriterion("DIAG_XML between", value1, value2, "diagXml");
            return (Criteria) this;
        }

        public Criteria andDiagXmlNotBetween(String value1, String value2) {
            addCriterion("DIAG_XML not between", value1, value2, "diagXml");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andValidDescIsNull() {
            addCriterion("VALID_DESC is null");
            return (Criteria) this;
        }

        public Criteria andValidDescIsNotNull() {
            addCriterion("VALID_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andValidDescEqualTo(String value) {
            addCriterion("VALID_DESC =", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescNotEqualTo(String value) {
            addCriterion("VALID_DESC <>", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescGreaterThan(String value) {
            addCriterion("VALID_DESC >", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescGreaterThanOrEqualTo(String value) {
            addCriterion("VALID_DESC >=", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescLessThan(String value) {
            addCriterion("VALID_DESC <", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescLessThanOrEqualTo(String value) {
            addCriterion("VALID_DESC <=", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescLike(String value) {
            addCriterion("VALID_DESC like", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescNotLike(String value) {
            addCriterion("VALID_DESC not like", value, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescIn(List<String> values) {
            addCriterion("VALID_DESC in", values, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescNotIn(List<String> values) {
            addCriterion("VALID_DESC not in", values, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescBetween(String value1, String value2) {
            addCriterion("VALID_DESC between", value1, value2, "validDesc");
            return (Criteria) this;
        }

        public Criteria andValidDescNotBetween(String value1, String value2) {
            addCriterion("VALID_DESC not between", value1, value2, "validDesc");
            return (Criteria) this;
        }

        public Criteria andDomainIdIsNull() {
            addCriterion("DOMAIN_ID is null");
            return (Criteria) this;
        }

        public Criteria andDomainIdIsNotNull() {
            addCriterion("DOMAIN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDomainIdEqualTo(Integer value) {
            addCriterion("DOMAIN_ID =", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotEqualTo(Integer value) {
            addCriterion("DOMAIN_ID <>", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdGreaterThan(Integer value) {
            addCriterion("DOMAIN_ID >", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("DOMAIN_ID >=", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdLessThan(Integer value) {
            addCriterion("DOMAIN_ID <", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdLessThanOrEqualTo(Integer value) {
            addCriterion("DOMAIN_ID <=", value, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdIn(List<Integer> values) {
            addCriterion("DOMAIN_ID in", values, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotIn(List<Integer> values) {
            addCriterion("DOMAIN_ID not in", values, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdBetween(Integer value1, Integer value2) {
            addCriterion("DOMAIN_ID between", value1, value2, "domainId");
            return (Criteria) this;
        }

        public Criteria andDomainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("DOMAIN_ID not between", value1, value2, "domainId");
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
            addCriterionForJDBCDate("XGSJ =", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("XGSJ <>", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThan(Date value) {
            addCriterionForJDBCDate("XGSJ >", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XGSJ >=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThan(Date value) {
            addCriterionForJDBCDate("XGSJ <", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("XGSJ <=", value, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjIn(List<Date> values) {
            addCriterionForJDBCDate("XGSJ in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("XGSJ not in", values, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XGSJ between", value1, value2, "xgsj");
            return (Criteria) this;
        }

        public Criteria andXgsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("XGSJ not between", value1, value2, "xgsj");
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

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
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