#新增
alter table `iomci`.`iom_ci_type_item` add column `IS_RELA` int(4) default 0  null  comment '是否关联字段，接口平台通过该字段匹配CICODE' after `IS_REQU`;