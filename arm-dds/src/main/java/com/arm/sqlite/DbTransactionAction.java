package com.arm.sqlite;

import org.springframework.jdbc.core.JdbcTemplate;

@FunctionalInterface
public interface DbTransactionAction<T> {

    T execute(JdbcTemplate jdbcTemplate);

}
