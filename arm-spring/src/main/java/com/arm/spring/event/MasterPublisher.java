package com.arm.spring.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 主人呼叫猫咪吃饭
 * 注册事件发布者
 *
 * @author zhaolangjing
 * @since 2021-3-15 9:29
 */
public class MasterPublisher<T extends Animal> implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    /**
     * 派发事件
     */
    public void sendEvent(T event) {
        publisher.publishEvent( event );
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
