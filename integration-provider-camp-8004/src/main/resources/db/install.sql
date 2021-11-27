/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : iommgt

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-01-08 17:26:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for iom_camp_action
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_action`;
CREATE TABLE `iom_camp_action` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `log_time` datetime DEFAULT NULL COMMENT '日志时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户',
  `czry_dldm` varchar(64) DEFAULT NULL COMMENT '登陆名称',
  `czry_mc` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `op_name` varchar(64) DEFAULT NULL COMMENT '模块名称',
  `op_path` varchar(64) DEFAULT NULL COMMENT '访问路径',
  `op_desc` varchar(64) DEFAULT NULL COMMENT '路径描述',
  `is_status` int(1) DEFAULT NULL COMMENT '是否成功0=失败；1=成果',
  `op_param` varchar(4000) DEFAULT NULL COMMENT '前端参数',
  `op_result` varchar(4000) DEFAULT NULL COMMENT '返回结果',
  `op_class` varchar(64) DEFAULT NULL COMMENT '访问类名',
  `op_method` varchar(64) DEFAULT NULL COMMENT '访问方法',
  `cjsj` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='人员操作日志';

-- ----------------------------
-- Records of iom_camp_action
-- ----------------------------

-- ----------------------------
-- Table structure for iom_camp_czry
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_czry`;
CREATE TABLE `iom_camp_czry` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `czry_dm` varchar(45) DEFAULT NULL COMMENT '操作人员代码',
  `czry_mc` varchar(45) DEFAULT NULL COMMENT '操作人员名称',
  `zzjg_mc` varchar(45) DEFAULT NULL COMMENT '组织机构名称',
  `mobile_no` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `email_addres` varchar(45) DEFAULT NULL COMMENT '邮箱地址',
  `czry_dldm` varchar(45) DEFAULT NULL COMMENT '操作人员登陆代码',
  `czry_pass` varchar(45) DEFAULT NULL COMMENT '操作人员登陆密码',
  `czry_short` varchar(45) DEFAULT NULL COMMENT '操作人员简称',
  `allow_pass` varchar(45) DEFAULT NULL COMMENT '上次修改密码',
  `last_login_time` varchar(45) DEFAULT NULL COMMENT '上次修改密码时间',
  `login_bz` int(1) DEFAULT NULL COMMENT '登陆标志 0未登录  1已登陆',
  `super_bz` int(1) DEFAULT NULL COMMENT '超级管理员标志',
  `lock_bz` int(1) DEFAULT '0' COMMENT '锁定标志 0未锁定  1已锁定',
  `pass_days` int(11) DEFAULT NULL COMMENT '密码修改天数',
  `up_pass_bz` int(1) DEFAULT NULL COMMENT '修改密码提示',
  `status` int(1) DEFAULT '1' COMMENT '人员状态 0：停用  1：启用',
  `sort` int(11) DEFAULT NULL COMMENT '排序列',
  `cjr_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `cjsj` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `xgr_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `xgsj` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `yxbz` int(1) DEFAULT NULL COMMENT '有效标志 0：无效 1：有效',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `org_id` bigint(20) DEFAULT NULL COMMENT '机构（单位）ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='系统用户信息表';

-- ----------------------------
-- Records of iom_camp_czry
-- ----------------------------
INSERT INTO `iom_camp_czry` VALUES ('72904780934168577', null, '超级管理员', null, '88888888', '88888888@163.com', 'sysadmin', 'FD91D883D3565907E4CDEF1AF273993A', null, null, null, null, null, '0', null, null, '1', null, '72819027357089793', '2019-09-26 15:20:35', '72904780934168577', '2019-10-14 20:00:25', '1', '64862802476679168', '1');

-- ----------------------------
-- Table structure for iom_camp_czry_role
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_czry_role`;
CREATE TABLE `iom_camp_czry_role` (
  `id` decimal(20,0) NOT NULL,
  `czry_id` varchar(50) DEFAULT NULL,
  `role_dm` varchar(50) DEFAULT NULL,
  `cjr_id` bigint(20) DEFAULT NULL,
  `cjsj` timestamp NULL DEFAULT NULL,
  `xgr_id` bigint(20) DEFAULT NULL,
  `xgsj` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_czry_role
-- ----------------------------
INSERT INTO `iom_camp_czry_role` VALUES ('79497128120041472', '72904780934168577', '79496622567997440', '72819027357089793', '2019-10-14 19:56:13', null, null);

-- ----------------------------
-- Table structure for iom_camp_dict
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_dict`;
CREATE TABLE `iom_camp_dict` (
  `DICT_ID` bigint(20) NOT NULL COMMENT '主键',
  `DICT_BM` varchar(32) DEFAULT NULL COMMENT '字典ID',
  `DICT_NAME` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `SJ_ID` varchar(32) NOT NULL COMMENT '上级字典ID',
  `GNFL` int(4) NOT NULL COMMENT '功能分类 0：枝干 1：叶子',
  `SORT` int(4) NOT NULL COMMENT '排序列',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志 0：无效  1：有效',
  `COUTENT` varchar(125) DEFAULT NULL COMMENT '字典内容',
  PRIMARY KEY (`DICT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_dict
-- ----------------------------
INSERT INTO `iom_camp_dict` VALUES ('-1694498816', '78', '时间', '0', '0', '0', '44', '2019-02-25 00:57:28', '3', '2019-08-14 15:45:29', '1', '');
INSERT INTO `iom_camp_dict` VALUES ('8', '1', '关闭', '1000002', '1', '1', '1', '2018-12-25 20:55:11', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('9', '0', '打开', '1000002', '1', '2', '1', '2018-12-25 20:55:36', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('11', '1', 'Zabbix', '1000003', '1', '0', '1', '2018-12-25 21:35:24', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('12', '2', 'BPC', '1000003', '1', '1', '1', '2018-12-25 21:37:54', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('13', '3', 'Netcool', '1000003', '1', '2', '1', '2018-12-25 21:38:12', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('15', '1', '事件指标', '1000004', '0', '0', '1', '2018-12-27 04:45:24', null, null, '1', 'kpi_name');
INSERT INTO `iom_camp_dict` VALUES ('16', '2', '事件对象', '1000004', '0', '0', '1', '2018-12-27 04:46:36', null, null, '1', 'source_name');
INSERT INTO `iom_camp_dict` VALUES ('18', '1', '基本管理', '1000005', '0', '0', '1', '2018-12-27 04:46:36', null, null, '1', '/camp');
INSERT INTO `iom_camp_dict` VALUES ('161', '3', '归属应用', '1000004', '0', '0', '1', '2018-12-27 04:46:36', null, null, '1', 'CI_APP');
INSERT INTO `iom_camp_dict` VALUES ('162', '4', '负责人', '1000004', '0', '0', '1', '2018-12-27 04:46:36', null, null, '1', 'CI_OWNER');
INSERT INTO `iom_camp_dict` VALUES ('321', '10051', '党建达标', '1005', '0', '1', '11', '2019-07-11 17:23:46', '22', '2019-07-11 17:23:48', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('1234', '10052', '队伍达标', '1005', '0', '2', '11', '2019-07-11 17:23:46', '22', '2019-07-11 17:23:48', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('1235', '10053', '安全达标', '1005', '0', '3', '11', '2019-07-11 17:23:46', '22', '2019-07-11 17:23:48', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('1236', '10054', '服务达标', '1005', '0', '4', '11', '2019-07-11 17:23:46', '22', '2019-07-11 17:23:48', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('1237', '10055', '硬件达标', '1005', '0', '5', '11', '2019-07-11 17:23:46', '22', '2019-07-11 17:23:48', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('1238', '10056', '管理达标', '1005', '0', '6', '11', '2019-07-11 17:23:46', '22', '2019-07-11 17:23:48', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001013', '1007', '启用状态', '0', '0', '0', '11', '2019-07-15 15:08:01', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001014', '100701', '启用', '1007', '0', '1', '11', '2019-07-15 15:08:39', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001015', '100702', '禁用', '1007', '0', '2', '11', '2019-07-15 15:09:36', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001016', '1008', '数据类型', '0', '0', '0', '11', '2019-07-15 15:10:43', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001017', '100801', '数值', '1008', '0', '1', '11', '2019-07-15 15:11:28', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001018', '100802', '百分比', '1008', '0', '2', '11', '2019-07-15 15:11:57', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001019', '1009', '判断类别', '0', '0', '0', '11', '2019-07-15 15:14:57', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001020', '100901', '是', '1009', '0', '1', '11', '2019-07-15 15:15:28', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001021', '100902', '否', '1009', '0', '2', '11', '2019-07-15 15:15:56', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001022', '1010', '图标类型', '0', '0', '0', '11', '2019-07-15 15:16:40', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001023', '101001', '柱状图', '1010', '0', '1', '11', '2019-07-15 15:17:20', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001024', '101002', '折线图', '1010', '0', '2', '11', '2019-07-15 15:17:49', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001025', '1011', 'X轴排序方式', '0', '0', '0', '11', '2019-07-15 15:19:08', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001026', '101101', '降序', '1011', '0', '1', '11', '2019-07-15 15:20:16', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001027', '101102', '升序', '1011', '0', '2', '11', '2019-07-15 15:20:42', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001028', '1012', '时间选择', '0', '0', '0', '11', '2019-07-15 15:21:33', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001029', '101201', '本月', '1012', '0', '1', '11', '2019-07-15 15:22:10', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001030', '101202', '本季度', '1012', '0', '2', '11', '2019-07-15 15:22:50', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001031', '101203', '上季度', '1012', '0', '3', '11', '2019-07-15 15:23:28', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001032', '101204', '本年', '1012', '0', '4', '11', '2019-07-15 15:23:54', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001039', '1015', '指标状态', '0', '0', '0', '11', '2019-07-15 15:34:18', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001040', '101501', '未报送', '1015', '0', '1', '11', '2019-07-15 15:34:57', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001041', '101502', '已报送', '1015', '0', '2', '11', '2019-07-15 15:35:28', '0', null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('10001042', '1016', '公式', '0', '0', '0', '11', '2019-07-15 15:34:18', '0', null, '1', '用于指标配置时的简介内容编写');
INSERT INTO `iom_camp_dict` VALUES ('10001043', '101601', 'time', '1016', '0', '1', '11', '2019-07-23 15:59:22', '0', '2019-07-23 15:59:25', '1', '时间 {{time}}');
INSERT INTO `iom_camp_dict` VALUES ('10001044', '101601', 'val', '1016', '0', '1', '11', '2019-07-23 15:59:22', '0', '2019-07-23 15:59:25', '1', '获取值 val(细项名称,部门名称)');
INSERT INTO `iom_camp_dict` VALUES ('10001045', '101601', 'maxName', '1016', '0', '1', '11', '2019-07-23 15:59:22', '0', '2019-07-23 15:59:25', '1', '降序获取名称 maxName(细项名称,界限值,数量) ');
INSERT INTO `iom_camp_dict` VALUES ('10001046', '101601', 'maxVal', '1016', '0', '1', '11', '2019-07-23 15:59:22', '0', '2019-07-23 15:59:25', '1', '降序获取值 maxVal(细项名称,界限值,数量)');
INSERT INTO `iom_camp_dict` VALUES ('10001047', '101601', 'minName', '1016', '0', '1', '11', '2019-07-23 15:59:22', '0', '2019-07-23 15:59:25', '1', '升序获取名称 minName(细项名称,界限值,数量) ');
INSERT INTO `iom_camp_dict` VALUES ('10001048', '101601', 'minVal', '1016', '0', '1', '11', '2019-07-23 15:59:22', '0', '2019-07-23 15:59:25', '1', '升序获取值 minVal(细项名称,界限值,数量)');
INSERT INTO `iom_camp_dict` VALUES ('1000002554', '1', '9879879', '1000006', '0', '0', '44', '2019-01-18 01:37:14', '44', '2019-01-23 19:22:43', '1', '#FF32yhgh');
INSERT INTO `iom_camp_dict` VALUES ('1000002555', '2', '9879876', '1000006', '0', '0', '44', '2019-01-18 01:37:16', '44', '2019-01-22 02:02:16', '1', '/camp');
INSERT INTO `iom_camp_dict` VALUES ('1000002556', '3', '99', '1000006', '0', '0', '44', '2019-01-18 01:37:19', '44', '2019-01-21 04:18:46', '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002558', '5', '0000', '1000006', '0', '0', '44', '2019-01-18 01:37:24', '44', '2019-01-21 04:18:50', '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002559', '6', '7587596078', '1000006', '0', '0', '44', '2019-01-18 01:37:27', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002560', '7', '9797098676', '1000006', '0', '0', '44', '2019-01-18 01:37:30', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002562', '9', '55', '1000006', '0', '0', '44', '2019-01-18 01:37:35', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002564', '11', '33', '1000006', '0', '0', '44', '2019-01-18 01:37:40', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002565', '12', '2225', '1000006', '0', '0', '44', '2019-01-18 01:37:42', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002566', '13', '664565', '1000006', '0', '0', '44', '2019-01-18 01:37:45', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002567', '14', '5476589', '1000006', '0', '0', '44', '2019-01-18 01:37:48', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002568', '15', '65465867', '1000006', '0', '0', '44', '2019-01-18 01:37:52', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002569', '16', '54345', '1000006', '0', '0', '44', '2019-01-18 01:37:56', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002570', '17', '54667676', '1000006', '0', '0', '44', '2019-01-18 01:37:59', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000002571', '18', '645466', '1000006', '0', '0', '44', '2019-01-18 01:38:02', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('1000004943', '10', '10', '1000006', '0', '0', '44', '2019-01-21 03:52:12', '0', null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('52966465946664960', '256666', '部门类别', '0', '0', '0', '301182497', '2019-08-02 14:52:50', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('52967380124581888', '25666601', '职能部门', '256666', '0', '0', '301182497', '2019-08-02 14:56:28', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('52967439243296768', '25666602', '管理部门', '256666', '0', '0', '301182497', '2019-08-02 14:56:42', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54398575676899328', '601', '601', '8', '0', '0', '4', '2019-08-06 13:43:32', null, null, '1', '601');
INSERT INTO `iom_camp_dict` VALUES ('54398611525615616', '602', '602', '8', '0', '0', '4', '2019-08-06 13:43:40', null, null, '1', '602');
INSERT INTO `iom_camp_dict` VALUES ('54398662016647168', '603', '603', '8', '0', '0', '4', '2019-08-06 13:43:52', null, null, '1', '603');
INSERT INTO `iom_camp_dict` VALUES ('54398706354634752', '604', '604', '8', '0', '0', '4', '2019-08-06 13:44:03', null, null, '1', '604');
INSERT INTO `iom_camp_dict` VALUES ('54398758162677760', '605', '605', '8', '0', '0', '4', '2019-08-06 13:44:15', null, null, '1', '605');
INSERT INTO `iom_camp_dict` VALUES ('54398794913169408', '606', '606', '8', '0', '0', '4', '2019-08-06 13:44:24', null, null, '1', '606');
INSERT INTO `iom_camp_dict` VALUES ('54398837577629696', '607', '607', '8', '0', '0', '4', '2019-08-06 13:44:34', null, null, '1', '607');
INSERT INTO `iom_camp_dict` VALUES ('54442186011459584', '257777', '告警级别', '0', '0', '0', '301182497', '2019-08-06 16:36:49', '301182497', '2019-08-07 17:46:38', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('54442352063954944', '1', '紧急', '257777', '0', '0', '301182497', '2019-08-06 16:37:29', '194002430457954305', '2021-05-12 15:34:11', 1, '#ff0000');
INSERT INTO `iom_camp_dict` VALUES ('54442398494900224', '2', '重要', '257777', '0', '0', '301182497', '2019-08-06 16:37:40', '194002430457954305', '2021-05-12 15:35:00', 1, '#ffa500');
INSERT INTO `iom_camp_dict` VALUES ('54442463707938816', '3', '次要', '257777', '0', '0', '301182497', '2019-08-06 16:37:56', '194002430457954305', '2021-05-12 15:35:48', 1, '#ffd700');
INSERT INTO `iom_camp_dict` VALUES ('54442495538511872', '4', '一般', '257777', '0', '0', '301182497', '2019-08-06 16:38:03', '194002430457954305', '2021-05-12 15:36:44', 1, '#0000ff');
INSERT INTO `iom_camp_dict` VALUES ('191571329730887680', '5', '提示', '257777', '0', '0', '139356496935927809', '2020-08-19 02:19:05', 194002430457954305, '2021-05-12 15:37:34', 1, '#808080');
INSERT INTO `iom_camp_dict` VALUES ('54442667911823360', '258888', '事件来源', '0', '0', '0', '301182497', '2019-08-06 16:38:44', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('54442794806296576', '1', 'Zabbix', '258888', '0', '0', '301182497', '2019-08-06 16:39:14', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54442847348342784', '2', 'BPC', '258888', '0', '0', '301182497', '2019-08-06 16:39:27', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54442971457798144', '3', 'Netcool', '258888', '0', '0', '301182497', '2019-08-06 16:39:57', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54443014243893248', '4', 'ELK', '258888', '0', '0', '301182497', '2019-08-06 16:40:07', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54443137032142848', '5', '共济', '258888', '0', '0', '301182497', '2019-08-06 16:40:36', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54443237032738816', '6', 'BPPM', '258888', '0', '0', '301182497', '2019-08-06 16:41:00', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('54443312253386752', '7', 'vCenter', '258888', '0', '0', '301182497', '2019-08-06 16:41:18', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('55093974268002305', '1000', '指标单位', '0', '0', '0', '54710505847275521', '2019-08-08 11:46:48', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('55096470327672833', '%', '%', '1000', '0', '0', '54710505847275521', '2019-08-08 11:56:43', '54710505847275521', '2019-08-08 11:59:29', '1', '%');
INSERT INTO `iom_camp_dict` VALUES ('55096687777169409', '‰', '‰', '1000', '0', '0', '54710505847275521', '2019-08-08 11:57:35', '54710505847275521', '2019-08-08 11:59:24', '1', '‰');
INSERT INTO `iom_camp_dict` VALUES ('55096743834042369', 'KB', 'KB', '1000', '0', '0', '54710505847275521', '2019-08-08 11:57:48', '54710505847275521', '2019-08-08 11:59:20', '1', 'KB');
INSERT INTO `iom_camp_dict` VALUES ('55096798175444992', 'MB', 'MB', '1000', '0', '0', '54710505847275521', '2019-08-08 11:58:01', '54710505847275521', '2019-08-08 11:59:16', '1', 'MB');
INSERT INTO `iom_camp_dict` VALUES ('55096840156233728', 'GB', 'GB', '1000', '0', '0', '54710505847275521', '2019-08-08 11:58:11', '54710505847275521', '2019-08-08 11:59:11', '1', 'GB');
INSERT INTO `iom_camp_dict` VALUES ('55096868698472449', 'TB', 'TB', '1000', '0', '0', '54710505847275521', '2019-08-08 11:58:18', '54710505847275521', '2019-08-08 11:59:07', '1', 'TB');
INSERT INTO `iom_camp_dict` VALUES ('55097031802372096', 'Kbps', 'Kbps', '1000', '0', '0', '54710505847275521', '2019-08-08 11:58:57', '54710505847275521', '2019-08-08 11:59:01', '1', 'Kbps');
INSERT INTO `iom_camp_dict` VALUES ('55097239642718209', 'Mbps', 'Mbps', '1000', '0', '0', '54710505847275521', '2019-08-08 11:59:46', null, null, '1', 'Mbps');
INSERT INTO `iom_camp_dict` VALUES ('55097276430958593', 'Gbps', 'Gbps', '1000', '0', '0', '54710505847275521', '2019-08-08 11:59:55', null, null, '1', 'Gbps');
INSERT INTO `iom_camp_dict` VALUES ('55097320240463873', 'MHz', 'MHz', '1000', '0', '0', '54710505847275521', '2019-08-08 12:00:05', null, null, '1', 'MHz');
INSERT INTO `iom_camp_dict` VALUES ('55097352314306560', 'GHz', 'GHz', '1000', '0', '0', '54710505847275521', '2019-08-08 12:00:13', null, null, '1', 'GHz');
INSERT INTO `iom_camp_dict` VALUES ('55119762329583617', 'RPM', 'RPM', '1000', '0', '0', '54710505847275521', '2019-08-08 13:29:16', null, null, '1', 'RPM');
INSERT INTO `iom_camp_dict` VALUES ('55119831216832513', 'm/s', 'm/s', '1000', '0', '0', '54710505847275521', '2019-08-08 13:29:33', null, null, '1', 'm/s');
INSERT INTO `iom_camp_dict` VALUES ('55119874543992833', 'km/h', 'km/h', '1000', '0', '0', '54710505847275521', '2019-08-08 13:29:43', null, null, '1', 'km/h');
INSERT INTO `iom_camp_dict` VALUES ('55119983868526593', '℃', '℃', '1000', '0', '0', '54710505847275521', '2019-08-08 13:30:09', null, null, '1', '℃');
INSERT INTO `iom_camp_dict` VALUES ('55120105255878656', '℉', '℉', '1000', '0', '0', '54710505847275521', '2019-08-08 13:30:38', null, null, '1', '℉');
INSERT INTO `iom_camp_dict` VALUES ('55120161652490240', 'k', 'k', '1000', '0', '0', '54710505847275521', '2019-08-08 13:30:51', null, null, '1', 'k');
INSERT INTO `iom_camp_dict` VALUES ('55120204220481536', '毫秒', '毫秒', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:01', null, null, '1', '毫秒');
INSERT INTO `iom_camp_dict` VALUES ('55120249409912832', '秒', '秒', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:12', null, null, '1', '秒');
INSERT INTO `iom_camp_dict` VALUES ('55120287078957056', '分', '分', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:21', null, null, '1', '分');
INSERT INTO `iom_camp_dict` VALUES ('55120325914017792', '时', '时', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:30', null, null, '1', '时');
INSERT INTO `iom_camp_dict` VALUES ('55120374966403072', '天', '天', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:42', null, null, '1', '天');
INSERT INTO `iom_camp_dict` VALUES ('55120408063655936', '笔', '笔', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:50', null, null, '1', '笔');
INSERT INTO `iom_camp_dict` VALUES ('55120435020447745', '个', '个', '1000', '0', '0', '54710505847275521', '2019-08-08 13:31:56', null, null, '1', '个');
INSERT INTO `iom_camp_dict` VALUES ('55120467396280320', '条', '条', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:04', null, null, '1', '条');
INSERT INTO `iom_camp_dict` VALUES ('55120493333856256', '瓦', '瓦', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:10', null, null, '1', '瓦');
INSERT INTO `iom_camp_dict` VALUES ('55120526271725568', '千瓦', '千瓦', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:18', null, null, '1', '千瓦');
INSERT INTO `iom_camp_dict` VALUES ('55120560983785472', '兆瓦', '兆瓦', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:27', null, null, '1', '兆瓦');
INSERT INTO `iom_camp_dict` VALUES ('55120610472378368', '焦', '焦', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:38', null, null, '1', '焦');
INSERT INTO `iom_camp_dict` VALUES ('55120645423513600', '千焦', '千焦', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:47', null, null, '1', '千焦');
INSERT INTO `iom_camp_dict` VALUES ('55120688662593536', '千瓦时', '千瓦时', '1000', '0', '0', '54710505847275521', '2019-08-08 13:32:57', null, null, '1', '千瓦时');
INSERT INTO `iom_camp_dict` VALUES ('55120723693420544', '毫米', '毫米', '1000', '0', '0', '54710505847275521', '2019-08-08 13:33:05', null, null, '1', '毫米');
INSERT INTO `iom_camp_dict` VALUES ('55120756673232897', '厘米', '厘米', '1000', '0', '0', '54710505847275521', '2019-08-08 13:33:13', null, null, '1', '厘米');
INSERT INTO `iom_camp_dict` VALUES ('55120801007026176', '米', '米', '1000', '0', '0', '54710505847275521', '2019-08-08 13:33:24', null, null, '1', '米');
INSERT INTO `iom_camp_dict` VALUES ('55121204998193152', '千米', '千米', '1000', '0', '0', '54710505847275521', '2019-08-08 13:35:00', null, null, '1', '千米');
INSERT INTO `iom_camp_dict` VALUES ('55121240645582849', '毫克', '毫克', '1000', '0', '0', '54710505847275521', '2019-08-08 13:35:09', null, null, '1', '毫克');
INSERT INTO `iom_camp_dict` VALUES ('55121272140611584', '克', '克', '1000', '0', '0', '54710505847275521', '2019-08-08 13:35:16', null, null, '1', '克');
INSERT INTO `iom_camp_dict` VALUES ('55121314284978177', '千克', '千克', '1000', '0', '0', '54710505847275521', '2019-08-08 13:35:26', null, null, '1', '千克');
INSERT INTO `iom_camp_dict` VALUES ('55121343267618817', '吨', '吨', '1000', '0', '0', '54710505847275521', '2019-08-08 13:35:33', null, null, '1', '吨');
INSERT INTO `iom_camp_dict` VALUES ('55140204851380224', '1001', '告警级别', '0', '0', '0', '54710311047020545', '2019-08-08 14:50:30', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('55140684939804672', '100101', '提示', '1001', '0', '0', '54710311047020545', '2019-08-08 14:52:24', '54710505847275521', '2019-08-08 18:58:25', '1', 'blue');
INSERT INTO `iom_camp_dict` VALUES ('55140763515895808', '100102', '警告', '1001', '0', '0', '54710311047020545', '2019-08-08 14:52:43', '54710505847275521', '2019-08-08 18:58:18', '1', 'Violet');
INSERT INTO `iom_camp_dict` VALUES ('55140820772339712', '100103', '次要', '1001', '0', '0', '54710311047020545', '2019-08-08 14:52:57', '54710505847275521', '2019-08-08 18:58:09', '1', 'Gold');
INSERT INTO `iom_camp_dict` VALUES ('55140911310585856', '100104', '主要', '1001', '0', '0', '54710311047020545', '2019-08-08 14:53:18', '54710505847275521', '2019-08-08 18:58:00', '1', 'orange');
INSERT INTO `iom_camp_dict` VALUES ('55140968504115200', '100105', '严重', '1001', '0', '0', '54710311047020545', '2019-08-08 14:53:32', '54710505847275521', '2019-08-08 18:57:49', '1', 'red');
INSERT INTO `iom_camp_dict` VALUES ('55143660131598336', '1002', '阈值策略', '0', '0', '0', '54710311047020545', '2019-08-08 15:04:14', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('55143916667813888', '100201', '通用策略', '1002', '0', '0', '54710311047020545', '2019-08-08 15:05:15', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('55143969239220224', '100202', '趋势策略', '1002', '0', '0', '54710311047020545', '2019-08-08 15:05:27', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('55144013900169216', '100203', '变化策略', '1002', '0', '0', '54710311047020545', '2019-08-08 15:05:38', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('55144177855512576', '1003', '阈值类型', '0', '0', '0', '54710311047020545', '2019-08-08 15:06:17', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('55144240472276992', '100301', '全局阈值', '1003', '0', '0', '54710311047020545', '2019-08-08 15:06:32', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('55144282427899904', '100302', '个性化阈值', '1003', '0', '0', '54710311047020545', '2019-08-08 15:06:42', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('55202403296886784', '100106', '正常', '1001', '0', '0', '54710505847275521', '2019-08-08 18:57:39', null, null, '1', 'green');
INSERT INTO `iom_camp_dict` VALUES ('56648360903458816', 'logicalOperator', '逻辑运算符', '0', '0', '0', '4', '2019-08-12 18:43:22', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('56648612712693760', '=', '等于', 'logicalOperator', '0', '0', '4', '2019-08-12 18:44:22', null, null, '1', '等于');
INSERT INTO `iom_camp_dict` VALUES ('56648752705978368', '!=', '不等于', 'logicalOperator', '0', '0', '4', '2019-08-12 18:44:56', null, null, '1', '不等于');
INSERT INTO `iom_camp_dict` VALUES ('56648891470331904', 'contains', '包含', 'logicalOperator', '0', '0', '4', '2019-08-12 18:45:29', null, null, '1', '包含');
INSERT INTO `iom_camp_dict` VALUES ('56649005794476032', 'not contains', '不包含', 'logicalOperator', '0', '0', '4', '2019-08-12 18:45:56', null, null, '1', '不包含');
INSERT INTO `iom_camp_dict` VALUES ('56649099813994496', '>', '大于', 'logicalOperator', '0', '0', '4', '2019-08-12 18:46:19', null, null, '1', '大于');
INSERT INTO `iom_camp_dict` VALUES ('56649173520498688', '<', '小于', 'logicalOperator', '0', '0', '4', '2019-08-12 18:46:36', null, null, '1', '小于');
INSERT INTO `iom_camp_dict` VALUES ('56649698165014528', 'like', '匹配', 'logicalOperator', '0', '0', '4', '2019-08-12 18:48:41', null, null, '1', '匹配');
INSERT INTO `iom_camp_dict` VALUES ('56650251620204544', 'timeScope', '时间范围', '0', '0', '0', '4', '2019-08-12 18:50:53', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('56653461114208256', '1', '1小时', 'timeScope', '0', '0', '4', '2019-08-12 19:03:38', null, null, '1', '1小时');
INSERT INTO `iom_camp_dict` VALUES ('56653513543008257', '2', '4小时', 'timeScope', '0', '0', '4', '2019-08-12 19:03:51', null, null, '1', '4小时');
INSERT INTO `iom_camp_dict` VALUES ('56653572804329472', '3', '1天', 'timeScope', '0', '0', '4', '2019-08-12 19:04:05', null, null, '1', '1天');
INSERT INTO `iom_camp_dict` VALUES ('56653764110729216', '4', '1周', 'timeScope', '0', '0', '4', '2019-08-12 19:04:51', null, null, '1', '1周');
INSERT INTO `iom_camp_dict` VALUES ('56654077458792448', 'eventStatus', '事件状态', '0', '0', '0', '4', '2019-08-12 19:06:05', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('56654111319408640', '0', '未通知', 'eventStatus', '0', '0', '4', '2019-08-12 19:06:13', '4', '2019-08-14 10:38:51', '1', '未通知');
INSERT INTO `iom_camp_dict` VALUES ('56654206181982208', '1', '已通知', 'eventStatus', '0', '0', '4', '2019-08-12 19:06:36', null, null, '1', '已通知');
INSERT INTO `iom_camp_dict` VALUES ('56655773413031936', 'eventTimeDefine', '事件时间定义', '0', '0', '0', '4', '2019-08-12 19:12:50', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('56656074748608512', '1', '事件台原始发生时间', 'eventTimeDefine', '0', '0', '4', '2019-08-12 19:14:02', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('56656153060458496', '2', '事件台收到事件时间', 'eventTimeDefine', '0', '0', '4', '2019-08-12 19:14:20', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57238441208987649', '201908141', '事件级别定义', '0', '0', '0', '4', '2019-08-14 09:48:09', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57239365767806976', '201908142', '事件单/服务单类型', '0', '0', '0', '4', '2019-08-14 09:51:49', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57239476577124353', '20190814201', '网络', '201908142', '0', '0', '4', '2019-08-14 09:52:15', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239574925164544', '20190814202', '中间件', '201908142', '0', '0', '4', '2019-08-14 09:52:39', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239649533444096', '20190814203', '数整报表', '201908142', '0', '0', '4', '2019-08-14 09:52:57', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239700242579457', '20190814204', '备份', '201908142', '0', '0', '4', '2019-08-14 09:53:09', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239739257995265', '20190814205', '主机', '201908142', '0', '0', '4', '2019-08-14 09:53:18', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239801891536896', '20190814206', '应用巡检', '201908142', '0', '0', '4', '2019-08-14 09:53:33', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239880379547648', '20190814207', '基础设施', '201908142', '0', '0', '4', '2019-08-14 09:53:52', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57239972553572353', '20190814208', '数据库', '201908142', '0', '0', '4', '2019-08-14 09:54:14', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240019496222720', '20190814209', '基金', '201908142', '0', '0', '4', '2019-08-14 09:54:25', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240079277637633', '20190814210', '安全', '201908142', '0', '0', '4', '2019-08-14 09:54:39', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240167479656448', '20190814211', '理财', '201908142', '0', '0', '4', '2019-08-14 09:55:00', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240220323692544', '20190814212', '应用', '201908142', '0', '0', '4', '2019-08-14 09:55:13', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240279182360576', '20190814213', '兴业汇通', '201908142', '0', '0', '4', '2019-08-14 09:55:27', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240385868677120', '20190814214', '资产管理', '201908142', '0', '0', '4', '2019-08-14 09:55:52', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240463232614400', '20190814215', '银行对账', '201908142', '0', '0', '4', '2019-08-14 09:56:11', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240499756613632', '20190814216', '存储', '201908142', '0', '0', '4', '2019-08-14 09:56:19', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240577951023104', '20190814217', '财务数据检查', '201908142', '0', '0', '4', '2019-08-14 09:56:38', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240824488017921', '20190814218', '互联网理财日终批量', '201908142', '0', '0', '4', '2019-08-14 09:57:37', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240868129751041', '20190814219', '核心', '201908142', '0', '0', '4', '2019-08-14 09:57:47', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57240997649858560', '20190814220', 'TO批量', '201908142', '0', '0', '4', '2019-08-14 09:58:18', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241098174742528', '20190814221', '余额宝', '201908142', '0', '0', '4', '2019-08-14 09:58:42', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241162351788032', '20190814222', 'CBUS', '201908142', '0', '0', '4', '2019-08-14 09:58:57', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241217020346368', '20190814223', 'EBUS', '201908142', '0', '0', '4', '2019-08-14 09:59:10', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241318069518337', '20190814224', '应用监控', '201908142', '0', '0', '4', '2019-08-14 09:59:34', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241373618880513', '20190814225', 'ESB', '201908142', '0', '0', '4', '2019-08-14 09:59:48', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241445446336512', '20190814226', '行方电话', '201908142', '0', '0', '4', '2019-08-14 10:00:05', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241490027593728', '20190814227', 'DBUS', '201908142', '0', '0', '4', '2019-08-14 10:00:15', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241549246971905', '20190814228', 'XBUS', '201908142', '0', '0', '4', '2019-08-14 10:00:30', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241596416114688', '20190814229', 'TOAG', '201908142', '0', '0', '4', '2019-08-14 10:00:41', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241693912711168', '20190814230', '互联网理财', '201908142', '0', '0', '4', '2019-08-14 10:01:04', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241773369606144', '20190814231', '理财日启', '201908142', '0', '0', '4', '2019-08-14 10:01:23', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241813081276416', '20190814232', '理财', '201908142', '0', '0', '4', '2019-08-14 10:01:32', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241857553481728', '20190814233', '理财批量', '201908142', '0', '0', '4', '2019-08-14 10:01:43', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57241988281548800', '20190814234', '安鑫宝', '201908142', '0', '0', '4', '2019-08-14 10:02:14', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57242049145094144', '20190814235', '汇率维护', '201908142', '0', '0', '4', '2019-08-14 10:02:29', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57242095685091329', '20190814236', '交接班', '201908142', '0', '0', '4', '2019-08-14 10:02:40', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57243013986009088', '201908144', '事件时间范围', '0', '0', '0', '4', '2019-08-14 10:06:19', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57243773947756545', '201908143', '事件影响范围', '0', '0', '0', '4', '2019-08-14 10:09:20', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57243955254935552', '20190814301', '个人', '201908143', '0', '0', '4', '2019-08-14 10:10:03', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244373762588672', '20190814302', '部门', '201908143', '0', '0', '4', '2019-08-14 10:11:43', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244406486548480', '20190814303', '地区', '201908143', '0', '0', '4', '2019-08-14 10:11:51', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244455115309056', '20190814304', '全部', '201908143', '0', '0', '4', '2019-08-14 10:12:02', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244691971850240', '20190814401', '1小时', '201908144', '0', '0', '4', '2019-08-14 10:12:59', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244816777560064', '20190814402', '4小时', '201908144', '0', '0', '4', '2019-08-14 10:13:29', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244872821850112', '20190814403', '1天', '201908144', '0', '0', '4', '2019-08-14 10:13:42', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244905524838400', '20190814404', '1周', '201908144', '0', '0', '4', '2019-08-14 10:13:50', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57244938794057728', '20190814405', '全部', '201908144', '0', '0', '4', '2019-08-14 10:13:58', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57245117563682817', '201908145', '事件源', '0', '0', '0', '4', '2019-08-14 10:14:40', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57245264624369664', '20190814501', 'Zabbix', '201908145', '0', '0', '4', '2019-08-14 10:15:15', '301182497', '2019-08-14 10:41:29', '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57265684652113920', '20190814502', 'BPC', '201908145', '0', '0', '3', '2019-08-14 11:36:24', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57265750704013312', '20190814503', 'Netcool', '201908145', '0', '0', '3', '2019-08-14 11:36:40', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57265792449921024', '20190814504', 'ELK', '201908145', '0', '0', '3', '2019-08-14 11:36:50', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57265887937445889', '20190814505', '共济', '201908145', '0', '0', '3', '2019-08-14 11:37:12', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57265945978224641', '20190814506', 'BPPM', '201908145', '0', '0', '3', '2019-08-14 11:37:26', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57266022729793536', '20190814507', 'vCenter', '201908145', '0', '0', '3', '2019-08-14 11:37:44', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57266079503892481', '20190814508', '派生时间', '201908145', '0', '0', '3', '2019-08-14 11:37:58', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57266660561797121', '201908146', '事件状态', '0', '0', '0', '3', '2019-08-14 11:40:17', '3', '2019-08-14 11:40:30', '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57267867506327553', '20190814601', '打开', '201908146', '0', '0', '3', '2019-08-14 11:45:04', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57267898988773376', '20190814602', '关闭', '201908146', '0', '0', '3', '2019-08-14 11:45:12', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57267938918547456', '20190814603', '已恢复', '201908146', '0', '0', '3', '2019-08-14 11:45:21', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57269614740127744', '201908147', '事件确认状态', '0', '0', '0', '3', '2019-08-14 11:52:01', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57269726379917312', '20190814701', '未确认', '201908147', '0', '0', '3', '2019-08-14 11:52:27', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57269802162601985', '20190814702', '未确认', '201908147', '0', '0', '3', '2019-08-14 11:52:46', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57269967682420736', '201908148', '事件通知状态', '0', '0', '0', '3', '2019-08-14 11:53:25', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57270046585667584', '20190814801', '已通知', '201908148', '0', '0', '3', '2019-08-14 11:53:44', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270082774122496', '20190814802', '未通知', '201908148', '0', '0', '3', '2019-08-14 11:53:52', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270197115043840', '201908149', '关系操作符', '0', '0', '0', '3', '2019-08-14 11:54:20', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57270398429052929', '20190814901', '等于', '201908149', '0', '0', '3', '2019-08-14 11:55:08', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270438459490304', '20190814902', '不等于', '201908149', '0', '0', '3', '2019-08-14 11:55:17', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270476594102273', '20190814903', '包含', '201908149', '0', '0', '3', '2019-08-14 11:55:26', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270516741980160', '20190814904', '不包含', '201908149', '0', '0', '3', '2019-08-14 11:55:36', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270706748145665', '2019081410', '发送工单状态', '0', '0', '0', '3', '2019-08-14 11:56:21', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57270808116084737', '201908141001', '未派单', '2019081410', '0', '0', '3', '2019-08-14 11:56:45', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57270878316150784', '201908141002', '已派单', '2019081410', '0', '0', '3', '2019-08-14 11:57:02', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271019672584192', '2019081411', '工单严重等级', '0', '0', '0', '3', '2019-08-14 11:57:36', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57271101373431808', '201908141101', '低', '2019081411', '0', '0', '3', '2019-08-14 11:57:55', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271148462882816', '201908141102', '中', '2019081411', '0', '0', '3', '2019-08-14 11:58:07', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271180339593217', '201908141103', '高', '2019081411', '0', '0', '3', '2019-08-14 11:58:14', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271232353157121', '201908141104', '严重', '2019081411', '0', '0', '3', '2019-08-14 11:58:27', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271339215634433', '2019081412', '真假表', '0', '0', '0', '3', '2019-08-14 11:58:52', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57271472716136448', '201908141201', '否', '2019081412', '0', '0', '3', '2019-08-14 11:59:24', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271540584169473', '201908141202', '是', '2019081412', '0', '0', '3', '2019-08-14 11:59:40', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271651246686208', '2019081413', '联系方式', '0', '0', '0', '3', '2019-08-14 12:00:06', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57271743798198272', '201908141301', '电话', '2019081413', '0', '0', '3', '2019-08-14 12:00:28', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271795492995072', '201908141302', '微信', '2019081413', '0', '0', '3', '2019-08-14 12:00:41', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271835703787520', '201908141303', '现场', '2019081413', '0', '0', '3', '2019-08-14 12:00:50', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271910450479104', '201908141304', 'RTX', '2019081413', '0', '0', '3', '2019-08-14 12:01:08', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57271974669467648', '201908141305', '邮件', '2019081413', '0', '0', '3', '2019-08-14 12:01:24', '3', '2019-08-14 12:01:56', '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272425573924864', '2019081414', '默认丰富规则配置信息', '0', '0', '0', '3', '2019-08-14 12:03:11', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('57272623440216065', '201908141401', '设备名称', '2019081414', '0', '0', '3', '2019-08-14 12:03:58', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272666272448512', '201908141402', 'IP地址', '2019081414', '0', '0', '3', '2019-08-14 12:04:08', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272729237340160', '201908141403', '应用名称', '2019081414', '0', '0', '3', '2019-08-14 12:04:23', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272768034652160', '201908141404', '负责人', '2019081414', '0', '0', '3', '2019-08-14 12:04:33', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272833994276864', '201908141405', '管理部门', '2019081414', '0', '0', '3', '2019-08-14 12:04:48', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272879439560704', '201908141406', '数据中心', '2019081414', '0', '0', '3', '2019-08-14 12:04:59', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57272955939471360', '201908141407', '应用等级', '2019081414', '0', '0', '3', '2019-08-14 12:05:17', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57273016387780608', '201908141408', '设备状态', '2019081414', '0', '0', '3', '2019-08-14 12:05:32', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57273072629202945', '201908141409', '所属集群', '2019081414', '0', '0', '3', '2019-08-14 12:05:45', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57273147006795776', '201908141410', '实例数量', '2019081414', '0', '0', '3', '2019-08-14 12:06:03', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57273200584835072', '201908141411', '自定义1', '2019081414', '0', '0', '3', '2019-08-14 12:06:16', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('57273235837960193', '201908141412', '自定义2', '2019081414', '0', '0', '3', '2019-08-14 12:06:24', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('59797154188902400', '201908211115', '运算符', '0', '0', '0', '54710311047020545', '2019-08-21 11:15:33', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59798081843118080', '>', '大于', '201908211115', '0', '0', '54710311047020545', '2019-08-21 11:19:14', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59798462987911168', '≥', '大于或等于', '201908211115', '0', '0', '54710311047020545', '2019-08-21 11:20:45', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59800150087974912', '<', '小于', '201908211115', '0', '0', '54710311047020545', '2019-08-21 11:27:28', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59800325489573888', '≤', '小于或等于', '201908211115', '0', '0', '54710311047020545', '2019-08-21 11:28:09', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59805818685636608', '201908211149', '变化方式', '0', '0', '0', '54710311047020545', '2019-08-21 11:49:59', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59806400460767232', '01', '变化', '201908211149', '0', '0', '54710311047020545', '2019-08-21 11:52:18', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('59806440470233088', '02', '%变化', '201908211149', '0', '0', '54710311047020545', '2019-08-21 11:52:27', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('61950460076113920', '20190827001', 'top10下拉框', '0', '0', '0', '54710505847275521', '2019-08-27 09:52:01', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494016', 'CI_NAME', '事件对象', '20190827001', '1', '0', '54710505847275521', '2019-08-27 10:11:41', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494017', 'KPI_NAME', '事件指标', '20190827001', '2', '0', '54710505847275521', '2019-08-27 10:11:41', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494018', 'CI_APP', '归属应用', '20190827001', '3', '0', '54710505847275521', '2019-08-27 10:11:41', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494019', 'CI_OWNER', '负责人', '20190827001', '4', '0', '54710505847275521', '2019-08-27 10:11:41', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494020', '2019082811', '维护周期', '0', '0', '0', '54710311047020545', '2019-08-28 11:47:07', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494021', '201908281101', '非周期', '2019082811', '0', '0', '54710311047020545', '2019-08-28 11:48:34', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494022', '201908281102', '按天', '2019082811', '0', '0', '54710311047020545', '2019-08-28 11:49:35', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494023', '201908281103', '按周', '2019082811', '0', '0', '54710311047020545', '2019-08-28 11:50:36', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('61955408721494024', '201908281104', '按月', '2019082811', '0', '0', '54710311047020545', '2019-08-28 11:51:28', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('64182799770468352', 'eventRule', '事件规则', '0', '0', '0', '3', '2019-09-02 13:42:33', null, null, '1', null);
INSERT INTO `iom_camp_dict` VALUES ('64182889390161920', '1', '标准化规则', 'eventRule', '0', '3', '3', '2019-09-02 13:42:54', '3', '2019-09-02 13:48:49', '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183171117367297', '2', '过滤规则', 'eventRule', '0', '1', '3', '2019-09-02 13:44:01', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183225861423105', '3', '丰富规则', 'eventRule', '0', '2', '3', '2019-09-02 13:44:14', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183272283979777', '4', '压缩规则', 'eventRule', '0', '6', '3', '2019-09-02 13:44:25', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183353657671681', '5', '归集规则', 'eventRule', '0', '9', '3', '2019-09-02 13:44:45', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183458792095745', '7', '超时升级规则', 'eventRule', '0', '8', '3', '2019-09-02 13:45:10', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183521723432960', '8', '定时清理规则', 'eventRule', '0', '7', '3', '2019-09-02 13:45:25', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64183688614789121', '9', '重定级规则', 'eventRule', '0', '5', '3', '2019-09-02 13:46:05', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('64184522182377473', '10', '维护期规则', 'eventRule', '0', '4', '3', '2019-09-02 13:49:23', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('67235368617000960', '121', 'ldld', '201908145', '0', '0', '65674684586409985', '2019-09-10 23:52:22', null, null, '1', '');
INSERT INTO `iom_camp_dict` VALUES ('67235844402069504', '121', 'ldldld', '258888', '0', '0', '65674684586409985', '2019-09-10 23:54:15', null, null, '1', '');

-- ----------------------------
-- Table structure for iom_camp_face
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_face`;
CREATE TABLE `iom_camp_face` (
  `id` decimal(22,0) NOT NULL,
  `czry_id` decimal(22,0) DEFAULT NULL COMMENT '用户id',
  `face_id` decimal(22,0) DEFAULT NULL COMMENT '面部id',
  `img_up` mediumblob COMMENT '照片上',
  `img_down` mediumblob COMMENT '照片下',
  `img_left` mediumblob COMMENT '照片左',
  `img_right` mediumblob COMMENT '照片右',
  `img_main` mediumblob COMMENT '正脸',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_face
-- ----------------------------

-- ----------------------------
-- Table structure for iom_camp_login
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_login`;
CREATE TABLE `iom_camp_login` (
  `id` decimal(22,0) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `czry_dldm` varchar(50) DEFAULT NULL,
  `czry_mc` varchar(50) DEFAULT NULL,
  `session_id` varchar(50) DEFAULT NULL,
  `login_time` timestamp NULL DEFAULT NULL,
  `logout_time` timestamp NULL DEFAULT NULL,
  `cjsj` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_login
-- ----------------------------

-- ----------------------------
-- Table structure for iom_camp_menu
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_menu`;
CREATE TABLE `iom_camp_menu` (
  `gncd_dm` bigint(20) NOT NULL,
  `gncd_mc` varchar(50) DEFAULT NULL,
  `sj_gncd_dm` varchar(50) DEFAULT NULL,
  `sj_gncd_mc` varchar(45) DEFAULT NULL,
  `gnfl` int(11) DEFAULT NULL,
  `gncd_level` int(11) DEFAULT NULL,
  `gncd_type` int(11) DEFAULT NULL,
  `gncd_img` varchar(50) DEFAULT NULL,
  `gncd_url` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `cjr_id` bigint(20) DEFAULT NULL,
  `cjsj` timestamp NULL DEFAULT NULL,
  `xgr_id` bigint(20) DEFAULT NULL,
  `xgsj` timestamp NULL DEFAULT NULL,
  `yxbz` int(11) DEFAULT NULL,
  PRIMARY KEY (`gncd_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_menu
-- ----------------------------
INSERT INTO `iom_camp_menu` VALUES ('27', '事件查询', '26', '查询统计', '0', '1', '1', 'fa fa-dot-circle-o', 'eventQuery', '1', '1', '2019-01-11 10:33:52', '1', '2019-01-11 10:33:52', '1');
INSERT INTO `iom_camp_menu` VALUES ('28', '事件统计', '26', '查询统计', '0', '1', '1', 'fa fa-bar-chart', 'eventStatistics', '2', '1', '2019-01-11 10:35:22', '1', '2019-01-11 10:35:22', '1');
INSERT INTO `iom_camp_menu` VALUES ('29', '告警统计', '26', '查询统计', '0', '1', '1', 'fa fa-bar-chart', 'warnginStatistics', '3', '1', '2019-01-11 10:35:44', '1', '2019-01-11 10:35:44', '1');
INSERT INTO `iom_camp_menu` VALUES ('30', '大屏可视化', '26', '查询统计', '0', '1', '1', 'el-icon-rank', 'duty5', '4', '1', '2019-01-11 10:36:04', '1', '2019-01-11 10:36:04', '1');
INSERT INTO `iom_camp_menu` VALUES ('42386232352849921', '指标模型', '42386232352849920', '指标管理', '0', '1', '2', 'fa fa-dot-circle-o', 'indicatorModule', '3', '3', '2019-07-04 10:25:36', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('54439099108573184', '性能可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.44476804829114847', '4', '4', '2019-08-06 16:24:33', '4', '2019-09-03 14:54:54', '1');
INSERT INTO `iom_camp_menu` VALUES ('54439190829613057', '性能', '54439099108573184', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-08-06 16:24:55', '4', '2019-09-03 14:55:06', '1');
INSERT INTO `iom_camp_menu` VALUES ('54439372728188929', '监控指标', '54439190829613057', null, null, null, '1', 'fa fa-dot-circle-o', 'monitorIndex', '2', '4', '2019-08-06 16:25:39', '4', '2019-09-03 15:43:22', '1');
INSERT INTO `iom_camp_menu` VALUES ('54439490827206656', '性能查看', '54439190829613057', null, null, null, '1', 'fa fa-dot-circle-o', 'performanceView', '1', '4', '2019-08-06 16:26:07', '4', '2019-09-03 14:55:19', '1');
INSERT INTO `iom_camp_menu` VALUES ('55538051727900672', '告警可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.2618945615979493', '5', '54710505847275521', '2019-08-09 17:11:24', '4', '2019-09-03 15:03:55', '1');
INSERT INTO `iom_camp_menu` VALUES ('55538411657904129', '查询统计', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', 'duty2', '5', '54710505847275521', '2019-08-09 17:12:50', '4', '2019-09-03 15:40:11', '1');
INSERT INTO `iom_camp_menu` VALUES ('56575448850776064', '数据中心可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.5362352150146275', '1', '4', '2019-08-12 13:53:39', '4', '2019-09-03 14:47:01', '1');
INSERT INTO `iom_camp_menu` VALUES ('56576968518418432', '数据中心管理', '56575448850776064', null, null, null, '1', 'fa fa-dot-circle-o', 'dataCenterManagement', '2', '4', '2019-08-12 13:59:41', '4', '2019-09-03 14:43:43', '1');
INSERT INTO `iom_camp_menu` VALUES ('59082417847287808', '阈值', '54439099108573184', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '54710505847275521', '2019-08-19 11:55:27', '4', '2019-09-03 14:57:23', '1');
INSERT INTO `iom_camp_menu` VALUES ('59082533056430081', '阈值列表', '59082417847287808', null, null, null, '1', 'fa fa-dot-circle-o', 'thresholdList', '1', '54710505847275521', '2019-08-19 11:55:54', '4', '2019-09-04 09:35:48', '1');
INSERT INTO `iom_camp_menu` VALUES ('59082673976655873', '自定义事件台', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '54710505847275521', '2019-08-19 11:56:28', '4', '2019-09-03 15:08:31', '1');
INSERT INTO `iom_camp_menu` VALUES ('59082748119367681', '新增事件台', '59082673976655873', null, null, null, '1', 'fa fa-dot-circle-o', 'addEventDesk', '1', '54710505847275521', '2019-08-19 11:56:46', '4', '2019-09-03 15:08:36', '1');
INSERT INTO `iom_camp_menu` VALUES ('59082797331136513', '我的事件台', '59082673976655873', null, null, null, '1', 'fa fa-dot-circle-o', 'eventDesk', '2', '54710505847275521', '2019-08-19 11:56:57', '4', '2019-09-03 15:08:38', '1');
INSERT INTO `iom_camp_menu` VALUES ('59799274975805440', '全局资源管理', '56575448850776064', null, null, null, '1', 'fa fa-dot-circle-o', 'resourceManagement', '3', '4', '2019-08-21 11:23:59', '4', '2019-09-03 14:43:45', '1');
INSERT INTO `iom_camp_menu` VALUES ('59853875653525504', '模拟告警', '59853819915419648', null, null, null, '1', 'fa fa-dot-circle-o', 'simulationAlarm', '1', '4', '2019-08-21 15:00:57', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('60613038398652416', '阈值设置', '59082417847287808', null, null, null, '1', 'fa fa-dot-circle-o', 'threshold', '2', '301182497', '2019-08-23 17:17:35', '4', '2019-09-04 09:35:44', '1');
INSERT INTO `iom_camp_menu` VALUES ('61650495902728193', '规则设置', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', '', '6', '54440149811085312', '2019-08-26 14:00:04', '4', '2019-09-03 15:12:39', '1');
INSERT INTO `iom_camp_menu` VALUES ('61650568413855745', '压缩规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-compress?page=compress-Rule', '6', '54440149811085312', '2019-08-26 14:00:22', '4', '2019-09-03 15:14:50', '1');
INSERT INTO `iom_camp_menu` VALUES ('61650709405384705', '归集规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'imputationRules', '11', '54440149811085312', '2019-08-26 14:00:55', '4', '2019-09-03 15:15:51', '1');
INSERT INTO `iom_camp_menu` VALUES ('61985553679269888', '个性化设置', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', '', '7', '54440149811085312', '2019-08-27 12:11:28', '4', '2019-09-03 15:16:04', '1');
INSERT INTO `iom_camp_menu` VALUES ('61985775738306561', '配置管理', '61985553679269888', null, null, null, '1', 'fa fa-dot-circle-o', 'setManage', '2', '54440149811085312', '2019-08-27 12:12:21', '4', '2019-09-03 15:41:24', '1');
INSERT INTO `iom_camp_menu` VALUES ('62797912773640193', '事件概览', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', 'eventOverView', '3', '301182497', '2019-08-29 17:59:30', '4', '2019-09-03 15:09:07', '1');
INSERT INTO `iom_camp_menu` VALUES ('64208321913176065', '展示信息设置', '61985553679269888', null, null, null, '1', 'fa fa-dot-circle-o', 'showInfoSet', '3', '54440149811085312', '2019-09-02 15:23:58', '4', '2019-09-03 15:16:36', '1');
INSERT INTO `iom_camp_menu` VALUES ('64211127487315968', '标准化规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-stand?page=stand-Rule', '3', '54440149811085312', '2019-09-02 15:35:06', '4', '2019-09-03 15:42:35', '1');
INSERT INTO `iom_camp_menu` VALUES ('64211251848429569', '过滤规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-filt?page=filt-Rule', '5', '54440149811085312', '2019-09-02 15:35:36', '4', '2019-09-03 15:14:46', '1');
INSERT INTO `iom_camp_menu` VALUES ('64211496997109761', '重定级规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-page?page=rewrite-level', '4', '54440149811085312', '2019-09-02 15:36:35', '64862905853689857', '2019-09-10 12:02:45', '1');
INSERT INTO `iom_camp_menu` VALUES ('64227778471542785', '经验管理', '61985553679269888', null, null, null, '1', 'fa fa-dot-circle-o', 'experienceManagement', '4', '301182497', '2019-09-02 16:41:16', '4', '2019-09-03 15:16:40', '1');
INSERT INTO `iom_camp_menu` VALUES ('64560555872108545', '3D数据中心', '56575448850776064', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 14:43:37', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561115702640641', '配置管理', '56575448850776064', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 14:45:50', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561183675531265', '系统配置', '64561115702640641', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 14:46:06', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561228521029633', '功能配置', '64561115702640641', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:46:17', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561263459581953', '展示配置', '64561115702640641', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:46:25', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561789928620033', '地理信息可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.6495222887216539', '2', '4', '2019-09-03 14:48:31', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561847700963329', '3D地理信息', '64561789928620033', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 14:48:45', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561896749154305', '映射管理', '64561789928620033', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:48:56', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561939027738625', '配置管理', '64561789928620033', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:49:06', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64561977296568320', '系统配置', '64561939027738625', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 14:49:16', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562020720197633', '功能配置', '64561939027738625', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:49:26', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562055675527169', '展示配置', '64561939027738625', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:49:34', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562128799023105', '地图设计', '64561789928620033', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 14:49:52', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562174026203137', '全局配置', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 14:50:02', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562222633992193', '主题配置', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:50:14', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562284449644544', '图表配置', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:50:29', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562330192723969', '默认配置', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 14:50:40', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562375797391361', '点配置', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '5', '4', '2019-09-03 14:50:51', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562425722191873', '线配置', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '6', '4', '2019-09-03 14:51:02', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562487328129025', '大数据特效', '64562128799023105', null, null, null, '1', 'fa fa-dot-circle-o', '', '7', '4', '2019-09-03 14:51:17', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562645432418305', '架构可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.26731909136377774', '3', '4', '2019-09-03 14:51:55', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64562694157647873', '视图广场', '64562645432418305', null, null, null, '1', 'fa fa-dot-circle-o', 'frameManageSquare', '1', '4', '2019-09-03 14:52:06', '71477989640126464', '2019-09-22 19:04:47', '1');
INSERT INTO `iom_camp_menu` VALUES ('64562731629559809', '我的视图', '64562645432418305', null, null, null, '1', 'fa fa-dot-circle-o', 'topology', '2', '4', '2019-09-03 14:52:15', '61962813039591424', '2019-09-12 18:01:24', '1');
INSERT INTO `iom_camp_menu` VALUES ('64562779293630465', '统计视图', '64562645432418305', null, null, null, '1', 'fa fa-dot-circle-o', 'viewStatistics', '3', '4', '2019-09-03 14:52:27', '71477822983651329', '2019-09-22 19:03:13', '1');
INSERT INTO `iom_camp_menu` VALUES ('64562834612305921', '创建视图', '64562645432418305', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 14:52:40', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64562899691126785', '架构设置', '64562645432418305', null, null, null, '1', 'fa fa-dot-circle-o', 'setting', '5', '4', '2019-09-03 14:52:56', '71478086625017857', '2019-09-22 17:47:55', '1');
INSERT INTO `iom_camp_menu` VALUES ('64562946818326529', '配置标签设置', '64562899691126785', null, null, null, '1', 'fa fa-dot-circle-o', 'setting', '1', '4', '2019-09-03 14:53:07', '71478086625017857', '2019-09-22 17:45:20', '0');
INSERT INTO `iom_camp_menu` VALUES ('64562997707816961', '监控指标设置', '64562899691126785', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:53:19', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64563045619351553', '自动化工具关联', '64562899691126785', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:53:30', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64563089852481537', '关联查询设置', '64562899691126785', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 14:53:41', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64563153446518785', '可视化风格设置', '64562899691126785', null, null, null, '1', 'fa fa-dot-circle-o', '', '5', '4', '2019-09-03 14:53:56', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64563643316060161', '仪表盘', '54439099108573184', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:55:53', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64563692427165697', '仪表盘模板', '64563643316060161', null, null, null, '1', 'fa fa-dot-circle-o', 'dashCIMould', '2', '4', '2019-09-03 14:56:05', '72819027357089793', '2019-11-09 15:06:26', '1');
INSERT INTO `iom_camp_menu` VALUES ('64563727629959169', '我的仪表盘', '64563643316060161', null, null, null, '1', 'fa fa-dot-circle-o', 'myDashboardList', '1', '4', '2019-09-03 14:56:13', '72819027357089793', '2019-10-18 19:39:20', '1');
INSERT INTO `iom_camp_menu` VALUES ('64563862032236545', '告警', '54439099108573184', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:56:45', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64563970635350017', '告警列表', '64563862032236545', null, null, null, '1', 'fa fa-dot-circle-o', 'alarmList', '1', '4', '2019-09-03 14:57:11', '4', '2019-09-03 15:43:41', '1');
INSERT INTO `iom_camp_menu` VALUES ('64564158296899585', '设置', '54439099108573184', null, null, null, '1', 'fa fa-dot-circle-o', '', '5', '4', '2019-09-03 14:57:56', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64564219152056321', '标签管理', '64564158296899585', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 14:58:10', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64564261002821633', '监控路径', '64564158296899585', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 14:58:20', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64564298726391809', '计划管理', '64564158296899585', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 14:58:29', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64564347850080257', '高级设置', '64564158296899585', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 14:58:41', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64564404502544385', '监控指标单位', '64564347850080257', null, null, null, '1', 'fa fa-dot-circle-o', 'setKpiUnit', '1', '4', '2019-09-03 14:58:54', '4', '2019-09-03 15:44:00', '0');
INSERT INTO `iom_camp_menu` VALUES ('64566728721580033', '已关注事件台', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 15:08:08', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64566784459685889', '全部故障', '64566728721580033', null, null, null, '1', 'fa fa-dot-circle-o', 'alarm', '1', '4', '2019-09-03 15:08:22', '4', '2019-09-03 15:42:12', '1');
INSERT INTO `iom_camp_menu` VALUES ('64566948373086209', '事件概览', '62797912773640193', null, null, null, '1', 'fa fa-dot-circle-o', 'eventOverView', '1', '4', '2019-09-03 15:09:01', '4', '2019-09-03 15:09:12', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567042421964801', '应用概览', '62797912773640193', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 15:09:23', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64567083844911105', '应用告警墙', '62797912773640193', null, null, null, '1', 'fa fa-dot-circle-o', 'applicationAlarm', '3', '4', '2019-09-03 15:09:33', '72819027357089793', '2019-11-20 21:23:06', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567147539611649', '同源分析', '62797912773640193', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 15:09:48', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64567335977107457', '维护期管理', '55538051727900672', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 15:10:33', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64567401504718849', '维护期事件', '64567335977107457', null, null, null, '1', 'fa fa-dot-circle-o', 'maintenanceConfig', '1', '4', '2019-09-03 15:10:49', '64862905853689857', '2019-09-09 10:38:53', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567449797935105', '维护期设置', '64567335977107457', null, null, null, '1', 'fa fa-dot-circle-o', 'maintenance', '2', '4', '2019-09-03 15:11:00', '64862905853689857', '2019-09-09 14:39:04', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567647081218049', '事件查询', '55538411657904129', null, null, null, '1', 'fa fa-dot-circle-o', 'eventQuery', '1', '4', '2019-09-03 15:11:47', '4', '2019-09-03 15:40:23', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567689980559361', '事件统计', '55538411657904129', null, null, null, '1', 'fa fa-dot-circle-o', 'eventStatistics', '2', '4', '2019-09-03 15:11:58', '4', '2019-09-03 15:40:31', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567733479686145', '监控源状态', '55538411657904129', null, null, null, '1', 'fa fa-dot-circle-o', 'monitorSourceStatus', '4', '4', '2019-09-03 15:12:08', '64862905853689857', '2019-09-05 15:17:35', '1');
INSERT INTO `iom_camp_menu` VALUES ('64567801792315393', '未丰富告警统计', '55538411657904129', null, null, null, '1', 'fa fa-dot-circle-o', 'unenrichedAlarmStatistics', '5', '4', '2019-09-03 15:12:24', '71478086625017857', '2019-09-22 18:07:13', '1');
INSERT INTO `iom_camp_menu` VALUES ('64568246367567873', '规则看板', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'ruleKanBan', '1', '4', '2019-09-03 15:14:10', '64862905853689857', '2019-09-09 10:39:33', '1');
INSERT INTO `iom_camp_menu` VALUES ('64568319885328385', '丰富规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-page?page=rich', '2', '4', '2019-09-03 15:14:28', '64862905853689857', '2019-09-10 12:02:29', '1');
INSERT INTO `iom_camp_menu` VALUES ('64568482460745729', '通知规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', '', '7', '4', '2019-09-03 15:15:07', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64568526916173825', '超时升级规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-page?page=time-out', '8', '4', '2019-09-03 15:15:17', '64862905853689857', '2019-09-10 12:03:14', '1');
INSERT INTO `iom_camp_menu` VALUES ('64568575666569217', '定时清理规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', 'rule-page?page=delay-clear', '9', '4', '2019-09-03 15:15:29', '64862905853689857', '2019-09-10 12:02:59', '1');
INSERT INTO `iom_camp_menu` VALUES ('64568635443789825', '派生规则', '61650495902728193', null, null, null, '1', 'fa fa-dot-circle-o', '', '10', '4', '2019-09-03 15:15:43', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64568806344900609', '模拟告警', '61985553679269888', null, null, null, '1', 'fa fa-dot-circle-o', 'simulationAlarm', '1', '4', '2019-09-03 15:16:24', '4', '2019-09-03 15:41:45', '1');
INSERT INTO `iom_camp_menu` VALUES ('64569041746018305', '配置管理', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.1867757802700183', '6', '4', '2019-09-03 15:17:20', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64569085089955841', '配置查询', '64569041746018305', null, null, null, '1', 'fa fa-dot-circle-o', 'config', '1', '4', '2019-09-03 15:17:30', '71478086625017857', '2019-09-22 18:11:57', '1');
INSERT INTO `iom_camp_menu` VALUES ('64569135580987392', '影响分析', '64569041746018305', null, null, null, '1', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 15:17:42', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64569179424047105', '根因分析', '64569041746018305', null, null, null, '1', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 15:17:53', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64569231894790145', '路径分析', '64569041746018305', null, null, null, '1', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 15:18:05', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64569277893722113', '关系遍历', '64569041746018305', null, null, null, '1', 'fa fa-dot-circle-o', '', '5', '4', '2019-09-03 15:18:16', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('64569584979689473', '系统设置', '0', null, null, null, '2', 'fa fa-dot-circle-o', '0.608019344553423', '7', '4', '2019-09-03 15:19:29', '64862905853689857', '2019-09-04 14:30:26', '1');
INSERT INTO `iom_camp_menu` VALUES ('64569669314560001', '基本信息', '64569584979689473', null, null, null, '2', 'fa fa-dot-circle-o', '', '1', '4', '2019-09-03 15:19:50', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64569710188052481', '用户权限', '64569584979689473', null, null, null, '2', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 15:19:59', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64569753439715328', '指标管理', '64569584979689473', null, null, null, '2', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 15:20:10', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64569813867053057', '业务数据', '64569584979689473', null, null, null, '2', 'fa fa-dot-circle-o', '', '4', '4', '2019-09-03 15:20:24', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64569956125261825', '日志查询', '64569584979689473', null, null, null, '2', 'fa fa-dot-circle-o', '', '5', '4', '2019-09-03 15:20:58', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64570056490762241', '对象管理', '64569669314560001', null, null, null, '2', 'fa fa-dot-circle-o', 'object', '1', '4', '2019-09-03 15:21:22', '4', '2019-09-03 15:35:23', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570098731597825', '关系管理', '64569669314560001', null, null, null, '2', 'fa fa-dot-circle-o', 'relationship', '2', '4', '2019-09-03 15:21:32', '4', '2019-09-03 15:35:34', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570144231407616', '图标管理', '64569669314560001', null, null, null, '2', 'fa fa-dot-circle-o', 'icons', '3', '4', '2019-09-03 15:21:43', '4', '2019-09-03 15:36:07', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570193191518209', '可视化建模', '64569669314560001', null, null, null, '2', 'fa fa-dot-circle-o', '/vm', '4', '4', '2019-09-03 15:21:54', '65732078498627585', '2019-09-26 21:36:47', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570258329059329', '用户管理', '64569710188052481', null, null, null, '2', 'fa fa-dot-circle-o', 'users', '1', '4', '2019-09-03 15:22:10', '4', '2019-09-03 15:36:29', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570295427678209', '角色管理', '64569710188052481', null, null, null, '2', 'fa fa-dot-circle-o', 'roles', '2', '4', '2019-09-03 15:22:19', '4', '2019-09-03 15:36:40', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570326067068928', '菜单管理', '64569710188052481', null, null, null, '2', 'fa fa-dot-circle-o', 'menus', '3', '4', '2019-09-03 15:22:26', '4', '2019-09-03 15:36:51', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570372070195201', '指标模型', '64569753439715328', null, null, null, '2', 'fa fa-dot-circle-o', 'indicatorModule', '1', '4', '2019-09-03 15:22:37', '4', '2019-09-03 15:38:59', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570419398721537', '字典管理', '64569813867053057', null, null, null, '2', 'fa fa-dot-circle-o', 'dictionary', '1', '4', '2019-09-03 15:22:48', '4', '2019-09-03 15:37:14', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570459307524097', '系统参数', '64569813867053057', null, null, null, '2', 'fa fa-dot-circle-o', 'parameter', '2', '4', '2019-09-03 15:22:58', '4', '2019-09-03 15:37:24', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570507294556161', '模拟数据', '64569813867053057', null, null, null, '2', 'fa fa-dot-circle-o', '', '3', '4', '2019-09-03 15:23:09', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64570544875520001', '模拟告警', '64570507294556161', null, null, null, '2', 'fa fa-dot-circle-o', 'simulationAlarm', '1', '4', '2019-09-03 15:23:18', '4', '2019-09-03 15:37:44', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570586181025793', '模拟性能', '64570507294556161', null, null, null, '2', 'fa fa-dot-circle-o', '', '2', '4', '2019-09-03 15:23:28', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('64570633916399617', '登录日志', '64569956125261825', null, null, null, '2', 'fa fa-dot-circle-o', 'loginLog', '1', '4', '2019-09-03 15:23:39', '54440149811085312', '2019-09-16 17:47:19', '1');
INSERT INTO `iom_camp_menu` VALUES ('64570667353391105', '操作日志', '64569956125261825', null, null, null, '2', 'fa fa-dot-circle-o', 'activeLog', '2', '4', '2019-09-03 15:23:47', '54440149811085312', '2019-09-16 17:47:40', '1');
INSERT INTO `iom_camp_menu` VALUES ('65270563421175808', '值班模式', '64566728721580033', null, null, null, '1', 'fa fa-dot-circle-o', '/duty', '2', '64862905853689857', '2019-09-05 13:44:56', '71477989640126464', '2019-09-22 19:47:50', '0');
INSERT INTO `iom_camp_menu` VALUES ('65293270439313408', '告警统计', '64567647081218049', null, null, null, '1', 'fa fa-dot-circle-o', 'warnginStatistics', '3', '64862905853689857', '2019-09-05 15:15:09', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('65293851014873089', '告警统计', '55538411657904129', null, null, null, '1', 'fa fa-dot-circle-o', 'warnginStatistics', '3', '64862905853689857', '2019-09-05 15:17:28', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('72839360290111488', '测试', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.04777304679389016', '10', '72819027357089793', '2019-09-26 11:00:37', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('72839427478667265', '子测试', '72839360290111488', null, null, null, '1', 'fa fa-dot-circle-o', '111', '1', '72819027357089793', '2019-09-26 11:00:53', null, null, '0');
INSERT INTO `iom_camp_menu` VALUES ('73623554029142016', '大屏可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.0659517326356387', '8', '73556915476643840', '2019-09-28 14:56:44', '73556915476643840', '2019-09-28 15:03:56', '1');
INSERT INTO `iom_camp_menu` VALUES ('73623657733308416', '大屏可视化', '73623554029142016', null, null, null, '1', 'fa fa-dot-circle-o', '/bigScreen', '1', '73556915476643840', '2019-09-28 14:57:09', '73625006814412801', '2019-11-20 21:13:07', '1');
INSERT INTO `iom_camp_menu` VALUES ('78816917619621888', '3D可视化', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.4255724337624023', '22', '73625006814412801', '2019-10-12 22:53:18', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('78816994799009793', '动环', '78816917619621888', null, null, null, '1', 'fa fa-dot-circle-o', '/dmv3d', '1', '73625006814412801', '2019-10-12 22:53:37', '79112933161975809', '2019-11-12 20:46:25', '1');
INSERT INTO `iom_camp_menu` VALUES ('88846668610551809', '仪表盘广场', '64563643316060161', null, null, null, '1', 'fa fa-dot-circle-o', 'dashboardSquare', '3', '72819027357089793', '2019-11-09 15:07:57', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('95295344035905537', '接口平台', '0', null, null, null, '1', 'fa fa-dot-circle-o', '0.3602787595135708', '1', '72904780934168577', '2019-11-27 10:12:41', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('95295710089592833', '接口配置', '95295344035905537', null, null, null, '1', 'fa fa-dot-circle-o', 'timingtasks', '1', '72904780934168577', '2019-11-27 10:14:08', null, null, '1');
INSERT INTO `iom_camp_menu` VALUES ('104846773020934145', '我的小组', '64562645432418305', null, null, null, '1', 'fa fa-dot-circle-o', 'myGroup', '4', '88201042784829440', '2019-11-12 11:44:55', '88201042784829440', '2019-11-12 11:46:24', '1');

-- ----------------------------
-- Table structure for iom_camp_menu_res
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_menu_res`;
CREATE TABLE `iom_camp_menu_res` (
  `res_dm` bigint(20) NOT NULL,
  `res_mc` varchar(50) DEFAULT NULL,
  `gncd_dm` bigint(20) DEFAULT NULL,
  `res_type` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `cjr_id` bigint(20) DEFAULT NULL,
  `cjsj` timestamp NULL DEFAULT NULL,
  `xgr_id` bigint(20) DEFAULT NULL,
  `xgsj` timestamp NULL DEFAULT NULL,
  `yxbz` int(11) DEFAULT NULL,
  `res_url` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`res_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_menu_res
-- ----------------------------

-- ----------------------------
-- Table structure for iom_camp_org
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_org`;
CREATE TABLE `iom_camp_org` (
  `id` decimal(20,0) NOT NULL COMMENT '主键机构ID',
  `name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `pid` decimal(20,0) DEFAULT NULL COMMENT '上级机构ID',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `service_type` varchar(20) DEFAULT NULL COMMENT '机构类别',
  `is_dept` int(1) DEFAULT NULL COMMENT '部门标志',
  `status` varchar(20) DEFAULT NULL COMMENT '机构状态',
  `sort` int(11) DEFAULT NULL COMMENT '机构排序',
  `cjr_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `cjsj` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `xgr_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `xgsj` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `yxbz` int(11) DEFAULT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_org
-- ----------------------------

-- ----------------------------
-- Table structure for iom_camp_parameter
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_parameter`;
CREATE TABLE `iom_camp_parameter` (
  `PARA_ID` bigint(20) NOT NULL COMMENT '主键',
  `PARA_MC` varchar(50) NOT NULL COMMENT '参数名称',
  `PARA_TYPE` int(4) DEFAULT NULL COMMENT '参数类型',
  `PARA_DATA` varchar(50) DEFAULT NULL COMMENT '参数值',
  `PARA_EXPRE` varchar(50) DEFAULT NULL COMMENT '参数表达式',
  `IS_OPEN` int(1) DEFAULT NULL COMMENT '公开标志',
  `SORT` int(4) NOT NULL COMMENT '排序列',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`PARA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_parameter
-- ----------------------------

-- ----------------------------
-- Table structure for iom_camp_role
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_role`;
CREATE TABLE `iom_camp_role` (
  `role_dm` varchar(50) NOT NULL,
  `role_mc` varchar(45) DEFAULT NULL,
  `role_type` int(11) DEFAULT NULL,
  `role_desc` varchar(45) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `cjr_id` decimal(20,0) DEFAULT NULL,
  `cjsj` datetime DEFAULT NULL,
  `xgr_id` decimal(20,0) DEFAULT NULL,
  `xgsj` datetime DEFAULT NULL,
  `yxbz` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_role
-- ----------------------------
INSERT INTO `iom_camp_role` VALUES ('104804839359397889', '普通管理员', '1', '普通管理员', '2', '72819027357089793', '2019-12-23 16:00:02', null, null, '1');
INSERT INTO `iom_camp_role` VALUES ('79496622567997440', '超级管理员', '1', '超级管理员角色', '1', '72819027357089793', '2019-10-14 19:54:12', null, null, '1');

-- ----------------------------
-- Table structure for iom_camp_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `iom_camp_role_menu`;
CREATE TABLE `iom_camp_role_menu` (
  `id` decimal(20,0) NOT NULL COMMENT '主键',
  `role_dm` varchar(50) DEFAULT NULL COMMENT '角色代码',
  `gncd_dm` bigint(30) DEFAULT NULL COMMENT '功能菜单代码',
  `gnfl_type` int(1) DEFAULT NULL COMMENT '功能分类代码1:菜单;2:资源;',
  `cjr_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `cjsj` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `xgr_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `xgsj` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_camp_role_menu
-- ----------------------------
INSERT INTO `iom_camp_role_menu` VALUES ('95295979036753920', '79496622567997440', '56576968518418432', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979040948224', '79496622567997440', '56575448850776064', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979045142528', '79496622567997440', '59799274975805440', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979049336832', '79496622567997440', '64562694157647873', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979053531136', '79496622567997440', '64562645432418305', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979057725440', '79496622567997440', '64562731629559809', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979061919744', '79496622567997440', '64562779293630465', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979066114048', '79496622567997440', '64562899691126785', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979066114049', '79496622567997440', '54439490827206656', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979070308352', '79496622567997440', '54439190829613057', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979074502656', '79496622567997440', '64563692427165697', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979078696960', '79496622567997440', '64563643316060161', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979082891264', '79496622567997440', '64563727629959169', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979087085568', '79496622567997440', '64563970635350017', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979091279872', '79496622567997440', '64563862032236545', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979091279873', '79496622567997440', '59082533056430081', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979095474176', '79496622567997440', '59082417847287808', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979099668480', '79496622567997440', '60613038398652416', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979103862784', '79496622567997440', '64566784459685889', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979108057088', '79496622567997440', '64566728721580033', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979112251392', '79496622567997440', '59082748119367681', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979112251393', '79496622567997440', '59082673976655873', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979116445696', '79496622567997440', '59082797331136513', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979120640000', '79496622567997440', '64566948373086209', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979124834304', '79496622567997440', '64567083844911105', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979129028608', '79496622567997440', '64567401504718849', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979133222912', '79496622567997440', '64567335977107457', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979137417216', '79496622567997440', '64567449797935105', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979137417217', '79496622567997440', '64567647081218049', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979141611520', '79496622567997440', '55538411657904129', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979145805824', '79496622567997440', '64567689980559361', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979150000128', '79496622567997440', '65293851014873089', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979154194432', '79496622567997440', '64567733479686145', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979154194433', '79496622567997440', '64567801792315393', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979158388736', '79496622567997440', '64568246367567873', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979162583040', '79496622567997440', '64568319885328385', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979166777344', '79496622567997440', '64211127487315968', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979170971648', '79496622567997440', '64211496997109761', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979175165952', '79496622567997440', '64211251848429569', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979179360256', '79496622567997440', '61650568413855745', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979179360257', '79496622567997440', '64568482460745729', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979183554560', '79496622567997440', '64568526916173825', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979187748864', '79496622567997440', '64568575666569217', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979191943168', '79496622567997440', '61650709405384705', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979196137472', '79496622567997440', '64568806344900609', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979200331776', '79496622567997440', '61985553679269888', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979200331777', '79496622567997440', '61985775738306561', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979204526080', '79496622567997440', '64208321913176065', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979208720384', '79496622567997440', '64227778471542785', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979212914688', '79496622567997440', '64569085089955841', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979217108992', '79496622567997440', '64569041746018305', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979221303296', '79496622567997440', '64570056490762241', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979221303297', '79496622567997440', '64569669314560001', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979225497600', '79496622567997440', '64569584979689473', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979229691904', '79496622567997440', '64570098731597825', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979233886208', '79496622567997440', '64570144231407616', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979238080512', '79496622567997440', '64570193191518209', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979242274816', '79496622567997440', '64570258329059329', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979242274817', '79496622567997440', '64569710188052481', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979246469120', '79496622567997440', '64570295427678209', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979250663424', '79496622567997440', '64570326067068928', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979254857728', '79496622567997440', '64570372070195201', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979259052032', '79496622567997440', '64569753439715328', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979263246336', '79496622567997440', '64570419398721537', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979263246337', '79496622567997440', '64569813867053057', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979267440640', '79496622567997440', '64570459307524097', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979271634944', '79496622567997440', '64570544875520001', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979275829248', '79496622567997440', '64570507294556161', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979280023552', '79496622567997440', '64570586181025793', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979284217856', '79496622567997440', '64570633916399617', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979284217857', '79496622567997440', '64569956125261825', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979309383680', '79496622567997440', '64570667353391105', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979313577984', '79496622567997440', '78816994799009793', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979317772288', '79496622567997440', '78816917619621888', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979321966592', '79496622567997440', '73623657733308416', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979326160896', '79496622567997440', '73623554029142016', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979330355200', '79496622567997440', '88846668610551809', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979330355201', '79496622567997440', '54439372728188929', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979334549504', '79496622567997440', '95295344035905537', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979338743808', '79496622567997440', '95295710089592833', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979342938112', '79496622567997440', '54439099108573184', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979347132416', '79496622567997440', '55538051727900672', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979351326720', '79496622567997440', '62797912773640193', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('95295979351326721', '79496622567997440', '61650495902728193', '1', '72904780934168577', '2019-11-27 10:15:13', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104847334696960000', '79496622567997440', '104846773020934145', '1', '72819027357089793', '2019-12-23 18:48:54', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775587184640', '104804839359397889', '56576968518418432', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775591378944', '104804839359397889', '64562731629559809', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775595573248', '104804839359397889', '64562645432418305', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775599767552', '104804839359397889', '54439490827206656', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775603961856', '104804839359397889', '54439190829613057', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775603961857', '104804839359397889', '54439372728188929', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775608156160', '104804839359397889', '64563727629959169', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775612350464', '104804839359397889', '64563643316060161', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775612350465', '104804839359397889', '64563692427165697', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775616544768', '104804839359397889', '88846668610551809', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775620739072', '104804839359397889', '64563970635350017', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775620739073', '104804839359397889', '64563862032236545', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775624933376', '104804839359397889', '59082533056430081', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775629127680', '104804839359397889', '59082417847287808', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775629127681', '104804839359397889', '60613038398652416', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775633321984', '104804839359397889', '64566784459685889', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775637516288', '104804839359397889', '64566728721580033', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775637516289', '104804839359397889', '59082748119367681', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775641710592', '104804839359397889', '59082673976655873', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775645904896', '104804839359397889', '59082797331136513', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775645904897', '104804839359397889', '64566948373086209', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775650099200', '104804839359397889', '64567083844911105', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775654293504', '104804839359397889', '64567401504718849', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775654293505', '104804839359397889', '64567335977107457', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775658487808', '104804839359397889', '64567449797935105', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775662682112', '104804839359397889', '64567647081218049', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775662682113', '104804839359397889', '55538411657904129', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775666876416', '104804839359397889', '64567689980559361', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775671070720', '104804839359397889', '65293851014873089', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775671070721', '104804839359397889', '64567801792315393', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775675265024', '104804839359397889', '64568246367567873', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775679459328', '104804839359397889', '64568319885328385', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775679459329', '104804839359397889', '64211127487315968', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775683653632', '104804839359397889', '64211496997109761', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775687847936', '104804839359397889', '64211251848429569', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775692042240', '104804839359397889', '61650568413855745', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775696236544', '104804839359397889', '64568526916173825', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775696236545', '104804839359397889', '64568575666569217', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775700430848', '104804839359397889', '61650709405384705', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775704625152', '104804839359397889', '64568806344900609', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775708819456', '104804839359397889', '61985553679269888', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775708819457', '104804839359397889', '61985775738306561', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775713013760', '104804839359397889', '64208321913176065', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775717208064', '104804839359397889', '64227778471542785', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775717208065', '104804839359397889', '64567733479686145', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775721402368', '104804839359397889', '64569085089955841', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775725596672', '104804839359397889', '64569041746018305', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775725596673', '104804839359397889', '64570056490762241', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775729790976', '104804839359397889', '64569669314560001', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775733985280', '104804839359397889', '64569584979689473', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775733985281', '104804839359397889', '64570098731597825', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775738179584', '104804839359397889', '64570144231407616', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775742373888', '104804839359397889', '64570193191518209', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775742373889', '104804839359397889', '64570258329059329', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775746568192', '104804839359397889', '64569710188052481', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775750762496', '104804839359397889', '64570295427678209', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775750762497', '104804839359397889', '64570326067068928', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775754956800', '104804839359397889', '64570372070195201', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775759151104', '104804839359397889', '64569753439715328', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775759151105', '104804839359397889', '64570419398721537', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775763345408', '104804839359397889', '64569813867053057', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775767539712', '104804839359397889', '64570459307524097', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775767539713', '104804839359397889', '64570544875520001', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775771734016', '104804839359397889', '64570507294556161', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775775928320', '104804839359397889', '64570586181025793', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775780122624', '104804839359397889', '64570633916399617', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775784316928', '104804839359397889', '64569956125261825', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775784316929', '104804839359397889', '64570667353391105', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775788511232', '104804839359397889', '73623657733308416', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775792705536', '104804839359397889', '73623554029142016', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775796899840', '104804839359397889', '104846773020934145', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775796899841', '104804839359397889', '64562694157647873', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775801094144', '104804839359397889', '64562779293630465', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775805288448', '104804839359397889', '64562899691126785', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775809482752', '104804839359397889', '59799274975805440', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775813677056', '104804839359397889', '56575448850776064', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775813677057', '104804839359397889', '54439099108573184', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775817871360', '104804839359397889', '55538051727900672', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775822065664', '104804839359397889', '62797912773640193', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);
INSERT INTO `iom_camp_role_menu` VALUES ('104848775826259968', '104804839359397889', '61650495902728193', '1', '72819027357089793', '2019-12-23 18:54:37', null, null);

-- ----------------------------
-- Table structure for subsystem
-- ----------------------------
DROP TABLE IF EXISTS `subsystem`;
CREATE TABLE `subsystem` (
  `ID` bigint(20) unsigned NOT NULL COMMENT '主键',
  `NAME` varchar(50) DEFAULT NULL COMMENT '名称',
  `DESCRIBES` varchar(50) DEFAULT NULL COMMENT '描述',
  `SERVERNAME` varchar(255) DEFAULT NULL,
  `ISORTHER` varchar(11) DEFAULT NULL COMMENT '0 其他 1 固定系统',
  `COMMONPAGEURL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subsystem
-- ----------------------------
SET GLOBAL log_bin_trust_function_creators = 1;
-- ----------------------------
-- Function structure for getChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getChildList`(`rootId` varchar(1000)) RETURNS varchar(4000) CHARSET utf8
begin
  declare sTemp varchar(4000);
  declare sTempChd varchar(1000);
  set sTemp = '$';
  set sTempChd =rootId;
  while sTempChd is not null do
  set sTemp = concat(sTemp,',',sTempChd);
  select group_concat(GNCD_DM) into sTempChd from iom_camp_menu where find_in_set(SJ_GNCD_DM,sTempChd)>0  and yxbz = 1 ;
  end while;
  return sTemp;
 end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getOrgChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getOrgChildList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getOrgChildList`(`rootId` varchar(1000)) RETURNS varchar(4000) CHARSET utf8
    DETERMINISTIC
begin
  declare sTemp varchar(4000);
  declare sTempChd varchar(1000);
  set sTemp = '$';
  set sTempChd =rootId;
  while sTempChd is not null do
  set sTemp = concat(sTemp,',',sTempChd);
  select group_concat(id) into sTempChd from iom_camp_org where find_in_set(pid,sTempChd)>0  and yxbz =1;
  end while;
  return sTemp;
 end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getOrgParentList
-- ----------------------------
DROP FUNCTION IF EXISTS `getOrgParentList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getOrgParentList`(`rootId` varchar (100)) RETURNS varchar(100) CHARSET utf8
    DETERMINISTIC
begin
  declare resultStr varchar (1000) ;
  declare tempStr varchar (100) ;
  set resultStr = '$' ;
  set tempStr = cast(rootId  as char);
  while
    tempStr is not null do set resultStr = concat(resultStr,',',tempStr);
    select
      group_concat(pid) into tempStr
    from
      iom_camp_org
    where pid <> id
      and find_in_set(id, tempStr) > 0
      and yxbz =1;
  end while ;
  return resultStr ;
end
;;
DELIMITER ;
