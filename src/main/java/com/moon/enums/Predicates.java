package com.moon.enums;

import java.util.function.Predicate;

/**
 * @author benshaoye
 */
public enum Predicates implements Predicate {
    TRUE {
        @Override
        public boolean test(Object object) {
            return true;
        }
    },
    FALSE {
        @Override
        public boolean test(Object object) {
            return false;
        }
    },
    NULL {
        @Override
        public boolean test(Object object) {
            return object == null;
        }
    },
    NON_NULL {
        @Override
        public boolean test(Object object) {
            return object != null;
        }
    }
}
