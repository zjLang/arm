package com.arm.demo;

import com.arm.demo.configuration.TestBean;
import com.arm.demo.controller.TestController;
import com.arm.demo.service.Test2Controller;
import com.arm.demo.service.UserService;
import com.arm.demo.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhaolangjing
 */
@SpringBootApplication()
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class);


        TestController bean5 = run.getBean(TestController.class);
        System.out.println("TestController:" + bean5);


        Test2Controller bean4 = run.getBean(Test2Controller.class);
        System.out.println("Test2Controller:" + bean4);

        UserService bean3 = run.getBean(UserServiceImpl.class);
        System.out.println("userService:" + bean3);

        TestBean bean = run.getBean(TestBean.class);
        System.out.println(bean);

        /*Test2Bean bean1 = run.getBean(Test2Bean.class);
        System.out.println(bean1);

        Test3Bean bean2 = run.getBean(Test3Bean.class);
        System.out.println(bean2);*/
    }

}
