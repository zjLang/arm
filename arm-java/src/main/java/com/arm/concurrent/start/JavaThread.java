package com.arm.concurrent.start;

/**
 * java 多线程
 *
 * @author zhaolangjing
 * @since 2021-3-8 14:59
 */
public class JavaThread extends Thread {

    public void run() {
        System.out.println( "JavaThread extends thread" );
    }
}
