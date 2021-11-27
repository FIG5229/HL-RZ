#增加工单规则菜单
delete from `iommgt`.`iom_camp_menu` where gncd_dm ='1405754215052996608';
delete from `iommgt`.`iom_camp_role_menu` where gncd_dm ='1405754215052996608';
INSERT INTO `iommgt`.`iom_camp_menu`(`gncd_dm`, `gncd_mc`, `sj_gncd_dm`, `sj_gncd_mc`, `gnfl`, `gncd_level`, `gncd_type`, `gncd_img`, `gncd_url`, `sort`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`, `yxbz`, `display_flag`) VALUES (1405754215052996608, '工单规则', '61650495902728193', NULL, NULL, NULL, 1, NULL, '/sys/event/ruleWorkOrder', 12, 72904780934168577, '2021-06-18 13:08:17', NULL, NULL, 1, 1);
INSERT INTO `iommgt`.`iom_camp_role_menu`(`id`, `role_dm`, `gncd_dm`, `gnfl_type`, `cjr_id`, `cjsj`, `xgr_id`, `xgsj`) VALUES (1405754500907397120, '104804839359397889', 1405754215052996608, 1, 72904780934168577, '2021-06-18 13:09:25', NULL, NULL);



delete from `iommgt`.`iom_camp_parameter` where PARA_ID in (1405329737508581376,1405329905578536960,1405330225415188480);

INSERT INTO `iommgt`.`iom_camp_parameter`(`PARA_ID`, `PARA_CODE`, `PARA_MC`, `PARA_TYPE`, `PARA_DATA`, `PARA_DESC`, `IS_OPEN`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`) VALUES (1405329737508581376, 'job_limit_alarm_epe', '工单告警限制时间', NULL, '60,120', '工单告警流程节点限定时间60,120代表60分钟签收，120分钟节点处理', 1, 1, 72904780934168577, '2021-06-17 09:01:33', NULL, NULL, 1);
INSERT INTO `iommgt`.`iom_camp_parameter`(`PARA_ID`, `PARA_CODE`, `PARA_MC`, `PARA_TYPE`, `PARA_DATA`, `PARA_DESC`, `IS_OPEN`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`) VALUES (1405329905578536960, 'job_limit_alarm_index', '工单预警限制时间', NULL, '60,120', '工单预警流程节点限定时间60,120代表60分钟签收，120分钟节点处理', 1, 1, 72904780934168577, '2021-06-17 09:02:13', 72904780934168577, '2021-06-17 09:02:27', 1);
INSERT INTO `iommgt`.`iom_camp_parameter`(`PARA_ID`, `PARA_CODE`, `PARA_MC`, `PARA_TYPE`, `PARA_DATA`, `PARA_DESC`, `IS_OPEN`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`) VALUES (1405330225415188480, 'job_limit_common_inspection', '工单巡检限制时间', NULL, '60,120', '工单巡检流程节点限定时间60,120代表60分钟签收，120分钟节点处理', 1, 1, 72904780934168577, '2021-06-17 09:03:30', 72904780934168577, '2021-06-17 09:03:51', 1);
