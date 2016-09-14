package com.tom.dengshaobing.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午7:53:58
 *
 */
@Configuration
public class AppConfig {
	public static final String CASH_VALUE_WSDL = "http://10.142.142.36:9081/iCIS/services/WebServiceServer?wsdl";
	public static final String CASH_VALUE_METHOD = "submitData";

	//
	public static final int MONITOR_PULSE = 30000;

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

	@Bean
	public DataSource getDataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		// return dataSourceLookup.getDataSource("java:comp/env/jdbc/zbxDS");
		return dataSourceLookup.getDataSource("jdbc/zbxDS");
	}

	@Bean
	JdbcTemplate namedParameterJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}
	
	public static void main(String[] args) {
	}
}
