DROP TABLE IF EXISTS `IOM_CI_ASSOCIATED_FIELD_CONF`;

create table IOM_CI_ASSOCIATED_FIELD_CONF  (
   ID                   DECIMAL(20,0)   not null,
   SOURCE_CI_ID           VARCHAR(64) not null comment '数据源ID',
   CI_TYPE_ID               DECIMAL(20,0)   not null comment 'CI大类ID',
   CI_ITEM_ID      LONGTEXT comment 'CI属性ID（多个逗号分隔）',
   DOMAIN_ID            VARCHAR(512) comment '数据域',
   CJR_ID               DECIMAL(20,0)  not null comment '创建人',
   CJSJ                 DATETIME         not null comment '创建时间',
   XGR_ID               DECIMAL(20,0) comment '修改人',
   XGSJ                 DATETIME comment '修改时间',
   YXBZ                 INT          not null comment '有效标志',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='关联字段配置表';


DROP TABLE IF EXISTS `IOM_CI_ASSOCIATED_TRIGGER`;

create table IOM_CI_ASSOCIATED_TRIGGER  (
   ID                   DECIMAL(20,0)   not null,
   CONF_ID           VARCHAR(64) not null comment '配置ID',
   CJR_ID               DECIMAL(20,0)  not null comment '创建人',
   CJSJ                 DATETIME         not null comment '创建时间',
   XGR_ID               DECIMAL(20,0) comment '修改人',
   XGSJ                 DATETIME comment '修改时间',
   YXBZ                 INT          not null comment '有效标志',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='关联字段配置触发记录表';