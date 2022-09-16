package com.arm.mongo;

import com.arm.mongo.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author z-ewa
 */
@SpringBootApplication
//@Import(MangoConfig.class)
public class MongoApplication {
    public static void main(String[] args) throws BeansException {
        ConfigurableApplicationContext run = new SpringApplication(MongoApplication.class).run(args);
        MongoService bean = null;
        bean = run.getBean(MongoService.class);
        bean.insert(User.newZhangSan());

        //bean.insert(User.newLiSi());

        //UserCopy one = bean.findOne();

        //List<UserCopy> all = bean.findAll();

        System.out.println("over");
    }
}
