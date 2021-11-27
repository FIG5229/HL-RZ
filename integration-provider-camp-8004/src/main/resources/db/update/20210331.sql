update iom_camp_menu set yxbz=0 where gncd_dm='78816917619621888';
update iom_camp_menu set yxbz=0 where gncd_dm='73623657733308416';
update iom_camp_menu set yxbz=0 where gncd_dm='73623554029142016';

delete from iom_camp_role_menu where gncd_dm = '78816917619621888';
delete from iom_camp_role_menu where gncd_dm = '73623657733308416';
delete from iom_camp_role_menu where gncd_dm = '73623554029142016';

update iommgt.iom_camp_custom_menu set url = REPLACE(url,'/monitoringStation','');