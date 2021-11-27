ALTER TABLE `iomci`.`iom_ci_mgt_log`
MODIFY COLUMN `CI_ID` varchar(512) NOT NULL COMMENT '所属CI' AFTER `ID`;