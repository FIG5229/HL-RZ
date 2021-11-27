 #增加ciInfo表CI_BM字段索引
ALTER TABLE `iomci`.`iom_ci_info` ADD INDEX `info_ci_bm`(`CI_BM`) USING BTREE;