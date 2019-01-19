package com.moon.util.validator;

import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @author benshaoye
 */
public final class MapValidator<M extends Map<K, V>, K, V>
    extends BaseValidator<M, MapValidator<M, K, V>>
    implements IKeyedValidator<M, K, V, MapValidator<M, K, V>, Validator<? super V>> {

    public MapValidator(M value) {
        super(value, null, SEPARATOR, false);
    }

    public final static <M extends Map<K, V>, K, V> MapValidator<M, K, V> of(M map) {
        return new MapValidator<>(map);
    }

    /*
     * -----------------------------------------------------
     * requires
     * -----------------------------------------------------
     */

    @Override
    public MapValidator<M, K, V> requireAtLeastCountOf(
        BiPredicate<? super K, Validator<? super V>> tester, int count, String message
    ) {
        return ifCondition(value -> {
            int amount = 0;
            for (Map.Entry<K, V> item : value.entrySet()) {
                if (tester.test(item.getKey(), new Validator<>(
                    item.getValue(), ensureMessages(),
                    getSeparator(), isImmediate()
                )) && (++amount >= count)) {
                    return this;
                }
            }
            return amount < count ? createMsgAtLeast(message, count) : this;
        });
    }

    @Override
    public MapValidator<M, K, V> requireAtMostCountOf(
        BiPredicate<? super K, Validator<? super V>> tester, int count, String message
    ) {
        return ifCondition(value -> {
            int amount = 0;
            for (Map.Entry<K, V> item : value.entrySet()) {
                if (tester.test(item.getKey(), new Validator<>(
                    item.getValue(), ensureMessages(),
                    getSeparator(), isImmediate()
                )) && (++amount > count)) {
                    return createMsgAtMost(message, count);
                }
            }
            return amount > count ? createMsgAtMost(message, count) : this;
        });
    }

    @Override
    public MapValidator<M, K, V> requireCountOf(
        BiPredicate<? super K, Validator<? super V>> tester, int count, String message
    ) {
        return ifCondition(value -> {
            int amount = 0;
            for (Map.Entry<K, V> item : value.entrySet()) {
                if (tester.test(item.getKey(), new Validator<>(
                    item.getValue(), ensureMessages(),
                    getSeparator(), isImmediate()
                )) && (++amount > count)) {
                    return createMsgOfCount(message, count);
                }
            }
            return amount > count ? createMsgOfCount(message, count) : this;
        });
    }
}
