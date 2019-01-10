package com.moon.util.validator;

import com.moon.enums.Const;
import com.moon.lang.JoinerUtil;
import com.moon.lang.ObjectUtil;
import com.moon.lang.StringUtil;
import com.moon.util.CollectUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 对象多条件验证器
 * <p>
 * 业务中有时候会需要对一个对象进行多字段、多条件验证，
 * 然后统一返回验证异常或通过验证，而不是验证一个不通过条件便立即返回对应异常
 * <p>
 * 此类便是针对这样的场景设计的，通过{@link #require(Predicate, String)}方法添加验证规则和错误信息
 * <p>
 * {@link #get()}方法会在验证通过的时候返回这个对象，否则抛出没有通过验证的异常，也可以自定义异常信息
 * <p>
 * 此外，此类还设置了前置条件验证{@link #condition(Predicate)}，介于前置条件和{@link #end()}之间
 * 的验证规则只在前置条件成立时才执行验证，否则将跳过中间的所有验证，
 * 另外，一个前置条件必须要{@link #end()}结束，虽然在某些“取巧”的情况下，可以不结束也不影响结果；
 * 但为了统一，哪怕没有任何多余的操作也建议以{@link #end()}结束.
 *
 * @author benshaoye
 */
public class Validator<T> implements Cloneable, Serializable {

    static final long serialVersionUID = 1L;
    /**
     * 默认错误信息分隔符
     */
    private final static String SEPARATOR = ", ";

    /**
     * 默认错误信息
     */
    final static String DEFAULT_MSG = Const.EMPTY;
    /**
     * 是否在验证错误时立即终止
     */
    boolean immediate;
    /**
     * 错误信息分隔符
     */
    String separator;
    /**
     * 错误信息
     */
    List<String> messages;
    /**
     * 待验证对象
     */
    final T value;

    final Validator<T> prevValidator;

    public Validator(T value) {
        this(value, null, null, SEPARATOR, false);
    }

    Validator(T value, Validator<T> prevValidator, List<String> messages, String separator, boolean immediate) {
        this.prevValidator = prevValidator;
        this.immediate = immediate;
        this.separator = separator;
        this.messages = messages;
        this.value = value;
    }

    public final static <T> Validator<T> of(T object) {
        return new Validator<>(object);
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    public final List<String> getMessages() {
        return Collections.unmodifiableList(ensureMessages());
    }

    final List<String> ensureMessages() {
        return messages == null ? (messages = new ArrayList<>()) : messages;
    }

    /**
     * 获取错误信息，用默认分隔符
     *
     * @return
     */
    public final String getMessage() {
        return getMessage(StringUtil.defaultIfNull(separator, SEPARATOR));
    }

    /**
     * 获取错误信息，用指定分隔符分割
     *
     * @param separator
     * @return
     */
    public final String getMessage(String separator) {
        return messages == null ? "" : JoinerUtil.join(messages, separator);
    }

    /**
     * 设置是否立即终止
     *
     * @param immediate
     * @return
     */
    public Validator<T> setImmediate(boolean immediate) {
        this.immediate = immediate;
        return this;
    }

    /**
     * 设置错误信息分隔符，不能为 null
     *
     * @param separator
     * @return
     */
    public Validator<T> setSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * 添加一条错误信息，如果设置了立即结束将抛出异常
     *
     * @param message
     * @return
     */
    Validator<T> createMsg(String message) {
        if (immediate) {
            throw new IllegalArgumentException(message);
        } else {
            ensureMessages().add(message);
        }
        return this;
    }

    /**
     * 验证通过，返回该对象，否则返回 null
     *
     * @return
     */
    public final T nullIfInvalid() {
        return defaultIfInvalid(null);
    }

    /**
     * 验证通过，返回该对象，否则返回指定的默认值
     *
     * @param defaultValue
     * @return
     */
    public final T defaultIfInvalid(T defaultValue) {
        return isEmpty() ? value : defaultValue;
    }

    /**
     * 验证通过，返回该对象，否则抛出异常
     *
     * @return
     */
    public final T get() {
        if (isEmpty()) {
            return value;
        }
        throw new IllegalStateException(getMessage());
    }

    /**
     * 验证通过，返回该对象，否则抛出指定信息异常
     *
     * @param errorMessage
     * @return
     */
    public final T get(String errorMessage) {
        if (isEmpty()) {
            return value;
        }
        throw new IllegalStateException(errorMessage);
    }

    /**
     * 验证通过，返回该对象，否则抛出指定异常
     *
     * @param exception
     * @param <EX>
     * @return
     * @throws EX
     */
    public final <EX extends Throwable> T get(Function<String, EX> exception) throws EX {
        if (isEmpty()) {
            return value;
        }
        throw exception.apply(toString());
    }

    /**
     * 转换
     *
     * @param transformer
     * @return
     */
    public Validator<T> transform(Function<T, T> transformer) {
        return Validator.of(transformer.apply(value));
    }

    /**
     * 当待验证对象为 null 时，返回默认值的新对象
     *
     * @param defaultValue
     * @return
     */
    public Validator<T> defaultIfNull(T defaultValue) {
        return value == null ? Validator.of(defaultValue) : this;
    }

    /**
     * 当待验证对象为 null 时，返回默认值的新对象
     *
     * @param defaultSupplier
     * @return
     */
    public Validator<T> defaultIfNull(Supplier<T> defaultSupplier) {
        return value == null ? Validator.of(defaultSupplier.get()) : this;
    }

    /**
     * 可用于在后面条件验证前预先设置一部分默认值
     *
     * @param consumer
     * @return
     */
    public Validator<T> preset(Consumer<T> consumer) {
        consumer.accept(value);
        return this;
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
    public Validator<T> condition(Predicate<T> tester) {
        return tester.test(value)
            ? new Validator<>(value, this, messages, separator, immediate)
            : new NonValidator<>(value, this, messages, separator, immediate);
    }

    static final class NonValidator<T> extends Validator<T> {
        NonValidator(T value, Validator<T> prevValidator, List<String> messages, String separator, boolean immediate) {
            super(value, prevValidator, messages, separator, immediate);
        }

        @Override
        public Validator<T> require(Predicate<T> checker, String message) {
            return this;
        }

        @Override
        public Validator<T> requireNonNull() {
            return this;
        }

        @Override
        public Validator<T> requireNonNull(String message) {
            return this;
        }
    }

    /**
     * 清除前置条件
     *
     * @return
     */
    public Validator<T> end() {
        return ObjectUtil.defaultIfNull(prevValidator, this);
    }

    public Validator<T> requireNonNull() {
        return requireNonNull(Const.EMPTY);
    }

    public Validator<T> requireNonNull(String message) {
        return value == null ? createMsg(message) : this;
    }

    public Validator<T> require(Predicate<T> checker, String message) {
        return checker.test(value) ? this : createMsg(message);
    }

    public Validator<T> ifValid(Consumer<T> consumer) {
        if (isEmpty()) {
            consumer.accept(value);
        }
        return this;
    }

    public Validator<T> ifInvalid(Consumer<T> consumer) {
        if (!isEmpty()) {
            consumer.accept(value);
        }
        return this;
    }

    @Override
    public final String toString() {
        return getMessage();
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return Objects.equals(value, ((Validator) obj).value);
    }

    final String formatMsg(String message, int count, QuantityType type) {
        return message == null ? new StringBuilder(type.str).append(count).append(" 项符合条件").toString() : message;
    }

    final boolean isEmpty() {
        return CollectUtil.isEmpty(messages);
    }
}
