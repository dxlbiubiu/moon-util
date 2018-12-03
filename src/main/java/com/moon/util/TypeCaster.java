package com.moon.util;

import java.util.function.Function;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface TypeCaster<R> extends Function<Object, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param o the function argument
     * @return the function result
     */
    @Override
    R apply(Object o);

    /**
     * alias for apply
     *
     * @param o
     * @return
     */
    default R cast(Object o) {
        return apply(o);
    }
}
