
delete from `iom_camp_parameter` where para_code = 'retain_event_time';
INSERT INTO `iommgt`.`iom_camp_parameter`(`PARA_ID`, `PARA_CODE`, `PARA_MC`, `PARA_TYPE`, `PARA_DATA`, `PARA_DESC`, `IS_OPEN`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`) VALUES (1376835139589369856, 'retain_event_time', '历史告警保留数据时间', NULL, '90', '历史告警保留数据时间(单位：天)', 1, 1, 1376354405414072321, '2021-03-30 17:54:12', NULL, NULL, 1);
