package com.moon.util.validator;

import com.moon.util.MapUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.*;

/**
 * @author benshaoye
 */
public class MapValidator<M extends Map<K, V>, K, V> extends Validator<M> {
    public MapValidator(M value) {
        super(value);
    }

    MapValidator(M value, Validator<M> prevValidator, List<String> messages, String separator, boolean immediate) {
        super(value, prevValidator, messages, separator, immediate);
    }

    public final static <M extends Map<K, V>, K, V> MapValidator<M, K, V> of(M map) {
        return new MapValidator<>(map);
    }

    @Override
    public MapValidator<M, K, V> setImmediate(boolean immediate) {
        super.setImmediate(immediate);
        return this;
    }

    @Override
    public MapValidator<M, K, V> setSeparator(String separator) {
        super.setSeparator(separator);
        return this;
    }

    @Override
    public MapValidator<M, K, V> transform(Function<M, M> transformer) {
        super.transform(transformer);
        return this;
    }

    @Override
    public MapValidator<M, K, V> defaultIfNull(M defaultValue) {
        super.defaultIfNull(defaultValue);
        return this;
    }

    @Override
    public MapValidator<M, K, V> defaultIfNull(Supplier<M> defaultSupplier) {
        super.defaultIfNull(defaultSupplier);
        return this;
    }

    @Override
    public MapValidator<M, K, V> preset(Consumer<M> consumer) {
        super.preset(consumer);
        return this;
    }

    @Override
    public MapValidator<M, K, V> ifValid(Consumer<M> consumer) {
        super.ifValid(consumer);
        return this;
    }

    @Override
    public MapValidator<M, K, V> ifInvalid(Consumer<M> consumer) {
        super.ifInvalid(consumer);
        return this;
    }

    @Override
    public MapValidator<M, K, V> requireNonNull() {
        super.requireNonNull();
        return this;
    }

    @Override
    public MapValidator<M, K, V> requireNonNull(String message) {
        super.requireNonNull(message);
        return this;
    }

    @Override
    public MapValidator<M, K, V> require(Predicate<M> checker, String message) {
        super.require(checker, message);
        return this;
    }

    /**
     * 要求没有重复项
     *
     * @return
     */
    public MapValidator<M, K, V> requireUnique() {
        return requireUnique(DEFAULT_MSG);
    }

    public MapValidator<M, K, V> requireUnique(String message) {
        if (new HashSet<>(value.values()).size() < value.size()) {
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
    public MapValidator<M, K, V> requireCountOf(BiPredicate<K, V> tester, int count) {
        return this.requireCountOf(tester, count, DEFAULT_MSG);
    }

    public MapValidator<M, K, V> requireCountOf(BiPredicate<K, V> tester, int count, String message) {
        int i = 0;
        if (value != null) {
            for (Map.Entry<K, V> entry : value.entrySet()) {
                if (tester.test(entry.getKey(), entry.getValue()) && (++i > count)) {
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
    public MapValidator<M, K, V> requireEvery(BiPredicate<K, V> tester) {
        return requireEvery(tester, DEFAULT_MSG);
    }

    public MapValidator<M, K, V> requireEvery(BiPredicate<K, V> tester, String message) {
        return requireAtLeastCountOf(tester, MapUtil.size(value), message);
    }

    /**
     * 要求集合内至少有一项符合条件
     *
     * @param tester
     * @return
     */
    public MapValidator<M, K, V> requireAtLeastOne(BiPredicate<K, V> tester) {
        return requireAtLeastCountOf(tester, 1);
    }

    public MapValidator<M, K, V> requireAtLeastOne(BiPredicate<K, V> tester, String message) {
        return requireAtLeastCountOf(tester, 1, message);
    }

    /**
     * 要求集合内至少有指定项符合条件
     *
     * @param tester
     * @param count
     * @return
     */
    public MapValidator<M, K, V> requireAtLeastCountOf(BiPredicate<K, V> tester, int count) {
        return requireAtLeastCountOf(tester, count, DEFAULT_MSG);
    }

    public MapValidator<M, K, V> requireAtLeastCountOf(BiPredicate<K, V> tester, int count, String message) {
        int i = 0;
        if (value != null) {
            for (Map.Entry<K, V> entry : value.entrySet()) {
                if (tester.test(entry.getKey(), entry.getValue()) && (++i >= count)) {
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
    public MapValidator<M, K, V> requireAtMostOne(BiPredicate<K, V> tester) {
        return requireAtMostCountOf(tester, 1);
    }

    public MapValidator<M, K, V> requireAtMostOne(BiPredicate<K, V> tester, String message) {
        return requireAtMostCountOf(tester, 1, message);
    }

    /**
     * 要求集合内最多有指定项符合条件
     *
     * @param tester
     * @param count
     * @return
     */
    public MapValidator<M, K, V> requireAtMostCountOf(BiPredicate<K, V> tester, int count) {
        return requireAtMostCountOf(tester, count, DEFAULT_MSG);
    }

    public MapValidator<M, K, V> requireAtMostCountOf(BiPredicate<K, V> tester, int count, String message) {
        int i = 0;
        if (value != null) {
            for (Map.Entry<K, V> entry : value.entrySet()) {
                if (tester.test(entry.getKey(), entry.getValue()) && (++i > count)) {
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
    public MapValidator<M, K, V> requireNone(BiPredicate<K, V> tester) {
        return requireAtMostCountOf(tester, 0);
    }

    public MapValidator<M, K, V> requireNone(BiPredicate<K, V> tester, String message) {
        return requireAtMostCountOf(tester, 0, message);
    }

    @Override
    public MapValidator<M, K, V> condition(Predicate<M> tester) {
        return tester.test(value)
            ? new MapValidator<>(value, this, messages, separator, immediate)
            : new NonValidator<>(value, this, messages, separator, immediate);
    }

    static final class NonValidator<M extends Map<K, V>, K, V> extends MapValidator<M, K, V> {

        NonValidator(M map, Validator<M> v, List<String> ms, String s, boolean i) {
            super(map, v, ms, s, i);
        }

        @Override
        public MapValidator<M, K, V> requireNonNull() {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireNonNull(String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> require(Predicate<M> t, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireUnique() {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireUnique(String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireCountOf(BiPredicate<K, V> t, int c) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireCountOf(BiPredicate<K, V> t, int c, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireEvery(BiPredicate<K, V> t) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireEvery(BiPredicate<K, V> t, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtLeastOne(BiPredicate<K, V> t) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtLeastOne(BiPredicate<K, V> t, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtLeastCountOf(BiPredicate<K, V> t, int c) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtLeastCountOf(BiPredicate<K, V> t, int c, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtMostOne(BiPredicate<K, V> t) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtMostOne(BiPredicate<K, V> t, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtMostCountOf(BiPredicate<K, V> t, int c) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireAtMostCountOf(BiPredicate<K, V> t, int c, String m) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireNone(BiPredicate<K, V> t) {
            return this;
        }

        @Override
        public MapValidator<M, K, V> requireNone(BiPredicate<K, V> t, String m) {
            return this;
        }
    }

    @Override
    public MapValidator<M, K, V> end() {
        return (MapValidator<M, K, V>) super.end();
    }
}
