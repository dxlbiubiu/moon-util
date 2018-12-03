package com.moon.lang.ref;

import java.util.function.DoubleConsumer;
import java.util.function.ToDoubleFunction;

/**
 * @author benshaoye
 * @date 2018/9/15
 */
public class DoubleAccessor {

    private double value;

    public DoubleAccessor() {
    }

    public DoubleAccessor(double value) {
        this.set(value);
    }

    public static DoubleAccessor of() {
        return new DoubleAccessor();
    }

    public static DoubleAccessor of(double value) {
        return new DoubleAccessor(value);
    }

    public DoubleAccessor set(double value) {
        this.value = value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * gets
     * ------------------------------------------------------------
     */

    public double get() {
        return value;
    }

    public double getAndIncrement() {
        return value++;
    }

    public double incrementAndGet() {
        return ++value;
    }

    public double getAndIncrement(double value) {
        return this.value += value;
    }

    public double incrementAndGet(double value) {
        double now = this.value;
        this.value += value;
        return now;
    }

    public DoubleAccessor decrement(double value) {
        this.value -= value;
        return this;
    }

    public DoubleAccessor decrement() {
        return decrement(1);
    }

    public double decrementAndGet() {
        return decrement().get();
    }

    public double getAndDecrement() {
        double num = get();
        this.decrement();
        return num;
    }

    public double decrementAndGet(double value) {
        return decrement(value).get();
    }

    public double getAndDecrement(double value) {
        double num = get();
        this.decrement(value);
        return num;
    }

    /*
     * ------------------------------------------------------------
     * adds
     * ------------------------------------------------------------
     */

    public DoubleAccessor increment() {
        return this.increment(1);
    }

    public DoubleAccessor increment(double value) {
        this.value += value;
        return this;
    }

    /*
     * ------------------------------------------------------------
     * operations
     * ------------------------------------------------------------
     */

    public DoubleAccessor ifEq(double value, DoubleConsumer consumer) {
        if (this.value == value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifGt(double value, DoubleConsumer consumer) {
        if (this.value > value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifLt(double value, DoubleConsumer consumer) {
        if (this.value < value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifGtOrEq(double value, DoubleConsumer consumer) {
        if (this.value >= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor ifLtOrEq(double value, DoubleConsumer consumer) {
        if (this.value <= value) {
            consumer.accept(this.value);
        }
        return this;
    }

    public DoubleAccessor use(DoubleConsumer consumer) {
        consumer.accept(value);
        return this;
    }

    public DoubleAccessor compute(ToDoubleFunction<Double> computer) {
        return set(computer.applyAsDouble(value));
    }

    public DoubleAccessor transform(ToDoubleFunction<Double> transformer) {
        return of(transformer.applyAsDouble(value));
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
