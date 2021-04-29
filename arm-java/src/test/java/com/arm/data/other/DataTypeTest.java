package com.arm.data.other;

import org.junit.Test;

/**
 * 其他基本数据类型的测试 ，int ，float
 *
 * @author zhaolangjing
 * @since 2021-3-3 15:24
 */
public class DataTypeTest {
    @Test
    public void tests() {
        Character c1 = 10, c2 = 10, c3 = 1000, c4 = 1000;
        System.out.println( (c1 == c2) + "---" + (c3 == c4) );

        Integer i = 10, j = 10, i1 = 1000, j1 = 1000;
        System.out.println( (i == j) + "---" + (i1 == j1) );

        Long l1 = 10l, l2 = 10l, l3 = 1000l, l4 = 1000l;
        System.out.println( (l1 == l2) + "---" + (l3 == l4) );

        Double d1 = 10.0, d2 = 10.0, d3 = 200.0, d4 = 200.0;
        System.out.println( (d1 == d2) + "---" + (d3 == d4) );
    }
}
