package com.moon.util.validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @author benshaoye
 */
public final class GroupValidator<M extends Map<K, C>, C extends Collection<E>, K, E>
    extends BaseValidator<M, GroupValidator<M, C, K, E>>
    implements IKeyedValidator<M, K, C, GroupValidator<M, C, K, E>, CollectValidator<C, E>> {

    public GroupValidator(M value) {
        super(value, null, SEPARATOR, false);
    }

    GroupValidator(M value, List<String> messages, String separator, boolean immediate) {
        super(value, messages, separator, immediate);
    }

    public final static <M extends Map<K, C>, C extends Collection<E>, K, E> GroupValidator<M, C, K, E> of(M map) {
        return new GroupValidator<>(map);
    }

    /*
     * -----------------------------------------------------
     * requires
     * -----------------------------------------------------
     */

    @Override
    public GroupValidator<M, C, K, E> requireAtLeastCountOf(
        BiPredicate<? super K, CollectValidator<C, E>> tester, int count, String message
    ) {
        return ifCondition(value -> {
            int amount = 0;
            for (Map.Entry<K, C> item : value.entrySet()) {
                if (tester.test(item.getKey(), new CollectValidator<>(
                    item.getValue(), ensureMessages(), getSeparator(), isImmediate()
                )) && (++amount >= count)) {
                    return this;
                }
            }
            return amount < count ? createMsgAtLeast(message, count) : this;
        });
    }

    @Override
    public GroupValidator<M, C, K, E> requireAtMostCountOf(
        BiPredicate<? super K, CollectValidator<C, E>> tester, int count, String message
    ) {
        return ifCondition(value -> {
            int amount = 0;
            for (Map.Entry<K, C> item : value.entrySet()) {
                if (tester.test(item.getKey(), new CollectValidator<>(
                    item.getValue(), ensureMessages(), getSeparator(), isImmediate()
                )) && (++amount > count)) {
                    return createMsgAtMost(message, count);
                }
            }
            return amount > count ? createMsgAtMost(message, count) : this;
        });
    }

    @Override
    public GroupValidator<M, C, K, E> requireCountOf(
        BiPredicate<? super K, CollectValidator<C, E>> tester, int count, String message
    ) {
        return ifCondition(value -> {
            int amount = 0;
            for (Map.Entry<K, C> item : value.entrySet()) {
                if (tester.test(item.getKey(), new CollectValidator<>(
                    item.getValue(), ensureMessages(), getSeparator(), isImmediate()
                )) && (++amount > count)) {
                    return createMsgOfCount(message, count);
                }
            }
            return amount > count ? createMsgOfCount(message, count) : this;
        });
    }
}
