package com.moon.util.compute.core;

import com.moon.util.assertions.Assertions;
import com.moon.util.compute.Runner;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.moon.util.compute.core.ParseUtil.parse;

/**
 * @author benshaoye
 */
class InnersTestTest {

    static final Assertions assertions = Assertions.of();

    String str, str1;
    Runner runner, runner1;
    Object data, data1, res, res1;

    Object toValue(String expression, Object data) {
        this.data = data;
        str = expression;
        runner = parse(expression);
        res = runner.run(data);
        return res;
    }

    Object toValue(String expression) {
        return toValue(expression, null);
    }

    @Test
    void testFunctionsName() {
        toValue("@ str . indexOf (12,1)");
        assertions.assertSame(runner.getClass(), DataConstNumber.class);
        assertions.assertEquals(res, 0);

        data = new HashMap() {{
            put("str", 12);
        }};
        toValue("@ str . indexOf (str,1)", data);
        assertions.assertEquals(res, 0);

        toValue("@now()");
        assertions.assertInstanceOf(res, Long.class);

        toValue("@now.year()");
        assertions.assertInstanceOf(res, Integer.class);

    }
}