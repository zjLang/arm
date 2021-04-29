package com.arm.spring.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * 洗猫救助站
 *
 * @author zhaolangjing
 * @since 2021-3-15 10:43
 */
@Slf4j
public class WashCatListener implements ApplicationListener<Cat> {
    @Override
    public void onApplicationEvent(Cat event) {
        log.info( "来个一个猫，要给他洗澡了" );
    }
}
