package com.moon.util.compute.core;

import com.moon.lang.ref.IntAccessor;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author benshaoye
 */
class ParseParamsTestTest {

    static final Assertions assertions = Assertions.of();

    AsRunner[] res;

    AsRunner[] parse(String exp) {
        char[] chars = exp.toCharArray();
        IntAccessor indexer = IntAccessor.of();
        int len = chars.length;
        int curr = ParseUtil.nextVal(chars, indexer, len);
        return res = ParseParams.parse(chars, indexer, len, null);
    }

    @Test
    void testParse() {
        res = parse("()");
        assertions.assertEq(res.length, 0);
        res = parse("(,)");
        assertions.assertEq(res.length, 1);
        res = parse("(null,)");
        assertions.assertEq(res.length, 1);
        res = parse("(null,null)");
        assertions.assertEq(res.length, 2);
        res = parse("('null',12)");
        assertions.assertEq(res.length, 2);
        res = parse("(12.3,12)");
        assertions.assertEq(res.length, 2);
        res = parse("(12.3,12,)");
        assertions.assertEq(res.length, 2);
        res = parse("(    )");
        assertions.assertEq(res.length, 0);
        res = parse("(   , )  ");
        assertions.assertEq(res.length, 1);
        res = parse("  (   null ,    )");
        assertions.assertEq(res.length, 1);
        res = parse("(null  ,   null)");
        assertions.assertEq(res.length, 2);
        res = parse("  (  ' null'   ,12)");
        assertions.assertEq(res.length, 2);
        res = parse("  (12.3  ,   12   )");
        assertions.assertEq(res.length, 2);
        res = parse("( 12.3  ,  12,   )");
        assertions.assertEq(res.length, 2);
        res = parse("( 12.3  ,  12,   true)");
        assertions.assertEq(res.length, 3);
        res = parse("( 12.3  ,  12, name,  true)");
        assertions.assertEq(res.length, 4);
        res = parse("( 12.3  ,  12, name,  ,'',   true)");
        assertions.assertEq(res.length, 5);
        res = parse("( 12.3  ,  12, name, , ,'',   true)");
        assertions.assertEq(res.length, 6);
        res = parse("( 12.3  ,  12, name, null , ,'',   true)");
        assertions.assertEq(res.length, 6);
        res = parse("( 12.3  ,  12, name, null,  , ,'',   true)");
        assertions.assertEq(res.length, 7);
    }
}