package com.arm.scene;

import com.arm.dds.core.DynamicDataSourceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务测试接口
 * 写一个
 */
public interface TransactionTestService {
    /**
     * 执行 insertMysql（） 和 insertSqLite（）；
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void insertAll() throws DynamicDataSourceException;

    /**
     * 插入数据到mysql库中
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void insertMysql();

    /**
     * 插入数据到sqLite中
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void insertSqLite() throws DynamicDataSourceException;
}
