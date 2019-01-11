package com.moon.util.validator;

import com.moon.util.DetectUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author benshaoye
 */
class CollectValidatorTestTest {

    static final Assertions assertions = Assertions.of();

    @Test
    void testOf() {
        List<String> collect = new ArrayList<>();
        collect.add("1111");
        collect.add("22222");
        collect.add("333333");
        collect.add("4444444");
        collect.add("55555555");
        collect.add("dsfgsdfgasdf");
        collect.add("sdfbhdfhnrththn");

        CollectValidator<List<String>, String> validator1 = CollectValidator.of(collect);
        CollectValidator<List<String>, String> validator2 = validator1.groupBy(str -> DetectUtil.isNumeric(str)).end();
        CollectValidator<List<String>, String> validator3 = validator1.filter(str -> true).end();

        assertions.assertTrue(validator1 == validator2);
        assertions.assertTrue(validator1 == validator3);

        validator1
            .condition(strList -> strList == null)
            .end()
            .groupBy(str -> str.length() > 6)
            .requireEvery((key, v) -> {
                System.out.println(v.getValue());
                return true;
            })
            .end()
            .transform(str -> str.substring(0, 2))
            .requireEvery(str -> {
                System.out.println(str);
                return DetectUtil.isNumeric(str);
            }, "所有项都应该是数字").get();
    }
}