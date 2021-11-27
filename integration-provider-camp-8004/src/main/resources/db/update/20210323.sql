CREATE TABLE `iom_camp_job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `class_name` varchar(100) DEFAULT NULL,
  `cron` varchar(50) DEFAULT NULL,
  `job_state` varchar(10) DEFAULT NULL,
  `CJR_ID` decimal(20,0) NOT NULL COMMENT '创建人ID',
  `CJSJ` datetime NOT NULL COMMENT '创建时间',
  `XGR_ID` decimal(20,0) DEFAULT NULL COMMENT '修改人ID',
  `XGSJ` datetime DEFAULT NULL COMMENT '修改时间',
  `YXBZ` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务调度-任务表,关联job框架quartz的job';