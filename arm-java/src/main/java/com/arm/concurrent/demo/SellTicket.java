package com.arm.concurrent.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * 使用synchronized控制多线程售票情况
 *
 * @author zhaolangjing
 * @since 2021-3-11 9:14
 */
public class SellTicket {

    private static Random random = new Random();

    /**
     * 出售票务 , 看卖出票数是否和总票数相等
     *
     * @return
     */
    public int sellTicket(int customers, int tickets) throws InterruptedException {
        Ticket window = new Ticket( tickets );
        List<Integer> list = new Vector<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < customers; i++) {
            Thread thread = new Thread( () -> {
                int get = window.sealTicket( random( 0, 10 ) );
                list.add( get );
            } );
            thread.start();
            threads.add( thread );
        }
        // 等待所有线程的执行结果
        for (Thread thread : threads) {
            thread.join();
        }
        long count = list.stream().mapToInt( v -> v ).sum();
        System.out.println( "卖出的总票数：" + count + "----剩余票数：" + window.getTickets() );
        return (int) count;
    }

    public static int random(int min, int max) {
        return random.nextInt( max ) % (max - min + 1) + min;
    }


    class Ticket {

        public int getTickets() {
            return tickets;
        }

        private int tickets;

        public Ticket(int tickets) {
            this.tickets = tickets;
        }

        /**
         * 买票
         *
         * @param seal
         * @return
         */
        public synchronized int sealTicket(int seal) {
            if (tickets >= seal) {
                tickets = tickets - seal;
                return seal;
            }
            return 0;
        }
    }

    /**
     * 批量执行方法 ： 进入target的classes目录：
     * 执行 ：for /L %n in (1,1,10) do java -cp .; com.arm.concurrent.demo.SellTicket (.;是必须的)
     *
     * @param args
     */
    public static void main(String[] args) {
        SellTicket sellTicket = new SellTicket();
        try {
            sellTicket.sellTicket( 3000, 10000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
