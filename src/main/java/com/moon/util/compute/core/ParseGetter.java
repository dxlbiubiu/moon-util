package com.moon.util.compute.core;

import com.moon.lang.SupportUtil;
import com.moon.lang.ref.IntAccessor;

import java.util.LinkedList;

import static com.moon.lang.ThrowUtil.noInstanceError;

/**
 * @author benshaoye
 */
final class ParseGetter {
    private ParseGetter() {
        noInstanceError();
    }

    /**
     * 返回一个 valuer
     *
     * @param chars
     * @param indexer
     * @param len
     * @param current
     * @return
     */
    final static AsValuer parseVar(char[] chars, IntAccessor indexer, int len, int current) {
        String str = SupportUtil.parseVar(chars, indexer, len, current);
        switch (str) {
            case "null":
                return DataConst.NULL;
            case "true":
                return DataConst.TRUE;
            case "false":
                return DataConst.FALSE;
            default:
                return new DataGetterOrdinary(str);
        }
    }

    /**
     * 返回一个 getter
     *
     * @param chars
     * @param indexer
     * @param len
     * @return
     */
    private final static AsValuer parseDot(char[] chars, IntAccessor indexer, int len) {
        int curr = ParseUtil.nextVal(chars, indexer, len);
        ParseUtil.assertTrue(ParseUtil.isVar(curr), chars, indexer);
        return parseVar(chars, indexer, len, curr);
    }

    final static AsRunner parseDot(
        char[] chars, IntAccessor indexer, int len, BaseSettings settings, AsRunner prevHandler
    ) {
        AsValuer prevValuer = (AsValuer) prevHandler;
        AsRunner handler = parseDot(chars, indexer, len);
        ParseUtil.assertTrue(handler.isValuer(), chars, indexer);
        AsRunner invoker = ParseInvoker.tryParseInvoker(chars, indexer, len, settings, handler.toString(), prevValuer);
        return invoker == null ? new DataGetterLinker(prevValuer, (AsValuer) handler) : invoker;
    }

    final static AsRunner parseNot(
        char[] chars, IntAccessor indexer, int len, BaseSettings settings
    ) {
        AsRunner valuer, tryLinked;
        int curr = ParseUtil.nextVal(chars, indexer, len);
        switch (curr) {
            case Constants.FANG_LEFT:
                valuer = parseFang(chars, indexer, len, settings);
                break;
            case Constants.YUAN_LEFT:
                valuer = parseYuan(chars, indexer, len, settings);
                break;
            case Constants.HUA_LEFT:
                valuer = ParseCurly.parse(chars, indexer, len, settings);
                break;
            case Constants.CALLER:
                valuer = ParseCaller.parse(chars, indexer, len, settings);
                break;
            default:
                if (ParseUtil.isVar(curr)) {
                    valuer = parseVar(chars, indexer, len, curr);
                    ParseUtil.assertFalse(valuer == DataConst.NULL, chars, indexer);
                    if (valuer == DataConst.TRUE) {
                        valuer = DataConst.FALSE;
                    } else if (valuer == DataConst.FALSE) {
                        valuer = DataConst.TRUE;
                    }
                } else {
                    valuer = ParseUtil.throwErr(chars, indexer);
                }
                break;
        }
        tryLinked = tryParseLinked(chars, indexer, len, settings, valuer);
        return tryLinked == valuer && tryLinked.isConst() ? flip(chars, indexer, tryLinked)
            : new DataGetterNot(tryLinked);
    }

    private static AsRunner flip(char[] chars, IntAccessor indexer, AsRunner valuer) {
        ParseUtil.assertTrue(valuer instanceof DataConstBoolean, chars, indexer);
        return ((DataConstBoolean) valuer).flip();
    }

    final static AsRunner tryParseLinked(
        char[] chars, IntAccessor indexer, int len, BaseSettings settings, AsRunner valuer
    ) {
        final int index = indexer.get();
        AsRunner next = valuer;
        for (int curr; ; ) {
            curr = ParseUtil.nextVal(chars, indexer, len);
            if (curr == Constants.DOT) {
                next = parseDot(chars, indexer, len, settings, next);
            } else if (curr == Constants.FANG_LEFT) {
                next = parseFangToComplex(chars, indexer, len, settings, next);
            } else {
                if (next == valuer) {
                    indexer.set(index);
                }
                return next;
            }
        }
    }

    /**
     * 返回一个getter
     *
     * @param chars
     * @param indexer
     * @param len
     * @return
     */
    final static DataGetterFang parseFang(char[] chars, IntAccessor indexer, int len, BaseSettings settings) {
        AsRunner handler = ParseCore.parse(chars, indexer, len, settings, Constants.FANG_RIGHT);
        ParseUtil.assertTrue(handler.isValuer(), chars, indexer);
        return new DataGetterFang((AsValuer) handler);
    }

    /**
     * 参考{@link ParseCore#core(char[], IntAccessor, int, BaseSettings, int, LinkedList, LinkedList, AsRunner)}
     * case FANG_LEFT: 的详细步骤
     *
     * @param chars
     * @param indexer
     * @param len
     * @param prevHandler
     * @return
     */
    private final static AsRunner parseFangToComplex(
        char[] chars, IntAccessor indexer, int len, BaseSettings settings, AsRunner prevHandler
    ) {
        AsRunner handler = ParseGetter.parseFang(chars, indexer, len, settings);
        ParseUtil.assertTrue(prevHandler.isValuer(), chars, indexer);
        return ((DataGetterFang) handler).toComplex(prevHandler);
    }

    final static AsRunner parseYuan(char[] chars, IntAccessor indexer, int len, BaseSettings settings) {
        return ParseCore.parse(chars, indexer, len, settings, Constants.YUAN_RIGHT);
    }
}
