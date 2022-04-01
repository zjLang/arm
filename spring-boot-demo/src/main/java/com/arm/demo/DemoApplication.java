package com.arm.demo;

import com.arm.configuration.Test2Bean;
import com.arm.configuration.enable.Test3Bean;
import com.arm.configuration.enable.TestEnable;
import com.arm.demo.configuration.TestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhaolangjing
 */
@SpringBootApplication
@TestEnable
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class);
        TestBean bean = run.getBean(TestBean.class);
        System.out.println(bean);

        Test2Bean bean1 = run.getBean(Test2Bean.class);
        System.out.println(bean1);

        Test3Bean bean2 = run.getBean(Test3Bean.class);
        System.out.println(bean2);
    }

}
