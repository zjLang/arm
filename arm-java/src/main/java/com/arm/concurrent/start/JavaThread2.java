package com.arm.concurrent.start;

/**
 * @author zhaolangjing
 * @since 2021-3-8 15:05
 */
public class JavaThread2 implements Runnable {
    @Override
    public void run() {
        System.out.println("JavaThread2 implement runnable ");
    }
}
