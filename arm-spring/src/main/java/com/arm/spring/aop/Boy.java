package com.arm.spring.aop;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaolangjing
 * @since 2021-3-15 17:14
 */
@Slf4j
public class Boy implements IBuy {
    @Override
    public String buy(int price) throws IllegalArgumentException {
        if (price == 0) {
            throw new IllegalArgumentException( "价钱不够，不能卖东西" );
        }
        log.info( "男孩买了一个游戏机" );
        return "游戏机";
    }
}
