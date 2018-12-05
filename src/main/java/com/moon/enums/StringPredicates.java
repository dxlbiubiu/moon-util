package com.moon.enums;

import com.moon.lang.StringUtil;

import java.util.function.Predicate;

/**
 * @author benshaoye
 */
public enum StringPredicates implements Predicate<String> {
    nonEmpty {
        @Override
        public boolean test(String s) {
            return s != null && s.length() > 0;
        }
    },
    nonBlank {
        @Override
        public boolean test(String s) {
            return s != null && s.trim().length() > 0;
        }
    },
    nonUndefined {
        @Override
        public boolean test(String s) {
            return !StringUtil.isUndefined(s);
        }
    },
    nonNullString {
        @Override
        public boolean test(String s) {
            return !StringUtil.isNullString(s);
        }
    }
}
