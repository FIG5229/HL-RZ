DROP TABLE IF EXISTS `IOM_CAMP_ROLE_DOMAIN`;

create table IOM_CAMP_ROLE_DOMAIN  (
   ID                   DECIMAL(20,0)   not null,
   ROLE_DM              DECIMAL(20,0) not null comment '角色代码',
   DATA_DOMAIN          VARCHAR(500) not null comment '角色对应数据域，多个逗号分隔',
   CJR_ID               DECIMAL(20,0)  comment '创建人',
   CJSJ                 DATETIME       comment '创建时间',
   XGR_ID               DECIMAL(20,0) comment '修改人',
   XGSJ                 DATETIME comment '修改时间',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8
CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色和数据域关联表';