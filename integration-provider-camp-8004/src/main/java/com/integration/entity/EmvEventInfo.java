

package com.integration.entity;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @version 1.0
 * @date 2018-12-13 05:03:27
 */

public class EmvEventInfo {


    /**
     * 主键
     */
    private String id;


    /**
     * 事件标题
     */
    private String event_title;


    /**
     * 压缩标识
     */
    private String identifier;


    /**
     * 事件序列号
     */
    private String event_serial;


    /**
     * 事件级别
     */
    private String event_level;


    /**
     * 事件描述
     */
    private String event_desc;

    /**
     * 事件时间
     */
    private String event_time;

    /**
     * 首发时间
     */
    private String first_time;

    /**
     * 末发时间
     */
    private String last_time;


    /**
     * 更改时间
     */
    private String state_time;


    /**
     * 重复次数
     */
    private String tally;


    /**
     * 状态 0 打开 1关闭
     */
    private Integer status;


    /**
     * 确认标志 0 未确认 1已确认
     */
    private Integer is_check;


    /**
     * 升级标志
     */
    private Integer is_grade;


    /**
     * 重定级前级别
     */
    private Integer old_event_level;


    /**
     * 服务器名称
     */
    private String server_name;


    /**
     * 服务器告警序列号
     */
    private String server_serial;


    /**
     * 事件来源
     */
    private String source_id;


    /**
     * 事件来源名称
     */
    private String source_name;


    /**
     * 原始标识
     */
    private String source_identifier;


    /**
     * 原始序列号
     */
    private String source_event_id;


    /**
     * 原始CI名称
     */
    private String source_ci_name;


    /**
     * 原始KPI名称
     */
    private String source_kpi_name;


    /**
     * 原始级别
     */
    private String source_level;


    /**
     * 原始描述
     */
    private String source_desc;


    /**
     * 确认信息
     */
    private String check_info;


    /**
     * 确认时间
     */
    private String check_time;


    /**
     * 确认人员
     */
    private String check_ryid;


    /**
     * 关闭信息
     */
    private String close_info;


    /**
     * 关闭时间
     */
    private String close_time;


    /**
     * 关闭人员
     */
    private String close_ryid;


    /**
     * 告警指标ID
     */
    private String kpi_id;


    /**
     * 告警指标名称
     */
    private String kpi_name;


    /**
     * 告警指标分类ID
     */
    private String kpi_category_id;


    /**
     * 告警指标分类名称
     */
    private String kpi_category_name;


    /**
     * 指标描述
     */
    private String kpi_desc;


    /**
     * 告警指标实例
     */
    private String kpi_instance;


    /**
     * 告警指标二级分类
     */
    private String kpi_type;


    /**
     * 告警指标三级分类
     */
    private String kpi_item;


    /**
     * 告警指标指标域
     */
    private Integer kpi_domain;


    /**
     * 告警指标单位
     */
    private Integer kpi_unit;


    /**
     * CIID
     */
    private String ci_id;


    /**
     * CI名称
     */
    private String ci_name;

    private String ci_code;


    /**
     * CI分类ID
     */
    private String ci_category_id;


    /**
     * CI分类名称
     */
    private String ci_category_name;


    /**
     * CI二级分类
     */
    private String ci_type;


    /**
     * CI三级分类
     */
    private String ci_item;


    /**
     * CI应用系统
     */
    private String ci_app;


    /**
     * CI负责人
     */
    private String ci_owner;


    /**
     * CI管理部门
     */
    private String ci_mgmeam;


    /**
     * CI数据中心
     */
    private String ci_dc;


    /**
     * CI物理位置
     */
    private String ci_location;


    /**
     * CI状态
     */
    private String ci_status;


    /**
     * CI应用类型
     */
    private String ci_usage_type;


    /**
     * CI群组
     */
    private String ci_group;


    /**
     * CI部署单元
     */
    private String ci_deploy_unit;


    /**
     * 屏蔽标志
     */
    private Integer is_blackout;


    /**
     * 短信通知标志
     */
    private Integer is_sms;


    /**
     * 邮件通知标志
     */
    private Integer is_email;


    /**
     * 创建工单标志
     */
    private Integer is_ticket;


    /**
     * 自动流转标志
     */
    private Integer is_workflow;


    /**
     * 订阅发送
     */
    private String is_notify;


    /**
     * 重复事件序列号
     */
    private String duplicate_serial;

    /**
     * 告警Id
     */
    private String alarm_id;


    /**
     * 维护期ID
     */
    private String maperiod_id;


    /**
     * 所属场景
     */
    private String scene_id;


    /**
     * 所属视图
     */
    private String view_id;


    /**
     * 持续时间
     */
    private String duration;


    /**
     * 标签
     */
    private String tag;


    /**
     * 过滤类型
     */
    private String filter_type;


    /**
     * 创建时间
     */
    private String cjsj;


    /**
     * 修改时间
     */
    private String xgsj;


    /**
     * 有效标志
     */
    private Integer yxbz;

    /**
     * 是否超时 1：超时 0：为超时
     */
    private Integer is_overtime;

    /**
     * 数据域ID
     */
    private String domain_id;

    /**
     * 扩展字段一
     */
    private String ext1;

    /**
     * 扩展字段二
     */
    private String ext2;

    /**
     * 扩展字段三
     */
    private String ext3;

    /**
     * 扩展字段四
     */
    private String ext4;

    /**
     * 扩展字段五
     */
    private String ext5;
    /**
     * 耗时
     */
    private String DurationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    public String getEvent_serial() {
        return event_serial;
    }

    public void setEvent_serial(String event_serial) {
        this.event_serial = event_serial;
    }


    public String getEvent_level() {
        return event_level;
    }

    public void setEvent_level(String event_level) {
        this.event_level = event_level;
    }


    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }


    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }


    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }


    public String getState_time() {
        return state_time;
    }

    public void setState_time(String state_time) {
        this.state_time = state_time;
    }


    public String getTally() {
        return tally;
    }

    public void setTally(String tally) {
        this.tally = tally;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getIs_check() {
        return is_check;
    }

    public void setIs_check(Integer is_check) {
        this.is_check = is_check;
    }


    public Integer getIs_grade() {
        return is_grade;
    }

    public void setIs_grade(Integer is_grade) {
        this.is_grade = is_grade;
    }


    public Integer getOld_event_level() {
        return old_event_level;
    }

    public void setOld_event_level(Integer old_event_level) {
        this.old_event_level = old_event_level;
    }


    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }


    public String getServer_serial() {
        return server_serial;
    }

    public void setServer_serial(String server_serial) {
        this.server_serial = server_serial;
    }


    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }


    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }


    public String getSource_identifier() {
        return source_identifier;
    }

    public void setSource_identifier(String source_identifier) {
        this.source_identifier = source_identifier;
    }


    public String getSource_event_id() {
        return source_event_id;
    }

    public void setSource_event_id(String source_event_id) {
        this.source_event_id = source_event_id;
    }


    public String getSource_ci_name() {
        return source_ci_name;
    }

    public void setSource_ci_name(String source_ci_name) {
        this.source_ci_name = source_ci_name;
    }


    public String getSource_kpi_name() {
        return source_kpi_name;
    }

    public void setSource_kpi_name(String source_kpi_name) {
        this.source_kpi_name = source_kpi_name;
    }


    public String getSource_level() {
        return source_level;
    }

    public void setSource_level(String source_level) {
        this.source_level = source_level;
    }


    public String getSource_desc() {
        return source_desc;
    }

    public void setSource_desc(String source_desc) {
        this.source_desc = source_desc;
    }


    public String getCheck_info() {
        return check_info;
    }

    public void setCheck_info(String check_info) {
        this.check_info = check_info;
    }


    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }


    public String getCheck_ryid() {
        return check_ryid;
    }

    public void setCheck_ryid(String check_ryid) {
        this.check_ryid = check_ryid;
    }

    public String getClose_info() {
        return close_info;
    }

    public void setClose_info(String close_info) {
        this.close_info = close_info;
    }


    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }


    public String getClose_ryid() {
        return close_ryid;
    }

    public void setClose_ryid(String close_ryid) {
        this.close_ryid = close_ryid;
    }

    public String getKpi_id() {
        return kpi_id;
    }

    public void setKpi_id(String kpi_id) {
        this.kpi_id = kpi_id;
    }


    public String getKpi_name() {
        return kpi_name;
    }

    public void setKpi_name(String kpi_name) {
        this.kpi_name = kpi_name;
    }


    public String getKpi_category_id() {
        return kpi_category_id;
    }

    public void setKpi_category_id(String kpi_category_id) {
        this.kpi_category_id = kpi_category_id;
    }


    public String getKpi_category_name() {
        return kpi_category_name;
    }

    public void setKpi_category_name(String kpi_category_name) {
        this.kpi_category_name = kpi_category_name;
    }


    public String getKpi_desc() {
        return kpi_desc;
    }

    public void setKpi_desc(String kpi_desc) {
        this.kpi_desc = kpi_desc;
    }


    public String getKpi_instance() {
        return kpi_instance;
    }

    public void setKpi_instance(String kpi_instance) {
        this.kpi_instance = kpi_instance;
    }


    public String getKpi_type() {
        return kpi_type;
    }

    public void setKpi_type(String kpi_type) {
        this.kpi_type = kpi_type;
    }


    public String getKpi_item() {
        return kpi_item;
    }

    public void setKpi_item(String kpi_item) {
        this.kpi_item = kpi_item;
    }


    public Integer getKpi_domain() {
        return kpi_domain;
    }

    public void setKpi_domain(Integer kpi_domain) {
        this.kpi_domain = kpi_domain;
    }


    public Integer getKpi_unit() {
        return kpi_unit;
    }

    public void setKpi_unit(Integer kpi_unit) {
        this.kpi_unit = kpi_unit;
    }


    public String getCi_id() {
        return ci_id;
    }

    public void setCi_id(String ci_id) {
        this.ci_id = ci_id;
    }


    public String getCi_name() {
        return ci_name;
    }

    public void setCi_name(String ci_name) {
        this.ci_name = ci_name;
    }


    public String getCi_category_id() {
        return ci_category_id;
    }

    public void setCi_category_id(String ci_category_id) {
        this.ci_category_id = ci_category_id;
    }


    public String getCi_category_name() {
        return ci_category_name;
    }

    public void setCi_category_name(String ci_category_name) {
        this.ci_category_name = ci_category_name;
    }


    public String getCi_type() {
        return ci_type;
    }

    public void setCi_type(String ci_type) {
        this.ci_type = ci_type;
    }


    public String getCi_item() {
        return ci_item;
    }

    public void setCi_item(String ci_item) {
        this.ci_item = ci_item;
    }


    public String getCi_app() {
        return ci_app;
    }

    public void setCi_app(String ci_app) {
        this.ci_app = ci_app;
    }

    public String getCi_owner() {
        return ci_owner;
    }

    public void setCi_owner(String ci_owner) {
        this.ci_owner = ci_owner;
    }

    public String getCi_mgmeam() {
        return ci_mgmeam;
    }

    public void setCi_mgmeam(String ci_mgmeam) {
        this.ci_mgmeam = ci_mgmeam;
    }

    public String getCi_dc() {
        return ci_dc;
    }

    public void setCi_dc(String ci_dc) {
        this.ci_dc = ci_dc;
    }


    public String getCi_location() {
        return ci_location;
    }

    public void setCi_location(String ci_location) {
        this.ci_location = ci_location;
    }


    public String getCi_status() {
        return ci_status;
    }

    public void setCi_status(String ci_status) {
        this.ci_status = ci_status;
    }


    public String getCi_usage_type() {
        return ci_usage_type;
    }

    public void setCi_usage_type(String ci_usage_type) {
        this.ci_usage_type = ci_usage_type;
    }


    public String getCi_group() {
        return ci_group;
    }

    public void setCi_group(String ci_group) {
        this.ci_group = ci_group;
    }


    public String getCi_deploy_unit() {
        return ci_deploy_unit;
    }

    public void setCi_deploy_unit(String ci_deploy_unit) {
        this.ci_deploy_unit = ci_deploy_unit;
    }


    public Integer getIs_blackout() {
        return is_blackout;
    }

    public void setIs_blackout(Integer is_blackout) {
        this.is_blackout = is_blackout;
    }


    public Integer getIs_sms() {
        return is_sms;
    }

    public void setIs_sms(Integer is_sms) {
        this.is_sms = is_sms;
    }


    public Integer getIs_email() {
        return is_email;
    }

    public void setIs_email(Integer is_email) {
        this.is_email = is_email;
    }


    public Integer getIs_ticket() {
        return is_ticket;
    }

    public void setIs_ticket(Integer is_ticket) {
        this.is_ticket = is_ticket;
    }


    public Integer getIs_workflow() {
        return is_workflow;
    }

    public void setIs_workflow(Integer is_workflow) {
        this.is_workflow = is_workflow;
    }


    public String getIs_notify() {
        return is_notify;
    }

    public void setIs_notify(String is_notify) {
        this.is_notify = is_notify;
    }


    public String getDuplicate_serial() {
        return duplicate_serial;
    }

    public void setDuplicate_serial(String duplicate_serial) {
        this.duplicate_serial = duplicate_serial;
    }


    public String getMaperiod_id() {
        return maperiod_id;
    }

    public void setMaperiod_id(String maperiod_id) {
        this.maperiod_id = maperiod_id;
    }


    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }


    public String getView_id() {
        return view_id;
    }

    public void setView_id(String view_id) {
        this.view_id = view_id;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(String filter_type) {
        this.filter_type = filter_type;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }


    public String getXgsj() {
        return xgsj;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }


    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public Integer getIs_overtime() {
        return is_overtime;
    }

    public void setIs_overtime(Integer is_overtime) {
        this.is_overtime = is_overtime;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

    public String getDurationTime() {
        return DurationTime;
    }

    public void setDurationTime(String durationTime) {
        DurationTime = durationTime;
    }

    public String getCi_code() {
        return ci_code;
    }

    public void setCi_code(String ci_code) {
        this.ci_code = ci_code;
    }
}