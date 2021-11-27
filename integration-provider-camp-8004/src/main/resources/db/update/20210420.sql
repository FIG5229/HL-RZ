delete from `iommgt`.`iom_camp_menu` where gncd_dm = '1384316514634559488';
delete from `iommgt`.`iom_camp_role_menu` where id = '1384316601309851648';


INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1384316514634559488, '定时任务', '64569584979689473', NULL, NULL, NULL, 1, 'iconfont icon-circle-selected', '/sys/camp/timingTask', 1, 72904780934168577, '2021-04-20 09:22:31', 72904780934168577, '2021-04-20 09:23:18', 1, 1);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1384316601309851648, '104804839359397889', 1384316514634559488, 1, 72904780934168577, '2021-04-20 09:22:52', NULL, NULL);
