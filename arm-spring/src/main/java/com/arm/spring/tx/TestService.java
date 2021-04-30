package com.arm.spring.tx;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaolangjing
 * @since 2021-4-16 17:22
 */
public interface TestService {
    void test1(Object[][] params);

    void test2(Object[] params);


    void test3(Object[][] params);

    void test4(Object[] params);

    void test5();

    /**
     * 模拟一个http远程调用
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void test6();
    @Transactional(propagation = Propagation.REQUIRED)
    void test7();
}
