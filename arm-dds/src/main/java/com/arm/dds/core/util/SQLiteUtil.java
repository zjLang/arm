package com.arm.dds.core.util;

import com.arm.dds.core.core.DynamicDataSourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class SQLiteUtil {

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
            } catch (SQLException exception) {
                throw new DynamicDataSourceException("error create JdbcTemplate : " + url, exception);
            }
        }
        return jdbcTemplate;
    }

}
