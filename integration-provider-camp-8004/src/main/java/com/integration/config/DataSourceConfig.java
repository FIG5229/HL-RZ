package com.integration.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @ClassName DataSourceConfig
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/4/26 14:50
 * @Version 1.0
 **/
@Order(0)
@Configuration
public class DataSourceConfig {
    private Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    /**
     * 声明其为Bean实例
     */
    @Bean
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(datasourceUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        try {
            Class.forName(driverClassName);

            String url01 = datasourceUrl.substring(0,datasourceUrl.indexOf("?"));
            String param = datasourceUrl.substring(datasourceUrl.indexOf("?"),datasourceUrl.length());

            String url02 = url01.substring(0,url01.lastIndexOf("/"));

            String datasourceName = url01.substring(url01.lastIndexOf("/")+1);
            // 连接已经存在的数据库，如：mysql
            Connection connection = DriverManager.getConnection(url02+param, username, password);
            Statement statement = connection.createStatement();

            // 创建数据库
            statement.executeUpdate("create database if not exists `" + datasourceName + "` default character set utf8 COLLATE utf8_general_ci");

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return datasource;
    }
}
