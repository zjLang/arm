package com.arm.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 两阶段终止模式，优雅的停止线程， 让线程执行完后事
 *
 * @author zhaolangjing
 * @since 2021-3-10 16:03
 */
@Slf4j
public class TwoPhaseTerminationTest {
    // 监控线程
    private Thread monitor;
    private int i;

    public void start() {
        monitor = new Thread( () -> {
            while (true) {
                // 监控当前线程状态
                Thread thread = Thread.currentThread();
                if (thread.isInterrupted()) {
                    //停止程序 , 打断线程的执行状态,thread将被执行
                    log.info( "thread {} wait to execute over()", thread.getName() );
                    over();
                    break;
                } else {
                    create();
                }
                try {
                    monitor.sleep( 500 ); // 模拟监控程序没2s执行一次监控
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt();
                }
            }
        } );
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }

    private void over() {
        log.debug( "正在收拾工具！" );
        log.debug( "工具收拾完毕！" );
    }

    private void create() {
        log.debug( "正在生产第{}个棒棒糖！", i++ );
    }

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTerminationTest twoPhaseTerminationTest = new TwoPhaseTerminationTest();
        twoPhaseTerminationTest.start();
        TimeUnit.SECONDS.sleep( 5 );
        twoPhaseTerminationTest.stop();
    }
}
