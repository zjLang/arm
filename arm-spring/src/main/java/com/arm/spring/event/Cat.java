package com.arm.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhaolangjing
 * @since 2021-3-15 9:54
 */
public class Cat extends ApplicationEvent implements Animal{
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public Cat(Object source) {
        super( source );
    }

    @Override
    public String toString() {
        return "Cat{" +
                "source=" + source +
                '}';
    }

    @Override
    public String name() {
        return "cat";
    }

    @Override
    public String voice() {
        return "miao miao";
    }
}
