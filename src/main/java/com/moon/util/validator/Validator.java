package com.moon.util.validator;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 对象多条件验证器：
 * <p>
 * 有时候在验证对象时，需要统一验证后，统一返回，而不是某个条件不符合便立即返回，
 * 此工具便是针对这样的场景设计的。
 * <p>
 * 非空：{@link #requireNonNull(String)}
 * <p>
 * 前置条件：{@link #condition(Predicate)}，前置条件会为紧接在后面的一系列验证规则
 * 添加一个“拦截器”，只有在前置条件成立的情况下，后续的条件（直到{@link #end()}之间）才会执行验证，
 * 否则将跳过{@link #condition(Predicate)}与{@link #end()}之间的验证。
 *
 * @author benshaoye
 */
public class Validator<T> extends BaseValidator<T, Validator<T>, Validator<T>> {

    public Validator(T value) {
        this(value, null, SEPARATOR, false);
    }

    Validator(T value, List<String> messages, String separator, boolean immediate) {
        super(value, messages, separator, immediate);
    }

    public final static <T> Validator<T> of(T object) {
        return new Validator<>(object);
    }

    /**
     * 当待验证对象为 null 时，返回默认值的新对象
     *
     * @param defaultSupplier
     * @return
     */
    @Override
    public Validator<T> defaultIfNull(Supplier<T> defaultSupplier) {
        return value == null ? Validator.of(defaultSupplier.get()) : this;
    }

    /**
     * 前置条件；
     * 用于当满足某一条件时，需要验证后面直到清除前置条件为止的条件
     * <p>
     * {@link #condition(Predicate)} 与 {@link #end()}组成一个 “验证条件区间”，
     * 当 {@link #condition(Predicate)} 满足条件时，两者之间的条件会执行验证，
     * 否则跳过不执行两者之间的验证条件。
     * <p>
     * 一般而言，一个{@link #condition(Predicate)}必须对应一个{@link #end()}，形成闭环;
     * 闭环与闭环之间可以嵌套。
     * <p>
     * 使用上如果出现交叉，未关闭的前置条件虽不一定会出错，但为了良好的可维护性，不推荐这样使用
     *
     * @param tester
     * @return
     */
    public final Validator<T> condition(Predicate<T> tester) {
        return tester.test(value) ? this : new NonValidator(value);
    }

    /*
     * -----------------------------------------------------
     * condition
     * -----------------------------------------------------
     */

    private final class NonValidator extends Validator<T> {
        NonValidator(T value) {
            super(value, null, null, false);
        }

        @Override
        public Validator<T> require(Predicate t, String m) {
            return this;
        }

        @Override
        public Validator<T> require(Predicate t) {
            return this;
        }

        @Override
        public Validator<T> requireNonNull() {
            return this;
        }

        @Override
        public Validator<T> requireNonNull(String m) {
            return this;
        }

        @Override
        public Validator<T> end() {
            return Validator.this;
        }
    }
}
