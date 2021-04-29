package com.arm.dubbo.base;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhaolangjing
 * @since 2021-3-17 17:08
 */
public class GoodsServiceImpl implements GoodsService {
    @Override
    public List<String> goods() throws NullPointerException {
        try {
            List<String> strings = Arrays.asList(new String[]{"鸡肉", "猪肉", "白菜"});
            int i = 1 / 0;
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("null");
        }
    }
}
