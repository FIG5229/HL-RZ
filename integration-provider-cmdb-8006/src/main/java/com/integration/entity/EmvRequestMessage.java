package com.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: EmvRequestMessage
 * @Author: sgh
 * @Description: 参数信息类
 * @Date: 2019/10/10 11:18
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmvRequestMessage {
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * CI大类id
     */
    private String cITypeId;
    /**
     * 未知值
     */
    private String zhi;
    /**
     * CIID
     */
    private String ciId;
    /**
     * 等级
     */
    private String level;
    /**
     * 等级2
     */
    private String levelTwo;
    /**
     * 来源
     */
    private String source;
    /**
     * 某一个属性的ID
     */
    private String valueStringId;
    /**
     * CI大类ID
     */
    private String typeId;
    /**
     * 方向（0：北，1：南，2：西，3：东）
     */
    private Integer direction;
    /**
     * 指向（0：上，1：下，2：左，3：右）
     */
    private Integer pointTo;
    /**
     * 指向（0：上，1：下，2：左，3：右，4：全）
     */
    private Integer pointToT;
    /**
     * 是否字符，（0：true，1：false）
     */
    private Integer yesOrNo;
    /**
     * 规则ID
     */
    private String ruleId;
    /**
     * KPI名称
     */
    private String kpiName;

    private String ciCode;

    private String ciName;

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getcITypeId() {
		return cITypeId;
	}
	public void setcITypeId(String cITypeId) {
		this.cITypeId = cITypeId;
	}
	public String getZhi() {
		return zhi;
	}
	public void setZhi(String zhi) {
		this.zhi = zhi;
	}
	public String getCiId() {
		return ciId;
	}
	public void setCiId(String ciId) {
		this.ciId = ciId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevelTwo() {
		return levelTwo;
	}
	public void setLevelTwo(String levelTwo) {
		this.levelTwo = levelTwo;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getValueStringId() {
		return valueStringId;
	}
	public void setValueStringId(String valueStringId) {
		this.valueStringId = valueStringId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getPointTo() {
		return pointTo;
	}
	public void setPointTo(Integer pointTo) {
		this.pointTo = pointTo;
	}
	public Integer getPointToT() {
		return pointToT;
	}
	public void setPointToT(Integer pointToT) {
		this.pointToT = pointToT;
	}
	public Integer getYesOrNo() {
		return yesOrNo;
	}
	public void setYesOrNo(Integer yesOrNo) {
		this.yesOrNo = yesOrNo;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getCiCode() {
		return ciCode;
	}

	public void setCiCode(String ciCode) {
		this.ciCode = ciCode;
	}

	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
	}
}
