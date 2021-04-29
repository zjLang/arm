package com.arm.spring.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @author zhaolangjing
 * @since 2021-3-15 9:47
 */
@Slf4j
public class PigListener implements ApplicationListener<Pig> {
    @Override
    public void onApplicationEvent(Pig event) {
        log.info( event.voice() );
    }
}
