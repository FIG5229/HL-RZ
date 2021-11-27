ALTER TABLE `iommgt`.`iom_camp_license_auth`
MODIFY COLUMN `menu_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单列表散列值' AFTER `is_blocked`,
MODIFY COLUMN `allow_module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '允许访问的模块名称' AFTER `menu_str`,
MODIFY COLUMN `CJR_ID` decimal(20, 0) NOT NULL COMMENT '创建人' AFTER `allow_module_name`;