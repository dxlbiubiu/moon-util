package com.moon.util.compute.core;

import com.moon.lang.reflect.MethodUtil;

/**
 * @author benshaoye
 */
class InvokeNone extends InvokeBase {

    InvokeNone(String methodName) {
        super(methodName);
    }

    @Override
    public Object run(Object data) {
        return MethodUtil.invoke(true, getMethod(data), data);
    }
}
