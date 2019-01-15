package com.moon.util.validator;

import java.util.function.Predicate;

import static com.moon.enums.Predicates.NON_NULL;

/**
 * @author benshaoye
 */
interface IValidator<T, IMPL> {
    /**
     * 要求符合指定验证规则，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    IMPL require(Predicate<? super T> tester, String message);

    /**
     * 要求非空，使用指定错误信息
     *
     * @param message
     * @return
     */
    default IMPL requireNonNull(String message) {
        return (IMPL) require(NON_NULL, message);
    }

    /**
     * 要求符合指定验证规则
     *
     * @param tester
     * @return
     */
    default IMPL require(Predicate<? super T> tester) {
        return require(tester, Value.NONE);
    }

    /**
     * 要求非空
     *
     * @return
     */
    default IMPL requireNonNull() {
        return requireNonNull(Value.NONE);
    }
}
