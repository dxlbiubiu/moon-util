package com.moon.util.compute.core;

import com.moon.lang.DoubleUtil;
import com.moon.lang.IntUtil;
import com.moon.time.TimeUtil;
import com.moon.util.DateUtil;
import com.moon.util.ListUtil;
import com.moon.util.MapUtil;
import com.moon.util.RandomStringUtil;
import com.moon.util.compute.RunnerFunction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 内置自定义函数
 *
 * @author benshaoye
 */
class IGetFun {

    private final static Map<String, RunnerFunction> CACHE = new HashMap<>();

    static {
        Class[] classes = {
            NowFunctions.class,
            StrFunctions.class,
            MapFunctions.class,
            ListFunctions.class,
            MathFunctions.class,
            DateFunctions.class,
        };
        for (Class<RunnerFunction> runner : classes) {
            for (RunnerFunction fun : runner.getEnumConstants()) {
                CACHE.put(fun.functionName(), fun);
            }
        }
    }

    private enum DateFunctions implements RunnerFunction {
        /**
         * @see DateUtil#format(Date, String)
         */
        date_format {
            @Override
            public Object apply(Object date, Object pattern) {
                String format = String.valueOf(pattern);
                if (date instanceof Date) {
                    return DateUtil.format((Date) date, format);
                } else if (date instanceof Calendar) {
                    return DateUtil.format((Calendar) date, format);
                }
                throw new IllegalArgumentException(String.valueOf(date));
            }

            @Override
            public Object apply() {
                return DateUtil.format();
            }

            @Override
            public Object apply(Object value) {
                if (value instanceof Date) {
                    return DateUtil.format((Date) value);
                }
                if (value instanceof CharSequence) {
                    return DateUtil.format(value.toString());
                }
                if (value instanceof Calendar) {
                    return DateUtil.format(((Calendar) value).getTime());
                }
                throw new IllegalArgumentException(String.valueOf(date));
            }
        },
        date_now {
            @Override
            public Object apply() {
                return System.currentTimeMillis();
            }

            @Override
            public Object apply(Object value) {
                return apply();
            }

            @Override
            public Object apply(Object value1, Object value2) {
                return apply();
            }

            @Override
            public Object apply(Object value1, Object value2, Object value3) {
                return apply();
            }

            @Override
            public Object apply(Object... values) {
                return apply();
            }
        },
        date {
            @Override
            public boolean isChangeless() {
                return false;
            }

            @Override
            public Object apply() {
                return new Date();
            }

            @Override
            public Object apply(Object o) {
                return DateUtil.toDate(o);
            }

            @Override
            public Object apply(Object o, Object o1) {
                return apply();
            }

            @Override
            public Object apply(Object o, Object o1, Object o2) {
                return apply();
            }

            @Override
            public Object apply(Object... values) {
                return apply();
            }
        };

        @Override
        public String functionName() {
            return this.name().replace('_', '.');
        }
    }

    private enum StrFunctions implements RunnerFunction {
        /**
         * @see String#substring(int, int) }
         */
        str_substring {
            @Override
            public String apply(Object str, Object from) {
                return ((String) str).substring(toInt(from));
            }

            @Override
            public String apply(Object str, Object from, Object to) {
                return ((String) str).substring(toInt(from), toInt(to));
            }
        },
        /**
         * @see String#contains(CharSequence) }
         */
        str_contains {
            @Override
            public Object apply(Object value1, Object value2) {
                return String.valueOf(value1).contains(String.valueOf(value2));
            }
        },
        str_indexOf {
            @Override
            public Integer apply(Object value1, Object value2) {
                return String.valueOf(value1).indexOf(String.valueOf(value2));
            }
        },
        str_startsWith {
            @Override
            public Boolean apply(Object value1, Object value2) {
                return String.valueOf(value1).startsWith(String.valueOf(value2));
            }

            @Override
            public Boolean apply(Object value1, Object value2, Object value3) {
                return String.valueOf(value1).startsWith(String.valueOf(value2), toInt(value3));
            }
        },
        str_endsWith {
            @Override
            public Boolean apply(Object value1, Object value2) {
                return String.valueOf(value1).endsWith(String.valueOf(value2));
            }
        },
        str_length {
            @Override
            public Integer apply(Object value) {
                return value == null ? 0 : ((CharSequence) value).length();
            }
        },
        str_random {
            @Override
            public Object apply() {
                return RandomStringUtil.next();
            }

            @Override
            public Object apply(Object value) {
                return RandomStringUtil.next(toInt(value));
            }

            @Override
            public Object apply(Object value1, Object value2) {
                return RandomStringUtil.next(toInt(value1), toInt(value2));
            }
        },
        str {
            @Override
            public String apply(Object value) {
                return String.valueOf(value);
            }
        },
        ;

        @Override
        public String functionName() {
            return this.name().replace('_', '.');
        }
    }

    private enum NowFunctions implements RunnerFunction {
        now_year {
            @Override
            public Object apply() {
                return LocalDate.now().getYear();
            }
        },
        now_month {
            @Override
            public Object apply() {
                return LocalDate.now().getMonthValue();
            }
        },
        now_day {
            @Override
            public Object apply() {
                return LocalDate.now().getDayOfMonth();
            }
        },
        now_hour {
            @Override
            public Object apply() {
                return LocalTime.now().getHour();
            }
        },
        now_minute {
            @Override
            public Object apply() {
                return LocalTime.now().getMinute();
            }
        },
        now_second {
            @Override
            public Object apply() {
                return LocalTime.now().getSecond();
            }
        },
        now {
            @Override
            public Object apply() {
                return System.currentTimeMillis();
            }

            @Override
            public Object apply(Object o) {
                return TimeUtil.toDateTime(o);
            }

            @Override
            public Object apply(Object o, Object o1) {
                return this.apply();
            }

            @Override
            public Object apply(Object o, Object o1, Object o2) {
                return this.apply();
            }

            @Override
            public Object apply(Object... values) {
                return this.apply();
            }
        };

        @Override
        public String functionName() {
            return this.name().replace('_', '.');
        }

        @Override
        public boolean isChangeless() {
            return false;
        }
    }

    private interface ChangeableRunnerFunction extends RunnerFunction {
        /**
         * 这个函数执行相同参数的返回值是否相同
         *
         * @return
         */
        @Override
        default boolean isChangeless() {
            return false;
        }
    }

    private enum MapFunctions implements ChangeableRunnerFunction {
        map_hasKey {
            @Override
            public Object apply(Object value1, Object value2) {
                return value1 == null ? false : ((Map) value1).containsKey(value2);
            }
        },
        map_hasValue {
            @Override
            public Object apply(Object value1, Object value2) {
                return value1 == null ? false : ((Map) value1).containsValue(value2);
            }
        },
        map_isEmpty {
            @Override
            public Object apply(Object value) {
                return MapUtil.sizeByObject(value) == 0;
            }
        },
        map_get {
            @Override
            public Object apply(Object value1, Object value2) {
                return MapUtil.getByObject(value1, value2);
            }
        },
        map_size {
            @Override
            public Object apply(Object value) {
                return MapUtil.sizeByObject(value);
            }
        },
        map {
            @Override
            public Object apply() {
                return new HashMap<>();
            }

            @Override
            public Map apply(Object key) {
                HashMap map = new HashMap();
                map.put(formatToKey(key), null);
                return map;
            }

            @Override
            public Map apply(Object key, Object value) {
                HashMap map = new HashMap();
                map.put(formatToKey(key), value);
                return map;
            }

            @Override
            public Map apply(Object key, Object value, Object key1) {
                HashMap map = new HashMap();
                map.put(formatToKey(key), value);
                map.put(formatToKey(key1), null);
                return map;
            }

            @Override
            public Map apply(Object... values) {
                HashMap map = new HashMap();
                int length = values.length;
                if (length > 0) {
                    int mod = length % 2, len = mod == 0 ? length : length - 1;
                    for (int i = 0; i < len; ) {
                        map.put(formatToKey(values[i++]), values[i++]);
                    }
                    if (mod == 1) {
                        map.put(formatToKey(values[len]), null);
                    }
                }
                return map;
            }
        };

        @Override
        public String functionName() {
            return this.name().replace('_', '.');
        }
    }

    private enum ListFunctions implements ChangeableRunnerFunction {
        list_hasIndex {
            @Override
            public Boolean apply(Object value1, Object value2) {
                int index = toInt(value2);
                return value1 == null ? false : index >= 0 && index < ((List) value1).size();
            }
        },
        list_hasValue {
            @Override
            public Boolean apply(Object value1, Object value2) {
                return value1 == null ? false : ((List) value1).contains(value2);
            }
        },
        list_isEmpty {
            @Override
            public Boolean apply(Object value) {
                return ListUtil.isEmpty((List) value);
            }
        },
        list_get {
            @Override
            public Object apply(Object value1, Object value2) {
                return ListUtil.getByObject(value1, toInt(value2));
            }
        },
        list_size {
            @Override
            public Integer apply(Object value) {
                return ListUtil.sizeByObject(value);
            }
        },
        list {
            @Override
            public boolean isChangeless() {
                return true;
            }

            @Override
            public List apply(Object value) {
                return ListUtil.ofArrayList(value);
            }

            @Override
            public List apply(Object value1, Object value2) {
                return ListUtil.ofArrayList(value1, value2);
            }

            @Override
            public List apply(Object value1, Object value2, Object value3) {
                return ListUtil.ofArrayList(value1, value2, value3);
            }

            @Override
            public List apply(Object... values) {
                return ListUtil.ofArrayList(values);
            }
        };

        @Override
        public String functionName() {
            return this.name().replace('_', '.');
        }
    }

    private enum MathFunctions implements RunnerFunction {
        math_double {
            @Override
            public Double apply(Object value) {
                return DoubleUtil.toDoubleValue(value);
            }
        },
        math_int {
            @Override
            public Integer apply(Object value) {
                return IntUtil.toIntValue(value);
            }
        },
        math_ceil {
            @Override
            public Double apply(Object value) {
                return Math.ceil(toDb(value));
            }
        },
        math_floor {
            @Override
            public Double apply(Object value) {
                return Math.floor(toDb(value));
            }
        },
        math_cos {
            @Override
            public Double apply(Object value) {
                return Math.cos(toDb(value));
            }
        },
        math_sin {
            @Override
            public Double apply(Object value) {
                return Math.sin(toDb(value));
            }
        },
        math_tan {
            @Override
            public Double apply(Object value) {
                return Math.tan(toDb(value));
            }
        },
        math_abs {
            @Override
            public Double apply(Object value) {
                return Math.abs(toDb(value));
            }
        },
        math_round {
            @Override
            public Long apply(Object value) {
                return Math.round(toDb(value));
            }
        },
        math_pow {
            @Override
            public Double apply(Object value1, Object value2) {
                return Math.pow(toDb(value1), toDb(value2));
            }
        },
        math_cbrt {
            @Override
            public Double apply(Object value) {
                return Math.cbrt(toDb(value));
            }
        },
        math_sqrt {
            @Override
            public Double apply(Object value) {
                return Math.sqrt(toDb(value));
            }
        },
        math_log {
            @Override
            public Double apply(Object value) {
                return Math.log(toDb(value));
            }
        },
        math_log10 {
            @Override
            public Double apply(Object value) {
                return Math.log10(toDb(value));
            }
        },
        math_random {
            private final ThreadLocalRandom random = ThreadLocalRandom.current();

            @Override
            public boolean isChangeless() {
                return false;
            }

            @Override
            public Double apply() {
                return random.nextDouble();
            }

            @Override
            public Integer apply(Object value) {
                return random.nextInt(toInt(value));
            }

            @Override
            public Integer apply(Object value1, Object value2) {
                return random.nextInt(toInt(value1), toInt(value2));
            }

            @Override
            public Double apply(Object... values) {
                return random.nextDouble();
            }
        };

        @Override
        public String functionName() {
            return this.name().replace('_', '.');
        }
    }

    private final static int toInt(Object value) {
        return value == null ? 0 : ((Number) value).intValue();
    }

    private final static double toDb(Object value) {
        return value == null ? 0 : ((Number) value).doubleValue();
    }

    private final static Object formatToKey(Object key) {
        boolean test = key == null || key instanceof Boolean
            || key instanceof Integer || key instanceof Double;
        if (!test) {
            if (key instanceof Number) {
                key = key instanceof Float
                    ? ((Float) key).doubleValue()
                    : ((Number) key).intValue();
            } else {
                key = String.valueOf(key);
            }
        }
        return key;
    }

    private static void assertVars(String str, int len) {
        for (int i = 0; i < len; i++) {
            if (!ParseUtil.isVar(str.charAt(i))) {
                throw new IllegalArgumentException("包含非法字符（只能是字母、数字、$、_ 的组合）>>>>>" + str + "<<<<<");
            }
        }
    }

    final static String toName(String ns, String name) {
        assertVars((ns = ns.trim()), ns.length());
        assertVars(name, name == null ? 0 : (name = name.trim()).length());
        return ns + (name == null || name.isEmpty() ? name : '.' + name);
    }

    final static RunnerFunction tryLoad(String name) {
        return CACHE.get(name);
    }
}
