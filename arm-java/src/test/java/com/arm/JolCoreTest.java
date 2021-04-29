package com.arm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * jol core 测试
 *
 * @author zhaolangjing
 * @since 2021-3-11 19:35
 */
@Slf4j
public class JolCoreTest {
    @Test
    public void test() {
        Dog dog = new Dog();
        log.debug( ClassLayout.parseInstance( dog ).toPrintable() );
        synchronized (dog) {
            log.debug( ClassLayout.parseInstance( dog ).toPrintable() );
        }
        log.debug( ClassLayout.parseInstance( dog ).toPrintable() );
    }

    class Dog {

    }
}
