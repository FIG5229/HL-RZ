#增加索引
ALTER TABLE iom_ci_info ADD INDEX `info_domain_id` (`DOMAIN_ID`) USING BTREE;
ALTER TABLE iom_ci_info ADD INDEX `info_yxbz` (`YXBZ`) USING BTREE;


ALTER TABLE iom_ci_type_data ADD INDEX `type_data_type_id` (`CI_TYPE_ID`) USING BTREE;
ALTER TABLE iom_ci_type_data ADD INDEX `type_data_domain_id` (`DOMAIN_ID`) USING BTREE;
ALTER TABLE iom_ci_type_data ADD INDEX `type_data_id` (`ID`) USING BTREE;


ALTER TABLE iom_ci_type ADD INDEX `type_domain_id` (`DOMAIN_ID`) USING BTREE;
ALTER TABLE iom_ci_type ADD INDEX `type_yxbz` (`YXBZ`) USING BTREE;