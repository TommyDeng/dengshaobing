package com.tom.dengshaobing.config;

import javax.sql.DataSource;

import org.h2.Driver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午7:53:58
 *
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
//@EnableAsync
//@EnableScheduling
@PropertySource("classpath:system.properties")
public class TestConfig {

	@Bean
	public DataSource getDataSource() {
		SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
		simpleDriverDataSource.setDriverClass(Driver.class);
		simpleDriverDataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
		simpleDriverDataSource.setUsername("sa");
		simpleDriverDataSource.setPassword("sa");
		return simpleDriverDataSource;
	}

	@Bean
	NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(getDataSource());
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(getDataSource());
		return dataSourceTransactionManager;
	}

//	@Bean
//	public TaskScheduler taskScheduler() {
//		return new ThreadPoolTaskScheduler();
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(TestConfig.class, args);
	}
}
