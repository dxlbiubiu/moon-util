package com.moon.util.compute.core;

import com.moon.lang.ClassUtil;
import com.moon.lang.ref.ReferenceUtil;
import com.moon.lang.reflect.ModifierUtil;

import java.util.Map;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
class IGetLoad {
    private IGetLoad() {
        noInstanceError();
    }

    private final static String[] packages = {
        "java.util.",
        "java.lang.",
        "java.lang.reflect.",

        "com.moon.beans.",
        "com.moon.enums.",
        "com.moon.io.",

        "com.moon.lang.",
        "com.moon.lang.ref.",
        "com.moon.lang.reflect.",
        "com.moon.lang.annotation.",

        "com.moon.math.",
        "com.moon.net.",
        "com.moon.time.",

        "com.moon.util.",
        "com.moon.util.able.",
        "com.moon.util.assertions.",
        "com.moon.util.compute.",
        "com.moon.util.concurrent.",
        "com.moon.util.console.",
        "com.moon.util.json.",
    };

    private final static Map<String, Class> CACHE = ReferenceUtil.weakMap();

    public final static Class tryLoad(String name) {
        Class type = CACHE.get(name);
        if (type == null) {
            for (String packageName : packages) {
                try {
                    type = ClassUtil.forName(packageName + name);
                    if (ModifierUtil.isPublic(type)) {
                        CACHE.put(name, type);
                        return type;
                    }
                } catch (Throwable t) {
                    // ignore
                }
            }
        }
        return type;
    }

    public final static Class of(String name) {
        Class type = tryLoad(name);
        if (type == null) {
            throw new IllegalArgumentException("can not find class of key: " + name);
        }
        return type;
    }
}
