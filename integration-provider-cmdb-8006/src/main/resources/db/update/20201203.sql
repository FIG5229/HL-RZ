alter table iom_ci_dir modify column `DIR_TYPE` int NOT NULL COMMENT '目录类型1:CI类别;2:CI关系;3:图标;4:视图;5:标签';

DROP TABLE IF EXISTS `iom_ci_label`;

/*==============================================================*/
/* Table: iom_ci_label                                                  */
/*==============================================================*/
create table iom_ci_label  (
   ID                   DECIMAL(20,0)   not null,
   LABEL_NAME           VARCHAR(256) not null comment '标签名',
   DIR_ID               DECIMAL(20,0)   not null comment '目录ID',
   LABEL_RULE_INFO      LONGTEXT comment '标签规则',
   DOMAIN_ID            INT comment '数据域',
   CJR_ID               DECIMAL(20,0)  not null comment '创建人',
   CJSJ                 DATETIME         not null comment '创建时间',
   XGR_ID               DECIMAL(20,0) comment '修改人',
   XGSJ                 DATETIME comment '修改时间',
   YXBZ                 INT          not null comment '有效标志',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='标签表';