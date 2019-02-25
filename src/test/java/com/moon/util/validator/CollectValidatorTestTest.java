package com.moon.util.validator;

import com.moon.util.Console;
import com.moon.util.DateUtil;
import com.moon.util.IteratorUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author benshaoye
 */
class CollectValidatorTestTest {

    static final Assertions assertions = Assertions.of();

    public static class Employee {
        String name;
    }

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


        Object o = validator.groupBy(str -> str.length() > 5)
            .getValue();
        ;
        Console.out.println(o);
        final String certNo = "520201197209083216";
    }

    @Test
    void testCertValid() {
        String path = "D:/cert-nos.txt";
        IteratorUtil.forEach(new File(path), str -> {
            if (str.trim().length() == 18) {
                testOut(str.trim());
            }
        });
        testOut("610525199401201946");
    }

    void testOut(final String certNo) {
        String year = certNo.substring(6, 10);
        final String certYearMonth = certNo.substring(6, 14);
        final int targetYear = DateUtil.getYear(DateUtil.parse(certYearMonth, "yyyyMMdd"));
        final int nowYear = LocalDate.now().getYear();
        final int age = nowYear - targetYear;
        final int sex = (certNo.charAt(16) - 48) % 2;
        if ((sex == 0 && age > 43) || (sex == 1 && age > 53)) {
            assertions.assertTrue(checkCertNoAgeDiffGender(certNo));
            Console.out.println("对不起，您的年龄（男53岁及以上，女43岁及以上）不符合下单条件。certNo：{}，sex：{}，age：{}", certNo, sex, age);
        } else {
            assertions.assertFalse(checkCertNoAgeDiffGender(certNo));
            Console.out.println("成功：{}", certNo);
        }
    }

    private static boolean checkCertNoAgeDiffGender(String certNo) {
        final String certYearMonth = certNo.substring(6, 10);
        int targetYear = Integer.valueOf(certYearMonth);
        final int nowYear = java.time.LocalDate.now().getYear();
        final int age = nowYear - targetYear;
        final int sex = (certNo.charAt(16) - 48) % 2;
        return (sex == 0 && age > 43) || (sex == 1 && age > 53);
    }
}