package com.moon.util.env;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * 开发模式和生产模式
 * <p>
 * 通过一个系统参数 moon.production 控制
 * <p>
 * 默认是生产模式，设置 -Dmoon.production=false 后开启开发模式
 *
 * @author benshaoye
 */
public final class EnvUtil {

    private EnvUtil() {
        noInstanceError();
    }

    public final static EnvEnum development() {
        return EnvEnum.DEVELOPMENT;
    }

    public final static EnvEnum production() {
        return EnvEnum.PRODUCTION;
    }

    public final static boolean isProduction() {
        return production().isTrue();
    }

    public final static boolean isDevelopment() {
        return development().isTrue();
    }

    public final static void ifProd(EnvRunnable executor) {
        production().run(executor);
    }

    public final static void ifDev(EnvRunnable executor) {
        development().run(executor);
    }

    public final static <T> T ifProdOrOther(T prod, T other) {
        return isProduction() ? prod : other;
    }

    public final static <T> T ifDevOrOther(T dev, T other) {
        return isDevelopment() ? dev : other;
    }

    public final static <T> T ifProdOrDefault(EnvSupplier supplier, T defaultValue) {
        return production().getOrDefault(supplier, defaultValue);
    }

    public final static <T> T ifDevOrDefault(EnvSupplier supplier, T defaultValue) {
        return development().getOrDefault(supplier, defaultValue);
    }

    public final static <T> T ifProdOrElse(EnvSupplier supplier, EnvSupplier defaultSupplier) {
        return production().getOrElse(supplier, defaultSupplier);
    }

    public final static <T> T ifDevOrElse(EnvSupplier supplier, EnvSupplier defaultSupplier) {
        return development().getOrElse(supplier, defaultSupplier);
    }
}
