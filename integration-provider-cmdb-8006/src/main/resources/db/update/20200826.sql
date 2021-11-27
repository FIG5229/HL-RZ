#修改最大值最小值字段长度
ALTER TABLE iom_ci_kpi MODIFY COLUMN `MAXIMUM` decimal(20,2);
ALTER TABLE iom_ci_kpi MODIFY COLUMN `MINIMUM` decimal(20,2);