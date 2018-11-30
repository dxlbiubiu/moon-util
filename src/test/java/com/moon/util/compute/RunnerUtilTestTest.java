package com.moon.util.compute;

import com.moon.lang.ClassUtil;
import com.moon.lang.DoubleUtil;
import com.moon.lang.reflect.MethodUtil;
import com.moon.script.ScriptUtil;
import com.moon.util.Console;
import com.moon.util.DateUtil;
import com.moon.util.ListUtil;
import com.moon.util.MapUtil;
import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.moon.util.assertions.Assertions.of;

/**
 * @author benshaoye
 */
class RunnerUtilTestTest {
    static final Assertions assertions = of();
    Object data, res;
    Runner runner;

    @Test
    void testMapAndList() {
        res = RunnerUtil.run("{:}.get(true)");
        assertions.assertEquals(res, null);
        res = RunnerUtil.run("{}.isEmpty()");
        assertions.assertEquals(res, true);
        res = RunnerUtil.run("{:}.isEmpty()");
        assertions.assertEquals(res, true);

        res = RunnerUtil.run("{a:10}.isEmpty()");
        assertions.assertEquals(res, false);

        assertions.assertThrows(() -> RunnerUtil.run("{a:}.isEmpty()"));
        assertions.assertThrows(() -> RunnerUtil.run("{a:,}.isEmpty()"));
        assertions.assertThrows(() -> RunnerUtil.run("{a}.isEmpty()"));

        res = RunnerUtil.run("{a:10}.isEmpty() + false");
        assertions.assertEquals(res, "falsefalse");

        res = RunnerUtil.run("!{:}.isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!{}.isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({:}.isEmpty())");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({}.isEmpty())");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({:}).isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("!({}).isEmpty()");
        assertions.assertEquals(res, false);

        res = RunnerUtil.run("-20.doubleValue()");
        assertions.assertEquals(res, -20D);

        res = RunnerUtil.run("-20.doubleValue().intValue()");
        assertions.assertEquals(res, -20);

        res = RunnerUtil.run("-@DateUtil.now().intValue()");
        assertions.assertInstanceOf(res, Integer.class);
    }

    @Test
    void testRun() {
        res = RunnerUtil.run("1+1.doubleValue()");
        assertions.assertEquals(res, 2D);
        res = RunnerUtil.run("''.length()");
        assertions.assertEquals(res, 0);
        res = RunnerUtil.run("1+1.doubleValue()");
        assertions.assertEquals(res, 2D);
        res = RunnerUtil.run("'a'.length()");
        assertions.assertEquals(res, 1);
        res = RunnerUtil.run("@DateUtil.yyyy_MM+20");
        assertions.assertEquals(res, "yyyy-MM20");
        res = RunnerUtil.run("@   DateUtil.yyyy_MM+20");
        assertions.assertEquals(res, "yyyy-MM20");
    }


    @Test
    void testCalc() {
        assertions.assertEquals(RunnerUtil.run("1^1"), 1 ^ 1);
        assertions.assertEquals(RunnerUtil.run("2^1+5"), 2 ^ 1 + 5);
    }

    @Test
    void testParseRun() {
        assertions.assertEquals(RunnerUtil.parseRun("{{1+2}}"), 3);
        assertions.assertEquals(RunnerUtil.parseRun("{{'中华人民共和国'}}"), "中华人民共和国");

        data = new HashMap() {{
            put("name", 456);
        }};

        str = "本草纲目{{'好的'}}  {{   123   }}  电脑 {{1+2+3+5+6}} {{name}}";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, data),
            "本草纲目好的  123  电脑 17 456"
        );

        str = "本草纲目{{'好的'}}  {{123}}  ";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, data),
            "本草纲目好的  123  "
        );
    }

    @Test
    void testParseRunPerformance() {
        int count = 100000;
        long begin = DateUtil.now();
        for (int i = 0; i < count; i++) {
            RunnerUtil.parseRun("{{1+2}}");
        }
        long end = DateUtil.now();
        Console.out.println(end - begin);
    }

    String str, result;
    String[] delimiters = new String[]{"${", "}}"};

    @Test
    void testParseRun1() {
        data = new HashMap() {{
            put("name", 456);
        }};

        str = "本草纲目${'好的'}}  ${123}}  电脑 ${1+2+3+5+6}} ${name}}";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, delimiters, data),
            "本草纲目好的  123  电脑 17 456"
        );

        str = "本草纲目${'好的'}}  ${123}}  ";
        assertions.assertEquals(
            RunnerUtil.parseRun(str, delimiters, data),
            "本草纲目好的  123  "
        );
    }

    @Test
    void testParseRun2() {
        res = RunnerUtil.run("@Long.  parseLong(   @Objects.toString(   @DateUtil.now(   )   )  )");
        res = RunnerUtil.run("-(-@Long.parseLong(   @   Objects  . toString( @    DateUtil .  now(   ))  )   ).longValue()");
        assertions.assertInstanceOf(res, Long.class);
        res = RunnerUtil.run("@MapUtil.sizeByObject({key: null, null: true})");
        assertions.assertInstanceOf(res, Integer.class);
        assertions.assertEquals(res, 2);
        res = RunnerUtil.run("{key: null, null: true, 25.3: 25}");
        assertions.assertInstanceOf(res, HashMap.class);
        assertions.assertEquals(MapUtil.sizeByObject(res), 3);
        assertions.assertEquals(MapUtil.getByObject(res, "key"), null);
        assertions.assertEquals(MapUtil.getByObject(res, null), true);
        assertions.assertEquals(MapUtil.getByObject(res, null), true);
        assertions.assertEquals(MapUtil.getByObject(res, "null"), null);
        assertions.assertEquals(MapUtil.getByObject(res, 25.3), 25);
    }

    @Test
    void testGetMethod() {
        res = MethodUtil.getPublicStaticMethods(Objects.class, "hash");
        Console.out.println((Object) ListUtil.getByObject(res, 0));
    }

    @Test
    void testParseMultipleParamMethod() {
        res = ClassUtil.getClasses(1, 2.0);
        res = MethodUtil.getPublicStaticMethods(DoubleUtil.class, "requireGt", (Class[]) res);
        //res = RunnerUtil.apply("@DateUtil.parse('2018-05-09 12:35:26', 'yyyy-MM-dd HH:mm:ss')");

        assertions.assertSame(String.class.getPackage(), Class.class.getPackage());
        data = new HashMap() {{
            put("arr", new Object[]{1, 2, 3});
        }};
        res = RunnerUtil.run("arr.length", data);
        System.out.println(res);
    }

    @Test
    void testRunningTimes() throws ScriptException {
        ScriptEngine engine = ScriptUtil.newJSEngine();
        System.out.println(engine.eval("1+1"));
        System.out.println(RunnerUtil.run("1+1"));
        Runner runner = RunnerUtil.parse("1+1");

        int count = 10000;
        Console.out.time();
        for (int i = 0; i < count; i++) {
            res = engine.eval("1+1");
        }
        Console.out.timeNext();
        for (int i = 0; i < count; i++) {
            res = RunnerUtil.run("1+1");
        }
        Console.out.timeNext();
        for (int i = 0; i < count; i++) {
            res = runner.run();
        }
        Console.out.timeNext();
        for (int i = 0; i < count; i++) {
            res = 1 + 1;
        }
        Console.out.timeEnd();
    }

    @Test
    void testParseToRunner() {
        Runner runner = RunnerUtil.parse("{'已有人数','需要人数','已提交','已付款','已完成'}");
        res = runner.run();
        System.out.println(res);
    }

    @Test
    void testCustomCaller() {
        RunnerSettings settings = RunnerSettings.of()
            .setObjCreator(LinkedHashMap::new)
            .setArrCreator(LinkedList::new)
            .addCaller("call", Caller.class)
            .addCaller("Objects", InnerObjects.class);

        Runner runner = RunnerUtil.parse("@call.get()", settings);
        assertions.assertEquals(runner.run(), "123456789");
        runner = RunnerUtil.parse("@Objects.toString('123123')", settings);
        assertions.assertEquals(runner.run(), "--11--");
        runner = RunnerUtil.parse("@Objects.toString('123123')");
        assertions.assertEquals(runner.run(), "123123");
        runner = RunnerUtil.parse("{'已有人数','需要人数','已提交','已付款','已完成'}", settings);
        assertions.assertInstanceOf(runner.run(), LinkedList.class);
        assertions.assertEquals(ListUtil.sizeByObject(runner.run()), 5);
    }

    @Test
    void testJEP() {
        str = "((a+b)*(c+b))/(c+a)/b";
        data = new HashMap() {{
            put("a", 10);
            put("b", 20);
            put("c", 30);
        }};
        int a = 10, b = 20, c = 30;

        Runner runner = RunnerUtil.parse(str);
        res = runner.run(data);
        assertions.assertEquals(res, ((a + b) * (c + b)) / (c + a) / b);

        str = "43*(2 + 1.4)+2*32/(3-2.1)";
        assertions.assertEquals(RunnerUtil.run(str), 43 * (2 + 1.4) + 2 * 32 / (3 - 2.1));

        str = "1 >> 1";
        runner = RunnerUtil.parse(str);
        res = runner.run(data);
        assertions.assertEquals(res, 1 >> 1);
        assertions.assertEquals(RunnerUtil.run("1<<1"), 1 << 1);
        assertions.assertEquals(RunnerUtil.run("1>>1"), 1 >> 1);
        assertions.assertEquals(RunnerUtil.run("1>>>1"), 1 >>> 1);
        assertions.assertEquals(RunnerUtil.run("2>>>2"), 2 >>> 2);
        assertions.assertEquals(RunnerUtil.run("3>>>5"), 3 >>> 5);
        assertions.assertEquals(RunnerUtil.run("8>>>2"), 8 >>> 2);
        assertions.assertEquals(RunnerUtil.run("1==1"), 1 == 1);
        assertions.assertEquals(RunnerUtil.run("1!=1"), 1 != 1);
        assertions.assertEquals(RunnerUtil.run("1|1"), 1 | 1);
        assertions.assertEquals(RunnerUtil.run("1&1"), 1 & 1);
        assertions.assertEquals(RunnerUtil.run("1^1"), 1 ^ 1);
        assertions.assertEquals(RunnerUtil.run("3>4?1:2"), 3 > 4 ? 1 : 2);
        assertions.assertEquals(RunnerUtil.run("9+44*(8-2/1)"), 9 + 44 * (8 - 2 / 1));

        data = new HashMap() {{
            put("money", 2640);
            put("count", 50);
            put("people", 25);
            put("cat", 1);
        }};
        runner = RunnerUtil.parse("((money+count)*people/100)+50-88+cat*10");
        int money = 2640, count = 50, people = 25, cat = 1, x = 2;
        assertions.assertEquals(runner.run(data), ((money + count) * people / 100) + 50 - 88 + cat * 10);

        data = new HashMap() {{
            put("value", 7);
            put("st", "test");
            put("state", "正常");
            put("flag", true);
        }};
        int value = 7;
        String st = "test", state = "正常";
        boolean flag = true;
        runner = RunnerUtil.parse("value > 5 && st == \"test\" && state == \"正常\" && flag == true");
        assertions.assertEquals(runner.run(data), value > 5 && Objects.equals("test", st) && Objects.equals("正常", state) && flag == true);
        runner = RunnerUtil.parse("value > 5 && st =='test' && state == '正常' && flag == true");
        assertions.assertEquals(runner.run(data), value > 5 && Objects.equals("test", st) && Objects.equals("正常", state) && flag == true);
        runner = RunnerUtil.parse("1 + 2 * (4 - 3) / 2");
        assertions.assertEquals(runner.run(), 1 + 2 * (4 - 3) / 2);
        runner = RunnerUtil.parse("(10 + 20) * 3 / 5 - 6");
        assertions.assertEquals(runner.run(), (10 + 20) * 3 / 5 - 6);
        runner = RunnerUtil.parse("110 + 2 * (40 - 3) / 2");
        assertions.assertEquals(runner.run(), 110 + 2 * (40 - 3) / 2);
        runner = RunnerUtil.parse("3+(2-5)*6/3 ");
        assertions.assertEquals(runner.run(), 3 + (2 - 5) * 6 / 3);
        runner = RunnerUtil.parse("5 * ( 4.1 + 2 -6 /(8-2))");
        assertions.assertEquals(runner.run(), 5 * (4.1 + 2 - 6 / (8 - 2)));
        runner = RunnerUtil.parse("5 * ( 4.1 + 2.9 )");
        assertions.assertEquals(runner.run(), 5 * (4.1 + 2.9));
        runner = RunnerUtil.parse("14/3*2");
        assertions.assertEquals(runner.run(), 14 / 3 * 2);

    }

    @Test
    void testPerformance() {
        str = "1000+100.0*99-(600-3*15)/(((68-9)-3)*2-100)+10000%7*71";
        res = 1000 + 100.0 * 99 - (600 - 3 * 15) / (((68 - 9) - 3) * 2 - 100) + 10000 % 7 * 71;

        runner = RunnerUtil.parse(str);
        assertions.assertEquals(runner.run(), res);

        runner = RunnerUtil.parse(str);
        int count = 10000000;
        Console.out.time();
        for (int i = 0; i < count; i++) {
            res = runner.run();
        }
        Console.out.timeEnd();
        Console.out.time();
        for (int i = 0; i < count; i++) {
            res = 1000 + 100.0 * 99 - (600 - 3 * 15) / (((68 - 9) - 3) * 2 - 100) + 10000 % 7 * 71;
        }
        Console.out.timeEnd();

        // ---------------------------------------------------------------------------------------
    }

    @Test
    void testPerformance1() {
        str = "6.7 - 100 > 39.6 ? 5 == 5 ? 4 + 5 : 6 - 1 : !(100 % 3 - 39.0 < 27) ? 8 * 2 - 199 : 100 % 3";
        runner = RunnerUtil.parse(str);

        res = 6.7 - 100 > 39.6 ? 5 == 5 ? 4 + 5 : 6 - 1 : !(100 % 3 - 39.0 < 27) ? 8 * 2 - 199 : 100 % 3;
        assertions.assertEquals(runner.run(), res);

        str = "6.7 - 100 > 39.6 ? (5 == 5 ? 4 + 5 : 6 - 1) : (!(100 % 3 - 39.0 < 27) ? (8 * 2 - 199) : 100 % 3)";
        runner = RunnerUtil.parse(str);
        res = 6.7 - 100 > 39.6 ? (5 == 5 ? 4 + 5 : 6 - 1) : (!(100 % 3 - 39.0 < 27) ? (8 * 2 - 199) : 100 % 3);
        assertions.assertEquals(runner.run(), res);

        final Runner er = runner;
        int count = 10000000;
        Console.out.time();
        for (int i = 0; i < count; i++) {
            res = 6.7 - 100 > 39.6 ? 5 == 5 ? 4 + 5 : 6 - 1 : !(100 % 3 - 39.0 < 27) ? 8 * 2 - 199 : 100 % 3;
        }
        Console.out.timeEnd();
        Console.out.time();
        for (int i = 0; i < count; i++) {
            res = er.run();
        }
        Console.out.timeEnd();
    }

    @Test
    void testRandom() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        System.out.println(random.nextDouble());
        System.out.println(random.nextDouble(20));
        System.out.println(random.nextDouble(10, 20));
    }

    Map vars = new HashMap() {{
        put("i", 100);
        put("pi", 3.14f);
        put("d", -3.9);
        put("b", (byte) 4);
        put("bool", false);
    }};

    @Test
    void testPerformance2() {
        data = vars;
        str = "i * @Math.PI + (d * b - 199) / (1 - d * @Math.PI) - (2 + 100 - i / @Math.PI) % 99 ==i * @Math.PI + (d * b - 199) / (1 - d * @Math.PI) - (2 + 100 - i / @Math.PI) % 99";
        runner = RunnerUtil.parse(str);

        int i = 100, b = 4;
        double d = -3.9;
        res = i * Math.PI + (d * b - 199) / (1 - d * Math.PI) - (2 + 100 - i / Math.PI) % 99 == i * Math.PI + (d * b - 199) / (1 - d * Math.PI) - (2 + 100 - i / Math.PI) % 99;
        assertions.assertEquals(res, runner.run(data));

        final Runner er = runner;
        int count = 1000;
        Console.out.time();
        for (int j = 0; j < count; j++) {
            res = 6.7 - 100 > 39.6 ? 5 == 5 ? 4 + 5 : 6 - 1 : !(100 % 3 - 39.0 < 27) ? 8 * 2 - 199 : 100 % 3;
        }
        Console.out.timeEnd();
        Console.out.time();
        for (int j = 0; j < count; j++) {
            res = er.run(data);
        }
        Console.out.timeEnd();
    }

    @Test
    void testPerformance6() {

        str = "i * @Math.PI + (d * b - 199) / (1 - d * @Math.PI) - (2 + 100 - i / @Math.PI) % 99 ==i * @Math.PI + (d * b - 199) / (1 - d * @Math.PI) - (2 + 100 - i / @Math.PI) % 99";
        runner = RunnerUtil.parse(str);
        res = runner.run(vars);

        System.out.println(res);

        int count = 100000;
        Console.out.time();
        for (int c = 0; c < count; c++) {
            runner.run(vars);
        }
        Console.out.timeEnd();
    }

    @Test
    void testParse0() {
        str = "1+5+i";
        runner = RunnerUtil.parse(str);
        System.out.println();
    }

    public static class Caller {
        public static final String get() {
            return "123456789";
        }
    }

    public static class InnerObjects {
        public static final String toString(Object o) {
            return "--11--";
        }
    }
}