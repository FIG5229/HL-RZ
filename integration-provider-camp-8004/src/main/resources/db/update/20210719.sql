DELETE FROM `iommgt`.`iom_camp_menu` WHERE gncd_dm ='1412963459305304064';
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1412963459305304064, '关联字段配置', '64569669314560001', NULL, NULL, NULL, 1, NULL, '/sys/camp/basicInfo/relationField', 8, 193980644114710529, '2021-07-08 10:35:14', NULL, NULL, 1, 1);

DELETE FROM `iommgt`.`iom_camp_role_menu` WHERE id ='1412963515072770048';
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1412963515072770048, '104804839359397889', 1412963459305304064, 1, 193980644114710529, '2021-07-08 10:35:28', NULL, NULL);
