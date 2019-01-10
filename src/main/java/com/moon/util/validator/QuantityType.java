package com.moon.util.validator;

/**
 * @author benshaoye
 */
enum QuantityType {
    AtMost("最多只能有 "),
    AtLeast("至少需要有 ");

    final String str;

    QuantityType(String str) {
        this.str = str;
    }
}
