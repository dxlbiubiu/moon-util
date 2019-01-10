package com.moon.util.validator;

import com.moon.util.DetectUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author benshaoye
 */
class CollectValidatorTestTest {

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

        CollectValidator.of(collect)
            .setImmediate(false)
            .requireUnique()
            .requireOnGrouped(
                DetectUtil::isNumeric,
                validator -> validator
                    .require(list -> list.size() > 2, "同一个人参加项目不能少于 2 项")
                    .requireUnique("不能有重复项")
            ).ifValid(c -> System.out.println("===="))
            .ifInvalid(c -> System.out.println("--"));
    }
}