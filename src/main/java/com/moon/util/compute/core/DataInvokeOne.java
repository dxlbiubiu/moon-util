package com.moon.util.compute.core;

import com.moon.lang.reflect.MethodUtil;

import java.util.Objects;

/**
 * @author benshaoye
 */
final class DataInvokeOne extends DataInvokeBase {

    final AsValuer prevValuer;
    final AsRunner valuer;

    public DataInvokeOne(AsValuer prevValuer, AsRunner valuer, String methodName) {
        super(methodName);
        this.valuer = Objects.requireNonNull(valuer);
        this.prevValuer = Objects.requireNonNull(prevValuer);
    }

    @Override
    public Object run(Object data) {
        Object source = prevValuer.run(data);
        Object params = valuer.run(data);
        return MethodUtil.invoke(true, getMethod(source, params), source, params);
    }
}
