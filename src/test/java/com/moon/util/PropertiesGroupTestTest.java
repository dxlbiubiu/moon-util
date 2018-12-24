package com.moon.util;

import com.moon.util.assertions.Assertions;
import com.moon.util.compute.Runner;
import com.moon.util.compute.RunnerUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author benshaoye
 */
class PropertiesGroupTestTest {

    static final Assertions assertions = Assertions.of();

    @Test
    void testCreated() {
        String str = "{'conn.url':'localhost:8080','conn.username':'moonsky','conn.password':'123456'}";
        str = "{'conn.url':'localhost:8080','conn.username':'moonsky','conn.password':'123456', 'conn': true}";
        Runner runner = RunnerUtil.parse(str);
        Map<String, String> ret = runner.run();

        PropertiesGroup group = new PropertiesGroup(ret);

        Console.out.println(group.get("conn"));
        Console.out.println(ret);
    }
}