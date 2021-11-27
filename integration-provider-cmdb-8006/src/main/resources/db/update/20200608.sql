#初始化图标表中新加的全限定名字段
update
  iom_ci_icon
set
  icon_full_name = concat(
    (select
      dir_name
    from
      `iom_ci_dir`
    where id = icon_dir),
    '|',
    icon_name
  )
where icon_full_name is null ;