package com.arm.code.mix.base;

/**
 * 基础测试
 *
 * @author z-ewa
 */
public class PublicTest {
    public String name;

    private int age;

    public String getName() {
        return name;
    }

    public static class GirlFriend {
        public String name;

        public String getName() {
            return name;
        }
    }

    static class Order {
        public String offer;

        public String getOffer() {
            return offer;
        }
    }


    public class Family {
        public int no;

        public int getNo() {
            name = "123";
            age = 100;
            return no;
        }

    }
}
