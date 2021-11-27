#修改domain_id 字段可以为空
alter table `iomci`.`iom_ci_dir`
  change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table `iomci`.`iom_ci_type`
  change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table  `iomci`.`iom_ci_info`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table `iomci`.`iom_ci_type_data`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table  `iomci`.`iom_ci_type_data_index`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table  `iomci`.`iom_ci_kpi_class`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table  `iomci`.`iom_ci_kpi`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';


alter table  `iomci`.`iom_ci_rel`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';

alter table  `iomci`.`iom_ci_data_rel`
    change `DOMAIN_ID` `DOMAIN_ID` int(5) null  comment '数据域ID';