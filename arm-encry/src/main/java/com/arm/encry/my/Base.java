package com.arm.encry.my;

import org.junit.Test;

/**
 * 基本运算测试累
 */
public class Base {
    @Test
    public void mod448_512(){
        System.out.println(448%512);
    }

    @Test
    public void test(){
        // 0x2000000000000000l 转成10进制的值
        System.out.println(new Long(0x200000000000000l));
    }
}
