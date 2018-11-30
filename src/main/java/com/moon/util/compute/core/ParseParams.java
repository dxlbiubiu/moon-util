package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;
import com.moon.util.compute.RunnerSettings;

import java.util.ArrayList;
import java.util.List;

import static com.moon.lang.ThrowUtil.noInstanceError;
import static com.moon.util.compute.core.Constants.*;

/**
 * @author benshaoye
 */
final class ParseParams {
    private ParseParams() {
        noInstanceError();
    }

    /**
     * 从左括号的下一个字符开始解析
     *
     * @param chars
     * @param indexer
     * @param len
     * @param settings
     * @return
     */
    final static AsRunner[] parse(char[] chars, IntAccessor indexer, int len, RunnerSettings settings) {
        int curr = ParseUtil.nextVal(chars, indexer, len);
        List params = new ArrayList();
        AsRunner runner;
        outer:
        for (int next = curr; ; curr = next) {
            switch (next) {
                case YUAN_R:
                    AsValuer[] runners = new AsValuer[params.size()];
                    return (AsValuer[]) params.toArray(runners);
                case SINGLE:
                case DOUBLE:
                    runner = ParseConst.parseStr(chars, indexer, next);
                    break;
                default:
                    runner = ParseCore.parse(chars, indexer.minus(), len, settings, COMMA, YUAN_R);
                    if ((next = chars[indexer.get() - 1]) == YUAN_R) {
                        params.add(runner);
                        continue outer;
                    }
                    break;
            }
            params.add(runner);
            next = ParseUtil.nextVal(chars, indexer, len);
            if (next == COMMA && (runner != DataConst.NULL || (curr != COMMA && curr != YUAN_L))) {
                next = ParseUtil.nextVal(chars, indexer, len);
            }
        }
    }
}
