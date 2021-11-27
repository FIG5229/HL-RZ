#初始化CI类定义排序
UPDATE iom_ci_type_item   SET SORT = IF(SORT=0,SUBSTRING_INDEX(MP_CI_ITEM,'_',-1),SORT)