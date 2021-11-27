#新增集群策略
delete from `iommgt`.`iom_camp_dict` where DICT_ID in ('1425997753283899392');

INSERT INTO `iommgt`.`iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1425997753283899392, '100204', '集群策略', '1002', 0, 0, 72904780934168577, '2021-08-13 09:48:52', NULL, NULL, 1, NULL);

ALTER TABLE `iommgt`.`iom_camp_data_domain` MODIFY COLUMN `DOMAIN_ID` varchar(25) NOT NULL COMMENT '数据域ID' AFTER `DOMAIN_DESC`;

DELETE FROM `iommgt`.`iom_camp_menu` WHERE  gncd_dm =1428174066086178816;
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1428174066086178816, '对象关系映射', '64569669314560001', NULL, NULL, NULL, 1, 'iconfont icon-circle-selected', '/sys/camp/basicInfo/relationMapping', 8, 193980644114710529, '2021-08-19 09:56:46', 193980812100780033, '2021-08-20 15:34:36', 1, 1);
DELETE FROM `iommgt`.`iom_camp_role_menu` WHERE ID = 1428174409855528960;
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1428174409855528960, '104804839359397889', 1428174066086178816, 1, 193980644114710529, '2021-08-19 09:58:08', NULL, NULL);



#接口平台集采
delete from iom_camp_role_menu where role_dm = '146380457360113664';

INSERT INTO `iom_camp_role_menu` VALUES (250008460161335296, '146380457360113664', 188888862351835136, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460169723904, '146380457360113664', 95371722399825920, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460178112512, '146380457360113664', 95371883268161537, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460186501120, '146380457360113664', 138510523468759040, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460199084032, '146380457360113664', 138513447716536321, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460207472640, '146380457360113664', 138513533779460097, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460215861248, '146380457360113664', 138513609373401089, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460224249856, '146380457360113664', 245332884985102336, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460232638464, '146380457360113664', 250007615101353984, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460241027072, '146380457360113664', 250007716620288000, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460249415680, '146380457360113664', 250007846287196160, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460257804288, '146380457360113664', 245333962204004352, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460266192896, '146380457360113664', 250008009353347072, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460274581504, '146380457360113664', 250008115137888256, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460287164416, '146380457360113664', 250008209241292800, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460295553024, '146380457360113664', 139237268249526272, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460303941632, '146380457360113664', 245335240233598976, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460333301760, '146380457360113664', 250008301981548544, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460350078976, '146380457360113664', 188888617983295488, 1, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460358467584, '146380457360113664', 188894073128632320, 2, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (250008460366856192, '146380457360113664', 188889270356951040, 2, 72904780934168577, '2021-01-27 08:27:23', NULL, NULL);
INSERT INTO `iom_camp_role_menu` VALUES (275037238713270272, '146380457360113664', 275037113005785088, 1, 72904780934168577, '2021-04-06 10:02:49', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (278386899146653696, '146380457360113664', 278386514612862976, 1, 72904780934168577, '2021-04-15 15:53:10', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (278386899155042304, '146380457360113664', 278386808818122752, 1, 72904780934168577, '2021-04-15 15:53:10', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1390596091853926400, '146380457360113664', 1390596034110943232, 1, 72904780934168577, '2021-05-07 17:15:19', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1403180924689838080, '146380457360113664', 1403171545999601664, 1, 72904780934168577, '2021-06-11 10:42:57', NULL, NULL);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1403180924689838081, '146380457360113664', 1403171545999601665, 1, 72904780934168577, '2021-06-11 10:42:57', NULL, NULL);


delete from iom_camp_menu where gncd_dm in (select gncd_dm from iom_camp_role_menu where role_dm = '146380457360113664');

INSERT INTO `iom_camp_menu` VALUES (95371722399825920, '接口平台', '0', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center', 1, 3, '2019-11-27 15:16:11', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (95371883268161537, '定时任务', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/timingtasks', 1, 3, '2019-11-27 15:16:50', 72904780934168577, '2021-01-27 08:22:09', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (138510523468759040, '系统服务', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/timingtasksService', 2, 100733577121120256, '2020-03-25 16:14:23', 72904780934168577, '2021-01-27 08:22:26', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (138513447716536321, '运行监控', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/monitor', 3, 100733577121120256, '2020-03-25 16:26:00', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (138513533779460097, '系统服务监控', '138513447716536321', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/monitor/insideHttpService', 1, 100733577121120256, '2020-03-25 16:26:21', 72904780934168577, '2021-01-27 08:23:17', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (138513609373401089, '定时任务监控', '138513447716536321', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/monitor/externalHttpService', 2, 100733577121120256, '2020-03-25 16:26:39', 72904780934168577, '2021-01-27 08:23:30', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (245332884985102336, '数据源', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataSource', 3, 72904780934168577, '2021-01-14 10:48:19', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250007615101353984, 'JDBC数据源', '245332884985102336', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataSource/timingtasksJdbc', 1, 72904780934168577, '2021-01-27 08:24:01', 72904780934168577, '2021-01-27 08:25:01', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250007716620288000, 'MQ数据源', '245332884985102336', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataSource/timingtasksMq', 2, 72904780934168577, '2021-01-27 08:24:25', 72904780934168577, '2021-01-27 08:25:06', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250007846287196160, 'webSocket数据源', '245332884985102336', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataSource/webSocketSource', 3, 72904780934168577, '2021-01-27 08:24:56', 72904780934168577, '2021-01-27 08:31:44', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (245333962204004352, '数据处理', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataProcessing', 3, 72904780934168577, '2021-01-14 10:52:35', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250008009353347072, '数据替换', '245333962204004352', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataProcessing/timingtasksReplace', 1, 72904780934168577, '2021-01-27 08:25:35', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250008115137888256, '数据筛选', '245333962204004352', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataProcessing/dataFiltering', 2, 72904780934168577, '2021-01-27 08:26:00', 72904780934168577, '2021-01-27 08:38:54', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250008209241292800, '表名替换', '245333962204004352', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/dataProcessing/timingtasksTableName', 3, 72904780934168577, '2021-01-27 08:26:23', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (139237268249526272, 'EXCEL导入', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/excelImport', 5, 72475508528529409, '2020-03-27 16:22:12', 61962686849761281, '2020-03-30 18:19:36', 1, 1);
INSERT INTO `iom_camp_menu` VALUES (245335240233598976, '基础管理', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/basicManage', 5, 72904780934168577, '2021-01-14 10:57:40', NULL, NULL, 1, 1);
INSERT INTO `iom_camp_menu` VALUES (250008301981548544, '字典管理', '245335240233598976', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/basicManage/dictionaryManage', 1, 72904780934168577, '2021-01-27 08:26:45', NULL, NULL, 1, 1);

INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (275037113005785088, '数据采集', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/interfaceTest', 3, 72904780934168577, '2021-04-06 10:02:18', NULL, NULL, 1, 1);

INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (278386514612862976, '接口权限', '95371722399825920', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/interfaceAuthority', 5, 72904780934168577, '2021-04-15 15:51:38', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (278386808818122752, '白名单', '278386514612862976', NULL, NULL, NULL, 1, 'fa fa-dot-circle-o', '/admin-center/interfaceAuthority/whiteList', 1, 72904780934168577, '2021-04-15 15:52:48', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1390596034110943232, '服务器源', '245332884985102336', NULL, NULL, NULL, 1, NULL, '/admin-center/dataSource/collectionServer', 6, 72904780934168577, '2021-05-07 17:15:05', NULL, NULL, 1, 1);

INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1403171545999601664, 'API服务', '95371722399825920', NULL, NULL, NULL, 1, NULL, '/admin-center/apiService', 3, 72904780934168577, '2021-06-11 10:05:40', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1403171545999601665, '资产采集', '95371722399825920', NULL, NULL, NULL, 1, NULL, '/admin-center/assetCollection', 3, 72904780934168577, '2021-08-21 10:05:40', NULL, NULL, 1, 1);


#告警级别颜色
UPDATE `iommgt`.`iom_camp_dict` SET `DICT_BM` = '1', `DICT_NAME` = '紧急', `SJ_ID` = '257777', `GNFL` = 0, `SORT` = 0,  `CJSJ` = '2019-08-06 16:37:29',  `COUTENT` = '#ff0000' WHERE `DICT_ID` = 54442352063954944;
UPDATE `iommgt`.`iom_camp_dict` SET `DICT_BM` = '2', `DICT_NAME` = '重要', `SJ_ID` = '257777', `GNFL` = 0, `SORT` = 0,  `CJSJ` = '2019-08-06 16:37:40', `COUTENT` = '#ffa500' WHERE `DICT_ID` = 54442398494900224;
UPDATE `iommgt`.`iom_camp_dict` SET `DICT_BM` = '3', `DICT_NAME` = '次要', `SJ_ID` = '257777', `GNFL` = 0, `SORT` = 0,  `CJSJ` = '2019-08-06 16:37:56',  `COUTENT` = '#ffd700' WHERE `DICT_ID` = 54442463707938816;
UPDATE `iommgt`.`iom_camp_dict` SET `DICT_BM` = '4', `DICT_NAME` = '一般', `SJ_ID` = '257777', `GNFL` = 0, `SORT` = 0,  `CJSJ` = '2019-08-06 16:38:03',   `COUTENT` = '#0000ff' WHERE `DICT_ID` = 54442495538511872;
UPDATE `iommgt`.`iom_camp_dict` SET `DICT_BM` = '5', `DICT_NAME` = '提示', `SJ_ID` = '257777', `GNFL` = 0, `SORT` = 0,  `CJSJ` = '2020-08-19 02:19:05', `COUTENT` = '#808080' WHERE `DICT_ID` = 191571329730887680;

