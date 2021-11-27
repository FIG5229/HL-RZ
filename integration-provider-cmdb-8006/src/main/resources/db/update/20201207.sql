alter table iom_ci_dir modify column `DIR_TYPE` int NOT NULL COMMENT '目录类型1:CI类别;2:CI关系;3:图标;4:视图;5:标签;6:默认图标';

UPDATE iom_ci_dir SET DIR_TYPE=6 WHERE DIR_NAME='默认图标';

UPDATE iom_ci_dir SET DIR_TYPE=6 WHERE DIR_NAME='shape';