package com.arm.dds.core;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class C3P0DynamicDataSource extends AbstractDynamicDataSource {
    @Override
    protected DataSource initDataSource(DataSourceProperties properties) {
        // 使用durid
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(properties.getDriver());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUser(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setInitialPoolSize(properties.getInitialPoolSize());
        dataSource.setMinPoolSize(properties.getMinPoolSize());
        dataSource.setMaxPoolSize(properties.getMaxPoolSize());
        dataSource.setMaxIdleTime(properties.getMaxIdleTime());
        //dataSource.setQueryTimeout(properties.getCheckoutTimeout());
        return dataSource;
    }

    @Override
    protected boolean closeDataSource(DataSource dataSource) {
        ComboPooledDataSource source = (ComboPooledDataSource) dataSource;
        source.close();
        return true;
    }

    @Override
    protected boolean closeAllDataSource() {
        dataSourceMap.forEach((k, v) -> {
            ComboPooledDataSource source = (ComboPooledDataSource) v;
            source.close();
        });
        return true;
    }

    @Override
    protected int tryReclaimDataSource() {
        final int[] reclaimNums = {0};
        dataSourceMap.forEach((k, v) -> {
            ComboPooledDataSource source = (ComboPooledDataSource) v;
            try {
                if (source.getThreadPoolNumActiveThreads() == 0) {
                    source.close();
                    dataSourceMap.remove(k);
                    reclaimNums[0]++;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
        return reclaimNums[0];
    }
}
