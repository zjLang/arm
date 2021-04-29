package com.arm.concurrent;

import com.arm.ArmUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 哲学家就餐问题
 *
 * @author zhaolangjing
 * @since 2021-3-13 9:00
 */
public class PhilosopherDealLockTest {

    public static void main(String[] args) {
        Chopstick chopstick1 = new Chopstick( "筷子1" );
        Chopstick chopstick2 = new Chopstick( "筷子2" );
        Chopstick chopstick3 = new Chopstick( "筷子3" );
        Chopstick chopstick4 = new Chopstick( "筷子4" );
        Chopstick chopstick5 = new Chopstick( "筷子5" );
        new Philosopher( "亚里士多德", chopstick1, chopstick2 ).start();
        new Philosopher( "柏拉图", chopstick2, chopstick3 ).start();
        new Philosopher( "阿基米德", chopstick3, chopstick4 ).start();
        new Philosopher( "牛顿", chopstick4, chopstick5 ).start();
        new Philosopher( "爱因斯坦", chopstick5, chopstick1 ).start();
    }
}

@Slf4j
class Philosopher extends Thread {
    private String name;
    // 左边筷子
    private Chopstick leftChopstick;
    // 右边筷子
    private Chopstick rightChopstick;

    @Override
    public void run() {
        while (true) {
            eating();
        }
    }

    public Philosopher(String name, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.name = name;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    public void eating() {
        synchronized (leftChopstick) {
            synchronized (rightChopstick) {
                log.info( "{}正在吃饭{}，{}", name, leftChopstick, rightChopstick );
                think();
            }
        }
    }

    // 思考一秒钟
    public void think() {
        ArmUtil.sleep( 1 );
    }
}


class Chopstick {
    private String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chopstick{" +
                "name='" + name + '\'' +
                '}';
    }
}
