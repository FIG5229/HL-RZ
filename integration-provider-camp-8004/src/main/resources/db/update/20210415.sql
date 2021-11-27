ALTER TABLE `iommgt`.`iom_camp_license_auth`
ADD COLUMN `allow_module_name` varchar(255) NULL COMMENT '允许访问的模块名称' AFTER `menu_str`;


ALTER TABLE `iommgt`.`iom_camp_action`
MODIFY COLUMN `op_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回结果' AFTER `op_param`;