package com.arm.spring.tx;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaolangjing
 * @since 2021-4-26 22:15
 */
public interface TestService2 {


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void test2();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void test3();


}
