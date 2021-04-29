package com.arm.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 购买增强代理类
 *
 * @author zhaolangjing
 * @since 2021-3-15 17:18
 */
@Aspect
@Slf4j
public class BuyAspectJ {
    @Pointcut("execution(* com.arm.spring.aop.IBuy.buy(int)) && args(price)")
    public void point(int price) {

    }

    /*@Before("execution(* com.arm.spring.aop.IBuy.buy(..))")
    public void before() {
        log.info( "男孩女孩都买自己喜欢的东西" );
    }*/

    @Before("point(int)")
    public void before() {
        log.info( "男孩女孩都买自己喜欢的东西" );
    }

    @After("point(int)")
    public void after() {
        log.info( "男孩女孩都买到了自己喜欢的礼物" );
    }

    /**
     * 环绕通知需要
     */
    @Around("point(int)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info( "男孩女孩卖礼物开始" );
        joinPoint.proceed();
        log.info( "男孩女孩卖礼物结束" );
    }

    @AfterThrowing(pointcut = "point(int)", throwing = "ex")
    public void exception(Throwable ex) {
        log.info( "接收异常" + ex );
    }

    @AfterReturning("point(int)")
    public void result() {
        log.info( "返回结果" );
    }



}
