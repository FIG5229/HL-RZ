delete from `iommgt`.`iom_camp_menu` where gncd_dm in (1415477310609420288);
delete from `iommgt`.`iom_camp_role_menu` where id in (1415477454285303808);


INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1415477310609420288, '任务执行', '1413047457670885376', NULL, NULL, NULL, 1, NULL, '/sys/intel/ocp/taskExec', 4, 72904780934168577, '2021-07-15 09:04:23', NULL, NULL, 1, 1);

INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1415477454285303808, '104804839359397889', 1415477310609420288, 1, 72904780934168577, '2021-07-15 09:04:58', NULL, NULL);
