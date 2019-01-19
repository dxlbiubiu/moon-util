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

        CollectValidator<List<String>, String> validator = CollectValidator.of(collect);

        validator.when(item -> true)
            .requireEvery(str -> str != null, "不能包含 null 值")
        ;
    }
}