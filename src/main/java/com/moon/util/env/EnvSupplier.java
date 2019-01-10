package com.moon.util.env;

import java.util.concurrent.Callable;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface EnvSupplier extends Callable {
    /**
     * 返回一个对象
     *
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T get() throws Exception;

    /**
     * 执行并返回一个对象
     *
     * @return
     * @throws Exception
     */
    @Override
    default Object call() throws Exception {
        return get();
    }
}
