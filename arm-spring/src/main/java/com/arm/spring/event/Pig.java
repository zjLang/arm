package com.arm.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhaolangjing
 * @since 2021-3-15 10:07
 */
public class Pig extends ApplicationEvent implements Animal {


    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public Pig(Object source) {
        super( source );
    }

    @Override
    public String toString() {
        return "Pig{" +
                "source=" + source +
                '}';
    }

    @Override
    public String name() {
        return "pig";
    }

    @Override
    public String voice() {
        return "gong gong";
    }
}
