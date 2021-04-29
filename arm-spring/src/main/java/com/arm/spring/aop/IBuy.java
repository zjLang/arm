package com.arm.spring.aop;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhaolangjing
 * @since 2021-3-15 17:13
 */
public interface IBuy {
    String buy(int price) throws IllegalArgumentException;
}
