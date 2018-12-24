package com.moon.util;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class EnvUtilTestTest {
    static final Assertions assertions = Assertions.of();

    @Test
    void testIsProduction() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.freeMemory());
        System.out.println(runtime.maxMemory());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.availableProcessors());
        System.out.println(System.getProperty("moon.production"));
    }
}