package com.moon.lang.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author benshaoye
 */
class ProxyUtilTestTest {

    @FunctionalInterface
    interface Getter<T> {
        T get();
    }

    static class Student implements Getter {

        @Override
        public Object get() {
            return null;
        }
    }

    @Test
    void testFirstMethod() {
        Student student = new Student();
    }
}