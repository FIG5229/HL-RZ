ALTER TABLE `iomci`.`iom_ci_kpi`
MODIFY COLUMN `KPI_NAME` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'KPI名称' AFTER `ID`;