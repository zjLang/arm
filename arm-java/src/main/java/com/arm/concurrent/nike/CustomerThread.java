package com.arm.concurrent.nike;

/**
 * 抢鞋子大军
 *
 * @author zhaolangjing
 * @since 2021-3-9 17:11
 */
public class CustomerThread implements Runnable {
    /**
     * 当前抢鞋子的顾客
     */
    private String customer;

    public CustomerThread(String customer) {
        this.customer = customer;
    }

    @Override
    public void run() {
        //System.out.println( Thread.currentThread().getName() + "[" + customer + "]" + "：正在抢鞋子" );
        Shoes shoes = ShoesFactory.getShoes();
        System.out.println( Thread.currentThread().getName() + "[" + customer + "]"
                + (shoes == null ? "未抢到鞋子" : "抢到了鞋子" + shoes.toString()) );
        try {
            //让该线程沉睡100ms， 因为主线程程序10ms，这边执行完了，那边才会创建一个新的线程，导致线程池总会是coreSize大小的线程数
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
