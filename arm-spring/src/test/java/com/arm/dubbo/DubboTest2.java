package com.arm.dubbo;

import com.arm.dubbo.base.GoodsService;
import com.arm.dubbo.base.RollBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.Collectors;

/**
 * @author zhaolangjing
 * @since 2021-3-17 17:18
 */
@Slf4j
public class DubboTest2 {
    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");

    public static void main(String[] args) {
        //GoodsService goodsService = (GoodsService) context.getBean(GoodsService.class);
        //log.info(goodsService.goods().stream().collect(Collectors.joining()));
        RollBackService bean = context.getBean(RollBackService.class);
        bean.save();
    }

}
