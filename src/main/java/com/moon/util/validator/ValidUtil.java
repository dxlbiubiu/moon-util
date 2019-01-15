package com.moon.util.validator;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
final class ValidUtil {
    private ValidUtil() {
        noInstanceError();
    }

    static Object requireUnique(BaseValidator validator, Function transformer, String message) {
        boolean isNull = false;
        Object transformed = null;
        for (Object one : (Collection) validator.value) {
            Object curr = transformer.apply(one);
            if (transformed == null) {
                if (isNull) {
                    if (curr == null) {
                        continue;
                    }
                    return validator.createMsg(message);
                }
                transformed = curr;
            } else if (curr == null && !isNull) {
                isNull = true;
            } else if (!curr.equals(transformed)) {
                return validator.createMsg(message);
            }
        }
        return validator;
    }

    static <E, C extends Collection<E>, IMPL extends BaseValidator<C, IMPL, IMPL1>, IMPL1 extends BaseValidator<C, IMPL, IMPL1>>
    IMPL requireAtLeastCountOf(IMPL impl, Predicate<? super E> tester, int count, String message) {
        int amount = 0;
        for (E item : impl.getValue()) {
            if (tester.test(item) && (++amount >= count)) {
                return impl;
            }
        }
        return amount < count ? impl.createMsgAtLeast(message, count) : impl;
    }

    static <E, E1, C extends Collection<E>, C1 extends Collection<E1>,
        IMPL extends BaseValidator<C, IMPL, IMPL1>, IMPL1 extends BaseValidator<C1, IMPL, IMPL1>>
    IMPL requireAtMostCountOf(IMPL impl, Predicate<? super E> tester, int count, String message) {
        int amount = 0;
        for (E item : impl.getValue()) {
            if (tester.test(item) && (++amount > count)) {
                return impl.createMsgAtMost(message, count);
            }
        }
        return amount < count ? impl.createMsgAtMost(message, count) : impl;
    }
}
