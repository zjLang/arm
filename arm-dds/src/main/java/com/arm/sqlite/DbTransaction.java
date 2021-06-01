package com.arm.sqlite;

import com.arm.dds.core.DynamicDataSourceException;
import org.springframework.transaction.TransactionException;

@FunctionalInterface
public interface DbTransaction {
    /**
     * 使用声明式事务完成 事务的管理
     * 注意： 1.该事务只能管理该方法作用域中的事务，至于外部事物无法控制，因为用的不是同一个连接。事务无法不在同一个连接中生效。
     * 2.如果要使外部事务因该事务的执行结果生效，可使用分布式事务。
     *
     * @param url
     * @param callback
     * @param <T>
     * @return
     * @throws TransactionException
     * @throws DynamicDataSourceException
     */
    <T> T execute(String url, DbTransactionAction callback) throws TransactionException, DynamicDataSourceException;
}
