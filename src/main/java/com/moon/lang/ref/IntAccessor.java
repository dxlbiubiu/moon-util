package com.moon.lang.ref;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public class IntAccessor {

    private int value;

    public IntAccessor() {
    }

    public IntAccessor(int value) {
        this.set(value);
    }

    public static IntAccessor of() {
        return new IntAccessor();
    }

    public static IntAccessor of(int value) {
        return new IntAccessor(value);
    }

    public IntAccessor set(int value) {
        this.value = value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    public int get() {
        return value;
    }

    public int getAndIncrement() {
        return value++;
    }

    public int incrementAndGet() {
        return ++value;
    }

    public int getAndIncrement(int value) {
        return this.value += value;
    }

    public int incrementAndGet(int value) {
        int now = this.value;
        this.value += value;
        return now;
    }

    public IntAccessor decrement(int value) {
        this.value -= value;
        return this;
    }

    public IntAccessor decrement() {
        return decrement(1);
    }

    public int decrementAndGet() {
        return decrement().get();
    }

    public int getAndDecrement() {
        int num = get();
        this.decrement();
        return num;
    }

    public int decrementAndGet(int value) {
        return decrement(value).get();
    }

    public int getAndDecrement(int value) {
        int num = get();
        this.decrement(value);
        return num;
    }

    /*
     * ------------------------------------------------------------
     * adds
     * ------------------------------------------------------------
     */

    public IntAccessor increment() {
        return this.increment(1);
    }

    public IntAccessor increment(int value) {
        this.value += value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * operations
     * ------------------------------------------------------------
     */

    public IntAccessor ifEq(int value, IntConsumer consumer) {
        if (this.value == value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifGt(int value, IntConsumer consumer) {
        if (this.value > value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifLt(int value, IntConsumer consumer) {
        if (this.value < value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifGtOrEq(int value, IntConsumer consumer) {
        if (this.value >= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor ifLtOrEq(int value, IntConsumer consumer) {
        if (this.value <= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public IntAccessor use(IntConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    public IntAccessor compute(ToIntFunction<Integer> computer) {
        return set(computer.applyAsInt(value));
    }

    public <T> T transform(IntFunction<T> transformer) {
        return transformer.apply(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
