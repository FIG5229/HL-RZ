#增加数据域字段
alter table `iomci`.iom_ci_type_rel add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `CJR_ID`;

alter table `iomci`.iom_ci_type_focus_rel add column `DOMAIN_ID` int(5)  null  comment '数据域ID' after `CJR_ID`;