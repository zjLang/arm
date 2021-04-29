package com.arm.concurrent.nike;

import java.util.ArrayList;
import java.util.List;

/**
 * 鞋子生成工厂， 在一定时候产生一批鞋子
 * 这里产生鞋子当作单线程处理，一个厂商生产一批限量板鞋子放入货架，供抢
 * 后面可以改成队列模式。
 *
 * @author zhaolangjing
 * @since 2021-3-9 15:42
 */
public class ShoesFactory {

    /**
     * 保证原子性和可见性
     */
    private volatile static List<Shoes> shoesList;

    public static final Shoes getShoes() {
        Shoes shoes = null;
        if (shoesList != null && shoesList.size() > 0) {
            //synchronized (shoesList) {
                shoes = shoesList.get( 0 );
                shoesList.remove( 0 ); // 删除后索引会减去1
            //}
        }
        return shoes;
    }

    /**
     * 简单的 生产一批相同的鞋子出来
     *
     * @return
     */
    public static final List<Shoes> createShoes(int capacity, String name, String shelves, String price) {
        if (shoesList == null) {
            // 这个地方不能以shoesList为锁，因为该对象现在还未被创建
            synchronized (ShoesFactory.class) {
                if (shoesList == null) {
                    shoesList = new ArrayList<>( capacity );
                    for (int i = 0; i < capacity; i++) {
                        Shoes shoes = new Shoes();
                        shoes.setName( name + "-" + i );
                        shoes.setShelves( shelves );
                        shoes.setPrice( price );
                        shoesList.add( shoes );
                    }
                }
            }
        }
        return shoesList;
    }
}
