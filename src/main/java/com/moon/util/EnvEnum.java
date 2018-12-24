package com.moon.util;

import static java.lang.Boolean.FALSE;
import static java.lang.System.getProperty;

/**
 * @author benshaoye
 */
public enum EnvEnum {
    DEVELOPMENT {
        @Override
        public void runMode(EnvExecutor executor) {
            if (isDevelopment()) {
                try {
                    executor.run();
                } catch (Throwable e) {
                    throwEx(e);
                }
            }
        }

        @Override
        public <T> T getMode(EnvSupplier supplier, T defaultValue) {
            if (isDevelopment()) {
                try {
                    return supplier.get();
                } catch (Throwable e) {
                    return throwEx(e);
                }
            }
            return defaultValue;
        }

        @Override
        public <T> T getModeOrElse(EnvSupplier supplier, EnvSupplier defaultValue) {
            if (isDevelopment()) {
                try {
                    return supplier.get();
                } catch (Throwable e) {
                    return throwEx(e);
                }
            } else {
                try {
                    return defaultValue.get();
                } catch (Throwable e) {
                    return throwEx(e);
                }
            }
        }
    },
    PRODUCTION {
        @Override
        public void runMode(EnvExecutor executor) {
            if (isProduction()) {
                try {
                    executor.run();
                } catch (Throwable e) {
                    throwEx(e);
                }
            }
        }

        @Override
        public <T> T getMode(EnvSupplier supplier, T defaultValue) {
            if (isProduction()) {
                try {
                    return supplier.get();
                } catch (Throwable e) {
                    return throwEx(e);
                }
            }
            return defaultValue;
        }

        @Override
        public <T> T getModeOrElse(EnvSupplier supplier, EnvSupplier defaultValue) {
            if (isProduction()) {
                try {
                    return supplier.get();
                } catch (Throwable e) {
                    return throwEx(e);
                }
            } else {
                try {
                    return defaultValue.get();
                } catch (Throwable e) {
                    return throwEx(e);
                }
            }
        }
    };

    public abstract void runMode(EnvExecutor executor);

    public abstract <T> T getMode(EnvSupplier executor, T defaultValue);

    public abstract <T> T getModeOrElse(EnvSupplier supplier, EnvSupplier defaultSupplier);

    public <T> T getModeOrNull(EnvSupplier executor) {
        return getMode(executor, null);
    }

    private static final boolean production;

    public final static boolean isProduction() {
        return production;
    }

    public final static boolean isDevelopment() {
        return !production;
    }

    static {
        boolean prod;
        try {
            prod = !FALSE.toString().equalsIgnoreCase(getProperty("moon.production"));
        } catch (Throwable e) {
            prod = true;
        }
        production = prod;
    }

    private static <T> T throwEx(Throwable e) {
        if (e instanceof Exception) {
            throw e instanceof RuntimeException ? (RuntimeException) e
                : new RuntimeException(e.getMessage(), e);
        } else {
            throw (Error) e;
        }
    }
}
