
delete from `iommgt`.`iom_camp_menu` where `gncd_dm` = '1407157810105413632';
delete from `iommgt`.`iom_camp_menu` where `gncd_dm` = '1407157638231224320';
delete from `iommgt`.`iom_camp_menu` where `gncd_dm` = '1407157273645543424';

INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1407157810105413632, '定期巡检规则', '1407157638231224320', NULL, NULL, NULL, 1, NULL, '/sys/intel/ruleRegularCheck', 1, 193980812100780033, '2021-06-22 10:05:40', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1407157638231224320, '规则设置', '1407157273645543424', NULL, NULL, NULL, 1, NULL, '', 1, 193980812100780033, '2021-06-22 10:04:59', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1407157273645543424, '智能运维', '0', NULL, NULL, NULL, 1, 'iconfont icon-circle-selected', '', 5, 193980812100780033, '2021-06-22 10:03:32', 193980812100780033, '2021-06-22 10:03:48', 1, 1);

delete from `iommgt`.`iom_camp_role_menu` where `id` = '1407157901692235776';
delete from `iommgt`.`iom_camp_role_menu` where `id` = '1407157901683847168';
delete from `iommgt`.`iom_camp_role_menu` where `id` = '1407157901675458560';

INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1407157901692235776, '104804839359397889', 1407157810105413632, 1, 193980812100780033, '2021-06-22 10:06:02', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1407157901683847168, '104804839359397889', 1407157638231224320, 1, 193980812100780033, '2021-06-22 10:06:02', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1407157901675458560, '104804839359397889', 1407157273645543424, 1, 193980812100780033, '2021-06-22 10:06:02', NULL, NULL);
