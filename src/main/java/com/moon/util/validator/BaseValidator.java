package com.moon.util.validator;

import com.moon.enums.Predicates;
import com.moon.lang.JoinerUtil;
import com.moon.lang.ObjectUtil;
import com.moon.lang.StringUtil;
import com.moon.util.CollectUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
abstract class BaseValidator<T, IMPL extends BaseValidator, IMPL1 extends BaseValidator>
    extends Value<T> implements Cloneable, Serializable, IValidator<T, IMPL>, Supplier<T> {

    static final long serialVersionUID = 1L;

    final static String SEPARATOR = ", ";

    private List<String> messages;

    private boolean immediate;

    private String separator;

    BaseValidator(T value, List<String> messages, String separator, boolean immediate) {
        super(value);
        this.separator = separator;
        this.immediate = immediate;
        this.messages = messages;
    }

    /*
     * -----------------------------------------------------------------
     * inner methods
     * -----------------------------------------------------------------
     */

    /**
     * 添加一条错误信息，如果设置了立即结束将抛出异常
     *
     * @param message
     * @return
     */
    final <O> O createMsg(String message) {
        if (immediate) {
            throw new IllegalArgumentException(message);
        } else {
            ensureMessages().add(message);
        }
        return (O) this;
    }

    final <O> O createMsgAtMost(String message, int count) {
        return createMsg(message == null ? StringUtil.format("最多只能有 {} 项符合条件", count) : message);
    }

    final <O> O createMsgAtLeast(String message, int count) {
        return createMsg(message == null ? StringUtil.format("至少需要有 {} 项符合条件", count) : message);
    }

    final <O> O createMsg(boolean tested, String message) {
        return tested ? (O) this : createMsg(message);
    }

    final boolean isEmpty() {
        return CollectUtil.isEmpty(messages);
    }

    final List<String> ensureMessages() {
        return messages == null ? (messages = new ArrayList<>()) : messages;
    }

    /*
     * -----------------------------------------------------------------
     * public info methods
     * -----------------------------------------------------------------
     */

    public final List<String> getMessages() {
        return new ArrayList<>(ensureMessages());
    }

    public final boolean isImmediate() {
        return immediate;
    }

    public final String getSeparator() {
        return separator;
    }

    /**
     * 获取错误信息，用默认分隔符
     *
     * @return
     */
    public final String getMessage() {
        return getMessage(StringUtil.defaultIfNull(separator, SEPARATOR));
    }

    public final String getMessage(String separator) {
        return JoinerUtil.join(ensureMessages(), separator);
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
        return Objects.equals(value, ((BaseValidator) obj).value);
    }

    /*
     * -----------------------------------------------------------------
     * get value or source
     * -----------------------------------------------------------------
     */

    /**
     * 验证通过，返回该对象，否则返回 null
     *
     * @return
     */
    public final T nullIfInvalid() {
        return defaultIfInvalid((T) null);
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
     * 验证通过，返回该对象，否则返回指定的默认值
     *
     * @param defaultSupplier
     * @return
     */
    public final T defaultIfInvalid(Supplier<T> defaultSupplier) {
        return isEmpty() ? value : defaultSupplier.get();
    }

    /**
     * 验证通过，返回该对象，否则抛出异常
     *
     * @return
     */
    @Override
    public final T get() {
        if (isEmpty()) {
            return value;
        }
        throw new IllegalArgumentException(getMessage());
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
        throw new IllegalArgumentException(errorMessage);
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

    /*
     * -----------------------------------------------------------------
     * defaults and presets
     * -----------------------------------------------------------------
     */

    abstract IMPL defaultIfNull(Supplier<T> defaultSupplier);

    /**
     * 可用于在后面条件验证前预先设置一部分默认值
     *
     * @param consumer
     * @return
     */
    public final IMPL preset(Consumer<T> consumer) {
        consumer.accept(value);
        return current();
    }

    /**
     * 设置是否立即终止
     *
     * @param immediate
     * @return
     */
    public final IMPL setImmediate(boolean immediate) {
        this.immediate = immediate;
        return current();
    }

    /**
     * 设置错误信息分隔符，不能为 null
     *
     * @param separator
     * @return
     */
    public final IMPL setSeparator(String separator) {
        this.separator = separator;
        return current();
    }

    public final IMPL ifValid(Consumer<T> consumer) {
        if (isEmpty()) {
            consumer.accept(value);
        }
        return current();
    }

    public final IMPL ifInvalid(Consumer<T> consumer) {
        if (!isEmpty()) {
            consumer.accept(value);
        }
        return current();
    }

    final IMPL current() {
        return (IMPL) this;
    }

    BaseValidator validator;

    public IMPL1 end() {
        return (IMPL1) ObjectUtil.defaultIfNull(validator, this);
    }

    /*
     * -----------------------------------------------------------------
     * requires
     * -----------------------------------------------------------------
     */

    @Override
    public IMPL require(Predicate<? super T> tester, String message) {
        return createMsg(tester.test(value), message);
    }
}
