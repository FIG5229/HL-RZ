#创建配置报表相关表
drop table if exists `iom_ci_rlt_line`;

create table `iom_ci_rlt_line` (
  `ID` decimal(20,0) not null comment '主键',
  `RULE_ID` decimal(20,0) not null comment '规则ID',
  `RLT_TYPE` int(11) default null comment '规则类型 1:配置报表',
  `TYPE_ID` decimal(20,0) default null comment '触发类别ID',
  `START_TYPE_ID` decimal(20,0) not null comment '起点类别ID',
  `END_TYPE_ID` decimal(20,0) not null comment '终点类别ID',
  `RLT_ID` decimal(20,0) not null comment '关系类型ID:依赖、包含等等',
  `START_NODE_ID` decimal(20,0) default null comment '起始节点ID:供页面用',
  `END_NODE_ID` decimal(20,0) default null comment '结束节点ID:供页面用',
  `LINE_TYPE` int(11) default null comment '关系线类型:1=规则关系、2=empty',
  `LINE_OP` VARCHAR(64) default null comment '连线运算符',
  `LINE_VAL` varchar(128) default null comment '连线条件值',
  `OP_TYPE` int(11) default null comment '条件类型 1:起点;2:终点',
  `DOMAIN_ID` int(5) default null comment '所属域',
  `CJSJ` date not null comment '创建时间',
  `XGSJ` date default null comment '修改时间',
  `YXBZ` int(1) not null comment '有效标志 1=正常 0=删除',
  primary key (`ID`)
) engine=innodb default charset=utf8 comment='关系遍历连线表';

drop table if exists `iom_ci_rlt_node`;

create table `iom_ci_rlt_node` (
  `ID` decimal(20,0) not null comment '主键',
  `RULE_ID` decimal(20,0) not null comment '规则ID',
  `RLT_TYPE` int(1) default null comment '规则类型 1:配置报表',
  `NODE_TYPE` int(1) not null comment '节点类型：1=CI节点 2=运算符节点',
  `NODE_LOGIC` int(1) default null comment '节点逻辑：1=and、2=or',
  `TYPE_ID` decimal(20,0) default null comment '触发类别ID',
  `NODE_TYPE_ID` decimal(20,0) default null comment '节点分类ID',
  `PAGE_NODE_ID` decimal(20,0) default null comment '页面节点ID',
  `X` decimal(8,0) default null comment 'X坐标',
  `Y` decimal(8,0) default null comment 'Y坐标',
  `DOMAIN_ID` int(5) default null comment '所属域',
  `CJSJ` date not null comment '创建时间',
  `XGSJ` date default null comment '修改时间',
  `YXBZ` int(1) not null comment '有效标志：1-正常 0-删除',
  primary key (`ID`)
) engine=innodb default charset=utf8 comment='关系遍历节点表';

drop table if exists `iom_ci_rlt_node_cdt`;

create table `iom_ci_rlt_node_cdt` (
  `ID` decimal(20,0) not null comment '主键',
  `NODE_ID` decimal(20,0) not null comment '节点ID',
  `ATTR_ID` decimal(20,0) not null comment '分类属性ID',
  `CDT_OP` VARCHAR(64) default null comment '运算符',
  `CDT_VAL` varchar(128) default null comment '条件值',
  `IS_NOT` int(11) default null comment '非运算',
  `STOR` int(11) not null comment '排列顺序',
  `DOMAIN_ID` int(5) default null comment '所属域',
  `CJSJ` date not null comment '创建时间',
  `XGSJ` date default null comment '修改时间',
  `YXBZ` int(1) not null comment '有效标志',
  primary key (`ID`)
) engine=innodb default charset=utf8 comment='关系遍历节点条件表';

drop table if exists `iom_ci_rlt_rule`;

create table `iom_ci_rlt_rule` (
  `ID` decimal(20,0) not null comment '主键',
  `RLT_TYPE` int(11) not null comment '规则类型1:配置报表',
  `RLT_NAME` varchar(128) not null comment '名称',
  `TYPE_ID` decimal(20,0) not null comment '触发类别 触发CI大类ID',
  `RLT_DESC` varchar(512) default null comment '定义描述',
  `DIAG_XML` text comment '视图XML',
  `STATUS` int(1) default null comment '有效状态0:无效;1:有效',
  `VALID_DESC` varchar(512) default null comment '无效说明',
  `DOMAIN_ID` int(5) default null comment '所属域',
  `CJR_ID` decimal(20,0) not null comment '创建人',
  `CJSJ` date not null comment '创建时间',
  `XGR_ID` decimal(20,0) default null comment '修改人',
  `XGSJ` date default null comment '修改时间',
  `YXBZ` int(1) not null comment '有效标志',
  primary key (`ID`)
) engine=innodb default charset=utf8 comment='数据提取规则表\n配置报表数据表';