package com.moon.util.compute.core;

import com.moon.lang.StringUtil;
import com.moon.util.IteratorUtil;
import com.moon.util.compute.RunnerFunction;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public abstract class BaseSettings {

    protected Supplier<List> arrCreator;
    protected Supplier<Map> objCreator;

    private final Map<String, Object> callers = new HashMap<>();

    protected BaseSettings() {
        this(ArrayList::new, HashMap::new);
    }

    public BaseSettings(Supplier<List> arrCreator, Supplier<Map> objCreator) {
        this.arrCreator = arrCreator;
        this.objCreator = objCreator;
    }

    /**
     * 获取静态方法执行类
     *
     * @param name
     * @return
     */
    final Object getCaller(String name) {
        return this.callers.get(name);
    }

    final Supplier<List> getArrCreator() {
        return arrCreator;
    }

    final Supplier<Map> getObjCreator() {
        return objCreator;
    }

    public BaseSettings setArrCreator(Supplier<List> arrCreator) {
        this.arrCreator = arrCreator;
        return this;
    }

    public BaseSettings setObjCreator(Supplier<Map> objCreator) {
        this.objCreator = objCreator;
        return this;
    }

    public BaseSettings addCaller(Class clazz) {
        return addCaller(clazz.getSimpleName(), clazz);
    }

    public BaseSettings addCallers(Class... classes) {
        for (Class type : classes) {
            addCaller(type);
        }
        return this;
    }

    public BaseSettings addCaller(RunnerFunction runner) {
        String name = StringUtil.deleteWhitespaces(Objects.requireNonNull(runner.functionName()));
        this.callers.put(Inners.checkName(name), runner);
        return this;
    }

    public BaseSettings addCallers(List<RunnerFunction> runners) {
        IteratorUtil.forEach(runners, (runner) -> addCaller(runner));
        return this;
    }

    public BaseSettings addCaller(String name, Class staticCallerClass) {
        this.callers.put(name, staticCallerClass);
        return this;
    }

    public BaseSettings addCallers(Map<String, Class> callers) {
        this.callers.putAll(callers);
        return this;
    }

    public BaseSettings removeCaller(String name) {
        this.callers.remove(name);
        return this;
    }

    public BaseSettings removeCallers(String... names) {
        for (String name : names) {
            this.callers.remove(name);
        }
        return this;
    }
}
