ALTER TABLE `iomci`.`iom_ci_type_item`  ADD COLUMN `IS_LABEL` int(0) NULL DEFAULT 0 COMMENT '是否标签,1:是;0:否' AFTER `IS_RELA`;

ALTER TABLE `iomci`.`iom_ci_info` CHANGE COLUMN `CI_BM` `CI_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'CI编码' AFTER `ID`;

ALTER TABLE `iomci`.`iom_ci_info` ADD COLUMN `CI_NAME` varchar(255) NULL COMMENT 'CI名称' AFTER `CI_CODE`;