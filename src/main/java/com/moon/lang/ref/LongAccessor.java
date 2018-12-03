package com.moon.lang.ref;

import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class LongAccessor {

    private long value;

    public LongAccessor() {
    }

    public LongAccessor(long value) {
        this.set(value);
    }

    public static LongAccessor of() {
        return new LongAccessor();
    }

    public static LongAccessor of(long value) {
        return new LongAccessor(value);
    }

    public LongAccessor set(long value) {
        this.value = value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    public long get() {
        return value;
    }

    public long getAndIncrement() {
        return value++;
    }

    public long incrementAndGet() {
        return ++value;
    }

    public long getAndIncrement(long value) {
        return this.value += value;
    }

    public long incrementAndGet(long value) {
        long now = this.value;
        this.value += value;
        return now;
    }

    public LongAccessor decrement(long value) {
        this.value -= value;
        return this;
    }

    public LongAccessor decrement() {
        return decrement(1);
    }

    public long decrementAndGet() {
        return decrement().get();
    }

    public long getAndDecrement() {
        long num = get();
        this.decrement();
        return num;
    }

    public long decrementAndGet(long value) {
        return decrement(value).get();
    }

    public long getAndDecrement(long value) {
        long num = get();
        this.decrement(value);
        return num;
    }

    /*
     * ------------------------------------------------------------
     * adds
     * ------------------------------------------------------------
     */

    public LongAccessor increment() {
        return this.increment(1);
    }

    public LongAccessor increment(int value) {
        this.value += value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * operations
     * ------------------------------------------------------------
     */

    public LongAccessor ifEq(int value, LongConsumer consumer) {
        if (this.value == value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifGt(int value, LongConsumer consumer) {
        if (this.value > value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifLt(int value, LongConsumer consumer) {
        if (this.value < value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifGtOrEq(int value, LongConsumer consumer) {
        if (this.value >= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor ifLtOrEq(int value, LongConsumer consumer) {
        if (this.value <= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public LongAccessor use(LongConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    public LongAccessor compute(ToLongFunction<Long> computer) {
        return set(computer.applyAsLong(value));
    }

    public <T> T transform(LongFunction<T> transformer) {
        return transformer.apply(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
