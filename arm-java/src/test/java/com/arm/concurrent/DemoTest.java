package com.arm.concurrent;

import com.arm.concurrent.demo.SellTicket;

/**
 * @author zhaolangjing
 * @since 2021-3-11 9:47
 */
public class DemoTest {

    public static void main(String[] args) {
        SellTicket sellTicket = new SellTicket();
        try {
            sellTicket.sellTicket( 3000, 10000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
