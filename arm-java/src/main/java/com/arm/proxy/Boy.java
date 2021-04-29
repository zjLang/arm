package com.arm.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaolangjing
 * @since 2021-3-22 16:03
 */
@Slf4j
public class Boy implements Human {
    @Override
    public void run() {
        log.info( "boy running" );
    }
}
