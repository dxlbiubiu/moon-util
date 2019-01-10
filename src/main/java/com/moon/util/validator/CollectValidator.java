package com.moon.util.validator;

import com.moon.util.CollectUtil;
import com.moon.util.GroupUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 集合验证器：
 * 首先请参考{@link Validator}文档。
 * <p>
 * 集合验证器可用于验证一个集合里面的项应该满足什么样的约束，
 * 比如，
 * 符合某些条件的项最多只能有多少项或最少只能有多少项目；
 * 必须包含些什么项，或不能包含什么项等。
 * <p>
 * 为什么会想到写这个工具呢？
 * 在一次验证家人关系的业务中，企业的员工所关联的家属都会分配自己的角色，
 * 如：
 * 配偶要求只能有一位{@link #requireAtMostOne(Predicate)}
 * 同一个人不能购买相同产品{@link #requireOnGrouped(Function, Consumer)}
 *
 * @author benshaoye
 */
public class CollectValidator<C extends Collection<E>, E> extends Validator<C> {

    public CollectValidator(C collect) {
        super(collect);
    }

    CollectValidator(C collect, CollectValidator<C, E> prevValidator,
                     List<String> messages, String separator, boolean immediate) {
        super(collect, prevValidator, messages, separator, immediate);
    }

    public static <C extends Collection<E>, E> CollectValidator<C, E> of(C collect) {
        return new CollectValidator<>(collect);
    }

    @Override
    public CollectValidator<C, E> transform(Function<C, C> transformer) {
        return CollectValidator.of(transformer.apply(value));
    }

    @Override
    public CollectValidator<C, E> defaultIfNull(C defaultValue) {
        return value == null ? CollectValidator.of(defaultValue) : this;
    }

    @Override
    public CollectValidator<C, E> defaultIfNull(Supplier<C> supplier) {
        return value == null ? CollectValidator.of(supplier.get()) : this;
    }

    @Override
    public CollectValidator<C, E> preset(Consumer<C> consumer) {
        super.preset(consumer);
        return this;
    }

    @Override
    public CollectValidator<C, E> setImmediate(boolean immediate) {
        super.setImmediate(immediate);
        return this;
    }

    @Override
    public CollectValidator<C, E> setSeparator(String separator) {
        super.setSeparator(separator);
        return this;
    }

    @Override
    public CollectValidator<C, E> condition(Predicate<C> tester) {
        return tester.test(value)
            ? new CollectValidator<>(value, this, messages, separator, immediate)
            : new NonValidator<>(value, this, messages, separator, immediate);
    }

    static final class NonValidator<C extends Collection<E>, E> extends CollectValidator<C, E> {
        NonValidator(C c, CollectValidator<C, E> v, List<String> ms, String s, boolean i) {
            super(c, v, ms, s, i);
        }

        @Override
        public CollectValidator<C, E> requireNonNull() {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireNonNull(String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> require(Predicate<C> t, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireEvery(Predicate<E> t) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireEvery(Predicate<E> t, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtLeastOne(Predicate<E> t) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtLeastOne(Predicate<E> t, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtLeastCountOf(Predicate<E> t, int c) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtLeastCountOf(Predicate<E> t, int c, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtMostOne(Predicate<E> t) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtMostOne(Predicate<E> t, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtMostCountOf(Predicate<E> t, int c) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireAtMostCountOf(Predicate<E> t, int c, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireNone(Predicate<E> t) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireNone(Predicate<E> t, String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireUnique() {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireUnique(String m) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireCountOf(Predicate<E> t, int c) {
            return this;
        }

        @Override
        public CollectValidator<C, E> requireCountOf(Predicate<E> t, int c, String m) {
            return this;
        }

        @Override
        public <K> CollectValidator<C, E> requireOnGrouped(Function<? super E, ? extends K> g, Consumer<CollectValidator<C, E>> c) {
            return this;
        }
    }

    @Override
    public CollectValidator<C, E> end() {
        return (CollectValidator<C, E>) super.end();
    }

    @Override
    public CollectValidator<C, E> requireNonNull() {
        super.requireNonNull();
        return this;
    }

    @Override
    public CollectValidator<C, E> requireNonNull(String message) {
        super.requireNonNull(message);
        return this;
    }

    @Override
    public CollectValidator<C, E> require(Predicate<C> checker, String message) {
        super.require(checker, message);
        return this;
    }

    /**
     * 根据条件分组，分组后的每一个列表要单独验证
     *
     * @param grouper
     * @param consumer
     * @param <K>
     * @return
     */
    public <K> CollectValidator<C, E> requireOnGrouped(
        Function<? super E, ? extends K> grouper, Consumer<CollectValidator<C, E>> consumer
    ) {
        final List<String> messages = this.ensureMessages();
        GroupUtil.groupBy(value, grouper).forEach((key, item) -> consumer.accept(
            new CollectValidator(item, this, messages, separator, immediate)
        ));
        return this;
    }

    /**
     * 要求没有重复项
     *
     * @return
     */
    public CollectValidator<C, E> requireUnique() {
        return requireUnique(DEFAULT_MSG);
    }

    public CollectValidator<C, E> requireUnique(String message) {
        if (new HashSet<>(value).size() < value.size()) {
            createMsg(message);
        }
        return this;
    }

    /**
     * 要求有指定数目的项匹配
     *
     * @param tester
     * @param count
     * @return
     */
    public CollectValidator<C, E> requireCountOf(Predicate<E> tester, int count) {
        return this.requireCountOf(tester, count, DEFAULT_MSG);
    }

    public CollectValidator<C, E> requireCountOf(Predicate<E> tester, int count, String message) {
        int i = 0;
        if (value != null) {
            for (E item : value) {
                if (tester.test(item) && (++i > count)) {
                    createMsg(message);
                    return this;
                }
            }
        }
        if (i < count) {
            createMsg(message);
        }
        return this;
    }

    /**
     * 要求集合内每一项均符合条件
     *
     * @param tester
     * @return
     */
    public CollectValidator<C, E> requireEvery(Predicate<E> tester) {
        return requireEvery(tester, DEFAULT_MSG);
    }

    public CollectValidator<C, E> requireEvery(Predicate<E> tester, String message) {
        return requireAtLeastCountOf(tester, CollectUtil.size(value), message);
    }

    /**
     * 要求集合内至少有一项符合条件
     *
     * @param tester
     * @return
     */
    public CollectValidator<C, E> requireAtLeastOne(Predicate<E> tester) {
        return requireAtLeastCountOf(tester, 1);
    }

    public CollectValidator<C, E> requireAtLeastOne(Predicate<E> tester, String message) {
        return requireAtLeastCountOf(tester, 1, message);
    }

    /**
     * 要求集合内至少有指定项符合条件
     *
     * @param tester
     * @param count
     * @return
     */
    public CollectValidator<C, E> requireAtLeastCountOf(Predicate<E> tester, int count) {
        return requireAtLeastCountOf(tester, count, DEFAULT_MSG);
    }

    public CollectValidator<C, E> requireAtLeastCountOf(Predicate<E> tester, int count, String message) {
        int i = 0;
        if (value != null) {
            for (E item : value) {
                if (tester.test(item) && (++i >= count)) {
                    return this;
                }
            }
        }
        if (i < count) {
            createMsg(formatMsg(message, count, QuantityType.AtLeast));
        }
        return this;
    }

    /**
     * 要求集合内最多有一项符合条件
     *
     * @param tester
     * @return
     */
    public CollectValidator<C, E> requireAtMostOne(Predicate<E> tester) {
        return requireAtMostCountOf(tester, 1);
    }

    public CollectValidator<C, E> requireAtMostOne(Predicate<E> tester, String message) {
        return requireAtMostCountOf(tester, 1, message);
    }

    /**
     * 要求集合内最多有指定项符合条件
     *
     * @param tester
     * @param count
     * @return
     */
    public CollectValidator<C, E> requireAtMostCountOf(Predicate<E> tester, int count) {
        return requireAtMostCountOf(tester, count, DEFAULT_MSG);
    }

    public CollectValidator<C, E> requireAtMostCountOf(Predicate<E> tester, int count, String message) {
        int i = 0;
        if (value != null) {
            for (E item : value) {
                if (tester.test(item) && (++i > count)) {
                    createMsg(formatMsg(message, count, QuantityType.AtMost));
                    return this;
                }
            }
        }
        if (i > count) {
            createMsg(formatMsg(message, count, QuantityType.AtMost));
        }
        return this;
    }

    /**
     * 要求集合内任何一项均不能符合条件
     *
     * @param tester
     * @return
     */
    public CollectValidator<C, E> requireNone(Predicate<E> tester) {
        return requireAtMostCountOf(tester, 0);
    }

    public CollectValidator<C, E> requireNone(Predicate<E> tester, String message) {
        return requireAtMostCountOf(tester, 0, message);
    }

    @Override
    public CollectValidator<C, E> ifValid(Consumer<C> consumer) {
        super.ifValid(consumer);
        return this;
    }

    @Override
    public CollectValidator<C, E> ifInvalid(Consumer<C> consumer) {
        super.ifInvalid(consumer);
        return this;
    }
}
