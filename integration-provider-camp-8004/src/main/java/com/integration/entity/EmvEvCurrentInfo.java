package com.integration.entity;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-12-13 09:47:08
 * @version 1.0
 */

public class EmvEvCurrentInfo extends EmvEventInfo{

	/**
	 * 用来存储统计的数量
	 */
	private int c_num;
	/**
	 *  耗时
	 */
	private String DurationTime;
	/**
	 *计算告警时间差
	 */
	private String timeDifference;
	/**
	 * 事件级别名称
	 */

	private String eventLevelName;
	/**
	 * 事件级别颜色
	 */
	private String eventLevelColour;
	/**
	 * kpi大类名称
	 */
	private String kpiClassName;
	/**
	 * 源告警状态
	 */
	private String source_status;

	/**
	 * 全部告警列表
	 */

	public String getKpiClassName() {
		return kpiClassName;
	}

	public void setKpiClassName(String kpiClassName) {
		this.kpiClassName = kpiClassName;
	}

	public String getEventLevelColour() {
		return eventLevelColour;
	}

	public void setEventLevelColour(String eventLevelColour) {
		this.eventLevelColour = eventLevelColour;
	}

	public String getEventLevelName() {
		return eventLevelName;
	}

	public void setEventLevelName(String eventLevelName) {
		this.eventLevelName = eventLevelName;
	}

	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	@Override
	public String getDurationTime() {
		return DurationTime;
	}

	@Override
	public void setDurationTime(String durationTime) {
		DurationTime = durationTime;
	}

	public int getC_num() {
		return c_num;
	}

	public void setC_num(int c_num) {
		this.c_num = c_num;
	}

	public String getSource_status() {
		return source_status;
	}

	public void setSource_status(String source_status) {
		this.source_status = source_status;
	}

}