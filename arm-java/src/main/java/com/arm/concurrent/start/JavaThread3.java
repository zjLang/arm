package com.arm.concurrent.start;

import java.util.concurrent.Callable;

/**
 * @author zhaolangjing
 * @since 2021-3-8 15:14
 */
public class JavaThread3 implements Callable {

    private static Integer value = 0;

    @Override
    public Integer call() throws Exception {
        System.out.println( "JavaThread3 implement Callable " );
        return value + 1;
    }
}
