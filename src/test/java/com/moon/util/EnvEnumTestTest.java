package com.moon.util;

import com.moon.util.env.EnvEnum;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class EnvEnumTestTest {

    @Test
    void testGetModeOrNull() {
        String str = EnvEnum.DEVELOPMENT.toString();
        Console.out.println(str);
    }

    @Test
    void testIsProduction() {
    }

    @Test
    void testIsDevelopment() {
    }
}