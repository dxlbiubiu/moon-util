package com.moon.util.validator;

import com.moon.util.MapUtil;

import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @author benshaoye
 */
public interface IKeyedValidator<M extends Map<K, V>, K, V, IMPL, ITEM>
    extends IValidator<M, IMPL> {

    /**
     * 要求包含指定数目项符合验证，使用指定错误信息
     *
     * @param tester
     * @param count
     * @param message
     * @return
     */
    IMPL requireCountOf(BiPredicate<? super K, ITEM> tester, int count, String message);

    /**
     * 要求至少指定数目项符合验证，使用指定错误信息
     *
     * @param tester
     * @param count
     * @param message
     * @return
     */
    IMPL requireAtLeastCountOf(BiPredicate<? super K, ITEM> tester, int count, String message);

    /**
     * 要求最多指定数目项符合验证，使用指定错误信息
     *
     * @param tester
     * @param count
     * @param message
     * @return
     */
    IMPL requireAtMostCountOf(BiPredicate<? super K, ITEM> tester, int count, String message);

    /*
     * -----------------------------------------------------
     * implemented
     * -----------------------------------------------------
     */

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
    default IMPL requireEvery(BiPredicate<? super K, ITEM> tester) {
        return requireEvery(tester, Value.NONE);
    }

    /**
     * 要求所有项都符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireEvery(BiPredicate<? super K, ITEM> tester, String message) {
        return requireAtLeastCountOf(tester, MapUtil.size(getValue()), message);
    }

    /**
     * 要求至少一项符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireAtLeastOne(BiPredicate<? super K, ITEM> tester) {
        return requireAtLeastOne(tester, Value.NONE);
    }

    /**
     * 要求至少一项符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireAtLeastOne(BiPredicate<? super K, ITEM> tester, String message) {
        return requireAtLeastCountOf(tester, 1, message);
    }

    /**
     * 要求至少指定数目项符合验证
     *
     * @param tester
     * @param count
     * @return
     */
    default IMPL requireAtLeastCountOf(BiPredicate<? super K, ITEM> tester, int count) {
        return requireAtLeastCountOf(tester, count, Value.NONE);
    }

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
    default IMPL requireNone(BiPredicate<? super K, ITEM> tester) {
        return requireNone(tester, Value.NONE);
    }

    /**
     * 要求所有项都不符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireNone(BiPredicate<? super K, ITEM> tester, String message) {
        return requireAtMostCountOf(tester, 0, message);
    }

    /**
     * 要求最多一项符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireAtMostOne(BiPredicate<? super K, ITEM> tester) {
        return requireAtMostOne(tester, Value.NONE);
    }

    /**
     * 要求最多一项符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireAtMostOne(BiPredicate<? super K, ITEM> tester, String message) {
        return requireAtMostCountOf(tester, 1, message);
    }

    /**
     * 要求最多指定数目项符合验证
     *
     * @param tester
     * @param count
     * @return
     */
    default IMPL requireAtMostCountOf(BiPredicate<? super K, ITEM> tester, int count) {
        return requireAtMostCountOf(tester, count, Value.NONE);
    }

    /*
     * -----------------------------------------------------
     * count of
     * -----------------------------------------------------
     */

    /**
     * 要求包含唯一项符合验证
     *
     * @param tester
     * @return
     */
    default IMPL requireOnly(BiPredicate<? super K, ITEM> tester) {
        return requireOnly(tester, Value.NONE);
    }

    /**
     * 要求包含唯一项符合验证，使用指定错误信息
     *
     * @param tester
     * @param message
     * @return
     */
    default IMPL requireOnly(BiPredicate<? super K, ITEM> tester, String message) {
        return requireCountOf(tester, 1, message);
    }

    /**
     * 要求包含指定数目项符合验证
     *
     * @param tester
     * @param count
     * @return
     */
    default IMPL requireCountOf(BiPredicate<? super K, ITEM> tester, int count) {
        return requireCountOf(tester, count, Value.NONE);
    }
}
