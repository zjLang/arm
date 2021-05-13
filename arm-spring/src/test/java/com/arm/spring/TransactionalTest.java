package com.arm.spring;

import com.alibaba.druid.pool.DruidDataSource;
import com.arm.spring.tx.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhaolangjing
 * @since 2021-4-16 17:28
 */
@Slf4j
public class TransactionalTest {
    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-bean.xml");


    public static void main(String[] args) throws InterruptedException {
        //test1();
        test3();
        DruidDataSource dataSource = (DruidDataSource) context.getBean("dataSource");
        log.info(String.valueOf(dataSource.getActiveCount()));
        //test4();
    }

    /**
     * 测试事务连接占用的场景
     * jabc最大连接设置成： <property name="maxActive" value="1"/>
     *
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        TestService testService = context.getBean(TestService.class);
        new Thread(() -> testService.test6()).start();
        Thread.sleep(1000);
        new Thread(() -> testService.test7()).start();
    }


    /**
     * 方法间调用
     */
    private static void test3() {
        TestService testService = context.getBean(TestService.class);
        testService.test5();
    }

    /**
     * 类似于service的内部方法调用
     */
    private static void test2() {
        TestService testService = context.getBean(TestService.class);
        testService.test3(new Object[][]{{"lisi", 1}, {"lisi1", 2}});
    }

    /**
     * 类似于controller调用两个service
     */
    private static void test1() {
        TestService testService = context.getBean(TestService.class);
        testService.test1(new Object[][]{{"lisi", 1}, {"lisi1", 2}});
        testService.test2(new Object[]{"lisi1", 2});
    }
}
