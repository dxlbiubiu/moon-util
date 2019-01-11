package com.moon.lang;

import com.moon.util.assertions.Assertions;
import com.moon.util.validator.Validator;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class ValidatorTestTest {

    static final Assertions assertions = Assertions.of();

    @Test
    void testOf() {
        Validator<String> validator = Validator.of("aaa")
            .require(item -> item.length() > 3, "长度必须大于2")
            .require(item -> item.contains("b"), "必须包含字母‘b’")
            .require(item -> item.contains("a"), "必须包含字母‘a’");

        assertions.assertThrows(IllegalArgumentException.class,
            () -> validator.get(IllegalArgumentException::new));

        Validator<Employee> employeeValidator = Validator.of(new Employee());
        Employee employee = employeeValidator
            .setImmediate(false)
            .requireNonNull("员工不能为空")
            .require(item -> item.age > 18, "未成年人不行")
            .require(item -> item.id != null, "ID 不能为空")
            .require(item -> item.name != null, "name 不能为空")
            .require(item -> item.account != null, "账号不能为空")
            .require(item -> item.address != null, "地址不能为空")
            .nullIfInvalid();
        System.out.println(employee);
        employeeValidator.get();

        // new 一个对象根本没有时间成本嘛...
        // new Integer.MAX_VALUE 个对象，花费时间 0 ms，完全可忽略
        // 那么 new 对象有不可忽略的空间成本吗? 如果有，应该怎么评估?
    }

    static class Employee {
        private int age;
        private String id;
        private String name;
        private boolean sex;
        private String account;
        private String address;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Employee{");
            sb.append("age=").append(age);
            sb.append(", id='").append(id).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append(", sex=").append(sex);
            sb.append(", account='").append(account).append('\'');
            sb.append(", address='").append(address).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}