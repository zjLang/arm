package com.arm.concurrent.juc;

import com.arm.ArmUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://www.cnblogs.com/erbing/p/10038855.html<br>
 * 1.synchronized 同步锁 ，可重入 。<br>
 * 2.volatile 变量可见性 。 volatile， 只能保证可见性，不能保证原子性。<br>
 * 3.wait()、notify/notifyAll()  在synchronized 代码块执行，说明当前线程一定是获取了锁的。wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写<br>
 * 4.AtomicXxx  原子操作<br>
 * 5.CountDownLatch 门闩  门闩相当于在一个门上加多个锁，当线程调用 await 方法时，会检查门闩数量，如果门闩数量大于 0，线程会阻塞等待。
 * 当线程调用 countDown 时，会递减门闩的数量，当门闩数量为 0 时，await 阻塞线程可执行 <br>
 * 6.CyclicBarrier 相较于 CountDownLatch 其可实现重复技术 <br>
 * 7.Semaphore 信号量 获取信号量的执行，执行完成之后可以释放，释放之后其他线程可以获取然后执行。 <br>
 * 8.ReentrantLock  重入锁，建议应用的同步方式。相对效率比 synchronized 高。量级较轻。使用重入锁， 必须手工释放锁标记。一般都是在 finally 代码块中定义释放锁标记的 unlock 方法。
 * 可以支持多个条件，synchronized 无法实现。<br>
 * 9.ThreadPool&Executor 线程池
 * >>> FixedThreadPool   (基础链表同步队列.活动状态和线程池容量是有上限的线程池。所有的线程池中，都有一个任务队列。使用的是 BlockingQueue<Runnable>作为任务的载体。当任务数量大于线程池容量的时候，没有运行的任务保存在任务队列中，当线程有空闲的，自动从队列中取出任务执行)
 * >>> CachedThreadPool   (缓存的线程池 .容量管理策略：如果线程池中的线程数量不满足任务执行，创建新的线程。每次有新任务无法即时处理的时候，都会创建新的线程。当线程池中的线程空闲时长达到一定的临界值（默认 60 秒），自动释放线程。默认线程空闲 60 秒，自动销毁)
 * >>> ScheduledThreadPool   (计划任务线程池。可以根据计划自动执行任务的线程池.)
 * >>> SingleThreadExecutor   (单一容量的线程池。使用场景： 保证任务顺序时使用。如： 游戏大厅中的公共频道聊天。秒杀)
 * 10.Queue
 * >>> ConcurrentLinkedQueue   (基础链表同步队列)
 * >>> LinkedBlockingQueue　　  (阻塞队列，队列容量不足自动阻塞，队列容量为 0 自动阻塞)
 * >>> ArrayBlockingQueue　　   (底层数组实现的有界队列)
 * >>> DelayQueue　　           (延时队列。根据比较机制，实现自定义处理顺序的队列。常用于定时任务)
 * >>> LinkedTransferQueue　　　(转移队列，使用 transfer 方法，实现数据的即时处理。没有消费者，就阻塞。)
 * >>> SynchronusQueue　　　　   (同步队列，是一个容量为 0 的队列。是一个特殊的 TransferQueue。必须现有消费线程等待，才能使用的队列)
 * 11.Map/Set  ConcurrentHashMap/ConcurrentHashSet   ConcurrentSkipListMap/ConcurrentSkipListSet(跳表)
 * 12. ReentrantReadWriteLock 读写锁  读锁可重入。
 * @author zhaolangjing
 * @since 2021-4-7 9:24
 */

public class JUC {
    public static void main(String[] args) {
        //1.volatile 不能保证原子性的测试
        //VolatileTest.run();

        //2.Atomic 原子性保证测试
        //AtomicTest.run();

        //3.CountDownLatch
        CountdownLatchTest.run();
    }

}

class VolatileTest {
    volatile int m = 0;

    public void m() {
        for (int i = 0; i < 10000; i++) {
            m++;
        }
    }

    public static void run() {
        final VolatileTest t = new VolatileTest();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add( new Thread( () -> t.m() ) );
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println( t.m );
    }
}

class AtomicTest {
    static AtomicInteger integer = new AtomicInteger();

    public void m() {
        for (int i = 0; i < 10000; i++) {
            integer.incrementAndGet();
        }
    }

    public static void run() {
        final AtomicTest t = new AtomicTest();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add( new Thread( () -> t.m() ) );
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println( integer.get() );
    }
}

@Slf4j
class CountdownLatchTest {
    // 启动线程数要和门闩数量一致
    static CountDownLatch latch = new CountDownLatch( 5 );

    public static void run() {
        for (int i = 0; i < 5; i++) {
            int j = i;
            new Thread( () -> {
                if (latch.getCount() != 0) {
                    log.info( Thread.currentThread().getName() + " get latch and do something..." );
                    ArmUtil.sleep( ArmUtil.random( 1, 5 ) );
                    log.info( Thread.currentThread().getName() + " end..." );
                    latch.countDown();
                }
            }, "thread-" + i ).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info( "等待门闩为0，执行逻辑..." );
    }
}
