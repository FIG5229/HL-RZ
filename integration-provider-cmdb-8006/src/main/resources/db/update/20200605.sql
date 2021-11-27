#图标表增加全限定名，dmv模块根据图标和目录名称获取图标路径
alter table `iomci`.`iom_ci_icon`
  add column `ICON_FULL_NAME` varchar(200) null  comment '图标全限定名称:目录名称|图标名称  如：常用图标|Firewall' after `ICON_NAME`;
