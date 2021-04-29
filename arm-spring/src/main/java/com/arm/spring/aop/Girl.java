package com.arm.spring.aop;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaolangjing
 * @since 2021-3-15 17:14
 */
@Slf4j
public class Girl implements IBuy {
    @Override
    public String buy(int price) {
        log.info( "女孩买了一件漂亮的衣服" );
        return "衣服";
    }
}
