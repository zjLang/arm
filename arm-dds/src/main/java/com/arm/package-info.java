/**
 * dds: 提供动态数据源。该动态数据源根据不同的连接提供不同的数据源。但是未与事务结合
 * tx: 根据dds提供的数据源将其与spring-tx的事务结合在一起。
 * sqlite: 依据jdbcTemplate 提供了一个 SQLite操作器。
 * <p>
 * <p>
 * 测试的几点内容：
 * 1.在同一个请求中（一个线程中），事务管理器在这个线程使用的是同一个连接。
 * 2.dataSourceTransactionManager.setDataSource(druidDataSource);无法在方法中实现注入，只能使用aop，在事务切面之前设置值。
 */
package com.arm;
