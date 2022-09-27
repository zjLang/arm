package com.arm.code.mix.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author z-ewa
 */
@SpringBootApplication
public class SpringBoot {
    public static void main(String[] args) {

        new SpringApplication(SpringBoot.class).run(args);
    }
}
