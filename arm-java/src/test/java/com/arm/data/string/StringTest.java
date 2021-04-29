package com.arm.data.string;

import org.junit.Test;

/**
 * https://blog.csdn.net/lee0723/article/details/78579100
 *
 * @author zhaolangjing
 * @since 2021-3-3 11:27
 */

public class StringTest {

    /**
     * String StringBuffer StringBuilder 比较
     * 时间比较 ：
     * String 22457ms
     * StringBuilder 2ms
     * StringBuffer 4ms
     */
    @Test
    public void String3Compare() {
        long l = System.currentTimeMillis();
        String str = "i";
        for (int i = 0; i < 100000; i++) {
            str += i;
        }
        System.out.println( System.currentTimeMillis() - l + "ms" );
        l = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append( i );
        }
        System.out.println( System.currentTimeMillis() - l + "ms" );
        l = System.currentTimeMillis();
        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < 100000; i++) {
            sb2.append( i );
        }
        System.out.println( System.currentTimeMillis() - l + "ms" );
    }


    /**
     * 字符串基本操作方法 ： 相等  相加 , 截取, 替换, 去空字符串,  大小写转换 ， 包含
     */
    @Test
    public void stringMethod() {
        String name = "123456789";
        System.out.println( name.equals( "123456789" ) );
        System.out.println( name.compareTo( "23456789" ) ); // 大小比较

        name.split( "3" );
        name.indexOf( 3 );
        name.substring( 2, 3 ); // 左闭右开

        name.contains( "22" );
        name.replace( "1", "2" );
        name.toUpperCase();
        name.toLowerCase();
        name.concat( "adc" );
        name.startsWith( "23" );
        name.isEmpty();
        name.trim();
    }

    /**
     * String在jvm里面得表现
     * 静态相加和变量得动态相加
     * 代码1中局部变量sa,sb存储的是堆中两个拘留字符串对象的地址。而当执行sa+sb时，JVM首先会在堆中创建一个StringBuilder类，
     * 同时用sa指向的拘留字符串对象完成初始化，然后调用append方法完成对sb所指向的拘留字符串的合并操作，
     * 接着调用StringBuilder的toString()方法在堆中创建一个String对象，最后将刚生成的String对象的堆地址存放在局部变量sab中。
     * 而局部变量s存储的是常量池中"abcd"所对应的拘留字符串对象的地址。 sab与s地址当然不一样了。这里要注意了，
     * 代码1的堆中实际上有五个字符串对象：三个拘留字符串对象、一个String对象和一个StringBuilder对象。
     * 代码2中"ab"+"cd"会直接在编译期就合并成常量"abcd"， 因此相同字面值常量"abcd"所对应的是同一个拘留字符串对象，自然地址也就相同。
     */
    @Test
    public void StringJvm() {
        String sa = "ab";
        String sb = "cd";
        String sab = sa + sb;
        String s = "abcd";
        System.out.println( sab == s ); // false
        //代码2
        String sc = "ab" + "cd";
        String sd = "abcd";
        System.out.println( sc == sd ); //true
        //代码3
        String sa1 = new String( "sa" );
        String sa2 = new String( "sa" );
        System.out.println( sa1 == sa2 );

        String s1 = "aa";
        String s2 = "sa";
        String s3 = "aasa";
        final String s4 = s1 + s2;
        System.out.println( s4 == s3 );
    }
}
