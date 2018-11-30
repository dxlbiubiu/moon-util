package com.moon.util.compute;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class RunnerSettingsTestTest {

    static final Assertions assertions = Assertions.of();

    String str, name;

    @Test
    void testCheckName() {
        name = "aaa";
        assertions.assertEquals(RunnerSettings.checkName(name), name);

        name = " aaa";
        assertions.assertThrows(() -> RunnerSettings.checkName(name));
        name = "$_aaa";
        assertions.assertEquals(RunnerSettings.checkName(name), name);
    }

    @Test
    void testName1() {
        name = "a aa";
        assertions.assertThrows(() -> RunnerSettings.checkName(name));
    }

    @Test
    void testToNsName() {
        name = RunnerSettings.toNsName("str", "indexOf");

        assertions.assertEquals("str.indexOf", name);
    }
}