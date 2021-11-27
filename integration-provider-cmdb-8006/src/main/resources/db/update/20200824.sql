#指标模型增加最大值最小值
alter table iom_ci_kpi add column `MAXIMUM` decimal(20,10)  null  comment '最大值' after `IDX_FIELD`;
alter table iom_ci_kpi add column `MINIMUM` decimal(20,10)  null  comment '最小值' after `IDX_FIELD`;