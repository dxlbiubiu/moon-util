package com.moon.util.env;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * 开发模式和生产模式
 * <p>
 * 通过一个系统参数 moon.production 控制
 * <p>
 * 默认是生产模式，设置 -Dmoon.production=false 后开启开发模式
 * <p>
 * 写这个工具的起因是在开发中，登录时通常会有验证码验证，但是开发就没必要验证了，节省时间精力;
 * 又如：开发和生产有些配置文件和参数是不同的，spring 本身是会支持，但是难免还是会有例外的场景
 * 有时候开发和生产使用的业务逻辑都是不一样（怎么会有这情况？我怎么知道，但是就是遇到了）
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
