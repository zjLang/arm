package com.arm.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaolangjing
 * @since 2021-4-26 22:15
 * try{int a = 1 / 0;}catch (Exception e){};
 */
@Service
public class TestService2Impl implements TestService2 {

    static String slq = "insert into tx_user(username , age) VALUES (?, ?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test2() {
        jdbcTemplate.update(slq, new Object[]{"lisi2", "2"});  // sql2
    }


    @Transactional(propagation = Propagation.NESTED)
    @Override
    public void test3() {
        jdbcTemplate.update(slq, new Object[]{"lisi3", "3"});  // sql3

    }
}
