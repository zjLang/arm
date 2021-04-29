package com.arm.concurrent;

import com.arm.concurrent.nike.*;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaolangjing
 * @since 2021-3-9 15:40
 */
public class NikeTest {
    /**
     * nike抢鞋测试
     */
    @Test
    public void robNikeShoesTest() throws InterruptedException {
        // 通过工厂创建100双鞋子
        ShoesFactory.createShoes( 100, "Nike DBreak-Type 女子运动鞋", "001", "749" );
        // 创建一个线程池。 添加1000个人去抢鞋 , 创建一个接收500人的队列
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor( 10, 20, 10l, TimeUnit.SECONDS,
                new CustomerTaskQueue( 500 ), new CustomerThreadFactory(), new RejectedSealPolicy() );
        for (int i = 0; i < 1000; i++) {
            // 执行抢鞋
            threadPoolExecutor.execute( new CustomerThread( "顾客" + i ) );
            // 没执行一个线程沉睡10ms在抢，后面可以改成随机数
            Thread.sleep( 10 );
        }
    }
}
