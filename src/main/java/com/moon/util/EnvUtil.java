package com.moon.util;

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
        return EnvEnum.isProduction();
    }

    public final static boolean isDevelopment() {
        return EnvEnum.isDevelopment();
    }

    public final static void ifProd(EnvExecutor executor) {
        production().runMode(executor);
    }

    public final static void ifDev(EnvExecutor executor) {
        development().runMode(executor);
    }

    public final static <T> T ifProdOrElse(EnvSupplier supplier, T defaultValue) {
        return production().getMode(supplier, defaultValue);
    }

    public final static <T> T ifDevOrElse(EnvSupplier supplier, T defaultValue) {
        return development().getMode(supplier, defaultValue);
    }

    public final static <T> T ifProdOrElseGet(EnvSupplier supplier, EnvSupplier defaultSupplier) {
        return production().getModeOrElse(supplier, defaultSupplier);
    }

    public final static <T> T ifDevOrElseGet(EnvSupplier supplier, EnvSupplier defaultSupplier) {
        return development().getModeOrElse(supplier, defaultSupplier);
    }
}
