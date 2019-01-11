package com.moon.util.validator;

import com.moon.enums.Predicates;

import java.util.function.Predicate;

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
    IMPL requireNonNull(String message);

    /**
     * 要求符合指定验证规则
     *
     * @param tester
     * @return
     */
    default IMPL require(Predicate<? super T> tester) {
        return require(tester, Value.EMPTY);
    }

    /**
     * 要求非空
     *
     * @return
     */
    default IMPL requireNonNull() {
        return (IMPL) require(Predicates.NON_NULL, Value.EMPTY);
    }
}
