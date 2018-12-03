package com.moon.util;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class UnicodeUtilTestTest {

    String str, str0, str1, str2, u, u0, u1, u2, u3;

    static final Assertions assertions = Assertions.of();

    @Test
    void testIsUnicode() {
        str = "123";
        assertions.assertFalse(UnicodeUtil.isUnicode(str));
        u = UnicodeUtil.toSimpleUnicode(str);
        u0 = UnicodeUtil.toFullUnicode(str);

        System.out.println(u);
        System.out.println(u0);
    }

    @Test
    void testIsNotUnicode() {
        str = "1本少爷";

        u = UnicodeUtil.toFullUnicode(str);
        System.out.println(u);
        System.out.println(UnicodeUtil.isUnicode(u));

        u = UnicodeUtil.toSimpleUnicode(str);
        System.out.println(u);
        System.out.println(UnicodeUtil.isUnicode(u));
        System.out.println(UnicodeUtil.isSimpleUnicode(u));
    }

    @Test
    void testToString() {
        u = "123";
        System.out.println(UnicodeUtil.isSimpleUnicode(u));
    }

    @Test
    void testToSimpleUnicode() {
    }

    @Test
    void testToFullUnicode() {
    }
}