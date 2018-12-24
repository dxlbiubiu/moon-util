package com.moon.util;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface EnvSupplier {

    <T> T get() throws Throwable;
}
