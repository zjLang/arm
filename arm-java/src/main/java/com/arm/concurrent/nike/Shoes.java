package com.arm.concurrent.nike;

/**
 * 鞋子对象
 *
 * @author zhaolangjing
 * @since 2021-3-9 14:25
 */
public class Shoes {
    /**
     * 鞋子名称
     */
    private String name;

    /**
     * 鞋子颜色
     */
    private String color;

    /**
     * 货架号
     */
    private String shelves;

    /**
     * 鞋子大小
     */
    private String size;

    /**
     * 价钱
     */
    private String price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShelves() {
        return shelves;
    }

    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", shelves='" + shelves + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
