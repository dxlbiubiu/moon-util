package com.moon.security;

import com.moon.util.Console;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class EncryptUtilTestTest {

    @Test
    void testMd5() {
        Console.out.println(EncryptUtil.sha1(EncryptUtil.md5("12345678")));
        Console.out.println(EncryptUtil.md5("12345678"));
        Console.out.println(EncryptUtil.sha1("12345678"));
        Console.out.println(EncryptUtil.sha256("12345678"));
        Console.out.println(EncryptUtil.sha384("12345678"));
        Console.out.println(EncryptUtil.sha512("12345678"));
    }
}