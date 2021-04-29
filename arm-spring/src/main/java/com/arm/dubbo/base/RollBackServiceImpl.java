package com.arm.dubbo.base;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RollBackServiceImpl implements RollBackService {
    static String sql = "insert into tx_user(username , age) VALUES (?, ?);";


    private JdbcTemplate jdbcTemplate;

    private RollBackMiddleService rollBackMiddleService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save() {
        jdbcTemplate.update(sql, new Object[]{"lisi2", "2"}); // 测试该事务是否会被回滚
        rollBackMiddleService.remoteGoods();
    }

    public void setRollBackMiddleService(RollBackMiddleServiceImpl rollBackMiddleService) {
        this.rollBackMiddleService = rollBackMiddleService;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
