package com.arm.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 线程同步问题研究
 *
 * @author zhaolangjing
 * @since 2021-3-10 20:26
 */
@Slf4j
public class SyncThreadTest {
    private static int i = 0;
    // 对象锁
    private Object lock = new Object();

    /**
     * 错误同步演示
     * 用两个线程同时对一个初始值为0的分别执行500加+1 另一个执行-1的操作。
     * 期望目标是0到最后。但是实际结果却不是
     */
    @Test
    public void errorSyncTest() throws InterruptedException {
        Thread t1 = new Thread( () -> {
            for (int i1 = 0; i1 < 1000; i1++) {
                i++;
            }
        }, "t1" );
        Thread t2 = new Thread( () -> {
            for (int i1 = 0; i1 < 1000; i1++) {
                i--;
            }
        }, "t2" );
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug( "运行结果==>{}", i );
    }

    /**
     * 使用synchronized保证线程安全
     * @throws InterruptedException
     */
    @Test
    public void synchronizedTest() throws InterruptedException {
        Thread t1 = new Thread( () -> {
            for (int i1 = 0; i1 < 1000; i1++) {
                synchronized (lock) {
                    i++;
                }
            }
        }, "t1" );
        Thread t2 = new Thread( () -> {
            for (int i1 = 0; i1 < 1000; i1++) {
                synchronized (lock) {
                    i--;
                }
            }
        }, "t2" );
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug( "运行结果==>{}", i );
    }
}
