package com.arm.dds.core.core;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * durid实现线程池
 *
 * @author zhaolangjing
 */
@Slf4j
public class DruidDynamicDataSource extends AbstractDynamicDataSource {

    @Override
    protected DataSource initDataSource(DataSourceProperties properties) {
        // 使用durid
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getDriver());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setInitialSize(properties.getInitialPoolSize());
        dataSource.setMinIdle(properties.getMinPoolSize());
        dataSource.setMaxActive(properties.getMaxPoolSize());
        dataSource.setMaxWait(properties.getMaxIdleTime());
        //dataSource.setQueryTimeout(properties.getCheckoutTimeout());
        return dataSource;
    }

    @Override
    protected boolean closeDataSource(DataSource dataSource) {
        DruidDataSource source = (DruidDataSource) dataSource;
        source.close();
        return true;
    }

    @Override
    protected boolean closeAllDataSource() {
        dataSourceMap.forEach((k, v) -> {
            DruidDataSource source = (DruidDataSource) v;
            source.close();
        });
        return true;
    }

    @Override
    protected int tryReclaimDataSource() {
        final int[] reclaimNums = {0};
        dataSourceMap.forEach((k, v) -> {
            DruidDataSource source = (DruidDataSource) v;
            if (source.getActiveCount() == 0) {
                source.close();
                dataSourceMap.remove(k);
                reclaimNums[0]++;
            }
        });
        return reclaimNums[0];
    }

}
