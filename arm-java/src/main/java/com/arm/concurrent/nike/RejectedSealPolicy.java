package com.arm.concurrent.nike;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拒绝任务策略
 * 拒绝任务有两种情况：1. 线程池已经被关闭；2. 任务队列已满且maximumPoolSizes已满；
 * 默认策略：
 * AbortPolicy：默认测策略，抛出RejectedExecutionException运行时异常；
 * CallerRunsPolicy：这提供了一个简单的反馈控制机制，可以减慢提交新任务的速度；
 * DiscardPolicy：直接丢弃新提交的任务；
 * DiscardOldestPolicy：如果执行器没有关闭，队列头的任务将会被丢弃，然后执行器重新尝试执行任务（如果失败，则重复这一过程）；
 * 我们可以自己定义RejectedExecutionHandler，以适应特殊的容量和队列策略场景中
 * <p>
 *
 * @author zhaolangjing
 * @since 2021-3-9 16:17
 */
public final class RejectedSealPolicy implements RejectedExecutionHandler {


    public RejectedSealPolicy() {
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // executor 有5状态 。
        //  1.running 开始状态，可以接收数据（青楼接客）
        //  2.shutdown 关闭态，处理队列中的线程（青楼菇凉不够用啦，客满员了）
        //  3.stop 停止态，不接收任务，不处理任务，且正在处理的任务也停止了（青楼菇凉觉得妈妈钱给少了，罢工了）
        //  4.tidying 整理态 所有任务已经结束，workerCount = 0 ，将执行terminated()方法 （夜深了，菇凉们收拾休息了）
        //  5.terminated 结束态，terminated() 方法已完成  (青楼关门休息了)
        if (executor.isShutdown()) {
            System.out.println( String.format( "排队人数较多，请等待！" + Thread.currentThread().getName() ) );
        } else {
            System.out.println( String.format( "队列无法正常工作，可能正在关闭：" + Thread.currentThread().getName() ) );
        }
        // todo other dubbo对其进行扩展，打印了堆栈信息和事件通知。
    }
}
