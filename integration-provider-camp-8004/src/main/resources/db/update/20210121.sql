#修改返回结果为text类型
alter table `iommgt`.`iom_camp_action`
  change `op_result` `op_result` text  null  comment '返回结果';