#增加数据域字段
alter table `iomci`.`iom_ci_dir`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `DIR_DESC`;

alter table `iomci`.`iom_ci_type`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `SORT`;

alter table `iomci`.`iom_ci_info`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `YXBZ`;

alter table `iomci`.`iom_ci_type_data`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `DATA_35`;

alter table `iomci`.`iom_ci_type_data_index`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `IDX`;

alter table `iomci`.`iom_ci_kpi_class`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `NAME`;

alter table `iomci`.`iom_ci_kpi`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `IDX_FIELD`;