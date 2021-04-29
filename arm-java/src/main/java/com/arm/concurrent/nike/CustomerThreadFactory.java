package com.arm.concurrent.nike;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个线程的命名规则 ： pool-1-thread-9
 * 一个创建线程的工厂
 *
 * @author zhaolangjing
 * @since 2021-3-9 14:52
 */
public class CustomerThreadFactory implements ThreadFactory {
    private static final String POOL_PREFIX = "pool-";
    /**
     * 创建一个线程池的pool number 。 固定值，不用Integer的原因是因为
     * 如果在多线程情况下，同时创建多个线程，可能会多次初始化该值。
     */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger( 1 );

    private static final String THREAD_PREFIX = "-seal-thread-";

    private static AtomicInteger threadNumber = new AtomicInteger( 1 );

    private final ThreadGroup group;

    private final String namePrefix;

    private final boolean deamon;


    public CustomerThreadFactory() {
        this( false );
    }

    public CustomerThreadFactory(boolean deamon) {
        this.deamon = deamon;
        SecurityManager securityManager = System.getSecurityManager();
        group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = POOL_PREFIX + POOL_NUMBER + THREAD_PREFIX;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread( group, r, namePrefix + threadNumber.getAndIncrement(), 0 );
        thread.setDaemon( deamon );
        return thread;
    }
}
