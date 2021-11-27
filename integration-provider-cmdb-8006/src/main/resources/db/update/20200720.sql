  #修改配置报表相关功能表格修改date为datetime
  alter table `iomci`.`iom_ci_rlt_rule`
  change `CJSJ` `CJSJ` datetime not null  comment '创建时间',
  change `XGSJ` `XGSJ` datetime null  comment '修改时间';

  alter table `iomci`.`iom_ci_rlt_line`
  change `CJSJ` `CJSJ` datetime not null  comment '创建时间',
  change `XGSJ` `XGSJ` datetime null  comment '修改时间';

  alter table `iomci`.`iom_ci_rlt_node`
  change `CJSJ` `CJSJ` datetime not null  comment '创建时间',
  change `XGSJ` `XGSJ` datetime null  comment '修改时间';


  alter table `iomci`.`iom_ci_rlt_node_cdt`
  change `CJSJ` `CJSJ` datetime not null  comment '创建时间',
  change `XGSJ` `XGSJ` datetime null  comment '修改时间';
