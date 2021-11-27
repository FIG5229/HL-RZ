/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : iomci

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-01-08 17:26:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for iom_ci_data_rel
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_data_rel`;
CREATE TABLE `iom_ci_data_rel` (
  `ID` decimal(20,0) NOT NULL COMMENT 'ID',
  `REL_ID` varchar(100) NOT NULL COMMENT '关系ID',
  `REL_NAME` varchar(200) NOT NULL COMMENT '关系名称',
  `REL_DESC` char(10) DEFAULT NULL COMMENT '关系描述',
  `SOURCE_ID` int(4) DEFAULT NULL COMMENT '来源',
  `OWENR_ID` int(4) DEFAULT NULL COMMENT '所有者',
  `ORG_ID` int(4) DEFAULT NULL COMMENT '所属组织',
  `SOURCE_CI_ID` decimal(22,0) NOT NULL COMMENT '源CIID',
  `SOURCE_CI_BM` varchar(100) NOT NULL COMMENT '源CI编码',
  `SOURCE_TYPE_BM` varchar(200) NOT NULL COMMENT '源CI类别',
  `SOURCE_TYPE_ID` decimal(20,0) DEFAULT NULL COMMENT '源CI类别ID',
  `TARGET_CI_ID` decimal(22,0) NOT NULL COMMENT '目标CIID',
  `TARGET_CI_BM` varchar(100) NOT NULL COMMENT '目标CI编码',
  `TARGET_TYPE_BM` varchar(200) NOT NULL COMMENT '目标CI类别',
  `TARGET_TYPE_ID` decimal(20,0) DEFAULT NULL COMMENT '目标CI类别ID',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_data_rel
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_dir
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_dir`;
CREATE TABLE `iom_ci_dir` (
  `ID` decimal(20,0) NOT NULL COMMENT 'ID',
  `DIR_NAME` varchar(100) NOT NULL COMMENT '目录名称',
  `DIR_TYPE` int(4) NOT NULL COMMENT '目录类型1:CI类别;2:CI关系;3:图标;4:视图',
  `PARENT_DIR_ID` decimal(22,0) NOT NULL COMMENT '上级目录ID',
  `DIR_LVL` int(4) DEFAULT NULL COMMENT '目录层级',
  `DIR_PATH` varchar(50) DEFAULT NULL COMMENT '目录路径',
  `SORT` int(4) NOT NULL COMMENT '排序列',
  `IS_LEAF` int(4) DEFAULT NULL COMMENT '是否末级',
  `DIR_ICON` varchar(100) DEFAULT NULL COMMENT '目录图标',
  `DIR_COLOR` varchar(20) DEFAULT NULL COMMENT '目录颜色',
  `DIR_DESC` varchar(200) DEFAULT NULL COMMENT '目录描述',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_dir
-- ----------------------------
INSERT INTO `iom_ci_dir` VALUES ('65768484637917184', 'DCV', '1', '0', '0', null, '0', '0', '', '#409EFF', '', '65674684586409985', '2019-09-06 22:43:29', '65732078498627585', '2019-09-24 10:03:51', '1');
INSERT INTO `iom_ci_dir` VALUES ('73181313049444352', '默认图标', '6', '0', '0', null, '1', '0', null, null, null, '72819027357089793', '2019-09-27 09:39:25', null, null, '1');

-- ----------------------------
-- Table structure for iom_ci_icon
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_icon`;
CREATE TABLE `iom_ci_icon` (
  `ID` decimal(20,0) NOT NULL COMMENT 'ID',
  `ICON_NAME` varchar(100) NOT NULL COMMENT '图标名称',
  `ICON_DIR` decimal(22,0) DEFAULT NULL COMMENT '图标目录',
  `ICON_TYPE` int(4) NOT NULL COMMENT '图标类型1=CI分类图标    2=视图背景图',
  `ICON_DESC` varchar(200) DEFAULT NULL COMMENT '图标描述',
  `ICON_FORM` varchar(10) DEFAULT NULL COMMENT '图标格式',
  `ICON_COLOR` varchar(20) DEFAULT NULL COMMENT '图标颜色',
  `ICON_PATH` varchar(100) DEFAULT NULL COMMENT '图标路径',
  `ICON_SIZE` int(4) DEFAULT NULL COMMENT '图标大小',
  `ICON_RANGE` int(4) DEFAULT NULL COMMENT '权限范围',
  `SORT` int(4) DEFAULT NULL COMMENT '排序列',
  `SCR_ID` bigint(20) DEFAULT NULL COMMENT '上传人',
  `SCSJ` datetime DEFAULT NULL COMMENT '上传时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) DEFAULT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_icon
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_info
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_info`;
CREATE TABLE `iom_ci_info` (
  `ID` decimal(20,0) NOT NULL COMMENT 'ID',
  `CI_BM` varchar(100) NOT NULL COMMENT 'CI编码',
  `CI_DESC` varchar(200) DEFAULT NULL COMMENT 'CI描述',
  `CI_TYPE_ID` decimal(22,0) NOT NULL COMMENT '所属类别',
  `SOURCE_ID` int(4) NOT NULL COMMENT '来源',
  `OWNER_ID` int(4) DEFAULT NULL COMMENT '所有者',
  `ORG_ID` int(4) DEFAULT NULL COMMENT '所属组织',
  `CI_VERSION` varchar(50) DEFAULT NULL COMMENT 'CI版本',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_info
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_kpi
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_kpi`;
CREATE TABLE `iom_ci_kpi` (
  `ID` decimal(20,0) NOT NULL COMMENT '主键',
  `KPI_NAME` varchar(64) NOT NULL COMMENT 'KPI名称',
  `KPI_ALIAS` varchar(128) DEFAULT NULL COMMENT 'KPI别名',
  `KPI_DESC` varchar(512) DEFAULT NULL COMMENT 'KPI描述',
  `TYPE_ID` decimal(20,0) DEFAULT NULL COMMENT '所属分类（CI大类）',
  `KPI_CLASS_ID` decimal(20,0) DEFAULT NULL COMMENT '指标分类',
  `IS_MATCH` int(1) DEFAULT NULL COMMENT '是否匹配 0未匹配 1匹配',
  `VAL_UNIT` varchar(12) DEFAULT NULL COMMENT '数值单位',
  `VAL_TYPE` int(4) DEFAULT NULL COMMENT '数值类型1:数值;2:字符',
  `VAL_EXP` text COMMENT '数据阀值',
  `DW_ID` int(4) DEFAULT NULL COMMENT '单位ID',
  `DW_NAME` varchar(64) DEFAULT NULL COMMENT '单位名称',
  `SOURCE_ID` decimal(20,0) DEFAULT NULL COMMENT '来源',
  `OP_ID` int(4) DEFAULT NULL COMMENT '所有者',
  `IDX_FIELD` varchar(512) DEFAULT NULL COMMENT '搜索字段',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标模型表';

-- ----------------------------
-- Records of iom_ci_kpi
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_kpi_class
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_kpi_class`;
CREATE TABLE `iom_ci_kpi_class` (
  `ID` decimal(20,0) NOT NULL COMMENT '主键',
  `NAME` varchar(128) NOT NULL COMMENT '指标类名称',
  `CJR_ID` decimal(20,0) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `XGR_ID` decimal(20,0) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标大类表';

-- ----------------------------
-- Records of iom_ci_kpi_class
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_kpi_thres
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_kpi_thres`;
CREATE TABLE `iom_ci_kpi_thres` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `KPI_ID` bigint(22) NOT NULL COMMENT 'KPI主键',
  `KPI_THRES` varchar(32) NOT NULL COMMENT 'KPI阀值',
  `KPI_ICON` varchar(128) DEFAULT NULL COMMENT 'KPI图标',
  `KPI_COLOR` varchar(32) DEFAULT NULL COMMENT 'KPI颜色',
  `CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_kpi_thres
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_kpi_tpl
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_kpi_tpl`;
CREATE TABLE `iom_ci_kpi_tpl` (
  `ID` decimal(22,0) NOT NULL COMMENT '主键',
  `TPL_NAME` varchar(64) NOT NULL COMMENT '模板名称',
  `TPL_ALIAS` varchar(128) DEFAULT NULL COMMENT '模板别名',
  `TPL_DESC` varchar(512) DEFAULT NULL COMMENT '模板描述',
  `IDX_FIELD` varchar(512) DEFAULT NULL COMMENT '搜索字段',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_kpi_tpl
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_kpi_tpl_item
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_kpi_tpl_item`;
CREATE TABLE `iom_ci_kpi_tpl_item` (
  `ID` decimal(22,0) NOT NULL COMMENT '主键',
  `TPL_ID` decimal(22,0) NOT NULL COMMENT '模板ID',
  `OBJ_TYPE` int(4) DEFAULT NULL COMMENT '关联对象类型1:指标;2:CI类别;3:标签',
  `OBJ_ID` decimal(22,0) DEFAULT NULL COMMENT '关联对象ID',
  `OBJ_NAME` varchar(64) DEFAULT NULL COMMENT '关联对象名称',
  `KPI_USE_TYPE` int(4) DEFAULT NULL COMMENT '指标应用类型1:应该;2:可能',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_kpi_tpl_item
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_kpi_type
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_kpi_type`;
CREATE TABLE `iom_ci_kpi_type` (
  `ID` decimal(22,0) NOT NULL COMMENT '主键',
  `KPI_ID` decimal(22,0) NOT NULL COMMENT 'KPI主键',
  `OBJ_TYPE` int(4) DEFAULT NULL COMMENT '对象类型1:标签;2:CI类别',
  `OBJ_ID` decimal(22,0) DEFAULT NULL COMMENT '对象ID',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_kpi_type
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_mgt_log
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_mgt_log`;
CREATE TABLE `iom_ci_mgt_log` (
  `ID` decimal(20,0) NOT NULL COMMENT 'ID',
  `CI_ID` decimal(20,0) NOT NULL COMMENT '所属CI',
  `TYPE_ID` decimal(20,0) NOT NULL COMMENT '所属类别',
  `BGR_ID` decimal(20,0) NOT NULL COMMENT '变更人',
  `BGSJ` datetime NOT NULL COMMENT '变更时间',
  `MGT_TYPE` int(1) NOT NULL COMMENT '操作类型１创建２修改３删除',
  `UP_ITEM` text COMMENT '变更内容[{id,name,befor,after}]',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='CI修改日志';

-- ----------------------------
-- Records of iom_ci_mgt_log
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_rel
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_rel`;
CREATE TABLE `iom_ci_rel` (
  `ID` decimal(22,0) NOT NULL COMMENT 'ID',
  `LINE_BM` varchar(100) NOT NULL COMMENT '关系代码',
  `LINE_NAME` varchar(100) NOT NULL COMMENT '关系名称',
  `LINE_STD_BM` varchar(100) DEFAULT NULL COMMENT '关系代码标准值',
  `LINE_DESC` varchar(200) DEFAULT NULL COMMENT '关系描述',
  `PARENT_LINE_ID` decimal(22,0) DEFAULT NULL COMMENT '上级关系ID',
  `LINE_LVL` int(4) DEFAULT NULL COMMENT '关系层级',
  `LINE_PATH` varchar(100) DEFAULT NULL COMMENT '关系路径',
  `SORT` int(4) NOT NULL COMMENT '排序列',
  `LINE_COST` int(4) DEFAULT NULL COMMENT '是否归集0＝不归集1＝归集（源目标）2＝归集（目标源）',
  `LINE_STYLE` int(4) DEFAULT NULL COMMENT '关系样式',
  `LINE_WIDTH` int(4) DEFAULT NULL COMMENT '关系宽度',
  `LINE_COLOR` varchar(50) DEFAULT NULL COMMENT '关系颜色',
  `LINE_ARROR` int(4) DEFAULT NULL COMMENT '关系箭头',
  `LINE_ANIME` int(4) DEFAULT NULL COMMENT '关系动态效果',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_rel
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_rlt_line
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_rlt_line`;
CREATE TABLE `iom_ci_rlt_line` (
  `ID` decimal(20,0) NOT NULL COMMENT '主键',
  `RULE_ID` decimal(20,0) NOT NULL COMMENT '规则ID',
  `RLT_TYPE` int(11) DEFAULT NULL COMMENT '规则类型',
  `TYPE_ID` decimal(20,0) DEFAULT NULL COMMENT '触发类别',
  `START_TYPE_ID` decimal(20,0) NOT NULL COMMENT '起点类别',
  `END_TYPE_ID` decimal(20,0) NOT NULL COMMENT '终点类别',
  `RLT_ID` decimal(20,0) NOT NULL COMMENT '关系类型',
  `START_NODE_ID` decimal(20,0) NOT NULL COMMENT '起始节点',
  `END_NODE_ID` decimal(20,0) NOT NULL COMMENT '终止节点',
  `LINE_TYPE` int(11) DEFAULT NULL COMMENT '连线类型1:规则关系;2:empty',
  `LINE_OP` int(11) DEFAULT NULL COMMENT '连线运算符',
  `LINE_VAL` varchar(128) DEFAULT NULL COMMENT '连线条件',
  `OP_TYPE` int(11) DEFAULT NULL COMMENT '条件类型1:起点;2:终点',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='关系遍历连线表';

-- ----------------------------
-- Records of iom_ci_rlt_line
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_rlt_node
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_rlt_node`;
CREATE TABLE `iom_ci_rlt_node` (
  `ID` decimal(20,0) NOT NULL COMMENT '主键',
  `RULE_ID` decimal(20,0) NOT NULL COMMENT '规则ID',
  `RLT_TYPE` int(11) DEFAULT NULL COMMENT '规则类型',
  `NODE_TYPE` int(11) NOT NULL COMMENT '节点类型1:CI节点;2:运算符节点',
  `NODE_LOGIC` int(11) DEFAULT NULL COMMENT '节点逻辑1:and;2:or',
  `TYPE_ID` decimal(20,0) DEFAULT NULL COMMENT '触发类别',
  `NODE_TYPE_ID` decimal(20,0) DEFAULT NULL COMMENT '节点类别',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='关系遍历节点表';

-- ----------------------------
-- Records of iom_ci_rlt_node
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_rlt_node_cdt
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_rlt_node_cdt`;
CREATE TABLE `iom_ci_rlt_node_cdt` (
  `ID` decimal(20,0) NOT NULL COMMENT '主键',
  `NODE_ID` decimal(20,0) NOT NULL COMMENT '节点ID',
  `ATTR_ID` decimal(20,0) NOT NULL COMMENT '分类属性ID',
  `CDT_OP` int(11) DEFAULT NULL COMMENT '运算符',
  `CDT_VAL` varchar(128) DEFAULT NULL COMMENT '条件值',
  `IS_NOT` int(11) DEFAULT NULL COMMENT '非运算',
  `STOR` int(11) NOT NULL COMMENT '排列顺序',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGSJ` datetime NOT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='关系遍历节点条件表';

-- ----------------------------
-- Records of iom_ci_rlt_node_cdt
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_rlt_rule
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_rlt_rule`;
CREATE TABLE `iom_ci_rlt_rule` (
  `ID` decimal(20,0) NOT NULL COMMENT '主键',
  `RLT_TYPE` int(11) NOT NULL COMMENT '规则类型1:关系遍历',
  `RLT_NAME` varchar(128) NOT NULL COMMENT '规则名称',
  `TYPE_ID` decimal(20,0) NOT NULL COMMENT '触发类别',
  `RLT_DESC` varchar(512) DEFAULT NULL COMMENT '定义描述',
  `DIAG_XML` text COMMENT '视图布局',
  `STATUS` int(11) DEFAULT NULL COMMENT '有效状态0:无效;1:有效',
  `VALID_DESC` varchar(512) DEFAULT NULL COMMENT '无效说明',
  `CJR_ID` decimal(20,0) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` decimal(20,0) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='关系遍历表';

-- ----------------------------
-- Records of iom_ci_rlt_rule
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_type
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type`;
CREATE TABLE `iom_ci_type` (
  `ID` decimal(22,0) NOT NULL COMMENT 'ID',
  `CI_TYPE_BM` varchar(200) NOT NULL COMMENT '类别代码',
  `CI_TYPE_MC` varchar(200) NOT NULL COMMENT '类别名称',
  `CI_TYPE_STD_BM` varchar(200) DEFAULT NULL COMMENT '类别代码标准值',
  `CI_TYPE_DIR` decimal(22,0) NOT NULL COMMENT '所属目录',
  `PARENT_CI_TYPE_ID` decimal(22,0) DEFAULT NULL COMMENT '上级类别代码',
  `CI_TYPE_LV` int(4) DEFAULT NULL COMMENT '类别层级',
  `CI_TYPE_PATH` varchar(20) DEFAULT NULL COMMENT '类别层级路径',
  `LEAF` char(1) DEFAULT NULL COMMENT '是否末级',
  `CI_TYPE_ICON` varchar(100) DEFAULT NULL COMMENT '类别图标',
  `CI_TYPE_DESC` varchar(200) DEFAULT NULL COMMENT '类别描述',
  `CI_TYPE_COLOR` varchar(50) DEFAULT NULL COMMENT '类别颜色',
  `SORT` int(4) NOT NULL COMMENT '排序列',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_type
-- ----------------------------
INSERT INTO `iom_ci_type` VALUES ('55201652034125824', '数据中心', '数据中心', null, '88888888888888888', null, '0', null, null, '', '', '#409EFF', '1', '3', '2019-08-08 18:54:40', null, null, '1');

-- ----------------------------
-- Table structure for iom_ci_type_data
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type_data`;
CREATE TABLE `iom_ci_type_data` (
  `ID` decimal(22,0) NOT NULL COMMENT 'ID同信息表ID',
  `CI_TYPE_ID` decimal(22,0) NOT NULL COMMENT '所属类别',
  `DATA_1` text NOT NULL COMMENT '数据列1',
  `DATA_2` text COMMENT '数据列2',
  `DATA_3` text COMMENT '数据列3',
  `DATA_4` text,
  `DATA_5` text,
  `DATA_6` text,
  `DATA_7` text,
  `DATA_8` text,
  `DATA_9` text,
  `DATA_10` text,
  `DATA_11` text,
  `DATA_12` text,
  `DATA_13` text,
  `DATA_14` text,
  `DATA_15` text,
  `DATA_16` text,
  `DATA_17` text,
  `DATA_18` text,
  `DATA_19` text,
  `DATA_20` text,
  `DATA_21` text,
  `DATA_22` text,
  `DATA_23` text,
  `DATA_24` text,
  `DATA_25` text,
  `DATA_26` text,
  `DATA_27` text,
  `DATA_28` text,
  `DATA_29` text,
  `DATA_30` text,
  `DATA_31` text,
  `DATA_32` text,
  `DATA_33` text,
  `DATA_34` text,
  `DATA_35` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_type_data
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_type_data_index
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type_data_index`;
CREATE TABLE `iom_ci_type_data_index` (
  `ID` decimal(22,0) NOT NULL COMMENT '主键',
  `CI_ID` decimal(22,0) NOT NULL COMMENT 'CI_ID',
  `ATTR_ID` decimal(22,0) NOT NULL COMMENT '属性ID',
  `TYPE_ID` decimal(22,0) NOT NULL COMMENT 'CI类别',
  `IDX` text COMMENT '索引列',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `data_index_ci_id` (`CI_ID`),
  KEY `data_index_type_id` (`TYPE_ID`),
  KEY `data_index_attr_id` (`ATTR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_type_data_index
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_type_focus_rel
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type_focus_rel`;
CREATE TABLE `iom_ci_type_focus_rel` (
  `ID` decimal(20,0) NOT NULL COMMENT 'ID',
  `TYPE_ID` decimal(20,0) NOT NULL COMMENT '影响类别ID',
  `SOURCE_TYPE_ID` decimal(20,0) NOT NULL COMMENT '源类别ID',
  `TARGET_TYPE_ID` decimal(20,0) NOT NULL COMMENT '目标类别ID',
  `RLT_ID` decimal(20,0) NOT NULL COMMENT '关系类别ID',
  `CJR_ID` decimal(20,0) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` decimal(20,0) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime NOT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='影响表';

-- ----------------------------
-- Records of iom_ci_type_focus_rel
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_type_item
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type_item`;
CREATE TABLE `iom_ci_type_item` (
  `ID` decimal(22,0) NOT NULL COMMENT 'ID',
  `CI_TYPE_ID` decimal(22,0) NOT NULL COMMENT '所属类别',
  `ATTR_NAME` varchar(100) NOT NULL COMMENT '属性名',
  `ATTR_STD_NAME` varchar(100) DEFAULT NULL COMMENT '标准名',
  `ATTR_TYPE` varchar(100) NOT NULL COMMENT '属性类型',
  `ATTR_DESC` varchar(200) DEFAULT NULL COMMENT '属性描述',
  `MP_CI_ITEM` varchar(50) NOT NULL COMMENT '映射字段',
  `IS_MAJOR` int(4) NOT NULL COMMENT '是否主键',
  `IS_REQU` int(4) NOT NULL COMMENT '是否必填',
  `DEF_VAL` varchar(100) DEFAULT NULL COMMENT '缺省值',
  `ENUM_VALS` varchar(100) DEFAULT NULL COMMENT '枚举值(分隔)',
  `SORT` int(4) NOT NULL COMMENT '排序列',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CONS134218794` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iom_ci_type_item
-- ----------------------------
INSERT INTO `iom_ci_type_item` VALUES ('55201793772240896', '55201652034125824', '编号', '编号', '字符串', '', 'DATA_1', '1', '1', '', '', '1', '3', '2019-08-08 18:55:14', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('55201876261617664', '55201652034125824', '所属', '所属', '字符串', '', 'DATA_2', '0', '1', '', '', '1', '3', '2019-08-08 18:55:34', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('55202006989684736', '55201652034125824', 'DESC', 'DESC', '字符串', '', 'DATA_3', '0', '0', '', '', '1', '3', '2019-08-08 18:56:05', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('55202279430701056', '55201652034125824', 'PICURL', 'PICURL', '字符串', '', 'DATA_4', '0', '0', '', '', '1', '3', '2019-08-08 18:57:10', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('55202419163938816', '55201652034125824', 'Lon', 'Lon', '字符串', '', 'DATA_5', '0', '0', '', '', '1', '3', '2019-08-08 18:57:43', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('55202481906532352', '55201652034125824', 'Lat', 'Lat', '字符串', '', 'DATA_6', '0', '0', '', '', '1', '3', '2019-08-08 18:57:58', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048062521982976', '55201652034125824', 'name', null, '字符串', null, 'DATA_7', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048062601674752', '55201652034125824', 'importType', null, '字符串', null, 'DATA_8', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048062719115264', '55201652034125824', 'description', null, '字符串', null, 'DATA_9', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048063008522240', '55201652034125824', 'scId', null, '字符串', null, 'DATA_10', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048063130157056', '55201652034125824', 'image', null, '字符串', null, 'DATA_11', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048063222431744', '55201652034125824', 'longitude', null, '字符串', null, 'DATA_12', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');
INSERT INTO `iom_ci_type_item` VALUES ('87048063390203904', '55201652034125824', 'latitude', null, '字符串', null, 'DATA_13', '0', '0', null, null, '0', '3', '2019-11-04 16:00:56', null, null, '1');

-- ----------------------------
-- Table structure for iom_ci_type_rel
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type_rel`;
CREATE TABLE `iom_ci_type_rel` (
  `ID` decimal(22,0) NOT NULL COMMENT 'ID',
  `REL_ID` decimal(22,0) NOT NULL COMMENT '关系ID',
  `SOURCE_TYPE_ID` decimal(22,0) NOT NULL COMMENT '源CI类别ID',
  `TARGET_TYPE_ID` decimal(22,0) NOT NULL COMMENT '目标CI类别ID',
  `CJR_ID` bigint(20) NOT NULL COMMENT '创建人',
  `DJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` bigint(20) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关系表';

-- ----------------------------
-- Records of iom_ci_type_rel
-- ----------------------------

-- ----------------------------
-- Table structure for iom_ci_type_rel_diagram
-- ----------------------------
DROP TABLE IF EXISTS `iom_ci_type_rel_diagram`;
CREATE TABLE `iom_ci_type_rel_diagram` (
  `ID` decimal(22,0) NOT NULL COMMENT '主键',
  `DIAG_NAME` varchar(128) DEFAULT NULL COMMENT '视图名称',
  `DIAG_XML` text COMMENT '视图XML',
  `ICONO_URL` varchar(128) DEFAULT NULL COMMENT '视图图标１',
  `USER_ID` decimal(22,0) DEFAULT NULL COMMENT '所属用户',
  `DIAG_TYPE` int(11) DEFAULT NULL COMMENT '视图类别(1:可视化建模;2:朋友圈)',
  `CJR_ID` decimal(22,0) DEFAULT NULL COMMENT '创建人',
  `CJSJ` datetime DEFAULT NULL COMMENT '创建时间',
  `XGR_ID` decimal(22,0) DEFAULT NULL COMMENT '修改人',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(11) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='CI建模朋友圈表';

-- ----------------------------
-- Records of iom_ci_type_rel_diagram
-- ----------------------------

-- ----------------------------
-- Table structure for sql_version
-- ----------------------------
DROP TABLE IF EXISTS `sql_version`;
CREATE TABLE `sql_version` (
  `id` decimal(20,0) NOT NULL,
  `code` int(11) DEFAULT NULL,
  `cjsj` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sql_version
-- ----------------------------

-- ----------------------------
-- Function structure for getDirChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getDirChildList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getDirChildList`(`rootId` varchar(1000)) RETURNS varchar(4000) CHARSET utf8
    DETERMINISTIC
begin 

  declare sTemp varchar(4000); 

  declare sTempChd varchar(1000); 

  set sTemp = '$';

  set sTempChd =rootId;   

  while sTempChd is not null do 

  set sTemp = concat(sTemp,',',sTempChd); 

select 

  group_concat(id) into sTempChd 

from

  iomci.iom_ci_dir 

where find_in_set(PARENT_DIR_ID, sTempChd) > 0 

  and yxbz = 1 ;

  end while; 

  return sTemp; 

 end
;;
DELIMITER ;
