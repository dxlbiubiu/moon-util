package com.moon.util.validator;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author benshaoye
 */
public interface ICollectValidator<C extends Collection<E>, E, IMPL>
    extends IValidator<C, IMPL> {

    /*
     * -----------------------------------------------------
     * at least
     * -----------------------------------------------------
     */

    /**
     * 要求所有项都符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireEvery(Predicate<? super E> tester) {
        return requireEvery(tester, Value.EMPTY);
    }

    /**
     * 要求所有项都符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    IMPL requireEvery(Predicate<? super E> tester, String message);

    /**
     * 要求至少一项符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireAtLeastOne(Predicate<? super E> tester) {
        return requireAtLeastOne(tester, Value.EMPTY);
    }

    /**
     * 要求至少一项符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireAtLeastOne(Predicate<? super E> tester, String message) {
        return requireAtLeastCountOf(tester, 1, message);
    }

    /**
     * 要求至少指定数目项符合验证
     *
     * @param tester
     * @param count
     * @return
     */
    default IMPL requireAtLeastCountOf(Predicate<? super E> tester, int count) {
        return requireAtLeastCountOf(tester, count, Value.EMPTY);
    }

    /**
     * 要求至少指定数目项符合验证，使用指定错误信息
     *
     * @param tester
     * @param count
     * @param message
     * @return
     */
    IMPL requireAtLeastCountOf(Predicate<? super E> tester, int count, String message);

    /*
     * -----------------------------------------------------
     * at most
     * -----------------------------------------------------
     */

    /**
     * 要求所有项都不符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireNone(Predicate<? super E> tester) {
        return requireNone(tester, Value.EMPTY);
    }

    /**
     * 要求所有项都不符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireNone(Predicate<? super E> tester, String message) {
        return requireAtMostCountOf(tester, 0, message);
    }

    /**
     * 要求最多一项符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireAtMostOne(Predicate<? super E> tester) {
        return requireAtMostOne(tester, Value.EMPTY);
    }

    /**
     * 要求最多一项符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireAtMostOne(Predicate<? super E> tester, String message) {
        return requireAtMostCountOf(tester, 1, message);
    }

    /**
     * 要求最多指定数目项符合验证
     *
     * @param tester
     * @param count
     * @return
     */
    default IMPL requireAtMostCountOf(Predicate<? super E> tester, int count) {
        return requireAtMostCountOf(tester, count, Value.EMPTY);
    }

    /**
     * 要求最多指定数目项符合验证，使用指定错误信息
     *
     * @param tester
     * @param count
     * @param message
     * @return
     */
    IMPL requireAtMostCountOf(Predicate<? super E> tester, int count, String message);
}
