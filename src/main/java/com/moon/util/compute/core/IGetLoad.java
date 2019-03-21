package com.moon.util.compute.core;

import com.moon.awt.ImageUtil;
import com.moon.beans.BeanInfoUtil;
import com.moon.enums.Chars;
import com.moon.io.IOUtil;
import com.moon.lang.ClassUtil;
import com.moon.lang.StringUtil;
import com.moon.lang.annotation.AnnotatedUtil;
import com.moon.lang.ref.ReferenceUtil;
import com.moon.lang.reflect.FieldUtil;
import com.moon.lang.reflect.ModifierUtil;
import com.moon.mail.EmailUtil;
import com.moon.math.BigDecimalUtil;
import com.moon.net.HttpUtil;
import com.moon.script.ScriptUtil;
import com.moon.security.EncryptUtil;
import com.moon.sql.ResultSetUtil;
import com.moon.time.TimeUtil;
import com.moon.util.ListUtil;
import com.moon.util.concurrent.ExecutorUtil;
import com.moon.util.env.EnvUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
class IGetLoad {
    private IGetLoad() {
        noInstanceError();
    }

    private final static String[] packages;

    static {
        /**
         * 每个包下列举一个类，得到包名，支持这个包下的所有类静态方法调用
         */
        Class[] classes = {
            System.class, Method.class, List.class,
            ImageUtil.class, BeanInfoUtil.class, Chars.class,
            IOUtil.class, StringUtil.class, ListUtil.class,
            ReferenceUtil.class, FieldUtil.class, AnnotatedUtil.class,
            EmailUtil.class, BigDecimalUtil.class, HttpUtil.class,
            TimeUtil.class, ScriptUtil.class, EncryptUtil.class,
            ResultSetUtil.class, ExecutorUtil.class, EnvUtil.class,
        };
        String[] names = packages = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            names[i] = classes[i].getPackage().getName();
        }
    }

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
