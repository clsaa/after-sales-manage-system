package com.clsaa.ms.hermes.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


/**
 * Druid数据源配置类
 *
 * @author 任贵杰
 */
@SpringBootConfiguration
public class DruidDataSourceConfig {
	@Autowired
	private Environment environment;

	public DataSource createDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
		druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
		druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
		druidDataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
		return druidDataSource;
	}
}
