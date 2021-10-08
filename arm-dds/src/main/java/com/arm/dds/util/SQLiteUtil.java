package com.arm.dds.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.arm.dds.DataSourceSpringProvider;
import com.arm.dds.core.DynamicDataSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class SQLiteUtil {
    public static final String SQLITE_PREFIX = "jdbc:sqlite:";

    /**
     * 根据url，返回一个JdbcTemplate
     *
     * @param url
     * @return
     * @throws DynamicDataSourceException
     * @throws SQLException
     */
    public static JdbcTemplate getJdbcTemplate(String url) throws DynamicDataSourceException {
        DataSource dataSource = DataSourceSpringProvider.getDataSource(url);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object resource = TransactionSynchronizationManager.getResource(dataSource);
        if (resource == null) {
            try {
                Connection connection = dataSource.getConnection();
                ConnectionHolder holder = new ConnectionHolder(connection);
                TransactionSynchronizationManager.bindResource(dataSource, holder);
                log.info("get connection :{}", connection);
            } catch (SQLException exception) {
                throw new DynamicDataSourceException("error create JdbcTemplate : " + url, exception);
            }
        }
        return jdbcTemplate;
    }

    public static JdbcTemplate getJdbcTemplate(String url , int i) throws DynamicDataSourceException {
        DruidDataSource dataSource = (DruidDataSource)DataSourceSpringProvider.getDataSource(url);
        log.info("start : {} , {}" , i,dataSource.getClass().getName() + "@" + Integer.toHexString(dataSource.hashCode()));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object resource = TransactionSynchronizationManager.getResource(dataSource);
        if (resource == null) {
            try {
                Connection connection = dataSource.getConnection();
                ConnectionHolder holder = new ConnectionHolder(connection);
                TransactionSynchronizationManager.bindResource(dataSource, holder);
                log.info("get connection :{}", connection);
            } catch (SQLException exception) {
                throw new DynamicDataSourceException("error create JdbcTemplate : " + url, exception);
            }
        }
        return jdbcTemplate;
    }

}
