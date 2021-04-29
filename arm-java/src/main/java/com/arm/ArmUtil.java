package com.arm;

import java.util.Random;

/**
 * @author zhaolangjing
 * @since 2021-3-11 11:13
 */
public class ArmUtil {
    private static Random random = new Random();

    public static int random(int min, int max) {
        return random.nextInt( max ) % (max - min + 1) + min;
    }

    public static void sleep(int second){
        try {
            Thread.sleep( second * 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
