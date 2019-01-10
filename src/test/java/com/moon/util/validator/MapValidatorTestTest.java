package com.moon.util.validator;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
class MapValidatorTestTest {

    @Test
    void testOf() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");

        MapValidator.of(new HashMap() {{
            put("1", "1");
            put("2", "1");
            put("3", "1");
            put("4", "1");
            put("5", "1");
        }}).requireEvery((key, value) -> value.equals("1"))
            .ifValid(m -> System.out.println("===="))
            .ifInvalid(m -> System.out.println("--"));
    }
}