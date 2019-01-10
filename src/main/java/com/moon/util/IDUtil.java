package com.moon.util;

import java.util.UUID;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * identifier ID 生成器
 *
 * @author benshaoye
 */
public final class IDUtil {
    private IDUtil() {
        noInstanceError();
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
