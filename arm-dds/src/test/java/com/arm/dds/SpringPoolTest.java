package com.arm.dds;

import com.alibaba.druid.pool.DruidDataSource;
import com.arm.dds.core.DynamicDataSourceException;
import com.arm.dds.util.SQLiteUtil;
import com.arm.scene.TransactionTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;

/**
 * 使用spring事物进行测试
 */
@Slf4j
public class SpringPoolTest {
    static ClassPathXmlApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring-bean.xml");
    }

    @Test
    public void Test() throws DynamicDataSourceException {
        TransactionTestService bean = context.getBean(TransactionTestService.class);
        DruidDataSource dataSource = context.getBean(DruidDataSource.class);
        log.info("1." + dataSource.getActiveCount());
        bean.insertSqLite();
        log.info("2." + dataSource.getActiveCount());
        // 数据处理 ， 删除db数据
        /*long l = System.currentTimeMillis();
        insert(basePath, -1);
        log.info("执行时间：" + (System.currentTimeMillis() - l) + "ms");*/
    }

    private void insert(String dbIndex, int i) throws DynamicDataSourceException {
        JdbcTemplate jdbcTemplate = SQLiteUtil.getJdbcTemplate("jdbc:sqlite:" + dbIndex, i);
        jdbcTemplate.update(PoolTest.INSERT_SQL);
    }
}
