package com.arm.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaolangjing
 * @since 2021-4-16 17:23
 * REQUIRED : 没有事务则创建一个事务。 事务包裹多个方法，任何方法异常都会回滚。
 * 如下，test1添加REQUIRED事务，test2不添加事务，则test2报错，事务被回滚。
 */
@Service
public class TestServiceImpl implements TestService {

    static String slq = "insert into tx_user(username , age) VALUES (?, ?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestService2 testService2;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED
    )
    public void test1(Object[][] params) {
        jdbcTemplate.update(slq, params[0]);  // sql1
    }

    @Transactional(propagation = Propagation.NESTED
    )
    public void test2(Object[] params) {
        jdbcTemplate.update(slq, params); // sql2
        //int a = 1 / 0; // 子方法抛出异常。
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY
    )
    public void test3(Object[][] params) {
        jdbcTemplate.update(slq, params[0]);  // sql1
        test4(params[1]);
    }

    @Transactional(propagation = Propagation.SUPPORTS

    )
    public void test4(Object[] params) {
        jdbcTemplate.update(slq, params); // sql2
        int a = 1 / 0; // 子方法抛出异常。
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED
    )
    public void test5() {
        jdbcTemplate.update(slq, new Object[]{"lisi1" , "1"});  // sql1
        testService2.test2(); // 调用rTest
        testService2.test3(); // 调用nTest
        int a = 1 / 0;
    }


}
