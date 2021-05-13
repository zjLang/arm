package com.arm.dds;

import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.util.StringUtils;
import com.arm.dds.core.*;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * datasource 提供器
 */
class DataSourceProvider {

    private DynamicDataSource dynamicDataSource;

    DataSourceProvider(DataSourceTypeEnum type) {
        init(type);
    }

    private void init(DataSourceTypeEnum type) {
        // todo 应该可以获取获取源配置信息
        if (type == null || type.equals(DataSourceTypeEnum.druid)) {
            dynamicDataSource = new DruidDynamicDataSource();
        } else if (type.equals(DataSourceTypeEnum.c3p0)) {
            dynamicDataSource = new C3P0DynamicDataSource();
        }
    }

    public DynamicDataSource getDynamicDataSource() {
        return dynamicDataSource;
    }

    public DataSource getDataSource(String url) throws DynamicDataSourceException {
        return getDataSource(url, null, null);
    }

    public DataSource getDataSource(String url, String userName, String password) throws DynamicDataSourceException {
        if (dynamicDataSource.isClose()) {
            throw new DynamicDataSourceException("the dynamicDataSource is closed : " + url);
        }
        // 参数验证
        if (StringUtils.isEmpty(url)) {
            throw new DynamicDataSourceException("error param : " + url);
        }
        DataSource dataSource = dynamicDataSource.getDataSource(url);
        if (dataSource == null) {
            dataSource = dynamicDataSource.setDataSource(createProperties(url, userName, password,
                    null, null, null, null));
        }
        return dataSource;
    }

    public boolean close() {
        return dynamicDataSource.close();
    }


    private DataSourceProperties createProperties(String url, String userName, String password, Integer initialPoolSize,
                                                  Integer minPoolSize, Integer maxPoolSize, Integer maxIdleTime) throws DynamicDataSourceException {
        String driverClassName;
        try {
            driverClassName = JdbcUtils.getDriverClassName(url);
        } catch (SQLException e) {
            throw new DynamicDataSourceException("error param url while get  driverClassName : " + url);
        }
        if (initialPoolSize == null) {
            initialPoolSize = DataSourceProperties.DEFAULT_INITIAL_POOL_SIZE;
        }
        if (minPoolSize == null) {
            minPoolSize = DataSourceProperties.DEFAULT_MIN_POOL_SIZE;
        }
        if (maxPoolSize == null) {
            maxPoolSize = DataSourceProperties.DEFAULT_MAX_POOL_SIZE;
        }
        if (maxIdleTime == null) {
            maxIdleTime = DataSourceProperties.DEFAULT_MAX_IDLE_TIME;
        }
        DataSourceProperties properties = new DataSourceProperties();
        properties.setKey(url);
        properties.setUrl(url);
        properties.setDriver(driverClassName);
        properties.setUsername(userName);
        properties.setPassword(password);
        properties.setInitialPoolSize(initialPoolSize);
        properties.setMinPoolSize(minPoolSize);
        properties.setMaxPoolSize(maxPoolSize);
        properties.setMaxIdleTime(maxIdleTime);
        return properties;
    }
}
