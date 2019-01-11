package com.moon.util.validator;

import com.moon.enums.Const;

/**
 * @author benshaoye
 */
abstract class Value<T> {

    final static String EMPTY = Const.EMPTY;

    final T value;

    protected Value(T value) {this.value = value;}

    public final T getValue() {
        return value;
    }
}
