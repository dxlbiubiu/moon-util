package com.moon.util.env;

import static com.moon.lang.ThrowUtil.wrapAndThrow;
import static java.lang.Boolean.FALSE;
import static java.lang.System.getProperty;

/**
 * @author benshaoye
 */
public enum EnvEnum {
    DEVELOPMENT {
        @Override
        boolean isTrue() {
            return isDevelopment();
        }
    },
    PRODUCTION {
        @Override
        boolean isTrue() {
            return isProduction();
        }
    };

    abstract boolean isTrue();

    public void run(EnvRunnable executor) {
        if (isTrue()) {
            try {
                executor.run();
            } catch (Throwable e) {
                wrapAndThrow(e);
            }
        }
    }

    public <T> T getOrDefault(EnvSupplier supplier, T defaultValue) {
        try {
            return isTrue() ? supplier.get() : defaultValue;
        } catch (Throwable e) {
            return wrapAndThrow(e);
        }
    }

    public <T> T getOrElse(EnvSupplier supplier, EnvSupplier defaultSupplier) {
        try {
            return (isTrue() ? supplier : defaultSupplier).get();
        } catch (Throwable e) {
            return wrapAndThrow(e);
        }
    }

    public <T> T getOrNull(EnvSupplier executor) {
        return getOrDefault(executor, null);
    }

    public final static boolean isProduction() {
        return production;
    }

    public final static boolean isDevelopment() {
        return !production;
    }

    private static final boolean production;

    static {
        boolean prod;
        try {
            prod = !FALSE.toString().equalsIgnoreCase(
                getProperty("moon.production"));
        } catch (Throwable e) {
            prod = true;
        }
        production = prod;
    }
}
