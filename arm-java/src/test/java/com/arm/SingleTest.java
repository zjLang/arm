package com.arm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单例懒加载
 *
 * @author zhaolangjing
 * @since 2021-3-4 14:58
 */
public class SingleTest {


    private SingleTest() {
        try {
            System.out.println( "create SingleTest object" );
            Thread.sleep( 10 ); // 模拟大对象创建
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // volatile 可以防止指令重排序。
    public static volatile SingleTest s = null;

    public static SingleTest getInstance() {
        if (null == s) { // 节省加载时间，下次加载的时候先判断，就不需要再枷锁之后才进行判断 ,但是经过多线程的测试，发现区别不大 ;
            synchronized (SingleTest.class) {
                if (null == s) {
                    s = new SingleTest();
                }
            }
        }
        return s;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        System.out.println( "开始" );
        for (int i = 0; i < 10000; i++) {
            executorService.execute( new Runnable() {
                @Override
                public void run() {
                    getInstance();
                }
            } );
        }
        executorService.shutdown();
        executorService.awaitTermination( 1, TimeUnit.HOURS );
        System.out.println( System.currentTimeMillis() - start + "ms" );
    }
}
