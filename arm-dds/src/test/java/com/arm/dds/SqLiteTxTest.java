package com.arm.dds;

import com.arm.dds.core.DynamicDataSourceException;
import com.arm.scene.TransactionTestService;
import com.arm.sqlite.DbManager;
import com.arm.sqlite.DbTemplateService;
import com.arm.sqlite.DbTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 动态数据源下的sqLite事务测试
 */
@Slf4j
public class SqLiteTxTest {
    static ClassPathXmlApplicationContext context;
    static DbTemplateService dbService;
    static TransactionTestService transactionTestService;
    public static final String path = "E:\\\\2.资料\\\\2.公司资料\\\\1.在建项目\\\\15.国资委数据上报\\\\test文件\\\\toFile\\\\115100007650616494_0037_1002_20210226154242\\\\115100007650616494_0037_1002_20210226154242.db";

    static {
        context = new ClassPathXmlApplicationContext("classpath:spring-bean.xml");
        dbService = context.getBean(DbManager.class).getDbService();
        transactionTestService = context.getBean(TransactionTestService.class);
    }


    @Test
    public void Test1() throws DynamicDataSourceException {
        dbService.update(path, "insert into hadwn_rest_gathr_tab_001 (CORP_NM) VALUES ('test1')");
        throw new NullPointerException("test");
    }

    @Test
    public void Test2() throws DynamicDataSourceException {
        transactionTestService.insertAll();
    }

    @Test
    public void Test3() throws DynamicDataSourceException {
        DbTransaction dbTransaction = context.getBean(DbManager.class).getDbTransaction();
        DbTemplateService dbService1 = dbTransaction.execute(path, jdbcTemplate -> {
            jdbcTemplate.update("insert into hadwn_rest_gathr_tab_001 (CORP_NM) VALUES ('test1')");
            jdbcTemplate.update("insert into hadwn_rest_gathr_tab_001 (CORP_NM) VALUES ('test2')");
            // do other
            throw new NullPointerException("test");
            //return "aaaaa";
        });


    }
}
