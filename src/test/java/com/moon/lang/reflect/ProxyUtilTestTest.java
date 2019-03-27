package com.moon.lang.reflect;

import com.moon.util.RandomStringUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author benshaoye
 */
class ProxyUtilTestTest {

    @FunctionalInterface
    interface Getter<T> {
        T get();
    }

    static class Student implements Getter {

        private final String str = RandomStringUtil.next();

        public Student() {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + str);
        }

        public String getStr() {
            return str;
        }

        @Override
        public Object get() {
            return str;
        }
    }

    public static class StudentInvocationHandler implements InvocationHandler {

        private final Student student;

        public StudentInvocationHandler() {this(new Student());}

        public StudentInvocationHandler(Student student) {this.student = student;}

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("+++++++++++++++++++++++++++++++++++  before");
            Object invoked = method.invoke(student, args);
            System.out.println("+++++++++++++++++++++++++++++++++++  after");
            return invoked;
        }
    }

    @Test
    void testJDKProxy() {
        Class[] interfaces = {Getter.class};
        Object o = Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, new StudentInvocationHandler());
        Getter getter = (Getter) o;
        Object res = getter.get();
        System.out.println("===================================  " + res);
    }

    @Test
    void testJDKProxy1() {
        Student student = new Student();
        System.out.println(student.get());
        Class[] interfaces = {Getter.class};
        Object o = Proxy.newProxyInstance(getClass().getClassLoader(),
            interfaces, new StudentInvocationHandler(student));
        Getter getter = (Getter) o;
        Object res = getter.get();
        System.out.println("===================================  " + res);
    }

    public static class StudentMethodInterceptor implements MethodInterceptor {

        private final Student student;

        public StudentMethodInterceptor() {this.student = new Student();}

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("-----------------------------------  before");
            Object invoked = proxy.invoke(student, args);
            System.out.println("-----------------------------------  after");
            return invoked;
        }
    }

    @Test
    void testFirstMethod() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        enhancer.setCallback(new StudentMethodInterceptor());
        Student student = (Student) enhancer.create();
        Object res = student.get();
        System.out.println("===================================  " + res);
    }
}