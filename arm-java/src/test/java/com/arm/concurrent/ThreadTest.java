package com.arm.concurrent;

import com.arm.concurrent.start.JavaThread;
import com.arm.concurrent.start.JavaThread2;
import com.arm.concurrent.start.JavaThread3;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.*;

/**
 * 1. 多线程启动方式:
 * 1.继承 thread  2.实现 runnable
 *
 * @author zhaolangjing
 * @since 2021-3-8 15:02
 */
@Slf4j(topic = "c.test") //topic %t的代替，当前类的代替
public class ThreadTest {
    // 创建线程的四种方式
    @Test
    public void threadCreateTest() throws ExecutionException, InterruptedException {
        //  1.extends thread
        new JavaThread().start();
        //  2.implement runnable
        new Thread(new JavaThread2()).start();
        // 3.implement Callable
        // 创建FutureTask来运行实例
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(new JavaThread3());
        new Thread(integerFutureTask, "有返回值的线程").start();
        System.out.println(integerFutureTask.get()); //获取线程返回的结果值
        // 4.基于线程池来实现 用ThreadPoolExecutor创建线程池 说明：Executors返回的线程池对象的弊端如下：
        //  1） FixedThreadPool和SingleThreadPool： 允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
        //  2） CachedThreadPool： 允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。
    }


    @Test
    public void threadTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        /*Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
            int i = 1 / 0;
        });*/
        // 创建多个线程任务，并执行
       /* while (true){
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }*/
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
                //int j = 1 / 0;
            });
        }
    }

    /**
     * threadPoolExecutor 线程池创建方法实践
     */
    @Test
    public void threadPoolExecutorTest() {
        // 创建一个线程池 参数说明 1. 驻留线程10个 ， 最大存在线程100个 ， 空闲线程在线程池可驻留的时间  LinkedBlockingQueue 任务队列, 队列最多存储100个线程
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        while (true) {
            threadPoolExecutor.execute(() -> {
                // 打印日志， 观察是否启用了最大的100线程。
                System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @Test
    public void threadPoolExecutorTest2() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 3; i++) {
            scheduledExecutorService.schedule(() -> {
                // 打印日志， 程序启动10s后开始执行任务
                System.out.println(Thread.currentThread().getName() + " is running .." + Calendar.getInstance().getTime());
            }, 10, TimeUnit.SECONDS);
        }
        while (true) { }
    }

    /**
     * thread 方法测试
     */
    @Test
    public void threadMethodTest() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("execute run method : " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        log.info("execute getId method " + t1.getId());
        t1.start();
        t1.interrupt();
        //t1.join(); // 无限制等待
        t1.join(1000); // 带时限的等待
        log.debug("主线程（main）等待t1线程执行结束");
    }

    /**
     * java 线程几种状态的演示
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
        }, "t1"); // t1未执行start方法，所以处于new状态
        Thread t2 = new Thread(() -> {
            while (true) {
            } //  t2将一直运行，处于RUNNABLE状态
        }, "t2");
        t2.start();
        Thread t3 = new Thread(() -> {
            try {
                t2.join(); // 等待t2的执行，但是t2将一直处于运行状态，所以t3一直等待t2的返回
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");
        t3.start();
        Thread t4 = new Thread(() -> {
            synchronized (ThreadTest.class) {
                try {
                    Thread.sleep(5000); // t4拿到锁然后睡眠5s， 将处于time-waiting状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t4");
        t4.start();
        Thread t5 = new Thread(() -> {
            synchronized (ThreadTest.class) {
                //t5一直去竞争锁，但是锁被t4拿到了，所以t5将处于阻塞状态
            }
        }, "t5");
        t5.start();
        Thread t6 = new Thread(() -> {
        }, "t6");
        t6.start(); // t6正常执行完成，将处于完成状态
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());
    }


}
