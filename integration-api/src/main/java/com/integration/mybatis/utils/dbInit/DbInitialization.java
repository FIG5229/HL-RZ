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

	private static final String sqlDomainInit = "CREATE TABLE `iom_camp_domain_init` (`id` DECIMAL (20, 0) NOT NULL,`domainId` varchar(25) comment '?????????',`cjsj` datetime comment '????????????',PRIMARY KEY (`id`)) ENGINE = INNODB DEFAULT CHARSET = utf8;";

	public void initialize() {
		LOGGER.info("======================DbInitialization start======================");
		LOGGER.info("======================"+URL+"======================");
		try {
			//??????url????????????????????????????????????/??????????????????
			String mysqlUrl = URL.split("\\?")[0];
			String mysqlUrlArgs = URL.split("\\?")[1];
			String urlBase = mysqlUrl.substring(0, mysqlUrl.lastIndexOf("/")) + "?" + mysqlUrlArgs;
			String database = mysqlUrl.substring(mysqlUrl.lastIndexOf("/") + 1);

			//??????????????????????????????????????????
			String initKey = "initialize-"+ database;
			while (RedisUtils.exists(initKey)) {
				LOGGER.info("???????????????????????????????????????????????????"+database+"??????30???");
				try {
					Thread.sleep(1000*30L);
				} catch (InterruptedException e) {
					LOGGER.error("Thread.sleep???????????????");
				}
			}
			//??????
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
				LOGGER.error("doinit???????????????");
			}
			//?????????,????????????
			if (RedisUtils.exists(initKey)){
				RedisUtils.remove(initKey);
			}

		} catch (Exception e) {
			LOGGER.error("??????????????????????????????",e);
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
			// ???????????????root?????????????????????
			try {
				conn = DriverManager.getConnection(urlBase, username, "");
				// ?????????????????????, ???????????????????????????????????????????????????
			} catch (SQLException e) {
				// ?????????????????????????????? ????????????????????????
				LOGGER.error("Database has initialized password and database successfully!");
			}
			if (conn != null) {
				stat = conn.createStatement();
				// ????????????
				stat.executeUpdate(
						"UPDATE mysql.user SET password=PASSWORD('" + password + "') WHERE user='" + username + "'");
				stat.executeUpdate("FLUSH PRIVILEGES");
				// ???????????????
				stat.executeUpdate("CREATE DATABASE " + database);
				stat.close();
				conn.close();
			}
		} catch (Exception e) {
			// ?????????????????????????????????
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
				// ???????????????
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
			// ??????????????????????????????????????????????????????????????????????????????????????????????????????
			try {
				stat.executeUpdate(sqVersionInit);
				executeSqlPath("/db/"+"install.sql", stat);
			} catch (SQLException e1) {
				// ?????????????????????????????????????????????
				e1.printStackTrace();
				return;
			}
		}

		try {
			// ??????????????????

			if (version == null) {
				version = new SqlVersion();
				version.setCode(-1);
			}

			// ??????
			String[] updateFileNames = getSqlFileNames();
			if (updateFileNames != null && updateFileNames.length > 0) {
				Arrays.sort(updateFileNames);
				for (String updateFileName : updateFileNames) {
					Integer updateVersion = Integer.parseInt(updateFileName.substring(0, updateFileName.indexOf(".")));
					if (updateVersion > version.getCode()) {
						LOGGER.info("?????????????????????" + updateVersion);
						executeSqlPath("/db/update/"+updateFileName, stat);
						addVersion(updateVersion.toString());
					}
				}
			}
		} catch (SQLException e) {
			LOGGER.error("??????sql??????????????????????????????????????????????????????", version);
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
			//????????????
			File dbFile = ResourceUtils.getFile("classpath:db");
			// ??????
			File updateDir = new File(dbFile, "update");
			return updateDir.list();
		} catch (FileNotFoundException e2) {
			try {
				//jar????????????
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
	 * @description: ?????????????????????????????????
	 */
	public void initDomain(String domainId){

		LOGGER.info("======================??????????????????????????????======================");
		try {
			//??????url????????????????????????????????????/??????????????????
			String mysqlUrl = URL.split("\\?")[0];
			String mysqlUrlArgs = URL.split("\\?")[1];
			String urlBase = mysqlUrl.substring(0, mysqlUrl.lastIndexOf("/")) + "?" + mysqlUrlArgs;
			String database = mysqlUrl.substring(mysqlUrl.lastIndexOf("/") + 1);

			try {
				excute(urlBase, database,domainId);
			}catch (Exception e) {
				LOGGER.error("???????????????????????????????????????",e);
			}

		} catch (Exception e) {
			LOGGER.error("????????????????????????????????????",e);
		}


		LOGGER.info("======================??????????????????????????????======================");

	}
	/**
	 * @Author: ztl
	 * date: 2021-09-07
	 * @description: ??????????????????????????????????????????
	 */
	private void excute(String urlBase,String database,String domianId){
		Connection conn = null;
		Statement stat = null;
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e1) {
			LOGGER.error("??????????????????????????????excute???e1",e1);
			return;
		}

		try {
			conn = DriverManager.getConnection(URL, username, password);
			stat = conn.createStatement();
		} catch (Exception e) {
			try {
				conn = DriverManager.getConnection(urlBase, username, password);
				stat = conn.createStatement();
				// ???????????????
				stat.executeUpdate("CREATE DATABASE " + database +" DEFAULT CHARACTER SET  utf8 COLLATE  utf8_general_ci");
				stat.close();
				conn.close();
				conn = DriverManager.getConnection(URL, username, password);
				stat = conn.createStatement();
			}catch (Exception e1){
				LOGGER.error("??????????????????????????????excute???e1",e1);
				return;
			}
		}

		boolean isInit = true;
		try {
			isInit = checkDomainId(domianId);
		} catch (Exception e) {
			// ??????????????????????????????????????????????????????????????????????????????????????????????????????
			try {
				stat.executeUpdate(sqlDomainInit);
			} catch (SQLException e1) {
				// ?????????????????????????????????????????????
				e1.printStackTrace();
				return;
			}
		}
		try {
			if (isInit) {
				return;
			}
			// ??????
			String[] updateFileNames = getDomainSqlFileNames();
			if (updateFileNames != null && updateFileNames.length > 0) {
				Arrays.sort(updateFileNames);
				for (String updateFileName : updateFileNames) {
					executeSqlPath("/db/domain/"+updateFileName, stat,domianId);
					sqlVersionMapper.insertDomain(SeqUtil.getId(),domianId);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("??????????????????????????????excute???SQLException",e);
		} catch (Exception e) {
			LOGGER.error("??????????????????????????????excute???SQLException",e);
		}

	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: ????????????????????????????????????????????????
	 */
	private boolean checkDomainId(String domainId) {
		return sqlVersionMapper.checkDomainId(domainId);
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: ??????????????????????????????????????????
	 */
	private String[] getDomainSqlFileNames() {
		try {
			//????????????
			File dbFile = ResourceUtils.getFile("classpath:db");
			// ??????
			File updateDir = new File(dbFile, "domain");
			return updateDir.list();
		} catch (FileNotFoundException e2) {
			try {
				//jar????????????
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
				LOGGER.error("??????????????????????????????getDomainSqlFileNames",e);
			}
		}
		return null;
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: ????????????????????????
	 */
	private void executeSqlPath(String path, Statement stat,String domainId) throws SQLException {
		InputStream inputStream = this.getClass().getResourceAsStream(path);
		try {
			String sqlScript = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			executeSql(sqlScript, stat,domainId);
		} catch (IOException e) {
			LOGGER.error("??????????????????????????????executeSqlPath",e);
		}
	}
	/**
	 * @Author: ztl
	 * date: 2021-09-08
	 * @description: ???????????????????????????????????????
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
				String updateFiledSal ="UPDATE `iomemv`.`iom_emv_event_rule` set action= REPLACE(action,'101,4,7','"+ Joiner.on(",").join(updateField) +"') where RULE_NAME='??????????????????' AND DOMAIN_ID ="+domainId+";";
				stat.executeUpdate(updateFiledSal);
			}
		}
	}
}
