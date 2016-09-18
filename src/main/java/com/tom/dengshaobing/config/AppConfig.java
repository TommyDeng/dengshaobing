package com.tom.dengshaobing.config;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午7:53:58
 *
 */
@Configuration
@ComponentScan(basePackages = "com.tom.dengshaobing")
@EnableTransactionManagement
public class AppConfig {

	// @Bean
	// public DataSource getDataSource() {
	// SimpleDriverDataSource simpleDriverDataSource = new
	// SimpleDriverDataSource();
	// simpleDriverDataSource.setDriverClass(OracleDriver.class);
	// simpleDriverDataSource.setUrl("jdbc:oracle:thin:@10.140.161.12:1521:orcl");
	// simpleDriverDataSource.setUsername("zbx_uat");
	// simpleDriverDataSource.setPassword("zbx_uat");
	// return simpleDriverDataSource;
	// }

//	@Bean
//	public DataSource getDataSource() {
//		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//		dataSourceLookup.setResourceRef(true);
//		return dataSourceLookup.getDataSource("jdbc/DengShaobingDS");
//	}
//
//	@Bean
//	NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
//		return new NamedParameterJdbcTemplate(getDataSource());
//	}
//
//	@Bean
//	public DataSourceTransactionManager transactionManager() {
//		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(getDataSource());
//		return dataSourceTransactionManager;
//	}

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
}
