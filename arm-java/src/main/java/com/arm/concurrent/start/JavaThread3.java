package com.arm.concurrent.start;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author zhaolangjing
 * @since 2021-3-8 15:14
 */
public class JavaThread3 implements Callable<Integer> {
    private static final Integer VALUE = 0;
    @Override
    public Integer call() {
        System.out.println(Thread.currentThread().getName() + ":JavaThread3 implement Callable ");
        return VALUE + 1;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new JavaThread3());
        new Thread(futureTask, "有返回值的线程").start();
        System.out.println(Thread.currentThread().getName() + futureTask.get());
    }
}
