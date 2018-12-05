package com.moon.util;

import com.moon.enums.Const;
import com.moon.lang.IntUtil;
import com.moon.lang.ref.WeakAccessor;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.RandomUtil.nextBoolean;
import static com.moon.util.RandomUtil.nextInt;

/**
 * @author benshaoye
 */
public final class RandomStringUtil {
    private RandomStringUtil() {
        noInstanceError();
    }

    private final static WeakAccessor<String> COMMON_CHINESE = WeakAccessor.of(() -> "");

    /*
     * -------------------------------------------------------------------
     * chinese 汉字
     * -------------------------------------------------------------------
     */

    public final static char nextChinese() {
        return (char) nextInt(0x4e00, 0x9fa6);
    }

    public final static String nextChinese(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextChinese();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextChinese(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextChinese(nextInt(min, max));
    }

    /*
     * -------------------------------------------------------------------
     * upper 大写字母
     * -------------------------------------------------------------------
     */

    public final static char nextUpper() {
        return (char) nextInt(65, 91);
    }

    public final static String nextUpper(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextUpper();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextUpper(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextUpper(nextInt(min, max));
    }

    /*
     * -------------------------------------------------------------------
     * lower 小写字母
     * -------------------------------------------------------------------
     */

    public final static char nextLower() {
        return (char) nextInt(97, 123);
    }

    public final static String nextLower(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextLower();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextLower(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextLower(nextInt(min, max));
    }

    /*
     * -------------------------------------------------------------------
     * letter 大小写字母
     * -------------------------------------------------------------------
     */

    public final static char nextLetter() {
        return nextBoolean() ? nextUpper() : nextLower();
    }

    public final static String nextLetter(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextLetter();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextLetter(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextLetter(nextInt(min, max));
    }

    /*
     * -------------------------------------------------------------------
     * control 控制字符
     * -------------------------------------------------------------------
     */

    public final static char nextControl() {
        return (char) nextInt(0, 32);
    }

    public final static String nextControl(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextControl();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextControl(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextControl(nextInt(min, max));
    }

    /*
     * -------------------------------------------------------------------
     * digit 数字
     * -------------------------------------------------------------------
     */

    public final static char nextDigit() {
        return (char) nextInt(48, 58);
    }

    public final static String nextDigit(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextDigit();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextDigit(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextDigit(nextInt(min, max));
    }

    /*
     * -------------------------------------------------------------------
     * Symbol 运算符
     * -------------------------------------------------------------------
     */

    private static final char[] SYMBOLS = "!@#$%^&*()_-+={}[]:;\"'|\\<>,.?/~`".toCharArray();

    public final static char nextSymbol() {
        return SYMBOLS[nextInt(0, SYMBOLS.length)];
    }

    public final static String nextSymbol(int length) {
        if (length > 0) {
            char[] value = new char[length];
            for (int i = 0; i < length; i++) {
                value[i] = nextSymbol();
            }
            return new String(value);
        }
        return Const.EMPTY;
    }

    public final static String nextSymbol(int min, int max) {
        IntUtil.requireGtOrEq(min, 0);
        IntUtil.requireLtOrEq(min, max);
        return nextSymbol(nextInt(min, max));
    }

    /**
     * 字符串乱序
     *
     * @param str
     * @return
     */
    public final static String randomOrder(String str) {
        if (str != null) {
            int i = 0, len = str.length();
            Character[] chars = new Character[len];
            for (; i < len; i++) {
                chars[i] = str.charAt(i);
            }
            RandomUtil.randomOrder(chars);
            char[] value = new char[len];
            for (i = 0; i < len; i++) {
                value[i] = chars[i];
            }
            str = new String(value);
        }
        return str;
    }
}
