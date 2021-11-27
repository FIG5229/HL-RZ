alter table `iomci`.`iom_ci_rel`
  add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `CJR_ID`;