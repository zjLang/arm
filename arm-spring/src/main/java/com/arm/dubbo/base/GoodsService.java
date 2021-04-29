package com.arm.dubbo.base;

import java.util.List;

/**
 * @author zhaolangjing
 * @since 2021-3-17 17:07
 */
@FunctionalInterface
public interface GoodsService {
    /**
     * 获取商品服务
     *
     * @return
     */
    List<String> goods() throws NullPointerException;
}
