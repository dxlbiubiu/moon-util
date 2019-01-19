package com.moon.util.validator;

import com.moon.enums.Const;

import java.util.Objects;

/**
 * @author benshaoye
 */
abstract class Value<T> {

    final static String NONE = Const.EMPTY;

    final T value;

    protected Value(T value) {this.value = Objects.requireNonNull(value);}

    public final T getValue() {
        return value;
    }
}
