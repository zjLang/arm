package com.arm.concurrent.demo;

import com.arm.ArmUtil;

/**
 * 银行转账系统
 *
 * @author zhaolangjing
 * @since 2021-3-11 11:05
 */
public class BankAccount {
    public static void main(String[] args) throws InterruptedException {
        new BankAccount().transfer();
    }

    public void transfer() throws InterruptedException {
        // 创建两个顾客
        Customer customer = new Customer( 1000 );
        Customer customer1 = new Customer( 1000 );
        Thread t1 = new Thread( () -> {
            for (int i = 0; i < 1000; i++) {
                customer.transfer( customer1, ArmUtil.random( 50, 100 ) );
            }
        }, "t1" );
        Thread t2 = new Thread( () -> {
            for (int i = 0; i < 1000; i++) {
                customer1.transfer( customer, ArmUtil.random( 50, 100 ) );
            }
        }, "t2" );
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println( "总钱数：" + (customer.getMoney() + customer1.getMoney()) );
    }

    class Customer {
        /**
         * 顾客的钱
         */
        private int money;

        public Customer(int money) {
            this.money = money;
        }

        /**
         * 转账 ，当前人往目标转账
         *
         * @param target
         * @param money
         */
        public void transfer(Customer target, int money) {
            synchronized (Customer.class) {
                if (this.money >= money) {
                    this.money -= money; // 当前人减少这么多钱
                    target.money += money; // 目标人加钱
                }
            }
        }

        public int getMoney() {
            return money;
        }
    }


}
