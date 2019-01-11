package com.moon.util.validator;

import com.moon.util.CollectUtil;
import com.moon.util.FilterUtil;
import com.moon.util.GroupUtil;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 集合验证器：
 * 首先请参考{@link Validator}文档。CollectValidator 继承了 Validator；具备基本的对象验证功能。
 * <p>
 * 前置条件：{@link #condition(Predicate)}，以{@link #end()}结束
 * <p>
 * 分组验证：{@link #groupBy(Function)}，先按条件分组然后分别验证，以{@link #end()}结束
 * <p>
 * 过滤验证：{@link #filter(Predicate)}，对过滤后的集合进行验证，以{@link #end()}结束
 *
 * @author benshaoye
 */
public class CollectValidator<C extends Collection<E>, E>
    extends BaseValidator<C, CollectValidator<C, E>, CollectValidator<C, E>>
    implements ICollectValidator<C, E, CollectValidator<C, E>> {

    public CollectValidator(C value) {
        this(value, null, SEPARATOR, false);
    }

    CollectValidator(C value, List<String> messages, String separator, boolean immediate) {
        super(value, messages, separator, immediate);
    }

    public final static <C extends Collection<E>, E> CollectValidator<C, E> of(C collect) {
        return new CollectValidator<>(collect);
    }

    @Override
    public CollectValidator<C, E> defaultIfNull(Supplier<C> defaultSupplier) {
        return value == null ? new CollectValidator<>(defaultSupplier.get()) : this;
    }

    /*
     * -----------------------------------------------------
     * requires
     * -----------------------------------------------------
     */

    @Override
    public CollectValidator<C, E> requireEvery(Predicate<? super E> tester, String message) {
        return requireAtLeastCountOf(tester, value.size(), message);
    }

    @Override
    public CollectValidator<C, E> requireAtLeastCountOf(Predicate<? super E> tester, int count, String message) {
        int amount = 0;
        for (E item : value) {
            if (tester.test(item) && (++amount >= count)) {
                return this;
            }
        }
        return amount < count ? createMsg(formatMsg(message, count, QuantityType.AtLeast)) : this;
    }

    @Override
    public CollectValidator<C, E> requireAtMostCountOf(Predicate<? super E> tester, int count, String message) {
        int amount = 0;
        for (E item : value) {
            if (tester.test(item) && (++amount > count)) {
                return createMsg(formatMsg(message, count, QuantityType.AtMost));
            }
        }
        return amount > count ? createMsg(formatMsg(message, count, QuantityType.AtMost)) : this;
    }

    /*
     * -----------------------------------------------------
     * transform
     * -----------------------------------------------------
     */

    public <O> CollectValidator<Collection<O>, O> transform(Function<? super E, O> transformer) {
        CollectValidator<Collection<O>, O> v = CollectValidator.of(CollectUtil.map(value, transformer));
        v.validator = this;
        return v;
    }

    /*
     * -----------------------------------------------------
     * group
     * -----------------------------------------------------
     */

    public <K> GroupedValidator<Map<K, Collection<E>>, K> groupBy(Function<? super E, ? extends K> grouper) {
        return new GroupedValidator<>(GroupUtil.groupBy(value, grouper),
            ensureMessages(), getSeparator(), isImmediate());
    }

    public class GroupedValidator<M extends Map<K, Collection<E>>, K>
        extends BaseValidator<M, GroupedValidator<M, K>, CollectValidator<C, E>>
        implements IKeyedValidator<M, Collection<E>, K, E, GroupedValidator<M, K>> {

        GroupedValidator(M value, List<String> messages, String separator, boolean immediate) {
            super(value, messages, separator, immediate);
        }

        @Override
        GroupedValidator<M, K> defaultIfNull(Supplier<M> defaultSupplier) {
            return value == null ? new GroupedValidator<>(defaultSupplier.get(),
                ensureMessages(), getSeparator(), isImmediate()) : this;
        }

        @Override
        public GroupedValidator<M, K> requireEvery(
            BiPredicate<? super K, CollectValidator<Collection<E>, E>> tester, String message) {
            return requireAtLeastCountOf(tester, value.size(), message);
        }

        @Override
        public GroupedValidator<M, K> requireAtLeastCountOf(
            BiPredicate<? super K, CollectValidator<Collection<E>, E>> tester, final int count, String message
        ) {
            int amount = 0;
            for (Map.Entry<K, Collection<E>> item : value.entrySet()) {
                if (tester.test(item.getKey(), new CollectValidator<>(
                    item.getValue(), CollectValidator.this.ensureMessages(),
                    CollectValidator.this.getSeparator(), CollectValidator.this.isImmediate()
                )) && (++amount >= count)) {
                    return this;
                }
            }
            return amount < count ? createMsg(formatMsg(message, count, QuantityType.AtLeast)) : this;
        }

        @Override
        public GroupedValidator<M, K> requireAtMostCountOf(
            BiPredicate<? super K, CollectValidator<Collection<E>, E>> tester, final int count, String message
        ) {
            int amount = 0;
            for (Map.Entry<K, Collection<E>> item : value.entrySet()) {
                if (tester.test(item.getKey(), new CollectValidator<>(
                    item.getValue(), CollectValidator.this.ensureMessages(),
                    CollectValidator.this.getSeparator(), CollectValidator.this.isImmediate()
                )) && (++amount > count)) {
                    return createMsg(formatMsg(message, count, QuantityType.AtMost));
                }
            }
            return amount > count ? createMsg(formatMsg(message, count, QuantityType.AtMost)) : this;
        }

        @Override
        public CollectValidator<C, E> end() {
            return CollectValidator.this;
        }
    }

    /*
     * -----------------------------------------------------
     * filter
     * -----------------------------------------------------
     */

    public <C1 extends Collection<E>> FilterCollectValidator<C1, E> filter(Predicate<? super E> tester) {
        ArrayList<E> filtered = FilterUtil.filter(value, tester, new ArrayList<>());
        return new FilterCollectValidator(filtered, ensureMessages(), getSeparator(), isImmediate());
    }

    public class FilterCollectValidator<C1 extends Collection<E1>, E1>
        extends BaseValidator<C1, FilterCollectValidator<C1, E1>, CollectValidator<C, E>>
        implements ICollectValidator<C1, E1, FilterCollectValidator<C1, E1>> {

        private FilterCollectValidator(C1 value, List<String> messages, String separator, boolean immediate) {
            super(value, messages, separator, immediate);
        }

        @Override
        FilterCollectValidator<C1, E1> defaultIfNull(Supplier<C1> defaultSupplier) {
            return value == null ? new FilterCollectValidator<>(defaultSupplier.get(),
                ensureMessages(), getSeparator(), isImmediate()) : this;
        }

        @Override
        public FilterCollectValidator<C1, E1> requireEvery(
            Predicate<? super E1> tester, String message) {
            return requireAtLeastCountOf(tester, value.size(), message);
        }

        @Override
        public FilterCollectValidator<C1, E1> requireAtLeastCountOf(
            Predicate<? super E1> tester, int count, String message) {
            int amount = 0;
            for (E1 item : value) {
                if (tester.test(item) && (++amount >= count)) {
                    return this;
                }
            }
            return amount < count ? createMsg(formatMsg(message, count, QuantityType.AtLeast)) : this;
        }

        @Override
        public FilterCollectValidator<C1, E1> requireAtMostCountOf(
            Predicate<? super E1> tester, int count, String message) {
            int amount = 0;
            for (E1 item : value) {
                if (tester.test(item) && (++amount > count)) {
                    return createMsg(formatMsg(message, count, QuantityType.AtMost));
                }
            }
            return amount > count ? createMsg(formatMsg(message, count, QuantityType.AtMost)) : this;
        }

        @Override
        public CollectValidator<C, E> end() {
            return CollectValidator.this;
        }
    }

    /*
     * -----------------------------------------------------
     * condition
     * -----------------------------------------------------
     */

    public final CollectValidator<C, E> condition(Predicate<C> tester) {
        return tester.test(value) ? this : new NonValidator(value);
    }

    private final class NonValidator extends CollectValidator<C, E> {
        NonValidator(C value) {
            super(value, null, null, false);
        }

        @Override
        public <K> GroupedValidator<Map<K, Collection<E>>, K> groupBy(Function<? super E, ? extends K> grouper) {
            return new NonGroupedValidator<>(Collections.EMPTY_MAP);
        }

        @Override
        public <C1 extends Collection<E>> FilterCollectValidator<C1, E> filter(Predicate<? super E> tester) {
            return new NonFilterCollectValidator(value instanceof List ? Collections.EMPTY_LIST : Collections.EMPTY_SET);
        }

        @Override
        public CollectValidator<C, E> requireEvery(Predicate t, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtLeastCountOf(Predicate t, int c, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtMostCountOf(Predicate t, int c, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> end() {
            return CollectValidator.this;
        }
    }

    private final class NonGroupedValidator<M extends Map<K, Collection<E>>, K> extends GroupedValidator<M, K> {
        NonGroupedValidator(M value) {
            super(value, null, null, false);
        }

        @Override
        public GroupedValidator<M, K> requireEvery(BiPredicate t, String m) {
            return this;
        }

        @Override
        public GroupedValidator<M, K> requireAtLeastCountOf(BiPredicate t, final int c, String m) {
            return this;
        }

        @Override
        public GroupedValidator<M, K> requireAtMostCountOf(BiPredicate t, final int c, String m) {
            return this;
        }
    }

    public final class NonFilterCollectValidator<C1 extends Collection<E1>, E1> extends FilterCollectValidator<C1, E1> {
        private NonFilterCollectValidator(C1 value) {
            super(value, null, null, false);
        }

        @Override
        public FilterCollectValidator<C1, E1> requireEvery(Predicate t, String m) {
            return this;
        }

        @Override
        public FilterCollectValidator<C1, E1> requireAtLeastCountOf(Predicate t, int c, String m) {
            return this;
        }

        @Override
        public FilterCollectValidator<C1, E1> requireAtMostCountOf(Predicate t, int c, String m) {
            return this;
        }
    }
}
