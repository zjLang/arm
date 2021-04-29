package com.arm.data;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间类测试
 * java.util.Date
 * java.sql.Date
 * java.sql.Time
 * java.sql.Timestamp
 * java.text.SimpleDateFormat
 * java.util.Calendar
 *
 * @author zhaolangjing
 * @since 2021-3-3 16:59
 */
public class DateTest {
    @Test
    public void test() {
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        Date time = Calendar.getInstance().getTime(); //获取一个时间
        System.out.println( instance.get( Calendar.YEAR ) + " "
                + instance.get( Calendar.MONTH ) + " " + instance.get( Calendar.MARCH ) );

        instance.add( Calendar.MINUTE, -30 ); // 40分钟前
        System.out.println( instance.get( Calendar.MINUTE ) );

        SimpleDateFormat.getDateInstance().format( date ); //使用SimpleDateFormat做时间格式化相关操作
    }
}
