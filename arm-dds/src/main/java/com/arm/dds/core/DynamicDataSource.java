package com.arm.dds.core;

import javax.sql.DataSource;

/**
 * 动态数据源接口定义
 * 管理定义的多个数据源接口
 *
 * @author zhaolangjing
 */
public interface DynamicDataSource {

    /**
     * 默认最大的可创建的dataSource的数据量
     */
    int DEFAULT_MAX_ACTIVE_SIZE = 16;

    /**
     * 资源满时触发的回收数据源操作次数
     */
    int DEFAULT_RECLAIM_TRIES_SIZE = 2;

    /**
     * 默认回收数据源线程沉睡时间 1分钟
     */
    long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60 * 1000L;


    /**
     * 获取数据源的类型  c3p0  durid...
     *
     * @param dataSourceKey 数据源的表述key
     * @return
     */
    //String getDataSourceType(String dataSourceKey);

    /**
     * 根据数据源的key获取数据源
     *
     * @param dataSourceKey 数据源的表述key
     * @return
     */
    DataSource getDataSource(String dataSourceKey);

    /**
     * @param properties
     * @return 返回当前注入好的数据源数据
     */
    DataSource setDataSource(DataSourceProperties properties) throws DynamicDataSourceException;

    /**
     * 关闭某数据源，当该数据源无法被正确关闭时，将抛出异常。 比如：有正在使用的连接
     *
     * @param dataSourceKey
     * @return
     * @throws DynamicDataSourceException
     */
    boolean closeDataSource(String dataSourceKey) throws DynamicDataSourceException;

    /**
     * 关闭销毁动态数据源
     *
     * @return
     */
    boolean close();

    /**
     * 数据源是否已经关闭了
     * @return
     */
    boolean isClose();
}
