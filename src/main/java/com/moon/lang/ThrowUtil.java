package com.moon.lang;

import com.moon.exception.WrappedException;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public final class ThrowUtil {

    private ThrowUtil() {
        noInstanceError();
    }

    public static <T> T throwRuntime() {
        throw new IllegalArgumentException();
    }

    public static <T> T throwRuntime(Object reason) {
        if (reason == null) {
            throw new NullPointerException();
        }
        if (reason instanceof RuntimeException) {
            throw (RuntimeException) reason;
        }
        if (reason instanceof Error) {
            throw (Error) reason;
        }
        if (reason instanceof Throwable) {
            throw new WrappedException((Throwable) reason);
        }
        throw new WrappedException(String.valueOf(reason));
    }

    public static <T> T wrapAndThrow(Throwable e) {
        return throwRuntime(e);
    }

    public static <T> T wrapAndThrow(Throwable e, String msg) {
        throw new WrappedException(msg, e);
    }

    /**
     * 忽略指定类型异常
     *
     * @param t
     * @param type
     * @param <T>
     * @param <EX>
     * @return
     */
    public final static <T, EX extends Throwable> T ignoreAssignException(Throwable t, Class<EX> type) {
        if (type.isInstance(t)) {
            return null;
        }
        return throwRuntime(t);
    }

    /**
     * 忽略包含指定类型 cause 的异常，否则抛出异常
     *
     * @param t
     * @param type
     * @param <T>
     * @param <EX>
     * @return
     */
    public final static <T, EX extends Throwable> T ignoreAssignExceptionOfCause(Throwable t, Class<EX> type) {
        for (Throwable ex = t; ex != null; ex = ex.getCause()) {
            if (type.isInstance(ex)) {
                return null;
            }
        }
        return throwRuntime(t);
    }

    /**
     * 忽略指定类抛出的异常
     *
     * @param t
     * @param targetClass
     * @param <T>
     * @return
     */
    public final static <T> T ignoreAssignPossion(Throwable t, Class targetClass) {
        String name = targetClass.getName();
        StackTraceElement element;
        for (Throwable th = t; th != null; th = th.getCause()) {
            StackTraceElement[] elements = th.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                element = elements[i];
                if (name.equals(element.getClassName())) {
                    return null;
                }
            }
        }
        return throwRuntime(t);
    }

    /**
     * 忽略自身或 cause 中包含指定方法抛出的异常，否则抛出异常
     *
     * @param t
     * @param targetClass
     * @param methodName
     * @param <T>
     * @return
     */
    public final static <T> T ignoreAssignPosition(Throwable t, Class targetClass, String methodName) {
        String name = targetClass.getName();
        StackTraceElement element;
        for (Throwable th = t; th != null; th = th.getCause()) {
            StackTraceElement[] elements = th.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                element = elements[i];
                if (name.equals(element.getClassName()) && methodName.equals(element.getMethodName())) {
                    return null;
                }
            }
        }
        return throwRuntime(t);
    }

    /**
     * 用于私有构造方法等，抛出一个错误表示该类不能有实例存在
     */
    public static void noInstanceError() {
        noInstanceError("No " + StackTraceUtil.getPrevCallerTypeName() + " instances for you!");
    }

    /**
     * 用于私有构造方法等，抛出一个错误表示该类不能有实例存在
     */
    public static void noInstanceError(String message) {
        throw new AssertionError(message);
    }

    /**
     * 不能访问指定位置
     */
    public final static <T> T rejectAccessError() {
        return rejectAccessError("Refuse to apply. \n\tLocation: " + StackTraceUtil.getPrevTraceOfSteps(1));
    }

    /***
     * 不能访问指定位置
     * @param message
     */
    public final static <T> T rejectAccessError(String message) {
        throw new IllegalAccessError(message);
    }
}
