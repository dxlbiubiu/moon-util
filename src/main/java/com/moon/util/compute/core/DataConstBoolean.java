package com.moon.util.compute.core;

/**
 * @author benshaoye
 */
enum DataConstBoolean implements AsConst, AsFlipable {
    TRUE {
        @Override
        public Object run(Object data) {
            return Boolean.TRUE;
        }

        @Override
        public DataConstBoolean flip() {
            return FALSE;
        }
    },
    FALSE {
        @Override
        public Object run(Object data) {
            return Boolean.FALSE;
        }

        @Override
        public DataConstBoolean flip() {
            return TRUE;
        }
    };

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
