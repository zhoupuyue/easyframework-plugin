package org.easyframework.plugin.spring;

import java.sql.Connection;

import javax.sql.DataSource;

import org.easyframework.persistence.ConnectionFactory;
import org.easyframework.persistence.config.EasyPersistenceConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class SpringConnectionFactory implements ConnectionFactory, InitializingBean {

	/**
	 * 配置文件路径
	 */
	private String configPath;

	/**
	 * 数据源
	 */
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	public void closeConnection(Connection connection) {
		DataSourceUtils.releaseConnection(connection, dataSource);
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public void afterPropertiesSet() {
		new EasyPersistenceConfig(configPath);
	}

}
