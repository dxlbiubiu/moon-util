package com.moon.util;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface EnvExecutor {
    /**
     * 执行
     *
     * @throws Throwable
     */
    void run() throws Throwable;
}
