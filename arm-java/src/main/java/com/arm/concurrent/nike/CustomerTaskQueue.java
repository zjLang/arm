package com.arm.concurrent.nike;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author zhaolangjing
 * @since 2021-3-9 17:19
 */
public class CustomerTaskQueue<R extends Runnable> extends LinkedBlockingQueue {

    public CustomerTaskQueue(int capacity) {
        super( capacity );
    }
}
