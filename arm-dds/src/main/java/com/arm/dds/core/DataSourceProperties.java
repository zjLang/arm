package com.arm.dds.core;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class DataSourceProperties implements Serializable {

    /**
     * 数据库连接各属性的默认值
     */
    public static final int DEFAULT_INITIAL_POOL_SIZE = 5;
    public static final int DEFAULT_MIN_POOL_SIZE = 5;
    public static final int DEFAULT_MAX_POOL_SIZE = 500;
    public static final int DEFAULT_MAX_IDLE_TIME = 600;
    public static final int DEFAULT_ACQUIRE_INCREMENT = 5;
    public static final int DEFAULT_CHECKOUT_TIMEOUT = 60000;

    /**
     * 数据源key
     */
    private String key;
    /**
     * 数据源驱动类
     */
    private String driver;
    /**
     * 数据源连接URL
     */
    private String url;

    /**
     * 数据源用户名
     */
    private String username;
    /**
     * 数据源密码
     */
    private String password;

    /**
     * 数据源初始连接数
     */
    private Integer initialPoolSize;
    /**
     * 数据源最小连接数
     */
    private Integer minPoolSize;
    /**
     * 数据源最大连接数
     */
    private Integer maxPoolSize;
    /**
     * 数据源最大等待时间
     */
    private Integer maxIdleTime;
    /**
     * 自增步长
     */
    //private Integer acquireIncrement;
    /**
     * 检查超时时间
     */
    //private Integer checkoutTimeout;


}
