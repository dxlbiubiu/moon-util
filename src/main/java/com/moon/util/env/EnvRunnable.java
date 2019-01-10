package com.moon.util.env;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface EnvRunnable {
    /**
     * 执行
     *
     * @throws Exception
     */
    void run() throws Exception;
}
