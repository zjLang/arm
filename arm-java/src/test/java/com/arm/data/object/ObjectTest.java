package com.arm.data.object;

import org.junit.Test;

/**
 * 面向对象的测试 抽象 封装 继承 多态
 * final类 ： 不能被继承
 * final类变量：不能被更改
 * final方法 ：不能被重载
 * final方法参数：不能被修改值，不能修改对象引用
 *
 * @author zhaolangjing
 * @since 2021-3-3 16:42
 */
public class ObjectTest {
    public static String name = "zhangsan";
    public static final String name2 = "zhangsan";

    static {
        name = "wangwu";
    }

    @Test
    public void change() {
        System.out.println( name );
        System.out.println( name = "lisi" );
    }

    private final void change1(final String name, String name2) {
        name2 = "zhangxin";
    }
}
