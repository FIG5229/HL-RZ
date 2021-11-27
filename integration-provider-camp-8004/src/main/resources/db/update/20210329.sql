#初始化系统参数表数据 ES是否分索引和性能ES保留数据时间
delete from `iom_camp_parameter` where para_code = 'is_sub_index';
delete from `iom_camp_parameter` where para_code = 'retain_performance_time';
insert into `iommgt`.`iom_camp_parameter` (`PARA_ID`, `PARA_CODE`, `PARA_MC`, `PARA_TYPE`, `PARA_DATA`, `PARA_DESC`, `IS_OPEN`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`) VALUES ('1376358875657138176', 'is_sub_index', 'ES是否分索引', NULL, '0', 'ES是否分索引(1：分索引 0：不分索引)', '0', '1', '1376354940355604481', '2021-03-29 10:21:42', '1376354940355604481', '2021-03-29 17:26:23', '1');
insert into `iommgt`.`iom_camp_parameter` (`PARA_ID`, `PARA_CODE`, `PARA_MC`, `PARA_TYPE`, `PARA_DATA`, `PARA_DESC`, `IS_OPEN`, `SORT`, `CJR_ID`, `CJSJ`, `XGR_ID`, `XGSJ`, `YXBZ`) VALUES ('1376359006620086272', 'retain_performance_time', '性能ES保留数据时间', NULL, '90', '性能ES保留数据时间(单位：天)', '0', '1', '1376354940355604481', '2021-03-29 10:22:13', '1376354940355604481', '2021-03-29 17:25:32', '1');
