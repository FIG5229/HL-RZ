drop table if exists iom_camp_job;
CREATE TABLE `iom_camp_job` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '名称 通过这个找对应的schedule任务',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) DEFAULT NULL COMMENT 'job全类名',
  `cron` varchar(50) DEFAULT NULL COMMENT 'cron表达式',
  `job_state` varchar(10) DEFAULT NULL COMMENT '任务状态',
  `cjr_id` decimal(20,0) NOT NULL COMMENT '创建人ID',
  `cjsj` datetime NOT NULL COMMENT '创建时间',
  `xgr_id` decimal(20,0) DEFAULT NULL COMMENT '修改人ID',
  `xgsj` datetime DEFAULT NULL COMMENT '修改时间',
  `yxbz` int(1) NOT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务调度-任务表,关联job框架quartz的job';