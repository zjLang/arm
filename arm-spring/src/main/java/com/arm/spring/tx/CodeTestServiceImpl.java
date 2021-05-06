package com.arm.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class CodeTestServiceImpl implements CodeTestService {

    static String sql = "insert into tx_user(username , age) VALUES (?, ?);";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void test() {
        transactionTemplate.execute(t -> {
            return jdbcTemplate.update(sql, new Object[]{"lisi2", "2"});
        });

    }
}
