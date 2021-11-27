delete from iom_camp_dict where DICT_ID in (1426084773918273536,
1426084867493195777,
1426085244179443712,
1426085470248235008,
1426085648019615744,
1426085792093958145);

INSERT INTO `iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1426084773918273536, 'device_check_kpi', '命令名称', '0', 0, 0, 72904780934168577, '2021-08-13 15:34:40', NULL, NULL, 1, NULL);
INSERT INTO `iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1426084867493195777, 'fan', '风扇巡检', 'device_check_kpi', 0, 0, 72904780934168577, '2021-08-13 15:35:02', 72904780934168577, '2021-08-13 15:35:57', 1, '');
INSERT INTO `iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1426085244179443712, 'power', '电源巡检', 'device_check_kpi', 0, 0, 72904780934168577, '2021-08-13 15:36:32', NULL, NULL, 1, '');
INSERT INTO `iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1426085470248235008, 'memory_usage', '内存使用率', 'device_check_kpi', 0, 0, 72904780934168577, '2021-08-13 15:37:26', NULL, NULL, 1, '');
INSERT INTO `iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1426085648019615744, 'cpu_usage', 'CPU使用率', 'device_check_kpi', 0, 0, 72904780934168577, '2021-08-13 15:38:08', NULL, NULL, 1, '');
INSERT INTO `iom_camp_dict`(`DICT_ID`, `DICT_BM`, `DICT_NAME`, `SJ_ID`, `GNFL`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`, `COUTENT`) VALUES (1426085792093958145, 'logbuffer', '日志', 'device_check_kpi', 0, 0, 72904780934168577, '2021-08-13 15:38:42', NULL, NULL, 1, '');