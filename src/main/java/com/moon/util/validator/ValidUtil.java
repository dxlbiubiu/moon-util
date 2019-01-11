package com.moon.util.validator;

import java.util.Collection;
import java.util.function.Function;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
final class ValidUtil {
    private ValidUtil() {
        noInstanceError();
    }

    static Object requireUnique(BaseValidator validator, Function transformer, String message) {
        boolean isNull = false;
        Object transformed = null;
        for (Object one : (Collection) validator.value) {
            Object curr = transformer.apply(one);
            if (transformed == null) {
                if (isNull) {
                    if (curr == null) {
                        continue;
                    }
                    return validator.createMsg(message);
                }
                transformed = curr;
            } else if (curr == null && !isNull) {
                isNull = true;
            } else if (!curr.equals(transformed)) {
                return validator.createMsg(message);
            }
        }
        return validator;
    }
}
