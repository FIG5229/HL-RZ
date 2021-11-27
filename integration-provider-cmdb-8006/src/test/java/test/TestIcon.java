/*
 * package test;
 * 
 * import com.integration.dao.CiIconDao; import
 * com.integration.entity.CiIconInfo; import
 * com.integration.integration_provider_8006; import
 * com.integration.service.CiIconService; import
 * com.integration.utils.FastDfsUtils; import com.integration.utils.FileUtils;
 * import org.junit.Test; import org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.test.context.junit4.SpringJUnit4ClassRunner; import
 * org.springframework.test.context.web.WebAppConfiguration;
 * 
 * import javax.servlet.http.HttpServletRequest; import java.io.File; import
 * java.util.ArrayList; import java.util.List; import java.util.ResourceBundle;
 * 
 * @RunWith(SpringJUnit4ClassRunner.class)
 * 
 * @SpringBootTest(classes={integration_provider_8006.class, TestIcon.class})
 * 
 * @WebAppConfiguration public class TestIcon {
 * 
 * @Autowired private CiIconService ciIconService;
 * 
 * @Autowired private FastDfsUtils fastDfsUtils;
 * 
 * @Autowired private FileUtils fileUtils;
 * 
 * @Autowired CiIconDao ciIconDao;
 * 
 *//**
	 * 导入图片
	 * 
	 * @throws Exception
	 */
/*
 * @Test public void test() throws Exception { HttpServletRequest request =
 * null; List<CiIconInfo> ciIconList = ciIconService.importIcon("G:/图标.zip",
 * "1",request); for (CiIconInfo info : ciIconList) {
 *  } }
 * 
 *//**
	 * 导出图片
	 */
/*
 * @Test public void export() throws Exception { String dirId = "-1";
 * ciIconService.exportIcon(dirId); }
 * 
 *//**
	 * 下载文件
	 */
/*
 * @Test public void download() throws Exception { ArrayList<CiIconInfo> list =
 * new ArrayList<>(); CiIconInfo info = new CiIconInfo();
 * info.setIcon_name("test"); info.setIcon_form("jpg"); info.setIcon_path(
 * "http://192.168.7.87:8888/group1/M00/00/00/wKgHV1wIjwiAQs7wAACzHiJfrTk825.jpg"
 * ); list.add(info); fastDfsUtils.download(list); }
 *//***
	 * 上传图片
	 * 
	 * @throws Exception
	 */
/*
 * @Test public void test1() throws Exception { ArrayList<String> list = new
 * ArrayList<>(); list.add("G:/1.jpg"); ArrayList<String> upload =
 * fastDfsUtils.upload(list); for (String s : upload) {  }
 * }
 * 
 *//**
	 * 获取图标大小
	 */
/*
 * @Test public void getFileLength(){ String s = "G:/1.jpg"; File file = new
 * File(s); fileUtils.getFileLength(file); }
 * 
 *//**
	 * 修改单个图标
	 */
/*
 * @Test public void updateIcon(){ String path = "G:/1210.jpg"; String iconId =
 * "1000000000000000509";
 * 
 * int i = ciIconService.updateInfo(path, iconId);  }
 * 
 *//**
	 * 删除单个图标
	 */
/*
 * @Test public void deleteIcon(){ int i =
 * ciIconService.deleteInfo("2264217176653824");  }
 * 
 *//**
	 * 通过目录id查询图标
	 */
/*
 * @Test public void searchById(){
 * 
 * List<CiIconInfo> list = ciIconService.searchByDirId("1"); for (CiIconInfo
 * info : list) {  } }
 * 
 *//**
	 * 通过名称进行模糊查询
	 *//*
		 * 
		 * @Test public void searchByName(){ List<CiIconInfo> list =
		 * ciIconService.searchByName("t"); for (CiIconInfo info : list) {
		 *  } }
		 * 
		 * @Test public void deleteAll(){ fileUtils.delAllFile("C:/zipFile"); }
		 * 
		 * @Test public void readProper(){ ResourceBundle resource =
		 * ResourceBundle.getBundle("config/fastconf"); String test =
		 * resource.getString("tracker_http_port");  }
		 * 
		 * @Test public void getAllPath(){ String path = "C:/down"; ArrayList<String>
		 * strings = new ArrayList<>(); ArrayList<String> filePath =
		 * fileUtils.getFilePath(path, strings); for (String s : filePath) {
		 *  } }
		 * 
		 * @Test public void searchByDirId(){ String dirId ="1000000000000000228";
		 * List<CiIconInfo> ciIconInfos = ciIconDao.searchByDirId(dirId); for
		 * (CiIconInfo ciIconInfo : ciIconInfos) {
		 *  } }
		 * 
		 * @Test public void decompress() throws Exception{ File file = new
		 * File("G:/交换机.zip"); fileUtils.decompression(file,"C:/zipFile"); }
		 * 
		 * }
		 */