delete from `iommgt`.`iom_camp_menu` where gncd_dm = 1404976760981680128;
delete from `iommgt`.`iom_camp_role_menu` where id = 1404976849426968576;
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1404976760981680128, '自动排班', '135543193558204416', NULL, NULL, NULL, 1, NULL, '/sys/scene/scheduleList', 4, 72904780934168577, '2021-06-16 09:38:57', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1404976849426968576, '104804839359397889', 1404976760981680128, 1, 72904780934168577, '2021-06-16 09:39:19', NULL, NULL);
