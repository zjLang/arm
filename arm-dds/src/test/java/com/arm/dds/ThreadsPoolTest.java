package com.arm.dds;

import com.arm.dds.core.DynamicDataSourceException;
import com.arm.dds.util.SQLiteUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;

/**
 * 多线程插入数据
 * 2021/08/18 经研究发现，dds的动态数据源在使用 SQLiteUtil.getJdbcTemplate()执行sql之后，只是将当前线程绑定的链接池赋值为null；
 * 并未将链接归还给durid链接池，故坐该测试以解决问题。
 */
@Slf4j
public class ThreadsPoolTest {
    public static String basePath = "/Users/zhaolangjing/资料/工作记录/国资委采集交换/115100007650616494_0031_1003_20210609095420_7fe7122a00924e8086a4486a192a2f1e.db";

    static ClassPathXmlApplicationContext context;
    //static DataSourceSpringProvider bean;


    static {
        context = new ClassPathXmlApplicationContext("classpath:spring-bean.xml");
    }

    @Test
    public void Test() throws DynamicDataSourceException {
        // 数据处理 ， 删除db数据
        long l = System.currentTimeMillis();
        /*for (int i = 0; i < 100; i++) {
            // int random = ArmUtil.random(1, maxDb);
            int finalI = i;
            Thread runnable = new Thread(() -> {
                try {
                    insert(basePath, finalI);
                } catch (DynamicDataSourceException e) {
                    e.printStackTrace();
                }
            });
            runnable.start();

        }*/
        insert(basePath, -1);
        log.info("执行时间：" + (System.currentTimeMillis() - l) + "ms");
    }

    private void insert(String dbIndex, int i) throws DynamicDataSourceException {
        JdbcTemplate jdbcTemplate = SQLiteUtil.getJdbcTemplate("jdbc:sqlite:" + dbIndex, i);
        Connection connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        log.info(connection.toString());
        jdbcTemplate.update(PoolTest.INSERT_SQL);
        connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        log.info(connection.toString());
        jdbcTemplate.update(PoolTest.INSERT_SQL);
        connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
        log.info(connection.toString());
    }


}
