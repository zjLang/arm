package com.arm.dds;

import com.arm.util.ArmUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 没有数据源管理池的时候的测试
 * 操作sqlLite 数据库， 其中设计到切换数据库。
 * 8个数据库
 */
@Slf4j
public class NoPoolTest {

    /**
     * 其中有8个数据库
     * 模拟10000次请求往8个数据库中插入10000条数据。
     * 顺序执行
     */
    @Test
    public void Test() {
        // 数据处理 ， 删除db数据
        PoolTest.deleteData();
        long l = System.currentTimeMillis();
        for (int i = 0; i < PoolTest.maxNum; i++) {
            int random = ArmUtil.random(1, 8);
            insert(PoolTest.basePath + "\\" + random + ".db");
        }
        log.info("执行时间：" + (System.currentTimeMillis() - l) + "ms");
    }

    /**
     * 每次黄健一个链接并存入数据
     */
    private void insert(String dbIndex) {
        log.info("往" + dbIndex + "里面插入数据。。。");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:" + dbIndex);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(PoolTest.INSERT_SQL);
    }


}
