package com.arm.dds;

import com.arm.dds.core.DynamicDataSourceException;
import com.arm.dds.util.SQLiteUtil;
import com.arm.util.ArmUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
public class PoolTest {

    //public static String basePath = "C:\\Users\\zhaolangjing\\Desktop\\testDb";
    public static String basePath = "/Users/zhaolangjing/资料/工作记录/国资委采集交换/115100007650616494_0031_1003_20210609095420_7fe7122a00924e8086a4486a192a2f1e";
    public static int maxDb = 8;
    public static int maxNum = 100;
    public static String INSERT_SQL = "insert into hadwn_rest_gathr_tab_001 ('CORP_NM') VALUES ('张三')  ";

    static ClassPathXmlApplicationContext context;
    //static DataSourceSpringProvider bean;


    static {
        context = new ClassPathXmlApplicationContext("classpath:spring-bean.xml");
    }


    @Test
    public void TestSingle() throws DynamicDataSourceException {
        // 数据处理 ， 删除db数据
        long l = System.currentTimeMillis();
        for (int i = 0; i < maxNum; i++) {
           // int random = ArmUtil.random(1, maxDb);
            insert(basePath + ".db");
        }
        log.info("执行时间：" + (System.currentTimeMillis() - l) + "ms");
    }

    @Test
    public void Test() throws DynamicDataSourceException {
        // 数据处理 ， 删除db数据
        deleteData();
        long l = System.currentTimeMillis();
        for (int i = 0; i < maxNum; i++) {
            int random = ArmUtil.random(1, maxDb);
            insert(basePath + "\\" + random + ".db");
        }
        log.info("执行时间：" + (System.currentTimeMillis() - l) + "ms");
    }

    /**
     * 每次黄健一个链接并存入数据
     */
    private void insert(String dbIndex) throws DynamicDataSourceException {
        log.info("往" + dbIndex + "里面插入数据。。。");
        JdbcTemplate jdbcTemplate = SQLiteUtil.getJdbcTemplate("jdbc:sqlite:" + dbIndex);
        jdbcTemplate.update(PoolTest.INSERT_SQL);
    }

    /**
     * new JdbcTemplate()的无法回收连接池
     * @param dbIndex
     * @throws DynamicDataSourceException
     */
    private void insert2(String dbIndex) throws DynamicDataSourceException {
        log.info("往" + dbIndex + "里面插入数据。。。");
        DataSource dataSource = DataSourceSpringProvider.getDataSource("jdbc:sqlite:" + dbIndex);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(PoolTest.INSERT_SQL);
    }


    public static void deleteData() {
        for (int i = 0; i < maxDb; i++) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.sqlite.JDBC");
            //dataSource.setUrl("jdbc:sqlite:" + basePath + "\\" + (i + 1) + ".db");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update("delete from hadwn_rest_gathr_tab_001 ");
        }
    }
}
