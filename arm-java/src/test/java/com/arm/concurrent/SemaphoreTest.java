package com.arm.concurrent;

import com.arm.util.ArmUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author zhaolangjing
 * @since 2021-3-22 11:57
 */
@Slf4j
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore( 5 );
        for (int i = 0; i < 10; i++) {
            int j = i;
            new Thread( () -> {
                try {
                    semaphore.acquire();
                    log.info( "thread " + j + "执行" );
                    ArmUtil.sleep( ArmUtil.random( 0 ,5 ) );
                    log.info( "thread " + j + "完毕" );
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } ).start();
        }
    }
}
