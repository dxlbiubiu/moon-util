package com.moon.util;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author benshaoye
 */
class RandomStringUtilTestTest {

    static final Assertions assertions = Assertions.of();

    Object res, res0, res1, res2, res3;
    String s, s1, s2, s3, s4, s5, s6, s0;
    char ch, ch1;

    @Test
    void testInitCommonChinese() {
    }

    @Test
    void testNextChinese() {
    }

    @Test
    void testNextUpper() {
    }

    @Test
    void testNextLetter() {
    }

    @Test
    void testNextControl() {
    }

    @Test
    void testNextDigit() {
    }

    @Test
    void testNextSymbol() {
    }

    @Test
    void testRandomOrder() {
        String str = "abcedfghijklmnopqrstuvwxyz";
        s = RandomStringUtil.randomOrder(str);
        assertions.assertEq(s.length(), str.length());
        assertions.assertNotEquals(s, str);
        char[] chars0 = str.toCharArray();
        char[] chars1 = s.toCharArray();
        Arrays.sort(chars0);
        Arrays.sort(chars1);
        assertions.assertTrue(Arrays.equals(chars0, chars1));
    }
}