package com.arm.demo;

import com.arm.demo.service.JdbcTestServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLException;

/**
 * @author z-ewa
 */
@SpringBootApplication()
@PropertySource(value = {"classpath:application_jdbc.yml"})
public class TestBootApplication {
    public static void main(String[] args) throws SQLException, JsonProcessingException {
        ConfigurableApplicationContext context = new SpringApplication(TestBootApplication.class).run(args);
        JdbcTestServiceImpl bean = context.getBean(JdbcTestServiceImpl.class);
        //bean.getCloumn();
        // re_index_snapshot_version re_client_fill_data_record
        System.out.println("query success:" + new ObjectMapper().writeValueAsString(bean.getCloumn("re_client_fill_data_record")));
        System.out.println("query success:" + new ObjectMapper().writeValueAsString(bean.getPk("re_client_fill_data_record")));
    }
}
