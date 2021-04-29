package com.arm.spring.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @author zhaolangjing
 * @since 2021-3-15 9:33
 */
@Slf4j
public class CatListener implements ApplicationListener<Cat> {
    @Override
    public void onApplicationEvent(Cat event) {
        log.info( event.voice() );
    }
}
