package com.integration.config;

import com.integration.entity.Dir;
import com.integration.service.CiIconService;
import com.integration.service.DirService;
import com.integration.utils.ProjectPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 默认图标
 * 
 * @author dell
 *
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

	@Autowired
	private CiIconService ciIconService;
	@Resource
	private DirService dirService;

	/**
	 * 项目启动时加载方法
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			Dir dir = dirService.getDirByDirName("默认图标");
			String importDirId = "73181313049444352";
			if (dir != null){
				importDirId = dir.getId();
			}
			HttpServletRequest request = null;
			String path = System.getProperty("user.dir");
			if (ciIconService.searchByDirId(importDirId) == null || ciIconService.searchByDirId(importDirId).size() <= 0) {
				ciIconService.importIcon(path+"/DefaultIcon.zip", importDirId, null);
			}
			/*=========================================初始化容器和文本标签图标开始==========================================================*/
			Dir dir1 = dirService.getDirByDirName("shape");
			if (dir1==null){
				Dir dirNew = new Dir();
				dirNew.setDir_name("shape");
				dirNew.setYxbz(1);
				dirNew.setDir_lvl(0);
				dirNew.setDir_type(6);
				dirNew.setParent_dir_id("0");
				dirNew.setIs_leaf(0);
				dir1 = dirService.addDir(dirNew,request);
			}
			String importDirId1 = null;
			if (dir1 != null){
				importDirId1 = dir1.getId();
			}
			if (importDirId1!=null &&(ciIconService.searchByDirId(importDirId1) == null || ciIconService.searchByDirId(importDirId1).size() <= 0)) {
				ciIconService.importIcon(path+"/shape.zip", importDirId1, null);
			}
			/*=========================================初始化容器和文本标签图标结束==========================================================*/
			//初始化数据域
			try {
				File file = new File(path+"/datadomain.txt");
				StringBuilder result = new StringBuilder();
				//构造一个BufferedReader类来读取文件
				BufferedReader br = new BufferedReader(new FileReader(file));
				//指定读取行
				int num = 1;
				int lines = 0;
				String s = null;
				//使用readLine方法，一次读一行
				while((s = br.readLine())!=null){
					lines ++;
					if (num == lines){
						result.append(System.lineSeparator()+s);
					}
				}
				br.close();
				if (result.toString()!=null && !"".equals(result.toString())){
					String domainId = result.toString().split(":")[1];
					dirService.updateDirDomian(domainId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("默认图标上传失败");
		}
	}
	

































}