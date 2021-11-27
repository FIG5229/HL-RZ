ALTER TABLE `iommgt`.`iom_camp_action`
MODIFY COLUMN `op_param` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '前端参数' AFTER `is_status`;