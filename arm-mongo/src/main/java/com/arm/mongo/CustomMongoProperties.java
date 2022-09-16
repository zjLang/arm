package com.arm.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author z-ewa
 */
@ConfigurationProperties("mongo")
@Data
public class CustomMongoProperties {
    /**
     * ip地址
     */
    private String ip = "127.0.0.1";

    /**
     * 端口
     */
    private int port = 27017;

    /**
     * 数据库名称
     */
    private String dbName;
}
