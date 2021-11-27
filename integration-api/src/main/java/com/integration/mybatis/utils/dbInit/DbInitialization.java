package com.integration.mybatis.utils.dbInit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.integration.utils.SerialNumberUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.tools.ant.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.integration.generator.dao.SqlVersionMapper;
import com.integration.generator.entity.SqlVersion;
import com.integration.generator.entity.SqlVersionExample;
import com.integration.utils.RedisUtils;
import com.integration.utils.SeqUtil;

@Component
public class DbInitialization {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbInitialization.class);

	@Value("${spring.datasource.${prefix:}url}")
	String URL;
	@Value("${spring.datasource.${prefix:}username}")
	String username;
	@Value("${spring.datasource.${prefix:}password}")
	String password;
	@Value("${spring.datasource.${prefix:}driver-class-name:'com.mysql.jdbc.Driver'}")
	String driverClassName;

	@Resource
	SqlVersionMapper sqlVersionMapper;
	private static final long expireTime = 10*60L;

	private static final String sqVersionInit = "CREATE TABLE `sql_version` (`id` DECIMAL (20, 0) NOT NULL,`code` INT (11) DEFAULT NULL,`cjsj` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,PRIMARY KEY (`id`)) ENGINE = INNODB DEFAULT CHARSET = utf8;";

	private static final String sqlDomainInit = "CREATE TABLE `iom_camp_domain_init` (`id` DECIMAL (20, 0) NOT NULL,`domainId` varchar(25) comment '数据域',`cjsj` datetime comment '创建时间',PRIMARY KEY (`id`)) ENGINE = INNODB DEFAULT CHARSET = utf8;";

	public void initialize() {
		LOGGER.info("======================DbInitialization start======================");
		LOGGER.info("======================"+URL+"======================");
		try {
			//先将url分成两段，防止传递参数有/对解析有影响
			String mysqlUrl = URL.split("\\?")[0];
			String mysqlUrlArgs = URL.split("\\?")[1];
			String urlBase = mysqlUrl.substring(0, mysqlUrl.lastIndexOf("/")) + "?" + mysqlUrlArgs;
			String database = mysqlUrl.substring(mysqlUrl.lastIndexOf("/") + 1);

			//监测是否有相同数据库正在升级
			String initKey = "initialize-"+ database;
			while (RedisUtils.exists(initKey)) {
				LOGGER.info("检测到有其他项目正在初始化数据库："+database+"，等30秒");
				try {
					Thread.sleep(1000*30L);
				} catch (InterruptedException e) {
					LOGGER.error("Thread.sleep执行失败！");
				}
			}
			//占位
			try {
				RedisUtils.set(initKey, "start"+ String.join(",", SerialNumberUtil.getLocalHostLANAddress().toArray(new String[1])),expireTime);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			try {
				doinit(urlBase, database);
			}catch (Exception e) {
				LOGGER.error("doinit执行失败！");
			}
			//结束时,删除占位
			if (RedisUtils.exists(initKey)){
				RedisUtils.remove(initKey);
			}

		} catch (Exception e) {
			LOGGER.error("数据库初始化时异常：",e);
		}


		LOGGER.info("======================DbInitialization end======================");
	}

	public void doinit(String urlBase,String database) {
		Connection conn = null;
		Statement stat = null;

		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

		try {
			// 使用用户名root，密码空格登陆
			try {
				conn = DriverManager.getConnection(urlBase, username, "");
				// 如果登陆成功了, 说明密码还没有改，那么初始化数据库
			} catch (SQLException e) {
				// 说明已经改过密码了， 数据库也初始化了
				LOGGER.error("Database has initialized password and database successfully!");
			}
			if (conn != null) {
				stat = conn.createStatement();
				// 修改密码
				stat.executeUpdate(
						"UPDATE mysql.user SET password=PASSWORD('" + password + "') WHERE user='" + username + "'");
				stat.executeUpdate("FLUSH PRIVILEGES");
				// 创建数据库
				stat.executeUpdate("CREATE DATABASE " + database);
				stat.close();
				conn.close();
			}
		} catch (Exception e) {
			// 若不能建立连接，则退出
			e.printStackTrace();
			return;
		}

		try {
			try {
				conn = DriverManager.getConnection(URL, username, password);
				stat = conn.createStatement();
			} catch (Exception e) {
				conn = DriverManager.getConnection(urlBase, username, password);
				stat = conn.createStatement();
				// 创建数据库
				stat.executeUpdate("CREATE DATABASE " + database +" DEFAULT CHARACTER SET  utf8 COLLATE  utf8_general_ci");
				stat.close();
				conn.close();
				conn = DriverManager.getConnection(URL, username, password);
				stat = conn.createStatement();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		SqlVersion version = null;
		try {
			version = getVersion();
		} catch (Exception e) {
			// 如果找不到版本管理表，则认为没有加入过本系统的所有表，执行初始化操作
			try {
				stat.executeUpdate(sqVersionInit);
				executeSqlPath("/db/"+"install.sql", stat);
			} catch (SQLException e1) {
				// 若没有版本表，切建立失败则退出
				e1.printStackTrace();
				return;
			}
		}

		try {
			// 初始化数据库

			if (version == null) {
				version = new SqlVersion();
				version.setCode(-1);
			}

			// 升级
			String[] updateFileNames = getSqlFileNames();
			if (updateFileNames != null && updateFileNames.length > 0) {
				Arrays.sort(updateFileNames);
				for (String updateFileName : updateFileNames) {
					Integer updateVersion = Integer.parseInt(updateFileName.substring(0, updateFileName.indexOf(".")));
					if (updateVersion > version.getCode()) {
						LOGGER.info("数据库初始化：" + updateVersion);
						executeSqlPath("/db/update/"+updateFileName, stat);
						addVersion(updateVersion.toString());
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error("升级sql文件内容不正确，最后一次成功版本是：", version);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SqlVersion getVersion() {
		SqlVersion reSqlVersion = null;
		SqlVersionExample example = new SqlVersionExample();
		List<SqlVersion> sqlVersions = sqlVersionMapper.selectByExample(example);
		if (sqlVersions != null && sqlVersions.size() > 0) {
			for (SqlVersion sqlVersion : sqlVersions) {
				if (reSqlVersion == null || sqlVersion.getCode().intValue() > reSqlVersion.getCode().intValue()) {
					reSqlVersion = sqlVersion;
				}
			}

			return reSqlVersion;
		}
		return reSqlVersion;
	}

	private int addVersion(String code) {
		SqlVersion sqlVersion = new SqlVersion();
		sqlVersion.setId(SeqUtil.nextId().toString());
		sqlVersion.setCode(Integer.parseInt(code));
		sqlVersion.setCjsj(new Date());
		return sqlVersionMapper.insertSelective(sqlVersion);
	}

	private void executeSqlPath(String path, Statement stat) throws SQLException {
		InputStream inputStream = this.getClass().getResourceAsStream(path);
		try {
			String sqlScript = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			executeSql(sqlScript, stat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeSql(String sqlScript, Statement stat) throws SQLException {
		if (StringUtils.isNotEmpty(sqlScript)) {
			int index = sqlScript.indexOf("DROP FUNCTION IF EXISTS");
			List<String> sqls = new ArrayList<String>();

			if (index > -1) {
				sqls = new ArrayList<String>(Arrays.asList(sqlScript.substring(0, index).split(";[\r\n]+")));
				String funtions = sqlScript.substring(index, sqlScript.length());
				String[] lineArry = funtions.split("[\r\n]+");

				String sql = "";
				for (String string : lineArry) {
					if (string.indexOf("DELIMITER") > -1) {
						sqls.add(sql);
						sql = "";
					} else {
						sql += string + " ";
					}
				}

			} else {
				sqls = Arrays.asList(sqlScript.split(";[\r\n]+"));
			}

			for (String sql : sqls) {
				if (StringUtils.isNotEmpty(sql)) {
					stat.executeUpdate(sql);
				}
			}
		}
	}

	private String[] getSqlFileNames() {
		try {
			//开发模式
			File dbFile = ResourceUtils.getFile("classpath:db");
			// 升级
			File updateDir = new File(dbFile, "update");
			return updateDir.list();
		} catch (FileNotFoundException e2) {
			try {
				//jar包的方式
				URL url = Main.class.getClassLoader().getResource("db/");
				String jarPath = url.toString().substring(0, url.toString().indexOf("!/") + 2);

				URL jarURL;
				jarURL = new URL(jarPath);
				JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
				JarFile jarFile = jarCon.getJarFile();
				Enumeration<JarEntry> jarEntrys = jarFile.entries();
				List<String> names = new ArrayList<String>();
				while (jarEntrys.hasMoreElements()) {
					JarEntry entry = jarEntrys.nextElement();
					String path = entry.getName();
					if (path.contains("update")) {
						String name = path.substring(path.lastIndexOf("/")+1);
						if (StringUtils.isNotEmpty(name)) {
							names.add(name);
						}
					}
				}
				return names.toArray(new String[names.size()]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Author: ztl
	 * date: 2021-09-07
	 * @description: 新增数据域时初始化数据
	 */
	public void initDomain(String domainId){

		LOGGER.info("======================数据域初始化数据开始======================");
		try {
			//先将url分成两段，防止传递参数有/对解析有影响
			String mysqlUrl = URL.split("\\?")[0];
			String mysqlUrlArgs = URL.split("\\?")[1];
			String urlBase = mysqlUrl.substring(0, mysqlUrl.lastIndexOf("/")) + "?" + mysqlUrlArgs;
			String database = mysqlUrl.substring(mysqlUrl.lastIndexOf("/") + 1);

			try {
				excute(urlBase, database,domainId);
			}catch (Exception e) {
				LOGGER.error("数据域初始化数据执行失败！",e);
			}

		} catch (Exception e) {
			LOGGER.error("数据域初始化数据时异常：",e);
		}


		LOGGER.info("======================数据域初始化数据结束======================");

	}
	/**
	 * @Author: ztl
	 * date: 2021-09-07
	 * @description: 实际操作数据域相关数据初始化
	 */
	private void excute(String urlBase,String database,String domianId){
		Connection conn = null;
		Statement stat = null;
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e1) {
			LOGGER.error("数据域初始化数据异常excute，e1",e1);
			return;
		}

		try {
			conn = DriverManager.getConnection(URL, username, password);
			stat = conn.createStatement();
		} catch (Exception e) {
			try {
				conn = DriverManager.getConnection(urlBase, username, password);
				stat = conn.createStatement();
				// 创建数据库
				stat.executeUpdate("CREATE DATABASE " + database +" DEFAULT CHARACTER SET  utf8 COLLATE  utf8_general_ci");
				stat.close();
				conn.close();
				conn = DriverManager.getConnection(URL, username, password);
				stat = conn.createStatement();
			}catch (Exception e1){
				LOGGER.error("数据域初始化数据异常excute，e1",e1);
				return;
			}
		}

		boolean isInit = true;
		try {
			isInit = checkDomainId(domianId);
		} catch (Exception e) {
			// 如果找不到版本管理表，则认为没有加入过本系统的所有表，执行初始化操作
			try {
				stat.executeUpdate(sqlDomainInit);
			} catch (SQLException e1) {
				// 若没有版本表，切建立失败则退出
				e1.printStackTrace();
				return;
			}
		}
		try {
			if (isInit) {
				return;
			}
			// 升级
			String[] updateFileNames = getDomainSqlFileNames();
			if (updateFileNames != null && updateFileNames.length > 0) {
				Arrays.sort(updateFileNames);
				for (String updateFileName : updateFileNames) {
					executeSqlPath("/db/domain/"+updateFileName, stat,domianId);
					sqlVersionMapper.insertDomain(SeqUtil.getId(),domianId);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("数据域初始化数据异常excute，SQLException",e);
		} catch (Exception e) {
			LOGGER.error("数据域初始化数据异常excute，SQLException",e);
		}

	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: 校验当前数据域是否初始化相应数据
	 */
	private boolean checkDomainId(String domainId) {
		return sqlVersionMapper.checkDomainId(domainId);
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: 获取数据域相关数据初始化文件
	 */
	private String[] getDomainSqlFileNames() {
		try {
			//开发模式
			File dbFile = ResourceUtils.getFile("classpath:db");
			// 升级
			File updateDir = new File(dbFile, "domain");
			return updateDir.list();
		} catch (FileNotFoundException e2) {
			try {
				//jar包的方式
				URL url = Main.class.getClassLoader().getResource("db/");
				String jarPath = url.toString().substring(0, url.toString().indexOf("!/") + 2);

				URL jarURL;
				jarURL = new URL(jarPath);
				JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
				JarFile jarFile = jarCon.getJarFile();
				Enumeration<JarEntry> jarEntrys = jarFile.entries();
				List<String> names = new ArrayList<String>();
				while (jarEntrys.hasMoreElements()) {
					JarEntry entry = jarEntrys.nextElement();
					String path = entry.getName();
					if (path.contains("domain")) {
						String name = path.substring(path.lastIndexOf("/")+1);
						if (StringUtils.isNotEmpty(name)) {
							names.add(name);
						}
					}
				}
				return names.toArray(new String[names.size()]);
			} catch (Exception e) {
				LOGGER.error("数据域初始化数据异常getDomainSqlFileNames",e);
			}
		}
		return null;
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: 数据域初始化数据
	 */
	private void executeSqlPath(String path, Statement stat,String domainId) throws SQLException {
		InputStream inputStream = this.getClass().getResourceAsStream(path);
		try {
			String sqlScript = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			executeSql(sqlScript, stat,domainId);
		} catch (IOException e) {
			LOGGER.error("数据域初始化数据异常executeSqlPath",e);
		}
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: 数据域初始化数据数据库操作
	 */
	private void executeSql(String sqlScript, Statement stat,String domainId) throws SQLException {
		if (StringUtils.isNotEmpty(sqlScript)) {
			Map values = new HashMap();
			values.put("domainId", domainId);

			int index = sqlScript.indexOf("DROP FUNCTION IF EXISTS");
			List<String> sqls = new ArrayList<String>();

			if (index > -1) {
				sqls = new ArrayList<String>(Arrays.asList(sqlScript.substring(0, index).split(";[\r\n]+")));
				String funtions = sqlScript.substring(index, sqlScript.length());
				String[] lineArry = funtions.split("[\r\n]+");

				String sql = "";
				for (String string : lineArry) {
					if (string.indexOf("DELIMITER") > -1) {
						sqls.add(sql);
						sql = "";
					} else {

						sql += string + " ";
					}
				}

			} else {
				sqls = Arrays.asList(sqlScript.split(";[\r\n]+"));
			}
			List<String> updateField = new ArrayList<>();
			for (String sql : sqls) {
				values.put("id", SeqUtil.getId());
				StrSubstitutor sub = new StrSubstitutor(values,"${","}");
				if (StringUtils.isNotEmpty(sql)) {
					if (sql.contains("iom_emv_event_model")&& ((sql.contains("event_level") && !sql.contains("old_event_level"))||sql.contains("last_time")||(sql.contains("ci_name")&& !sql.contains("source_ci_name")))){
						updateField.add(String.valueOf(values.get("id")));
					}
					stat.executeUpdate(sub.replace(sql));
				}
			}
			if (updateField!=null && updateField.size()>0){
				String updateFiledSal ="UPDATE `iomemv`.`iom_emv_event_rule` set action= REPLACE(action,'101,4,7','"+ Joiner.on(",").join(updateField) +"') where RULE_NAME='默认压缩规则' AND DOMAIN_ID ="+domainId+";";
				stat.executeUpdate(updateFiledSal);
			}
		}
	}
}
