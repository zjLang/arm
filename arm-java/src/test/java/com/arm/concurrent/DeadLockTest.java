package com.arm.concurrent;

import com.arm.ArmUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 死锁现象测试
 * 条件 :
 * A 线程获得了a锁，想获取b锁
 * B 线程获得了b锁，想获取a锁
 *
 * @author zhaolangjing
 * @since 2021-3-12 23:21
 */
@Slf4j
public class DeadLockTest {
    public static void main(String[] args) {
        deadTest();
    }

    public static void deadTest() {
        Object a = new Object();
        Object b = new Object();
        new Thread( () -> {
            synchronized (a) {
                log.info( "t1 get lock a" );
                ArmUtil.sleep( 1 );
                synchronized (b) {
                    log.info( "t1 get lock b" );
                }
            }
        }, "t1" ).start();
        new Thread( () -> {
            synchronized (b) {
                log.info( "t2 get lock b" );
                ArmUtil.sleep( 1 );
                synchronized (a) {
                    log.info( "t2 get lock a" );
                }
            }
        }, "t2" ).start();
    }
}
