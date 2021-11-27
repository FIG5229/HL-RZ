package com.integration.config;

import com.integration.controller.BabyConntroller;
import com.integration.entity.Domain;
import com.integration.service.DomainService;
import com.integration.utils.ProjectPath;
import com.integration.utils.SeqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * 初始化默认数据域权限
 *
 * @author ztl
 *
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(BabyConntroller.class);
	@Resource
	private DomainService domainService;
	/**
	 * 项目启动时加载方法
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {

			HttpServletRequest request = null;
			String path= System.getProperty("user.dir");
			File file = new File(path+"/datadomain.txt");
			StringBuilder result = new StringBuilder(1000);
			//构造一个BufferedReader类来读取文件
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8.name());
			BufferedReader br = new BufferedReader(isr);
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
				Domain domain = domainService.findDomainByDomainCode("default");
				if (domain == null){
					Domain newDomain = new Domain();
					newDomain.setId(SeqUtil.nextId().toString());
					newDomain.setDomain_code("default");
					newDomain.setDomain_name("默认");
					newDomain.setDomain_id(domainId);
					newDomain.setYxbz("1");
					domainService.insertDomain(newDomain);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	

































}