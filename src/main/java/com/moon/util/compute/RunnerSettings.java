package com.moon.util.compute;

import com.moon.util.compute.core.BaseSettings;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public final class RunnerSettings extends BaseSettings {

    final static Object NULL = null;

    private RunnerSettings() {
    }

    @Override
    public RunnerSettings setArrCreator(Supplier<List> arrCreator) {
        super.setArrCreator(arrCreator);
        return this;
    }

    @Override
    public RunnerSettings setObjCreator(Supplier<Map> objCreator) {
        super.setObjCreator(objCreator);
        return this;
    }

    @Override
    public RunnerSettings addCaller(Class clazz) {
        super.addCaller(clazz);
        return this;
    }

    @Override
    public RunnerSettings addCallers(Class... classes) {
        super.addCallers(classes);
        return this;
    }

    @Override
    public RunnerSettings addCaller(RunnerFunction runner) {
        super.addCaller(runner);
        return this;
    }

    @Override
    public RunnerSettings addCallers(List<RunnerFunction> runners) {
        super.addCallers(runners);
        return this;
    }

    @Override
    public RunnerSettings addCaller(String name, Class staticCallerClass) {
        super.addCaller(name, staticCallerClass);
        return this;
    }

    @Override
    public RunnerSettings addCallers(Map<String, Class> callers) {
        super.addCallers(callers);
        return this;
    }

    @Override
    public RunnerSettings removeCaller(String name) {
        super.removeCaller(name);
        return this;
    }

    @Override
    public RunnerSettings removeCallers(String... names) {
        super.removeCallers(names);
        return this;
    }

    public final static RunnerSettings builder() {
        return new RunnerSettings();
    }

    public RunnerSettings build() {
        return this;
    }
}
