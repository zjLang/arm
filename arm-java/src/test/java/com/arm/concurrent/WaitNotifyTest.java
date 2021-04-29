package com.arm.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhaolangjing
 * @since 2021-3-11 21:12
 */
@Slf4j
public class WaitNotifyTest {
    static final Object lock = new Object();

    public static void main(String[] args)

    throws InterruptedException {
        new Thread( () -> {
            log.debug( "do something" );
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug( "do end something" );
        }, "t1" ).start();

        new Thread( () -> {
            log.debug( "do something" );
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug( "do end something" );
        }, "t2" ).start();

        Thread.sleep( 2000 );
        synchronized (lock) {
            //lock.notify();  // 换醒某一个线程，另外一个线程将永远处于等待的状态
            lock.notifyAll();  // 唤醒所有线程，线程竞争锁，然后得到锁的线程执行， 两个线程都会得到执行
        }
    }
}
