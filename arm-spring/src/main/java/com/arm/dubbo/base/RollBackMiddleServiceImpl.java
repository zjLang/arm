package com.arm.dubbo.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class RollBackMiddleServiceImpl implements RollBackMiddleService {

    private GoodsService goodsService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED) // 事务使用required
    public void remoteGoods() {
        goodsService.goods(); // 该方法远程调用，抛出异常
        /*try {
            goodsService.goods(); // 该方法远程调用，抛出异常
        } catch (Exception e) {
            log.error("远程调用异常：" + e);
        }*/
    }

    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
}
