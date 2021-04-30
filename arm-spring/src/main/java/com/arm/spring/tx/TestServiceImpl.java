package com.arm.spring.tx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author zhaolangjing
 * @since 2021-4-16 17:23
 * REQUIRED : 没有事务则创建一个事务。 事务包裹多个方法，任何方法异常都会回滚。
 * 如下，test1添加REQUIRED事务，test2不添加事务，则test2报错，事务被回滚。
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    static String slq = "insert into tx_user(username , age) VALUES (?, ?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestService2 testService2;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED
    )
    public void test1(Object[][] params) {
        jdbcTemplate.update(slq, params[0]);  // sql1
    }

    @Transactional(propagation = Propagation.NESTED
    )
    public void test2(Object[] params) {
        jdbcTemplate.update(slq, params); // sql2
        //int a = 1 / 0; // 子方法抛出异常。
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY
    )
    public void test3(Object[][] params) {
        jdbcTemplate.update(slq, params[0]);  // sql1
        test4(params[1]);
    }

    @Transactional(propagation = Propagation.SUPPORTS

    )
    public void test4(Object[] params) {
        jdbcTemplate.update(slq, params); // sql2
        int a = 1 / 0; // 子方法抛出异常。
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void test5() {
        try{
            jdbcTemplate.update(slq, new Object[]{"lisi1", "1"});  // sql1
            testService2.test2(); // 调用rTest
            testService2.test3(); // 调用nTest
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override

    public void test6() {
        log.info("http 执行开始....");
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String temp = null;
                StringBuffer sbf = new StringBuffer();
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                String result = sbf.toString();
                System.out.println(result);
                Thread.sleep(20000);
                log.info("http 执行完成....");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override

    public void test7() {
        log.info("保存数据执行开始 ....");
        jdbcTemplate.update(slq, new Object[]{"lisi1", "1"});  // sql1
        log.info("保存数据执行结束 ....");
    }


}
