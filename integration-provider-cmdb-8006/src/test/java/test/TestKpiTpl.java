/*
 * package test;
 * 
 * import com.integration.entity.CiKpiTplInfo; import
 * com.integration.entity.TplItem; import com.integration.entity.Tpl_Item;
 * import com.integration.entity.Type; import
 * com.integration.integration_provider_8006; import
 * com.integration.service.CiKpiTplService; import
 * com.integration.service.TypeService; import org.junit.Test; import
 * org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.test.context.junit4.SpringJUnit4ClassRunner; import
 * org.springframework.test.context.web.WebAppConfiguration;
 * 
 * import java.util.*;
 * 
 * @RunWith(SpringJUnit4ClassRunner.class)
 * 
 * @SpringBootTest(classes = {integration_provider_8006.class,
 * TestKpiTpl.class})
 * 
 * @WebAppConfiguration public class TestKpiTpl {
 * 
 * @Autowired CiKpiTplService ciKpiTplService;
 * 
 * @Autowired TypeService typeService;
 * 
 *//**
	 * 添加模板
	 */
/*
 * @Test public void addKpiTpl() { CiKpiTplInfo info = new CiKpiTplInfo();
 * info.setTpl_name("RAM使用率"); String[] kpiIdArr = {"10060", "10031"}; String[]
 * objIdArr = {"10046", "10047"}; //int i = ciKpiTplService.insertInfo(info,
 * kpiIdArr, objIdArr);  }
 * 
 *//**
	 * 获取模板和对象组合体
	 */
/*
 * @Test public void getTpl_Item() { // List<Tpl_Item> tplItemList =
 * ciKpiTplService.getTpl_Item(); //  //
 * LinkedHashMap<String, TplItem> map = new LinkedHashMap<>(); // for (int i =
 * 0; i < tplItemList.size(); i++) { // TplItem tplItem = new TplItem(); // if
 * (map.containsKey(tplItemList.get(i).getId())) { // TplItem tplItem1 =
 * map.get(tplItemList.get(i).getId()); // List<String> obj_id =
 * tplItem1.getObj_id(); // obj_id.add(tplItemList.get(i).getObj_id()); //
 * List<Integer> obj_type = tplItem1.getObj_type(); //
 * obj_type.add(tplItemList.get(i).getObj_type()); // List<String> obj_name =
 * tplItem1.getObj_name(); // obj_name.add(tplItemList.get(i).getObj_name()); //
 * map.put(tplItemList.get(i).getId(), tplItem1); // } else { //
 * tplItem.setTpl_name(tplItemList.get(i).getTpl_name()); //
 * tplItem.setTpl_alias(tplItemList.get(i).getTpl_alias()); //
 * tplItem.setTpl_desc(tplItemList.get(i).getTpl_desc()); //
 * tplItem.setYxbz(tplItemList.get(i).getYxbz()); // ArrayList<String> objIdList
 * = new ArrayList<>(); // objIdList.add(tplItemList.get(i).getObj_id()); //
 * tplItem.setObj_id(objIdList); // ArrayList<Integer> objTypeList = new
 * ArrayList<>(); // objTypeList.add(tplItemList.get(i).getObj_type()); //
 * tplItem.setObj_type(objTypeList); // ArrayList<String> objNameList = new
 * ArrayList<>(); // objNameList.add(tplItemList.get(i).getObj_name()); //
 * tplItem.setObj_name(objNameList); // map.put(tplItemList.get(i).getId(),
 * tplItem); // } // } // Set<Map.Entry<String, TplItem>> entries =
 * map.entrySet(); // for (Map.Entry<String, TplItem> entry : entries) { //
 * String key = entry.getKey(); // TplItem value = entry.getValue(); //
 *  // } }
 * 
 *//**
	 * 删除模板
	 */
/*
 * @Test public void deleteTpl() { int i =
 * ciKpiTplService.deleteInfo("1000000000000000075"); }
 * 
 *//**
	 * 修改模板
	 */
/*
 * @Test public void update() { CiKpiTplInfo info = new CiKpiTplInfo();
 * info.setId("1000000000000000154"); info.setTpl_name("GTX");
 * 
 * 
 * String[] tplIdArr =
 * {"1000000000000000042","1000000000000000044","1000000000000000046"}; String[]
 * objIdArr =
 * {"1000000000000000090","1000000000000000215","1000000000000000213"}; //int i
 * = ciKpiTplService.updateInfo(info, tplIdArr, objIdArr);
 * 
 *//**
	 * 通过id获取大类
	 *//*
		 * @Test public void getTypeById(){ Type typeById =
		 * typeService.findTypeById("1000000000000000213");
		 * 
		 * }
		 */